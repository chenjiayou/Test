package nc.bs.fbm.upgrade;

import javax.naming.NamingException;

import nc.bs.pub.DataManageObject;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billcodemanage.BillCodeObjValueVO;

/**
 *
 * �๦��˵����
     ������Ϣ����������ҵ�񵥾ݵĵ��ݺš�Ʊ�ݻ�����ϢPK
 * ���ڣ�2007-11-22
 * ����Ա�� wues
 *
 */
public class BaseInfoGenerator extends DataManageObject{

	public BaseInfoGenerator() throws NamingException {
		super();
	}
	public BaseInfoGenerator(String dbName) throws NamingException {
		super(dbName);
	}
	/**
	 * ��ȡ����
	 * @return
	 * @throws NamingException
	 */
	public String getPK(){
		return super.getOID();
	}
	/**
	 * ȡ�õ��ݱ��
	 * @param billTypeCode
	 * @return
	 * @throws BusinessException
	 */
	public String getBillCodeNo(String billTypeCode) throws BusinessException {
		return this.getBillCodeNo(billTypeCode, null);
	}
	/**
	 * ȡ���ݱ��
	 * @param billTypeCode
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	public String getBillCodeNo(String billTypeCode, String pk_corp) throws BusinessException {
		return this.getBillCodeNo(billTypeCode, pk_corp, null, null);
	}

	/**
	 * ���ĳ�������͵ĵ��ݱ��
	 * @param typeNo
	 * @return
	 * @throws BusinessException
	 */
	private String getBillCodeNo(String billTypeCode, String pk_corp, String customBillCode, BillCodeObjValueVO vo) throws BusinessException{
		try {
			return OuterProxy.getBillCodeRuleService().getBillCode_RequiresNew(billTypeCode, pk_corp, customBillCode, vo);
		} catch (Exception e) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000347")/* @res"��ȡ���ݺ�ʧ�ܡ�"*/ + e.getMessage());
		}
	}
}