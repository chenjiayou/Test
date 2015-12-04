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
 * �����ˣ�lpf <b>���ڣ�2007-10-10
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
	 * ������������ڲ���Ͷ�ʵ�λ�����ƣ�ֻȡ����ǰ��˾��Ӧ���ڲ�����
	 * <p>
	 * ���ߣ�lpf ���ڣ�2007-8-15
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
		// * "��ǰ��˾û�����ö�Ӧ���ڲ����̣�"
		// */);
		// if (custbasVos.length > 1) {
		// throw new BusinessException(
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
		// "UPPFBMComm-000154")/*
		// * @res
		// * "��ǰ��˾���ö�Ӧ���ڲ����̶���һ���봦��"
		// */);
		// }
		// return custbasVos[0].getPk_cubasdoc();
		return ClientInfo.getCorpCuBas(getCorpPK());
	}

	/**
	 * <p>
	 * �õ���ǰ��˾PK
	 * <p>
	 * ���ߣ�lpf ���ڣ�2007-10-10
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
	 * ȡ����˾��Ӧ���ڲ�����
	 * <p>
	 * ���ߣ�lpf ���ڣ�2007-8-15
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
	 * �õ��ϼ��������Ķ�Ӧ���ڲ���������
	 * <p>
	 * ���ߣ�lpf ���ڣ�2007-10-10
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
	 * �жϵ�ǰ��˾�Ƿ��ǽ������ġ�
	 * <p>
	 * ���ߣ�qbh <br>
	 * ���ڣ�2006-2-13
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
	 * ���ݹ�˾PK�ж��Ƿ��ǽ�������
	 * <p>
	 * ���ߣ�lpf ���ڣ�2007-10-11
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
	 * �жϵ�ǰ��˾�Ƿ��ǽ��㵥λ
	 * <p>
	 * ���ߣ�lpf ���ڣ�2007-11-28
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
	 * ���ݿ���pk�鹫˾pk
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
	 * ���ݽ��㵥λ���˻�ȡ�˻���Ӧ��VO ��ֳ��������÷���
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
																								 * "���ݽ��㵥λ���˻���ѯ�˻������쳣"
																								 */);
		}
		if (vos == null || vos.length == 0) {
			throw new BusinessException(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000156")/*
																								 * @res
																								 * "���ݽ��㵥λ���˻��޷���ѯ���˻���Ӧ����"
																								 */);
		}
		return (CurrtypeVO) vos[0];
	}

	/**
	 * ͨ��У�����������ȷ�ϲ�����ť�Ƿ�ɼ�
	 * 
	 * @param centercorpid
	 * @param date
	 */
	public static boolean isMakeBtnVisible() {
		if (!isSettleCenter()) {
			return true;// ���㵥λ��ť�ɼ�
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
	 * �Ƿ�������Ϊ��� �ڲ��йܡ��������㵥���������⡢�ڲ����� ��ε��� isMakeBtnVisible()�������Զ�̵��õ���������
	 * 
	 * @return
	 */
	public static UFBoolean isStartProcess() {
		return new UFBoolean(isMakeBtnVisible());
	}

}