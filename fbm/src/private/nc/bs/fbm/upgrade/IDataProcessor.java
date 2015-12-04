package nc.bs.fbm.upgrade;

import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/**
 * 
 * 类功能说明：
     各项业务处理统一接口
 * 日期：2007-11-20
 * 程序员： wues
 *
 */
public interface IDataProcessor {
	
	public void buildData(SuperVO vo)throws BusinessException;
	
	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException;
}
