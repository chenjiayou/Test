package nc.ui.fbm.pub.reffilter;

import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * �ڲ��˻��������ֶ����ʻ�
 * @author xwq
 *
 * 2008-10-14
 */
public class InnerAccIncludePartFrozenFilter extends BillItemRefModelFilter{
	
	@Override
	protected String getSelfFilterString() {
		return  " frozenflag in(0,2)";
	}
}
