package nc.bs.fbm.discount.action;

import nc.bs.fbm.endore.EndoreService;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

public class CancelAuditDiscountTran<K extends DiscountVO, T extends DiscountVO>  extends ActionDiscountTran<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
			//校验票据动作
		if(!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)
					&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000262")/* @res"的前一操作必须是审核贴现办理单。"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param) throws BusinessException {
		DiscountVO discountVO = param.getSuperVO();
		if(discountVO.getResult().equalsIgnoreCase(FbmBusConstant.DISCOUNT_RESULT_SUCCESS)){
			return FbmStatusConstant.HAS_DISCOUNT;
		}else{
			return FbmStatusConstant.HAS_DISABLE;
		}
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dealAccount(String pk_action, BusiActionParamVO<T> param)
			throws BusinessException {
		CommonDAO dao = new CommonDAO();
		dao.delAccountDetailByActionPK(pk_action);

		DiscountVO discountVO = param.getSuperVO();
		if(discountVO.getResult().equalsIgnoreCase(FbmBusConstant.DISCOUNT_RESULT_DISABLE)){
			String opbilltype = ((DiscountVO)param.getSuperVO()).getOpbilltype();
			//只有统管的票据才执行新增清算户。
			if(FbmBusConstant.BILL_UNISTORAGE.equals(opbilltype)){
				String pkAction = "";

				String pk_baseinfo = param.getPk_baseinfo();

				EndoreService endores = new EndoreService();
				ActionVO actionvo = endores.queryPK_Action(pk_baseinfo, FbmActionConstant.CENTERUSE);
				if(null==actionvo)
				{
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000127")/*@res "无法得到票据动作。"*/);
				}
				pkAction = actionvo.getPk_action();

				RegisterVO tmp = (RegisterVO)dao.getBaseDAO().retrieveByPK(RegisterVO.class, actionvo.getPk_source());
				if("".equals(pkAction))
				{
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000128")/*@res "无法得到pk_action　不能删除清算户"*/);
				}

				HYPubBO hypubbo = new HYPubBO();
				//新增清算户
				AccountDetailVO accDetail = new AccountDetailVO();

				accDetail.setPk_org(tmp.getHoldunit());// 调出的单位减少

				accDetail.setPk_baseinfo(param.getPk_baseinfo());
				accDetail.setPk_action(pkAction);
				accDetail.setMoneyy(param.getBaseinfoVO().getMoneyy().multiply(-1));
				accDetail.setPk_type(FbmBusConstant.ACCOUNT_TYPE_LIQUID);// A单位清算户减少
				accDetail.setReason(FbmBusConstant.ACCOUNT_REASON_CENTER_USE);
				accDetail.setIsliquid(UFBoolean.FALSE);// 是否清算，默认false
				accDetail.setLiquidationdate(param.getBaseinfoVO().getEnddate());// 清算日期

				accDetail.setPk_register(actionvo.getPk_source());// 设置pk_register

				dao.getBaseDAO().insertVO(accDetail);

			}
		}
	}



}