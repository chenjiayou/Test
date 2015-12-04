/**
 *
 */
package nc.ui.fbm.accept;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.fbm.pub.refmodel.BaseInfoAccBankRefModel;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-9-7
 * 
 */
public class AcceptBillRefTakenProcessor extends RefTakenProccessor {

	/**
	 * @param listpanel
	 * @param cardpanel
	 */
	public AcceptBillRefTakenProcessor(BillListPanel listpanel,
			BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.fac.account.pub.RefTakenProccessor#createTakenColUtil()
	 */
	@Override
	protected ITakenColUtil createTakenColUtil() {
		// TODO Auto-generated method stub
		return new AbstractTakenColUtil() {
			@Override
			protected RefTakenVO[] getRefTakenVOs() {
				return new RefTakenVO[] {
						new RefTakenVO(
								AcceptVO.BASEINFO_TAB,
								AcceptVO.PK_SOURCE,
								new String[] { AcceptVO.ACCEPTANCENO,
										AcceptVO.CONTRACTNO, AcceptVO.ENDDATE,
										AcceptVO.FBMBILLTYPE,
										AcceptVO.INVOICEDATE,
										AcceptVO.INVOICEUNIT,
										AcceptVO.BILLMONEYY, AcceptVO.PAYUNIT,
										AcceptVO.PAYBANKACC,
										AcceptVO.RECEIVEUNIT,
										AcceptVO.RECEIVEBANKACC,
										AcceptVO.PK_BASEINFO,
										AcceptVO.SECURITYACCOUNT,
										AcceptVO.SECURITYMONEY,
										AcceptVO.IMPAWNMODE, AcceptVO.PK_CURR,
										AcceptVO.CCTYPE, AcceptVO.PK_LOANBANK ,
										AcceptVO.FBMBILLNO,
										AcceptVO.BACKSECACCOUNT,
										//AcceptVO.BACKSECMONEY,
										AcceptVO.PAYBANK,
										AcceptVO.RECEIVEBANK},
								new String[] { BaseinfoVO.ACCEPTANCENO,
										BaseinfoVO.CONTRACTNO,
										BaseinfoVO.ENDDATE,
										BaseinfoVO.FBMBILLTYPE,
										BaseinfoVO.INVOICEDATE,
										BaseinfoVO.INVOICEUNIT,
										BaseinfoVO.MONEYY,
										BaseinfoVO.PAYUNIT,
										BaseinfoVO.PAYBANKACC,
										BaseinfoVO.RECEIVEUNIT,
										BaseinfoVO.RECEIVEBANKACC,
										BaseinfoVO.PK_BASEINFO,
										RegisterVO.SECURITYACCOUNT,
										RegisterVO.SECURITYMONEY,
										RegisterVO.IMPAWNMODE,
										RegisterVO.PK_CURR,
										RegisterVO.CCTYPE,
										RegisterVO.PK_LOANBANK ,
										RegisterVO.FBMBILLNO,
										RegisterVO.SECURITYACCOUNT,
										//RegisterVO.SECURITYMONEY,
										RegisterVO.PAYBANK,
										RegisterVO.RECEIVEBANK}),
//						new RefTakenVO(
//								AcceptVO.BASEINFO_TAB,
//								AcceptVO.PAYBANKACC,
//								new String[] { RegisterVO.PAYBANK },
//								new String[] { BaseInfoAccBankRefModel.BANKDOC_FIELD }),
//						new RefTakenVO(
//								AcceptVO.BASEINFO_TAB,
//								AcceptVO.RECEIVEBANKACC,
//								new String[] { RegisterVO.RECEIVEBANK },
//								new String[] { BaseInfoAccBankRefModel.BANKDOC_FIELD })

				};
			}

			@Override
			public CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[] {
						new CurrencyDecimalVO(AcceptVO.tablecode,
								AcceptVO.PK_CURR, new String[] {
										AcceptVO.MONEYY, AcceptVO.MONEYF,
										AcceptVO.MONEYB, AcceptVO.FRATE,
										AcceptVO.BRATE }),
						new CurrencyDecimalVO(AcceptVO.tablecode,
								AcceptVO.PK_CURR,
								new String[] { AcceptVO.SECURITYMONEY,AcceptVO.SECURITYMONEYF,AcceptVO.SECURITYMONEYB,AcceptVO.FRATE,AcceptVO.BRATE }),
						new CurrencyDecimalVO(AcceptVO.tablecode,
								AcceptVO.PK_CURR,
								new String[] { AcceptVO.BILLMONEYY }),
						new CurrencyDecimalVO(AcceptVO.tablecode,
								AcceptVO.PK_CURR,
								new String[] { AcceptVO.BACKSECMONEY }) };
			}
		};
	}

}
