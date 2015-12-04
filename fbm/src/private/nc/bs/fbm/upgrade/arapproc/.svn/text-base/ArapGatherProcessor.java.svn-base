package nc.bs.fbm.upgrade.arapproc;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/**
 * 
 * 类功能说明：
     收票处理类
 * 日期：2007-11-20
 * 程序员： wues
 *
 */
public class ArapGatherProcessor extends ArapAbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		RegisterVO regVO = (RegisterVO)specialRebuildData(vo);

		HYPubBO bo = new HYPubBO();
		bo.insert(regVO);
		
		//保存收票
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, FbmActionConstant.SAVE).doAction(regVO, FbmActionConstant.SAVE,false);

		//审核收票
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, FbmActionConstant.AUDIT).doAction(regVO, FbmActionConstant.AUDIT,false);
	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		RegisterVO regVO = (RegisterVO)vo;
		//regVO.setSfflag(new UFBoolean(true));
		//regVO.setGathertype(FbmBusConstant.GATHER_TYPE_INPUT);
		return regVO;
	}

}
