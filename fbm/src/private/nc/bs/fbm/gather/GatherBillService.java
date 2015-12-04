/**
 *
 */
package nc.bs.fbm.gather;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.fbm.pub.BaseInfoBO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.trade.business.HYPubBO;
import nc.bs.trade.comcheckunique.BillIsUnique;
import nc.impl.fbm.illegal.IllegalBillServiceImpl;
import nc.itf.cdm.util.CommonUtil;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.uif.pub.exception.UifException;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.FbmBankDocVO;
import nc.vo.fbm.pub.FbmBankaccbasVO;
import nc.vo.fbm.pub.FbmCustBasDocMapping;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 收票登记后台处理票据基本信息的方法
 * <p>
 * 创建人：lpf
 * <b>
 * 日期：2007-9-4
 *
 */
public class GatherBillService {
	private static final String FBM_GENECORP = "FBMC";
	private static final String FBM_GENEACC = "FBM";
	private static final String FBM_GENECUST = "FBM_GENECUST";

	private BaseDAO m_baseDAO = null;

	public GatherBillService(){
		super();
	}

	 public void saveBaseInfo(RegisterVO RegisterVO) throws Exception {
		checkValidateBill(RegisterVO);

		RegisterVO = dealwithInputDoc(RegisterVO);

		BaseinfoVO baseinfoVo = RegisterVO.getBaseinfoVO();

		BaseInfoBO baseService = new BaseInfoBO();

		
		String pk_baseinfo = baseinfoVo.getPk_baseinfo();
		// 只有新收的票才需要校验
		if ((RegisterVO.getIsnewbill().booleanValue() && pk_baseinfo==null)||pk_baseinfo==null) {
			baseService.checkisFBMBillnoUnique(baseinfoVo);
		}

		// checkReceiveUnitisCust(baseinfoVo);

		// checkRegisterNoUnique(RegisterVO);

		
		if(!CommonUtil.isNull(pk_baseinfo)) {
			BaseinfoVO newVo = new CommonDAO().queryBaseinfoByPK(pk_baseinfo);
			if(newVo==null){
				baseinfoVo.setPk_baseinfo(null);
			}
		}

		pk_baseinfo = baseService.saveBaseinfo(baseinfoVo);
		RegisterVO.setPk_baseinfo(pk_baseinfo);
	}

	/**
	 * 处理手工录入的参照
	 *
	 * @param registerVO
	 * @return
	 */
	private RegisterVO dealwithInputDoc(RegisterVO registerVO) throws Exception {
		BaseinfoVO baseinfoVo = registerVO.getBaseinfoVO();
		String pk_curr = registerVO.getPk_curr();
		if (CommonUtil.isNull(registerVO.getReceiveunit())&& !CommonUtil.isNull(registerVO.getReceiveunitname())) {
			String pk_cubasdoc = insertInputCustDoc(registerVO.getReceiveunitname());
			baseinfoVo.setReceiveunit(pk_cubasdoc);
		}
		if (CommonUtil.isNull(registerVO.getReceivebankacc())&& !CommonUtil.isNull(registerVO.getReceivebankaccode())) {
			String pk_accbank = insertInputAccbank(pk_curr,registerVO.getReceivebankaccode(),registerVO.getReceiveaccname());
			baseinfoVo.setReceivebankacc(pk_accbank);
		}else{
			if (registerVO.getIsrecacccust()!=null&&registerVO.getIsrecacccust().booleanValue()) {
				updateInputAccbank(registerVO.getReceivebankacc(),registerVO.getReceiveaccname());
			}
		}
		if(CommonUtil.isNull(registerVO.getReceivebank())&& !CommonUtil.isNull(registerVO.getReceivebankname())){
			String pk_bankdoc = insertInputBankDoc(registerVO.getReceivebankname(), registerVO.getReceivebankname());
			baseinfoVo.setReceivebank(pk_bankdoc);
			if(!CommonUtil.isNull(baseinfoVo.getReceivebankacc())){
				FbmBankaccbasVO basVo = new FbmBankaccbasVO();
				basVo.setPk_bankaccbas(baseinfoVo.getReceivebankacc());
				basVo.setPk_bankdoc(pk_bankdoc);
				getBaseDAO().updateVO(basVo, new String[]{"pk_bankdoc"});
			}
		}
		
		if (CommonUtil.isNull(registerVO.getPaybankacc())&& !CommonUtil.isNull(registerVO.getPaybankaccode())) {
			String pk_accbank = insertInputAccbank(pk_curr,registerVO.getPaybankaccode(),registerVO.getPayaccname());
			baseinfoVo.setPaybankacc(pk_accbank);
		}else{
			if (registerVO.getIsacccust()!=null&&registerVO.getIsacccust().booleanValue()) {
				updateInputAccbank(registerVO.getPaybankacc(),registerVO.getPayaccname());
			}
		}

		if (CommonUtil.isNull(registerVO.getInvoiceunit())&& !CommonUtil.isNull(registerVO.getInvoiceunitname())) {
			String pk_cubasdoc = insertInputCustDoc(registerVO.getInvoiceunitname());
			baseinfoVo.setInvoiceunit(pk_cubasdoc);
		}
		if (CommonUtil.isNull(registerVO.getPayunit())&& !CommonUtil.isNull(registerVO.getPayunitname())) {
			String pk_cubasdoc = insertInputCustDoc(registerVO.getPayunitname());
			baseinfoVo.setPayunit(pk_cubasdoc);
		}
		
		if(CommonUtil.isNull(registerVO.getPaybank())&& !CommonUtil.isNull(registerVO.getPaybankname())){
			String pk_bankdoc = insertInputBankDoc(registerVO.getPaybankname(), registerVO.getPaybankname());
			baseinfoVo.setPaybank(pk_bankdoc);
			if(!CommonUtil.isNull(baseinfoVo.getPaybankacc())){
				FbmBankaccbasVO basVo = new FbmBankaccbasVO();
				basVo.setPk_bankaccbas(baseinfoVo.getPaybankacc());
				basVo.setPk_bankdoc(pk_bankdoc);
				getBaseDAO().updateVO(basVo, new String[]{"pk_bankdoc"});
			}
		}
		return registerVO;
	}

