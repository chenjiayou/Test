package nc.bs.fbm.pub.action;

import nc.bs.dao.BaseDAO;
import nc.vo.bd.CorpVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

/**
 * ��ʾ�������Ϣ������
 * @author xwq
 *
 */
public class BusiMessageTranslator {



	public static String translateAction(BusiActionParamVO param) throws BusinessException{
		ActionVO action = param.getLastActionVO();
		String billtypename = retrieveBillNameByBillCode(action.getBilltype());
		String actionName = retrieveActNameByCode(action.getActioncode());
		//String endstatus = retriveStatusName(action.getEndstatus());
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000282")/* @res"\nʵ��ǰһ����Ϊ"*/+actionName + billtypename+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000283")/* @res",ҵ�����ڣ�"*/+action.getActiondate()+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000284")/* @res",������˾��"*/ + getCorpName(param);
	}

	/**
	 * ���ݵ������ͷ��ص�������
	 * @param pk_billtypecode
	 * @return
	 */
	public static String retrieveBillNameByBillCode (String pk_billtypecode) throws BusinessException{
		if(pk_billtypecode == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000285")/* @res"�޵������ͱ���"*/);
		}

		if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_GATHER)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000286")/* @res"��Ʊ�Ǽǵ�"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BANKKEEP)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000287")/* @res"���д�ŵ�"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BANKBACK)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000288")/* @res"�������õ�"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERKEEP)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000289")/* @res"�ڲ���ŵ�"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERBACK)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000290")/* @res"�ڲ����õ�"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_DISCOUNT_APP)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000291")/* @res"�������뵥"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000292")/* @res"���ְ���"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000293")/* @res"���հ���"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_IMPAWN)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000294")/* @res"Ʊ����Ѻ��"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INVOICE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000295")/* @res"��Ʊ�Ǽǵ�"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BILLPAY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000296")/* @res"Ʊ�ݸ��"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RETURN)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000297")/* @res"��Ʊ��"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_LIQUIDATE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000298")/* @res"�������㵥"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_ILLEGAL)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000299")/* @res"�Ƿ�Ʊ�ݵǼ�"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_ENDORE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000300")/* @res"�������"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RELIEF)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000301")/* @res"�������ⵥ"*/;
		}

		throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000302")/* @res"����ĵ�������"*/ + pk_billtypecode);
	}
	/**
	 * ���ݶ������뷵�ض�������
	 * @param actioncode
	 * @return
	 */
	public static  String retrieveActNameByCode(String actioncode) throws BusinessException{
		if(actioncode == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000303")/* @res"��������Ϊ��"*/);
		}
		if(actioncode.equals(FbmActionConstant.SAVE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000304")/* @res"����"*/;
		}else if(actioncode.equals(FbmActionConstant.DELETE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000305")/* @res"ɾ��"*/;
		}else if(actioncode.equals(FbmActionConstant.AUDIT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000306")/* @res"���"*/;
		}else if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000307")/* @res"����"*/;
		}else if(actioncode.equals(FbmActionConstant.EDITSAVE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000308")/* @res"�༭����"*/;
		}else if(actioncode.equals(FbmActionConstant.ONAUDIT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000309")/* @res"��˽�����"*/;
		}else if(actioncode.equals(FbmActionConstant.TRANSACT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000011")/* @res"����"*/;
		}else if(actioncode.equals(FbmActionConstant.CANCELTRANSACT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000006")/* @res"ȡ������"*/;
		}else if(actioncode.equals(FbmActionConstant.DISABLE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000007")/* @res"����"*/;
		}else if(actioncode.equals(FbmActionConstant.IMPAWNBACK)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000022")/* @res"��Ѻ����"*/;
		}else if(actioncode.equals(FbmActionConstant.INPUT_SUCCESS)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000005")/* @res"���"*/;
		}else if(actioncode.equals(FbmActionConstant.OUTPUT_SUCCESS)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000003")/* @res"����"*/;
		}else if(actioncode.equals(FbmActionConstant.CANCELINPUT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000004")/* @res"ȡ�����"*/;
		}else if(actioncode.equals(FbmActionConstant.CANCELOUTPUT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000002")/* @res"ȡ������"*/;
		}else if(actioncode.equals(FbmActionConstant.VOUCHER)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000028")/* @res"��֤"*/;
		}else if(actioncode.equals(FbmActionConstant.CANCELVOUCHER)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000027")/* @res"ȡ����֤"*/;
		}else if(actioncode.equals(FbmActionConstant.CANCELDESTROY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000024")/* @res"ȡ������"*/;
		}else if(actioncode.equals(FbmActionConstant.DESTROY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000023")/* @res"����"*/;
		}  else if (actioncode.equals(FbmActionConstant.CENTERUSE)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000151")/*@res "����ʹ��"*/;
		}
		throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000310")/* @res"δ֪��Ʊ�ݶ���"*/ + actioncode);
	}

	/**
	 * ���״̬����
	 * @param statuscode
	 * @return
	 * @throws BusinessException
	 */
	public static String retriveStatusName(String statuscode)throws BusinessException{
		if(statuscode == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000311")/* @res"״̬����Ϊ��"*/);
		}
		String name = FbmStatusConstant.getChinaName(statuscode);
//		if(statuscode.equals(FbmStatusConstant.NONE)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000312")/* @res"��״̬"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_GATHER)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000091")/* @res"����Ʊ"*/;
//		}else if(statuscode.equals(FbmStatusConstant.REGISTER)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000075")/* @res"�ѵǼ�"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_ENDORE)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000076")/* @res"�ڱ���"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_ENDORE)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000077")/* @res"�ѱ���"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_DISCOUNT)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000078")/* @res"������"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_DISCOUNT)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000079")/* @res"������"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_COLLECTION)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000080")/* @res"������"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_COLLECTION)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000081")/* @res"������"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_BANK_KEEP)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000082")/* @res"�����д��"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_BANK_KEEP)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000083")/* @res"�����д��"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_BANK_BACK)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000084")/* @res"����������"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_INNER_KEEP)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000085")/* @res"���ڲ����"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_INNER_KEEP)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000086")/* @res"���ڲ����"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_INNER_BACK)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000087")/* @res"���ڲ�����"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_RELIEF_KEEP)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000100")/* @res"�ڵ������"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_RELIEF_KEEP)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000101")/* @res"�ѵ������"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_IMPAWN)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000089")/* @res"����Ѻ"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_IMPAWN)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000090")/* @res"����Ѻ"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_IMPAWN_BACK)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000313")/* @res"����Ѻ����"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_DISABLE)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000088")/* @res"������"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_RETURN)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000092")/* @res"����Ʊ"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_RETURN)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000093")/* @res"����Ʊ"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_INVOICE)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000094")/* @res"�ڿ�Ʊ"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_INVOICE)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000095")/* @res"�ѿ�Ʊ"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_PAY)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000096")/* @res"�ڸ���"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_PAY)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000097")/* @res"�Ѹ���"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_PAYBILL)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000098")/* @res"�ڸ�Ʊ"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_PAYBILL)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000099")/* @res"�Ѹ�Ʊ"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_RELIEF)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000314")/* @res"�ڵ���"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_RELIEF)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000315")/* @res"�ѵ���"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_DESTROY)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000102")/* @res"�Ѻ���"*/;
//		} else if (statuscode.equals(FbmStatusConstant.HAS_CENTER_USE)){
//			return "������ʹ��";
//		}else if(statuscode.equals(arg0))
//
//

		if(name == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000316")/* @res"δ֪��״̬"*/+statuscode);
		}
		return name;
	}

	/**
	 * ���ݹ�˾PK���ع�˾����
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	private static String getCorpName(BusiActionParamVO param) throws BusinessException{
		BaseDAO dao = new BaseDAO();
		RegisterVO regVO = (RegisterVO)dao.retrieveByPK(RegisterVO.class, param.getLastActionVO().getPk_source());
		if(regVO == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000317")/* @res"�Ҳ����ո�Ʊ�Ǽǵ�"*/);
		}
		CorpVO corpVO = (CorpVO)dao.retrieveByPK(CorpVO.class, regVO.getPk_corp());
		return corpVO == null?null:corpVO.getUnitname();
	}

}