package nc.ui.fbm.planref;

import nc.ui.fp.pub.PlanItemRefModel;
import nc.ui.pub.bill.BillItem;
import nc.vo.fp.pub.IPlanConst;

/**
 * 
 * @author xwq
 *
 * 2009-1-6
 */
public class PlanItem4InnerKeepModel extends PlanItemRefModel{

	private BillItem item;
	
	public PlanItem4InnerKeepModel(){
		super();
	}
			
	public PlanItem4InnerKeepModel(BillItem item){
		super();
		this.item = item;
	}
	public String getWherePart() {
	    //计划项目收支属性0-收；1-支；2-其他
	    String pk_corp = item == null ? null :item.getValue();
	    this.setWherePart(" pk_corp='" + pk_corp + "' and isnull(fp_dim_planitem.dr,0)=0 and ioflag =1 and fp_dim_planitem.itemtype=" + IPlanConst.ITEMTYPE_IOITEM );
	    return super.getWherePart()  ;
	}
}
