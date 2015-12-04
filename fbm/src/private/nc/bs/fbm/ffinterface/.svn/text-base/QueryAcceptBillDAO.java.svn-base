/*
 * @(#)QueryAcceptBillDAO.java 2008-9-22
 * Copyright 2008 UFIDA Software CO.LTD. All rights reserved.
 */
package nc.bs.fbm.ffinterface;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.naming.NamingException;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.logging.Logger;
import nc.bs.pub.DataManageObject;
import nc.itf.ff.enumerate.Flowdirection;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.ff.inter.FFInterfaceHeaderVO;
import nc.vo.ff.inter.FFInterfaceItemVO;
import nc.vo.ff.inter.FFInterfaceVO;
import nc.vo.ff.reason.Arithmetic;
import nc.vo.ff.reason.FormulaCondVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * TODO 简要说明
 * 
 * <p>
 * 
 * @author wangxf
 * @version 1.0 2008-9-22
 * @since NC5.5
 */
public class QueryAcceptBillDAO extends DataManageObject {

	public QueryAcceptBillDAO() throws NamingException {
		super();
	}

	public QueryAcceptBillDAO(String dbName) throws NamingException {
		super(dbName);
	}

	/***************************************************************************
	 * <br>
	 * Created on 2005-3-7 10:10:56</br>
	 * 
	 * @param formulaconditionvo
	 * @return UFDate[][]
	 * @throws Exception
	 **************************************************************************/
	protected UFDate[][] getForecastPeriod(FormulaCondVO formulaconditionvo)
			throws BusinessException {
		UFDate[][] ufDatePeriods = null;

		// 是否包含未定期间
		if (formulaconditionvo.getBunknowable().booleanValue()) {
			UFDate[][] temp = formulaconditionvo.getArrPeriodDatas();
			ufDatePeriods = new UFDate[temp.length + 1][];
			ufDatePeriods[0] = formulaconditionvo.getArrunknowperiod();

			for (int i = 0; i < temp.length; i++) {
				ufDatePeriods[i + 1] = temp[i];
			}
		} else {
			ufDatePeriods = formulaconditionvo.getArrPeriodDatas();
		}

		return ufDatePeriods;
	}

	/***************************************************************************
	 * <br>
	 * Created on 2005-3-7 14:45:13</br>
	 * 
	 * @param formulaCondVO
	 * @param strPjfx
	 * @return ArrayList
	 * @throws Exception
	 **************************************************************************/
	public ArrayList getInterfaceVOs(FormulaCondVO formulaCondVO, int strPjfx)
			throws BusinessException {
		// 查询期间段
		Vector vResultvos1 = getReturnVOs(formulaCondVO, strPjfx);

		UFDate ufDatePeriods[][] = getForecastPeriod(formulaCondVO);

		// 未定期间段
		UFDate sDstartdate = formulaCondVO.getDstartdate();
		if (sDstartdate != null) {
			sDstartdate = sDstartdate.getDateBefore(1);
			formulaCondVO.setDstartdate(null);
			formulaCondVO.setDenddate(sDstartdate);
			Vector vResultvos2 = getReturnVOs(formulaCondVO, strPjfx);
			if (vResultvos2 != null) {
				if (vResultvos1 == null)
					vResultvos1 = new Vector();
				for (int i = 0; i < vResultvos2.size(); i++) {
					vResultvos1.add(vResultvos2.get(i));
				}
			}
		}
		FFInterfaceVO[] ffVOs = new FFInterfaceVO[vResultvos1.size()];
		vResultvos1.copyInto(ffVOs);

		ArrayList arrListResult = null;
		// if (IBillType.AR.equals(strPjfx)) {
		// arrListResult = Arithmetic.calculatePeriodValue(ufDatePeriods,
		// ffVOs, Flowdirection.IN);
		// } else {
		arrListResult = Arithmetic.calculatePeriodValue(ufDatePeriods, ffVOs,
				Flowdirection.OUT);
		// }

		return arrListResult;
	}

