package nc.bs.pub.action;

import nc.bs.fbm.endore.EndoreService;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.uif.pub.exception.UifException;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.uapbd.bankaccount.BankaccbasVO;

/**
 * 
 *********************************************************** 
 * 日期：2008-3-17 程序员:吴二山 功能：贴现办理保存，包含中心业务(注：中心业务编辑保存时需要先删除生成的收票登记单)
 *********************************************************** 
 */
public class N_36GG_SAVE extends AbstractCenterOperation {

	/**
	 * N_36GG_SAVE 构造子注解。
	 */
	public N_36GG_SAVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			DiscountVO headVo = (DiscountVO) vo.m_preValueVo.getParentVO();
			checkSave(headVo); // 前台校验方法，修改效率时，转到后台进行校验。
			if ((headVo.getPk_discount_app() == null || "".equals(headVo.getPk_discount_app().trim()))
					&& FbmBusConstant.BILL_UNISTORAGE.equals(headVo.getOpbilltype())) {// 如果贴现申请单为空且为统管票据
				return saveUniStorage(vo);
			} else {// 私有业务直接保存
				// 存在贴现申请单、且为统管业务、且贴现办理结果为作废
				// 需要删除保存贴现申请单生成的调剂户和清算户，需要在生成新action之前将账户处理掉
				// if (headVo.getPk_discount_app() != null
				// && !"".equals(headVo.getPk_discount_app())&&
				// FbmBusConstant.BILL_UNISTORAGE.equals(headVo
				// .getOpbilltype())&&
				// FbmBusConstant.DISCOUNT_RESULT_DISABLE.equals(headVo.getResult()))
				// {
				// //delAccountDetail(headVo);
				// }

				return savePrivacy(vo);
			}

		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

	/**
	 * 如果贴现办理作废且有贴现申请单则将新生成的账户处理删除
	 * 
	 * @param headVo
	 * @throws BusinessException
	 */
	private void delAccountDetail(DiscountVO headVo) throws BusinessException {
		// ActionQueryDAO dao = new ActionQueryDAO();
		// ActionVO[] actionVO = dao.queryActionByWhereClause(" actioncode='" +
		// FbmActionConstant.CENTERUSE + "' and pk_bill='" +
		// headVo.getPk_discount_app() + "' ");
		EndoreService endores = new EndoreService();
		ActionVO actionvo = endores.queryPK_Action(headVo.getPk_baseinfo(), FbmActionConstant.CENTERUSE);
		if (null == actionvo) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000127")/*
																										 * @res
																										 * "无法得到票据动作。"
																										 */);
		}
		String pkAction = actionvo.getPk_action();

		CommonDAO comDao = new CommonDAO();
		comDao.delAccountDetailByActionPK(pkAction);

	}

	/**
	 * 新生成收票登记单
	 */
	protected RegisterVO changeRegister(RegisterVO register_main, SuperVO superVO)
			throws BusinessException {
		RegisterVO register = (RegisterVO) register_main.clone();
		register.setPk_corp((String) superVO.getAttributeValue("pk_corp"));

		// 取当前公司对应的客商
		String pk_cubasdoc = new CommonDAO().queryCustByCorp(register.getPk_corp());
		register.setPaybillunit(register.getHoldunit());

		register.setHoldunit(pk_cubasdoc);
		register.setKeepunit(pk_cubasdoc);

		register.setPk_source(register.getPrimaryKey());
		register.setPrimaryKey(null);
		register.setGathertype(FbmBusConstant.GATHER_TYPE_UNISTORAGE);
		register.setVbillstatus(IBillStatus.FREE);

		// 设置业务日期。
		replaceRegisterValue(register, superVO);

		register.setDoperatedate((UFDate) superVO.getAttributeValue("doperatedate"));
		register.setVapproveid((String) superVO.getAttributeValue("voperatorid"));
		register.setVoperatorid((String) superVO.getAttributeValue("voperatorid"));
		// 添加VBillNO到收票登记单中。
		String pk_corp = (String) superVO.getAttributeValue("pk_corp");
		IBillcodeRuleService ibrs = (IBillcodeRuleService) NCLocator.getInstance().lookup(IBillcodeRuleService.class.getName());
		String vbillno = ibrs.getBillCode_RequiresNew(FbmBusConstant.BILLTYPE_GATHER, pk_corp, null, null);
		register.setVbillno(vbillno);
		return register;
	}

	// 用SuperVO中的日期替换到RegisterVO中的Gatherdate,Dapprovedate值。
	@Override
	protected void replaceRegisterValue(RegisterVO regvo, SuperVO supervo) {
		if ((UFDate) supervo.getAttributeValue("dtransactdate") != null) {// 如果办理日期不为空则用办理日期
			regvo.setGatherdate((UFDate) supervo.getAttributeValue("dtransactdate"));
			regvo.setDapprovedate((UFDate) supervo.getAttributeValue("dtransactdate"));
		} else {
			regvo.setGatherdate((UFDate) supervo.getAttributeValue("doperatedate"));
			regvo.setDapprovedate((UFDate) supervo.getAttributeValue("doperatedate"));
		}
	}

	/**
	 * 前台校验方法移到后台进行校验。(效率修改)
	 * 
	 * @param discountvo
	 * @throws BusinessException
	 */
	private void checkSave(DiscountVO discountvo) throws BusinessException {
		String result = discountvo.getResult();
		UFDate discountdate = discountvo.getDdiscountdate();
		if (result.equalsIgnoreCase(FbmBusConstant.DISCOUNT_RESULT_SUCCESS)) {
			try {
				String discount_account = discountvo.getDiscount_account();
				if (discount_account != null) {
					BankaccbasVO bankaccbasVO = (BankaccbasVO) FBMProxy.getUifService().queryByPrimaryKey(BankaccbasVO.class, discount_account);
					UFDate opendate = (UFDate) bankaccbasVO.getAccopendate();
					if (discountdate != null
							&& opendate != null
							&& discountdate.before(opendate)) {
						throw new BusinessException(
								nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000002")/*
																															 * @res
																															 * "实现贴现日期不能早于开户日期"
																															 */);
					}
				} else {
					throw new BusinessException(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000003")/*
																														 * @res
																														 * "贴现银行账号不能为空"
																														 */);
				}
			} catch (UifException e) {
				Logger.error(e.getMessage());
			}
		}
	}
}