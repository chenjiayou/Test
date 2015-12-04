package nc.vo.fbm.pub;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.bs.framework.common.RuntimeEnv;
import nc.itf.bd.bankdoc.IBankDocQueryService;
import nc.itf.fbm.accrule.IAccRule;
import nc.itf.uap.IUAPQueryBS;
import nc.itf.uap.bd.ISettleCenter;
import nc.itf.uif.pub.IUifService;
import nc.jdbc.framework.processor.BaseProcessor;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.ui.dbcache.DBCacheFacade;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.uapbd.bankaccount.BankaccbasVO;

/**
 * 票据公用查询类
 * 
 * @author xwq
 * 
 *         2008-10-9
 */
public class FBMProxy {

	public static IUAPQueryBS getUAPQuery() {
		return (IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class);
	}

	public static IUifService getUifService() {
		return (IUifService) NCLocator.getInstance().lookup(IUifService.class);
	}

	public static ISettleCenter getSettleCentService() {
		return (ISettleCenter) NCLocator.getInstance().lookup(ISettleCenter.class);
	}

	public static IBankDocQueryService getBankDocQueryService() {
		return (IBankDocQueryService) NCLocator.getInstance().lookup(IBankDocQueryService.class);
	}

	/**
	 * 内部账户入账规则接口
	 * 
	 * @return
	 */
	public static IAccRule getAccRuleService() {
		return (IAccRule) NCLocator.getInstance().lookup(IAccRule.class.getName());
	}

	/**
	 * 更新后返回superVO
	 */
	public static SuperVO updateSuperVO(SuperVO superVO)
			throws BusinessException {
		String pk = superVO.getPrimaryKey();
		getUifService().update(superVO);
		return (SuperVO) getUAPQuery().retrieveByPK(superVO.getClass(), pk);
	}

	/**
	 * 获取手动录入的客商
	 * 
	 * @param pk_cust
	 * @return
	 * @throws BusinessException
	 */
	public static CustBasVO retriveInputCust(String pk_cubasdoc)
			throws BusinessException {
		String sql = "select * from fbm_cubasdoc where pk_cubasdoc='"
				+ pk_cubasdoc
				+ "'";

		return queryVO(sql, CustBasVO.class);
	}

	/**
	 * 获取手动录入的银行账号
	 * 
	 * @param pk_bankaccbas
	 * @return
	 * @throws BusinessException
	 */
	public static BankaccbasVO retriveInputBankacc(String pk_bankaccbas)
			throws BusinessException {
		String sql = "select * from fbm_bankaccbas where pk_bankaccbas = '"
				+ pk_bankaccbas
				+ "'";
		return queryVO(sql, BankaccbasVO.class);
	}

	@SuppressWarnings("unchecked")
	public static <T> T queryVO(String sql, Class<T> clazz)
			throws BusinessException {
		Object retObject = null;
		ResultSetProcessor processor = new BeanProcessor(clazz);

		if (RuntimeEnv.getInstance().isRunningInServer()) {
			retObject = getUAPQuery().executeQuery(sql, processor);
		} else {
			retObject = DBCacheFacade.runQuery(sql, processor);
		}

		return (T) retObject;
	}

	@SuppressWarnings("unchecked")
	public static List queryListVO(String sql, Class clazz)
			throws BusinessException {
		List retObject = null;
		ResultSetProcessor processor = new BeanListProcessor(clazz);

		if (RuntimeEnv.getInstance().isRunningInServer()) {
			retObject = (List) getUAPQuery().executeQuery(sql, processor);
		} else {
			retObject = (List) DBCacheFacade.runQuery(sql, processor);
		}

		return retObject;
	}

	public static List queryListVOByColumnListProcessor(String sql,
			BaseProcessor processor) throws BusinessException {

		List retObject = null;

		if (RuntimeEnv.getInstance().isRunningInServer()) {
			retObject = (List) getUAPQuery().executeQuery(sql, processor);
		} else {
			retObject = (List) DBCacheFacade.runQuery(sql, processor);
		}

		return retObject;
	}
}
