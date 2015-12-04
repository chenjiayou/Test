package nc.bs.fbm.upgrade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.fbm.upgrade.processor.ProcessorFactory;
import nc.bs.framework.common.NCLocator;
import nc.itf.cdm.util.CommonUtil;
//import nc.itf.pj.pub.INoteUpdateDataPublic;
import nc.itf.uap.sfapp.IBillcodeCorpsnService;
import nc.itf.uap.sfapp.IBillcodeRuleQryService;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmUpgradeConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billcodemanage.BillcodeRuleVO;

/**
 * 处理接口传递过来的数据
 *
 * @author xwq
 *
 */
public class UpgradeDataManager {

	protected UpgradeParamDAO dao = new UpgradeParamDAO();
	protected IBillcodeRuleService billcodeSrv = null;

	public UpgradeDataManager() {
		super();
		billcodeSrv = (IBillcodeRuleService) NCLocator.getInstance().lookup(
				IBillcodeRuleService.class.getName());
	}

	public void checkUpgrade(String syscode) throws BusinessException {
		if (dao.isUpgrade(syscode)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000359")/* @res"已经完成升级，如需重新升级，请先清空升级数据"*/);
		}
	}

	/**
	 * 升级财务票据
	 *
	 * @throws BusinessException
	 */
	public void upgradeARAP(Map<String,String> typeMap) throws BusinessException {
//		INoteUpdateDataPublic update = (INoteUpdateDataPublic) NCLocator
//				.getInstance().lookup(INoteUpdateDataPublic.class.getName());
//		List<BusiActVO>[] data = update.getUpdateDataForV502Note("3.5", "5.02",typeMap);
//		dealData(data, FbmBusConstant.SYSCODE_ARAP);
//		dao.addUpgrade(FbmBusConstant.SYSCODE_ARAP);
//		update.updatePJDirection();//更新票据方向
//		updateAccbankToAuth();
	}

	/**
	 * 升级资金票据
	 *
	 * @throws BusinessException
	 */
	public void upgradeFBM(Map<String,String> typeMap) throws BusinessException {
		//检查是否有资金票据表
		BaseDAO baseDao = new BaseDAO();
		String sql = "select 1 from fbm_pjzb";
		try{
			baseDao.executeQuery(sql, new ColumnProcessor());
		}catch(DAOException e){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000360")/* @res"找不到原资金票据数据，无法升级"*/);
		}


		FbmNoteUpdateDataBO bo = new FbmNoteUpdateDataBO();
		List<BusiActVO>[] data = bo.getUpdateDataForV502FdmNote("3.5", "5.02",typeMap);
		dealData(data, FbmBusConstant.SYSCODE_FBM);
		//处理非法票据
		transIllegal();
		dao.addUpgrade(FbmBusConstant.SYSCODE_FBM);
		
		updateAccbankToAuth();
		
		upgradeVoucher();
	}

	/**
	 * 
	 * <p>
	 * 升级开户银行，公司使用集团自有帐号自动授权
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-17
	 * @throws BusinessException 
	 */
	private void updateAccbankToAuth() throws BusinessException {
		OuterProxy.getAccbankUpdateService().updateAccbank(new FBMAccbankUpdateImpl());
	}
	
	/**
	 * 处理非法票据
	 * @throws BusinessException
	 */
	private void transIllegal() throws BusinessException{
		String sql  = "select pk_ffpj pk_illegal ,yhzj,pjlx,bzzj pk_curr ,hphm fbmbillno,hpje moneyy ,cpdw invoiceunitname ,cprq invoicedate ,dqrq enddate ,bz note from fbm_ffpj";
		BaseDAO baseDao = new BaseDAO();
		List<IllegalVO> list = (List<IllegalVO>)baseDao.executeQuery(sql, new BeanListProcessor(IllegalVO.class));
		if(list!= null && list.size() > 0){
			baseDao.insertVOArrayWithPK(list.toArray(new IllegalVO[0]));
		}
	}
	

	/**
	 * 清空升级数据
	 *
	 * @throws BusinessException
	 */
	public void clearUpgrade() throws BusinessException {
		String[] corps = queryPJCorp();
		recoverFinindex();//此方法调用要在清除表数据之前
		
		clearFBMTable();

		modifyCorpSn(new String[] {
				FbmBusConstant.BILLTYPE_GATHER,
				FbmBusConstant.BILLTYPE_INVOICE,
				FbmBusConstant.BILLTYPE_BILLPAY,
				FbmBusConstant.BILLTYPE_COLLECTION_UNIT,
				FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT,
				FbmBusConstant.BILLTYPE_ENDORE,
				FbmBusConstant.BILLTYPE_IMPAWN,
				FbmBusConstant.BILLTYPE_RETURN
				},corps);
		dao.clearUpgrade();
		
		
	}

