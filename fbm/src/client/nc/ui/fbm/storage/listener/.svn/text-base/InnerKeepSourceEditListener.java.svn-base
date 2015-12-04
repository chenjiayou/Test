/**
 *
 */
package nc.ui.fbm.storage.listener;

import java.math.BigDecimal;
import java.util.ArrayList;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.fbm.pub.IFBMQuery;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillItem;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-11-20
 *
 */
public class InnerKeepSourceEditListener extends AbstractItemEditListener {
	/**
	 * 
	 */
	public InnerKeepSourceEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public InnerKeepSourceEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public InnerKeepSourceEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		UIRefPane gatherPane = (UIRefPane) getUI().getBillCardPanel().getBodyItem(
				getItemKey()).getComponent();
		
		
		if(gatherPane.getRefPKs()==null)
			return;
		String[] pks = gatherPane.getRefPKs().clone();
		int row = getUI().getBillCardPanel().getRowCount();
		
		ArrayList<String> exPksList = new ArrayList<String>();
		for (int i = 0; i < row; i++) {
			String Pk = (String)getUI().getBillCardPanel().getBillModel().getValueAt(i, getItemKey()+IBillItem.ID_SUFFIX);
			if(!CommonUtil.isNull(Pk))
				exPksList.add(Pk);
		}
		
		String[] newPks = createPks(pks,exPksList);
		try {
			
			String vos[] = getUI().getUIControl().getBillVoName();
			
			IFBMQuery fbmquery = NCLocator.getInstance().lookup(IFBMQuery.class);
			
			CircularlyAccessibleValueObject[] bodyVOs = fbmquery.queryBodyVOs((SuperVO) Class.forName(vos[2]).newInstance(), newPks);
			int currentrowcount = getUI().getBillCardPanel().getRowCount();
			for (int j = 0; j < currentrowcount; j++) {
				getUI().getBillCardPanel().delLine();
			}
			getUI().getBillCardPanel().addLine();
			getUI().getBillCardPanel().getBillModel().setBodyRowVO(bodyVOs[0], 0);
			for (int i = 1; i < bodyVOs.length; i++) {
					getUI().getBillCardPanel().addLine();
				int newrow = getUI().getBillCardPanel().getRowCount();
				getUI().getBillCardPanel().getBillModel().setBodyRowVO(bodyVOs[i], newrow - 1);
			}
			// getUI().getBillCardPanel().getBillModel().setBodyDataVO(bodyVOs);
			
			

			 if (FbmBusConstant.BILLTYPE_LIQUIDATE.equals(getUI().getUIControl().getBillType())) {
				 dealReckon(getUI().getBillCardPanel().getBillModel());
			} else if (FbmBusConstant.BILLTYPE_INNERKEEP.equals(getUI().getUIControl().getBillType())
					|| FbmBusConstant.BILLTYPE_INNERBACK.equals(getUI().getUIControl().getBillType())) {
				 dealSumMoneyy(getUI().getBillCardPanel().getBillModel(), StorageBVO.tablecode, StorageBVO.MONEYY, StorageVO.SUMMONEYY);
			} else if (FbmBusConstant.BILLTYPE_RELIEF.equals(getUI().getUIControl().getBillType())) {
				dealSumMoneyy(getUI().getBillCardPanel().getBillModel(), ReliefBVO.TABLECODE, ReliefBVO.MONEYY, ReliefVO.SUMMONEY);
			}
				
		} catch (Exception e) {
			Logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 * <p>
	 * 过滤PK
	 * <p>
	 * 作者：lpf
	 * 日期：2007-11-27
	 * @param pks
	 * @param exPksList
	 * @return
	 */
	private String[] createPks(String[] pks, ArrayList<String> exPksList){
		for (int i = 0; i < pks.length; i++) {
				if(!exPksList.contains(pks[i])){
					exPksList.add(pks[i]);
				}
			}
		
		String[] returnPks = new String[exPksList.size()];
		exPksList.toArray(returnPks);
		
		return returnPks;
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
		UFDouble summoney = new UFDouble(0);
		for (int i = 0; i < rowcount; i++) {
			Object obj = getUI().getRefTakenProccessor().getValueByTakenInList(ReckonBVO.TABLENAME, ReckonBVO.MONEYY, i);
			if (obj instanceof BigDecimal) {
				reckonmoney = new UFDouble(((BigDecimal) obj).doubleValue());
			} else {
				reckonmoney = (UFDouble) obj;
			}
			if (null == reckonmoney || reckonmoney.doubleValue() == 0) {//取到的清算金额为空或0
				continue;
			}
			if (reckonmoney.doubleValue() > 0) {
				uitable.getModel().setValueAt(FbmBusConstant.RELIEF_DIRECTION_IN,i,
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
	
	public void dealSumMoneyy(BillModel billmodel,String tablecode,String moneyKey,String summoneyKey){
		int rowcount = billmodel.getRowCount();
		UFDouble summoneyy = new UFDouble(0);
		UFDouble moneyy=new UFDouble(0.0);
		for(int i = 0 ; i < rowcount; i++){
			Object moneyobj = getUI().getRefTakenProccessor().getValueByTakenInList(tablecode, moneyKey, i);
			if (moneyobj instanceof BigDecimal) {
				moneyy = new UFDouble(((BigDecimal) moneyobj).doubleValue());
			} else {
				moneyy = (UFDouble) moneyobj;
			}
			if (null == moneyy || moneyy.doubleValue() == 0) {
				continue;
			}
			// moneyy = (UFDouble) moneyobj;
			summoneyy = summoneyy.add(moneyy);
		}
		getUI().getBillCardPanel().getHeadItem(summoneyKey).setValue(summoneyy);
		
	}

}
