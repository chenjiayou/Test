package nc.vo.fbm.report;

import nc.vo.fvm.fundcashquery.IVarNameDefine;
import nc.vo.pub.SuperVO;

public class BcbBodyMaxVO extends SuperVO implements IReportTempTable,  IVarNameDefine{
	
	public Integer serialnum;
	public String pk_bill;//操作业务单据PK
	
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
		return "fbm_action_max";
	}

	public String getTableCols() {
		// TODO Auto-generated method stub
		StringBuffer sbcol = new StringBuffer();
		sbcol.append("serialnum int, ");
		sbcol.append("pk_bill char(40) ");
		return sbcol.toString();
	}

	public String getTableIndexs() {
		// TODO Auto-generated method stub
		return "pk_bill";
	}

	public String[] getVaribleNames() {
		// TODO Auto-generated method stub
		return this.getAttributeNames();
	}

	public Integer getSerialnum() {
		return serialnum;
	}

	public void setSerialnum(Integer serialnum) {
		this.serialnum = serialnum;
	}

	public String getPk_bill() {
		return pk_bill;
	}

	public void setPk_bill(String pk_bill) {
		this.pk_bill = pk_bill;
	}

	
}
