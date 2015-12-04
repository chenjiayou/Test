package nc.bs.fbm.accpet.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;

public class AuditAccept<K extends AcceptVO, T extends AcceptVO> extends ActionAccept<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
//		String commonError = commonCheck(param);
//		if(commonError != null)return commonError;
//		
//		ActionVO actionVO = param.getLastActionVO();
//		//校验票据动作
//		if(!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
//				&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_BILLPAY)
//				&& actionVO.getPk_bill().equals(param.getPk_bill()))){
//			return "票据" + param.getBaseinfoVO().getFbmbillno() + "的前一操作必须是保存票据付款单。"+ MessageTranslator.translateAction(param);
//		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.ON_PAY;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.HAS_PAY;
	}

	public void dealAccount(String pk_action, BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		CommonDAO dao = new CommonDAO();
		BaseinfoVO baseinfoVO = dao.queryBaseinfoByPK(param.getPk_baseinfo());
		
		//应付存量减少
		AccountDetailVO[] vos = new AccountDetailVO[1]; 
		vos[0] = new AccountDetailVO();
		vos[0].setPk_org(param.getPk_org());
		vos[0].setMoneyy(baseinfoVO.getMoneyy().multiply(-1));
		vos[0].setPk_baseinfo(param.getPk_baseinfo());
		vos[0].setPk_action(pk_action);
		vos[0].setPk_type(FbmBusConstant.ACCOUNT_TYPE_YF);
		vos[0].setReason(FbmBusConstant.ACCOUNT_REASON_ACCEPT);
		dao.getBaseDAO().insertVOArray(vos);
		
	}
}
