package nc.bs.fbm.invoice.action;

import nc.bs.fbm.pub.action.AbstractBusiAction;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;

public abstract class ActionInvoice<K extends RegisterVO,T extends RegisterVO> extends AbstractBusiAction<K,T> {
	
	
	@Override
	public BusiActionParamVO<T>[] buildParam(K data, String actioncode)
			throws BusinessException {
		BusiActionParamVO<T>[] params = new BusiActionParamVO[1];
		params[0] = new BusiActionParamVO<T>();
		params[0].setActioncode(actioncode);
		if (actioncode.equals(FbmActionConstant.EDITSAVE)
				|| actioncode.equals(FbmActionConstant.SAVE)) {
			params[0].setActionperson((String) data.getVoperatorid());
			params[0].setActiondate((UFDate) data.getDoperatedate());
		}

		if (actioncode.equals(FbmActionConstant.ONAUDIT)
				|| actioncode.equals(FbmActionConstant.AUDIT)
				|| actioncode.equals(FbmActionConstant.DESTROY)
				|| actioncode.equals(FbmActionConstant.CANCELDESTROY)) {
			params[0].setActionperson((String) data.getAttributeValue("vapproveid"));
			params[0].setActiondate((UFDate) data.getAttributeValue("dapprovedate"));
		}

		params[0].setActiondate(data.getInvoicedate());
		if (actioncode.equals(FbmActionConstant.DESTROY)) {
			params[0].setActiondate(data.getVerifydate());
		}
		params[0].setPk_bill(data.getPrimaryKey());
		params[0].setPk_source(data.getPrimaryKey());
		params[0].setPk_baseinfo(data.getPk_baseinfo());
		params[0].setSourcefield(RegisterVO.PK_REGISTER);
		params[0].setBilltype(FbmBusConstant.BILLTYPE_INVOICE);
		params[0].setPk_corp(data.getPk_corp());
		params[0].setPk_org(data.getHoldunit());
		return params;
	}



	protected String checkOuter(BusiActionParamVO<T> param) throws BusinessException{
		String actioncode = param.getActioncode();
		if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
			String pk_bill = param.getPk_bill();
			OuterVO[] outerVO =  relDao.queryByPkBusibill(pk_bill);
			if(outerVO!= null){
				return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000245")/* @res"收付报单据已经引用此付票登记，无法弃审"*/;
			}
		}
		return null;
	}

}
