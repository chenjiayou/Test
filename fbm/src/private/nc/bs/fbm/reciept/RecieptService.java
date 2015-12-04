package nc.bs.fbm.reciept;

import nc.bs.dao.BaseDAO;
import nc.bs.dap.out.IAccountProcMsg;
import nc.bs.fbm.pub.ResetRefValues;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.itf.dap.pub.IDapSendMessage;
import nc.vo.dap.inteface.IAccountPlat;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

/**
 *
 * �๦��˵���� ��������ҵ���� ���ڣ�2007-11-1 ����Ա�� wues
 *
 */
public class RecieptService implements IAccountProcMsg {

	public RecieptService() {
		super();
	}

	/**
	 * ��֤ opType = 0 ȡ����֤ opType = 1
	 * ��֤ʱ�������㵥PK�������㵥״̬Ϊ����֤��ͬʱ�������㵥PK��def1�и�����Ӧ����ص�״̬Ϊ����֤״̬
	 * ȡ�����㵥��֤ʱ���㵥������ص�״̬Ӧ��Ϊ��������֤ ȡ������ص���֤ʱ���㵥Ϊ��������֤������ص�Ϊ�ѵ�λ��֤
	 *
	 * @throws BusinessException
	 */
	public AggregatedValueObject operateVoucher(AggregatedValueObject billVO,
			String corpPK, String opType) throws BusinessException {

		if (null != billVO && billVO.getParentVO() != null) {
			ReckonVO reckonVO = (ReckonVO) billVO.getParentVO();
			ReckonVO newVO = null;

//			// 36GT���ص�:�ѵ�λ��֤��36GK�����㣺��������֤
			if (FbmBusConstant.OP_VOUCHER.equals(opType)) {// ������֤����
				if (FbmBusConstant.BILLTYPE_LIQUIDATE.equals(reckonVO
						.getPk_billtypecode())) {// ���㵥
					reckonVO.setCentervoucher(new UFBoolean(true));
				} else {// ����ص�
					reckonVO.setUnitvoucher(new UFBoolean(true));
				}
			}
			if (FbmBusConstant.OP_CANCELVOUCHER.equals(opType)) {// ȡ����֤
				if (FbmBusConstant.BILLTYPE_LIQUIDATE.equals(reckonVO
						.getPk_billtypecode())) {// ���㵥
					reckonVO.setCentervoucher(new UFBoolean(false));
				} else {// ����ص�
					reckonVO.setUnitvoucher(new UFBoolean(false));
				}
			}
			
			newVO = updateVoucher4Reckon(reckonVO);
			if (newVO != null)
				billVO.setParentVO(newVO);

			sendDAPMessage(billVO, corpPK, opType);
		}
		return billVO;
	}

	private ReckonVO updateVoucher4Reckon(ReckonVO vo) throws BusinessException{
		BaseDAO dao   = new BaseDAO();
		dao.updateVO(vo);
		return (ReckonVO)dao.retrieveByPK(ReckonVO.class, vo.getPrimaryKey());
	}

	/**
	 * ������ƽ̨����
	 *
	 * @param billVO
	 * @param corpPK
	 * @param opType
	 * @throws BusinessException
	 */
	private void sendDAPMessage(AggregatedValueObject billVO, String corpPK,
			String opType) throws BusinessException {
		// ���촫��ƽ̨VO
		DapMsgVO dapVO = getDapMsgVo(billVO, opType);
		IDapSendMessage dap = OuterProxy.getDapSendMessageService();

		if (FbmBusConstant.OP_CANCELVOUCHER.equals(opType)) {// ȡ����֤
//			dapVO.setMsgType(DapMsgVO.DELMSG);// ȡ����֤����Ϊɾ����Ϣ��Ĭ��Ϊ������Ϣ
			checkIsEditForDelVoucher(dapVO, dap);
		}

		// ����ƾ֤
		dap.sendMessage(dapVO, billVO);
	}

	/**
	 * ɾ��ǰУ���Ƿ�����ɾ��
	 *
	 * @param dapVO
	 * @param dap
	 * @throws BusinessException
	 */
	private void checkIsEditForDelVoucher(DapMsgVO dapVO, IDapSendMessage dap)
			throws BusinessException {
		boolean isEdit = dap.isEditBillType(dapVO.getCorp(), dapVO.getSys(),
				dapVO.getProc(), dapVO.getBusiType(), dapVO.getProcMsg());
		if (!isEdit) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201055","UPP36201055-000006")/* @res"������ƾ֤����ҵ���ܽ��в���!"*/);
		}
	}

	/**
	 * ��װ��DapMsgVO����
	 *
	 * @param billVO
	 * @param opType
	 * @return
	 */
	public DapMsgVO getDapMsgVo(AggregatedValueObject billVO, String opType) {
		ReckonVO reckonVO = (ReckonVO) billVO.getParentVO();
		DapMsgVO vo = new DapMsgVO();
		vo.setBillCode(reckonVO.getVbillno());
//		if (reckonVO.getPk_billtypecode().equalsIgnoreCase(
//				FbmBusConstant.BILLTYPE_LIQUIDATE)) {
//			vo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201055","UPP36201055-000007")/* @res"�������㵥"*/);
//			vo.setDestSystem(IAccountPlat.DESTSYS_SETTLE);// ����ƾ֤
//		} else {
		vo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
				"36201005", "UPP36201005-000019")/* @res"��������ص�/�������㵥" */);
		vo.setDestSystem(IAccountPlat.DESTSYS_GL);// ����ƾ֤
