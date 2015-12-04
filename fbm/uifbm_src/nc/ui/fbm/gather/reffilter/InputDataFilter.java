package nc.ui.fbm.gather.reffilter;

import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

public class InputDataFilter extends BillItemRefModelFilter{

	@Override
	protected String getSelfFilterString() {
		// TODO Auto-generated method stub
		return "v_fbm_bankaccbas.pk_corp='FBMC'";
	}

}
