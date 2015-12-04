package nc.bs.fbm.upgrade;

import javax.naming.NamingException;

import nc.bs.pub.DataManageObject;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billcodemanage.BillCodeObjValueVO;

/**
 *
 * 类功能说明：
     基本信息生成器：各业务单据的单据号、票据基本信息PK
 * 日期：2007-11-22
 * 程序员： wues
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
	 * 获取主键
	 * @return
	 * @throws NamingException
	 */
	public String getPK(){
		return super.getOID();
	}
	/**
	 * 取得单据编号
	 * @param billTypeCode
	 * @return
	 * @throws BusinessException
	 */
	public String getBillCodeNo(String billTypeCode) throws BusinessException {
		return this.getBillCodeNo(billTypeCode, null);
	}
	/**
	 * 取单据编号
	 * @param billTypeCode
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	public String getBillCodeNo(String billTypeCode, String pk_corp) throws BusinessException {
		return this.getBillCodeNo(billTypeCode, pk_corp, null, null);
	}

	/**
	 * 获的某单据类型的单据编号
	 * @param typeNo
	 * @return
	 * @throws BusinessException
	 */
	private String getBillCodeNo(String billTypeCode, String pk_corp, String customBillCode, BillCodeObjValueVO vo) throws BusinessException{
		try {
			return OuterProxy.getBillCodeRuleService().getBillCode_RequiresNew(billTypeCode, pk_corp, customBillCode, vo);
		} catch (Exception e) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000347")/* @res"获取单据号失败。"*/ + e.getMessage());
		}
	}
}