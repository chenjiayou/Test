package nc.impl.fbm.report;

import java.util.Hashtable;

import nc.bs.fbm.report.BcbBodyBO;
import nc.bs.fbm.report.BcbHeadBO;
import nc.itf.fbm.report.IFBMReportQuery;
import nc.vo.fbm.report.ReportParam;
import nc.vo.pub.BusinessException;

import com.borland.dx.dataset.StorageDataSet;

public class FBMReportQueryImpl implements IFBMReportQuery {

	public StorageDataSet queryBcbBodyData(Hashtable hashparam,
			ReportParam reportParam) throws BusinessException {
		// TODO Auto-generated method stub
		BcbBodyBO bo = new BcbBodyBO();
		return bo.queryData(reportParam);
	}

	public StorageDataSet queryBcbHeadData(Hashtable hashparam,
			ReportParam reportParam) throws BusinessException {
		// TODO Auto-generated method stub
		BcbHeadBO bo = new BcbHeadBO();
		return bo.queryData(reportParam);
	}

 


}