	/**
	 * 清空票据数据
	 *
	 * @throws BusinessException
	 */
	private void clearFBMTable() throws BusinessException {
		// 删除表数据
		String sql_fi_impawn = "delete from fi_impawn where impawncode like '36GO%'";
		String sql_accept = "delete from fbm_accept";
		String sql_accountdetial = "delete from fbm_accountdetail";
		String sql_action = "delete from fbm_action";
		String sql_baseinfo = "delete from fbm_baseinfo";
		String sql_collection = "delete from fbm_collection";
		String sql_discount = "delete from fbm_discount";
		String sql_endore = "delete from fbm_endore";
		String sql_illegal = "delete from fbm_illegal";
		String sql_impawn = "delete from fbm_impawn";
		String sql_outer = "delete from fbm_outer";
		String sql_register = "delete from fbm_register";
		String sql_return = "delete from fbm_return";
		String sql_return_b = "delete from fbm_return_b";
		
		String sql_reckon = "delete from fbm_reckon";
		String sql_reckon_b = "delete from fbm_reckon_b";
		String sql_relief = "delete from fbm_relief";
		String sql_relief_b = "delete from fbm_relief_b";
		String sql_storage = "delete from fbm_storage";
		String sql_storage_b = "delete from fbm_storage_b";
		
		BaseDAO dao = new BaseDAO();
		try{
			dao.executeUpdate(sql_fi_impawn);
		}catch(Exception e){
			//吃掉异常
		}

		dao.executeUpdate(sql_accept);
		dao.executeUpdate(sql_accountdetial);
		dao.executeUpdate(sql_action);
		dao.executeUpdate(sql_baseinfo);
		dao.executeUpdate(sql_collection);
		dao.executeUpdate(sql_discount);
		dao.executeUpdate(sql_endore);
		dao.executeUpdate(sql_illegal);
		dao.executeUpdate(sql_impawn);
		dao.executeUpdate(sql_outer);
		dao.executeUpdate(sql_register);
		dao.executeUpdate(sql_return);
		dao.executeUpdate(sql_return_b);
		
		dao.executeUpdate(sql_reckon);
		dao.executeUpdate(sql_reckon_b);
		dao.executeUpdate(sql_relief);
		dao.executeUpdate(sql_relief_b);
		dao.executeUpdate(sql_storage);
		dao.executeUpdate(sql_storage_b);
	}

