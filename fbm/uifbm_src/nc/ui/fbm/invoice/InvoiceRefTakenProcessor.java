/**
 *
 */
package nc.ui.fbm.invoice;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.fbm.pub.refmodel.BaseInfoAccBankRefModel;
import nc.ui.fbm.pub.refmodel.FBMBankAccBasRefModel;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-9-4
 *
 */
public class InvoiceRefTakenProcessor extends RefTakenProccessor {

	public InvoiceRefTakenProcessor(BillListPanel listpanel,
			BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ITakenColUtil createTakenColUtil() {
		// TODO Auto-generated method stub
		return new AbstractTakenColUtil() {
			protected RefTakenVO[] getRefTakenVOs() {
				// TODO Auto-generated method stub
				return new RefTakenVO[] {
						new RefTakenVO(
								RegisterVO.BASEINFO_TAB,
								RegisterVO.PK_BASEINFO,
								new String[] {
										RegisterVO.ACCEPTANCENO,
										RegisterVO.CONTRACTNO,
										RegisterVO.ENDDATE, 
										RegisterVO.INVOICEDATE,
										RegisterVO.INVOICEUNIT, 
										RegisterVO.PK_CURR, 
										RegisterVO.MONEYY,
										RegisterVO.FBMBILLNO,
										RegisterVO.PAYUNIT,
										RegisterVO.PAYBANKACC,
										RegisterVO.RECEIVEUNIT,
										RegisterVO.RECEIVEBANKACC,
										RegisterVO.FBMBILLTYPE, 
										RegisterVO.ORIENTATION,
										RegisterVO.PAYBANK,
										RegisterVO.RECEIVEBANK
										},
								new String[] { 
										BaseinfoVO.ACCEPTANCENO,
										BaseinfoVO.CONTRACTNO,
										BaseinfoVO.ENDDATE,
										BaseinfoVO.INVOICEDATE,
										BaseinfoVO.INVOICEUNIT,
										BaseinfoVO.PK_CURR,
										BaseinfoVO.MONEYY,
										BaseinfoVO.FBMBILLNO,
										BaseinfoVO.PAYUNIT,
										BaseinfoVO.PAYBANKACC,
										BaseinfoVO.RECEIVEUNIT,
										BaseinfoVO.RECEIVEBANKACC,
										BaseinfoVO.FBMBILLTYPE,
										BaseinfoVO.ORIENTATION,
										BaseinfoVO.PAYBANK,
										BaseinfoVO.RECEIVEBANK
								}),

								new RefTakenVO(RegisterVO.TABLENAME, RegisterVO.PAYBANKACC,
										new String[] { RegisterVO.PAYUNIT},
										new String[] {  FBMBankAccBasRefModel.PK_CUBASDOC}),
								new RefTakenVO(RegisterVO.TABLENAME,
										RegisterVO.RECEIVEBANKACC, new String[] {
												RegisterVO.RECEIVEUNIT}, new String[] {
												FBMBankAccBasRefModel.PK_CUBASDOC})
								};
			}
			@Override
			public CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[]{
						new CurrencyDecimalVO(
								"table_baseinfo", RegisterVO.PK_CURR, new String[] {
								RegisterVO.MONEYY, RegisterVO.MONEYF,
								RegisterVO.MONEYB, RegisterVO.FRATE,
								RegisterVO.BRATE }),
						new CurrencyDecimalVO(
								"table_baseinfo", RegisterVO.PK_CURR, new String[] {
								RegisterVO.SECURITYMONEY, RegisterVO.SECURITYMONEYF,
								RegisterVO.SECURITYMONEYB, RegisterVO.FRATE,
								RegisterVO.BRATE }),
						new CurrencyDecimalVO(
								"table_baseinfo", RegisterVO.PK_CURR, new String[] {
								RegisterVO.POUNDAGEMONEY,
								RegisterVO.POUNDAGEMONEYF, RegisterVO.POUNDAGEMONEYB, RegisterVO.FRATE,
								RegisterVO.BRATE })
				};
			}
		};
	}

}
