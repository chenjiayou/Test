/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.table.TableColumnModel;

import nc.ui.pub.beans.UITable;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillSortListener;
import nc.ui.trade.bsdelegate.BusinessDelegator;
import nc.ui.trade.card.BillCardUI;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.bill.BillRendererVO;

/**
 * <p>
 * ���ϼ��еĿ�ƬUI������
 * <p>
 * �������ڣ�2006-9-19
 * 
 * @author lisg
 * @since v5.0
 */
public abstract class TotalRowBillCardUI extends BillCardUI implements BillSortListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-26
	 *
	 * @see nc.ui.trade.card.BillCardUI#createBusinessDelegator()
	 */
	@Override
	protected BusinessDelegator createBusinessDelegator() {
		if(getUIControl().getBusinessActionType() == nc.ui.trade.businessaction.IBusinessActionType.BD){
			return new TotalRowBusinessDelegator(getUIControl());
		}else{
			return new BusinessDelegator();
		}
	}

	Hashtable<String, Object> totalHash = new Hashtable<String, Object>();

	/**
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-19
	 *
	 * @see nc.ui.pub.bill.BillSortListener#afterSort(java.lang.String)
	 */
	public void afterSort(String key) {
		//���Controller������Ч�����������,ʲôҲ����
		if(!TotalRowUITools.isLegalTotalController(getUIControl())) return;
		
		Vector v = getBillCardPanel().getBillModel().getDataVector();
		v = OrganizeTotalVector(v);
		getBillCardPanel().getBillModel().setDataVector(v);
	}
	
	/**
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-19
	 *
	 * @see nc.ui.trade.card.BillCardUI#initEventListener()
	 */
	protected void initEventListener() {
		super.initEventListener();
		getBillCardPanel().getBillModel().addSortListener(this);//ע���������
	}
	
	/**
	 * <p>
	 * ���غϼƷ��ദ�������,���������Ǹ�����ʱ,����null
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-19
	 * @return
	 */
	public ITotalSpecify getTotalSpecifyController(){
		if(getUIControl() instanceof ITotalSpecify){
			return (ITotalSpecify)getUIControl();
		}
		
		return null;
	}
	
	/**
	 * <p>
	 * �жϵ�ǰvector�Ƿ��Ǻϼ���
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-19
	 * @param v
	 * @return
	 */
	protected boolean isTotalRow(Vector v){
		
		if(!TotalRowUITools.isLegalTotalController(getUIControl())) return false;
		
		String classname = getBodyVOName();
		
		ITotalSpecify ts = getTotalSpecifyController();
		
		TotalRowPara trp = ts.getTotalRowPara(classname);
		
		if(trp == null) return false;
		
		Object Flag = getValueByKey(v,trp.getDistinctColName());
		
		if(Flag == null || !(Flag instanceof String)) return false;
		
		if(trp.getKeyWithFlag().containsKey((String)Flag)){
			return true;
		}
		return false;
	}
	
	/**
	 * <p>
	 * �жϵ�ǰindex�Ƿ��Ǻϼ���
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-20
	 * @param index
	 * @return
	 */
	public boolean isTotalRow(int index){
		Vector v = getBillCardPanel().getBillModel().getDataVector();
		if(index < 0 || index+1 > v.size()) return false;
		
		return isTotalRow((Vector)v.get(index));
	}
	
	
	/**
	 * <p>
	 * ������֯������Vector����,���ϼ���׼ȷ�Ĳ�����ȷ��λ��
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-19
	 * @param v
	 * @return
	 */
	private Vector<Vector> OrganizeTotalVector(Vector<Vector> v){
		
		if(v == null || v.size() == 0 || !TotalRowUITools.isLegalTotalController(getUIControl())) return v;
		
		HashMap<String,Vector<Vector>> specifyList = new HashMap<String,Vector<Vector>>();
		HashMap<String, Vector> totalList = new HashMap<String, Vector>();
		
		//��hashtable����key���ֵ�˳��
		HashMap<Integer,Object> squenceList = new HashMap<Integer,Object>();
		//��ʼ����key����
		int squenceindex = 0;
		
		String classname = getBodyVOName();
		
		ITotalSpecify ts = getTotalSpecifyController();
		
		TotalRowPara trp = ts.getTotalRowPara(classname);
		
		if(trp == null) return v;
		
		String flagitem = trp.getSpecifyFeild();
		String distinckey = trp.getDistinctColName();
		
		for(int index = 0; index < v.size(); index++){
			Vector v1 = v.get(index);//���һ������
			Object key = getValueByKey(v1, flagitem);
			String str_key = "";
			if(key == null){
				str_key = "";
			}else{
				str_key = key.toString();
			}
			
			if(!isTotalRow(v1)){
				if(!squenceList.containsValue(key)){
					//��ʼ��squenceList�б�,ÿһ��keyֻ�ܱ����һ��
					squenceList.put(squenceindex,key);
					//Ϊ��һ����ͬ��key��׼��
					squenceindex++;
					
					specifyList.put(str_key, new Vector<Vector>());
				}
				
				specifyList.get(str_key).add(v1);
			}else{
				//�ϼ���,���ϲ��а�������Flag��key�洢��hashtable��
				String Flag = (String)getValueByKey(v1,distinckey);
				totalList.put((String)trp.getKeyWithFlag().get(Flag), v1);
			}
		}
		
		//������֯Vector������
		Vector<Vector> v_ret = new Vector<Vector>();
		
		for(int index = 0; index < squenceindex; index++){
			Object o_key = squenceList.get(index);
			String str_key = "";
			if(o_key == null){
				str_key = "";
			}else{
				str_key = o_key.toString();
			}
			v_ret.addAll(specifyList.get(str_key));
			v_ret.add(totalList.get(str_key));
		}
		
		return v_ret;
	}
	
	protected Object getValueByKey(Vector v, String key){
		
		int index = getBillCardPanel().getBodyColByKey(key);
		if(index == -1) return null;
		
		return v.get(index);
	}

	/**
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-20
	 *
	 * @see nc.ui.trade.card.BillCardUI#beforeEdit(nc.ui.pub.bill.BillEditEvent)
	 */
	public boolean beforeEdit(BillEditEvent e) {
		if(isTotalRow((Vector)getBillCardPanel().getBillModel().getDataVector().get(e.getRow()))){
			return false;
		}
		return true;
	}
	
	/**
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-20
	 *
	 * @see nc.ui.trade.base.AbstractBillUI#initSelfData()
	 */
	protected void initSelfData() {
		initRender();
	}
	
	protected void initRender(){
		UITable t = getBillCardPanel().getBillTable();
		BillModel bm = getBillCardPanel().getBillModel();
		TableColumnModel tm = t.getColumnModel();
		BillItem[] bis = getBillCardPanel().getBodyItems();
		BillRendererVO rendervo = new BillRendererVO();
		rendervo.setShowThMark(true);
		for(BillItem bi : bis){
			if(bi.isShow() == false || bi.getKey().equalsIgnoreCase(RegisterVO.CHOICE)){
				continue;
			}else{
				int col_index = t.convertColumnIndexToView(bm.getBodyColByKey(bi.getKey()));
				if(!TotalRowUITools.isLegalTotalController(getUIControl())){
					tm.getColumn(col_index).setCellRenderer(new TotalRowTableCellColorRender(bm,null,getDistinctColIndex(),bi,rendervo));
				}else{
					String classname = getBodyVOName();
					ITotalSpecify ts = getTotalSpecifyController();
					TotalRowPara trp = ts.getTotalRowPara(classname);
					
					tm.getColumn(col_index).setCellRenderer(new TotalRowTableCellColorRender(bm,trp,getDistinctColIndex(),bi,rendervo));
				}
			}
		}
	}
	
	public int getDistinctColIndex(){
		if(!TotalRowUITools.isLegalTotalController(getUIControl())) return -1;
		
		String classname = getBodyVOName();
		
		ITotalSpecify ts = getTotalSpecifyController();
		
		TotalRowPara trp = ts.getTotalRowPara(classname);
		
		if(trp == null) return -1;
		
		String key = trp.getDistinctColName();
		
		int index = getBillCardPanel().getBodyColByKey(key);
		
		return index;
	}
	
	/**
	 * <p>
	 *
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-27
	 * @return
	 */
	protected String getBodyVOName() {
		String classname = getUIControl().getBillVoName()[2];
		return classname;
	}
}
