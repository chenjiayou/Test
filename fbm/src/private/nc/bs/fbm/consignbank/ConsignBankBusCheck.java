package nc.bs.fbm.consignbank;

import java.util.ArrayList;

import nc.bs.trade.combase.HYComBase;
import nc.bs.trade.comcheckunique.BillIsUnique;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;

//银行托收后台校验类
//bsrl
public class ConsignBankBusCheck extends HYComBase {
	public AggregatedValueObject beforeSaveBill(AggregatedValueObject vo) throws BusinessException {
		if (vo != null) {
			CircularlyAccessibleValueObject pvo = vo.getParentVO();
			if (pvo != null) {
				BillIsUnique check = new BillIsUnique();
				ArrayList<String[]> uniquekey = new ArrayList();
				uniquekey.add(new String[] { CollectionVO.PK_SOURCE});
				try {
					if (pvo.getAttributeValue(CollectionVO.PK_SOURCE) != null)
						check.checkBillIsUnique(vo, uniquekey);
				} catch (BusinessException e) {
					if(e.getMessage().length() != 0)
						throw new BusinessException(e.getMessage());
					else
					    throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201020","UPP36201020-000012")/* @res"此票据对应的托收单已经存在！"*/);
				}
			}
		}
    return vo;
    }

		/**
	     *
	     */
	    public ConsignBankBusCheck() {
	        super();
	    }


	    /* （非 Javadoc）
	     * @see nc.bs.trade.business.IBDBusiCheck#dealAfter(int, nc.vo.pub.AggregatedValueObject, java.lang.Object)
	     */
	    public void dealAfter(int intBdAction, AggregatedValueObject billVo,
	            Object userObj) throws Exception {

	    }


}