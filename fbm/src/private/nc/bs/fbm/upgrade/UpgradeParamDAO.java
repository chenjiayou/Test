package nc.bs.fbm.upgrade;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ColumnProcessor;

/**
 * Ʊ������������DAO
 * @author xwq
 *
 */
public class UpgradeParamDAO {
	
	/**
	 * �ж�ϵͳ�Ƿ�������
	 * @param syscode
	 * @return
	 */
	public boolean isUpgrade(String syscode) throws DAOException{
		String sql = "select 1 from fbm_upgrade where syscode = '" + syscode + "'";
		BaseDAO dao = new BaseDAO();
		Object obj = dao.executeQuery(sql, new ColumnProcessor());
		return obj == null ? false:true;
	}
	
	/**
	 * ����������¼
	 * @param syscode
	 * @throws DAOException
	 */
	public void addUpgrade(String syscode)throws DAOException{
		String sql = "insert into fbm_upgrade(syscode) values ('" + syscode + "')";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql);
	}
	
	/**
	 * �����������
	 * @throws DAOException
	 */
	public void clearUpgrade() throws DAOException{
		String sql = "delete from fbm_upgrade";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql);
	}
}
