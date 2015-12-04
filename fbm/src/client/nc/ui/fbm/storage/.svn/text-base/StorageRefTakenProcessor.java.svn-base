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

/**
 * <p>
 * 银行保管携带定义
 * <p>创建人：lpf
 * <b>日期：2007-8-21
 *
 */
public class StorageRefTakenProcessor extends RefTakenProccessor{

	public StorageRefTakenProcessor(BillListPanel listpanel,BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ITakenColUtil createTakenColUtil() {
		// TODO Auto-generated method stub
		return new AbstractTakenColUtil(){
			protected RefTakenVO[] getRefTakenVOs() {
				// TODO Auto-generated method stub
				return new RefTakenVO[]{
						new RefTakenVO("fbm_storage_b",StorageBVO.PK_SOURCE,new String[]{
								StorageBVO.ACCEPTANCETYPE,
								StorageBVO.GATHERDATE,
								StorageBVO.ENDDATE,
								StorageBVO.INVOICEDATE,
								// StorageBVO.PAYUNIT,
								// StorageBVO.PAYBANKACC,
								// StorageBVO.RECEIVEUNIT,
								// StorageBVO.RECEIVEBANKACC,
								StorageBVO.PK_CURR,
								StorageBVO.MONEYY,
								StorageBVO.PK_BASEINFO,
								StorageBVO.FBMBILLNO
						},new String[]{
								BaseinfoVO.FBMBILLTYPE,
								RegisterVO.GATHERDATE,
								RegisterVO.ENDDATE,
								RegisterVO.INVOICEDATE,
								// RegisterVO.PAYUNIT,
								// RegisterVO.PAYBANKACC,
								// RegisterVO.RECEIVEUNIT,
								// RegisterVO.RECEIVEBANKACC,
								RegisterVO.PK_CURR,
								RegisterVO.MONEYY,
								RegisterVO.PK_BASEINFO,
								RegisterVO.FBMBILLNO
						})
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
