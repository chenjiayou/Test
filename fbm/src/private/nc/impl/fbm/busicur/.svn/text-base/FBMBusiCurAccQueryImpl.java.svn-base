package nc.impl.fbm.busicur;

import java.util.ArrayList;
import java.util.List;

import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.fts.busicurraccount.IBusiCurAccQuery;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.fts.busicurraccount.BusiCurAccConst;
import nc.vo.fts.busicurraccount.BusiCurAccQueryVO;
import nc.vo.fts.busicurraccount.BusiCurAccVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.IBillStatus;

/**
 * 
 * @author xwq
 * 
 *         2008-10-24
 */
public class FBMBusiCurAccQueryImpl implements IBusiCurAccQuery {
	public String getSourceSystem() {
		// TODO Auto-generated method stub
		return FbmBusConstant.SYSCODE_FBM;
	}

	public BusiCurAccVO[] queryBusiCurAccVos(BusiCurAccQueryVO queryvo)
			throws BusinessException {

		// String[] systemtypes = queryvo.getSystemtype();
		String[] billtypes = queryvo.getBilltype();

		// boolean isfbmsystem = false;
		// if (systemtypes != null && systemtypes.length > 0) {
		// for (int i = 0; i < systemtypes.length; i++) {
		// if (FbmBusConstant.SYSCODE_FBM
		// .equalsIgnoreCase(systemtypes[i])) {
		// isfbmsystem = true;
		// }
		// }
		// }
		// if (!isfbmsystem) {
		// return null;
		// }
		List<BusiCurAccVO[]> voarrays = new ArrayList<BusiCurAccVO[]>();
		if (billtypes != null && billtypes.length > 0) {
			for (int i = 0; i < billtypes.length; i++) {
				if (FbmBusConstant.BILLTYPE_INNERKEEP.equalsIgnoreCase(billtypes[i])) {
					BusiCurAccVO[] curvos = queryInnerKeep(queryvo);
					if (curvos != null && curvos.length > 0) {
						voarrays.add(curvos);
					}
				} else if (FbmBusConstant.BILLTYPE_INNERBACK.equals(billtypes[i])) {
					BusiCurAccVO[] curvos = queryInnerBack(queryvo);
					if (curvos != null && curvos.length > 0) {
						voarrays.add(curvos);
					}
				} else if (FbmBusConstant.BILLTYPE_RELIEF.equals(billtypes[i])) {
					BusiCurAccVO[] curvos = queryRelief(queryvo);
					if (curvos != null && curvos.length > 0) {
						voarrays.add(curvos);
					}
				} else if (FbmBusConstant.BILLTYPE_LIQUIDATE.equals(billtypes[i])) {
					BusiCurAccVO[] curvos = queryReckon(queryvo);
					if (curvos != null && curvos.length > 0) {
						voarrays.add(curvos);
					}
				}
			}
		} else {
			BusiCurAccVO[] innerKeepCurvos = queryInnerKeep(queryvo);
			if (innerKeepCurvos != null && innerKeepCurvos.length > 0) {
				voarrays.add(innerKeepCurvos);
			}

			BusiCurAccVO[] innerBackCurvos = queryInnerBack(queryvo);
			if (innerBackCurvos != null && innerBackCurvos.length > 0) {
				voarrays.add(innerBackCurvos);
			}

			BusiCurAccVO[] reliefCurvos = queryRelief(queryvo);
			if (reliefCurvos != null && reliefCurvos.length > 0) {
				voarrays.add(reliefCurvos);
			}

			BusiCurAccVO[] reckonCurvos = queryReckon(queryvo);
			if (reckonCurvos != null && reckonCurvos.length > 0) {
				voarrays.add(reckonCurvos);
			}
		}

		List<BusiCurAccVO> busivosArray = new ArrayList<BusiCurAccVO>();
		if (voarrays != null && voarrays.size() > 0) {
			for (int i = 0; i < voarrays.size(); i++) {
				BusiCurAccVO[] vos = voarrays.get(i);
				if (vos != null && vos.length > 0) {
					for (int j = 0; j < vos.length; j++) {
						busivosArray.add(vos[j]);
					}
				}
			}
		}

		BusiCurAccVO[] busivos = null;

		if (busivosArray != null && busivosArray.size() > 0) {
			busivos = new BusiCurAccVO[busivosArray.size()];
			busivos = busivosArray.toArray(new BusiCurAccVO[] {});
		}

		return busivos;
	}

