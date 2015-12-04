package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

//备查簿自定义查询参数参照专用
public class QEBCBCuBasDocRefModel extends QECuBasDocRefModel {

	
	public QEBCBCuBasDocRefModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QEBCBCuBasDocRefModel(Container con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getFuncode() {
		// TODO Auto-generated method stub
		return "0001ZZ1000000000GYCE";//备查簿
	}

	
}
