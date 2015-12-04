package nc.bs.fbm.gather.action;

import nc.bs.fbm.pub.action.AbstractBusiAction;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

public abstract class ActionGather<K extends RegisterVO, T extends RegisterVO>
		extends AbstractBusiAction<K, T> {

	@Override
	public BusiActionParamVO<T>[] buildParam(K data, String actioncode)
			throws BusinessException {
		BusiActionParamVO<T>[] params = new BusiActionParamVO[1];
		params[0] = new BusiActionParamVO<T>();

		params[0].setActioncode(actioncode);
		if (actioncode.equals(FbmActionConstant.EDITSAVE)
				|| actioncode.equals(FbmActionConstant.SAVE)
				|| actioncode.equals(FbmActionConstant.CENTERUSE)
				|| actioncode.equals(FbmActionConstant.CANCELCENTERUSER)) {// 中心使用或取消中心使用时取操作人
			params[0].setActionperson(data.getVoperatorid());
		} else if (actioncode.equals(FbmActionConstant.ONAUDIT)
				|| actioncode.equals(FbmActionConstant.AUDIT)
				|| actioncode.equals(FbmActionConstant.DESTROY)
				|| actioncode.equals(FbmActionConstant.CANCELDESTROY)) {
			params[0].setActionperson(data.getVapproveid());
		}
		params[0].setActiondate(data.getGatherdate());
		params[0].setPk_bill(data.getPrimaryKey());
		params[0].setPk_source(data.getPrimaryKey());
		params[0].setPk_baseinfo(data.getPk_baseinfo());
		params[0].setMoneyy(data.getMoneyy());
		params[0].setUnit_a(data.getHoldunit());
		params[0].setBilltype(data.getPk_billtypecode());// 有可能为贴现或贴现办理
		params[0].setSourcefield(RegisterVO.PK_REGISTER);
		params[0].setPk_org(data.getHoldunit());
		params[0].setPk_corp(data.getPk_corp());

		return params;
	}

	@Override
	protected String checkOuter(BusiActionParamVO<T> param)
			throws BusinessException {
		String actioncode = param.getActioncode();
		if (actioncode.equals(FbmActionConstant.CANCELAUDIT)) {
			String pk_bill = param.getPk_bill();
			OuterVO[] outerVO = relDao.queryByPkBusibill(pk_bill);
			if (outerVO != null) {
				return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
						"fbmcomm", "UPPFBMComm-000244")/* @res"收付报单据已经引用此收票登记，无法弃审" */;
			}
		}
		return null;
	}
}
