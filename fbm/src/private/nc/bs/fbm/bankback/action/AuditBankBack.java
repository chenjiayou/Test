package nc.bs.fbm.bankback.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

public class AuditBankBack<K extends HYBillVO,T extends StorageVO> extends ActionBankBack<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
		//У��Ʊ�ݶ���
		if(!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
				&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_BANKBACK)
				&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000246")/* @res"��ǰһ���������Ǳ����������õ���"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
			return FbmStatusConstant.ON_BANK_BACK;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.REGISTER;

	}

	public void dealAccount(String pk_action, BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		CommonDAO dao = new CommonDAO();
		BaseinfoVO baseinfoVO = dao.queryBaseinfoByPK(param.getPk_baseinfo());

		//���ش�Ż����ӣ����Ĵ�ż���
		AccountDetailVO[] vos = new AccountDetailVO[2];
		vos[0] = new AccountDetailVO();
		vos[0].setPk_org(param.getPk_org());
		vos[0].setMoneyy(baseinfoVO.getMoneyy());
		vos[0].setPk_baseinfo(param.getPk_baseinfo());
		vos[0].setPk_action(pk_action);
		vos[0].setPk_type(FbmBusConstant.ACCOUNT_TYPE_LOCALKEEP);
		vos[0].setReason(FbmBusConstant.ACCOUNT_REASON_BANKBACK);

		vos[1] = new AccountDetailVO();
		vos[1] = new AccountDetailVO();
		vos[1].setPk_org(param.getPk_org());
		vos[1].setMoneyy(baseinfoVO.getMoneyy().multiply(-1));
		vos[1].setPk_baseinfo(param.getPk_baseinfo());
		vos[1].setPk_action(pk_action);
		vos[1].setPk_type(FbmBusConstant.ACCOUNT_TYPE_BANKKEEP);
		vos[1].setReason(FbmBusConstant.ACCOUNT_REASON_BANKBACK);


		dao.getBaseDAO().insertVOArray(vos);
	}
}