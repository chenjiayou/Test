package nc.bs.fbm.upgrade.arapproc;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;

public class ArapImpawnBackProcessor  extends ArapAbstractDataProcessor{

	public void buildData(SuperVO vo) throws BusinessException {
		ImpawnVO impawnVO = (ImpawnVO)vo;
		HYPubBO bo = new HYPubBO();
		bo.insert(impawnVO);


		//保存
	BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.SAVE).doAction(impawnVO, FbmActionConstant.SAVE,false);
		
		//审核
	BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.AUDIT).doAction(impawnVO, FbmActionConstant.AUDIT,false);
		
		//质押回收
	BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.IMPAWNBACK).doAction(impawnVO, FbmActionConstant.IMPAWNBACK,false);

	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
