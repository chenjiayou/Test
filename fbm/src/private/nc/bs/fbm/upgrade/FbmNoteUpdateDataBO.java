package nc.bs.fbm.upgrade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.gather.GatherBillService;
import nc.bs.fbm.pub.CommonDAO;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.bd.CorpVO;
import nc.vo.bd.b08.CustBasMapping;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.bd.b203.SettleunitHeaderVO;
import nc.vo.bd.b23.AccbankVO;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActVO;
import nc.vo.fbm.pub.ReturnMiddleVO;
import nc.vo.fbm.pub.StorageMiddleVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.upgrade.PjfbVO;
import nc.vo.fbm.upgrade.PjzbVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.IBillStatus;

/**
 * 原资金票据的升级数据组织类
 * @author xwq
 *
 */
public class FbmNoteUpdateDataBO {

	private GatherBillService gatherSrv ;


	public List<BusiActVO>[] getUpdateDataForV502FdmNote(
			String oldVersionNumber, String newVersionNumber,Map<String,String> typeMap)
			throws BusinessException {


		List<BusiActVO>[] rtnValues = null;

		if((oldVersionNumber.compareTo("5.02")<0) && (newVersionNumber.compareTo("5.02")>=0)){

			BaseDAO dao=new BaseDAO();

			//将arap的票据数据转换为资金管理的票据数据并返回
			rtnValues = getUpdateData(dao,typeMap);



		}

		//return rtnValues;
		return refactor(rtnValues);

	}



	private List<BusiActVO>[] formData(List<PjzbVO> headResult,List<PjfbVO> dealResult,HashMap<String, String> corpCustMap,HashMap<String,String> custNameMap,HashMap<String,String> settleunitMap,HashMap<String,String> accBankNameMap,HashMap<String,String> settleunitCorpMap,Map<String,String> typeMap) throws BusinessException{

		//存放最终数据的map
		HashMap<String, List<BusiActVO>> resultMap = new LinkedHashMap<String, List<BusiActVO>>();


		//将从fbm_pjzb表（主表）中读取的数据形成"基本信息"和"收付票登记信息"
		if(headResult.size() > 0){
			CurrencyPublicUtil currUtil = null;
			String direction;
			String pk_corp;
			for(int i = 0;i < headResult.size();i++){
				//设置汇率等
				direction = headResult.get(i).getPjfx();
				pk_corp= null;
				if(direction.equals("0")){
					pk_corp = settleunitCorpMap.get(headResult.get(i).getSkdw());
				}else if(direction.equals("1")){
					pk_corp = settleunitCorpMap.get(headResult.get(i).getFkdw());
				}
				currUtil = new CurrencyPublicUtil(pk_corp);
				currUtil.setPk_currtype_y(headResult.get(i).getYbbz());
				if(headResult.get(i).getShrq()!=null){

					UFDouble[] rate = currUtil.getExchangeRate(headResult.get(i).getShrq().toString());
					headResult.get(i).setFbhl(rate[0]);
					headResult.get(i).setBbhl(rate[1]);
					currUtil.setExchangeRate(rate);
					UFDouble[] yfbMoney = currUtil.getYfbMoney(headResult.get(i).getYbje());
					headResult.get(i).setFbje(yfbMoney[1]);
					headResult.get(i).setBbje(yfbMoney[2]);
				}

				//组织数据
				BusiActVO baseInfoVO = formBaseInfoVO(headResult.get(i),corpCustMap,custNameMap,settleunitMap,accBankNameMap,typeMap);
				BusiActVO registerVO = formRegisterVO(headResult.get(i),corpCustMap,custNameMap,settleunitMap,accBankNameMap,settleunitCorpMap);

				List<BusiActVO> noteList = new ArrayList<BusiActVO>();
				noteList.add(baseInfoVO);
				if(registerVO != null){
					noteList.add(registerVO);
				}

				resultMap.put(headResult.get(i).getPk_pjzb(), noteList);

		    }
		}


		//将从fbm_pjzb（主表）中读取的数据放入headVOMap，以备形成"业务变动信息"时读取headVO数据
		HashMap<String, PjzbVO> headVOMap = new HashMap<String, PjzbVO>();
		if(headResult.size() > 0){
			for(int i = 0;i < headResult.size();i++){
				headVOMap.put(headResult.get(i).getPk_pjzb(), headResult.get(i));
			}
		}


		//将从fbm_pjfb表（辅表）中读取的数据形成"业务变动信息"
		if(dealResult.size() > 0){
			for(int i = 0 ;i < dealResult.size();i++){

				PjfbVO itemVO = dealResult.get(i);
				String strKey = itemVO.getPk_pjzb();
				List<BusiActVO> resultList = resultMap.get(strKey);
				PjzbVO headVO = headVOMap.get(strKey);

				//ar:应收;ap:应付
				BusiActVO busiActVO = formBusiActVO(headVO, itemVO, corpCustMap,custNameMap,settleunitMap,accBankNameMap,settleunitCorpMap);
				if(busiActVO.getVo()!=null){
					resultList.add(busiActVO);
				}

			}
		}


		//将resultMap中的数据转换为数组返回
		Collection<List<BusiActVO>> collection = resultMap.values();
		List<BusiActVO>[] rtnValue = null;
		if(collection.size() > 0){
			rtnValue = new List[collection.size()];
		}
		collection.toArray(rtnValue);


		return rtnValue;
	}

