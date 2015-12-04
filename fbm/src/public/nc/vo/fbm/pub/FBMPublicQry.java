package nc.vo.fbm.pub;

import java.util.List;

import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.bd.settle.SettlecenterVO;
import nc.vo.fts.pub.CenterUnitUtil;
import nc.vo.pub.BusinessException;

/**
 * Ʊ�ݹ�����ѯ��ǰ��̨������
 * 
 * @author xwq
 * 
 *         2008-10-22
 */
public class FBMPublicQry {

	/**
	 * ���ݹ�˾PK��ý����������� �����˾�ǵ�λ����ô���ֱ���ϼ��������� �����˾�����ģ���ô����˾���ǽ�������
	 * 
	 * @param pk_corp
	 * @return
	 */
	public static String retrivePk_settlecentByPkCorp(String pk_corp)
			throws BusinessException {
		// SettlecenterVO centVO =
		// FBMProxy.getSettleCentService().getSettleCenterByCorpPk(pk_corp);
		SettlecenterVO centVO = CenterUnitUtil.instance.getSettleCenterVOByCorpPK(pk_corp);
		if (centVO != null) {
			return centVO.getPrimaryKey();
		}
		String sql = "select bd_settlecenter.pk_settlecenter from bd_settlecenter join bd_settleunit on bd_settlecenter.pk_settlecenter = bd_settleunit.pk_settlecent where bd_settleunit.pk_corp1='"
				+ pk_corp
				+ "'";

		List list = (List) FBMProxy.queryListVOByColumnListProcessor(sql, new ColumnListProcessor());
		if (list != null && list.size() > 0) {
			return (String) list.get(0);
		}
		return null;
	}

	/**
	 * ���ݹ�˾PK��ö�Ӧ���ĵĹ�˾PK
	 * 
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	public static String retrivePk_centcorpByPkCorp(String pk_corp)
			throws BusinessException {
		// SettlecenterVO centVO =
		// FBMProxy.getSettleCentService().getSettleCenterByCorpPk(pk_corp);
		SettlecenterVO centVO = CenterUnitUtil.instance.getSettleCenterVOByCorpPK(pk_corp);
		if (centVO != null) {
			return centVO.getPk_corp();
		}
		String sql = "select bd_settlecenter.pk_corp from bd_settlecenter join bd_settleunit on bd_settlecenter.pk_settlecenter = bd_settleunit.pk_settlecent where bd_settleunit.pk_corp1='"
				+ pk_corp
				+ "'";
		List list = (List) FBMProxy.queryListVOByColumnListProcessor(sql, new ColumnListProcessor());
		return (String) list.get(0);
	}

	/**
	 * ���������˺Ż�ÿ��̻�������PK
	 * 
	 * @param pk_bankaccbas
	 * @return
	 * @throws BusinessException
	 */
	public static String retrivePk_cubasdocByPk_bankaccbas(String pk_bankaccbas)
			throws BusinessException {
		String sql = "select pk_cubasdoc from bd_cubasdoc join bd_bankaccbas on bd_cubasdoc.pk_corp1=bd_bankaccbas.ownercorp where pk_bankaccbas = '"
				+ pk_bankaccbas
				+ "' ";
		Object obj = FBMProxy.queryListVOByColumnListProcessor(sql, new ColumnProcessor());
		if (obj != null) {
			return obj.toString();
		}
		// ����������
		sql = "select pk_cubasdoc from bd_custbank where pk_accbank = '"
				+ pk_bankaccbas
				+ "'";
		obj = FBMProxy.queryListVOByColumnListProcessor(sql, new ColumnProcessor());
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}
}
