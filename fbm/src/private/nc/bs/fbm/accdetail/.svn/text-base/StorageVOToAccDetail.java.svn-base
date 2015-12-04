package nc.bs.fbm.accdetail;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.FbmBDQueryDAO;
import nc.bs.framework.common.NCLocator;
import nc.itf.fts.pub.IAccountService;
import nc.vo.fbm.accrule.AccRuleVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.fts.account.AccDetailVO;
import nc.vo.fts.account.AccMessageVO;
import nc.vo.fts.account.DetailBusVO;
import nc.vo.fts.account.IAccountConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

/**
 * 内部托管和内部领用维护内部帐户账
 * 
 * @author xwq
 * 
 *         2008-9-16
 */
public class StorageVOToAccDetail extends FbmVO2AccDetail {

	/**
	 * 增加内部账户账
	 * 
	 * @param storageVO
	 * @param isCenter
	 *            是否中心记账
	 */
	public void addAccDetail(HYBillVO billvo, String pk_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		StorageVO storageVO = (StorageVO) billvo.getParentVO();
		String pk_billtypecode = storageVO.getPk_billtypecode();
		AccRuleVO accruleVO = FBMProxy.getAccRuleService().retriveAccRef(pk_billtypecode, FbmBusConstant.ACC_INNER);
		// 如果是不入账，则直接返回
		if (accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)) {
			return;
		}

		// 如果是保管，则直接返回
		if (FbmBusConstant.KEEP_TYPE_STORE.equals(storageVO.getInputtype())) {
			return;
		}