	/**
	 * 查询内部托管数据
	 * 
	 * @param queryvo
	 * @return
	 * @throws BusinessException
	 */
	private BusiCurAccVO[] queryInnerKeep(BusiCurAccQueryVO queryvo)
			throws BusinessException {
		String dealbegindate = queryvo.getDealbegindate();
		String dealenddate = queryvo.getDealenddate();
		String billstatus = queryvo.getBillstatus();
		String pk_centercorp = queryvo.getPk_corpcenter();
		String pk_currtype = queryvo.getPk_currtype();

		StringBuffer wherepart = new StringBuffer();
		wherepart.append(" isnull(dr,0) = 0 and pk_billtypecode='"
				+ FbmBusConstant.BILLTYPE_INNERKEEP
				+ "' ");
		if (dealbegindate != null && !dealbegindate.equalsIgnoreCase("")) {
			wherepart.append(" and dealdate >= '").append(dealbegindate).append("' ");
		}

		if (dealenddate != null && !dealenddate.equalsIgnoreCase("")) {
			wherepart.append(" and dealdate <= '").append(dealenddate).append("' ");
		}

		if (billstatus != null && !billstatus.equalsIgnoreCase("")) {
			if (billstatus.equalsIgnoreCase(BusiCurAccConst.BILLSTATUSFORQUERY_DEALING_BusiCurAcc)) {
				wherepart.append(" and ((pk_corp='"
						+ pk_centercorp
						+ "' and vbillstatus="
						+ IBillStatus.CHECKPASS
						+ ")");
				wherepart.append(" or (");
				wherepart.append(getCorpSQL(pk_centercorp));
				wherepart.append(" and vbillstatus=" + IFBMStatus.SUBMIT + "))");

			} else if (billstatus.equalsIgnoreCase(BusiCurAccConst.BILLSTATUSFORQUERY_DEALED_BusiCurAcc)) {
				wherepart.append(" and vbillstatus = ").append(IFBMStatus.INPUT_SUCCESS).append(" and ( ");
				wherepart.append(" pk_corp='" + pk_centercorp + "' or ");
				wherepart.append(getCorpSQL(pk_centercorp));
				wherepart.append(" )");
			} else {
				wherepart.append(" and ");
				wherepart.append(" ((pk_corp='"
						+ pk_centercorp
						+ "' and vbillstatus="
						+ IBillStatus.CHECKPASS
						+ ")");
				wherepart.append(" or (");
				wherepart.append(getCorpSQL(pk_centercorp));
				wherepart.append(" and vbillstatus=" + IFBMStatus.SUBMIT + ")");
				wherepart.append(" or (vbillstatus = ").append(IFBMStatus.INPUT_SUCCESS).append(" and ( ");
				wherepart.append(" pk_corp='" + pk_centercorp + "' or ");
				wherepart.append(getCorpSQL(pk_centercorp));
				wherepart.append(" )))");
			}
		}

		if (!CommonUtil.isNull(pk_currtype)) {
			wherepart.append(" and pk_currtype ='").append(pk_currtype).append("' ");
		}

		HYPubBO bo = new HYPubBO();
		StorageVO[] vos = null;
		vos = (StorageVO[]) bo.queryByCondition(StorageVO.class, wherepart.toString());
		String billStatus = "";

		BusiCurAccVO[] curvos = null;
		if (vos != null && vos.length > 0) {
			ArrayList<BusiCurAccVO> curaccvoArray = new ArrayList<BusiCurAccVO>();
			for (int i = 0; i < vos.length; i++) {
				BusiCurAccVO tempvo = new BusiCurAccVO();
				tempvo.setBusinessno(vos[i].getBusinessno());
				tempvo.setSystem(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000191")/*
																													 * @res
																													 * "票据管理"
																													 */);
				tempvo.setBilltype(vos[i].getPk_billtypecode());
				tempvo.setBilltypename(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000192")/*
																															 * @res
																															 * "内部托管单"
																															 */);
				tempvo.setVbillno(vos[i].getVbillno());
				if (vos[i].getVbillstatus().equals(IFBMStatus.INPUT_SUCCESS)) {
					billStatus = BusiCurAccConst.BILLSTATUSFORQUERY_DEALED_BusiCurAcc;
				} else {
					billStatus = BusiCurAccConst.BILLSTATUSFORQUERY_DEALING_BusiCurAcc;
				}
				tempvo.setBillstatus(billStatus);
				tempvo.setMoney(vos[i].getSummoneyy());
				tempvo.setDealdate(vos[i].getDealdate());
				tempvo.setPk_sourcebill(vos[i].getPrimaryKey());
				tempvo.setPk_corp(queryvo.getPk_corpcenter());
				tempvo.setPk_currtype(vos[i].getPk_currtype());
				curaccvoArray.add(tempvo);
			}

			if (curaccvoArray != null && curaccvoArray.size() > 0) {
				curvos = new BusiCurAccVO[curaccvoArray.size()];
				curvos = curaccvoArray.toArray(new BusiCurAccVO[] {});
			}
		}
		return curvos;
	}

