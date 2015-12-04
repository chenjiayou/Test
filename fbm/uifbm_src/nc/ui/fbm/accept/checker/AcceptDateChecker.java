/**
 *
 */
package nc.ui.fbm.accept.checker;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillCardPanel;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.pub.lang.UFDate;

/**
 * <p>
 * 通知承兑日期必须小于等于结算付款日期。
 * 付款日期必须大于等于通知承兑日期
 * <p>创建人：lpf
 * <b>日期：2007-9-7
 *
 */
public class AcceptDateChecker extends AbstractUIChecker {

	/**
	 *
	 */
	public AcceptDateChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public AcceptDateChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractUIChecker#check()
	 */
	@Override
	public String check() {
		BillCardPanel panel= getUI().getBillCardPanel();
		String acceptdate=(String) panel.getHeadItem(AcceptVO.DACCEPTDATE).getValueObject();
		String acceptnotedate=(String) panel.getHeadItem(AcceptVO.DACCEPTNOTEDATE).getValueObject();
		String invoicedate = (String) panel.getHeadItem(AcceptVO.INVOICEDATE).getValueObject();
		String enddate = (String) panel.getHeadItem(AcceptVO.ENDDATE).getValueObject();
		UFDate currentDate = ClientEnvironment.getInstance().getDate();


		if(acceptdate!=null && currentDate!=null && new UFDate(acceptdate).after(currentDate)){
			return(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000000")/*@res "付款日期应小于等于当前登陆日期"*/);
		}
		if(new UFDate(acceptdate).before(new UFDate(enddate))){
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201510","UPP36201510-000000")/* @res"付款日期必须大于到期日期"*/+"\n";
		}
		// try {
		// String backaccount = (String)
		// panel.getHeadItem(AcceptVO.BACKSECACCOUNT).getValueObject();
		// if(!CommonUtil.isNull(backaccount)){
		// BankaccbasVO bankaccbasVO =
		// (BankaccbasVO)FBMProxy.getUifService().queryByPrimaryKey(BankaccbasVO.class,
		// backaccount);
		// UFDate opendadte = (UFDate)bankaccbasVO.getAccopendate();
		// if(opendadte!=null && acceptdate!=null && new
		// UFDate(acceptdate).before(opendadte) ){
		// return
		// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000000")/*@res
		// "付款日期不能早于返回保证金账户的开户日期"*/;
		// }
		// }
		// } catch (UifException e) {
		// Logger.error(e.getMessage());
		// }
		if(CommonUtil.isNull(acceptnotedate)){
			return null;
		}

		if(new UFDate(acceptdate).before(new UFDate(acceptnotedate))){
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201510","UPP36201510-000001")/* @res"通知承兑日期必须小于等于付款日期"*/+"\n";
		}
		if(new UFDate(acceptnotedate).before(new UFDate(invoicedate))||new UFDate(acceptnotedate).after(new UFDate(enddate))){
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201510","UPP36201510-000002")/* @res"通知承兑日期必须大于出票日期小于到期日期"*/+"\n";
		}

		return null;
	}

}