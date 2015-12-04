package nc.ui.fbm.reckon;

import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.bill.BillModel;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * �������㵥EventHandler
 * <p>
 * �����ˣ�bsrl <b>���ڣ�2007-10-19
 *
 */
public class ReckonEventHandler extends FBManageEventHandler {
	/**
	 * @param billUI
	 * @param control
	 */
	public ReckonEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

//	@Override
//	protected UIDialog createQueryUI() {
//		return new RefTakenQueryConditionClient(getUI(), null, _getCorp()
//				.getPk_corp(), FbmBusConstant.FUNCODE_RELIEF, _getOperator(),
//				FbmBusConstant.BILLTYPE_LIQUIDATE, getUI()
//						.getRefTakenProccessor());
//	}

	@Override
	protected void onBoSave() throws Exception {
		getBillCardPanelWrapper().getBillCardPanel().getBillData()
				.dataNotNullValidate();
		super.onBoSave();
	}
	
	/**
	 * ����Я��������ֵ
	 */
	public void onBoActionElse(ButtonObject bo) throws Exception {
		String btncode = bo.getCode();
		AggregatedValueObject vo = getBufferData().getCurrentVO();

		if (btncode.equals(IFBMButton.RECKON_UNVOUCHER)) {// ȡ����֤
			// ���Ĳ���
			if (FBMClientInfo.isSettleCenter()) {// ����
				((ReckonVO) vo.getParentVO()).setVoucherdate(null);// ������֤����
				((ReckonVO) vo.getParentVO()).setVouchermanid(null);// ������֤��
			} else {// ��λ����
				((ReckonVO) vo.getParentVO()).setUnitvoucherdate(null);// ��λ��֤����
				((ReckonVO) vo.getParentVO()).setUnitvouchermanid(null);// ��λ��֤��
			}
		}
		if (btncode.equals(IFBMButton.RELIEF_VOUCHER)) {// ��֤
			// ���Ĳ���
			if (FBMClientInfo.isSettleCenter()) {// ����
				((ReckonVO) vo.getParentVO()).setVoucherdate(ClientInfo
						.getCurrentDate());// ������֤����
				((ReckonVO) vo.getParentVO()).setVouchermanid(ClientInfo
						.getUserPK());// ������֤��
			} else {// ��λ����
				((ReckonVO) vo.getParentVO()).setUnitvoucherdate(ClientInfo
						.getCurrentDate());// ��λ��֤����
				((ReckonVO) vo.getParentVO()).setUnitvouchermanid(ClientInfo
						.getUserPK());// ��λ��֤��
			}
		}
		if (vo != null && vo.getParentVO() != null) {
			if (btncode.equals(IFBMButton.RELIEF_VOUCHER)
					|| btncode.equals(IFBMButton.RECKON_UNVOUCHER)) {
				ReckonBVO[] reckonBVO = (ReckonBVO[]) vo.getChildrenVO();
				for (int i = 0; i < reckonBVO.length; i++) {
					UFDouble moneyy = new UFDouble(0.0);
					Object moneyyobj =  getUI().getRefTakenProccessor()
						.getValueByTakenInList(ReckonBVO.TABLECODE,
							ReckonBVO.MONEYY, i);
					if(moneyyobj !=null){
						moneyy = new UFDouble(moneyyobj.toString());
					}
					
					
					UFDouble moneyb = new UFDouble(0.0);
					Object moneybobj = getUI().getRefTakenProccessor()
							.getValueByTakenInList(ReckonBVO.TABLECODE,
									ReckonBVO.MONEYB, i);
					if (moneybobj != null)
						moneyb = new UFDouble(moneybobj.toString());

					UFDouble moneybrate = new UFDouble(0.0);
					Object brateobj = getUI().getRefTakenProccessor()
							.getValueByTakenInList(ReckonBVO.TABLECODE,
									ReckonBVO.BRATE, i);
					if (brateobj != null)
						moneybrate = new UFDouble(brateobj.toString());

					UFDouble frate = new UFDouble(0);
					Object frateObj = getUI().getRefTakenProccessor()
							.getValueByTakenInList(ReckonBVO.TABLECODE,
									ReckonBVO.FRATE, i);
					if (frateObj != null)
						frate = new UFDouble(frateObj.toString());

					UFDouble moneyf = new UFDouble(0);
					Object moneyfObj = getUI().getRefTakenProccessor()
							.getValueByTakenInList(ReckonBVO.TABLECODE,
									ReckonBVO.MONEYF, i);
					if (moneyfObj != null)
						moneyf = new UFDouble(moneyfObj.toString());

					reckonBVO[i].setMoneyy(moneyy);
					reckonBVO[i].setMoneyb(moneyb);
					reckonBVO[i].setBrate(moneybrate);
					reckonBVO[i].setFrate(frate);
					reckonBVO[i].setMoneyf(moneyf);
					// Я��Ʊ�ݱ�ź�Ʊ������
					reckonBVO[i].setFbmbillno((String) getUI()
							.getRefTakenProccessor()
							.getValueByTakenInList(ReckonBVO.TABLECODE,
									ReckonBVO.FBMBILLNO, i));
					reckonBVO[i].setFbmbilltype((String) getUI()
							.getRefTakenProccessor().getValueByTakenInList(
									ReckonBVO.TABLECODE, ReckonBVO.FBMBILLTYPE,
									i));
				}
			}
		}

		super.onBoActionElse(bo);
		if(btncode.equals(IFBMButton.FBM_TALLY_STR) ){
			if(getBufferData().getCurrentVO()!=null && getBufferData().getCurrentVO().getParentVO() !=null){
				String pk_accid = ((ReckonVO)getBufferData().getCurrentVO().getParentVO()).getInacc() ;
				boolean isValid = showNoTallyInfo(pk_accid);
				if(!isValid){
					return;
				}
			}
		}
	}