	/**
	 * 查询内部领用数据
	 * 
	 * @param queryvo
	 * @return
	 * @throws BusinessException
	 */
	private BusiCurAccVO[] queryInnerBack(BusiCurAccQueryVO queryvo)
			throws BusinessException {
		String dealbegindate = queryvo.getDealbegindate();
		String dealenddate = queryvo.getDealenddate();
		String billstatus = queryvo.getBillstatus();
		String pk_centercorp = queryvo.getPk_corpcenter();
		String pk_currtype = queryvo.getPk_currtype();

		StringBuffer wherepart = new StringBuffer();
		wherepart.append(" isnull(dr,0) = 0 and pk_billtypecode='"
				+ FbmBusConstant.BILLTYPE_INNERBACK
				+ "' ");
		if (dealbegindate != null && !dealbegindate.equalsIgnoreCase("")) {
			wherepart.append(" and dealdate >= '").append(dealbegindate).append("' ");
		}

		if (dealenddate != null && !dealenddate.equalsIgnoreCase("")) {
			wherepart.append(" and dealdate <= '").append(dealenddate).append("' ");
		}

		if (!CommonUtil.isNull(pk_currtype)) {
			wherepart.append(" and pk_currtype ='").append(pk_currtype).append("' ");
		}

		if (billstatus != null && !billstatus.equalsIgnoreCase("")) {
			if (billstatus.equalsIgnoreCase(BusiCurAccConst.BILLSTATUSFORQUERY_DEALING_BusiCurAcc)) {
				wherepart.append(" and ((pk_corp='"
						+ pk_centercorp
						+ "' and vbillstatus="
						+ IBillStatus.CHECKPASS
						+ ")");
				wherepart.append(" or (");
				wherepart.append(getCorpSQL(pk_centercorp));
				wherepart.append(" and vbillstatus=" + IFBMStatus.SUBMIT + "))");

			} else if (billstatus.equalsIgnoreCase(BusiCurAccConst.BILLSTATUSFORQUERY_DEALED_BusiCurAcc)) {
				wherepart.append(" and vbillstatus = ").append(IFBMStatus.OUTPUT_SUCCESS).append(" and ( ");
				wherepart.append(" pk_corp='" + pk_centercorp + "' or ");
				wherepart.append(getCorpSQL(pk_centercorp));
				wherepart.append(" )");
			} else {
				wherepart.append(" and ");
				wherepart.append(" ((pk_corp='"
						+ pk_centercorp
						+ "' and vbillstatus="
						+ IBillStatus.CHECKPASS
						+ ")");
				wherepart.append(" or (");
				wherepart.append(getCorpSQL(pk_centercorp));
				wherepart.append(" and vbillstatus=" + IFBMStatus.SUBMIT + ")");
				wherepart.append(" or (vbillstatus = ").append(IFBMStatus.OUTPUT_SUCCESS).append(" and ( ");
				wherepart.append(" pk_corp='" + pk_centercorp + "' or ");
				wherepart.append(getCorpSQL(pk_centercorp));
				wherepart.append(" )))");
			}
		}

		HYPubBO bo = new HYPubBO();
		StorageVO[] vos = null;
		vos = (StorageVO[]) bo.queryByCondition(StorageVO.class, wherepart.toString());
		String billStatus = "";
		BusiCurAccVO[] curvos = null;
		if (vos != null && vos.length > 0) {
			ArrayList<BusiCurAccVO> curaccvoArray = new ArrayList<BusiCurAccVO>();
			for (int i = 0; i < vos.length; i++) {
				BusiCurAccVO tempvo = new BusiCurAccVO();
				tempvo.setBusinessno(vos[i].getBusinessno());
				tempvo.setSystem(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000191")/*
																													 * @res
																													 * "票据管理"
																													 */);
				tempvo.setBilltype(vos[i].getPk_billtypecode());
				tempvo.setBilltypename(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000193")/*
																															 * @res
																															 * "内部领用单"
																															 */);
				tempvo.setVbillno(vos[i].getVbillno());
				if (vos[i].getVbillstatus().equals(IFBMStatus.INPUT_SUCCESS) || vos[i].getVbillstatus().equals(IFBMStatus.OUTPUT_SUCCESS) ) {
					billStatus = BusiCurAccConst.BILLSTATUSFORQUERY_DEALED_BusiCurAcc;
				} else {
					billStatus = BusiCurAccConst.BILLSTATUSFORQUERY_DEALING_BusiCurAcc;
				}
				tempvo.setBillstatus(billStatus);
				tempvo.setMoney(vos[i].getSummoneyy());
				tempvo.setDealdate(vos[i].getDealdate());
				tempvo.setPk_sourcebill(vos[i].getPrimaryKey());
				tempvo.setPk_corp(queryvo.getPk_corpcenter());
				tempvo.setPk_currtype(vos[i].getPk_currtype());
				curaccvoArray.add(tempvo);
			}

			if (curaccvoArray != null && curaccvoArray.size() > 0) {
				curvos = new BusiCurAccVO[curaccvoArray.size()];
				curvos = curaccvoArray.toArray(new BusiCurAccVO[] {});
			}
		}
		return curvos;
	}

