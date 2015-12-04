package nc.ui.fbm.discountcalculate;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.register.RegisterVO;

public class DiscountCalculateRefTakenProccessor extends RefTakenProccessor{
	public DiscountCalculateRefTakenProccessor(BillListPanel listpanel,
			BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
	}

	@Override
	protected ITakenColUtil createTakenColUtil() {
		return new AbstractTakenColUtil(){
			protected RefTakenVO[] getRefTakenVOs() {
				return new RefTakenVO[]{
					new RefTakenVO("fbm_register",RegisterVO.PK_REGISTER,
							new String[]{RegisterVO.REGISTERSTATUS, RegisterVO.ENDDATE, RegisterVO.HOLDUNIT, RegisterVO.PAYUNIT, RegisterVO.PAYBANKACC, RegisterVO.INVOICEDATE, RegisterVO.PK_CURR, RegisterVO.FBMBILLNO, RegisterVO.RECEIVEUNIT, RegisterVO.RECEIVEBANKACC, RegisterVO.INVOICEUNIT,RegisterVO.FBMBILLTYPE,RegisterVO.SFFLAG, RegisterVO.DISABLE,RegisterVO.ACCEPTANCENO , RegisterVO.CONTRACTNO, RegisterVO.DEPT },
							new String[]{RegisterVO.REGISTERSTATUS, RegisterVO.ENDDATE, RegisterVO.HOLDUNIT, RegisterVO.PAYUNIT,RegisterVO.PAYBANKACC,  RegisterVO.INVOICEDATE, RegisterVO.PK_CURR, RegisterVO.FBMBILLNO, RegisterVO.RECEIVEUNIT,RegisterVO.RECEIVEBANKACC, RegisterVO.INVOICEUNIT ,RegisterVO.FBMBILLTYPE,RegisterVO.SFFLAG, RegisterVO.DISABLE,RegisterVO.ACCEPTANCENO , RegisterVO.CONTRACTNO , RegisterVO.DEPT })
				};
		    }
		};
	}
}
