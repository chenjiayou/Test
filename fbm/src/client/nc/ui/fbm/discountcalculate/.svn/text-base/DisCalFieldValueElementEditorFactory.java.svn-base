/**
 *
 */
package nc.ui.fbm.discountcalculate;

import nc.ui.fbm.pub.refmodel.GatherBusRefModel;
import nc.ui.fi.refmodel.ContractTypeRefModel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.querytemplate.QueryConditionEditorContext;
import nc.ui.querytemplate.meta.FilterMeta;
import nc.ui.querytemplate.valueeditor.DefaultFieldValueElementEditorFactory;
import nc.ui.querytemplate.valueeditor.IFieldValueElementEditor;
import nc.ui.querytemplate.valueeditor.IFieldValueElementEditorFactory;
import nc.ui.querytemplate.valueeditor.RefElementEditor;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.SQLRefModelFilter;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.tm.enumeration.IContractType;

/**
 * @author bsrl
 *
 */
public class DisCalFieldValueElementEditorFactory implements
		IFieldValueElementEditorFactory {

	 private DefaultFieldValueElementEditorFactory defaultFactor;
	private QueryConditionEditorContext context;
	private String[] descriptor;

	/**
	 *
	 */
	public DisCalFieldValueElementEditorFactory(
			QueryConditionEditorContext context) {
		this.context = context;
		defaultFactor = new DefaultFieldValueElementEditorFactory(this.context);
		descriptor = new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("36201035","UPP36201035-000000")/* @res"SX,已登记=register,"*/ +
				nc.ui.ml.NCLangRes.getInstance().getStrByID("36201035","UPP36201035-000001")/* @res"在内部存放=on_inner_keep,"*/ +
				nc.ui.ml.NCLangRes.getInstance().getStrByID("36201035","UPP36201035-000002")/* @res"已内部存放=has_inner_keep,"*/ +
				nc.ui.ml.NCLangRes.getInstance().getStrByID("36201035","UPP36201035-000003")/* @res"在内部领用=on_inner_back,"*/ +
				nc.ui.ml.NCLangRes.getInstance().getStrByID("36201035","UPP36201035-000004")/* @res"在银行存放=on_bank_keep,"*/ +
				nc.ui.ml.NCLangRes.getInstance().getStrByID("36201035","UPP36201035-000005")/* @res"已银行存放=has_bank_keep,"*/ +
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000012")/*@res "在质押="*/ + FbmStatusConstant.ON_IMPAWN + "," +
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000013")/*@res "已质押="*/ + FbmStatusConstant.HAS_IMPAWN + "," +
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000014")/*@res "在收票="*/ + FbmStatusConstant.ON_GATHER + "," +
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000015")/*@res "在调剂存放="*/ + FbmStatusConstant.ON_RELIEF_KEEP + "," +
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000016")/*@res "已调剂存放="*/ + FbmStatusConstant.HAS_RELIEF_KEEP + "," +
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000017")/*@res "已调剂领用="*/ + FbmStatusConstant.HAS_RELIEF + "," +
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000018")/*@res "在调剂领用="*/ + FbmStatusConstant.ON_RELIEF + "," +
				nc.ui.ml.NCLangRes.getInstance().getStrByID("36201035","UPP36201035-000006")/* @res"在银行领用=on_bank_back"*/};
	}

	public IFieldValueElementEditor createFieldValueElementEditor(
			FilterMeta meta) {

		if(meta.getFieldCode().equals("v_fbm_regbase.fbillstatus")){
			meta.setValueEditorDescription(descriptor[0]);
			return defaultFactor.createFieldValueElementEditor(meta);
		}

		if ("v_fbm_regbase.pk_register".equals(meta.getFieldCode())) {
			UIRefPane refPane = new UIRefPane();
			refPane.setRefModel(new GatherBusRefModel());
			return new RefElementEditor(refPane, meta.getReturnType());
		}
		return null;
	}

}