/**
 *
 */
package nc.ui.fbm.pub;

/**
 * <p>
 * FBM��ťIDע��
 * <p>
 * �����ˣ�lpf <b>���ڣ�2007-8-24
 * 
 */
public interface IFBMButton {
	/**
	 * ��Ʊ�Ǽ�-ҵ����
	 */
	public static int Gather_BusGroup = 101;
	/**
	 * ��Ʊ�Ǽ�-���д��
	 */
	public static int Gather_BankKeep = 102;
	/**
	 * ��Ʊ�Ǽ�-��������
	 */
	public static int Gather_BankBack = 103;

	/**
	 * ��������-����
	 */
	public static int ConsignBank_Transact = 105;
	/**
	 * ��������-����
	 */
	public static int ConsignBank_Disable = 106;
	/**
	 * ��������-����
	 */
	public static int DiscountApply_Transact = 107;
	/**
	 * ��Ʊ�Ǽ�-������Ʊ
	 */
	public static int Gather_ReturnBill = 108;
	/**
	 * ��Ʊ�Ǽ�-��������
	 */
	public static int Gather_BankDiscount = 109;
	/**
	 * ��Ʊ�Ǽ�-��������
	 */
	public static int Gather_Consign = 110;

	/**
	 * ��������_��������
	 */
	public static int Discount_Calculate = 111;
	/**
	 * ��Ʊ-�������Ŷ��
	 */
	public static int QUERYRATION = 112;
	/**
	 * ��Ʊ-Ʊ�ݳж�
	 */
	public static int Invoice_BillPay = 113;
	/**
	 * ��Ʊ-��Ʊ
	 */
	public static int Invoice_Return = 114;
	/**
	 * ��Ʊ�Ǽ�-ҵ����
	 */
	public static int Invoice_BusGroup = 115;
	/**
	 * ��Ʊ�Ǽ�-����
	 */
	public static int Invoice_LinkQueryGroup = 116;

	/**
	 * ��Ʊ�Ǽ�-����
	 */
	public static int Invoice_CancelBill = 118;
	/**
	 * ��Ʊ�Ǽ�-ȡ������
	 */
	public static int Invoice_DelCancelBill = 119;
	/**
	 * ��Ʊ�Ǽ�-��Ѻ
	 */
	public static int Gather_Impawn = 120;
	/**
	 * ��Ʊ�Ǽ�-����
	 */
	public static int Gather_LQueryGroup = 121;
	/**
	 * ��Ʊ�Ǽ�-�����ո����
	 */
	public static int Gather_LQuerySFBill = 122;
	/**
	 * ��Ʊ�Ǽ�-����Ʊ�ݱ��鲾
	 */
	public static int Gather_LQueryPJBook = 123;
	/**
	 * �����й�-ȷ��
	 */
	public static int CenterKeep_INPUT = 124;
	/**
	 * �����й�-ȡ��ȷ��
	 */
	public static int CenterKeep_CANCELINPUT = 125;
	/**
	 * ��Ʊ�Ǽ�-����
	 */
	public static int Invoice_LQueryGroup = 126;

	/**
	 * ��Ʊ-ҵ����
	 */
	public static int ReturnBill_BusGroup = 128;
	/**
	 * ��Ʊ-ת��
	 */
	public static int ReturnBill_TransToSFBill = 129;
	/**
	 * ��Ʊ-ȡ��ת��
	 */
	public static int ReturnBill_CancelTrans = 130;

	/**
	 * ��������-�����鰴ť
	 */
	public static int ConsignBank_LinkGroup = 131;
	/**
	 * ��������-����Ʊ��
	 */
	public static int ConsignBank_LinkGather = 132;

	/**
	 * ���ְ���-�����鰴ť
	 */
	public static int Discount_LinkGroup = 134;
	/**
	 * ���ְ���-����Ʊ��
	 */
	public static int Discount_LinkGather = 135;

	/**
	 * Ʊ����Ѻ-��Ѻ����
	 */
	public static int BTN_IMPAWN_BACK = 150;
	/**
	 * �������-�˻�
	 */
	public static int CenterKeep_Return = 151;

