package nc.bs.fbm.upgrade.arapproc;

import nc.bs.fbm.upgrade.IDataProcessor;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/**
 * 
 * �๦��˵����
     Ʊ�ݻ�����Ϣ������
 * ���ڣ�2007-11-20
 * ����Ա�� wues
 *
 */
public class ArapBaseinfoProcessor implements IDataProcessor {

	
	public void buildData(SuperVO vo) throws BusinessException {
		HYPubBO bo = new HYPubBO();
		bo.insert(specialRebuildData(vo));
	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		//��������ֶι���������,�Ժ���
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