	@Override
	protected void buttonActionBefore(AbstractBillUI billUI, int intBtn)
			throws Exception {
		super.buttonActionBefore(billUI, intBtn);
		if (intBtn == IBillButton.Save) {
			CircularlyAccessibleValueObject[] bodyvos = getBillCardPanelWrapper()
					.getBillCardPanel().getBillModel(ReckonBVO.TABLENAME)
					.getBodyValueVOs(ReckonBVO.class.getName());
			if (bodyvos == null || bodyvos.length <= 0) {
				throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201050","UPP36201050-000000")/* @res"���岻��Ϊ�գ�"*/);
			}
		}
	}

	/**
	 * ɾ��ʱ�����¼������������
	 */
	protected void onBoLineDel() throws Exception {
		super.onBoLineDel();
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(
				ReckonBVO.TABLECODE);
		dealReckon(billmodel);
	}


	/**
	 * ��������ĵ�������
	 * @param billmodel
	 * @param billType
	 */
	private void dealReckon(BillModel billmodel){
		int rowcount = billmodel.getRowCount();
		UITable uitable = getUI().getBillCardPanel().getBillTable();
		UFDouble reckonmoney = null;
		UFDouble summoney = new UFDouble(0);
		for (int i = 0; i < rowcount; i++) {
			reckonmoney = (UFDouble) getUI().getRefTakenProccessor()
					.getValueByTakenInList(ReckonBVO.TABLENAME,
							ReckonBVO.MONEYY, i);
			if (null == reckonmoney || reckonmoney.doubleValue() == 0) {//ȡ����������Ϊ�ջ�0
				continue;
			}
			if (reckonmoney.doubleValue() > 0) {
				uitable.getModel().setValueAt(
						FbmBusConstant.RELIEF_DIRECTION_IN,
						i,
						getUI().getBillCardPanel().getBodyColByKey(
								ReckonBVO.DIRECTION));
			} else {
				uitable.getModel().setValueAt(
						FbmBusConstant.RELIEF_DIRECTION_OUT,
						i,
						getUI().getBillCardPanel().getBodyColByKey(
								ReckonBVO.DIRECTION));
			}
			summoney = summoney.add(reckonmoney);

		}
		getUI().getBillCardPanel().getHeadItem(ReckonVO.RECKONMONEYSUM)
				.setValue(summoney);
	}

	@Override
	public void onBoAudit() throws Exception {
		// TODO Auto-generated method stub
		super.onBoAudit();
		

	}
	
	
	
}