	/**
	 * ���ĳ���-����
	 */
	public static int CenterBack_Output = 154;
	/**
	 * ���ĳ���-ȡ������
	 */
	public static int CenterBack_CancelOut = 155;
	/**
	 * ���ĳ����-��������ƾ֤
	 */
	public static int Center_Storage_QueryGL = 156;

	/**
	 * �����й�-�����飨Ʊ�ݱ��鲾+����ƾ֤��
	 */
	public static int Storage_LQueryGroup = 158;

	/**
	 * ��������-����
	 */
	public static int Relief_Output = 159;
	/**
	 * ��������ȡ������
	 */
	public static int Relief_CancelOutput = 160;
	/**
	 * ��������-��֤
	 */
	public static int Relief_Voucher = 161;
	/**
	 * ��������-ȡ����֤
	 */
	public static int Relief_CancelVoucher = 162;
	/**
	 * ��������-�����鰴ť
	 */
	public static int Relief_LinkQueryGroup = 163;
	/**
	 * ��������-����-�˻����
	 */
	public static int Relief_LQAccoutBalance = 164;
	/**
	 * ��������-����-ƾ֤
	 */
	public static int Relief_LQVoucher = 165;

	/**
	 * ��������ص�-��֤
	 */
	public static int Reciept_Voucher = 166;
	/**
	 * ��������ص�-ȡ����֤
	 */
	public static int Reciept_CancelVoucher = 167;

	/**
	 * �������㵥-�����鰴ť
	 */
	public static int Reckon_LinkGroup = 168;
	/**
	 * �������㵥-����-�˻����
	 */
	public static int Reckon_LinkAccoutBalance = 169;

	/**
	 * �������㵥-����-ƾ֤
	 */
	public static int Reckon_LinkVoucher = 170;

	/**
	 * �������㵥-��֤
	 */
	public static int Reckon_Voucher = 171;
	/**
	 * �������㵥-ȡ����֤
	 */
	public static int Reckon_CancelVoucher = 172;

	/**
	 * ��������-ȡ������
	 */
	public static int ConsignBank_CancelTransact = 173;

	/**
	 * �����������֤
	 */
	public static int Endore_Voucher = 174;

	/**
	 * ���������ȡ����֤
	 */
	public static int Endore_CancelVoucher = 175;

	/**
	 * �������գ���֤
	 */
	public static int ConsignBank_Voucher = 176;

	/**
	 * �������գ�ȡ����֤
	 */
	public static int ConsignBank_CancelVoucher = 177;

	/**
	 * ���ְ�����֤
	 */
	public static int Discount_Voucher = 178;

	/**
	 * ���ְ���ȡ����֤
	 */
	public static int Discount_CancelVoucher = 179;

	/**
	 * �������-����-ƾ֤
	 */
	public static int Endore_LinkVoucher = 180;

	/**
	 * �����������
	 */
	public static int Endore_Destroy = 181;

	/**
	 * ���ְ�������ƾ֤
	 */
	public static int Discount_LinkVoucher = 182;

	/**
	 * �������գ�����ƾ֤
	 */
	public static int ConsignBank_LinkVoucher = 183;

	/**
	 * �������������
	 */
	public static int Endore_LinkGroup = 184;

	/**
	 * ������������ո�������
	 */
	public static int Endore_LinkBill = 185;

	/**
	 * ��������������ƾ֤
	 */
	public static int Endore_LinkClearVoucher = 186;

	/** ��������--�������ռƻ� */
	public static int ConsignBank_LinkPlan = 187;

	/** ���ְ���--������Ʊ�ƻ� */
	public static int Discount_LinkPlan = 188;

	/** �ڲ����--���鰴ť�� */
	public static int InnerKeep_LinkGroup = 189;

	/** �ڲ�����--���鰴ť�� */
	public static int InnerBack_LinkGroup = 192;

	/** ��������--������Ʊ�ƻ� */
	public static int ConsignBank_LinkGatherPlan = 193;

