package nc.bs.fbm.discount;

import java.util.ArrayList;
import java.util.Collection;

import nc.bs.dao.BaseDAO;
import nc.bs.trade.combase.HYComBase;
import nc.bs.trade.comcheckunique.BillIsUnique;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;

//贴现申请和办理后台校验类
//bsrl
public class DiscountBusCheck extends HYComBase  {

	public AggregatedValueObject beforeSaveBill(AggregatedValueObject vo) throws BusinessException {
		if (vo != null) {
			DiscountVO pvo = (DiscountVO)vo.getParentVO();
			if (pvo != null) {
				BillIsUnique check = new BillIsUnique();
				ArrayList<String[]> uniquekey = new ArrayList();
				uniquekey.add(new String[] { DiscountVO.PK_SOURCE, DiscountVO.PK_BILLTYPECODE });
				try {
					if (pvo.getAttributeValue(DiscountVO.PK_SOURCE) != null && pvo.getAttributeValue(DiscountVO.PK_BILLTYPECODE) != null)
						check.checkBillIsUnique(vo, uniquekey);
				} catch (BusinessException e) {
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201030","UPP36201030-000007")/* @res"此票据已经存在，不允许重复办理"*/);
				}

				if(pvo.getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {
					if(pvo.getPk_discount_app() != null) {
						BaseDAO basedao = new BaseDAO();
						Collection<DiscountVO> ret = basedao.retrieveByClause(DiscountVO.class, " pk_discount = '"+ pvo.getPk_discount_app()+
								"' and isnull(dr, 0) = 0 and vbillstatus = 1 ");
						if(ret.size() <= 0) {
							throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201030","UPP36201030-000008")/* @res"被引用的贴现申请单不是审批通过状态，请检查！"*/);
						}
					}
				}
			}
		}
		return vo;
    }

	/**
     *
     */
    public DiscountBusCheck() {
        super();
    }


    /* （非 Javadoc）
     * @see nc.bs.trade.business.IBDBusiCheck#dealAfter(int, nc.vo.pub.AggregatedValueObject, java.lang.Object)
     */
    public void dealAfter(int intBdAction, AggregatedValueObject billVo,
            Object userObj) throws Exception {

    }
 }