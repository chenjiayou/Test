package nc.vo.fbm.report;

import nc.vo.fvm.fundcashquery.IVarNameDefine;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class BcbHeadVO extends SuperVO implements IReportTempTable,  IVarNameDefine{

	//付款单位银行账号
	public String paybankacc;

	//付款银行
	public String paybank;

	//到期日期
	public UFDate enddate;

	//收款银行
	public String receivebank;

	//承兑协议编号
	public String acceptanceno;

	//废票标志
	public UFBoolean disable;

	//主键
	public String pk_baseinfo;


	//付款单位
	public String payunit;

	//存放地点
	public String keepunit;


	//收款单位
	public String receiveunit;

	//出票日期
	public UFDate invoicedate;

	//出票人全称
	public String invoiceunit;

	//汇票金额
	public UFDouble moneyy;

	//票据编号
	public String fbmbillno;

	//票据类型
	public String fbmbilltype;

	//交易合同编号
	public String contractno;

	//收款单位银行账号
	public String receivebankacc;

	//币种
	public String pk_curr;


	//public String receiveunitname;

	//public String receiveaccname;

	public String orientation;

	//public String invoiceunitname;
	
	public String fbmbillstatus;
	
	public String ts;
	
	public int currdigit;//币种精度
	

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "fbm_baseinfo_temp";
	}
	
	public String getTableIndexs(){
		return "pk_baseinfo";
	}
	
	public String getTableCols(){
		StringBuffer sbcol = new StringBuffer();

		sbcol.append("contractno varchar(50)  ");
		sbcol.append("/*交易合同编号*/,");
		sbcol.append("acceptanceno varchar(50)  ");
		sbcol.append("/*承兑协议编号*/,");
		sbcol.append("enddate char(10)  ");
		sbcol.append("/*到期日期*/,");
		sbcol.append("invoicedate char(10)  ");
		sbcol.append("/*出票日期*/,");
		sbcol.append("moneyy decimal(23,8)  ");
		sbcol.append("/*汇票金额*/,");
		sbcol.append("pk_curr varchar(20)  ");
		sbcol.append("/*币种*/,");
		sbcol.append("receivebank varchar(100)  ");
		sbcol.append("/*收款银行*/,");
		sbcol.append("receiveaccname varchar(200) ");
		sbcol.append("/*收款单位帐号名称*/,");
		sbcol.append("receivebankacc varchar(100) ");
		sbcol.append("/* 收款单位银行帐号 */,");
		sbcol.append("receiveunitname varchar(200)  ");
		sbcol.append("/* 收款单位名称 */,");
		sbcol.append("def5 varchar(200)  ");
		sbcol.append("/* 自定义项5 */,");
		sbcol.append("def4 varchar(200)  ");
		sbcol.append("/* 自定义项4 */,");
		sbcol.append("def3 varchar(200)  ");
		sbcol.append("/* 自定义项3 */,");
		sbcol.append("def2 varchar(200)  ");
		sbcol.append("/* 自定义项2 */,");
		sbcol.append("def1 varchar(200)  ");
		sbcol.append("/* 自定义项1 */,");
		sbcol.append("orientation varchar(20)  ");
		sbcol.append("/* 票据方向 */,");
		sbcol.append("receiveunit varchar(100)  ");
		sbcol.append("/* 收款单位 */,");
		sbcol.append("disable char(1)  ");
		sbcol.append("/* 废票标志 */,");
		sbcol.append("paybank varchar(100)  ");
		sbcol.append("/* 付款银行 */,");
		sbcol.append("paybankacc varchar(100)  ");
		sbcol.append("/* 付款单位银行帐号 */,");
		sbcol.append("keepunit varchar(100)  ");
		sbcol.append("/* 存放地点 */,");
		sbcol.append("payunit varchar(100)  ");
		sbcol.append("/* 付款单位 */,");
		sbcol.append("invoiceunitname varchar(200)  ");
		sbcol.append("/* 出票单位名称 */,");
		sbcol.append("invoiceunit varchar(100)  ");
		sbcol.append("/* 出票单位 */,");
		sbcol.append("fbmbilltype varchar(50)  ");
		sbcol.append("/* 票据类型 */,");
		sbcol.append("fbmbillno varchar(256)  ");
		sbcol.append("/* 票据编号 */,");
		sbcol.append("pk_baseinfo char(20)   ,");
		sbcol.append("currdigit int ,");
		sbcol.append("fbmbillstatus varchar(100)  ,");
		sbcol.append(" ts char(19)");

		
		return sbcol.toString();
	}

	public String[] getVaribleNames() {
		// TODO Auto-generated method stub
		return this.getAttributeNames();
	}

	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return "pk_baseinfo";
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPaybankacc() {
		return paybankacc;
	}

	public void setPaybankacc(String paybankacc) {
		this.paybankacc = paybankacc;
	}

	public String getPaybank() {
		return paybank;
	}

	public void setPaybank(String paybank) {
		this.paybank = paybank;
	}

	public UFDate getEnddate() {
		return enddate;
	}

	public void setEnddate(UFDate enddate) {
		this.enddate = enddate;
	}

	public String getReceivebank() {
		return receivebank;
	}

	public void setReceivebank(String receivebank) {
		this.receivebank = receivebank;
	}

	public String getAcceptanceno() {
		return acceptanceno;
	}

	public void setAcceptanceno(String acceptanceno) {
		this.acceptanceno = acceptanceno;
	}

	public UFBoolean getDisable() {
		return disable;
	}

	public void setDisable(UFBoolean disable) {
		this.disable = disable;
	}

	public String getPk_baseinfo() {
		return pk_baseinfo;
	}

	public void setPk_baseinfo(String pk_baseinfo) {
		this.pk_baseinfo = pk_baseinfo;
	}

	public String getPayunit() {
		return payunit;
	}

	public void setPayunit(String payunit) {
		this.payunit = payunit;
	}

	public String getKeepunit() {
		return keepunit;
	}

	public void setKeepunit(String keepunit) {
		this.keepunit = keepunit;
	}

	public String getReceiveunit() {
		return receiveunit;
	}

	public void setReceiveunit(String receiveunit) {
		this.receiveunit = receiveunit;
	}

	public UFDate getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(UFDate invoicedate) {
		this.invoicedate = invoicedate;
	}

	public String getInvoiceunit() {
		return invoiceunit;
	}

	public void setInvoiceunit(String invoiceunit) {
		this.invoiceunit = invoiceunit;
	}

	public UFDouble getMoneyy() {
		return moneyy;
	}

	public void setMoneyy(UFDouble moneyy) {
		this.moneyy = moneyy;
	}

	public String getFbmbillno() {
		return fbmbillno;
	}

	public void setFbmbillno(String fbmbillno) {
		this.fbmbillno = fbmbillno;
	}

	public String getFbmbilltype() {
		return fbmbilltype;
	}

	public void setFbmbilltype(String fbmbilltype) {
		this.fbmbilltype = fbmbilltype;
	}

	public String getContractno() {
		return contractno;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public String getReceivebankacc() {
		return receivebankacc;
	}

	public void setReceivebankacc(String receivebankacc) {
		this.receivebankacc = receivebankacc;
	}

	public String getPk_curr() {
		return pk_curr;
	}

	public void setPk_curr(String pk_curr) {
		this.pk_curr = pk_curr;
	}

//	public String getReceiveunitname() {
//		return receiveunitname;
//	}
//
//	public void setReceiveunitname(String receiveunitname) {
//		this.receiveunitname = receiveunitname;
//	}
//
//	public String getReceiveaccname() {
//		return receiveaccname;
//	}
//
//	public void setReceiveaccname(String receiveaccname) {
//		this.receiveaccname = receiveaccname;
//	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

//	public String getInvoiceunitname() {
//		return invoiceunitname;
//	}
//
//	public void setInvoiceunitname(String invoiceunitname) {
//		this.invoiceunitname = invoiceunitname;
//	}

	public String getFbmbillstatus() {
		return fbmbillstatus;
	}

	public void setFbmbillstatus(String fbmbillstatus) {
		this.fbmbillstatus = fbmbillstatus;
	}

	public int getCurrdigit() {
		return currdigit;
	}

	public void setCurrdigit(int currdigit) {
		this.currdigit = currdigit;
	}


	
	
	
}
