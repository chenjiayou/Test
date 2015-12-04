package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.fbm.accept.AcceptBillService;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.itf.cdm.util.CommonUtil;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.uapbd.bankaccount.BankaccbasVO;
/**
 * 备注：票据付款的保存
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-9-4)
 * @author 平台脚本生成
 */
public class N_36GM_SAVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
	/**
	 * N_36GM_SAVE 构造子注解。
	 */
	public N_36GM_SAVE() {
		super();
	}
	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			boolean isnew = false;
			AcceptVO headVo = (AcceptVO) vo.m_preValueVo.getParentVO();
			checkSave(headVo);// 前台校验类，改到后台进行校验。
			if (headVo.getPrimaryKey() == null
					|| headVo.getPrimaryKey().trim().length() == 0) {
				isnew = true;
			}
			// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			Object retObj = null;
			headVo = new AcceptBillService().saveHandInRefData(headVo);// 处理手工录入的参照
			retObj = runClass("nc.bs.trade.comsave.BillSave", "saveBill", "nc.vo.pub.AggregatedValueObject:36GM", vo, m_keyHas, m_methodReturnHas);// 36GN是单据类型

			HYBillVO retBillVO = (HYBillVO) ((ArrayList) retObj).get(1);
			String actioncode = null;
			if (isnew) {
				actioncode = FbmActionConstant.SAVE;
			} else {
				actioncode = FbmActionConstant.EDITSAVE;
			}
			// ActionParamVO[] params =
			// DefaultParamAdapter.changeAccept2Param(retBillVO,actioncode);
			// FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BILLPAY,
			// actioncode).doAction(params);
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BILLPAY, actioncode).doAction((AcceptVO) retBillVO.getParentVO(), actioncode, false);

			if (retObj != null) {
				m_methodReturnHas.put("saveBill", retObj);
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

	/**
	 * 前台校台验类。修改效率改到后台进行校验。
	 * 
	 * @param headvo
	 * @throws BusinessException
	 */
	private void checkSave(AcceptVO headvo) throws BusinessException {
			UFDate acceptdate = headvo.getDacceptdate();
			String backaccount = headvo.getBacksecaccount();
			if (!CommonUtil.isNull(backaccount)) {
				BankaccbasVO bankaccbasVO = (BankaccbasVO) FBMProxy.getUifService().queryByPrimaryKey(BankaccbasVO.class, backaccount);
				UFDate opendadte = bankaccbasVO.getAccopendate();
				if (opendadte != null
						&& acceptdate != null
						&& acceptdate.before(opendadte)) {
					throw new BusinessException(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000000")/*
																														 * @res
																														 * "付款日期不能早于返回保证金账户的开户日期"
																														 */);
				}
			}
	}

}