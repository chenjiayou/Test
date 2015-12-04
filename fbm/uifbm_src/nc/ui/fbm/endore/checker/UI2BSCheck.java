package nc.ui.fbm.endore.checker;

import java.io.Serializable;

import nc.vo.trade.pub.IBDGetCheckClass;

public class UI2BSCheck implements Serializable, IBDGetCheckClass {

	private static final long serialVersionUID = 4056141687050500375L;

	public String getCheckClass() {
		return "nc.bs.fbm.endore.action.EndoreBSCheck";
	}

}
