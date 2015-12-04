package nc.bs.fbm.innerback.action;

import nc.bs.framework.common.NCLocator;
import nc.itf.tm.cmpbankacc.ITMBankaccService;

public class InnerBackService {

	/**
	 * 操作银行帐户账接口
	 */
	private ITMBankaccService bankTallyService = null;
	
	private ITMBankaccService getBankTallyService() {
		if (null == bankTallyService) {
			bankTallyService = (ITMBankaccService) NCLocator.getInstance()
					.lookup(ITMBankaccService.class);
		}
		return bankTallyService;
	}
}
