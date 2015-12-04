package nc.vo.fbm.report;

import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

public class ReportParam {

	
	private String fbmbillno;
	
	
	// 公司
	private String pk_corp;

	// 票据类型
	private String fbmbilltype;

	// 票据编号
	private String pk_baseinfo;

	// 收款单位
	private String receiveunit;

	// 付款单位
	private String payunit;

	// 出票日期由
	private String invoicedate_begin;

	// 出票日期到
	private String invoicedate_end;

	// 到期日期由
	private String enddate_begin;

	// 到期日期到
	private String enddate_end;

	// 币种
	private String pk_curr;

	// 汇票金额 由
	private String money_begin;

	// 汇票金额 到
	private String money_end;
	
	private String orientation;
	
	private String fbmbillstatus;

	public String getFbmbillno() {
		return fbmbillno;
	}

	public void setFbmbillno(String fbmbillno) {
		this.fbmbillno = fbmbillno;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getFbmbilltype() {
		return fbmbilltype;
	}

	public void setFbmbilltype(String fbmbilltype) {
		this.fbmbilltype = fbmbilltype;
	}

	public String getPk_baseinfo() {
		return pk_baseinfo;
	}

	public void setPk_baseinfo(String pk_baseinfo) {
		this.pk_baseinfo = pk_baseinfo;
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

	public String getInvoicedate_begin() {
		return invoicedate_begin;
	}

	public void setInvoicedate_begin(String invoicedate_begin) {
		this.invoicedate_begin = invoicedate_begin;
	}

	public String getInvoicedate_end() {
		return invoicedate_end;
	}

	public void setInvoicedate_end(String invoicedate_end) {
		this.invoicedate_end = invoicedate_end;
	}

	public String getEnddate_begin() {
		return enddate_begin;
	}

	public void setEnddate_begin(String enddate_begin) {
		this.enddate_begin = enddate_begin;
	}

	public String getEnddate_end() {
		return enddate_end;
	}

	public void setEnddate_end(String enddate_end) {
		this.enddate_end = enddate_end;
	}

	public String getPk_curr() {
		return pk_curr;
	}

	public void setPk_curr(String pk_curr) {
		this.pk_curr = pk_curr;
	}

	public String getMoney_begin() {
		return money_begin;
	}

	public void setMoney_begin(String money_begin) {
		this.money_begin = money_begin;
	}

	public String getMoney_end() {
		return money_end;
	}

	public void setMoney_end(String money_end) {
		this.money_end = money_end;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public String getFbmbillstatus() {
		return fbmbillstatus;
	}

	public void setFbmbillstatus(String fbmbillstatus) {
		this.fbmbillstatus = fbmbillstatus;
	}
 
	
}
