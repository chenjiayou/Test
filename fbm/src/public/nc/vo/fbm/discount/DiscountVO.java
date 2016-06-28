/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.vo.fbm.discount;

import java.util.ArrayList;

import nc.vo.pub.NullFieldException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

/**
 * <b> �ڴ˴���Ҫ��������Ĺ��� </b>
 * 
 * <p>
 * ����VO��
 * </p>
 * 
 * ��������:2007-8-22
 * 
 * @author author bsrl
 * @version Your Project 1.0
 */
public class DiscountVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String discountno;
	public String def5;
	public String pk_corp;
	public UFDate ddiscountdate;
	public UFDate applydate;
	public String pk_discount_app;
	public UFDouble discountinterest;
	public String voperatorid;
	public UFDouble brate;
	public String vapprovenote;
	public String discount_account;
	public UFDate dtransactdate;
	public Integer vbillstatus;
	public String pk_source;
	public String pk_org;
	public UFDouble frate;
	public String apply_reason;
	public UFDouble moneyf;
	public Integer ratedaynum;
	public String vbillno;
	public String vtransactorid;
	public String pk_baseinfo;
	public String def1;
	public String note;
	public UFDate dapprovedate;
	public String pk_billtypecode;
	public String failreason;
	public String vapproveid;
	public String def2;
	public UFDouble moneyy;
	public String pk_discount_bank;
	public String def4;
	public UFDouble moneyb;
	public String result;
	public String def3;
	public String discountyratescheme;
	public UFDate doperatedate;
	public UFDouble discountyrate;
	public String pk_discount;
	public Integer dr;
	public UFDateTime ts;
	public UFDouble discountcharge;
	public Integer discountdelaydaynum;
	public UFDouble interestmoneyf;
	public UFDouble interestmoneyb;
	public UFDouble chargemoneyf;
	public UFDouble chargemoneyb;
	// ��������:bill_privacy->˽�У�bill_unistor->ͳ��
	public String opbilltype;
	// ��֤��
	public String vvoucherid;
	// ��֤����
	public UFDate dvoucherdate;

	public String pjlx;
	public String cprmc;
	public String holderacc;
	public String holderbank;
	public String hpcdrmc;
	public String hpcdrkhh;
	public String hpcdryhzh;
	public String ybbz;
	public UFDouble hpje;
	public UFDate cprq;
	public UFDate dqrq;
	public UFDate sprq;
	public String pk_curr;

	private String fbmbillno; // Ʊ�ݱ��

	public String chargeitem;// ������֧����Ŀ
	public String interestitem;// ����Ϣ֧����Ŀ
	public String balanceitem;// �������������Ŀ
	public String chargeplanitem;// �����Ѽƻ���Ŀ
	public String interestplanitem;// ����Ϣ�ƻ���Ŀ
	public String balanceplanitem;// �������ƻ���Ŀ

	public UFBoolean unitvoucher;// ����֤
	public String fbmplanitem;// Ӧ��Ʊ��֧���ƻ���Ŀ
	public String fbmitem;// Ӧ��Ʊ��֧����Ŀ

	// public String fbmbillstatus;

	/**
	 * ��ʱ�����������Ƿ�Ҫ��д�ƻ�,true����д��null || false������д
	 */
	private UFBoolean writePlan;

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

	public static final String INTERESTMONEYF = "interestmoneyf";
	public static final String INTERESTMONEYB = "interestmoneyb";
	public static final String CHARGEMONEYF = "chargemoneyf";
	public static final String CHARGEMONEYB = "chargemoneyb";
	public static final String DISCOUNTNO = "discountno";
	public static final String DEF5 = "def5";
	public static final String PK_CORP = "pk_corp";
	public static final String DDISCOUNTDATE = "ddiscountdate";
	public static final String APPLY_DATE = "applydate";
	public static final String PK_DISCOUNT_APP = "pk_discount_app";
	public static final String DISCOUNTINTEREST = "discountinterest";
	public static final String VOPERATORID = "voperatorid";
	public static final String BRATE = "brate";
	public static final String VAPPROVENOTE = "vapprovenote";
	public static final String DISCOUNT_ACCOUNT = "discount_account";
	public static final String DTRANSACTDATE = "dtransactdate";
	public static final String VBILLSTATUS = "vbillstatus";
	public static final String PK_SOURCE = "pk_source";
	public static final String PK_ORG = "pk_org";
	public static final String FRATE = "frate";
	public static final String APPLY_REASON = "apply_reason";
	public static final String MONEYF = "moneyf";
	public static final String RATEDAYNUM = "ratedaynum";
	public static final String VBILLNO = "vbillno";
	public static final String VTRANSACTORID = "vtransactorid";
	public static final String PK_BASEINFO = "pk_baseinfo";
	public static final String DEF1 = "def1";
	public static final String NOTE = "note";
	public static final String DAPPROVEDATE = "dapprovedate";
	public static final String PK_BILLTYPECODE = "pk_billtypecode";
	public static final String FAILREASON = "failreason";
	public static final String VAPPROVEID = "vapproveid";
	public static final String DEF2 = "def2";
	public static final String MONEYY = "moneyy";
	public static final String PK_DISCOUNT_BANK = "pk_discount_bank";
	public static final String DEF4 = "def4";
	public static final String MONEYB = "moneyb";
	public static final String RESULT = "result";
	public static final String DEF3 = "def3";
	public static final String DISCOUNTYRATESCHEME = "discountyratescheme";
	public static final String DOPERATEDATE = "doperatedate";
	public static final String DISCOUNTYRATE = "discountyrate";
	public static final String PK_DISCOUNT = "pk_discount";

	public static final String PJLX = "pjlx";
	public static final String CPRMC = "cprmc";
	public static final String HOLDERACC = "holderacc";
	public static final String HOLDERBANK = "holderbank";
	public static final String HPCDRMC = "hpcdrmc";
	public static final String YBBZ = "ybbz";
	public static final String HPJE = "hpje";
	public static final String CPRQ = "cprq";
	public static final String DQRQ = "dqrq";
	public static final String SPRQ = "sprq";
	public static final String HPCDRKHH = "hpcdrkhh";
	public static final String HPCDRYHZH = "hpcdryhzh";
	public static final String DISCOUNTCHARGE = "discountcharge";
	public static final String DISCOUNTDELAYDAYNUM = "discountdelaydaynum";
	// public static final String FBMBILLSTATUS = "fbmbillstatus";
	public static final String OPBILLTYPE = "opbilltype";
	public static final String VVOUCHERID = "vvoucherid";
	public static final String DVOUCHERDATE = "dvoucherdate";

	public static final String PK_CURR = "pk_curr";

	public static final String FBMBILLNO = "fbmbillno"; // Ʊ�ݱ��

	public static final String CHARGEITEM = "chargeitem";// ������֧����Ŀ
	public static final String INTERESTITEM = "interestitem";// ����Ϣ֧����Ŀ

	private boolean isInnerDiscountPJ;// �Ƿ�����Ʊ��

	public static final String UNITVOUCHER = "unitvoucher";

	public static final String CHARGEPLANITEM = "chargeplanitem";

	public static final String INTERESTPLANITEM = "interestplanitem";

	public static final String BALANCEPLANITEM = "balanceplanitem";

	public static final String FBMPLANITEM = "fbmplanitem";

	public static final String FBMITEM = "fbmitem";

	/**
	 * @return the pk_curr
	 */
	public String getPk_curr() {
		return pk_curr;
	}

	/**
	 * @param pk_curr
	 *            the pk_curr to set
	 */
	public void setPk_curr(String pk_curr) {
		this.pk_curr = pk_curr;
	}

	public Integer getDiscountdelaydaynum() {
		return discountdelaydaynum;
	}

	public void setDiscountdelaydaynum(Integer discountdelaydaynum) {
		this.discountdelaydaynum = discountdelaydaynum;
	}

	/**
	 * ����def5��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getDef5() {
		return def5;
	}

	/**
	 * ����def5��Setter����.
	 * 
	 * ��������:2007-8-22
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
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getPk_corp() {
		return pk_corp;
	}

	/**
	 * ����pk_corp��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newPk_corp
	 *            String
	 */
	public void setPk_corp(String newPk_corp) {

		pk_corp = newPk_corp;
	}

	/**
	 * ����ddiscountdate��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return UFDate
	 */
	public UFDate getDdiscountdate() {
		return ddiscountdate;
	}

	/**
	 * ����ddiscountdate��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newDdiscountdate
	 *            UFDate
	 */
	public void setDdiscountdate(UFDate newDdiscountdate) {

		ddiscountdate = newDdiscountdate;
	}

	/**
	 * ����apply_date��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return UFDate
	 */
	public UFDate getApplydate() {
		return applydate;
	}

	/**
	 * ����apply_date��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newApply_date
	 *            UFDate
	 */
	public void setApplydate(UFDate newApplydate) {

		applydate = newApplydate;
	}

	/**
	 * ����pk_discount_app��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getPk_discount_app() {
		return pk_discount_app;
	}

	/**
	 * ����pk_discount_app��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newPk_discount_app
	 *            String
	 */
	public void setPk_discount_app(String newPk_discount_app) {

		pk_discount_app = newPk_discount_app;
	}

	/**
	 * ����discountinterest��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return UFDouble
	 */
	public UFDouble getDiscountinterest() {
		return discountinterest;
	}

	/**
	 * ����discountinterest��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newDiscountinterest
	 *            UFDouble
	 */
	public void setDiscountinterest(UFDouble newDiscountinterest) {

		discountinterest = newDiscountinterest;
	}

	/**
	 * ����voperatorid��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getVoperatorid() {
		return voperatorid;
	}

	/**
	 * ����voperatorid��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newVoperatorid
	 *            String
	 */
	public void setVoperatorid(String newVoperatorid) {

		voperatorid = newVoperatorid;
	}

	/**
	 * ����brate��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return UFDouble
	 */
	public UFDouble getBrate() {
		return brate;
	}

	/**
	 * ����brate��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newBrate
	 *            UFDouble
	 */
	public void setBrate(UFDouble newBrate) {

		brate = newBrate;
	}

	/**
	 * ����vapprovenote��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getVapprovenote() {
		return vapprovenote;
	}

	/**
	 * ����vapprovenote��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newVapprovenote
	 *            String
	 */
	public void setVapprovenote(String newVapprovenote) {

		vapprovenote = newVapprovenote;
	}

	/**
	 * ����discount_account��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getDiscount_account() {
		return discount_account;
	}

	/**
	 * ����discount_account��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newDiscount_account
	 *            String
	 */
	public void setDiscount_account(String newDiscount_account) {

		discount_account = newDiscount_account;
	}

	/**
	 * ����dtransactdate��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return UFDate
	 */
	public UFDate getDtransactdate() {
		return dtransactdate;
	}

	/**
	 * ����dtransactdate��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newDtransactdate
	 *            UFDate
	 */
	public void setDtransactdate(UFDate newDtransactdate) {

		dtransactdate = newDtransactdate;
	}

	/**
	 * ����vbillstatus��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return Integer
	 */
	public Integer getVbillstatus() {
		return vbillstatus;
	}

	/**
	 * ����vbillstatus��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newVbillstatus
	 *            Integer
	 */
	public void setVbillstatus(Integer newVbillstatus) {

		vbillstatus = newVbillstatus;
	}

	/**
	 * ����pk_source��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getPk_source() {
		return pk_source;
	}

	/**
	 * ����pk_source��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newPk_source
	 *            String
	 */
	public void setPk_source(String newPk_source) {

		pk_source = newPk_source;
	}

	/**
	 * ����pk_org��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getPk_org() {
		return pk_org;
	}

	/**
	 * ����pk_org��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newPk_org
	 *            String
	 */
	public void setPk_org(String newPk_org) {

		pk_org = newPk_org;
	}

	/**
	 * ����frate��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return UFDouble
	 */
	public UFDouble getFrate() {
		return frate;
	}

	/**
	 * ����frate��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newFrate
	 *            UFDouble
	 */
	public void setFrate(UFDouble newFrate) {

		frate = newFrate;
	}

	/**
	 * ����apply_reason��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getApply_reason() {
		return apply_reason;
	}

	/**
	 * ����apply_reason��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newApply_reason
	 *            String
	 */
	public void setApply_reason(String newApply_reason) {

		apply_reason = newApply_reason;
	}

	/**
	 * ����moneyf��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return UFDouble
	 */
	public UFDouble getMoneyf() {
		return moneyf;
	}

	/**
	 * ����moneyf��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newMoneyf
	 *            UFDouble
	 */
	public void setMoneyf(UFDouble newMoneyf) {

		moneyf = newMoneyf;
	}

	/**
	 * ����ratedaynum��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return Integer
	 */
	public Integer getRatedaynum() {
		return ratedaynum;
	}

	/**
	 * ����ratedaynum��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newRatedaynum
	 *            Integer
	 */
	public void setRatedaynum(Integer newRatedaynum) {

		ratedaynum = newRatedaynum;
	}

	/**
	 * ����vbillno��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getVbillno() {
		return vbillno;
	}

	/**
	 * ����vbillno��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newVbillno
	 *            String
	 */
	public void setVbillno(String newVbillno) {

		vbillno = newVbillno;
	}

	/**
	 * ����vtransactorid��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getVtransactorid() {
		return vtransactorid;
	}

	/**
	 * ����vtransactorid��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newVtransactorid
	 *            String
	 */
	public void setVtransactorid(String newVtransactorid) {

		vtransactorid = newVtransactorid;
	}

	/**
	 * ����pk_baseinfo��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getPk_baseinfo() {
		return pk_baseinfo;
	}

	/**
	 * ����pk_baseinfo��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newPk_baseinfo
	 *            String
	 */
	public void setPk_baseinfo(String newPk_baseinfo) {

		pk_baseinfo = newPk_baseinfo;
	}

	/**
	 * ����def1��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getDef1() {
		return def1;
	}

	/**
	 * ����def1��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newDef1
	 *            String
	 */
	public void setDef1(String newDef1) {

		def1 = newDef1;
	}

	/**
	 * ����note��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getNote() {
		return note;
	}

	/**
	 * ����note��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newNote
	 *            String
	 */
	public void setNote(String newNote) {

		note = newNote;
	}

	/**
	 * ����dapprovedate��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return UFDate
	 */
	public UFDate getDapprovedate() {
		return dapprovedate;
	}

	/**
	 * ����dapprovedate��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newDapprovedate
	 *            UFDate
	 */
	public void setDapprovedate(UFDate newDapprovedate) {

		dapprovedate = newDapprovedate;
	}

	/**
	 * ����pk_billtypecode��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getPk_billtypecode() {
		return pk_billtypecode;
	}

	/**
	 * ����pk_billtypecode��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newPk_billtypecode
	 *            String
	 */
	public void setPk_billtypecode(String newPk_billtypecode) {

		pk_billtypecode = newPk_billtypecode;
	}

	/**
	 * ����failreason��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getFailreason() {
		return failreason;
	}

	/**
	 * ����failreason��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newFailreason
	 *            String
	 */
	public void setFailreason(String newFailreason) {

		failreason = newFailreason;
	}

	/**
	 * ����vapproveid��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getVapproveid() {
		return vapproveid;
	}

	/**
	 * ����vapproveid��Setter����.
	 * 
	 * ��������:2007-8-22
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
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getDef2() {
		return def2;
	}

	/**
	 * ����def2��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newDef2
	 *            String
	 */
	public void setDef2(String newDef2) {

		def2 = newDef2;
	}

	/**
	 * ����moneyy��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return UFDouble
	 */
	public UFDouble getMoneyy() {
		return moneyy;
	}

	/**
	 * ����moneyy��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newMoneyy
	 *            UFDouble
	 */
	public void setMoneyy(UFDouble newMoneyy) {

		moneyy = newMoneyy;
	}

	public String getPk_discount_bank() {
		return pk_discount_bank;
	}

	public void setPk_discount_bank(String pk_discount_bank) {
		this.pk_discount_bank = pk_discount_bank;
	}

	/**
	 * ����def4��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getDef4() {
		return def4;
	}

	/**
	 * ����def4��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newDef4
	 *            String
	 */
	public void setDef4(String newDef4) {

		def4 = newDef4;
	}

	/**
	 * ����moneyb��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return UFDouble
	 */
	public UFDouble getMoneyb() {
		return moneyb;
	}

	/**
	 * ����moneyb��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newMoneyb
	 *            UFDouble
	 */
	public void setMoneyb(UFDouble newMoneyb) {

		moneyb = newMoneyb;
	}

	/**
	 * ����def3��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getDef3() {
		return def3;
	}

	/**
	 * ����def3��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newDef3
	 *            String
	 */
	public void setDef3(String newDef3) {

		def3 = newDef3;
	}

	/**
	 * ����discountyratescheme��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getDiscountyratescheme() {
		return discountyratescheme;
	}

	/**
	 * ����discountyratescheme��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newDiscountyratescheme
	 *            String
	 */
	public void setDiscountyratescheme(String newDiscountyratescheme) {

		discountyratescheme = newDiscountyratescheme;
	}

	/**
	 * ����doperatedate��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return UFDate
	 */
	public UFDate getDoperatedate() {
		return doperatedate;
	}

	/**
	 * ����doperatedate��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newDoperatedate
	 *            UFDate
	 */
	public void setDoperatedate(UFDate newDoperatedate) {

		doperatedate = newDoperatedate;
	}

	/**
	 * ����discountyrate��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return UFDouble
	 */
	public UFDouble getDiscountyrate() {
		return discountyrate;
	}

	/**
	 * ����discountyrate��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newDiscountyrate
	 *            UFDouble
	 */
	public void setDiscountyrate(UFDouble newDiscountyrate) {

		discountyrate = newDiscountyrate;
	}

	/**
	 * ����pk_discount��Getter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getPk_discount() {
		return pk_discount;
	}

	/**
	 * ����pk_discount��Setter����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newPk_discount
	 *            String
	 */
	public void setPk_discount(String newPk_discount) {

		pk_discount = newPk_discount;
	}

	/**
	 * ��֤���������֮��������߼���ȷ��.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @exception nc.vo.pub.ValidationException
	 *                �����֤ʧ��,�׳� ValidationException,�Դ�����н���.
	 */
	public void validate() throws ValidationException {

		ArrayList errFields = new ArrayList(); // errFields record those null

		// fields that cannot be null.
		// ����Ƿ�Ϊ�������յ��ֶθ��˿�ֵ,�������Ҫ�޸��������ʾ��Ϣ:

		if (pk_discount == null) {
			errFields.add(new String("pk_discount"));
		}

		StringBuffer message = new StringBuffer();
		message.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201030", "UPP36201030-000009")/*
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
	 * ��������:2007-8-22
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
	 * ��������:2007-8-22
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPKFieldName() {
		return "pk_discount";
	}

	/**
	 * <p>
	 * ���ر�����.
	 * <p>
	 * ��������:2007-8-22
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {

		return "fbm_discount";
	}

	/**
	 * ����Ĭ�Ϸ�ʽ����������.
	 * 
	 * ��������:2007-8-22
	 */
	public DiscountVO() {

		super();
	}

	/**
	 * ʹ���������г�ʼ���Ĺ�����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newPk_discount
	 *            ����ֵ
	 */
	public DiscountVO(String newPk_discount) {

		// Ϊ�����ֶθ�ֵ:
		pk_discount = newPk_discount;

	}

	/**
	 * ���ض����ʶ,����Ψһ��λ����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return String
	 */
	public String getPrimaryKey() {

		return pk_discount;

	}

	/**
	 * ���ö����ʶ,����Ψһ��λ����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @param newPk_discount
	 *            String
	 */
	public void setPrimaryKey(String newPk_discount) {

		pk_discount = newPk_discount;

	}

	/**
	 * ������ֵ�������ʾ����.
	 * 
	 * ��������:2007-8-22
	 * 
	 * @return java.lang.String ������ֵ�������ʾ����.
	 */
	public String getEntityName() {

		return "fbm_discount";

	}

	public String getPjlx() {
		return pjlx;
	}

	public void setPjlx(String pjlx) {
		this.pjlx = pjlx;
	}

	public String getCprmc() {
		return cprmc;
	}

	public void setCprmc(String cprmc) {
		this.cprmc = cprmc;
	}

	public String getHpcdrmc() {
		return hpcdrmc;
	}

	public void setHpcdrmc(String hpcdrmc) {
		this.hpcdrmc = hpcdrmc;
	}

	public String getYbbz() {
		return ybbz;
	}

	public void setYbbz(String ybbz) {
		this.ybbz = ybbz;
	}

	public UFDouble getHpje() {
		return hpje;
	}

	public void setHpje(UFDouble hpje) {
		this.hpje = hpje;
	}

	public UFDate getCprq() {
		return cprq;
	}

	public void setCprq(UFDate cprq) {
		this.cprq = cprq;
	}

	public UFDate getDqrq() {
		return dqrq;
	}

	public void setDqrq(UFDate dqrq) {
		this.dqrq = dqrq;
	}

	public UFDate getSprq() {
		return sprq;
	}

	public void setSprq(UFDate sprq) {
		this.sprq = sprq;
	}

	public String getHpcdrkhh() {
		return hpcdrkhh;
	}

	public void setHpcdrkhh(String hpcdrkhh) {
		this.hpcdrkhh = hpcdrkhh;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public UFDateTime getTs() {
		return ts;
	}

	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	public void setAttributeValue(String attributeName, Object value) {
		if (attributeName.equals(VBILLSTATUS)) {
			if (value != null)
				setVbillstatus(new Integer(value.toString()));
		} else if (attributeName.equals(DDISCOUNTDATE)) {
			if (value != null)
				setDdiscountdate(new UFDate(value.toString()));
		} else if (attributeName.equals(APPLY_DATE)) {
			if (value != null)
				setApplydate(new UFDate(value.toString()));
		} else if (attributeName.equals(CPRQ)) {
			if (value != null)
				setCprq(new UFDate(value.toString()));
		} else if (attributeName.equals(DQRQ)) {
			if (value != null)
				setDqrq(new UFDate(value.toString()));
		} else if (attributeName.equals(SPRQ)) {
			if (value != null)
				setSprq(new UFDate(value.toString()));
		} else {
			super.setAttributeValue(attributeName, value);
		}
	}

	public String getOpbilltype() {
		return opbilltype;
	}

	public void setOpbilltype(String opbilltype) {
		this.opbilltype = opbilltype;
	}

	public String getHolderacc() {
		return holderacc;
	}

	public void setHolderacc(String holderacc) {
		this.holderacc = holderacc;
	}

	public String getHolderbank() {
		return holderbank;
	}

	public void setHolderbank(String holderbank) {
		this.holderbank = holderbank;
	}

	public String getHpcdryhzh() {
		return hpcdryhzh;
	}

	public void setHpcdryhzh(String hpcdryhzh) {
		this.hpcdryhzh = hpcdryhzh;
	}

	public String getDiscountno() {
		return discountno;
	}

	public void setDiscountno(String discountno) {
		this.discountno = discountno;
	}

	public UFDouble getDiscountcharge() {
		return discountcharge;
	}

	public void setDiscountcharge(UFDouble discountcharge) {
		this.discountcharge = discountcharge;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public UFDouble getInterestmoneyf() {
		return interestmoneyf;
	}

	public void setInterestmoneyf(UFDouble interestmoneyf) {
		this.interestmoneyf = interestmoneyf;
	}

	public UFDouble getInterestmoneyb() {
		return interestmoneyb;
	}

	public void setInterestmoneyb(UFDouble interestmoneyb) {
		this.interestmoneyb = interestmoneyb;
	}

	public UFDouble getChargemoneyf() {
		return chargemoneyf;
	}

	public void setChargemoneyf(UFDouble chargemoneyf) {
		this.chargemoneyf = chargemoneyf;
	}

	public UFDouble getChargemoneyb() {
		return chargemoneyb;
	}

	public String getVvoucherid() {
		return vvoucherid;
	}

	public void setVvoucherid(String vvoucherid) {
		this.vvoucherid = vvoucherid;
	}

	public UFDate getDvoucherdate() {
		return dvoucherdate;
	}

	public void setDvoucherdate(UFDate dvoucherdate) {
		this.dvoucherdate = dvoucherdate;
	}

	public void setChargemoneyb(UFDouble chargemoneyb) {
		this.chargemoneyb = chargemoneyb;
	}

	public String getFbmbillno() {
		return fbmbillno;
	}

	public void setFbmbillno(String fbmbillno) {
		this.fbmbillno = fbmbillno;
	}

	public boolean isInnerDiscountPJ() {
		return isInnerDiscountPJ;
	}

	public void setInnerDiscountPJ(boolean isInnerDiscountPJ) {
		this.isInnerDiscountPJ = isInnerDiscountPJ;
	}

	public String getChargeitem() {
		return chargeitem;
	}

	public void setChargeitem(String chargeitem) {
		this.chargeitem = chargeitem;
	}

	public String getInterestitem() {
		return interestitem;
	}

	public void setInterestitem(String interestitem) {
		this.interestitem = interestitem;
	}

	public UFBoolean getUnitvoucher() {
		return unitvoucher;
	}

	public void setUnitvoucher(UFBoolean unitvoucher) {
		this.unitvoucher = unitvoucher;
	}

	public String getBalanceitem() {
		return balanceitem;
	}

	public void setBalanceitem(String balanceitem) {
		this.balanceitem = balanceitem;
	}

	public String getChargeplanitem() {
		return chargeplanitem;
	}

	public void setChargeplanitem(String chargeplanitem) {
		this.chargeplanitem = chargeplanitem;
	}

	public String getInterestplanitem() {
		return interestplanitem;
	}

	public void setInterestplanitem(String interestplanitem) {
		this.interestplanitem = interestplanitem;
	}

	public String getBalanceplanitem() {
		return balanceplanitem;
	}

	public void setBalanceplanitem(String balanceplanitem) {
		this.balanceplanitem = balanceplanitem;
	}

	public UFBoolean getWritePlan() {
		return writePlan;
	}

	public void setWritePlan(UFBoolean writePlan) {
		this.writePlan = writePlan;
	}

	public String getFbmplanitem() {
		return fbmplanitem;
	}

	public void setFbmplanitem(String fbmplanitem) {
		this.fbmplanitem = fbmplanitem;
	}

	public String getFbmitem() {
		return fbmitem;
	}

	public void setFbmitem(String fbmitem) {
		this.fbmitem = fbmitem;
	}
}