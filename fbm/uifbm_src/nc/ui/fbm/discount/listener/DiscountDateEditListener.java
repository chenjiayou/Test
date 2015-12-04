package nc.ui.fbm.discount.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * <p>
 * 贴现界面实际贴现日期编辑listener
 * <p>
 * 创建人：bsrl <b>日期：2007-09
 * 
 */
public class DiscountDateEditListener extends AbstractItemEditListener {

	/**
	 * 
	 */
	public DiscountDateEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountDateEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountDateEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		Integer discountdelayday = null;
		BillCardPanel panel = getUI().getBillCardPanel();
		Object objdelayday = panel.getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM)
				.getValueObject();
		if (objdelayday != null && objdelayday.toString().trim().length() > 0) {
			discountdelayday = new Integer((String) objdelayday);
		} else {
			discountdelayday = new Integer(0);
		}

		String discountDate = (String) panel.getHeadItem(
				DiscountVO.DDISCOUNTDATE).getValueObject();
		if (null == discountDate || "".equals(discountDate.trim())) {
			return;
		}

		UFDate txrq = new UFDate(discountDate);

		String strrate = (String) panel.getHeadItem(DiscountVO.DISCOUNTYRATE)
				.getValueObject();
		UFDouble ratevalue = null;
		if (!CommonUtil.isNull(strrate)) {
			ratevalue = new UFDouble((String) (panel
					.getHeadItem(DiscountVO.DISCOUNTYRATE).getValueObject()));
		}
		if (ratevalue != null) {
			UFDate dqrq = new UFDate((String) panel
					.getHeadItem(DiscountVO.DQRQ).getValueObject());
			if (dqrq.after(txrq)) {
				Integer ratedaynum = (Integer) panel.getHeadItem(
						DiscountVO.RATEDAYNUM).getValueObject();
				UFDouble hpje = null;
				String strhpje = null;
				if (panel.getHeadItem(DiscountVO.HPJE).getValueObject() != null) {
					strhpje = (String) (panel.getHeadItem(DiscountVO.HPJE)
							.getValueObject());
				}
				UFDouble discountcharge = null;
				if (panel.getHeadItem(DiscountVO.DISCOUNTCHARGE)
						.getValueObject() != null
						&& panel.getHeadItem(DiscountVO.DISCOUNTCHARGE)
								.getValueObject().toString().trim().length() > 0) {
					discountcharge = new UFDouble((String) panel.getHeadItem(
							DiscountVO.DISCOUNTCHARGE).getValueObject());
				}

				if (strhpje != null && strhpje.trim().length() != 0) {
					hpje = new UFDouble((String) (panel
							.getHeadItem(DiscountVO.HPJE).getValueObject()));
					String currtype = (String) (panel
							.getHeadItem(DiscountVO.YBBZ).getValueObject());
					String pk_corp = (String) (panel
							.getHeadItem(DiscountVO.PK_CORP).getValueObject());
					UFDouble discountinterest = null;
					if (txrq != null && ratedaynum != null) {
						discountinterest = DiscountCalculate
								.calculateDiscountInterest(hpje, txrq, dqrq,
										currtype, discountdelayday, ratevalue,
										ratedaynum, pk_corp);
						// discountinterest = hpje.multiply(intdate +
						// discountdelayday).multiply(ratevalue).div(100).div(ratedaynum);
					}
					panel.getHeadItem(DiscountVO.DISCOUNTINTEREST).setValue(
							discountinterest);
					if (discountcharge != null) {
						panel.getHeadItem(DiscountVO.MONEYY).setValue(
								hpje.sub(discountinterest).sub(discountcharge));
					} else {
						panel.getHeadItem(DiscountVO.MONEYY).setValue(
								hpje.sub(discountinterest));
					}
				}
			} else {
				panel.getHeadItem(DiscountVO.MONEYY).setValue(null);
				panel.getHeadItem(DiscountVO.DISCOUNTINTEREST).setValue(null);
			}
		}
	}

}
