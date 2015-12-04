/**
 *
 */
package nc.ui.fbm.relief.status;

import nc.bs.logging.Logger;
import nc.ui.fbm.relief.ReliefUI;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.pub.BusinessException;

/**
 *
 * 类功能说明： 制证按钮控制,如果系统参数设置为否则亮，否则为否 日期：2007-11-27 程序员： wues
 *
 */
public class VoucherStatus extends AbstractBillUIStatus<Integer> {

	/**
	 * 集团编码，根据此编码和系统参数编码取系统参数值
	 */
	private static final String GROUPCODE = "@@@@";


	private static String isAuto = null;

	/**
	 * @param ui
	 */
	public VoucherStatus(AbstractBillUI ui) {
		super(ui);
		try {
			isAuto = OuterProxy.getSysInitQry().getParaString(GROUPCODE,
					FBMParamConstant.FBM002);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			this.getUI().showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201045","UPP36201045-000000")/* @res"取系统参数时异常"*/);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		// 默认亮
		Integer retStatus = IVoucherStatus.SYS_PARAM_NO;
		// 参数是否设置，默认参数未设
		if (null == isAuto || "".equals(isAuto) || "true".equals(isAuto))
			retStatus = IVoucherStatus.SYS_PARAM_YES;// 不亮

		return retStatus;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return IVoucherStatus.class.getName();
	}

	@Override
	public ReliefUI getUI() {
		// TODO Auto-generated method stub
		return (ReliefUI) super.getUI();
	}

}