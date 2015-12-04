package nc.ui.fbm.pub;

import nc.ui.pub.bill.BillEditEvent;

public interface IItemEditListener {
	/**
	 * <p>IBillItem定义
	 *	int HEAD = 0; //表头
	 *  int BODY = 1; //表体
	 *  int TAIL = 2; //表尾
	 *  对应名称
	 *  String _HEAD = "head";
	 *  String _BODY = "body";
	 *  String _TAIL = "tail";
	 * <p>
	 * 
	 * 作者：yjfeng <br>
	 * 日期：2006-11-23
	 * @return
	 */
	public int getPos();
	
	public String getItemKey();
	
	/**
	 * <p>
	 *处理编辑后事件
	 * <p>
	 * 作者：yjfeng <br>
	 * 日期：2006-11-23
	 * @param editEvent
	 */
	public void responseEditEvent(BillEditEvent editEvent);


}
