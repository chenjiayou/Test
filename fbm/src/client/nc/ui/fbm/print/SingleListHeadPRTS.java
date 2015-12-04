package nc.ui.fbm.print;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.print.IDataSource;
import nc.ui.tm.framework.billtemplet.FTableCellRenderer;
import nc.vo.pub.formulaset.MoneyFormat;

/**
 * �����ڲ���ģ�����ɹ������ɵĴ�ӡģ�壬��UIԭ���ǵ���ͷ��ʽ
 * @author hzguo
 *
 */
public class SingleListHeadPRTS implements IDataSource{

	BillListPanel m_listpanel;
	String m_modulecode;
/**
 * MonnthApplyPRTS ������ע�⡣
 */
public SingleListHeadPRTS(String modulecode,BillListPanel bp) {
	super();
	m_listpanel=bp;
	m_modulecode=modulecode;
}
/**
 *
 * �õ����е���������ʽ����
 * Ҳ���Ƿ������ж����������ı��ʽ
 *
 */
public java.lang.String[] getAllDataItemExpress() {
	int headCount = 0;
	int bodyCount = 0;
	if (m_listpanel.getHeadBillModel().getBodyItems()!= null){
		headCount =m_listpanel.getHeadBillModel().getBodyItems().length;
	}
	if (m_listpanel.getBodyBillModel().getBodyItems()!= null){
		bodyCount = m_listpanel.getBodyBillModel().getBodyItems().length;
	}
	int count = headCount + bodyCount ;
	String[] expfields = new String[count];
	try{
		for (int i = 0; i < headCount; i++){
			expfields[i] = "h_"+m_listpanel.getHeadBillModel().getBodyItems()[i].getKey();
		}
		for (int j = 0; j < bodyCount ; j++){
			expfields[j+headCount] = m_listpanel.getBodyBillModel().getBodyItems()[j].getKey();
		}
	}catch (Throwable e) {
		Logger.error(e.getMessage(),e);
	}
	return expfields;
}
public java.lang.String[] getAllDataItemNames() {
	int headCount = 0;
	int bodyCount = 0;

	if (m_listpanel.getHeadBillModel().getBodyItems()!= null){
		headCount = m_listpanel.getHeadBillModel().getBodyItems().length;
	}
	if (m_listpanel.getBodyBillModel().getBodyItems()!= null){
		bodyCount = m_listpanel.getBodyBillModel().getBodyItems().length;
	}

	int count = headCount + bodyCount ;
	String[] namefields = new String[count];
	try{
		for (int i = 0; i < headCount; i++){
			namefields[i] = m_listpanel.getHeadBillModel().getBodyItems()[i].getName();
		}
		for (int j = 0; j < bodyCount ; j++){
			namefields[j + headCount] = m_listpanel.getBodyBillModel().getBodyItems()[j].getName();
		}
	}catch (Throwable e) {
		Logger.error(e.getMessage(),e);
	}
	return namefields;
}
/**
 *
 * ������������������飬���������ֻ��Ϊ 1 ���� 2
 * ���� null : 		û������
 * ���� 1 :			��������
 * ���� 2 :			˫������
 *
 */
public java.lang.String[] getDependentItemExpressByExpress(String itemName) {
	return null;
}
/*
 * �������е��������Ӧ������
 * ������ �����������
 * ���أ� �������Ӧ�����ݣ�ֻ��Ϊ String[]��

 */
public java.lang.String[] getItemValuesByExpress(String itemExpress) {

	BillModel bm = m_listpanel.getHeadBillModel();
	int rowCount=bm.getRowCount();
	BillItem item=getBillItem(itemExpress);
	UITable table = null;
    table = m_listpanel.getHeadTable();
    TableColumnModel cm = table.getColumnModel();



	if(item == null)
		return null;

	String[] retStr=new String[rowCount];
	if (itemExpress.startsWith("h_") || itemExpress.startsWith("t_")){
		itemExpress = itemExpress.substring(2);
	}
	for(int i=0;i<rowCount;i++){
		Object obj=bm.getValueAt(i,itemExpress);
		switch(item.getDataType()) {
		case IBillItem.BOOLEAN:
			if(obj==null||obj.toString().equals("false"))
			{
				retStr[i]=nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory","UPPuifactory-000165")/*@res "��"*/;
			}
			else
			{
				retStr[i]=nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory","UPPuifactory-000164")/*@res "��"*/;
			}
			break;
		case IBillItem.UFREF:
			int col = bm.getBodyColByKey(item.getKey());
			col = table.convertColumnIndexToView(col);
			if (col > -1 && col < bm.getColumnCount()) {
				TableCellRenderer render = cm.getColumn(col).getCellRenderer();
				if (render instanceof FTableCellRenderer) {
				retStr[i] = ((FTableCellRenderer) render).getShowText(obj);
					}
				} else {
					UIRefPane refpane = (UIRefPane) item.getComponent();
					refpane.setPK(obj);
					retStr[i] = refpane.getRefName();
				}
				break;
		case IBillItem.DECIMAL:
			boolean isshowTh = m_listpanel.getParentListPanel().getRendererVO().isShowThMark();
			if(obj!=null)
				retStr[i]=obj.toString();
			else
				retStr[i]="";
			if(!CommonUtil.isNull(retStr[i]) && isshowTh){
				retStr[i] = MoneyFormat.getDisplayFormat(retStr[i]);
			}
			break;
		default:
			if(obj!=null)
				retStr[i]=obj.toString();
			else
				retStr[i]="";
		}
	}
	return retStr;
}
/*
 *  ���ظ�����Դ��Ӧ�Ľڵ����
 */
public String getModuleName() {
	return m_modulecode;
}
/*
 * ���ظ��������Ƿ�Ϊ������
 * ������ɲ������㣻��������ֻ��Ϊ�ַ�������
 * �硰������Ϊ�������������롱Ϊ��������
 */
public boolean isNumber(String itemExpress) {
	BillItem item = getBillItem(itemExpress);
	if (item.getDataType() == 1 || item.getDataType() == 2){
		return true;
	} else {
		return false;
	}

}
	public BillItem getBillItem(String itemExpress){
		BillItem item = null;
		try {
			if (itemExpress.startsWith("h_")||itemExpress.startsWith("t_")){
				item = m_listpanel.getHeadItem(itemExpress.substring(2));
			}
			else {
				item=m_listpanel.getBodyItem(itemExpress);
			}
			if (item == null){
				item = m_listpanel.getHeadItem(itemExpress);
			}
			if (item == null){
				throw new RuntimeException(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000000")/* @res"�б��ӡ��֧�ֱ��岿�����ݵĴ�ӡ��"*/);
			}
		} catch (Throwable e) {
			Logger.error(e.getMessage(),e);
		}
		return item;

	}
}