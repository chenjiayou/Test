package nc.vo.fbm.endore;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class EndoreVO extends SuperVO {

	private static final long serialVersionUID = 1L;

	private String pk_endore;//主键
	
	private String pk_billtypecode ;//单据类型编码
	
	private String pk_source ;//登记PK
	 
	private String pk_baseinfo ;//票据基本信息PK
	
	private String endorser ;//背书单位
	
	private String endorser_acc ;//背书单位银行账号
	
	private String endorser_bank ;//背书单位银行
	
	private String endorsee;//被背书单位
	
	private String endorsee_acc ;//被背书单位银行账号
	
	private String endorsee_bank ;//被背书单位银行
	
	private UFDate busdate;//业务日期
	
	private UFDouble moneyy ;//金额
	
	private String contractno;//交易合同号
	
	private String note;//备注
	
	private String vbillno ;//单据编号
	private String voperatorid ;//操作人
	
	private UFDate doperatedate;//操作日期
	
	private String vapproveid ;//审核人
	
	private UFDate dapprovedate ;//审核日期
	
	private String vapprovenote ;//审核批语
	
	private Integer vbillstatus;//单据状态
	
	private String pk_corp;//公司
	
	private String pk_org ;//组织
	
	private UFDouble frate;//辅币汇率
	
	private UFDouble brate;//本币汇率
	
	private UFDouble moneyf;//辅币金额
	
	private UFDouble moneyb;//本币金额
	
	private String payunitname;
	private String paybank;
	private String receiveunit;
	private String receivebank;
	
	private String payunit;
	
	private String syscode;

	private Integer dr;
	
	private UFDateTime ts;
	
	
	// 操作类型:bill_privacy->私有；bill_unistor->统管
	public String opbilltype;

	//币种
	public String pk_curr;

	
	
	private String fbmbillno; //票据编号
	
	public UFBoolean unitvoucher;
	
	private String def1;
	private String def2;
	private String def3;
	private String def4;
	private String def5;
	
	public static final String PK_BILLTYPECODE = "pk_billtypecode";
	public static final String FBM_ENDORE = "fbm_endore";//背书表名
	public static final String Endore_TabName = "fbm_aendore";//背书模板表头页签名
	public static final String PK_ENDORE = "pk_endore";//主键
	public static final String PK_SOURCE = "pk_source";//来源单据
	public static final String VBILLNO = "vbillno"; //单据编号
	public static final String VBILLSTATUS = "vbillstatus";//单据状态
	public static final String ENDORSEE = "endorsee";//被背书单位
	public static final String ENDORSER_ACC = "endorser_acc";//被背书人银行帐号
	public static final String ENDORSER_BANK = "endorser_bank";//被背书人银行
	public static final String ENDORSER = "endorser";//背书单位
	public static final String ENDORSEE_ACC = "endorsee_acc";//背书单位银行帐号
	public static final String ENDORSEE_BANK = "endorsee_bank";//背书单位银行
	public static final String MONEYY = "moneyy";//背书金额
	public static final String BUSDATE = "busdate";//背书日期
	public static final String BRATE = "brate";//本币汇率
	public static final String MONEYB = "moneyb";//本币金额
	public static final String MONEYF = "moneyf";//辅币金额
	public static final String FRATE = "frate";//辅币汇率
	public static final String VOPERATORID = "voperatorid";//操作人
	public static final String DOPERATEDATE = "doperatedate";//操作日期
	public static final String VAPPROVEID = "vapproveid";//审核人
	public static final String DAPPROVEDATE = "dapprovedate";//审核日期
	public static final String VAPPROVENOTE = "vapprovenote";//审核评语
	public static final String BANKNAME = "bankname";//银行名称
	public static final String PK_CORP = "pk_corp";//公司
	public static final String NOTE = "note";//备注
	public static final String PK_BASEINFO = "pk_baseinfo";//票据基本信息ＰＫ
	
    public static final String FBMBILLTYPE = "fbmbilltype";//票据类型
    public static final String INVOICEDATE = "invoicedate";//出票日期
    public static final String ENDDATE = "enddate";//到期日期
    public static final String INVOICEUNIT = "invoiceunit";//出票单位
    public static final String KEEPUNIT = "keepunit";//存放地点
    public static final String PAYBANK = "paybank";//付款银行
    public static final String PAYBANKACC = "paybankacc";//付款银行账号
    public static final String PAYUNITNAME = "payunitname";//付款单位
    public static final String RECEIVEBANK = "receivebank";//收款银行
    public static final String RECEIVEBANKACC = "receivebankacc";//收款银行帐号
    public static final String RECEIVEUNIT = "receiveunit";//收款单位
    public static final String GATHERDATE = "gatherdate";//收到日期
    public static final String HOLDUNIT = "holdunit";//持票人名称
    public static final String PAYUNIT = "payunit";//付款人名称
    public static final String PK_CURR = "pk_curr";//币种
    public static final String CONTRACTNO = "contractno";//合同编号
    public static final String ACCEPTANCENO = "acceptanceno";//承兑协议编号
	
	public static final String OPBILLTYPE = "opbilltype";
	public static final String VVOUCHERID = "vvoucherid";
	public static final String DVOUCHERDATE = "dvoucherdate";
	
	public static final String FBMBILLNO = "fbmbillno";//票据编号
	
	public static final String SYSCODE = "syscode";
	
	private String upgrade;
	public static final String UPGRADE = "UPGRADE";
	private int endoretype;//0表示内部背书，1表示外部背书
	public static final int INNER = 0;
	public static final int OUTER = 1;
	private boolean isInnerDiscountPJ ;//是否内贴票据
	
	public static final String UNITVOUCHER = "unitvoucher";
	
	public String getPk_endore() {
		return pk_endore;
	}

	public void setPk_endore(String pk_endore) {
		this.pk_endore = pk_endore;
	}

	public String getPrimaryKey() {
		return pk_endore;
	}
	
	public void setPrimaryKey(String pk_endore){
		this.pk_endore = pk_endore;
	}
	
	public String getPk_billtypecode() {
		return pk_billtypecode;
	}

	public void setPk_billtypecode(String pk_billtypecode) {
		this.pk_billtypecode = pk_billtypecode;
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

	public String getEndorser() {
		return endorser;
	}

	public void setEndorser(String endorser) {
		this.endorser = endorser;
	}

	public String getEndorser_acc() {
		return endorser_acc;
	}

	public void setEndorser_acc(String endorser_acc) {
		this.endorser_acc = endorser_acc;
	}

	public String getEndorser_bank() {
		return endorser_bank;
	}

	public void setEndorser_bank(String endorser_bank) {
		this.endorser_bank = endorser_bank;
	}

	public String getEndorsee() {
		return endorsee;
	}

	public void setEndorsee(String endorsee) {
		this.endorsee = endorsee;
	}

	public String getEndorsee_acc() {
		return endorsee_acc;
	}

	public void setEndorsee_acc(String endorsee_acc) {
		this.endorsee_acc = endorsee_acc;
	}

	public String getEndorsee_bank() {
		return endorsee_bank;
	}

	public void setEndorsee_bank(String endorsee_bank) {
		this.endorsee_bank = endorsee_bank;
	}

	public UFDate getBusdate() {
		return busdate;
	}

	public void setBusdate(UFDate busdate) {
		this.busdate = busdate;
	}

	public UFDouble getMoneyy() {
		return moneyy;
	}

	public void setMoneyy(UFDouble moneyy) {
		this.moneyy = moneyy;
	}

	public String getContractno() {
		return contractno;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getVbillno() {
		return vbillno;
	}

	public void setVbillno(String vbillno) {
		this.vbillno = vbillno;
	}

	public String getVoperatorid() {
		return voperatorid;
	}

	public void setVoperatorid(String voperatorid) {
		this.voperatorid = voperatorid;
	}

	public UFDate getDoperatedate() {
		return doperatedate;
	}

	public void setDoperatedate(UFDate doperatedate) {
		this.doperatedate = doperatedate;
	}

	public String getVapproveid() {
		return vapproveid;
	}

	public void setVapproveid(String vapproveid) {
		this.vapproveid = vapproveid;
	}

	public UFDate getDapprovedate() {
		return dapprovedate;
	}

	public void setDapprovedate(UFDate dapprovedate) {
		this.dapprovedate = dapprovedate;
	}

	public String getVapprovenote() {
		return vapprovenote;
	}

	public void setVapprovenote(String vapprovenote) {
		this.vapprovenote = vapprovenote;
	}



	public Integer getVbillstatus() {
		return vbillstatus;
	}

	public void setVbillstatus(Integer vbillstatus) {
		this.vbillstatus = vbillstatus;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getPk_org() {
		return pk_org;
	}

	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}

	public UFDouble getFrate() {
		return frate;
	}

	public void setFrate(UFDouble frate) {
		this.frate = frate;
	}

	public UFDouble getBrate() {
		return brate;
	}

	public void setBrate(UFDouble brate) {
		this.brate = brate;
	}

	public UFDouble getMoneyf() {
		return moneyf;
	}

	public void setMoneyf(UFDouble moneyf) {
		this.moneyf = moneyf;
	}

	public UFDouble getMoneyb() {
		return moneyb;
	}

	public void setMoneyb(UFDouble moneyb) {
		this.moneyb = moneyb;
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

	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return "pk_endore";
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "fbm_endore";
	}

	public String getPayunitname() {
		return payunitname;
	}

	public void setPayunitname(String payunitname) {
		this.payunitname = payunitname;
	}

	public String getPaybank() {
		return paybank;
	}

	public void setPaybank(String paybank) {
		this.paybank = paybank;
	}

	public String getReceiveunit() {
		return receiveunit;
	}

	public void setReceiveunit(String receiveunit) {
		this.receiveunit = receiveunit;
	}

	public String getReceivebank() {
		return receivebank;
	}

	public void setReceivebank(String receivebank) {
		this.receivebank = receivebank;
	}

	public String getPayunit() {
		return payunit;
	}

	public void setPayunit(String payunit) {
		this.payunit = payunit;
	}

	/**
	 * @return the opbilltype
	 */
	public String getOpbilltype() {
		return opbilltype;
	}

	/**
	 * @param opbilltype the opbilltype to set
	 */
	public void setOpbilltype(String opbilltype) {
		this.opbilltype = opbilltype;
	}



	public String getPk_curr() {
		return pk_curr;
	}

	public void setPk_curr(String pk_curr) {
		this.pk_curr = pk_curr;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}


	public String getFbmbillno() {
		return fbmbillno;
	}

	public void setFbmbillno(String fbmbillno) {
		this.fbmbillno = fbmbillno;
	}

	public String getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(String upgrade) {
		this.upgrade = upgrade;
	}

	public int getEndoretype() {
		return endoretype;
	}

	public void setEndoretype(int endoretype) {
		this.endoretype = endoretype;
	}

	public boolean isInnerDiscountPJ() {
		return isInnerDiscountPJ;
	}

	public void setInnerDiscountPJ(boolean isInnerDiscountPJ) {
		this.isInnerDiscountPJ = isInnerDiscountPJ;
	}

	public UFBoolean getUnitvoucher() {
		return unitvoucher;
	}

	public void setUnitvoucher(UFBoolean unitvoucher) {
		this.unitvoucher = unitvoucher;
	}

	public String getDef1() {
		return def1;
	}

	public void setDef1(String def1) {
		this.def1 = def1;
	}

	public String getDef2() {
		return def2;
	}

	public void setDef2(String def2) {
		this.def2 = def2;
	}

	public String getDef3() {
		return def3;
	}

	public void setDef3(String def3) {
		this.def3 = def3;
	}

	public String getDef4() {
		return def4;
	}

	public void setDef4(String def4) {
		this.def4 = def4;
	}

	public String getDef5() {
		return def5;
	}

	public void setDef5(String def5) {
		this.def5 = def5;
	}

	
}
