package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.tm.framework.ref.filter.RefDataFilter;
import nc.ui.tm.framework.ref.filter.RefModelFilter;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.fbm.proxy.OuterProxy;

/**
 * 
 * �๦��˵���� ȡ�ڲ����̵������� ���ڣ�2007-11-27 ����Ա�� wues
 * 
 */
public class ReliefSubCusRefModel extends ReckonInnerCusDocRefModel {

	public ReliefSubCusRefModel() {
		super();
	}

	public ReliefSubCusRefModel(Container con) {
		super(con);

		// this.addSqlFilter(new );
	}

	public String getWherePart() {
		String sqlClause = "";
		if (FBMClientInfo.isSettleCenter(ClientInfo.getCorpPK())) {// ����
			sqlClause = " ( bd_settlecenter.pk_settlecenter = '"
					+ OuterProxy.getSettleCenter().getSettleCenterByCorpPk(
							ClientInfo.getCorpPK()).getPrimaryKey() + "'";
			sqlClause += " and ( bd_cubasdoc.pk_corp1 !=  '"
					+ ClientInfo.getCorpPK() + "') ";
		} else {
			sqlClause += "(1=1 ";
		}
		// } else {
		// sqlClause = "( bd_cubasdoc.pk_corp1 = '" + ClientInfo.getCorpPK() +
		// "'";
		// }
		// //���ܽ�Ʊ�ݵ������Լ�
		// //���ӹ���������ѡ��λʱ�����Լ�ѡ��
		// sqlClause += " and ( bd_cubasdoc.pk_corp1 != '" +
		// ClientInfo.getCorpPK() + "') ";

		sqlClause += m_strWherePart;
		return sqlClause;
	}
}
