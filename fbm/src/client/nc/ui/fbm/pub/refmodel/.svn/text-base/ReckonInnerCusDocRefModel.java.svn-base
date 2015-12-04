package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.RefDataFilter;
import nc.ui.tm.framework.ref.filter.RefModelFilter;

/**
 * <p>
 * 调剂清算内部客商参照
 * <p>创建人：bsrl
 * <b>日期：2007-10-31
 *
 */
public class ReckonInnerCusDocRefModel extends AbstractTMRefModel{
	protected String m_strWherePart = " AND (bd_cumandoc.custflag='0' OR bd_cumandoc.custflag='1' OR bd_cumandoc.custflag='2')) ";

	/**
	 *
	 */
	public ReckonInnerCusDocRefModel() {
		this(null);
	}

	public ReckonInnerCusDocRefModel(Container con) {
		super(con);
		corpWherePart.setCorpField("bd_cumandoc.pk_corp");
		corpWherePart.setSenttleCenterCorpField("bd_cubasdoc.pk_corp1");
		setMatchPkWithWherePart(true);
		setChangeTableSeq(false);
		setDataPowerResourceColumn("bd_cumandoc.pk_cumandoc");
	}

	public void setWherePart(String newWherePart) {
		if (newWherePart != null) {
			newWherePart = newWherePart.trim();
			m_strWherePart = m_strWherePart + newWherePart;
		}
	}

	public int getDefaultFieldCount() {
		return 3;
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "bd_cubasdoc.custcode", "bd_cubasdoc.custname",
				"bd_cubasdoc.custshortname", "bd_cubasdoc.mnecode",
				"bd_cubasdoc.phone1",
				"bd_cubasdoc.pk_cubasdoc",
				// "bd_cumandoc.pk_cumandoc",
				"bd_cubasdoc.pk_corp1", "bd_cumandoc.sealflag",
				"bd_cubasdoc.sealflag", "frozenflag" };
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] {"bd_settlecenter.pk_settlecenter" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] {nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000050")/* @res"单位编码"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000051")/* @res"单位名称"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000052")/* @res"单位简称"*/};
	}

	public java.lang.String getPkFieldCode() {
		return "bd_cubasdoc.pk_cubasdoc";
	}

	protected RefModelFilter createRefFilter() {
		RefDataFilter refFilter = new RefDataFilter();
		// 设置过滤的列
		refFilter.setFilterIndex(new int[] { getFieldCode().length - 3,
				getFieldCode().length - 2, getFieldCode().length - 1});
		// 设置过滤的值
		refFilter.setFilterValue(new String[][] {
				{ RefDataFilter.NOT_NULL_VALUE },
				{ RefDataFilter.NOT_NULL_VALUE }, { "Y" } });

		refFilter.setRefPane(getParent());

		return refFilter;
	}

	public java.lang.String getRefNodeName() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0001584")/*@res "客商档案"*/;
	}

	/**
	 * getRefTitle 方法注解。
	 */
	public String getRefTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000056")/*@res "客商档案参照"*/;
	}

	/**
	 * getTableName 方法注解。
	 */
	public String getTableName() {
		return "bd_cumandoc inner join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc= bd_cumandoc.pk_cubasdoc"+
	   	" left outer join bd_settleunit on bd_cubasdoc.pk_corp1 = bd_settleunit.pk_corp1 " +
	   	" left outer join bd_settlecenter on bd_settlecenter.pk_settlecenter = bd_settleunit.pk_settlecent " ;

	}

	public String getWherePart() {
		return "( " + corpWherePart.getCorpWhereWithExtCorp() + m_strWherePart;
	}
}