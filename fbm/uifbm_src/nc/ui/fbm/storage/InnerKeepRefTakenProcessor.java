/**
 *
 */
package nc.ui.fbm.storage;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-11-27
 *
 */
public class InnerKeepRefTakenProcessor extends RefTakenProccessor {

	public InnerKeepRefTakenProcessor(BillListPanel listpanel,
			BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
		// TODO Auto-generated constructor stub
	}
	
	protected ITakenColUtil createTakenColUtil() {
		return new AbstractTakenColUtil(){
			protected RefTakenVO[] getRefTakenVOs() {
				return new RefTakenVO[]{
						new RefTakenVO("fbm_storage_b",StorageBVO.PK_SOURCE,new String[]{
								StorageBVO.ACCEPTANCETYPE,
								StorageBVO.GATHERDATE,
								StorageBVO.ENDDATE,
								StorageBVO.INVOICEDATE,
								StorageBVO.PK_CURR,
								StorageBVO.MONEYY,
								StorageBVO.MONEYB,
								StorageBVO.MONEYBRATE,
								StorageBVO.PK_BASEINFO,
								StorageBVO.FBMBILLNO
						},new String[]{
								BaseinfoVO.FBMBILLTYPE,
								RegisterVO.GATHERDATE,
								RegisterVO.ENDDATE,
								RegisterVO.INVOICEDATE,
								RegisterVO.PK_CURR,
								RegisterVO.MONEYY,
								RegisterVO.MONEYB,
								RegisterVO.BRATE,
								RegisterVO.PK_BASEINFO,
								RegisterVO.FBMBILLNO
						}),
						new RefTakenVO("fbm_storage", StorageVO.KEEPUNIT,
								new String[] { StorageVO.KEEPCORP },
								new String[] { "bd_cubasdoc.pk_corp1" })
				};
			}
			@Override
			protected CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[]{
						new CurrencyDecimalVO(
						StorageBVO.tablecode, StorageBVO.PK_CURR, new String[] {StorageBVO.MONEYY})
				};
			}
		};
	}

}
