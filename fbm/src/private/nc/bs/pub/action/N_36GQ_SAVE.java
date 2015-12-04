package nc.bs.pub.action;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：中心背书的保存 单据动作执行中的动态执行类的动态执行类。
 * 
 * 创建日期：(2008-2-27)
 * 
 * @author 平台脚本生成
 */
public class N_36GQ_SAVE extends AbstractCenterOperation {
	/**
	 * N_36GW_SAVE 构造子注解。
	 */
	public N_36GQ_SAVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			EndoreVO headVo = (EndoreVO) vo.m_preValueVo.getParentVO();
			// 前台保存时的校验，由于性能与连接数问题 迁到后台进行校验。
			// checkSave(headVo.getPk_corp(), headVo.getOpbilltype());
			// 判断是统管业务还是做的自己的业务：根据票据类型进行判断
			if (headVo.getOpbilltype().equals(FbmBusConstant.BILL_PRIVACY)) {// 私有票据
				return savePrivacy(vo);
			} else {
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
	protected RegisterVO changeRegister(RegisterVO register, SuperVO superVO)
			throws BusinessException {
		register.setPk_corp((String) superVO.getAttributeValue("pk_corp"));
		register.setPaybillunit(register.getHoldunit());
		register.setHoldunit((String) superVO.getAttributeValue("endorser"));
		register.setKeepunit((String) superVO.getAttributeValue("endorser"));
		register.setPk_source((String) superVO.getAttributeValue("pk_source"));
		register.setGathertype(FbmBusConstant.GATHER_TYPE_UNISTORAGE);
		register.setVbillstatus(IBillStatus.FREE);
		register.setPrimaryKey(null);

		// 设置业务日期。
		replaceRegisterValue(register, superVO);

		register.setDapprovedate((UFDate) superVO.getAttributeValue("dapprovedate"));
		register.setDoperatedate((UFDate) superVO.getAttributeValue("doperatedate"));
		register.setVapproveid((String) superVO.getAttributeValue("vapproveid"));
		register.setVoperatorid((String) superVO.getAttributeValue("voperatorid"));

		// 本、辅币汇率，金额
		// register.setBrate((UFDouble)superVO.getAttributeValue("brate"));
		// register.setFrate((UFDouble)superVO.getAttributeValue("frate"));
		// register.setMoneyy((UFDouble)superVO.getAttributeValue("moneyy"));

		// 添加VBillNO到收票登记单中。
		String pk_corp = (String) superVO.getAttributeValue("pk_corp");
		IBillcodeRuleService ibrs = (IBillcodeRuleService) NCLocator.getInstance().lookup(IBillcodeRuleService.class.getName());
		String vbillno = ibrs.getBillCode_RequiresNew(FbmBusConstant.BILLTYPE_GATHER, pk_corp, null, null);
		register.setVbillno(vbillno);
		
		return register;

	}

	// 用SuperVO中的日期替换到RegisterVO中的Gatherdate值。
	@Override
	protected void replaceRegisterValue(RegisterVO regvo, SuperVO supervo) {
		regvo.setGatherdate((UFDate) supervo.getAttributeValue("busdate"));
	}

}
