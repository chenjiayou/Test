package nc.bs.fbm.out;

import nc.bs.fbm.pub.OuterRelationDAO;
import nc.impl.fbm.cmp.CMPConstant;
import nc.itf.cdm.util.CommonUtil;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;

/**
 *
 * @author wues
 *
 */
public class AddPushBillValidator extends AbstractBillValidator {

	public void checkBaseinfo(ArapBillParamVO param)throws BusinessException {
		String djdl = param.getOuterdjdl();
		if(!CMPConstant.BILLTYPE_HJ.equals(djdl)){
			if(param.getFbmbillno() == null ){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000184")/* @res"第"*/ + param.getRow() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000185")/* @res"行票据编号不可为空"*/);
			}
			if(param.getBaseinfoVO() == null){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000184")/* @res"第"*/ + param.getRow() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000186")/* @res"行票据编号不存在"*/);
			}

			if(!param.getPk_curr().equals(param.getBaseinfoVO().getPk_curr())){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000184")/* @res"第"*/ + param.getRow() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000187")/* @res"行票据币种不一致"*/);
			}
		}else{
			param.setPk_billtypecode(FbmBusConstant.BILLTYPE_INVOICE);
		}

		String pk_billtypecode = param.getPk_billtypecode();

		String pk_busibill = param.getPk_busbill();

		RegisterVO regVO = null;
		if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {// 托收
			CollectionVO collectionVO = (CollectionVO) baseDao.retrieveByPK(
					CollectionVO.class, pk_busibill);
			regVO = (RegisterVO)baseDao.retrieveByPK(RegisterVO.class, collectionVO.getPk_source());
			// 检查金额
			if (collectionVO.getMoneyy().doubleValue() != param.getMoneyy()
					.doubleValue()) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000188")/* @res"托收单结算金额和收付报单据金额不一致"*/);
			}
			// 检查客户
			String pk_cubasdoc = regVO.getPaybillunit();

			//客商均为空也合法
			boolean isNullCust = (pk_cubasdoc == null && param.getOtherunit() == null);
//			if(pk_cubasdoc == null){
//				throw new BusinessException("收票登记单的付票单位为空，无法生成单据");
//			}
//
			boolean oneCustNull = (pk_cubasdoc == null && param.getOtherunit() != null) || (pk_cubasdoc != null && param.getOtherunit() == null);
			if (oneCustNull||(!isNullCust && !pk_cubasdoc.equals(param.getOtherunit()))) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000145")/*@res "票据的付票单位与收付报的客户不一致"*/);
			}

			// 检查收款银行帐号
			if ((collectionVO.getHolderacc() ==null &&param.getSkbankacc()!=null)|| !collectionVO.getHolderacc().equals(param.getSkbankacc())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000189")/* @res"银行托收单上的持票银行账号与收付报单据上的收款银行账号不一致"*/);
			}
			// 检查单据日期，必须大于款项收妥日期
			if (collectionVO.getDcollectiondate().after(param.getDjrq())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000190")/* @res"银行托收单上的款项收妥日期必须小于等于收付报单据日期"*/);
			}

		} else if (pk_billtypecode
				.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {// 贴现

			DiscountVO discountVO = (DiscountVO) baseDao.retrieveByPK(
					DiscountVO.class, pk_busibill);
			regVO = (RegisterVO)baseDao.retrieveByPK(RegisterVO.class, discountVO.getPk_source());
			// 检查贴现余额
			if (discountVO.getMoneyy().doubleValue() != param.getMoneyy()
					.doubleValue()) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000191")/* @res"贴现余额与收付报单据金额不一致"*/);
			}
			// 检查贴现利息
			UFDouble txlx_ybje = discountVO.getDiscountinterest();
			if (txlx_ybje == null) {
				txlx_ybje = new UFDouble(0);
			}

			if (txlx_ybje.doubleValue() != param.getTxlx_ybje().doubleValue()) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000192")/* @res"贴现利息与收付报单据上的贴现利息金额不一致"*/);
			}
			// 检查贴现手续费
			UFDouble txfy_ybje = discountVO.getDiscountcharge();
			if (txfy_ybje == null) {
				txfy_ybje = new UFDouble(0);
			}
			if (txfy_ybje.doubleValue() != param.getTxfy_ybje().doubleValue()) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000193")/* @res"贴现手续费与收付报单据上的贴现费用字段不一致"*/);
			}

			// 检查客户
			String pk_cubasdoc = regVO.getPaybillunit();
			//客商均为空也合法
			boolean isNullCust = (pk_cubasdoc == null && param.getOtherunit() == null);

			boolean oneCustNull = (pk_cubasdoc == null && param.getOtherunit() != null) || (pk_cubasdoc != null && param.getOtherunit() == null);
