/**
 *
 */
package nc.ui.fbm.returnbill.reffilter;

import java.util.ArrayList;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.IBillOperate;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 * <p>
 * 退票类型编辑设置票据编号参照过滤
 * <p>
 * 创建人：lpf <b>日期：2007-8-31
 * 
 */
public class ReturnTypeRefModelFilter extends BillItemRefModelFilter {
	private FBMManageUI ui;
	/**
	 * @param billitem
	 */
	public ReturnTypeRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	
	public ReturnTypeRefModelFilter(FBMManageUI ui,BillItem billitem) {
		super(billitem);
		this.ui = ui;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {
		String ret = "";
		UIComboBox returnTypeBox = (UIComboBox) getBillItem().getComponent();
		String returnType = (String) returnTypeBox.getSelectdItemValue();
		if (CommonUtil.isNull(returnType)) {
			return ret;
		}
		String notUnistorage = " and (isnull(gathertype,'') <> '" + FbmBusConstant.GATHER_TYPE_UNISTORAGE + "' or  gathertype is null)";
		String unistorage =" and gathertype= '" + FbmBusConstant.GATHER_TYPE_UNISTORAGE + "'";
		if (returnType.equalsIgnoreCase(FbmBusConstant.BACK_TYPE_DISABLE)) {
			ret = " (registerstatus in('" + FbmStatusConstant.HAS_DISABLE + "','"+FbmStatusConstant.REGISTER+"') ) and disable='Y' " + notUnistorage;
		} else if (returnType.equalsIgnoreCase(FbmBusConstant.BACK_TYPE_ENDORE)) {
			ret = " registerstatus='" + FbmStatusConstant.HAS_ENDORE + "' " ;//+ notUnistorage;
		} else if (returnType.equalsIgnoreCase(FbmBusConstant.BACK_TYPE_GATHER)) {
			ret = " registerstatus in('" + FbmStatusConstant.REGISTER + "') and disable='N' " + notUnistorage;
		} else if (returnType.equalsIgnoreCase(FbmBusConstant.BACK_TYPE_INVOICE)) {
			ret = " registerstatus='" + FbmStatusConstant.HAS_PAYBILL + "' " + notUnistorage;
		} else if (returnType.equalsIgnoreCase(FbmBusConstant.BACK_TYPE_UNISTORAGE)){
			ret = " registerstatus  in('"+FbmStatusConstant.HAS_DISABLE+"','"+FbmStatusConstant.REGISTER+"')" + unistorage;
		}else if (returnType.equalsIgnoreCase(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT)){
			ret = " registerstatus  ='"+FbmStatusConstant.HAS_CENTER_USE+"' and exists(select 1 from fbm_action where fbm_action.pk_baseinfo =gathertable.pk_baseinfo and fbm_action.endstatus = 'has_center_return' and serialnum=(select max(serialnum) from fbm_action where  fbm_action.pk_baseinfo =gathertable.pk_baseinfo))" ;
		}
		
		AggregatedValueObject vo = ui.getBufferData().getCurrentVO();
		if(vo!=null&&vo.getChildrenVO()!=null&&IBillOperate.OP_EDIT==ui.getBillOperate()){
			CircularlyAccessibleValueObject[] vos = vo.getChildrenVO();
			ArrayList<String> pks = new ArrayList<String>();
			if(!CommonUtil.isNull(vos)){
				String pkfilter = " or pk_register in(";
				for (int i = 0; i < vos.length; i++) {
					String key = (String) vos[i].getAttributeValue(StorageBVO.PK_SOURCE);
					if(!CommonUtil.isNull(key)&&!pks.contains(key)){
						if(i>0){
							pkfilter = pkfilter+",";
						}
						pks.add(key);
						pkfilter = pkfilter+"'"+key+"'";
					}
				}
				if(pks.size()>0)
					ret = "("+ret+")"+pkfilter+")";			
			}
		}
		return "("+ret+")";
	}

}
