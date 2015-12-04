package nc.bs.fbm.invoice.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

public class AuditInvoice<K extends RegisterVO,T extends RegisterVO> extends ActionInvoice<K,T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
			//У��Ʊ�ݶ���
		if(!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_INVOICE)
					&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000255")/* @res"��ǰһ���������Ǳ��渶Ʊ�Ǽǵ���"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		RegisterVO vo = param.getSuperVO();
		if(vo.getSfflag().booleanValue()){
			return FbmStatusConstant.ON_PAYBILL;
		}
		return FbmStatusConstant.ON_INVOICE;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		RegisterVO vo = param.getSuperVO();
		if(vo.getSfflag().booleanValue()){
			return FbmStatusConstant.HAS_PAYBILL;
		}
		return FbmStatusConstant.HAS_INVOICE;
	}

	public void dealAccount(String pk_action, BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		CommonDAO dao = new CommonDAO();
		BaseinfoVO baseinfoVO = dao.queryBaseinfoByPK(param.getPk_baseinfo());

		//Ӧ����������
		AccountDetailVO[] vos = new AccountDetailVO[1];
		vos[0] = new AccountDetailVO();
		vos[0].setPk_org(param.getPk_org());
		vos[0].setMoneyy(baseinfoVO.getMoneyy());
		vos[0].setPk_baseinfo(param.getPk_baseinfo());
		vos[0].setPk_action(pk_action);
		vos[0].setPk_type(FbmBusConstant.ACCOUNT_TYPE_YF);
		vos[0].setReason(FbmBusConstant.ACCOUNT_REASON_INVOICE);
		dao.getBaseDAO().insertVOArray(vos);

	}
}
