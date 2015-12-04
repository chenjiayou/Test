package nc.bs.fbm.pub;

import nc.bs.framework.common.NCLocator;
import nc.bs.trade.business.HYPubBO;
import nc.itf.uap.bd.ISettleCenter;
import nc.itf.uap.bd.ISettleUnitQry;
import nc.itf.uap.bd.cust.ICuBasDocQry;
import nc.vo.bd.b08.CubasdocVO;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.bd.b203.SettleunitHeaderVO;
import nc.vo.bd.settle.SettlecenterVO;
import nc.vo.pub.BusinessException;

/**
 * Ʊ����ػ���������ѯ��
 * 
 * @author xwq
 * 
 *         2008-9-16
 */
public class FbmBDQueryDAO {

	/**
	 * ���ݳ�Ա��λPK��������ϼ��ʽ���֯VO
	 * 
	 * @param pk_corp
	 * @return
	 */
	public SettlecenterVO retriveSettleCenterBySubCorp(String pk_corp)
			throws BusinessException {
		ISettleUnitQry unitSrv = (ISettleUnitQry) NCLocator.getInstance().lookup(ISettleUnitQry.class);
		SettleunitHeaderVO[] unitVos = (SettleunitHeaderVO[]) (new HYPubBO()).queryByCondition(SettleunitHeaderVO.class, "pk_corp1='"
				+ pk_corp
				+ "'");
		if (unitVos == null || unitVos.length == 0) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000152")/*
																										 * @res
																										 * "�Ҳ�����Ա��λ"
																										 */);
		}
		if (unitVos.length > 1) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000153")/*
																										 * @res
																										 * "һ����˾��Ӧ�����Ա��λ"
																										 */);
		}

		String pk_settlecent = unitVos[0].getPk_settlecent();
		ISettleCenter centerSrv = (ISettleCenter) NCLocator.getInstance().lookup(ISettleCenter.class);
		SettlecenterVO centVO = centerSrv.getSettleCenterByPk(pk_settlecent);
		if (centVO == null) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000154")/*
																										 * @res
																										 * "�Ҳ�����Ӧ���ʽ���֯"
																										 */);
		}
		return centVO;
	}

	/**
	 * ���ݿ��̻�������PK�õ���˾PK
	 * 
	 * @param pk_cubasdoc
	 * @return
	 * @throws BusinessException
	 */
	public String retriveCorpByPk_cubasdoc(String pk_cubasdoc)
			throws BusinessException {
		ICuBasDocQry cubasdocQry = (ICuBasDocQry) NCLocator.getInstance().lookup(ICuBasDocQry.class);
		CubasdocVO cubasdocVO = cubasdocQry.findCubasdocVOByPK(pk_cubasdoc);
		return ((CustBasVO) cubasdocVO.getParentVO()).getPk_corp1();
	}

	/**
	 * ���ݹ�˾PK�õ����̻�������PK
	 * 
	 * @param pk_corp1
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	public String retriveCubasdocByPk_corp(String pk_corp1, String pk_corp)
			throws BusinessException {
		ICuBasDocQry cubasdocQry = (ICuBasDocQry) NCLocator.getInstance().lookup(ICuBasDocQry.class);
		CustBasVO cubasVO = cubasdocQry.getInnerCustByPkCorp1(pk_corp1, pk_corp);
		return cubasVO.getPrimaryKey();
	}

}