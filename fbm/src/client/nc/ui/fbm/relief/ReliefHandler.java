package nc.ui.fbm.relief;

import java.math.BigDecimal;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.bill.BillModel;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.IBillStatus;

/**
 * 
 * ���ܣ� ���������¼����� ���ڣ�2007-10-20 ����Ա��wues
 */
public class ReliefHandler extends FBManageEventHandler {

	/**
	 * ���ű��룬���ݴ˱����ϵͳ��������ȡϵͳ����ֵ
	 */
	private static final String GROUPCODE = "@@@@";


	public ReliefHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);

	}

	/**
	 * ����ҵ��ť������VO����ֵ
	 */
	protected void beforeOnBoAction(int intBtn, AggregatedValueObject billVo)
			throws Exception {
		super.beforeOnBoAction(intBtn, billVo);
		switch (intBtn) {
		case IFBMButton.Relief_Output:// ������ת���ڲ����õ�����
			JumpToInnerBack(FbmBusConstant.BILLTYPE_INNERBACK);// �ڲ����õ�
			throw new BusinessException(NOTSHOW);
		}

	}

	/**
	 * ����Я��������ֵ
	 */
	public void onBoActionElse(ButtonObject bo) throws Exception {
		String btncode = bo.getCode();
		AggregatedValueObject vo = getBufferData().getCurrentVO();

		String isAuto = OuterProxy.getSysInitQry().getParaString(GROUPCODE,
				FBMParamConstant.FBM002);

		if (btncode.equals(IFBMButton.RELIEF_CANCELVOUCHER)) {// ȡ����֤
			// ���Ĳ���
			if (FBMClientInfo.isSettleCenter()) {// ����
				((ReliefVO) vo.getParentVO()).setVoucherdate(null);// ������֤����
				((ReliefVO) vo.getParentVO()).setVouchermanid(null);// ������֤��
			} else {// ��λ����
				((ReliefVO) vo.getParentVO()).setUnitvoucherdate(null);// ��λ��֤����
				((ReliefVO) vo.getParentVO()).setUnitvouchermanid(null);// ��λ��֤��
			}
		}
		if (btncode.equals(IFBMButton.RELIEF_VOUCHER)) {// ��֤
			if (null == isAuto || "".equals(isAuto)
					|| UFBoolean.TRUE.equals(UFBoolean.valueOf(isAuto))) {
				((ReliefVO) vo.getParentVO()).setVoucherdate(ClientInfo
						.getCurrentDate());// ������֤����
				((ReliefVO) vo.getParentVO()).setVouchermanid(ClientInfo
						.getUserPK());// ������֤��
				((ReliefVO) vo.getParentVO()).setUnitvoucherdate(ClientInfo
						.getCurrentDate());// ��λ��֤����
				((ReliefVO) vo.getParentVO()).setUnitvouchermanid(ClientInfo
						.getUserPK());// ��λ��֤��
			} else {
				// ���Ĳ���
				if (FBMClientInfo.isSettleCenter()) {// ����
					((ReliefVO) vo.getParentVO()).setVoucherdate(ClientInfo
							.getCurrentDate());// ������֤����
					((ReliefVO) vo.getParentVO()).setVouchermanid(ClientInfo
							.getUserPK());// ������֤��
				} else {// ��λ����
					((ReliefVO) vo.getParentVO()).setUnitvoucherdate(ClientInfo
							.getCurrentDate());// ��λ��֤����
					((ReliefVO) vo.getParentVO())
							.setUnitvouchermanid(ClientInfo.getUserPK());// ��λ��֤��
				}
			}
		}

		if (vo != null && vo.getParentVO() != null) {

			if (btncode.equals(IFBMButton.RELIEF_VOUCHER)
					|| btncode.equals(IFBMButton.RELIEF_CANCELVOUCHER)) {
				ReliefBVO[] reliefBVOS = (ReliefBVO[]) vo.getChildrenVO();
				for (int i = 0; i < reliefBVOS.length; i++) {
					UFDouble moneyy = new UFDouble(0.0);
					Object moneyyobj = getUI().getRefTakenProccessor()
					.getValueByTakenInList(ReliefBVO.TABLECODE,
							ReliefBVO.MONEYY, i);
					if(moneyyobj !=null){
						moneyy = new UFDouble(moneyyobj.toString());
					}
					
					UFDouble moneyb = new UFDouble(0.0);
					Object moneybobj = getUI().getRefTakenProccessor()
							.getValueByTakenInList(ReliefBVO.TABLECODE,
									ReliefBVO.MONEYB, i);
					if (moneybobj != null)
						moneyb = new UFDouble(moneybobj.toString());

					UFDouble moneybrate = new UFDouble(0.0);
					Object brateobj = getUI().getRefTakenProccessor()
							.getValueByTakenInList(ReliefBVO.TABLECODE,
									ReliefBVO.BRATE, i);
					if (brateobj != null)
						moneybrate = new UFDouble(brateobj.toString());

					UFDouble frate = new UFDouble(0);
					Object frateObj = getUI().getRefTakenProccessor()
							.getValueByTakenInList(ReliefBVO.TABLECODE,
									ReliefBVO.FRATE, i);
					if (frateObj != null)
						frate = new UFDouble(frateObj.toString());

					UFDouble moneyf = new UFDouble(0);
					Object moneyfObj = getUI().getRefTakenProccessor()
							.getValueByTakenInList(ReliefBVO.TABLECODE,
									ReliefBVO.MONEYF, i);
					if (moneyfObj != null)
						moneyf = new UFDouble(moneyfObj.toString());

					reliefBVOS[i].setMoneyy(moneyy);
					reliefBVOS[i].setMoneyb(moneyb);
					reliefBVOS[i].setBrate(moneybrate);
					reliefBVOS[i].setFrate(frate);
					reliefBVOS[i].setMoneyf(moneyf);

					// Я��Ʊ�ݱ�ź�Ʊ������
					reliefBVOS[i].setFbmbillno((String) getUI()
							.getRefTakenProccessor()
							.getValueByTakenInList(ReliefBVO.TABLECODE,
									ReliefBVO.FBMBILLNO, i));
					reliefBVOS[i].setFbmbilltype((String) getUI()
							.getRefTakenProccessor().getValueByTakenInList(
									ReliefBVO.TABLECODE, ReliefBVO.FBMBILLTYPE,
									i));
				}
			}
		}

		super.onBoActionElse(bo);
		if(btncode.equals(IFBMButton.FBM_TALLY_STR) ){
			if(getBufferData().getCurrentVO()!=null && getBufferData().getCurrentVO().getParentVO() !=null){
				String pk_accid = ((ReliefVO)getBufferData().getCurrentVO().getParentVO()).getInneracc() ;
				boolean isValid = showNoTallyInfo(pk_accid);
				if(!isValid){
					return;
				}
			}
		}
	}

	
	/**
	 * ���Ǹ��෽������Ϊ���㵥λʱֻ��ѯ����˻�����֤�ĵ���
	 */
	protected String getHeadCondition() {
		String pk_corp = _getCorp().getPrimaryKey();
		boolean isCenter = FBMClientInfo.isSettleCenter(pk_corp);
		if (isCenter) {// ���ģ���������ֱ�ӷ��ظ�����Ӧ��ѯ����
			return super.getHeadCondition();
		} else {// ��λ
			return new StringBuffer().append(" (reliefcorp = '"+pk_corp+"' and fbm_relief.vbillstatus =  ")
					.append(IBillStatus.CHECKPASS).append(
							" or fbm_relief.vbillstatus =  ").append(
							IFBMStatus.HAS_VOUCHER).append(
							" or fbm_relief.vbillstatus= ").append(
							IFBMStatus.HAS_UNIT_VOUCHER).append(" ) ")
					.toString();
		}
	}

	/**
	 * �������Я�����ɵ�Ҫ�ز�ѯʱ����
	 */
