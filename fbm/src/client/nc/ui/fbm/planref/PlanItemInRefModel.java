package nc.ui.fbm.planref;

import nc.ui.fp.pub.PlanItemRefModel;


/**
 * �������֧��ƻ���Ŀ����
 */

public class PlanItemInRefModel  extends PlanItemRefModel{

	public String getWherePart() {
    	//�ƻ���Ŀ��֧����0-�գ�1-֧��2-����
	    return super.getWherePart() + "and ioflag =0";
	}

}