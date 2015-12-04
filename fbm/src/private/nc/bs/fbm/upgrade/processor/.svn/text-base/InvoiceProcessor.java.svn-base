package nc.bs.fbm.upgrade.processor;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/**
 * 
 * 类功能说明：
     开票处理类
 * 日期：2007-11-20
 * 程序员： wues
 *
 */
public class InvoiceProcessor extends AbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		RegisterVO regVO = (RegisterVO)specialRebuildData(vo);
		HYPubBO bo = new HYPubBO();
		bo.insert(regVO);
	
		
		//保存
//		ActionParamVO[] params = DefaultParamAdapter.changeInvoice2Param(billVO,FbmActionConstant.SAVE);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INVOICE, FbmActionConstant.SAVE).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INVOICE, FbmActionConstant.SAVE).doAction(regVO, FbmActionConstant.SAVE,false);

		//审核
//		params = DefaultParamAdapter.changeInvoice2Param(billVO,FbmActionConstant.AUDIT);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INVOICE, FbmActionConstant.AUDIT).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INVOICE, FbmActionConstant.AUDIT).doAction(regVO, FbmActionConstant.AUDIT,false);
		
	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		RegisterVO regVO = (RegisterVO)vo;
		HYPubBO bo = new HYPubBO();
		BaseinfoVO baseinfo = (BaseinfoVO)bo.queryByPrimaryKey(BaseinfoVO.class, regVO.getPk_baseinfo());
		regVO.setInvoicedate(baseinfo.getInvoicedate());
		//regVO.setSfflag(new UFBoolean(true));
		return regVO;
	}

}
