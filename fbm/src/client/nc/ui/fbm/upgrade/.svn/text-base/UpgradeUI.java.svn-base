package nc.ui.fbm.upgrade;

import nc.bs.logging.Logger;
import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.IListController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.list.BillListUI;
import nc.ui.trade.list.ListEventHandler;
import nc.uif.pub.exception.UifException;
import nc.vo.fbm.upgrade.NoteTypeVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.HYBillVO;

public class UpgradeUI extends BillListUI {

	public UpgradeUI() {
		super();
		queryAllData();
	}

	public UpgradeUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
		queryAllData();
	}

	@Override
	protected String checkPrerequisite() {
		// TODO Auto-generated method stub
		String pk_corp = ClientEnvironment.getInstance().getCorporation()
				.getPk_corp();
		if (pk_corp.equals("0001")) {
			return null;
		} else {
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000170")/* @res"该节点只能由集团用户使用"*/;
		}
	}



	@Override
	protected IListController createController() {
		// TODO Auto-generated method stub
		return new UpdateCTL();
	}

	@Override
	public String getRefBillType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initSelfData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDefaultData() throws Exception {
		// TODO Auto-generated method stub

	}



	@Override
	protected ListEventHandler createEventHandler() {
		// TODO Auto-generated method stub
		return new UpgradeHDL(this,getUIControl());
	}

	@Override
	protected void initPrivateButton() {
		// TODO Auto-generated method stub
		super.initPrivateButton();
		addPrivateButton(new UpgradeArapBtnVO().getButtonVO());
		addPrivateButton(new UpgradeFbmBtnVO().getButtonVO());
		addPrivateButton(new ClearBtnVO().getButtonVO());
	}


	private void queryAllData(){
		HYPubBO_Client client = new HYPubBO_Client();
		try {
			//String sql = "isnull(dr,0)=0 and pk_corp <> '0001'";
			String sql = "isnull(dr,0)=0";
			NoteTypeVO[] vos = (NoteTypeVO[])client.queryByCondition(NoteTypeVO.class, sql);

			if(vos!= null && vos.length >0){
				HYBillVO[] aggVos = new HYBillVO[vos.length];
				for(int i = 0; i<vos.length ;i++){
					aggVos[i] = new HYBillVO();
					if(vos[i].getType_name()!= null && vos[i].getType_name().startsWith("UPPpjinitdata")){
						vos[i].setType_name(nc.ui.ml.NCLangRes.getInstance().getStrByID("pjinitdata",vos[i].getType_name().trim()));
					}
					vos[i].setIsbank(new UFBoolean(true));
					aggVos[i].setParentVO(vos[i]);
				}
				getBufferData().clear();
				try {
					setListHeadData(vos);
				} catch (Exception e) {
					Logger.error(e.getMessage(),e);
				}
				getBufferData().addVOsToBuffer(aggVos);
			}
			getBillListWrapper().getBillListPanel().getHeadItem("isbank").setEnabled(true);

		} catch (UifException e) {
			Logger.error(e.getMessage(),e);
		}
	}

}