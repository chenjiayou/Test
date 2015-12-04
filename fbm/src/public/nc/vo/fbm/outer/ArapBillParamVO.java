package nc.vo.fbm.outer;

import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * 收付报单据转换成票据需要的参数对象
 * @author xwq
 *
 */

public class ArapBillParamVO extends SuperVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fbmbillno;//票据编号
	
	private String memo;//备注
	
	private String pk_curr;//币种
	
	private UFDouble moneyy;//金额
	private UFDouble moneyf;//辅币金额
	private UFDouble moneyb;//本币金额
	private UFDouble frate;//辅币汇率
	private UFDouble brate;//本币汇率
	
	private String pk_busbill;//票据动作对应单据PK
	
	private String pk_billtypecode;//单据类型
	//是否要校验汇率等字段
	
	private String pk_bill_h;//收付款单主表主键
	
	private String pk_bill_b;//收付款子表主键
	
	private String outerbilltype;//收付报单据类型
	
	private String outerdjdl;//收付单据大类
	
	private String outerstatus;//收付款单据状态
	
	private String currentunit;//本方单位(收款时为收票单位，付款时为付票单位)
	
	/**
	 * 对方类型
	 * 0 客商
	 * 1 部门
	 * 2 人员
	 * 3 散户
	 * 4 银行
	 * 
	 */
	protected int tradertype = 0;//默认0客商
	
	/**
	 * 判断对方类型如果不为客商抛异常提示
	 */
	private String otherunit;//对方单位(收款时为付票单位，付款时为收票单位)

	
	private String pk_corp;
	
	private String voperator;//录入人
	private UFDate doperatdate;//录入日期
	private String veffector;//生效人
	private UFDate deffectdate;//生效日期
	
	private String jsfs;//结算方式
	
	private int row;//行号
	
	private String skbankacc;//收款银行账号
	private String fkbankacc;//付款银行账号
	
	private UFDate djrq;//单据日期
	
	private UFDouble txlx_ybje;//贴现利息
	private UFDouble txfy_ybje;//贴现手续费
	
	private BaseinfoVO baseinfoVO;//基本信息VO
	
	private ActionVO newActionVO;//最新动作VO
	
	private RegisterVO registerVO;//对应的收付票登记单VO
	
	private OuterVO outerVO;//当前单据关联
	
	/**
	 * 是否退票生成
	 */
	private String isReturnBill;
	
	private String pk_plansubj;//计划项目
	
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
