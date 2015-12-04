package nc.ui.fbm.discountcalculate;

/**
 * <p>
 * 合计分类接口
 * <p>
 * 创建日期：2006-9-19
 * 
 * @author lisg
 * @since v5.0
 */
public interface ITotalSpecify {

	/**
	 * <p>
	 * 根据VOClass名称,获得分类行标示数组
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-26
	 * @return
	 */
	public TotalRowPara getTotalRowPara(String voname);
}
