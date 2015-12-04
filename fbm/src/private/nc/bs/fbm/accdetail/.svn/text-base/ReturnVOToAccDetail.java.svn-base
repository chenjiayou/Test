package nc.bs.fbm.accdetail;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.FbmBDQueryDAO;
import nc.bs.framework.common.NCLocator;
import nc.itf.fts.pub.IAccountService;
import nc.vo.fbm.accrule.AccRuleVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.fts.account.AccDetailVO;
import nc.vo.fts.account.DetailBusVO;
import nc.vo.fts.account.IAccountConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

/**
 * 退票维护内部帐户帐
 * @author xwq
 *
 * 2008-11-7
 */
public class ReturnVOToAccDetail  extends FbmVO2AccDetail{

	public void addAccDetail(ReturnVO returnVO,ReturnBVO bvo,HYBillVO billvo,String tallyman,UFDate tallydate) throws BusinessException{
		StorageVO storageVO = (StorageVO) billvo.getParentVO();
		String pk_billtypecode = storageVO.getPk_billtypecode();
		AccRuleVO accruleVO = FBMProxy.getAccRuleService().retriveAccRef(pk_billtypecode,FbmBusConstant.ACC_INNER);
		//如果是不入账，则直接返回
		if(accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)){
			return;
		}

		String keepaccount = storageVO.getKeepaccount();
		if(keepaccount == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000123")/*@res "内部帐户为空，无法记账"*/);
		}


		AccDetailVO[] accdetailVO = null;
		if(FbmBusConstant.BILLTYPE_INNERKEEP.equals(pk_billtypecode)){
			accdetailVO = addKeepCenterAccDetail(returnVO,bvo,billvo,accruleVO,tallyman,tallydate);
		}else {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000124")/*@res "错误的单据类型"*/);
		}

		IAccountService accSrv = (IAccountService)NCLocator.getInstance().lookup(IAccountService.class);
		accSrv.addAccDetail(accdetailVO);

	}

	public void delAccDetail(ReturnVO returnVO) throws BusinessException{

		DetailBusVO detailBusVO = new DetailBusVO();
		detailBusVO.setPk_sourcebill(returnVO.getPrimaryKey());
		detailBusVO.setBusinesstype(returnVO.getPk_billtypecode());
		IAccountService accSrv = (IAccountService)NCLocator.getInstance().lookup(IAccountService.class);
		accSrv.deleteAccDetail(new DetailBusVO[]{detailBusVO});
	}

	/**
	 * 记中心端内部存放内部帐户账
	 * 只能对一条记账
	 * @param accdetailVO
	 * @throws BusinessException
	 */
	private AccDetailVO[] addKeepCenterAccDetail(ReturnVO returnVO,ReturnBVO bvo,HYBillVO billvo,AccRuleVO accruleVO,String tallyman,UFDate tallydate) throws BusinessException{
		StorageVO storageVO = (StorageVO) billvo.getParentVO();

		CommonDAO commonDAO = new CommonDAO();
		AccDetailVO[] accdetails = new AccDetailVO[1];
		accdetails[0] = new AccDetailVO();
		accdetails[0].setTallydate(tallydate);//记账日期
		accdetails[0].setTallyperson(tallyman);//记账人
		accdetails[0].setInterestdate(returnVO.getDreturndate());//起息日
		accdetails[0].setBilltype(returnVO.getPk_billtypecode());//单据类型
		accdetails[0].setVbillno(returnVO.getVbillno());//单据类型
		accdetails[0].setPk_oppcorp(storageVO.getKeepcorp());//对方公司
		accdetails[0].setPk_oppunit(storageVO.getKeepunit());//对方单位

		accdetails[0].setUnit(storageVO.getKeepcorp());//本方公司
		accdetails[0].setPk_account(storageVO.getKeepaccount());//内部帐户PK
		BaseinfoVO baseinfoVO = commonDAO.queryBaseinfoByPK(bvo.getPk_baseinfo());
		accdetails[0].setOutmoney(baseinfoVO.getMoneyy());//付款金额
		accdetails[0].setFbmbillno(baseinfoVO.getFbmbillno());//票据编号
		accdetails[0].setSfflag( IAccountConst.OUTFLAG);//方向
		accdetails[0].setSourcesystem(FbmBusConstant.SYSCODE_FBM);
		accdetails[0].setPk_currtype(storageVO.getPk_currtype());//币种
		accdetails[0].setPk_sourcebill(returnVO.getPrimaryKey());
		accdetails[0].setPk_sourcebill_b(bvo.getPrimaryKey());
		accdetails[0].setMemo(returnVO.getReturnnote());
		fillCurrKeyValue(accdetails[0]);

		return accdetails;
	}


}