	private BusiActVO formRegisterVO(PjzbVO headVO,HashMap<String, String> corpCustMap,HashMap<String, String> custNameMap,HashMap<String,String> settleunitMap,HashMap<String,String> accbankNameMap,HashMap<String,String> settleunitCorpMap) throws BusinessException{

		BusiActVO busiActVo = new BusiActVO();
		String direction = headVO.getPjfx();


		//收票
		if(direction.equals("0")){

			busiActVo.setBusitype(BusiActVO.GATHER);

			RegisterVO vo = new RegisterVO();

			vo.setPk_billtypecode("36GA");
			vo.setGatherdate(headVO.getPjsdrq());
			vo.setGathertype(FbmBusConstant.GATHER_TYPE_INPUT);
			if(headVO.getFkdw() != null){
				String pk_cubasdoc =  custNameMap.get(headVO.getFkdw());
				if(pk_cubasdoc==null || pk_cubasdoc.length() == 0){
					//throw new BusinessException(headVO.getFkdw() + "找不到对应的客商定义");
				}
				vo.setPaybillunit(pk_cubasdoc);
			}
			if(headVO.getSkdw() != null){
				String pk_cubasdoc = settleunitMap.get(headVO.getSkdw());
				vo.setHoldunit(pk_cubasdoc);
				vo.setKeepunit(pk_cubasdoc);
			}
			vo.setMoneyy(headVO.getYbje());
			vo.setFrate(headVO.getFbhl());
			vo.setBrate(headVO.getBbhl());
			vo.setMoneyf(headVO.getFbje());
			vo.setMoneyb(headVO.getBbje());
			vo.setIsnewbill(new UFBoolean(true));
			vo.setSfflag(new UFBoolean(true));
			vo.setPk_curr(headVO.getYbbz());
			//vo.setPk_corp(headVO.getPk_corp());
			vo.setPk_corp(settleunitCorpMap.get(headVO.getSkdw()));
			vo.setVbillstatus(new Integer(1));
			vo.setVoperatorid(headVO.getCzy());
			vo.setDoperatedate(headVO.getCzrq());
			vo.setVapproveid(headVO.getShr());

			if(headVO.getShrq() != null){
				vo.setDapprovedate(new UFDate(headVO.getShrq().toString().trim()));
			}
			vo.setPrimaryKey(headVO.getPrimaryKey());
			vo.setPk_source(headVO.getPjlyid());


			//如果收票对应的上游收票状态为内部贴现，则修改这个收票的PK为pjlyid值，最后在处理时如果收票PK重复，则不处理
			String pjlyid = headVO.getPjlyid();
			if(pjlyid !=null){
				BaseDAO dao = new BaseDAO();
				PjzbVO lypj = (PjzbVO)dao.retrieveByPK(PjzbVO.class, pjlyid);
				if(lypj != null && lypj.getPjzt().equals("02") ){//状态为已内部贴现
					vo.setPrimaryKey(headVO.getPjlyid());
				}
			}
			busiActVo.setVo(vo);
		}
		//开票
		else if(direction.equals("1")){

			busiActVo.setBusitype(BusiActVO.INVOICE);

			RegisterVO vo = new RegisterVO();

			vo.setPk_billtypecode("36GL");
			vo.setMoneyy(headVO.getYbje());
			vo.setFrate(headVO.getFbhl());
			vo.setBrate(headVO.getBbhl());
			vo.setMoneyf(headVO.getFbje());
			vo.setMoneyb(headVO.getBbje());

			vo.setIsnewbill(new UFBoolean(true));

			//处理担保方式
			String oldImpawnmode = headVO.getDbfs().trim();
			String newImpawnmode = null;
			if(oldImpawnmode.equals("0")){//信用
				newImpawnmode = FbmBusConstant.ASSURETYPE_CREDIT;
			}else if(oldImpawnmode.equals("1")){//保证
				newImpawnmode = FbmBusConstant.ASSURETYPE_ASSURE;
			}else if(oldImpawnmode.equals("2")){//抵押
				newImpawnmode = FbmBusConstant.ASSURETYPE_PLEDGE;
			}else if(oldImpawnmode.equals("3")){//质押
				newImpawnmode = FbmBusConstant.ASSURETYPE_IMPAWN;
			}else if(oldImpawnmode.equals("4")){//保证金
				newImpawnmode = FbmBusConstant.ASSURETYPE_BAIL;
			}
			vo.setImpawnmode(newImpawnmode);
			if(headVO.getBzjbl()!=null){
				vo.setSecurityrate(headVO.getBzjbl().multiply(100));//小数转百分数
			}
			vo.setSecuritymoney(headVO.getBzje());
			vo.setSecurityaccount(headVO.getBzjzh());
			vo.setPoundagemoney(headVO.getSxfje());

			vo.setCctype("NONE");
			vo.setSfflag(new UFBoolean(true));
			vo.setIsverify(new UFBoolean(false));
			vo.setPk_corp(settleunitCorpMap.get(headVO.getFkdw()));
			vo.setVbillstatus(new Integer(1));
			vo.setVoperatorid(headVO.getCzy());
			vo.setDoperatedate(headVO.getCzrq());
			vo.setVapproveid(headVO.getShr());
			vo.setPk_curr(headVO.getYbbz());

			if(headVO.getShrq() != null){
				vo.setDapprovedate(new UFDate(headVO.getShrq().toString().trim()));
			}
			vo.setPrimaryKey(headVO.getPrimaryKey());
			busiActVo.setVo(vo);
		}


		return busiActVo;

	}


