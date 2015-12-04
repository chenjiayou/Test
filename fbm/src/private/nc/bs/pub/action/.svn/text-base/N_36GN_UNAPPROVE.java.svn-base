package nc.bs.pub.action;

import java.util.Hashtable;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.accdetail.ReturnVOToAccDetail;
import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.returnbill.ReturnBill2CMP;
import nc.bs.fbm.returnbill.ReturnBillDAO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：集中退票的弃审 单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-8-31)
 *
 * @author 平台脚本生成
 */
public class N_36GN_UNAPPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GN_UNAPPROVE 构造子注解。
	 */
	public N_36GN_UNAPPROVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			//检查单据状态是否审核通过
			ReturnVO returnvo = (ReturnVO)vo.m_preValueVo.getParentVO();
			int vbillstatus = returnvo.getVbillstatus();

			// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			procUnApproveFlow(vo);
			Object retObj = runClass("nc.bs.trade.comstatus.BillUnApprove",
					"unApproveBill", "nc.vo.pub.AggregatedValueObject:36GN",
					vo, m_keyHas, m_methodReturnHas);
			if(retObj instanceof HYBillVO){
				HYBillVO retBillVO = (HYBillVO)retObj;
				HYPubBO bo = new HYPubBO();
				BaseDAO dao = new BaseDAO();
				retBillVO = (HYBillVO)bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
						ReturnVO.class.getName(), ReturnBVO.class.getName()}, retBillVO.getParentVO().getPrimaryKey());

				ReturnBVO[] bvos = (ReturnBVO[])retBillVO.getChildrenVO();
				
				ReturnBill2CMP return2Cmp = new ReturnBill2CMP();
				PlanFacade facade = new PlanFacade();
				
				checkUnitUnistorage((HYBillVO)retObj);
				//删除单位生成的退票登记单
				delUnitReturnVO((HYBillVO)retObj);
				ReturnVO parentVO = (ReturnVO)((HYBillVO)retObj).getParentVO();
				if(vbillstatus == IBillStatus.CHECKPASS){//只有审核通过的弃审
					BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RETURN, FbmActionConstant.CANCELAUDIT).doAction((HYBillVO)retObj, FbmActionConstant.CANCELAUDIT,false);
					//统管退票审核要维护托管时的内部账户账
					
					if(FbmBusConstant.BACK_TYPE_UNISTORAGE.equals(parentVO.getAttributeValue(ReturnVO.RETURNTYPE))){
						//中心转出记内部账户账
						ReturnVOToAccDetail returnSrv = new ReturnVOToAccDetail();
						returnSrv.delAccDetail(parentVO);
					}else if(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT.equals(parentVO.getAttributeValue(ReturnVO.RETURNTYPE))){
						//删除资金计划
						facade.delPlanExec(returnvo, returnvo.getPk_corp());
					}//NCdp200801221 56未完善:当收票直接打上收票标记，
					// 维护日记账流入,那么在收票退票-冲销操作时,应计日记账流入票面负金额
					else if(parentVO.getAttributeValue(ReturnVO.RETURNTYPE).equals(FbmBusConstant.BACK_TYPE_GATHER) ||
							parentVO.getAttributeValue(ReturnVO.RETURNTYPE).equals(FbmBusConstant.BACK_TYPE_DISABLE))  //如果退票类型为收票退票，并且已收票标记已打勾
					{
						String loginCorp = InvocationInfoProxy.getInstance().getCorpCode();
						return2Cmp.delNegativeBillCMP((HYBillVO)retObj, loginCorp, vo.m_operator,((ReturnVO)parentVO).getDreturndate());
						//冲计划，开票退票冲开票计划项目
						facade.delPlanExec(returnvo, returnvo.getPk_corp());
							
					}else if(parentVO.getAttributeValue(ReturnVO.RETURNTYPE).equals(FbmBusConstant.BACK_TYPE_INVOICE)){
						String loginCorp = InvocationInfoProxy.getInstance().getCorpCode();
						return2Cmp.delNegativeBillCMP((HYBillVO)retObj, loginCorp, vo.m_operator,((ReturnVO)parentVO).getDreturndate());
						//写计划。开票收入计划项目，因为审核时冲了退票写的计划，所以弃审时要把计划写回来。
						//维护资金计划
						facade.delPlanExec(returnvo, returnvo.getPk_corp());
					}
					//删除银行账
