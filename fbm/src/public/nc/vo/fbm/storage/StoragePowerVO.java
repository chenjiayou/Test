/**
 *
 */
package nc.vo.fbm.storage;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;

/**
 * <p>
 * 权限VO,是否结算单位，是否结算中心，对应结算中心，对应公司，公司对应客商
 * <p>创建人：lpf
 * <b>日期：2007-11-28
 *
 */
public class StoragePowerVO extends SuperVO {
	private boolean isSettleCenter;
	private boolean isSettleUnit;
	private String pk_settlecenter;
	private String pk_corp;
	private String pk_cubasdoc;
	private String rela_corp;//暂存入库单位的公司编码，防止频繁转换
	private String currUserPK;//当前用户
	private UFDate currDate;//当前日期
	private boolean isUnitBill; //暂存当前单据是否是单位的单据（提供给单据制证权限控制）
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public StoragePowerVO() {
		// TODO Auto-generated constructor stub
	}

	
	public boolean isSettleCenter() {
		return isSettleCenter;
	}


	public void setSettleCenter(boolean isSettleCenter) {
		this.isSettleCenter = isSettleCenter;
	}


	public boolean isSettleUnit() {
		return isSettleUnit;
	}


	public void setSettleUnit(boolean isSettleUnit) {
		this.isSettleUnit = isSettleUnit;
	}


	public String getPk_settlecenter() {
		return pk_settlecenter;
	}


	public void setPk_settlecenter(String pk_settlecenter) {
		this.pk_settlecenter = pk_settlecenter;
	}


	public String getPk_corp() {
		return pk_corp;
	}


	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}


	public String getPk_cubasdoc() {
		return pk_cubasdoc;
	}


	public void setPk_cubasdoc(String pk_cubasdoc) {
		this.pk_cubasdoc = pk_cubasdoc;
	}


	public String getRela_corp() {
		return rela_corp;
	}


	public void setRela_corp(String rela_corp) {
		this.rela_corp = rela_corp;
	}


	public String getCurrUserPK() {
		return currUserPK;
	}


	public void setCurrUserPK(String currUserPK) {
		this.currUserPK = currUserPK;
	}


	public UFDate getCurrDate() {
		return currDate;
	}


	public void setCurrDate(UFDate currDate) {
		this.currDate = currDate;
	}

	public boolean isUnitBill() {
		return isUnitBill;
	}


	public void setUnitBill(boolean isUnitBill) {
		this.isUnitBill = isUnitBill;
	}


	/* (non-Javadoc)
	 * @see nc.vo.pub.SuperVO#getPKFieldName()
	 */
	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.vo.pub.SuperVO#getParentPKFieldName()
	 */
	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.vo.pub.SuperVO#getTableName()
	 */
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
