package nc.ui.fbm.returnbill.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.returnbill.ReturnVO;

/**
 * 根据退票类型来确定计划项目
 * 只有中心退入的，计划项目才能编辑
 * @author xwq
 *
 * 2008-12-26
 */
public class ReturnTypePlanItemListener  extends AbstractItemEditListener{

	
	public ReturnTypePlanItemListener(FBMManageUI ui, String returnTypeKey) {
		super(ui, returnTypeKey);
	}
	
	public void responseEditEvent(BillEditEvent editEvent) {
		String returnType = (String)getUI().getBillCardPanel().getHeadItem(getItemKey()).getValueObject();
		if(returnType !=null ){
			BillItem planItem = getUI().getBillCardPanel().getHeadItem(ReturnVO.UNITPLANITEM);
			if(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT.equals(returnType)){//单位退入
				planItem.setEnabled(true);
			}else {
				planItem.setValue(null);
				planItem.setEnabled(false);
			}
		}
	}
}
