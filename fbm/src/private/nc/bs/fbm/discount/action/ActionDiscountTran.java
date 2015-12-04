package nc.bs.fbm.discount.action;

import nc.bs.fbm.pub.action.AbstractBusiAction;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;

public abstract class ActionDiscountTran<K extends DiscountVO,T extends DiscountVO > extends AbstractBusiAction<K, T> {
	
//	protected String checkOuter(ActionParamVO param) throws BusinessException{
//		OuterVO[] outerVO = queryOuterVO(param);
//		
//		//�Ϸ�����������ø�pk_register���ⲿ�������ҽ���һ����ɵ���Ʊ�տ
//		if(outerVO != null && outerVO[0] != null ){
//			if(outerVO.length == 1){
//				String pk_billtypecode = outerVO[0].getPk_billtypecode();
//				String outerStatus = outerVO[0].getOuterstatus();
//				if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_GATHER) && outerStatus.equals(FbmBusConstant.OUTERBILL_OVER)){
//					return null;
//				}else{
//					return "�ո���δ������Ʊ�տ����Ʊ�տ�δ���";
//				}
//				
//			}else{
//				return "�ո������������Ʊ�����ݴ���";
//			}
//		}else{
//			return "û����Ʊ�տ��¼���޷�ִ�в���";
//		}
//	}
	
	/**
	 * 
	 * ���ְ���ת��Ϊ��������VO xwq 2007-8-29
	 * 
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	@Override
	public BusiActionParamVO<T>[] buildParam(K data, String actioncode) throws BusinessException{
		BusiActionParamVO<T>[] params = new BusiActionParamVO[1];
		params[0] = new BusiActionParamVO<T>();
		fillBusiField(data, params[0], actioncode);
		params[0].setActioncode(actioncode);
		
		params[0].setActiondate(data.getDdiscountdate());
		
		// �������⣺���ְ������ʱУ����һҵ�����ڴ��� NCdp200983987
		// if (data.getDtransactdate()!=null) {
		// params[0].setActiondate(data.getDtransactdate());//��������
		// params[0].setActionperson(data.getVtransactorid());// ������
		// } else {
		//			
		// params[0].setActiondate(data.doperatedate);//��������
		//			
		// }
		
		params[0].setPk_baseinfo(data.getPk_baseinfo());
		params[0].setPk_bill(data.getPrimaryKey());
		params[0].setPk_source(data.getPk_source());
		params[0].setSourcefield(DiscountVO.PK_SOURCE);
		params[0].setBilltype(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT);
		params[0].setPk_corp(data.getPk_corp());
		params[0].setSuperVO((T)data);
		return params;
	}
}
