package nc.ui.fbm.pub.refmodel;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.ref.filter.RefDataFilter;
import nc.vo.fbm.pub.constant.FbmStatusConstant;

/**
 * 
 * <p>
 * 收票参照，用于托收，贴现等应收票据业务单据
 * </p>
 * 
 * @author bsrl
 * @date 2007-11-01
 * @version V5.02
 */
public class GatherBusRefModel extends DefaultGatherRefModel {
	private String TABLE_NAME = "(select fbm_register.pk_billtypecode pk_billtypecode,fbm_register.pk_corp pk_corp,"
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
			+ "and isnull(fbm_baseinfo.dr,0)=0  " 

			// 存放地点为结算中心或者收票为中心但存放地点不为中心(指的是银行存放)
			+ " and ((fbm_register.pk_corp = '"
			+ ClientEnvironment.getInstance().getCorporation().getPk_corp()
			+ "' and fbm_register.keepunit != '"
			+ FBMClientInfo.getCorpCubasdoc(ClientEnvironment.getInstance()
					.getCorporation().getPk_corp())
			+ "') or  fbm_register.keepunit = '"
			+ FBMClientInfo.getCorpCubasdoc(ClientEnvironment.getInstance()
					.getCorporation().getPk_corp()) + "') "

			+ " ) gathertable ";

	/*
	 * @修改人 zhouwb 2008-10-08
	 * @修改原因 天圣项目，允许参照［已质押］的票据 FbmStatusConstant.HAS_IMPAWN
	 */
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
			m_RefFilter_child.setFilterValue(new String[][] { { "register",
				"has_bank_keep", FbmStatusConstant.HAS_RELIEF_KEEP, FbmStatusConstant.HAS_IMPAWN } });
		} else {
			m_RefFilter_child.setFilterValue(new String[][] { { "register",
				"has_bank_keep", FbmStatusConstant.HAS_IMPAWN } });

		}
		
		
		
		m_RefFilter_child.setReverse(true);
		m_RefFilter_child.setRefPane(this.getParent());

		
		
		RefDataFilter m_RefFilter = null;
		m_RefFilter = new RefDataFilter(m_RefFilter_child);
		m_RefFilter
				.setFilterIndex(new int[] { getFieldIndex("pk_billtypecode") });
		// 设置过滤的值（过滤应付票据）
		m_RefFilter.setFilterValue(new String[][] { { "36GA" } });
		m_RefFilter.setReverse(true);

		return m_RefFilter;
	}

	public String[] getHiddenFieldCode() {
		return new String[] { "frate", "brate","moneyb","moneyf", "disable", "pk_register", "pk_baseinfo", "dept","acceptanceno","contractno",
				"invoicedate", "payunit", "paybankacc", "receivebankacc","pk_loanbank",
				"receiveunit", "fbmbilltype", "gatherdate","registerstatus", "holdunit","sfflag","pk_billtypecode","invoiceunit","pk_curr","pk_corp"
				};
	}
	
	@Override
	public String getTableName() {
		return TABLE_NAME;
	}
	
	public boolean isCacheEnabled() {
		return false;
	}
}