	/**
	 * 查询调剂数据
	 * 
	 * @param queryvo
	 * @return
	 * @throws BusinessException
	 */
	private BusiCurAccVO[] queryRelief(BusiCurAccQueryVO queryvo)
			throws BusinessException {
		String dealbegindate = queryvo.getDealbegindate();
		String dealenddate = queryvo.getDealenddate();
		String billstatus = queryvo.getBillstatus();
		String pk_centercorp = queryvo.getPk_corpcenter();
		String pk_currtype = queryvo.getPk_currtype();

		StringBuffer wherepart = new StringBuffer();
		wherepart.append(" isnull(dr,0) = 0 ");
		if (dealbegindate != null && !dealbegindate.equalsIgnoreCase("")) {
			wherepart.append(" and dealdate >= '").append(dealbegindate).append("' ");
		}

		if (!CommonUtil.isNull(pk_currtype)) {
			wherepart.append(" and pk_currtype ='").append(pk_currtype).append("' ");
		}

		if (dealenddate != null && !dealenddate.equalsIgnoreCase("")) {
			wherepart.append(" and dealdate <= '").append(dealenddate).append("' ");
		}

		if (billstatus != null && !billstatus.equalsIgnoreCase("")) {
			if (billstatus.equalsIgnoreCase(BusiCurAccConst.BILLSTATUSFORQUERY_DEALING_BusiCurAcc)) {
				wherepart.append(" and 1=0 ");// 处理中没有数据

			} else if (billstatus.equalsIgnoreCase(BusiCurAccConst.BILLSTATUSFORQUERY_DEALED_BusiCurAcc)) {
				wherepart.append(" and vbillstatus = ").append(IFBMStatus.CHECKPASS);
				wherepart.append(" and pk_corp='" + pk_centercorp + "'");
			} else {
				wherepart.append(" and pk_corp='"
						+ pk_centercorp
						+ "' and vbillstatus in("
						+ IBillStatus.CHECKPASS
						+ ","
						+ IFBMStatus.HAS_OUTPUT
						+ ")");
			}
		}

		HYPubBO bo = new HYPubBO();
		ReliefVO[] vos = null;
		vos = (ReliefVO[]) bo.queryByCondition(ReliefVO.class, wherepart.toString());

		BusiCurAccVO[] curvos = null;
		if (vos != null && vos.length > 0) {
			ArrayList<BusiCurAccVO> curaccvoArray = new ArrayList<BusiCurAccVO>();
			for (int i = 0; i < vos.length; i++) {
				BusiCurAccVO tempvo = new BusiCurAccVO();
				tempvo.setBusinessno(vos[i].getBusinessno());
				tempvo.setSystem(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000191")/*
																													 * @res
																													 * "票据管理"
																													 */);
				tempvo.setBilltype(vos[i].getPk_billtypecode());
				tempvo.setBilltypename(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000194")/*
																															 * @res
																															 * "票据调剂单"
																															 */);
				tempvo.setVbillno(vos[i].getVbillno());
				
				tempvo.setBillstatus(BusiCurAccConst.BILLSTATUSFORQUERY_DEALED_BusiCurAcc);
				tempvo.setMoney(vos[i].getSummoney());
				tempvo.setDealdate(vos[i].getDealdate());
				tempvo.setPk_sourcebill(vos[i].getPrimaryKey());
				tempvo.setPk_corp(queryvo.getPk_corpcenter());
				tempvo.setPk_currtype(vos[i].getPk_currtype());
				curaccvoArray.add(tempvo);
			}

			if (curaccvoArray != null && curaccvoArray.size() > 0) {
				curvos = new BusiCurAccVO[curaccvoArray.size()];
				curvos = curaccvoArray.toArray(new BusiCurAccVO[] {});
			}
		}
		return curvos;
	}

