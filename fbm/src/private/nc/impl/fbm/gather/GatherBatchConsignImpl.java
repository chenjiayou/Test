package nc.impl.fbm.gather;

import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.trade.business.HYPubBO;
import nc.itf.fbm.discountcalculate.IInterestAccural;
import nc.itf.fbm.gather.IGatherBatchConsign;
import nc.itf.fbm.gather.IGatherBatchOP;
import nc.vo.cf.exception.InterestAccrualException;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

public class GatherBatchConsignImpl implements IGatherBatchConsign {

	@SuppressWarnings("unchecked")
	public String batchBankConsignOP(Vector vec, String currentdate,
			String operator) throws Exception {
		StringBuffer errorbillno = new StringBuffer();
		StringBuffer billnobuffer = new StringBuffer();
		HYPubBO pubbo = new HYPubBO();
		for (int i = 0; i < vec.size(); i++) {
			HYBillVO hybo = (HYBillVO) vec.get(i);
			CollectionVO collectionvo = (CollectionVO) hybo.getParentVO();
			String pk_baseinfo = collectionvo.getPk_baseinfo();
			BaseinfoVO baseinfo = (BaseinfoVO) pubbo.queryByPrimaryKey(BaseinfoVO.class, pk_baseinfo);
			UFDate enddate = baseinfo.getEnddate();
			UFDate dcollectiondate = collectionvo.getDcollectiondate();
			UFDate dconsigndate = collectionvo.getDconsigndate();
			// 批量托收时，款项收妥日期大于票据到期日期.
			if (enddate != null
					&& dcollectiondate != null
					&& dcollectiondate.before(enddate)) {
				billnobuffer.append("'" + collectionvo.getPk_source() + "',");
				continue;
			}
			// 托收办理单保存时应校验款项收妥日期>=委托日期,
			if (dcollectiondate != null
					&& dconsigndate != null
					&& dconsigndate.after(dcollectiondate)) {
				billnobuffer.append("'" + collectionvo.getPk_source() + "',");
				continue;
			}
			try {
				IGatherBatchOP gatherop = (IGatherBatchOP) NCLocator.getInstance().lookup(IGatherBatchOP.class.getName());
				gatherop.gatherOp_RequiresNew(hybo, currentdate, operator);
			} catch (Exception e) {
				billnobuffer.append("'" + collectionvo.getPk_source() + "',");
			}

		}
		errorbillno.append(queryBillNo(billnobuffer.toString()));
		return errorbillno.toString();
	}

	@SuppressWarnings("unchecked")
	public String batchDiscountOP(Vector vec, String currentdate,
			String operator) throws Exception {
		HYPubBO pubbo = new HYPubBO();
		StringBuffer errorbillno = new StringBuffer();
		StringBuffer billnobuffer = new StringBuffer();
		for (int i = 0; i < vec.size(); i++) {
			UFDate actiondate = null;
			HYBillVO hybo = (HYBillVO) vec.get(i);
			DiscountVO discountvo = (DiscountVO) hybo.getParentVO();
			UFDate discountdate = discountvo.getDdiscountdate();
			String pk_baseinfo = discountvo.getPk_baseinfo();
			ActionVO actionvo[] = (ActionVO[]) pubbo.queryByCondition(ActionVO.class, " pk_baseinfo='"
					+ pk_baseinfo
					+ "' order by serialnum  desc");
			BaseinfoVO baseinfo = (BaseinfoVO) pubbo.queryByPrimaryKey(BaseinfoVO.class, pk_baseinfo);
			if (actionvo != null && actionvo.length > 0) {
				actiondate = actionvo[0].getActiondate();
			}
			

			
			
			UFDate enddate = baseinfo.getEnddate();
			// 批贴时，应校验实际贴现日期<票据到期日期.
			if (enddate != null
					&& discountdate != null
					&& !enddate.after(discountdate)) {
				billnobuffer.append("'" + discountvo.getPk_source() + "',");
				continue;
			}
			if (actiondate != null
					&& discountdate != null
					&& discountdate.before(actiondate)) {
				billnobuffer.append("'" + discountvo.getPk_source() + "',");
				continue;
			}
			// 计算贴现利息。
			// 调用方法计算贴现利息.
			RegisterVO resvo = (RegisterVO) pubbo.queryByPrimaryKey(RegisterVO.class, discountvo.getPk_source());
			UFDouble interestValue = calculateDiscountInterest(resvo.getMoneyy(), discountvo.getDdiscountdate(), baseinfo.getEnddate(), baseinfo.getPk_curr(), discountvo.getDiscountdelaydaynum(), discountvo.getDiscountyrate(), discountvo.getRatedaynum(), discountvo.getPk_corp());
			discountvo.setDiscountinterest(interestValue);
			discountvo.setMoneyy(resvo.getMoneyy().sub(interestValue));
			hybo.setParentVO(discountvo);
			try {
				IGatherBatchOP gatherop = (IGatherBatchOP) NCLocator.getInstance().lookup(IGatherBatchOP.class.getName());
				gatherop.gatherDiscount_RequiresNew(hybo, currentdate, operator);
			} catch (Exception e) {
				billnobuffer.append("'" + discountvo.getPk_source() + "',");
			}
		}
		errorbillno.append(queryBillNo(billnobuffer.toString()));
		return errorbillno.toString();
	}

