/**
 *
 */
package nc.bs.fbm.gather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.logging.Logger;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CommonUtil;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.mapping.IMappingMeta;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.jdbc.framework.processor.MapProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.SuperVO;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-10-17
 *
 */
public class FBMPubQueryDAO {
	private BaseDAO m_baseDAO = null;
	/**
	 * 
	 */
	public FBMPubQueryDAO() {
		super();
		
	}

    public SuperVO querySingleData(String sql,Class c) throws BusinessRuntimeException, BusinessException{
    	SuperVO[] retvos=null;
        JdbcSession session=null;
        PersistenceManager sessionManager=null;
        try {
        	sessionManager=PersistenceManager.getInstance() ;
        	session=sessionManager.getJdbcSession() ;
            ArrayList al=(ArrayList)session.executeQuery(sql.toString(),new BeanListProcessor(c));
            if(al.size()>0){
            	retvos=new SuperVO[al.size()];
            	al.toArray(retvos);
            }
            else{
            	return null;
            }
        }
        catch (DbException e) {
            // TODO: handle exception
            Logger.error(e);
            throw new BusinessRuntimeException(e.getSQLState());
        }
        finally{
        	sessionManager.release();
        }
        return retvos[0];
    }
    
    public SuperVO[] queryData(String sql,Class c) throws BusinessRuntimeException, BusinessException{
        SuperVO[] retvos=null;
        JdbcSession session=null;
        PersistenceManager sessionManager=null;
        try {
        	sessionManager=PersistenceManager.getInstance() ;
        	session=sessionManager.getJdbcSession() ;
            ArrayList al=(ArrayList)session.executeQuery(sql.toString(),new BeanListProcessor(c));
            if(al.size()>0){
            	retvos=new SuperVO[al.size()];
            	al.toArray(retvos);
            }
        }
        catch (DbException e) {
            // TODO: handle exception
            Logger.error(e.getMessage());
            throw new BusinessRuntimeException(e.getMessage());
        }
        finally{
        	sessionManager.release();
        }
        return retvos;
    }
    
    public ArrayList queryArrayListData(String sql) throws BusinessException,BusinessRuntimeException{
        ArrayList retlist=null;
        JdbcSession session=null;
        PersistenceManager sessionManager=null;
        try {
        	sessionManager=PersistenceManager.getInstance() ;
        	session=sessionManager.getJdbcSession() ;
            //session=new JdbcSession();
            retlist=(ArrayList)session.executeQuery(sql.toString(),new MapListProcessor());
        }
        catch (DbException e) {
            // TODO: handle exception
            Logger.error(e.getMessage());
            throw new BusinessRuntimeException(e.getSQLState());
        }
        finally{
            //session.closeAll();
        	sessionManager.release();
        }
        return retlist;
    }
    
    public Map queryMapData(String sql) throws BusinessException,BusinessRuntimeException{
        Map retmap=null;
        JdbcSession session=null;
        PersistenceManager sessionManager=null;
        try {
        	sessionManager=PersistenceManager.getInstance() ;
        	session=sessionManager.getJdbcSession() ;
            retmap=(Map)session.executeQuery(sql.toString(),new MapProcessor());
        }
        catch (DbException e) {
            // TODO: handle exception
            Logger.error(e.getMessage());
            throw new BusinessRuntimeException(e.getSQLState());
        }
        finally{
        	sessionManager.release();
        }
        return retmap;
    }

    /**
     * 
     * <p>
     * 插入数据
     * <p>
     * 作者：lpf
     * 日期：2007-12-12
     * @param vo
     * @param map
     * @return
     * @throws BusinessException
     */
    public String insertData(Object vo,IMappingMeta map) throws BusinessException{
		return getBaseDAO().insertObject(vo, map);
    }
    
    /**
     * 
     * <p>
     * 更新并返回最新ts
     * <p>
     * 作者：lpf
     * 日期：2007-10-29
     * @param vo
     * @return
     * @throws BusinessException
     */
    public SuperVO updateVoreturnlatestTs(SuperVO vo,String[] fieldNames) throws BusinessException{
    	if(vo==null){
    		return vo;
    	}
    	getBaseDAO().updateVO(vo, fieldNames);
    	SuperVO returnVo = new HYPubBO().queryByPrimaryKey(vo.getClass(), vo.getPrimaryKey());
    	return returnVo;
    }
    
	private BaseDAO getBaseDAO() {
		if(m_baseDAO==null)
			m_baseDAO = new BaseDAO();
		return m_baseDAO;
	}
	
	/**
	 * 
	 * <p>
	 * 提供给升级开户银行使用
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-17
	 * @param sql
	 * @param fieldkey
	 * @return
	 */
	public Map<String, List<String>> queryMapwithKeyData(Map<String, List<String>> maplist, String sql,String[] fieldkey){
        JdbcSession session=null;
        PersistenceManager sessionManager=null;
        try {
        	sessionManager=PersistenceManager.getInstance() ;
        	session=sessionManager.getJdbcSession() ;
        	ArrayList retlist=(ArrayList)session.executeQuery(sql.toString(),new MapListProcessor());
        	if(retlist!=null&&retlist.size()>0){
        		if(maplist == null){
        			maplist = new HashMap<String, List<String>>();
        		}
        		for (int i = 0; i < retlist.size(); i++) {
					Map result = (Map) retlist.get(i);
					String key = (String) result.get(fieldkey[0]);
					String value = (String) result.get(fieldkey[1]);
					if(CommonUtil.isNull(key)||CommonUtil.isNull(value))
						continue;
					if(maplist.containsKey(key)){
						List oldlist = (List)maplist.get(key);
						if(!(oldlist).contains(value)){
							oldlist.add(value);
						}
					}else{
						ArrayList<String> newlist = new ArrayList<String>();
						newlist.add(value);
						maplist.put(key, newlist);
					}
				}
        	}
        }
        catch (DbException e) {
            // TODO: handle exception
            Logger.error(e.getMessage());
            throw new BusinessRuntimeException(e.getSQLState());
        }
        finally{
            //session.closeAll();
        	sessionManager.release();
        }
        return maplist;
	}
}
