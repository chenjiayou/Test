package nc.bs.fbm.report;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReportSqlBuilder {

	public static List<String> buildByConstant(String tablename,String updatefield,Map<String,String> valuemap){
		List<String> list = new LinkedList<String>();
		Set set = valuemap.keySet();
		Iterator itr = set.iterator();
		String oldvalue = null;
		String newvalue = null;
		while(itr.hasNext()){
			oldvalue = (String)itr.next();
			newvalue = valuemap.get(oldvalue);
			list.add("update " + tablename + " set " + updatefield + "=" + newvalue + " where " +  updatefield +"=" + oldvalue + "");
		}
		
		return list;
	}
 
}
