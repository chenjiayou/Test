package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.fbm.pub.ResetRefValues;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：调剂出库的保存 单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-10-22)
 *
 * @author 平台脚本生成
 */
public class N_36GR_SAVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GR_SAVE 构造子注解。
	 */
	public N_36GR_SAVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			ReliefVO reliefVO = (ReliefVO) vo.m_preValueVo.getParentVO();
//			reliefVO.setSummoney(getSumMoney((ReliefBVO[]) vo.m_preValueVo
//					.getChildrenVO()));
//			vo.m_preValueVo.setParentVO(reliefVO);

			HYBillVO tmpVO = (HYBillVO) vo.m_preValueVo;
			HYPubBO bo = new HYPubBO();
			if (!isNew(reliefVO)) {// 不是新增
				tmpVO = (HYBillVO) bo.queryBillVOByPrimaryKey(new String[] {
						HYBillVO.class.getName(), ReliefVO.class.getName(),
						ReliefBVO.class.getName() }, tmpVO.getParentVO()
						.getPrimaryKey());
//				ActionParamVO[] params = DefaultParamAdapter.changeReliefParam(
//						tmpVO, FbmActionConstant.DELETE);
//				FbmActionFactory.getInstance().createActionClass(
//						FbmBusConstant.BILLTYPE_RELIEF,
//						FbmActionConstant.DELETE).doAction(params);
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RELIEF, FbmActionConstant.DELETE).doAction(tmpVO, FbmActionConstant.DELETE,false);

			}

			// 取动作编码
			String actionCode = null;
			if (isNew(reliefVO)) {
				actionCode = FbmActionConstant.SAVE;
			} else {
				actionCode = FbmActionConstant.EDITSAVE;
			}

			// 执行保存
			Object retObj = runClass("nc.bs.trade.comsave.BillSave",
					"saveBill", "nc.vo.pub.AggregatedValueObject:36GR", vo,
					m_keyHas, m_methodReturnHas);

			//重新设值，将携带的值重新赋值
		    if (vo.m_preValueVo.getChildrenVO() != null
					&& vo.m_preValueVo.getChildrenVO().length != 0) {// 得到的字表数据不为0或空
		    	ResetRefValues.setReliefBodyRefValues(vo.m_preValueVo);
			}

			HYBillVO retBillVO = (HYBillVO) ((ArrayList) retObj).get(1);
			// 重新查询子表数据，防止修改时没有子表数据
			retBillVO = (HYBillVO) bo.queryBillVOByPrimaryKey(new String[] {
					HYBillVO.class.getName(), ReliefVO.class.getName(),
					ReliefBVO.class.getName() }, retBillVO.getParentVO()
					.getPrimaryKey());

//			FbmActionFactory.getInstance().createActionClass(
//					FbmBusConstant.BILLTYPE_RELIEF, actionCode).doAction(
//					DefaultParamAdapter
//							.changeReliefParam(retBillVO, actionCode));
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RELIEF, actionCode).doAction(retBillVO, actionCode,false);


			// 执 行公式 填充参照名称，解决效率问题，减少前台与后台交互次数。
			RegisterVO2BVO.actionOPExecFormula((HYBillVO) ((ArrayList) retObj).get(1));
			
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

	// 判断是新增保存还是修改保存
	private boolean isNew(SuperVO superVO) {
		if (null != superVO.getPrimaryKey()
				&& 0 != superVO.getPrimaryKey().trim().length()) {
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

	/**
	 * 将字表数据汇总
	 *
	 * @param bvos
	 * @return
	 */
	private UFDouble getSumMoney(ReliefBVO[] bvos) throws BusinessException{
		UFDouble sumMoney = new UFDouble(0);
		CommonDAO dao = new CommonDAO();
		BaseinfoVO baseinfoVO = null;
		for (int i = 0; null != bvos && i < bvos.length; i++) {
			baseinfoVO = dao.queryBaseinfoByPK(bvos[i].getPk_baseinfo());
			sumMoney = sumMoney.add(baseinfoVO.getMoneyy());
		}
		return sumMoney;
	}
}