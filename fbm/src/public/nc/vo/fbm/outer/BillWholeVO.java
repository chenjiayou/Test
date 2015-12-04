package nc.vo.fbm.outer;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;

/**
 * 携带完整信息的票据对象
 * 包含
 * 登记信息
 * 基本信息
 * 动作状态信息
 * 外部关联信息
 * @author xwq
 *
 */
public class BillWholeVO {

	//票据基本信息
	private String pk_baseinfo;
	
	private String fbmbillno;//票据编号
	
	private String pk_curr;//币种
	
	private UFDouble moneyy;//金额
	
	private String receiveunit;//收款单位
	
	private String payunit;//付款单位
	
	private String paybankacc;//付款银行账号
	
	private String receivebankacc;//收款银行账号
	
	//外部关联信息
	private String pk_outerbill_h;//收付款单主表主键
	
	private String pk_outerbill_b;//收付款子表主键
	
	private String outerdjdl;//收付报单据大类
	
	private String outerbilltype;//收付报单据类型
	
	private String outerstatus;//收付款单据状态
	
	private String pk_busibill;//业务单据PK
	
	
	//登记信息
	private String pk_register;//登记主键
	
	private String paybillunit;//付票单位
	
	private String holdunit;//持票单位
	
	private String pk_billtypecode;//收票开票的单据类型
	
	private String pk_corp;
	
	private UFBoolean sfflag;//收付款标志
	
	private String securityaccount;//保证金账号
	
	private UFDouble securitymoney;//保证金金额
	
	private UFDouble poundagemoney;//手续费金额
	
	//动作信息
	private String billtype;//单据类型
	
	private String beginstatus;//开始状态
	
	private String endstatus;//结束状态

	public String getFbmbillno() {
		return fbmbillno;
	}

	public void setFbmbillno(String fbmbillno) {
		this.fbmbillno = fbmbillno;
	}

	public String getPk_curr() {
		return pk_curr;
	}

	public void setPk_curr(String pk_curr) {
		this.pk_curr = pk_curr;
	}

	public UFDouble getMoneyy() {
		return moneyy;
	}

	public void setMoneyy(UFDouble moneyy) {
		this.moneyy = moneyy;
	}

	public String getPk_outerbill_h() {
		return pk_outerbill_h;
	}

	public void setPk_outerbill_h(String pk_outerbill_h) {
		this.pk_outerbill_h = pk_outerbill_h;
	}

	public String getPk_outerbill_b() {
		return pk_outerbill_b;
	}

	public void setPk_outerbill_b(String pk_outerbill_b) {
		this.pk_outerbill_b = pk_outerbill_b;
	}

	public String getOuterbilltype() {
		return outerbilltype;
	}

	public void setOuterbilltype(String outerbilltype) {
		this.outerbilltype = outerbilltype;
	}

	public String getOuterstatus() {
		return outerstatus;
	}

	public void setOuterstatus(String outerstatus) {
		this.outerstatus = outerstatus;
	}

	public String getPaybillunit() {
		return paybillunit;
	}

	public void setPaybillunit(String paybillunit) {
		this.paybillunit = paybillunit;
	}

	public String getHoldunit() {
		return holdunit;
	}

	public void setHoldunit(String holdunit) {
		this.holdunit = holdunit;
	}

	public String getBeginstatus() {
		return beginstatus;
	}

	public void setBeginstatus(String beginstatus) {
		this.beginstatus = beginstatus;
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

	public String getEndstatus() {
		return endstatus;
	}

	public void setEndstatus(String endstatus) {
		this.endstatus = endstatus;
	}

	public String getPk_register() {
		return pk_register;
	}

	public void setPk_register(String pk_register) {
		this.pk_register = pk_register;
	}

	public String getPk_billtypecode() {
		return pk_billtypecode;
	}

	public void setPk_billtypecode(String pk_billtypecode) {
		this.pk_billtypecode = pk_billtypecode;
	}

	public String getOuterdjdl() {
		return outerdjdl;
	}

	public void setOuterdjdl(String outerdjdl) {
		this.outerdjdl = outerdjdl;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getBilltype() {
		return billtype;
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	public UFBoolean getSfflag() {
		return sfflag;
	}

	public void setSfflag(UFBoolean sfflag) {
		this.sfflag = sfflag;
	}

	public String getPk_baseinfo() {
		return pk_baseinfo;
	}

	public void setPk_baseinfo(String pk_baseinfo) {
		this.pk_baseinfo = pk_baseinfo;
	}

	public String getPaybankacc() {
		return paybankacc;
	}

	public void setPaybankacc(String paybankacc) {
		this.paybankacc = paybankacc;
	}

	public String getReceivebankacc() {
		return receivebankacc;
	}

	public void setReceivebankacc(String receivebankacc) {
		this.receivebankacc = receivebankacc;
	}

	public String getSecurityaccount() {
		return securityaccount;
	}

	public void setSecurityaccount(String securityaccount) {
		this.securityaccount = securityaccount;
	}

	public UFDouble getSecuritymoney() {
		return securitymoney;
	}

	public void setSecuritymoney(UFDouble securitymoney) {
		this.securitymoney = securitymoney;
	}

	public UFDouble getPoundagemoney() {
		return poundagemoney;
	}

	public void setPoundagemoney(UFDouble poundagemoney) {
		this.poundagemoney = poundagemoney;
	}

	public String getPk_busibill() {
		return pk_busibill;
	}

	public void setPk_busibill(String pk_busibill) {
		this.pk_busibill = pk_busibill;
	}
	
	
	
}
