/**
 *
 */
package nc.bs.fbm.returnbill;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-10-26
 *
 */
public class ReturnBillServiceImpl {

	/**
	 * 
	 */
	public ReturnBillServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * <p>
	 * 后台推式生成退票单
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-26
	 * @param pk_source
	 */
	public void saveReturnVoForOtherSys(HYBillVO[] returnBillVos) throws BusinessException{
		if(returnBillVos!=null&&returnBillVos.length>0){
			HYPubBO bo = new HYPubBO();
			for (int i = 0; i < returnBillVos.length; i++) {
				if(returnBillVos[i]!=null){
					bo.saveBill(returnBillVos[i]);
					doBillAction(returnBillVos[i],FbmActionConstant.SAVE);//保存
					doBillAction(returnBillVos[i],FbmActionConstant.AUDIT);//审核
				}
			}
		}
	}

	private void doBillAction(HYBillVO billVo, String actioncode)
			throws BusinessException {
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RETURN, actioncode).doAction(billVo, actioncode, false);

	}
	
	
	/**
	 * 合计退票单子表数据
	 * @param billvo
	 * @return
	 * @throws BusinessException
	 */
	public UFDouble sumMoneyy(AggregatedValueObject billvo) throws BusinessException{
		String pk_return = billvo.getParentVO().getPrimaryKey();
		String sql = "select sum(moneyy) from fbm_baseinfo join fbm_return_b on fbm_baseinfo.pk_baseinfo = fbm_return_b.pk_baseinfo where pk_return='"+pk_return+"'";
		Object obj = FBMProxy.getUAPQuery().executeQuery(sql, new ColumnProcessor());
		return obj == null?new UFDouble(0):new UFDouble(obj.toString());
	}
}
