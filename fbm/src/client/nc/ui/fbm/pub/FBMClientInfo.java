/**
 *
 */
package nc.ui.fbm.pub;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.fts.pub.ICommon;
import nc.itf.uap.bd.cust.ICustBasDocQuery;
import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.business.HYPubBO_Client;
import nc.uif.pub.exception.UifException;
import nc.vo.bd.b08.CubasdocVO;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.bd.basedata.CurrtypeVO;
import nc.vo.fts.pub.CenterUnitUtil;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;

/**
 * <p>
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-10-10
 * 
 */
public class FBMClientInfo {

	

	public FBMClientInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * <p>
	 * 不加入必须是内部筹投资单位的限制，只取出当前公司对应的内部客商
	 * <p>
	 * 作者：lpf 日期：2007-8-15
	 */
	public static String getCommonCurCorpCubasdoc() throws BusinessException {
		// String strWhere = " bd_cubasdoc.pk_corp1 = '" + getCorpPK() + "' ";
		// CustBasVO[] custbasVos =
		// OuterProxy.getCustManDocQuery().queryCustBasicInfo(strWhere,
		// getCorpPK());
		// if (custbasVos == null || custbasVos.length == 0)
		// throw new BusinessException(
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
		// "UPPFBMComm-000153")/*
		// * @res
		// * "当前公司没有设置对应的内部客商！"
		// */);
		// if (custbasVos.length > 1) {
		// throw new BusinessException(
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
		// "UPPFBMComm-000154")/*
		// * @res
		// * "当前公司设置对应的内部客商多于一个请处理！"
		// */);
		// }
		// return custbasVos[0].getPk_cubasdoc();
		return ClientInfo.getCorpCuBas(getCorpPK());
	}

	/**
	 * <p>
	 * 得到当前公司PK
	 * <p>
	 * 作者：lpf 日期：2007-10-10
	 * 
	 * @return
	 */
	public static String getCorpPK() {
		// TODO Auto-generated method stub
		return ClientInfo.getCorpPK();
	}

	public static String getSuperSettleCenter(String pk_corp) {
		// return ClientInfo.getSuperSettleCenter(pk_corp);
		return CenterUnitUtil.instance.getUpperCenterCorpPKByUnitCorpPK(pk_corp);
	}

	/**
	 * 
	 * <p>
	 * 取出公司对应的内部客商
	 * <p>
	 * 作者：lpf 日期：2007-8-15
	 */
	public static String getCorpCubasdoc(String pk_corp) {
		// String strWhere = " bd_cubasdoc.pk_corp1 = '" + pk_corp + "' ";
		// CustBasVO[] custbasVos = null;
		// try {
		// custbasVos =
		// OuterProxy.getCustManDocQuery().queryCustBasicInfo(strWhere,
		// getCorpPK());
		// } catch (BusinessException e) {
		// Logger.error(e.getMessage(), e);
		// }
		// if (custbasVos == null || custbasVos.length == 0)
		// return null;
		// return custbasVos[0].getPk_cubasdoc();
		String cubasstr = null;
		try {
			cubasstr = ClientInfo.getCorpCuBas(getCorpPK());
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
		}
		return cubasstr;
	}

	/**
	 * 
	 * <p>
	 * 得到上级结算中心对应的内部客商主键
	 * <p>
	 * 作者：lpf 日期：2007-10-10
	 * 
	 * @param pk_corp
	 * @return
	 */
	public static String getSuperSettleCenterCust(String pk_corp)
			throws BusinessException {
		String centerCorp = getSuperSettleCenter(pk_corp);
		return getCorpCubasdoc(centerCorp);
	}

	/**
	 * <p>
	 * 判断当前公司是否是结算中心。
	 * <p>
	 * 作者：qbh <br>
	 * 日期：2006-2-13
	 * 
	 * @return
	 */
	public static boolean isSettleCenter() {
		String pk_corp = ClientEnvironment.getInstance().getCorporation().getPk_corp();
		return CenterUnitUtil.instance.isCorpCenter(pk_corp);

	}

