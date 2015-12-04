package nc.bs.fbm.reckon.alarm;

import java.util.HashMap;
import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.pub.pa.IBusinessPlugin2;
import nc.bs.pub.pa.html.IAlertMessage;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.itf.cdm.util.MathUtil;
import nc.itf.uif.pub.IUifService;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.reckonalarm.ReckalarmVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.pa.CurrEnvVO;
import nc.vo.pub.pa.Key;
import nc.vo.trade.pub.HYBillVO;

/**
 * <p>
 * 调剂清算预警后台类
 * <p>创建人：bsrl
 * <b>日期：2007-10-23
 *
 */
public class FbmBillReckonAlarmBO  implements IBusinessPlugin2{

	public FbmBillReckonAlarmBO() {
		super();
	}

	public IAlertMessage[] implementReturnFormatMsg(Key[] keys,
			Object currEnvVO, UFDate clientLoginDate) throws BusinessException {
		Vector<IAlertMessage> vec = new Vector<IAlertMessage>();
		String cuserid = ((CurrEnvVO) currEnvVO).getPk_user();
		for(int i = 0; i < keys.length; i ++) {
			if(keys[i].getName().equalsIgnoreCase("voperatorid") && keys[i].getValue() != null && keys[i].getValue().toString().length() != 0) {
				cuserid = keys[i].getValue().toString();
			}
		}
        if(cuserid == null) {
        	return null;
        }
		HYPubBO hyPubBO = new HYPubBO();

	    ReckalarmVO[] reckalarmVOs = (ReckalarmVO[])hyPubBO.queryByCondition(ReckalarmVO.class, " pk_corp = '" + ((CurrEnvVO) currEnvVO).getPk_corp() + "' ");

	    for(int i = 0; i < reckalarmVOs.length; i++) {
	    	keys = new Key[6];
	    	keys[0] = new Key(ReckalarmVO.PK_CURR, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"币种"*/ ,  reckalarmVOs[i].getPk_curr());
	    	keys[1] = new Key(ReckalarmVO.INACC, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000339")/* @res"内部账户"*/ ,  reckalarmVOs[i].getInacc());
	    	keys[2] = new Key(ReckalarmVO.OUTACC, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000339")/* @res"内部账户"*/ ,  reckalarmVOs[i].getOutacc());
	    	keys[3] = new Key(ReckalarmVO.RECKONDATE, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000340")/* @res"清算开始日期"*/ ,  reckalarmVOs[i].getReckondate().toString().trim());
	    	keys[4] = new Key(ReckalarmVO.RECKONUNIT, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000069")/* @res"清算单位"*/ ,  reckalarmVOs[i].getReckonunit());
	    	keys[5] = new Key("voperatorid", nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0000661")/*@res "制单人"*/, cuserid);
	    	IAlertMessage msgVos = implementReturnFormatMsg(keys,reckalarmVOs[i].getPk_corp(),clientLoginDate);
	    	if(msgVos!=null) {
				vec.addElement(msgVos);
			}
	    }

	    IAlertMessage[] ret = null;
		vec.trimToSize();
		if (vec.size() > 0){
			ret = new IAlertMessage[vec.size()];
			vec.copyInto(ret);
		}
		return ret;
	}

	public String[] implementReturnMessage(Key[] keys, Object currEnvVO,
			UFDate clientLoginDate) throws BusinessException {
		return null;
	}

	public Object implementReturnObject(Key[] keys, Object currEnvVO,
			UFDate clientLoginDate) throws BusinessException {
		return null;
	}

	public boolean implementWriteFile(Key[] keys, String fileName,
			Object currEnvVO, UFDate clientLoginDate) throws BusinessException {
		return false;
	}

	public int getImplmentsType() {
		return IMPLEMENT_RETURNFORMATMSG;
	}

	public Key[] getKeys() {
		return null;
	}

	public String getTypeDescription() {
		return null;
	}

	public String getTypeName() {
		return null;
	}

	public IAlertMessage implementReturnFormatMsg(Key[] keys, String corpPK,
			UFDate clientLoginDate) throws BusinessException {
		AccountDetailVO[]  accountdetailvos = null;
		//所要显示的列名
		String[] colNames = new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"票据编号"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000174")/* @res"付款单位"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000048")/* @res"持票单位"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"币种"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"金额"*/ , nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000068")/* @res"到期日期"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000341")/* @res"清算方向"*/};
		//最后显示的所有列的列值
		String[][] colValues = null;

		//从传入的参数中解析必要的值
		String reckonunit = null;//清算单位
		String curr = null;//清算币种
		UFDate reckondate = null;//清算开始日期
		String inacc = null;//内部账户
		String outacc = null;
		String curuser = null; //当前用户

		HashMap<String, Object> map= new HashMap<String, Object>();
		for(int i=0;i<keys.length;i++){
		   map.put(keys[i].getName(),keys[i].getValue());
		}

//		获得用户定义的预警条件值
		curr = (String)getValueByKey(ReckalarmVO.PK_CURR,map);
		inacc = (String)getValueByKey(ReckalarmVO.INACC,map);
		outacc = (String)getValueByKey(ReckalarmVO.OUTACC,map);
		reckondate = new UFDate((String)getValueByKey(ReckalarmVO.RECKONDATE,map));
		reckonunit = (String)getValueByKey(ReckalarmVO.RECKONUNIT,map);
		curuser = (String) getValueByKey("voperatorid",map);
		colValues=new String[][]{{""}};

		//组织数据：
		try{
			 //规则起效日期与清算开始日期以大的为准
			 if(reckondate.after(clientLoginDate)) {
				return null;
			 }

    		 String strWhere = " exists (select 1 from fbm_accountdetail a left join fbm_register on " +
			 		"a.pk_register = fbm_register.pk_register join fbm_baseinfo " +
			 		"on fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo where a.pk_detail = fbm_accountdetail.pk_detail and " +
				"a.pk_org = '" + reckonunit + "' and pk_curr ='" + curr + "' and enddate <= '" +
				reckondate +"' and isnull(fbm_baseinfo.dr,0)=0 and isnull(a.dr,0)=0" +
						" and (fbm_accountdetail.reason = '" + FbmBusConstant.ACCOUNT_REASON_RELIEF_USE +"' or " +
							"fbm_accountdetail.reason = '" + FbmBusConstant.ACCOUNT_REASON_CENTER_USE +"')" +
								"and a.pk_type = '"+ FbmBusConstant.ACCOUNT_TYPE_LIQUID + "' " +
				" and a.isliquid = 'N' and not exists (select 1 from fbm_reckon_b join fbm_reckon on" +
				" fbm_reckon_b.pk_reckon = fbm_reckon.pk_reckon where fbm_reckon.pk_billtypecode = '" +
				FbmBusConstant.BILLTYPE_LIQUIDATE + "' and " +
				" fbm_reckon_b.pk_detail = a.pk_detail and isnull(fbm_reckon_b.dr,0)=0) )";

			 HYPubBO hyPubBO = new HYPubBO();
			 accountdetailvos = (AccountDetailVO[])hyPubBO.queryByCondition(AccountDetailVO.class, strWhere);
			 HYBillVO billvos = new HYBillVO();
			 if(accountdetailvos == null || accountdetailvos.length == 0) {
				 FBMAlertMessageFormat msg = new FBMAlertMessageFormat();
				 msg.setTitle(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000342")/* @res"资金票据调剂清算预警"*/);
					msg.setBodyFields(new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0002916")/*@res "状态"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0003660")/*@res "说明"*/});
					msg.setBodyValue(new String[][]{
							{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000027")/*@res "成功"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000028")/*@res "没有查询到需要清算的内部账户账数据，生成0条调剂清算单！"*/}
					});
				 return msg;
			 }
			 ReckonBVO[] reckonBVOs = new ReckonBVO[accountdetailvos.length];
			 for(int i = 0; i < accountdetailvos.length; i ++) {
				 reckonBVOs[i] = new ReckonBVO();
				 reckonBVOs[i].setPk_detail(accountdetailvos[i].getPrimaryKey());
				 reckonBVOs[i].setPk_source(accountdetailvos[i].getPk_register());
				 reckonBVOs[i].setPk_baseinfo(accountdetailvos[i].getPk_baseinfo());
				 CurrencyPublicUtil currutil = new CurrencyPublicUtil(corpPK, curr);
				 accountdetailvos[i].setMoneyy(accountdetailvos[i].getMoneyy().setScale(currutil.getYDecimalDigit(), UFDouble.ROUND_HALF_UP));
				 reckonBVOs[i].setMoneyy(accountdetailvos[i].getMoneyy());
				 reckonBVOs[i].setEnddate(accountdetailvos[i].getLiquidationdate());
				 if(accountdetailvos[i].getMoneyy().compareTo(MathUtil.ZERO_UFDOUBLE) > 0) {
					 reckonBVOs[i].setDirection(FbmBusConstant.RELIEF_DIRECTION_IN);
				 }else {
					 reckonBVOs[i].setDirection(FbmBusConstant.RELIEF_DIRECTION_OUT);
				 }
			 }

			 ReckonVO reckonVO = new ReckonVO();
			 UFDouble newReckonmoneysum = new UFDouble(0);
			 for(int i = 0 ; i < reckonBVOs.length; i ++) {
				 newReckonmoneysum = newReckonmoneysum.add(reckonBVOs[i].getMoneyy());
			 }
			 reckonVO.setDoperatedate(clientLoginDate);
			 reckonVO.setDreckondate(clientLoginDate);
			 reckonVO.setPk_billtypecode(FbmBusConstant.BILLTYPE_LIQUIDATE);
			 reckonVO.setPk_corp(corpPK);
			 reckonVO.setPk_curr(curr);
			 reckonVO.setReckonunit(reckonunit);
			 reckonVO.setReckoncenter(OuterProxy.getSettleCenter().getSettleCenterByCorpPk(corpPK).getPrimaryKey());
			 reckonVO.setReckonmoneysum(newReckonmoneysum);
			 reckonVO.setInacc(inacc);
			 reckonVO.setOutacc(outacc);
			 reckonVO.setVoperatorid(curuser);
			 reckonVO.setDoperatedate(clientLoginDate);
			 reckonVO.setVbillstatus(nc.vo.trade.pub.IBillStatus.FREE);
			 String billcode =((IUifService) NCLocator.getInstance().lookup(IUifService.class.getName())).getBillNo(FbmBusConstant.BILLTYPE_LIQUIDATE, corpPK, null, null);
			 reckonVO.setVbillno(billcode);

			 billvos.setParentVO(reckonVO);
			 billvos.setChildrenVO(reckonBVOs);
			 hyPubBO.saveBill(billvos);

			 String[] regvospks = new String[reckonBVOs.length];
			 for(int i = 0; i < reckonBVOs.length; i ++) {
				 regvospks[i] = reckonBVOs[i].getPk_source();
			 }
			 if(reckonVO!=null){
				 
//				币种，清算方向，持票单位，付款单位
			 nc.vo.pub.SuperVOUtil.execFormulaWithVOs(new ReckonVO[] {reckonVO},
						new String[]{
					 		"reckoncenter->getColValue(bd_settlecenter,settlecentername,pk_settlecenter,reckoncenter)",
							"reckonunit->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,reckonunit)",
							"pk_curr->getColValue(bd_currtype,currtypename,pk_currtype,pk_curr)"
							});
			 }
			 
			 if(reckonBVOs!=null && reckonBVOs.length > 0){
			 nc.vo.pub.SuperVOUtil.execFormulaWithVOs(reckonBVOs,
						new String[]{
					        "direction->iif(direction=\"reliefin\",\"调剂存放\",\"调剂领用\")",
							"holdunit->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,getColValue(fbm_register, holdunit, pk_register, pk_source))",
							"payunit->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,getColValue(fbm_baseinfo, payunit, pk_baseinfo, pk_baseinfo))",
							"pk_baseinfo->getColValue(fbm_baseinfo,fbmbillno,pk_baseinfo,pk_baseinfo)"
							});
			 }
			Vector<String[]> vct = new Vector<String[]>();
			for(int i = 0;i < reckonBVOs.length; i ++){
				String[] row = new String[7];
				row[0] = reckonBVOs[i].getPk_baseinfo();
				row[1] = reckonBVOs[i].getPayunit();
				row[2] = reckonBVOs[i].getHoldunit();
				row[3] = reckonVO.getPk_curr();
				row[4] = reckonBVOs[i].getMoneyy().toString();
				row[5] = reckonBVOs[i].getEnddate().toString();
				row[6] = reckonBVOs[i].getDirection();
				vct.addElement(row);
			}
			
			if(vct.size() == 0)
				return null;
			colValues = new String[vct.size()][6];
			vct.copyInto(colValues);
		//	产生格式化信息
			FBMAlertMessageFormat msg = new FBMAlertMessageFormat();
			msg.setTitle(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000342")/* @res"资金票据调剂清算预警"*/);
			msg.setTop(new String[]{reckonVO.getReckonunit() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000343")/* @res"在"*/+reckonVO.getDreckondate() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000344")/* @res"之前到期的票据信息："*/});
			msg.setBodyFields(colNames);
			msg.setBodyValue(colValues);
			return msg;
		}catch(Exception e){
			if (e instanceof BusinessException) {
				throw (BusinessException)e;
			} else {
				throw new BusinessException(e);
			}
		}
	}

	public String implementReturnMessage(Key[] keys, String corpPK,
			UFDate clientLoginDate) throws BusinessException {
		return null;
	}

	public Object implementReturnObject(Key[] keys, String corpPK,
			UFDate clientLoginDate) throws BusinessException {
		return null;
	}

	public boolean implementWriteFile(Key[] keys, String fileName,
			String corpPK, UFDate clientLoginDate) throws BusinessException {
		return false;
	}

	/**
	 * getKeys 方法注解。
	 */
	private Object getValueByKey(String key,java.util.HashMap<String, Object>  map) throws BusinessException {
		Object objdata= map.get(key);
		Logger.debug(" map.get(key)="+objdata.toString());
		if(objdata!=null){
			Object temp=objdata.toString().trim();
			if(temp.equals("")||temp.equals("null"))
		         objdata=null;
		}

		return objdata;

	}
}