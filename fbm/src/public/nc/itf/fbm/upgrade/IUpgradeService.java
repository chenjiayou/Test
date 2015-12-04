package nc.itf.fbm.upgrade;

import java.util.Map;

import nc.vo.pub.BusinessException;

/**
 * 升级服务类接口
 * @author xwq
 *
 */
public interface IUpgradeService {
	/**
	 * 检查系统是否已升级
	 * @param syscode
	 * @throws BusinessException
	 */
	public void checkUpgrade(String syscode) throws BusinessException;
	
	/**
	 * 升级财务票据
	 * @param typeMap
	 * @throws BusinessException
	 */

	public void upgradeARAP(Map<String,String> typeMap) throws BusinessException;
	
	/**
	 * 升级原资金票据
	 * @param typeMap
	 * @throws BusinessException
	 */
	public void upgradeFBM(Map<String,String> typeMap) throws BusinessException;
	
	/**
	 * 清除升级数据
	 * @throws BusinessException
	 */
	public void clearUpgrade() throws BusinessException;
	
	/**
	 * 检查公司是否安装资金票据
	 * @throws BusinessException
	 */
	public String[] checkInstall(String syscode) throws BusinessException;
}
