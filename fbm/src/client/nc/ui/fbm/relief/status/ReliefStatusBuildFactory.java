package nc.ui.fbm.relief.status;

import java.util.ArrayList;
import java.util.List;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.storage.status.DealSelfBillStatus;
import nc.ui.fbm.storage.status.LoginCenterStatus;
import nc.ui.fbm.storage.status.TallyStatus;
import nc.ui.fbm.storage.status.VoucherStatus;
import nc.ui.trade.button.IBillButton;
import nc.vo.engine.status.element.IStatusCheck;
import nc.vo.engine.status.element.StatusElementForInt;
import nc.vo.engine.status.element.UAPBillStatusElement;
import nc.vo.engine.status.factory.UAPButtonStatusBuildFactory;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * 
 * �๦��˵���� ״̬��չ���칤�� ���ڣ�2007-10-29 ����Ա�� wues
 * 
 */
public class ReliefStatusBuildFactory extends UAPButtonStatusBuildFactory {

	/**
	 * 
	 */
	public ReliefStatusBuildFactory() {
	}

	protected List<List> initSelfButtonRule(List<List> rule, ButtonVO btnVO) {
		rule.clear();
		ArrayList<IStatusCheck<Integer>> elementStatus = new ArrayList<IStatusCheck<Integer>>();;
		switch (btnVO.getBtnNo()) {
		case IBillButton.Add://����
			elementStatus.add(new StatusElementForInt(LoginCenterStatus.class.getName(),new int[]{LoginCenterStatus.LOGIN_CENTER}));
			break;
		case IBillButton.Edit:// �޸�
		case IBillButton.Delete:// ɾ��
			elementStatus.add(new StatusElementForInt(LoginCenterStatus.class.getName(),new int[]{LoginCenterStatus.LOGIN_CENTER}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[] { IBillStatus.FREE }));// ����̬
			elementStatus.add(new StatusElementForInt(DealSelfBillStatus.class.getName(),new int[]{DealSelfBillStatus.DEAL_SELF_BILL}));
			break;
		case IFBMButton.Discount_LinkGather:	
			elementStatus.add(new StatusElementForInt(DealSelfBillStatus.class.getName(),new int[]{DealSelfBillStatus.DEAL_SELF_BILL}));
			rule.add(elementStatus);
			break;
		case IBillButton.Audit:// ���
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class
					.getName(), new int[] { IBillStatus.FREE,
					IBillStatus.CHECKGOING }));
			elementStatus.add(new StatusElementForInt(DealSelfBillStatus.class.getName(),new int[]{DealSelfBillStatus.DEAL_SELF_BILL}));
			rule.add(elementStatus);
			break;
		case IBillButton.CancelAudit:// ����
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class
					.getName(), new int[] { IBillStatus.CHECKPASS }));// �����ͨ��
			elementStatus.add(new StatusElementForInt(DealSelfBillStatus.class.getName(),new int[]{DealSelfBillStatus.DEAL_SELF_BILL}));
			elementStatus.add(new StatusElementForInt(IsUnitWriteBankaccStatus.class.getName(),new int[]{IsUnitHasAccountStatus.NOT_SIGH}));
			elementStatus.add(new StatusElementForInt(VoucherStatus.class.getName(),new int[]{VoucherStatus.CENTER_CAN_VOUCHER,VoucherStatus.UNIT_CAN_VOUCHER}));
			rule.add(elementStatus);
			break;
		case IFBMButton.Relief_Output:// ����
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS}));
			elementStatus.add(new StatusElementForInt(LoginCenterStatus.class.getName(),new int[]{LoginCenterStatus.LOGIN_CENTER}));
			break;
			
		case IFBMButton.FBM_TALLY:
			elementStatus.add(new StatusElementForInt(TallyStatus.class.getName(),new int[]{TallyStatus.UNIT_CAN_TALLY}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS}));
			break;
		case IFBMButton.FBM_CANCELTALLY:
			elementStatus.add(new StatusElementForInt(TallyStatus.class.getName(),new int[]{TallyStatus.UNIT_CAN_CANCELTALLY}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS}));
			break;
		case IFBMButton.FBM_VOUCHER:
			elementStatus.add(new StatusElementForInt(VoucherStatus.class.getName(),new int[]{VoucherStatus.CENTER_CAN_VOUCHER,VoucherStatus.UNIT_CAN_VOUCHER}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS}));
			break;
		case IFBMButton.FBM_CANCELVOUCHER:
			elementStatus.add(new StatusElementForInt(VoucherStatus.class.getName(),new int[]{VoucherStatus.CENTER_CAN_CANCELVOUCHER,VoucherStatus.UNIT_CAN_CANCELVOUCHER}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS}));
			break;

		default:
		}
		if(elementStatus!=null && elementStatus.size() > 0){
			rule.add(elementStatus);
		}
		return rule;
	}
}