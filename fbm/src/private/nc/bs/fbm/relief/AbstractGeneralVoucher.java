package nc.bs.fbm.relief;

import nc.bs.dap.out.IAccountProcMsg;
import nc.bs.fbm.pub.UnVoucherCheckService;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.uap.bd.ISettleCenter;
import nc.vo.dap.inteface.IAccountPlat;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 *
 * �๦��˵���� ��֤��ȡ����֤ ���ڣ�2008-3-19
 *
 */
public abstract class AbstractGeneralVoucher implements IAccountProcMsg {

	/**
	 * ����VO
	 */
	public static final String CENTER = "CENTER";
	/**
	 * ��λVO
	 */
	public static final String UNIT = "UNIT";

	public AbstractGeneralVoucher() {
	}

	/**
	 * <p>
	 * ��֤ ���ݲ������ɳ�Ա��λ������ƾ֤ ���ɽ������ĵĽ���ƾ֤
	 * ȡ����֤������opType�ж�����֤����ȡ����֤��
	 * <p>
	 * ���ߣ�wangyg
	 * ���ڣ�2008-3-19
	 * @param billVO
	 * @param opType   �ж�����֤��ȡ����֤���ǳ�����֤ voucher����֤ cancelvoucher:ȡ����֤ clear:������֤
	 * @throws BusinessException
	 */

	public AggregatedValueObject voucher(AggregatedValueObject billVO,String opType) throws BusinessException{
		if(FbmBusConstant.OP_CANCELVOUCHER.equals(opType)){
			UnVoucherCheckService srv = new UnVoucherCheckService();
			if (null != billVO && billVO.getParentVO() != null && billVO.getParentVO().getPrimaryKey() !=null){
				srv.checkUnVoucher(billVO.getParentVO().getPrimaryKey());
			}
		}
		DapMsgVO dapvo = getDapVO(billVO,opType);

		handleVO(billVO);

		if(null==dapvo){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000158")/*@res "�޷����ɻ��ƽ̨��Ҫ��DapMsgVO"*/);
		}
		OuterProxy.getDapSendMessageService().sendMessage(dapvo, billVO);

		return billVO;
	}

	/**
	 * <p>
	 * 	����DapMsgVO
	 * <p>
	 * ���ߣ�wangyg
	 * ���ڣ�2008-5-6
	 * @param billVO
	 * @param opType
	 * @return
	 * @throws BusinessException
	 */
	public DapMsgVO getDapVO(AggregatedValueObject billVO,String opType)throws BusinessException {

		DapMsgVO unitVO =  null;
		String voucherOperate = ""; //����ƾ֤��ɾ��ƾ֤��ʶ��
		String companyType = ""; //���Ļ��ǵ�λ��ʶ��
		if (null != billVO && billVO.getParentVO() != null) {
			//�õ���ǰ��½��˾
			String corpPK = InvocationInfoProxy.getInstance().getCorpCode();
			SuperVO newVO = getSuperVO(billVO);
			String pk_billtypecode = (String)newVO.getAttributeValue("pk_billtypecode");

			//���������ĵ�λ��֤�ĵ�������
			boolean isExceptBillType = FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT.equals(pk_billtypecode) || FbmBusConstant.BILLTYPE_ENDORE.equals(pk_billtypecode) || FbmBusConstant.BILLTYPE_COLLECTION_UNIT.equals(pk_billtypecode);
			//�жϹ�˾�Ƿ�Ϊ����
			if (isCenter(corpPK) && !isExceptBillType) {
				companyType = CENTER;
				if(FbmBusConstant.OP_VOUCHER.equals(opType)) //�ж�����֤����ȡ����֤
				{
					voucherOperate = FbmBusConstant.OP_VOUCHER;
				}else if(FbmBusConstant.OP_CLEAR.equals(opType)){
					voucherOperate = FbmBusConstant.OP_VOUCHER;
				}
				else  //ȡ����֤
				{
					UFBoolean centervoucher = (UFBoolean)newVO.getAttributeValue("centervoucher");
					if(!centervoucher.booleanValue()){
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000159")/*@res "����δ��֤��������ȡ��������֤����ˢ�º����ԡ�"*/);
					}

					voucherOperate = FbmBusConstant.OP_CANCELVOUCHER;
				}
			} else {// ��λ����������֤����
				companyType = UNIT;
				if(FbmBusConstant.OP_VOUCHER.equals(opType))
				{
					//�ж��Ƿ�Ϊ����֤
					UFBoolean unitvoucher = (UFBoolean)newVO.getAttributeValue("unitvoucher");
					if(unitvoucher.booleanValue()){
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000160")/*@res "��λ����֤��������λ��֤����ˢ�º����ԡ�"*/);
					}

					voucherOperate = FbmBusConstant.OP_VOUCHER;
				}else if(FbmBusConstant.OP_CLEAR.equals(opType)){
					voucherOperate = FbmBusConstant.OP_VOUCHER;
				}
				else
				{
					UFBoolean unitvoucher = (UFBoolean)newVO.getAttributeValue("unitvoucher");
					if(!unitvoucher.booleanValue()){
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000161")/*@res "��λδ��֤��������λȡ����֤����ˢ�º����ԡ�"*/);
					}
					voucherOperate = FbmBusConstant.OP_CANCELVOUCHER;
				}
			}
			unitVO = getDapVO(billVO, voucherOperate, companyType);

			// ���Ϊɾ������ɾ��ǰУ���Ƿ����ɾ��
			if (!FbmBusConstant.OP_VOUCHER.equals(voucherOperate)) {
				valideVoucherBeforeDel(unitVO);
			}
		}
		return unitVO;
	}

