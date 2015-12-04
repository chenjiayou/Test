package nc.ui.fbm.illegal;

import java.awt.Container;

import nc.ui.fbm.illegal.checker.IllegalBillChecker;
import nc.ui.trade.check.BeforeActionCHK;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.trade.checkrule.VOChecker;

/**
 * checker gateway from client UI 
 * @author wues
 *
 */
public class IllegalBillUIChecker extends BeforeActionCHK{

	public void runBatchClass(Container parent, String billType,
			String actionName, AggregatedValueObject[] vos, Object[] obj)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void runClass(Container parent, String billType, String actionName,
			AggregatedValueObject vo, Object obj) throws Exception {
		if (!VOChecker.check(vo, new IllegalBillChecker())){
			throw new nc.vo.pub.BusinessException(VOChecker.getErrorMessage());
		}
	}

}
