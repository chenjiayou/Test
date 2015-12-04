package nc.impl.fbm.pub;

import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.framework.common.NCLocator;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.fbm.pub.IFBMQuery;
import nc.itf.tm.query.ITakenPubQuery;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;

public class FBMQueryImpl implements IFBMQuery {

	@SuppressWarnings("unchecked")
	public CircularlyAccessibleValueObject[] queryBodyData(Class c,
			String wherePart) throws BusinessException {
		CircularlyAccessibleValueObject[] retVos = null;
		retVos = getService().queryByCondition(c, wherePart);
		RegisterVO2BVO.execFormula((SuperVO[]) retVos);
		return retVos;
	}

	protected ITakenPubQuery getService() {
		return (ITakenPubQuery) NCLocator.getInstance().lookup(ITakenPubQuery.class.getName());
	}

	public CircularlyAccessibleValueObject[] queryBodyVOs(SuperVO supervo,
			String[] pks)
			throws BusinessException {
		
		String wheresql = getWhereSQL(supervo, pks);
		
		SuperVO sourcevo[] = getSourceVO(supervo, wheresql);
		
		CircularlyAccessibleValueObject[] retvos = null;
		retvos = RegisterVO2BVO.getCoverVO(supervo, sourcevo);
		RegisterVO2BVO.execFormula((SuperVO[]) retvos);
		return retvos;
	}
	
	/**
	 * 根据 pks 组合 带IN 的SQL
	 * 
	 * @param supervo
	 * @param pks
	 * @return
	 */
	private String getWhereSQL(SuperVO supervo, String[] pks) {
		if (supervo instanceof ReckonBVO) {
			return CommonUtil.buildSqlForIn("pk_detail", pks);
		} else {
			return CommonUtil.buildSqlForIn("pk_register", pks);
		}
	}
	
	/**
	 * 根据 where 条件 查询 SuperVO 数组。
	 * 
	 * @param supervo
	 * @param wheresql
	 * @return
	 * @throws BusinessException
	 */
	private SuperVO[] getSourceVO(SuperVO supervo, String wheresql)
			throws BusinessException {
		if (supervo instanceof ReckonBVO) {
			return (AccountDetailVO[]) getService().queryByCondition(AccountDetailVO.class, wheresql);
		} else {
			return (RegisterVO[]) getService().queryByCondition(RegisterVO.class, wheresql);

		}
	}
	
	
}
