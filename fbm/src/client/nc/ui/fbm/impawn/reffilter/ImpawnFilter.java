package nc.ui.fbm.impawn.reffilter;

import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.pub.constant.FbmStatusConstant;

/**
 * 
 * �๦��˵���� 
 * 	Ʊ����Ѻ��ѡ��Ʊ�ݱ��ʱ�Ĳ��չ�������ѡ��״̬Ϊ�ѵǼǵ�Ʊ��
 * ���ڣ�2007-11-6 
 * ����Ա�� wues
 */
public class ImpawnFilter extends BillItemRefModelFilter {

	public ImpawnFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSelfFilterString() {
		return " registerstatus='" + FbmStatusConstant.REGISTER + "' and disable='N' ";
	}

}
