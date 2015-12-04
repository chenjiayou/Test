package nc.bs.fbm.endore.action;

import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

public class SaveEndore<K extends EndoreVO, T extends EndoreVO> extends ActionEndore<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;
		RegisterVO regvo = param.getRegisterVO();
		UFBoolean ufb = regvo.getSfflag();

		if(FbmBusConstant.BILLTYPE_GATHER.equals(param.getRegisterVO().getPk_billtypecode())&& false==ufb.booleanValue())
		{
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000138")/*@res "应收票据未完成收票操作"*/;
		}

		if(!regvo.getPrimaryKey().equals(param.getLastActionVO().getPk_source())){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000025")/*@res "背书办理单无法保存，可能是票据已背书"*/);
		}
		//判断收付报是否启用，如果启用并且单据类别为私有时，不能进行保存操作
//		String[] test_str = new String[]{FbmBusConstant.AR,FbmBusConstant.AP,FbmBusConstant.EP};
//		EndoreService es = new EndoreService();
//		EndoreVO endorevo = (EndoreVO)param.getSuperVO();
//		String opbilltype = endorevo.getOpbilltype();
//		String syscode = endorevo.getSyscode()==null?"":endorevo.getSyscode();
//
//		if (!syscode.equals(FbmBusConstant.SYSCODE_ARAP)&&es.productEnableByCorp(param.getPk_corp(), test_str)
//				&& FbmBusConstant.BILL_PRIVACY.equals(opbilltype)) {
//			return "已启用收付报不能进行此操作";
//		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		String pk_register = param.getPk_source();
		RegisterVO reg = (RegisterVO)dao.getBaseDAO().retrieveByPK(RegisterVO.class,pk_register);
		if(reg.getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_GATHER)){
			return FbmStatusConstant.REGISTER;
		}else{
			return FbmStatusConstant.HAS_INVOICE;
		}
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		String pk_register = param.getPk_source();
		RegisterVO reg = (RegisterVO)dao.getBaseDAO().retrieveByPK(RegisterVO.class,pk_register);
		if(reg.getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_GATHER)){
			return FbmStatusConstant.ON_ENDORE;
		}else{
			return FbmStatusConstant.ON_PAYBILL;
		}
	}

}