//			if(pk_cubasdoc == null){
//				throw new BusinessException("收票登记单的付票单位为空，无法生成单据");
//			}
//
			if (oneCustNull||(!isNullCust && !pk_cubasdoc.equals(param.getOtherunit()))) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000145")/*@res "票据的付票单位与收付报的客户不一致"*/);
			}
//			if(pk_cubasdoc == null){
//				throw new BusinessException("收票登记单的付票单位为空，无法生成单据");
//			}
//
//			if (!pk_cubasdoc.equals(param.getOtherunit())) {
//				throw new BusinessException("票据的付票单位与收付报的客户不一致");
//			}

			if(discountVO.getDiscount_account() == null){
				if(param.getSkbankacc() != null){
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000194")/* @res"贴现银行账号为空"*/);
				}
			}else if (!discountVO.getDiscount_account().equals(param.getSkbankacc())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000195")/* @res"贴现办理单上的贴现银行账号与收付报单据上的收款银行账号不一致"*/);
			}
			// 检查单据日期
			if (discountVO.getDdiscountdate().after(param.getDjrq())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000196")/* @res"贴现办理单上的贴现日期必须小于等于收付报单据日期"*/);
			}
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BILLPAY)) {// 承兑
			AcceptVO acceptVO = (AcceptVO) baseDao.retrieveByPK(AcceptVO.class,
					pk_busibill);
			// 检查金额
			if (param.getMoneyy().doubleValue() != acceptVO.getMoneyy()
					.doubleValue()) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000197")/* @res"票据付款单上的金额与收付报单据上金额不一致"*/);
			}

			String pk_cubasdoc = param.getBaseinfoVO().getReceiveunit();
			//客商均为空也合法
			boolean isNullCust = (pk_cubasdoc == null && param.getOtherunit() == null);
//			if(pk_cubasdoc == null){
//				throw new BusinessException("收票登记单的付票单位为空，无法生成单据");
//			}
//
			boolean oneCustNull = (pk_cubasdoc == null && param.getOtherunit() != null) || (pk_cubasdoc != null && param.getOtherunit() == null);
			if (oneCustNull||(!isNullCust && !pk_cubasdoc.equals(param.getOtherunit()))) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000146")/*@res "票据的收款单位与收付报的客商不一致"*/);
			}
//			String cust_sql = "pk_cubasdoc='" + pk_cubasdoc + "' and pk_corp='"
//					+ acceptVO.getPk_corp()
//					+ "' and (custflag='0' or custflag='1')";
//			HYPubBO bo = new HYPubBO();
//			CumandocVO[] cusmanVOs = (CumandocVO[]) bo.queryByCondition(
//					CumandocVO.class, cust_sql);
//			if (cusmanVOs != null && cusmanVOs.length > 0) {
//				if (!cusmanVOs[0].getPk_cumandoc().equals(param.getOtherunit())) {
//					throw new BusinessException("票据的付票单位与收付报的客户不一致");
//				}
//			}
			// 检查收款银行账号
//			if (param.getBaseinfoVO().getReceivebankacc()!= null && !param.getBaseinfoVO().getReceivebankacc().equals(param.getSkbankacc())) {
//				throw new BusinessException("票据的收款账号与收付报的收款账号不一致");
//			}
			// 检查付款银行账号
