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
		//背书日期
		String busdate = (String) panel.getHeadItem(EndoreVO.BUSDATE).getValueObject();
		//出票日期
		String invoicedate = (String) panel.getHeadItem(EndoreVO.INVOICEDATE).getValueObject();
		//当前日期
		UFDate currentdate = ClientEnvironment.getInstance().getDate();

		//到期日期
		String enddate = (String) panel.getHeadItem(EndoreVO.ENDDATE).getValueObject();

		if ("".equals(busdate) || busdate == null) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000021")/*@res "背书日期不能为空"*/;
		}
		
		
		
		if("".equals(invoicedate)||invoicedate==null)
		{
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000022")/*@res "票据出票日期不能为空！"*/;
		}

		if("".equals(enddate)||enddate==null)
		{
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000023")/*@res "票据到期日期不能为空！"*/;
		}
//20091106泰格林纸要求去掉此限制，存在到期後背书的情况
//		if(new UFDate(busdate).before(new UFDate(invoicedate))||new UFDate(busdate).after(new UFDate(enddate))){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000024")/*@res "背书日期要大于等于出票日期，小于等于到期日期"*/;
//		}
		String endorsee = (String) getUI().getBillCardPanel().getHeadItem(
				EndoreVO.ENDORSEE).getValueObject();
		if("".equals(endorsee)||endorsee == null)
		{
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000025")/*@res "被背书单位不能为空"*/;
		}
		
		UFDate dbusdate = new UFDate(busdate);
		
		if(currentdate!=null && dbusdate!=null && currentdate.before(dbusdate)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000007")/*@res "背书日期应小于等于当前登陆日期"*/;
		}


		return null;
	}

}