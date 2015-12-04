/**
 *
 */
package nc.ui.fbm.pub;

//import nc.ui.fbm.pub.itemmanager.IBillItemManager;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.IBillItem;

/**
 * ����˵����
 * <b>�������ڣ�2007-8-13
 * <b>�� �� �ˣ�lpf
 * <b>����������
 * <b>��    ����
 * <b>
 */
public abstract class AbstractItemEditListener implements IItemEditListener {
	private String itemKey = null;
	private int pos = IBillItem.HEAD;
	private FBMManageUI ui = null;
//	private IBillItemManager itemmanager=null;
	/**
	 * 
	 */
	public AbstractItemEditListener() {
		// TODO Auto-generated constructor stub
	}
	
	public AbstractItemEditListener(FBMManageUI ui,String itemKey) {
		// TODO Auto-generated constructor stub
		this.ui=ui;
		this.itemKey=itemKey;
	}		
	
	public AbstractItemEditListener(FBMManageUI ui,String itemKey,int pos) {
		// TODO Auto-generated constructor stub
		this.ui=ui;
		this.itemKey=itemKey;
		this.pos=pos;
	}	

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.IBillItemEditListener#getItemKey()
	 */
	public String getItemKey() {
		// TODO Auto-generated method stub
		return itemKey;
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.IBillItemEditListener#getPos()
	 */
	public int getPos() {
		// TODO Auto-generated method stub
		return pos;
	}
	
	public FBMManageUI getUI(){
		return ui;
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.IBillItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	public abstract void responseEditEvent(BillEditEvent editEvent) ;

//	/**
//	 * 
//	 * <p>
//	 *��ע�ᵥ��item������
//	 * <p>
//	 * ���ߣ�lpf
//	 * ���ڣ�2007-8-15
//	 */
//	public IBillItemManager registerItemManager(){
//		return null;
//	}
//	
//	private IBillItemManager getBillItemManager(){
//		if(itemmanager==null)
//			itemmanager=registerItemManager();
//		return itemmanager;
//	}
}
