package nc.ui.fbm.relief;

/**
 * 
 * 类功能说明：
     UI工具类
 * 日期：2007-11-13
 * 程序员： wues
 *
 */
public class ReliefUtil {
	
	/**
	 * 根据fieldValue将SQL查询包上单引号
	 * 例：fieldValue = "555";
	 * 返回： "'555'",增强正常拼接时的正确性和易读性
	 * @param fieldCode
	 * @param fieldValue
	 * @return
	 */
	public static String enclose(String fieldValue){
		if (null == fieldValue || "".equals(fieldValue.trim())){
			return "''";
		} else {
			return "'" + fieldValue + "'";
		}
	}

	/**
	 * 得到当前公司及其下属所有公司对应的客商
	 */ 
	public static String getSubCorpCustPK(String pk_corp) {
		StringBuffer buf = new StringBuffer();

		buf.append(" select pk_cubasdoc from bd_cubasdoc where pk_corp1 in ");
		buf.append(" (").append(getAllSubCorp(pk_corp)).append(")");
		
		return buf.toString();
	}

	/**
	 * 取当前公司下面的所有子公司
	 * 包含当前公司
	 * @param pk_corp
	 * @return
	 */
	public static String getAllSubCorp(String pk_corp) {
		StringBuffer buf = new StringBuffer();
		buf.append(" select pk_corp1 from bd_settleunit left join bd_settlecenter on");
		buf.append("(bd_settleunit.pk_settlecent=bd_settlecenter.pk_settlecenter) where pk_corp=").append(enclose(pk_corp));
		return buf.toString();
	}
}
