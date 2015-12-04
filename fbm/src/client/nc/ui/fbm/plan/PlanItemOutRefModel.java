package nc.ui.fbm.plan;


/**
 * ֧������֧��ƻ���Ŀ����
 */
import nc.ui.bd.ref.AbstractRefTreeModel;

public class PlanItemOutRefModel extends AbstractRefTreeModel {

	/**
	 * ��ʾ�ֶ��б�
	 */
	public java.lang.String[] getFieldCode() {
	    setRefCodeField("itemcode");
	    setRefNameField("itemname");
	    return new String[] { "itemcode", "itemname", "pk_planitem", "pk_corp","pk_source","pk_supersource","pk_parent","ioflag","pk_plangroup","pk_supergroup" };
	}

	/**
	 * ָʾ���¼���ϵ�������ֶΡ�
	 */
	public String getFatherField() {
		return "pk_parent";
	}

	/**
	 * ָʾ���¼���ϵ�������ֶΡ� �������ڣ�(2001-8-11 14:43:58)
	 */
	public String getChildField() {
		return "pk_planitem" ;
	}

	/**
	 * ��ʾ�ֶ�������
	 */
	public java.lang.String[] getFieldName() {
		return new String[] { nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000042")/*@res "�ƻ���Ŀ����"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000043")/*@res "�ƻ���Ŀ����"*/};
	}

	/**
	 * �����ֶ���
	 */
	public String getPkFieldCode() {
		return "pk_planitem";
	}

	/**
	 * ���ձ���
	 */
	public String getRefTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000044")/*@res "�ʽ�ƻ���Ŀ����"*/;
	}

	/**
	 * �������ݿ�������ͼ��
	 */
	public String getTableName() {

		return "fp_dim_planitem";
	}

	public String getWherePart() {
	    if (super.getWherePart() == null) {
	    	//�ƻ���Ŀ��֧����0-�գ�1-֧��2-����
	    	this.setWherePart(" pk_corp='" + getPk_corp() + "' and isnull(fp_dim_planitem.dr,0)=0 and ioflag in(1,2)");
	    }
	    return super.getWherePart();
	}

	/**
	 * ����ʾ�ֶ��б�
	 */
	public java.lang.String[] getHiddenFieldCode() {
		return new String[] {"pk_planitem", "pk_corp","pk_source","pk_supersource","pk_parent","ioflag","pk_plangroup","pk_supergroup"};
	}

}