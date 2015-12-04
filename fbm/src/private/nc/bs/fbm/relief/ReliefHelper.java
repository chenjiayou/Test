package nc.bs.fbm.relief;

import nc.bs.dao.BaseDAO;
import nc.bs.dap.out.IAccountProcMsg;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.ResetRefValues;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.itf.dap.pub.IDapSendMessage;
import nc.itf.uap.bd.ISettleCenter;
import nc.vo.dap.inteface.IAccountPlat;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

/**
 *
 * 类功能说明： 调剂出库Service 日期：2007-10-29 程序员： wues
 *
 */
public class ReliefHelper implements IAccountProcMsg {

	private CommonDAO dao = null;
//	/**
//	 * 制证,生成凭证
//	 */
//	private static final String DO_ADDVOUCHER = "DOADDVOUCHER";
//	/**
//	 * 取消制证,删除凭证
//	 */
//	private static final String DO_DELVOUCHER = "DODELVOUCHER";
	
	/**
	 * 集团编码，根据此编码和系统参数编码取系统参数值
	 */
	private static final String GROUPCODE = "@@@@";
	/**
	 * 系统参数名称
	 */
	private static final String SYSPARAMCODE = "FBM002";
	
	
	/**
	 * 中心VO
	 */
	private static final String CENTER = "CENTER";
	/**
	 * 单位VO
	 */
	private static final String UNIT = "UNIT";


	/**
	 * 传会计平台的接口
	 */
	private IDapSendMessage dap = null;

	public ReliefHelper() {
	}

