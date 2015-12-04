package nc.bs.fbm.accdetail;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.framework.common.NCLocator;
import nc.itf.fts.pub.IAccountService;
import nc.vo.fbm.accrule.AccRuleVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fts.account.AccDetailVO;
import nc.vo.fts.account.DetailBusVO;
import nc.vo.fts.account.IAccountConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

/**
 * 调剂单维护内部帐户帐
 * @author xwq
 *
 * 2008-9-16
 */
public class ReliefVOToAccDetail extends FbmVO2AccDetail{

	public void addAccDetail(HYBillVO billvo,String tallyman,UFDate tallydate) throws BusinessException{

		ReliefVO reliefVO = (ReliefVO) billvo.getParentVO();
		ReliefBVO[] bvos = (ReliefBVO[])billvo.getChildrenVO();
		String reliefAccount = reliefVO.getInneracc();
		AccRuleVO accruleVO = FBMProxy.getAccRuleService().retriveAccRef(FbmBusConstant.BILLTYPE_RELIEF,FbmBusConstant.ACC_INNER);
		//如果是不入账，则直接返回
		if(accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE) ){
			return;
		}

		if(reliefAccount == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000122")/*@res "调剂内部帐户为空，无法记账"*/);
		}


		if(bvos !=null && bvos.length > 0){
			AccDetailVO[] details = new AccDetailVO[bvos.length];
			CommonDAO commonDAO = new CommonDAO();
			for(int i =0 ; i < details.length ;i++){
				details[i] = new AccDetailVO();
				details[i].setTallydate(tallydate);
				details[i].setTallyperson(tallyman);
				details[i].setInterestdate(reliefVO.getDapprovedate());
				details[i].setBilltype(reliefVO.getPk_billtypecode());
				details[i].setVbillno(reliefVO.getVbillno());
				details[i].setUnit(reliefVO.getPk_corp());
				details[i].setPk_oppcorp(reliefVO.getReliefcorp());
				details[i].setPk_oppunit(reliefVO.getReliefunit());
				details[i].setPk_account(reliefVO.getInneracc());
				details[i].setInmoney(reliefVO.getSummoney());
				details[i].setSfflag(IAccountConst.INFLAG);
				details[i].setSourcesystem(FbmBusConstant.SYSCODE_FBM);
				details[i].setPk_currtype(reliefVO.getPk_currtype());
				details[i].setPk_sourcebill(reliefVO.getPrimaryKey());
				details[i].setMemo(reliefVO.getMemo());
				fillCurrKeyValue(details[i]);

				details[i].setFbmbillno(commonDAO.queryBaseinfoByPK(bvos[i].getPk_baseinfo()).getFbmbillno());
			}
			IAccountService accSrv = (IAccountService)NCLocator.getInstance().lookup(IAccountService.class);
			accSrv.addAccDetail(details);
		}

	}


	public void delAccDetail(ReliefVO reliefVO) throws BusinessException{
		AccRuleVO accruleVO = FBMProxy.getAccRuleService().retriveAccRef(FbmBusConstant.BILLTYPE_RELIEF,FbmBusConstant.ACC_INNER);
		//如果是不入账，则直接返回
		if(accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)){
			return;
		}

		String reliefAccount = reliefVO.getInneracc();
		if(reliefAccount == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000122")/*@res "调剂内部帐户为空，无法记账"*/);
		}
		DetailBusVO detailBusVO = new DetailBusVO();
		detailBusVO.setPk_sourcebill(reliefVO.getPrimaryKey());
		detailBusVO.setBusinesstype(reliefVO.getPk_billtypecode());
		IAccountService accSrv = (IAccountService)NCLocator.getInstance().lookup(IAccountService.class);
		accSrv.deleteAccDetail(new DetailBusVO[]{detailBusVO});
	}

}