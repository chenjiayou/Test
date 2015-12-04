/**
 * 
 */
package nc.ui.fbm.endore.checker;

import nc.bs.logging.Logger;
import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.para.SysInitBO_Client;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.pub.lang.UFBoolean;

/**
 * @author lpf
 *
 */
public class CopyButtonChecker extends AbstractUIChecker {

	/**
	 * 
	 */
	public CopyButtonChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public CopyButtonChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractUIChecker#check()
	 */
	@Override
	public String check() {
		try {
			if(isGatherARAP()){
				return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000027")/*@res "备查簿业务与应收管理、应付管理、现金管理集成应用，请通过应付管理、现金平台进行该操作。"*/;
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * @return 背书业务是否与收付报单据集成应用
	 * 
	 */
	private boolean isGatherARAP() throws Exception
	{
		UFBoolean value = SysInitBO_Client.getParaBoolean(ClientInfo.getCorpPK(), FBMParamConstant.FBM005);
		return value != null && value.booleanValue();
	}
}
