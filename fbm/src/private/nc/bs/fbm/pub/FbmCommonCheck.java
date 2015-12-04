package nc.bs.fbm.pub;

import nc.bs.framework.common.NCLocator;
import nc.itf.fbm.endore.IEndoreService;
import nc.vo.fbm.pub.constant.FbmBusConstant;

public class FbmCommonCheck {

	/**
	 * <p>
	 * 判断某个公司是否启用收付报.
	 * <p>
	 * 作者：wangyg 日期：2008-4-30
	 * 
	 * @param pk_corp
	 * @return
	 * @throws Exception
	 */
	public static boolean isStartARAP(String pk_corp) throws Exception {
		// 判断是否启用收复报
		String[] test_str = new String[] { FbmBusConstant.AR,
				FbmBusConstant.AP, FbmBusConstant.EP };
		IEndoreService ies = (IEndoreService) NCLocator.getInstance().lookup(IEndoreService.class.getName());
		boolean product_flag = ies.volidARAP(pk_corp, test_str);
		return product_flag;
	}

	/**
	 * <p>
	 * 取公司背书办理单是否与收付报单据集成应用参数值。
	 * <p>
	 * 作者：wangyg 日期：2008-5-7
	 * 
	 * @param pk_corp
	 * @return
	 * @throws Exception
	 */
	public static String getParamValue(String pk_corp) throws Exception {
		IEndoreService ies = (IEndoreService) NCLocator.getInstance().lookup(IEndoreService.class.getName());
		String paramvalue = ies.getParamValue(pk_corp);
		return paramvalue;
	}

	/**
	 * <p>
	 * 判断背书办理单当前记录是否为收复报推式生成。
	 * <p>
	 * 作者：wangyg 日期：2008-5-7
	 * 
	 * @param pk_endore
	 * @return
	 * @throws Exception
	 */
	public static boolean isCreatedByARAP(String pk_endore) throws Exception {
		IEndoreService ies = (IEndoreService) NCLocator.getInstance().lookup(IEndoreService.class.getName());
		return ies.isCratedByARAP(pk_endore);
	}

}