	/**
	 * 
	 * <p>
	 * 根据公司PK判断是否是结算中心
	 * <p>
	 * 作者：lpf 日期：2007-10-11
	 * 
	 * @param corpPk
	 * @return
	 */
	public static boolean isSettleCenter(String corpPk) {
		// return OuterProxy.getSettleCenter().isSettleCenter(corpPk);
		return CenterUnitUtil.instance.isCorpCenter(corpPk);
	}

	/**
	 * 
	 * <p>
	 * 判断当前公司是否是结算单位
	 * <p>
	 * 作者：lpf 日期：2007-11-28
	 * 
	 * @param corpPk
	 * @return
	 */
	public static boolean isSettleUnit(String corpPk) {
		// try {
		// SettleunitHeaderVO[] headVos =
		// OuterProxy.getSettleUnitQryService().getSettleUnitByEntityPk(corpPk,
		// ISettleUnitConst.ENTTITY_TYPE_CORP);
		// if (!CommonUtil.isNull(headVos)) {
		//
		// return true;
		// }
		// } catch (BusinessException e) {
		// Logger.error(e.getMessage(), e);
		// }
		//
		// return false;
		String pk_corp = ClientEnvironment.getInstance().getCorporation().getPk_corp();
		return CenterUnitUtil.instance.isCorpCenter(pk_corp);

	}

	/**
	 * 根据客商pk查公司pk
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public static String queryCorpByCust(String pk_cust)
			throws BusinessException {
		ICustBasDocQuery cusQuery = (ICustBasDocQuery) NCLocator.getInstance().lookup(ICustBasDocQuery.class.getName());
		CubasdocVO custBasVO = cusQuery.queryCustBasDocVOByPK(pk_cust);
		return null == custBasVO ? null
				: ((CustBasVO) custBasVO.getParentVO()).getPk_corp1();
	}

	/**
	 * 根据结算单位，账户取账户对应的VO 拆分出来做公用方法
	 * 
	 * @return
	 */
	public static CurrtypeVO getCurrTypeVO(String pk_accid)
			throws BusinessException {
		StringBuffer whereClause = new StringBuffer().append(" bd_currtype.pk_currtype = ").append(" (select e.pk_currtype from bd_accid e where ").append(" e.pk_accid='").append(pk_accid).append("')");
		SuperVO[] vos = null;
		try {
			vos = HYPubBO_Client.queryByCondition(CurrtypeVO.class, whereClause.toString());
		} catch (UifException e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000155")/*
																								 * @res
																								 * "根据结算单位和账户查询账户币种异常"
																								 */);
		}
		if (vos == null || vos.length == 0) {
			throw new BusinessException(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000156")/*
																								 * @res
																								 * "根据结算单位和账户无法查询到账户对应币种"
																								 */);
		}
		return (CurrtypeVO) vos[0];
	}

	/**
	 * 通过校验受理情况，确认操作按钮是否可见
	 * 
	 * @param centercorpid
	 * @param date
	 */
	public static boolean isMakeBtnVisible() {
		if (!isSettleCenter()) {
			return true;// 结算单位按钮可见
		}

		ICommon icom = NCLocator.getInstance().lookup(ICommon.class);
		String pk_corp = ClientEnvironment.getInstance().getCorporation().getPrimaryKey();
		UFDate date = ClientEnvironment.getInstance().getDate();
		try {
			return icom.isStartProcess(pk_corp, date).booleanValue();
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
		}
		return true;
	}

	/**
	 * 
	 * 是否已受理，为解决 内部托管、调剂清算单、调剂出库、内部领用 多次调用 isMakeBtnVisible()产生多次远程调用的性能问题
	 * 
	 * @return
	 */
	public static UFBoolean isStartProcess() {
		return new UFBoolean(isMakeBtnVisible());
	}

}