	/**
	 * 查询有票据业务的公司
	 * @return
	 * @throws BusinessException
	 */
	private String[] queryPJCorp() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		Object obj = dao.executeQuery("select distinct(pk_corp) from fbm_register ", new ColumnListProcessor());
		if(obj != null){
			return (String[])((List)obj).toArray(new String[0]);
		}
		return null;
	}
	/**
	 * 各公司流水号清0
	 *
	 * @throws BusinessException
	 */
	private void modifyCorpSn(String pk_billtypecode[],String[] corps)
			throws BusinessException {
		IBillcodeRuleQryService ruleQrySrv = (IBillcodeRuleQryService) NCLocator
				.getInstance().lookup(IBillcodeRuleQryService.class.getName());
		IBillcodeRuleService ruleSrv = (IBillcodeRuleService) NCLocator
				.getInstance().lookup(IBillcodeRuleService.class.getName());
		IBillcodeCorpsnService corpSnSrv = (IBillcodeCorpsnService) NCLocator
				.getInstance().lookup(IBillcodeCorpsnService.class.getName());

		for (String billtype : pk_billtypecode) {
			BillcodeRuleVO[] vos = ruleQrySrv.queryAllBillcodeRuleVOs(billtype);
			if (!CommonUtil.isNull(vos)) {
				for (int i = 0; i < vos.length; i++) {
					boolean isGroup = vos[i]
							.getControlpara()
							.equals(
									nc.vo.pub.billcodemanage.BillcodeConstant.BILLCODE_CONTROLPARA_GROUP);
					Integer snNum = vos[i].getSnnum();
					String newSN = buildSN(snNum.intValue());
					if (isGroup) {
						vos[i].setLastsn(newSN);
						ruleSrv.update(vos[i]);
					} else {
						for(int j = 0 ; j < corps.length ; j++){
							corpSnSrv.modifyCorpSn(billtype, corps[j],
									newSN);
						}
					}
				}
			}
		}
	}

	/**
	 * 构造起始流水号
	 *
	 * @param snNum
	 * @return
	 */
	private String buildSN(int snNum) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < snNum; i++) {
			sb.append("0");
		}
		return sb.toString();
	}

	/**
	 * 处理中间VO后续操作
	 *
	 * @param data
	 * @throws BusinessException
	 */
	private void dealData(List<BusiActVO>[] data, String syscode)
			throws BusinessException {
		if (data == null || data.length == 0)
			return;

		// 开始之前校验所有VO的合法性
		doCheck(data);

		List<BusiActVO> list = new ArrayList<BusiActVO>();
		String lastPJH = "";
		String lastPk_baseinfo = "";
		boolean existPJH = false;
		for (int i = 0; i < data.length; i++) {
			String pjh = ((BaseinfoVO) ((BusiActVO) data[i].get(0)).getVo())
					.getFbmbillno();
			if (pjh.equals(lastPJH)) {
				existPJH = true;
			} else {
				existPJH = false;
			}
			dealBusiVos(data[i], existPJH, lastPk_baseinfo);
			list.addAll(data[i]);
			lastPk_baseinfo = ((BaseinfoVO) ((BusiActVO) data[i].get(0))
					.getVo()).getPrimaryKey();
			lastPJH = pjh;
		}

		if (syscode.equals(FbmBusConstant.SYSCODE_ARAP)) {
			// 更新外部关联
			dealRelation(list.toArray(new BusiActVO[0]));
			// 更新子表上游单据类型
			dealArapDjfb();
		}

		//补充质押表信息，关联物权担保
		relationFIImpawn();

	}

	private void doCheck(List<BusiActVO>[] data) throws BusinessException {

		for (int i = 0; i < data.length; i++) {
			doCheck(data[i]);
		}
	}

	private void doCheck(List<BusiActVO> data) throws BusinessException {
		for (Iterator<BusiActVO> it = data.iterator(); it.hasNext();) {
			BusiActVO vo = it.next();
			doCheck(vo);
		}
	}

	private void doCheck(BusiActVO busiActVO) throws BusinessException {

	}

	/**
	 * 处理一笔业务 基本信息->收票登记->贴现
	 *
	 * @throws BusinessException
	 */
	private void dealBusiVos(List<BusiActVO> vos, boolean existPJH,
			String lastPk_baseinfo) throws BusinessException {
		BaseDAO dao = new BaseDAO();
		if (null == vos || vos.size() == 0)
			return;
		BaseInfoGenerator gen;
		try {
			gen = new BaseInfoGenerator();
		} catch (NamingException e) {
			throw new BusinessException(e.getMessage());
		}
		String pk_baseinfo = gen.getPK();
		BusiActVO[] busiActVos = vos.toArray(new BusiActVO[0]);

		for (BusiActVO busiActVO : busiActVos) {
			if (existPJH) {// 如果等于前一票据号，那么PK_baseinfo一致
				pk_baseinfo = lastPk_baseinfo;

				if (busiActVO.getBusitype() == BusiActVO.BASEINFO) {// 如果是基本信息，则不用处理
					busiActVO.getVo().setAttributeValue("pk_baseinfo", pk_baseinfo);
					continue;
				}
			}
			// 设置票据基本信息PK字段
			busiActVO.getVo().setAttributeValue("pk_baseinfo", pk_baseinfo);
			// 设置单据类型
			String billtype = BusiActVO
					.retriveBillType(busiActVO.getBusitype());
			if (billtype != null) {
				busiActVO.getVo()
						.setAttributeValue("pk_billtypecode", billtype);
				String pk_corp = (String) busiActVO.getVo()
							.getAttributeValue("pk_corp");
				// 设置单据号
				String billcode = billcodeSrv.getBillCode_RequiresNew(
							billtype, pk_corp, null, null);
				busiActVO.getVo().setAttributeValue("vbillno", billcode);
				if(billtype.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)){//内贴不处理
					DiscountVO tmpDiscount = (DiscountVO)busiActVO.getVo();
					if(tmpDiscount.isInnerDiscountPJ()){
						continue;
					}
				}
				
				if(existPJH && billtype.equals(FbmBusConstant.BILLTYPE_GATHER)){
					busiActVO.getVo().setAttributeValue("gathertype", FbmBusConstant.GATHER_TYPE_ENDORE);
					Object pk_source = busiActVO.getVo().getAttributeValue("pk_source");
					if(pk_source!=null){
						//原数据组织的时候用的是前一次收票的PK,现系统要翻译成前一背书的PK
						List list = (List)dao.retrieveByClause(EndoreVO.class, "isnull(dr,0)=0 and pk_source='"+pk_source+"'");
						if(list!=null && list.size() > 0){
							busiActVO.getVo().setAttributeValue("pk_source",((EndoreVO)list.get(0)).getPrimaryKey());
							busiActVO.getVo().setAttributeValue("paybillunit", ((EndoreVO)list.get(0)).getEndorser());
						}
						
						if(pk_source.equals(busiActVO.getVo().getPrimaryKey())){//如果是内部贴现形成的收票，则前期已经将pk_source和pk_register置成一样的值,这种收票不处理了
							continue;
						}
					}
				}
				
				
			}

			dealBusiVO(busiActVO);
		}
	}

	/**
	 * 处理一笔业务的一个动作 基本信息|收票登记|贴现
	 *
	 * @param busiActvo
	 * @throws BusinessException
	 */
	private void dealBusiVO(BusiActVO busiActvo) throws BusinessException {
		if (null == busiActvo)
			return;

		int bustype = busiActvo.getBusitype();

		IDataProcessor processor = ProcessorFactory.createProcessor(bustype);
		if (null == processor) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000361")/* @res"无法取得["*/
					+ FbmUpgradeConstant.getZnName(bustype) + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000362")/* @res"]的处理器"*/);
		}
		processor.buildData(busiActvo.getVo());
	}

	/**
	 * 维护单据关联关系
	 *
	 * @throws BusinessException
	 */
	private void dealRelation(BusiActVO[] busiActVos) throws BusinessException {
		if (!CommonUtil.isNull(busiActVos)) {
			BaseDAO baseDao = new BaseDAO();
			List<String> pkList = new ArrayList();
			for (int i = 0; i < busiActVos.length; i++) {
				pkList.add(busiActVos[i].getVo().getPrimaryKey());
			}
			String inSql = CommonUtil.buildSqlForIn("f.ddhh", pkList
					.toArray(new String[0]));

			// 插入外部关联
			String sql_outer = "select z.vouchid as pk_outerbill_h,f.fb_oid as pk_outerbill_b,z.djdl as outerdjdl,z.djlxbm as outerbilltype,'over' as outerstatus,z.dwbm as pk_corp,f.ddhh as pk_busibill from arap_djfb f join arap_djzb z on f.vouchid = z.vouchid  where isnull(z.dr,0)=0 and "
					+ inSql;
			List<OuterVO> outerList = (List<OuterVO>) baseDao.executeQuery(
					sql_outer, new BeanListProcessor(OuterVO.class));
			if (outerList != null && outerList.size() > 0) {
				baseDao.insertVOArray(outerList.toArray(new OuterVO[0]));
			}

			// 更新外部关联
			String updateSQL = "update fbm_outer set pk_billtypecode = (select distinct (billtype) from fbm_action where fbm_outer.pk_busibill = fbm_action.pk_bill ),pk_register =(select distinct(pk_source)  from fbm_action where fbm_outer.pk_busibill = fbm_action.pk_bill )";
			baseDao.executeUpdate(updateSQL);

		}
	}

	/**
	 * 更新收付款单据 子表的上游单据类型字段值
	 *
	 * @throws BusinessException
	 */
	private void dealArapDjfb() throws BusinessException {
		String sql = "update arap_djfb set jsfsbm = (select distinct(pk_billtypecode) from fbm_outer where fb_oid = fbm_outer.pk_outerbill_b) where fb_oid in(select pk_outerbill_b from fbm_outer)";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql);
	}

	/**
	 * 返回安装了财务票据，但未安装新资金票据的公司名称
	 * @return
	 * @throws BusinessException
	 */
	public String[] retriveCorpNotInstall() throws BusinessException{
		BaseDAO dao  = new BaseDAO();
		String sql = "select distinct unitname from arap_note_pj join bd_corp on arap_note_pj.pk_corp = bd_corp.pk_corp  where isnull(arap_note_pj.dr,0 )= 0 and not exists (select 1 from sm_createcorp where funccode = '3620' and isnull(sm_createcorp.dr,0)=0 and arap_note_pj.pk_corp = sm_createcorp.pk_corp)";
		List list = (List)dao.executeQuery(sql, new ColumnListProcessor());
		if(list == null || list.size() == 0){
			return null;
		}
		return (String[])list.toArray(new String[0]);
	}

	/**
	 * 关联物权担保和票据质押
	 * @throws BusinessException
	 */
	private void relationFIImpawn() throws BusinessException{
		BaseDAO dao = new BaseDAO();

		try{
			String fi_sql = "update fi_impawn set pk_othersys = (select max(pk_impawn) from fbm_impawn join fbm_baseinfo on fbm_impawn.pk_baseinfo = fbm_baseinfo.pk_baseinfo where fbm_baseinfo.fbmbillno = impawnname )where impawnname in(select distinct fbmbillno from fbm_impawn join fbm_baseinfo on fbm_impawn.pk_baseinfo = fbm_baseinfo.pk_baseinfo)";
			dao.executeUpdate(fi_sql);

			//更新所有权单位
			String updateOwner = "update fi_impawn set impawnowner = (select distinct pk_corp from fbm_impawn where pk_othersys = fbm_impawn.pk_impawn ) where pk_othersys in(select pk_impawn from fbm_impawn)";
			dao.executeUpdate(updateOwner);
			
			StringBuffer sb = new StringBuffer();
			sb.append("update fbm_impawn set ");
			sb.append(" evaluatevalue = (select impawnallvalue from fi_impawn where pk_othersys = fbm_impawn.pk_impawn) ,");
			sb.append(" impawnrate = (select impawnrate from fi_impawn where pk_othersys = fbm_impawn.pk_impawn) ,");
			sb.append(" impawnable = (select impawnvalue from fi_impawn where pk_othersys = fbm_impawn.pk_impawn) ,");
			sb.append(" evaluateunit = (select evaluateunit from fi_impawn where pk_othersys = fbm_impawn.pk_impawn) ,");
			sb.append(" impawnbank =  (select bank from fi_impawn where pk_othersys = fbm_impawn.pk_impawn) ");
			sb.append(" where pk_impawn in(select pk_othersys from fi_impawn)");
			dao.executeUpdate(sb.toString());

		}catch(Exception e){
			nc.bs.logging.Logger.error("可能是物权担保表不存在，忽略该异常，不用向外抛出", e);
		}
	}

	
	/**
	 * 升级凭证，建立关联
	 * @throws BusinessException
	 */
	private void upgradeVoucher() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		//更新procmsg为新的单据pk,原票据 收票和业务传平台的pk都是收票PK
		String rebuildprocmsg_discount = "update dap_finindex set procmsg= isnull((select pk_discount from fbm_discount join fbm_register on fbm_discount.pk_source=fbm_register.pk_register where fbm_register.pk_source=dap_finindex.procmsg),procmsg) where pk_sys='FBM' and pk_proc='GD'";
		String rebuildprocmsg_outendore = "update dap_finindex set procmsg = isnull((select pk_endore from fbm_endore join fbm_register on fbm_endore.pk_source=fbm_register.pk_register where fbm_register.pk_source=dap_finindex.procmsg ),procmsg) where pk_sys='FBM' and pk_proc ='GG'";
		//内背没有联查凭证，不需要升级
		//String rebuildprocmsg_innerendore = "update dap_finindex set procmsg= isnull((select pk_endore from fbm_endore where pk_source=dap_finindex.procmsg),procmsg) where pk_sys='FBM' and pk_proc='GF'";
		String rebuildprocmsg_collection ="update dap_finindex set procmsg= isnull((select pk_collection from fbm_collection join fbm_register on fbm_collection.pk_source=fbm_register.pk_register where fbm_register.pk_source=dap_finindex.procmsg),procmsg) where pk_sys='FBM' and pk_proc='GE'";
		
		String rebuildprocmsg_storage = "update dap_finindex set procmsg = isnull((select distinct(pk_storage) from fbm_storage_b where pk_source =  dap_finindex.procmsg ),procmsg) where pk_sys='FBM' and pk_proc='GA'";
		
		int rebuild1 = dao.executeUpdate(rebuildprocmsg_discount);
		int rebuild2 = dao.executeUpdate(rebuildprocmsg_outendore);
		//int rebuild3 = dao.executeUpdate(rebuildprocmsg_innerendore);
		int rebuild4 = dao.executeUpdate(rebuildprocmsg_collection);
		int rebuild5 = dao.executeUpdate(rebuildprocmsg_storage);
		//修改单据状态为已制证
		String discount_status = "update fbm_discount set vbillstatus=25,vvoucherid=(select operator from dap_finindex where procmsg=pk_discount),dvoucherdate=(select busidate from dap_finindex where procmsg=pk_discount) where pk_discount in(select procmsg from dap_finindex where pk_sys='FBM' and pk_proc='GD')";
		String endore_status = "update fbm_endore set vbillstatus=25,vvoucherid=(select operator from dap_finindex where procmsg=pk_endore),dvoucherdate=(select busidate from dap_finindex where procmsg=pk_endore) where pk_endore in(select procmsg from dap_finindex where pk_sys='FBM' and pk_proc = 'GG')";
		String collection_status = "update fbm_collection set vbillstatus=25,vvoucherid=(select operator from dap_finindex where procmsg=pk_collection),dvoucherdate=(select busidate from dap_finindex where procmsg=pk_collection) where pk_collection in(select procmsg from dap_finindex where pk_sys='FBM' and pk_proc='GE')";
		String storage_status = "update fbm_storage set vbillstatus=16,vvouchermanid=(select operator from dap_finindex where procmsg=pk_storage),vvoucherdate=(select busidate from dap_finindex where procmsg=pk_storage) where pk_storage in(select procmsg from dap_finindex where pk_sys='FBM' and pk_proc='GA')";
		
		//对procmsg加上pk_corp
		String update_procmsg = "update dap_finindex set procmsg=procmsg || '_'|| pk_corp where pk_sys='FBM'";
		
		
		int discount_count = dao.executeUpdate(discount_status);
		int endore_count = dao.executeUpdate(endore_status);
		int collection_count = dao.executeUpdate(collection_status);
		int storage_count = dao.executeUpdate(storage_status);
		
		int procMsg_count = dao.executeUpdate(update_procmsg);
		
		//内部存放单上的帐户应该是原收票登记上的收款单位帐户
		String storage_acc = "update fbm_storage set keepaccount=(select skdwzh from fbm_pjzb join fbm_storage_b on fbm_pjzb.pk_pjzb = fbm_storage_b.pk_source where fbm_storage_b.pk_storage = fbm_storage.pk_storage ) ";
		dao.executeUpdate(storage_acc);
	}
	
	/**
	 * 重置凭证关联关系
	 * @throws BusinessException
	 */
	private void recoverFinindex() throws BusinessException{
		String sql = "update dap_finindex set procmsg= substring(procmsg,1,20) where pk_sys='FBM'";
		BaseDAO dao  = new BaseDAO();
		int count = dao.executeUpdate(sql);
		
		String rebuildprocmsg_discount = "update dap_finindex set procmsg= isnull((select fbm_register.pk_source from fbm_discount join fbm_register on fbm_discount.pk_source=fbm_register.pk_register where pk_discount=dap_finindex.procmsg),procmsg) where pk_sys='FBM' and pk_proc='GD'";
		//String rebuildprocmsg_endoreinner = "update dap_finindex set procmsg = isnull((select pk_source from fbm_endore where pk_endore=dap_finindex.procmsg ),procmsg) where pk_sys='FBM' and pk_proc ='GF'";
		String rebuildprocmsg_endoreouter = "update dap_finindex set procmsg= isnull((select fbm_register.pk_source from fbm_endore join fbm_register on fbm_endore.pk_source=fbm_register.pk_register where pk_endore=dap_finindex.procmsg),procmsg) where pk_sys='FBM' and pk_proc='GG'";
		String rebuildprocmsg_collection ="update dap_finindex set procmsg= isnull((select fbm_register.pk_source from fbm_collection join fbm_register on fbm_collection.pk_source=fbm_register.pk_register where pk_collection=dap_finindex.procmsg),procmsg) where pk_sys='FBM' and pk_proc='GE'";
		String rebuildprocmsg_storage = "update dap_finindex set procmsg = isnull((select fbm_register.pk_register from fbm_storage_b join fbm_register on fbm_register.pk_register=fbm_storage_b.pk_source where pk_storage = dap_finindex.procmsg),procmsg) where pk_sys='FBM' and pk_proc='GA'";
		
		int rebuild1 = dao.executeUpdate(rebuildprocmsg_discount);
		//int rebuild2 = dao.executeUpdate(rebuildprocmsg_endoreinner);
		int rebuild3 = dao.executeUpdate(rebuildprocmsg_endoreouter);
 		int rebuild4 = dao.executeUpdate(rebuildprocmsg_collection);
 		int rebuild5 = dao.executeUpdate(rebuildprocmsg_storage);
	}
}