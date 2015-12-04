package nc.bs.fbm.upgrade.arapproc;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/**
 * 
 * �๦��˵����
     ���ִ�����
 * ���ڣ�2007-11-20
 * ����Ա�� wues
 *
 */
public class ArapDiscountProcessor extends ArapAbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		DiscountVO discountVO = (DiscountVO)vo;
		HYPubBO bo = new HYPubBO();
		bo.insert(discountVO);
		
		//����
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT, FbmActionConstant.SAVE).doAction(discountVO, FbmActionConstant.SAVE,false);

		//���
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT, FbmActionConstant.AUDIT).doAction(discountVO, FbmActionConstant.AUDIT,false);
	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
