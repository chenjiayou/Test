package nc.ui.fbm.impawn.listener;

import nc.ui.fbm.impawn.ImpawnUI;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.pub.lang.UFDouble;

/**
 * ���ܣ� ������ֵ������ ���ڣ�2007-9-20 ����Ա��wues
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
		// ȡ����Ѻ��
		String impawnRateStr = (String) getUI().getBillCardPanel().getHeadItem(
				ImpawnVO.IMPAWNRATE).getValueObject();
		// ȡ��������ֵ
		String evaluateValueStr = (String) getUI().getBillCardPanel()
				.getHeadItem(ImpawnVO.EVALUATEVALUE).getValueObject();



		// ȡ�õ���һ��Ϊ�գ������ѺֵΪ��
		if (null == evaluateValueStr || "".equals(evaluateValueStr.trim())
				|| null == impawnRateStr || "".equals(impawnRateStr)) {
			getUI().getBillCardPanel().getHeadItem(ImpawnVO.IMPAWNABLE)
					.setValue(new UFDouble(0));
		}

		double impawnRate = new UFDouble(impawnRateStr).getDouble();

		if ( impawnRate < 0  || impawnRate >100) {
			((ImpawnUI)getUI()).showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000004")/* @res"��Ѻ�ʱ������0��С��100"*/);
			((ImpawnUI)getUI()).getBillCardPanel().getHeadItem(ImpawnVO.IMPAWNRATE).setValue("");//��ֵ���
			return;
		}

		// ���߶���Ϊ��
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.IMPAWNABLE).setValue(
				new UFDouble(evaluateValueStr).multiply(
						new UFDouble(impawnRateStr)).div(100));

		getUI().fireCardAfterEdit(ImpawnVO.IMPAWNABLE);
	}

}