	/**
	 * 保存手工录入的客商
	 * 参数：客商名称
	 * 返回值：客商PK
	 * @param receiveunitname
	 * @return
	 * @throws BusinessException
	 */
	public String insertInputCustDoc(String custname) throws BusinessException {
		CustBasVO basVo = new CustBasVO();
		basVo.setCustcode(custname);
		basVo.setCustname(custname);
		basVo.setCustshortname(custname);
		basVo.setDr(Integer.valueOf(0));
		basVo.setUrl(FBM_GENECUST);
		basVo.setPk_corp(FBM_GENECORP);
		basVo.setPk_areacl("FBM");

		FBMPubQueryDAO service = new FBMPubQueryDAO();
		Map map = service.queryMapData(" select pk_cubasdoc from fbm_cubasdoc where (custcode='"+custname+"' or custname='"+custname+"')  and pk_corp='"+FBM_GENECORP+"' ");
		String pk_cubasdoc = null;
		if(map!=null&&map.get("pk_cubasdoc")!=null){
			pk_cubasdoc = (String) map.get("pk_cubasdoc");
		}
		if(CommonUtil.isNull(pk_cubasdoc)){
			pk_cubasdoc = service.insertData(basVo,new FbmCustBasDocMapping());
		}
		return pk_cubasdoc;
	}


	/**
	 * 保存手工录入的开户银行
	 * 参数币种PK,银行编码，开户行
	 * return:开户银行PK
	 * @throws BusinessException
	 * @throws BusinessException
	 */
	public String insertInputAccbank(String pk_curr,String bankaccode,String bankaccname) throws BusinessException{
		FbmBankaccbasVO headVo = new FbmBankaccbasVO();
		headVo.setAccountcode(bankaccode);
		headVo.setAccount(bankaccode);
		headVo.setAccountname(bankaccname);

		headVo.setDr(new Integer(0));
		headVo.setPk_corp(FBM_GENECORP);
		headVo.setPk_currtype(pk_curr);

		String pk_bankaccbas = null;
		FBMPubQueryDAO service = new FBMPubQueryDAO();
		Map map = service.queryMapData(" select pk_bankaccbas from fbm_bankaccbas where account='"+bankaccode+"' and pk_corp='"+FBM_GENECORP+"' ");
		if(map!=null&&map.get("pk_bankaccbas")!=null){
			pk_bankaccbas = (String) map.get("pk_bankaccbas");

		}
		if(CommonUtil.isNull(pk_bankaccbas)){
			try {
				pk_bankaccbas = getBaseDAO().insertVO(headVo);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					throw new BusinessException(e.getMessage());
				}
		}
		return pk_bankaccbas;
	}
	/**
	 * 插入手动录入的银行
	 * @param bankdoccode
	 * @param bankdocname
	 * @return
	 * @throws BusinessException
	 */
	public String insertInputBankDoc(String bankdoccode,String bankdocname) throws BusinessException{
		FbmBankDocVO headVo  = new FbmBankDocVO();
		headVo.setBankdoccode(bankdoccode);
		headVo.setBankdocname(bankdocname);
		FBMPubQueryDAO service = new FBMPubQueryDAO();
		String pk_bankdoc = null;
		Map map = service.queryMapData(" select pk_bankdoc from fbm_bankdoc where bankdocname='"+bankdocname+"'");
		if(map!=null&&map.get("pk_bankdoc")!=null){
			pk_bankdoc = (String) map.get("pk_bankdoc");
		}
		
		if(CommonUtil.isNull(pk_bankdoc)){
			pk_bankdoc = getBaseDAO().insertVO(headVo);
		}
		return pk_bankdoc;
	}

