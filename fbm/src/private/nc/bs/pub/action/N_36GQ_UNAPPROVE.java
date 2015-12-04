package nc.bs.pub.action;

import java.util.Hashtable;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.gather.GatherBillService;
import nc.bs.fbm.pub.ActionQueryDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.FbmCommonCheck;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
	/**
 * 备注：中心背书的弃审 单据动作执行中的动态执行类的动态执行类。
 * 
 * 创建日期：(2008-2-27)
 * 
 * @author 平台脚本生成
 */
public class N_36GQ_UNAPPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GW_UNAPPROVE 构造子注解。
	 */
	public N_36GQ_UNAPPROVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;

			EndoreVO checkvo = (EndoreVO) vo.m_preValueVo.getParentVO();
			checkCancelAudit(checkvo); // 前台背书单校验，由于效率改到后台进行校验.
			
			// 检查单据状态是否审核通过
			int vbillstatus = (Integer) vo.m_preValueVo.getParentVO().getAttributeValue("vbillstatus");
	
		// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			procUnApproveFlow(vo);
			Object retObj = runClass("nc.bs.trade.comstatus.BillUnApprove", "unApproveBill", "nc.vo.pub.AggregatedValueObject:36GQ", vo, m_keyHas, m_methodReturnHas);

			if (retObj instanceof HYBillVO
					&& vbillstatus == IBillStatus.CHECKPASS) {
				EndoreVO endorevo = (EndoreVO) ((HYBillVO) retObj).getParentVO();
				// 校验被背书单位，如果是内部单位则删除收票
				String otherunit = endorevo.getEndorsee();
				CommonDAO comDao = new CommonDAO();
				BaseDAO baseDao = new BaseDAO();
				ActionQueryDAO actionDao = new ActionQueryDAO();
				boolean fbmEnable = false;
				try {
					fbmEnable = comDao.productEnableByCust(otherunit, FbmBusConstant.SYSCODE_FBM);
				} catch (Exception e) {
					throw new BusinessException("校验被背书单位出现异常!请检查是否分配客商");
				}
				if (fbmEnable) {
					// 检查推式生成单据是否有后续操作
					List registerList = (List) baseDao.retrieveByClause(RegisterVO.class, " isnull(dr,0)=0 and pk_source ='"
							+ endorevo.getPrimaryKey()
							+ "'");
					if (registerList != null && registerList.size() > 0) {// 如果有推式生成收票登记单
						RegisterVO regVO = (RegisterVO) registerList.get(0);
						ActionVO[] actionVos = actionDao.queryAllByPk_register(regVO.getPrimaryKey());
						if (actionVos.length != 2) {
							throw new BusinessException(
									nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000218")/*
																														 * @res
																														 * "推式生成的收票登记单已进行后续处理"
																														 */);
						}
				}
			}
			List endoreList = (List) baseDao.retrieveByClause(EndoreVO.class, " isnull(dr,0)=0 and pk_source = '"
						+ endorevo.getPk_source()
						+ "'");
				EndoreVO[] vos = (EndoreVO[]) endoreList.toArray(new EndoreVO[0]);
				if (vos != null && vos.length > 0) {
					GatherBillService service = new GatherBillService();
					service.deleteRegisterVosForOuterSys(vos[0].getPrimaryKey());
				}

				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.CANCELAUDIT).doAction(endorevo, FbmActionConstant.CANCELAUDIT, false);
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
		return "	Object retObj =null;\n	return retObj;\n";
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
	 * 背书单弃审时进行校验。
	 * 
	 * @param endorevo
	 * @throws Exception
	 */
	private void checkCancelAudit(EndoreVO endorevo) throws Exception {
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
