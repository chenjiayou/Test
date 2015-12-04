package nc.bs.fbm.returnbill;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;

public class ReturnBillDAO {
	/**
	 * NC56新增需求，背书退票要写收票登记单备注
	 * @param pk_return
	 * @throws BusinessException
	 */
	public void appendRegNote(String pk_return,UFDate returndate)throws BusinessException{
		String sql = "select pk_endore ,fbm_endore.pk_source,custname,fbm_register.note  as note from fbm_return_b join fbm_register on fbm_return_b.pk_source = fbm_register.pk_register join fbm_endore on fbm_return_b.pk_source =fbm_endore.pk_source left join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc = endorsee where pk_return='"+pk_return+"'";
		BaseDAO dao = new BaseDAO();
		List<Map> list = (List<Map>)dao.executeQuery(sql, new MapListProcessor());
		if(list!=null && list.size() > 0){
			int len = list.size();
			String str = null;
			Map m = null;
			List<String> updateList = new ArrayList<String>();
			String oldnote = null;
			String custname = null;
			for(int i = 0; i < len;i++){
				m = list.get(i);
				oldnote = m.get("note") == null?"":m.get("note").toString();
				custname = m.get("custname") == null?"":m.get("custname").toString();
				str =  returndate.toString() + custname + "单位背书退票";
				updateList.add("update fbm_register set note = '"+str+"' where pk_register = '"+m.get("pk_source")+"'");
			}
			new CommonDAO().executeUpdate((String[])updateList.toArray(new String[0]));
		}
	}
	
	/**
	 * 清除备注字段
	 * @param pk_return
	 * @throws BusinessException
	 */
	public void clearRegNote(String pk_return)throws BusinessException{
		String sql = "select pk_endore ,fbm_endore.pk_source,custname,fbm_register.note  as note from fbm_return_b join fbm_register on fbm_return_b.pk_source = fbm_register.pk_register join fbm_endore on fbm_return_b.pk_source =fbm_endore.pk_source left join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc = endorsee where pk_return='"+pk_return+"'";
		BaseDAO dao = new BaseDAO();
		List<Map> list = (List<Map>)dao.executeQuery(sql, new MapListProcessor());
		if(list!=null && list.size() > 0){
			int len = list.size();
			Map m = null;
			List<String> updateList = new ArrayList<String>();
			for(int i = 0;i<len;i++){
				m= list.get(i);
				updateList.add("update fbm_register set note = null where pk_register = '"+m.get("pk_source")+"'");
			}
			new CommonDAO().executeUpdate((String[])updateList.toArray(new String[0]));
		}
	}
}
