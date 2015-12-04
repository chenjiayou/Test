package nc.bs.fbm.alarm;

import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;

/**
 * <p>
 * 应付票据到期预警后台类
 * <p>创建人：bsrl
 * <b>日期：2007-12-11
 *
 */
public class FBMInvoiceAlarmBO  extends nc.bs.fbm.alarm.FBMGatherAlarmBO {

	public FBMInvoiceAlarmBO(){
		   super();
		}

	private String billtype = FbmBusConstant.BILLTYPE_INVOICE;
	private String orientation = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000183")/* @res"应付"*/;
	private String[] bodyFields = new String[] {nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000055")/* @res"票据类型"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"票据编号"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000173")/* @res"收款单位"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000174")/* @res"付款单位"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"币种"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"金额"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000175")/* @res"出票日期"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000068")/* @res"到期日期"*/};
	private String status = "'" + FbmStatusConstant.HAS_INVOICE + "','" + FbmStatusConstant.HAS_PAYBILL + "'"
	                          + ",'" + FbmStatusConstant.ON_INVOICE + "','" + FbmStatusConstant.ON_PAYBILL + "'";


	@Override
	protected String getBillType() {
		return billtype;
	}

	@Override
	protected String getOrientation() {
		return orientation;
	}

	@Override
	protected String[] getbodyFields() {
		return bodyFields;
	}

	@Override
	protected String getStatus() {
		return status;
	}



}