package nc.bs.pub.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.uif.pub.exception.UifException;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.FBMActionQuery;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.uapbd.bankaccount.BankaccbasVO;
/**
 * 
 ***********************************************************
 * 日期：2008-3-17							   
 * 程序员:吴二山 							   
 * 功能：银行托收保存，包含中心业务(注：中心业务编辑保存时需要先删除)						   
 ***********************************************************
 */
public class N_36GI_SAVE extends AbstractCenterOperation {

	/**
	 * N_36GI_SAVE 构造子注解。
	 */
	public N_36GI_SAVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			CollectionVO headVo = (CollectionVO) vo.m_preValueVo.getParentVO();
			checkSave(headVo); // 前台校验类改到后台。
			if (headVo.getOpbilltype().equals(FbmBusConstant.BILL_PRIVACY)) {// 私人的业务
				return savePrivacy(vo);
			} else {// 统管业务
				return saveUniStorage(vo);
			}
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}
	/**
	 * 新生成收票登记单
	 */
	protected RegisterVO changeRegister(RegisterVO register, SuperVO superVO)throws BusinessException {
		register.setPk_corp((String) superVO.getAttributeValue("pk_corp"));
		
		//取当前公司对应的客商
		String pk_cubasdoc = new CommonDAO().queryCustByCorp(register
				.getPk_corp());
		
		register.setPaybillunit(register.getHoldunit());

		register.setHoldunit(pk_cubasdoc);
		register.setKeepunit(pk_cubasdoc);
		
		register.setPk_source(register.getPrimaryKey());
		register.setPrimaryKey(null);
		register.setGathertype(FbmBusConstant.GATHER_TYPE_UNISTORAGE);
		register.setVbillstatus(IBillStatus.FREE);

		//设置业务日期.
		replaceRegisterValue(register,superVO);
		
		register.setDoperatedate((UFDate)superVO.getAttributeValue("doperatedate"));
		register.setVapproveid((String)superVO.getAttributeValue("voperatorid"));
		register.setVoperatorid((String)superVO.getAttributeValue("voperatorid"));
		
		//添加VBillNO到收票登记单中。
		String pk_corp = (String)superVO.getAttributeValue("pk_corp");
		IBillcodeRuleService ibrs = (IBillcodeRuleService)NCLocator.getInstance().lookup(IBillcodeRuleService.class.getName());
		String vbillno = ibrs.getBillCode_RequiresNew(FbmBusConstant.BILLTYPE_GATHER, pk_corp,null, null);
		register.setVbillno(vbillno);
		
		return register;
		
	}
	
	//用SuperVO中的日期替换到RegisterVO中的Gatherdate,Dapprovedate值。
	@Override
	protected void replaceRegisterValue(RegisterVO regvo, SuperVO supervo) {
		if ((UFDate) supervo.getAttributeValue("dconsigndate") != null) {//如果委托日期不为空则用委托日期
			regvo.setGatherdate((UFDate) supervo
					.getAttributeValue("dconsigndate"));
			regvo.setDapprovedate((UFDate) supervo
					.getAttributeValue("dconsigndate"));
		} else {
			regvo.setGatherdate((UFDate) supervo
					.getAttributeValue("doperatedate"));
			regvo.setDapprovedate((UFDate) supervo
					.getAttributeValue("doperatedate"));
		}
		
	
	}
	private void checkSave(CollectionVO headvo) throws BusinessException {
		String collectionbank = headvo.getPk_holderbank();
		String pk_source = headvo.getPk_source();
		UFDate collectiondate = headvo.getDcollectiondate();
		if (collectionbank != null && pk_source != null) {
			FBMActionQuery actQry = FBMActionQuery.getInstance();
			try {
				String endstatus = actQry.queryCurrentStatus(pk_source);
				if (FbmStatusConstant.HAS_BANK_KEEP.equals(endstatus)) {
					HYBillVO billvo = actQry.queryCurrentVO(pk_source);
					if (billvo != null) {
						StorageVO storageVO = (StorageVO) billvo.getParentVO();
						if (!collectionbank.equals(storageVO.getKeepunit())) {
							throw new BusinessException(
									nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000001"))/*
																														 * @res
																														 * "托收银行与托管银行不一致"
																														 */;
						}
					}
				}
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
			}
		}
		try {
			String holderacc = headvo.getHolderacc();
			BankaccbasVO bankaccbasVO = (BankaccbasVO) FBMProxy.getUifService().queryByPrimaryKey(BankaccbasVO.class, holderacc);
			UFDate opendate = (UFDate) bankaccbasVO.getAccopendate();
			if (collectiondate != null
					&& opendate != null
					&& collectiondate.before(opendate)) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000001")/*
																													 * @res
																													 * "款项收妥日期不能早于开户日期"
																													 */);
			}
		} catch (UifException e) {
			Logger.error(e.getMessage());
		}
	}
}
