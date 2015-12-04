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
 * 带合计行的卡片UI见面类
 * <p>
 * 创建日期：2006-9-19
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
	 * 作者：lisg <br>
	 * 日期：2006-9-26
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
	 * 作者：lisg <br>
	 * 日期：2006-9-19
	 *
	 * @see nc.ui.pub.bill.BillSortListener#afterSort(java.lang.String)
	 */
	public void afterSort(String key) {
		//如果Controller不是有效的排序控制器,什么也不做
		if(!TotalRowUITools.isLegalTotalController(getUIControl())) return;
		
		Vector v = getBillCardPanel().getBillModel().getDataVector();
		v = OrganizeTotalVector(v);
		getBillCardPanel().getBillModel().setDataVector(v);
	}
	
	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-19
	 *
	 * @see nc.ui.trade.card.BillCardUI#initEventListener()
	 */
	protected void initEventListener() {
		super.initEventListener();
		getBillCardPanel().getBillModel().addSortListener(this);//注册排序监听
	}
	
	/**
	 * <p>
	 * 返回合计分类处理控制器,当控制器非该类型时,返回null
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-19
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
	 * 判断当前vector是否是合计行
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-19
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
	 * 判断当前index是否是合计行
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-20
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
	 * 重新组织排序后的Vector向量,将合计行准确的插入正确的位置
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-19
	 * @param v
	 * @return
	 */
	private Vector<Vector> OrganizeTotalVector(Vector<Vector> v){
		
		if(v == null || v.size() == 0 || !TotalRowUITools.isLegalTotalController(getUIControl())) return v;
		
		HashMap<String,Vector<Vector>> specifyList = new HashMap<String,Vector<Vector>>();
		HashMap<String, Vector> totalList = new HashMap<String, Vector>();
		
		//该hashtable保存key出现的顺序
		HashMap<Integer,Object> squenceList = new HashMap<Integer,Object>();
		//初始化的key索引
		int squenceindex = 0;
		
		String classname = getBodyVOName();
		
		ITotalSpecify ts = getTotalSpecifyController();
		
		TotalRowPara trp = ts.getTotalRowPara(classname);
		
		if(trp == null) return v;
		
		String flagitem = trp.getSpecifyFeild();
		String distinckey = trp.getDistinctColName();
		
		for(int index = 0; index < v.size(); index++){
			Vector v1 = v.get(index);//获得一行数据
			Object key = getValueByKey(v1, flagitem);
			String str_key = "";
			if(key == null){
				str_key = "";
			}else{
				str_key = key.toString();
			}
			
			if(!isTotalRow(v1)){
				if(!squenceList.containsValue(key)){
					//初始化squenceList列表,每一个key只能被添加一次
					squenceList.put(squenceindex,key);
					//为下一个不同的key做准备
					squenceindex++;
					
					specifyList.put(str_key, new Vector<Vector>());
				}
				
				specifyList.get(str_key).add(v1);
			}else{
				//合计行,将合并行按照生成Flag的key存储到hashtable中
				String Flag = (String)getValueByKey(v1,distinckey);
				totalList.put((String)trp.getKeyWithFlag().get(Flag), v1);
			}
		}
		
		//重新组织Vector并返回
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
	 * 作者：lisg <br>
	 * 日期：2006-9-20
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
	 * 作者：lisg <br>
	 * 日期：2006-9-20
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
	 * 作者：lisg <br>
	 * 日期：2006-9-27
	 * @return
	 */
	protected String getBodyVOName() {
		String classname = getUIControl().getBillVoName()[2];
		return classname;
	}
}
