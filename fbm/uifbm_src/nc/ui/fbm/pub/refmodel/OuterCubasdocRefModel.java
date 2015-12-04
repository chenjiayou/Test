/**
 *
 */
package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

/**
 * <p>
 * 外部客商参照
 * <p>创建人：lpf
 * <b>日期：2007-8-30
 *
 */
public class OuterCubasdocRefModel extends DefaultCustbasdocRefModel {

	/**
	 *
	 */
	public OuterCubasdocRefModel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param con
	 */
	public OuterCubasdocRefModel(Container con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefNodeName()
	 */
	@Override
	public String getRefNodeName() {
		// TODO Auto-generated method stub
		return "bd_cubasdoc";
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefTitle()
	 */
	@Override
	public String getRefTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000067")/*@res "外部客商参照"*/;
	}

	@Override
	public String getWherePart() {
		return super.getWherePart()+" and bd_cubasdoc.custprop = 0 ";
	}

}