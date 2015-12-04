package nc.bs.fbm.upgrade.arapproc;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.HYBillVO;
/**
 * 
 * �๦��˵����
     ���鴦����
 * ���ڣ�2007-11-20
 * ����Ա�� wues
 *
 */
public class ArapEndoreProcessor extends ArapAbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		EndoreVO endoreVO = (EndoreVO)specialRebuildData(vo);
		HYPubBO bo = new HYPubBO();
		bo.insert(endoreVO);
		
		HYBillVO billVO = new HYBillVO();
		billVO.setParentVO(endoreVO);
		
		//����
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.SAVE).doAction(endoreVO, FbmActionConstant.SAVE,false);

		//���
		endoreVO.setUpgrade(EndoreVO.UPGRADE);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.AUDIT).doAction(endoreVO, FbmActionConstant.AUDIT,false);


	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		EndoreVO endoreVO = (EndoreVO)vo;
		if(endoreVO.getEndorsee() != null && endoreVO.getEndorsee().getBytes().length > 20){
			endoreVO.setEndorsee(null);
		}
		return endoreVO;
	}

}
