package nc.bs.fbm.upgrade.processor;

import nc.bs.fbm.upgrade.IDataProcessor;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/**
 * 
 * 类功能说明：
     票据基本信息处理类
 * 日期：2007-11-20
 * 程序员： wues
 *
 */
public class BaseinfoProcessor implements IDataProcessor {

	
	public void buildData(SuperVO vo) throws BusinessException {
		HYPubBO bo = new HYPubBO();
		bo.insert(specialRebuildData(vo));
	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		//解决部分字段过长的问题,稍后处理
		BaseinfoVO baseinfoVO = (BaseinfoVO)vo;
		if(baseinfoVO.getPaybankacc() != null && baseinfoVO.getPaybankacc().getBytes().length > 20){
			baseinfoVO.setPaybankacc(null);
		}
		if(baseinfoVO.getPayunit() != null && baseinfoVO.getPayunit().getBytes().length > 20){
			baseinfoVO.setPayunit(null);
		}
		return baseinfoVO;
	}

}
