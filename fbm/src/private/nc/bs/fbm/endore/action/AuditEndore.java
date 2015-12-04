package nc.bs.fbm.endore.action;

import nc.bs.fbm.gather.GatherBillService;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.bd.cust.ICustBasDocQuery;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.vo.bd.b08.CubasdocVO;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.cdm.proxy.OuterProxy;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;

public class AuditEndore<K extends EndoreVO, T extends EndoreVO> extends ActionEndore<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
		//校验票据动作
		if(!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
				&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_ENDORE)
				&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000132")/*@res "前一操作必须是保存背书单。"*/;
		}
	
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		String pk_register = param.getPk_source();
		RegisterVO reg = (RegisterVO)dao.getBaseDAO().retrieveByPK(RegisterVO.class,pk_register);
		if(reg.getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_GATHER)){
			return FbmStatusConstant.ON_ENDORE;
		}else{
			return FbmStatusConstant.ON_PAYBILL;
		}
		 
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		String pk_register = param.getPk_source();
		RegisterVO reg = (RegisterVO)dao.getBaseDAO().retrieveByPK(RegisterVO.class,pk_register);
		if(reg.getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_GATHER)){
			return FbmStatusConstant.HAS_ENDORE;
		}else{
			return FbmStatusConstant.HAS_PAYBILL;
		}
		
	}
	
	/**
	 * @修改人 zhouwb 2008-9-17
	 * @修改原因
	 * 屏蔽原有业务：通过［是否启用收付报］，勾选［收票登记单］的［已收票］标记。
	 * 改为现有业务：通过集团级参数［背书业务是否生成已收票的收票登记单］，勾选［收票登记单］的［已收票］标记。
	 */
	@Override
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		int len = params.length ;
		
		for(int i = 0; i < len ; i++){
			BaseinfoVO baseinfoVO = params[i].getBaseinfoVO();
			EndoreVO endoreVO = (EndoreVO)params[i].getSuperVO();
			
			//升级不生成对方收票登记单
			if(EndoreVO.UPGRADE.equals(params[i].getUpgrade())){
				return;
			}
			String endorer = endoreVO.getEndorser();
			String endoree = endoreVO.getEndorsee();//被背书单位客商基本档案PK
			
			if (endoree == null) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000213")/* @res"使用票据付款必须选择客商"*/);
			}
			
			ICustBasDocQuery custQry = (ICustBasDocQuery)NCLocator.getInstance().lookup(ICustBasDocQuery.class.getName());
			
			//根据被背书单位客商基本档案PK得到要生成对方单位收票登记单的公司ＶＯ信息。
			CubasdocVO cubasdocVO = custQry.queryCustBasDocVOByPK(endoree);
			CustBasVO custBasVO = (CustBasVO)cubasdocVO.getCustBasVO();
			
			//如果是内部背书，则生成对方单位的收票登记单，类型为背书生成
			if(custBasVO != null){
				boolean fbmEnable = dao.productEnableByCorp(custBasVO.getPk_corp1(), FbmBusConstant.SYSCODE_FBM);
				if(custBasVO.getCustprop().intValue()!=0&& fbmEnable){//非外部单位
					RegisterVO regVO = new RegisterVO();
					IBillcodeRuleService billnoService = (IBillcodeRuleService) NCLocator
					.getInstance().lookup(
							IBillcodeRuleService.class.getName());
					regVO.setVbillno(billnoService.getBillCode_RequiresNew(
							FbmBusConstant.BILLTYPE_GATHER, custBasVO
							.getPk_corp1(), null, null));
					regVO.setPk_baseinfo(baseinfoVO.getPrimaryKey());
					regVO.setPaybillunit(endorer);
					regVO.setHoldunit(endoree);
					regVO.setKeepunit(endoree);
					regVO.setGathertype(FbmBusConstant.GATHER_TYPE_ENDORE);
					
					//生成对方的收票登记单的上的收票日期取的是背书办理单上的背书日期
					regVO.setGatherdate(endoreVO.getBusdate());
					
					regVO.setOrientation(FbmBusConstant.ORIEINTATION_AR);
					regVO.setPk_corp(custBasVO.getPk_corp1());
					regVO.setPk_billtypecode(FbmBusConstant.BILLTYPE_GATHER);
					regVO.setVbillstatus(IBillStatus.FREE);
					regVO.setPk_source(endoreVO.getPrimaryKey());//设置收票上游单据PK
					
					//审核人，审核日期，操作人，操作日期。
					regVO.setDapprovedate(endoreVO.getDapprovedate());
					regVO.setDoperatedate(endoreVO.getDoperatedate());
					regVO.setVapproveid(endoreVO.getVapproveid());
					regVO.setVoperatorid(endoreVO.getVoperatorid());
					
					//本、辅币汇率及金额。
					regVO.setBrate(endoreVO.getBrate());
					regVO.setMoneyb(endoreVO.getMoneyb());
					regVO.setFrate(endoreVO.getFrate());
					regVO.setMoneyf(endoreVO.getMoneyf());
					regVO.setMoneyy(endoreVO.getMoneyy());
					
					//收票标记是否打勾操作。 zhouwb 2008-9-17
					regVO.setSfflag(isMakeYsp(regVO.getPk_corp()));
					
					HYBillVO billVO = new HYBillVO();
					billVO.setParentVO(regVO);
					
					try {
						GatherBillService service = new GatherBillService();
						service.saveRegisterVos(new HYBillVO[]{billVO});
					} catch (Exception e) {
						throw new BusinessException(e.getMessage());
					}
				}
			}

		
		}
		
	}
	
	/**
	 * TODO 背书业务是否生成已收票的收票登记单
	 * @param pk_corp
	 * 		收票登记单位PK
	 * @return
	 * 		true 生成，false 不生成
	 */
	private UFBoolean isMakeYsp(String pk_corp) throws BusinessException {
		UFBoolean isTogather = OuterProxy.getSysInitQry().getParaBoolean(pk_corp, FBMParamConstant.FBM005);
		if(isTogather.booleanValue()){
			return UFBoolean.FALSE;
		}else{
			return OuterProxy.getSysInitQry().getParaBoolean(pk_corp, FBMParamConstant.FBM006);
		}
	}

}
