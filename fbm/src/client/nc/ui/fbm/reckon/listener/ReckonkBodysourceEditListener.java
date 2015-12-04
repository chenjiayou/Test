package nc.ui.fbm.reckon.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * 调剂清算携界面表体票据编号编辑监听器类
 * <p>
 * 创建人：bsrl <b>日期：2007-10-22
 * 
 */
public class ReckonkBodysourceEditListener extends AbstractItemEditListener {
	/**
	 * 
	 */
	public ReckonkBodysourceEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public ReckonkBodysourceEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public ReckonkBodysourceEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		UIRefPane sourcegroup = (UIRefPane) getUI().getBillCardPanel()
				.getBodyItem(editEvent.getKey()).getComponent();
		nc.vo.pub.ValueObject[] vob = sourcegroup.getVOs();

		// ReckonBVO[] selectedvos = new ReckonBVO[vob.length];
		// for (int i = 0; i < vob.length; i++) {
		// selectedvos[i] = (ReckonBVO) vob[i];
		// }

		// BillModel billmodel = getUI().getBillCardPanel().getBillModel(
		// ReckonBVO.TABLENAME);

		BillModel billmodel = getUI().getBillCardPanel().getBillModel(
				editEvent.getTableCode());

		int rowcount = billmodel.getRowCount();
//		int[] delline = new int[rowcount];
//		for (int i = 0; i < rowcount; i++) {
//			delline[i] = i;
//		}
//		billmodel.delLine(delline);
//
//		rowcount = billmodel.getRowCount();
		// for (int i = 0; i < selectedvos.length; i++) {
		// CircularlyAccessibleValueObject curVo = selectedvos[i];
		// billmodel.addLine();
		// billmodel.setBodyRowVO(curVo, rowcount + i);
		// }
		String billType = getUI().getUIControl().getBillType();
		String compValue = null;
		String comCol = null;
		int temprowcount = 0;
		boolean add = true; 
		if (FbmBusConstant.BILLTYPE_LIQUIDATE.equals(billType)) {
			comCol = ReckonBVO.PK_DETAIL;
		} else {
			comCol = ReliefBVO.PK_SOURCE;
		}
		if(vob != null && vob.length > 0) {
			for (int i = 0; i < vob.length; i++) {
				add = true;
				if(i == 0 ) {
					billmodel.setBodyRowVO((CircularlyAccessibleValueObject) vob[0],
							editEvent.getRow());
					continue;
				}
				for(int j = 0; j < rowcount; j ++) {
					compValue = (String)billmodel.getValueAt(j, comCol);
					if(((SuperVO)vob[i]).getAttributeValue(comCol).equals(compValue)) {
						add = false;
						break;
					}
				}
				if(add) {					
					temprowcount = billmodel.getRowCount();
					billmodel.addLine();
					billmodel.setBodyRowVO((CircularlyAccessibleValueObject) vob[i],
							temprowcount );
				}
			}
	
			
			if (FbmBusConstant.BILLTYPE_LIQUIDATE.equals(billType)) {
				dealReckon(billmodel);
			}
		}

	}
	
	/**
	 * 调剂清算的单独处理
	 * @param billmodel
	 * @param billType
	 */
	private void dealReckon(BillModel billmodel){
		int rowcount = billmodel.getRowCount();
		UITable uitable = getUI().getBillCardPanel().getBillTable();
		UFDouble reckonmoney = null;
		// UFDouble fbmmoney = null;
		UFDouble summoney = new UFDouble(0);
		for (int i = 0; i < rowcount; i++) {
			reckonmoney = (UFDouble) getUI().getRefTakenProccessor()
					.getValueByTakenInList(ReckonBVO.TABLENAME,
							ReckonBVO.MONEYY, i);
			if (null == reckonmoney || reckonmoney.doubleValue() == 0) {//取到的清算金额为空或0
				continue;
			}
			// fbmmoney =
			// (UFDouble)getUI().getRefTakenProccessor().getValueByTakenInList(ReckonBVO.TABLENAME,
			// ReckonBVO.FBMBILLMONEY, i);

			if (reckonmoney.doubleValue() > 0) {
				uitable.getModel().setValueAt(
						FbmBusConstant.RELIEF_DIRECTION_IN,
						i,
						getUI().getBillCardPanel().getBodyColByKey(
								ReckonBVO.DIRECTION));
			} else {
				uitable.getModel().setValueAt(
						FbmBusConstant.RELIEF_DIRECTION_OUT,
						i,
						getUI().getBillCardPanel().getBodyColByKey(
								ReckonBVO.DIRECTION));
			}
			summoney = summoney.add(reckonmoney);

		}
		getUI().getBillCardPanel().getHeadItem(ReckonVO.RECKONMONEYSUM)
				.setValue(summoney);
	}
	
}