package nc.bs.fbm.relief.action;

import java.util.ArrayList;
import java.util.List;

import nc.bs.fbm.pub.action.AbstractBusiAction;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

/**
 * 类功能说明： 调剂出库业务处理类 日期：2007-10-24 程序员： wues
 */
public abstract class ActionRelief<K extends HYBillVO,T extends ReliefVO> extends AbstractBusiAction<K, T>{
	/**
	 * <p>
	 * 调剂出库VO转换 wes
	 * </p>
	 * 
	 * @param billVo
	 * @param actionCodessss
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public BusiActionParamVO<T>[] buildParam(K data, String actioncode)
			throws BusinessException {
		ReliefVO parent = (ReliefVO) data.getParentVO();

		ReliefBVO[] child = (ReliefBVO[]) data.getChildrenVO();
		if (child != null && child.length > 0) {
			int len = child.length;
			List<BusiActionParamVO<T>> list = new ArrayList<BusiActionParamVO<T>>();
			for (int i = 0; i < len; i++) {
				BusiActionParamVO<T> param = new BusiActionParamVO<T>();

				if (actioncode != null) {
					fillBusiField(parent, param, actioncode);
				}
				param.setActioncode(actioncode);
				param.setPk_bill(parent.getPrimaryKey());
				param.setPk_source(child[i].getPk_source());
				param.setSourcefield(ReliefBVO.PK_SOURCE);
				param.setPk_baseinfo(child[i].getPk_baseinfo());
				param.setBilltype(FbmBusConstant.BILLTYPE_RELIEF);
				param.setSuperVO((T)parent);
				param.setPk_baseinfo(child[i].getPk_baseinfo());

				param.setUnit_b(parent.getReliefunit());// 调剂给的单位
				param.setPk_corp(parent.getPk_corp());
				list.add(param);
			}
			return (BusiActionParamVO<T>[]) list.toArray(new BusiActionParamVO[0]);
		}
		return null;
	}
}
