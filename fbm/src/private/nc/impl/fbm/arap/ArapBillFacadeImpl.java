package nc.impl.fbm.arap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.arap.outer.ArapPubAddInterface;
import nc.bs.arap.outer.ArapPubDelInterface;
import nc.bs.arap.outer.ArapPubEditInterface;
import nc.bs.arap.outer.IArapPubEffectInterface;
import nc.bs.arap.outer.IArapPubUnEffectInterface;
import nc.bs.fbm.out.AddPushBillValidator;
import nc.bs.fbm.out.ApAddBillValidator;
import nc.bs.fbm.out.ApDelBillValidator;
import nc.bs.fbm.out.ApEffectBillValidator;
import nc.bs.fbm.out.ApUneffectBillValidator;
import nc.bs.fbm.out.ArAddBillValidator;
import nc.bs.fbm.out.ArDelBillValidator;
import nc.bs.fbm.out.ArEffectBillValidator;
import nc.bs.fbm.out.ArUneffectBillValidator;
import nc.bs.fbm.out.ArapBillDataAdapter;
import nc.bs.fbm.out.DelPushBillValidator;
import nc.bs.fbm.out.EffectPushBillValidator;
import nc.bs.fbm.out.IBillValidator;
import nc.bs.fbm.out.ReturnBillAdd;
import nc.bs.fbm.out.ReturnBillDel;
import nc.bs.fbm.out.ReturnBillEffect;
import nc.bs.fbm.out.ReturnBillUnEffect;
import nc.bs.fbm.out.UnEffectPushBillValidator;
import nc.bs.fbm.pub.BaseInfoBO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.itf.cdm.util.CommonUtil;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.ep.dj.DJZBVO;
import nc.vo.ep.dj.DJZBVOConsts;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ExAggregatedVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;

/**
 * 票据实现收付报接口实现类
 *
 * @author xwq
 *
 */
