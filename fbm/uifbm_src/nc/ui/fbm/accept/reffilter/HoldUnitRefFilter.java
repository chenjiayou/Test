package nc.ui.fbm.accept.reffilter;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;


/**
 * 不包括当前公司客商的过滤器
 * @author xwq
 *
 * 2008-10-30
 */
public class HoldUnitRefFilter extends BillItemRefModelFilter {
	
	private FBMManageUI ui; 

	public HoldUnitRefFilter( FBMManageUI ui) {
		super();
		this.ui = ui;
	}

	@Override
	protected String getSelfFilterString() {
		String pk_cubasdoc = FBMClientInfo.getCorpCubasdoc(ui._getCorp().getPk_corp());
		if(!CommonUtil.isNull(pk_cubasdoc)){
			return " pk_cubasdoc<>'"+pk_cubasdoc+"' ";
		}
		return null;
	}

}