	private BusiActVO formBaseInfoVO(PjzbVO headVO,HashMap<String, String> corpCustMap,HashMap<String,String>  custNameMap,HashMap<String,String> settleunitMap,HashMap<String,String> accBankNameMap,Map<String,String> typeMap) throws BusinessException{

		BusiActVO busiActVo = new BusiActVO();
		busiActVo.setBusitype(BusiActVO.BASEINFO);

		BaseinfoVO vo = new BaseinfoVO();

		vo.setFbmbillno(headVO.getPjbh());
		vo.setFbmbilltype(typeMap.get(headVO.getPjlx()));

		vo.setInvoiceunitname(headVO.getCpr());
		if(headVO.getCpr()!= null){
			vo.setInvoiceunit(custNameMap.get(headVO.getCpr()));
		}




		vo.setPk_curr(headVO.getYbbz());
		vo.setMoneyy(headVO.getYbje());
		vo.setInvoicedate(headVO.getCprq());
		vo.setEnddate(headVO.getDqrq());
		vo.setKeepunit(corpCustMap.get(headVO.getPk_corp()));
		vo.setDisable(new UFBoolean(false));

		//******************注意**********************
		String direction = headVO.getPjfx();
		//orientation(应收票据：ar;应付票据：ap)
		if(direction != null){
			if(direction.equals("0")){
				vo.setOrientation("ar");
				vo.setPayunit(custNameMap.get(headVO.getFkdw()));

				String pk_cubasdoc = settleunitMap.get(headVO.getSkdw());
				vo.setReceiveunit(pk_cubasdoc);
				vo.setReceivebankacc(headVO.getSkyh());
				String paybankacc = accBankNameMap.get(headVO.getFkyh());
				if(paybankacc == null){
					paybankacc = getGatherService().insertInputAccbank(headVO.getYbbz(), headVO.getFkyh(), headVO.getFkyh());
				}
				vo.setPaybankacc(paybankacc);
			}else if(direction.equals("1")){
				vo.setOrientation("ap");
				vo.setPayunit(settleunitMap.get(headVO.getFkdw()));
				vo.setReceiveunit(custNameMap.get(headVO.getSkdw()));

				String recbankacc = accBankNameMap.get(headVO.getSkyh());
				if(recbankacc == null){
					recbankacc = getGatherService().insertInputAccbank(headVO.getYbbz(), headVO.getSkyh(),headVO.getSkyh());
				}
				vo.setReceivebankacc(recbankacc);
				vo.setPaybankacc(headVO.getFkyh());
			}
		}

		busiActVo.setVo(vo);

		return busiActVo;

	}

	/**
	 * 检查应收票据的付款单位名称是否建立了客商基本档案
	 * @param dao
	 * @throws BusinessException
	 */
	private void checkFkdw(BaseDAO dao) throws BusinessException{
		String sql = "select pjbh,fkdw from fbm_pjzb  where pjfx = '0' and isnull(fbm_pjzb.dr,0)=0 and fkdw not in(select custname from bd_cubasdoc)";
		List<PjzbVO> vos = (List)dao.executeQuery(sql, new BeanListProcessor(PjzbVO.class));
		if(vos!= null && vos.size() >0){
			StringBuffer sb = new StringBuffer();
			sb.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000348")/* @res"下列付款单位客商名称没有在客商基本档案中：\n"*/);
			for(PjzbVO vo:vos){
				sb.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/+vo.getPjbh()+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000349")/* @res"的付款单位名称:"*/+vo.getFkdw());
				sb.append(",\n");
			}
			throw new BusinessException(sb.toString());
		}
	}
	/**
	 * 检查收付票据的收款单位名称是否建立了客商基本档案
	 * @param dao
	 * @throws BusinessException
	 */
	private void checkSkdw(BaseDAO dao) throws BusinessException{
		String sql = "select pjbh,skdw from fbm_pjzb where pjfx = '1' and isnull(fbm_pjzb.dr,0)=0 and skdw not in(select custname from bd_cubasdoc)";
		List<PjzbVO> vos = (List)dao.executeQuery(sql, new BeanListProcessor(PjzbVO.class));
		if(vos!= null && vos.size() >0){
			StringBuffer sb = new StringBuffer();
			sb.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000348")/* @res"下列付款单位客商名称没有在客商基本档案中：\n"*/);
			for(PjzbVO vo:vos){
				sb.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/+vo.getPjbh()+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000350")/* @res"的收款单位名称:"*/+vo.getSkdw());
				sb.append(",\n");
			}
			throw new BusinessException(sb.toString());
		}
	}

	private List<BusiActVO>[] getUpdateData(BaseDAO dao,Map<String,String> typeMap) throws BusinessException {
		//检查客商名称是否在客商基本档案中
		checkSkdw(dao);
		checkFkdw(dao);

		//检查是否数据是否已经审核
		String sql_check = "select pjbh from fbm_pjzb left join fbm_pjfb on (fbm_pjzb.pk_pjzb = fbm_pjfb.pk_pjzb) where isnull(fbm_pjzb.dr,0)=0 and isnull(fbm_pjfb.dr,0)=0  and ( fbm_pjzb.shzt <> 1 or fbm_pjfb.shzt <> 1)";
		Object ret = dao.executeQuery(sql_check, new ColumnListProcessor());
		if(ret!= null){
			StringBuffer sb = new StringBuffer();
			Object[] objTmp = ((List)ret).toArray(new Object[0]);
			if(objTmp!=null && objTmp.length > 0){
				sb.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000351")/* @res"下列票据未审核通过，不能进行票据升级\n"*/);
				for(Object pjbh:objTmp){
					sb.append(pjbh);
					sb.append("\n");
				}
				throw new BusinessException(sb.toString());
			}
		}



		//银行账号map
		List<AccbankVO> accList = (List<AccbankVO>)dao.retrieveByClause(AccbankVO.class, " isnull(dr,0)=0");
		HashMap<String,String> accBankNameMap = new HashMap<String,String>();
		for(AccbankVO accbank:accList){
			accBankNameMap.put(accbank.getBankname(),accbank.getPk_accbank());
		}

		//查找结算单位对应公司
		String settleSQL = "select pk_settleunit,pk_corp1,shortname from bd_settleunit where pk_settleunit in(select skdw from fbm_pjzb where pjfx = '0' union select fkdw from fbm_pjzb where pjfx = '1')";
		List<Object[]> settleList = (List<Object[]>)dao.executeQuery(settleSQL, new ArrayListProcessor());
		if(settleList == null ){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000352")/* @res"票据结算单位错误"*/);
		}

		HashMap<String,String> settleunitCorp = new HashMap<String,String>();
		List<String> corpList = new ArrayList<String>();
		for(Object[] tmp:settleList){
			String settleunit = (String)tmp[0];
			String pk_corp = (String)tmp[1];
			String shortname = (String)tmp[2];
			if(pk_corp == null){
				throw new BusinessException(shortname + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000353")/* @res"没有对应公司"*/);
			}
			settleunitCorp.put(settleunit, pk_corp);
			corpList.add(pk_corp);
		}

		String[] pk_corps = corpList.toArray(new String[0]);

		//检查公司是否安装了新的票据
		CommonDAO comDao = new CommonDAO();
		for(String pk_corp:pk_corps){
			boolean fbmEnable = comDao.productEnableByCorp(pk_corp, FbmBusConstant.SYSCODE_FBM);
			if(!fbmEnable){
				CorpVO corpVO = (CorpVO)dao.retrieveByPK(CorpVO.class, pk_corp);
				if(corpVO != null){
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000354")/* @res"公司:"*/+corpVO.getUnitname() +nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000355")/* @res"没有安装资金票据，请安装后升级"*/);
				}
			}
		}


