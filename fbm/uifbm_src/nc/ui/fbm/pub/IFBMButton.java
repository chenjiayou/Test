/**
 *
 */
package nc.ui.fbm.pub;

/**
 * <p>
 * FBM按钮ID注册
 * <p>
 * 创建人：lpf <b>日期：2007-8-24
 * 
 */
public interface IFBMButton {
	/**
	 * 收票登记-业务处理
	 */
	public static int Gather_BusGroup = 101;
	/**
	 * 收票登记-银行存放
	 */
	public static int Gather_BankKeep = 102;
	/**
	 * 收票登记-银行领用
	 */
	public static int Gather_BankBack = 103;

	/**
	 * 银行托收-办理
	 */
	public static int ConsignBank_Transact = 105;
	/**
	 * 银行托收-作废
	 */
	public static int ConsignBank_Disable = 106;
	/**
	 * 贴现申请-办理
	 */
	public static int DiscountApply_Transact = 107;
	/**
	 * 收票登记-集中退票
	 */
	public static int Gather_ReturnBill = 108;
	/**
	 * 收票登记-银行贴现
	 */
	public static int Gather_BankDiscount = 109;
	/**
	 * 收票登记-银行托收
	 */
	public static int Gather_Consign = 110;

	/**
	 * 贴现试算_贴现试算
	 */
	public static int Discount_Calculate = 111;
	/**
	 * 开票-联查授信额度
	 */
	public static int QUERYRATION = 112;
	/**
	 * 开票-票据承兑
	 */
	public static int Invoice_BillPay = 113;
	/**
	 * 开票-退票
	 */
	public static int Invoice_Return = 114;
	/**
	 * 开票登记-业务处理
	 */
	public static int Invoice_BusGroup = 115;
	/**
	 * 开票登记-联查
	 */
	public static int Invoice_LinkQueryGroup = 116;

	/**
	 * 开票登记-核销
	 */
	public static int Invoice_CancelBill = 118;
	/**
	 * 开票登记-取消核销
	 */
	public static int Invoice_DelCancelBill = 119;
	/**
	 * 收票登记-质押
	 */
	public static int Gather_Impawn = 120;
	/**
	 * 收票登记-联查
	 */
	public static int Gather_LQueryGroup = 121;
	/**
	 * 收票登记-联查收付款单据
	 */
	public static int Gather_LQuerySFBill = 122;
	/**
	 * 收票登记-联查票据备查簿
	 */
	public static int Gather_LQueryPJBook = 123;
	/**
	 * 中心托管-确认
	 */
	public static int CenterKeep_INPUT = 124;
	/**
	 * 中心托管-取消确认
	 */
	public static int CenterKeep_CANCELINPUT = 125;
	/**
	 * 开票登记-联查
	 */
	public static int Invoice_LQueryGroup = 126;

	/**
	 * 退票-业务处理
	 */
	public static int ReturnBill_BusGroup = 128;
	/**
	 * 退票-转出
	 */
	public static int ReturnBill_TransToSFBill = 129;
	/**
	 * 退票-取消转出
	 */
	public static int ReturnBill_CancelTrans = 130;

	/**
	 * 银行托收-联查组按钮
	 */
	public static int ConsignBank_LinkGroup = 131;
	/**
	 * 银行托收-联查票据
	 */
	public static int ConsignBank_LinkGather = 132;

	/**
	 * 贴现办理-联查组按钮
	 */
	public static int Discount_LinkGroup = 134;
	/**
	 * 贴现办理-联查票据
	 */
	public static int Discount_LinkGather = 135;

	/**
	 * 票据质押-质押回收
	 */
	public static int BTN_IMPAWN_BACK = 150;
	/**
	 * 中心入库-退回
	 */
	public static int CenterKeep_Return = 151;

	/**
	 * 中心出库-出库
	 */
	public static int CenterBack_Output = 154;
	/**
	 * 中心出库-取消出库
	 */
	public static int CenterBack_CancelOut = 155;
	/**
	 * 中心出入库-联查总账凭证
	 */
	public static int Center_Storage_QueryGL = 156;

	/**
	 * 中心托管-联查组（票据备查簿+联查凭证）
	 */
	public static int Storage_LQueryGroup = 158;

	/**
	 * 调剂出库-出库
	 */
	public static int Relief_Output = 159;
	/**
	 * 调剂出库取消出库
	 */
	public static int Relief_CancelOutput = 160;
	/**
	 * 调剂出库-制证
	 */
	public static int Relief_Voucher = 161;
	/**
	 * 调剂出库-取消制证
	 */
	public static int Relief_CancelVoucher = 162;
	/**
	 * 调剂出库-联查组按钮
	 */
	public static int Relief_LinkQueryGroup = 163;
	/**
	 * 调剂出库-联查-账户余额
	 */
	public static int Relief_LQAccoutBalance = 164;
	/**
	 * 调剂出库-联查-凭证
	 */
	public static int Relief_LQVoucher = 165;

