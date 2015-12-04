package nc.ui.fbm.pub.reffilter;

import nc.bs.logging.Logger;
import nc.ui.bd.ref.IRefConst;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.pub.BusinessException;

public class BankAccUsePowerFilter extends BillItemRefModelFilter {

	private String custKey;
	
	public BankAccUsePowerFilter(AbstractBillUI ui,String custKey){
		super();
		this.ui=ui;
		this.custKey = custKey;
	}

	@Override
	protected String getSelfFilterString() {
		String pk_cust = (String)((FBMManageUI)ui).getBillCardPanel().getHeadItem(custKey).getValueObject();
		String pk_corp = "";
		if(pk_cust!=null){
			try {
				pk_corp = FBMClientInfo.queryCorpByCust(pk_cust);
			} catch (BusinessException e) {
				Logger.error(e.getMessage(),e);
			}
		}
		if(pk_corp == null){//外部客商
			return "v_fbm_bankaccbas.pk_bankaccbas in(select pk_accbank from bd_custbank where pk_cubasdoc='"+pk_cust+"')";
		}else if(!pk_corp.equals("")){//用户选择了单位
			if (pk_corp.equals(IRefConst.GROUPCORP)) {
				return " ( isauthedusepower='Y' and usepowerflag='Y' ) or (v_fbm_bankaccbas.pk_corp='FBMC')";
			} else {
				if(pk_corp.equals("FBMC")){
					return " bd_bankaccauth.pk_corp ='" + pk_corp
						+ "' and isauthedusepower='Y' and usepowerflag='Y'  or v_fbm_bankaccbas.pk_corp='FBMC'";
				}else {
					return " bd_bankaccauth.pk_corp ='" + pk_corp
					+ "' and isauthedusepower='Y' and usepowerflag='Y' ";
				}
			}
		}else{
			return " v_fbm_bankaccbas.ownercorp in(select pk_corp1 from bd_cumandoc join bd_cubasdoc  on bd_cumandoc.pk_cubasdoc =  bd_cubasdoc.pk_cubasdoc where bd_cumandoc.pk_corp='"
			+ ClientEnvironment.getInstance().getCorporation().getPrimaryKey()
			+"') or v_fbm_bankaccbas.ownercorp is null";
		}
		
	}

}
