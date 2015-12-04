package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.fbm.invoice.InvoiceBillService;
import nc.bs.fbm.invoice.InvoiceUtil;
import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：票据开票的审核 单据动作执行中的动态执行类的动态执行类。
 * 
 * 创建日期：(2007-9-4)
 * 
 * @author 平台脚本生成
 */
public class N_36GL_APPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GL_APPROVE 构造子注解。
	 */
	public N_36GL_APPROVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			String ccReturnMsg = null;
			RegisterVO registerVo = null;
			UFBoolean writebankacc = null;
			if (vo.m_preValueVo != null
					&& vo.m_preValueVo.getParentVO() != null) {
				registerVo = (RegisterVO) vo.m_preValueVo.getParentVO().clone();
			}
			if (vo.m_preValueVo.getParentVO() != null) {
				writebankacc = ((RegisterVO) vo.m_preValueVo.getParentVO()).getWritebankacc();
			}
			procActionFlow(vo);

			Object retObj = runClass("nc.bs.trade.comstatus.BillApprove", "approveBill", "nc.vo.pub.AggregatedValueObject:36GL", vo, m_keyHas, m_methodReturnHas);

			String actioncode = null;
			if (retObj instanceof HYBillVO) {
				CircularlyAccessibleValueObject parentVO = ((HYBillVO) retObj).getParentVO();
				Integer billstatus = (Integer) parentVO.getAttributeValue("vbillstatus");

				if (billstatus.intValue() == IBillStatus.CHECKPASS) {// 如果操作结果为审核通过
					actioncode = FbmActionConstant.AUDIT;
				} else {
					actioncode = FbmActionConstant.ONAUDIT;
				}
				((RegisterVO) parentVO).setInvoicedate(registerVo.getInvoicedate());
				// ActionParamVO[] params =
				// DefaultParamAdapter.changeInvoice2Param((HYBillVO)retObj,actioncode);
				// FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INVOICE,
				// actioncode).doAction(params);
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INVOICE, actioncode).doAction((RegisterVO) parentVO, actioncode, false);
			}

			if (actioncode != null
					&& actioncode.equals(FbmActionConstant.AUDIT)) {
				if (registerVo != null) {
					ccReturnMsg = new InvoiceBillService().applyRationBeforeGLApprove(registerVo);
				}

				// if(!registerVo.getSfflag().booleanValue()){//如果已付票不打勾，才写银行账和计划
				// 维护资金计划
				PlanFacade facade = new PlanFacade();
				facade.insertPlanExec(registerVo, registerVo.getPk_corp(), "AUDIT");

				// try
				// {
				// 写银行账户账.
				RegisterVO tmpvo = (RegisterVO) ((HYBillVO) retObj).getParentVO();
				tmpvo.setWritebankacc(writebankacc);
				((HYBillVO) retObj).setParentVO(tmpvo);
				InvoiceUtil cbs = new InvoiceUtil();
				String loginCorp = InvocationInfoProxy.getInstance().getCorpCode();
				cbs.addCMPBank((HYBillVO) retObj, loginCorp, vo.m_operator, new UFDate(
						vo.m_currentDate));
				cbs.addCMPBill((HYBillVO) retObj, loginCorp, vo.m_operator, new UFDate(
						vo.m_currentDate));
				// } catch (Exception e) {
				// throw new BusinessException(e.getMessage());
				// }
				// }
			}
			
			//由于更新状态，因此必须重新查询一次
			RegisterVO regvo = (RegisterVO)new HYPubBO().queryByPrimaryKey(RegisterVO.class,registerVo.getPrimaryKey());
			((HYBillVO) retObj).setParentVO(regvo);
			
			retObj = new Object[] { ccReturnMsg, retObj };

			if (retObj != null) {
				m_methodReturnHas.put("approveBill", retObj);
			}

			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

	/*
	 * 备注：平台编写原始脚本
	 */
	public String getCodeRemark() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000173")/*
																									 * @res
																									 * "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n	Object retObj=null;\n	return retObj;\n"
																									 */;
	}

	/*
	 * 备注：设置脚本变量的HAS
	 */
	private void setParameter(String key, Object val) {
		if (m_keyHas == null) {
			m_keyHas = new Hashtable();
		}
		if (val != null) {
			m_keyHas.put(key, val);
		}
	}
}