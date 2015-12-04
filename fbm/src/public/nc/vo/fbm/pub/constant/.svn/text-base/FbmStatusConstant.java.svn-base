package nc.vo.fbm.pub.constant;

/**
 *
 * <p>
 *   票据状态常量定义
 * </p>
 * @author xwq
 * @date 2007-8-9
 * @version V5.0
 */
public class FbmStatusConstant {
	public static String ERROR = "error";//状态错误
	public static String NONE = "none";//无状态，特指新增票据
	public static String ON_GATHER = "on_gather";//在收票
	public static String REGISTER = "register";//已登记
	public static String ON_ENDORE = "on_endore";//在背书
	public static String HAS_ENDORE = "has_endore";//已背书
	public static String ON_DISCOUNT = "on_discount";//在贴现
	public static String HAS_DISCOUNT = "has_discount";//已贴现
	public static String ON_COLLECTION = "on_collection";//在托收
	public static String HAS_COLLECTION = "has_collection";//已托收
	public static String ON_BANK_KEEP = "on_bank_keep";//在银行存放
	public static String HAS_BANK_KEEP = "has_bank_keep";//已银行存放
	public static String ON_BANK_BACK = "on_bank_back";//在银行领用
	public static String ON_INNER_KEEP = "on_inner_keep";//在内部存放
	public static String HAS_INNER_KEEP = "has_inner_keep";//已内部存放
	public static String ON_INNER_BACK = "on_inner_back";//在内部领用

	//added by wes
	public static String ON_RELIEF_KEEP = "on_relief_keep";//在调剂存放
	public static String HAS_RELIEF_KEEP = "has_relief_keep";//已调剂存放

	public static String ON_IMPAWN = "on_impawn";//在质押
	public static String HAS_IMPAWN = "has_impawn";//已质押
	public static String ON_IMPAWN_BACK = "on_impawn_back";//在质押回收
	public static String HAS_DISABLE = "has_disable";//已作废
	public static String ON_RETURN = "on_return";//在退票
	public static String HAS_RETURN = "has_return";//已退票
	public static String ON_INVOICE = "on_invoice";//在开票
	public static String HAS_INVOICE = "has_invoice";//已开票
	public static String ON_PAY = "on_pay";//在付款
	public static String HAS_PAY = "has_pay";//已付款
	public static String ON_PAYBILL = "on_paybill";//在付票
	public static String HAS_PAYBILL = "has_paybill";//已付票

	public static String ON_RELIEF = "on_relief";//在调剂
	public static String HAS_RELIEF = "has_relief";//已调剂

	public static String HAS_DESTROY = "has_destroy"; //已核销

	public static String HAS_CENTER_USE = "has_center_use";//已中心使用(适用于中心统管业务的贴现托收)

	public static String HAS_CLEAR = "has_clear";//已冲销

	public static String ON_CENTER_RETURN = "on_center_return";//在中心退出
	public static String HAS_CENTER_RETURN = "has_center_return";//已中心退出
	public static String ON_UNIT_RETURN = "on_unit_return";//在单位退入
	public static String HAS_UNIT_RETURN = "has_unit_return";//已单位退入



