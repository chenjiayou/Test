package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.bs.logging.Logger;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.storage.innerkeep.SubCustDocCondition;
import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.ref.filter.AbstractRefModelFilter;
import nc.ui.tm.framework.ref.filter.SQLRefModelFilter;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.pub.BusinessException;

/**
 * <p>
 * 实务库存表和流量明细表（自定义报表）成员单位使用
 * <p>
 * 创建人：bsrl <b>日期：2007-12-25
 * 
 */
public class QECuBasDocRefModel extends
		nc.ui.fbm.pub.refmodel.DefaultCustbasdocRefModel {

	protected String getFuncode() {
		return "0001NC100000000068JK";// 台帐
	}

	public QECuBasDocRefModel() {
		super();
		super.setSqlFilter(new SQLRefModelFilter() {
			@Override
			protected String getSelfFilterString() {
				String sqlfilter = "1=1";
				String pk_cubasdoc = null;
				String pk_corp = null;
				String pk_user = null;
				try {
					pk_cubasdoc = FBMClientInfo.getCommonCurCorpCubasdoc();
					pk_corp = FBMClientInfo.getCorpPK();
					pk_user = ClientEnvironment.getInstance().getUser().getPrimaryKey();
				} catch (BusinessException e) {
					Logger.error(e.getMessage(), e);
				}

				sqlfilter = " ( bd_cumandoc.pk_cubasdoc='"
						+ pk_cubasdoc
						+ "' or bd_cubasdoc.pk_corp1 in "
						+ "((select unit.pk_corp1 as pk_corp1 from bd_settleunit unit "
						+ " left join bd_settlecenter cent on unit.pk_settlecent = cent.pk_settlecenter "
						+ " where  cent.pk_corp = '"
						+ pk_corp
						+ "' union select pk_corp1 from bd_settleunit "
						+ " unit where unit.pk_corp1 = '"
						+ pk_corp
						+ "' union  select pk_corp from bd_corp "
						+ " where bd_corp.pk_corp = '"
						+ pk_corp
						+ "' union (select distinct "
						+ " sm_user_role.pk_corp as pk_corp from sm_user_role  inner join  "
						+ " sm_power_func on sm_user_role.pk_role = sm_power_func.pk_role where "
						+ " (sm_user_role.cuserid = '"
						+ pk_user
						+ "') and (sm_power_func.resource_data_id = "
						+ " '"
						+ getFuncode()
						+ "')) )))";
				return "(" + sqlfilter + ") and custprop<>0 ";

			}

			@Override
			protected boolean isEnabled() {

				return true;
			}

		});
	}

	public QECuBasDocRefModel(Container con) {
		super(con);
	}

	@Override
	public void setSqlFilter(AbstractRefModelFilter filter) {
		super.setSqlFilter(new SQLRefModelFilter() {
			@Override
			protected String getSelfFilterString() {
				String sqlfilter = null;
				String pk_cubasdoc = null;
				;
				try {
					pk_cubasdoc = FBMClientInfo.getCommonCurCorpCubasdoc();
				} catch (BusinessException e) {
					Logger.error(e.getMessage(), e);
				}
				if (FBMClientInfo.isSettleCenter()) {
					sqlfilter = SubCustDocCondition.getCusDocFilterContidtion();
					if (FBMClientInfo.isSettleUnit(ClientInfo.getCorpPK())
							&& pk_cubasdoc != null) {
						sqlfilter = sqlfilter
								+ " or bd_cubasdoc.pk_cubasdoc='"
								+ pk_cubasdoc
								+ "'";
					}
				} else {
					if (pk_cubasdoc != null) {
						sqlfilter = " bd_cubasdoc.pk_cubasdoc='"
								+ pk_cubasdoc
								+ "' ";
					}
				}
				return "(" + sqlfilter + ") and custprop<>0 ";
			}

			@Override
			protected boolean isEnabled() {

				return true;
			}

		});
	}

}
