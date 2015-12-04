package nc.ui.fbm.accept.status;

import nc.ui.fbm.pub.btnstatus.FBMVoucherBtnStatus;
import nc.ui.pub.ButtonObject;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.UAPBillOperate;
import nc.vo.engine.status.UAPBillStatus;
import nc.vo.engine.status.assemble.IStatusAssemble;
import nc.vo.engine.status.assemble.StatusAssemble;
import nc.vo.engine.status.core.UAPButtonObjectStatusEngine;
import nc.vo.engine.status.factory.IStatusBuildFactory;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.register.RegisterVO;

public class AcceptStatusEngine extends UAPButtonObjectStatusEngine {

	public AcceptStatusEngine(AbstractBillUI ui) {
		super(ui);
	}
	@Override
	protected IStatusAssemble createStatusAssemble() {
		StatusAssemble assemble = new StatusAssemble();
		assemble.registStatusType(new UAPBillOperate(getUI()));
		assemble.registStatusType(new UAPBillStatus(getUI()));
		assemble.registStatusType(new FBMVoucherBtnStatus(getUI(),AcceptVO.UNITVOUCHER));
		return assemble;
	}

	@Override
	protected IStatusBuildFactory<ButtonObject> createStatusBuildFactory() {
		// TODO Auto-generated method stub
		return new AcceptStatusBuildFactory();
	}

}
