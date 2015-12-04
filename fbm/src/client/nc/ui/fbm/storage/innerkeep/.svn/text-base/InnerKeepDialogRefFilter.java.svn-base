package nc.ui.fbm.storage.innerkeep;

import java.util.List;

import nc.itf.cdm.util.CommonUtil;
import nc.itf.cdm.util.SqlUtil;
import nc.ui.querytemplate.CriteriaChangedEvent;
import nc.ui.querytemplate.ICriteriaChangedListener;
import nc.ui.querytemplate.ICriteriaEditor;
import nc.ui.querytemplate.filtereditor.DefaultFilterEditor;
import nc.ui.querytemplate.filtereditor.FilterEditorChangedUtil;
import nc.ui.querytemplate.filtereditor.IFilterEditor;
import nc.ui.querytemplate.simpleeditor.SimpleEditor;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.sf.pub.OrgTypeVO;

public class InnerKeepDialogRefFilter implements ICriteriaChangedListener {
	private ICriteriaEditor criteriaEditor;
	private InnerKeepUI ui;
	
	public InnerKeepDialogRefFilter(InnerKeepUI ui) {
		super();
		this.ui = ui;
	}

	public void criteriaChanged(CriteriaChangedEvent event) {
		criteriaEditor = event.getCriteriaEditor();
		if (CriteriaChangedEvent.FILTEREDITOR_INITIALIZED == event
				.getEventtype()) {
			initRefWherePart(event);
		}
	}

	private void initRefWherePart(CriteriaChangedEvent event) {
		if("fbm_storage.keepunit".equals(event.getFieldCode())){
			String sql = null;
			StoragePowerVO power = ui.getPower();
			String pk_cubasdoc = power.getPk_cubasdoc();
			if(power.isSettleCenter()){
				sql = SubCustDocCondition.getCusDocFilterContidtion();
				if(power.isSettleUnit()){
					sql = sql+" or bd_cubasdoc.pk_cubasdoc='"+pk_cubasdoc+"'";
				}
			}else{
				sql = " bd_cubasdoc.pk_cubasdoc='"+pk_cubasdoc+"' ";
			}
			sql = " and ("+sql+") and custprop<>0 ";
			setRefWherePart(event.getFieldCode(), sql);
		}
	}

	private void setRefWherePart(String fieldCode, String where) {
		if(null != criteriaEditor && SimpleEditor.class.isAssignableFrom(criteriaEditor.getClass())) {
		    SimpleEditor se = (SimpleEditor) criteriaEditor;      
		    List<IFilterEditor> filterEditors = se.getFilterEditorsByCode(fieldCode);
			for(IFilterEditor filterEditor : filterEditors) {
				if(null != filterEditor && DefaultFilterEditor.class.isAssignableFrom(filterEditor.getClass())) {
					FilterEditorChangedUtil.setRefWherePart((DefaultFilterEditor) filterEditor, where);
				}
			}
		}
	}
	
	
}