	private void updateInputAccbank(String pk_bankaccbas,String bankaccname) throws BusinessException{
		FbmBankaccbasVO headVo = new FbmBankaccbasVO();
		headVo.setPrimaryKey(pk_bankaccbas);
		headVo.setAccountcode(bankaccname);
		headVo.setAccount(bankaccname);
		getBaseDAO().updateVO(headVo, new String[]{"accountcode","account"});
	}
	/**
	 * <p>
	 * 校验是否非法票据
	 * <p>
	 * 作者：lpf 日期：2007-10-18
	 *
	 * @throws BusinessException
	 */
	private void checkValidateBill(RegisterVO RegisterVo) throws BusinessException {
		IllegalVO illeagalVo = new IllegalVO();
		illeagalVo.setFbmbillno(RegisterVo.getFbmbillno());
		illeagalVo.setEnddate(RegisterVo.getEnddate());
		illeagalVo.setInvoicedate(RegisterVo.getInvoicedate());
		illeagalVo.setInvoiceunitname(RegisterVo.getInvoiceunitname());
		illeagalVo.setMoneyy(RegisterVo.getMoneyy());
		illeagalVo.setPk_curr(RegisterVo.getPk_curr());
		illeagalVo.setPayunitname(RegisterVO.PAYUNITNAME);
		illeagalVo.setReceiveunitname(RegisterVo.getReceiveunitname());
		String errMsg = new IllegalBillServiceImpl().validateBeforeInsert(illeagalVo);
		if(!CommonUtil.isNull(errMsg)){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005","UPP36201005-000010")/* @res"编号为"*/+RegisterVo.getFbmbillno()+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005","UPP36201005-000011")/* @res"的票据是非法票据，无法继续收票业务"*/);
		}
	}

