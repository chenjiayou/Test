/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import java.awt.Color;

import nc.ui.pub.style.Style;

/**
 * <p>
 * �����б�Ԫ����ɫ����
 * <p>
 * �������ڣ�2006-9-14
 * 
 * @author lisg
 * @since v5.0
 */
public interface IBillTableCellColor {

	/**
	 * ��������ʾ��ɫ
	 */
	public static Color ODD_ROW_COLOR = Color.WHITE;
	/**
	 * ż������ʾ��ɫ
	 */
	public static Color EVEN_ROW_COLOR = Style.getTableIntervalColor();
	/**
	 * �ɱ༭��Ԫ����ɫ
	 */
	public static Color ENABLE_EDIT_COLOR = new Color(204,255,255);
	/**
	 * �ϼ�����ʾ��ɫ
	 */
	public static Color TOTAL_ROW_COLOR = new Color(239,239,231);
}
