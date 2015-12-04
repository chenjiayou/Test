/**
 *
 */
package nc.ui.fbm.returnbill;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.returnbill.ReturnBVO;

/**
 * <p>
 * 退票节点携带定义
 * <p>创建人：lpf
 * <b>日期：2007-8-31
 *
 */
public class ReturnRefTakenProcessor extends RefTakenProccessor {

	/**
	 * @param listpanel
	 * @param cardpanel
	 */
	public ReturnRefTakenProcessor(BillListPanel listpanel,
			BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fac.account.pub.RefTakenProccessor#createTakenColUtil()
	 */
	@Override
	protected ITakenColUtil createTakenColUtil() {
		// TODO Auto-generated method stub
		return new AbstractTakenColUtil(){
			protected RefTakenVO[] getRefTakenVOs() {
				// TODO Auto-generated method stub
				return new RefTakenVO[]{
						new RefTakenVO("fbm_return_b",ReturnBVO.PK_SOURCE,new String[]{
								ReturnBVO.FBMBILLTYPE,
								ReturnBVO.ENDDATE,
								ReturnBVO.INVOICEDATE,
								ReturnBVO.PAYUNIT,
								ReturnBVO.PAYBANKACC,
								ReturnBVO.RECEIVEUNIT,
								ReturnBVO.RECEIVEBANKACC,
								ReturnBVO.PK_CURR,
								ReturnBVO.MONEYY,
								ReturnBVO.GATHERDATE,
								ReturnBVO.PK_BASEINFO,
								ReturnBVO.FBMBILLNO
						},new String[]{
								BaseinfoVO.FBMBILLTYPE,
								RegisterVO.ENDDATE,
								RegisterVO.INVOICEDATE,
								RegisterVO.PAYUNIT,
								RegisterVO.PAYBANKACC,
								RegisterVO.RECEIVEUNIT,
								RegisterVO.RECEIVEBANKACC,
								RegisterVO.PK_CURR,
								RegisterVO.MONEYY,
								RegisterVO.GATHERDATE,
								RegisterVO.PK_BASEINFO,
								RegisterVO.FBMBILLNO
						})
				};
			}

			@Override
			protected CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[] { new CurrencyDecimalVO(ReturnBVO.tablecode, ReturnBVO.PK_CURR,
						new String[] { ReturnBVO.MONEYY }) };
			}
		};
	}

}
