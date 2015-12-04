package nc.vo.fbm.report;

import nc.vo.fvm.fundcashquery.IVarNameDefine;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class BcbBodyVO extends SuperVO implements IReportTempTable,  IVarNameDefine{

	public Integer serialnum;

	public String beginstatus;

	public String endstatus;

	public String actionperson;

	public String pk_bill;//操作业务单据PK

	public String pk_action;

	public String billtype;

	public UFDate actiondate;

	public String actioncode;

	public String pk_source;//收票登记单PK

	public String pk_baseinfo;//票据基本信息PK

	public String pk_org;//组织

	public UFBoolean isnew;

	public String pk_corp;

	public int currdigit;//币种精度
	
	//公共字段
	public String vbillno;//单据编号
	public String remark;//备注
	public UFDouble moneyy;//业务金额
	public UFDouble sxfmoneyy;//业务手续费
	//基本信息类字段
	
	
	//托管类字段
	public String storage_keepbank;//受托银行
	public String storage_inputunit;//托管单位
	public String storage_outputunit;//领用单位
	
	//收票登记单字段
	public String register_paybillunit;//付票单位
	//托收字段
	public String collection_bankacc;//托收银行账号
	public String collection_bank;//托收银行
	//贴现字段
	public String discount_bankacc;//贴现银行账号
	public String discount_bank;//贴现银行
	public UFDouble discount_rate;//贴现年利率
	public UFDouble discount_lx;//贴现利息
	 
	//背书字段
	public String endore_endorser;//背书单位
	public String endore_endorsee;//被背书单位
	//质押字段
	public String impawn_creditunit;//贷款单位
	public String impawn_debitunit;//借款单位

	//调剂字段
	public String relief_reliefunit;//调剂单位
	
	//付票登记
	public String register_cctype;//授信类型
	public String register_securityacc;//保证金账户
	public UFDouble register_securityrate;//保证金比例
	public UFDouble register_securitymoneyy;//保证金金额
	public UFDouble register_mode;//担保方式
	//票据付款
	public String accept_holdunit;//持票单位
	public String accept_holderacc;//持票单位账号
	public String accept_backsecacc;//返回保证金账户
	public String accept_banksecmoneyy;//返回保证金金额
	
	//退票单
	public String return_person;//退票人
	public String return_date;//退票日期
	public String return_type;//退票类型
	
	
	
	public String getVbillno() {
		return vbillno;
	}

	public void setVbillno(String vbillno) {
		this.vbillno = vbillno;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public UFDouble getMoneyy() {
		return moneyy;
	}

	public void setMoneyy(UFDouble moneyy) {
		this.moneyy = moneyy;
	}

	public UFDouble getSxfmoneyy() {
		return sxfmoneyy;
	}

	public void setSxfmoneyy(UFDouble sxfmoneyy) {
		this.sxfmoneyy = sxfmoneyy;
	}

	public String getStorage_keepbank() {
		return storage_keepbank;
	}

	public void setStorage_keepbank(String storage_keepbank) {
		this.storage_keepbank = storage_keepbank;
	}

	public String getStorage_inputunit() {
		return storage_inputunit;
	}

	public void setStorage_inputunit(String storage_inputunit) {
		this.storage_inputunit = storage_inputunit;
	}

	public String getStorage_outputunit() {
		return storage_outputunit;
	}

	public void setStorage_outputunit(String storage_outputunit) {
		this.storage_outputunit = storage_outputunit;
	}

	public String getRegister_paybillunit() {
		return register_paybillunit;
	}

	public void setRegister_paybillunit(String register_paybillunit) {
		this.register_paybillunit = register_paybillunit;
	}

	public String getCollection_bankacc() {
		return collection_bankacc;
	}

	public void setCollection_bankacc(String collection_bankacc) {
		this.collection_bankacc = collection_bankacc;
	}

	public String getCollection_bank() {
		return collection_bank;
	}

	public void setCollection_bank(String collection_bank) {
		this.collection_bank = collection_bank;
	}

	public String getDiscount_bankacc() {
		return discount_bankacc;
	}

	public void setDiscount_bankacc(String discount_bankacc) {
		this.discount_bankacc = discount_bankacc;
	}

	public String getDiscount_bank() {
		return discount_bank;
	}

	public void setDiscount_bank(String discount_bank) {
		this.discount_bank = discount_bank;
	}

	public UFDouble getDiscount_rate() {
		return discount_rate;
	}

	public void setDiscount_rate(UFDouble discount_rate) {
		this.discount_rate = discount_rate;
	}

	public UFDouble getDiscount_lx() {
		return discount_lx;
	}

	public void setDiscount_lx(UFDouble discount_lx) {
		this.discount_lx = discount_lx;
	}

	public String getEndore_endorser() {
		return endore_endorser;
	}

	public void setEndore_endorser(String endore_endorser) {
		this.endore_endorser = endore_endorser;
	}

	public String getEndore_endorsee() {
		return endore_endorsee;
	}

 

	public String getImpawn_creditunit() {
		return impawn_creditunit;
	}

	public void setImpawn_creditunit(String impawn_creditunit) {
		this.impawn_creditunit = impawn_creditunit;
	}

	public String getImpawn_debitunit() {
		return impawn_debitunit;
	}

	public void setImpawn_debitunit(String impawn_debitunit) {
		this.impawn_debitunit = impawn_debitunit;
	}

	public String getRelief_reliefunit() {
		return relief_reliefunit;
	}

	public void setRelief_reliefunit(String relief_reliefunit) {
		this.relief_reliefunit = relief_reliefunit;
	}

	public String getRegister_cctype() {
		return register_cctype;
	}

	public void setRegister_cctype(String register_cctype) {
		this.register_cctype = register_cctype;
	}

	public String getRegister_securityacc() {
		return register_securityacc;
	}

	public void setRegister_securityacc(String register_securityacc) {
		this.register_securityacc = register_securityacc;
	}

	public UFDouble getRegister_securityrate() {
		return register_securityrate;
	}

	public void setRegister_securityrate(UFDouble register_securityrate) {
		this.register_securityrate = register_securityrate;
	}

	public UFDouble getRegister_securitymoneyy() {
		return register_securitymoneyy;
	}

	public void setRegister_securitymoneyy(UFDouble register_securitymoneyy) {
		this.register_securitymoneyy = register_securitymoneyy;
	}

	public UFDouble getRegister_mode() {
		return register_mode;
	}

	public void setRegister_mode(UFDouble register_mode) {
		this.register_mode = register_mode;
	}

	public String getAccept_holdunit() {
		return accept_holdunit;
	}

	public void setAccept_holdunit(String accept_holdunit) {
		this.accept_holdunit = accept_holdunit;
	}

	public String getAccept_holderacc() {
		return accept_holderacc;
	}

	public void setAccept_holderacc(String accept_holderacc) {
		this.accept_holderacc = accept_holderacc;
	}

	public String getAccept_backsecacc() {
		return accept_backsecacc;
	}

	public void setAccept_backsecacc(String accept_backsecacc) {
		this.accept_backsecacc = accept_backsecacc;
	}

	public String getAccept_banksecmoneyy() {
		return accept_banksecmoneyy;
	}

	public void setAccept_banksecmoneyy(String accept_banksecmoneyy) {
		this.accept_banksecmoneyy = accept_banksecmoneyy;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "fbm_action_temp";
	}

	public String getTableCols() {
		StringBuffer sbcol = new StringBuffer();
		
		sbcol.append("vbillno varchar(50),");
		sbcol.append("remark varchar(250),");
		sbcol.append("moneyy decimal(23,8),");
		sbcol.append("sxfmoneyy decimal(23,8),");
		sbcol.append("storage_keepbank varchar(100),");
		sbcol.append("storage_inputunit varchar(100),");
		sbcol.append("storage_outputunit varchar(100),");
		sbcol.append("register_paybillunit varchar(100),");
		sbcol.append("collection_bankacc varchar(100),");
		sbcol.append("collection_bank varchar(100),");
		sbcol.append("discount_bankacc varchar(100),");
		sbcol.append("discount_bank varchar(100),");
		sbcol.append("discount_rate decimal(23,8),");
		sbcol.append("discount_lx decimal(23,8),");
		sbcol.append("endore_endorser varchar(100),");
		sbcol.append("endore_endorsee varchar(100),");
		sbcol.append("impawn_creditunit varchar(100),");
		sbcol.append("impawn_debitunit varchar(100)," );
		sbcol.append("relief_reliefunit varchar(100),");
		sbcol.append("register_cctype varchar(20),");
		sbcol.append("register_securityacc varchar(100),");
		sbcol.append("register_securityrate decimal(23,8),");
		sbcol.append("register_securitymoneyy decimal(23,8),");
		sbcol.append("register_mode varchar(20),");
		sbcol.append("accept_holdunit varchar(100),");
		sbcol.append("accept_holderacc varchar(100),");
		sbcol.append("accept_backsecacc varchar(100),");
		sbcol.append("accept_banksecmoneyy decimal(23,8),");
		sbcol.append("isnew char(1),");
		sbcol.append("actioncode varchar(100),");
		sbcol.append("pk_org varchar(20),");
		sbcol.append("serialnum int, ");
		sbcol.append("pk_action char(20),");
		sbcol.append("pk_baseinfo char(20),");
		sbcol.append("pk_source char(20), ");
		sbcol.append("endstatus varchar(50),");
		sbcol.append("beginstatus varchar(50),");
		sbcol.append("actiondate char(10),");
		sbcol.append("actionperson varchar(100),");
		sbcol.append("pk_corp char(4),");
		sbcol.append("pk_bill char(20), ");
		sbcol.append("billtype varchar(20), ");
		sbcol.append("return_person varchar(100),");
		sbcol.append("return_date char(10),");
		sbcol.append("return_type varchar(20),");
		sbcol.append("currdigit int ,");
		sbcol.append(" ts char(19)");
		return sbcol.toString();
		
	}

	public String getTableIndexs() {
		// TODO Auto-generated method stub
		return "pk_action";
	}
	

	public String[] getVaribleNames() {
		// TODO Auto-generated method stub
		return this.getAttributeNames();
	}

	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return "pk_action";
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getSerialnum() {
		return serialnum;
	}

	public void setSerialnum(Integer serialnum) {
		this.serialnum = serialnum;
	}

	public String getBeginstatus() {
		return beginstatus;
	}

	public void setBeginstatus(String beginstatus) {
		this.beginstatus = beginstatus;
	}

	public String getEndstatus() {
		return endstatus;
	}

	public void setEndstatus(String endstatus) {
		this.endstatus = endstatus;
	}

	public String getActionperson() {
		return actionperson;
	}

	public void setActionperson(String actionperson) {
		this.actionperson = actionperson;
	}

	public String getPk_bill() {
		return pk_bill;
	}

	public void setPk_bill(String pk_bill) {
		this.pk_bill = pk_bill;
	}

	public String getPk_action() {
		return pk_action;
	}

	public void setPk_action(String pk_action) {
		this.pk_action = pk_action;
	}

	public String getBilltype() {
		return billtype;
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	public UFDate getActiondate() {
		return actiondate;
	}

	public void setActiondate(UFDate actiondate) {
		this.actiondate = actiondate;
	}

	public String getActioncode() {
		return actioncode;
	}

	public void setActioncode(String actioncode) {
		this.actioncode = actioncode;
	}

	public String getPk_source() {
		return pk_source;
	}

	public void setPk_source(String pk_source) {
		this.pk_source = pk_source;
	}

	public String getPk_baseinfo() {
		return pk_baseinfo;
	}

	public void setPk_baseinfo(String pk_baseinfo) {
		this.pk_baseinfo = pk_baseinfo;
	}

	public String getPk_org() {
		return pk_org;
	}

	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}

	public UFBoolean getIsnew() {
		return isnew;
	}

	public void setIsnew(UFBoolean isnew) {
		this.isnew = isnew;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}


	public void setEndore_endorsee(String endore_endorsee) {
		this.endore_endorsee = endore_endorsee;
	}

	public String getReturn_person() {
		return return_person;
	}

	public void setReturn_person(String return_person) {
		this.return_person = return_person;
	}

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}

	public String getReturn_type() {
		return return_type;
	}

	public void setReturn_type(String return_type) {
		this.return_type = return_type;
	}
	
	
}
