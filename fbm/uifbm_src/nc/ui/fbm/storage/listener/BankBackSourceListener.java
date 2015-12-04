package nc.ui.fbm.storage.listener;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.pub.BusinessException;

/**
 * 由票据编号携带出托管银行
 * @author xwq
 *
 */
public class BankBackSourceListener extends AbstractItemEditListener {
	public BankBackSourceListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		
		UIRefPane gatherPane = (UIRefPane) getUI().getBillCardPanel().getBodyItem(
				getItemKey()).getComponent();
		
		if(gatherPane.getRefPK()==null)
			return;
		
		String pk_source = gatherPane.getRefPK();
		
		String sql = "select keepunit from fbm_storage s left join fbm_action a on s.pk_storage=a.pk_bill where pk_source='"+pk_source+"' order by serialnum desc ";
		IUAPQueryBS query = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		try {
			List<String> ret = (List<String>)query.executeQuery(sql, new ColumnListProcessor());
			getUI().getBillCardPanel().getBillModel().setValueAt(ret.get(0),  editEvent.getRow(), getUI().getBillCardPanel().getBodyColByKey(StorageBVO.KEEPBANK));
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
		}
	}

}
