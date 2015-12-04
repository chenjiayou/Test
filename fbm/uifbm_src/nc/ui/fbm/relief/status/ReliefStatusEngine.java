package nc.ui.fbm.relief.status;

import nc.ui.fbm.storage.status.ApproveStatus;
import nc.ui.fbm.storage.status.DealSelfBillStatus;
import nc.ui.fbm.storage.status.LoginCenterStatus;
import nc.ui.fbm.storage.status.OwnerCorpStatus;
import nc.ui.fbm.storage.status.TallyStatus;
import nc.ui.fbm.storage.status.UnApproveStatus;
import nc.ui.fbm.storage.status.UnitBillStatus;
import nc.ui.fbm.storage.status.VoucherStatus;
import nc.ui.pub.ButtonObject;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.UAPBillOperate;
import nc.vo.engine.status.UAPBillStatus;
import nc.vo.engine.status.assemble.IStatusAssemble;
import nc.vo.engine.status.assemble.StatusAssemble;
import nc.vo.engine.status.core.UAPButtonObjectStatusEngine;
import nc.vo.engine.status.factory.IStatusBuildFactory;
import nc.vo.fbm.relief.ReliefVO;
/**
 * 
 * 类功能说明：
     状态设置引擎
 * 日期：2007-10-29
 * 程序员： wues
 *
 */
public class ReliefStatusEngine extends UAPButtonObjectStatusEngine {
	/**
	 * @param ui
	 */
	public ReliefStatusEngine(AbstractBillUI ui) {
		super(ui);
	}

	@Override
	protected IStatusAssemble createStatusAssemble() {
		StatusAssemble assemble = new StatusAssemble();
		assemble.registStatusType(new UAPBillOperate(getUI()));
		assemble.registStatusType(new UAPBillStatus(getUI()));
		assemble.registStatusType(new ApproveStatus(getUI()));
		assemble.registStatusType(new UnApproveStatus(getUI()));
		assemble.registStatusType(new OwnerCorpStatus(getUI()));
		assemble.registStatusType(new TallyStatus(getUI(),ReliefVO.RELIEFCORP,ReliefVO.UNITTALLY));
		assemble.registStatusType(new VoucherStatus(getUI(),ReliefVO.RELIEFCORP,ReliefVO.UNITVOUCHER,ReliefVO.CENTERVOUCHER));
		assemble.registStatusType(new LoginCenterStatus(getUI()));
		assemble.registStatusType(new UnitBillStatus(getUI(),ReliefVO.RELIEFCORP));
		assemble.registStatusType(new DealSelfBillStatus(getUI(),ReliefVO.PK_CORP));
		assemble.registStatusType(new IsUnitWriteBankaccStatus(getUI()));
		return assemble;
	}

	protected IStatusBuildFactory<ButtonObject> createStatusBuildFactory() {
		return new ReliefStatusBuildFactory();
	}

}
