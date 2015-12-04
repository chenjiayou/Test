package nc.ui.fbm.impawn.listener;

import nc.ui.fbm.impawn.ImpawnUI;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.pub.lang.UFDouble;

/**
 * 功能： 评估价值监听器 日期：2007-9-20 程序员：wues
 */
public class ImpawnValueListener extends AbstractItemEditListener {

	public ImpawnValueListener() {
		super();

	}

	public ImpawnValueListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);

	}

	public ImpawnValueListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);

	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent){
		// 取得质押率
		String impawnRateStr = (String) getUI().getBillCardPanel().getHeadItem(
				ImpawnVO.IMPAWNRATE).getValueObject();
		// 取得评估价值
		String evaluateValueStr = (String) getUI().getBillCardPanel()
				.getHeadItem(ImpawnVO.EVALUATEVALUE).getValueObject();



		// 取得的有一个为空，设可质押值为空
		if (null == evaluateValueStr || "".equals(evaluateValueStr.trim())
				|| null == impawnRateStr || "".equals(impawnRateStr)) {
			getUI().getBillCardPanel().getHeadItem(ImpawnVO.IMPAWNABLE)
					.setValue(new UFDouble(0));
		}

		double impawnRate = new UFDouble(impawnRateStr).getDouble();

		if ( impawnRate < 0  || impawnRate >100) {
			((ImpawnUI)getUI()).showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000004")/* @res"质押率必须大于0且小于100"*/);
			((ImpawnUI)getUI()).getBillCardPanel().getHeadItem(ImpawnVO.IMPAWNRATE).setValue("");//将值清空
			return;
		}

		// 两者都不为空
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.IMPAWNABLE).setValue(
				new UFDouble(evaluateValueStr).multiply(
						new UFDouble(impawnRateStr)).div(100));

		getUI().fireCardAfterEdit(ImpawnVO.IMPAWNABLE);
	}

}