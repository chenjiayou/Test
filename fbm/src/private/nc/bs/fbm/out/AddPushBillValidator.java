package nc.bs.fbm.out;

import nc.bs.fbm.pub.OuterRelationDAO;
import nc.impl.fbm.cmp.CMPConstant;
import nc.itf.cdm.util.CommonUtil;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;

/**
 *
 * @author wues
 *
 */
public class AddPushBillValidator extends AbstractBillValidator {

	public void checkBaseinfo(ArapBillParamVO param)throws BusinessException {
		String djdl = param.getOuterdjdl();
		if(!CMPConstant.BILLTYPE_HJ.equals(djdl)){
			if(param.getFbmbillno() == null ){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000184")/* @res"��"*/ + param.getRow() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000185")/* @res"��Ʊ�ݱ�Ų���Ϊ��"*/);
			}
			if(param.getBaseinfoVO() == null){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000184")/* @res"��"*/ + param.getRow() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000186")/* @res"��Ʊ�ݱ�Ų�����"*/);
			}

			if(!param.getPk_curr().equals(param.getBaseinfoVO().getPk_curr())){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000184")/* @res"��"*/ + param.getRow() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000187")/* @res"��Ʊ�ݱ��ֲ�һ��"*/);
			}
		}else{
			param.setPk_billtypecode(FbmBusConstant.BILLTYPE_INVOICE);
		}

		String pk_billtypecode = param.getPk_billtypecode();

		String pk_busibill = param.getPk_busbill();

		RegisterVO regVO = null;
		if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {// ����
			CollectionVO collectionVO = (CollectionVO) baseDao.retrieveByPK(
					CollectionVO.class, pk_busibill);
			regVO = (RegisterVO)baseDao.retrieveByPK(RegisterVO.class, collectionVO.getPk_source());
			// �����
			if (collectionVO.getMoneyy().doubleValue() != param.getMoneyy()
					.doubleValue()) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000188")/* @res"���յ���������ո������ݽ�һ��"*/);
			}
			// ���ͻ�
			String pk_cubasdoc = regVO.getPaybillunit();

			//���̾�Ϊ��Ҳ�Ϸ�
			boolean isNullCust = (pk_cubasdoc == null && param.getOtherunit() == null);
//			if(pk_cubasdoc == null){
//				throw new BusinessException("��Ʊ�Ǽǵ��ĸ�Ʊ��λΪ�գ��޷����ɵ���");
//			}
//
			boolean oneCustNull = (pk_cubasdoc == null && param.getOtherunit() != null) || (pk_cubasdoc != null && param.getOtherunit() == null);
			if (oneCustNull||(!isNullCust && !pk_cubasdoc.equals(param.getOtherunit()))) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000145")/*@res "Ʊ�ݵĸ�Ʊ��λ���ո����Ŀͻ���һ��"*/);
			}

			// ����տ������ʺ�
			if ((collectionVO.getHolderacc() ==null &&param.getSkbankacc()!=null)|| !collectionVO.getHolderacc().equals(param.getSkbankacc())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000189")/* @res"�������յ��ϵĳ�Ʊ�����˺����ո��������ϵ��տ������˺Ų�һ��"*/);
			}
			// ��鵥�����ڣ�������ڿ�����������
			if (collectionVO.getDcollectiondate().after(param.getDjrq())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000190")/* @res"�������յ��ϵĿ����������ڱ���С�ڵ����ո�����������"*/);
			}

		} else if (pk_billtypecode
				.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {// ����

			DiscountVO discountVO = (DiscountVO) baseDao.retrieveByPK(
					DiscountVO.class, pk_busibill);
			regVO = (RegisterVO)baseDao.retrieveByPK(RegisterVO.class, discountVO.getPk_source());
			// ����������
			if (discountVO.getMoneyy().doubleValue() != param.getMoneyy()
					.doubleValue()) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000191")/* @res"����������ո������ݽ�һ��"*/);
			}
			// ���������Ϣ
			UFDouble txlx_ybje = discountVO.getDiscountinterest();
			if (txlx_ybje == null) {
				txlx_ybje = new UFDouble(0);
			}

			if (txlx_ybje.doubleValue() != param.getTxlx_ybje().doubleValue()) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000192")/* @res"������Ϣ���ո��������ϵ�������Ϣ��һ��"*/);
			}
			// �������������
			UFDouble txfy_ybje = discountVO.getDiscountcharge();
			if (txfy_ybje == null) {
				txfy_ybje = new UFDouble(0);
			}
			if (txfy_ybje.doubleValue() != param.getTxfy_ybje().doubleValue()) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000193")/* @res"�������������ո��������ϵ����ַ����ֶβ�һ��"*/);
			}

			// ���ͻ�
			String pk_cubasdoc = regVO.getPaybillunit();
			//���̾�Ϊ��Ҳ�Ϸ�
			boolean isNullCust = (pk_cubasdoc == null && param.getOtherunit() == null);

			boolean oneCustNull = (pk_cubasdoc == null && param.getOtherunit() != null) || (pk_cubasdoc != null && param.getOtherunit() == null);