	/** ���ְ���--������Ʊ�ƻ� */
	public static int Discount_LinkGatherPlan = 194;


	
	
	
	/**
	 * ��֤��ť����
	 */
	// public static String INNERKEEP_VOUCHER="VOUCHER";
	/**
	 * ȡ����֤����
	 */
	// public static String INNERKEEP_DELVOUCHER="DELGL";
	/**
	 * ������
	 */
	public static String INNERKEEP_INPUT = "INPUT";
	/**
	 * ȡ��������
	 */
	public static String INNERKEEP_CANCELINPUT = "CANCELIN";
	/**
	 * �������
	 */
	public static String INNERBACK_OUTPUT = "OUTPUT";
	/**
	 * ȡ���������
	 */
	public static String INNERBACK_CANCELOUT = "CANCELOUT";

	/**
	 * ����/����/����ص�-��֤
	 */
	public static String RELIEF_VOUCHER = "VOUCHER";
	/**
	 * ����-ȡ����֤
	 */
	public static String RECKON_UNVOUCHER = "UNVOUCHER";

	/**
	 * ����/����ص�-ȡ����֤
	 */
	public static String RELIEF_CANCELVOUCHER = "CANCELVOUCHER";

	/**
	 * ���������֤
	 */
	public static String Endore_VOUCHER_STR = "VOUCHER";

	/**
	 * �������ȡ����֤
	 */
	public static String Endore_CANCELVOUCHER_STR = "CANCELVOUCHER";

	/**
	 * �����������
	 */
	public static String Endore_Destroy_STR = "DESTROY";

	/**
	 * ����
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

	// ����ת���ʻ�
	public static int Reckon_LinkInAccountBalance = 500;
	public static String Reckon_LinkInAccountBalance_STR = "LinkInAccountBalance";

	// ����ת���ʻ�
	public static int Reckon_LinkOutAccountBalance = 501;
	public static String Reckon_LinkOutAccountBalance_STR = "LinkOutAccountBalance";

	// ȡ����Ѻ����
	public static int BTN_CANCEL_IMPAWNBACK = 601;
	public static String BTN_CANCEL_IMPAWNBACK_STR = "CANCELBACK";

	// ��Ʊ������
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

	/** ��Ʊ�Ǽǣ���Ʊ��� zhouwb 2008-9-17 */
	public static final int BTNVO_SPWC = 190;
	/** ��Ʊ�Ǽǣ�ȡ����Ʊ zhouwb 2008-9-17 */
	public static final int BTNVO_QXSP = 191;

	public static final String Invoice_CancelBill_STR = "DESTROY";// ��Ʊ ����

	public static final int PRINT4NOTE = 195; // Ʊ��ͨ��ӡ
	
	
	
	
	/** 
	 *�������� 
	 */
	public static final int Gather_BatchConsign = 196;
	
	/**
	 * �����������
	 */
	public static final int Consign_BatchApprove = 197;
	
	/**
	 * ��������:��������.
	 */
	public static final int Consign_Batch = 198;
	
	/**
	 * ��������-��������.
	 */
	public static final int Consign_BatchUnApprove = 199;
	
	/**
	 * ��������-��������
	 */
	public static final int Consign_BatchTransact = 200;
	
	/**
	 * ��������-����ȡ������
	 */
	public static final int Consign_BatchUnTransact = 201;
	
	/**
	 * ��������-�������ɵ���.
	 */
	public static final int Consign_BatchCreateBill = 202;
	
	/**
	 * Ʊ��:ȫѡ
	 */
	public static final int FBM_SELECTALL = 203;
	
	/**
	 * Ʊ��:ȫ��
	 */
	public static final int FBM_CANCELSELECT = 204;
	
	/**
	 * Ʊ��:��ѡ
	 */
	public static final int FBM_INVERTSELECT = 205;
	
	/**
	 * Ʊ��:������ѯ
	 */
	public static final int FBM_BATCHQUERY = 206;
	
	/**
	 * ��������
	 */
	public static final int Gather_Batch = 207;
	
	/**
	 * ������Ѻ
	 */
	public static final int Gather_BatchImpawn =  208;
	
	/**
	 * ������������
	 */
	public static final int Gather_BatchDiscount = 209;
	
	

}
