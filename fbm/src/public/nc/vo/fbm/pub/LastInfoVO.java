package nc.vo.fbm.pub;

import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * <p>
 *   票据最新数据
 *   包括最新动作和基本信息
 * </p>
 * @author xwq
 * @date 2007-9-3
 * @version V5.0
 */
public class LastInfoVO {
	private String pk_action;
	
	private String pk_source;
	
	private String pk_baseinfo;
	
	private String billtype;
	
	private String actioncode;
	
	private String fbmbillno;
	
	private String beginstatus;
	
	private String endstatus;
	
	private UFDouble moneyy;
	
	private UFDate actiondate;

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

	public String getPk_source() {
		return pk_source;
	}

	public void setPk_source(String pk_source) {
		this.pk_source = pk_source;
	}

	public String getFbmbillno() {
		return fbmbillno;
	}

	public void setFbmbillno(String fbmbillno) {
		this.fbmbillno = fbmbillno;
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

	public UFDouble getMoneyy() {
		return moneyy;
	}

	public void setMoneyy(UFDouble moneyy) {
		this.moneyy = moneyy;
	}

	public UFDate getActiondate() {
		return actiondate;
	}

	public void setActiondate(UFDate actiondate) {
		this.actiondate = actiondate;
	}
	
	
	
	
}
