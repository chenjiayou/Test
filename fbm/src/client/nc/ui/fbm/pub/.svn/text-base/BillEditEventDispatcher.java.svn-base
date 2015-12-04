/**
 *
 */
package nc.ui.fbm.pub;

import java.util.ArrayList;
import java.util.HashMap;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.IBillItem;

/**
 * <p>
 * 1113修改支持key有多个listener
 * <p>
 * 创建日期：2006-12-16
 * 
 * @author lisg
 * @since v3.5YD
 */
public class BillEditEventDispatcher {
	private HashMap<String, ArrayList<IItemEditListener>> editListenerMap = new HashMap<String, ArrayList<IItemEditListener>>();
	private String mid = "&";

	private String getTabCode(int pos) {
		String tabCode = null;
		switch (pos) {
		case IBillItem.HEAD:
			tabCode = IBillItem._HEAD;
			break;
		case IBillItem.BODY:
			tabCode = IBillItem._BODY;
			break;
		case IBillItem.TAIL:
			tabCode = IBillItem._TAIL;
			break;
		}
		return tabCode;
	}

	private String getListenerKey(IItemEditListener editListener) {
		return getTabCode(editListener.getPos()) + mid
				+ editListener.getItemKey();
	}

	private String getEditEventKey(BillEditEvent editEvent) {
		return getTabCode(editEvent.getPos()) + mid + editEvent.getKey();
	}
	
	public void addEditListeners(IItemEditListener[] editListeners){
		if(!CommonUtil.isNull(editListeners)){				
			for(int i = 0;i < editListeners.length;i ++){
				if(editListeners[i] != null){
					if(!editListenerMap.containsKey(getListenerKey(editListeners[i]))){
						editListenerMap.put(getListenerKey(editListeners[i]), new ArrayList<IItemEditListener>());
					}
					
					ArrayList<IItemEditListener> list = editListenerMap.get(getListenerKey(editListeners[i]));
					list.add(editListeners[i]);
				}
			}
		}
	}
	
	public void responseEditEvent(BillEditEvent editEvent){
		ArrayList<IItemEditListener> list = editListenerMap.get(getEditEventKey(editEvent));
		if(list != null){
			for(int i = 0; i < list.size(); i++){
				IItemEditListener editListener = list.get(i);
				editListener.responseEditEvent(editEvent);
			}
		}
	}


}
