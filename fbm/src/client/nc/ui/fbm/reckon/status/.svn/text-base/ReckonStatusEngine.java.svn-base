package nc.ui.fbm.reckon.status;

import nc.ui.pub.ButtonObject;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.UAPBillStatus;
import nc.vo.engine.status.assemble.IStatusAssemble;
import nc.vo.engine.status.assemble.StatusAssemble;
import nc.vo.engine.status.core.UAPButtonObjectStatusEngine;
import nc.vo.engine.status.factory.IStatusBuildFactory;
import nc.vo.fbm.reckon.ReckonVO;
import nc.ui.fbm.pub.btnstatus.FBMVoucherBtnStatus;

public class ReckonStatusEngine extends UAPButtonObjectStatusEngine {
	public ReckonStatusEngine(AbstractBillUI ui) {
		super(ui);
	}
	
	@Override
	protected IStatusAssemble createStatusAssemble() {
		StatusAssemble assemble = new StatusAssemble();
		assemble.registStatusType(new UAPBillStatus(getUI()));
		assemble.registStatusType(new FBMVoucherBtnStatus(getUI(),ReckonVO.CENTERVOUCHER));
		return assemble;
	}
	
	protected IStatusBuildFactory<ButtonObject> createStatusBuildFactory() {
		return new ReckonStatusBuildFactory();
	}
}
