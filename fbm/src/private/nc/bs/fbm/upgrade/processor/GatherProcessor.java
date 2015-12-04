package nc.bs.fbm.upgrade.processor;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/**
 * 
 * �๦��˵����
     ��Ʊ������
 * ���ڣ�2007-11-20
 * ����Ա�� wues
 *
 */
public class GatherProcessor extends AbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		RegisterVO regVO = (RegisterVO)specialRebuildData(vo);

		HYPubBO bo = new HYPubBO();
		bo.insert(regVO);
		
		//������Ʊ
//		ActionParamVO[] params = DefaultParamAdapter.changeGather2Param(billVO,FbmActionConstant.SAVE);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, FbmActionConstant.SAVE).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, FbmActionConstant.SAVE).doAction(regVO, FbmActionConstant.SAVE,false);

		//�����Ʊ
//		params = DefaultParamAdapter.changeGather2Param(billVO, FbmActionConstant.AUDIT);
//		setUpgrade(params);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, FbmActionConstant.AUDIT).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, FbmActionConstant.AUDIT).doAction(regVO, FbmActionConstant.AUDIT,false);

	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		RegisterVO regVO = (RegisterVO)vo;
		//regVO.setSfflag(new UFBoolean(true));
		//regVO.setGathertype(FbmBusConstant.GATHER_TYPE_INPUT);
		return regVO;
	}

}
