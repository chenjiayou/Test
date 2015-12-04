/**
 *
 */
package nc.ui.fbm.returnbill.checker;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillCardPanel;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.lang.UFDate;

/**
 * <p>
 * 退票日期合法性校验
 * <p>
 * 创建人：lpf
 * <b>日期：2007-9-3
 * 说明：收票退票日期必须大于开票日期，小于到期日期。
 * 		背书退票日期必须大于开票日期，大于实际业务日期.小于到期日期。
 * 		贴现退票日期必须大于到期日期。
 * 		开票退票日期必须大于开票日期，小于到期日期。
 * 变更：去掉小于到期日期的限制 xwq
 *
 */
public class ReturnDateValidChecker extends AbstractUIChecker {

	/**
	 *
	 */
	public ReturnDateValidChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public ReturnDateValidChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nc.ui.fbm.pub.AbstractUIChecker#check()
	 */
	@Override
	public String check() {
		boolean normalchek = false;
		BillCardPanel panel = getUI().getBillCardPanel();
		String returnType = (String) panel.getHeadItem(ReturnVO.RETURNTYPE)
				.getValueObject();
		UFDate returnDate = new UFDate((String) panel.getHeadItem(
				ReturnVO.DRETURNDATE).getValueObject());
		UFDate currentDate = getUI()._getDate();

		if(returnDate.after(currentDate)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000115")/*@res "退票日期不能大于当前登录日期"*/;
		}
		if (returnType.equalsIgnoreCase(FbmBusConstant.BACK_TYPE_GATHER)
				|| returnType.equalsIgnoreCase(FbmBusConstant.BACK_TYPE_ENDORE)
				|| returnType
						.equalsIgnoreCase(FbmBusConstant.BACK_TYPE_INVOICE)) {
			normalchek = true;
		}
		int row = panel.getBillData().getBillModel().getRowCount();
		for (int i = 0; i < row; i++) {
			UFDate invoiceDate;
			UFDate enddate;
			Object invoiceDateObj = panel.getBillData().getBillModel(ReturnBVO.tablecode).getValueAt(i, ReturnBVO.INVOICEDATE);
			if(invoiceDateObj!=null&&invoiceDateObj instanceof String){
				invoiceDate = new UFDate((String)invoiceDateObj);
			}else{
				invoiceDate = (UFDate) invoiceDateObj;
			}
			Object enddateobj = panel.getBillData().getBillModel(ReturnBVO.tablecode).getValueAt(i, ReturnBVO.ENDDATE);
			if(enddateobj!=null&&enddateobj instanceof String){
				enddate = new UFDate((String)enddateobj);
			}else{
				enddate = (UFDate) enddateobj;
			}

			if(normalchek){
				if(enddate!=null){
//					if(returnDate.before(invoiceDate))
//						return nc.ui.ml.NCLangRes.getInstance().getStrByID("362025","UPP362025-000000")/* @res"退票日期应该大于出票日期！"*/;
				}
			}
		}
		return null;
	}

}