	@SuppressWarnings("unchecked")
	public String batchImpawnOP(Vector vec, String currentdate, String operator)
			throws Exception {
		StringBuffer errorbillno = new StringBuffer();
		StringBuffer billnobuffer = new StringBuffer();
		HYPubBO pubbo = new HYPubBO();
		for (int i = 0; i < vec.size(); i++) {
			UFDate actiondate = null;
			HYBillVO hybo = (HYBillVO) vec.get(i);
			ImpawnVO impawnvo = (ImpawnVO) hybo.getParentVO();
			UFDate impawndate = impawnvo.getImpawndate();
			String pk_baseinfo = impawnvo.getPk_baseinfo();
			BaseinfoVO baseinfo = (BaseinfoVO) pubbo.queryByPrimaryKey(BaseinfoVO.class, pk_baseinfo);
			ActionVO actionvo[] = (ActionVO[]) pubbo.queryByCondition(ActionVO.class, " pk_baseinfo='"
					+ pk_baseinfo
					+ "' order by serialnum  desc");
			if (actionvo != null && actionvo.length > 0) {
				actiondate = actionvo[0].getActiondate();
			}
			UFDate enddate = baseinfo.getEnddate();
			// 批质押时，应校验质押日期<＝票据到期日期.
			if (enddate != null
					&& impawndate != null
					&& impawndate.after(enddate)) {
				billnobuffer.append("'" + impawnvo.getPk_source() + "',");
				continue;
			}
			if (actiondate != null
					&& impawndate != null
					&& impawndate.before(actiondate)) {
				billnobuffer.append("'" + impawnvo.getPk_source() + "',");
				continue;
			}

			try {
				IGatherBatchOP gatherop = (IGatherBatchOP) NCLocator.getInstance().lookup(IGatherBatchOP.class.getName());
				gatherop.gatherImpawn_RequiresNew(hybo, currentdate, operator);
			} catch (Exception e) {
				billnobuffer.append("'" + impawnvo.getPk_source() + "',");
			}
		}
		errorbillno.append(queryBillNo(billnobuffer.toString()));
		return errorbillno.toString();
	}

	public String queryBillNo(String pk) throws Exception {
		HYPubBO pubbo = new HYPubBO();
		StringBuffer reMsg = new StringBuffer();
		if (pk != null && !"".equals(pk)) {
			pk = pk.substring(0, pk.length() - 1);
			RegisterVO regvo[];
			regvo = (RegisterVO[]) pubbo.queryByCondition(RegisterVO.class, " pk_register in("
					+ pk
					+ ")");
			for (int i = 0; i < regvo.length; i++) {
				reMsg.append(regvo[i].getVbillno() + "\n");
			}
		}
		return reMsg.toString();
	}
	
	/**
	 * 计算贴现利息
	 * 
	 * @param hpje
	 * @param txrq
	 * @param enddate
	 * @param currtype
	 * @param delayday
	 * @param ratenvalue
	 * @param ratedaynum
	 * @param pk_corp
	 * @return
	 */
	public UFDouble calculateDiscountInterest(UFDouble hpje, UFDate txrq,
			UFDate enddate, String currtype, int delayday, UFDouble ratenvalue,
			int ratedaynum, String pk_corp) throws BusinessException {
		RegisterVO[] vos = new RegisterVO[1];
		vos[0] = new RegisterVO();
		vos[0].setMoneyy(hpje);
		vos[0].setTxrq(txrq);
		vos[0].setEnddate(enddate);
		vos[0].setDiscountdelaydaynum(delayday);
		vos[0].setTxnlv(ratenvalue);
		vos[0].setLlts(ratedaynum);
		vos[0].setPk_curr(currtype);
		vos[0].setPk_corp(pk_corp);
		IInterestAccural IInterAccu = (IInterestAccural) NCLocator.getInstance().lookup(IInterestAccural.class.getName());
		RegisterVO[] ret = null;
		try {
			ret = IInterAccu.computetxjx(vos);
		} catch (InterestAccrualException e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return ret[0].getTxlx();
	}
}
