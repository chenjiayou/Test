package nc.bs.fbm.returnbill.action;

import java.util.ArrayList;
import java.util.List;

import nc.bs.fbm.pub.action.AbstractBusiAction;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;


public abstract class ActionReturnBill<K extends HYBillVO,T extends ReturnVO>  extends AbstractBusiAction<K, T> {

	@Override
	public BusiActionParamVO<T>[] buildParam(K data, String actioncode)
			throws BusinessException {
		ReturnVO parent = (ReturnVO) data.getParentVO();
		ReturnBVO[] child = (ReturnBVO[]) data.getChildrenVO();
		if (child != null && child.length > 0) {
			int len = child.length;
			List<BusiActionParamVO<T>> list = new ArrayList<BusiActionParamVO<T>>();

			for (int i = 0; i < len; i++) {
				BusiActionParamVO<T> param = new BusiActionParamVO<T>();
				param.setActiondate(parent.getDreturndate());
				param.setActionperson(parent.getReturnperson());
				param.setActioncode(actioncode);
				param.setPk_bill(parent.getPrimaryKey());
				param.setPk_source(child[i].getPk_source());
				param.setSourcefield(ReturnBVO.PK_SOURCE);
				param.setBilltype(FbmBusConstant.BILLTYPE_RETURN);
				param.setPk_baseinfo(child[i].getPk_baseinfo());
				param.setSuperVO((T)parent);
				param.setPk_corp(parent.getPk_corp());
				list.add(param);
			}
			return (BusiActionParamVO<T>[]) list.toArray(new BusiActionParamVO[0]);

		}
		return null;
	}

}
