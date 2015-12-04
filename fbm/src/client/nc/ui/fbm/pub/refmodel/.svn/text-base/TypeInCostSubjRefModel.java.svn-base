package nc.ui.fbm.pub.refmodel;

import nc.ui.bd.ref.AbstractRefTreeModel;

/**
 * 方向为收支和收入的收支项目
 * @author xwq
 *
 * 2008-10-14
 */
public class TypeInCostSubjRefModel extends AbstractRefTreeModel{

	/**
	 * 显示字段列表
	 */
	public java.lang.String[] getFieldCode() {
	    setRefCodeField("bd_costsubj.costcode");
	    setRefNameField("bd_costsubj.costname");
	    return new String[] { "bd_costsubj.costcode", "bd_costsubj.costname", "bd_costsubj.memo","bd_costsubj.mnecode" };
	}

	/**
	 * 指示上下级关系－－父字段。
	 */
	public String getFatherField() {
		return "bd_costsubj.pk_parent";
	}

	/**
	 * 指示上下级关系－－子字段。 创建日期：(2001-8-11 14:43:58)
	 */
	public String getChildField() {
		return "bd_costsubj.pk_costsubj" ;
	}

	/**
	 * 显示字段中文名
	 */
	public java.lang.String[] getFieldName() {
		return new String[] { nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000070")/*@res "收支项目编码"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000071")/*@res "收支项目名称"*/};
	}

	/**
	 * 主键字段名
	 */
	public String getPkFieldCode() {
		return "bd_costsubj.pk_costsubj";
	}

	/**
	 * 参照标题
	 */
	public String getRefTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000072")/*@res "收支项目参照"*/;
	}

	/**
	 * 参照数据库表或者视图名
	 */
	public String getTableName() {

		return "bd_costsubj";
	}

	public String getWherePart() {
	    if (super.getWherePart() == null) {
	    	this.setWherePart(" pk_corp='" + getPk_corp() + "' and isnull(bd_costsubj.dr,0)=0 and (incomeflag='Y' or ioflag='Y')");
	    }
	    return super.getWherePart();
	}

	/**
	 * 不显示字段列表
	 */
	public java.lang.String[] getHiddenFieldCode() {
		return new String[] { "bd_costsubj.pk_costsubj", "bd_costsubj.pk_parent","bd_costsubj.pk_source" };
	}
}