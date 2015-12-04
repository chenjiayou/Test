package nc.ui.fbm.illegal;

import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.pub.beans.UIDialog;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.bsdelegate.BDBusinessDelegator;
import nc.ui.trade.card.BillCardUI;
import nc.ui.trade.card.CardEventHandler;
import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * 非法票据处理的事件处理器
 * 
 * @author wues
 */
public class IllegalBillHandler extends CardEventHandler {

	public IllegalBillHandler(BillCardUI billUI, ICardController control) {
		super(billUI, control);
	}

	/**
	 * 父类中的onBoRefresh()刷新时会把数据刷没,由于是单表体，UAP默认如果为表头为空就设表体为空
	 * 参见CtrlDelegatorAdapter.refreshVO方法
	 * 覆盖此方法，重新查询出所有
	 */
	protected void onBoRefresh() throws Exception {
		HYBillVO[] dataVOs = new HYBillVO[1];
		SuperVO[] queryVos = null;
		String strWhere = "1=1";
		BDBusinessDelegator delegator = new BDBusinessDelegator();
		queryVos = (SuperVO[]) delegator.queryByCondition(IllegalVO.class,
				strWhere);
		if (queryVos != null && queryVos.length > 0) {
			dataVOs[0] = new HYBillVO();
			dataVOs[0].setChildrenVO(queryVos);
			dataVOs[0].setParentVO(queryVos[0]);
		}
		getBufferData().clear();
		getBufferData().addVOsToBuffer(dataVOs);
		if ((queryVos == null) || (queryVos.length == 0))
			return;
		getBufferData().setCurrentRow(0);
	}

	/**
	 * 按钮m_boSave点击时执行的动作,如有必要，请覆盖. 保存之后自动查询出所有数据
	 */
	protected void onBoSave() throws Exception {
		getBillCardPanelWrapper().getBillCardPanel().getBillData()
				.dataNotNullValidate();
		super.onBoSave();
	}

	@Override
	/**
	 * UAP自带查询对话框有问题，查找图片时异常NullPointerExcetpion 覆盖其createQueryUI()
	 */
//	protected UIDialog createQueryUI() {
//		return new RefTakenQueryConditionClient(getBillUI(), null, _getCorp()
//				.getPk_corp(), FbmBusConstant.FUNCODE_ILLEGAL, _getOperator(),
//				FbmBusConstant.BILLTYPE_ILLEGAL, null);
//	}

	/**
	 * 覆盖父方法：界面没有数据或者有数据但是没有选中任何行,如果界面没数据即返回 
	 * 在界面没任何数据时点击修改可以进行增加行操作,去掉为空判断
	 */
	protected void onBoEdit() throws Exception {
		String strTime = getBillUI()._getServerTime().toString();
		// 得到当前VO，父类中，如果VO为空即返回
		AggregatedValueObject modelVo = getBufferData().getCurrentVO();
		// 根据当前设置的走平台与否得到对应的BusinessAction类，此为BDBusinessAction,edit保存此单据,返回o为null
		Object o = getBusinessAction()
				.edit(
						modelVo,
						getUIController().getBillType(),
						getBillUI()._getDate().toString()
								+ strTime.substring(10), null);
		if (o instanceof AggregatedValueObject) {
			AggregatedValueObject retVo = (AggregatedValueObject) o;
			if (retVo.getChildrenVO() == null)
				modelVo.setParentVO(retVo.getParentVO());
			else
				modelVo = retVo;
			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}
		// 将单据状态设置为编辑,行操作可以使用
		getBillUI().setBillOperate(IBillOperate.OP_EDIT);
	}
}
