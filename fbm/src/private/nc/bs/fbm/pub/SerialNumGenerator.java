package nc.bs.fbm.pub;

import nc.bs.dao.BaseDAO;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.pub.BusinessException;


/**
 * 
 * <p>
 *  ���к�������
 *  ����DataMangerObject��getOID����Ϊprotected
 *  �����Ҫ�̳и����ᣬ������public����
 * </p>
 * @author xwq
 * @date 2007-8-31
 * @version V5.0
 */
public class SerialNumGenerator {

	public SerialNumGenerator() {
		super();
	}
	
	
	public int getNextID(String pk_baseinfo) throws BusinessException{
		BaseDAO dao = new BaseDAO();
		
		Object curNum = dao.executeQuery("select max(serialnum) from fbm_action where isnull(dr,0)=0 and pk_baseinfo='" + pk_baseinfo + "'", new ColumnProcessor());
		if(curNum == null){
			return 1;
		}else{
			return ((Integer)curNum).intValue() + 1;
		}
	}
}
