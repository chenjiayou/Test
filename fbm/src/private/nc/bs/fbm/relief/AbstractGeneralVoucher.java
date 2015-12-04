package nc.bs.fbm.relief;

import nc.bs.dap.out.IAccountProcMsg;
import nc.bs.fbm.pub.UnVoucherCheckService;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.uap.bd.ISettleCenter;
import nc.vo.dap.inteface.IAccountPlat;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 *
 * 类功能说明： 制证与取消制证 日期：2008-3-19
 *
 */
public abstract class AbstractGeneralVoucher implements IAccountProcMsg {

	/**
	 * 中心VO
	 */
	public static final String CENTER = "CENTER";
	/**
	 * 单位VO
	 */
	public static final String UNIT = "UNIT";

	public AbstractGeneralVoucher() {
	}

	/**
	 * <p>
	 * 制证 根据参数生成成员单位的总账凭证 生成结算中心的结算凭证
	 * 取消制证　根据opType判断是制证还是取消制证。
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-3-19
	 * @param billVO
	 * @param opType   判断是制证、取消制证还是冲销制证 voucher：制证 cancelvoucher:取消制证 clear:冲销制证
	 * @throws BusinessException
	 */

	public AggregatedValueObject voucher(AggregatedValueObject billVO,String opType) throws BusinessException{
		if(FbmBusConstant.OP_CANCELVOUCHER.equals(opType)){
			UnVoucherCheckService srv = new UnVoucherCheckService();
			if (null != billVO && billVO.getParentVO() != null && billVO.getParentVO().getPrimaryKey() !=null){
				srv.checkUnVoucher(billVO.getParentVO().getPrimaryKey());
			}
		}
		DapMsgVO dapvo = getDapVO(billVO,opType);

		handleVO(billVO);

		if(null==dapvo){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000158")/*@res "无法生成会计平台需要的DapMsgVO"*/);
		}
		OuterProxy.getDapSendMessageService().sendMessage(dapvo, billVO);

		return billVO;
	}

	/**
	 * <p>
	 * 	生成DapMsgVO
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-5-6
	 * @param billVO
	 * @param opType
	 * @return
	 * @throws BusinessException
	 */
	public DapMsgVO getDapVO(AggregatedValueObject billVO,String opType)throws BusinessException {

		DapMsgVO unitVO =  null;
		String voucherOperate = ""; //生成凭证和删除凭证标识。
		String companyType = ""; //中心还是单位标识。
		if (null != billVO && billVO.getParentVO() != null) {
			//得到当前登陆公司
			String corpPK = InvocationInfoProxy.getInstance().getCorpCode();
			SuperVO newVO = getSuperVO(billVO);
			String pk_billtypecode = (String)newVO.getAttributeValue("pk_billtypecode");

			//不区分中心单位制证的单据类型
			boolean isExceptBillType = FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT.equals(pk_billtypecode) || FbmBusConstant.BILLTYPE_ENDORE.equals(pk_billtypecode) || FbmBusConstant.BILLTYPE_COLLECTION_UNIT.equals(pk_billtypecode);
			//判断公司是否为中心
			if (isCenter(corpPK) && !isExceptBillType) {
				companyType = CENTER;
				if(FbmBusConstant.OP_VOUCHER.equals(opType)) //判断是制证还是取消制证
				{
					voucherOperate = FbmBusConstant.OP_VOUCHER;
				}else if(FbmBusConstant.OP_CLEAR.equals(opType)){
					voucherOperate = FbmBusConstant.OP_VOUCHER;
				}
				else  //取消制证
				{
					UFBoolean centervoucher = (UFBoolean)newVO.getAttributeValue("centervoucher");
					if(!centervoucher.booleanValue()){
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000159")/*@res "中心未制证，不允许取消中心制证，请刷新后重试。"*/);
					}

					voucherOperate = FbmBusConstant.OP_CANCELVOUCHER;
				}
			} else {// 单位操作进行制证操作
				companyType = UNIT;
				if(FbmBusConstant.OP_VOUCHER.equals(opType))
				{
					//判断是否为已制证
					UFBoolean unitvoucher = (UFBoolean)newVO.getAttributeValue("unitvoucher");
					if(unitvoucher.booleanValue()){
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000160")/*@res "单位已制证，不允许单位制证，请刷新后重试。"*/);
					}

					voucherOperate = FbmBusConstant.OP_VOUCHER;
				}else if(FbmBusConstant.OP_CLEAR.equals(opType)){
					voucherOperate = FbmBusConstant.OP_VOUCHER;
				}
				else
				{
					UFBoolean unitvoucher = (UFBoolean)newVO.getAttributeValue("unitvoucher");
					if(!unitvoucher.booleanValue()){
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000161")/*@res "单位未制证，不允许单位取消制证，请刷新后重试。"*/);
					}
					voucherOperate = FbmBusConstant.OP_CANCELVOUCHER;
				}
			}
			unitVO = getDapVO(billVO, voucherOperate, companyType);

			// 如果为删除，则删除前校验是否可以删除
			if (!FbmBusConstant.OP_VOUCHER.equals(voucherOperate)) {
				valideVoucherBeforeDel(unitVO);
			}
		}
		return unitVO;
	}

