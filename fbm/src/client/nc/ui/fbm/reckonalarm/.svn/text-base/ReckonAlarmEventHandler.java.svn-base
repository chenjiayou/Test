package nc.ui.fbm.reckonalarm;

import java.util.ArrayList;

import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.button.ButtonVOFactory;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.card.BillCardUI;
import nc.ui.trade.card.CardEventHandler;
import nc.vo.fbm.reckonalarm.ReckalarmVO;


/**
 * <p>
 * 调剂清算设置EventHandler
 * <p>创建人：bsrl
 * <b>日期：2007-10-30
 *
 */
public class ReckonAlarmEventHandler  extends CardEventHandler{

	public ReckonAlarmEventHandler(BillCardUI billUI, ICardController control) {
		super(billUI, control);
	}

	protected void onBoSave() throws Exception {
		getBillCardPanelWrapper().getBillCardPanel().getBillData()
				.dataNotNullValidate();
		super.onBoSave();
		afterSave();
	}
	
	@Override
	protected void onBoRefresh() throws Exception {
		onButton(ButtonVOFactory.getInstance().build(IBillButton.Query).buildButton());
	}

	@Override
	protected void buttonActionAfter(AbstractBillUI billUI, int intBtn)
			throws Exception {
		super.buttonActionAfter(billUI, intBtn);
		if(intBtn == IBillButton.Save) {
			ReckalarmVO[] childvos = (ReckalarmVO[])getBufferData().getCurrentVO().getChildrenVO();
			ArrayList<ReckalarmVO> newchildvos = new ArrayList<ReckalarmVO>();
			for(int i = 0; i < childvos.length; i ++) {
				if(childvos[i].getPk_corp().equalsIgnoreCase(ClientInfo.getCorpPK())) {
					newchildvos.add(childvos[i]);
				}
			}
			Object[] obj = newchildvos.toArray();
			ReckalarmVO[] chvos = new ReckalarmVO[obj.length];
			for(int i = 0; i < obj.length; i ++) {
				chvos[i] = (ReckalarmVO)obj[i];
			}
			getBufferData().getCurrentVO().setChildrenVO(chvos);
			getBillCardPanelWrapper().getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).setBodyDataVO(chvos);
		}
	}

	@Override
	protected void buttonActionBefore(AbstractBillUI billUI, int intBtn)
			throws Exception {
		super.buttonActionBefore(billUI, intBtn);
		if(intBtn == IBillButton.Save) {
			BillModel billModel = getBillCardPanelWrapper().getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME);
			int row = billModel.getRowCount();
			ArrayList<Integer> delline = new ArrayList<Integer>();
			for(int i = 0; i < row; i ++) {
				for(int j = i+1; j < row; j ++) {
					if(compareReckonAlarmVO(i, j)) {
						delline.add(new Integer(j));
					}
				}
			}
			int[] delrow = new int[delline.size()]; 
			for(int i = 0; i < delline.size(); i ++) {
				delrow[i] = delline.get(i).intValue();
			}
			billModel.delLine(delrow);
		}
	}

	private boolean compareReckonAlarmVO(int row1, int row2) {
		if(row1 < 0 || row2 < 0)
			return false;
		ReckalarmVO vo1 = (ReckalarmVO)getBillCardPanelWrapper().getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).getBodyValueRowVO(row1, ReckalarmVO.class.getName());
		ReckalarmVO vo2 = (ReckalarmVO)getBillCardPanelWrapper().getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).getBodyValueRowVO(row2, ReckalarmVO.class.getName());
		if(vo1 == null || vo2 == null) 
			return false;
		if(vo1.getReckonunit() == null || vo2.getReckonunit() == null || vo1.getInacc() == null ||
				vo2.getInacc() == null || vo1.getReckondate() == null || vo2.getReckondate() == null || 
				vo1.getPk_curr() == null || vo2.getPk_curr() == null) {
			return false;
		}
        if(vo1.getReckonunit().equals(vo2.getReckonunit()) && vo1.getInacc().equals(vo2.getInacc()) &&
        		vo1.getReckondate().equals(vo2.getReckondate())	&& vo1.getPk_curr().equals(vo2.getPk_curr())) {
        	return true;
        }
		return false;
	}
	
	private void afterSave() {
		ReckalarmVO[] childvos = (ReckalarmVO[])getBufferData().getCurrentVO().getChildrenVO();
		ArrayList<ReckalarmVO> newchildvos = new ArrayList<ReckalarmVO>();
		for(int i = 0; i < childvos.length; i ++) {
			if(childvos[i].getPk_corp().equalsIgnoreCase(ClientInfo.getCorpPK())) {
				newchildvos.add(childvos[i]);
			}
		}
		Object[] obj = newchildvos.toArray();
		ReckalarmVO[] chvos = new ReckalarmVO[obj.length];
		for(int i = 0; i < obj.length; i ++) {
			chvos[i] = (ReckalarmVO)obj[i];
		}
		getBufferData().getCurrentVO().setChildrenVO(chvos);
		getBillCardPanelWrapper().getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).setBodyDataVO(chvos);
	}
	
	protected boolean askForBodyQueryCondition(StringBuffer sqlWhereBuf) throws Exception {
		String strWhere = new String();
		strWhere = " pk_corp = '" + ClientInfo.getCorpPK() + "'";
		sqlWhereBuf.append(strWhere);
		return true;
	}

	@Override
	protected void onBoLineAdd() throws Exception {
		super.onBoLineAdd();
		BillModel reckalarmbmodel = getBillCardPanelWrapper().getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME);
		int row = reckalarmbmodel.getRowCount() - 1;
		reckalarmbmodel.setValueAt(ClientInfo.getCorpPK(), row, getBillCardPanelWrapper().getBillCardPanel().getBodyColByKey(ReckalarmVO.PK_CORP+IBillItem.ID_SUFFIX));
		reckalarmbmodel.setValueAt(ClientInfo.getCurrentDate(), row, getBillCardPanelWrapper().getBillCardPanel().getBodyColByKey(ReckalarmVO.RECKONDATE));
		reckalarmbmodel.setValueAt(ClientInfo.getLocalCurrPk(), row, getBillCardPanelWrapper().getBillCardPanel().getBodyColByKey(ReckalarmVO.PK_CURR));
	}
	
	@Override
	protected void onBoLineIns() throws Exception {
		// TODO Auto-generated method stub
		super.onBoLineIns();
		BillModel reckalarmbmodel = getBillCardPanelWrapper().getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME);
		int row = getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectedRow();
		reckalarmbmodel.setValueAt(ClientInfo.getCorpPK(), row, getBillCardPanelWrapper().getBillCardPanel().getBodyColByKey(ReckalarmVO.PK_CORP));
		reckalarmbmodel.setValueAt(ClientInfo.getCurrentDate(), row, getBillCardPanelWrapper().getBillCardPanel().getBodyColByKey(ReckalarmVO.RECKONDATE));
		
	}
	
}
