/**
 *
 */
package nc.ui.fbm.storage.innerkeep;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.tm.framework.util.ClientInfo;

/**
 * <p>
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-11-29
 * 
 */
public class SubCustDocCondition {

	/**
	 * 
	 */
	public SubCustDocCondition() {
		// TODO Auto-generated constructor stub
	}

	public static String getCusDocFilterContidtion() {
		String settelCenterSql = getSettleCenterSQL(ClientInfo.getCorpPK(), ClientInfo.getUserPK());

		String strl = "select bd_cubasdoc.pk_cubasdoc from bd_cubasdoc "
				+ " left outer join bd_cumandoc on bd_cubasdoc.pk_cubasdoc = bd_cumandoc.pk_cubasdoc "
				+ " where bd_cubasdoc.pk_corp1 in ("
				+ settelCenterSql
				+ ") "
				+ " and bd_cubasdoc.custprop <> 0 "
				+ " and (bd_cumandoc.custflag='0' or bd_cumandoc.custflag='1' or bd_cumandoc.custflag='2') ";

		strl = " ((bd_cubasdoc.pk_cubasdoc in ("
				+ strl
				+ ")) "
				+ " or (not exists ("
				+ settelCenterSql
				+ ") "
				+ " and bd_cubasdoc.pk_cubasdoc in(select bd_cubasdoc.pk_cubasdoc from bd_cubasdoc "
				+ " left outer join bd_cumandoc on bd_cubasdoc.pk_cubasdoc = bd_cumandoc.pk_cubasdoc "
				+ " where bd_cubasdoc.pk_corp1 in("
				+ getSettleUnitSQL(ClientInfo.getCorpPK())
				+ ") and bd_cubasdoc.custprop <> 0 "
				+ " and (bd_cumandoc.custflag='0' or bd_cumandoc.custflag='1' or bd_cumandoc.custflag='2') ))) ";
		return strl;
	}

	/**
	 * 
	 * <p>
	 * 下属所有结算单位对应的客商
	 * <p>
	 * 作者：lpf 日期：2007-12-6
	 * 
	 * @return
	 */
	private static String getSettleUnitSQL(String pk_corp) {
		return getSettleCenterSQL(pk_corp, null);
	}

	public static String getSettleCenterSQL(String pk_corp, String userid) {
		String sql = " select pk_corp1 from bd_settlecenter "
				+ " left outer join bd_settleunit on bd_settlecenter.pk_settlecenter = bd_settleunit.pk_settlecent "
				+ " left outer join bd_settleunit_b on bd_settleunit.pk_settleunit = bd_settleunit_b.pk_settleunit "
				+ " where  "
				+ " bd_settlecenter.pk_corp = '"
				+ pk_corp
				+ "' ";

		if (!CommonUtil.isNull(userid)) {
			sql = sql + " and bd_settleunit_b.pk_user = '" + userid + "' ";
		}
		// 公司下级成员单位pk_corp
		return sql;
	}

	public static String getCorpSQL() {
		String strl = null;
		String settelCenterSql = getSettleCenterSQL(ClientInfo.getCorpPK(), ClientInfo.getUserPK());
		strl = "pk_corp in("
				+ settelCenterSql
				+ ") or (not exists("
				+ settelCenterSql
				+ ") and pk_corp in("
				+ getSettleUnitSQL(ClientInfo.getCorpPK())
				+ "))";
		return strl;
	}
}
