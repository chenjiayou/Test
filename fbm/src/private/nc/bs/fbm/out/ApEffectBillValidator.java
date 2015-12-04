package nc.bs.fbm.out;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.gather.GatherBillService;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.IBillStatus;

public class ApEffectBillValidator extends AbstractBillValidator {

	public void checkBaseinfo(ArapBillParamVO param) throws BusinessException {

	}

	public void doCheck(ArapBillParamVO param) throws BusinessException {

		if (billnoSet.contains(param.getFbmbillno())) {
			return;
		}
		if(param.getNewActionVO() == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000389")/* @res"�Ҳ���Ʊ��ҵ�����"*/);
		}
		
		String curStatus = param.getNewActionVO().getEndstatus();
		if (!(curStatus.equals(FbmStatusConstant.ON_ENDORE) || curStatus
				.equals(FbmStatusConstant.ON_PAYBILL))) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000211")/* @res"Ʊ��״̬�����ڱ�����ڸ�Ʊ"*/);
		}
	}

	@Override
	public void beforeAction(ArapBillParamVO param) throws BusinessException {

	}

	public void afterAction(ArapBillParamVO param) throws BusinessException {
		BaseDAO dao = new BaseDAO();
		RegisterVO registerVO = (RegisterVO) dao.retrieveByPK(RegisterVO.class,
				param.getNewActionVO().getPk_source());

		if (!billnoSet.contains(param.getFbmbillno())) {
			List endoreList = (List) dao.retrieveByClause(EndoreVO.class,
					" isnull(dr,0)=0 and vbillstatus = 8 and pk_source = '"
							+ registerVO.getPrimaryKey() + "'");
			EndoreVO[] vos = (EndoreVO[]) endoreList.toArray(new EndoreVO[0]);
			if (vos == null || vos.length == 0) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000212")/* @res"�Ҳ����������"*/);
			}
			
			vos[0].setVbillstatus(IBillStatus.CHECKPASS);
			vos[0].setVapproveid(param.getVeffector());
			//vos[0].setDapprovedate(param.getDeffectdate());
			vos[0].setDapprovedate(param.getDjrq());
			
			vos[0].setNote(param.getMemo());//��ע
			
			dao.updateVO(vos[0]);
			
			
			
			// ��˱������
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.AUDIT).doAction(vos[0], FbmActionConstant.AUDIT, false);

			// ���ڲ�������ʽ��������˵���Ʊ�Ǽǵ�
//			String endorer = param.getCurrentunit();// ������
//			String endoree = param.getOtherunit();// �����鵥λ



//			ICustBasDocQuery custQry = (ICustBasDocQuery) NCLocator
//					.getInstance().lookup(ICustBasDocQuery.class.getName());
//			CubasdocVO cubasdocVO = custQry.queryCustBasDocVOByPK(endoree);
//			CustBasVO custBasVO = (CustBasVO) cubasdocVO.getCustBasVO();
//
//				GatherBillService service = new GatherBillService();
//
//				boolean fbmEnable = comDao.productEnableByCorp(custBasVO.getPk_corp1(), FbmBusConstant.SYSCODE_FBM);
//				// ������ڲ����飬�����ɶԷ���λ����Ʊ�Ǽǵ�������Ϊ��������
//				if (custBasVO.getCustprop().intValue() != 0 && fbmEnable) {// ���ⲿ��λ
//					RegisterVO regVO = new RegisterVO();
//					IBillcodeRuleService billnoService = (IBillcodeRuleService) NCLocator
//							.getInstance().lookup(
//									IBillcodeRuleService.class.getName());
//					regVO.setVbillno(billnoService.getBillCode_RequiresNew(
//							FbmBusConstant.BILLTYPE_GATHER, custBasVO
//									.getPk_corp1(), null, null));
//					regVO.setPk_baseinfo(vos[0].getPk_baseinfo());
//
//					regVO.setPaybillunit(endorer);
//					regVO.setHoldunit(endoree);
//					regVO.setKeepunit(endoree);
//					regVO.setGathertype(FbmBusConstant.GATHER_TYPE_ENDORE);
//					regVO.setGatherdate(param.getDjrq());
//					regVO.setOrientation(FbmBusConstant.ORIEINTATION_AR);
//					regVO.setPk_corp(custBasVO.getPk_corp1());
//					regVO.setPk_billtypecode(FbmBusConstant.BILLTYPE_GATHER);
//					regVO.setVbillstatus(IBillStatus.FREE);
//					regVO.setPk_source(vos[0].getPrimaryKey());// ������Ʊ���ε���PK
//					regVO.setVoperatorid(param.getVeffector());
//					regVO.setDoperatedate(param.getDeffectdate());
//					regVO.setSfflag(new UFBoolean(false));
//
//					regVO.setMoneyy(param.getMoneyy());
//					regVO.setMoneyf(param.getMoneyf());
//					regVO.setMoneyb(param.getMoneyb());
//					regVO.setFrate(param.getFrate());
//					regVO.setBrate(param.getBrate());
//
//					HYBillVO tmpVO = new HYBillVO();
//					tmpVO.setParentVO(regVO);
//					service.saveRegisterVos(new HYBillVO[] { tmpVO });
//				}
				// ���õǼǱ��ո����־
			GatherBillService service = new GatherBillService();
			if (FbmBusConstant.BILLTYPE_INVOICE.equals(registerVO
						.getPk_billtypecode())) {
					service.updateRegisterSfflag(registerVO.getPrimaryKey(),
							true);
			}
		}

		// ά���ⲿ����
		OuterRelationDAO relDao = new OuterRelationDAO();
		relDao.effectRelation(param.getOuterVO().getPk_outerbill_b());

		super.afterAction(param);
	}

}