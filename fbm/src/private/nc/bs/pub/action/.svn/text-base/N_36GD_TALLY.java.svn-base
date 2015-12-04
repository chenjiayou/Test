package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.impl.fbm.pub.FBMBillTallyImpl;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：内部存放的记账 单据动作执行中的动态执行类的动态执行类。
 * 
 * 创建日期：(2008-7-29)
 * 
 * @author 平台脚本生成
 */
public class N_36GD_TALLY extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GD_TALLY 构造子注解。
	 */
	public N_36GD_TALLY() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			HYBillVO billVo = (HYBillVO) vo.m_preValueVo;
			StorageVO storageVo = (StorageVO) billVo.getParentVO();
			String corpPk = InvocationInfoProxy.getInstance().getCorpCode();
			String userId = storageVo.getTallyman();
			// String currdate = storageVo.getTallydate().toString();
			String tallydate = storageVo.getInputdate().toString();
			// new FBMBillTallyImpl().tally(billVo, UFDate.getDate(currdate),
			// userId, corpPk);
			new FBMBillTallyImpl().tally(billVo, UFDate.getDate(tallydate), userId, corpPk);

			// 写资金计划
			PlanFacade facade = new PlanFacade();
			facade.insertPlanExec(storageVo, storageVo.getKeepcorp());

			String pk_storage = (String) storageVo.getAttributeValue(StorageVO.PK_STORAGE);
			HYPubBO bo = new HYPubBO();
			Object retObj = bo.queryBillVOByPrimaryKey(new String[] {
					HYBillVO.class.getName(), StorageVO.class.getName(),
					StorageBVO.class.getName() }, pk_storage);
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