package nc.itf.fbm.arap;

import nc.vo.pub.BusinessException;

/**
 * �����ո���ӱ�PK
 * ���ض�Ӧ��Ʊ��ҵ�񵥾ݽڵ���
 * @author xwq
 *
 */
public interface IFuncodeQuery {
	public String queryFuncode(String pk_bill_b) throws BusinessException;
}
