package nc.bs.fbm.upgrade.processor;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/**
 * 
 * 类功能说明：
     质押处理类
 * 日期：2007-11-20
 * 程序员： wues
 *
 */
public class ImpawnProcessor extends AbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		ImpawnVO impawnVO = (ImpawnVO)vo;
		HYPubBO bo = new HYPubBO();
		bo.insert(impawnVO);

		//保存
//		ActionParamVO[] params = DefaultParamAdapter.changeImpawnParam(billVO,FbmActionConstant.SAVE);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.SAVE).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.SAVE).doAction(impawnVO, FbmActionConstant.SAVE,false);

		//审核
//		params = DefaultParamAdapter.changeImpawnParam(billVO,FbmActionConstant.AUDIT);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.AUDIT).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.AUDIT).doAction(impawnVO, FbmActionConstant.AUDIT,false);

	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
