/**
 *
 */
package nc.ui.fbm.storage.innerback;

import nc.ui.fbm.storage.innerkeep.InnerKeepEventHandler;
import nc.ui.fbm.storage.innerkeep.InnerKeepUI;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 
 * <p>创建人：lpf
 * <b>日期：2007-10-9
 *
 */
public class InnerBackEventHandler extends InnerKeepEventHandler {

	/**
	 * @param billUI
	 * @param control
	 */
	public InnerBackEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String getHeadCondition() {
		StringBuffer whereSql = new StringBuffer();
		StoragePowerVO power = ((InnerKeepUI)getUI()).getPower();
		
		String statusfilter = "(fbm_storage.vbillstatus="+ String.valueOf(IFBMStatus.INPUT_SUCCESS)
			+ " or fbm_storage.vbillstatus="+ String.valueOf(IFBMStatus.OUTPUT_SUCCESS)
			+ " or fbm_storage.vbillstatus="+ String.valueOf(IFBMStatus.SUBMIT)
			+ " or fbm_storage.vbillstatus="+ String.valueOf(IFBMStatus.RETURNED) + " )";
		
		String centerstatus =  "( fbm_storage.vbillstatus="+ String.valueOf(IFBMStatus.INPUT_SUCCESS)
		+ " or fbm_storage.vbillstatus="+ String.valueOf(IFBMStatus.OUTPUT_SUCCESS)
		+ " or fbm_storage.vbillstatus="+ String.valueOf(IFBMStatus.SUBMIT)
		+ " or fbm_storage.vbillstatus="+ String.valueOf(IFBMStatus.RETURNED) + " )";

		if(power.isSettleCenter()){
			whereSql.append(" (fbm_storage.pk_corp ='" + ClientInfo.getCorpPK()
					+ "' or ((fbm_storage.outputunit in ("+ getsubCorpCustPK(ClientInfo.getCorpPK())
					+ ")) and "+centerstatus+")) ") ;
		}else{
			whereSql.append(" (fbm_storage.pk_corp ='"+ClientInfo.getCorpPK()+"' or (fbm_storage.outputunit='"+power.getPk_cubasdoc()+"' and "+statusfilter+")) ");
		}
		whereSql.append(" and fbm_storage.pk_billtypecode = '"+getUIController().getBillType()+"' ");
		return whereSql.toString();
	}
	
	
}
