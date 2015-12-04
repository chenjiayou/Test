package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.relief.ReliefHelper;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：调剂出库的出库 单据动作执行中的动态执行类的动态执行类。
 *
 * 票据调剂的出库作为一个快捷方式，点击跳转到内部领用单界面，此类废弃
 *
 * 创建日期：(2007-10-22)
 *
 * @author 平台脚本生成
 */
public class N_36GR_OUTPUT extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GR_OUTPUT 构造子注解。
	 */
	public N_36GR_OUTPUT() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			ReliefHelper srv = new ReliefHelper();

			// 更新Reilef表，生成出库人和出库日期
			HYBillVO retBillVO = srv
					.updateReliefOut((HYBillVO) vo.m_preValueVo,
							vo.m_currentDate, vo.m_operator);

//			ActionParamVO[] params = DefaultParamAdapter.changeReliefParam(
//					retBillVO, FbmActionConstant.OUTPUT_SUCCESS);
			// 出库处理，生成内部领用单,
			//srv.makeStorageInnerBack(params);

			return retBillVO;
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