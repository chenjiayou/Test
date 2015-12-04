package nc.ui.fbm.relief;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.storage.innerkeep.SubCustDocCondition;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillItem;

public class ReliefUnitRefModelFilter  extends nc.ui.tm.framework.ref.filter.BillItemRefModelFilter {
	private FBMManageUI ui;

	public ReliefUnitRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}
	
	public ReliefUnitRefModelFilter(BillItem billitem, FBMManageUI ui) {
		super(billitem);
		this.ui = ui;
	}

	@Override
	protected String getSelfFilterString() {
		String sqlfilter = null;
		
		String pk_corp = FBMClientInfo.getCorpPK();
		String pk_cubasdoc = FBMClientInfo.getCorpCubasdoc(pk_corp);
		
		if(FBMClientInfo.isSettleCenter()){//Ω·À„÷––ƒ
			sqlfilter = SubCustDocCondition.getCusDocFilterContidtion();
			if(FBMClientInfo.isSettleUnit(pk_corp)){
				sqlfilter = sqlfilter+" or bd_cubasdoc.pk_cubasdoc='"+pk_cubasdoc+"'";
				sqlfilter = sqlfilter
						+ " and bd_cubasdoc.pk_corp1 <> '"
						+ ClientEnvironment.getInstance().getCorporation().getPk_corp()
						+ "'";
			}
		}else{
			sqlfilter = " bd_cubasdoc.pk_cubasdoc='"+pk_cubasdoc+"' ";
		}
		return "("+sqlfilter+") and custprop<>0 ";
	}
}
