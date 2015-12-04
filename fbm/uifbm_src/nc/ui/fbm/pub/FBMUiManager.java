package nc.ui.fbm.pub;

import nc.ui.glpub.UiManager;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.FramePanel;
import nc.ui.pub.linkoperate.ILinkAdd;
import nc.ui.pub.linkoperate.ILinkAddData;
import nc.uif.pub.exception.UifRuntimeException;

public class FBMUiManager extends UiManager implements ILinkAdd{

	public FBMUiManager(FramePanel panel) {
		super(panel);
		// TODO Auto-generated constructor stub
	}

	public void doAddAction(ILinkAddData adddata) {
		// TODO Auto-generated method stub
		try {
			ILinkAdd app = (ILinkAdd) getCurrentPanel();
			if(app==null)
				return;
			app.doAddAction(adddata);
		} catch (ClassCastException e) {
			throw new UifRuntimeException("current Panel does not implement ILinkMaintain");
		}	
	}

	public ButtonObject[] getButtons() {
		return getCurrentPanel().getButtons();
	}
}
