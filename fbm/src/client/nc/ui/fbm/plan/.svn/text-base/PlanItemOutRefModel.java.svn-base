package nc.ui.fbm.plan;


/**
 * 支出和收支类计划项目参照
 */
import nc.ui.bd.ref.AbstractRefTreeModel;

public class PlanItemOutRefModel extends AbstractRefTreeModel {

	/**
	 * 显示字段列表
	 */
	public java.lang.String[] getFieldCode() {
	    setRefCodeField("itemcode");
	    setRefNameField("itemname");
	    return new String[] { "itemcode", "itemname", "pk_planitem", "pk_corp","pk_source","pk_supersource","pk_parent","ioflag","pk_plangroup","pk_supergroup" };
	}

	/**
	 * 指示上下级关系－－父字段。
	 */
	public String getFatherField() {
		return "pk_parent";
	}

	/**
	 * 指示上下级关系－－子字段。 创建日期：(2001-8-11 14:43:58)
	 */
	public String getChildField() {
		return "pk_planitem" ;
	}

	/**
	 * 显示字段中文名
	 */
	public java.lang.String[] getFieldName() {
		return new String[] { nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000042")/*@res "计划项目编码"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000043")/*@res "计划项目名称"*/};
	}

	/**
	 * 主键字段名
	 */
	public String getPkFieldCode() {
		return "pk_planitem";
	}

	/**
	 * 参照标题
	 */
	public String getRefTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000044")/*@res "资金计划项目参照"*/;
	}

	/**
	 * 参照数据库表或者视图名
	 */
	public String getTableName() {

		return "fp_dim_planitem";
	}

	public String getWherePart() {
	    if (super.getWherePart() == null) {
	    	//计划项目收支属性0-收；1-支；2-其他
	    	this.setWherePart(" pk_corp='" + getPk_corp() + "' and isnull(fp_dim_planitem.dr,0)=0 and ioflag in(1,2)");
	    }
	    return super.getWherePart();
	}

	/**
	 * 不显示字段列表
	 */
	public java.lang.String[] getHiddenFieldCode() {
		return new String[] {"pk_planitem", "pk_corp","pk_source","pk_supersource","pk_parent","ioflag","pk_plangroup","pk_supergroup"};
	}

}