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
		dapvo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000130")/*@res "Ʊ��������֤"*/);
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
	 * ��֤ ���ݲ������ɳ�Ա��λ������ƾ֤ ���ɽ������ĵĽ���ƾ֤
	 * ȡ����֤������isVoucher�ж�����֤����ȡ����֤��
	 * �����޸ģ�����ǽ��㵥λ��¼�����֤��ť��ֻ���ɵ�λ������ƾ֤
	 * <p>
	 * ���ߣ�wangyg
	 * ���ڣ�2008-3-19
	 * @param billVO
	 * @param opType   �ж�����֤��ȡ����֤������ voucher����֤ cancelvoucher:ȡ����֤ clear:����
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
//			//�õ���ǰ��½��˾
//			String corpPK = InvocationInfoProxy.getInstance().getCorpCode();
//
//			SuperVO newVO = getSuperVO(billVO);
//
//			//�жϹ�˾�Ƿ�Ϊ����
//			if (isCenter(corpPK)) {
//				if(FbmBusConstant.OP_VOUCHER.equals(opType)) //�ж�����֤����ȡ����֤
//				{
//					//�ж�Ʊ��״̬�Ƿ�Ϊ�Ѱ���
//					if (IFBMStatus.Transact != ((Integer)newVO.getAttributeValue("vbillstatus")).intValue()) {
//						throw new BusinessException("Ʊ��״̬��Ϊ�Ѱ�����������֤������ˢ�º����ԡ�");
//					}
//					voucherAction(billVO, FbmBusConstant.OP_VOUCHER,CENTER);// �������Ľ���ƾ֤
//				}else  //ȡ����֤
//				{
//					if (IFBMStatus.HAS_VOUCHER_VAR != ((Integer)newVO.getAttributeValue("vbillstatus")).intValue()) {// ��������֤
//						throw new BusinessException("Ŀǰ����״̬��Ϊ����֤��������ȡ����֤����ˢ�º����ԡ�");
//					}
//					voucherAction(billVO, FbmBusConstant.OP_CANCELVOUCHER,CENTER);// ɾ�����Ľ���ƾ֤
//				}
//			} else {// ��λ����������֤����
//				if(FbmBusConstant.OP_VOUCHER.equals(opType))
//				{
//					//�ж��Ƿ�Ϊ��������֤
//					if (IFBMStatus.HAS_VOUCHER_VAR != ((Integer)newVO.getAttributeValue("vbillstatus")).intValue()) {
//						throw new BusinessException("Ʊ�ݲ�Ϊ����֤����������֤������ˢ�º����ԡ�");
//					}
//					voucherAction(billVO, FbmBusConstant.OP_VOUCHER,UNIT);// ���ɵ�λ����ƾ֤
//				}else
//				{
//					if (IFBMStatus.HAS_UNIT_VOUCHER != ((Integer)newVO.getAttributeValue("vbillstatus")).intValue())
//					{// �ѵ�λ��֤
//						throw new BusinessException("Ŀǰ����״̬��Ϊ�ѵ�λ��֤��������ȡ����֤����ˢ�º����ԡ�");
//					}
//					voucherAction(billVO, FbmBusConstant.OP_CANCELVOUCHER,UNIT);// ɾ����λ����ƾ֤
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