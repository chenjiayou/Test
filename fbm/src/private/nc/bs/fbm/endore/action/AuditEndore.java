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
		//У��Ʊ�ݶ���
		if(!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
				&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_ENDORE)
				&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000132")/*@res "ǰһ���������Ǳ��汳�鵥��"*/;
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
	 * @�޸��� zhouwb 2008-9-17
	 * @�޸�ԭ��
	 * ����ԭ��ҵ��ͨ�����Ƿ������ո����ݣ���ѡ����Ʊ�Ǽǵ��ݵģ�����Ʊ�ݱ�ǡ�
	 * ��Ϊ����ҵ��ͨ�����ż������۱���ҵ���Ƿ���������Ʊ����Ʊ�Ǽǵ��ݣ���ѡ����Ʊ�Ǽǵ��ݵģ�����Ʊ�ݱ�ǡ�
	 */
	@Override
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		int len = params.length ;
		
		for(int i = 0; i < len ; i++){
			BaseinfoVO baseinfoVO = params[i].getBaseinfoVO();
			EndoreVO endoreVO = (EndoreVO)params[i].getSuperVO();
			
			//���������ɶԷ���Ʊ�Ǽǵ�
			if(EndoreVO.UPGRADE.equals(params[i].getUpgrade())){
				return;
			}
			String endorer = endoreVO.getEndorser();
			String endoree = endoreVO.getEndorsee();//�����鵥λ���̻�������PK
			
			if (endoree == null) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000213")/* @res"ʹ��Ʊ�ݸ������ѡ�����"*/);
			}
			
			ICustBasDocQuery custQry = (ICustBasDocQuery)NCLocator.getInstance().lookup(ICustBasDocQuery.class.getName());
			
			//���ݱ����鵥λ���̻�������PK�õ�Ҫ���ɶԷ���λ��Ʊ�Ǽǵ��Ĺ�˾�֣���Ϣ��
			CubasdocVO cubasdocVO = custQry.queryCustBasDocVOByPK(endoree);
			CustBasVO custBasVO = (CustBasVO)cubasdocVO.getCustBasVO();
			
			//������ڲ����飬�����ɶԷ���λ����Ʊ�Ǽǵ�������Ϊ��������
			if(custBasVO != null){
				boolean fbmEnable = dao.productEnableByCorp(custBasVO.getPk_corp1(), FbmBusConstant.SYSCODE_FBM);
				if(custBasVO.getCustprop().intValue()!=0&& fbmEnable){//���ⲿ��λ
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
					
					//���ɶԷ�����Ʊ�Ǽǵ����ϵ���Ʊ����ȡ���Ǳ�������ϵı�������
					regVO.setGatherdate(endoreVO.getBusdate());
					
					regVO.setOrientation(FbmBusConstant.ORIEINTATION_AR);
					regVO.setPk_corp(custBasVO.getPk_corp1());
					regVO.setPk_billtypecode(FbmBusConstant.BILLTYPE_GATHER);
					regVO.setVbillstatus(IBillStatus.FREE);
					regVO.setPk_source(endoreVO.getPrimaryKey());//������Ʊ���ε���PK
					
					//����ˣ�������ڣ������ˣ��������ڡ�
					regVO.setDapprovedate(endoreVO.getDapprovedate());
					regVO.setDoperatedate(endoreVO.getDoperatedate());
					regVO.setVapproveid(endoreVO.getVapproveid());
					regVO.setVoperatorid(endoreVO.getVoperatorid());
					
					//�������һ��ʼ���
					regVO.setBrate(endoreVO.getBrate());
					regVO.setMoneyb(endoreVO.getMoneyb());
					regVO.setFrate(endoreVO.getFrate());
					regVO.setMoneyf(endoreVO.getMoneyf());
					regVO.setMoneyy(endoreVO.getMoneyy());
					
					//��Ʊ����Ƿ�򹴲����� zhouwb 2008-9-17
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
	 * TODO ����ҵ���Ƿ���������Ʊ����Ʊ�Ǽǵ�
	 * @param pk_corp
	 * 		��Ʊ�Ǽǵ�λPK
	 * @return
	 * 		true ���ɣ�false ������
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