//	@Override
//	protected UIDialog createQueryUI() {
//		return new RefTakenQueryConditionClient(getUI(), null, _getCorp()
//				.getPk_corp(), FbmBusConstant.FUNCODE_RELIEF_OUT,
//				_getOperator(), FbmBusConstant.BILLTYPE_RELIEF, getUI()
//						.getRefTakenProccessor());
//	}
	
	/**
	 * ɾ��ʱ�����¼��������
	 */
	protected void onBoLineDel() throws Exception {
		super.onBoLineDel();
		recalSummoney();
	}

	
	private void recalSummoney(){
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(
				ReliefBVO.TABLECODE);
		
		int rowcount = billmodel.getRowCount();
		UFDouble summoneyy = new UFDouble(0);
		UFDouble moneyy;
		for(int i = 0 ; i < rowcount; i++){
			Object obj = getUI().getRefTakenProccessor().getValueByTakenInList(ReliefBVO.TABLECODE, ReliefBVO.MONEYY, i);
			if (obj instanceof BigDecimal) {
				moneyy = new UFDouble(((BigDecimal) obj).doubleValue());
			} else {
				moneyy = (UFDouble) obj;
			}
			
			if(null == moneyy || moneyy.doubleValue() == 0){
				continue;
			}
			summoneyy = summoneyy.add(moneyy);
		}
		getUI().getBillCardPanel().getHeadItem(ReliefVO.SUMMONEY).setValue(summoneyy);
	}
	@Override
	protected void onBoSave() throws Exception {
		// TODO Auto-generated method stub
		recalSummoney();
		super.onBoSave();
	}
	
	@Override
	public void onBoAudit() throws Exception {
		// TODO Auto-generated method stub
		super.onBoAudit();
		

	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.bill.BillEventHandler#onBoQuery()
	 */
	@Override
	protected void onBoQuery() throws Exception {
		// TODO �Զ����ɷ������
		super.onBoQuery();
	}
}
