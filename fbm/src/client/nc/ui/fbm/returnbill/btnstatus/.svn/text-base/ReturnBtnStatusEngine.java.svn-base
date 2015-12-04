package nc.ui.fbm.returnbill.btnstatus;

import nc.ui.pub.ButtonObject;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.UAPBillStatus;
import nc.vo.engine.status.assemble.IStatusAssemble;
import nc.vo.engine.status.assemble.StatusAssemble;
import nc.vo.engine.status.core.UAPButtonObjectStatusEngine;
import nc.vo.engine.status.factory.IStatusBuildFactory;
import nc.vo.fbm.returnbill.ReturnVO;

public class ReturnBtnStatusEngine extends UAPButtonObjectStatusEngine {

	public ReturnBtnStatusEngine(AbstractBillUI ui) {
		super(ui);
	}

	@Override
	protected IStatusAssemble createStatusAssemble() {
		StatusAssemble assemble = new StatusAssemble();
		assemble.registStatusType(new UAPBillStatus(getUI()));
		assemble.registStatusType(new ReturnVoucherBtnStatus(getUI(),ReturnVO.VOUCHER));
		assemble.registStatusType(new TransformBtnStatus(getUI()));
		assemble.registStatusType(new ReturnVoucherFlagBtnStatus(getUI(),ReturnVO.VOUCHER));
		return assemble;
	}

	@Override
	protected IStatusBuildFactory<ButtonObject> createStatusBuildFactory() {
		return new ReturnBtnStatusBuildFactory();
	}

}
