package nc.bs.fbm.upgrade.processor;

import javax.naming.NamingException;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.upgrade.BaseInfoGenerator;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.StorageMiddleVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.HYBillVO;

public class InnerKeepProcessor extends AbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {
		StorageMiddleVO middleVO = (StorageMiddleVO)vo;
		StorageVO hvo = new StorageVO();
		StorageBVO bvo = new StorageBVO();
		
		BaseInfoGenerator gen;
		try {
			gen  = new BaseInfoGenerator();
			hvo.setPk_storage(gen.getPK());
			bvo.setPk_storage(hvo.getPk_storage());
			bvo.setPk_storage_b(gen.getPK());
		} catch (NamingException e) {
			throw new BusinessException(e.getMessage());
		}
		hvo.setVbillno(middleVO.getVbillno());
		hvo.setInputperson(middleVO.getInputperson());
		hvo.setKeepunit(middleVO.getKeepunit());
		hvo.setVoperatorid(middleVO.getVoperatorid());
		hvo.setDapprovedate(middleVO.getDapprovedate());
		hvo.setPk_billtypecode(FbmBusConstant.BILLTYPE_INNERKEEP);
		hvo.setVbillstatus(Integer.valueOf(IFBMStatus.INPUT_SUCCESS));//状态修改为已入库
		hvo.setVapproveid(middleVO.getVapproveid());
		hvo.setInputdate(middleVO.getInputdate());
		hvo.setInputtype(FbmBusConstant.KEEP_TYPE_RELIEF);
		hvo.setDoperatedate(middleVO.getDoperatedate());
		hvo.setPk_corp(middleVO.getPk_corp());
		hvo.setPk_currtype(middleVO.getPk_curr());
		hvo.setDiscountinterest(middleVO.getDiscountinterest());
		
		bvo.setPk_source(middleVO.getPk_source());
		bvo.setPk_baseinfo(middleVO.getPk_baseinfo());
		
		
		HYPubBO bo = new HYPubBO();
		bo.insert(hvo);
		bo.insert(bvo);
		
		//更新收票对应的keepunit,由于内贴对应的收票地点没更新
		BaseDAO dao = new BaseDAO();
		//dao.executeUpdate("update fbm_register set keepunit = '"+middleVO.getKeepunit()+"' where pk_register = '" + middleVO.getPk_source() + "'");
		
		HYBillVO billVO = new HYBillVO();
		billVO.setParentVO(hvo);
		billVO.setChildrenVO(new CircularlyAccessibleValueObject[]{bvo});
		
		//保存内部存放单
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERKEEP, FbmActionConstant.SAVE).doAction(billVO, FbmActionConstant.SAVE,false);
		
		//审核内部存放单
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERKEEP, FbmActionConstant.AUDIT).doAction(billVO, FbmActionConstant.AUDIT,false);
		
		//入库内部存放单
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERKEEP, FbmActionConstant.INPUT_SUCCESS).doAction(billVO, FbmActionConstant.INPUT_SUCCESS,false);
	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