//		}
		vo.setCorp(reckonVO.getPk_corp());
		vo.setMoney(reckonVO.getReckonmoneysum());
		vo.setChecker(reckonVO.getVapproveid());
		vo.setSys("FBM");
		vo.setProc(reckonVO.getPk_billtypecode());// ��������
		vo.setOperator(reckonVO.getVoperatorid());
		vo.setProcMsg(reckonVO.getPrimaryKey() + StorageVO.Procmsg_flag
				+ reckonVO.getPk_corp());

		vo.setBusiDate(reckonVO.getDreckondate());// ҵ������
		vo.setCurrency(reckonVO.getPk_curr());
		if (FbmBusConstant.OP_CANCELVOUCHER.equals(opType)) {// ȡ����֤
			vo.setMsgType(DapMsgVO.DELMSG);// ȡ����֤����Ϊɾ����Ϣ��Ĭ��Ϊ������Ϣ
		}
		
		ReckonBVO[] bvos = (ReckonBVO[]) billVO.getChildrenVO();
		CurrencyPublicUtil currencyPublicUtil = new CurrencyPublicUtil(reckonVO.getPk_corp());
		currencyPublicUtil.setPk_currtype_y(reckonVO.getPk_curr());
		
		String pk_billtypecode = reckonVO.getPk_billtypecode();
		UFDate voucherDate = null;
		if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_LIQUIDATE)){
			voucherDate = reckonVO.getVoucherdate();
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RECKON_RECIEPT)){
			voucherDate = reckonVO.getUnitvoucherdate();
		}
		try {
			UFDouble[] fbrate = currencyPublicUtil.getExchangeRate(String.valueOf(voucherDate));
			for (int i = 0; i < bvos.length; i++) {
				UFDouble[] yfbmoney = currencyPublicUtil.getYfbMoney(bvos[i].getMoneyy(), String.valueOf(voucherDate));
				bvos[i].setMoneyb(yfbmoney[2]);
				bvos[i].setBrate(fbrate[1]);
			}
		} catch (BusinessException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		return vo;
	}

	/**
	 * ����-����pk���,��ֳ�Pk�͵�λ
	 */
	public AggregatedValueObject queryDataByProcId(String billTypeOrProc,
			String procMsg) throws BusinessException {
		if (CommonUtil.isNull(billTypeOrProc) || CommonUtil.isNull(procMsg)) {
			return null;
		}
		String[] splits = procMsg.split(StorageVO.Procmsg_flag);
		if (splits == null || splits.length == 0) {
			return null;
		}
		String pk = splits[0];
		HYPubBO bo = new HYPubBO();
		String[] vonames = new String[] { HYBillVO.class.getName(),
				ReckonVO.class.getName(), ReckonBVO.class.getName() };
		AggregatedValueObject vo = bo.queryBillVOByPrimaryKey(vonames, pk);

		if(vo==null||vo.getParentVO()==null||vo.getChildrenVO()==null||vo.getChildrenVO().length==0){
			return vo;
		}

		ResetRefValues.setReckonBodyRefValues(vo);

		/*
		StringBuffer sql = new StringBuffer();

		sql.append(" select fbm_reckon_b.direction,fbm_reckon_b.pk_reckon, fbm_reckon_b.pk_reckon_b,fbm_reckon_b.pk_baseinfo, fbm_reckon_b.pk_source, ");
		sql.append(" fbm_reckon_b.pk_detail, fbm_reckon_b.moneyy, fbm_register.moneyy fbmbillmoney, fbm_baseinfo.enddate, ");
		sql.append(" fbm_register.holdunit, fbm_baseinfo.payunit, fbm_register.frate,fbm_register.brate,fbm_register.moneyf,fbm_register.moneyb, fbm_baseinfo.fbmbillno fbmbillno, ");
		sql.append(" fbm_baseinfo.pk_curr,fbm_baseinfo.fbmbilltype ");
		sql.append(" from fbm_reckon_b left join fbm_register ");
		sql.append(" on(fbm_reckon_b.pk_source=fbm_register.pk_register) join fbm_baseinfo on (fbm_register.pk_baseinfo=fbm_baseinfo.pk_baseinfo) where ");

		ReckonBVO[] reckonBVO = (ReckonBVO[]) vo.getChildrenVO();
		for (int i = 0; i < reckonBVO.length; i++) {
			sql.append(" pk_reckon_b ='"+reckonBVO[i].getPk_reckon_b()+"' ");
			if(i<reckonBVO.length-1){
				sql.append(" or ");
			}
		}

		FBMPubQueryDAO dao = new FBMPubQueryDAO();
		SuperVO[] queryVos = dao.queryData(sql.toString(), ReckonBVO.class);
		vo.setChildrenVO(queryVos);
		*/
		return vo;
	}
}