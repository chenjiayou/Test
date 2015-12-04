package nc.vo.fbm.endore;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class EndoreVO extends SuperVO {

	private static final long serialVersionUID = 1L;

	private String pk_endore;//����
	
	private String pk_billtypecode ;//�������ͱ���
	
	private String pk_source ;//�Ǽ�PK
	 
	private String pk_baseinfo ;//Ʊ�ݻ�����ϢPK
	
	private String endorser ;//���鵥λ
	
	private String endorser_acc ;//���鵥λ�����˺�
	
	private String endorser_bank ;//���鵥λ����
	
	private String endorsee;//�����鵥λ
	
	private String endorsee_acc ;//�����鵥λ�����˺�
	
	private String endorsee_bank ;//�����鵥λ����
	
	private UFDate busdate;//ҵ������
	
	private UFDouble moneyy ;//���
	
	private String contractno;//���׺�ͬ��
	
	private String note;//��ע
	
	private String vbillno ;//���ݱ��
	private String voperatorid ;//������
	
	private UFDate doperatedate;//��������
	
	private String vapproveid ;//�����
	
	private UFDate dapprovedate ;//�������
	
	private String vapprovenote ;//�������
	
	private Integer vbillstatus;//����״̬
	
	private String pk_corp;//��˾
	
	private String pk_org ;//��֯
	
	private UFDouble frate;//���һ���
	
	private UFDouble brate;//���һ���
	
	private UFDouble moneyf;//���ҽ��
	
	private UFDouble moneyb;//���ҽ��
	
	private String payunitname;
	private String paybank;
	private String receiveunit;
	private String receivebank;
	
	private String payunit;
	
	private String syscode;

	private Integer dr;
	
	private UFDateTime ts;
	
	
	// ��������:bill_privacy->˽�У�bill_unistor->ͳ��
	public String opbilltype;

	//����
	public String pk_curr;

	
	
	private String fbmbillno; //Ʊ�ݱ��
	
	public UFBoolean unitvoucher;
	
	private String def1;
	private String def2;
	private String def3;
	private String def4;
	private String def5;
	
	public static final String PK_BILLTYPECODE = "pk_billtypecode";
	public static final String FBM_ENDORE = "fbm_endore";//�������
	public static final String Endore_TabName = "fbm_aendore";//����ģ���ͷҳǩ��
	public static final String PK_ENDORE = "pk_endore";//����
	public static final String PK_SOURCE = "pk_source";//��Դ����
	public static final String VBILLNO = "vbillno"; //���ݱ��
	public static final String VBILLSTATUS = "vbillstatus";//����״̬
	public static final String ENDORSEE = "endorsee";//�����鵥λ
	public static final String ENDORSER_ACC = "endorser_acc";//�������������ʺ�
	public static final String ENDORSER_BANK = "endorser_bank";//������������
	public static final String ENDORSER = "endorser";//���鵥λ
	public static final String ENDORSEE_ACC = "endorsee_acc";//���鵥λ�����ʺ�
	public static final String ENDORSEE_BANK = "endorsee_bank";//���鵥λ����
	public static final String MONEYY = "moneyy";//������
	public static final String BUSDATE = "busdate";//��������
	public static final String BRATE = "brate";//���һ���
	public static final String MONEYB = "moneyb";//���ҽ��
	public static final String MONEYF = "moneyf";//���ҽ��
	public static final String FRATE = "frate";//���һ���
	public static final String VOPERATORID = "voperatorid";//������
	public static final String DOPERATEDATE = "doperatedate";//��������
	public static final String VAPPROVEID = "vapproveid";//�����
	public static final String DAPPROVEDATE = "dapprovedate";//�������
	public static final String VAPPROVENOTE = "vapprovenote";//�������
	public static final String BANKNAME = "bankname";//��������
	public static final String PK_CORP = "pk_corp";//��˾
	public static final String NOTE = "note";//��ע
	public static final String PK_BASEINFO = "pk_baseinfo";//Ʊ�ݻ�����Ϣ�У�
	
    public static final String FBMBILLTYPE = "fbmbilltype";//Ʊ������
    public static final String INVOICEDATE = "invoicedate";//��Ʊ����
    public static final String ENDDATE = "enddate";//��������
    public static final String INVOICEUNIT = "invoiceunit";//��Ʊ��λ
    public static final String KEEPUNIT = "keepunit";//��ŵص�
    public static final String PAYBANK = "paybank";//��������
    public static final String PAYBANKACC = "paybankacc";//���������˺�
    public static final String PAYUNITNAME = "payunitname";//���λ
    public static final String RECEIVEBANK = "receivebank";//�տ�����
    public static final String RECEIVEBANKACC = "receivebankacc";//�տ������ʺ�
    public static final String RECEIVEUNIT = "receiveunit";//�տλ
    public static final String GATHERDATE = "gatherdate";//�յ�����
    public static final String HOLDUNIT = "holdunit";//��Ʊ������
    public static final String PAYUNIT = "payunit";//����������
    public static final String PK_CURR = "pk_curr";//����
    public static final String CONTRACTNO = "contractno";//��ͬ���
    public static final String ACCEPTANCENO = "acceptanceno";//�ж�Э����
	
	public static final String OPBILLTYPE = "opbilltype";
	public static final String VVOUCHERID = "vvoucherid";
	public static final String DVOUCHERDATE = "dvoucherdate";
	
	public static final String FBMBILLNO = "fbmbillno";//Ʊ�ݱ��
	
	public static final String SYSCODE = "syscode";
	
	private String upgrade;
	public static final String UPGRADE = "UPGRADE";
	private int endoretype;//0��ʾ�ڲ����飬1��ʾ�ⲿ����
	public static final int INNER = 0;
	public static final int OUTER = 1;
	private boolean isInnerDiscountPJ ;//�Ƿ�����Ʊ��
	
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
