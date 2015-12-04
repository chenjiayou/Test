package nc.ui.fbm.discount.status;

import nc.ui.fbm.pub.btnstatus.FBMVoucherBtnStatus;
import nc.ui.pub.ButtonObject;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.UAPBillOperate;
import nc.vo.engine.status.UAPBillStatus;
import nc.vo.engine.status.assemble.IStatusAssemble;
import nc.vo.engine.status.assemble.StatusAssemble;
import nc.vo.engine.status.core.UAPButtonObjectStatusEngine;
import nc.vo.engine.status.factory.IStatusBuildFactory;
import nc.vo.fbm.discount.DiscountVO;
/**
 * 
 ***********************************************************
 * 日期：2008-3-28							   
 * 程序员:吴二山 							   
 * 功能：贴现办理的状态控制类						   
 ***********************************************************
 */
public class DiscountStatusEngine extends UAPButtonObjectStatusEngine {
	/**
	 * @param ui
	 */
	public DiscountStatusEngine(AbstractBillUI ui) {
		super(ui);
	}

	@Override
	protected IStatusAssemble createStatusAssemble() {
		StatusAssemble assemble = new StatusAssemble();
		assemble.registStatusType(new UAPBillOperate(getUI()));
		assemble.registStatusType(new UAPBillStatus(getUI()));
		assemble.registStatusType(new FBMVoucherBtnStatus(getUI(),DiscountVO.UNITVOUCHER));
		assemble.registStatusType(new DiscountVouchStatus(getUI()));
		assemble.registStatusType(new DiscountBillTypeStatus(getUI()));
		
		return assemble ;
	}

	protected IStatusBuildFactory<ButtonObject> createStatusBuildFactory() {
		return new DiscountStatusBuildFactory();
	}

}
