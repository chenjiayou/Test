/**
 *
 */
package nc.ui.fbm.storage.checker;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.pub.lang.UFDate;

/**
 * <p>
 * �����������Ʊ���ڼ���Ʊ���ڵ��Ⱥ����
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-10-30
 *
 */
public class KeepInviceDateChecker extends AbstractUIChecker {
	private String itemkey;
	/**
	 *
	 */
	public KeepInviceDateChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public KeepInviceDateChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}



	public KeepInviceDateChecker(FBMManageUI ui,String itemkey) {
		super(ui);
		this.itemkey = itemkey;
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractUIChecker#check()
	 */
	@Override
	public String check() {
		BillCardPanel billCardPanel = getUI().getBillCardPanel();
		UFDate inputDate=new UFDate((String) billCardPanel.getHeadItem(itemkey).getValueObject());
		if(inputDate==null){
			inputDate = ClientInfo.getCurrentDate();
		}

		int row = billCardPanel.getBillModel(StorageBVO.tablecode).getRowCount();
		for (int i = 0; i < row; i++) {
			Object invoiceDataObj = getUI().getRefTakenProccessor().getValueByTakenInList(StorageBVO.tablecode, StorageBVO.INVOICEDATE, i);
			
			if(invoiceDataObj !=null){
				String invoiceDate = invoiceDataObj.toString();
				if(inputDate.before(new UFDate(invoiceDate))){
					return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000000")/* @res"��"*/+(i+1)+nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000007")/* @res"��Ʊ�ݵĳ�Ʊ����Ӧ��С�ڵ���ҵ������"*/;
				}
			}
			
			Object gatherDateObj = getUI().getRefTakenProccessor().getValueByTakenInList(StorageBVO.tablecode, StorageBVO.GATHERDATE, i);
			if(gatherDateObj!=null){
				String gatherDate = gatherDateObj.toString();
				if(inputDate.before(new UFDate(gatherDate))){
					return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000000")/* @res"��"*/+(i+1)+nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000008")/* @res"��Ʊ�ݵ���Ʊ����Ӧ��С�ڵ���ҵ������"*/;
				}
			}
			
//			Object enddateObj = getUI().getRefTakenProccessor().getValueByTakenInList(StorageBVO.tablecode, StorageBVO.ENDDATE, i);
//			if(enddateObj !=null){
//				String enddate = enddateObj.toString();
//				if(inputDate.after(new UFDate(enddate))){
//					return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000000")/* @res"��"*/+(i+1)+nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000009")/* @res"��Ʊ�ݵĵ�������Ӧ�ô��ڵ���ҵ������"*/;
//				}
//			}


		}
		return null;
	}

}