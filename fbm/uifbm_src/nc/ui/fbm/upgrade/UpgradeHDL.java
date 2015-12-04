package nc.ui.fbm.upgrade;

import java.util.HashMap;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.itf.fbm.upgrade.IUpgradeService;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.bill.BillModel;
import nc.ui.trade.bill.IListController;
import nc.ui.trade.list.BillListUI;
import nc.ui.trade.list.ListEventHandler;
import nc.vo.fbm.pub.constant.FbmBusConstant;

public class UpgradeHDL extends ListEventHandler {

	//汇票票据类型
	public static String ACCEPTANCE_BANK = "bank";//银行承兑汇票
	public static String ACCEPTANCE_BUSI = "busi";//商业承兑汇票
	
	
	public UpgradeHDL(BillListUI billUI, IListController control) {
		super(billUI, control);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onBoElse(int intBtn) throws Exception {
		//获得对应票据类型值

		BillModel headModel = ((UpgradeUI)getBillUI()).getBillListWrapper().getBillListPanel().getHeadBillModel();
		int row =  headModel.getRowCount();

		Map<String,String> typeMap = new HashMap<String,String>();
		if(row != 0){
			for(int i = 0 ; i < row;i++){
				boolean isbank = ((Boolean)headModel.getValueAt(i, "isbank")).booleanValue();
				if(isbank){
					typeMap.put(headModel.getValueAt(i, "notetype_oid").toString(),ACCEPTANCE_BANK);
				}else{
					typeMap.put(headModel.getValueAt(i, "notetype_oid").toString(),ACCEPTANCE_BUSI);
				}
			}
		}


		IUpgradeService service = (IUpgradeService) NCLocator.getInstance()
				.lookup(IUpgradeService.class.getName());

		switch (intBtn) {
		case IUpgradeBtn.UPGRADE_ARAP:
			service.checkUpgrade(FbmBusConstant.SYSCODE_ARAP);
			// 用户确认
			if (MessageDialog.ID_OK == getBillUI().showOkCancelMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000164")/* @res"请确认升级财务票据数据"*/)) {

				String[] corpNames = service.checkInstall(FbmBusConstant.SYSCODE_ARAP);

				if(corpNames != null){
					StringBuffer sb = new StringBuffer();
					sb.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000165")/* @res"有公司使用财务票据，但票据升级前未安装新资金票据，可能造成数据错误，是否继续？\n"*/);
					for(int i = 0; i < corpNames.length;i++){
						sb.append(corpNames[i]);
						sb.append("\n");
					}

					if(MessageDialog.ID_CANCEL == getBillUI().showOkCancelMessage(sb.toString())){
						return;
					}
				}

				service.upgradeARAP(typeMap);
			}else{
				return;
			}

			break;

		case IUpgradeBtn.UPGRADE_FBM:
			service.checkUpgrade(FbmBusConstant.SYSCODE_FBM);
			// 用户确认
			if (MessageDialog.ID_OK == getBillUI().showOkCancelMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000166")/* @res"请确认升级资金票据数据"*/)) {
				service.upgradeFBM(typeMap);
			}else{
				return;
			}
			break;

		case IUpgradeBtn.UPGRADE_CLEAR:
			if (MessageDialog.ID_OK == getBillUI().showOkCancelMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000167")/* @res"删除票据所有数据,请确认"*/)) {
				if (MessageDialog.ID_OK == getBillUI().showOkCancelMessage(
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000168")/* @res"此操作不可恢复被删除数据，请再次确认！"*/)) {
					service.clearUpgrade();
				}else{
					return;
				}
			}else{
				return;
			}
			break;

		}

		getBillUI().showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000169")/* @res"处理完毕"*/);
	}

}