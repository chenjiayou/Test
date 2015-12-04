package nc.bs.fbm.upgrade.arapproc;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/**
 * 
 * 类功能说明：
     托收办理类
 * 日期：2007-11-20
 * 程序员： wues
 *
 */
public class ArapCollectionProcessor extends ArapAbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		CollectionVO collectionVO = (CollectionVO)specialRebuildData(vo);
		HYPubBO bo = new HYPubBO();
		bo.insert(collectionVO);
		
		//保存
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.SAVE).doAction(collectionVO, FbmActionConstant.SAVE,false);

		//审核
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.AUDIT).doAction(collectionVO, FbmActionConstant.AUDIT,false);

		//办理
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.TRANSACT).doAction(collectionVO, FbmActionConstant.TRANSACT,false);
	
	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		//如果委托日期为空，那么返回托收日期
		CollectionVO collectionVO = (CollectionVO)vo;
		if(collectionVO.getDconsigndate() == null){
			collectionVO.setDconsigndate(collectionVO.getDcollectiondate());
		}
		//collectionVO.setVbillstatus(IFBMStatus.Transact);
		return collectionVO;
	}

}
