package nc.bs.fbm.upgrade.processor;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.HYBillVO;
/**
 * 
 * 类功能说明：
     承兑处理类
 * 日期：2007-11-20
 * 程序员： wues
 *
 */
public class AcceptProcessor extends AbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		//保存承兑付款单
		AcceptVO acceptVO = (AcceptVO)specialRebuildData(vo);
		HYPubBO bo = new HYPubBO();
		bo.insert(acceptVO);
		
		HYBillVO billVO = new HYBillVO();
		billVO.setParentVO(acceptVO);
		
		//保存
//		ActionParamVO[] params = DefaultParamAdapter.changeAccept2Param(billVO,FbmActionConstant.SAVE);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BILLPAY, FbmActionConstant.SAVE).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BILLPAY, FbmActionConstant.SAVE).doAction(acceptVO, FbmActionConstant.SAVE,false);

		//审核
//		params = DefaultParamAdapter.changeAccept2Param(billVO,FbmActionConstant.AUDIT);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BILLPAY, FbmActionConstant.AUDIT).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BILLPAY, FbmActionConstant.AUDIT).doAction(acceptVO, FbmActionConstant.AUDIT,false);
	
	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		AcceptVO acceptVO = (AcceptVO)vo;
		acceptVO.setPk_billtypecode(FbmBusConstant.BILLTYPE_BILLPAY);
		return acceptVO;
	}

}
