package nc.bs.fbm.pub;

import nc.bs.dao.BaseDAO;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.pub.BusinessException;


/**
 * 
 * <p>
 *  序列号生成器
 *  由于DataMangerObject的getOID方法为protected
 *  因此需要继承该类後，提升到public级别
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