	/**
	 * <p>
	 * 	����AggregatedValueObject
	 * <p>
	 * ���ߣ�wangyg
	 * ���ڣ�2008-5-6
	 * @param vo
	 * @throws BusinessException
	 */
	public void handleVO(AggregatedValueObject vo)
			throws BusinessException {

		if (vo.getChildrenVO() == null || vo.getChildrenVO().length == 0) {
			String[] vonames = getVONames();
			try {
				SuperVO tmpChild = (SuperVO)Class.forName(vonames[2]).newInstance();
				vo.setChildrenVO(new CircularlyAccessibleValueObject[]{tmpChild});
			} catch (Exception e) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000162")/*@res "�޷�ʵ�����ӱ���"*/);
			}
		}
	}


	/**
	 * <p>
	 * ��֤��ȡ����֤����
	 * <p>
	 * ���ߣ�wangyg
	 * ���ڣ�2008-3-19
	 * @param vo
	 * @param opType
	 * @param companyType         �ж��ǵ�λ�������ģ���λΪ��UNIT,����Ϊ��CENTER
	 * @throws BusinessException
	 */
	public void voucherAction(AggregatedValueObject vo, String opType,String companyType)
			throws BusinessException {

		DapMsgVO unitVO = getDapVO(vo, opType, companyType);
		// ���Ϊɾ������ɾ��ǰУ���Ƿ����ɾ��
		if (!FbmBusConstant.OP_VOUCHER.equals(opType)) {
			valideVoucherBeforeDel(unitVO);
		}
		if (vo.getChildrenVO() == null || vo.getChildrenVO().length == 0) {
			String[] vonames = getVONames();
			try {
				SuperVO tmpChild = (SuperVO)Class.forName(vonames[2]).newInstance();
				vo.setChildrenVO(new CircularlyAccessibleValueObject[]{tmpChild});
			} catch (Exception e) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000162")/*@res "�޷�ʵ�����ӱ���"*/);
			}

		}

		OuterProxy.getDapSendMessageService().sendMessage(unitVO, vo);
	}

	/**
	 * �ж�һ����˾�Ƿ�Ϊ��������
	 *
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	public boolean isCenter(String pk_corp) throws BusinessException {
		boolean isCenter = false;
		// ����
		if (((ISettleCenter) NCLocator.getInstance().lookup(
				ISettleCenter.class.getName())).isSettleCenter(pk_corp)) {
			isCenter = true;
		}
		return isCenter;
	}

	/**
	 * ɾ��ƾ֤ǰУ���Ƿ�����ɾ��
	 *
	 * @param msgVo
	 * @param dap
	 * @throws BusinessException
	 */
	public void valideVoucherBeforeDel(DapMsgVO msgVo)
			throws BusinessException {
		boolean isEdit = OuterProxy.getDapSendMessageService().isEditBillType(
				msgVo.getCorp(), msgVo.getSys(), msgVo.getProc(),
				msgVo.getBusiType(), msgVo.getProcMsg());
		if (!isEdit) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000163")/*@res "������ƾ֤����ҵ���ܽ��в���!"*/);
		}
	}

	/**
	 * ���ݵ�����PK������Ӧ�ĵ���������
	 *
	 * @param pk
	 * @return
	 * @throws BusinessException
	 */
	public SuperVO getSuperVO(AggregatedValueObject billVo)
			throws BusinessException {
		if (null == billVo) {
			return null;
		}
		String pk = (billVo.getParentVO()).getPrimaryKey();
		if (null == pk || "".equals(pk.trim())) {
			return null;
		}
		return (SuperVO) (new HYPubBO().queryByPrimaryKey(billVo.getParentVO().getClass(), pk));
	}

	/**
	 * ����SuperVO��������ƽ̨Ҫ��DAPMsgVO
	 *
	 * @return
	 */
	public DapMsgVO getDapVO(AggregatedValueObject billVo, String opType,
			String unitType) throws BusinessException {
		SuperVO superVO = (SuperVO) billVo.getParentVO();
		DapMsgVO msgVo = new DapMsgVO();
		msgVo.setBillCode((String)superVO.getAttributeValue("vbillno"));
		msgVo.setChecker((String)superVO.getAttributeValue("vapproveid"));
		msgVo.setSys("FBM");

		msgVo.setDestSystem(IAccountPlat.DESTSYS_GL);
		// ���ģ����ɽ���ƾ֤,��˾ȡ�Լ�����
		//if (CENTER.equals(unitType)) {
			msgVo.setCorp((String)superVO.getAttributeValue("pk_corp"));
		//}

		if (FbmBusConstant.OP_VOUCHER.equals(opType)) {
			msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000164")/*@res "Ʊ�ݵ�λ��֤"*/);
			msgVo.setMsgType(DapMsgVO.ADDMSG);// ����Ϊ������Ϣ
		} else {
			msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000165")/*@res "Ʊ�ݵ�λȡ����֤"*/);
			msgVo.setMsgType(DapMsgVO.DELMSG);// ����Ϊɾ����Ϣ
		}
		msgVo.setBillCode((String)superVO.getAttributeValue("vbillno"));// ���ݱ���

		msgVo.setProc((String)superVO.getAttributeValue("pk_billtypecode"));
		msgVo.setOperator((String)superVO.getAttributeValue("voperatorid"));

		msgVo.setProcMsg(superVO.getPrimaryKey() + StorageVO.Procmsg_flag
				+ msgVo.getCorp());

		msgVo.setBusiDate((UFDate)superVO.getAttributeValue("dapprovedate"));// ����ҵ������Ϊ�������
		msgVo.setMoney((UFDouble)superVO.getAttributeValue("moneyy"));

		//�˳��󷽷�Ҫ��ʵ����ʵ�֣����MsgDapVO��δ����Ĳ��֡�
		completeDapMsgVO(msgVo,superVO,unitType);

		return msgVo;
	}

	/**
	 * �˷���ֻ�л��ƽ̨���á�
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
		//��ʵ����ʵ�ִ�vonames����������
		String[] vonames = getVONames();
		AggregatedValueObject vo = bo.queryBillVOByPrimaryKey(vonames, pk);
		if (vo == null || vo.getParentVO() == null
				|| vo.getChildrenVO() == null || vo.getChildrenVO().length == 0) {
			return vo;
		}

		//��ѯЭ����ϢSQL
		String sql = handelTakenSqlValue((SuperVO)vo.getParentVO());

		//���µ�ǰvo�е���Ϣ����Э����Ϣ����vo��.
		setValues(vo,sql);

		return vo;
	}



	/**
	 * <p>
	 * 	ʵ����Ҫʵ�ִ˷���������Ҫʵ�֡�dapvo.setMoney(UFDouble money);���ܽ��
	 *  ͬʱ�ж�unitTypeΪ���ǻ���Ϊ���ģ����Ϊ��λ������Ҫʵ�� msgVo.setCorp(); ѡȡ������λ��Ӧ��˾
	 *  ��Ҫʵ��dapvo.setCurrency();���֡�
	 * <p>
	 * ���ߣ�wangyg
	 * ���ڣ�2008-3-19
	 * @param dapvo�������������ƽ̨Ҫ�õ���DapMsgVO
	 * @param supervo	   ��ǰ�ģ֣ϡ�
	 * @param unitType     ���ͣ�UNITΪ��λ��CENTERΪ����
	 */
	public abstract void completeDapMsgVO(DapMsgVO dapvo,SuperVO supervo,String unitType);

	/**
	 * <p>
	 * �õ����ӱ�֣ϡ�
	 * <p>
	 * ���ߣ�wangyg
	 * ���ڣ�2008-3-19
	 * @return
	 */
	public abstract String[] getVONames();


	/**
	 * <p>
	 *  ����SQL,���챳������������ա����ְ����е�Э����Ϣ��SQL��
	 *  �˷����ڻ��ƽ̨����ʱ���õ�����ǰ��VO��û��Э��ֵ��Ϣ��������Ҫ��
	 *  �²�ѯһ�£���Э����Ϣ��ѯ������
	 * <p>
	 * ���ߣ�wangyg
	 * ���ڣ�2008-4-1
	 * @return
	 */
	public abstract String handelTakenSqlValue(SuperVO supervo);

	/**
	 * <p>
	 * 	������SQL��ѯ�����SuperVO,�ŵ��ۺ�VO��parentVO�С�
	 *  �˷����ڻ��ƽ̨����ʱ���õ���ͨ����ѯЭ����SQL����ѯ������Э����Ϣ��SuperVO
	 *  ��SuperVO�ŵ�AggregatedValueObject�з��ظ����ƽ̨��
	 * <p>
	 * ���ߣ�wangyg
	 * ���ڣ�2008-4-1
	 * @param vo   ���ۺϣ֣�
	 * @param sql��������ۺ�VO��parentVO��SQL,ͨ��handelTakenSqlValue()�õ���
	 * @throws BusinessException
	 */
	public abstract void setValues(AggregatedValueObject vo, String sql) throws BusinessException ;

}