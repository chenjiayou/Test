/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.CardEventHandler;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;

/**
 * <p>
 * 合计卡片事件处理类
 * <p>
 * 创建日期：2006-9-19
 * 
 * @author lisg
 * @since v5.0
 */
public abstract class TotalCardEventHandler extends CardEventHandler {



	public TotalCardEventHandler(TotalRowBillCardUI billUI, ICardController control) {
		super(billUI, control);
	}
	
	public TotalRowBillCardUI getTotalRowBillCardUI(){
		return (TotalRowBillCardUI)getBillUI();
	}
	

	/**
	 * <p>
	 * 判断当前的VO是否是合计VO
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-19
	 * @param vo
	 * @return
	 */
	public boolean isCountVO(SuperVO vo){
		if(vo == null || !TotalRowUITools.isLegalTotalController(getUIController())) return false;
		
		ITotalSpecify ts = getTotalSpecifyController();
		TotalRowPara trp = ts.getTotalRowPara(vo.getClass().getName());
		if(trp == null) return false;
		return trp.isCountVO(vo);
	}
	
	/**
	 * <p>
	 * 返回合计分类处理控制器,当控制器非该类型时,返回null
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-19
	 * @return
	 */
	public ITotalSpecify getTotalSpecifyController(){
		if(getUIController() instanceof ITotalSpecify){
			return (ITotalSpecify)getUIController();
		}
		
		return null;
	}
	
	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-20
	 *
	 * @see nc.ui.trade.bill.BillEventHandler#doBodyQuery(java.lang.String)
	 */
	@Override
	protected void doBodyQuery(String strWhere) throws Exception, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		SuperVO[] queryVos = getBusiDelegator().queryByCondition(
				Class.forName(getUIController().getBillVoName()[2]),
				strWhere == null ? "" : strWhere);
		
		reSetData(queryVos);
	}
	
	
	/**
	 * <p>
	 * 直接设置数据，这个函数直接跟新buffer，不进行合计行的处理
	 * 如果要使用合计行处理请使用reSetRowVOs(...)
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-8
	 * @param queryVos
	 * @throws Exception
	 */
	protected void reSetData(SuperVO[] queryVos) throws Exception{
		
		getBufferData().clear();

		AggregatedValueObject vo = (AggregatedValueObject) Class.forName(
				getUIController().getBillVoName()[0]).newInstance();
		vo.setChildrenVO(queryVos);
		getBufferData().addVOToBuffer(vo);
		
		//执行buffer更新
		doUpdateBuffer(CommonUtil.isNull(queryVos));
	}
	
	/**
	 * <p>
	 * 更新Buffer的操作,默认使用系统动作
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-20
	 * @throws Exception
	 */
	protected void doUpdateBuffer(boolean isNull)throws Exception{
		updateBuffer();
	}
	
	/**
	 * <p>
	 * 重新设置行VO,该函数会自动生成合计行，注意传入的参数VO不包含合计行
	 * 如果对于已经组织好的VO数组，请使用reSetData(...)函数更新Buffer
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-8
	 * @param vos
	 * @throws Exception
	 */
	protected void reSetRowVOs(SuperVO[] vos) throws Exception{
		
		vos = (SuperVO[])((TotalRowBusinessDelegator)getBillUI().getBusiDelegator()).OrganizeTotalVO(vos);
		
		//2.重新设置数据
		reSetData(vos);
		
	}
}
