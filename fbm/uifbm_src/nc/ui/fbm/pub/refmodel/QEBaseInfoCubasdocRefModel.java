package nc.ui.fbm.pub.refmodel;

import nc.ui.tm.framework.util.ClientInfo;

/**
 * @author bsrl
 *自定义查询用，收付款单位参照（客商）
 *2008-01-10
 */
public class QEBaseInfoCubasdocRefModel extends
		nc.ui.fbm.pub.refmodel.BaseInfoCubasdocRefModel {

	public String getTableName() {
		  return " ((select bd_cubasdoc.pk_cubasdoc,mnecode,custcode,custname,bd_cumandoc.pk_corp mancorp,'Y' as isCust from (bd_cubasdoc inner join bd_cumandoc on "
		    + " bd_cubasdoc.pk_cubasdoc= bd_cumandoc.pk_cubasdoc) where isnull(bd_cubasdoc.dr,0)=0 and isnull(bd_cumandoc.dr,0)=0 "
		    + " and (bd_cumandoc.custflag='0' OR bd_cumandoc.custflag='1' OR bd_cumandoc.custflag='2'))) baseinfo ";
		 }

	@Override
	public String getWherePart() {
		return " mancorp='" + ClientInfo.getCorpPK() + "'";
	}



}
