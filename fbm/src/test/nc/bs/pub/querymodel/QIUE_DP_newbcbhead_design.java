package nc.bs.pub.querymodel;
import java.util.*;
import com.borland.dx.dataset.*;
//import nc.bs.iuforeport.businessquery.*;
import nc.vo.dbbase.tools.*;
//import nc.vo.iuforeport.businessquery.*;
//import nc.vo.pub.dbbase.ClientDataSet;
//import nc.vo.pub.dbbase.QEDataSet;
//import nc.vo.pub.querymodel.*;
//import nc.vo.pub.rs.statistics.*;
public class QIUE_DP_newbcbhead_design	extends AbstractDataProcess {
   
	public void process() throws Exception {
		Hashtable param = getHashParam();
		nc.vo.fbm.report.ReportParam rep = new nc.vo.fbm.report.ReportParam();
		
		String pk_baseinfo = getValue("pk_baseinfo")==null?"":getValue("pk_baseinfo").toString();
		String receiveunit=getValue("receiveunit")==null?"":getValue("receiveunit").toString();
		String payunit=getValue("payunit")==null?"":getValue("payunit").toString();
		String invoicedate_begin=(String)getValue("invoicedate_begin");
		String invoicedate_end=(String)getValue("invoicedate_end");
		String enddate_begin=(String)getValue("enddate_begin");
		String enddate_end=(String)getValue("enddate_end");
		String pk_curr=getValue("pk_curr")==null?"":getValue("pk_curr").toString();
		String money_begin=(String)getValue("money_begin");
		String money_end=(String)getValue("money_end");
		String fbmbilltype = (String)getValue("fbmbilltype");
		String orientation = (String)getValue("orientation");
		String fbmbillstatus=(String)getValue("fbmbillstatus");
		
		rep.setPk_baseinfo(pk_baseinfo);
		rep.setReceiveunit(receiveunit);
		rep.setPayunit(payunit);
		rep.setInvoicedate_begin(invoicedate_begin);
		rep.setInvoicedate_end(invoicedate_end);
		rep.setEnddate_begin(enddate_begin);
		rep.setEnddate_end(enddate_end);
		rep.setPk_curr(pk_curr);
		rep.setMoney_begin(money_begin);
		rep.setMoney_end(money_end);
		rep.setFbmbilltype(fbmbilltype);
		rep.setOrientation(orientation);
		rep.setFbmbillstatus(fbmbillstatus);
		
		nc.itf.fbm.report.IFBMReportQuery fbmreport = (nc.itf.fbm.report.IFBMReportQuery)nc.bs.framework.common.NCLocator.getInstance().lookup(nc.itf.fbm.report.IFBMReportQuery.class.getName());
		StorageDataSet ds = fbmreport.queryBcbHeadData(param,rep);
		String[] valuecol = new String[]{"moneyy"};
		
		String curr = "CURRDIGIT";
		DataSetScaleProvider dscp = new DataSetScaleProvider(valuecol,curr);
		
		dscp.setDataProviders(new Object[]{ds});
		
		setProvider(dscp);
		//setDataSet(ds);
    }

}
