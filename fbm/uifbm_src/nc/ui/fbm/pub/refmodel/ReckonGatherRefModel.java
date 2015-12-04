package nc.ui.fbm.pub.refmodel;

import nc.ui.tm.framework.ref.filter.RefDataFilter;

/**
 * 
 * <p>
 *   收票参照，用于调剂清算
 * </p>
 * @author bsrl
 * @date 2007-11-01
 * @version V5.02 
 */
public class ReckonGatherRefModel  extends DefaultGatherRefModel{
	
	protected RefDataFilter createRefFilter() {
		RefDataFilter m_RefFilter_child_1 = new RefDataFilter();
		//设置过滤的列   过滤废票
		m_RefFilter_child_1.setFilterIndex(new int[]{getFieldIndex("disable")});
		//设置过滤的值
		m_RefFilter_child_1.setFilterValue(new String[][]{{"Y"}});
		m_RefFilter_child_1.setRefPane(this.getParent());
		
		RefDataFilter m_RefFilter_child_2 =  new RefDataFilter(m_RefFilter_child_1);
		//设置过滤的列    票据状态过滤
		m_RefFilter_child_2.setFilterIndex(new int[]{getFieldIndex("registerstatus")});
		//设置过滤的值
		m_RefFilter_child_2.setFilterValue(new String[][]{{"has_inner_keep", "has_relief"}});
		m_RefFilter_child_2.setReverse(true);
		m_RefFilter_child_2.setRefPane(this.getParent());
		
		RefDataFilter m_RefFilter_child = new RefDataFilter(m_RefFilter_child_2);
		m_RefFilter_child.setFilterIndex(new int[]{getFieldIndex("pk_billtypecode")});
		//设置过滤的值（过滤应付票据）
		m_RefFilter_child.setFilterValue(new String[][]{{"36GA"}});
		m_RefFilter_child.setReverse(true);
		
		RefDataFilter m_RefFilter = new RefDataFilter(m_RefFilter_child);
		//设置过滤的列  收付标志
		m_RefFilter.setFilterIndex(new int[]{getFieldIndex("sfflag")});
		//设置过滤的值
		m_RefFilter.setFilterValue(new String[][]{{"Y"}});
		m_RefFilter.setReverse(true);
		m_RefFilter.setRefPane(this.getParent());
		
		return m_RefFilter;
	}
}
