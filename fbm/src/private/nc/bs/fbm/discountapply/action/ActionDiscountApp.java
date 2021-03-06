package nc.bs.fbm.discountapply.action;

import nc.bs.fbm.pub.action.AbstractBusiAction;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;


public abstract class ActionDiscountApp<K extends DiscountVO, T extends DiscountVO> extends AbstractBusiAction<K, T> {

//	protected String checkOuter(ActionParamVO param) throws BusinessException{
//		OuterVO[] outerVO = queryOuterVO(param);
//		
//		//合法的情况：引用该pk_register的外部关联有且仅有一条完成的收票收款单
//		if(outerVO != null && outerVO[0] != null ){
//			if(outerVO.length == 1){
//				String pk_billtypecode = outerVO[0].getPk_billtypecode();
//				String outerStatus = outerVO[0].getOuterstatus();
//				if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_GATHER) && outerStatus.equals(FbmBusConstant.OUTERBILL_OVER)){
//					return null;
//				}else{
//					return "收付报未进行收票收款或收票收款未完成";
//				}
//				
//			}else{
//				return "收付报多次引用收票，数据错误";
//			}
//		}else{
//			return "没有收票收款单记录，无法执行操作";
//		}
//	}
	
	/**
	 * 
	 * 贴现申请单转换为动作参数VO xwq 2007-8-29
	 * 
	 * @param
	 * @return
	 * @throws BusinessException 
	 * @throws
	 * @since NC5.0
	 */
	@Override
	public BusiActionParamVO<T>[] buildParam(K data, String actioncode) throws BusinessException{
		BusiActionParamVO<T>[] params = new BusiActionParamVO[1];
		params[0] = new BusiActionParamVO<T>();
		fillBusiField(data, params[0], actioncode);
		params[0].setActioncode(actioncode);
		params[0].setActiondate(data.getApplydate());
		params[0].setPk_bill(data.getPrimaryKey());
		params[0].setPk_source(data.getPk_source());
		params[0].setPk_baseinfo(data.getPk_baseinfo());
		params[0].setSourcefield(DiscountVO.PK_SOURCE);
		params[0].setBilltype(FbmBusConstant.BILLTYPE_DISCOUNT_APP);
		params[0].setPk_corp(data.getPk_corp());
		return params;
    }
}
