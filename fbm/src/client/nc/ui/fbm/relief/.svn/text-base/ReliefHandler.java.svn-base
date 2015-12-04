package nc.ui.fbm.relief;

import java.math.BigDecimal;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.bill.BillModel;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.IBillStatus;

/**
 * 
 * 功能： 调剂出库事件处理 日期：2007-10-20 程序员：wues
 */
public class ReliefHandler extends FBManageEventHandler {

	/**
	 * 集团编码，根据此编码和系统参数编码取系统参数值
	 */
	private static final String GROUPCODE = "@@@@";


	public ReliefHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);

	}

	/**
	 * 捕获业务按钮，重设VO参照值
	 */
	protected void beforeOnBoAction(int intBtn, AggregatedValueObject billVo)
			throws Exception {
		super.beforeOnBoAction(intBtn, billVo);
		switch (intBtn) {
		case IFBMButton.Relief_Output:// 出库跳转到内部领用单界面
			JumpToInnerBack(FbmBusConstant.BILLTYPE_INNERBACK);// 内部领用单
			throw new BusinessException(NOTSHOW);
		}

	}

	/**
	 * 重设携带出来的值
	 */
	public void onBoActionElse(ButtonObject bo) throws Exception {
		String btncode = bo.getCode();
		AggregatedValueObject vo = getBufferData().getCurrentVO();

		String isAuto = OuterProxy.getSysInitQry().getParaString(GROUPCODE,
				FBMParamConstant.FBM002);

		if (btncode.equals(IFBMButton.RELIEF_CANCELVOUCHER)) {// 取消制证
			// 中心操作
			if (FBMClientInfo.isSettleCenter()) {// 中心
				((ReliefVO) vo.getParentVO()).setVoucherdate(null);// 中心制证日期
				((ReliefVO) vo.getParentVO()).setVouchermanid(null);// 中心制证人
			} else {// 单位操作
				((ReliefVO) vo.getParentVO()).setUnitvoucherdate(null);// 单位制证日期
				((ReliefVO) vo.getParentVO()).setUnitvouchermanid(null);// 单位制证人
			}
		}
		if (btncode.equals(IFBMButton.RELIEF_VOUCHER)) {// 制证
			if (null == isAuto || "".equals(isAuto)
					|| UFBoolean.TRUE.equals(UFBoolean.valueOf(isAuto))) {
				((ReliefVO) vo.getParentVO()).setVoucherdate(ClientInfo
						.getCurrentDate());// 中心制证日期
				((ReliefVO) vo.getParentVO()).setVouchermanid(ClientInfo
						.getUserPK());// 中心制证人
				((ReliefVO) vo.getParentVO()).setUnitvoucherdate(ClientInfo
						.getCurrentDate());// 单位制证日期
				((ReliefVO) vo.getParentVO()).setUnitvouchermanid(ClientInfo
						.getUserPK());// 单位制证人
			} else {
				// 中心操作
				if (FBMClientInfo.isSettleCenter()) {// 中心
					((ReliefVO) vo.getParentVO()).setVoucherdate(ClientInfo
							.getCurrentDate());// 中心制证日期
					((ReliefVO) vo.getParentVO()).setVouchermanid(ClientInfo
							.getUserPK());// 中心制证人
				} else {// 单位操作
					((ReliefVO) vo.getParentVO()).setUnitvoucherdate(ClientInfo
							.getCurrentDate());// 单位制证日期
					((ReliefVO) vo.getParentVO())
							.setUnitvouchermanid(ClientInfo.getUserPK());// 单位制证人
				}
			}
		}

		if (vo != null && vo.getParentVO() != null) {

			if (btncode.equals(IFBMButton.RELIEF_VOUCHER)
					|| btncode.equals(IFBMButton.RELIEF_CANCELVOUCHER)) {
				ReliefBVO[] reliefBVOS = (ReliefBVO[]) vo.getChildrenVO();
				for (int i = 0; i < reliefBVOS.length; i++) {
					UFDouble moneyy = new UFDouble(0.0);
					Object moneyyobj = getUI().getRefTakenProccessor()
					.getValueByTakenInList(ReliefBVO.TABLECODE,
							ReliefBVO.MONEYY, i);
					if(moneyyobj !=null){
						moneyy = new UFDouble(moneyyobj.toString());
					}
					
					UFDouble moneyb = new UFDouble(0.0);
					Object moneybobj = getUI().getRefTakenProccessor()
							.getValueByTakenInList(ReliefBVO.TABLECODE,
									ReliefBVO.MONEYB, i);
					if (moneybobj != null)
						moneyb = new UFDouble(moneybobj.toString());

					UFDouble moneybrate = new UFDouble(0.0);
					Object brateobj = getUI().getRefTakenProccessor()
							.getValueByTakenInList(ReliefBVO.TABLECODE,
									ReliefBVO.BRATE, i);
					if (brateobj != null)
						moneybrate = new UFDouble(brateobj.toString());

					UFDouble frate = new UFDouble(0);
					Object frateObj = getUI().getRefTakenProccessor()
							.getValueByTakenInList(ReliefBVO.TABLECODE,
									ReliefBVO.FRATE, i);
					if (frateObj != null)
						frate = new UFDouble(frateObj.toString());

					UFDouble moneyf = new UFDouble(0);
					Object moneyfObj = getUI().getRefTakenProccessor()
							.getValueByTakenInList(ReliefBVO.TABLECODE,
									ReliefBVO.MONEYF, i);
					if (moneyfObj != null)
						moneyf = new UFDouble(moneyfObj.toString());

					reliefBVOS[i].setMoneyy(moneyy);
					reliefBVOS[i].setMoneyb(moneyb);
					reliefBVOS[i].setBrate(moneybrate);
					reliefBVOS[i].setFrate(frate);
					reliefBVOS[i].setMoneyf(moneyf);

					// 携带票据编号和票据类型
					reliefBVOS[i].setFbmbillno((String) getUI()
							.getRefTakenProccessor()
							.getValueByTakenInList(ReliefBVO.TABLECODE,
									ReliefBVO.FBMBILLNO, i));
					reliefBVOS[i].setFbmbilltype((String) getUI()
							.getRefTakenProccessor().getValueByTakenInList(
									ReliefBVO.TABLECODE, ReliefBVO.FBMBILLTYPE,
									i));
				}
			}
		}

		super.onBoActionElse(bo);
		if(btncode.equals(IFBMButton.FBM_TALLY_STR) ){
			if(getBufferData().getCurrentVO()!=null && getBufferData().getCurrentVO().getParentVO() !=null){
				String pk_accid = ((ReliefVO)getBufferData().getCurrentVO().getParentVO()).getInneracc() ;
				boolean isValid = showNoTallyInfo(pk_accid);
				if(!isValid){
					return;
				}
			}
		}
	}

	
	/**
	 * 覆盖父类方法，当为结算单位时只查询已审核或已制证的单据
	 */
	protected String getHeadCondition() {
		String pk_corp = _getCorp().getPrimaryKey();
		boolean isCenter = FBMClientInfo.isSettleCenter(pk_corp);
		if (isCenter) {// 中心，不做处理，直接返回父类相应查询条件
			return super.getHeadCondition();
		} else {// 单位
			return new StringBuffer().append(" (reliefcorp = '"+pk_corp+"' and fbm_relief.vbillstatus =  ")
					.append(IBillStatus.CHECKPASS).append(
							" or fbm_relief.vbillstatus =  ").append(
							IFBMStatus.HAS_VOUCHER).append(
							" or fbm_relief.vbillstatus= ").append(
							IFBMStatus.HAS_UNIT_VOUCHER).append(" ) ")
					.toString();
		}
	}

	/**
	 * 避免根据携带生成的要素查询时出错
	 */