	public static String getChinaName(String statusCode){
		if(statusCode.equals(ERROR)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000377")/* @res"状态错误"*/;
		}
		if(statusCode.equals(NONE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000312")/* @res"无状态"*/;
		}
		if(statusCode.equals(ON_GATHER)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000112")/* @res"自由态"*/;
		}
		if(statusCode.equals(REGISTER)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000075")/* @res"已登记"*/;
		}
		if(statusCode.equals(ON_ENDORE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000076")/* @res"在背书"*/;
		}
		if(statusCode.equals(HAS_ENDORE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000077")/* @res"已背书"*/;
		}
		if(statusCode.equals(ON_DISCOUNT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000078")/* @res"在贴现"*/;
		}
		if(statusCode.equals(HAS_DISCOUNT)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000079")/* @res"已贴现"*/;
		}
		if(statusCode.equals(ON_COLLECTION)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000080")/* @res"在托收"*/;
		}
		if(statusCode.equals(HAS_COLLECTION)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000081")/* @res"已托收"*/;
		}
		if(statusCode.equals(ON_BANK_KEEP)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000082")/* @res"在银行托管"*/;
		}
		if(statusCode.equals(HAS_BANK_KEEP)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000083")/* @res"已银行托管"*/;
		}
		if(statusCode.equals(ON_BANK_BACK)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000084")/* @res"在银行领用"*/;
		}
		if(statusCode.equals(ON_IMPAWN)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000089")/* @res"在质押"*/;
		}
		if(statusCode.equals(HAS_IMPAWN)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000090")/* @res"已质押"*/;
		}
		if(statusCode.equals(ON_IMPAWN_BACK)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000313")/* @res"在质押回收"*/;
		}
		if(statusCode.equals(HAS_DISABLE)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000088")/* @res"已作废"*/;
		}
		if (statusCode.equals(ON_RELIEF_KEEP)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000100")/* @res"在调剂托管"*/;
		}
		if (statusCode.equals(HAS_RELIEF_KEEP)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000101")/* @res"已调剂托管"*/;
		}if (statusCode.equals(HAS_RELIEF)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000103")/* @res"已调剂"*/;
		}if (statusCode.equals(ON_RELIEF)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000100")/* @res"在调剂托管"*/;
		}if (statusCode.equals(ON_INNER_BACK)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000087")/* @res"在内部领用"*/;
		}
		if(statusCode.equals(FbmStatusConstant.ON_INNER_KEEP)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000085")/* @res"在内部存放"*/;
		}
		if(statusCode.equals(FbmStatusConstant.HAS_INNER_KEEP)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000086")/* @res"已内部存放"*/;
		}
		if(ON_PAY.equals(statusCode)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000234")/*@res "在付款"*/;
		}
		if(HAS_PAY.equals(statusCode)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPT36201505-000029")/*@res "已付款"*/;
		}
		if (HAS_CENTER_USE.equals(statusCode)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000078")/*@res "已中心使用"*/;
		}if (HAS_CLEAR.equals(statusCode)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000083")/*@res "已冲销"*/;
		}if( ON_CENTER_RETURN.equals(statusCode)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000079")/*@res "在中心退出"*/;
		}
		if(HAS_CENTER_RETURN.equals(statusCode)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000080")/*@res "已中心退出"*/;
		}
		if(ON_UNIT_RETURN.equals(statusCode)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000081")/*@res "在单位退入"*/;
		}
		if(HAS_UNIT_RETURN.equals(statusCode)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000082")/*@res "已单位退入"*/;
		}
		if(statusCode.equals(FbmStatusConstant.ON_RETURN)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000092")/* @res"在退票"*/;
		}
		if(statusCode.equals(FbmStatusConstant.HAS_RETURN)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000093")/* @res"已退票"*/;
		}
		if(statusCode.equals(FbmStatusConstant.ON_PAY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000096")/* @res"在付款"*/;
		}
		if(statusCode.equals(FbmStatusConstant.HAS_PAY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000097")/* @res"已付款"*/;
		}
		if(statusCode.equals(FbmStatusConstant.ON_PAYBILL)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000098")/* @res"在付票"*/;
		}
		if(statusCode.equals(FbmStatusConstant.HAS_PAYBILL)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000099")/* @res"已付票"*/;
		}
		if(statusCode.equals(FbmStatusConstant.ON_RELIEF)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000314")/* @res"在调剂"*/;
		}
		if(statusCode.equals(FbmStatusConstant.HAS_RELIEF)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000315")/* @res"已调剂"*/;
		}
		if(statusCode.equals(FbmStatusConstant.HAS_DESTROY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000102")/* @res"已核销"*/;
		}


		return null;
	}
}