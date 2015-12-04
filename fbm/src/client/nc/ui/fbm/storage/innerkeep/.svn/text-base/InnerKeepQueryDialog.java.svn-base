/**
 * 
 */
package nc.ui.fbm.storage.innerkeep;

import java.awt.Container;

import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.querytemplate.normalpanel.INormalQueryPanel;
import nc.vo.querytemplate.TemplateInfo;

/**
 * @author lpf
 *
 */
public class InnerKeepQueryDialog extends RefTakenQueryConditionClient {
	private InnerKeepUI ui;
	private INormalQueryPanel panel;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param parent
	 */
	public InnerKeepQueryDialog(InnerKeepUI parent) {
		super(parent);
		this.ui = parent;
		registerCriteriaEditorListener(new InnerKeepDialogRefFilter(ui));
	}

	/**
	 * @param parent
	 * @param normalPnl
	 */
	public InnerKeepQueryDialog(InnerKeepUI parent, INormalQueryPanel normalPnl) {
		super(parent, normalPnl);
		this.ui = parent;
		registerCriteriaEditorListener(new InnerKeepDialogRefFilter(ui));
	}

	/**
	 * @param parent
	 * @param ti
	 */
	public InnerKeepQueryDialog(InnerKeepUI parent, TemplateInfo ti) {
		super(parent, ti);
		this.ui = parent;
		registerCriteriaEditorListener(new InnerKeepDialogRefFilter(ui));
	}

	/**
	 * @param parent
	 * @param normalPnl
	 * @param ti
	 */
	public InnerKeepQueryDialog(Container parent, INormalQueryPanel normalPnl,
			TemplateInfo ti) {
		super(parent, normalPnl, ti);
	}

	/**
	 * @param parent
	 * @param ti
	 * @param title
	 */
	public InnerKeepQueryDialog(Container parent, TemplateInfo ti, String title) {
		super(parent, ti, title);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parent
	 * @param normalPnl
	 * @param ti
	 * @param title
	 */
	public InnerKeepQueryDialog(Container parent, INormalQueryPanel normalPnl,
			TemplateInfo ti, String title) {
		super(parent, normalPnl, ti, title);
		// TODO Auto-generated constructor stub
	}

	
}
