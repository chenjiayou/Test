package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.accdetail.ReckonVOToAccDetail;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.itf.fts.pub.ICommon;
import nc.itf.uif.pub.IUifService;
import nc.uif.pub.exception.UifException;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：调剂清算单的审核
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-11-1)
 * @author 平台脚本生成
 */
public class N_36GK_APPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GK_APPROVE 构造子注解。
 */
public N_36GK_APPROVE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
	try{
		super.m_tmpVo=vo;
		String pk_corp = InvocationInfoProxy.getInstance().getCorpCode();
		procActionFlow(vo);
		Object retObj=runClass("nc.bs.trade.comstatus.BillApprove","approveBill","nc.vo.pub.AggregatedValueObject:36GK",vo,m_keyHas,m_methodReturnHas);

		if (retObj != null) {
			m_methodReturnHas.put("approveBill",retObj);
		}

		//领用单出库时维护清算户数据，要是票据已经领用了就不能再清算。
//		ReckonBVO[] reckonBVOs = (ReckonBVO[])vo.m_preValueVo.getChildrenVO();
//		HYPubBO hyPubBO = new HYPubBO();
//		for(int i = 0; i < reckonBVOs.length; i++) {
//			String condition = " exists (select 1 from fbm_accountdetail a,fbm_action b where a.reason = '"
//			 +FbmBusConstant.ACCOUNT_REASON_CENTER_USE +"' and pk_detail = '" +reckonBVOs[i].getPk_detail()
//			 + "' and b.isnew = 'Y' and isnull(a.dr,0) = 0 and isnull(b.dr,0) = 0 and b.endstatus = '"
//			 +FbmStatusConstant.REGISTER + "' and fbm_register.pk_source = a.pk_register "
//			 +" and b.pk_source = fbm_register.pk_register )";
//			RegisterVO[] registerVO = (RegisterVO[])hyPubBO.queryByCondition(RegisterVO.class, condition);
//			if(registerVO.length > 0 ) {
//                throw new BusinessException("票据 " + reckonBVOs[i].getFbmbillno() + "已经内部领用，不能清算！");
//			}
//		}
		if(retObj   instanceof HYBillVO) {
			Integer vbillstatus = (Integer)((HYBillVO)retObj).getParentVO().getAttributeValue(ReckonVO.VBILLSTATUS);
			if(vbillstatus.intValue() == nc.vo.trade.pub.IBillStatus.CHECKPASS){
				insertReckonReceipt(vo);
				//if(doHoldUnitAccont(vo) < 0 )
					//throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000363")/* @res"持票单位内部账户余额不足，不能审批！"*/);
				changeAccountDetailLiquidFlag(vo);

				//检查受理日期
				ReckonVO reckonVO = (ReckonVO)((HYBillVO)retObj).getParentVO();
				 ICommon commonInterface = (ICommon)NCLocator.getInstance().lookup(ICommon.class);
				 UFDate dhandledate = commonInterface.getAutoProcessDate(reckonVO.getPk_corp());
					if (dhandledate != null) {
						if (reckonVO.getDapprovedate().after(dhandledate)) {
							throw new BusinessException(
									nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
											.getStrByID("3620add",
													"UPP3620ADD-000175")/*
																		 * @res
																		 * "审核日期大于受理日期，无法写内部账户账"
																		 */);
						}
						
						String businessno = commonInterface.getBusinessNo("36LW", pk_corp, null, null);
						//更新受理日期字段
						reckonVO.setDealdate(dhandledate);
						reckonVO.setBusinessno(businessno);
						((HYBillVO) retObj).setParentVO(reckonVO);
						new BaseDAO().updateVO(reckonVO, new String[]{ReckonVO.DEALDATE,"businessno"});
						
						((HYBillVO) retObj).setParentVO(new HYPubBO().queryByPrimaryKey(ReckonVO.class, reckonVO.getPrimaryKey()));
					}

				//记内部账户账
				ReckonVOToAccDetail reckonAccdetailSrv = new ReckonVOToAccDetail();
				reckonAccdetailSrv.addAccDetail((HYBillVO)retObj,vo.m_operator,new UFDate(vo.m_currentDate));
			}
		}

		return retObj;

	} catch (Exception ex) {
		if (ex instanceof BusinessException)
			throw (BusinessException) ex;
		else
	    throw new PFBusinessException(ex.getMessage(), ex);
	}
}

	//根据清算单自动生成清算回单
	private void insertReckonReceipt(PfParameterVO vo) throws UifException, BusinessException {
		HYBillVO reckonreceiptvo = new HYBillVO();
		AggregatedValueObject billvo = vo.m_preValueVo;
		reckonreceiptvo.setParentVO((CircularlyAccessibleValueObject)billvo.getParentVO().clone());
		((ReckonVO)reckonreceiptvo.getParentVO()).setPk_billtypecode(FbmBusConstant.BILLTYPE_RECKON_RECIEPT);
		((ReckonVO)reckonreceiptvo.getParentVO()).setPk_reckon(null);
		CommonDAO commdao = new CommonDAO();
		String pkcorp = commdao.queryCorpByCust(((ReckonVO)reckonreceiptvo.getParentVO()).getReckonunit());
		String billcode =((IUifService) NCLocator.getInstance().lookup(IUifService.class.getName())).getBillNo(FbmBusConstant.BILLTYPE_RECKON_RECIEPT, pkcorp, null, null);
		((ReckonVO)reckonreceiptvo.getParentVO()).setVbillno(billcode);
		((ReckonVO)reckonreceiptvo.getParentVO()).setPk_corp(pkcorp);
		((ReckonVO)reckonreceiptvo.getParentVO()).setVoperatorid(vo.m_operator);
		((ReckonVO)reckonreceiptvo.getParentVO()).setDoperatedate(new UFDate(vo.m_currentDate.substring(0, 10)));
		((ReckonVO)reckonreceiptvo.getParentVO()).setDef1(((ReckonVO)(billvo.getParentVO())).getPk_reckon());

		((ReckonVO)reckonreceiptvo.getParentVO()).setVouchermanid(null);
		((ReckonVO)reckonreceiptvo.getParentVO()).setVoucherdate(null);
		((ReckonVO)reckonreceiptvo.getParentVO()).setUnitvoucherdate(null);
		((ReckonVO)reckonreceiptvo.getParentVO()).setUnitvouchermanid(null);
		((ReckonVO)reckonreceiptvo.getParentVO()).setUnittally(new UFBoolean(false));
		((ReckonVO)reckonreceiptvo.getParentVO()).setUnitvoucher(new UFBoolean(false));
		((ReckonVO)reckonreceiptvo.getParentVO()).setCentervoucher(new UFBoolean(false));

		ReckonBVO[] childvos = (ReckonBVO[])billvo.getChildrenVO();
		ReckonBVO[] childreceiptvos = new ReckonBVO[childvos.length];
		for(int i = 0; i < childvos.length; i ++) {
			childreceiptvos[i] = (ReckonBVO)childvos[i].clone();
			childreceiptvos[i].setPk_reckon(null);
			childreceiptvos[i].setPk_reckon_b(null);
		}
		reckonreceiptvo.setChildrenVO(childreceiptvos);
		HYPubBO hypubbo = new HYPubBO();
		hypubbo.saveBill(reckonreceiptvo);
	}

	//维护持票单位内部账户余额
	private int doHoldUnitAccont(PfParameterVO vo) throws BusinessException{
		AggregatedValueObject billvo = vo.m_preValueVo;
		ReckonVO reckvo = (ReckonVO)billvo.getParentVO();
		String accont = reckvo.getInacc();
		UFDouble reckmoney = reckvo.getReckonmoneysum();
		BaseDAO basebo = new BaseDAO();
		String sql = null;
		if(reckmoney.compareTo(new UFDouble(0)) > 0 ) {
			sql = "update bd_accid set currmny = currmny + "+ reckmoney + " where pk_accid = '" + accont + "'";
		} else if(reckmoney.compareTo(new UFDouble(0)) < 0 ){
			sql = "update bd_accid set currmny = currmny + "+ reckmoney + " where pk_accid = '" + accont + "' and currmny >= " + reckmoney.abs();
		}
		int ret = 0;

		if(sql!=null){
			ret = basebo.executeUpdate(sql);
		}
		return ret;
	}

	//维护持票单位票据账户余额
	private void doHoldUnitFbmBillAccont(PfParameterVO vo) {

	}

	private void changeAccountDetailLiquidFlag(PfParameterVO vo) throws BusinessException{
		AggregatedValueObject billvo = vo.m_preValueVo;
		ReckonBVO[] childvos = (ReckonBVO[])billvo.getChildrenVO();
		StringBuffer pk_details = new StringBuffer();
		for(int i = 0; i < childvos.length; i ++) {
			if(i == 0) {
				pk_details.append(" (");
			}
			pk_details.append("'" + childvos[i].getPk_source()+ "'");
			if(i == childvos.length - 1) {
				pk_details.append(" ) ");
			} else {
				pk_details.append(", ");
			}
		}
		BaseDAO basebo = new BaseDAO();
		String sql = "update fbm_accountdetail set isliquid = 'Y' where pk_detail in "+ pk_details ;
		basebo.executeUpdate(sql);
	}
/*
* 备注：平台编写原始脚本
*/
public String getCodeRemark(){
	return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000173")/*@res "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n	Object retObj=null;\n	return retObj;\n"*/;}
/*
* 备注：设置脚本变量的HAS
*/
private void setParameter(String key,Object val)	{
	if (m_keyHas==null){
		m_keyHas=new Hashtable();
	}
	if (val!=null)	{
		m_keyHas.put(key,val);
	}
}
}