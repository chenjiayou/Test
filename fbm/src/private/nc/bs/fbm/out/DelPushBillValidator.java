package nc.bs.fbm.out;

import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * ɾ����ʽ���ɵĵ��ݣ����֡����ա��ж�
 * V55 Ʊ�ݲ������ո�������
 * @author wues
 *
 */
public class DelPushBillValidator extends AbstractBillValidator {
	public void doCheck(ArapBillParamVO param)throws BusinessException {
		if(billnoSet.contains(param.getFbmbillno())){
			return;
		}
	}

	@Override
	public void checkBaseinfo(ArapBillParamVO param)throws BusinessException {
		//�շ���������ɾ��
	}


	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		//�����ⲿ����
		outRelDao.deleteRelation(param.getPk_bill_b());

		// ����Ʊ��ҵ�񵥾�״̬
		String billtype = param.getPk_billtypecode();
		String pk_busibill = param.getPk_busbill();

		SuperVO superVO = null;
		if(billtype != null){
			if (billtype.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {
				superVO = (SuperVO) baseDao.retrieveByPK(CollectionVO.class,
						pk_busibill);
				superVO.setAttributeValue("vbillstatus", IFBMStatus.Transact);
			} else {
				if (billtype.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {
					superVO = (SuperVO) baseDao.retrieveByPK(DiscountVO.class,
							pk_busibill);
				} else if (billtype.equals(FbmBusConstant.BILLTYPE_INVOICE)
						|| billtype.equals(FbmBusConstant.BILLTYPE_ENDORE)) {
					superVO = (SuperVO) baseDao.retrieveByPK(RegisterVO.class,
							pk_busibill);
				} else if (billtype.equals(FbmBusConstant.BILLTYPE_BILLPAY)) {
					superVO = (SuperVO) baseDao.retrieveByPK(AcceptVO.class,
							pk_busibill);
				}
				if(superVO != null){
					superVO.setAttributeValue("vbillstatus", IBillStatus.CHECKPASS);
				}
			}
			if(superVO != null){
				baseDao.updateVO(superVO);
			}
		}

		super.afterAction(param);
	}

}
