/**
 *
 */
package nc.ui.fbm.accept.reffilter;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.pub.BusinessException;

/**
 * <p>
 * 持票单位银行账号参照过滤器
 * 持票单位/币种
 * <p>创建人：lpf
 * <b>日期：2008-1-10
 *
 */
public class HolderaccRefModelFilter extends BillItemRefModelFilter {
	private FBMManageUI ui;
	/**
	 * 客商
	 */
	private String souceKey;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * @param billitem
	 */
	public HolderaccRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}
	
	public HolderaccRefModelFilter(BillItem billitem, FBMManageUI ui,
			String souceKey, String currency) {
		super(billitem);
		this.ui = ui;
		this.souceKey = souceKey;
		this.currency = currency;
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {
		String sqlfilter = "";

		String custPk = (String) ui.getRefTakenProccessor().getValueByTakenInCard(souceKey);
		String pk_curr = (String) ui.getRefTakenProccessor().getValueByTakenInCard(currency);
		String ownerCust = null;
		try {
			ownerCust = (String) FBMClientInfo.getCommonCurCorpCubasdoc();
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
		}
		//如果公司没有对应客商，清空开户银行
		if(CommonUtil.isNull(ownerCust)){
			return "1=2";
		}
		
		String corpPK = FBMClientInfo.getCorpPK();
		String corpCondition = " (bd_bankaccbas.pk_corp='"+corpPK+"' or bd_bankaccbas.pk_corp='0001') "; 

		if(!CommonUtil.isNull(custPk)){
			if (ownerCust.equals(custPk)) {
				sqlfilter = "  (bd_bankaccbas.pk_corp='"+corpPK+"' or (bd_bankaccbas.pk_corp='0001' and bd_bankaccbas.pk_bankaccbas in" +
						"(select pk_bankaccbas from bd_bankaccauth where pk_corp='"+corpPK+"' and isnull(dr,0)=0)))";
			} else {
				sqlfilter = " pk_bankaccbas in(select distinct pk_accbank from bd_custbank where pk_cubasdoc ='"+ custPk + "')  and "+corpCondition;
			}
		}
			
		if (!CommonUtil.isNull(pk_curr)) {
			sqlfilter = sqlfilter + " and pk_currtype ='" + pk_curr + "' ";
		}
		
		if(CommonUtil.isNull(sqlfilter)){
			sqlfilter = "1=1";
		}
		return "("+sqlfilter+") "+"   and "+corpCondition;
	}

}
