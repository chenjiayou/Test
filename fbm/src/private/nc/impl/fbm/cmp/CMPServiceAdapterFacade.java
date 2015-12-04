package nc.impl.fbm.cmp;

import java.util.HashMap;
import java.util.Map;

import nc.bs.fbm.out.ArapBillDataAdapter;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cmp.plugin.ICMPAddPlugin;
import nc.itf.cmp.plugin.ICMPSignPlugin;
import nc.vo.cmp.BusiStatus;
import nc.vo.cmp.settlement.SettlementAggVO;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.cmp.settlement.SettlementHeadVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;

/**
 * 实现现金平台提供接口 命令模式统一处理 为方便使用老代码所有数据校验都在after方法中进行， 前置方法不予理会，此种方式效率较低，有要求需要重构
 *
 * @author wues
 *
 */
public class CMPServiceAdapterFacade extends AbstractCommandProcess implements
		ICMPAddPlugin, ICMPSignPlugin {

	/**
	 * 用票据进行支付时需要票据系统提供票据的方向(是用收票支付还是开票支付)
	 */
	public void beforeAdd(SettlementAggVO info) throws BusinessException {
		if (info == null) {
			return;
		}

		CmpHelper.debug(info, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
				.getStrByID("3620add", "UPP3620ADD-000212")/*
															 * @res
															 * "beforeAdd方法开始==>>>>>\n"
															 */, "");
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {

			// 处理红冲单据
			dealRedBill(info,ArapBillDataAdapter.ACTION_ADD);

			// 设置票据方向
			addOrient(info,ArapBillDataAdapter.ACTION_ADD);

			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000213")/* @res "<<<<<<======beforeAdd方法=======>>>>>结束" */);

		}

		
	}

	/**
	 * 业务单据新增保存
	 */
	public void afterAdd(SettlementAggVO info) throws BusinessException {
		if (info == null) {
			return;
		}

		CmpHelper
				.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
						"3620add", "UPP3620ADD-000214")/* @res "<<<<<<======afterAdd方法=======>>>>>开始==>>" */);
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {

			// 处理红冲单据
			dealRedBill(info,ArapBillDataAdapter.ACTION_ADD);

			// 增加后额外处理
			doProcess(info, getClass(info, ArapBillDataAdapter.ACTION_ADD),ArapBillDataAdapter.ACTION_ADD);

			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000215")/* @res "<<<<<<======afterAdd方法=======>>>>>结束==>>" */);
		}
	}

	/**
	 * 增加收付方向，同时判断退票生成不与处理
	 */
	public void beforeEdit(SettlementAggVO oldinfo, SettlementAggVO newinfo)
			throws BusinessException {
		if (newinfo == null || oldinfo == null) {
			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000216")/*
																		 * @res
																		 * "=========newinfo或oldinfo为空========="
																		 */);
			return;
		}
		CmpHelper
				.debug(
						newinfo,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"3620add", "UPP3620ADD-000217")/* @res "<<<<<<======beforeEdit方法=======>>>>>开始==>>" */,
						"");

		// 判断是否为升级数据，如果为升级数据直接抛异常处理
		// updateDataDealer(newinfo); 090106
		// 与xwq确认，处理开始未引用票据，编辑引用票据情况，升级数据不必在edit中校验
		if (isFBMEnabled(((SettlementHeadVO) newinfo.getParentVO())
				.getPk_corp())) {
			// 添加票据方向
			addOrient(newinfo,ArapBillDataAdapter.ACTION_EDIT);

			// 退票生成单据不处理
			noProcessReturnBill(oldinfo, newinfo);

			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000218")/*@res "<<<<<<======beforeEdit方法=======>>>>>结束==>>"*/);
		}
	}

	/**
	 * 业务单据编辑保存
	 */
	public void afterEdit(SettlementAggVO oldinfo, SettlementAggVO newinfo)
			throws BusinessException {
		//收款单暂存，修改，再暂存时，会走到这里向fbm_outer表写数据，这种情况是不正确的
		//所以加上如下判断，当单据是暂存时，直接返回即可。
		SettlementHeadVO newHeadVO = (SettlementHeadVO)newinfo.getParentVO();
		if(newHeadVO.getBusistatus().equals(BusiStatus.Tempeorary.getBillStatusKind())){
			// 先删
			afterDelete(oldinfo);
			return ;
		}
		if (newinfo == null) {
			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000219")/*
																		 * @res "
																		 * =======newinfo为空========="
																		 */);
			return;
		}
		CmpHelper
				.debug(
						newinfo,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"3620add", "UPP3620ADD-000218")/* @res "<<<<<<======beforeEdit方法=======>>>>>结束==>>" */,
						"");
		if (isFBMEnabled(((SettlementHeadVO) newinfo.getParentVO())
				.getPk_corp())) {
			// 退票生成的单据不与处理
			if (isReturnBil(oldinfo, newinfo)) {
				return;
			}

			// 非ADD，需要进行前续处理，补全信息看是否为退票生成单据
			noAddProcess(newinfo,ArapBillDataAdapter.ACTION_EDIT);

			// 先删
			afterDelete(oldinfo);

			// 后插
			afterAdd(newinfo);
		}
	}

	/**
	 * 业务单据删除
	 */
	public void afterDelete(SettlementAggVO info) throws BusinessException {
		if (info == null) {
			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000220")/*
																		 * @res
																		 * "===========info为空============="
																		 */);
			return;
		}
		CmpHelper
				.debug(
						info,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"3620add", "UPP3620ADD-000221")/* @res "<<<<<<======afterDelete方法=======>>>>>开始==>>" */,
						"");
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {
			// 非ADD，需要进行前续处理，补全信息看是否为退票生成单据
			noAddProcess(info,ArapBillDataAdapter.ACTION_DEL);

			// 排除应收应付单的删除操作
			boolean isContinuous = processYsYf(info);
			if (false == isContinuous) {
				return;
			}

			// 删除处理
			doProcess(info, getClass(info, ArapBillDataAdapter.ACTION_DEL),ArapBillDataAdapter.ACTION_DEL);
		}
	}

	/**
	 * 签字后
	 */
	public void afterSign(SettlementAggVO info) throws BusinessException {
		if (info == null) {
			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000222")/*
																		 * @res
																		 * "=======info为空================="
																		 */);
			return;
		}

		CmpHelper
				.debug(
						info,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"3620add", "UPP3620ADD-000223")/* @res "<<<<<<======afterSign方法=======>>>>>开始==>>" */,
						"");
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {
			noAddProcess(info,ArapBillDataAdapter.ACTION_EFFECT);
			// 签字后调用方法
			doProcess(info, getClass(info, ArapBillDataAdapter.ACTION_EFFECT),ArapBillDataAdapter.ACTION_EFFECT);

			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000224")/*@res "<<<<<<======afterSign方法=======>>>>>结束==>>"*/);
		}
	}

	/**
	 * 取消签字后
	 */
	public void afterReverseSign(SettlementAggVO info) throws BusinessException {
		if (info == null) {
			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000222")/*
																		 * @res
																		 * "=======info为空================="
																		 */);
			return;
		}

		CmpHelper
				.debug(
						info,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"3620add", "UPP3620ADD-000225")/* @res "<<<<<<======afterReverseSign方法=======>>>>>开始==>>" */,
						"");
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {
			noAddProcess(info,ArapBillDataAdapter.ACTION_UNEFFECT);
			// 取消签字后调用方法
			doProcess(info, getClass(info, ArapBillDataAdapter.ACTION_UNEFFECT),ArapBillDataAdapter.ACTION_UNEFFECT);

			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000226")/*@res "<<<<<<======afterReverseSign方法=======>>>>>结束==>>"*/);
		}
	}

	/**
	 * 签字时判断是否为升级数据，如果为升级数据不允许签字
	 */
	public void beforeSign(SettlementAggVO info) throws BusinessException {
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {
			// 判断是否为升级数据，如果为升级数据直接抛异常处理
			updateDataDealer(info,ArapBillDataAdapter.ACTION_EFFECT);
		}
	}

	
	/**
	 * 暂存时需要校验票据编号在票据系统中是否存在 
	 * @see nc.itf.cmp.plugin.ICMPAddPlugin#aftertempsave(nc.vo.cmp.settlement.SettlementAggVO)
	 */
	public void aftertempsave(SettlementAggVO info) throws BusinessException {
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {
			SettlementBodyVO[] vos = (SettlementBodyVO[]) info.getChildrenVO();
			SettlementHeadVO head = (SettlementHeadVO) info.getParentVO();
			//NCdp201058738 
			//具体操作步骤：
			 //增加一张委托收款结算流程的收款结算单，先不勾选“申请委托收款”标志，点保存。然后点修改，勾选上“申请委托收款”标志，点暂存。然后再点修改，去掉“申请委托收款”标志的勾，点保存。就会提示“保存失败 票据已经被其他单据使用”
			afterDelete(info);
			
			for (int i = 0; !CommonUtil.isNull(vos) && i < vos.length; i++) {
				isNeedFbmDeal(vos[i], head,ArapBillDataAdapter.ACTION_ADD);// 判断每一行表体是否需要fbm处理
			}
		}
	}
	
	public void beforeReverseSign(SettlementAggVO info)
			throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void afterFtsGather(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void afterFtsPay(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void aftertempDelete(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}


	public void beforeDelete(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void beforeFtsGather(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void beforeFtsPay(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void beforetempDelete(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void beforetempsave(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	/**
	 * 处理应收应付，如果不满足条件则返回false进行后续处理，否则返回ture，直接退出
	 *
	 * @return
	 * @throws BusinessException
	 */
	private boolean processYsYf(SettlementAggVO info) throws BusinessException {
		SettlementHeadVO headVO = (SettlementHeadVO) info.getParentVO();
		boolean returnBill = info.isReturnBill();
		String billType = headVO.getPk_tradetype();

		// 排除应收单和应付单
		if (billType.equals(CMPConstant.BILLTYPE_YS)
				|| billType.equals(CMPConstant.BILLTYPE_YF)) {
			if (returnBill) {//退票
				OuterRelationDAO relDao = new OuterRelationDAO();
				SuperVO[] items = (SuperVO[]) info.getChildrenVO();
				for (int i = 0; i < items.length; i++) {
					relDao.deleteRelation(items[i].getPrimaryKey());
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据动作类型和相应的单据类型获得需要的处理类
	 *
	 * @param info
	 * @param action
	 * @return
	 */
	private String getClass(SettlementAggVO info, String action)
			throws BusinessException {
		SettlementHeadVO vo = (SettlementHeadVO) info.getParentVO();

		if (info.isReturnBill()) {// 当前单据为退票生成
			String key = action + "_" + CMPConstant.BILLTYPE_RETURN;
			return CMPConstant.getMap().get(key);
		}

		String parentBillType = getDjdl(vo);
		if (CommonUtil.isNull(parentBillType)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000227")/*@res "根据单据类型："*/ + vo.getPk_tradetype()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000228")/*@res "；取得的单据大类为空。"*/);
		}

		vo.setDef11(parentBillType);//def11用来临时放置单据大类，避免每次处理都从数据库查询

		String key = action + "_" + parentBillType;

		return CMPConstant.getMap().get(key);
	}
	
	
	/**
	 * 判断是否为退票生成
	 *
	 * @return
	 */
	private boolean isReturnBil(SettlementAggVO oldInfo, SettlementAggVO newInfo)
			throws BusinessException {
		// 退票编辑不处理
		OuterRelationDAO relDao = new OuterRelationDAO();
		OuterVO[] outerVos = relDao.queryByPk_h(newInfo.getParentVO()
				.getPrimaryKey());
		if (!CommonUtil.isNull(outerVos)) {
			if (outerVos[0].getPk_billtypecode().equals(
					FbmBusConstant.BILLTYPE_RETURN)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 退票编辑不予处理
	 *
	 * @param oldinfo
	 * @param newinfo
	 * @throws BusinessException
	 */
	private void noProcessReturnBill(SettlementAggVO oldinfo,
			SettlementAggVO newinfo) throws BusinessException {
		SettlementHeadVO vo = (SettlementHeadVO) newinfo.getParentVO();

		String pk_billtype = vo.getPk_tradetype();// 单据类型
		
		if (isReturnBil(oldinfo, newinfo)
				|| ProductInfo.pro_FBM.equals(vo.getSystemcode())) {
			if (pk_billtype != CMPConstant.BILLTYPE_HJ) {
				isFbmFieldChanged(oldinfo, newinfo);
			}
			return;
		}
	}

	/**
	 * 检查退票生成单据 字段是否被修改
	 *
	 * @param oldVo
	 * @param newVo
	 * @throws BusinessException
	 */
	private void isFbmFieldChanged(SettlementAggVO oldVo, SettlementAggVO newVo)
			throws BusinessException {
		SettlementBodyVO[] oldItems = (SettlementBodyVO[]) oldVo
				.getChildrenVO();
		SettlementBodyVO[] newItems = (SettlementBodyVO[]) newVo
				.getChildrenVO();

		int oldLen = oldVo.getChildrenVO().length;
		Map<String, SettlementBodyVO> oldMap = new HashMap<String, SettlementBodyVO>();
		for (int i = 0; i < oldLen; i++) {
			oldMap.put(oldItems[i].getPk_bill(), oldItems[i]);
		}

		int len = newVo.getChildrenVO().length;
		// 循环检查字段值
		for (int i = 0; i < len; i++) {
			// newVo里不能有VO状态为DELETE的
			if (newItems[i].getStatus() == VOStatus.DELETED) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000182")/*@res "资金票据生成单据不能删行"*/);
			}
			// newVo里不能有VO状态为NEW的
			if (newItems[i].getStatus() == VOStatus.NEW) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000183")/*@res "资金票据生成单据不能增行"*/);
			}
			SettlementBodyVO oldTmp = oldMap.get(newItems[i].getPk_bill());

			if (oldTmp == null) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000184")/*@res "资金票据生成单据编辑后不可增行"*/);
			}

			if (!isEqual(oldTmp.getNotenumber(), newItems[i].getNotenumber())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "资金票据生成单据，第"*/ + (i + 1)
						+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000186")/*@res "行票据编号不允许修改"*/);
			}
			if (!isEqual(oldTmp.getReceive(), newItems[i].getReceive())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "资金票据生成单据，第"*/ + (i + 1) + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000188")/*@res "行金额不允许修改"*/);
			}
			if (!isEqual(oldTmp.getPay(), newItems[i].getPay())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "资金票据生成单据，第"*/ + (i + 1) + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000188")/*@res "行金额不允许修改"*/);
			}
			if (!isEqual(oldTmp.getPk_currtype(), newItems[i].getPk_currtype())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "资金票据生成单据，第"*/ + (i + 1) + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000189")/*@res "行币种不允许修改"*/);
			}
			if (!isEqual(oldTmp.getPk_trader(), newItems[i].getPk_trader())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "资金票据生成单据，第"*/ + (i + 1)
						+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000229")/*@res "行对方名称不允许修改"*/);
			}

		}
	}

	/**
	 * 比较某字段值是否相等
	 *
	 * @param vo1
	 * @param vo2
	 * @param field
	 * @return
	 */
	private boolean isEqual(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		}
		if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
			return false;
		}

		if (o1.equals(o2)) {
			return true;
		} else {
			return false;
		}
	}

}