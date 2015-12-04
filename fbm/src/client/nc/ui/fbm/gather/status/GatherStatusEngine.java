/**
 *
 */
package nc.ui.fbm.gather.status;

import nc.ui.pub.ButtonObject;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.UAPBillOperate;
import nc.vo.engine.status.UAPBillStatus;
import nc.vo.engine.status.assemble.IStatusAssemble;
import nc.vo.engine.status.assemble.StatusAssemble;
import nc.vo.engine.status.core.UAPButtonObjectStatusEngine;
import nc.vo.engine.status.factory.IStatusBuildFactory;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-9-18
 *
 */
public class GatherStatusEngine extends UAPButtonObjectStatusEngine {

	/**
	 * @param ui
	 */
	public GatherStatusEngine(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.core.AbstractStatusEngine#createStatusAssemble()
	 */
	@Override
	protected IStatusAssemble createStatusAssemble() {
		// TODO Auto-generated method stub
		StatusAssemble assemble = new StatusAssemble();
		assemble.registStatusType(new UAPBillOperate(getUI()));
		assemble.registStatusType(new UAPBillStatus(getUI()));
		assemble.registStatusType(new GatherFbillTypeStatus(getUI()));
		assemble.registStatusType(new GatherIsEndureStatus(getUI()));
		return assemble;
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.core.AbstractStatusEngine#createStatusBuildFactory()
	 */
	@Override
	protected IStatusBuildFactory<ButtonObject> createStatusBuildFactory() {
		// TODO Auto-generated method stub
		return new GatherStatusBuildFactory();
	}

}
