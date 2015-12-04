package nc.ui.fbm.discount;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.fac.account.pub.SpecialTakenVO;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.register.RegisterVO;

/**
 * 
 * <p>
 * 贴现办理携带类
 * <p>
 * 创建人：bsrl <b>日期：2007-09
 * 
 */
public class DiscountRefTakenProccessor_Print extends RefTakenProccessor {

	public DiscountRefTakenProccessor_Print(BillListPanel listpanel,
			BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
	}

	@Override
	protected ITakenColUtil createTakenColUtil() {
		return new AbstractTakenColUtil() {
			protected RefTakenVO[] getRefTakenVOs() {
				return new RefTakenVO[] {
						new RefTakenVO("fbm_discount",
								DiscountVO.PK_DISCOUNT_APP, new String[] {
										DiscountVO.DDISCOUNTDATE,
										DiscountVO.DISCOUNTDELAYDAYNUM,
										DiscountVO.DISCOUNTYRATE,
										DiscountVO.RATEDAYNUM,
										DiscountVO.DISCOUNTINTEREST,
										DiscountVO.DISCOUNTCHARGE,
										DiscountVO.MONEYY, DiscountVO.CPRMC,
										DiscountVO.DISCOUNT_ACCOUNT,
										DiscountVO.PJLX, DiscountVO.PK_SOURCE,
										DiscountVO.HPCDRMC,
										DiscountVO.HPCDRYHZH, DiscountVO.YBBZ,
										DiscountVO.PJLX, DiscountVO.HPJE,
										DiscountVO.SPRQ, DiscountVO.CPRQ,
										DiscountVO.DQRQ,
										DiscountVO.PK_BASEINFO,
										DiscountVO.FRATE, DiscountVO.BRATE,
										DiscountVO.MONEYF, DiscountVO.MONEYB,
										DiscountVO.CHARGEMONEYF,
										DiscountVO.CHARGEMONEYB,
										DiscountVO.INTERESTMONEYF,
										DiscountVO.INTERESTMONEYB,
										DiscountVO.OPBILLTYPE }, new String[] {
										DiscountVO.DDISCOUNTDATE,
										DiscountVO.DISCOUNTDELAYDAYNUM,
										DiscountVO.DISCOUNTYRATE,
										DiscountVO.RATEDAYNUM,
										DiscountVO.DISCOUNTINTEREST,
										DiscountVO.DISCOUNTCHARGE, "dismoneyy",
										RegisterVO.HOLDUNIT,
										DiscountVO.DISCOUNT_ACCOUNT,
										RegisterVO.FBMBILLTYPE,
										RegisterVO.PK_REGISTER,
										RegisterVO.PAYUNIT,
										RegisterVO.PAYBANKACC,
										RegisterVO.PK_CURR,
										RegisterVO.FBMBILLTYPE,
										RegisterVO.MONEYY,
										RegisterVO.GATHERDATE,
										RegisterVO.INVOICEDATE,
										RegisterVO.ENDDATE,
										DiscountVO.PK_BASEINFO,
										DiscountVO.FRATE, DiscountVO.BRATE,
										DiscountVO.MONEYF, DiscountVO.MONEYB,
										DiscountVO.CHARGEMONEYF,
										DiscountVO.CHARGEMONEYB,
										DiscountVO.INTERESTMONEYF,
										DiscountVO.INTERESTMONEYB,
										DiscountVO.OPBILLTYPE }),
						new RefTakenVO("fbm_discount",
								DiscountVO.FBMBILLNO,
								new String[] { DiscountVO.PJLX,
										DiscountVO.HPCDRMC,
										DiscountVO.HPCDRYHZH, DiscountVO.CPRMC,
										DiscountVO.YBBZ, DiscountVO.PJLX,
										DiscountVO.HPJE, DiscountVO.SPRQ,
										DiscountVO.CPRQ, DiscountVO.DQRQ,
										DiscountVO.PK_BASEINFO,
										DiscountVO.FRATE, DiscountVO.BRATE,
										DiscountVO.FBMBILLNO },
								new String[] { RegisterVO.FBMBILLTYPE,
										RegisterVO.PAYUNIT,
										RegisterVO.PAYBANKACC,
										RegisterVO.HOLDUNIT,
										RegisterVO.PK_CURR,
										RegisterVO.FBMBILLTYPE,
										RegisterVO.MONEYY,
										RegisterVO.GATHERDATE,
										RegisterVO.INVOICEDATE,
										RegisterVO.ENDDATE,
										RegisterVO.PK_BASEINFO,
										RegisterVO.FRATE, RegisterVO.BRATE,
										RegisterVO.FBMBILLNO }),
//						  new RefTakenVO(
//								  	"fbm_discount", DiscountVO.PK_SOURCE,
//									new String[]{DiscountVO.FBMBILLNO},new String[]{RegisterVO.FBMBILLNO}
//									),
						new RefTakenVO("fbm_discount", DiscountVO.HOLDERACC,
								new String[] { DiscountVO.HOLDERBANK },
								new String[] { "bankname" }),
						new RefTakenVO("headbaseinfo", DiscountVO.HPCDRYHZH,
								new String[] { DiscountVO.HPCDRKHH },
								new String[] { "bankname" }),
						new RefTakenVO("fbm_discount",
								DiscountVO.DISCOUNT_ACCOUNT,
								new String[] { DiscountVO.PK_DISCOUNT_BANK },
								new String[] { "bankname" }) };
			}

			@Override
			public CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[] {
						new CurrencyDecimalVO("headbaseinfo", DiscountVO.YBBZ,
								new String[] { DiscountVO.HPJE }),
						new CurrencyDecimalVO("headbaseinfo", DiscountVO.YBBZ,
								new String[] { DiscountVO.DISCOUNTINTEREST,
										DiscountVO.INTERESTMONEYF,
										DiscountVO.INTERESTMONEYB,
										DiscountVO.FRATE, DiscountVO.BRATE }),
						new CurrencyDecimalVO("headbaseinfo", DiscountVO.YBBZ,
								new String[] { DiscountVO.MONEYY,
										DiscountVO.MONEYF, DiscountVO.MONEYB,
										DiscountVO.FRATE, DiscountVO.BRATE }),
						new CurrencyDecimalVO("headbaseinfo", DiscountVO.YBBZ,
								new String[] { DiscountVO.DISCOUNTCHARGE,
										DiscountVO.CHARGEMONEYF,
										DiscountVO.CHARGEMONEYB,
										DiscountVO.FRATE, DiscountVO.BRATE }) };
			}

			public SpecialTakenVO[] getSpecialTakenVOs() {
				return new SpecialTakenVO[] { new SpecialTakenVO(
						"fbm_discount", DiscountVO.PK_SOURCE,
						DiscountVO.DISCOUNTDELAYDAYNUM,
						DiscountVO.DISCOUNTYRATE, DiscountVO.RATEDAYNUM,
						DiscountVO.DISCOUNTINTEREST, DiscountVO.DISCOUNTCHARGE,
						DiscountVO.MONEYY, DiscountVO.FRATE, DiscountVO.BRATE), };
			}

		};
	}

}
