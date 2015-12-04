package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.accdetail.ReliefVOToAccDetail;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.relief.ReliefService;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：调剂出库的弃审 单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-10-22)
 *
 * @author 平台脚本生成
 */
public class N_36GR_UNAPPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GR_UNAPPROVE 构造子注解。
	 */
	public N_36GR_UNAPPROVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			//检查单据状态是否审核通过
			int vbillstatus = (Integer)vo.m_preValueVo.getParentVO().getAttributeValue("vbillstatus");

			procUnApproveFlow(vo);
			Object retObj = runClass("nc.bs.trade.comstatus.BillUnApprove",
					"unApproveBill", "nc.vo.pub.AggregatedValueObject:36GR",
					vo, m_keyHas, m_methodReturnHas);
			if (retObj instanceof HYBillVO && vbillstatus == IBillStatus.CHECKPASS) {
				HYBillVO retBillVO = (HYBillVO) retObj;
				// 重新查询子表数据，防止修改时没有子表数据
				HYPubBO bo = new HYPubBO();
				retBillVO = (HYBillVO) bo.queryBillVOByPrimaryKey(new String[] {
						HYBillVO.class.getName(), ReliefVO.class.getName(),
						ReliefBVO.class.getName() }, retBillVO.getParentVO()
						.getPrimaryKey());

				// ActionParamVO[] params =
				// DefaultParamAdapter.changeReliefParam(retBillVO,FbmActionConstant.CANCELAUDIT);
				// FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RELIEF,FbmActionConstant.CANCELAUDIT).doAction(params);
				BusiActionFactory.getInstance().createActionClass(
						FbmBusConstant.BILLTYPE_RELIEF,
						FbmActionConstant.CANCELAUDIT).doAction(retBillVO,
						FbmActionConstant.CANCELAUDIT, false);

				// 清除受理日期字段
				ReliefVO reliefVO = (ReliefVO) retBillVO.getParentVO();
				reliefVO.setDealdate(null);
				((HYBillVO) retBillVO).setParentVO(reliefVO);
				new HYPubBO().saveBill((HYBillVO) retBillVO);
				retObj = retBillVO;
				//NCdp200620425，20081209-更新持票单位为原持票单位
				new ReliefService().updateHoldUnitForUnApprove(retBillVO);
				// 维护内部账户账
				ReliefVOToAccDetail reliefAccdetailSrv = new ReliefVOToAccDetail();
				reliefAccdetailSrv
						.delAccDetail((ReliefVO) ((AggregatedValueObject) retObj)
								.getParentVO());
			}
			if (retObj != null) {
				m_methodReturnHas.put("unApproveBill", retObj);
			}
			
			// 执 行公式 填充参照名称，解决效率问题，减少前台与后台交互次数。
			RegisterVO2BVO.actionOPExecFormula((HYBillVO) retObj);
			
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