		//1.存放客商及其对应公司的map
		HashMap<String, String> corpCustMap = new HashMap<String, String>();
		//客商名称和基本档案PK的关系
		HashMap<String,String> custNameMap= new HashMap<String,String>();
		CustBasVO[] vos = null;
		String where = "  pk_cubasdoc is not null";
		Collection c = dao.retrieveByClause(CustBasVO.class,new CustBasMapping(), where, new String[] { "pk_corp1","custname","pk_cubasdoc" });
		if (c != null && c.size() > 0) {
			vos = new CustBasVO[c.size()];
			c.toArray(vos);

			for(int i = 0;i < vos.length;i++){
				custNameMap.put(vos[i].getCustname(),vos[i].getPk_cubasdoc());
				corpCustMap.put(vos[i].getPk_corp1(), vos[i].getPk_cubasdoc());
			}
		}


		//2.查询数据(为资金开发部处理方便,按票号和签发日期排序)
		//要排除内部贴现生成的收票登记单
		String sql6= "select * from fbm_pjzb where isnull(dr,0)=0 order by pjbh,pjsdrq,ts ";
		List<PjzbVO> headResult=(List<PjzbVO>)dao.executeQuery(sql6,new BeanListProcessor(PjzbVO.class));
		if(headResult == null || headResult.size() == 0){
			return null;
		}



		//校验（有票据的公司必须在客商基本档案中被设置为“对应单位”）

		//未建好客商与相应公司对应关系的map
		HashMap<String, String> exceptionMap = new HashMap<String, String>();

		for(String pk_corp:pk_corps){
			if(corpCustMap.get(pk_corp) == null){
				exceptionMap.put(pk_corp,"");
			}
		}


		//抛出异常
		if(exceptionMap.size() > 0){
			HashMap<String, CorpVO> tempMap = getCorp(dao);
			String[] tempCorps = new String[exceptionMap.size()];
			exceptionMap.keySet().toArray(tempCorps);

			StringBuffer strHint =new StringBuffer();
			strHint.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000356")/* @res"升级前已使用票据备查簿且需要进行数据升级的公司，都必须建成客商基本档案，并且与相应公司建立对应关系."*/);
			strHint.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000357")/* @res"不符合要求的公司编码为:"*/);
			for(int i = 0;i < tempCorps.length;i++){
				strHint.append(tempMap.get(tempCorps[i]).getUnitcode());
				strHint.append(",");
			}

			throw new BusinessException(strHint.toString());
		}

		StringBuffer strInCondition = new StringBuffer();
		String strIn = "";
		if(pk_corps != null){
			for(int i = 0; i < pk_corps.length;i++){
				strInCondition.append("'");
				strInCondition.append(pk_corps[i]);
				strInCondition.append("'");
				strInCondition.append(",");
			}
		}
		if(strInCondition.length() > 0){
			strIn = strInCondition.substring(0, strInCondition.length()-1);
		}

//		CumandocVO[] vos2 = null;
//		//存放管理档案和基本档案对应关系的map，用于基本信息和登记信息中的数据转换
//		HashMap<String, String> custBasManMap = new HashMap<String, String>();
//
		String strWhere = null;
		HashMap<String,String> settleunitCustMap = new HashMap<String,String>();
		if(strIn.length() > 0){
			strWhere = " bd_cubasdoc.pk_corp1 in " + "(" + strIn + ")";
			String sql_settltunit = "select pk_settleunit,pk_cubasdoc from bd_cubasdoc join bd_settleunit on bd_cubasdoc.pk_corp1=bd_settleunit.pk_corp1 where "+strWhere;
			List list = (List)dao.executeQuery(sql_settltunit, new ArrayListProcessor());
			if(list != null && list.size() > 0){
				Object[] tmp;
				for(Object obj :list){
					tmp = (Object[])obj;
					settleunitCustMap.put(tmp[0].toString(), tmp[1].toString());
				}
			}else {
				HashMap<String, CorpVO> tempMap = getCorp(dao);
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000358")/* @res"结算单位没有对应公司或客商没有对应公司："*/ + tempMap.get(pk_corps[0]));
			}

		}


		List<PjfbVO> dealResult=(List<PjfbVO>)dao.retrieveByClause(PjfbVO.class," dr = 0"," pk_pjzb,sjywrq,pk_pjfb");

		//3.整理数据
		List<BusiActVO>[] rtnValues = formData(headResult,dealResult, corpCustMap,custNameMap,settleunitCustMap,accBankNameMap,settleunitCorp,typeMap);