	/**
	 * 查询清算数据
	 * 
	 * @param queryvo
	 * @return
	 * @throws BusinessException
	 */
	private BusiCurAccVO[] queryReckon(BusiCurAccQueryVO queryvo)
			throws BusinessException {
		String dealbegindate = queryvo.getDealbegindate();
		String dealenddate = queryvo.getDealenddate();
		String billstatus = queryvo.getBillstatus();
		String pk_centercorp = queryvo.getPk_corpcenter();
		String pk_currtype = queryvo.getPk_currtype();

		StringBuffer wherepart = new StringBuffer();
		wherepart.append(" isnull(dr,0) = 0 ");
		if (dealbegindate != null && !dealbegindate.equalsIgnoreCase("")) {
			wherepart.append(" and dealdate >= '").append(dealbegindate).append("' ");
		}

		if (dealenddate != null && !dealenddate.equalsIgnoreCase("")) {
			wherepart.append(" and dealdate <= '").append(dealenddate).append("' ");
		}

		if (billstatus != null && !billstatus.equalsIgnoreCase("")) {

			// wherepart.append(" and pk_corp='" + pk_centercorp
			// + "' and vbillstatus=" + IBillStatus.CHECKPASS);

			if (billstatus.equalsIgnoreCase(BusiCurAccConst.BILLSTATUSFORQUERY_DEALING_BusiCurAcc)) {
				wherepart.append(" and 1 = 0 ");

			} else if (billstatus.equalsIgnoreCase(BusiCurAccConst.BILLSTATUSFORQUERY_DEALED_BusiCurAcc)) {
				wherepart.append(" and pk_corp= '"
						+ pk_centercorp
						+ "' and vbillstatus = ").append(IFBMStatus.CHECKPASS);
			} else {
				wherepart.append(" and pk_corp= '"
						+ pk_centercorp
						+ "' and vbillstatus = ").append(IFBMStatus.CHECKPASS);
			}
		}

		// 增加币种过滤
		if (!CommonUtil.isNull(pk_currtype)) {
			wherepart.append(" and pk_curr ='").append(pk_currtype).append("' ");
		}

		HYPubBO bo = new HYPubBO();
		ReckonVO[] vos = (ReckonVO[]) bo.queryByCondition(ReckonVO.class, wherepart.toString());
		BusiCurAccVO[] curvos = null;
		if (vos != null && vos.length > 0) {
			ArrayList<BusiCurAccVO> curaccvoArray = new ArrayList<BusiCurAccVO>();
			for (int i = 0; i < vos.length; i++) {
				BusiCurAccVO tempvo = new BusiCurAccVO();
				tempvo.setBusinessno(vos[i].getBusinessno());
				tempvo.setSystem(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000191")/*
																													 * @res
																													 * "票据管理"
																													 */);
				tempvo.setBilltype(vos[i].getPk_billtypecode());
				tempvo.setBilltypename(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201050", "UPT36201050-000043")/*
																															 * @res
																															 * "调剂清算单"
																															 */);
				tempvo.setVbillno(vos[i].getVbillno());
				tempvo.setBillstatus(BusiCurAccConst.BILLSTATUSFORQUERY_DEALED_BusiCurAcc);
				tempvo.setMoney(vos[i].getReckonmoneysum());
				tempvo.setDealdate(vos[i].getDealdate());
				tempvo.setPk_sourcebill(vos[i].getPrimaryKey());
				tempvo.setPk_corp(queryvo.getPk_corpcenter());
				tempvo.setPk_currtype(vos[i].getPk_curr());
				curaccvoArray.add(tempvo);
			}

			if (curaccvoArray != null && curaccvoArray.size() > 0) {
				curvos = new BusiCurAccVO[curaccvoArray.size()];
				curvos = curaccvoArray.toArray(new BusiCurAccVO[] {});
			}
		}
		return curvos;
	}

	private String getCorpSQL(String center_corp) {
		StringBuffer sbf = new StringBuffer();
		sbf.append(" pk_corp in (select pk_corp1  from bd_settleunit ");
		sbf.append(" inner join bd_settlecenter on bd_settleunit.pk_settlecent = bd_settlecenter.pk_settlecenter ");
		sbf.append(" where bd_settlecenter.pk_corp = '" + center_corp + "')");
		return sbf.toString();
	}
}