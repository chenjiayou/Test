package nc.bs.fbm.upgrade;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ColumnProcessor;

/**
 * 票据升级参数表DAO
 * @author xwq
 *
 */
public class UpgradeParamDAO {
	
	/**
	 * 判断系统是否已升级
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
	 * 增加升级记录
	 * @param syscode
	 * @throws DAOException
	 */
	public void addUpgrade(String syscode)throws DAOException{
		String sql = "insert into fbm_upgrade(syscode) values ('" + syscode + "')";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql);
	}
	
	/**
	 * 清空升级数据
	 * @throws DAOException
	 */
	public void clearUpgrade() throws DAOException{
		String sql = "delete from fbm_upgrade";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql);
	}
}