	/**
	 * <p>
	 * 	处理AggregatedValueObject
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-5-6
	 * @param vo
	 * @throws BusinessException
	 */
	public void handleVO(AggregatedValueObject vo)
			throws BusinessException {

		if (vo.getChildrenVO() == null || vo.getChildrenVO().length == 0) {
			String[] vonames = getVONames();
			try {
				SuperVO tmpChild = (SuperVO)Class.forName(vonames[2]).newInstance();
				vo.setChildrenVO(new CircularlyAccessibleValueObject[]{tmpChild});
			} catch (Exception e) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000162")/*@res "无法实例化子表类"*/);
			}
		}
	}


	/**
	 * <p>
	 * 制证或取消制证方法
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-3-19
	 * @param vo
	 * @param opType
	 * @param companyType         判断是单位还是中心，单位为：UNIT,中心为：CENTER
	 * @throws BusinessException
	 */
	public void voucherAction(AggregatedValueObject vo, String opType,String companyType)
			throws BusinessException {

		DapMsgVO unitVO = getDapVO(vo, opType, companyType);
		// 如果为删除，则删除前校验是否可以删除
		if (!FbmBusConstant.OP_VOUCHER.equals(opType)) {
			valideVoucherBeforeDel(unitVO);
		}
		if (vo.getChildrenVO() == null || vo.getChildrenVO().length == 0) {
			String[] vonames = getVONames();
			try {
				SuperVO tmpChild = (SuperVO)Class.forName(vonames[2]).newInstance();
				vo.setChildrenVO(new CircularlyAccessibleValueObject[]{tmpChild});
			} catch (Exception e) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000162")/*@res "无法实例化子表类"*/);
			}

		}

		OuterProxy.getDapSendMessageService().sendMessage(unitVO, vo);
	}

	/**
	 * 判断一个公司是否为结算中心
	 *
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	public boolean isCenter(String pk_corp) throws BusinessException {
		boolean isCenter = false;
		// 中心
		if (((ISettleCenter) NCLocator.getInstance().lookup(
				ISettleCenter.class.getName())).isSettleCenter(pk_corp)) {
			isCenter = true;
		}
		return isCenter;
	}

	/**
	 * 删除凭证前校验是否允许删除
	 *
	 * @param msgVo
	 * @param dap
	 * @throws BusinessException
	 */
	public void valideVoucherBeforeDel(DapMsgVO msgVo)
			throws BusinessException {
		boolean isEdit = OuterProxy.getDapSendMessageService().isEditBillType(
				msgVo.getCorp(), msgVo.getSys(), msgVo.getProc(),
				msgVo.getBusiType(), msgVo.getProcMsg());
		if (!isEdit) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000163")/*@res "已生成凭证，该业务不能进行操作!"*/);
		}
	}

	/**
	 * 根据调剂单PK返回相应的调剂单对象
	 *
	 * @param pk
	 * @return
	 * @throws BusinessException
	 */
	public SuperVO getSuperVO(AggregatedValueObject billVo)
			throws BusinessException {
		if (null == billVo) {
			return null;
		}
		String pk = (billVo.getParentVO()).getPrimaryKey();
		if (null == pk || "".equals(pk.trim())) {
			return null;
		}
		return (SuperVO) (new HYPubBO().queryByPrimaryKey(billVo.getParentVO().getClass(), pk));
	}

	/**
	 * 根据SuperVO构造出会计平台要的DAPMsgVO
	 *
	 * @return
	 */
	public DapMsgVO getDapVO(AggregatedValueObject billVo, String opType,
			String unitType) throws BusinessException {
		SuperVO superVO = (SuperVO) billVo.getParentVO();
		DapMsgVO msgVo = new DapMsgVO();
		msgVo.setBillCode((String)superVO.getAttributeValue("vbillno"));
		msgVo.setChecker((String)superVO.getAttributeValue("vapproveid"));
		msgVo.setSys("FBM");

		msgVo.setDestSystem(IAccountPlat.DESTSYS_GL);
		// 中心，生成结算凭证,公司取自己即可
		//if (CENTER.equals(unitType)) {
			msgVo.setCorp((String)superVO.getAttributeValue("pk_corp"));
		//}

		if (FbmBusConstant.OP_VOUCHER.equals(opType)) {
			msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000164")/*@res "票据单位制证"*/);
			msgVo.setMsgType(DapMsgVO.ADDMSG);// 设置为增加消息
		} else {
			msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000165")/*@res "票据单位取消制证"*/);
			msgVo.setMsgType(DapMsgVO.DELMSG);// 设置为删除消息
		}
		msgVo.setBillCode((String)superVO.getAttributeValue("vbillno"));// 单据编码

		msgVo.setProc((String)superVO.getAttributeValue("pk_billtypecode"));
		msgVo.setOperator((String)superVO.getAttributeValue("voperatorid"));

		msgVo.setProcMsg(superVO.getPrimaryKey() + StorageVO.Procmsg_flag
				+ msgVo.getCorp());

		msgVo.setBusiDate((UFDate)superVO.getAttributeValue("dapprovedate"));// 设置业务日期为审核日期
		msgVo.setMoney((UFDouble)superVO.getAttributeValue("moneyy"));

		//此抽象方法要求实现类实现，完成MsgDapVO中未构造的部分。
		completeDapMsgVO(msgVo,superVO,unitType);

		return msgVo;
	}

	/**
	 * 此方法只有会计平台调用。
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
		//由实现类实现此vonames的数组内容
		String[] vonames = getVONames();
		AggregatedValueObject vo = bo.queryBillVOByPrimaryKey(vonames, pk);
		if (vo == null || vo.getParentVO() == null
				|| vo.getChildrenVO() == null || vo.getChildrenVO().length == 0) {
			return vo;
		}

		//查询协带信息SQL
		String sql = handelTakenSqlValue((SuperVO)vo.getParentVO());

		//更新当前vo中的信息，把协带信息放入vo中.
		setValues(vo,sql);

		return vo;
	}



	/**
	 * <p>
	 * 	实现类要实现此方法，其中要实现　dapvo.setMoney(UFDouble money);汇总金额
	 *  同时判断unitType为单们还是为中心，如果为单位，则需要实现 msgVo.setCorp(); 选取调剂单位对应公司
	 *  还要实现dapvo.setCurrency();币种。
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-3-19
	 * @param dapvo　　　　　会计平台要用到的DapMsgVO
	 * @param supervo	   当前的ＶＯ。
	 * @param unitType     类型：UNIT为单位，CENTER为中心
	 */
	public abstract void completeDapMsgVO(DapMsgVO dapvo,SuperVO supervo,String unitType);

	/**
	 * <p>
	 * 得到主子表ＶＯ。
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-3-19
	 * @return
	 */
	public abstract String[] getVONames();


	/**
	 * <p>
	 *  构造SQL,构造背书办理、银行托收、贴现办理中的协带信息的SQL。
	 *  此方法在会计平台调用时会用到。当前的VO中没有协带值信息，所以需要重
	 *  新查询一下，把协带信息查询出来。
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-4-1
	 * @return
	 */
	public abstract String handelTakenSqlValue(SuperVO supervo);

	/**
	 * <p>
	 * 	将参数SQL查询后构造的SuperVO,放到聚合VO的parentVO中。
	 *  此方法在会计平台调用时会用到，通过查询协带的SQL，查询出带有协带信息的SuperVO
	 *  将SuperVO放到AggregatedValueObject中返回给会计平台。
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-4-1
	 * @param vo   　聚合ＶＯ
	 * @param sql　　构造聚合VO中parentVO的SQL,通过handelTakenSqlValue()得到。
	 * @throws BusinessException
	 */
	public abstract void setValues(AggregatedValueObject vo, String sql) throws BusinessException ;

}