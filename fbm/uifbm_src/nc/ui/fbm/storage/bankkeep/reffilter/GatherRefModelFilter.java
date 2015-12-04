/**
 *
 */
package nc.ui.fbm.storage.bankkeep.reffilter;

import java.util.ArrayList;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.base.IBillOperate;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.pub.AggregatedValueObject;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-8-21
 *
 */
public class GatherRefModelFilter extends BillItemRefModelFilter {
	
	
	public GatherRefModelFilter(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSelfFilterString() {
		String sql =  " registerstatus= '"+FbmStatusConstant.REGISTER+"' and disable='N' ";
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
		return "("+sql+")";
}
}
