package nc.ui.fbm.endore.checker;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillCardPanel;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.pub.lang.UFDate;

public class EndoreMoneyChecker extends AbstractUIChecker {

	public EndoreMoneyChecker() {
		// TODO Auto-generated constructor stub
	}

	public EndoreMoneyChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String check() {
		BillCardPanel panel= getUI().getBillCardPanel();
		//��������
		String busdate = (String) panel.getHeadItem(EndoreVO.BUSDATE).getValueObject();
		//��Ʊ����
		String invoicedate = (String) panel.getHeadItem(EndoreVO.INVOICEDATE).getValueObject();
		//��ǰ����
		UFDate currentdate = ClientEnvironment.getInstance().getDate();

		//��������
		String enddate = (String) panel.getHeadItem(EndoreVO.ENDDATE).getValueObject();

		if ("".equals(busdate) || busdate == null) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000021")/*@res "�������ڲ���Ϊ��"*/;
		}
		
		
		
		if("".equals(invoicedate)||invoicedate==null)
		{
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000022")/*@res "Ʊ�ݳ�Ʊ���ڲ���Ϊ�գ�"*/;
		}

		if("".equals(enddate)||enddate==null)
		{
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000023")/*@res "Ʊ�ݵ������ڲ���Ϊ�գ�"*/;
		}
//20091106̩����ֽҪ��ȥ�������ƣ����ڵ����ᱳ������
//		if(new UFDate(busdate).before(new UFDate(invoicedate))||new UFDate(busdate).after(new UFDate(enddate))){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000024")/*@res "��������Ҫ���ڵ��ڳ�Ʊ���ڣ�С�ڵ��ڵ�������"*/;
//		}
		String endorsee = (String) getUI().getBillCardPanel().getHeadItem(
				EndoreVO.ENDORSEE).getValueObject();
		if("".equals(endorsee)||endorsee == null)
		{
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000025")/*@res "�����鵥λ����Ϊ��"*/;
		}
		
		UFDate dbusdate = new UFDate(busdate);
		
		if(currentdate!=null && dbusdate!=null && currentdate.before(dbusdate)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000007")/*@res "��������ӦС�ڵ��ڵ�ǰ��½����"*/;
		}


		return null;
	}

}