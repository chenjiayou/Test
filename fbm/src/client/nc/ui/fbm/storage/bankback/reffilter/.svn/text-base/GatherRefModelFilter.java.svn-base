/**
 *
 */
package nc.ui.fbm.storage.bankback.reffilter;

import java.util.ArrayList;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.base.IBillOperate;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;

/**
 * <p>
 * 1113增加废票过滤
 * <p>创建人：lpf
 * <b>日期：2007-8-21
 *
 */
public class GatherRefModelFilter extends BillItemRefModelFilter {


	public GatherRefModelFilter(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {
		String sql =   " registerstatus= '"+FbmStatusConstant.HAS_BANK_KEEP+"' and disable='N' ";
		AggregatedValueObject vo = ui.getBufferData().getCurrentVO();
		if(vo!=null&&vo.getChildrenVO()!=null&&IBillOperate.OP_EDIT==ui.getBillOperate()){
			StorageBVO[] vos = (StorageBVO[]) vo.getChildrenVO();
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
					sql = "("+sql+")"+pkfilter+")";			
			}
		}
		//托管银行
		BillItem keepbankItem = ((FBMManageUI)ui).getBillCardPanel().getHeadItem(StorageVO.KEEPUNIT);
		if(keepbankItem !=null){
			Object bank = keepbankItem.getValueObject();
			if(bank !=null){
				//取序列号小一位的action，肯定是银行存放
				String querySql = "select 1 from fbm_storage s left join fbm_action a on s.pk_storage=a.pk_bill where pk_source=gathertable.pk_register";
				sql = sql +" and exists (" +querySql+ "  and s.keepunit='"+bank+"')";
			}
		}
		
		return "("+sql+")";
	}

}
