package nc.bs.fbm.upgrade.arapproc;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/**
 * 
 * �๦��˵����
     ��Ѻ������
 * ���ڣ�2007-11-20
 * ����Ա�� wues
 *
 */
public class ArapImpawnProcessor extends ArapAbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		ImpawnVO impawnVO = (ImpawnVO)vo;
		HYPubBO bo = new HYPubBO();
		bo.insert(impawnVO);

		//����
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.SAVE).doAction(impawnVO, FbmActionConstant.SAVE,false);

		//���
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.AUDIT).doAction(impawnVO, FbmActionConstant.AUDIT,false);

	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
