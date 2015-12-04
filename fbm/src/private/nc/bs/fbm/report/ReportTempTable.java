package nc.bs.fbm.report;

import java.sql.Connection;
import java.sql.SQLException;

import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.bs.mw.sqltrans.TempTable;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.vo.fbm.report.IReportTempTable;
import nc.vo.pub.BusinessException;

/**
 * 临时表工具类
 * 
 * @author xwq
 * 
 * 2009-8-8
 */
public class ReportTempTable {
	
	public String createTempTableByClass(Class cls) throws BusinessException{
		try {
			IReportTempTable r = (IReportTempTable)cls.newInstance();
			return createTempTable(r.getTableName(),r.getTableCols(),r.getTableIndexs());
		} catch (InstantiationException e) {
			throw new BusinessException(e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new BusinessException(e);
		}
	}
	
	/**
	 * 创建临时表
	 * @param tableName
	 * @param columns
	 * @param indexs
	 * @return
	 * @throws BusinessException
	 */
	public String createTempTable(String tableName, String columns,String indexs) throws BusinessException {
		// TODO Auto-generated method stub
		PersistenceManager sessionManager = null;
		Connection con = null;
		try {
			sessionManager = PersistenceManager.getInstance();
			JdbcSession session = sessionManager.getJdbcSession();
			con = session.getConnection();
			String tablename = new TempTable().createTempTable(con, tableName,columns, indexs);
			return tablename;

		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					Logger.error(e.getMessage(), e);
					throw new DAOException(e.getMessage(), e);
				}
			}
			if (sessionManager != null) {
				sessionManager.release();
			}
		}
	}
}