//	/**
//	 * <p>
//	 * 处理收款单位/银行账号/出票单位参照
//	 * <p>
//	 * 作者：lpf
//	 * 日期：2007-9-14
//	 * @param baseinfoVo
//	 * @throws NamingException
//	 */
//	private void checkReceiveUnitisCust(BaseinfoVO baseinfoVo)
//			throws NamingException {
//		SpecialOIDCreater oidCreater = new SpecialOIDCreater();
//		if(CommonUtil.isNull(baseinfoVo.getReceiveunit())&&!CommonUtil.isNull(baseinfoVo.getReceiveunitname())){
//			String pk_cubasdoc = oidCreater.getGatherUnitOIDs();
//			baseinfoVo.setReceiveunit(pk_cubasdoc);
//		}
//		if(CommonUtil.isNull(baseinfoVo.getReceivebankacc())&&!CommonUtil.isNull(baseinfoVo.getReceiveaccname())){
//			String pk_accbank = oidCreater.getGatherBankOIDs();
//			baseinfoVo.setReceivebankacc(pk_accbank);
//		}
//		if(CommonUtil.isNull(baseinfoVo.getInvoiceunit())&&!CommonUtil.isNull(baseinfoVo.getInvoiceunitname())){
//			String pk_cubasdoc = oidCreater.getGatherUnitOIDs();
//			baseinfoVo.setInvoiceunit(pk_cubasdoc);
//		}
//	}

	/**
	 * 删除票据基本信息
	 * @param parentVO
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public void deleteBaseInfo(RegisterVO parentVO) throws BusinessException {
		String pk_baseinfo = parentVO.getPk_baseinfo();
		if(parentVO==null||pk_baseinfo==null)
			return;
		if(parentVO!=null&&parentVO.getGathertype().equals(FbmBusConstant.GATHER_TYPE_ENDORE)){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005","UPP36201005-000012")/* @res"由背书产生的收票单据无法删除!"*/);
		}
		if(parentVO.getIsnewbill().booleanValue()){
			String sql = " pk_baseinfo='"+pk_baseinfo+"' and isnull(dr,0)=0 ";
			SuperVO[] queryVos = new HYPubBO().queryByCondition(ActionVO.class, sql);
			if(CommonUtil.isNull(queryVos)){
				new CommonDAO().deleteBaseinfobyPk(pk_baseinfo);
			}
		}else
		{
			//NCdp201057361公司000000001 ，删除收票登记单 票据编号55，再新增时，应该还能看到该票号，目前不能
			//由于可能是退票，所以删除 时要维护票据基本信息的票据状态。
			ActionVO fbmaction = null;
			String endstatus = "";
			CommonDAO commondao = new CommonDAO();
			Collection collection = commondao.getBaseDAO().retrieveByClause(ActionVO.class, "pk_baseinfo = '"+pk_baseinfo+"' order by serialnum desc");
			ActionVO actionvo[]  = (ActionVO[])collection.toArray(new ActionVO[0]);
			if(actionvo!=null && actionvo.length>0){
				fbmaction = actionvo[0];
				endstatus = fbmaction.getEndstatus();
			}
			String sql = "update fbm_baseinfo set baseinfostatus = '"+endstatus+"' where pk_baseinfo='"+pk_baseinfo+"'";
			commondao.getBaseDAO().executeUpdate(sql);
		}
	}

	/**
	 * <p>
	 * 删除废弃的票据基本信息记录
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-4
	 * @throws BusinessException
	 */
	public void deleteUnUsedBaseInfo() throws BusinessException {
		//此部分代码有严重的效率问题
//		String sql = " pk_baseinfo not in ( select pk_baseinfo from fbm_register where isnull(dr,0) = 0 )";
//		new CommonDAO().deleteBaseinfobyClause(sql);
		
		//改进后，先查询出无用的pk_baseinfo再删除
		String sql ="select distinct fbm_baseinfo.pk_baseinfo from fbm_baseinfo left join fbm_register on fbm_baseinfo.pk_baseinfo=fbm_register.pk_baseinfo where fbm_register.pk_register is null and isnull(fbm_register.dr,0)=0 and isnull(fbm_baseinfo.dr,0)=0";
		BaseDAO dao = new BaseDAO();
		List list  = (List)dao.executeQuery(sql, new ColumnListProcessor());
		if(list!=null && list.size() > 0){
			int count = list.size();
			String pk_baseinfo;
			for(int i = 0; i < count;i++){
				pk_baseinfo = (String)list.get(i);
				dao.deleteByPK(BaseinfoVO.class, pk_baseinfo);
			}
		}
	}

	/**
	 *
	 * <p>
	 * 校验收票、开票编号是否重复
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-12
	 * @param registerVO
	 * @throws BusinessException
	 */
	public void checkRegisterNoUnique(RegisterVO registerVO) throws BusinessException{
		HYBillVO billVo = new HYBillVO();
		billVo.setParentVO(registerVO);
		BillIsUnique unique = new BillIsUnique();

		ArrayList<String[]> unlist = new ArrayList<String[]>();
		unlist.add(new String[] { RegisterVO.REGISTERNO,RegisterVO.PK_BILLTYPECODE });

		try {
			unique.checkBillIsUnique(billVo, unlist);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005","UPP36201005-000013")/* @res"该单据中的收票编号已经存在，请重新录入"*/);
		}
	}

	/**
	 *
	 * <p>
	 * 后台推式保存收票登记单，暂时不校验收票登记单编号是否重复
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-25
	 * @param registerBillVos
	 * @throws BusinessException
	 * 
	 * eidt by wangyg
	 * 修改了方法的返回值，将原方法返回的void改为String[]数组，数组中存放的是主键.
	 * 日期：2008-3-10
	 */
	public String[] saveRegisterVos(HYBillVO[] registerBillVos) throws BusinessException{
		if(registerBillVos==null||registerBillVos.length==0){
			return null;
		}
		String[] pk_arry = new String[registerBillVos.length];
		HYPubBO bo = new HYPubBO();
		for (int i = 0; i < registerBillVos.length; i++) {
			if(registerBillVos[i]!=null){
				HYBillVO billVo = registerBillVos[i];
				if(billVo!=null&&billVo.getParentVO()!=null){
					AggregatedValueObject newVo = bo.saveBill(billVo);//保存收票登记单
					
					pk_arry[i] = newVo.getParentVO().getPrimaryKey();
					
					doBillAction((HYBillVO)newVo,FbmActionConstant.SAVE);

					//不加审批流,审核收票登记单
					RegisterVO registerVo = (RegisterVO) newVo.getParentVO();
					registerVo.setVbillstatus(IBillStatus.CHECKPASS);
					registerVo.setVapproveid(registerVo.getVoperatorid());
					registerVo.setDapprovedate(registerVo.getDoperatedate());
					bo.update(registerVo);
					newVo.setParentVO(bo.queryByPrimaryKey(RegisterVO.class, registerVo.getPrimaryKey()));
					doBillAction((HYBillVO)newVo,FbmActionConstant.AUDIT);
				}
			}
		}
		return pk_arry;
	}

	/**
	 *
	 * <p>
	 * 后台推式删除收票登记单
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-25
	 * @param pk_source
	 * @throws BusinessException
	 */
	public void deleteRegisterVosForOuterSys(String pk_source) throws BusinessException{
		HYPubBO bo = new HYPubBO();
		try {
			SuperVO[] queryVos = queryRegisterVOSBySrc(pk_source);
			if(queryVos!=null&&queryVos.length>0){
				for (int i = 0; i < queryVos.length; i++) {
					if(queryVos[i]!=null){
						HYBillVO billVo = new HYBillVO();
						billVo.setParentVO(queryVos[i]);
						doBillAction(billVo,FbmActionConstant.CANCELAUDIT);
						doBillAction(billVo,FbmActionConstant.DELETE);
						bo.delete(queryVos[i]);
					}
				}
			}
		} catch (UifException e) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005","UPP36201005-000014")/* @res"删除收票登记单错误"*/+e.getMessage());
		}
	}
	/**
	 * 根据pk_source查询出所有的RegisterVO对象
	 * @return
	 * @throws BusinessException
	 */
	public SuperVO[] queryRegisterVOSBySrc(String pk_source) throws BusinessException {
		HYPubBO bo = new HYPubBO();
		return bo.queryByCondition(RegisterVO.class, RegisterVO.PK_SOURCE+"='"+pk_source+"'");
	}

	/**
	 * 根据过滤条件查询出所有的RegisterVO对象
	 * added by wes
	 * @return
	 * @throws BusinessException
	 */
	public SuperVO[] queryRegisterVOSByCondition(String condition) throws BusinessException {
		HYPubBO bo = new HYPubBO();
		return bo.queryByCondition(RegisterVO.class, condition);
	}


	private void doBillAction(HYBillVO billVo,String actioncode) throws BusinessException{
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, actioncode).doAction(billVo.getParentVO(),actioncode,true);
	}

	/**
	 *
	 * <p>
	 * 作废票据
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-29
	 * @param isDisable
	 */
	public void updateGatherBillDisable(String pk_baseinfo,UFBoolean isDisable) throws BusinessException{
		getBaseDAO().executeUpdate(" update fbm_baseinfo set disable='"+isDisable.toString()+"' where pk_baseinfo='"
				+ pk_baseinfo + "' ");
	}

	/**
	 * 更新收付款标志
	 * @param sfflag
	 * @throws BusinessException
	 */
	public void updateRegisterSfflag(String pk_register,boolean sfflag) throws BusinessException{
		String sql = null;
		if(sfflag){
			sql = "update fbm_register set sfflag = 'Y' where pk_register='" + pk_register + "'";
		}else{
			sql = "update fbm_register set sfflag = 'N' where pk_register='" + pk_register + "'";
		}
		getBaseDAO().executeUpdate(sql);
	}
	
	/**
	 * 更新收付款标志和计划项目
	 * @param pk_register
	 * @param sfflag
	 * @param pk_plansubj
	 * @throws BusinessException
	 */
	public void updateSfflagAndPlan(String pk_register,boolean sfflag,String pk_plansubj) throws BusinessException{
		String sql = null;
		
		if(sfflag){
			if(pk_plansubj!=null){
			sql = "update fbm_register set sfflag = 'Y',invoiceplanitem='"+pk_plansubj+"' where pk_register='" + pk_register + "'";
			}else{
				sql = "update fbm_register set sfflag = 'Y' where pk_register='" + pk_register + "'";
			}
		}else{
			sql = "update fbm_register set sfflag = 'N' ,invoiceplanitem=null where pk_register='" + pk_register + "'";
		}
		getBaseDAO().executeUpdate(sql);
	}

	private BaseDAO getBaseDAO() {
		if (m_baseDAO == null) {
			m_baseDAO = new BaseDAO();
		}
		return m_baseDAO;
	}
}