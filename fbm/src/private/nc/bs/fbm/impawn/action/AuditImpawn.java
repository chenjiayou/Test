package nc.bs.fbm.impawn.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;


/**
 *
 * 功能：
       质押审批
 * 日期：2007-10-10
 * 程序员：wues
 */
public class AuditImpawn<K extends ImpawnVO, T extends ImpawnVO> extends ActionImpawn<K, T> {
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
		//校验票据动作
		if(!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
				&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_IMPAWN)
				&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000252")/* @res"的前一操作必须是保存质押单。"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}
	/**
	 * 前置条件为在质押
	 */
	protected String getBeginStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.ON_IMPAWN;
	}

	/**
	 * 后置条件为已质押
	 */
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.HAS_IMPAWN;
	}

	/**
	 * 额外处理：
	 *  生成物权担保记录
	 */
	protected void afterAction(BusiActionParamVO<T>[] param) throws BusinessException {
		ImpawnVO impawnVO = param[0].getSuperVO();
		//向物权担保表中添加记录
		insertFiImpawn(impawnVO);
	}

	/**
	 * 插入物权担保
	 * @return
	 * @throws BusinessException
	 */
	private nc.vo.fi.impawn.ImpawnVO insertFiImpawn(ImpawnVO fbmImpawnVO) throws BusinessException {
		nc.vo.fi.impawn.ImpawnVO fiImpawnVO = null;
		//判断信贷系统是否启用
		if (isEnabled(fbmImpawnVO.getPk_corp(),ProductInfo.pro_CDM)) {
			fiImpawnVO = OuterProxy.getImpawnService().insertImpawn(changeVO(fbmImpawnVO));
		}
		return fiImpawnVO;
	}

	/**
	 * 帐户处理
	 */
	public void dealAccount(String pk_action, BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		CommonDAO dao = new CommonDAO();
		BaseinfoVO baseinfoVO = dao.queryBaseinfoByPK(param.getPk_baseinfo());

		AccountDetailVO[] vos = new AccountDetailVO[2];
		vos[0] = new AccountDetailVO();
		vos[0].setPk_org(param.getPk_org());
		vos[0].setMoneyy(baseinfoVO.getMoneyy().multiply(-1));
		vos[0].setPk_baseinfo(param.getPk_baseinfo());
		vos[0].setPk_action(pk_action);
		vos[0].setPk_type(FbmBusConstant.ACCOUNT_TYPE_LOCALKEEP);
		vos[0].setReason(FbmBusConstant.ACCOUNT_REASON_IMPAWN);

		vos[1] = new AccountDetailVO();
		vos[1].setPk_org(param.getPk_org());
		vos[1].setMoneyy(baseinfoVO.getMoneyy());
		vos[1].setPk_baseinfo(param.getPk_baseinfo());
		vos[1].setPk_action(pk_action);
		vos[1].setPk_type(FbmBusConstant.ACCOUNT_TYPE_IMPAWN_BANK);
		vos[1].setReason(FbmBusConstant.ACCOUNT_REASON_IMPAWN);

		dao.getBaseDAO().insertVOArray(vos);

	}
}