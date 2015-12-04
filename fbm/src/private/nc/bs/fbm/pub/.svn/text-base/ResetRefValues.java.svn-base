package nc.bs.fbm.pub;

import nc.bs.fbm.gather.FBMPubQueryDAO;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;

/**
 * 
 * 类功能说明： SQL存放常量类 日期：2007-12-25 程序员： wues
 * 
 */
public class ResetRefValues {

	/**
	 * 将给定的聚合VO中表体重新赋上携带出来的值 票据调剂表体Vo赋值
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	public static void setReliefBodyRefValues(AggregatedValueObject vo)
			throws BusinessException {
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select fbm_relief_b.pk_relief_b,fbm_relief_b.pk_source,fbm_relief_b.pk_baseinfo,fbm_relief_b.pk_relief,");
		sql.append(" fbm_baseinfo.fbmbillno fbmbillno,fbm_baseinfo.payunit,");
		sql.append(" fbm_baseinfo.receiveunit,fbm_baseinfo.pk_curr,");
		sql
				.append(" fbm_baseinfo.moneyy,fbm_baseinfo.invoicedate,fbm_register.gatherdate,fbm_baseinfo.enddate,fbm_baseinfo.invoiceunit,fbm_register.keepunit,");
		sql
				.append(" fbm_register.paybillunit,fbm_register.moneyb,fbm_register.brate, ");
		sql
				.append(" fbm_register.moneyf,fbm_register.frate,fbm_baseinfo.fbmbilltype ");
		sql.append(" from fbm_relief_b left join fbm_register ");
		sql
				.append(" on(fbm_relief_b.pk_source=fbm_register.pk_register) join fbm_baseinfo on (fbm_register.pk_baseinfo=fbm_baseinfo.pk_baseinfo) where ");

		ReliefBVO[] reliefBVO = (ReliefBVO[]) vo.getChildrenVO();
		for (int i = 0; i < reliefBVO.length; i++) {
			sql
					.append(" pk_relief_b ='" + reliefBVO[i].getPk_relief_b()
							+ "' ");
			if (i < reliefBVO.length - 1) {
				sql.append(" or ");
			}
		}
		setValues(vo, sql.toString(),"reliefbvo");
	}
	
	public static void setReturnBodyValues(AggregatedValueObject vo)
			throws BusinessException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select fbm_return_b.pk_return_b,fbm_return_b.pk_source,fbm_return_b.pk_baseinfo,fbm_return_b.pk_return,");
		sql.append(" fbm_baseinfo.fbmbillno fbmbillno,fbm_baseinfo.payunit,");
		sql.append(" fbm_baseinfo.receiveunit,fbm_baseinfo.pk_curr,");
		sql.append(" fbm_baseinfo.moneyy,fbm_baseinfo.invoicedate,fbm_register.gatherdate,fbm_baseinfo.enddate,fbm_baseinfo.invoiceunit,fbm_register.keepunit,");
		sql.append(" fbm_register.paybillunit,fbm_register.moneyb,fbm_register.brate, ");
		sql.append(" fbm_register.moneyf,fbm_register.frate,fbm_baseinfo.fbmbilltype ");
		sql.append(" from fbm_return_b left join fbm_register ");
		sql.append(" on(fbm_return_b.pk_source=fbm_register.pk_register) join fbm_baseinfo on (fbm_register.pk_baseinfo=fbm_baseinfo.pk_baseinfo) where ");

		ReturnBVO[] returnBVO = (ReturnBVO[]) vo.getChildrenVO();
		for (int i = 0; i < returnBVO.length; i++) {
			sql.append(" pk_return_b ='" + returnBVO[i].getPk_return_b() + "' ");
			if (i < returnBVO.length - 1) {
				sql.append(" or ");
			}
		}
		setValues(vo, sql.toString(), "returnbvo");
	}

	/**
	 * 将给定的聚合VO中表体重新赋上携带出来的值 调剂清算/清算回单表体Vo赋值
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	public static void setReckonBodyRefValues(AggregatedValueObject vo)
			throws BusinessException {
		StringBuffer sql = new StringBuffer();

		sql
				.append(" select fbm_reckon_b.direction,fbm_reckon_b.pk_reckon, fbm_reckon_b.pk_reckon_b,fbm_reckon_b.pk_baseinfo, fbm_reckon_b.pk_source, ");
		sql
				.append(" fbm_reckon_b.pk_detail, fbm_reckon_b.moneyy, fbm_register.moneyy fbmbillmoney, fbm_baseinfo.enddate, ");
		sql
				.append(" fbm_register.holdunit, fbm_baseinfo.payunit, fbm_register.frate,fbm_register.brate,fbm_register.moneyf,fbm_register.moneyb, fbm_baseinfo.fbmbillno fbmbillno, ");
		sql.append(" fbm_baseinfo.pk_curr,fbm_baseinfo.fbmbilltype ");
		sql.append(" from fbm_reckon_b left join fbm_register ");
		sql
				.append(" on(fbm_reckon_b.pk_source=fbm_register.pk_register) join fbm_baseinfo on (fbm_register.pk_baseinfo=fbm_baseinfo.pk_baseinfo) where ");

		ReckonBVO[] reckonBVO = (ReckonBVO[]) vo.getChildrenVO();
		for (int i = 0; i < reckonBVO.length; i++) {
			sql
					.append(" pk_reckon_b ='" + reckonBVO[i].getPk_reckon_b()
							+ "' ");
			if (i < reckonBVO.length - 1) {
				sql.append(" or ");
			}
		}
		setValues(vo, sql.toString(),"reckonbvo");
	}

	private static void setValues(AggregatedValueObject vo, String sql,String classType) throws BusinessException {
		FBMPubQueryDAO dao = new FBMPubQueryDAO();
		SuperVO[] queryVos = null;
		if("reliefbvo".equals(classType))
		{
			queryVos = dao.queryData(sql, ReliefBVO.class);
		}else if ("reckonbvo".equals(classType))
		{
			queryVos = dao.queryData(sql, ReckonBVO.class);
		} else if ("returnbvo".equals(classType)) {
			queryVos = dao.queryData(sql, ReturnBVO.class);
		}
		vo.setChildrenVO(queryVos);
	}
	
}
