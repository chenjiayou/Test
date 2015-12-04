/**
 *
 */
package nc.ui.fbm.storage.bankkeep;

import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.pub.beans.UIDialog;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.pub.constant.FbmBusConstant;

/**
 * <p>
 * 银行存放单动作类
 * <p>创建人：lpf
 * <b>日期：2007-8-20
 *
 */
public class BankKeepEventHandler extends FBManageEventHandler {

	/**
	 * @param billUI
	 * @param control
	 */
	public BankKeepEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
		// TODO Auto-generated constructor stub
	}

	protected String getHeadCondition() {
		return " fbm_storage.pk_billtypecode='"+FbmBusConstant.BILLTYPE_BANKKEEP+"' and fbm_storage.pk_corp='"+ClientInfo.getCorpPK()+"' ";
	}

//	protected UIDialog createQueryUI() {
//		// TODO Auto-generated method stub
//		return new RefTakenQueryConditionClient(getUI(), null, _getCorp()
//				.getPk_corp(), FbmBusConstant.FUNCODE_STORAGE_BANKKEEP,
//				_getOperator(), FbmBusConstant.BILLTYPE_BANKKEEP,getUI().getRefTakenProccessor());
//	}

}
