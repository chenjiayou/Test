package nc.ui.fbm.discountcalculate.listener;

import nc.bs.logging.Logger;
import nc.ui.fbm.discountcalculate.DiscountCalculationUI;
import nc.ui.fbm.discountcalculate.ITotalSpecify;
import nc.ui.fbm.discountcalculate.TotalRowPara;
import nc.ui.fbm.discountcalculate.TotalRowUITools;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;

/**
 * 
 * <p>
 *	表头全部选择事件发生后的监听类
 * <p>创建人：hzguo
 * <b>日期：2008-07-10
 *
 */
public class DiscountCalculateHeadAllChoiceEditListener extends
DiscountCalculationAbstractItemEditListener {

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculateHeadAllChoiceEditListener(DiscountCalculationUI ui) {
		super(ui, RegisterVO.ALL_CHOICE);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		try {
//			if(getBufferData() == null || getBufferData().getCurrentVO() == null) return ;
//			
//			AggregatedValueObject billVO = getBillCardWrapper().getBillVOFromUI();
//			PjzbVO[] vos2 = (PjzbVO[]) billVO.getChildrenVO();
//			
//			if(vos2 == null){
//				return;
//			}
			
			BillCardPanel billCardPanel = getUI().getBillCardPanel();
			UFBoolean value = new UFBoolean(billCardPanel.getHeadItem("allchoice").getValueObject().toString());
//				ITotalSpecify ts = getTotalSpecifyController();
//				TotalRowPara trp = (ts == null)?null:ts.getTotalRowPara(PjzbVO.class.getName());
			int count = billCardPanel.getBillModel(RegisterVO.TABLENAME).getRowCount();
			for (int i = 0; i < count; i++){
				RegisterVO curvo = (RegisterVO)billCardPanel.getBillModel(RegisterVO.TABLENAME).getBodyValueRowVO(i, RegisterVO.class.getName());
				if (!isCountVO(curvo)){
					billCardPanel.getBillModel(RegisterVO.TABLENAME).setValueAt(value, i, RegisterVO.CHOICE);
					if (!value.booleanValue()){
						billCardPanel.getBillModel(RegisterVO.TABLENAME).setValueAt(null, i, RegisterVO.TXLX);
						billCardPanel.getBillModel(RegisterVO.TABLENAME).setValueAt(null, i, RegisterVO.TXJZ);
					}
				}
			}
//				for(int index = 0; index < vos2.length; index++){
//					if(!isCountVO(vos2[index])){
//						vos2[index].setAttributeValue("choice", value);
////						if(TotalRowUITools.isLegalTotalController(getUIControl()) && trp != null){
////							//如果不是合计行则设置VO所在的序号
////							vos2[index].setAttributeValue(trp.getDistinctColName(), (new Integer(index)).toString());
////						}else{
////							vos2[index].setZyx10((new Integer(index)).toString());
////						}
//					}
//				}
//				billVO.getParentVO().setAttributeValue("allchoice", value);
//				billVO.setChildrenVO(vos2);
//				getBufferData().clear();
//				getBufferData().addVOToBuffer(billVO);
//				getBufferData().updateView();
//				getBillCardWrapper().set
			} catch (Exception ex) {
				Logger.error(ex.getMessage(),ex);
			}
		}

	/**
	 * <p>
	 * 判断当前的VO是否是合计VO
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-19
	 * @param vo
	 * @return
	 */
	public boolean isCountVO(SuperVO vo){
		if(vo == null || !TotalRowUITools.isLegalTotalController(getUI().getUIControl())) return false;
		
		ITotalSpecify ts = getTotalSpecifyController();
		TotalRowPara trp = ts.getTotalRowPara(vo.getClass().getName());
		if(trp == null) return false;
		return trp.isCountVO(vo);
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
		if(getUI().getUIControl() instanceof ITotalSpecify){
			return (ITotalSpecify)getUI().getUIControl();
		}
		
		return null;
	}

}
