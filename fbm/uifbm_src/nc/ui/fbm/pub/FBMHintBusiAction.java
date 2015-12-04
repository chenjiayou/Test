package nc.ui.fbm.pub;

import nc.ui.pub.beans.UIDialog;
import nc.ui.tm.cmpbankacc.TMAccExceptionHandler;
import nc.ui.tm.framework.util.hint.HintBusiActionSupport;
import nc.ui.tm.framework.util.hint.HintExceptionHandledStatus;
import nc.ui.tm.framework.util.hint.manage.ManageHintBusiActionContext;
import nc.vo.cdm.exception.InnerAccountException;
import nc.vo.cmp.exception.CmpAuthorizationException;
import nc.vo.fts.account.IAccountConst;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;

public class FBMHintBusiAction<R> extends
		HintBusiActionSupport<ManageHintBusiActionContext, R> {

	public FBMHintBusiAction() {
		super();
	}

	@SuppressWarnings("unchecked")
	protected R processActionInternal() throws Exception {
		return (R) getCurrentContext().getBusiController().processAction(getCurrentContext().getButtonTag(), getCurrentContext().getCloneModelVO(), getCurrentContext().getController().getBillType(), getCurrentContext().getUI()._getDate().toString(), getCurrentContext().getUI().getUserObject());
	}

	public HintExceptionHandledStatus handleActionException(Exception exception) {
		String message = exception.getMessage();
		
		HintExceptionHandledStatus handledStatus = HintExceptionHandledStatus.Unhandled;
		
		if (message != null && message.indexOf("10003") != -1) {// 里面包含10003，说明是提示信息
			handledStatus = HintExceptionHandledStatus.HintShowed;
			int ret = getCurrentContext().getUI().showYesNoMessage(message);
			if (UIDialog.ID_YES == ret) {// 用户点击yes
				SuperVO superVO = (SuperVO) getCurrentContext().getCloneModelVO().getParentVO();
				superVO.setAttributeValue("writeplan", UFBoolean.TRUE);
				
				handledStatus = HintExceptionHandledStatus.Continue;
			}
		} else if (CmpAuthorizationException.class.isAssignableFrom(exception.getClass())) {// 
			handledStatus = HintExceptionHandledStatus.HintShowed;

			boolean result = new TMAccExceptionHandler(
					getCurrentContext().getUI()).handleException((CmpAuthorizationException) exception);
			if (result) {// 继续执行下面

				SuperVO superVO = (SuperVO) getCurrentContext().getCloneModelVO().getParentVO();
				superVO.setAttributeValue("writebankacc", UFBoolean.TRUE);

				handledStatus = HintExceptionHandledStatus.Continue;
			}
		} else if (exception.getCause() != null
				&& InnerAccountException.class.isAssignableFrom(exception.getCause().getClass())) {
			handledStatus = HintExceptionHandledStatus.HintShowed;
			
			InnerAccountException ex = (InnerAccountException) exception.getCause();

			if (IAccountConst.HINT.equals(ex.getErrType())) {// 提示
				int ret = getCurrentContext().getUI().showYesNoMessage(ex.getErrInfo()
						+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("faccdmcode", "UPPFACCDMCODE-000034")/*
																													 * @res
																													 * "
																													 * 是否继续
																													 * ？
																													 * "
																													 */);
				if (UIDialog.ID_YES == ret) {// 用户点击yes
					SuperVO superVO = (SuperVO) getCurrentContext().getCloneModelVO().getParentVO();
					superVO.setAttributeValue("writeinneracc", UFBoolean.TRUE);

					handledStatus = HintExceptionHandledStatus.Continue;
				}
			}
		}
		
		return handledStatus;
	}
}
