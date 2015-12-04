package nc.bs.fbm.bankback.action;

import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

public class DeleteBankBack<K extends HYBillVO,T extends StorageVO> extends ActionBankBack<K, T> {

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
		return null;
	}
}