package nc.ui.fbm.impawn;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.fbm.pub.refmodel.BaseInfoAccBankRefModel;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.register.RegisterVO;

/**
 * 功能： 票据质押时选择票据编号带回的一批信息 日期：2007-9-20 程序员：wues
 */
public class ImpawnRefTakenProccessor extends RefTakenProccessor {

	public ImpawnRefTakenProccessor(BillListPanel listpanel,
			BillCardPanel cardpanel) {
		super(listpanel, cardpanel);

	}

	@Override
	protected ITakenColUtil createTakenColUtil() {
		return new AbstractTakenColUtil() {
			protected RefTakenVO[] getRefTakenVOs() {
				return new RefTakenVO[] {
						new RefTakenVO("fbm_impawn_base", ImpawnVO.PK_SOURCE,
								new String[] { 
										ImpawnVO.FBMBILLNO,
										ImpawnVO.FBMBILLTYPE,
										ImpawnVO.GATHERDATE,
										ImpawnVO.INVOICEDATE, 
										ImpawnVO.ENDDATE,
										ImpawnVO.HOLDUNIT, 
										ImpawnVO.PAYUNIT,
										ImpawnVO.PAYBANKACC, 
										ImpawnVO.PK_CURR,
										ImpawnVO.INVOICEUNIT, 
										ImpawnVO.MONEYY,
										ImpawnVO.PK_BASEINFO,
										ImpawnVO.EVALUATEVALUE, 
										ImpawnVO.BRATE,
										ImpawnVO.FRATE, 
										ImpawnVO.MONEYB,
										ImpawnVO.MONEYF,
										ImpawnVO.PAYBANK}, 
								new String[] {
										RegisterVO.FBMBILLNO,
										RegisterVO.FBMBILLTYPE,
										RegisterVO.GATHERDATE,
										RegisterVO.INVOICEDATE,
										RegisterVO.ENDDATE,
										RegisterVO.HOLDUNIT,
										RegisterVO.PAYUNIT,
										RegisterVO.PAYBANKACC,
										RegisterVO.PK_CURR,
										RegisterVO.INVOICEUNIT,
										RegisterVO.MONEYY,
										RegisterVO.PK_BASEINFO,
										RegisterVO.MONEYY, 
										RegisterVO.BRATE,
										RegisterVO.FRATE, 
										RegisterVO.MONEYB,
										RegisterVO.MONEYF ,
										RegisterVO.PAYBANK}),

						new RefTakenVO("fbm_impawn_base",
								ImpawnVO.PAYBANKACC,// 根据付款银行账号得到付款银行名称
								new String[] { ImpawnVO.PAYBANK },
								new String[] { BaseInfoAccBankRefModel.BANKDOC_FIELD })

				};
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see nc.ui.fac.account.pub.AbstractTakenColUtil#getCurrencyDecimalVOs()
			 */
			@Override
			protected CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[] { new CurrencyDecimalVO(
						"fbm_impawn_base",
						ImpawnVO.PK_CURR,
						new String[] { ImpawnVO.MONEYY, ImpawnVO.MONEYB,
								ImpawnVO.MONEYF, ImpawnVO.BRATE, ImpawnVO.FRATE }) };
			}

		};

	}

}
