package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.relief.ReliefUtil;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.pub.ValueObject;

/**
 * 
 * 类功能说明： 票据调剂/内部存放查询对话框里面的票据编号参照需要加过滤条件 过滤掉重复的票据编号 日期：2007-12-18 程序员： wues
 * 
 */
public class DistinctBillNoRefModel extends ReliefRefModel {

	public DistinctBillNoRefModel() {
		super();
	}

	public DistinctBillNoRefModel(Container con) {
		super(con);
	}

	@Override
	public String getTableName() {
		StringBuffer strBuf = new StringBuffer();
		strBuf
				.append(" (select distinct pk_register, fbm_baseinfo.pk_baseinfo, fbmbillno, fbm_baseinfo.moneyy,fbmbilltype, ");
		strBuf
				.append(" fbm_baseinfo.enddate, fbm_baseinfo.invoiceunit invoiceunit, fbm_register.brate, fbm_register.frate, fbm_register.moneyf, fbm_register.moneyb, ");
		strBuf
				.append("fbm_baseinfo.receiveunit, fbm_baseinfo.payunit, holdunit, paybillunit, pk_curr , registerstatus ,currtypename");
		strBuf
				.append(" from fbm_relief_b join fbm_register on fbm_relief_b.pk_source=fbm_register.pk_register ");
		strBuf
				.append(" join (select fbm_baseinfo.*,bd_currtype.currtypename from fbm_baseinfo join bd_currtype on fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype ) fbm_baseinfo on (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) ");
		strBuf
				.append(" where isnull(fbm_relief_b.dr,0)=0 and isnull(fbm_register.dr,0)=0 and isnull(fbm_baseinfo.dr,0)=0 ");

		// 如果当前登录的为结算中心，则取中心及其下属的所有子单位；如果为结算单位则不加公司条件
		String pk_corp = ClientInfo.getCorpPK();
		if (FBMClientInfo.isSettleCenter(pk_corp))
			// 本单位及其下属子单位
			strBuf.append(" and fbm_register.pk_corp in (").append(
					ReliefUtil.getAllSubCorp(pk_corp)).append(")");

		strBuf.append(" ) gatherTable");
		return strBuf.toString();
	}

	public ValueObject convertToVO(java.util.Vector vData) {
		return null;
	}
	
	public boolean isCacheEnabled() {
		return false;
	}
}
