package nc.bs.fbm.consignbank;

import nc.bs.fbm.gather.FBMPubQueryDAO;
import nc.bs.fbm.pub.UnVoucherCheckService;
import nc.bs.fbm.relief.AbstractGeneralVoucher;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;

public class ConsignBankVoucher extends AbstractGeneralVoucher {

	@Override
	public void completeDapMsgVO(DapMsgVO dapvo, SuperVO supervo,
			String unitType) {
		dapvo.setCurrency((String)supervo.getAttributeValue("ybbz"));
		dapvo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000130")/*@res "票据托收制证"*/);
		dapvo.setProcMsg(supervo.getPrimaryKey());
	}

	@Override
	public String[] getVONames() {
		// TODO Auto-generated method stub
		return new String[] {
				nc.vo.trade.pub.HYBillVO.class.getName(),
				nc.vo.fbm.consignbank.CollectionVO.class.getName(),
				nc.vo.fbm.consignbank.CollectionBVO.class.getName()
				};
	}


	/**
	 * <p>
	 * 制证 根据参数生成成员单位的总账凭证 生成结算中心的结算凭证
	 * 取消制证　根据isVoucher判断是制证还是取消制证。
	 * 需求修改：如果是结算单位登录点击制证按钮则只生成单位的总账凭证
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-3-19
	 * @param billVO
	 * @param opType   判断是制证、取消制证、冲销 voucher：制证 cancelvoucher:取消制证 clear:冲销
	 * @throws BusinessException
	 */
//	public AggregatedValueObject voucher(AggregatedValueObject billVO,String opType)
//			throws BusinessException {
//		if(FbmBusConstant.OP_CANCELVOUCHER.equals(opType)){
//			UnVoucherCheckService srv = new UnVoucherCheckService();
//			if (null != billVO && billVO.getParentVO() != null && billVO.getParentVO().getPrimaryKey() !=null){
//				srv.checkUnVoucher(billVO.getParentVO().getPrimaryKey());
//			}
//		}
//		if (null != billVO && billVO.getParentVO() != null) {
//			//得到当前登陆公司
//			String corpPK = InvocationInfoProxy.getInstance().getCorpCode();
//
//			SuperVO newVO = getSuperVO(billVO);
//
//			//判断公司是否为中心
//			if (isCenter(corpPK)) {
//				if(FbmBusConstant.OP_VOUCHER.equals(opType)) //判断是制证还是取消制证
//				{
//					//判断票据状态是否为已办理
//					if (IFBMStatus.Transact != ((Integer)newVO.getAttributeValue("vbillstatus")).intValue()) {
//						throw new BusinessException("票据状态不为已办理，不允许制证处理，请刷新后重试。");
//					}
//					voucherAction(billVO, FbmBusConstant.OP_VOUCHER,CENTER);// 生成中心结算凭证
//				}else  //取消制证
//				{
//					if (IFBMStatus.HAS_VOUCHER_VAR != ((Integer)newVO.getAttributeValue("vbillstatus")).intValue()) {// 已中心制证
//						throw new BusinessException("目前单据状态不为已制证，不允许取消制证，请刷新后重试。");
//					}
//					voucherAction(billVO, FbmBusConstant.OP_CANCELVOUCHER,CENTER);// 删除中心结算凭证
//				}
//			} else {// 单位操作进行制证操作
//				if(FbmBusConstant.OP_VOUCHER.equals(opType))
//				{
//					//判断是否为已中心制证
//					if (IFBMStatus.HAS_VOUCHER_VAR != ((Integer)newVO.getAttributeValue("vbillstatus")).intValue()) {
//						throw new BusinessException("票据不为已制证，不允许制证处理，请刷新后重试。");
//					}
//					voucherAction(billVO, FbmBusConstant.OP_VOUCHER,UNIT);// 生成单位总账凭证
//				}else
//				{
//					if (IFBMStatus.HAS_UNIT_VOUCHER != ((Integer)newVO.getAttributeValue("vbillstatus")).intValue())
//					{// 已单位制证
//						throw new BusinessException("目前单据状态不为已单位制证，不允许取消制证，请刷新后重试。");
//					}
//					voucherAction(billVO, FbmBusConstant.OP_CANCELVOUCHER,UNIT);// 删除单位总账凭证
//				}
//			}
//		}
//		return billVO;
//	}

	@Override
	public String handelTakenSqlValue(SuperVO supervo) {

		CollectionVO collvo = (CollectionVO)supervo;
		StringBuffer sql = new StringBuffer();
		sql.append(" select fbm_collection.pk_collection,fbm_collection.pk_source,fbm_collection.pk_baseinfo,");
		sql.append(" fbm_baseinfo.fbmbillno fbmbillno,fbm_baseinfo.payunit,");
		sql.append(" fbm_baseinfo.receiveunit,fbm_baseinfo.pk_curr,");
		sql.append(" fbm_baseinfo.moneyy,fbm_baseinfo.invoicedate,fbm_register.gatherdate,");
		sql.append(" fbm_baseinfo.enddate,fbm_baseinfo.invoiceunit,");
		sql.append(" fbm_register.keepunit,fbm_register.paybillunit,fbm_register.moneyb,fbm_register.brate, ");
		sql.append(" fbm_register.moneyf,fbm_register.frate,fbm_baseinfo.fbmbilltype ");
		sql.append(" from fbm_collection left join fbm_register ");
		sql.append(" on (fbm_collection.pk_source=fbm_register.pk_register) join fbm_baseinfo ");
		sql.append(" on (fbm_register.pk_baseinfo=fbm_baseinfo.pk_baseinfo) where ");

		sql.append(" pk_collection ='" + collvo.getPk_collection() + "' ");

		return sql.toString();
	}

	@Override
	public void setValues(AggregatedValueObject vo, String sql)
			throws BusinessException {
		FBMPubQueryDAO dao = new FBMPubQueryDAO();
		SuperVO queryVos = dao.querySingleData(sql, CollectionVO.class);
		vo.setParentVO(queryVos);

	}


}