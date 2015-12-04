package nc.bs.fbm.plan;

import nc.bs.framework.common.NCLocator;
import nc.itf.fp.pub.IFtsPlan;
import nc.itf.uap.busibean.SysinitAccessor;
import nc.itf.uap.sf.ICreateCorpQueryService;
import nc.vo.cdm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.fp.pub.IOBudgetQueryVO;
import nc.vo.fp.pub.IOBudgetRetVO;
import nc.vo.fp.pub.IPlanConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;

/**
 * 票据与资金计划接口类
 * @author xwq
 *
 * 2008-9-19
 */
public class PlanFacade {
	
	private IFtsPlan plan = null;//资金计划接口类
	
	/**
	 * 插入执行计划数
	 * @param regvo
	 * @param pk_corp
	 * @throws BusinessException
	 */
	public void insertPlanExec(SuperVO vo,String pk_corp) throws BusinessException{
		insertPlanExec(vo,pk_corp,null);
	}
	
	/**
	 * 插入执行计划数
	 * @param vo
	 * @param pk_corp
	 * @param actioncode
	 */
	public void insertPlanExec(SuperVO vo,String pk_corp,String actioncode)  throws BusinessException{
		//检查资金计划是否安装
		ICreateCorpQueryService productSrv = (ICreateCorpQueryService)NCLocator.getInstance().lookup(ICreateCorpQueryService.class.getName());
		
		SysinitAccessor initAccessor = SysinitAccessor.getInstance();
		initAccessor.clearInvalidPara(pk_corp, new String[]{FBMParamConstant.FBM005});
		
		UFBoolean isTogather = OuterProxy.getSysInitQry().getParaBoolean(pk_corp, FBMParamConstant.FBM005);
		if(!isTogather.booleanValue()){
			return;
		}
		if(productSrv.isEnabled(pk_corp, ProductInfo.pro_FP)){
			IOBudgetQueryVO[] vos = null;
			vos = FbmBill2PlanAdapter.bill2PlanVO(vo,actioncode);
			
			UFBoolean writerplan = (UFBoolean) vo.getAttributeValue("writeplan");
			
			if(vo instanceof ReturnVO){
				if(vo.getAttributeValue(ReturnVO.RETURNTYPE).equals(FbmBusConstant.BACK_TYPE_INVOICE)){
					writerplan = new UFBoolean(true);
				}
			}else if (vo instanceof RegisterVO)
			{
				if("DESTROY".equals(actioncode)){
					writerplan = new UFBoolean(true);
				}
			}
			if (null == writerplan
					|| UFBoolean.FALSE.equals(writerplan)) {// 检查并回写
				IOBudgetRetVO rvo = getPlan().updateIOBudget(vos,
						Boolean.FALSE, Boolean.TRUE, Boolean.TRUE);
				throwException(rvo);
			} else {// 强制回写
				getPlan().updateIOBudget(vos, Boolean.TRUE, Boolean.TRUE,
						Boolean.TRUE);
			}
		}
	}
	
	public void delPlanExec(SuperVO vo,String pk_corp,String actioncode) throws BusinessException{
		
		UFBoolean isTogather = OuterProxy.getSysInitQry().getParaBoolean(pk_corp, FBMParamConstant.FBM005);
		if(!isTogather.booleanValue()){
			return;
		}
		//检查资金计划是否安装
		ICreateCorpQueryService productSrv = (ICreateCorpQueryService)NCLocator.getInstance().lookup(ICreateCorpQueryService.class.getName());
		if(productSrv.isEnabled(pk_corp, ProductInfo.pro_FP)){
			IOBudgetQueryVO[] vos = FbmBill2PlanAdapter.bill2PlanVO(vo,actioncode);
			for(IOBudgetQueryVO v :vos){
				v.setWriteperformdetailflag(IPlanConst.WritePerformDetailFlag_Delete);
			}
			getPlan().updateIOBudget(minusQueryVO(vos), Boolean.TRUE,Boolean.TRUE, Boolean.TRUE);
		}
	}
	/**
	 * 删除资金计划数
	 * @param regvo
	 * @param pk_corp
	 * @throws BusinessException
	 */
	public void delPlanExec(SuperVO vo,String pk_corp) throws BusinessException{
		delPlanExec(vo,pk_corp,null);
	}
	
	/**
	 * 获得计划服务
	 * @return
	 */
	private IFtsPlan getPlan() {
		if (null == plan) {
			plan = (IFtsPlan) NCLocator.getInstance().lookup(IFtsPlan.class);
		}
		return plan;
	}
	
	/**
	 * 将IOBudgetQueryVO[] vos中的金额变为负数，删除计划执行数时需要调用此方法取反
	 * 
	 * @param vos
	 */
	private IOBudgetQueryVO[] minusQueryVO(IOBudgetQueryVO[] vos) {
		for (int i = 0; null != vos && i < vos.length; i++) {
			if (null != vos[i].getAmount()
					&& vos[i].getAmount().doubleValue() != 0) {
				vos[i].setAmount(vos[i].getAmount().multiply(-1));
			}
		}
		return vos;
	}
	
 
	
	/**
	 * 根据计划返回的消息抛出异常, copied from FP
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	private void throwException(IOBudgetRetVO vo) throws BusinessException {
		int iReturn = vo.getRettype();
		String hintString = vo.getHintmessage();

		if (iReturn == 2) {// 严格限制不通过
			throw new BusinessException("10002" + hintString);
		}

		if (iReturn == 3) {
			throw new BusinessException("10003" + hintString);
		}

	}
}
