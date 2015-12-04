package nc.ui.fbm.endore.checker;

import nc.bs.framework.common.NCLocator;
import nc.itf.fbm.endore.IEndoreService;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;

public class OperateEndoreCheck {

	/**
	 * <p>
	 * 验证是否能够进行背书办理增加操作。
	 * <p>
	 * 作者：wangyg 日期：2008-5-7
	 * 
	 * @param pk_corp
	 * @throws Exception
	 */
	public static void checkAdd(String pk_corp) throws Exception {
		commonCheck(pk_corp);
		if ("Y".equals(getParamValue(pk_corp))) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000027")/*
																										 * @res
																										 * "备查簿业务与应收管理、应付管理、现金管理集成应用，请通过应付管理、现金平台进行该操作。"
																										 */);
		}
	}

	/**
	 * <p>
	 * 验证是否能够进行背书办理保存操作。
	 * <p>
	 * 作者：wangyg 日期：2008-5-7
	 * 
	 * @param pk_corp
	 * @param opbilltype
	 * @throws Exception
	 */
	public static void checkSave(String pk_corp, String opbilltype)
			throws Exception {
		// 已迁移到后台进行校验。
		// commonCheck(pk_corp);
		// if (FbmBusConstant.BILL_PRIVACY.equals(opbilltype)
		// && "Y".equals(getParamValue(pk_corp))) {
		// throw new
		// BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000028")/*@res
		// "已启用收付报不能对私有票据进行保存。"*/);
		// }
	}

	/**
	 * <p>
	 * 验证是否能够进行背书办理修改、审核、弃审、制证、取消制证、冲销操作。
	 * <p>
	 * 作者：wangyg 日期：2008-5-7
	 * 
	 * @param pk_corp
	 * @throws Exception
	 */
	public static void checkOtherOP(String pk_corp, String pk_endore)
			throws Exception {
		commonCheck(pk_corp);
		if (isCreatedByARAP(pk_endore)) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000029")/*
																										 * @res
																										 * "此记录由收付报推式生成，不允许执行此操作。"
																										 */);
		}
	}

	/**
	 * <p>
	 * 验证收付报标识与办理单是否与收付报单据集成应用参数值是否一致。
	 * <p>
	 * 作者：wangyg 日期：2008-5-7
	 * 
	 * @param pk_corp
	 * @throws Exception
	 */
	public static void commonCheck(String pk_corp) throws Exception {

		boolean arapFlag = isStartARAP(pk_corp);
		boolean paramFlag = "Y".equals(getParamValue(pk_corp));

		if (!arapFlag && paramFlag) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000030")/*
																										 * @res
																										 * "背书办理单是否与收付报单据集成应用参数值 与\n　启用收付报标识不一致！"
																										 */);
		}
	}

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