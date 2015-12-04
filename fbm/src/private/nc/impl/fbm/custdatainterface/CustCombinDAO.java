/*
 * 创建日期 2005-6-20
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package nc.impl.fbm.custdatainterface;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;

/**
 * @author hzguo
 */
public class CustCombinDAO  {

	public CustCombinDAO() {
		super();
	}

	/**
	 * 进行客商合并 创建日期：(2002-10-29 9:22:00)
	 */
	public void updateString(String sql, String[] param) throws DAOException {
		SQLParameter parameter = new SQLParameter();
		for (int i = 0; i < param.length; i++) {
			parameter.addParam(param[i]);
		}
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql, parameter);
	}
}
