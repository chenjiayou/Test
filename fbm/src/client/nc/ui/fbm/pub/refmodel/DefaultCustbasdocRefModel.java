/**
 *
 */
package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.RefDataFilter;
import nc.ui.tm.framework.ref.filter.RefModelFilter;

/**
 * <p>
 * 票据系统客商档案参照（内部+外部） 返回客商基本档案PK
 * <p>
 * 创建人：lpf <b>日期：2007-10-23
 * 
 */
public class DefaultCustbasdocRefModel extends AbstractTMRefModel {

	protected String m_strWherePart = " AND (custflag='0' OR custflag='1' OR custflag='2')) ";

	/**
	 *
	 */
	public DefaultCustbasdocRefModel() {
		this(null);

	}

	public DefaultCustbasdocRefModel(Container con) {
		super(con);
		corpWherePart.setCorpField("bd_cumandoc.pk_corp");
		corpWherePart.setSenttleCenterCorpField("bd_cubasdoc.pk_corp1");
		setMatchPkWithWherePart(true);
		setChangeTableSeq(false);
		setDataPowerResourceColumn("pk_cumandoc");

	}

	public void setWherePart(String newWherePart) {
		if (newWherePart != null) {
			newWherePart = newWherePart.trim();
			m_strWherePart = m_strWherePart + newWherePart;
		}
	}

	public int getDefaultFieldCount() {
		return 4;
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "bd_cubasdoc.custcode", "bd_cubasdoc.custname",
				"bd_cubasdoc.custshortname", "bd_cubasdoc.mnecode",
				"bd_cubasdoc.phone1",
				"bd_cubasdoc.pk_cubasdoc", "bd_cumandoc.pk_corp",
				"bd_cubasdoc.pk_corp1", "custflag", "bd_cumandoc.sealflag",
				"frozenflag" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] {
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000050")/*
																							 * @res
																							 * "单位编码"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000051")/*
																							 * @res
																							 * "单位名称"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000052")/*
																							 * @res
																							 * "单位简称"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000060") /*
																								 * @res
																								 * "助记码"
																								 */, };
	}

	public java.lang.String getPkFieldCode() {
		return "bd_cubasdoc.pk_cubasdoc";
	}

	protected RefModelFilter createRefFilter() {
		RefDataFilter refFilter = new RefDataFilter();
		// 设置过滤的列,sealflag和frozenflag字段过滤
		refFilter.setFilterIndex(new int[] { getFieldCode().length - 2,
				getFieldCode().length - 1 });
		// 设置过滤的值
		refFilter.setFilterValue(new String[][] {
				{ RefDataFilter.NOT_NULL_VALUE }, { "Y" } });

		refFilter.setRefPane(getParent());

		return refFilter;
	}

	public java.lang.String getRefNodeName() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0001584")/*
																								 * @res
																								 * "客商档案"
																								 */;
	}

	/**
	 * getRefTitle 方法注解。
	 */
	public String getRefTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000056")/*
																									 * @res
																									 * "客商档案参照"
																									 */;
	}

	/**
	 * getTableName 方法注解。
	 */
	public String getTableName() {
		return " bd_cubasdoc inner join bd_cumandoc on bd_cubasdoc.pk_cubasdoc= bd_cumandoc.pk_cubasdoc ";
	}

	public String getWherePart() {
		return "( " + corpWherePart.getCorpWhereWithExtCorp() + m_strWherePart;
	}

}