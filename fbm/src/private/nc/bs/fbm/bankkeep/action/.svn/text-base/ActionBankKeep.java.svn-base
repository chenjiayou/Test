package nc.bs.fbm.bankkeep.action;

import java.util.ArrayList;
import java.util.List;

import nc.bs.fbm.pub.action.AbstractBusiAction;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

public abstract class ActionBankKeep<K extends HYBillVO,T extends StorageVO> extends  AbstractBusiAction<K, T>  {
	

	
	@Override
	public BusiActionParamVO<T>[] buildParam(K data, String actioncode)
			throws BusinessException {
		StorageVO parent = (StorageVO) data.getParentVO();
		StorageBVO[] child = (StorageBVO[]) data.getChildrenVO();
		if (child != null && child.length > 0) {
			int len = child.length;
			List<BusiActionParamVO<T>> list = new ArrayList<BusiActionParamVO<T>>();
			for (int i = 0; i < len; i++) {
				BusiActionParamVO<T> param = new BusiActionParamVO<T>();
				param.setPk_bill(parent.getPrimaryKey());
				param.setPk_source(child[i].getPk_source());
				param.setActionperson(parent.getInputperson());
				param.setActiondate(parent.getInputdate());
				param.setActioncode(actioncode);
				param.setPk_baseinfo(child[i].getPk_baseinfo());
				param.setPk_corp(parent.getPk_corp());
				param.setSourcefield(StorageBVO.PK_SOURCE);
				param.setBilltype(FbmBusConstant.BILLTYPE_BANKKEEP);
				list.add(param);
			}

			return (BusiActionParamVO<T>[]) list.toArray(new BusiActionParamVO[0]);
		}
		return null;
	}
}
