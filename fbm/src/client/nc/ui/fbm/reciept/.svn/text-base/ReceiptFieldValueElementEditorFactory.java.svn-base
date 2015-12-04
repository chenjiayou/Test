/**
 * 
 */
package nc.ui.fbm.reciept;

import nc.ui.bd.ref.DefaultRefModel;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.querytemplate.QueryConditionEditorContext;
import nc.ui.querytemplate.meta.FilterMeta;
import nc.ui.querytemplate.valueeditor.IFieldValueElementEditor;
import nc.ui.querytemplate.valueeditor.IFieldValueElementEditorFactory;
import nc.ui.querytemplate.valueeditor.RefElementEditor;

/**
 * @author bsrl
 * 
 */
public class ReceiptFieldValueElementEditorFactory implements
		IFieldValueElementEditorFactory {

	private QueryConditionEditorContext context;

	/**
	 * 
	 */
	public ReceiptFieldValueElementEditorFactory(
			QueryConditionEditorContext context) {
		this.context = context;
	}

	public IFieldValueElementEditor createFieldValueElementEditor(
			FilterMeta meta) {
		//清算单位
		if ("fbm_reckon.reckonunit".equals(meta.getFieldCode())) {
			UIRefPane refPane = new UIRefPane();
//			AbstractTMRefModel refModel = new nc.ui.fbm.pub.refmodel.ReckonInnerCusDocRefModel();
 
			refPane.setRefNodeName("客商基本档案");//不要多语言
			refPane.getRefModel().setUseDataPower(false);
			String pk_corp = FBMClientInfo.getCorpPK();
			refPane.setPK(FBMClientInfo.getCorpCubasdoc(pk_corp));
			refPane.setEditable(false);
			refPane.setEnabled(false);
			refPane.setButtonEnable(false);
			refPane.setButtonFireEvent(false);
			refPane.setButtonVisible(false);//不允许编辑，只能查看当前公司对应客商的回单
			
			return new RefElementEditor(refPane, meta.getReturnType());
		}

		return null;
	}

}
