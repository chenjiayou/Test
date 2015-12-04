/**
 *
 */
package nc.ui.fbm.storage.bankback;

import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.pub.beans.UIDialog;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.pub.constant.FbmBusConstant;

/**
 * <p>
 * 银行领用单动作类
 * <p>创建人：lpf
 * <b>日期：2007-8-21
 *
 */
public class BankBackEventHandler extends FBManageEventHandler {

	/**
	 * @param billUI
	 * @param control
	 */
	public BankBackEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getHeadCondition() {
		// TODO Auto-generated method stub
		return " fbm_storage.pk_billtypecode='"+FbmBusConstant.BILLTYPE_BANKBACK+"' and fbm_storage.pk_corp='"+ClientInfo.getCorpPK()+"' ";
	}
	
//	@Override
//	protected UIDialog createQueryUI() {
//		// TODO Auto-generated method stub
//		return new RefTakenQueryConditionClient(getUI(), null, _getCorp()
//				.getPk_corp(), FbmBusConstant.FUNCODE_STORAGE_BANKBACK,
//				_getOperator(), FbmBusConstant.BILLTYPE_BANKBACK,getUI().getRefTakenProccessor());
//	}

}
