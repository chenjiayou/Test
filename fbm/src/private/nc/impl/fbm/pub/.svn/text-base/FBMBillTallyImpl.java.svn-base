package nc.impl.fbm.pub;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.reckon.ReckonUtil;
import nc.bs.fbm.relief.ReliefUtil;
import nc.bs.fbm.returnbill.ReturnBill2CMP;
import nc.bs.fbm.storage.StorageUtil;
import nc.bs.trade.business.HYPubBO;
import nc.itf.fbm.pub.IFBMBillTally;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;


public class FBMBillTallyImpl implements IFBMBillTally {

	public void cancelTally(HYBillVO billvo, UFDate operatedate, String operator,String pk_corp)
			throws BusinessException {
		HYPubBO bo = new HYPubBO();
		String billtype = (String)billvo.getParentVO().getAttributeValue("pk_billtypecode");
		if(isUnit(billvo,pk_corp)){
			billvo.getParentVO().setAttributeValue(StorageVO.UNITTALLY, new UFBoolean(false));
			billvo.getParentVO().setAttributeValue("tallyman", null);
			billvo.getParentVO().setAttributeValue("tallydate", null);
			bo.saveBill(billvo);
			
			if(FbmBusConstant.BILLTYPE_INNERKEEP.equals(billtype)
					|| FbmBusConstant.BILLTYPE_INNERBACK.equals(billtype)){
				//删除银行账户账.
				StorageUtil cbs = new StorageUtil();
				cbs.delCMPacc(billvo, pk_corp, operator, operatedate);
			}else if(FbmBusConstant.BILLTYPE_RELIEF.equals(billtype)){
				ReliefUtil reliefSrv = new ReliefUtil();
				reliefSrv.delCMPacc(billvo, pk_corp, operator, operatedate);
			}else if(FbmBusConstant.BILLTYPE_RECKON_RECIEPT.equals(billtype)){
				ReckonUtil reckonSrv = new ReckonUtil();
				ReckonVO reckonVO = (ReckonVO)billvo.getParentVO();
				String reckonunit = reckonVO.getReckonunit();//清算单位
				CommonDAO commonDAO = new CommonDAO();
				reckonSrv.delCMPacc(billvo, commonDAO.queryCorpByCust(reckonunit), operator, operatedate);
			}else if(FbmBusConstant.BILLTYPE_RETURN.equals(billtype)){
				ReturnBill2CMP returnCmp = new ReturnBill2CMP();
				returnCmp.delCMPacc(billvo, pk_corp, operator, operatedate);
			}

		}
	}

	public void tally(HYBillVO billvo, UFDate tallydate, String tallyman,String tally_corp)
			throws BusinessException {
		HYPubBO bo = new HYPubBO();
		String billtype = (String)billvo.getParentVO().getAttributeValue("pk_billtypecode");
		if(isUnit(billvo,tally_corp)){
			billvo.getParentVO().setAttributeValue("unittally", new UFBoolean(true));
			billvo.getParentVO().setAttributeValue("tallyman", tallyman);
			billvo.getParentVO().setAttributeValue("tallydate", tallydate);
			bo.saveBill(billvo);
			
			if(FbmBusConstant.BILLTYPE_INNERKEEP.equals(billtype)
					||FbmBusConstant.BILLTYPE_INNERBACK.equals(billtype)){
				//写银行账户账.
				StorageUtil cbs = new StorageUtil();
				cbs.insertBankAcc4Unit(billvo, tally_corp, tallyman, tallydate);
			}else if(FbmBusConstant.BILLTYPE_RELIEF.equals(billtype)){
				ReliefUtil reliefSrv = new ReliefUtil();
				reliefSrv.insertBankAcc4Unit(billvo, tally_corp, tallyman, tallydate) ;
			}else if(FbmBusConstant.BILLTYPE_RECKON_RECIEPT.equals(billtype)){
				ReckonUtil reckonSrv = new ReckonUtil();
				ReckonVO reckonVO = (ReckonVO)billvo.getParentVO();
				String reckonunit = reckonVO.getReckonunit();//清算单位
				CommonDAO commonDAO = new CommonDAO();
				reckonSrv.addCMPBank(billvo, commonDAO.queryCorpByCust(reckonunit), tallyman, tallydate) ;
				//清算不要写票据账
				//reckonSrv.addCMPBill(billvo, commonDAO.queryCorpByCust(reckonunit), tallyman, tallydate) ;
			}
		}

	}
	

	/**
	 * 判断是否单位端的记账
	 * @param billvo
	 * @param pk_corp
	 * @return
	 */
	private boolean isUnit(HYBillVO billvo,String pk_corp){
		String pk_billtypecode = (String)billvo.getParentVO().getAttributeValue("pk_billtypecode");
		boolean isUnit = false;
		if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERKEEP) || pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERBACK)){
			isUnit =  pk_corp.equals(billvo.getParentVO().getAttributeValue(StorageVO.KEEPCORP));
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RELIEF)){
			isUnit =  pk_corp.equals(billvo.getParentVO().getAttributeValue(ReliefVO.RELIEFCORP));
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RECKON_RECIEPT)){
			return true;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RETURN)){
			return !FBMProxy.getSettleCentService().isSettleCenter(pk_corp);
		}
		return isUnit;
	}
}
