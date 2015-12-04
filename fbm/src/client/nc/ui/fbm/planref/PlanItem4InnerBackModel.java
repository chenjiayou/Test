package nc.ui.fbm.planref;

import nc.ui.fp.pub.PlanItemRefModel;
import nc.ui.pub.bill.BillItem;
import nc.vo.fp.pub.IPlanConst;

public class PlanItem4InnerBackModel extends PlanItemRefModel{


	private BillItem item;
	
	public PlanItem4InnerBackModel(){
		super();
	}
			
	public PlanItem4InnerBackModel(BillItem item){
		super();
		this.item = item;
	}
	 
	public String getWherePart() {
	    //�ƻ���Ŀ��֧����0-�գ�1-֧��2-����
	    String pk_corp = item == null ? null :item.getValue();
	    this.setWherePart(" pk_corp='" + pk_corp + "' and isnull(fp_dim_planitem.dr,0)=0 and ioflag =0 and fp_dim_planitem.itemtype=" + IPlanConst.ITEMTYPE_IOITEM );
	    return super.getWherePart()  ;
	}


}
