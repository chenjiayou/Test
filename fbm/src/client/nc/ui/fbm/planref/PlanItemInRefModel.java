package nc.ui.fbm.planref;

import nc.ui.fp.pub.PlanItemRefModel;


/**
 * 收入和收支类计划项目参照
 */

public class PlanItemInRefModel  extends PlanItemRefModel{

	public String getWherePart() {
    	//计划项目收支属性0-收；1-支；2-其他
	    return super.getWherePart() + "and ioflag =0";
	}

}