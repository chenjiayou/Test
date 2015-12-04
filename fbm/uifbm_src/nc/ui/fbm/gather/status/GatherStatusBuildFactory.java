/**
 *
 */
package nc.ui.fbm.gather.status;

import java.util.ArrayList;
import java.util.List;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButton;
import nc.vo.engine.status.element.IStatusCheck;
import nc.vo.engine.status.element.StatusElementForInt;
import nc.vo.engine.status.element.UAPBillStatusElement;
import nc.vo.engine.status.factory.UAPButtonStatusBuildFactory;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-9-18
 *
 */
public class GatherStatusBuildFactory extends UAPButtonStatusBuildFactory {

	/**
	 * 
	 */
	public GatherStatusBuildFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<List> initSelfButtonRule(List<List> rule, ButtonVO btnVO) {
		switch(btnVO.getBtnNo()){
			case IFBMButton.Gather_ReturnBill:
				rule.clear();
				ArrayList<IStatusCheck<Integer>> elementStatus = new ArrayList<IStatusCheck<Integer>>();
				elementStatus.add(new StatusElementForInt(IFBMBillTypeStatus.class.getName(), new int[]{IFBMBillTypeStatus.STATUS_REGISTERD,IFBMBillTypeStatus.STATUA_DISABLE,IFBMBillTypeStatus.STATUA_ENDORE,IFBMBillTypeStatus.STATUS_ALL}));
				rule.add(elementStatus);
				break;
			case IFBMButton.Gather_Impawn:
			case IFBMButton.Gather_BankKeep:
				rule.clear();
				elementStatus = new ArrayList<IStatusCheck<Integer>>();
				elementStatus.add(new StatusElementForInt(IFBMBillTypeStatus.class.getName(), new int[]{IFBMBillTypeStatus.STATUS_REGISTERD,IFBMBillTypeStatus.STATUS_ALL}));
				rule.add(elementStatus);
				break;
			case IFBMButton.Gather_BankDiscount:
			case IFBMButton.Gather_Consign:
				rule.clear();
				elementStatus = new ArrayList<IStatusCheck<Integer>>();
				
				elementStatus.add(new StatusElementForInt(IFBMBillTypeStatus.class.getName(), new int[]{IFBMBillTypeStatus.STATUS_BANKKEEPED,IFBMBillTypeStatus.STATUS_REGISTERD,IFBMBillTypeStatus.STATUS_ALL}));
				rule.add(elementStatus);
				break;
			case IFBMButton.Gather_BankBack:
				rule.clear();
				elementStatus = new ArrayList<IStatusCheck<Integer>>();
				elementStatus.add(new StatusElementForInt(IFBMBillTypeStatus.class.getName(), new int[]{IFBMBillTypeStatus.STATUS_BANKKEEPED,IFBMBillTypeStatus.STATUS_ALL}));
				rule.add(elementStatus);
				break;
			case IBillButton.Delete:
			case IBillButton.Edit:
				List<IStatusCheck<Integer>> rules = rule.get(0);
				rules.add(new StatusElementForInt(IsEndureControlStatus.class.getName(), new int[]{IsEndureControlStatus.IS_NOT_ENDURE}));
				
				elementStatus = new ArrayList<IStatusCheck<Integer>>();
				elementStatus.add(new UAPBillStatusElement(IFBMStatus.class.getName(), new int[]{IFBMStatus.INIT}));
				break;
			default:
		}
		
		return rule;
	}
}
