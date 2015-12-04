package nc.ui.fbm.storage.bankback;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.vo.fbm.storage.StorageBVO;

public class KeepBankChecker extends AbstractUIChecker {

	private String itemkey;

	public KeepBankChecker(FBMManageUI ui,String itemkey) {
		super(ui);
		this.itemkey = itemkey;
	}

	@Override
	public String check() {
		// TODO Auto-generated method stub
		Object keepunit = getUI().getBillCardPanel().getHeadItem(itemkey).getValueObject();
		//校验领用票据的托管银行必须与表头的一致
		int rowCount = getUI().getBillCardPanel().getBillModel().getRowCount();
		if(keepunit !=null){
			for(int i = 0; i < rowCount;i++){
				String keepbank = (String) getUI().getBillCardPanel().getBillModel().getValueAt(i, getUI().getBillCardPanel().getBodyColByKey(StorageBVO.KEEPBANK));
				if (keepbank == null || !keepbank.equals(keepunit)) {
					return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000118")/*@res "表体第"*/+ (i+1) +nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000119")/*@res "行托管银行与表头托管银行不一致，无法保存"*/;
				}
			}
		}

		return null;
	}

}