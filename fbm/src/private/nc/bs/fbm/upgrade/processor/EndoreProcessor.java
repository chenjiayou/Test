package nc.bs.fbm.upgrade.processor;

import java.util.List;

import nc.bs.fbm.gather.GatherBillService;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.NCLocator;
import nc.bs.trade.business.HYPubBO;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
/**
 *
 * �๦��˵����
     ���鴦����
 * ���ڣ�2007-11-20
 * ����Ա�� wues
 *
 */
public class EndoreProcessor extends AbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		EndoreVO endoreVO = (EndoreVO)specialRebuildData(vo);

		if(endoreVO.getEndoretype() == EndoreVO.OUTER){
			//�õ�������е�Pk_register
			String pk_register = endoreVO.getPk_source();
			CommonDAO dao = new CommonDAO();

			String sqlCentreCust = "select pk_cubasdoc from bd_cubasdoc where pk_corp1='"+endoreVO.getPk_corp()+"'";
			List ret = (List)dao.getBaseDAO().executeQuery(sqlCentreCust, new ColumnListProcessor());
			if(ret == null && ret.size() == 0){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000170")/*@res "�Ҳ�����Ӧ���Ĺ�˾�Ŀ��̻���������Ϣ"*/);
			}
			String pk_cubasdoc = (String)ret.get(0);

			//���ñ�����е�pk_register�õ�Ʊ����Ϣ
			RegisterVO register = (RegisterVO)dao.getBaseDAO().retrieveByPK(RegisterVO.class,pk_register);

			register.setGatherdate(endoreVO.getBusdate());
			//��ԭƱ��״̬��Ϊ����ʹ��
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, FbmActionConstant.CENTERUSE).doAction(register, FbmActionConstant.CENTERUSE,false);

			//�����µ�Ʊ�ݵ�
			IBillcodeRuleService ibrs = (IBillcodeRuleService)NCLocator.getInstance().lookup(IBillcodeRuleService.class.getName());
			String vbillno = ibrs.getBillCode_RequiresNew(FbmBusConstant.BILLTYPE_GATHER, endoreVO.getPk_corp(),null, null);
			register.setVbillno(vbillno);
			register.setPk_corp(endoreVO.getPk_corp());
			register.setPaybillunit(register.getHoldunit());
			register.setHoldunit(pk_cubasdoc);
			register.setKeepunit(pk_cubasdoc);
			register.setPk_source(pk_register);
			register.setGathertype(FbmBusConstant.GATHER_TYPE_UNISTORAGE);
			register.setVbillstatus(IBillStatus.FREE);
			register.setPrimaryKey(null);
			register.setGatherdate(endoreVO.getBusdate());


			HYBillVO billVO = new HYBillVO();
			billVO.setParentVO(register);

			GatherBillService service = new GatherBillService();
			String[] pk_arry = service.saveRegisterVos(new HYBillVO[]{billVO});

			//��Ʊ�Ĺ���,�������ɵ�Ʊ�������ŵ�ԭEndoreVO�С�
			if(pk_arry.length>0)
			{
				endoreVO.setPk_source(pk_arry[0]);
			}
			endoreVO.setOpbilltype(FbmBusConstant.BILL_UNISTORAGE);//����Ϊͳ��
		}else{
			endoreVO.setOpbilltype(FbmBusConstant.BILL_PRIVACY);//����Ϊ˽��
		}
		HYPubBO bo = new HYPubBO();

		bo.insert(endoreVO);

		//����
//		ActionParamVO[] params = DefaultParamAdapter.changeEndore2Param(billVO,
//				FbmActionConstant.SAVE);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(
//				FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.SAVE)
//				.doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.SAVE).doAction(endoreVO, FbmActionConstant.SAVE,false);

		//���
//		params = DefaultParamAdapter.changeEndore2Param(billVO,FbmActionConstant.AUDIT);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.AUDIT).doAction(params);
		endoreVO.setUpgrade(EndoreVO.UPGRADE);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.AUDIT).doAction(endoreVO, FbmActionConstant.AUDIT,false);


	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		EndoreVO endoreVO = (EndoreVO)vo;
		if(endoreVO.getEndorsee() != null && endoreVO.getEndorsee().getBytes().length > 20){
			endoreVO.setEndorsee(null);
		}
		return endoreVO;
	}

}