	/**
	 * 调剂清算回单-制证
	 */
	public static int Reciept_Voucher = 166;
	/**
	 * 调剂清算回单-取消制证
	 */
	public static int Reciept_CancelVoucher = 167;

	/**
	 * 调剂清算单-联查组按钮
	 */
	public static int Reckon_LinkGroup = 168;
	/**
	 * 调剂清算单-联查-账户余额
	 */
	public static int Reckon_LinkAccoutBalance = 169;

	/**
	 * 调剂清算单-联查-凭证
	 */
	public static int Reckon_LinkVoucher = 170;

	/**
	 * 调剂清算单-制证
	 */
	public static int Reckon_Voucher = 171;
	/**
	 * 调剂清算单-取消制证
	 */
	public static int Reckon_CancelVoucher = 172;

	/**
	 * 银行托收-取消办理
	 */
	public static int ConsignBank_CancelTransact = 173;

	/**
	 * 背书办理单－制证
	 */
	public static int Endore_Voucher = 174;

	/**
	 * 背书办理单－取消制证
	 */
	public static int Endore_CancelVoucher = 175;

	/**
	 * 银行托收－制证
	 */
	public static int ConsignBank_Voucher = 176;

	/**
	 * 银行托收－取消制证
	 */
	public static int ConsignBank_CancelVoucher = 177;

	/**
	 * 贴现办理－制证
	 */
	public static int Discount_Voucher = 178;

	/**
	 * 贴现办理－取消制证
	 */
	public static int Discount_CancelVoucher = 179;

	/**
	 * 背书办理单-联查-凭证
	 */
	public static int Endore_LinkVoucher = 180;

	/**
	 * 背书办理－冲销
	 */
	public static int Endore_Destroy = 181;

	/**
	 * 贴现办理－联查凭证
	 */
	public static int Discount_LinkVoucher = 182;

	/**
	 * 银行托收－联查凭证
	 */
	public static int ConsignBank_LinkVoucher = 183;

	/**
	 * 背书办理－联查组
	 */
	public static int Endore_LinkGroup = 184;

	/**
	 * 背书办理－联查收付报单据
	 */
	public static int Endore_LinkBill = 185;

	/**
	 * 背书办理－联查冲销凭证
	 */
	public static int Endore_LinkClearVoucher = 186;

	/** 银行托收--联查托收计划 */
	public static int ConsignBank_LinkPlan = 187;

	/** 贴现办理--联查收票计划 */
	public static int Discount_LinkPlan = 188;

	/** 内部存放--联查按钮组 */
	public static int InnerKeep_LinkGroup = 189;

	/** 内部领用--联查按钮组 */
	public static int InnerBack_LinkGroup = 192;

	/** 银行托收--联查收票计划 */
	public static int ConsignBank_LinkGatherPlan = 193;

	/** 贴现办理--联查收票计划 */
	public static int Discount_LinkGatherPlan = 194;


	
	
	
	/**
	 * 制证按钮编码
	 */
	// public static String INNERKEEP_VOUCHER="VOUCHER";
	/**
	 * 取消制证编码
	 */
	// public static String INNERKEEP_DELVOUCHER="DELGL";
	/**
	 * 入库编码
	 */
	public static String INNERKEEP_INPUT = "INPUT";
	/**
	 * 取消入库编码
	 */
	public static String INNERKEEP_CANCELINPUT = "CANCELIN";
	/**
	 * 出库编码
	 */
	public static String INNERBACK_OUTPUT = "OUTPUT";
	/**
	 * 取消出库编码
	 */
	public static String INNERBACK_CANCELOUT = "CANCELOUT";

	/**
	 * 调剂/清算/清算回单-制证
	 */
	public static String RELIEF_VOUCHER = "VOUCHER";
	/**
	 * 清算-取消制证
	 */
	public static String RECKON_UNVOUCHER = "UNVOUCHER";

	/**
	 * 调剂/清算回单-取消制证
	 */
	public static String RELIEF_CANCELVOUCHER = "CANCELVOUCHER";

	/**
	 * 背书办理－制证
	 */
	public static String Endore_VOUCHER_STR = "VOUCHER";

	/**
	 * 背书办理－取消制证
	 */
	public static String Endore_CANCELVOUCHER_STR = "CANCELVOUCHER";

	/**
	 * 背书办理－冲销
	 */
	public static String Endore_Destroy_STR = "DESTROY";

	/**
	 * 附件
	 */
	public static int Attach = 284;

