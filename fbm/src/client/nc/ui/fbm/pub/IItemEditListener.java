package nc.ui.fbm.pub;

import nc.ui.pub.bill.BillEditEvent;

public interface IItemEditListener {
	/**
	 * <p>IBillItem����
	 *	int HEAD = 0; //��ͷ
	 *  int BODY = 1; //����
	 *  int TAIL = 2; //��β
	 *  ��Ӧ����
	 *  String _HEAD = "head";
	 *  String _BODY = "body";
	 *  String _TAIL = "tail";
	 * <p>
	 * 
	 * ���ߣ�yjfeng <br>
	 * ���ڣ�2006-11-23
	 * @return
	 */
	public int getPos();
	
	public String getItemKey();
	
	/**
	 * <p>
	 *����༭���¼�
	 * <p>
	 * ���ߣ�yjfeng <br>
	 * ���ڣ�2006-11-23
	 * @param editEvent
	 */
	public void responseEditEvent(BillEditEvent editEvent);


}