public class ArapBillFacadeImpl implements ArapPubAddInterface,
		ArapPubEditInterface, ArapPubDelInterface,
		IArapPubEffectInterface, IArapPubUnEffectInterface {

	public DJZBVO afterAddAct(DJZBVO param) throws Exception {
		return dispatchAction(param,ArapBillDataAdapter.ACTION_ADD);
	}


	public DJZBVO beforeAddAct(DJZBVO param) throws Exception {
		// TODO Auto-generated method stub
		return addOrient(param);//添加
	}

	public void afterEditAct(DJZBVO oldVo, DJZBVO newVo) throws Exception {
		// 退票编辑不处理
		OuterRelationDAO relDao = new OuterRelationDAO();
		OuterVO[] outerVos = relDao.queryByPk_h(newVo.header.getPrimaryKey());
		if(!CommonUtil.isNull(outerVos)){
			if(outerVos[0].getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_RETURN)){
				return ;
			}
		}
		//先删除新增时增加的关联表数据
		afterDelAct(oldVo);
		afterAddAct(newVo);
	}

	public void beforeEditAct(DJZBVO oldVo, DJZBVO newVo) throws Exception {
		// TODO Auto-generated method stub
		newVo =  addOrient(newVo);
		// 退票编辑不处理
		OuterRelationDAO relDao = new OuterRelationDAO();
		OuterVO[] outerVos = relDao.queryByPk_h(newVo.header.getPrimaryKey());
		Integer lybz = newVo.header.getLybz();//来源标志
		String djdl = newVo.header.getDjdl();//单据大类
		if(!CommonUtil.isNull(outerVos)){
			if(outerVos[0].getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_RETURN) || lybz.intValue() == FbmBusConstant.SYSTEM_FBM){
				//2008.1.15 xwq 增加校验退票单不可修改
				if(djdl!="hj"){
					isFbmFieldChanged(oldVo,newVo);
				}
				return ;
			}
		}



	}


	public DJZBVO afterDelAct(DJZBVO param) throws Exception {
		// TODO Auto-generated method stub
//		Integer lybz = param.header.getLybz();//来源标志
//		//如果单据由票据系统生成，并且从保存态到暂存态
//		if((lybz.intValue() == FbmBusConstant.SYSTEM_FBM) && UFBoolean.TRUE.equals(param.header.fromSaveTotemporarily)){
//			throw new BusinessException("资金票据推式生成收付报单据不能由保存态变为暂存态");
//		}
		return dispatchAction(param,ArapBillDataAdapter.ACTION_DEL);
	}

	public DJZBVO beforeDelAct(DJZBVO param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	public DJZBVO afterEffectAct(ExAggregatedVO param) throws Exception {
		// TODO Auto-generated method stub
		return dispatchAction((DJZBVO)param.getAggregatedValueObject(),ArapBillDataAdapter.ACTION_EFFECT);
	}

	public DJZBVO beforeEffectAct(ExAggregatedVO param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public DJZBVO afterUnEffectAct(ExAggregatedVO param) throws Exception {
		return dispatchAction((DJZBVO)param.getAggregatedValueObject(),ArapBillDataAdapter.ACTION_UNEFFECT);
	}

	public DJZBVO beforeUnEffectAct(ExAggregatedVO param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 派发接口 请求到对应实现类
	 * @param param
	 * @param action
	 * @return
	 * @throws BusinessException
	 */
	private DJZBVO dispatchAction(DJZBVO param, String action) throws BusinessException{
		//排除没有启用票据产品的情况
		CommonDAO comDao = new CommonDAO();
		boolean fbmEnable = comDao.productEnableByCorp(param.header.getDwbm(), FbmBusConstant.SYSCODE_FBM);
		if(!fbmEnable){
			return param;
		}

		//排除期初数据
		UFBoolean qcbz = param.header.getQcbz();
		if(qcbz!= null && qcbz.booleanValue()){
			return param;
		}



		//非新增先查询关联表
		if(!action.equals(ArapBillDataAdapter.ACTION_ADD)){
			OuterRelationDAO relDao = new OuterRelationDAO();
			OuterVO[] outerVos = relDao.queryByPk_h(param.header.getPrimaryKey());
			if(!CommonUtil.isNull(outerVos)){
				if(outerVos[0].getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_RETURN)){
					param.header.setTransientFlag(DJZBVOConsts.From_quit_FtsPj);
				}
			}
		}


		String djdl = param.header.getDjdl();//单据大类
		Integer lybz = param.header.getLybz();//来源标志


		//排除应收单和应付单
		if(djdl.equals("ys") || djdl.equals("yf")){
			if(param.header.getTransientFlag()!= null
					&& param.header.getTransientFlag().equals(DJZBVOConsts.From_quit_FtsPj)){
				if(action.equals(ArapBillDataAdapter.ACTION_DEL)){
					OuterRelationDAO relDao = new OuterRelationDAO();
					DJZBItemVO[] items = (DJZBItemVO[])param.getChildrenVO();
					for(int i = 0 ; i < items.length ; i++){
						relDao.deleteRelation(items[i].getPrimaryKey());
					}
				}
			}
			return param;
		}

		IBillValidator validator = null;

		if(param.header.getTransientFlag()!= null
				&& param.header.getTransientFlag().equals(DJZBVOConsts.From_quit_FtsPj)){
			//退票生成单据
			 if(action.equals(ArapBillDataAdapter.ACTION_DEL)){
				 validator = new ReturnBillDel();
			 }else if(action.equals(ArapBillDataAdapter.ACTION_ADD)){
				 validator = new ReturnBillAdd();
			 }else if(action.equals(ArapBillDataAdapter.ACTION_EFFECT)){
				 validator = new ReturnBillEffect();
			 }else if(action.equals(ArapBillDataAdapter.ACTION_UNEFFECT)){
				 validator = new ReturnBillUnEffect();
			 }else{
				 return param;
			 }
		}else if(lybz.intValue() == FbmBusConstant.SYSTEM_FBM){//处理推式生成单据的操作
			 if(action.equals(ArapBillDataAdapter.ACTION_DEL)){
				 validator = new DelPushBillValidator();
			 }else if(action.equals(ArapBillDataAdapter.ACTION_ADD)){
				 validator = new AddPushBillValidator();
			 }else if(action.equals(ArapBillDataAdapter.ACTION_EFFECT)){
				 validator = new EffectPushBillValidator();
			 }else if(action.equals(ArapBillDataAdapter.ACTION_UNEFFECT)){
				 validator = new UnEffectPushBillValidator();
			 }else{
				 return param;
			 }
		}else{
			if(djdl.equals("fk") || djdl.equals("fj")){
				if(action.equals(ArapBillDataAdapter.ACTION_ADD)){
					validator = new ApAddBillValidator();
				}else if(action.equals(ArapBillDataAdapter.ACTION_DEL)){
					validator = new ApDelBillValidator();
				}else if(action.equals(ArapBillDataAdapter.ACTION_EFFECT)){
					validator = new ApEffectBillValidator();
				}else if(action.equals(ArapBillDataAdapter.ACTION_UNEFFECT)){
					validator = new ApUneffectBillValidator();
				}

			}else if(djdl.equals("sk") || djdl.equals("sj")){
				if(action.equals(ArapBillDataAdapter.ACTION_ADD)){
					validator = new ArAddBillValidator();
				}else if(action.equals(ArapBillDataAdapter.ACTION_DEL)){
					validator = new ArDelBillValidator();
				}else if(action.equals(ArapBillDataAdapter.ACTION_EFFECT)){
					validator = new ArEffectBillValidator();
				}else if(action.equals(ArapBillDataAdapter.ACTION_UNEFFECT)){
					validator = new ArUneffectBillValidator();
				}

			}else {
				return param;
			}
		}

		DJZBItemVO[] items = (DJZBItemVO[])param.getChildrenVO();

		if(CommonUtil.isNull(items)){
			return null;
		}
		for(int i = 0 ; i < items.length ; i++){
			//如果不是推式生成的单据并且结算方式不是承兑汇票
			if(!(lybz.intValue() == FbmBusConstant.SYSTEM_FBM) && !comDao.isFbmBill(items[i].getPj_jsfs())){
				continue;
			}
			ArapBillParamVO arapParam = ArapBillDataAdapter.buildBillParam(param,items[i],i,action);
			validator.checkBaseinfo(arapParam);
			validator.beforeAction(arapParam);
			validator.doCheck(arapParam);
			validator.afterAction(arapParam);
		}

		return param;
	}

	/**
	 * 设置应收应付属性
	 * 里面赋值应收应付返回
	 * @param param
	 * @return
	 */
	private DJZBVO addOrient(DJZBVO param) throws BusinessException{
		//排除期初数据
		UFBoolean qcbz = param.header.getQcbz();
		if(qcbz!= null && qcbz.booleanValue()){
			return param;
		}

		String djdl = param.header.getDjdl();//单据大类
		Integer lybz = param.header.getLybz();//来源标志
		//排除应收单和应付单
		if(djdl.equals("ys") || djdl.equals("yf")){
			return param;
		}

		DJZBItemVO[] items = (DJZBItemVO[])param.getChildrenVO();

		if(CommonUtil.isNull(items)){
			return param;
		}

		List<String> fbmbillnoList = new ArrayList<String>();
		for(int i = 0 ; i < items.length ; i++){
			if(!CommonUtil.isNull(items[i].getPjh())){
				fbmbillnoList.add(items[i].getPjh());
			}
		}

		BaseInfoBO bo = new BaseInfoBO();
		if(fbmbillnoList!=null && fbmbillnoList.size() > 0){
			Map<String,String> map = bo.queryOrientByBillno(fbmbillnoList.toArray(new String[0]));
			for(int i = 0 ; i < items.length ; i++){
				if(!CommonUtil.isNull(items[i].getPjh())){
					items[i].setpjdirection(map.get(items[i].getPjh()));
				}
			}
		}

		return param;
	}


	/**
	 * 检查退票生成单据 字段是否被修改
	 * @param oldVo
	 * @param newVo
	 * @throws BusinessException
	 */
	private void isFbmFieldChanged(DJZBVO oldVo, DJZBVO newVo) throws BusinessException{
		DJZBItemVO[] oldItems = (DJZBItemVO[])oldVo.getChildrenVO();
		DJZBItemVO[] newItems = (DJZBItemVO[])newVo.getChildrenVO();


		int oldLen = oldVo.items.length;
		Map<String,DJZBItemVO> oldMap = new HashMap<String,DJZBItemVO>();
		for(int i = 0;i< oldLen;i++){
			oldMap.put(oldItems[i].getFb_oid(), oldItems[i]);
		}

		int len = newVo.items.length ;
		//循环检查字段值
		for(int i = 0; i < len; i++){
			//newVo里不能有VO状态为DELETE的
			if(newItems[i].getStatus()  == VOStatus.DELETED){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000182")/*@res "资金票据生成单据不能删行"*/);
			}
			//newVo里不能有VO状态为NEW的
			if(newItems[i].getStatus() == VOStatus.NEW){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000183")/*@res "资金票据生成单据不能增行"*/);
			}
			DJZBItemVO oldTmp = oldMap.get(newItems[i].getFb_oid());

			if(oldTmp == null){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000184")/*@res "资金票据生成单据编辑后不可增行"*/);
			}

			if(!isEqual(oldTmp.getPjh(),newItems[i].getPjh())){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "资金票据生成单据，第"*/+(i+1)+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000186")/*@res "行票据编号不允许修改"*/);
			}
			if(!isEqual(oldTmp.getPj_jsfs(),newItems[i].getPj_jsfs())){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "资金票据生成单据，第"*/+(i+1)+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000187")/*@res "行结算方式不允许修改"*/);
			}
			if(!isEqual(oldTmp.getJfybje(),newItems[i].getJfybje())){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "资金票据生成单据，第"*/+(i+1)+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000188")/*@res "行金额不允许修改"*/);
			}
			if(!isEqual(oldTmp.getDfybje(),newItems[i].getDfybje())){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "资金票据生成单据，第"*/+(i+1)+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000188")/*@res "行金额不允许修改"*/);
			}
			if(!isEqual(oldTmp.getBzbm(),newItems[i].getBzbm())){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "资金票据生成单据，第"*/+(i+1)+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000189")/*@res "行币种不允许修改"*/);
			}
			if(!isEqual(oldTmp.getHbbm(),newItems[i].getHbbm())){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "资金票据生成单据，第"*/+(i+1)+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000190")/*@res "行客商不允许修改"*/);
			}


		}
	}

	/**
	 * 比较某字段值是否相等
	 * @param vo1
	 * @param vo2
	 * @param field
	 * @return
	 */
	private boolean isEqual(Object o1,Object o2){
		if(o1 == null && o2 == null){
			return true;
		}
		if((o1 == null && o2 !=null )||(o1 != null && o2 == null)){
			return false;
		}

		if(o1.equals(o2)){
			return true;
		}else{
			return false;
		}
	}


}