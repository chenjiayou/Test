/**
 *
 */
package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.tm.framework.ref.AbstractTMRefModel;

/**
 * <p>
 * 支持手工录入的客商基本档案参照（pk+name）
 * <p>
 * 创建人：lpf <b>日期：2007-9-13
 * 
 */
public class BaseInfoCubasdocRefModel extends AbstractTMRefModel {

	/**
	 *
	 */
	public BaseInfoCubasdocRefModel() {
		setMatchPkWithWherePart(false);
	}

	/**
	 * @param con
	 */
	public BaseInfoCubasdocRefModel(Container con) {
		super(con);
		setMatchPkWithWherePart(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefNodeName()
	 */
	@Override
	public String getRefNodeName() {
		return "bd_cubasdoc";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefTitle()
	 */
	@Override
	public String getRefTitle() {
		// TODO Auto-generated method stub
		return nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000042")/*
																						 * @res
																						 * "收款单位参照"
																						 */;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getTableName()
	 */
	public String getTableName() {
		return " ((select bd_cubasdoc.pk_cubasdoc,custcode,custname,mnecode,bd_cumandoc.pk_corp mancorp,'Y' as isCust"
				+ ",bd_cumandoc.bp as xx, bd_cubasdoc.bp2 as yy  from (bd_cubasdoc inner join bd_cumandoc on "
				+ " bd_cubasdoc.pk_cubasdoc= bd_cumandoc.pk_cubasdoc) where isnull(bd_cubasdoc.dr,0)=0 and isnull(bd_cumandoc.dr,0)=0 "
				+ " and (bd_cumandoc.custflag='0' OR bd_cumandoc.custflag='1' OR bd_cumandoc.custflag='2')) "
				+ " union (select pk_cubasdoc,custcode,custname,mnecode,'FBMC' as mancorp,'N' as isCust"
				+ ",fbm_cubasdoc.bp2 as xx, fbm_cubasdoc.bp2 as yy from fbm_cubasdoc where url='FBM_GENECUST' )) baseinfo ";
	}

	@Override
	public String[] getFieldCode() {
		return new String[] { "custcode", "custname", "mnecode" };
	}

	@Override
	public String[] getFieldName() {
		return new String[] {
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000043")/*
																							 * @res
																							 * "客商编号"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000044")/*
																							 * @res
																							 * "客商名称"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000060") /*
																								 * @res
																								 * "助记码"
																								 */, };
	}

	@Override
	public String[] getHiddenFieldCode() {
		// return new String[] { "pk_cubasdoc","isCust","mancorp" };
		return new String[] { "pk_cubasdoc", "isCust" };
	}

	@Override
	public String getPkFieldCode() {
		return "pk_cubasdoc";
	}

	@Override
	public String[] getRefShowNameValues() {
		String[] refNames = super.getRefShowNameValues();
		if (refNames != null && refNames.length > 0) {
			String[] returnStr = new String[1];
			returnStr[0] = refNames[0];
			return returnStr;
		}
		return refNames;
	}

	/**
	 * 返回值数组－－编码字段 创建日期：(2001-8-13 16:19:24)
	 * 
	 * @return java.lang.String[]
	 */
	public java.lang.String[] getRefCodeValues() {
		String[] sDatas = super.getRefCodeValues();
		if (sDatas != null && sDatas.length > 1) {
			String[] returnStr = new String[1];
			returnStr[0] = sDatas[0];
			return returnStr;
		}
		return sDatas;
	}
}