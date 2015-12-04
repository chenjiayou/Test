package nc.ui.fbm.endore.checker;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.vo.fbm.endore.EndoreVO;

public class EndoreSerNotEqSeeChecker extends AbstractUIChecker {

	public EndoreSerNotEqSeeChecker() {
		// TODO Auto-generated constructor stub
	}

	public EndoreSerNotEqSeeChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String check() {

		StringBuffer errors=new StringBuffer();
		String endorser =(String) getUI().getBillCardPanel().getHeadItem(EndoreVO.ENDORSER).getValueObject();
		String endorsee=(String) getUI().getBillCardPanel().getHeadItem(EndoreVO.ENDORSEE).getValueObject();
		if(endorser.equalsIgnoreCase(endorsee)){
			errors.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000026")/*@res "背书单位和被背书单位不能选择同一客商"*/);
		}
		return errors.toString();
	}

}