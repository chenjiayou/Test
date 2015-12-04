/**
 *
 */
package nc.ui.fbm.gather.reffilter;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

/**
 * <p>
 * �����˺Ź���
 * ���̻�������+�����˺ţ��������У�
 * ��λ���հ�������Ŀ��̣����Ϊ����������˺�Ϊ�����˺ţ�����Ϊ��������
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-10-24
 *
 */
public class CustAccbankRefModelFilter extends BillItemRefModelFilter {
	private FBMManageUI ui;
	private String souceKey;
	/**
	 * @param billitem
	 */
	public CustAccbankRefModelFilter(BillItem billitem) {
		super(billitem);
	}

	
	public CustAccbankRefModelFilter(BillItem billitem, FBMManageUI ui) {
		super(billitem);
		this.ui = ui;
	}

	public CustAccbankRefModelFilter(BillItem billitem, FBMManageUI ui,
			String souceKey) {
		super(billitem);
		this.ui = ui;
		this.souceKey = souceKey;
	}


	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {


		return null;
	}

}
