package nc.bs.fbm.consignbank.action;
import nc.bs.fbm.pub.action.AbstractBusiAction;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;

public abstract class ActionCollection<K extends CollectionVO, T extends CollectionVO> extends AbstractBusiAction<K, T> {
	/**
	 * 
	 * 托收办理单转换为动作参数VO xwq 2007-8-29
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
		params[0].setPk_baseinfo(data.getPk_baseinfo());
		params[0].setPk_bill(data.getPrimaryKey());
		params[0].setPk_source(data.getPk_source());
		params[0].setSourcefield(CollectionVO.PK_SOURCE);
		params[0].setBilltype(FbmBusConstant.BILLTYPE_COLLECTION_UNIT);
		params[0].setPk_corp(data.getPk_corp());
		if (actioncode.equals(FbmActionConstant.TRANSACT)) {
			params[0].setActiondate(data.getDtransactdate());//办理日期
			params[0].setActionperson(data.getVtransactorid());// 办理人
		} else if (actioncode.equals(FbmActionConstant.DISABLE)) {
			params[0].setActiondate(data.getDdisabledate());//作废日期
			params[0].setActionperson(data.getVdisablemanid());// 作废人
		}
		else if(actioncode.equals(FbmActionConstant.AUDIT)){
			params[0].setActiondate(data.getDapprovedate());
			params[0].setActionperson(data.getVapproveid());
		}
		else {
			if (data.getDconsigndate() == null) {//如果委托日期为空
				params[0].setActiondate(data.doperatedate);//操作日期	
			} else {
				params[0].setActiondate(data.dconsigndate);//委托日期	
			}
		}
		return params;
	}
}
