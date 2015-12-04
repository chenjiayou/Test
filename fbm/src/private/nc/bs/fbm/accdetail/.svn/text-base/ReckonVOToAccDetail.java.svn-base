package nc.bs.fbm.accdetail;

import java.util.ArrayList;
import java.util.List;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.framework.common.NCLocator;
import nc.itf.fts.pub.IAccountService;
import nc.itf.uap.bd.cust.ICuBasDocQry;
import nc.vo.bd.b08.CubasdocVO;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.fbm.accrule.AccRuleVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fts.account.AccDetailVO;
import nc.vo.fts.account.DetailBusVO;
import nc.vo.fts.account.IAccountConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

/**
 * 清算单维护内部账户账
 * @author xwq
 *
 * 2008-9-16
 */
public class ReckonVOToAccDetail extends FbmVO2AccDetail{

	public void addAccDetail(HYBillVO billvo,String tallyman,UFDate tallydate) throws BusinessException{

		ReckonVO reckonVO = (ReckonVO) billvo.getParentVO();
		ReckonBVO[] bvos = (ReckonBVO[])billvo.getChildrenVO();
		AccRuleVO accruleVO = FBMProxy.getAccRuleService().retriveAccRef(FbmBusConstant.BILLTYPE_LIQUIDATE,FbmBusConstant.ACC_IN);
		//如果是不入账，则直接返回
		if(accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)){
			return;
		}
		accruleVO = FBMProxy.getAccRuleService().retriveAccRef(FbmBusConstant.BILLTYPE_LIQUIDATE,FbmBusConstant.ACC_OUT);
		//如果是不入账，则直接返回
		if(accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)){
			return;
		}

		String inacc  = reckonVO.getInacc();
		String outacc = reckonVO.getOutacc();
		if(inacc ==null || outacc == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000121")/*@res "没有转入帐户或转出帐户"*/);
		}


		ICuBasDocQry cubasSrv = (ICuBasDocQry)NCLocator.getInstance().lookup(ICuBasDocQry.class);
		//清算单位客商
		CubasdocVO reckonCust  = cubasSrv.findCubasdocVOByPK(reckonVO.getReckonunit());
		//中心对应客商
		String pk_centCust = cubasSrv.getInnerCustByPkCorp1(reckonVO.getPk_corp(),reckonVO.getPk_corp()).getPrimaryKey();


		List<AccDetailVO> detailList = new ArrayList<AccDetailVO>();
		CommonDAO commonDAO = new CommonDAO();
		for(ReckonBVO b:bvos){
			BaseinfoVO base  = commonDAO.queryBaseinfoByPK(b.getPk_baseinfo());
			AccDetailVO accdetailIn = new AccDetailVO();
			accdetailIn.setTallydate(tallydate);
			accdetailIn.setTallyperson(tallyman);
			accdetailIn.setInterestdate(reckonVO.getDapprovedate());
			accdetailIn.setBilltype(reckonVO.getPk_billtypecode());
			accdetailIn.setVbillno(reckonVO.getVbillno());
			accdetailIn.setUnit(reckonVO.getPk_corp());
			accdetailIn.setPk_oppcorp(((CustBasVO)reckonCust.getParentVO()).getPk_corp1());
			accdetailIn.setPk_oppunit(((CustBasVO)reckonCust.getParentVO()).getPrimaryKey());
			accdetailIn.setPk_account(reckonVO.getInacc());
			if(b.getMoneyy().doubleValue() > 0){
				accdetailIn.setInmoney(b.getMoneyy());
				accdetailIn.setSfflag(IAccountConst.INFLAG);
			}else{
				accdetailIn.setOutmoney(b.getMoneyy().multiply(-1));
				accdetailIn.setSfflag(IAccountConst.OUTFLAG);
			}


			accdetailIn.setSourcesystem(FbmBusConstant.SYSCODE_FBM);
			accdetailIn.setPk_currtype(reckonVO.getPk_curr());
			accdetailIn.setPk_sourcebill(reckonVO.getPrimaryKey());
			accdetailIn.setMemo(reckonVO.getNote());
			accdetailIn.setFbmbillno(base.getFbmbillno());
			fillCurrKeyValue(accdetailIn);

			AccDetailVO accdetailOut = new AccDetailVO();
			accdetailOut.setTallydate(tallydate);
			accdetailOut.setTallyperson(tallyman);
			accdetailOut.setInterestdate(reckonVO.getDapprovedate());
			accdetailOut.setBilltype(reckonVO.getPk_billtypecode());
			accdetailOut.setVbillno(reckonVO.getVbillno());
			accdetailOut.setUnit(((CustBasVO)reckonCust.getParentVO()).getPk_corp1());
			accdetailOut.setPk_oppcorp(reckonVO.getPk_corp());
			accdetailOut.setPk_oppunit(pk_centCust);
			accdetailOut.setPk_account(reckonVO.getOutacc());

			if(b.getMoneyy().doubleValue() > 0){
				accdetailOut.setOutmoney(b.getMoneyy());
				accdetailOut.setSfflag(IAccountConst.OUTFLAG);
			}else{
				accdetailOut.setInmoney(b.getMoneyy().multiply(-1));
				accdetailOut.setSfflag(IAccountConst.INFLAG);
			}

			accdetailOut.setSourcesystem(FbmBusConstant.SYSCODE_FBM);
			accdetailOut.setPk_currtype(reckonVO.getPk_curr());
			accdetailOut.setPk_sourcebill(reckonVO.getPrimaryKey());
			accdetailOut.setFbmbillno(base.getFbmbillno());
			accdetailOut.setMemo(reckonVO.getNote());
			fillCurrKeyValue(accdetailOut);

			detailList.add(accdetailIn);
			//NCdp200673179 lx:调剂清算时，当清算金额为负时，转出账户即票据户应为空
			if(b.getMoneyy().doubleValue() > 0){
				detailList.add(accdetailOut);
			}
		}

		IAccountService accSrv = (IAccountService)NCLocator.getInstance().lookup(IAccountService.class);
		accSrv.addAccDetail((AccDetailVO[])detailList.toArray(new AccDetailVO[0]));
	}


	public void delAccDetail(ReckonVO reckonVO) throws BusinessException{
		AccRuleVO accruleVO = FBMProxy.getAccRuleService().retriveAccRef(FbmBusConstant.BILLTYPE_LIQUIDATE,FbmBusConstant.ACC_IN);
		//如果是不入账，则直接返回
		if(accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)){
			return;
		}
		accruleVO = FBMProxy.getAccRuleService().retriveAccRef(FbmBusConstant.BILLTYPE_LIQUIDATE,FbmBusConstant.ACC_OUT);
		//如果是不入账，则直接返回
		if(accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)){
			return;
		}

		String inacc  = reckonVO.getInacc();
		String outacc = reckonVO.getOutacc();
		if(inacc ==null || outacc == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000121")/*@res "没有转入帐户或转出帐户"*/);
		}

		DetailBusVO[] detailBusVos = new DetailBusVO[2];
		detailBusVos[0] = new DetailBusVO();
		detailBusVos[0].setPk_sourcebill(reckonVO.getPrimaryKey());
		detailBusVos[0].setPk_account(reckonVO.getInacc());

		detailBusVos[1] = new DetailBusVO();
		detailBusVos[1].setPk_sourcebill(reckonVO.getPrimaryKey());
		detailBusVos[1].setPk_account(reckonVO.getOutacc());
		IAccountService accSrv = (IAccountService)NCLocator.getInstance().lookup(IAccountService.class);
		accSrv.deleteAccDetail(detailBusVos);
	}
}