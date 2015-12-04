package nc.vo.fbm.pub;

import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

public class BusiActionParamVO <T> extends SuperVO{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pk_source;//收票主键

	private String pk_baseinfo;//票据基本信息主键
	
	private String pk_bill;//单据主键
	
	private UFDouble moneyy;//金额
	
	private String unit_a;//单位A
	
	private String unit_b;//单位B
	
	private String unit_c;//单位C
	
	private String actionperson;//操作员
	
	private UFDate actiondate;//操作日期
	
	private String billtype;//单据类型
	
	private String actioncode;//动作编码
	
	private String sourcefield;//收票、开票PK字段名称
	
	private String pk_org;//组织
	
	private T superVO;//推式生成的时候用到，在自己的实际类中再进行转型
	
	private RegisterVO registerVO;
	
	private BaseinfoVO baseinfoVO;
	
	private ActionVO lastActionVO;
	
	private boolean forUpgrade = false;
	
	private String pk_corp ;

	private String upgrade;
	/**
	 * 目前abstractAction在做操作时重新查询SuperVO致使界面传递的一些数据丢失
	 * 因此增加viewVO保存界面传递过来的数据
	 */
	private SuperVO viewVO;
	
	
	public String getPk_source() {
		return pk_source;
	}

	public void setPk_source(String pk_source) {
		this.pk_source = pk_source;
	}

	
	
	/**
	 * @return the viewVO
	 */
	public SuperVO getViewVO() {
		return viewVO;
	}

	/**
	 * @param viewVO the viewVO to set
	 */
	public void setViewVO(SuperVO viewVO) {
		this.viewVO = viewVO;
	}

	public String getPk_bill() {
		return pk_bill;
	}

	public void setPk_bill(String pk_bill) {
		this.pk_bill = pk_bill;
	}
	
	

	public UFDouble getMoneyy() {
		return moneyy;
	}

	public void setMoneyy(UFDouble moneyy) {
		this.moneyy = moneyy;
	}

	public String getUnit_a() {
		return unit_a;
	}

	public void setUnit_a(String unit_a) {
		this.unit_a = unit_a;
	}

	public String getUnit_b() {
		return unit_b;
	}

	public void setUnit_b(String unit_b) {
		this.unit_b = unit_b;
	}

	public String getUnit_c() {
		return unit_c;
	}

	public void setUnit_c(String unit_c) {
		this.unit_c = unit_c;
	}

	
	
	public String getPk_baseinfo() {
		return pk_baseinfo;
	}

	public void setPk_baseinfo(String pk_baseinfo) {
		this.pk_baseinfo = pk_baseinfo;
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



	public UFDate getActiondate() {
		return actiondate;
	}

	public void setActiondate(UFDate actiondate) {
		this.actiondate = actiondate;
	}

	public String getActionperson() {
		return actionperson;
	}

	public void setActionperson(String actionperson) {
		this.actionperson = actionperson;
	}

	public String getActioncode() {
		return actioncode;
	}

	public void setActioncode(String actioncode) {
		this.actioncode = actioncode;
	}

	public String getBilltype() {
		return billtype;
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	public String getSourcefield() {
		return sourcefield;
	}

	public void setSourcefield(String sourcefield) {
		this.sourcefield = sourcefield;
	}

	public String getPk_org() {
		return pk_org;
	}

	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}

	public T getSuperVO() {
		return superVO;
	}

	public void setSuperVO(T superVO) {
		this.superVO = superVO;
	}

	public RegisterVO getRegisterVO() {
		return registerVO;
	}

	public void setRegisterVO(RegisterVO registerVO) {
		this.registerVO = registerVO;
	}

	public BaseinfoVO getBaseinfoVO() {
		return baseinfoVO;
	}

	public void setBaseinfoVO(BaseinfoVO baseinfoVO) {
		this.baseinfoVO = baseinfoVO;
	}

	public ActionVO getLastActionVO() {
		return lastActionVO;
	}

	public void setLastActionVO(ActionVO lastActionVO) {
		this.lastActionVO = lastActionVO;
	}

	public boolean isForUpgrade() {
		return forUpgrade;
	}

	public void setForUpgrade(boolean forUpgrade) {
		this.forUpgrade = forUpgrade;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(String upgrade) {
		this.upgrade = upgrade;
	}

	

}
