package nc.bs.fbm.pub;

import java.util.Hashtable;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.uap.bd.cust.ICustBasDocQuery;
import nc.itf.uap.bd.cust.ICustManDocQuery;
import nc.itf.uap.sf.ICreateCorpQueryService;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.bd.b08.CubasdocVO;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.bd.b19.BalatypeVO;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

/**
 *
 * <p>
 *   公用票据数据库操作
 * </p>
 * @author xwq
 * @date 2007-8-9
 * @version V5.0
 */
public class CommonDAO {
	private BaseDAO baseDao = null;

	public CommonDAO(){
		super();
		baseDao = new BaseDAO();
	}


	/**
	 *
	 * 查询票据基本信息
	 * @param pk_baseinfo 票据基本信息主键
	 * @return 基本信息VO
	 *
	 */
	public BaseinfoVO queryBaseinfoByPK(String pk_baseinfo) throws BusinessException{
		try {
			return (BaseinfoVO)baseDao.retrieveByPK(BaseinfoVO.class, pk_baseinfo);
		} catch (DAOException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * 删除票据基本信息数据
	 * @param pk_baseinfo
	 * @throws BusinessException
	 */
	public void deleteBaseinfobyPk(String pk_baseinfo) throws BusinessException{
		try {
			baseDao.deleteByPK(BaseinfoVO.class, pk_baseinfo);
		} catch (DAOException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * 票据基本信息打废票标志
	 * @param pk_baseinfo
	 * @throws BusinessException
	 */
	public void updateBaseinfoDisableStatusbyPk(BaseinfoVO baseinfovo) throws BusinessException{
		try {
			String sql = "update fbm_baseinfo set disable = '" + baseinfovo.getDisable() + "' where isnull(dr,0)=0 and pk_baseinfo = '" + baseinfovo.getPk_baseinfo() + "'";
			baseDao.executeUpdate(sql);
		} catch (DAOException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * <p>
	 * 删除票据基本信息数据
	 * <p>
	 * 作者：lpf
	 * 日期：2007-8-28
	 * @param strWhere
	 * @throws BusinessException
	 * @throws BusinessException
	 */
	public void deleteBaseinfobyClause(String strWhere) throws BusinessException{
		try {
			baseDao.deleteByClause(BaseinfoVO.class, strWhere);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			throw new BusinessException(e.getMessage());
		}
	}



	/**
	 *
	 * 根据动作PK删除账户信息
	 * xwq    2007-8-31
	 * @param
	 * @return
	 * @throws
	 * @since	NC5.0
	 */
	public void delAccountDetailByActionPK(String pk_action) throws BusinessException{
		baseDao.deleteByClause(AccountDetailVO.class, "pk_action='" + pk_action +"'");
	}


	/**
	 *
	 * TODO 方法简要描述，必须以句号为结束
	 * xwq    2007-8-31
	 * @param
	 * @return
	 * @throws
	 * @since	NC5.0
	 */
	public BaseDAO getBaseDAO(){
		return baseDao;
	}

	/**
	 * 根据公司编码查询出对应客商基本档案PK
	 * @param pk_corp
	 * @return
	 */
	public String queryCustByCorp(String pk_corp)throws BusinessException{
		return queryCustVOByCorp(pk_corp).getPk_cubasdoc();
	}

	/**
	 * 根据公司编码查询出对应客商基本档案VO
	 * @param pk_corp
	 * @return
	 */
	public CustBasVO queryCustVOByCorp(String pk_corp) throws BusinessException{
		String sCondition = " bd_cubasdoc.pk_corp1 = '" + pk_corp + "' ";;
		ICustManDocQuery cusQuery = (ICustManDocQuery)NCLocator.getInstance().lookup(ICustManDocQuery.class.getName());
		CustBasVO[] custbasVos= cusQuery.queryCustBasicInfo(sCondition, pk_corp);
		if(custbasVos==null||custbasVos.length==0)
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000153")/* @res"当前公司没有设置对应的内部客商！"*/);
		if(custbasVos.length>1){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000154")/* @res"当前公司设置对应的内部客商多于一个请处理！"*/);
		}
		return custbasVos[0];
	}
	/**
	 * 根据客商pk查公司pk
	 * @return
	 * @throws BusinessException
	 */
	public String queryCorpByCust(String pk_cust) throws BusinessException {
		ICustBasDocQuery cusQuery = (ICustBasDocQuery)NCLocator.getInstance().lookup(ICustBasDocQuery.class.getName());
		CubasdocVO custBasVO = cusQuery.queryCustBasDocVOByPK(pk_cust);
		if(custBasVO == null || custBasVO.getParentVO() == null){
			return null;
		}
		return ((CustBasVO)custBasVO.getParentVO()).getPk_corp1();
	}

	/**
	 * 查询得到最新的收票
	 * @param pk_invoice
	 * @return
	 * @throws BusinessException
	 */
	public RegisterVO queryLastGather(String pk_baseinfo,String pk_corp) throws BusinessException{
		String sql = "select * from fbm_action where isnull(dr,0)=0 and billtype = '36GA' and pk_baseinfo = '" + pk_baseinfo + "' order by serialnum desc";
		List<ActionVO> list = (List<ActionVO>)(getBaseDAO().executeQuery(sql,new BeanListProcessor(ActionVO.class)));
		ActionVO actionvo = null;
		if(list != null && list.size() > 0){
			actionvo =  (ActionVO)list.get(0);
		}
		if(actionvo == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000333")/* @res"无此票据收票操作记录"*/);
		}
		String pk_register = actionvo.getPk_source();
		RegisterVO regVO = (RegisterVO)baseDao.retrieveByPK(RegisterVO.class, pk_register);

		regVO.setRegisterstatus(actionvo.getEndstatus());

		if(!regVO.getPk_corp().equals(pk_corp)){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000334")/* @res"此票据不在当前公司"*/);
		}

		return regVO;
	}

	/**
	 * 判断结算方式，如果是银行承兑汇票，则返回true，否则返回false
	 * @param pk_jsfs
	 * @return
	 * @throws BusinessException
	 */
	public boolean isFbmBill(String pk_jsfs) throws BusinessException{
//		if(CommonUtil.isNull(pk_jsfs)){
//			return false;
//		}
//		BalatypeVO balatypeVO = (BalatypeVO) baseDao.retrieveByPK(BalatypeVO.class, pk_jsfs);
//		UFBoolean b = balatypeVO.getIsacceptbill();
//		return b == null? false:b.booleanValue();
		//throw new BusinessException("结算方式已取消，此方法不能被引用");
		return false;
	}
	/**
	 * 根据客商判断产品是否启用
	 */
	public boolean productEnableByCust(String pk_bacusdoc,String product) throws BusinessException{
		String pk_corp = queryCorpByCust(pk_bacusdoc);
		if(pk_corp == null){
			return false;
		}
		return productEnableByCorp(pk_corp,product);
	}
	/**
	 * 根据公司判断产品是否启用
	 * @param pk_corp
	 * @param product
	 * @return
	 * @throws BusinessException
	 */
	public boolean productEnableByCorp(String pk_corp,String product) throws BusinessException{
		if(pk_corp == null){
			return false;
		}
		ICreateCorpQueryService ProductService = (ICreateCorpQueryService) NCLocator.getInstance().lookup(ICreateCorpQueryService.class.getName());
		Hashtable productEnabled = ProductService.queryProductEnabled( pk_corp,new String[]{product});
		return ((UFBoolean) productEnabled.get(product)).booleanValue() ;
	}
	
	public int executeUpdate(String[] sqls) throws DAOException {
		if (CommonUtil.isNull(sqls)) {
			return 0;
		}

		PersistenceManager sessionManager = null;
		try {
			sessionManager = PersistenceManager.getInstance();
			JdbcSession session = sessionManager.getJdbcSession();
			for (int i = 0; i < sqls.length; i++) {
				if (!CommonUtil.isNull(sqls[i])) {
					session.addBatch(sqls[i]);
				}
			}
			return session.executeBatch();
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (sessionManager != null) {
				sessionManager.release();
			}
		}
	}

}