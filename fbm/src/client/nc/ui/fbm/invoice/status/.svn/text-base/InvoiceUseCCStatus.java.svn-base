/**
 *
 */
package nc.ui.fbm.invoice.status;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.AggregatedValueObject;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-11-1
 *
 */
public class InvoiceUseCCStatus extends AbstractBillUIStatus<Integer> {

	/**
	 * @param ui
	 */
	public InvoiceUseCCStatus(AbstractBillUI ui) {
		super(ui);
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		int retStatus = IsUseCCcontrol.IS_NOT_USECC;
		AggregatedValueObject vo = getUI().getBufferData().getCurrentVO();
		if(vo==null||vo.getParentVO()==null)
			return retStatus;
		String cctype = (String) vo.getParentVO().getAttributeValue(RegisterVO.CCTYPE);
		if(!CommonUtil.isNull(cctype)&&!cctype.equals(FbmBusConstant.CCTYPE_NONE)){
			retStatus = IsUseCCcontrol.IS_USECC;
		}
		return retStatus;
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return IsUseCCcontrol.class.getName();
	}

}
