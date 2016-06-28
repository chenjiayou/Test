/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.vo.fbm.returnbill;

import java.util.ArrayList;

import nc.vo.pub.NullFieldException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;

/**
 * <b> �ڴ˴���Ҫ��������Ĺ��� </b>
 * 
 * <p>
 * ��Ʊ����VO
 * </p>
 * 
 * ��������:2007-8-30
 * 
 * @author author
 * @version Your Project 1.0
 */
public class ReturnVO extends SuperVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public String def5;
	public String pk_corp;
	public String vbillno;
	public String returnnote;
	public String returntype;
	public String returnperson;
	public Integer dr;
	public String def1;
	public String pk_return;
	public String voperatorid;
	public UFDate dreturndate;
	public String vapprovenote;
	public String pk_billtypecode;
	public UFDate busdate;
	public UFDate dapprovedate;
	public Integer vbillstatus;
	public String pk_org;
	public String vapproveid;
	public String def2;
	public UFDateTime ts;
	public String def4;
	public String def3;
	public UFDate doperatedate;
	public UFBoolean voucher;
	public UFDate dvoucherdate;
	public String vvoucherid;
	public UFBoolean unittally;
	public String unitplanitem;
	public String unititem;
	public UFBoolean writePlan;

	private UFBoolean writebankacc;

	private UFBoolean writeinneracc;

	public UFBoolean getWritebankacc() {
		return writebankacc;
	}

	public void setWritebankacc(UFBoolean writebankacc) {
		this.writebankacc = writebankacc;
	}

	public UFBoolean getWriteinneracc() {
		return writeinneracc;
	}

	public void setWriteinneracc(UFBoolean writeinneracc) {
		this.writeinneracc = writeinneracc;
	}

	public static final String DEF5 = "def5";
	public static final String PK_CORP = "pk_corp";
	public static final String VBILLNO = "vbillno";
	public static final String RETURNNOTE = "returnnote";
	public static final String RETURNTYPE = "returntype";
	public static final String RETURNPERSON = "returnperson";
	public static final String DR = "dr";
	public static final String DEF1 = "def1";
	public static final String PK_RETURN = "pk_return";
	public static final String VOPERATORID = "voperatorid";
	public static final String DRETURNDATE = "dreturndate";
	public static final String VAPPROVENOTE = "vapprovenote";
	public static final String PK_BILLTYPECODE = "pk_billtypecode";
	public static final String BUSDATE = "busdate";
	public static final String DAPPROVEDATE = "dapprovedate";
	public static final String VBILLSTATUS = "vbillstatus";
	public static final String PK_ORG = "pk_org";
	public static final String VAPPROVEID = "vapproveid";
	public static final String DEF2 = "def2";
	public static final String TS = "ts";
	public static final String DEF4 = "def4";
	public static final String DEF3 = "def3";
	public static final String DOPERATEDATE = "doperatedate";
	public static final String VOUCHER = "voucher";
	public static final String UNITTALLY = "unittally";
	public static final String DVOUCHERDATE = "dvoucherdate";
	public static final String VVOUCHERID = "vvoucherid";
	public static final String UNITPLANITEM = "unitplanitem";
	public static final String UNITITEM = "unititem";

	/**
	 * ����def5��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getDef5() {
		return def5;
	}

	/**
	 * ����def5��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newDef5
	 *            String
	 */
	public void setDef5(String newDef5) {

		def5 = newDef5;
	}

	/**
	 * ����pk_corp��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getPk_corp() {
		return pk_corp;
	}

	/**
	 * ����pk_corp��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newPk_corp
	 *            String
	 */
	public void setPk_corp(String newPk_corp) {

		pk_corp = newPk_corp;
	}

	/**
	 * ����vbillno��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getVbillno() {
		return vbillno;
	}

	/**
	 * ����vbillno��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newVbillno
	 *            String
	 */
	public void setVbillno(String newVbillno) {

		vbillno = newVbillno;
	}

	/**
	 * ����returnnote��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getReturnnote() {
		return returnnote;
	}

	/**
	 * ����returnnote��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newReturnnote
	 *            String
	 */
	public void setReturnnote(String newReturnnote) {

		returnnote = newReturnnote;
	}

	/**
	 * ����returntype��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getReturntype() {
		return returntype;
	}

	/**
	 * ����returntype��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newReturntype
	 *            String
	 */
	public void setReturntype(String newReturntype) {

		returntype = newReturntype;
	}

	/**
	 * ����returnperson��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getReturnperson() {
		return returnperson;
	}

	/**
	 * ����returnperson��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newReturnperson
	 *            String
	 */
	public void setReturnperson(String newReturnperson) {

		returnperson = newReturnperson;
	}

	/**
	 * ����dr��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return Integer
	 */
	public Integer getDr() {
		return dr;
	}

	/**
	 * ����dr��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newDr
	 *            Integer
	 */
	public void setDr(Integer newDr) {

		dr = newDr;
	}

	/**
	 * ����def1��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getDef1() {
		return def1;
	}

	/**
	 * ����def1��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newDef1
	 *            String
	 */
	public void setDef1(String newDef1) {

		def1 = newDef1;
	}

	/**
	 * ����pk_return��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getPk_return() {
		return pk_return;
	}

	/**
	 * ����pk_return��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newPk_return
	 *            String
	 */
	public void setPk_return(String newPk_return) {

		pk_return = newPk_return;
	}

	/**
	 * ����voperatorid��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getVoperatorid() {
		return voperatorid;
	}

	/**
	 * ����voperatorid��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newVoperatorid
	 *            String
	 */
	public void setVoperatorid(String newVoperatorid) {

		voperatorid = newVoperatorid;
	}

	/**
	 * ����dreturndate��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return UFDate
	 */
	public UFDate getDreturndate() {
		return dreturndate;
	}

	/**
	 * ����dreturndate��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newDreturndate
	 *            UFDate
	 */
	public void setDreturndate(UFDate newDreturndate) {

		dreturndate = newDreturndate;
	}

	/**
	 * ����vapprovenote��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getVapprovenote() {
		return vapprovenote;
	}

	/**
	 * ����vapprovenote��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newVapprovenote
	 *            String
	 */
	public void setVapprovenote(String newVapprovenote) {

		vapprovenote = newVapprovenote;
	}

	/**
	 * ����pk_billtypecode��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getPk_billtypecode() {
		return pk_billtypecode;
	}

	/**
	 * ����pk_billtypecode��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newPk_billtypecode
	 *            String
	 */
	public void setPk_billtypecode(String newPk_billtypecode) {

		pk_billtypecode = newPk_billtypecode;
	}

	/**
	 * ����busdate��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return UFDate
	 */
	public UFDate getBusdate() {
		return busdate;
	}

	/**
	 * ����busdate��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newBusdate
	 *            UFDate
	 */
	public void setBusdate(UFDate newBusdate) {

		busdate = newBusdate;
	}

	/**
	 * ����dapprovedate��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return UFDate
	 */
	public UFDate getDapprovedate() {
		return dapprovedate;
	}

	/**
	 * ����dapprovedate��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newDapprovedate
	 *            UFDate
	 */
	public void setDapprovedate(UFDate newDapprovedate) {

		dapprovedate = newDapprovedate;
	}

	/**
	 * ����vbillstatus��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return Integer
	 */
	public Integer getVbillstatus() {
		return vbillstatus;
	}

	/**
	 * ����vbillstatus��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newVbillstatus
	 *            Integer
	 */
	public void setVbillstatus(Integer newVbillstatus) {

		vbillstatus = newVbillstatus;
	}

	/**
	 * ����pk_org��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getPk_org() {
		return pk_org;
	}

	/**
	 * ����pk_org��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newPk_org
	 *            String
	 */
	public void setPk_org(String newPk_org) {

		pk_org = newPk_org;
	}

	/**
	 * ����vapproveid��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getVapproveid() {
		return vapproveid;
	}

	/**
	 * ����vapproveid��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newVapproveid
	 *            String
	 */
	public void setVapproveid(String newVapproveid) {

		vapproveid = newVapproveid;
	}

	/**
	 * ����def2��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getDef2() {
		return def2;
	}

	/**
	 * ����def2��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newDef2
	 *            String
	 */
	public void setDef2(String newDef2) {

		def2 = newDef2;
	}

	/**
	 * ����ts��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return UFDateTime
	 */
	public UFDateTime getTs() {
		return ts;
	}

	/**
	 * ����ts��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newTs
	 *            UFDateTime
	 */
	public void setTs(UFDateTime newTs) {

		ts = newTs;
	}

	/**
	 * ����def4��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getDef4() {
		return def4;
	}

	/**
	 * ����def4��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newDef4
	 *            String
	 */
	public void setDef4(String newDef4) {

		def4 = newDef4;
	}

	/**
	 * ����def3��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getDef3() {
		return def3;
	}

	/**
	 * ����def3��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newDef3
	 *            String
	 */
	public void setDef3(String newDef3) {

		def3 = newDef3;
	}

	/**
	 * ����doperatedate��Getter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return UFDate
	 */
	public UFDate getDoperatedate() {
		return doperatedate;
	}

	/**
	 * ����doperatedate��Setter����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newDoperatedate
	 *            UFDate
	 */
	public void setDoperatedate(UFDate newDoperatedate) {

		doperatedate = newDoperatedate;
	}

	/**
	 * ��֤���������֮��������߼���ȷ��.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @exception nc.vo.pub.ValidationException
	 *                �����֤ʧ��,�׳� ValidationException,�Դ�����н���.
	 */
	public void validate() throws ValidationException {

		ArrayList errFields = new ArrayList(); // errFields record those null

		// fields that cannot be null.
		// ����Ƿ�Ϊ�������յ��ֶθ��˿�ֵ,�������Ҫ�޸��������ʾ��Ϣ:

		if (pk_return == null) {
			errFields.add(new String("pk_return"));
		}

		StringBuffer message = new StringBuffer();
		message.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("362025", "UPP362025-000001")/*
																										 * @res
																										 * "�����ֶβ���Ϊ��:"
																										 */);
		if (errFields.size() > 0) {
			String[] temp = (String[]) errFields.toArray(new String[0]);
			message.append(temp[0]);
			for (int i = 1; i < temp.length; i++) {
				message.append(",");
				message.append(temp[i]);
			}
			throw new NullFieldException(message.toString());
		}
	}

	/**
	 * <p>
	 * ȡ�ø�VO�����ֶ�.
	 * <p>
	 * ��������:2007-8-30
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getParentPKFieldName() {

		return null;

	}

	/**
	 * <p>
	 * ȡ�ñ�����.
	 * <p>
	 * ��������:2007-8-30
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPKFieldName() {
		return "pk_return";
	}

	/**
	 * <p>
	 * ���ر�����.
	 * <p>
	 * ��������:2007-8-30
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {

		return "fbm_return";
	}

	/**
	 * ����Ĭ�Ϸ�ʽ����������.
	 * 
	 * ��������:2007-8-30
	 */
	public ReturnVO() {

		super();
	}

	/**
	 * ʹ���������г�ʼ���Ĺ�����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newPk_return
	 *            ����ֵ
	 */
	public ReturnVO(String newPk_return) {

		// Ϊ�����ֶθ�ֵ:
		pk_return = newPk_return;

	}

	/**
	 * ���ض����ʶ,����Ψһ��λ����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return String
	 */
	public String getPrimaryKey() {

		return pk_return;

	}

	/**
	 * ���ö����ʶ,����Ψһ��λ����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @param newPk_return
	 *            String
	 */
	public void setPrimaryKey(String newPk_return) {

		pk_return = newPk_return;

	}

	public UFDate getDvoucherdate() {
		return dvoucherdate;
	}

	public void setDvoucherdate(UFDate dvoucherdate) {
		this.dvoucherdate = dvoucherdate;
	}

	public String getVvoucherid() {
		return vvoucherid;
	}

	public void setVvoucherid(String vvoucherid) {
		this.vvoucherid = vvoucherid;
	}

	public UFBoolean getVoucher() {
		return voucher;
	}

	public void setVoucher(UFBoolean voucher) {
		this.voucher = voucher;
	}

	public UFBoolean getUnittally() {
		return unittally;
	}

	public void setUnittally(UFBoolean unittally) {
		this.unittally = unittally;
	}

	public String getUnitplanitem() {
		return unitplanitem;
	}

	public void setUnitplanitem(String unitplanitem) {
		this.unitplanitem = unitplanitem;
	}

	public String getUnititem() {
		return unititem;
	}

	public void setUnititem(String unititem) {
		this.unititem = unititem;
	}

	public UFBoolean getWritePlan() {
		return writePlan;
	}

	public void setWritePlan(UFBoolean writePlan) {
		this.writePlan = writePlan;
	}

	/**
	 * ������ֵ�������ʾ����.
	 * 
	 * ��������:2007-8-30
	 * 
	 * @return java.lang.String ������ֵ�������ʾ����.
	 */
	public String getEntityName() {

		return "fbm_return";

	}
}