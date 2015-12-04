package nc.vo.fbm.endore;

import nc.vo.pub.SuperVO;

public class EndoreBVO extends SuperVO {

	private static final long serialVersionUID = 1L;



	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return "pk_endore";
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "fbm_endore";
	}

	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return "pk_endore_b";
	}
}
