/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import nc.ui.trade.controller.IControllerBase;

/**
 * <p>
 * �ϼ�UI������
 * <p>
 * �������ڣ�2006-9-20
 * 
 * @author lisg
 * @since v5.0
 */
public class TotalRowUITools {
	
	/**
	 * <p>
	 * ���鵱ǰcontroller�Ƿ�Ϊ��Ч�ĺϼƿ�����
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-20
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
