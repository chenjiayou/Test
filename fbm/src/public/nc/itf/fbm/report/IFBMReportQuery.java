package nc.itf.fbm.report;

import java.util.Hashtable;

import nc.vo.fbm.report.ReportParam;
import nc.vo.pub.BusinessException;

import com.borland.dx.dataset.StorageDataSet;

public interface IFBMReportQuery {

	/**
	 * ��ѯƱ�ݱ��鲾��ͷ����
	 * @param hashparam
	 * @param reportParam
	 * @return
	 * @throws BusinessException
	 */
	public StorageDataSet queryBcbHeadData(Hashtable hashparam,
			ReportParam reportParam) throws BusinessException;
 
	/**
	 * ��ѯƱ�ݱ��鲾��������
	 * @param hashparam
	 * @param reportParam
	 * @return
	 * @throws BusinessException
	 */
	public StorageDataSet queryBcbBodyData(Hashtable hashparam,
			ReportParam reportParam) throws BusinessException;
	
}
