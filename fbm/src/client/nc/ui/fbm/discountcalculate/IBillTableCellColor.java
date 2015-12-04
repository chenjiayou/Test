/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import java.awt.Color;

import nc.ui.pub.style.Style;

/**
 * <p>
 * 单据列表单元格颜色定义
 * <p>
 * 创建日期：2006-9-14
 * 
 * @author lisg
 * @since v5.0
 */
public interface IBillTableCellColor {

	/**
	 * 基数行显示颜色
	 */
	public static Color ODD_ROW_COLOR = Color.WHITE;
	/**
	 * 偶数行显示颜色
	 */
	public static Color EVEN_ROW_COLOR = Style.getTableIntervalColor();
	/**
	 * 可编辑单元格颜色
	 */
	public static Color ENABLE_EDIT_COLOR = new Color(204,255,255);
	/**
	 * 合计行显示颜色
	 */
	public static Color TOTAL_ROW_COLOR = new Color(239,239,231);
}