//	@Override
//	protected UIDialog createQueryUI() {
//		return new RefTakenQueryConditionClient(getUI(), null, _getCorp()
//				.getPk_corp(), FbmBusConstant.FUNCODE_RELIEF_OUT,
//				_getOperator(), FbmBusConstant.BILLTYPE_RELIEF, getUI()
//						.getRefTakenProccessor());
//	}
	
	/**
	 * 删行时候重新计算金额汇总
	 */
	protected void onBoLineDel() throws Exception {
		super.onBoLineDel();
		recalSummoney();
	}

	
	private void recalSummoney(){
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(
				ReliefBVO.TABLECODE);
		
		int rowcount = billmodel.getRowCount();
		UFDouble summoneyy = new UFDouble(0);
		UFDouble moneyy;
		for(int i = 0 ; i < rowcount; i++){
			Object obj = getUI().getRefTakenProccessor().getValueByTakenInList(ReliefBVO.TABLECODE, ReliefBVO.MONEYY, i);
			if (obj instanceof BigDecimal) {
				moneyy = new UFDouble(((BigDecimal) obj).doubleValue());
			} else {
				moneyy = (UFDouble) obj;
			}
			
			if(null == moneyy || moneyy.doubleValue() == 0){
				continue;
			}
			summoneyy = summoneyy.add(moneyy);
		}
		getUI().getBillCardPanel().getHeadItem(ReliefVO.SUMMONEY).setValue(summoneyy);
	}
	@Override
	protected void onBoSave() throws Exception {
		// TODO Auto-generated method stub
		recalSummoney();
		super.onBoSave();
	}
	
	@Override
	public void onBoAudit() throws Exception {
		// TODO Auto-generated method stub
		super.onBoAudit();
		

	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.bill.BillEventHandler#onBoQuery()
	 */
	@Override
	protected void onBoQuery() throws Exception {
		// TODO 自动生成方法存根
		super.onBoQuery();
	}
}