	/***************************************************************************
	 * <br>
	 * Created on 2005-3-7 14:45:13</br>
	 * 
	 * @param formulaCondVO
	 * @param strPjfx
	 * @return ArrayList
	 * @throws Exception
	 **************************************************************************/
	private Vector getReturnVOs(FormulaCondVO formulaCondVO, int strPjfx)
			throws BusinessException {
		// String strBillType = ISysBillType.GA;

		String strSQL = "select distinct info.pk_baseinfo,"
				+ "info.fbmbillno,"
				+ "fbm_register.vbillstatus,"
				+ "info.enddate,"// --到期日期
				+ "fbm_register.dapprovedate,"// --审核日期
				+ "fbm_register.moneyy,"
				+ "fbm_register.moneyb,"
				+ "fbm_register.moneyf,"
				+ "fbm_register.pk_corp,"
				+ "bd_corp.unitcode,"
				+ "bd_corp.unitname,"
				+ "info.pk_curr, "// --原币币种
				+ "bd_currtype.currtypecode,"
				+ "bd_currtype.currtypename,"
				+ "fbm_register.holdunit,"
				+ "cuhold.custcode,"
				+ "cuhold.custname,"
				+ "fbm_register.vapproveid,"
				+ "sm_user.user_code,"
				+ "sm_user.user_name,"
				+ "fbm_register.pk_billtypecode,";
		if (strPjfx == Flowdirection.IN) {
			strSQL += "fbm_register.holdunit";// --持票单位
		} else {
			strSQL += "info.payunit ";// --付款单位
		}
		strSQL += " from fbm_baseinfo info"
				+ " inner join fbm_register on info.pk_baseinfo = fbm_register.pk_baseinfo and fbm_register.sfflag = 'Y'";
		if (strPjfx == Flowdirection.IN) {
			strSQL += " and fbm_register.pk_billtypecode = '36GA'";
		} else {
			strSQL += " and fbm_register.pk_billtypecode = '36GL'";
		}
		strSQL += " and fbm_register.vbillstatus = 1"
				+ " inner join bd_corp on fbm_register.pk_corp = bd_corp.pk_corp and bd_corp.pk_corp = '"
				+ InvocationInfoProxy.getInstance().getCorpCode()
				+ "'"
				+ " inner join bd_currtype on info.pk_curr = bd_currtype.pk_currtype"
				+ " left outer join bd_cubasdoc cuhold on fbm_register.holdunit =cuhold.pk_cubasdoc"
				+ " left outer join sm_user on fbm_register.vapproveid = sm_user.cuserid"
				+ " where info.pk_baseinfo not in ";
		if (strPjfx == Flowdirection.IN) {
			strSQL += " ( select fbm_baseinfo.pk_baseinfo "
					+ " from fbm_baseinfo"
					+ " inner join  fbm_discount on fbm_baseinfo.pk_baseinfo  = fbm_discount.pk_baseinfo"
					+ " and fbm_discount.vbillstatus = 1"
					+ " and fbm_discount.result = 'success'"
					+ " and fbm_discount.dapprovedate <= fbm_baseinfo.enddate"
					+ " union"
					+ " select fbm_baseinfo.pk_baseinfo "
					+ " from fbm_baseinfo"
					+ " inner join  fbm_collection on fbm_baseinfo.pk_baseinfo  = fbm_collection.pk_baseinfo"
					+ " and fbm_collection.vbillstatus = 1"
					+ " and fbm_collection.dapprovedate <= fbm_baseinfo.enddate"
					+ " union "
					+ " select fbm_baseinfo.pk_baseinfo "
					+ " from fbm_baseinfo"
					+ " inner join  fbm_endore on fbm_baseinfo.pk_baseinfo  = fbm_endore.pk_baseinfo"
					+ " and fbm_endore.vbillstatus = 1"
					+ " and fbm_endore.dapprovedate <= fbm_baseinfo.enddate )";
		} else {
			strSQL += " (select fbm_accept.pk_baseinfo"
					+ " from fbm_accept"
					+ " inner join fbm_baseinfo on fbm_accept.pk_baseinfo ="
					+ " fbm_baseinfo.pk_baseinfo"
					+ " where vbillstatus = 1"
					+ " and fbm_accept.dapprovedate <= fbm_baseinfo.enddate)";
		}

		// 查询公司本位币
		// Hashtable hOriCurrtype = new Hashtable();

		// if (formulaCondVO.getAmultcorps() != null
		// && formulaCondVO.getAmultcorps().length > 0) {
		// String strPk_corps = "";
		//
		// for (int i = 0; i < formulaCondVO.getAmultcorps().length; i++) {
		// if (formulaCondVO.getAmultcorps()[i] == null
		// || formulaCondVO.getAmultcorps()[i].trim().length() == 0) {
		// continue;
		// }
		// strPk_corps += ",'" + formulaCondVO.getAmultcorps()[i] + "'";
		// // 查询各公司的本位币
		// SysInitVO vo = FBMProxy.getSysInitQry().queryByParaCode(
		// formulaCondVO.getAmultcorps()[i], "BD301");
		// hOriCurrtype.put(formulaCondVO.getAmultcorps()[i], vo
		// .getPkvalue());
		// }
		//
		// if (strPk_corps.length() > 0) {
		// strPk_corps = strPk_corps.substring(1);
		// strbufSQL.append(" and a.pk_corp in(").append(strPk_corps)
		// .append(")");
		// }
		// }

		if (formulaCondVO.getMultsettles() != null
				&& formulaCondVO.getMultsettles().length > 0) {
			String strPk_setttles = "";

			for (int i = 0; i < formulaCondVO.getMultsettles().length; i++) {
				if (formulaCondVO.getMultsettles()[i] == null
						|| formulaCondVO.getMultsettles()[i].trim().length() == 0) {
					continue;
				}
				strPk_setttles += ",'"
						+ formulaCondVO.getMultsettles()[i]
						+ "'";
			}

			if (strPk_setttles.length() > 0) {
				strPk_setttles = strPk_setttles.substring(1);
				// if (Flowdirection.IN.equals(strPjfx)) {
				// strbufSQL.append(" and a.skdw in(").append(strPk_setttles)
				// .append(")");
				// } else {
				strSQL += " and fbm_register.pk_corp in("
						+ strPk_setttles
						+ ")";
				// }
			}
		}

		if (formulaCondVO.getDstartdate() != null) {
			strSQL += " and info.enddate >='"
					+ formulaCondVO.getDstartdate()
					+ "'";
		} else {// 未定期间
			strSQL += " and info.enddate <='"
					+ formulaCondVO.getDenddate()
					+ "'";
		}

		// if (formulaCondVO.getDenddate() != null) {
		// strbufSQL.append(" and a.dqrq<='").append(
		// formulaCondVO.getDenddate()).append("'");
		// }
		//
		// if (formulaCondVO.getPk_currtype() != null) {
		// strbufSQL.append(" and a.ybbz='").append(
		// formulaCondVO.getPk_currtype()).append("'");
		// }

		Connection conn = null;
		PreparedStatement stmt = null;

		Vector<FFInterfaceVO> vectResult = new Vector<FFInterfaceVO>();

		try {
			// 把结算单位全部查出放在HASHTABLE中
			// HYSuperDMO hydmo = new HYSuperDMO();
			Hashtable<String, CustBasVO> hSettleunit = new Hashtable<String, CustBasVO>();
			CustBasVO[] vos = (CustBasVO[]) OuterProxy.getCustManDocQuery()
					.queryCustBasicInfo(null, null);
			if (vos != null) {
				for (int i = 0; i < vos.length; i++) {
					hSettleunit.put(vos[i].getPrimaryKey(), vos[i]);
				}
			}

			conn = getConnection();
			stmt = conn.prepareStatement(strSQL);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int iIndex = 1;

				FFInterfaceVO ffvo = new FFInterfaceVO();
				// 组织表头数据项
				FFInterfaceHeaderVO header = new FFInterfaceHeaderVO();

				String strPk_Pjzb = rs.getString(iIndex++);
				header.setCbillid(strPk_Pjzb);

				String strDjbh = rs.getString(iIndex++);
				header.setVbillcode(strDjbh);

				header.setVbilltype("36GL");

				int iDjzt = rs.getInt(iIndex++);
				header.setIbillstatus(new Integer(iDjzt));

				String strDjrq = rs.getString(iIndex++);
				header.setDbusinessdate((strDjrq == null) ? null : new UFDate(
						strDjrq));

				String strShrq = rs.getString(iIndex++);
				header.setDauditdate((strShrq == null) ? null : new UFDate(
						strShrq));

				BigDecimal jfybje = rs.getBigDecimal(iIndex++);

				BigDecimal jfbbje = rs.getBigDecimal(iIndex++);

				BigDecimal jffbje = rs.getBigDecimal(iIndex++);

				String strPk_corp = rs.getString(iIndex++);
				header.setPk_corp(strPk_corp);

				String strUnitCode = rs.getString(iIndex++);
				header.setVunitcode(strUnitCode);

				String strUnitName = rs.getString(iIndex++);
				header.setVunitname(strUnitName);

				String strYbbz = rs.getString(iIndex++);
				header.setCorigcurrencyid(strYbbz);

				rs.getString(iIndex++);

				rs.getString(iIndex++);

				// String strBbbz = rs.getString(iIndex++);
				header.setCLocalcurrencyid(strYbbz);// 本币币种PK
				//
				// rs.getString(iIndex++);
				// rs.getString(iIndex++);

				String strCpr = rs.getString(iIndex++);// 持票人
				header.setCoperatorid(strCpr);

				String strCprCode = rs.getString(iIndex++);
				header.setVoperatorcode(strCprCode);

				String strCprName = rs.getString(iIndex++);
				header.setVoperatorname(strCprName);

				// shr
				String strPk_shr = rs.getString(iIndex++);// 审核人
				header.setCassessorid(strPk_shr);

				String strShrCode = rs.getString(iIndex++);
				header.setVassessorcode(strShrCode);

				String strShrName = rs.getString(iIndex++);
				header.setVassessorname(strShrName);

				String strBillName = rs.getString(iIndex++);
				header.setVbillname(strBillName);

				// String strSKDW = rs.getString(iIndex++);
				String strFKDW = rs.getString(iIndex++);

				// String strPk_pjfb = rs.getString(iIndex++); //票据辅表主键

				FFInterfaceItemVO body[] = new FFInterfaceItemVO[1];
				body[0] = new FFInterfaceItemVO();

				// if (IBillType.AR.equals(strPjfx)) { // 应收票据
				// // 置结算单位名称
				// SettleunitHeaderVO settleunitvo = (SettleunitHeaderVO)
				// hSettleunit
				// .get(strSKDW);
				// body[0].setPk_sourcecorp(strSKDW);
				// if (settleunitvo != null) {
				// body[0].setVsourcecorpcode(settleunitvo
				// .getSettleunitcode());
				// body[0].setVsourcecorpname(settleunitvo
				// .getSettleunitname());
				// }
				// } else {
				CustBasVO settleunitvo = (CustBasVO) hSettleunit.get(strFKDW);
				body[0].setPk_sourcecorp(strFKDW);
				if (settleunitvo != null) {
					body[0].setVsourcecorpcode(settleunitvo.getCustcode());
					body[0].setVsourcecorpname(settleunitvo.getCustname());
				}
				// }
				body[0].setVbillcode(strDjbh);
				body[0].setVbilltype("36GL");
				body[0].setVbillname(strBillName);
				body[0].setCbillid(strPk_Pjzb);
				body[0].setCbillid_b(strPk_Pjzb);

				body[0].setNorigmoney(jfybje == null ? null : new UFDouble(
						jfybje));

				body[0].setNlocalmoney(jfbbje == null ? null : new UFDouble(
						jfbbje));
				// 特殊处理本币, 因为票据单据上未处理本币，所以再此特殊处理，如果币种是公司本币，则返回原币为本币。
				// String pk_oricurrtype = (String)
				// hOriCurrtype.get(strPk_corp);
				// if (pk_oricurrtype != null
				// && pk_oricurrtype.equals(strYbbz)
				// && jfbbje == null) {
				// body[0].setNlocalmoney(body[0].getNorigmoney());
				// }

				body[0].setNassimoney(jffbje == null ? null : new UFDouble(
						jffbje));

				ffvo.setParentVO(header);
				ffvo.setChildrenVO(body);
				vectResult.addElement(ffvo);
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				throw (BusinessException) e;
			} else {
				throw new BusinessException(e);
			}
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException ex) {
					Logger.error(ex.getMessage(),ex);
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					Logger.error(ex.getMessage(),ex);
				}
			}
		}

		// ffVOs = new FFInterfaceVO[vectResult.size()];
		// vectResult.copyInto(ffVOs);

		return vectResult;
	}
}
