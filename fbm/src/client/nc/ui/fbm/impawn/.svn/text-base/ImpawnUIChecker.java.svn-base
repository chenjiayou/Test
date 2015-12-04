package nc.ui.fbm.impawn;

import java.awt.Container;

import nc.ui.fbm.impawn.checker.ImpawnChecker;
import nc.ui.trade.businessaction.IPFACTION;
import nc.ui.trade.check.BeforeActionCHK;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.trade.checkrule.VOChecker;

/**
 * checker gateway from client UI 
 * @author wues
 *
 */
public class ImpawnUIChecker extends BeforeActionCHK{

	public void runBatchClass(Container parent, String billType,
			String actionName, AggregatedValueObject[] vos, Object[] obj)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void runClass(Container parent, String billType, String actionName,
			AggregatedValueObject vo, Object obj) throws Exception {
		if (IPFACTION.COMMIT.equals(actionName)) {
			if (!VOChecker.check(vo, new ImpawnChecker())){
				throw new nc.vo.pub.BusinessException(VOChecker.getErrorMessage());
			}	
		}
	}

}