		return rtnValues;

	}

	private BusiActVO formBusiActVO(PjzbVO headVO,PjfbVO itemVO,HashMap<String, String> corpCustMap,HashMap<String, String> custNameMap,HashMap<String,String> settleunitMap,HashMap<String,String> accbankNameMap,HashMap<String,String> settleunitCorpMap) throws BusinessException{

		BusiActVO busiActVo = new BusiActVO();

		CurrencyPublicUtil currUtil ;



		//应收结算(托收)
		if(itemVO.getPjywlx() != null && itemVO.getPjywlx().trim().equalsIgnoreCase("GE")){

			busiActVo.setBusitype(BusiActVO.COLLECTION);
			CollectionVO vo = new CollectionVO();

			vo.setPk_corp(settleunitCorpMap.get(headVO.getSkdw()));

			//记录副表ＰＫ
			vo.setPk_collection(itemVO.getPk_pjfb());
			//记录主表ＰＫ
			vo.setPk_source(headVO.getPk_pjzb());
			vo.setHolderacc(itemVO.getZdyx4());

			vo.setDconsigndate(itemVO.getSqrq());
			vo.setMoneyy(itemVO.getSfje());
			vo.setDcollectiondate(itemVO.getSjywrq());
			vo.setNote(itemVO.getBz());
			vo.setVapprovenote(itemVO.getThyy());
			vo.setVtransactorid(itemVO.getSqr());
			vo.setDtransactdate(itemVO.getSqrq());

			//已办理
			vo.setVbillstatus(IFBMStatus.Transact);
			vo.setVoperatorid(itemVO.getCzy());
			vo.setDoperatedate(itemVO.getCzrq());
			vo.setVapproveid(itemVO.getShr());
			if(itemVO.getShrq() != null){
				vo.setDapprovedate(new UFDate(itemVO.getShrq().toString().trim()));
			}
			vo.setFrate(headVO.getFbhl());
			vo.setBrate(headVO.getBbhl());
			currUtil =  new CurrencyPublicUtil(vo.getPk_corp());
			currUtil.setPk_currtype_y(headVO.getYbbz());
			currUtil.setExchangeRate(new UFDouble[]{headVO.getFbhl(),headVO.getBbhl()});
			UFDouble[] yfbmoney = currUtil.getYfbMoney(itemVO.getSfje());
			vo.setMoneyf(yfbmoney[1]);
			vo.setMoneyb(yfbmoney[2]);

			busiActVo.setVo(vo);
		}


		//应付结算(承兑)
		else if(itemVO.getPjywlx() != null && itemVO.getPjywlx().trim().equalsIgnoreCase("GN")){

			busiActVo.setBusitype(BusiActVO.ACCEPT);
			AcceptVO vo = new AcceptVO();

			vo.setPk_corp(settleunitCorpMap.get(headVO.getFkdw()));
			//记录副表ＰＫ
			vo.setPk_accept(itemVO.getPk_pjfb());
			//取主表ＰＫ
			vo.setPk_source(headVO.getPk_pjzb());
			vo.setPk_billtypecode("36GM");
			vo.setBacksecmoney(new UFDouble(itemVO.getZdyx3()));
			vo.setBacksecaccount(itemVO.getZdyx2());
			vo.setMoneyy(itemVO.getSfje());
			vo.setDacceptnotedate(itemVO.getSqrq());
			vo.setDacceptdate(itemVO.getSjywrq());
			vo.setNote(itemVO.getBz());
			vo.setVbillstatus(new Integer(1));
			vo.setVoperatorid(itemVO.getCzy());
			vo.setDoperatedate(itemVO.getCzrq());
			vo.setVapproveid(itemVO.getShr());
			if(itemVO.getShrq() != null){
				vo.setDapprovedate(new UFDate(itemVO.getShrq().toString().trim()));
			}
			vo.setVapprovenote(itemVO.getThyy());
			vo.setFrate(headVO.getFbhl());
			vo.setBrate(headVO.getBbhl());
			currUtil =  new CurrencyPublicUtil(vo.getPk_corp());
			currUtil.setPk_currtype_y(headVO.getYbbz());
			currUtil.setExchangeRate(new UFDouble[]{headVO.getFbhl(),headVO.getBbhl()});
			UFDouble[] yfbmoney = currUtil.getYfbMoney(itemVO.getSfje());
			vo.setMoneyf(yfbmoney[1]);
			vo.setMoneyb(yfbmoney[2]);
			busiActVo.setVo(vo);

		}



		//应收转出(退票)
		else if(itemVO.getPjywlx() != null && itemVO.getPjywlx().trim().equalsIgnoreCase("GI")){

			busiActVo.setBusitype(BusiActVO.RETURNBILL);
			ReturnMiddleVO vo = new ReturnMiddleVO();

			vo.setPk_corp(settleunitCorpMap.get(headVO.getSkdw()));

			vo.setPk_billtypecode("36GN");
			vo.setReturntype(FbmBusConstant.BACK_TYPE_GATHER);
			vo.setReturnperson(itemVO.getSqr());
			vo.setDreturndate(itemVO.getSjywrq());
			vo.setReturnnote(itemVO.getBz());
			vo.setBusdate(itemVO.getSjywrq());
			//取主表ＰＫ
			vo.setPk_source(itemVO.getPk_pjzb());
			//记录副表ＰＫ
			vo.setPk_return(itemVO.getPk_pjfb());
			vo.setVbillstatus(new Integer(1));
			vo.setVoperatorid(itemVO.getCzy());
			vo.setDoperatedate(itemVO.getCzrq());
			vo.setVapproveid(itemVO.getShr());
			vo.setVapprovenote(itemVO.getThyy());
			if(itemVO.getShrq() != null){
				vo.setDapprovedate(new UFDate(itemVO.getShrq().toString().trim()));
			}
			busiActVo.setVo(vo);
		}


		//背书
		else if(itemVO.getPjywlx() != null && (itemVO.getPjywlx().trim().equalsIgnoreCase("GF") || itemVO.getPjywlx().trim().equalsIgnoreCase("GG"))){

			busiActVo.setBusitype(BusiActVO.ENDORE);
			EndoreVO vo = new EndoreVO();

			vo.setPk_corp(settleunitCorpMap.get(headVO.getSkdw()));
			//取副表ＰＫ
			vo.setPk_endore(itemVO.getPk_pjfb());
			//取主表ＰＫ
			vo.setPk_source(itemVO.getPk_pjzb());


			//xwq 2008.5.7 浙江三狮升级错误
			if(itemVO.getPjywlx().trim().equalsIgnoreCase("GF")){//内部背书
				//自定义项1标识结算单位PK
				vo.setEndorsee(settleunitMap.get(itemVO.getZdyx1()));
				vo.setEndoretype(EndoreVO.INNER);
				vo.setEndorser(corpCustMap.get(settleunitCorpMap.get(headVO.getSkdw())));
			}else{
				//自定义项1标识外部单位名称
				vo.setEndorsee(custNameMap.get(itemVO.getZdyx1()));
				vo.setEndoretype(EndoreVO.OUTER);
				vo.setEndorser(corpCustMap.get(headVO.getPk_corp()));
			}

			vo.setBusdate(itemVO.getSjywrq());
			vo.setMoneyy(itemVO.getSfje());
			vo.setNote(itemVO.getBz());
			vo.setVbillstatus(new Integer(1));
			vo.setVoperatorid(itemVO.getCzy());
			vo.setDoperatedate(itemVO.getCzrq());
			vo.setVapproveid(itemVO.getShr());
			if(itemVO.getShrq() != null){
				vo.setDapprovedate(new UFDate(itemVO.getShrq().toString().trim()));
			}

			vo.setFrate(headVO.getFbhl());
			vo.setBrate(headVO.getBbhl());
			currUtil =  new CurrencyPublicUtil(vo.getPk_corp());
			currUtil.setPk_currtype_y(headVO.getYbbz());
			currUtil.setExchangeRate(new UFDouble[]{headVO.getFbhl(),headVO.getBbhl()});
			UFDouble[] yfbmoney = currUtil.getYfbMoney(itemVO.getSfje());
			vo.setMoneyf(yfbmoney[1]);
			vo.setMoneyb(yfbmoney[2]);

			busiActVo.setVo(vo);
		}

		//质押
		else if(itemVO.getPjywlx() != null && itemVO.getPjywlx().trim().equalsIgnoreCase("GH")){
			BaseDAO baseDao = new BaseDAO();
			List list = (List)baseDao.retrieveByClause(SettleunitHeaderVO.class, "settleunitname='" + itemVO.getYwryhzh() +"'");
			String pk_settleunit = null;
			if(list!=null && list.size() >0){
				SettleunitHeaderVO settleunitvo = (SettleunitHeaderVO) list.get(0);
				pk_settleunit = settleunitvo.getPrimaryKey();
			}

			busiActVo.setBusitype(BusiActVO.IMPAWN);
			ImpawnVO vo = new ImpawnVO();

			vo.setPk_corp(settleunitCorpMap.get(headVO.getSkdw()));
			//取副表ＰＫ
			vo.setPk_impawn(itemVO.getPk_pjfb());
			//取主表ＰＫ
			vo.setPk_source(itemVO.getPk_pjzb());
			vo.setImpawndate(itemVO.getSjywrq());
			vo.setMoneyy(itemVO.getSfje());
			vo.setImpawnable(itemVO.getSfje());
			vo.setEvaluatevalue(itemVO.getSfje());

			vo.setImpawnrate(itemVO.getSfje().div(headVO.getYbje()).multiply(100));

			vo.setDebitunit(settleunitMap.get(pk_settleunit));
			vo.setCreditunit(itemVO.getYwrkhyh());
			vo.setVbillstatus(new Integer(1));
			vo.setVoperatorid(itemVO.getCzy());
			vo.setDoperatedate(itemVO.getCzrq());
			vo.setVapproveid(itemVO.getShr());
			vo.setVapprovenote(itemVO.getThyy());
			if(itemVO.getShrq() != null){
				vo.setDapprovedate(new UFDate(itemVO.getShrq().toString().trim()));
			}
			vo.setImpawnunit(itemVO.getSqr());
			vo.setFrate(headVO.getFbhl());
			vo.setBrate(headVO.getBbhl());
			currUtil =  new CurrencyPublicUtil(vo.getPk_corp());
			currUtil.setPk_currtype_y(headVO.getYbbz());
			currUtil.setExchangeRate(new UFDouble[]{headVO.getFbhl(),headVO.getBbhl()});
			UFDouble[] yfbmoney = currUtil.getYfbMoney(itemVO.getSfje());
			vo.setMoneyf(yfbmoney[1]);
			vo.setMoneyb(yfbmoney[2]);
			busiActVo.setVo(vo);
		}
		//质押回收
		else if(itemVO.getPjywlx() != null && itemVO.getPjywlx().trim().equalsIgnoreCase("GO")){
			BaseDAO baseDao = new BaseDAO();
			List list = (List)baseDao.retrieveByClause(SettleunitHeaderVO.class, "settleunitname='" + itemVO.getYwryhzh() +"'");
			String pk_settleunit = null;
			if(list!=null && list.size() >0){
				SettleunitHeaderVO settleunitvo = (SettleunitHeaderVO) list.get(0);
				pk_settleunit = settleunitvo.getPrimaryKey();
			}


			busiActVo.setBusitype(BusiActVO.IMPAWNBACK);
			ImpawnVO vo = new ImpawnVO();

			vo.setPk_corp(settleunitCorpMap.get(headVO.getSkdw()));
			//取副表ＰＫ
			vo.setPk_impawn(itemVO.getPk_pjfb());
			//取主表ＰＫ
			vo.setPk_source(itemVO.getPk_pjzb());

			vo.setImpawnable(itemVO.getSfje());
			vo.setEvaluatevalue(itemVO.getSfje());
			vo.setImpawndate(itemVO.getSjywrq());
			vo.setImpawnbackdate(itemVO.getSjywrq());
			vo.setImpawnrate(itemVO.getSfje().div(headVO.getYbje()).multiply(100));
			vo.setMoneyy(itemVO.getSfje());
			vo.setDebitunit(settleunitMap.get(pk_settleunit));
			vo.setCreditunit(itemVO.getYwrkhyh());
			vo.setVbillstatus(IFBMStatus.IMPAWN_BACK);
			vo.setVoperatorid(itemVO.getCzy());
			vo.setDoperatedate(itemVO.getCzrq());
			vo.setVapproveid(itemVO.getShr());
			vo.setVapprovenote(itemVO.getThyy());
			if(itemVO.getShrq() != null){
				vo.setDapprovedate(new UFDate(itemVO.getShrq().toString().trim()));
			}
			vo.setImpawnunit(itemVO.getSqr());
			vo.setImpawnbackunit(itemVO.getSqr());
			vo.setFrate(headVO.getFbhl());
			vo.setBrate(headVO.getBbhl());
			currUtil =  new CurrencyPublicUtil(vo.getPk_corp());
			currUtil.setPk_currtype_y(headVO.getYbbz());
			currUtil.setExchangeRate(new UFDouble[]{headVO.getFbhl(),headVO.getBbhl()});
			UFDouble[] yfbmoney = currUtil.getYfbMoney(itemVO.getSfje());
			vo.setMoneyf(yfbmoney[1]);
			vo.setMoneyb(yfbmoney[2]);
			busiActVo.setVo(vo);

		}
		//贴现
		else if(itemVO.getPjywlx() != null &&  (itemVO.getPjywlx().trim().equalsIgnoreCase("GC") || itemVO.getPjywlx().trim().equalsIgnoreCase("GD"))){

			busiActVo.setBusitype(BusiActVO.DISCOUNT);
			DiscountVO vo = new DiscountVO();

			vo.setPk_corp(settleunitCorpMap.get(headVO.getSkdw()));
			//取副表ＰＫ
			vo.setPk_discount(itemVO.getPk_pjfb());
			//主表ＰＫ
			vo.setPk_source(itemVO.getPk_pjzb());
			vo.setApplydate(itemVO.getSqrq());
			vo.setDiscount_account(itemVO.getYwrkhyh());
			vo.setResult(FbmBusConstant.DISCOUNT_RESULT_SUCCESS);
			vo.setDdiscountdate(itemVO.getSjywrq());
			vo.setDiscountyrate(itemVO.getTxlv());
			vo.setRatedaynum(new Integer(360));
			vo.setMoneyy(itemVO.getSfje());
			vo.setNote(itemVO.getBz());
			vo.setDiscountinterest(itemVO.getTxlx());
			vo.setDiscountcharge(new UFDouble(0));
			vo.setVtransactorid(itemVO.getSqr());
			vo.setDtransactdate(itemVO.getSqrq());
			vo.setVbillstatus(new Integer(1));
			vo.setVoperatorid(itemVO.getCzy());
			vo.setDoperatedate(itemVO.getCzrq());
			vo.setVapproveid(itemVO.getShr());
			vo.setVapprovenote(itemVO.getThyy());
			if(itemVO.getShrq() != null){
				vo.setDapprovedate(new UFDate(itemVO.getShrq().toString().trim()));
			}
			vo.setFrate(headVO.getFbhl());
			vo.setBrate(headVO.getBbhl());
			currUtil =  new CurrencyPublicUtil(vo.getPk_corp());
			currUtil.setPk_currtype_y(headVO.getYbbz());
			currUtil.setExchangeRate(new UFDouble[]{headVO.getFbhl(),headVO.getBbhl()});
			UFDouble[] yfbmoney = currUtil.getYfbMoney(itemVO.getSfje());
			vo.setMoneyf(yfbmoney[1]);
			vo.setMoneyb(yfbmoney[2]);

			String pjlyid = headVO.getPjlyid();
			//内部贴现形成收票的后续操作
			if(itemVO.getPjywlx().trim().equalsIgnoreCase("GC")){
				vo.setInnerDiscountPJ(true);

			}
			busiActVo.setVo(vo);
		}

		return busiActVo;
	}


	public FbmNoteUpdateDataBO() {
		super();
	}

	private HashMap<String, CorpVO> getCorp(BaseDAO dao) throws BusinessException {

		HashMap<String, CorpVO> rtnMap = new HashMap<String, CorpVO>();

		Collection c = dao.retrieveAll(CorpVO.class);
		if (c != null && c.size() > 0) {

			CorpVO[] vos = new CorpVO[c.size()];
			c.toArray(vos);

			for(int i = 0;i < vos.length;i++){
				rtnMap.put(vos[i].getPk_corp(), vos[i]);
			}
		}

		return rtnMap;
	}

	private GatherBillService getGatherService() throws BusinessException{
		if(gatherSrv == null){
			gatherSrv = new GatherBillService();
		}
		return gatherSrv;
	}

	/**
	 * 对旧业务数据插入统管业务数据
	 * @param rtnValues
	 * @return
	 * @throws BusinessException
	 */
	private List<BusiActVO>[] refactor(List<BusiActVO>[] rtnValues ) throws BusinessException{
		if(rtnValues != null){
			int len = rtnValues.length;
			List<BusiActVO>[] refactVos = new LinkedList[len];//重构後的业务数据
			for(int i = 0; i < len; i++){
				List<BusiActVO> actVos = rtnValues[i];
				refactVos[i] = new LinkedList<BusiActVO>();

				int busCount = actVos.size();
				BusiActVO tmp ;

				if(busCount == 2 && actVos.get(1).getBusitype() == BusiActVO.GATHER){//只有收票的情况，在最后加内部存放
					refactVos[i].add(actVos.get(0));
					refactVos[i].add(actVos.get(1));
					refactVos[i].add(makeStorage((RegisterVO)(actVos.get(1).getVo()),actVos.get(1)));
				}else{
					for(int j = 0; j < busCount ; j ++ ){
						tmp = actVos.get(j);

						//只有统管业务才加上存放
						if(tmp.getBusitype() == BusiActVO.COLLECTION || (tmp.getBusitype() == BusiActVO.DISCOUNT ) ||  (tmp.getBusitype() == BusiActVO.ENDORE && ((EndoreVO)tmp.getVo()).getEndoretype() == EndoreVO.OUTER)){
							BusiActVO storageVO = makeStorage((RegisterVO)(actVos.get(1).getVo()), tmp);
							if(storageVO !=null){
								refactVos[i].add(storageVO);
							}
						}

						refactVos[i].add(tmp);
					}
				}
			}//end of for
			return refactVos;
		}
		return null;
	}

	private BusiActVO makeStorage(RegisterVO regVO,BusiActVO currAct) throws BusinessException {
		BusiActVO storageAct = new BusiActVO();
		storageAct.setBusitype(BusiActVO.INNER_KEEP);


		BaseDAO dao = new BaseDAO();
		String sqlCentreCorp = "select c.pk_corp from bd_settleunit s join bd_settlecenter c on s.pk_settlecent = c.pk_settlecenter where s.pk_corp1='"+regVO.getPk_corp()+"' ";
		List ret = (List)dao.executeQuery(sqlCentreCorp, new ColumnListProcessor());
		String pk_corp = null;
		if(ret == null && ret.size() == 0){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000171")/*@res "找不到中心对应的公司"*/);
		}
		pk_corp = (String)ret.get(0);//中心对应公司

		String sqlCentreCust = "select pk_cubasdoc from bd_cubasdoc where pk_corp1='"+pk_corp+"'";
		ret = (List)dao.executeQuery(sqlCentreCust, new ColumnListProcessor());
		if(ret == null && ret.size() == 0){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000170")/*@res "找不到对应中心公司的客商基本档案信息"*/);
		}
		String pk_cubasdoc = (String)ret.get(0);
		regVO.setKeepunit(pk_cubasdoc);//只要做了存放，原收票登记的存放地点要改变

		StorageMiddleVO vo = new StorageMiddleVO();
		vo.setInputperson(regVO.getVoperatorid());
		vo.setPk_corp(regVO.getPk_corp());
		vo.setKeepunit(regVO.getHoldunit());

		vo.setPk_billtypecode(FbmBusConstant.BILLTYPE_INNERKEEP);
		vo.setVbillstatus(IBillStatus.FREE);
		vo.setVapproveid((String)currAct.getVo().getAttributeValue("vapproveid"));
		vo.setVoperatorid((String)currAct.getVo().getAttributeValue("voperatorid"));
		vo.setInputtype(FbmBusConstant.KEEP_TYPE_RELIEF);
		vo.setPk_source(regVO.getPrimaryKey());
		vo.setPk_baseinfo(regVO.getPk_baseinfo());
		vo.setPk_curr(regVO.getPk_curr());

		UFDate inputdate = null;
		if(currAct.getBusitype() == BusiActVO.COLLECTION){
			CollectionVO collectionVO = (CollectionVO)currAct.getVo();
			UFDate transactdate = collectionVO.getDtransactdate();
			UFDate disabledate = collectionVO.getDdisabledate();
			if(disabledate != null){
				inputdate = disabledate;
			}
			if(transactdate != null){
				inputdate = transactdate;
			}
		}else if(currAct.getBusitype() == BusiActVO.DISCOUNT){
			DiscountVO discountVO = (DiscountVO)currAct.getVo();
			inputdate = discountVO.getDtransactdate();
		}else if(currAct.getBusitype() ==  BusiActVO.GATHER){
			RegisterVO registerVO = (RegisterVO)currAct.getVo();
			inputdate = registerVO.getGatherdate();
		}else if(currAct.getBusitype() == BusiActVO.ENDORE){
			EndoreVO endoreVO = (EndoreVO)currAct.getVo();
			inputdate = endoreVO.getBusdate();
		}

		vo.setInputdate(inputdate);
		vo.setDoperatedate(inputdate);
		vo.setDapprovedate(inputdate);

		//统管业务如果是引用了内贴形成的收票登记单，则取贴现息
		List list = (List)dao.retrieveByClause(PjfbVO.class, "isnull(dr,0)=0 and pjywlx= 'GC' and pk_pjzb='" + regVO.getPrimaryKey() +"'");
		if(list != null && list.size() > 0){
			vo.setDiscountinterest(((PjfbVO)list.get(0)).getTxlx());
		}


		storageAct.setVo(vo);

		if(currAct.getBusitype() != BusiActVO.GATHER){//注意：如果传入的是收票登记，那么不可修改其公司PK
			currAct.getVo().setAttributeValue("pk_corp", pk_corp);//将统管的业务单据公司改成中心
		}

		//如果当前是内贴，那么返回空，即不加内部存放
		if(currAct.getBusitype() == BusiActVO.DISCOUNT && ((DiscountVO)currAct.getVo()).isInnerDiscountPJ()){
			return null;
		}

		return storageAct;
	}



}