//			if(pk_cubasdoc == null){
//				throw new BusinessException("��Ʊ�Ǽǵ��ĸ�Ʊ��λΪ�գ��޷����ɵ���");
//			}
//
			if (oneCustNull||(!isNullCust && !pk_cubasdoc.equals(param.getOtherunit()))) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000145")/*@res "Ʊ�ݵĸ�Ʊ��λ���ո����Ŀͻ���һ��"*/);
			}
//			if(pk_cubasdoc == null){
//				throw new BusinessException("��Ʊ�Ǽǵ��ĸ�Ʊ��λΪ�գ��޷����ɵ���");
//			}
//
//			if (!pk_cubasdoc.equals(param.getOtherunit())) {
//				throw new BusinessException("Ʊ�ݵĸ�Ʊ��λ���ո����Ŀͻ���һ��");
//			}

			if(discountVO.getDiscount_account() == null){
				if(param.getSkbankacc() != null){
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000194")/* @res"���������˺�Ϊ��"*/);
				}
			}else if (!discountVO.getDiscount_account().equals(param.getSkbankacc())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000195")/* @res"���ְ����ϵ����������˺����ո��������ϵ��տ������˺Ų�һ��"*/);
			}
			// ��鵥������
			if (discountVO.getDdiscountdate().after(param.getDjrq())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000196")/* @res"���ְ����ϵ��������ڱ���С�ڵ����ո�����������"*/);
			}
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BILLPAY)) {// �ж�
			AcceptVO acceptVO = (AcceptVO) baseDao.retrieveByPK(AcceptVO.class,
					pk_busibill);
			// �����
			if (param.getMoneyy().doubleValue() != acceptVO.getMoneyy()
					.doubleValue()) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000197")/* @res"Ʊ�ݸ���ϵĽ�����ո��������Ͻ�һ��"*/);
			}

			String pk_cubasdoc = param.getBaseinfoVO().getReceiveunit();
			//���̾�Ϊ��Ҳ�Ϸ�
			boolean isNullCust = (pk_cubasdoc == null && param.getOtherunit() == null);
//			if(pk_cubasdoc == null){
//				throw new BusinessException("��Ʊ�Ǽǵ��ĸ�Ʊ��λΪ�գ��޷����ɵ���");
//			}
//
			boolean oneCustNull = (pk_cubasdoc == null && param.getOtherunit() != null) || (pk_cubasdoc != null && param.getOtherunit() == null);
			if (oneCustNull||(!isNullCust && !pk_cubasdoc.equals(param.getOtherunit()))) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000146")/*@res "Ʊ�ݵ��տλ���ո����Ŀ��̲�һ��"*/);
			}
//			String cust_sql = "pk_cubasdoc='" + pk_cubasdoc + "' and pk_corp='"
//					+ acceptVO.getPk_corp()
//					+ "' and (custflag='0' or custflag='1')";
//			HYPubBO bo = new HYPubBO();
//			CumandocVO[] cusmanVOs = (CumandocVO[]) bo.queryByCondition(
//					CumandocVO.class, cust_sql);
//			if (cusmanVOs != null && cusmanVOs.length > 0) {
//				if (!cusmanVOs[0].getPk_cumandoc().equals(param.getOtherunit())) {
//					throw new BusinessException("Ʊ�ݵĸ�Ʊ��λ���ո����Ŀͻ���һ��");
//				}
//			}
			// ����տ������˺�
//			if (param.getBaseinfoVO().getReceivebankacc()!= null && !param.getBaseinfoVO().getReceivebankacc().equals(param.getSkbankacc())) {
//				throw new BusinessException("Ʊ�ݵ��տ��˺����ո������տ��˺Ų�һ��");
//			}
			// ��鸶�������˺�
//			if (param.getBaseinfoVO().getPaybankacc()!= null&&!param.getBaseinfoVO().getPaybankacc().equals(param.getFkbankacc())) {
//				throw new BusinessException("Ʊ�ݵĸ����˺����ո����ĸ����˺Ų�һ��");
//			}
			// ��鵥������
			if (acceptVO.getDacceptdate()!= null && acceptVO.getDacceptdate().after(param.getDjrq())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000198")/* @res"���ݵĸ������ڱ���С�ڵ����ո�����������"*/);
			}
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INVOICE)) {// ��Ʊ
			if(pk_busibill != null && pk_busibill.length() > 0){
				regVO = (RegisterVO) baseDao.retrieveByPK(RegisterVO.class,
						pk_busibill);
			}

			//ȷ���ӱ������壺�и��������˺ţ��򸶿���Ϣ=0�����տ��˺ţ���֤����Ϣ=1��������������Ϣ=2
			int type ;
			if(!CommonUtil.isNull(param.getFkbankacc())){
				type = 0;
			}else if(!CommonUtil.isNull(param.getSkbankacc())){
				type = 1;
			}else if(CommonUtil.isNull(param.getFkbankacc() )&& CommonUtil.isNull(param.getSkbankacc())){
				type = 2;
			}else {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000199")/* @res"�ո����˺��������"*/);
			}

//			if (type == 0) {
//				// ��鸶�������˺�
//				if (!param.getBaseinfoVO().getPaybankacc().equals(param.getFkbankacc())) {
//					throw new BusinessException("Ʊ�ݵĸ��������˺ź��ո����ĸ��������˺Ų�һ��");
//				}
//			}
			if (type == 1) {
				if(param.getFbmbillno() == null){
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000184")/* @res"��"*/ + param.getRow() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000200")/* @res"�б�֤���¼�б������Ʊ�ݺ�"*/);
				}
				if (!param.getPk_curr().equals(param.getBaseinfoVO().getPk_curr())) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000184")/* @res"��"*/ + param.getRow() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000187")/* @res"��Ʊ�ݱ��ֲ�һ��"*/);
				}
				// ��鱣֤���˻�
				if (!regVO.getSecurityaccount().equals(param.getSkbankacc())) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000201")/* @res"Ʊ�ݱ�֤���˻����ո����տ������˺Ų�һ��"*/);
				}
				// ��鱣֤����
				if (regVO.getSecuritymoney().doubleValue() != param.getMoneyy()
						.doubleValue()) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000202")/* @res"��֤���һ��"*/);
				}
			}

//			if (type == 2) {
//				// ���������
//				if (regVO.getPoundagemoney().doubleValue() != param.getMoneyy()
//						.doubleValue()) {
//					throw new BusinessException("�����ѽ�һ��");
//				}
//			}
		}
	}

	public void doCheck(ArapBillParamVO param)
			throws BusinessException {
		//����Ƿ���������ʹ��
		String djdl = param.getOuterdjdl();
		if(!djdl.equals("hj")){
			OuterRelationDAO dao = new OuterRelationDAO();
			OuterVO[] outerVos = dao.queryByPkBusibill(param.getPk_busbill());
			if(!CommonUtil.isNull(outerVos)){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000147")/*@res "�Ѿ���Ʊ��ϵͳ��ʽ���ɵ���"*/);
			}
		}
	}


	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		if(param.getFbmbillno() == null){//��Ʊ�ݱ�ţ��򲻴���
			return;
		}

		String pk_busbill = param.getPk_busbill();
		// �����ⲿ���ݹ�����ϵ
		OuterVO outer = new OuterVO();
		outer.setPk_outerbill_h(param.getPk_bill_h());
		outer.setPk_outerbill_b(param.getPk_bill_b());
		outer.setOuterdjdl(param.getOuterdjdl());
		outer.setOuterstatus(FbmBusConstant.OUTERBILL_USE);
		outer.setOuterbilltype(param.getOuterbilltype());

		outer.setPk_billtypecode(param.getPk_billtypecode());

		outer.setPk_corp(param.getPk_corp());
		outer.setPk_busibill(pk_busbill);
		outer.setPk_register(param.getNewActionVO().getPk_source());
		baseDao.insertVO(outer);

		// �޸�ҵ�񵥾�״̬Ϊ�����ɵ���
		String billtype = param.getPk_billtypecode();
		String pk_busibill = param.getPk_busbill();
		SuperVO superVO = null;

		if(billtype != null){
			if (billtype.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {
				superVO = (SuperVO) baseDao.retrieveByPK(CollectionVO.class,
						pk_busibill);
				superVO.setAttributeValue("vbillstatus", IFBMStatus.Create);
				baseDao.updateVO(superVO);
			} else if (billtype.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {
				superVO = (SuperVO) baseDao.retrieveByPK(DiscountVO.class, pk_busibill);
				superVO.setAttributeValue("vbillstatus", IFBMStatus.Create);
				baseDao.updateVO(superVO);
			} else if (billtype.equals(FbmBusConstant.BILLTYPE_INVOICE)) {
				superVO = (SuperVO) baseDao.retrieveByPK(RegisterVO.class, pk_busibill);
				superVO.setAttributeValue("vbillstatus", IFBMStatus.Create);
				baseDao.updateVO(superVO);
			} else if (billtype.equals(FbmBusConstant.BILLTYPE_BILLPAY)) {
				superVO = (SuperVO) baseDao.retrieveByPK(AcceptVO.class, pk_busibill);
				superVO.setAttributeValue("vbillstatus", IFBMStatus.Create);
				baseDao.updateVO(superVO);
			}
		}
		super.afterAction(param);
	}

}