	/**
	 * 制证 根据参数设置FBM002判断是否生成成员单位的总账凭证 生成结算中心的结算凭证
	 *
	 * 需求修改：如果是结算单位登录点击制证按钮则只生成单位的总账凭证
	 *
	 * @return
	 * @throws BusinessException
	 */
	public AggregatedValueObject voucher(AggregatedValueObject billVO)
			throws BusinessException {
		if (null != billVO && billVO.getParentVO() != null) {
			String corpPK = getCorpPK();
			ReliefVO newVO = getReliefVO(billVO);
			if (isCenter(corpPK)) {
				if (IFBMStatus.CHECKPASS != newVO.getVbillstatus().intValue()) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045","UPP36201045-000001")/* @res"票据调剂单状态不为已审核，不允许制证处理，请刷新后重试。"*/);
				}
				voucherCenter(billVO, FbmBusConstant.OP_VOUCHER);// 生成中心结算凭证
				if (isMakeUnitVoucher()) {
					//判断单位是否已制证,如果已制证,中心不再制单位的凭证.
					ReliefVO reliefVO = (ReliefVO)billVO.getParentVO();
					HYPubBO pubbo = new HYPubBO();
					ReliefVO reliefNew = (ReliefVO)pubbo.queryByPrimaryKey(ReliefVO.class,reliefVO.getPrimaryKey());
					if(reliefNew.getUnitvoucher().booleanValue()==false){
						voucherUnit(billVO, FbmBusConstant.OP_VOUCHER);// 生成单位总账凭证
					}
				}
			} else {// 单位操作进行制证操作
				voucherUnit(billVO, FbmBusConstant.OP_VOUCHER);// 生成单位总账凭证
			}
			billVO.setParentVO(getReliefVO(billVO));
		}
		return billVO;
	}

	/**
	 * 取消制证 删除成员单位端的总账凭证和结算中心的结算凭证
	 *
	 * 需求调整，区分单位取消制证和中心取消制证
	 *
	 * @return
	 * @throws BusinessException
	 */
	public AggregatedValueObject cancelVoucher(AggregatedValueObject billVo)
			throws BusinessException {
		if (null != billVo && null != billVo.getParentVO()) {
			String corpPK = getCorpPK();
			ReliefVO newVO = getReliefVO(billVo);
			if (isCenter(corpPK)) {
				UFBoolean centervoucher = (UFBoolean)newVO.getAttributeValue("centervoucher");
				if(!centervoucher.booleanValue()){
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000159")/*@res "中心未制证，不允许取消中心制证，请刷新后重试。"*/);
				}
				voucherCenter(billVo, FbmBusConstant.OP_CANCELVOUCHER);// 删除中心结算凭证
				if (isMakeUnitVoucher()) {
					voucherUnit(billVo, FbmBusConstant.OP_CANCELVOUCHER);// 删除单位总账凭证
				}
			} else {// 单位
				//判断是否为已制证
				UFBoolean unitvoucher = (UFBoolean)newVO.getAttributeValue("unitvoucher");
				if(!unitvoucher.booleanValue()){
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000161")/*@res "单位未制证，不允许单位取消制证，请刷新后重试。"*/);
				}
				voucherUnit(billVo, FbmBusConstant.OP_CANCELVOUCHER);// 删除单位总账凭证
			}

			billVo.setParentVO(getReliefVO(billVo));
		}
		return billVo;
	}

	/**
	 * 根据调剂单PK返回相应的调剂单对象
	 *
	 * @param pk
	 * @return
	 * @throws BusinessException
	 */
	private ReliefVO getReliefVO(AggregatedValueObject billVo)
			throws BusinessException {
		if (null == billVo) {
			return null;
		}
		String pk = ((ReliefVO) billVo.getParentVO()).getPrimaryKey();
		if (null == pk || "".equals(pk.trim())) {
			return null;
		}
		return (ReliefVO) (new HYPubBO().queryByPrimaryKey(ReliefVO.class, pk));
	}

	/**
	 * 远程调用取得当前登录公司
	 *
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	private String getCorpPK() throws BusinessException {
		return InvocationInfoProxy.getInstance().getCorpCode();
	}

	/**
	 * 判断一个公司是否为结算中心
	 *
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	private boolean isCenter(String pk_corp) throws BusinessException {
		boolean isCenter = false;
		// 中心
		if (getSettleCenter().isSettleCenter(pk_corp)) {
			isCenter = true;
		}
		return isCenter;
	}

	/**
	 * 取结算中心service
	 *
	 * @return
	 * @throws BusinessException
	 */
	private ISettleCenter getSettleCenter() throws BusinessException {
		return (ISettleCenter) NCLocator.getInstance().lookup(
				ISettleCenter.class.getName());
	}


	/**
	 * 出库
	 *
	 * 生成出库人和出库日期进行出库保存
	 *
	 * @return
	 * @throws BusinessException
	 */
	public HYBillVO updateReliefOut(HYBillVO vo, String date, String pk_user)
			throws BusinessException {
		HYPubBO bo = new HYPubBO();
		if (null != vo && null != vo.getParentVO()) {
			ReliefVO reliefVO = (ReliefVO) vo.getParentVO();
			reliefVO.setOutdate(new UFDate(date));
			reliefVO.setOutperson(pk_user);
			bo.update(reliefVO);
		}
		return (HYBillVO) bo.queryBillVOByPrimaryKey(new String[] {
				HYBillVO.class.getName(), ReliefVO.class.getName(),
				ReliefBVO.class.getName() }, vo.getParentVO().getPrimaryKey());
	}



	/**
	 * 制/删单位的总账凭证
	 *
	 * @param vo
	 * @param corpPK
	 * @param opType
	 * @throws BusinessException
	 */
	private void voucherUnit(AggregatedValueObject vo, String opType)
			throws BusinessException {
		// 单位VO,生成单位总账凭证时需要corpPK
		DapMsgVO unitVO = getDapVO(vo, opType, UNIT);
		ReliefVO reliefVO = (ReliefVO)vo.getParentVO();

		if (FbmBusConstant.OP_VOUCHER.equals(opType)) {
			reliefVO.setUnitvoucher(new UFBoolean(true));
		} else{// 如果为删除，则删除前校验是否可以删除
			valideVoucherBeforeDel(unitVO);
			reliefVO.setUnitvoucher(new UFBoolean(false));
			reliefVO.setUnitvoucherdate(null);
			reliefVO.setUnitvouchermanid(null);
		}
		BaseDAO dao = new BaseDAO();
		dao.updateVO(reliefVO);

		getDapService().sendMessage(unitVO, vo);
	}

	/**
	 * 制/删中心的结算凭证
	 *
	 * @param vo
	 * @param corpPK
	 * @param opType
	 * @throws BusinessException
	 */
	private void voucherCenter(AggregatedValueObject vo, String opType)
			throws BusinessException {
		// 中心VO,生成单位总账凭证时不需要corpPK
		DapMsgVO centerVO = getDapVO(vo, opType, CENTER);
		ReliefVO reliefVO = (ReliefVO)vo.getParentVO();
		if (FbmBusConstant.OP_VOUCHER.equals(opType)) {
			reliefVO.setCentervoucher(new UFBoolean(true));
		}else{
			valideVoucherBeforeDel(centerVO);
			reliefVO.setCentervoucher(new UFBoolean(false));
		}
		BaseDAO dao = new BaseDAO();
		dao.updateVO(reliefVO);
		getDapService().sendMessage(centerVO, vo);
	}

	/**
	 * 根据参数设置确定否生成成员单位的总账凭证
	 *
	 * @return
	 * @throws BusinessException
	 */
	private boolean isMakeUnitVoucher() throws BusinessException {
		// 取集团级参数
		String isAuto = OuterProxy.getSysInitQry().getParaString(GROUPCODE,
				SYSPARAMCODE);
		// 自动生成单位凭证,默认自动生成
		if (null == isAuto || "".equals(isAuto)
				|| UFBoolean.TRUE.equals(UFBoolean.valueOf(isAuto)))
			return true;
		else
			return false;
	}

	/**
	 * 删除凭证前校验是否允许删除
	 *
	 * @param msgVo
	 * @param dap
	 * @throws BusinessException
	 */
	private void valideVoucherBeforeDel(DapMsgVO msgVo)
			throws BusinessException {
		boolean isEdit = getDapService().isEditBillType(msgVo.getCorp(),
				msgVo.getSys(), msgVo.getProc(), msgVo.getBusiType(),
				msgVo.getProcMsg());
		if (!isEdit) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045","UPP36201045-000004")/* @res"已生成凭证，该业务不能进行操作!"*/);
		}
	}

	/**
	 * 得到DAPMsgVO
	 *
	 * @return
	 */
	private DapMsgVO getDapVO(AggregatedValueObject billVo, String opType,
			String unitType) throws BusinessException {
		ReliefVO reliefVO = (ReliefVO) billVo.getParentVO();

		DapMsgVO msgVo = new DapMsgVO();
		msgVo.setBillCode(reliefVO.getVbillno());

		msgVo.setMoney(reliefVO.getSummoney());
		msgVo.setChecker(reliefVO.getVapproveid());
		msgVo.setSys("FBM");
		msgVo.setDestSystem(IAccountPlat.DESTSYS_GL);
		// 中心，生成结算凭证,公司取自己即可
		if (CENTER.equals(unitType)) {
			msgVo.setCorp(reliefVO.getPk_corp());
			if (FbmBusConstant.OP_VOUCHER.equals(opType)) {
				msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045","UPP36201045-000005")/* @res"票据调剂中心制证"*/);
				msgVo.setMsgType(DapMsgVO.ADDMSG);// 设置为增加消息
			} else {
				msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045","UPP36201045-000006")/* @res"票据调剂中心取消制证"*/);
				msgVo.setMsgType(DapMsgVO.DELMSG);// 设置为删除消息
			}
		} else {
			// 单位，生成总账凭证，公司取调剂单位的公司

			msgVo.setCorp(getDAO().queryCorpByCust(reliefVO.getReliefunit()));// 选取调剂单位对应公司
			if (FbmBusConstant.OP_VOUCHER.equals(opType)) {
				msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045","UPP36201045-000007")/* @res"票据调剂单位制证"*/);
				msgVo.setMsgType(DapMsgVO.ADDMSG);// 设置为增加消息
			} else {
				msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045","UPP36201045-000008")/* @res"票据调剂单位取消制证"*/);
				msgVo.setMsgType(DapMsgVO.DELMSG);// 设置为删除消息
			}
		}
		msgVo.setBillCode(reliefVO.getVbillno());// 单据编码
		msgVo.setProc(reliefVO.getPk_billtypecode());
		msgVo.setOperator(reliefVO.getVoperatorid());
		msgVo.setProcMsg(reliefVO.getPrimaryKey() + StorageVO.Procmsg_flag
				+ msgVo.getCorp());
 		msgVo.setBusiDate(reliefVO.getDapprovedate());// 设置业务日期为审核日期

		// msgVo.setCurrency(strCurrency);//币种

		ReliefBVO[] vos = (ReliefBVO[]) billVo.getChildrenVO();
		
		
		if (null != vos && vos.length != 0) { 
			//设置本币汇率和本币金额
			
			String pk_corp = null;
			UFDate voucherDate =  null;
			if (CENTER.equals(unitType)) {
				pk_corp = reliefVO.getPk_corp();
				voucherDate = reliefVO.getVoucherdate();
			}else{
				pk_corp = reliefVO.getReliefcorp();
				voucherDate = reliefVO.getUnitvoucherdate();
			}
			CurrencyPublicUtil currencyPublicUtil = new CurrencyPublicUtil(pk_corp);
			currencyPublicUtil.setPk_currtype_y(reliefVO.getPk_currtype());
			UFDouble[] fbrate = currencyPublicUtil.getExchangeRate(String.valueOf(voucherDate));
			for (int i = 0; i < vos.length; i++) {
				UFDouble[] yfbmoney = currencyPublicUtil.getYfbMoney(vos[i].getMoneyy(), String.valueOf(voucherDate));
				vos[i].setMoneyb(yfbmoney[2]);
				vos[i].setBrate(fbrate[1]);
			}
			msgVo.setCurrency(vos[0].getPk_curr());
		}

		return msgVo;
	}

	public DapMsgVO getDapMsgVo(AggregatedValueObject billVO, String opType) throws BusinessException{
		String unitType = null;
		if(isCenter(getCorpPK())) {
			unitType = CENTER;
		} else {
			unitType = UNIT;
		}
		return getDapVO(billVO, opType, unitType);
	}

	/**
	 * 取得DAO
	 *
	 * @return
	 */
	private CommonDAO getDAO() {
		if (null == dao) {
			dao = new CommonDAO();
		}
		return dao;
	}

	/**
	 * 得到传凭证的接口
	 *
	 * @return
	 */
	private IDapSendMessage getDapService() {
		if (null == dap) {
			dap = OuterProxy.getDapSendMessageService();
		}
		return dap;
	}

	/**
	 * 根据-进行pk拆分,拆分出Pk和单位
	 */
	public AggregatedValueObject queryDataByProcId(String billTypeOrProc,
			String procMsg) throws BusinessException {
		if (CommonUtil.isNull(billTypeOrProc) || CommonUtil.isNull(procMsg)) {
			return null;
		}
		String[] splits = procMsg.split(StorageVO.Procmsg_flag);
		if (splits == null || splits.length == 0) {
			return null;
		}
		String pk = splits[0];
		HYPubBO bo = new HYPubBO();
		String[] vonames = new String[] { HYBillVO.class.getName(),
				ReliefVO.class.getName(), ReliefBVO.class.getName() };
		AggregatedValueObject vo = bo.queryBillVOByPrimaryKey(vonames, pk);
		if (vo == null || vo.getParentVO() == null
				|| vo.getChildrenVO() == null || vo.getChildrenVO().length == 0) {
			return vo;
		}

		if (vo.getChildrenVO() != null && vo.getChildrenVO().length != 0) {
			ResetRefValues.setReliefBodyRefValues(vo);
		}

		/*
		 * StringBuffer sql = new StringBuffer(); sql.append(" select
		 * fbm_relief_b.pk_relief_b,fbm_relief_b.pk_source,fbm_relief_b.pk_baseinfo,fbm_relief_b.pk_relief,");
		 * sql.append(" fbm_baseinfo.fbmbillno
		 * fbmbillno,fbm_baseinfo.payunit,"); sql.append("
		 * fbm_baseinfo.receiveunit,fbm_baseinfo.pk_curr,"); sql.append("
		 * fbm_baseinfo.moneyy,fbm_baseinfo.invoicedate,fbm_register.gatherdate,fbm_baseinfo.enddate,fbm_baseinfo.invoiceunit,fbm_register.keepunit,");
		 * sql.append("
		 * fbm_register.paybillunit,fbm_register.moneyb,fbm_register.brate, ");
		 * sql.append("
		 * fbm_register.moneyf,fbm_register.frate,fbm_baseinfo.fbmbilltype ");
		 * sql.append(" from fbm_relief_b left join fbm_register ");
		 * sql.append(" on(fbm_relief_b.pk_source=fbm_register.pk_register) join
		 * fbm_baseinfo on (fbm_register.pk_baseinfo=fbm_baseinfo.pk_baseinfo)
		 * where ");
		 *
		 * ReliefBVO[] reliefBVO = (ReliefBVO[]) vo.getChildrenVO(); for (int i =
		 * 0; i < reliefBVO.length; i++) { sql.append(" pk_relief_b
		 * ='"+reliefBVO[i].getPk_relief_b()+"' "); if(i<reliefBVO.length-1){
		 * sql.append(" or "); } }
		 *
		 * FBMPubQueryDAO dao = new FBMPubQueryDAO(); SuperVO[] queryVos =
		 * dao.queryData(sql.toString(), ReliefBVO.class);
		 * vo.setChildrenVO(queryVos);
		 */
		return vo;
	}



}