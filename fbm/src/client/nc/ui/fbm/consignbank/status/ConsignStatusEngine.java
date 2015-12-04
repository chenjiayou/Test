package nc.ui.fbm.consignbank.status;

import nc.ui.fbm.discount.status.DiscountBillTypeStatus;
import nc.ui.fbm.pub.btnstatus.FBMVoucherBtnStatus;
import nc.ui.pub.ButtonObject;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.UAPBillOperate;
import nc.vo.engine.status.UAPBillStatus;
import nc.vo.engine.status.assemble.IStatusAssemble;
import nc.vo.engine.status.assemble.StatusAssemble;
import nc.vo.engine.status.core.UAPButtonObjectStatusEngine;
import nc.vo.engine.status.factory.IStatusBuildFactory;
import nc.vo.fbm.consignbank.CollectionVO;

public class ConsignStatusEngine extends UAPButtonObjectStatusEngine {
	/**
	 * @param ui
	 */
	public ConsignStatusEngine(AbstractBillUI ui) {
		super(ui);
	}

	@Override
	protected IStatusAssemble createStatusAssemble() {
		StatusAssemble assemble = new StatusAssemble();
		assemble.registStatusType(new UAPBillOperate(getUI()));
		assemble.registStatusType(new UAPBillStatus(getUI()));
		assemble.registStatusType(new FBMVoucherBtnStatus(getUI(),CollectionVO.UNITVOUCHER));
		assemble.registStatusType(new DiscountBillTypeStatus(getUI()));//根据统管和自有票据判断
		
		return assemble ;
	}

	protected IStatusBuildFactory<ButtonObject> createStatusBuildFactory() {
		return new ConsignStatusBuildFactory();
	}

}