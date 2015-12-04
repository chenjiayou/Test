/**
 *
 */
package nc.ui.fbm.gather.listener;

import java.util.List;

import nc.itf.cdm.util.CommonUtil;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.refmodel.BaseInfoAccBankRefModel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;

/**
 * <p>
 * ��Ʊ/��Ʊ�տλ�����˺ű༭������
 * <p>
 * �����ˣ�lpf <b>���ڣ�2007-9-13
 * 
 */
public class ReceiveAccbankEditListener extends AbstractItemEditListener {
	private String accNameKey;
	private String acccodeKey;
	private String custKey;//�����ֶα���
	private String bankdocKey;//�����ֶ�
	/**
	 * 
	 */
	public ReceiveAccbankEditListener() {

	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public ReceiveAccbankEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * 
	 * @param ui
	 * @param itemKey
	 * @param accNameKey
	 *            �˺�����
	 * @param accbankKey
	 *            �������ֹ�¼����˺�
	 */
	public ReceiveAccbankEditListener(FBMManageUI ui, String itemKey,
			String accNameKey, String accbankKey,String custKey,String bankdocKey) {
		super(ui, itemKey);
		this.accNameKey = accNameKey;
		this.acccodeKey = accbankKey;
		this.custKey = custKey;
		this.bankdocKey = bankdocKey;
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public ReceiveAccbankEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		UIRefPane accbankunit = (UIRefPane) getUI().getBillCardPanel().getHeadItem(getItemKey()).getComponent();
//		if (accbankunit.getRefModel() instanceof FBMBankAccBasRefModel) {
//			((FBMBankAccBasRefModel) accbankunit.getRefModel()).matchNameData(FBMBankAccBasRefModel.ACCOUNT_FIELD, accbankunit.getText());
//		}
//		if (accbankunit.getRefModel() instanceof BaseInfoAccBankRefModel) {
//			((BaseInfoAccBankRefModel) accbankunit.getRefModel()).matchNameData(BaseInfoAccBankRefModel.ACCOUNT_FIELD, accbankunit.getText());
//		}

		
		String pk_bankaccbas = accbankunit.getRefPK();
		if (CommonUtil.isNull(pk_bankaccbas)) {
			getUI().getBillCardPanel().getHeadItem(acccodeKey).setValue(accbankunit.getText());
			if (getUI().getBillCardPanel().getHeadItem(accNameKey) != null) {
				getUI().getBillCardPanel().getHeadItem(accNameKey).setValue(accbankunit.getText());
				getUI().getBillCardPanel().getHeadItem(accNameKey).setEnabled(true);
			}
		} else {
			if (getUI().getBillCardPanel().getHeadItem(accNameKey) != null) {
				getUI().getBillCardPanel().getHeadItem(accNameKey).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(accNameKey).setValue(accbankunit.getRefValue("accountname"));
			}
		}
		
		
		Object cust = accbankunit.getRefModel().getValue(BaseInfoAccBankRefModel.CUBASDOC_FIELD);
		if(cust != null){
			getUI().getBillCardPanel().getHeadItem(custKey).setValue(cust);
		}else{
			//�����Ƿ����ⲿ����
			String sql = "select pk_cubasdoc from bd_custbank where pk_accbank = '"+pk_bankaccbas+"'";
			try {
				List<String> list = (List<String>)FBMProxy.getUAPQuery().executeQuery(sql, new ColumnListProcessor());
				if(list !=null && list.size() > 0){
					getUI().getBillCardPanel().getHeadItem(custKey).setValue(list.get(0));
				}
			} catch (BusinessException e) {
				throw new BusinessRuntimeException(e.getMessage());
			}
		}
		
		//�����˺�Я������
		Object bankdoc = accbankunit.getRefModel().getValue(BaseInfoAccBankRefModel.BANKDOC_FIELD);
		if(bankdoc!=null){
			getUI().getBillCardPanel().getHeadItem(bankdocKey).setValue(bankdoc);
		}
	}

}
