package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：贴现申请单的审核 单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-8-25)
 *
 * @author 平台脚本生成
 */
public class N_36GF_APPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GF_APPROVE 构造子注解。
	 */
	public N_36GF_APPROVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;

			procActionFlow(vo);
			Object retObj = runClass("nc.bs.trade.comstatus.BillApprove",
					"approveBill", "nc.vo.pub.AggregatedValueObject:36GF", vo,
					m_keyHas, m_methodReturnHas);

			createBillAction(retObj);
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

	private void createBillAction(Object retObj) throws BusinessException {
		if (retObj instanceof HYBillVO) {
			CircularlyAccessibleValueObject parentVO = ((HYBillVO) retObj)
					.getParentVO();
			Integer billstatus = (Integer) parentVO
					.getAttributeValue("vbillstatus");

			String actioncode = null;
			if (billstatus.intValue() == IBillStatus.CHECKPASS) {// 如果操作结果为审核通过
				actioncode = FbmActionConstant.AUDIT;
			} else {
				actioncode = FbmActionConstant.ONAUDIT;
			}
			BusiActionFactory.getInstance().createActionClass(
					FbmBusConstant.BILLTYPE_DISCOUNT_APP, actioncode).doAction(
					(DiscountVO) ((HYBillVO) retObj).getParentVO(), actioncode,
					false);

		}
	}

	/*
	 * 备注：平台编写原始脚本
	 */
	public String getCodeRemark() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000173")/*@res "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n	Object retObj=null;\n	return retObj;\n"*/;
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