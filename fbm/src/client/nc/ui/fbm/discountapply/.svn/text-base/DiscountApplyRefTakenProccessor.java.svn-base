package nc.ui.fbm.discountapply;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.fbm.pub.refmodel.BaseInfoAccBankRefModel;
import nc.ui.fbm.pub.refmodel.FBMBankAccBasRefModel;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.register.RegisterVO;

/**
 * 
 * <p>
 *	贴现申请界面携带类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountApplyRefTakenProccessor extends RefTakenProccessor {
 
	public DiscountApplyRefTakenProccessor(BillListPanel listpanel,
			BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
	}

	@Override
	protected ITakenColUtil createTakenColUtil() {
		return new AbstractTakenColUtil(){
			protected RefTakenVO[] getRefTakenVOs() {
				return new RefTakenVO[]{
					new RefTakenVO("headbaseinfo",DiscountVO.PK_SOURCE,
							new String[]{DiscountVO.PJLX, 
							DiscountVO.HPCDRMC,
							DiscountVO.HPCDRYHZH, 
							DiscountVO.YBBZ, 
							DiscountVO.HPJE,
							DiscountVO.SPRQ,
							DiscountVO.CPRQ, 
							DiscountVO.DQRQ, 
							DiscountVO.PK_BASEINFO,
							DiscountVO.FRATE,
							DiscountVO.BRATE , 
							DiscountVO.CPRMC ,
							DiscountVO.HOLDERACC,
							DiscountVO.FBMBILLNO,
							DiscountVO.HOLDERBANK,
							DiscountVO.HPCDRKHH},
							new String[]{RegisterVO.FBMBILLTYPE,
							RegisterVO.PAYUNIT,
							RegisterVO.PAYBANKACC,  
							RegisterVO.PK_CURR, 
							RegisterVO.MONEYY, 
							RegisterVO.GATHERDATE, 
							RegisterVO.INVOICEDATE,
							RegisterVO.ENDDATE, 
							RegisterVO.PK_BASEINFO,
							RegisterVO.FRATE, 
							RegisterVO.BRATE ,
							RegisterVO.HOLDUNIT,
							RegisterVO.RECEIVEBANKACC,
							RegisterVO.FBMBILLNO,
							RegisterVO.RECEIVEBANK,
							RegisterVO.PAYBANK}),

//					new RefTakenVO("fbm_discount",DiscountVO.HOLDERACC,
//							new String[]{DiscountVO.HOLDERBANK },
//							new String[]{FBMBankAccBasRefModel.BANKDOC_FIELD }),
//					new RefTakenVO("headbaseinfo",DiscountVO.HPCDRYHZH,
//							new String[]{DiscountVO.HPCDRKHH },
//							new String[]{BaseInfoAccBankRefModel.BANKDOC_FIELD }),
					new RefTakenVO("headbaseinfo",DiscountVO.DISCOUNT_ACCOUNT,
							new String[]{DiscountVO.PK_DISCOUNT_BANK},
							new String[]{BaseInfoAccBankRefModel.BANKDOC_FIELD})
				};
		    }
			
			@Override
			public CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[]{
						new CurrencyDecimalVO(
						"headbaseinfo", DiscountVO.YBBZ, new String[] {
								DiscountVO.HPJE}),
						new CurrencyDecimalVO(
								"headbaseinfo", DiscountVO.YBBZ, new String[] {
										DiscountVO.DISCOUNTINTEREST,DiscountVO.INTERESTMONEYF, DiscountVO.INTERESTMONEYB, DiscountVO.FRATE,
										DiscountVO.BRATE }),
						new CurrencyDecimalVO(
								"headbaseinfo", DiscountVO.YBBZ, new String[] {
										DiscountVO.MONEYY,DiscountVO.MONEYF, DiscountVO.MONEYB, DiscountVO.FRATE,
										DiscountVO.BRATE }),
						new CurrencyDecimalVO(
								"headbaseinfo", DiscountVO.YBBZ, new String[] {
										DiscountVO.DISCOUNTCHARGE,DiscountVO.CHARGEMONEYF, DiscountVO.CHARGEMONEYB, DiscountVO.FRATE,
										DiscountVO.BRATE })
				};
			}
			
		};
	}

}
