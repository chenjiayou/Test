package nc.ui.fbm.discountapply.refmodel;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.refmodel.DefaultGatherRefModel;
import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.ref.filter.SQLRefModelFilter;

/**
 * NC5.5 处理银行相关业务的票据编号参照 符合以下条件的票据： a、存放地点为当前公司或持票单位为当前公司
 * b、普通公司票据状态为已登记或已银行存放，中心增加已调剂存放
 * 
 * @author xwq
 * 
 */
public class GatherBankBusRefModel extends DefaultGatherRefModel {
	
	public String[] getHiddenFieldCode() {
		return new String[] { "frate", "brate", "moneyb", "moneyf", "disable",
				"pk_register", "pk_baseinfo", "dept", "acceptanceno",
				"contractno", "invoicedate", "payunit", "paybankacc",
				"receivebankacc", "pk_loanbank", "keepunit", "receiveunit",
				"fbmbilltype", "gatherdate", "registerstatus", "holdunit", "sfflag",
				"pk_billtypecode", "invoiceunit", "pk_curr", "pk_corp","paybank","receivebank"
				 };
	}

	@Override
	public String getTableName() {
		return "(select fbm_register.pk_billtypecode pk_billtypecode,fbm_register.pk_corp pk_corp,"
				+ "fbm_register.sfflag sfflag,fbm_register.frate frate,fbm_register.brate brate , "
				+ "fbm_register.moneyb moneyb, fbm_register.moneyf moneyf,  fbm_register.holdunit holdunit,"
				+ "fbm_register.keepunit keepunit, fbm_register.pk_loanbank,pk_register,fbm_baseinfo.pk_baseinfo,"
				+ "fbm_baseinfo.enddate,isnull(fbm_baseinfo.disable,'N') disable,fbm_baseinfo.invoicedate,"
				+ "fbm_baseinfo.payunit,fbm_baseinfo.paybankacc,fbm_baseinfo.receivebankacc,fbm_baseinfo.paybank,fbm_baseinfo.receivebank,"
				+ "fbm_baseinfo.contractno contractno, fbm_baseinfo.acceptanceno acceptanceno, "
				+ "fbm_baseinfo.receiveunit,fbm_register.dept, fbm_baseinfo.invoiceunit invoiceunit,"
				+ "fbm_baseinfo.fbmbilltype,fbm_register.gatherdate,registerstatus,fbmbillno,pk_curr,"
				+ "fbm_baseinfo.moneyy,currtypename from fbm_register "
				+ " join ( select fbm_baseinfo.*,bd_currtype.currtypename from fbm_baseinfo join bd_currtype "
				+ "on fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype ) fbm_baseinfo on "
				+ "(fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) where isnull(fbm_register.dr,0)=0 "
				+ "and  isnull(fbm_baseinfo.dr,0)=0  "
				 + " and ((fbm_register.keepunit = '"
				 +FBMClientInfo.getCorpCubasdoc(ClientEnvironment.getInstance()
				 .getCorporation().getPk_corp())
				 + "') or  fbm_register.holdunit = '"
				 +
				 FBMClientInfo.getCorpCubasdoc(ClientEnvironment.getInstance()
				 .getCorporation().getPk_corp()) + "') "

				+ " ) gathertable ";
	}

	public boolean isCacheEnabled() {
		return false;
	}
}
