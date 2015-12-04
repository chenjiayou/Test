package nc.ui.fbm.storage.status;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fts.pub.CenterUnitUtil;

/**
 * 判断登录公司是否结算中心
 * 
 * @author xwq
 * 
 *         2008-8-2
 */
public class LoginCenterStatus extends AbstractBillUIStatus<Integer> {
	public static int BTN_DISABLE = 0;

	public static int LOGIN_CENTER = 1;

	public static int LOGIN_UNIT = 2;

	public LoginCenterStatus(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	public Integer getStatus() throws Exception {
		String loginCorp = ClientEnvironment.getInstance().getCorporation().getPk_corp();
		// ISettleCenter centerSrv =
		// (ISettleCenter)NCLocator.getInstance().lookup(ISettleCenter.class.getName());
		// if(centerSrv.getSettleCenterByCorpPk(loginCorp) != null){
		// return LOGIN_CENTER;
		// }
		if (CenterUnitUtil.instance.getSettleCenterVOByCorpPK(loginCorp) != null) {
			return LOGIN_CENTER;
		}
		return LOGIN_UNIT;
	}

	public String getStatusKindName() {
		// TODO Auto-generated method stub
		return LoginCenterStatus.class.getName();
	}

}
