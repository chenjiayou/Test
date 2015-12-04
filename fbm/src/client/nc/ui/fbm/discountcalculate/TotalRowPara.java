/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import java.util.HashMap;

import nc.ui.ml.NCLangRes;
import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 * <p>
 * 合计行参数定义
 * <p>
 * 创建日期：2006-9-26
 * 
 * @author lisg
 * @since v5.0
 */
public class TotalRowPara{
	
	//需要进行合计组织的VO类名称
	private String voname = null;
	//合计分类主标识字段名称
	private String specifyfeild = null;
	//需要进行合计的列名称数组
	private String[] totalcolname = null;
	//返回合计行"合计"显示的列名称
	private String totaldispname = null;
	//合计列唯一区别列名称
	private String distinctcolname = null;
	
	//用来保存当前生成的key对应的分类标识
	private HashMap<String, String> m_keyWithFlag = new HashMap<String, String>();
	
	/**
	 * @param voClassName
	 * @param specifyFeild
	 * @param totalDispName
	 * @param distinctColName
	 * @param totalColName
	 */
	public TotalRowPara(String voClassName,
			String specifyFeild,
			String totalDispName,
			String distinctColName,
			String[] totalColName){
		
		setVOClassName(voClassName);
		setSpecifyFeild(specifyFeild);
		setTotalDispName(totalDispName);
		setDistinctColName(distinctColName);
		setTotalColName(totalColName);
		
	}
	
	/**
	 * <p>
	 * 获得合计列唯一区别列名称
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @return
	 */
	public String getDistinctColName() {
		return distinctcolname;
	}

	/**
	 * <p>
	 * 设置合计列唯一区别列名称
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @param distinctcolname
	 */
	public void setDistinctColName(String distinctcolname) {
		this.distinctcolname = distinctcolname;
	}

	/**
	 * <p>
	 * 获取分类字段VOfeild
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @return
	 */
	public String getSpecifyFeild() {
		return specifyfeild;
	}

	/**
	 * <p>
	 * 设置分类字段VOfeild
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @param specifyfeild
	 */
	public void setSpecifyFeild(String specifyfeild) {
		this.specifyfeild = specifyfeild;
	}

	/**
	 * <p>
	 * 获得需要进行合计的列名称数组
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @return
	 */
	public String[] getTotalColName() {
		return totalcolname;
	}

	/**
	 * <p>
	 * 设置需要进行合计的列名称数组
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @param totalcolname
	 */
	public void setTotalColName(String[] totalcolname) {
		this.totalcolname = totalcolname;
	}

	
	/**
	 * <p>
	 * 获得用来显示"合计"的列名称
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @return
	 */
	public String getTotalDispName() {
		return totaldispname;
	}

	
	/**
	 * <p>
	 * 设置用来显示"合计"的列名称
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @param totaldispname
	 */
	public void setTotalDispName(String totaldispname) {
		this.totaldispname = totaldispname;
	}

	/**
	 * <p>
	 * 获得VOClassName
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @return
	 */
	public String getVOClassName() {
		return voname;
	}

	
	/**
	 * <p>
	 * 设置VOClassName
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @param voname
	 */
	public void setVOClassName(String voname) {
		this.voname = voname;
	}
	
	/**
	 * <p>
	 * 验证参数的有效性
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @return
	 */
	public boolean validate(){
		if(getVOClassName() == null ||
				getSpecifyFeild() == null ||
				getDistinctColName() == null){
			return false;
		}
		
		return true;
	}
	
	/**
	 * <p>
	 * 获得Key-Flag对应关系表
	 * Flag为hashtable中的主键
	 * Key为生成Flag的对象
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-20
	 * @return
	 */
	public HashMap<String,String> getKeyWithFlag(){
		return m_keyWithFlag;
	}
	
	/**
	 * <p>
	 * 判断CircularlyAccessibleValueObject是否是一个合计VO
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-20
	 * @param vo
	 * @return
	 */
	public boolean isCountVO(CircularlyAccessibleValueObject vo){
		if(!vo.getClass().getName().equals(getVOClassName())){
			return false;
		}
		
		if(getKeyWithFlag().containsKey((String)vo.getAttributeValue(getDistinctColName()))) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * <p>
	 * 根据合计分类标志获得合计行标识
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @param key
	 * @return
	 */
	public String getTotalFlag(Object key) {
		String str_per = getVOClassName();
		String str_end = "";
		if(key == null){
			//do nothing;
		}else if(key instanceof String){
			str_end = (String)key;
		}else{
			str_end = key.toString();
		}
		
		return str_per+"_"+str_end;
	}

	/**
	 * <p>
	 * 根据不同的key,获得合计行显示名称,key为分类列中的具体值
	 * 该函数可以重载针对不同的key给出不同合计行返回表达式
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @param key
	 * @return
	 */
	public String getTotalFlagName(Object key) {
		return NCLangRes.getInstance().getStrByID("362004", "UPP362004-000151")
;
	}
}
