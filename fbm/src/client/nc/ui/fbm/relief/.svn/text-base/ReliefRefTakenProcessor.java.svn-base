package nc.ui.fbm.relief;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;

/**
 * 
 * 类功能说明： 调剂出库表体选择票据编号后的携带 日期：2007-10-22 程序员： wues
 * 
 */
public class ReliefRefTakenProcessor extends RefTakenProccessor {

	public ReliefRefTakenProcessor(BillListPanel listpanel,
			BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
	}

	@Override
	protected ITakenColUtil createTakenColUtil() {
		return new AbstractTakenColUtil() {
			protected RefTakenVO[] getRefTakenVOs() {
				return new RefTakenVO[] {
						new RefTakenVO("fbm_relief_b", ReliefBVO.PK_SOURCE,
								new String[] { ReliefBVO.PK_CURR,
										ReliefBVO.MONEYY, ReliefBVO.ENDDATE,
										ReliefBVO.INVOICEUNIT,
										ReliefBVO.RECEIVEUNIT,
										ReliefBVO.PAYUNIT, ReliefBVO.HOLDUNIT,
										ReliefBVO.PAYBILLUNIT,
										ReliefBVO.PK_BASEINFO, ReliefBVO.BRATE,
										ReliefBVO.FRATE, ReliefBVO.MONEYB,
										ReliefBVO.MONEYF, ReliefBVO.FBMBILLNO,
										ReliefBVO.FBMBILLTYPE }, new String[] {
										RegisterVO.PK_CURR, BaseinfoVO.MONEYY,
										BaseinfoVO.ENDDATE,
										RegisterVO.INVOICEUNIT,
										RegisterVO.RECEIVEUNIT,
										RegisterVO.PAYUNIT,
										RegisterVO.HOLDUNIT,
										RegisterVO.PAYBILLUNIT,
										BaseinfoVO.PK_BASEINFO,
										RegisterVO.BRATE, RegisterVO.FRATE,
										RegisterVO.MONEYB, RegisterVO.MONEYF,
										RegisterVO.FBMBILLNO,
										RegisterVO.FBMBILLTYPE }),
						new RefTakenVO("fbm_relief", ReliefVO.RELIEFUNIT,
								new String[] { ReliefVO.RELIEFCORP },
								new String[] { "bd_cubasdoc.pk_corp1" })

				};
			}

			@Override
			protected CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[] { new CurrencyDecimalVO(
						"fbm_relief_b", ReliefBVO.PK_CURR,
						new String[] { ReliefBVO.MONEYY }) };
			}

		};
	}

}
