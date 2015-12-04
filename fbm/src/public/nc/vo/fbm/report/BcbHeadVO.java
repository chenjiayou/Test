package nc.vo.fbm.report;

import nc.vo.fvm.fundcashquery.IVarNameDefine;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class BcbHeadVO extends SuperVO implements IReportTempTable,  IVarNameDefine{

	//���λ�����˺�
	public String paybankacc;

	//��������
	public String paybank;

	//��������
	public UFDate enddate;

	//�տ�����
	public String receivebank;

	//�ж�Э����
	public String acceptanceno;

	//��Ʊ��־
	public UFBoolean disable;

	//����
	public String pk_baseinfo;


	//���λ
	public String payunit;

	//��ŵص�
	public String keepunit;


	//�տλ
	public String receiveunit;

	//��Ʊ����
	public UFDate invoicedate;

	//��Ʊ��ȫ��
	public String invoiceunit;

	//��Ʊ���
	public UFDouble moneyy;

	//Ʊ�ݱ��
	public String fbmbillno;

	//Ʊ������
	public String fbmbilltype;

	//���׺�ͬ���
	public String contractno;

	//�տλ�����˺�
	public String receivebankacc;

	//����
	public String pk_curr;


	//public String receiveunitname;

	//public String receiveaccname;

	public String orientation;

	//public String invoiceunitname;
	
	public String fbmbillstatus;
	
	public String ts;
	
	public int currdigit;//���־���
	

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
		sbcol.append("/*���׺�ͬ���*/,");
		sbcol.append("acceptanceno varchar(50)  ");
		sbcol.append("/*�ж�Э����*/,");
		sbcol.append("enddate char(10)  ");
		sbcol.append("/*��������*/,");
		sbcol.append("invoicedate char(10)  ");
		sbcol.append("/*��Ʊ����*/,");
		sbcol.append("moneyy decimal(23,8)  ");
		sbcol.append("/*��Ʊ���*/,");
		sbcol.append("pk_curr varchar(20)  ");
		sbcol.append("/*����*/,");
		sbcol.append("receivebank varchar(100)  ");
		sbcol.append("/*�տ�����*/,");
		sbcol.append("receiveaccname varchar(200) ");
		sbcol.append("/*�տλ�ʺ�����*/,");
		sbcol.append("receivebankacc varchar(100) ");
		sbcol.append("/* �տλ�����ʺ� */,");
		sbcol.append("receiveunitname varchar(200)  ");
		sbcol.append("/* �տλ���� */,");
		sbcol.append("def5 varchar(200)  ");
		sbcol.append("/* �Զ�����5 */,");
		sbcol.append("def4 varchar(200)  ");
		sbcol.append("/* �Զ�����4 */,");
		sbcol.append("def3 varchar(200)  ");
		sbcol.append("/* �Զ�����3 */,");
		sbcol.append("def2 varchar(200)  ");
		sbcol.append("/* �Զ�����2 */,");
		sbcol.append("def1 varchar(200)  ");
		sbcol.append("/* �Զ�����1 */,");
		sbcol.append("orientation varchar(20)  ");
		sbcol.append("/* Ʊ�ݷ��� */,");
		sbcol.append("receiveunit varchar(100)  ");
		sbcol.append("/* �տλ */,");
		sbcol.append("disable char(1)  ");
		sbcol.append("/* ��Ʊ��־ */,");
		sbcol.append("paybank varchar(100)  ");
		sbcol.append("/* �������� */,");
		sbcol.append("paybankacc varchar(100)  ");
		sbcol.append("/* ���λ�����ʺ� */,");
		sbcol.append("keepunit varchar(100)  ");
		sbcol.append("/* ��ŵص� */,");
		sbcol.append("payunit varchar(100)  ");
		sbcol.append("/* ���λ */,");
		sbcol.append("invoiceunitname varchar(200)  ");
		sbcol.append("/* ��Ʊ��λ���� */,");
		sbcol.append("invoiceunit varchar(100)  ");
		sbcol.append("/* ��Ʊ��λ */,");
		sbcol.append("fbmbilltype varchar(50)  ");
		sbcol.append("/* Ʊ������ */,");
		sbcol.append("fbmbillno varchar(256)  ");
		sbcol.append("/* Ʊ�ݱ�� */,");
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
