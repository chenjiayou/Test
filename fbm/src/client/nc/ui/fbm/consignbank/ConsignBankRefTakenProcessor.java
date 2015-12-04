package nc.ui.fbm.consignbank;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.fbm.pub.refmodel.BaseInfoAccBankRefModel;
import nc.ui.fbm.pub.refmodel.FBMBankAccBasRefModel;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 银行托收携带类
 * <p>
 * 创建人：bsrl <b>日期：2007-09
 * 
 */
public class ConsignBankRefTakenProcessor extends RefTakenProccessor {

	public ConsignBankRefTakenProcessor(BillListPanel listpanel,
			BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
	}

	@Override
	protected ITakenColUtil createTakenColUtil() {
		return new AbstractTakenColUtil() {
			protected RefTakenVO[] getRefTakenVOs() {
				return new RefTakenVO[] {
						new RefTakenVO(
								"haedbaseinfo",
								CollectionVO.PK_SOURCE,
								new String[] { CollectionVO.PJLX,
										CollectionVO.FKDW,
										CollectionVO.FKDWYHZH,
										CollectionVO.CPRMC, 
										CollectionVO.YBBZ,
										CollectionVO.PMJE, 
										CollectionVO.MONEYY,
										CollectionVO.CPRQ, 
										CollectionVO.DQRQ,
										CollectionVO.SPRQ,
										CollectionVO.PK_BASEINFO,
										CollectionVO.FRATE, 
										CollectionVO.BRATE,
										CollectionVO.MONEYF,
										CollectionVO.MONEYB,
										CollectionVO.RECEIVEUNIT,
										CollectionVO.RECEIVEBANKACC,
										CollectionVO.FBMBILLNO,
										CollectionVO.FKKHH},
								new String[] { RegisterVO.FBMBILLTYPE,
										RegisterVO.PAYUNIT,
										RegisterVO.PAYBANKACC,
										RegisterVO.HOLDUNIT,
										RegisterVO.PK_CURR, 
										RegisterVO.MONEYY,
										RegisterVO.MONEYY,
										RegisterVO.INVOICEDATE,	
										RegisterVO.ENDDATE,
										RegisterVO.GATHERDATE,
										RegisterVO.PK_BASEINFO,
										RegisterVO.FRATE, 
										RegisterVO.BRATE,
										RegisterVO.MONEYF,
										RegisterVO.MONEYB,
										RegisterVO.RECEIVEUNIT, 
										RegisterVO.RECEIVEBANKACC,
										RegisterVO.FBMBILLNO,
										RegisterVO.PAYBANK}),
//						  new RefTakenVO(
//								  	"fbm_collection", CollectionVO.PK_SOURCE,
//									new String[]{CollectionVO.FBMBILLNO},new String[]{RegisterVO.FBMBILLNO}
//									),				
//						new RefTakenVO("haedbaseinfo", CollectionVO.FKDWYHZH,
//								new String[] { CollectionVO.FKKHH },
//								new String[] { BaseInfoAccBankRefModel.BANKDOC_FIELD }),
						new RefTakenVO("fbm_collection",
								CollectionVO.HOLDERACC,
								new String[] { CollectionVO.HOLDERBANK },
								new String[] { FBMBankAccBasRefModel.BANKDOC_FIELD })

				};
			}

			@Override
			public CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[] {
						new CurrencyDecimalVO(
								"haedbaseinfo",
								CollectionVO.YBBZ,
								new String[] { CollectionVO.MONEYY,
										CollectionVO.MONEYF,
										CollectionVO.MONEYB,
										CollectionVO.FRATE, CollectionVO.BRATE }),
						new CurrencyDecimalVO("haedbaseinfo", DiscountVO.YBBZ,
								new String[] { CollectionVO.PMJE }) };
			}

		};
	}

}
