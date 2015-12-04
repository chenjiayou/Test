package nc.ui.fbm.pub.refmodel;

import nc.bs.logging.Logger;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.tm.framework.ref.filter.RefDataFilter;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;

/**
 * 统管票据查询对话框参照
 * 
 * @author wangyg
 * 
 */
public class QueryUniStorBillRefModel extends DefaultGatherRefModel {
	
	@Override
	protected RefDataFilter createRefFilter() {

		RefDataFilter m_RefFilter_child_1 = null;
		m_RefFilter_child_1 = new RefDataFilter();
		// 设置过滤的列 过滤废票
		m_RefFilter_child_1
				.setFilterIndex(new int[] { getFieldIndex("disable") });
		// 设置过滤的值
		m_RefFilter_child_1.setFilterValue(new String[][] { { "Y" } });
		m_RefFilter_child_1.setRefPane(this.getParent());

		RefDataFilter m_RefFilter_child = null;
		m_RefFilter_child = new RefDataFilter(m_RefFilter_child_1);
		// 设置过滤的列 票据状态过滤
		m_RefFilter_child
				.setFilterIndex(new int[] { getFieldIndex("registerstatus") });
		// 设置过滤的值
		if (FBMClientInfo.isSettleCenter()) {
			m_RefFilter_child.setFilterValue(new String[][] { {
					FbmStatusConstant.ON_ENDORE,
					FbmStatusConstant.HAS_ENDORE,
					FbmStatusConstant.HAS_RELIEF_KEEP } });
		} else {
			m_RefFilter_child.setFilterValue(new String[][] { {
					FbmStatusConstant.ON_ENDORE,
					FbmStatusConstant.HAS_ENDORE, "register" } });
		}

		m_RefFilter_child.setReverse(true);
		m_RefFilter_child.setRefPane(this.getParent());

		RefDataFilter m_RefFilter = null;
//		m_RefFilter = new RefDataFilter(m_RefFilter_child);
//		m_RefFilter
//				.setFilterIndex(new int[] { getFieldIndex("pk_billtypecode") });
//		// 设置过滤的值（过滤应付票据）
//		m_RefFilter.setFilterValue(new String[][] { { "36GA" } });
//		m_RefFilter.setReverse(true);

		return m_RefFilter;
	}

	public String[] getHiddenFieldCode() {
		return new String[] { "frate", "brate", "moneyb", "moneyf", "disable",
				"pk_register", "pk_baseinfo", "dept",
				"acceptanceno", "contractno", "invoicedate", "payunit",
				"paybankacc", "receivebankacc", "pk_loanbank", "receiveunit",
				"fbmbilltype", "gatherdate", "registerstatus", "holdunit", "sfflag",
				"pk_billtypecode", "invoiceunit", "pk_curr", "pk_corp" };
	}

	@Override
	public String getTableName() {
		
		String commonCurCorp = null;
		try {
			commonCurCorp =	ClientInfo.getCommonCurCorpCubasdoc();
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
		}
		//取存放在中心的票据
		String sql =  "(select fbm_register.pk_billtypecode pk_billtypecode,fbm_register.pk_corp pk_corp,"
		+ "fbm_register.sfflag sfflag,fbm_register.frate frate,fbm_register.brate brate , "
		+ "fbm_register.moneyb moneyb, fbm_register.moneyf moneyf,  fbm_register.holdunit holdunit,"
		+ "fbm_register.keepunit keepunit, fbm_register.pk_loanbank,pk_register,fbm_baseinfo.pk_baseinfo,"
		+ "fbm_baseinfo.enddate,isnull(fbm_baseinfo.disable,'N') disable,fbm_baseinfo.invoicedate,"
		+ "fbm_baseinfo.payunit,fbm_baseinfo.paybankacc,fbm_baseinfo.receivebankacc,"
		+ "fbm_baseinfo.contractno contractno, fbm_baseinfo.acceptanceno acceptanceno, "
		+ "fbm_baseinfo.receiveunit,fbm_register.dept, fbm_baseinfo.invoiceunit invoiceunit,"
		+ "fbm_baseinfo.fbmbilltype,fbm_register.gatherdate,registerstatus,fbmbillno,pk_curr,"
		+ "fbm_baseinfo.moneyy,currtypename from fbm_register "
		+ "join ( select fbm_baseinfo.*,bd_currtype.currtypename from fbm_baseinfo join bd_currtype "
		+ "on fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype ) fbm_baseinfo on "
		+ "(fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) where isnull(fbm_register.dr,0)=0 "
		+ " and isnull(fbm_baseinfo.dr,0)=0  "
		+ " and fbm_register.keepunit='" + commonCurCorp + "'"//客商
		
		+ " and fbm_register.pk_billtypecode='36GA' ";
		
		
		if (FBMClientInfo.isSettleCenter()) {
			sql = sql + " and  (registerstatus='" + FbmStatusConstant.ON_ENDORE
					+ "' or registerstatus='" + FbmStatusConstant.HAS_ENDORE
					+ "' or registerstatus='" + FbmStatusConstant.HAS_RELIEF_KEEP
					+ "' or registerstatus='" + FbmStatusConstant.ON_DISCOUNT
					+ "' or registerstatus='" + FbmStatusConstant.HAS_DISCOUNT
					+ "' or registerstatus='" + FbmStatusConstant.ON_COLLECTION
					+ "' or registerstatus='" + FbmStatusConstant.HAS_COLLECTION
					+ "' or registerstatus='" + FbmStatusConstant.HAS_DISABLE
					+ "' or registerstatus='" + FbmStatusConstant.HAS_CLEAR
					//如果是已退票，那么必须是自己的收票，即存放地点=持票单位
					+ "' or (registerstatus in('" + FbmStatusConstant.HAS_RETURN + "','"+FbmStatusConstant.ON_RETURN+"') and fbm_register.holdunit='"+commonCurCorp+"')"
					+ " or registerstatus='" + FbmStatusConstant.ON_CENTER_RETURN
					+ "' or registerstatus='" + FbmStatusConstant.HAS_CENTER_RETURN
					+ "') ";
		} else {
			sql = sql + " and (registerstatus='" + FbmStatusConstant.ON_ENDORE
			+ "' or registerstatus='" + FbmStatusConstant.HAS_ENDORE
			+ "' or registerstatus='register" 
			+ "' or registerstatus='" + FbmStatusConstant.ON_DISCOUNT
			+ "' or registerstatus='" + FbmStatusConstant.HAS_DISCOUNT
			+ "' or registerstatus='" + FbmStatusConstant.ON_COLLECTION
			+ "' or registerstatus='" + FbmStatusConstant.HAS_COLLECTION
			+ "' or registerstatus='" + FbmStatusConstant.HAS_DISABLE
			+ "' or registerstatus='" + FbmStatusConstant.HAS_CLEAR
			+ "' or (registerstatus='" + FbmStatusConstant.ON_RETURN+ "' and fbm_register.holdunit='"+commonCurCorp+"')"
			+ " or (registerstatus='" + FbmStatusConstant.HAS_RETURN+ "' and fbm_register.holdunit='"+commonCurCorp+"')"
			+ " or registerstatus='" + FbmStatusConstant.ON_UNIT_RETURN
			+ "' or registerstatus='" + FbmStatusConstant.HAS_UNIT_RETURN
			+"')  ";
	
		}
		
		sql = sql + ") baseinfo";
		return sql;
	}

	
	public boolean isCacheEnabled() {
		return false;
	}
}
