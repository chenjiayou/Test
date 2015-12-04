package nc.ui.fbm.pub.commonref;

import nc.ui.bd.ref.AbstractRefTreeModel;
import nc.vo.ml.NCLangRes4VoTransl;


/**
 * �������Ķ�Ӧ�����е�������
 * @author xwq
 *
 * 2008-10-22
 */
public class BankDoc4SettleCentRefModel extends AbstractRefTreeModel{


	public BankDoc4SettleCentRefModel() {
		setRefNodeName();

		// TODO �Զ����ɹ��캯�����
	}

	public void setRefNodeName() {

		setRootName(NCLangRes4VoTransl.getNCLangRes().getStrByID("ref",
				"���е���")/*
								 * @res "���е���"
								 */);
		setClassFieldCode(new String[] { "banktypecode", "banktypename","bd_banktype.pk_banktype"});
		setClassJoinField("bd_banktype.pk_banktype");
		setClassTableName("bd_banktype");
		setClassCodingRule("9");
		setClassWherePart(" banktypecode='9999' ");
		//
		setFieldCode(new String[] { "bankdoccode","bankdocname","shortname","bd_bankdoc.pk_banktype" });

		setHiddenFieldCode(new String[] { "pk_bankdoc","pk_fatherbank",});
		setPkFieldCode("pk_bankdoc");
		setDocJoinField("bd_bankdoc.pk_banktype");
		setTableName("bd_bankdoc");
		setChildField("pk_bankdoc");
		setFatherField("pk_fatherbank");
//		setWherePart(" (pk_corp='"+IRefConst.GROUPCORP+"' or pk_corp='"+getPk_corp()+"')");
        resetFieldName();
        setSealedWherePart(" sealflag ='N' or sealflag is null");
        
        String strFomula = "getColValue(bd_banktype, banktypename, pk_banktype ,bd_bankdoc.pk_banktype)";
		setFormulas(new String[][] { { "bd_bankdoc.pk_banktype", strFomula } });
		//������ֱ�ȡ��
		setCompositeTreeByClassValue(true);

	}

}