//			if (param.getBaseinfoVO().getPaybankacc()!= null&&!param.getBaseinfoVO().getPaybankacc().equals(param.getFkbankacc())) {
//				throw new BusinessException("票据的付款账号与收付报的付款账号不一致");
//			}
			// 检查单据日期
			if (acceptVO.getDacceptdate()!= null && acceptVO.getDacceptdate().after(param.getDjrq())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000198")/* @res"单据的付款日期必须小于等于收付报单据日期"*/);
			}
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INVOICE)) {// 开票
			if(pk_busibill != null && pk_busibill.length() > 0){
				regVO = (RegisterVO) baseDao.retrieveByPK(RegisterVO.class,
						pk_busibill);
			}

			//确认子表行意义：有付款银行账号，则付款信息=0；有收款账号，则保证金信息=1；其他手续费信息=2
			int type ;
			if(!CommonUtil.isNull(param.getFkbankacc())){
				type = 0;
			}else if(!CommonUtil.isNull(param.getSkbankacc())){
				type = 1;
			}else if(CommonUtil.isNull(param.getFkbankacc() )&& CommonUtil.isNull(param.getSkbankacc())){
				type = 2;
			}else {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000199")/* @res"收付款账号输入错误"*/);
			}

//			if (type == 0) {
//				// 检查付款银行账号
//				if (!param.getBaseinfoVO().getPaybankacc().equals(param.getFkbankacc())) {
//					throw new BusinessException("票据的付款银行账号和收付报的付款银行账号不一致");
//				}
//			}
			if (type == 1) {
				if(param.getFbmbillno() == null){
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000184")/* @res"第"*/ + param.getRow() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000200")/* @res"行保证金记录行必须包含票据号"*/);
				}
				if (!param.getPk_curr().equals(param.getBaseinfoVO().getPk_curr())) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000184")/* @res"第"*/ + param.getRow() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000187")/* @res"行票据币种不一致"*/);
				}
				// 检查保证金账户
				if (!regVO.getSecurityaccount().equals(param.getSkbankacc())) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000201")/* @res"票据保证金账户与收付报收款银行账号不一致"*/);
				}
				// 检查保证金金额
				if (regVO.getSecuritymoney().doubleValue() != param.getMoneyy()
						.doubleValue()) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000202")/* @res"保证金金额不一致"*/);
				}
			}

//			if (type == 2) {
//				// 检查手续费
//				if (regVO.getPoundagemoney().doubleValue() != param.getMoneyy()
//						.doubleValue()) {
//					throw new BusinessException("手续费金额不一致");
//				}
//			}
		}
	}

	public void doCheck(ArapBillParamVO param)
			throws BusinessException {
		//检查是否被其他单据使用
		String djdl = param.getOuterdjdl();
		if(!djdl.equals("hj")){
			OuterRelationDAO dao = new OuterRelationDAO();
			OuterVO[] outerVos = dao.queryByPkBusibill(param.getPk_busbill());
			if(!CommonUtil.isNull(outerVos)){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000147")/*@res "已经由票据系统推式生成单据"*/);
			}
		}
	}


	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		if(param.getFbmbillno() == null){//无票据编号，则不处理
			return;
		}

		String pk_busbill = param.getPk_busbill();
		// 插入外部单据关联关系
		OuterVO outer = new OuterVO();
		outer.setPk_outerbill_h(param.getPk_bill_h());
		outer.setPk_outerbill_b(param.getPk_bill_b());
		outer.setOuterdjdl(param.getOuterdjdl());
		outer.setOuterstatus(FbmBusConstant.OUTERBILL_USE);
		outer.setOuterbilltype(param.getOuterbilltype());

		outer.setPk_billtypecode(param.getPk_billtypecode());

		outer.setPk_corp(param.getPk_corp());
		outer.setPk_busibill(pk_busbill);
		outer.setPk_register(param.getNewActionVO().getPk_source());
		baseDao.insertVO(outer);

		// 修改业务单据状态为已生成单据
		String billtype = param.getPk_billtypecode();
		String pk_busibill = param.getPk_busbill();
		SuperVO superVO = null;

		if(billtype != null){
			if (billtype.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {
				superVO = (SuperVO) baseDao.retrieveByPK(CollectionVO.class,
						pk_busibill);
				superVO.setAttributeValue("vbillstatus", IFBMStatus.Create);
				baseDao.updateVO(superVO);
			} else if (billtype.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {
				superVO = (SuperVO) baseDao.retrieveByPK(DiscountVO.class, pk_busibill);
				superVO.setAttributeValue("vbillstatus", IFBMStatus.Create);
				baseDao.updateVO(superVO);
			} else if (billtype.equals(FbmBusConstant.BILLTYPE_INVOICE)) {
				superVO = (SuperVO) baseDao.retrieveByPK(RegisterVO.class, pk_busibill);
				superVO.setAttributeValue("vbillstatus", IFBMStatus.Create);
				baseDao.updateVO(superVO);
			} else if (billtype.equals(FbmBusConstant.BILLTYPE_BILLPAY)) {
				superVO = (SuperVO) baseDao.retrieveByPK(AcceptVO.class, pk_busibill);
				superVO.setAttributeValue("vbillstatus", IFBMStatus.Create);
				baseDao.updateVO(superVO);
			}
		}
		super.afterAction(param);
	}

}