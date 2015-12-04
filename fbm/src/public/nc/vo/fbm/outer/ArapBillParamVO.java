package nc.vo.fbm.outer;

import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * �ո�������ת����Ʊ����Ҫ�Ĳ�������
 * @author xwq
 *
 */

public class ArapBillParamVO extends SuperVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fbmbillno;//Ʊ�ݱ��
	
	private String memo;//��ע
	
	private String pk_curr;//����
	
	private UFDouble moneyy;//���
	private UFDouble moneyf;//���ҽ��
	private UFDouble moneyb;//���ҽ��
	private UFDouble frate;//���һ���
	private UFDouble brate;//���һ���
	
	private String pk_busbill;//Ʊ�ݶ�����Ӧ����PK
	
	private String pk_billtypecode;//��������
	//�Ƿ�ҪУ����ʵ��ֶ�
	
	private String pk_bill_h;//�ո����������
	
	private String pk_bill_b;//�ո����ӱ�����
	
	private String outerbilltype;//�ո�����������
	
	private String outerdjdl;//�ո����ݴ���
	
	private String outerstatus;//�ո����״̬
	
	private String currentunit;//������λ(�տ�ʱΪ��Ʊ��λ������ʱΪ��Ʊ��λ)
	
	/**
	 * �Է�����
	 * 0 ����
	 * 1 ����
	 * 2 ��Ա
	 * 3 ɢ��
	 * 4 ����
	 * 
	 */
	protected int tradertype = 0;//Ĭ��0����
	
	/**
	 * �ж϶Է����������Ϊ�������쳣��ʾ
	 */
	private String otherunit;//�Է���λ(�տ�ʱΪ��Ʊ��λ������ʱΪ��Ʊ��λ)

	
	private String pk_corp;
	
	private String voperator;//¼����
	private UFDate doperatdate;//¼������
	private String veffector;//��Ч��
	private UFDate deffectdate;//��Ч����
	
	private String jsfs;//���㷽ʽ
	
	private int row;//�к�
	
	private String skbankacc;//�տ������˺�
	private String fkbankacc;//���������˺�
	
	private UFDate djrq;//��������
	
	private UFDouble txlx_ybje;//������Ϣ
	private UFDouble txfy_ybje;//����������
	
	private BaseinfoVO baseinfoVO;//������ϢVO
	
	private ActionVO newActionVO;//���¶���VO
	
	private RegisterVO registerVO;//��Ӧ���ո�Ʊ�Ǽǵ�VO
	
	private OuterVO outerVO;//��ǰ���ݹ���
	
	/**
	 * �Ƿ���Ʊ����
	 */
	private String isReturnBill;
	
	private String pk_plansubj;//�ƻ���Ŀ
	
	/**
	 * @return the isReturnBill
	 */
	public String getIsReturnBill() {
		return isReturnBill;
	}

	/**
	 * @param isReturnBill the isReturnBill to set
	 */
	public void setIsReturnBill(String isReturnBill) {
		this.isReturnBill = isReturnBill;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFbmbillno() {
		return fbmbillno;
	}

	/**
	 * @return the tradertype
	 */
	public int getTradertype() {
		return tradertype;
	}

	/**
	 * @param tradertype the tradertype to set
	 */
	public void setTradertype(int tradertype) {
		this.tradertype = tradertype;
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

	public String getPk_bill_h() {
		return pk_bill_h;
	}

	public void setPk_bill_h(String pk_bill_h) {
		this.pk_bill_h = pk_bill_h;
	}

	public String getPk_bill_b() {
		return pk_bill_b;
	}

	public void setPk_bill_b(String pk_bill_b) {
		this.pk_bill_b = pk_bill_b;
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



	public String getCurrentunit() {
		return currentunit;
	}

	public void setCurrentunit(String currentunit) {
		this.currentunit = currentunit;
	}

	public String getOtherunit() {
		return otherunit;
	}

	public void setOtherunit(String otherunit) {
		this.otherunit = otherunit;
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

	public String getPk_busbill() {
		return pk_busbill;
	}

	public void setPk_busbill(String pk_busbill) {
		this.pk_busbill = pk_busbill;
	}

	public String getVoperator() {
		return voperator;
	}

	public void setVoperator(String voperator) {
		this.voperator = voperator;
	}

	public UFDate getDoperatdate() {
		return doperatdate;
	}

	public void setDoperatdate(UFDate doperatdate) {
		this.doperatdate = doperatdate;
	}

	public String getVeffector() {
		return veffector;
	}

	public void setVeffector(String veffector) {
		this.veffector = veffector;
	}

	public UFDate getDeffectdate() {
		return deffectdate;
	}

	public void setDeffectdate(UFDate deffectdate) {
		this.deffectdate = deffectdate;
	}

	public String getPk_billtypecode() {
		return pk_billtypecode;
	}

	public void setPk_billtypecode(String pk_billtypecode) {
		this.pk_billtypecode = pk_billtypecode;
	}

	public String getJsfs() {
		return jsfs;
	}

	public void setJsfs(String jsfs) {
		this.jsfs = jsfs;
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

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getSkbankacc() {
		return skbankacc;
	}

	public void setSkbankacc(String skbankacc) {
		this.skbankacc = skbankacc;
	}

	public String getFkbankacc() {
		return fkbankacc;
	}

	public void setFkbankacc(String fkbankacc) {
		this.fkbankacc = fkbankacc;
	}

	public UFDate getDjrq() {
		return djrq;
	}

	public void setDjrq(UFDate djrq) {
		this.djrq = djrq;
	}

	public UFDouble getTxlx_ybje() {
		return txlx_ybje;
	}

	public void setTxlx_ybje(UFDouble txlx_ybje) {
		this.txlx_ybje = txlx_ybje;
	}

	public UFDouble getTxfy_ybje() {
		return txfy_ybje;
	}

	public void setTxfy_ybje(UFDouble txfy_ybje) {
		this.txfy_ybje = txfy_ybje;
	}

	public BaseinfoVO getBaseinfoVO() {
		return baseinfoVO;
	}

	public void setBaseinfoVO(BaseinfoVO baseinfoVO) {
		this.baseinfoVO = baseinfoVO;
	}

	public ActionVO getNewActionVO() {
		return newActionVO;
	}

	public void setNewActionVO(ActionVO newActionVO) {
		this.newActionVO = newActionVO;
	}

	public RegisterVO getRegisterVO() {
		return registerVO;
	}

	public void setRegisterVO(RegisterVO registerVO) {
		this.registerVO = registerVO;
	}

	public OuterVO getOuterVO() {
		return outerVO;
	}

	public void setOuterVO(OuterVO outerVO) {
		this.outerVO = outerVO;
	}

	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPk_plansubj() {
		return pk_plansubj;
	}

	public void setPk_plansubj(String pk_plansubj) {
		this.pk_plansubj = pk_plansubj;
	}


	
}
