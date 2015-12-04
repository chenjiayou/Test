package nc.vo.fbm.outer;

/**
 * 
 * <p>
 *   票据属性对象
 *   返回到收付报单据作为影响因素处理
 * </p>
 * @author xwq
 * @date 2007-9-12
 * @version V5.0
 */
public class BillinfoPropVO {
	
	private String orientation;//票据方向,应收(ar)应付(ap)无(none)

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	
	
}
