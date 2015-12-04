/**
 *
 */
package nc.ui.fbm.invoice.status;

import nc.ui.fbm.gather.status.GatherFbillTypeStatus;
import nc.ui.fbm.pub.btnstatus.FBMVoucherBtnStatus;
import nc.ui.pub.ButtonObject;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.UAPBillOperate;
import nc.vo.engine.status.UAPBillStatus;
import nc.vo.engine.status.assemble.IStatusAssemble;
import nc.vo.engine.status.assemble.StatusAssemble;
import nc.vo.engine.status.core.UAPButtonObjectStatusEngine;
import nc.vo.engine.status.factory.IStatusBuildFactory;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 开票状态机
 * <p>创建人：lpf
 * <b>日期：2007-9-20
 *
 */
public class InvoiceStatusEngine extends UAPButtonObjectStatusEngine {

	/**
	 * @param ui
	 */
	public InvoiceStatusEngine(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.core.AbstractStatusEngine#createStatusAssemble()
	 */
	@Override
	protected IStatusAssemble createStatusAssemble() {
		StatusAssemble assemble = new StatusAssemble();
		assemble.registStatusType(new UAPBillOperate(getUI()));
		assemble.registStatusType(new UAPBillStatus(getUI()));
		assemble.registStatusType(new InvoiceFbillTypeStatus(getUI()));
		assemble.registStatusType(new InvoiceUseCCStatus(getUI()));
		assemble.registStatusType(new DestroyBillStatus(getUI()));
		assemble.registStatusType(new FBMVoucherBtnStatus(getUI(),RegisterVO.VOUCHER));
		assemble.registStatusType(new InvoiceFbillTypeStatus(getUI()));
		return assemble;
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.core.AbstractStatusEngine#createStatusBuildFactory()
	 */
	@Override
	protected IStatusBuildFactory<ButtonObject> createStatusBuildFactory() {
		return new InvoiceStatusBuildFactory();
	}

}
