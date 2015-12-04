package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.accdetail.ReturnVOToAccDetail;
import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.FbmBDQueryDAO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.returnbill.ReturnBill2CMP;
import nc.bs.fbm.returnbill.ReturnBillDAO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.bs.trade.comsave.BillSave;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.FBMActionQuery;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.trade.summarize.Hashlize;
import nc.vo.trade.summarize.VOHashKeyAdapter;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：集中退票的审核 单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-8-31)
 *
 * @author 平台脚本生成
 */
public class N_36GN_APPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GN_APPROVE 构造子注解。
	 */
	public N_36GN_APPROVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			procActionFlow(vo);
			Object retObj = runClass("nc.bs.trade.comstatus.BillApprove",
					"approveBill", "nc.vo.pub.AggregatedValueObject:36GN", vo,
					m_keyHas, m_methodReturnHas);

			
			if(retObj instanceof HYBillVO){
				HYBillVO retBillVO = (HYBillVO)retObj;
				BaseDAO dao = new BaseDAO();
				//重新查询子表数据，防止修改时没有子表数据
				HYPubBO bo = new HYPubBO();
				retBillVO = (HYBillVO)bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
						ReturnVO.class.getName(), ReturnBVO.class.getName()}, retBillVO.getParentVO().getPrimaryKey());


				CircularlyAccessibleValueObject parentVO = retBillVO.getParentVO();
				Integer billstatus = (Integer)parentVO.getAttributeValue("vbillstatus");

				String actioncode = null;
				if(billstatus.intValue() == IBillStatus.CHECKPASS){//如果操作结果为审核通过
					actioncode = FbmActionConstant.AUDIT;
					//如果是背书退票则设置收票登记备注
					if(parentVO.getAttributeValue(ReturnVO.RETURNTYPE).equals(FbmBusConstant.BACK_TYPE_ENDORE)){
						String pk_return =( (ReturnVO)parentVO).getPrimaryKey();
						new ReturnBillDAO().appendRegNote(pk_return, ( (ReturnVO)parentVO).getDreturndate());
					}
				}else{
					actioncode = FbmActionConstant.ONAUDIT;
				}
