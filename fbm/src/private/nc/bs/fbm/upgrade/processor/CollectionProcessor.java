package nc.bs.fbm.upgrade.processor;

import java.util.List;

import nc.bs.fbm.gather.GatherBillService;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.NCLocator;
import nc.bs.trade.business.HYPubBO;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.fbm.consignbank.CollectionVO;
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
     ���հ�����
 * ���ڣ�2007-11-20
 * ����Ա�� wues
 *
 */
public class CollectionProcessor extends AbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		CollectionVO collectionVO = (CollectionVO)specialRebuildData(vo);
		CommonDAO dao = new CommonDAO();

		String sqlCentreCust = "select pk_cubasdoc from bd_cubasdoc where pk_corp1='"+collectionVO.getPk_corp()+"'";
		List ret = (List)dao.getBaseDAO().executeQuery(sqlCentreCust, new ColumnListProcessor());
		if(ret == null && ret.size() == 0){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000170")/*@res "�Ҳ�����Ӧ���Ĺ�˾�Ŀ��̻���������Ϣ"*/);
		}
		String pk_cubasdoc = (String)ret.get(0);

		String pk_register = collectionVO.getPk_source();

		RegisterVO register = (RegisterVO)dao.getBaseDAO().retrieveByPK(RegisterVO.class,pk_register);

		register.setGatherdate(collectionVO.getDconsigndate());
		//��ԭƱ��״̬��Ϊ����ʹ��
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, FbmActionConstant.CENTERUSE).doAction(register, FbmActionConstant.CENTERUSE,false);

		//�����µ�Ʊ�ݵ�
		IBillcodeRuleService ibrs = (IBillcodeRuleService)NCLocator.getInstance().lookup(IBillcodeRuleService.class.getName());
		String vbillno = ibrs.getBillCode_RequiresNew(FbmBusConstant.BILLTYPE_GATHER, collectionVO.getPk_corp(),null, null);
		register.setVbillno(vbillno);
		register.setPk_corp(collectionVO.getPk_corp());
		register.setPaybillunit(register.getHoldunit());
		register.setHoldunit(pk_cubasdoc);
		register.setKeepunit(pk_cubasdoc);
		register.setPk_source(pk_register);
		register.setGathertype(FbmBusConstant.GATHER_TYPE_UNISTORAGE);
		register.setVbillstatus(IBillStatus.FREE);
		register.setPrimaryKey(null);
		register.setGatherdate(collectionVO.getDconsigndate());


		HYBillVO billVO = new HYBillVO();
		billVO.setParentVO(register);

		GatherBillService service = new GatherBillService();
		String[] pk_arry = service.saveRegisterVos(new HYBillVO[]{billVO});


		if(pk_arry.length>0)
		{
			collectionVO.setPk_source(pk_arry[0]);
		}

		HYPubBO bo = new HYPubBO();
		collectionVO.setOpbilltype(FbmBusConstant.BILL_UNISTORAGE);//����Ϊͳ��
		bo.insert(collectionVO);

		billVO = new HYBillVO();
		billVO.setParentVO(collectionVO);

		//����
//		ActionParamVO[] params = DefaultParamAdapter.changeCollection2Param(billVO,FbmActionConstant.SAVE);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.SAVE).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.SAVE).doAction(collectionVO, FbmActionConstant.SAVE,false);

		//���
//		params = DefaultParamAdapter.changeCollection2Param(billVO,FbmActionConstant.AUDIT);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.AUDIT).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.AUDIT).doAction(collectionVO, FbmActionConstant.AUDIT,false);

		//����
//		params = DefaultParamAdapter.changeCollection2Param(billVO,FbmActionConstant.TRANSACT);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.TRANSACT).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.TRANSACT).doAction(collectionVO, FbmActionConstant.TRANSACT,false);


	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		//���ί������Ϊ�գ���ô������������
		CollectionVO collectionVO = (CollectionVO)vo;
		if(collectionVO.getDconsigndate() == null){
			collectionVO.setDconsigndate(collectionVO.getDcollectiondate());
		}
		//collectionVO.setVbillstatus(IFBMStatus.Transact);
		return collectionVO;
	}

}