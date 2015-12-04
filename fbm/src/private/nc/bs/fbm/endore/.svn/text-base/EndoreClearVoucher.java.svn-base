package nc.bs.fbm.endore;

import nc.bs.fbm.gather.FBMPubQueryDAO;
import nc.bs.fbm.relief.AbstractGeneralVoucher;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.endore.EndoreBVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.HYBillVO;

public class EndoreClearVoucher extends AbstractGeneralVoucher {
	@Override
	public String[] getVONames() {
		// TODO Auto-generated method stub
		return new String[] { HYBillVO.class.getName(),
				EndoreVO.class.getName(), EndoreBVO.class.getName() };
	}

	@Override
	public void completeDapMsgVO(DapMsgVO dapvo, SuperVO supervo,
			String unitType) {
		dapvo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000139")/*@res "背书办理冲销生成凭证"*/);
		dapvo.setCurrency((String) supervo.getAttributeValue("pk_curr"));
		dapvo.setProc(FbmBusConstant.BILLTYPE_ENDORECLEAE);
		dapvo.setProcMsg(supervo.getPrimaryKey() + StorageVO.Procmsg_flag
				+ dapvo.getCorp() + StorageVO.Procmsg_flag
				+ FbmBusConstant.BILLTYPE_ENDORECLEAE);
	}

	@Override
	public String handelTakenSqlValue(SuperVO supervo) {
		EndoreVO endorevo = (EndoreVO)supervo;
		StringBuffer sql = new StringBuffer();
		sql.append(" select fbm_endore.pk_endore,fbm_endore.pk_source,fbm_endore.pk_baseinfo,");
		sql.append(" fbm_baseinfo.fbmbillno fbmbillno,fbm_baseinfo.payunit,");
		sql.append(" fbm_baseinfo.receiveunit,fbm_baseinfo.pk_curr,");
		sql.append(" fbm_baseinfo.moneyy,fbm_baseinfo.invoicedate,fbm_register.gatherdate,");
		sql.append(" fbm_baseinfo.enddate,fbm_baseinfo.invoiceunit,");
		sql.append(" fbm_register.keepunit,fbm_register.paybillunit,fbm_register.moneyb,fbm_register.brate, ");
		sql.append(" fbm_register.moneyf,fbm_register.frate,fbm_baseinfo.fbmbilltype ");
		sql.append(" from fbm_endore left join fbm_register ");
		sql.append(" on (fbm_endore.pk_source=fbm_register.pk_register) join fbm_baseinfo ");
		sql.append(" on (fbm_register.pk_baseinfo=fbm_baseinfo.pk_baseinfo) where ");

		sql.append(" pk_endore ='" + endorevo.getPrimaryKey() + "' ");

		return sql.toString();
	}

	@Override
	public void setValues(AggregatedValueObject vo, String sql)
			throws BusinessException {

			FBMPubQueryDAO dao = new FBMPubQueryDAO();
			SuperVO queryVos = dao.querySingleData(sql, EndoreVO.class);
			vo.setParentVO(queryVos);
		}


}