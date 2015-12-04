/**
 *
 */
package nc.ui.fbm.gather.status;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.AggregatedValueObject;

/**
 * <p>
 * 是否背书生成的状态判断
 * <p>创建人：lpf
 * <b>日期：2007-10-25
 *
 */
public class GatherIsEndureStatus extends AbstractBillUIStatus<Integer> {

	/**
	 * @param ui
	 */
	public GatherIsEndureStatus(AbstractBillUI ui) {
		super(ui);
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		int isEndure = IsEndureControlStatus.IS_ENDURE;
		AggregatedValueObject vo = getUI().getBufferData().getCurrentVO();
		if(vo!=null&&vo.getParentVO()!=null){
			String gatherType = (String) vo.getParentVO().getAttributeValue(RegisterVO.GATHERTYPE);
			if(!CommonUtil.isNull(gatherType)&&!gatherType.equals(FbmBusConstant.GATHER_TYPE_ENDORE)){
				isEndure = IsEndureControlStatus.IS_NOT_ENDURE;
			}
		}
		return isEndure;
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return IsEndureControlStatus.class.getName();
	}

	@Override
	public FBMManageUI getUI() {
		return (FBMManageUI) super.getUI();
	}
}
