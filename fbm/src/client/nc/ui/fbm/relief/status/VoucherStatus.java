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
 * �๦��˵���� ��֤��ť����,���ϵͳ��������Ϊ������������Ϊ�� ���ڣ�2007-11-27 ����Ա�� wues
 *
 */
public class VoucherStatus extends AbstractBillUIStatus<Integer> {

	/**
	 * ���ű��룬���ݴ˱����ϵͳ��������ȡϵͳ����ֵ
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
			this.getUI().showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201045","UPP36201045-000000")/* @res"ȡϵͳ����ʱ�쳣"*/);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		// Ĭ����
		Integer retStatus = IVoucherStatus.SYS_PARAM_NO;
		// �����Ƿ����ã�Ĭ�ϲ���δ��
		if (null == isAuto || "".equals(isAuto) || "true".equals(isAuto))
			retStatus = IVoucherStatus.SYS_PARAM_YES;// ����

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