/**
 *
 */
package nc.ui.fbm.gather.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.YFBEditListerner;
import nc.ui.fbm.pub.refmodel.DefaultBaseInfoRefModel;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.IBillItem;
import nc.vo.fbm.register.RegisterVO;

/**
 * 
 * <p>
 * 收票登记票据基本信息编辑监听
 * <p>
 * 创建人：lpf <b>日期：2007-8-14
 * 
 */
public class BaseInfoEditListener extends AbstractItemEditListener {

	/**
	 * 
	 */
	public BaseInfoEditListener() {
		// TODO Auto-generated constructor stub
	}

	public BaseInfoEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.fbm.pub.IFbmEditListener#getItemKey()
	 */
	public String getItemKey() {
		return RegisterVO.PK_BASEINFO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.fbm.pub.IFbmEditListener#getPos()
	 */
	public int getPos() {
		return IBillItem.HEAD;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.fbm.pub.IFbmEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	public void responseEditEvent(BillEditEvent editEvent) {
		BillItem baseinfo = getUI().getBillCardPanel().getHeadItem(getItemKey());
		UIRefPane refpane = ((UIRefPane) baseinfo.getComponent());
		String pk_baseinfo = refpane.getRefPK();
		String blankbase = ((UIRefPane) refpane).getText();
		
		if(CommonUtil.isNull(pk_baseinfo)&&!CommonUtil.isNull(blankbase)){
			DefaultBaseInfoRefModel model = (DefaultBaseInfoRefModel) (refpane).getRefModel();
			model.MatchNameData("fbmbillno", blankbase.trim());
			pk_baseinfo = refpane.getRefPK();
		}
		// 参照录入
		if (pk_baseinfo != null) {
			getUI().getBillCardPanel().getHeadItem(RegisterVO.ISNEWBILL)
					.setValue(new Boolean(false));
			getUI().setHeadItemEditable(
					new String[] { RegisterVO.PAYBANKACC, RegisterVO.PAYUNIT,
							RegisterVO.PAYBANK, RegisterVO.RECEIVEBANKACC,
							RegisterVO.PAYUNIT, RegisterVO.RECEIVEBANK,
							RegisterVO.FBMBILLTYPE, RegisterVO.MONEYY,
							RegisterVO.BRATE, RegisterVO.FRATE,
							RegisterVO.PK_CURR, RegisterVO.INVOICEDATE,
							RegisterVO.INVOICEUNIT, RegisterVO.ENDDATE,
							RegisterVO.ACCEPTANCENO, RegisterVO.CONTRACTNO,
							RegisterVO.RECEIVEUNIT }, false);
			BillItem bi = getUI().getBillCardPanel().getHeadItem(RegisterVO.PK_CURR);
			BillEditEvent e = new BillEditEvent(bi.getComponent(), bi.getValueObject(),
					RegisterVO.PK_CURR, -1, BillItem.HEAD);
			new YFBEditListerner(getUI(), RegisterVO.PK_CURR,RegisterVO.PK_CURR,RegisterVO.MONEYY, RegisterVO.MONEYF,RegisterVO.MONEYB, RegisterVO.FRATE, RegisterVO.BRATE).responseEditEvent(e);
		}else{
		 // 手工录入
			if (blankbase != null) {
				getUI().getBillCardPanel().getHeadItem(RegisterVO.ISNEWBILL)
						.setValue(new Boolean(true));
				getUI().getBillCardPanel().getHeadItem(RegisterVO.FBMBILLNO)
						.setValue(blankbase);
				getUI().setHeadItemEditable(
						new String[] { RegisterVO.PAYBANKACC,
								RegisterVO.PAYUNIT, RegisterVO.PAYBANK,
								RegisterVO.RECEIVEBANKACC,
								RegisterVO.RECEIVEUNIT, RegisterVO.RECEIVEBANK,
								RegisterVO.FBMBILLTYPE, RegisterVO.MONEYY,
								RegisterVO.PK_CURR, RegisterVO.INVOICEDATE,
								RegisterVO.INVOICEUNIT, RegisterVO.ENDDATE,
								RegisterVO.ACCEPTANCENO, RegisterVO.CONTRACTNO,
								RegisterVO.RECEIVEUNIT }, true);
				String fbmbilltype = (String)getUI().getBillCardPanel().getHeadItem(RegisterVO.FBMBILLTYPE).getValueObject();
				if(fbmbilltype ==null ||"".equals(fbmbilltype)){
					((UIComboBox) getUI().getBillCardPanel().getHeadItem(RegisterVO.FBMBILLTYPE).getComponent()).setSelectedIndex(0);
				}
			}			
		}
	}

}
