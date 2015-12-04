/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import nc.ui.trade.controller.IControllerBase;

/**
 * <p>
 * 合计UI工具类
 * <p>
 * 创建日期：2006-9-20
 * 
 * @author lisg
 * @since v5.0
 */
public class TotalRowUITools {
	
	/**
	 * <p>
	 * 交验当前controller是否为有效的合计控制器
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-20
	 * @param controller
	 * @return
	 */
	public static boolean isLegalTotalController(IControllerBase controller){
		
		if(!(controller instanceof ITotalSpecify)) return false;
		
//		ITotalSpecify ts = (ITotalSpecify)controller;
//		if(ts == null ||
//				CommonUtil.isNull(ts.getTotalRowPara())){
//			return false;
//		}

		return true;
	}
}
