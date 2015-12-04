/**
 *
 */
package nc.bs.fbm.gather;

import javax.naming.NamingException;

import nc.bs.pub.DataManageObject;

/**
 * <p>
 * �տλ/�տλ�����˺�PK����
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-9-13
 *
 */
public class SpecialOIDCreater extends DataManageObject {

	/**
	 * @throws NamingException
	 */
	public SpecialOIDCreater() throws NamingException {
		
	}

	/**
	 * @param dbName
	 * @throws NamingException
	 */
	public SpecialOIDCreater(String dbName) throws NamingException {
		super(dbName);
	}
	
	public String getGatherUnitOIDs(){
		return getOID("UNIT");
	}
	
	public String getGatherBankOIDs(){
		return getOID("BANK");
	}

	public String getInvoiceUnitOIDs(){
		return getOID("KUNT");
	}
	
	public String getInvoiceBankOIDs(){
		return getOID("KBAK");
	}
}
