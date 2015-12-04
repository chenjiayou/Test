package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.pub.FbmCommonCheck;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;
	/**
 * 备注：中心背书的编辑 单据动作执行中的动态执行类的动态执行类。
 * 
 * 创建日期：(2008-2-27)
 * 
 * @author 平台脚本生成
 */
public class N_36GQ_EDIT extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GW_EDIT 构造子注解。
	 */
	public N_36GQ_EDIT() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			
			// 前台校验转到后台。
			EndoreVO headVo = (EndoreVO) vo.m_preValueVo.getParentVO();
			checkEdit(headVo);
			Object retObj = null;
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
		return "	Object retObj=null;\n	return retObj;\n";
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

	private void checkEdit(EndoreVO endorevo) throws Exception {
		String pk_corp = InvocationInfoProxy.getInstance().getCorpCode();
		String pk_endore = endorevo.getPrimaryKey();
		boolean arapFlag = FbmCommonCheck.isStartARAP(pk_corp);
		boolean paramFlag = "Y".equals(FbmCommonCheck.getParamValue(pk_corp));

		if (!arapFlag && paramFlag) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000030")/*
																										 * @res
																										 * "背书办理单是否与收付报单据集成应用参数值 与\n　启用收付报标识不一致！"
																										 */);
		}
		if (FbmCommonCheck.isCreatedByARAP(pk_endore)) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000029")/*
																										 * @res
																										 * "此记录由收付报推式生成，不允许执行此操作。"
																										 */);
		}

	}
}
