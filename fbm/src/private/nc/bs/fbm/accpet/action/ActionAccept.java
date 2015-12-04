package nc.bs.fbm.accpet.action;

import nc.bs.fbm.pub.action.AbstractBusiAction;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;



public abstract class ActionAccept<K extends AcceptVO, T extends AcceptVO> extends AbstractBusiAction<K, T> {
	/**
	 * 
	 * 承兑办理单转换为动作参数VO xwq 2007-8-29
	 * 
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	public BusiActionParamVO<T>[] buildParam(K data,
			String actioncode) throws BusinessException {
		BusiActionParamVO<T>[] params = new BusiActionParamVO[1];
		params[0] = new BusiActionParamVO<T>();
		fillBusiField(data, params[0], actioncode);
		params[0].setActioncode(actioncode);
		params[0].setActiondate(data.getDacceptdate());
		params[0].setPk_bill(data.getPrimaryKey());
		params[0].setPk_source(data.getPk_source());
		params[0].setSourcefield(AcceptVO.PK_SOURCE);
		params[0].setPk_baseinfo(data.getPk_baseinfo());
		params[0].setBilltype(FbmBusConstant.BILLTYPE_BILLPAY);
		params[0].setPk_corp(data.getPk_corp());
		return params;
	}
	
}
