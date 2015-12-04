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
 * ��Ʊ���ںϷ���У��
 * <p>
 * �����ˣ�lpf
 * <b>���ڣ�2007-9-3
 * ˵������Ʊ��Ʊ���ڱ�����ڿ�Ʊ���ڣ�С�ڵ������ڡ�
 * 		������Ʊ���ڱ�����ڿ�Ʊ���ڣ�����ʵ��ҵ������.С�ڵ������ڡ�
 * 		������Ʊ���ڱ�����ڵ������ڡ�
 * 		��Ʊ��Ʊ���ڱ�����ڿ�Ʊ���ڣ�С�ڵ������ڡ�
 * �����ȥ��С�ڵ������ڵ����� xwq
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
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000115")/*@res "��Ʊ���ڲ��ܴ��ڵ�ǰ��¼����"*/;
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
//						return nc.ui.ml.NCLangRes.getInstance().getStrByID("362025","UPP362025-000000")/* @res"��Ʊ����Ӧ�ô��ڳ�Ʊ���ڣ�"*/;
				}
			}
		}
		return null;
	}

}