//				ActionParamVO[] params = DefaultParamAdapter.changeReturn2Param(retBillVO,actioncode);
//				FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RETURN, actioncode).doAction(params);
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RETURN, actioncode).doAction(retBillVO, actioncode,false);

				//生成单位端的统管退票单
				//createUnitReturnVO(retBillVO);

				//统管退票审核要维护托管时的内部账户账
				if(FbmActionConstant.AUDIT.equals(actioncode) && parentVO!=null ){
					ReturnBill2CMP return2Cmp = new ReturnBill2CMP();
					ReturnBVO[] bvos = (ReturnBVO[])retBillVO.getChildrenVO();
					if(FbmBusConstant.BACK_TYPE_UNISTORAGE.equals(parentVO.getAttributeValue(ReturnVO.RETURNTYPE))){
						//中心转出记内部账户账
						for(ReturnBVO bvo:bvos){
							HYBillVO billvo = FBMActionQuery.getInstance().queryLastBillTypeBillVO(bvo.getPk_baseinfo(), FbmBusConstant.BILLTYPE_INNERKEEP);
							if(billvo !=null && billvo.getParentVO()!=null){
								//记内部账户账
								ReturnVOToAccDetail returnSrv = new ReturnVOToAccDetail();
								returnSrv.addAccDetail((ReturnVO)retBillVO.getParentVO(),bvo,billvo,vo.m_operator,new UFDate(vo.m_currentDate));
							}
						}
						
						return2Cmp.insertBankAcc4Center(retBillVO, ((ReturnVO)parentVO).getPk_corp(), vo.m_operator, new UFDate(vo.m_currentDate));
					}else if(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT.equals(parentVO.getAttributeValue(ReturnVO.RETURNTYPE))){
						//单位转入记银行账户账
						return2Cmp.insertBankAcc4Unit(retBillVO, ((ReturnVO)parentVO).getPk_corp(), vo.m_operator, new UFDate(vo.m_currentDate));
						//写资金计划
						PlanFacade facade = new PlanFacade();
						facade.insertPlanExec((ReturnVO)parentVO, ((ReturnVO)parentVO).getPk_corp());
					}//NCdp200801221 56未完善:当收票直接打上收票标记，
					// 维护日记账流入,那么在收票退票-冲销操作时,应计日记账流入票面负金额
					else if(parentVO.getAttributeValue(ReturnVO.RETURNTYPE).equals(FbmBusConstant.BACK_TYPE_GATHER) ||
							parentVO.getAttributeValue(ReturnVO.RETURNTYPE).equals(FbmBusConstant.BACK_TYPE_DISABLE)) 
						//如果退票类型为收票退票或者是废票退票，并且已收票标记已打勾
					{
						PlanFacade facade = new PlanFacade();
						String loginCorp = InvocationInfoProxy.getInstance().getCorpCode();
						//写票据账
						return2Cmp.addGatherNegativeBillCMP(retBillVO, loginCorp, vo.m_operator,((ReturnVO)parentVO).getDreturndate());
						//冲计划，开票退票冲开票计划项目
						facade.insertPlanExec((ReturnVO)parentVO, ((ReturnVO)parentVO).getPk_corp());
							
					}else if(parentVO.getAttributeValue(ReturnVO.RETURNTYPE).equals(FbmBusConstant.BACK_TYPE_INVOICE)){
						String loginCorp = InvocationInfoProxy.getInstance().getCorpCode();
						return2Cmp.addNegativeBillCMP(retBillVO, loginCorp, vo.m_operator,((ReturnVO)parentVO).getDreturndate());
						//冲计划。付票退票冲开票收入计划项目。
						//维护资金计划
						PlanFacade facade = new PlanFacade();
						facade.insertPlanExec((ReturnVO)parentVO, ((ReturnVO)parentVO).getPk_corp());
					}


				}
			}
			if (retObj != null) {
				m_methodReturnHas.put("approveBill", retObj);
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
	 * 生成单位端退票单
	 */
	private void createUnitReturnVO(HYBillVO aggVO) throws BusinessException{
		ReturnVO returnVO = (ReturnVO)aggVO.getParentVO();
		ReturnBVO[] returnbvos = (ReturnBVO[])aggVO.getChildrenVO();

		//退票类型为统管退票时要生成单位端的统管退票单
		if(FbmBusConstant.BACK_TYPE_UNISTORAGE.equals(returnVO.getReturntype())){
			//新增单位收票登记单
			List<RegisterVO> regList = new ArrayList<RegisterVO>();
			RegisterVO newRegVO ;
			FbmBDQueryDAO bdQry = new FbmBDQueryDAO();
			IBillcodeRuleService billcodeSrv = OuterProxy.getBillCodeRuleService();
			String billno = null;
			BillSave billsave = new BillSave();
			HYBillVO aggvo = new HYBillVO();
			BaseDAO dao = new BaseDAO();

			for(int i = 0 ; i < returnbvos.length ;i++){
				RegisterVO tmpVO = (RegisterVO)dao.retrieveByPK(RegisterVO.class, returnbvos[i].getPk_source());
				newRegVO = new RegisterVO();
				newRegVO.setBrate(tmpVO.getBrate());
				newRegVO.setDapprovedate(tmpVO.getDapprovedate());
				newRegVO.setDoperatedate(tmpVO.getDoperatedate());
				newRegVO.setFrate(tmpVO.getFrate());
				newRegVO.setGatherdate(returnVO.getDreturndate());
				newRegVO.setGathertype(FbmBusConstant.GATHER_TYPE_RETURN);
				newRegVO.setHoldunit(tmpVO.getPaybillunit());
				newRegVO.setKeepunit(tmpVO.getPaybillunit());
				newRegVO.setMoneyb(tmpVO.getMoneyb());
				newRegVO.setMoneyf(tmpVO.getMoneyf());
				newRegVO.setMoneyy(tmpVO.getMoneyy());
				newRegVO.setPaybillunit(tmpVO.getKeepunit());
				newRegVO.setPk_baseinfo(tmpVO.getPk_baseinfo());
				newRegVO.setPk_billtypecode(tmpVO.getPk_billtypecode());
				newRegVO.setPk_corp(bdQry.retriveCorpByPk_cubasdoc(tmpVO.getPaybillunit()));
				newRegVO.setPk_source(returnVO.getPrimaryKey());
				newRegVO.setSfflag(new UFBoolean(true));
				newRegVO.setVapproveid(tmpVO.getVapproveid());
				billno = billcodeSrv.getBillCode_RequiresNew(
						FbmBusConstant.BILLTYPE_GATHER, newRegVO.getPk_corp(),
						null, null);
				newRegVO.setVbillno(billno);
				newRegVO.setVbillstatus(IBillStatus.CHECKPASS);
				newRegVO.setVoperatorid(tmpVO.getVoperatorid());
				aggvo = new HYBillVO();
				aggvo.setParentVO(newRegVO);
				List list = billsave.saveBill(aggvo);
				regList.add((RegisterVO)((AggregatedValueObject)list.get(1)).getParentVO());
			}
			RegisterVO[] regVos = (RegisterVO[])regList.toArray(new RegisterVO[0]);

			//收票登记单分组生成退票单
			HashMap tmpMap = Hashlize.hashlizeObjects(regVos, new VOHashKeyAdapter(new String[]{"pk_corp"}));
			Iterator itr = tmpMap.keySet().iterator();
			List newReturnAgg = new ArrayList();
			while(itr.hasNext()){
				Object key = itr.next();
				ArrayList al = (ArrayList)tmpMap.get(key);

				ReturnVO unitReturnVO = new ReturnVO();
				unitReturnVO.setBusdate(returnVO.getBusdate());
				unitReturnVO.setDapprovedate(returnVO.getDapprovedate());
				unitReturnVO.setDoperatedate(returnVO.getDoperatedate());
				unitReturnVO.setDreturndate(returnVO.getDreturndate());
				unitReturnVO.setPk_billtypecode(returnVO.getPk_billtypecode());
				unitReturnVO.setPk_corp(String.valueOf(key));
				unitReturnVO.setReturnperson(returnVO.getReturnperson());
				unitReturnVO.setReturntype(returnVO.getReturntype());
				unitReturnVO.setVapproveid(returnVO.getVapproveid());
				billno = billcodeSrv.getBillCode_RequiresNew(
						FbmBusConstant.BILLTYPE_RETURN, String.valueOf(key),
						null, null);
				unitReturnVO.setVbillno(billno);
				unitReturnVO.setVbillstatus(IBillStatus.CHECKPASS);
				unitReturnVO.setVoperatorid(returnVO.getVoperatorid());
				unitReturnVO.setVoucher(UFBoolean.FALSE);

				ReturnBVO[] bvos = new ReturnBVO[al.size()];
				for(int j = 0 ;j < al.size();j++){
					RegisterVO reg = (RegisterVO)al.get(j);
					bvos[j] = new ReturnBVO();
					bvos[j].setPk_baseinfo(reg.getPk_baseinfo());
					bvos[j].setPk_source(reg.getPrimaryKey());
				}

				HYBillVO billVO = new HYBillVO();
				billVO.setParentVO(unitReturnVO);
				billVO.setChildrenVO(bvos);
				ArrayList list = billsave.saveBill(billVO);

				HYBillVO retBillVO = (HYBillVO)((ArrayList)list).get(1);
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RETURN, FbmActionConstant.SAVE).doAction(retBillVO, FbmActionConstant.SAVE,true);
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RETURN, FbmActionConstant.AUDIT).doAction(retBillVO, FbmActionConstant.AUDIT,true);

			}
		}
	}
}