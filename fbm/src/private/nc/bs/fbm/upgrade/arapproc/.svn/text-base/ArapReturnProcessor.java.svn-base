package nc.bs.fbm.upgrade.arapproc;

import javax.naming.NamingException;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.upgrade.BaseInfoGenerator;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.ReturnMiddleVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * 
 * 类功能说明： 退票处理 日期：2007-11-20 程序员： wues
 * 
 */
public class ArapReturnProcessor extends ArapAbstractDataProcessor {

	public void buildData(SuperVO vo) throws BusinessException {

		ReturnMiddleVO middleVO = (ReturnMiddleVO) vo;
		ReturnVO returnVO = new ReturnVO();
		ReturnBVO returnBVO = new ReturnBVO();

		returnVO.setPk_return(middleVO.getPk_return());
		returnVO.setPk_corp(middleVO.getPk_corp());
		returnVO.setReturnnote(middleVO.getReturnnote());
		returnVO.setReturnperson(middleVO.getReturnperson());
		returnVO.setReturntype(middleVO.getReturntype());
		returnVO.setPk_billtypecode(FbmBusConstant.BILLTYPE_RETURN);
		returnVO.setDreturndate(middleVO.getDreturndate());
		returnVO.setBusdate(middleVO.getBusdate());
		returnVO.setVoperatorid(middleVO.getVoperatorid());
		returnVO.setDoperatedate(middleVO.getDoperatedate());
		returnVO.setVapproveid(middleVO.getVapproveid());
		returnVO.setDapprovedate(middleVO.getDapprovedate());
		returnVO.setVapprovenote(middleVO.getVapprovenote());
		returnVO.setVbillstatus(middleVO.getVbillstatus());
		returnVO.setVbillno(middleVO.getVbillno());

		BaseInfoGenerator gen;
		try {
			gen = new BaseInfoGenerator();
			returnBVO.setPk_return_b(gen.getPK());
		} catch (NamingException e) {
			throw new BusinessException(e.getMessage());
		}
		returnBVO.setPk_source(middleVO.getPk_source());
		returnBVO.setPk_baseinfo(middleVO.getPk_baseinfo());
		returnBVO.setPk_return(middleVO.getPk_return());

		HYPubBO bo = new HYPubBO();
		bo.insert(returnVO);
		bo.insert(returnBVO);

		HYBillVO billVO = new HYBillVO();
		billVO.setParentVO(returnVO);
		billVO
				.setChildrenVO(new CircularlyAccessibleValueObject[] { returnBVO });

		// 保存
		BusiActionFactory.getInstance().createActionClass(
				FbmBusConstant.BILLTYPE_RETURN, FbmActionConstant.SAVE)
				.doAction(billVO, FbmActionConstant.SAVE, false);

		// 审核
		BusiActionFactory.getInstance().createActionClass(
				FbmBusConstant.BILLTYPE_RETURN, FbmActionConstant.AUDIT)
				.doAction(billVO, FbmActionConstant.AUDIT, false);

	}

	public SuperVO specialRebuildData(SuperVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
