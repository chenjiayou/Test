/**
 *
 */
package nc.ui.fbm.gather;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.refmodel.BaseInfoAccBankRefModel;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.trade.base.IBillOperate;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-8-14
 * 
 */
public class GatherRefTakenProcessor extends RefTakenProccessor {

	/**
	 * @param listpanel
	 * @param cardpanel
	 */
	public GatherRefTakenProcessor(BillListPanel listpanel,
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
			protected RefTakenVO[] getRefTakenVOs() {
				// TODO Auto-generated method stub
				return new RefTakenVO[] {
						new RefTakenVO(
								RegisterVO.BASEINFO_TAB,
								RegisterVO.PK_BASEINFO,
								new String[] { 
										RegisterVO.ACCEPTANCENO,
										RegisterVO.CONTRACTNO, 
										RegisterVO.DISABLE,
										RegisterVO.ENDDATE, 
										RegisterVO.INVOICEDATE,
										RegisterVO.INVOICEUNIT, 
										RegisterVO.MONEYY,
										RegisterVO.PAYBANK, 
										RegisterVO.PAYUNIT,
										RegisterVO.PAYBANKACC,
										RegisterVO.PK_CURR, 
										RegisterVO.RECEIVEBANK,
										RegisterVO.RECEIVEUNIT,
										RegisterVO.RECEIVEBANKACC,
										RegisterVO.FBMBILLNO,
										RegisterVO.FBMBILLTYPE, 
										RegisterVO.ORIENTATION
//										,
//										RegisterVO.RECEIVEACCNAME,
//										RegisterVO.RECEIVEUNITNAME,
//										RegisterVO.INVOICEUNITNAME
										},
								new String[] { 
										BaseinfoVO.ACCEPTANCENO,
										BaseinfoVO.CONTRACTNO,
										BaseinfoVO.DISABLE,
										BaseinfoVO.ENDDATE,
										BaseinfoVO.INVOICEDATE,
										BaseinfoVO.INVOICEUNIT,
										BaseinfoVO.MONEYY,
										BaseinfoVO.PAYBANK,
										BaseinfoVO.PAYUNIT,
										BaseinfoVO.PAYBANKACC,
										BaseinfoVO.PK_CURR,
										BaseinfoVO.RECEIVEBANK,
										BaseinfoVO.RECEIVEUNIT,
										BaseinfoVO.RECEIVEBANKACC,
										BaseinfoVO.FBMBILLNO,
										BaseinfoVO.FBMBILLTYPE,
										BaseinfoVO.ORIENTATION
										}),
						new RefTakenVO(
								RegisterVO.TABLENAME, RegisterVO.PK_BASEINFO,
								new String[]{RegisterVO.FBMBILLNO},new String[]{BaseinfoVO.FBMBILLNO}
								),
//						new RefTakenVO(RegisterVO.BASEINFO_TAB, RegisterVO.PAYBANKACC,
//								new String[] { RegisterVO.PAYBANK },
//								new String[] { BaseInfoAccBankRefModel.BANKDOC_FIELD}),
//						new RefTakenVO(RegisterVO.BASEINFO_TAB, RegisterVO.RECEIVEBANKACC,
//								new String[] { RegisterVO.RECEIVEBANK},
//								new String[] {  BaseInfoAccBankRefModel.BANKDOC_FIELD}) ,
						new RefTakenVO(RegisterVO.TABLENAME, RegisterVO.RECEIVEUNIT,
								new String[] { RegisterVO.RECEIVEUNITNAME },
								new String[] { "custname" }),
						new RefTakenVO(RegisterVO.TABLENAME, RegisterVO.INVOICEUNIT,
								new String[] { RegisterVO.INVOICEUNITNAME },
								new String[] { "custname" }),
						new RefTakenVO(RegisterVO.TABLENAME, RegisterVO.PAYUNIT,
								new String[] { RegisterVO.PAYUNITNAME },
								new String[] { "custname" })
						};
				
			}

			@Override
			public CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[]{
						new CurrencyDecimalVO(
						"table_baseinfo", RegisterVO.PK_CURR, new String[] {
								RegisterVO.MONEYY, RegisterVO.MONEYF,
								RegisterVO.MONEYB, RegisterVO.FRATE,
								RegisterVO.BRATE })
				};
			}

		};
	}

	
	protected boolean isTakenInCard(BillItem sourceItem, BillItem targetItem,
			boolean isEdit) {
		boolean ret = super.isTakenInCard(sourceItem, targetItem, isEdit);
		ret = ret && isNeedRefTaken(sourceItem);
		if(targetItem.getKey().equals(RegisterVO.DISABLE)){
			ret = true;
		}
		return ret;
	}

	/**
	 * <p>
	 * 没办法的办法：解决票据编号是手工输入并且参照中不存在（已经被过滤或者是新增票据） 的情况下，如果修改票据编号，携带会清空所有界面数据的问题
	 * <p>
	 * 作者：lpf 日期：2007-9-12
	 * 
	 * @param e
	 * @return false 不需要携带
	 */
	private boolean isNeedRefTaken(BillItem sourceItem) {
		String pk_source = sourceItem.getKey();
		if (pk_source.equalsIgnoreCase(RegisterVO.PK_BASEINFO)) {
			String pk_baseinfo = (String) getManageUI().getBillCardPanel().getHeadItem(
					RegisterVO.PK_BASEINFO).getValueObject();
			if (getManageUI().getBillOperate() == IBillOperate.OP_ADD) {
				if (CommonUtil.isNull(pk_baseinfo)) {
					return false;
				}
			} else if (getManageUI().getBillOperate() == IBillOperate.OP_EDIT) {
				if (CommonUtil.isNull(pk_baseinfo)) {
					return false;
				}
				String isnewstr = (String) getManageUI().getBillCardPanel().getHeadItem(
						RegisterVO.ISNEWBILL).getValueObject();
				if (new Boolean(isnewstr).booleanValue()
						&& CommonUtil.isNull(pk_baseinfo)) {
					return false;
				}
			}
		}
		return true;
	}
	
	private FBMManageUI getManageUI(){
		return (FBMManageUI) getAbstractUI();
	}
}
