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
 * ��Ʊά���ڲ��ʻ���
 * @author xwq
 *
 * 2008-11-7
 */
public class ReturnVOToAccDetail  extends FbmVO2AccDetail{

	public void addAccDetail(ReturnVO returnVO,ReturnBVO bvo,HYBillVO billvo,String tallyman,UFDate tallydate) throws BusinessException{
		StorageVO storageVO = (StorageVO) billvo.getParentVO();
		String pk_billtypecode = storageVO.getPk_billtypecode();
		AccRuleVO accruleVO = FBMProxy.getAccRuleService().retriveAccRef(pk_billtypecode,FbmBusConstant.ACC_INNER);
		//����ǲ����ˣ���ֱ�ӷ���
		if(accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)){
			return;
		}

		String keepaccount = storageVO.getKeepaccount();
		if(keepaccount == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000123")/*@res "�ڲ��ʻ�Ϊ�գ��޷�����"*/);
		}


		AccDetailVO[] accdetailVO = null;
		if(FbmBusConstant.BILLTYPE_INNERKEEP.equals(pk_billtypecode)){
			accdetailVO = addKeepCenterAccDetail(returnVO,bvo,billvo,accruleVO,tallyman,tallydate);
		}else {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000124")/*@res "����ĵ�������"*/);
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
	 * �����Ķ��ڲ�����ڲ��ʻ���
	 * ֻ�ܶ�һ������
	 * @param accdetailVO
	 * @throws BusinessException
	 */
	private AccDetailVO[] addKeepCenterAccDetail(ReturnVO returnVO,ReturnBVO bvo,HYBillVO billvo,AccRuleVO accruleVO,String tallyman,UFDate tallydate) throws BusinessException{
		StorageVO storageVO = (StorageVO) billvo.getParentVO();

		CommonDAO commonDAO = new CommonDAO();
		AccDetailVO[] accdetails = new AccDetailVO[1];
		accdetails[0] = new AccDetailVO();
		accdetails[0].setTallydate(tallydate);//��������
		accdetails[0].setTallyperson(tallyman);//������
		accdetails[0].setInterestdate(returnVO.getDreturndate());//��Ϣ��
		accdetails[0].setBilltype(returnVO.getPk_billtypecode());//��������
		accdetails[0].setVbillno(returnVO.getVbillno());//��������
		accdetails[0].setPk_oppcorp(storageVO.getKeepcorp());//�Է���˾
		accdetails[0].setPk_oppunit(storageVO.getKeepunit());//�Է���λ

		accdetails[0].setUnit(storageVO.getKeepcorp());//������˾
		accdetails[0].setPk_account(storageVO.getKeepaccount());//�ڲ��ʻ�PK
		BaseinfoVO baseinfoVO = commonDAO.queryBaseinfoByPK(bvo.getPk_baseinfo());
		accdetails[0].setOutmoney(baseinfoVO.getMoneyy());//������
		accdetails[0].setFbmbillno(baseinfoVO.getFbmbillno());//Ʊ�ݱ��
		accdetails[0].setSfflag( IAccountConst.OUTFLAG);//����
		accdetails[0].setSourcesystem(FbmBusConstant.SYSCODE_FBM);
		accdetails[0].setPk_currtype(storageVO.getPk_currtype());//����
		accdetails[0].setPk_sourcebill(returnVO.getPrimaryKey());
		accdetails[0].setPk_sourcebill_b(bvo.getPrimaryKey());
		accdetails[0].setMemo(returnVO.getReturnnote());
		fillCurrKeyValue(accdetails[0]);

		return accdetails;
	}


}