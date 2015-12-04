package nc.ui.fbm.pub.refmodel;

import nc.ui.tm.framework.ref.filter.RefDataFilter;

/**
 * 
 * <p>
 *   ��Ʊ���գ����ڵ�������
 * </p>
 * @author bsrl
 * @date 2007-11-01
 * @version V5.02 
 */
public class ReckonGatherRefModel  extends DefaultGatherRefModel{
	
	protected RefDataFilter createRefFilter() {
		RefDataFilter m_RefFilter_child_1 = new RefDataFilter();
		//���ù��˵���   ���˷�Ʊ
		m_RefFilter_child_1.setFilterIndex(new int[]{getFieldIndex("disable")});
		//���ù��˵�ֵ
		m_RefFilter_child_1.setFilterValue(new String[][]{{"Y"}});
		m_RefFilter_child_1.setRefPane(this.getParent());
		
		RefDataFilter m_RefFilter_child_2 =  new RefDataFilter(m_RefFilter_child_1);
		//���ù��˵���    Ʊ��״̬����
		m_RefFilter_child_2.setFilterIndex(new int[]{getFieldIndex("registerstatus")});
		//���ù��˵�ֵ
		m_RefFilter_child_2.setFilterValue(new String[][]{{"has_inner_keep", "has_relief"}});
		m_RefFilter_child_2.setReverse(true);
		m_RefFilter_child_2.setRefPane(this.getParent());
		
		RefDataFilter m_RefFilter_child = new RefDataFilter(m_RefFilter_child_2);
		m_RefFilter_child.setFilterIndex(new int[]{getFieldIndex("pk_billtypecode")});
		//���ù��˵�ֵ������Ӧ��Ʊ�ݣ�
		m_RefFilter_child.setFilterValue(new String[][]{{"36GA"}});
		m_RefFilter_child.setReverse(true);
		
		RefDataFilter m_RefFilter = new RefDataFilter(m_RefFilter_child);
		//���ù��˵���  �ո���־
		m_RefFilter.setFilterIndex(new int[]{getFieldIndex("sfflag")});
		//���ù��˵�ֵ
		m_RefFilter.setFilterValue(new String[][]{{"Y"}});
		m_RefFilter.setReverse(true);
		m_RefFilter.setRefPane(this.getParent());
		
		return m_RefFilter;
	}
}
