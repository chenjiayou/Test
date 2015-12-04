package nc.ui.fbm.reckon;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 调剂清算携带定义
 * <p>创建人：bsrl
 * <b>日期：2007-10-22
 *
 */
public class ReckonRefTakenProcessor extends RefTakenProccessor{


	public ReckonRefTakenProcessor(BillListPanel listpanel,BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
	}

	@Override
	protected ITakenColUtil createTakenColUtil() {
		return new AbstractTakenColUtil(){
			protected RefTakenVO[] getRefTakenVOs() {
				return new RefTakenVO[]{
						new RefTakenVO("fbm_reckon_b",ReckonBVO.PK_DETAIL,new String[]{
								ReckonBVO.ENDDATE,
								ReckonBVO.FBMBILLMONEY,
								// ReckonBVO.HOLDUNIT,
										// ReckonBVO.PAYUNIT,
								ReckonBVO.PK_BASEINFO,
								ReckonBVO.BRATE,
								ReckonBVO.FRATE,
								ReckonBVO.MONEYB,
								ReckonBVO.MONEYF,
								ReckonBVO.FBMBILLNO,
								ReckonBVO.FBMBILLTYPE,
								ReckonBVO.PK_CURR,
								ReckonBVO.MONEYY
						    },new String[]{
								RegisterVO.ENDDATE,
								"fbmmoneyy",
								// RegisterVO.HOLDUNIT,
										// RegisterVO.PAYUNIT,
								RegisterVO.PK_BASEINFO,
								RegisterVO.BRATE,
								RegisterVO.FRATE,
								RegisterVO.MONEYB,
								RegisterVO.MONEYF,
								RegisterVO.FBMBILLNO,
								RegisterVO.FBMBILLTYPE,
								"pk_currtype",
								RegisterVO.MONEYY
								}),
						new RefTakenVO("fbm_reckon",ReckonVO.INACC,new String[]{
								ReckonVO.PK_CURR
					    	},new String[]{
								 "bd_accid.pk_currtype"}),
						new RefTakenVO("fbm_reckon",ReckonVO.OUTACC,new String[]{
								ReckonVO.PK_CURR
							},new String[]{
								"bd_accid.pk_currtype"})
				};
			}
			
			@Override
			public CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[]{
						new CurrencyDecimalVO(
						"fbm_reckon_b", ReckonBVO.PK_CURR, new String[] {
								ReckonBVO.FBMBILLMONEY}),
						new CurrencyDecimalVO(
								"fbm_reckon_b", ReckonBVO.PK_CURR, new String[] {
										ReckonBVO.MONEYY}),
						new CurrencyDecimalVO(
								"fbm_reckon", ReckonVO.PK_CURR, new String[] {
										ReckonVO.RECKONMONEYSUM}),
				};
			}
			
		};
	}
	
	

} 
