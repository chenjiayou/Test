package nc.bs.fbm.endore.action;

import nc.bs.fbm.pub.action.AbstractBusiAction;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;


public abstract class ActionEndore<K extends EndoreVO, T extends EndoreVO> extends AbstractBusiAction<K, T> {

	protected String checkOuter(BusiActionParamVO<T> param) throws BusinessException{
		//背书时不检查外部关联表
		return null;
	}
	
	/**
	 * 背书办理单转换
	 * 
	 * @param billVo
	 * @param actionCode
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public BusiActionParamVO<T>[] buildParam(K data, String actioncode)
			throws BusinessException {
		BusiActionParamVO<T>[] params = new BusiActionParamVO[1];
		params[0] = new BusiActionParamVO<T>();
		fillBusiField(data, params[0], actioncode);
		params[0].setActioncode(actioncode);
		params[0].setActiondate(data.getBusdate());
		params[0].setPk_bill(data.getPrimaryKey());
		params[0].setPk_source(data.getPk_source());
		params[0].setSourcefield("pk_source");
		params[0].setBilltype(FbmBusConstant.BILLTYPE_ENDORE);
		params[0].setPk_baseinfo(data.getPk_baseinfo());
		params[0].setPk_corp(data.getPk_corp());
		params[0].setUpgrade(data.getUpgrade());
		return params;
	}

}
