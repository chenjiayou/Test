package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：质押管理的保存 单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-9-19)
 *
 * @author 平台脚本生成
 */
public class N_36GO_SAVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GO_SAVE 构造子注解。
	 */
	public N_36GO_SAVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;

			ImpawnVO impawnVO = (ImpawnVO)vo.m_preValueVo.getParentVO();
			//取动作编码
			String actionCode = null;
			if (isNew(impawnVO)) {
				actionCode = FbmActionConstant.SAVE;
			}else {
				actionCode = FbmActionConstant.EDITSAVE;
			}

			//执行保存
			Object retObj = runClass("nc.bs.trade.comsave.BillSave", "saveBill",
					"nc.vo.pub.AggregatedValueObject:36GO", vo, m_keyHas,
					m_methodReturnHas);
			if (null != retObj) {
				m_methodReturnHas.put("saveBill", retObj);
			}

			HYBillVO retBillVO = (HYBillVO)((ArrayList)retObj).get(1);
//			FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, actionCode).doAction(DefaultParamAdapter.changeImpawnParam(retBillVO,actionCode));
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, actionCode).doAction((ImpawnVO)retBillVO.getParentVO(), actionCode,false);


			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}
	//判断是新增保存还是修改保存
	private boolean isNew(SuperVO superVO){
		if (null != superVO.getPrimaryKey() && 0 != superVO.getPrimaryKey().trim().length()){
			return false;
		}
		return true;
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