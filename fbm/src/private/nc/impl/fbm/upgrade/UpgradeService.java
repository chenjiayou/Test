package nc.impl.fbm.upgrade;

import java.util.Map;

import nc.bs.fbm.upgrade.UpgradeDataManager;
import nc.itf.fbm.upgrade.IUpgradeService;
import nc.vo.pub.BusinessException;

public class UpgradeService implements IUpgradeService {

	public void checkUpgrade(String syscode) throws BusinessException {
		// TODO Auto-generated method stub
		UpgradeDataManager manager = new UpgradeDataManager();
		manager.checkUpgrade(syscode);
	}

	public void clearUpgrade() throws BusinessException {
		// TODO Auto-generated method stub
		UpgradeDataManager manager = new UpgradeDataManager();
		manager.clearUpgrade();
	}

	public void upgradeARAP(Map<String,String> typeMap) throws BusinessException {
		// TODO Auto-generated method stub
		UpgradeDataManager manager = new UpgradeDataManager();
		manager.upgradeARAP(typeMap);
	}

	public void upgradeFBM(Map<String,String> typeMap) throws BusinessException {
		// TODO Auto-generated method stub
		UpgradeDataManager manager = new UpgradeDataManager();
		manager.upgradeFBM(typeMap);
	}

	public String[] checkInstall(String syscode) throws BusinessException {
		UpgradeDataManager manager = new UpgradeDataManager();
		return manager.retriveCorpNotInstall();
	}
 
}
