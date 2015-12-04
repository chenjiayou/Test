package nc.vo.fbm.relief;

import java.util.ArrayList;

import nc.vo.pub.NullFieldException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class ReliefBVO extends SuperVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String TABLECODE = "fbm_relief_b";

	public String pk_relief_b;
	public String pk_source;
	public String pk_baseinfo;
	public String pk_relief;
	public UFDateTime ts;
	public Integer dr;

	// baseinfo字段
	public String fbmbillno;
	public UFDouble moneyy;
	public UFDate enddate;
	public String invoiceunit;
	public String receiveunit;
	public String payunit;
	public String holdunit;
	public String paybillunit;
	public String pk_curr;


	  public UFDouble brate;
	  public UFDouble frate;
	  public UFDouble moneyf;
	  public UFDouble moneyb;
	  public String fbmbilltype;//票据类型

	// 公式里要用到的字段
	private String payunitname;
	private String paybankaccname;
	private String receiveunitname;
	private String receivebankaccname;
	private String invoiceunitname;
	private String keepplacename;
	private String paybillunitname;
	private String holdunitname;
	private String currtypename;

	public String getCurrtypename() {
		return currtypename;
	}

	public void setCurrtypename(String currtypename) {
		this.currtypename = currtypename;
	}

	public String getHoldunitname() {
		return holdunitname;
	}

	public void setHoldunitname(String holdunitname) {
		this.holdunitname = holdunitname;
	}

	public String getPayunitname() {
		return payunitname;
	}

	public void setPayunitname(String payunitname) {
		this.payunitname = payunitname;
	}

	public String getPaybankaccname() {
		return paybankaccname;
	}

	public void setPaybankaccname(String paybankaccname) {
		this.paybankaccname = paybankaccname;
	}

	public String getReceiveunitname() {
		return receiveunitname;
	}

	public void setReceiveunitname(String receiveunitname) {
		this.receiveunitname = receiveunitname;
	}

		public String getReceivebankaccname() {
		return receivebankaccname;
	}

	public void setReceivebankaccname(String receivebankaccname) {
		this.receivebankaccname = receivebankaccname;
	}

	public String getInvoiceunitname() {
		return invoiceunitname;
	}

	public void setInvoiceunitname(String invoiceunitname) {
		this.invoiceunitname = invoiceunitname;
	}

	public String getKeepplacename() {
		return keepplacename;
	}

	public void setKeepplacename(String keepplacename) {
		this.keepplacename = keepplacename;
	}

	public String getPaybillunitname() {
		return paybillunitname;
	}

	public void setPaybillunitname(String paybillunitname) {
		this.paybillunitname = paybillunitname;
	}
		

	  public static final String BRATE="brate";
	  public static final String FRATE="frate";
	  public static final String MONEYF="moneyf";
	  public static final String MONEYB="moneyb";


	  public static final String FBMBILLTYPE = "fbmbilltype";
    public static final String FBMBILLNO = "fbmbillno";
	public static final String PK_RELIEF_B = "pk_relief_b";
	public static final String PK_SOURCE = "pk_source";
	public static final String PK_RELIEF = "pk_relief";
	public static final String TS = "ts";
	public static final String DR = "dr";
	public static final String PK_BASEINFO = "pk_baseinfo";

	public static final String MONEYY = "moneyy";
	public static final String ENDDATE = "enddate";
	public static final String INVOICEUNIT = "invoiceunit";
	public static final String RECEIVEUNIT = "receiveunit";
	public static final String PAYUNIT = "payunit";
	public static final String HOLDUNIT = "holdunit";
	public static final String PAYBILLUNIT = "paybillunit";
	public static final String PK_CURR = "pk_curr";



	/**
	 * @return the fbmbilltype
	 */
	public String getFbmbilltype() {
		return fbmbilltype;
	}

	/**
	 * @param fbmbilltype the fbmbilltype to set
	 */
	public void setFbmbilltype(String fbmbilltype) {
		this.fbmbilltype = fbmbilltype;
	}

	/**
	 * @return the brate
	 */
	public UFDouble getBrate() {
		return brate;
	}

	/**
	 * @param brate the brate to set
	 */
	public void setBrate(UFDouble brate) {
		this.brate = brate;
	}

	/**
	 * @return the frate
	 */
	public UFDouble getFrate() {
		return frate;
	}

	/**
	 * @param frate the frate to set
	 */
	public void setFrate(UFDouble frate) {
		this.frate = frate;
	}

	/**
	 * @return the moneyf
	 */
	public UFDouble getMoneyf() {
		return moneyf;
	}

	/**
	 * @param moneyf the moneyf to set
	 */
	public void setMoneyf(UFDouble moneyf) {
		this.moneyf = moneyf;
	}

	/**
	 * @return the moneyb
	 */
	public UFDouble getMoneyb() {
		return moneyb;
	}

	/**
	 * @param moneyb the moneyb to set
	 */
	public void setMoneyb(UFDouble moneyb) {
		this.moneyb = moneyb;
	}

	public String getFbmbillno() {
		return fbmbillno;
	}

	public void setFbmbillno(String fbmbillno) {
		this.fbmbillno = fbmbillno;
	}

	public String getPk_relief_b() {
		return pk_relief_b;
	}

	public void setPk_relief_b(String pk_relief_b) {
		this.pk_relief_b = pk_relief_b;
	}

	public String getPk_source() {
		return pk_source;
	}

	public void setPk_source(String pk_source) {
		this.pk_source = pk_source;
	}

	public String getPk_relief() {
		return pk_relief;
	}

	public void setPk_relief(String pk_relief) {
		this.pk_relief = pk_relief;
	}

	public UFDateTime getTs() {
		return ts;
	}

	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public UFDouble getMoneyy() {
		return moneyy;
	}

	public void setMoneyy(UFDouble moneyy) {
		this.moneyy = moneyy;
	}

	public UFDate getEnddate() {
		return enddate;
	}

	public void setEnddate(UFDate enddate) {
		this.enddate = enddate;
	}

	public String getInvoiceunit() {
		return invoiceunit;
	}

	public void setInvoiceunit(String invoiceunit) {
		this.invoiceunit = invoiceunit;
	}

	public String getReceiveunit() {
		return receiveunit;
	}

	public void setReceiveunit(String receiveunit) {
		this.receiveunit = receiveunit;
	}

	public String getPayunit() {
		return payunit;
	}

	public void setPayunit(String payunit) {
		this.payunit = payunit;
	}

	public String getHoldunit() {
		return holdunit;
	}

	public void setHoldunit(String holdunit) {
		this.holdunit = holdunit;
	}

	public String getPaybillunit() {
		return paybillunit;
	}

	public void setPaybillunit(String paybillunit) {
		this.paybillunit = paybillunit;
	}

	public String getPk_curr() {
		return pk_curr;
	}

	public void setPk_curr(String pk_curr) {
		this.pk_curr = pk_curr;
	}

	/**
	 * 验证对象各属性之间的数据逻辑正确性.
	 *
	 * 创建日期:2007-8-21
	 *
	 * @exception nc.vo.pub.ValidationException
	 *                如果验证失败,抛出 ValidationException,对错误进行解释.
	 */
	public void validate() throws ValidationException {

		ArrayList errFields = new ArrayList(); // errFields record those null

		// fields that cannot be null.
		// 检查是否为不允许空的字段赋了空值,你可能需要修改下面的提示信息:

		if (pk_relief_b == null) {
			errFields.add(new String("pk_relief_b"));
		}

		StringBuffer message = new StringBuffer();
		message.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045","UPP36201045-000013")/* @res"下列字段不能为空:"*/);
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
	 * 取得父VO主键字段.
	 * <p>
	 * 创建日期:2007-8-21
	 *
	 * @return java.lang.String
	 */
	public java.lang.String getParentPKFieldName() {

		return PK_RELIEF;

	}

	/**
	 * <p>
	 * 取得表主键.
	 * <p>
	 * 创建日期:2007-8-21
	 *
	 * @return java.lang.String
	 */
	public java.lang.String getPKFieldName() {
		return "pk_relief_b";
	}

	/**
	 * <p>
	 * 返回表名称.
	 * <p>
	 * 创建日期:2007-8-21
	 *
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {

		return "fbm_relief_b";
	}

	/**
	 * 按照默认方式创建构造子.
	 *
	 * 创建日期:2007-8-21
	 */
	public ReliefBVO() {

		super();
	}

	/**
	 * 使用主键进行初始化的构造子.
	 *
	 * 创建日期:2007-8-21
	 *
	 * @param newPk_storage_b
	 *            主键值
	 */
	public ReliefBVO(String newPk_relief_b) {

		// 为主键字段赋值:
		pk_relief_b = newPk_relief_b;

	}

	/**
	 * 返回对象标识,用来唯一定位对象.
	 *
	 * 创建日期:2007-8-21
	 *
	 * @return String
	 */
	public String getPrimaryKey() {

		return pk_relief_b;

	}

	/**
	 * 设置对象标识,用来唯一定位对象.
	 *
	 * 创建日期:2007-8-21
	 *
	 * @param newPk_storage_b
	 *            String
	 */
	public void setPrimaryKey(String newPk_storage_b) {

		pk_relief_b = newPk_storage_b;

	}

	/**
	 * 返回数值对象的显示名称.
	 *
	 * 创建日期:2007-8-21
	 *
	 * @return java.lang.String 返回数值对象的显示名称.
	 */
	public String getEntityName() {

		return "fbm_relief_b";

	}

	public String getPk_baseinfo() {
		return pk_baseinfo;
	}

	public void setPk_baseinfo(String pk_baseinfo) {
		this.pk_baseinfo = pk_baseinfo;
	}

}