/**
 *
 */
package nc.bs.fbm.innerkeep.action;

import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

/**
 * <p>
 *
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-10-15
 *
 */
public class InputInnerKeep<K extends HYBillVO,T extends StorageVO> extends ActionInnerKeep<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
			//У��Ʊ�ݶ���
		if(!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_INNERKEEP)
					&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000266")/* @res"��ǰһ��������������ڲ���ŵ���"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.bs.fbm.pub.action.AbstractAction#getBeginStatus(nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		StorageVO vo = param.getSuperVO();
		String inputtype = vo.getInputtype();
		if(inputtype.equals(FbmBusConstant.KEEP_TYPE_STORE)){
			return FbmStatusConstant.ON_INNER_KEEP;
		}else {
			return FbmStatusConstant.ON_RELIEF_KEEP;
		}
	}

	/* (non-Javadoc)
	 * @see nc.bs.fbm.pub.action.AbstractAction#getEndStatus(nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		StorageVO vo = param.getSuperVO();
		String inputtype = vo.getInputtype();
		if(inputtype.equals(FbmBusConstant.KEEP_TYPE_STORE)){
			return FbmStatusConstant.HAS_INNER_KEEP;
		}else {
			return FbmStatusConstant.HAS_RELIEF_KEEP;
		}
	}

}