//					ReturnBill2CMP return2Cmp = new ReturnBill2CMP();
					return2Cmp.delCMPacc((HYBillVO)retObj, ((ReturnVO)parentVO).getPk_corp(), vo.m_operator, new UFDate(vo.m_currentDate));
				}
				

			}
		
			//如果是背书退票则设置收票登记备注
			if(returnvo.getAttributeValue(ReturnVO.RETURNTYPE).equals(FbmBusConstant.BACK_TYPE_ENDORE)){
				new ReturnBillDAO().clearRegNote(returnvo.getPrimaryKey());
			}
			if (retObj != null) {
				m_methodReturnHas.put("unApproveBill", retObj);
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
	 * 删除生成的单位退票单
	 * @param billvo
	 * @throws BusinessException
	 */
	private void delUnitReturnVO(HYBillVO billvo) throws BusinessException{
		ReturnVO returnVO =(ReturnVO) billvo.getParentVO();
		String pk_return =returnVO.getPrimaryKey();
		if(FbmBusConstant.BACK_TYPE_UNISTORAGE.equals(returnVO.getReturntype())){
		BaseDAO dao = new BaseDAO();
		//查询退票生成的收票
		List<RegisterVO> regList = (List<RegisterVO>)dao.retrieveByClause(RegisterVO.class, "isnull(dr,0)=0 and pk_source ='"+pk_return+"'");

		if(regList !=null && regList.size() >0){
			StringBuffer sb = new StringBuffer();
			sb.append("select fbm_return.pk_return from fbm_return join fbm_return_b on fbm_return.pk_return = fbm_return_b.pk_return");
			sb.append(" where fbm_return_b.pk_source in(select pk_register from fbm_register where isnull(dr,0)=0 and pk_source='"+pk_return+"')");
			List<String> returnPks = (List)dao.executeQuery(sb.toString(), new ColumnListProcessor());

			HYPubBO bo = new HYPubBO();
			String[] vonames = new String[]{HYBillVO.class.getName(),ReturnVO.class.getName(),ReturnBVO.class.getName()};
			for(String pk : returnPks){
				AggregatedValueObject aggvo = bo.queryBillVOByPrimaryKey(vonames, pk);

				//弃审退票单
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RETURN, FbmActionConstant.CANCELAUDIT).doAction(aggvo, FbmActionConstant.CANCELAUDIT,true);

				//删除退票单
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RETURN, FbmActionConstant.DELETE).doAction(aggvo, FbmActionConstant.DELETE,true);

				bo.deleteBill(aggvo);
			}
			for(RegisterVO regVO:regList){
				dao.deleteVO(regVO);
			}
		}
		}
	}

	/**
	 * 检查是否为单位统管退票的弃审
	 * 如果当前退票单对应的收票登记单类型为退票生成，则抛出异常
	 * @param billvo
	 * @throws BusinessException
	 */
	private void checkUnitUnistorage(HYBillVO billvo) throws BusinessException{
		ReturnVO headvo = (ReturnVO)billvo.getParentVO();
		if(headvo.getReturntype().equals(FbmBusConstant.BACK_TYPE_INVOICE)){
			return;//付票退票不用检查
		}
		ReturnBVO[] bvos = (ReturnBVO[]) billvo.getChildrenVO();
		BaseDAO dao = new BaseDAO();
		RegisterVO regVO = null;
		if (bvos == null) {
			throw new BusinessException("数据错误，表体为空！");
		}
		for(ReturnBVO bvo:bvos){
			regVO = (RegisterVO)dao.retrieveByPK(RegisterVO.class, bvo.getPk_source());
			if(regVO.getGathertype().equals(FbmBusConstant.GATHER_TYPE_RETURN)){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000179")/*@res "中心推式生成的统管退票单不允许弃审"*/);
			}
		}
	}
}