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
 * 类功能说明：
     背书处理类
 * 日期：2007-11-20
 * 程序员： wues
 *
 */
public class ArapEndoreProcessor extends ArapAbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		EndoreVO endoreVO = (EndoreVO)specialRebuildData(vo);
		HYPubBO bo = new HYPubBO();
		bo.insert(endoreVO);
		
		HYBillVO billVO = new HYBillVO();
		billVO.setParentVO(endoreVO);
		
		//保存
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.SAVE).doAction(endoreVO, FbmActionConstant.SAVE,false);

		//审核
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
