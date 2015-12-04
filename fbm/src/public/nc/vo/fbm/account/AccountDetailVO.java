package nc.vo.fbm.account;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class AccountDetailVO extends SuperVO {

	private String pk_detail;
	
	private String pk_baseinfo;
	
	private String pk_type;
	
	private UFDouble moneyy;
	
	private UFDate liquidationdate;
	
	private UFDate busdate;
	
	private String pk_action;
	
	private String pk_org;
	
	private String reason;
	
	private Integer dr;
	
	private UFDateTime ts;
	
	private UFBoolean isliquid;
	
	/**  ’∆±µ«º«pk */
	private String pk_register;
	
	
	/**
	 * @return the pk_register
	 */
	public String getPk_register() {
		return pk_register;
	}

	/**
	 * @param pk_register the pk_register to set
	 */
	public void setPk_register(String pk_register) {
		this.pk_register = pk_register;
	}

	/**
	 * @return the isliquid
	 */
	public UFBoolean getIsliquid() {
		return isliquid;
	}

	/**
	 * @param isliquid the isliquid to set
	 */
	public void setIsliquid(UFBoolean isliquid) {
		this.isliquid = isliquid;
	}

	public UFDate getBusdate() {
		return busdate;
	}

	public void setBusdate(UFDate busdate) {
		this.busdate = busdate;
	}

	public UFDate getLiquidationdate() {
		return liquidationdate;
	}

	public void setLiquidationdate(UFDate liquidationdate) {
		this.liquidationdate = liquidationdate;
	}

	public UFDouble getMoneyy() {
		return moneyy;
	}

	public void setMoneyy(UFDouble moneyy) {
		this.moneyy = moneyy;
	}

	public String getPk_action() {
		return pk_action;
	}

	public void setPk_action(String pk_action) {
		this.pk_action = pk_action;
	}

	public String getPk_baseinfo() {
		return pk_baseinfo;
	}

	public void setPk_baseinfo(String pk_baseinfo) {
		this.pk_baseinfo = pk_baseinfo;
	}

	public String getPk_detail() {
		return pk_detail;
	}

	public void setPk_detail(String pk_detail) {
		this.pk_detail = pk_detail;
	}

	public String getPk_type() {
		return pk_type;
	}

	public void setPk_type(String pk_type) {
		this.pk_type = pk_type;
	}

	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return "pk_detail";
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "fbm_accountdetail";
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

	public String getPk_org() {
		return pk_org;
	}

	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
}
