package nc.bs.fbm.upgrade;

import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/**
 * 
 * �๦��˵����
     ����ҵ����ͳһ�ӿ�
 * ���ڣ�2007-11-20
 * ����Ա�� wues
 *
 */
public interface IDataProcessor {
	
	public void buildData(SuperVO vo)throws BusinessException;
	
	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException;
}
