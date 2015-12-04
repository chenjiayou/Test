package nc.bs.fbm.accpet.action;

import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.IBillStatus;

public class SaveAccept<K extends AcceptVO, T extends AcceptVO> extends ActionAccept<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {

		ActionVO actionVO = param.getLastActionVO();
		if(actionVO == null){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000238")/* @res"找不到票据动作"*/;
		}

		//2007.11.27 xwq承兑付款日期在票据到期日之后可不校验状态
		if(actionVO.getEndstatus().equals(FbmStatusConstant.HAS_DESTROY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000318")/* @res"已核销，无法承兑"*/;
		}

		if(actionVO.getEndstatus().equals(FbmStatusConstant.HAS_PAY) || actionVO.getEndstatus().equals(FbmStatusConstant.ON_PAY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/+ param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000319")/* @res"在付款或已付款，无法承兑"*/;
		}

		//校验业务日期
		if(actionVO.getActiondate().after(param.getActiondate())){
			return param.getBaseinfoVO().getFbmbillno()+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000320")/* @res"的业务日期早于前一操作业务日期"*/;
		}

		//校验付票是否审核
		RegisterVO regVO = param.getRegisterVO();
		if(regVO != null){
			if(regVO.getVbillstatus().intValue() != IBillStatus.CHECKPASS && regVO.getVbillstatus().intValue() !=  IFBMStatus.Create){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000321")/* @res"付票登记单状态必须为审核通过或已生成单据"*/);
			}
		}

		//校验是否已生成划账单
//		ICreateCorpQueryService ProductService = (ICreateCorpQueryService) NCLocator.getInstance().lookup(ICreateCorpQueryService.class.getName());
//		Hashtable productEnabled = ProductService.queryProductEnabled(param.getRegisterVO().getPk_corp(),new String[]{ProductCode.PROD_EP});
//		if(((UFBoolean)productEnabled.get(ProductInfo.pro_EP)).booleanValue()){//启用了收付报才校验
//			OuterRelationDAO relDao = new OuterRelationDAO();
//			if(!relDao.isHjEffect(param.getPk_source())){
//				throw  new BusinessException("付票登记的划账结算单未生成或未生效");
//			}
//		}

		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		return FbmStatusConstant.HAS_PAYBILL;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.ON_PAY;
	}

}