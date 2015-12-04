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
 * 票据相关基本档案查询类
 * 
 * @author xwq
 * 
 *         2008-9-16
 */
public class FbmBDQueryDAO {

	/**
	 * 根据成员单位PK获得它的上级资金组织VO
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
																										 * "找不到成员单位"
																										 */);
		}
		if (unitVos.length > 1) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000153")/*
																										 * @res
																										 * "一个公司对应多个成员单位"
																										 */);
		}

		String pk_settlecent = unitVos[0].getPk_settlecent();
		ISettleCenter centerSrv = (ISettleCenter) NCLocator.getInstance().lookup(ISettleCenter.class);
		SettlecenterVO centVO = centerSrv.getSettleCenterByPk(pk_settlecent);
		if (centVO == null) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000154")/*
																										 * @res
																										 * "找不到对应的资金组织"
																										 */);
		}
		return centVO;
	}

	/**
	 * 根据客商基本档案PK得到公司PK
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
	 * 根据公司PK得到客商基本档案PK
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