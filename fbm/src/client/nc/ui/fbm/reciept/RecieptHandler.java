package nc.ui.fbm.reciept;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fac.account.pub.RefTakenQueryFilterEditorFactory;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIDialog;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.lang.UFDouble;

/**
 *
 * �๦��˵���� ��������ص�(��Ա��λ) ���ڣ�2007-10-31 ����Ա�� wues
 *
 */
public class RecieptHandler extends FBManageEventHandler {
	/**
	 * @param billUI
	 * @param control
	 */
	public RecieptHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

    @Override
	protected String getHeadCondition() {
    	String strwhere = super.getHeadCondition();
    	String selfwhere = null;
    	try{
    		
    		selfwhere = " (fbm_reckon.pk_billtypecode = '36GT') and fbm_reckon.reckonunit='"
				+ ClientInfo.getCorpCuBas(_getCorp().getPrimaryKey()) + "'";
    	}catch(Exception e){
    		throw new BusinessRuntimeException(e.getMessage());
    	}
    	if(CommonUtil.isNull(strwhere)){
    		return  selfwhere;
    	}else{
    		return strwhere + " and "+selfwhere;
    	}
	}

	/**
	 * ���ݹ�˾PK��ѯ���Ӧ�Ŀ���pk
	 *
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
//	private String queryCustVOByCorp(String pk_corp) throws BusinessException {
//		String sCondition = " bd_cubasdoc.pk_corp1 = '" + pk_corp + "' ";
//		;
//		ICustManDocQuery cusQuery = (ICustManDocQuery) NCLocator.getInstance()
//				.lookup(ICustManDocQuery.class.getName());
//		CustBasVO[] custbasVos = cusQuery.queryCustBasicInfo(sCondition,
//				pk_corp);
//		if (custbasVos == null || custbasVos.length == 0)
//			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201055","UPP36201055-000000")/* @res"��ǰ��˾û�����ö�Ӧ���ڲ����̣�"*/);
//		if (custbasVos.length > 1) {
//			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201055","UPP36201055-000001")/* @res"��ǰ��˾���ö�Ӧ���ڲ����̶���һ���봦��"*/);
//		}
//		return custbasVos[0].getPk_cubasdoc();
//	}

	/**
	 * ����Я��������ֵ
	 */
	public void onBoActionElse(ButtonObject bo) throws Exception {
		String btncode = bo.getCode();
		AggregatedValueObject vo = getBufferData().getCurrentVO();

		if (btncode.equals(IFBMButton.RELIEF_CANCELVOUCHER)) {// ȡ����֤
			// ���Ĳ���
			if (FBMClientInfo.isSettleCenter ()) {// ����
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
					|| btncode.equals(IFBMButton.RELIEF_CANCELVOUCHER)) {
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
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.bill.BillEventHandler#onBoQuery()
	 */
	@Override
	protected void onBoQuery() throws Exception {
		// TODO �Զ����ɷ������
		super.onBoQuery();
	}

	protected UIDialog createQueryUI() {
//		return new HYQueryDLG(getBillUI(), null, _getCorp().getPrimaryKey(),
//				getBillUI()._getModuleCode(), _getOperator(), getBillUI()
//						.getBusinessType(), getBillUI().getNodeKey());
		RefTakenQueryConditionClient dialog = new RefTakenQueryConditionClient(getBillUI(),createTemplateInof(getBillUI()));
		dialog.registerFieldValueEelementEditorFactory(new ReceiptFieldValueElementEditorFactory(dialog.getQueryContext()));
		dialog.registerFilterEditorFactory(new RefTakenQueryFilterEditorFactory((RecieptUI)getBillUI(),((RecieptUI)getBillUI()).getRefTakenProccessor(),dialog));
		return dialog;
	}

//	@Override
//	protected UIDialog createQueryUI() {
//		return new RefTakenQueryConditionClient(getUI(), null, _getCorp()
//				.getPk_corp(), FbmBusConstant.FUNCODE_RELIEF_RECIEPT,
//				_getOperator(), FbmBusConstant.BILLTYPE_RECKON_RECIEPT, getUI()
//						.getRefTakenProccessor());
//	}
}