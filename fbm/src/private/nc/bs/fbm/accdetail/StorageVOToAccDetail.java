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
 * �ڲ��йܺ��ڲ�����ά���ڲ��ʻ���
 * 
 * @author xwq
 * 
 *         2008-9-16
 */
public class StorageVOToAccDetail extends FbmVO2AccDetail {

	/**
	 * �����ڲ��˻���
	 * 
	 * @param storageVO
	 * @param isCenter
	 *            �Ƿ����ļ���
	 */
	public void addAccDetail(HYBillVO billvo, String pk_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		StorageVO storageVO = (StorageVO) billvo.getParentVO();
		String pk_billtypecode = storageVO.getPk_billtypecode();
		AccRuleVO accruleVO = FBMProxy.getAccRuleService().retriveAccRef(pk_billtypecode, FbmBusConstant.ACC_INNER);
		// ����ǲ����ˣ���ֱ�ӷ���
		if (accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)) {
			return;
		}

		// ����Ǳ��ܣ���ֱ�ӷ���
		if (FbmBusConstant.KEEP_TYPE_STORE.equals(storageVO.getInputtype())) {
			return;
		}

		String keepaccount = storageVO.getKeepaccount();
		if (keepaccount == null) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000123")/*
																										 * @res
																										 * "�ڲ��ʻ�Ϊ�գ��޷�����"
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
																										 * "����ĵ�������"
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
		// ����ǲ����ˣ���ֱ�ӷ���
		if (accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)) {
			return;
		}
		String keepaccount = storageVO.getKeepaccount();
		if (keepaccount == null) {
			return;// û���ڲ��ʻ��������ڲ��˻���
		}
		DetailBusVO detailBusVO = new DetailBusVO();
		detailBusVO.setPk_sourcebill(storageVO.getPrimaryKey());
		detailBusVO.setBusinesstype(storageVO.getPk_billtypecode());
		IAccountService accSrv = (IAccountService) NCLocator.getInstance().lookup(IAccountService.class);
		accSrv.deleteAccDetail(new DetailBusVO[] { detailBusVO });

	}

	/**
	 * �����Ķ��ڲ�����ڲ��ʻ���
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
			accdetails[i].setTallydate(tallydate);// ��������
			accdetails[i].setTallyperson(tallyman);// ������
			accdetails[i].setInterestdate(storageVO.getInputdate());// ��Ϣ��
			accdetails[i].setBilltype(storageVO.getPk_billtypecode());// ��������
			accdetails[i].setVbillno(storageVO.getVbillno());// ���ݱ���
			accdetails[i].setPk_oppcorp(storageVO.getKeepcorp());// �Է���˾
			accdetails[i].setPk_oppunit(storageVO.getKeepunit());// �Է���λ
			String cent_corp = bdDao.retriveSettleCenterBySubCorp(storageVO.getKeepcorp()).getPk_corp();
			accdetails[i].setUnit(cent_corp);// ������˾
			accdetails[i].setPk_account(storageVO.getKeepaccount());// �ڲ��ʻ�PK
			BaseinfoVO baseinfoVO = commonDAO.queryBaseinfoByPK(bvos[i].getPk_baseinfo());

			accdetails[i].setInmoney(baseinfoVO.getMoneyy());// �տ���
			accdetails[i].setFbmbillno(baseinfoVO.getFbmbillno());// Ʊ�ݱ��
			accdetails[i].setSfflag(IAccountConst.INFLAG);// ����
			accdetails[i].setSourcesystem(FbmBusConstant.SYSCODE_FBM);
			accdetails[i].setPk_currtype(storageVO.getPk_currtype());// ����
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
			accdetails[i].setTallydate(tallydate);// ��������
			accdetails[i].setTallyperson(tallyman);// ������
			accdetails[i].setInterestdate(storageVO.getOutputdate());// ��Ϣ��
			accdetails[i].setBilltype(storageVO.getPk_billtypecode());// ��������
			accdetails[i].setVbillno(storageVO.getVbillno());// ��������
			String cent_corp = bdDao.retriveSettleCenterBySubCorp(storageVO.getKeepcorp()).getPk_corp();
			accdetails[i].setPk_oppcorp(storageVO.getKeepcorp());// �Է���˾
			accdetails[i].setPk_oppunit(storageVO.getKeepunit());// �Է���λ

			accdetails[i].setUnit(storageVO.getKeepcorp());// ������˾
			accdetails[i].setPk_account(storageVO.getKeepaccount());// �ڲ��ʻ�PK
			BaseinfoVO baseinfoVO = commonDAO.queryBaseinfoByPK(bvos[i].getPk_baseinfo());
			accdetails[i].setOutmoney(baseinfoVO.getMoneyy());// ������
			accdetails[i].setFbmbillno(baseinfoVO.getFbmbillno());// Ʊ�ݱ��
			accdetails[i].setSfflag(IAccountConst.OUTFLAG);// ����
			accdetails[i].setSourcesystem(FbmBusConstant.SYSCODE_FBM);
			accdetails[i].setPk_currtype(storageVO.getPk_currtype());// ����
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