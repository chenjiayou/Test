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
 * 提示或错误信息翻译器
 * @author xwq
 *
 */
public class BusiMessageTranslator {



	public static String translateAction(BusiActionParamVO param) throws BusinessException{
		ActionVO action = param.getLastActionVO();
		String billtypename = retrieveBillNameByBillCode(action.getBilltype());
		String actionName = retrieveActNameByCode(action.getActioncode());
		//String endstatus = retriveStatusName(action.getEndstatus());
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000282")/* @res"\n实际前一操作为"*/+actionName + billtypename+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000283")/* @res",业务日期："*/+action.getActiondate()+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000284")/* @res",操作公司："*/ + getCorpName(param);
	}

	/**
	 * 根据单据类型返回单据名称
	 * @param pk_billtypecode
	 * @return
	 */
	public static String retrieveBillNameByBillCode (String pk_billtypecode) throws BusinessException{
		if(pk_billtypecode == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000285")/* @res"无单据类型编码"*/);
		}

		if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_GATHER)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000286")/* @res"收票登记单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BANKKEEP)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000287")/* @res"银行存放单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BANKBACK)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000288")/* @res"银行领用单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERKEEP)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000289")/* @res"内部存放单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERBACK)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000290")/* @res"内部领用单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_DISCOUNT_APP)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000291")/* @res"贴现申请单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000292")/* @res"贴现办理单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000293")/* @res"托收办理单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_IMPAWN)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000294")/* @res"票据质押单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INVOICE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000295")/* @res"付票登记单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BILLPAY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000296")/* @res"票据付款单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RETURN)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000297")/* @res"退票单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_LIQUIDATE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000298")/* @res"调剂清算单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_ILLEGAL)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000299")/* @res"非法票据登记"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_ENDORE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000300")/* @res"背书办理单"*/;
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RELIEF)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000301")/* @res"调剂出库单"*/;
		}

		throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000302")/* @res"错误的单据类型"*/ + pk_billtypecode);
	}
	/**
	 * 根据动作编码返回动作名称
	 * @param actioncode
	 * @return
	 */
	public static  String retrieveActNameByCode(String actioncode) throws BusinessException{
		if(actioncode == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000303")/* @res"动作编码为空"*/);
		}
		if(actioncode.equals(FbmActionConstant.SAVE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000304")/* @res"保存"*/;
		}else if(actioncode.equals(FbmActionConstant.DELETE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000305")/* @res"删除"*/;
		}else if(actioncode.equals(FbmActionConstant.AUDIT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000306")/* @res"审核"*/;
		}else if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000307")/* @res"弃审"*/;
		}else if(actioncode.equals(FbmActionConstant.EDITSAVE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000308")/* @res"编辑保存"*/;
		}else if(actioncode.equals(FbmActionConstant.ONAUDIT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000309")/* @res"审核进行中"*/;
		}else if(actioncode.equals(FbmActionConstant.TRANSACT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000011")/* @res"办理"*/;
		}else if(actioncode.equals(FbmActionConstant.CANCELTRANSACT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000006")/* @res"取消办理"*/;
		}else if(actioncode.equals(FbmActionConstant.DISABLE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000007")/* @res"作废"*/;
		}else if(actioncode.equals(FbmActionConstant.IMPAWNBACK)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000022")/* @res"质押回收"*/;
		}else if(actioncode.equals(FbmActionConstant.INPUT_SUCCESS)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000005")/* @res"入库"*/;
		}else if(actioncode.equals(FbmActionConstant.OUTPUT_SUCCESS)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000003")/* @res"出库"*/;
		}else if(actioncode.equals(FbmActionConstant.CANCELINPUT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000004")/* @res"取消入库"*/;
		}else if(actioncode.equals(FbmActionConstant.CANCELOUTPUT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000002")/* @res"取消出库"*/;
		}else if(actioncode.equals(FbmActionConstant.VOUCHER)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000028")/* @res"制证"*/;
		}else if(actioncode.equals(FbmActionConstant.CANCELVOUCHER)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000027")/* @res"取消制证"*/;
		}else if(actioncode.equals(FbmActionConstant.CANCELDESTROY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000024")/* @res"取消核销"*/;
		}else if(actioncode.equals(FbmActionConstant.DESTROY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000023")/* @res"核销"*/;
		}  else if (actioncode.equals(FbmActionConstant.CENTERUSE)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000151")/*@res "中心使用"*/;
		}
		throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000310")/* @res"未知的票据动作"*/ + actioncode);
	}

	/**
	 * 获得状态名称
	 * @param statuscode
	 * @return
	 * @throws BusinessException
	 */
	public static String retriveStatusName(String statuscode)throws BusinessException{
		if(statuscode == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000311")/* @res"状态编码为空"*/);
		}
		String name = FbmStatusConstant.getChinaName(statuscode);
//		if(statuscode.equals(FbmStatusConstant.NONE)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000312")/* @res"无状态"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_GATHER)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000091")/* @res"在收票"*/;
//		}else if(statuscode.equals(FbmStatusConstant.REGISTER)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000075")/* @res"已登记"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_ENDORE)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000076")/* @res"在背书"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_ENDORE)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000077")/* @res"已背书"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_DISCOUNT)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000078")/* @res"在贴现"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_DISCOUNT)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000079")/* @res"已贴现"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_COLLECTION)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000080")/* @res"在托收"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_COLLECTION)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000081")/* @res"已托收"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_BANK_KEEP)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000082")/* @res"在银行存放"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_BANK_KEEP)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000083")/* @res"已银行存放"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_BANK_BACK)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000084")/* @res"在银行领用"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_INNER_KEEP)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000085")/* @res"在内部存放"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_INNER_KEEP)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000086")/* @res"已内部存放"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_INNER_BACK)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000087")/* @res"在内部领用"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_RELIEF_KEEP)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000100")/* @res"在调剂存放"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_RELIEF_KEEP)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000101")/* @res"已调剂存放"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_IMPAWN)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000089")/* @res"在质押"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_IMPAWN)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000090")/* @res"已质押"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_IMPAWN_BACK)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000313")/* @res"在质押回收"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_DISABLE)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000088")/* @res"已作废"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_RETURN)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000092")/* @res"在退票"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_RETURN)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000093")/* @res"已退票"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_INVOICE)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000094")/* @res"在开票"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_INVOICE)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000095")/* @res"已开票"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_PAY)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000096")/* @res"在付款"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_PAY)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000097")/* @res"已付款"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_PAYBILL)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000098")/* @res"在付票"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_PAYBILL)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000099")/* @res"已付票"*/;
//		}else if(statuscode.equals(FbmStatusConstant.ON_RELIEF)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000314")/* @res"在调剂"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_RELIEF)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000315")/* @res"已调剂"*/;
//		}else if(statuscode.equals(FbmStatusConstant.HAS_DESTROY)){
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000102")/* @res"已核销"*/;
//		} else if (statuscode.equals(FbmStatusConstant.HAS_CENTER_USE)){
//			return "已中心使用";
//		}else if(statuscode.equals(arg0))
//
//

		if(name == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000316")/* @res"未知的状态"*/+statuscode);
		}
		return name;
	}

	/**
	 * 根据公司PK返回公司名称
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	private static String getCorpName(BusiActionParamVO param) throws BusinessException{
		BaseDAO dao = new BaseDAO();
		RegisterVO regVO = (RegisterVO)dao.retrieveByPK(RegisterVO.class, param.getLastActionVO().getPk_source());
		if(regVO == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000317")/* @res"找不到收付票登记单"*/);
		}
		CorpVO corpVO = (CorpVO)dao.retrieveByPK(CorpVO.class, regVO.getPk_corp());
		return corpVO == null?null:corpVO.getUnitname();
	}

}