package nc.itf.fbm.cust;

/**
 * 银行档案映射成虚拟客商的
 * @author xwq
 *
 */
public class CombineCust {
	
	public static String getCombineCustSQL(){
		StringBuffer sb = new StringBuffer();
		sb.append("((select pk_cubasdoc ,custcode,custname,0 as type from bd_cubasdoc)");
		sb.append(" union ");
		sb.append(" (select pk_bankdoc as pk_cubasdoc,bankdoccode as custcode,bankdocname as custname,1 as type from bd_bankdoc)) as bd_cubasdoc");
		return sb.toString();
	}
}
