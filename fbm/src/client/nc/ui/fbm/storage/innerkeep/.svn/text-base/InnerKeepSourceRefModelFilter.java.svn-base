/**
 *
 */
package nc.ui.fbm.storage.innerkeep;

import java.util.ArrayList;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.IBillOperate;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;

/**
 * <p>
 * 内部存放票据编号根据入库单位过滤
 * <p>创建人：lpf
 * <b>日期：2007-10-11
 *
 */
public class InnerKeepSourceRefModelFilter extends BillItemRefModelFilter {
	private FBMManageUI ui;
	/**
	 * @param billitem
	 */
	public InnerKeepSourceRefModelFilter(BillItem billitem) {
		super(billitem);
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	public InnerKeepSourceRefModelFilter(BillItem billitem,FBMManageUI ui) {
		super(billitem);
		this.ui=ui;
	}

	@Override
	protected String getSelfFilterString() {
		String pk_corp1 =null;
		String pk_cubasdoc = (String) ui.getBillCardPanel().getHeadItem(StorageVO.KEEPUNIT).getValueObject();
		String pk_curr = (String) ui.getBillCardPanel().getHeadItem(StorageVO.PK_CURRTYPE).getValueObject();
//		String pk_account = (String) ui.getBillCardPanel().getHeadItem(StorageVO.KEEPACCOUNT).getValueObject();
		if(!CommonUtil.isNull(pk_cubasdoc)){
			UIRefPane refpane = (UIRefPane) ui.getBillCardPanel().getHeadItem(StorageVO.KEEPUNIT).getComponent();
			pk_corp1 = (String) refpane.getRefValue("bd_cubasdoc.pk_corp1");
		}
		
		if(CommonUtil.isNull(pk_corp1)){
			pk_corp1 = ClientEnvironment.getInstance().getCorporation().getPk_corp();
		}

		String sql = " pk_corp='"+ pk_corp1+ "' and registerstatus= '" + FbmStatusConstant.REGISTER + "' and sfflag='Y' and disable='N' ";
		String pk_currtype = null;
		if(!CommonUtil.isNull(pk_curr)){
			UIRefPane refpane = (UIRefPane) ui.getBillCardPanel().getHeadItem(StorageVO.PK_CURRTYPE).getComponent();
			pk_currtype = (String) refpane.getRefValue("pk_currtype");
			sql = sql+" and pk_curr='"+pk_currtype+"'";
		}
		
		AggregatedValueObject vo = ui.getBufferData().getCurrentVO();
		
		if(vo!=null&&vo.getChildrenVO()!=null&&IBillOperate.OP_EDIT==ui.getBillOperate()){
			StorageBVO[] vos = (StorageBVO[]) vo.getChildrenVO();
			ArrayList<String> pks = new ArrayList<String>();
			if(!CommonUtil.isNull(vos)){
				String pkfilter = " or ( pk_corp='"+ pk_corp1+ "' and  pk_register in(";
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
				if(pks.size()>0) {
					if(pk_currtype != null)
					    sql = "("+sql+")"+pkfilter+") and pk_curr = '"+pk_currtype+"')";	
					else 
						sql = "("+sql+")"+pkfilter+")";	
				}
			}
		}
		return "("+sql+")";
	}
	
}
