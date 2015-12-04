package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：票据质押的质押回收 单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-9-27)
 *
 * @author 平台脚本生成
 */
public class N_36GO_IMPAWNBACK extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GO_IMPAWNBACK 构造子注解。
	 */
	public N_36GO_IMPAWNBACK() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			Object retObj = null;

//			ImpawnVO impawnVo = (ImpawnVO) vo.m_preValueVo.getParentVO();
//			impawnVo.setVbillstatus(new Integer(IFBMStatus.IMPAWN_BACK));// 将单据状态改为质押回收,保存
//
//			retObj = runClass("nc.bs.trade.comsave.BillSave", "saveBill",
//					"nc.vo.pub.AggregatedValueObject:36GO", vo, m_keyHas,
//					m_methodReturnHas);
//

//			HYBillVO retBillVO = (HYBillVO) ((ArrayList) retObj).get(1);
			HYBillVO retBillVO = (HYBillVO)vo.m_preValueVo;

//			ActionParamVO[] params = DefaultParamAdapter.changeImpawnParam(
//					retBillVO, FbmActionConstant.IMPAWNBACK);
//			FbmActionFactory.getInstance().createActionClass(
//					FbmBusConstant.BILLTYPE_IMPAWN,
//					FbmActionConstant.IMPAWNBACK).doAction(params);
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.IMPAWNBACK).doAction((ImpawnVO)retBillVO.getParentVO(), FbmActionConstant.IMPAWNBACK,false);


			retBillVO.setParentVO(new HYPubBO().queryByPrimaryKey(ImpawnVO.class, retBillVO.getParentVO().getPrimaryKey()));

			return retBillVO;// 返回一个聚合VO，onBoActionElse中如果是聚合vo的时候才刷新缓存中的vo（否则继续用原来的vo）刷新列表
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

	// 得到外部票据主键
	private String getPk_h(OuterVO[] vos) throws BusinessException {
		if (null == vos || vos.length == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000367")/* @res"数据错误，找不到与此质押业务对应的物权担保记录，回收失败"*/);
		}
		if (vos.length > 1) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000368")/* @res"数据错误，与此质押业务对应多条物权担保，回收失败"*/);
		}
		return vos[0].getPk_outerbill_h();// 得到物权担保pk
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