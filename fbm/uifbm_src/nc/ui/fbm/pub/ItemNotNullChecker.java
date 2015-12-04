package nc.ui.fbm.pub;

import nc.ui.ml.NCLangRes;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillItem;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * 界面数据能否为空的校验
 * <p>创建人：lpf
 * <b>日期：2007-8-14
 *
 */
public class ItemNotNullChecker extends AbstractUIChecker {

	/**
	 *
	 */
	public ItemNotNullChecker() {
		// TODO Auto-generated constructor stub
	}

	public ItemNotNullChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.IValidateCheck#validate()
	 */
	public String check() {
		// TODO Auto-generated method stub
		return getErrorMessage(getUI().getBillCardPanel());
	}

	/**
	 * <p>
	 * 获取错误消息。
	 * <p>
	 * 作者：qbh <br>
	 * 日期：2005-12-8
	 *
	 * @param panel
	 * @return
	 * @throws ValidationException
	 */
	private static String getErrorMessage(BillCardPanel panel) {
		StringBuffer message = new StringBuffer();

		// 验证表头
		BillItem[] headtailitems = panel.getBillData().getHeadTailItems();
		if (headtailitems != null) {
			for (int i = 0; i < headtailitems.length; i++) {
				if (headtailitems[i].isNull() && headtailitems[i].isShow()) {
					if (isNULL(headtailitems[i].getValueObject())&&checkRefPkisNull(headtailitems[i])) {
						message.append("[" + headtailitems[i].getTableName() + nc.ui.ml.NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000607")/* "]上的[" */+ headtailitems[i].getName()
								+ nc.ui.ml.NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000608")/* "]为空；" */+ "\n");
					} else {
						if (headtailitems[i].getDataType() == 2) {
							if ((new UFDouble((String)headtailitems[i].getValueObject())).doubleValue() == 0.00) {
								message.append("[" + headtailitems[i].getTableName() + nc.ui.ml.NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000607")/* "]上的[" */+ headtailitems[i].getName()
										+ nc.ui.ml.NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000608")/* "]为空；" */+ "\n");
							}
							double min = ((UIRefPane) headtailitems[i].getComponent()).getUITextField().getMinValue();
							if (new UFDouble((String)headtailitems[i].getValueObject()).doubleValue() < min){
								message.append("[" + headtailitems[i].getTableName() + NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000607")/* @res"]上的 [" */+ headtailitems[i].getName() +nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000160")/* @res"]小于"*/+min+"\n");
							}

						}
					}
				}
			}
		}

		// 验证表体
		String[] tableCodes = panel.getBillData().getTableCodes(BillData.BODY);
		if (tableCodes != null) {
			for (int t = 0; t < tableCodes.length; t++) {
				String tablecode = tableCodes[t];
				for (int row = 0; row < panel.getBillData().getBillModel(tablecode).getRowCount(); row++) {
					for (int col = 0; col < panel.getBillData().getBodyItemsForTable(tablecode).length; col++) {
						BillItem item = panel.getBillData().getBodyItemsForTable(tablecode)[col];
						if (item.isNull() && item.isShow()) {
							Object aValue = panel.getBillData().getBillModel(tablecode).getValueAt(row, item.getKey());
							if (isNULL(aValue)) {
								message.append("[" + item.getTableName() + nc.ui.ml.NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000609")/* "]第" */
										+ (row + 1) + nc.ui.ml.NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000610")/* "行[" */+ item.getName() + nc.ui.ml.NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000608")/* "]为空；" */
										+ "\n");
							} else {
								if (item.getDataType() == 2) {
									if ((new UFDouble(aValue.toString())).doubleValue() == 0.00) {
										message.append("[" + item.getTableName() + nc.ui.ml.NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000609")/* "]第" */
												+ (row + 1) + nc.ui.ml.NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000610")/* "行 [" */+ item.getName() + nc.ui.ml.NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000608")/* "]为空；" */
												+ "\n");
									}
									double min = ((UIRefPane) item.getComponent()).getUITextField().getMinValue();
									if (new UFDouble(aValue.toString()).doubleValue() < min){
										message.append("[" + item.getTableName() + NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000609")/* @res"]第" */
										+ (row + 1) + NCLangRes.getInstance().getStrByID("36304510", "UPP36304510-000610")/* @res"行[" */+ item.getName() +nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000160")/* @res"]小于"*/+min+"\n");
									}

								}
							}
						}
					}
				}
			}
		}
		if (message.length()>0) {
			return message.toString();
		}
		return null;
	}

	/**
	 * <p>
	 * 校验参照是否为空，PK和text，存在参照直接录入的情况
	 * <p>
	 * 作者：lpf
	 * 日期：2007-8-20
	 * @param item
	 * @return
	 */
	private static boolean checkRefPkisNull(BillItem item){
		if(item.getComponent() instanceof UIRefPane){
			UIRefPane ref=(UIRefPane) item.getComponent();
			if(isNULL(ref.getRefPK())&&isNULL(ref.getText())){
				return true;
			}
		}else{
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * UAP提供的界面数据校验方法，也能用，和上面方法的区别，数字只要录入就不算为空
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-7
	 * @return
	 */
	public String UAPcheckItemNotNull(){
		String strError=null;
		try {
			getUI().getBillCardPanel().dataNotNullValidate();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			strError=e.getMessage();
		}
		return strError;
	}
}