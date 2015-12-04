package nc.ui.fbm.reckon;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.glpub.UiManager;
import nc.ui.pub.FramePanel;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.linkoperate.ILinkType;
import nc.vo.pub.BusinessException;

public class ReckonUiManager extends UiManager {

	private static final long serialVersionUID = 1L;

	public ReckonUiManager(FramePanel panel) {
		super(panel);
		// TODO Auto-generated constructor stub
	}

	@Override
	/*
	 * * 作者：bsrl <br> 日期：2007-11-28
	 * 
	 * @see nc.ui.pub.ToftPanel#checkPrerequisite()
	 */
	protected String checkPrerequisite() {
		if (!FBMClientInfo.isSettleCenter())
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201050", "UPP36201050-000001")/*
																								 * @res
																								 * "非票据管理中心不可使用该节点!"
																								 */;
		String pk_cubasdoc = null;
		try {
			pk_cubasdoc = FBMClientInfo.getCommonCurCorpCubasdoc();
		} catch (BusinessException e) {
			Logger.error(e);
		}
		if (CommonUtil.isNull(pk_cubasdoc)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000113")/*
																										 * @res
																										 * "当前公司未设置对应客商,无法进行调剂清算业务"
																										 */;
		}
		if (ILinkType.LINK_TYPE_QUERY == getLinkedType()) {
			return super.checkPrerequisite();
		}

		if (!FBMClientInfo.isStartProcess().booleanValue()) {
			MessageDialog.showWarningDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("361608", "UPP361608-000015")/*
																																 * @res
																																 * "提示"
																																 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("361608", "UPP361608-000263")/*
																																																						 * @res
																																																						 * "登录日期没有开始受理过，只有查询，打印，刷新功能！"
																																																						 */);
		}

		return super.checkPrerequisite();
	}

}