	public static int FBM_VOUCHER = 300;
	public static String FBM_VOUCHER_STR = "VOUCHER";

	public static int FBM_CANCELVOUCHER = 301;
	public static String FBM_CANCELVOUCHER_STR = "CANCELVOUCHER";

	public static int FBM_QUERYVOUCHER = 302;
	public static String FBM_QUERYVOUCHER_STR = "QUERYVOUCHER";

	public static int FBM_SUBMIT = 303;
	public static String FBM_SUBMIT_STR = "SUBMIT";

	public static int FBM_CANCELSUBMIT = 304;
	public static String FBM_CANCELSUBMIT_STR = "CANCELSUBMIT";

	public static int FBM_TALLY = 305;
	public static String FBM_TALLY_STR = "TALLY";

	public static int FBM_CANCELTALLY = 306;
	public static String FBM_CANCELTALLY_STR = "CANCELTALLY";

	public static int LINK_ACCOUNTBANLANCE = 400;
	public static String LINK_ACCOUNTBANLANCE_STR = "Link_AccountBanlance";

	// 联查转入帐户
	public static int Reckon_LinkInAccountBalance = 500;
	public static String Reckon_LinkInAccountBalance_STR = "LinkInAccountBalance";

	// 联查转出帐户
	public static int Reckon_LinkOutAccountBalance = 501;
	public static String Reckon_LinkOutAccountBalance_STR = "LinkOutAccountBalance";

	// 取消质押回收
	public static int BTN_CANCEL_IMPAWNBACK = 601;
	public static String BTN_CANCEL_IMPAWNBACK_STR = "CANCELBACK";

	// 退票联查组
	public static int BTN_RETURN_QUERYGROUP = 701;
	public static String BTN_RETURN_QUERYGROUP_STR = "return_querygroup";

	public static int BTN_QUERY_PLAN = 710;
	public static String BTN_QUERY_PLAN_STR = "query_plan";

	public static int BTN_QUERY_CHARGE_PLAN = 720;
	public static String BTN_QUERY_CHARGE_PLAN_STR = "query_chargeplan";
	public static int BTN_QUERY_INVOICE_PAY_PLAN = 721;
	public static String BTN_QUERY_INVOICE_PAY_PLAN_STR = "query_payplan";

	public static int BTN_ACCEPT_QUERY_GROUP = 722;
	public static String BTN_ACCEPT_QUERY_GROUP_STR = "acceptquerygroup";

	public static int BTN_QUERY_INTEREST_PLAN = 723;
	public static String BTN_QUERY_INTEREST_PLAN_STR = "queryinterestplan";

	public static int BTN_QUERY_BALANCE_PLAN = 724;
	public static String BTN_QUERY_BALANCE_PLAN_STR = "querybalanceplan";

	/** 收票登记－收票完成 zhouwb 2008-9-17 */
	public static final int BTNVO_SPWC = 190;
	/** 收票登记－取消收票 zhouwb 2008-9-17 */
	public static final int BTNVO_QXSP = 191;

	public static final String Invoice_CancelBill_STR = "DESTROY";// 付票 核销

	public static final int PRINT4NOTE = 195; // 票据通打印
	
	
	
	
	/** 
	 *批量托收 
	 */
	public static final int Gather_BatchConsign = 196;
	
	/**
	 * 托收批量审核
	 */
	public static final int Consign_BatchApprove = 197;
	
	/**
	 * 银行托收:批量操作.
	 */
	public static final int Consign_Batch = 198;
	
	/**
	 * 银行托收-批量弃审.
	 */
	public static final int Consign_BatchUnApprove = 199;
	
	/**
	 * 银行托收-批量办理
	 */
	public static final int Consign_BatchTransact = 200;
	
	/**
	 * 银行托收-批量取消办理
	 */
	public static final int Consign_BatchUnTransact = 201;
	
	/**
	 * 银行托收-批量生成单据.
	 */
	public static final int Consign_BatchCreateBill = 202;
	
	/**
	 * 票据:全选
	 */
	public static final int FBM_SELECTALL = 203;
	
	/**
	 * 票据:全消
	 */
	public static final int FBM_CANCELSELECT = 204;
	
	/**
	 * 票据:反选
	 */
	public static final int FBM_INVERTSELECT = 205;
	
	/**
	 * 票据:批量查询
	 */
	public static final int FBM_BATCHQUERY = 206;
	
	/**
	 * 批量操作
	 */
	public static final int Gather_Batch = 207;
	
	/**
	 * 批量质押
	 */
	public static final int Gather_BatchImpawn =  208;
	
	/**
	 * 批量银行贴现
	 */
	public static final int Gather_BatchDiscount = 209;
	
	

}
