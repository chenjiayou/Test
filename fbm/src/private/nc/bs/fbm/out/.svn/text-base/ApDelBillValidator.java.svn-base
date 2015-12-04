package nc.bs.fbm.out;

import java.util.List;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.action.N_36GQ_DELETE;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;

public class ApDelBillValidator extends AbstractBillValidator {

	public void doCheck(ArapBillParamVO param)
			throws BusinessException {
		//如果是反向操作如果没有找到关联关系则直接返回
		if(param.getOuterVO() == null){
			return;
		}
		if(billnoSet.contains(param.getFbmbillno())){
			return;
		}
		String curStatus = param.getNewActionVO().getEndstatus();
		if(!(curStatus.equals(FbmStatusConstant.ON_ENDORE)
				|| curStatus.equals(FbmStatusConstant.ON_PAYBILL))){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000211")/* @res"票据状态不是在背书或在付票"*/);
		}
	}

	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		//如果是反向操作如果没有找到关联关系则直接返回
		if(param.getOuterVO() == null){
			return;
		}
		RegisterVO regVO = (RegisterVO)baseDao.retrieveByPK(RegisterVO.class, param.getNewActionVO().getPk_source());

		if(!billnoSet.contains(param.getFbmbillno())){
			List<EndoreVO> endoreList = (List<EndoreVO>) baseDao.retrieveByClause(EndoreVO.class,
					" isnull(dr,0)=0 and vbillstatus = 8 and pk_source = '" + regVO.getPrimaryKey()
							+ "'");
			EndoreVO[] vos = (EndoreVO[]) endoreList.toArray(new EndoreVO[0]);
			if (vos == null || vos.length == 0) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000212")/* @res"找不到背书办理单"*/);
			}


			// 删除背书办理单
			//BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.DELETE).doAction(vos[0], FbmActionConstant.DELETE, false);
			
			String typecode = vos[0].getPk_billtypecode();
			String pk_corp = vos[0].getPk_corp();
			
			if(regVO.getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_GATHER)){
				N_36GQ_DELETE delete = new N_36GQ_DELETE();
				PfParameterVO vo = new PfParameterVO();
				vo.m_preValueVo= new HYBillVO();
				vo.m_preValueVo.setParentVO(vos[0]);
				delete.runComClass(vo);
			}else{
				// 删除背书办理单
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.DELETE).doAction(vos[0], FbmActionConstant.DELETE, false);
				baseDao.deleteVO(vos[0]);
			}
			

//			baseDao.deleteVO(vos[0]);
			String vbillno = vos[0].getVbillno();
			if (vbillno != null) {
				OuterProxy.getBillCodeRuleService().returnBillCodeOnDelete(
						pk_corp,typecode,
						vbillno, null);
			}
			
		}
		// 删除外部关联关系
		if(param.getOuterVO() == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000388")/* @res"找不到票据和收付报单据的关联关系"*/);
		}
		outRelDao.deleteRelation(param.getOuterVO().getPk_outerbill_b());

		super.afterAction(param);
	}


}