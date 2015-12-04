package nc.bs.fbm.plan;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nc.bs.dao.BaseDAO;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.pub.BusinessException;

public class FBMPlanItemAssist {


	/**
	 * Ʊ�����й������ƻ��ı���ֶεļ���
	 */
	private String[][] getParamArray() {
		return new String[][] {
				new String[] {"fbm_register","chargeplanitem","isnull(dr,0)=0","�üƻ���Ŀ����Ʊ�Ǽǵ����ã�����ɾ��!\n"},
				new String[] {"fbm_register","invoiceplanitem","isnull(dr,0)=0","�üƻ���Ŀ����Ʊ�Ǽǵ����ã�����ɾ��!\n"},
				new String[] {"fbm_discount","fbmplanitem","isnull(dr,0)=0", "�üƻ���Ŀ��Ʊ�����ֵ����ã�����ɾ��!\n"},
				new String[] { "fbm_discount", "balanceplanitem","isnull(dr,0)=0", "�üƻ���Ŀ��Ʊ�����ֵ����ã�����ɾ��!\n"},
				new String[] { "fbm_discount", "interestplanitem","isnull(dr,0)=0", "�üƻ���Ŀ��Ʊ�����ֵ����ã�����ɾ��!\n"},
				new String[] { "fbm_discount", "chargeplanitem","isnull(dr,0)=0", "�üƻ���Ŀ��Ʊ�����ֵ����ã�����ɾ��!\n"},
				new String[] { "fbm_storage", "unitplanitem", "isnull(dr,0)=0",	"�üƻ���Ŀ��Ʊ���йܵ����ã�����ɾ��!\n" },
				new String[] { "fbm_collection", "fbmplanitem","isnull(dr,0)=0", "�üƻ���Ŀ��Ʊ�����յ����ã�����ɾ��!\n" },
				new String[] { "fbm_collection", "collectionplanitem","isnull(dr,0)=0", "�üƻ���Ŀ��Ʊ�����յ����ã�����ɾ��!\n" },
				new String[] { "fbm_accept", "acceptplanitem","isnull(dr,0)=0", "�üƻ���Ŀ��Ʊ�ݸ�����ã�����ɾ��!\n" },
				new String[] { "fbm_return", "unitplanitem", "isnull(dr,0)=0","�üƻ���Ŀ��Ʊ����Ʊ�����ã�����ɾ��!\n" } 
		};

	}
	public String getCheckMessage(String pk_field) throws BusinessException{
		StringBuffer errMsg = new StringBuffer();
		Set errSet = new HashSet();
		String[][] paramArray = getParamArray();
		int arrayLength = paramArray.length;
		for(int i=0;i<arrayLength;i++){
			String tmpMsg = getErrorMsg(paramArray[i],pk_field);
			if(tmpMsg!=null && !"".equals(tmpMsg)){
				errSet.add(tmpMsg);
			}
		}
		if(errSet!=null && errSet.size()>0){
			Iterator it = errSet.iterator();
			while(it.hasNext())
			{
				errMsg.append(it.next());
			}
			
		}
		return errMsg.toString();
	}
	
	private String  getErrorMsg(String[] paramArray,String pk_field) throws BusinessException{
		StringBuffer sqlBuffer = new StringBuffer();
		String tableName = paramArray[0];
		String fieldName = paramArray[1];
		String commonCondition = paramArray[2];
		String errMsg = paramArray[3];
		sqlBuffer.append("select "+ fieldName +" from "+tableName+" where " );
		sqlBuffer.append( fieldName + " ='"+pk_field+"' ");
		sqlBuffer.append(" and "+commonCondition);
		
		BaseDAO baseDAO = new BaseDAO();
		List list = (List) baseDAO.executeQuery(sqlBuffer.toString(), new ColumnListProcessor());
		if(list!=null && list.size()>0){
			return errMsg;
		}
		return null;
	}
}
