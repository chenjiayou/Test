package nc.bs.fbm.discount;

import nc.bs.fbm.gather.FBMPubQueryDAO;
import nc.bs.fbm.relief.AbstractGeneralVoucher;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;

public class DiscountVoucher extends AbstractGeneralVoucher {

	@Override
	public void completeDapMsgVO(DapMsgVO dapvo, SuperVO supervo,
			String unitType) {
		dapvo.setCurrency((String)supervo.getAttributeValue("ybbz"));
		dapvo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000239")/*@res "票据贴现制证"*/);
		dapvo.setProcMsg(supervo.getPrimaryKey());
	}

	@Override
	public String[] getVONames() {
		// TODO Auto-generated method stub
		return new String[]{
				nc.vo.trade.pub.HYBillVO.class.getName(),
				nc.vo.fbm.discount.DiscountVO.class.getName(),
				nc.vo.fbm.discount.DiscountBVO.class.getName()
				};
		}

	@Override
	public String handelTakenSqlValue(SuperVO supervo) {

		DiscountVO disvo = (DiscountVO)supervo;
		StringBuffer sql = new StringBuffer();
		sql.append(" select fbm_discount.pk_discount,fbm_discount.pk_source,fbm_discount.pk_baseinfo,fbm_discount.moneyy,fbm_discount.discountinterest,fbm_discount.discountcharge,");
		sql.append(" fbm_baseinfo.fbmbillno fbmbillno,fbm_baseinfo.payunit,");
		sql.append(" fbm_baseinfo.receiveunit,fbm_baseinfo.pk_curr,");
		sql.append(" fbm_baseinfo.invoicedate,fbm_register.gatherdate,");
		sql.append(" fbm_baseinfo.enddate,fbm_baseinfo.invoiceunit,");
		sql.append(" fbm_register.keepunit,fbm_register.paybillunit,fbm_register.moneyb,fbm_register.brate, ");
		sql.append(" fbm_register.moneyf,fbm_register.frate,fbm_baseinfo.fbmbilltype ");
		sql.append(" from fbm_discount left join fbm_register ");
		sql.append(" on (fbm_discount.pk_source=fbm_register.pk_register) join fbm_baseinfo ");
		sql.append(" on (fbm_register.pk_baseinfo=fbm_baseinfo.pk_baseinfo) where ");

		sql.append(" pk_discount ='" + disvo.getPrimaryKey() + "' ");

		return sql.toString();
	}

	@Override
	public void setValues(AggregatedValueObject vo, String sql)
			throws BusinessException {

		FBMPubQueryDAO dao = new FBMPubQueryDAO();
		SuperVO queryVos = dao.querySingleData(sql, DiscountVO.class);
		vo.setParentVO(queryVos);

	}



}