		String keepaccount = storageVO.getKeepaccount();
		if (keepaccount == null) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000123")/*
																										 * @res
																										 * "内部帐户为空，无法记账"
																										 */);
		}

		AccDetailVO[] accdetailVO = null;
		if (FbmBusConstant.BILLTYPE_INNERKEEP.equals(pk_billtypecode)) {
			accdetailVO = addKeepCenterAccDetail(billvo, accruleVO, tallyman, tallydate);
		} else if (FbmBusConstant.BILLTYPE_INNERBACK.equals(pk_billtypecode)) {
			accdetailVO = addBackCenterAccDetail(billvo, accruleVO, tallyman, tallydate);
		} else {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000124")/*
																										 * @res
																										 * "错误的单据类型"
																										 */);
		}

		IAccountService accSrv = (IAccountService) NCLocator.getInstance().lookup(IAccountService.class);
		AccMessageVO[] result = accSrv.addAccDetail(accdetailVO);

		dealInnerAccountException(result);

	}

	public void delAccDetail(HYBillVO billvo, String pk_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		StorageVO storageVO = (StorageVO) billvo.getParentVO();
		String pk_billtypecode = storageVO.getPk_billtypecode();
		AccRuleVO accruleVO = FBMProxy.getAccRuleService().retriveAccRef(pk_billtypecode, FbmBusConstant.ACC_INNER);
		// 如果是不入账，则直接返回
		if (accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)) {
			return;
		}
		String keepaccount = storageVO.getKeepaccount();
		if (keepaccount == null) {
			return;// 没有内部帐户，不记内部账户账
		}
		DetailBusVO detailBusVO = new DetailBusVO();
		detailBusVO.setPk_sourcebill(storageVO.getPrimaryKey());
		detailBusVO.setBusinesstype(storageVO.getPk_billtypecode());
		IAccountService accSrv = (IAccountService) NCLocator.getInstance().lookup(IAccountService.class);
		accSrv.deleteAccDetail(new DetailBusVO[] { detailBusVO });

	}

	/**
	 * 记中心端内部存放内部帐户账
	 * 
	 * @param accdetailVO
	 * @throws BusinessException
	 */
	private AccDetailVO[] addKeepCenterAccDetail(HYBillVO billvo,
			AccRuleVO accruleVO, String tallyman, UFDate tallydate)
			throws BusinessException {
		StorageVO storageVO = (StorageVO) billvo.getParentVO();

		StorageBVO[] bvos = (StorageBVO[]) billvo.getChildrenVO();

		AccDetailVO[] accdetails = new AccDetailVO[bvos.length];
		FbmBDQueryDAO bdDao = new FbmBDQueryDAO();
		CommonDAO commonDAO = new CommonDAO();

		for (int i = 0; i < accdetails.length; i++) {
			accdetails[i] = new AccDetailVO();
			accdetails[i].setTallydate(tallydate);// 记账日期
			accdetails[i].setTallyperson(tallyman);// 记账人
			accdetails[i].setInterestdate(storageVO.getInputdate());// 起息日
			accdetails[i].setBilltype(storageVO.getPk_billtypecode());// 单据类型
			accdetails[i].setVbillno(storageVO.getVbillno());// 单据编码
			accdetails[i].setPk_oppcorp(storageVO.getKeepcorp());// 对方公司
			accdetails[i].setPk_oppunit(storageVO.getKeepunit());// 对方单位
			String cent_corp = bdDao.retriveSettleCenterBySubCorp(storageVO.getKeepcorp()).getPk_corp();
			accdetails[i].setUnit(cent_corp);// 本方公司
			accdetails[i].setPk_account(storageVO.getKeepaccount());// 内部帐户PK
			BaseinfoVO baseinfoVO = commonDAO.queryBaseinfoByPK(bvos[i].getPk_baseinfo());

			accdetails[i].setInmoney(baseinfoVO.getMoneyy());// 收款金额
			accdetails[i].setFbmbillno(baseinfoVO.getFbmbillno());// 票据编号
			accdetails[i].setSfflag(IAccountConst.INFLAG);// 方向
			accdetails[i].setSourcesystem(FbmBusConstant.SYSCODE_FBM);
			accdetails[i].setPk_currtype(storageVO.getPk_currtype());// 币种
			accdetails[i].setPk_sourcebill(storageVO.getPrimaryKey());
			accdetails[i].setPk_sourcebill_b(bvos[i].getPrimaryKey());
			accdetails[i].setMemo(storageVO.getMemo());
			accdetails[i].setIscheckBalance(UFBoolean.TRUE.equals(storageVO.getWriteinneracc()) ? UFBoolean.FALSE
					: UFBoolean.TRUE);
			fillCurrKeyValue(accdetails[i]);
		}

		return accdetails;
	}

	/**
	 * 
	 * @param accdetailVO
	 * @throws BusinessException
	 */
	private AccDetailVO[] addBackCenterAccDetail(HYBillVO billvo,
			AccRuleVO accruleVO, String tallyman, UFDate tallydate)
			throws BusinessException {

		StorageVO storageVO = (StorageVO) billvo.getParentVO();

		StorageBVO[] bvos = (StorageBVO[]) billvo.getChildrenVO();

		AccDetailVO[] accdetails = new AccDetailVO[bvos.length];
		FbmBDQueryDAO bdDao = new FbmBDQueryDAO();
		CommonDAO commonDAO = new CommonDAO();

		for (int i = 0; i < accdetails.length; i++) {
			accdetails[i] = new AccDetailVO();
			accdetails[i].setTallydate(tallydate);// 记账日期
			accdetails[i].setTallyperson(tallyman);// 记账人
			accdetails[i].setInterestdate(storageVO.getOutputdate());// 起息日
			accdetails[i].setBilltype(storageVO.getPk_billtypecode());// 单据类型
			accdetails[i].setVbillno(storageVO.getVbillno());// 单据类型
			String cent_corp = bdDao.retriveSettleCenterBySubCorp(storageVO.getKeepcorp()).getPk_corp();
			accdetails[i].setPk_oppcorp(storageVO.getKeepcorp());// 对方公司
			accdetails[i].setPk_oppunit(storageVO.getKeepunit());// 对方单位

			accdetails[i].setUnit(storageVO.getKeepcorp());// 本方公司
			accdetails[i].setPk_account(storageVO.getKeepaccount());// 内部帐户PK
			BaseinfoVO baseinfoVO = commonDAO.queryBaseinfoByPK(bvos[i].getPk_baseinfo());
			accdetails[i].setOutmoney(baseinfoVO.getMoneyy());// 付款金额
			accdetails[i].setFbmbillno(baseinfoVO.getFbmbillno());// 票据编号
			accdetails[i].setSfflag(IAccountConst.OUTFLAG);// 方向
			accdetails[i].setSourcesystem(FbmBusConstant.SYSCODE_FBM);
			accdetails[i].setPk_currtype(storageVO.getPk_currtype());// 币种
			accdetails[i].setPk_sourcebill(storageVO.getPrimaryKey());
			accdetails[i].setPk_sourcebill_b(bvos[i].getPrimaryKey());
			accdetails[i].setMemo(storageVO.getMemo());
			accdetails[i].setIscheckBalance(UFBoolean.TRUE.equals(storageVO.getWriteinneracc()) ? UFBoolean.FALSE
					: UFBoolean.TRUE);
			fillCurrKeyValue(accdetails[i]);
		}

		return accdetails;

	}

}