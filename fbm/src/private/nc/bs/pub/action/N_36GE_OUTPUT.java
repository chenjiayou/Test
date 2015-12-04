package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.storage.StorageAccountBO;
import nc.bs.fbm.storage.StorageBillService;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：内部领用的出库 单据动作执行中的动态执行类的动态执行类。
 * 
 * 创建日期：(2007-10-10)
 * 
 * @author 平台脚本生成
 */
public class N_36GE_OUTPUT extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GE_OUTPUT 构造子注解。
	 */
	public N_36GE_OUTPUT() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			Object retObj = null;
			HYBillVO tmpVO = (HYBillVO) vo.m_preValueVo;
			String pk_corp = InvocationInfoProxy.getInstance().getCorpCode();
			if (tmpVO instanceof HYBillVO) {
				// 重新查询子表数据，防止修改时没有子表数据
				HYPubBO bo = new HYPubBO();
				// 保存是否写内部账的用户选择字段
				UFBoolean writeInnerAcc = ((StorageVO) tmpVO.getParentVO()).getWriteinneracc();
				tmpVO = (HYBillVO) bo.queryBillVOByPrimaryKey(new String[] {
						HYBillVO.class.getName(), StorageVO.class.getName(),
						StorageBVO.class.getName() }, tmpVO.getParentVO().getPrimaryKey());

				((StorageVO) tmpVO.getParentVO()).setOutputdate(new UFDate(
						vo.m_currentDate));
				tmpVO.getParentVO().setAttributeValue(StorageVO.OUTPUTPERSON, vo.m_operator);

				// 领用单出库时维护清算户数据，要是票据已经进行清算了就不能再领用。
				// StorageBVO[] storageBVOs =
				// (StorageBVO[])tmpVO.getChildrenVO();
				// HYPubBO hYPubBO = new HYPubBO();
				// BaseDAO basedao = new BaseDAO();
				// for(int i = 0; i < storageBVOs.length; i++) {
				// String condition = " pk_type = '"+
				// FbmBusConstant.ACCOUNT_TYPE_LIQUID +
				// "' and exists ( select 1" +
				// " from fbm_register where isnull(fbm_register.dr,0) = 0 and fbm_accountdetail.pk_register = "
				// +
				// "fbm_register.pk_register and fbm_register.pk_source = '" +
				// storageBVOs[i].getPk_source() +
				// "' ) and fbm_accountdetail.reason = '"+FbmBusConstant.ACCOUNT_REASON_CENTER_USE+"' ";
				// AccountDetailVO[] accountDetailVO =
				// (AccountDetailVO[])hYPubBO.queryByCondition(AccountDetailVO.class,
				// condition);
				// if(accountDetailVO != null && accountDetailVO.length > 0) {
				// String updatesql =
				// "update fbm_accountdetail set isliquid = 'Y' where isliquid = 'N' and pk_detail = '"+
				// accountDetailVO[0].getPk_detail() +
				// "' and not exists ( select 1 from fbm_reckon_b where isnull(fbm_reckon_b.dr,0) = 0 and fbm_reckon_b.pk_detail = '"+
				// accountDetailVO[0].getPk_detail()+ "')";
				// int ret = basedao.executeUpdate(updatesql);
				// if(ret != 0) {
				// throw new BusinessException("票据 " +
				// storageBVOs[i].getFbmbillno() + "已经进行清算，不能领用！");
				// }
				// }
				// }
				// ActionParamVO[] params =
				// DefaultParamAdapter.changeInnerBack2Param(tmpVO,FbmActionConstant.OUTPUT_SUCCESS);
				// FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERBACK,FbmActionConstant.OUTPUT_SUCCESS).doAction(params);
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERBACK, FbmActionConstant.OUTPUT_SUCCESS).doAction(tmpVO, FbmActionConstant.OUTPUT_SUCCESS, false);

				retObj = new StorageBillService().outputStorageBillVO(tmpVO, vo.m_currentDate);

				// 维护内部账户帐和银行账户帐
				StorageAccountBO accBO = new StorageAccountBO();
				accBO.account4InnerBack((HYBillVO) retObj, writeInnerAcc, vo.m_operator, new UFDate(
						vo.m_currentDate));

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