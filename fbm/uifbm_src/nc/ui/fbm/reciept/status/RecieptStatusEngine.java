package nc.ui.fbm.reciept.status;

import nc.ui.fbm.pub.btnstatus.FBMVoucherBtnStatus;
import nc.ui.pub.ButtonObject;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.UAPBillStatus;
import nc.vo.engine.status.assemble.IStatusAssemble;
import nc.vo.engine.status.assemble.StatusAssemble;
import nc.vo.engine.status.core.UAPButtonObjectStatusEngine;
import nc.vo.engine.status.factory.IStatusBuildFactory;
import nc.vo.fbm.reckon.ReckonVO;
import nc.ui.fbm.pub.btnstatus.FBMTallyBtnStatus;

public class RecieptStatusEngine extends UAPButtonObjectStatusEngine {

	public RecieptStatusEngine(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IStatusAssemble createStatusAssemble() {
		StatusAssemble assemble = new StatusAssemble();
		assemble.registStatusType(new UAPBillStatus(getUI()));
		assemble.registStatusType(new FBMVoucherBtnStatus(getUI(),ReckonVO.UNITVOUCHER));
		assemble.registStatusType(new FBMTallyBtnStatus(getUI(),ReckonVO.UNITTALLY));
		return assemble;
	}

	@Override
	protected IStatusBuildFactory<ButtonObject> createStatusBuildFactory() {
		// TODO Auto-generated method stub
		return new RecieptStatusBuildFactory();
	}

}
