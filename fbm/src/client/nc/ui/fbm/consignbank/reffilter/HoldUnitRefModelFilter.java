package nc.ui.fbm.consignbank.reffilter;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.refmodel.FBMBankAccBasRefModel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * <p>
 *	Ʊ�����յ����ݳ�Ʊ��λ������������
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-09
 *
 */
public class HoldUnitRefModelFilter extends BillItemRefModelFilter {

	public HoldUnitRefModelFilter(BillItem billitem) {
		super(billitem);
	}

	@Override 
	protected String getSelfFilterString() {
		UIRefPane refHoldAcc= (UIRefPane)getBillItem().getComponent();
		String condition = null;
		condition = (String)refHoldAcc.getRefModel().getValue("bd_cubasdoc.pk_cubasdoc");
		String pk_corp = FBMClientInfo.getCorpPK();
		if(CommonUtil.isNull(condition)){
			return "   authcorp='"+pk_corp+"' ";
		}else{
			return "   authcorp='"+pk_corp+"' and "+FBMBankAccBasRefModel.PK_CUBASDOC+"='"+condition+"' " ;
					// and bd_cubasdoc.pk_cubasdoc = '" + condition + "' ";
		}
//		return " pk_cubasdoc =  '" + condition + "'";
	}

}
