package nc.itf.fbm.action;

import nc.vo.pub.BusinessException;

/**
*
* <p>
* Ʊ�ݺ�̨���������ӿ�
* <p>
* �����ˣ�bsrl <b>���ڣ�2008-2-27
*
*/
public interface IBusiAction <K> {
  
	public void doAction(K data, String actioncode,boolean isNesting) throws BusinessException ;
	
}
