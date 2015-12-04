package nc.ui.fbm.pub;

import nc.bs.framework.common.NCLocator;
import nc.bs.pf.pub.PfDataCache;
import nc.itf.fbm.pub.IFBMQuery;
import nc.itf.tm.query.ITakenPubQuery;
import nc.md.MDBaseQueryFacade;
import nc.md.model.IBean;
import nc.uap.pf.metadata.PfMetadataTools;
import nc.ui.pf.pub.PfUIDataCache;
import nc.ui.trade.bsdelegate.BusinessDelegator;
import nc.vo.pf.changeui02.VotableVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.billtype.BilltypeVO;

public class FBMBusinessDelegator extends BusinessDelegator {

	@SuppressWarnings("unchecked")
	@Override
	public CircularlyAccessibleValueObject[] queryBodyAllData(Class voClass,
			String billType, String key, String strWhere) throws Exception {
		if (billType == null || billType.trim().length() == 0)
			return null;
		else
			return queryAllBodyData(billType, voClass, key, strWhere);

	}

	/**
	 * 根据条件进行数据查询
	 */
	public SuperVO[] queryHeadAllData(Class headClass, String strBillType,
			String strWhere) throws Exception {
		BilltypeVO billVo = PfUIDataCache.getBillType(strBillType);
		if (billVo.getWherestring() != null
				&& billVo.getWherestring().length() != 0) {
			if (strWhere != null)
				strWhere = strWhere + " and (" + billVo.getWherestring() + ")";
			else
				strWhere = billVo.getWherestring();
		}
		return getService().queryByCondition(headClass, strWhere);
	}

	/**
	 * 根据条件主键进行数据查询
	 */
	public SuperVO queryByPrimaryKey(Class voClass, String strKey)
			throws Exception {
		return getService().queryByPrimaryKey(voClass, strKey);
	}

	/**
	 * 根据条件进行数据查询
	 */
	public SuperVO[] queryByCondition(Class voClass, String strWhere)
			throws Exception {
		return getService().queryByCondition(voClass, strWhere);
	}

	protected ITakenPubQuery getService() {
		return (ITakenPubQuery) NCLocator.getInstance().lookup(ITakenPubQuery.class.getName());
	}

	@SuppressWarnings("unchecked")
	public CircularlyAccessibleValueObject[] queryAllBodyData(String billType,
			Class c, String key, String whereString) throws BusinessException {
		//boolean hasMeta = PfMetadataTools.isBilltypeRelatedMeta(billType);
		// 子表引用的主表PK字段
		
//		if (hasMeta) {
//			// XXX:使用元数据模型
//			IBean mainbean = PfMetadataTools.queryMetaOfBilltype(billType);
//			IBean subbean = MDBaseQueryFacade.getInstance().getBeanByFullClassName(c.getName());
//
//			strPkField = subbean.getTable().getForeignKeyWithEndTable(mainbean.getTable()).getStartColumn().getName();
//
//			// strPkField = bean.getTable().getPrimaryKeyName();
//		} else {
//			// XXX:使用VO对照信息
//			VotableVO tableVo = PfDataCache.getBillTypeToVO(billType, false);
//			strPkField = tableVo.getPkfield();
//		}
		String strPkField = null;
		try {
			SuperVO temp = (SuperVO)c.newInstance();
			strPkField = temp.getParentPKFieldName();
		} catch (InstantiationException e) {
			throw new BusinessException(e);
		} catch (IllegalAccessException e) {
			throw new BusinessException(e);
		}
		CircularlyAccessibleValueObject[] retVos = null;
		String strWhere = null;
		strWhere = strPkField + "='" + key + "'";
		if (whereString != null)
			strWhere += " and (" + whereString + ")";
		
		
		IFBMQuery fbmQuery = (IFBMQuery) NCLocator.getInstance().lookup(IFBMQuery.class);
		retVos = fbmQuery.queryBodyData(c, strWhere);
		return retVos;

	}
}
