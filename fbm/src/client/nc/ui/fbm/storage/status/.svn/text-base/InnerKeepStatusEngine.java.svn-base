/**
 *
 */
package nc.ui.fbm.storage.status;

import nc.ui.pub.ButtonObject;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.UAPBillOperate;
import nc.vo.engine.status.UAPBillStatus;
import nc.vo.engine.status.assemble.IStatusAssemble;
import nc.vo.engine.status.assemble.StatusAssemble;
import nc.vo.engine.status.core.UAPButtonObjectStatusEngine;
import nc.vo.engine.status.factory.IStatusBuildFactory;
import nc.vo.fbm.storage.StorageVO;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-10-11
 *
 */
public class InnerKeepStatusEngine extends UAPButtonObjectStatusEngine {

	/**
	 * @param ui
	 */
	public InnerKeepStatusEngine(AbstractBillUI ui) {
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
		assemble.registStatusType(new ApproveStatus(getUI()));
		assemble.registStatusType(new UnApproveStatus(getUI()));
		assemble.registStatusType(new OwnerCorpStatus(getUI()));
		assemble.registStatusType(new TallyStatus(getUI(),StorageVO.KEEPCORP,StorageVO.UNITTALLY));
		assemble.registStatusType(new VoucherStatus(getUI(),StorageVO.KEEPCORP,StorageVO.UNITVOUCHER,StorageVO.CENTERVOUCHER));
		assemble.registStatusType(new LoginCenterStatus(getUI()));
		assemble.registStatusType(new UnitBillStatus(getUI(),StorageVO.KEEPCORP));
		assemble.registStatusType(new DealSelfBillStatus(getUI(),StorageVO.KEEPCORP));
		assemble.registStatusType(new BillStatusWithPKCorp(getUI(),StorageVO.KEEPCORP));
		return assemble ;
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.core.AbstractStatusEngine#createStatusBuildFactory()
	 */
	protected IStatusBuildFactory<ButtonObject> createStatusBuildFactory() {
		return new StorageStatusBuildFactory();
	}

}
