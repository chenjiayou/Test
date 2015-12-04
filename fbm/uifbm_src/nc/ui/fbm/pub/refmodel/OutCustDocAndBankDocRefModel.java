package nc.ui.fbm.pub.refmodel;

import nc.ui.cdm.ref.unit.DefaultCusDocRefModel;
import nc.ui.tm.framework.util.ClientInfo;

/**
 * Ʊ��ʹ�õ��ⲿ���̺����е����ϼ�����
 * 
 * @author xwq
 * 
 *         2008-11-11
 */
public class OutCustDocAndBankDocRefModel extends DefaultCusDocRefModel {
	public String getWherePart() {
		String custSQL = "( custprop=0 and unittype='CUST'   AND (custflag='0' OR custflag='1' OR custflag='2') and pk_corp= '"
				+ ClientInfo.getCorpPK()
				+ "') ";
		String bankSQL = " or (banktypecode <> '9999' and unittype='BANK')";
		return custSQL + bankSQL;
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "custcode", "custname", "custshortname",
				"unittypeName", "mnecode", "phone1", "pk_cubasdoc",
				// "bd_cumandoc.pk_cumandoc",
				"pk_corp1", "sealflag", "frozenflag", "unittype",
				"banktypecode", "custprop" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] {
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0000787")/*
																								 * @res
																								 * "��λ����"
																								 */
				,
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0000783")/*
																								 * @res
																								 * "��λ����"
																								 */
				,
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36141035", "UPP36141035-000089") /*
																										 * @res
																										 * "��λ���"
																										 */
				,
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("faccdmcode", "UPPFACCDMCODE-000025") /*
																											 * @res
																											 * "��������"
																											 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000060") /*
																												 * @res
																												 * "������"
																												 */, };
	}
}
