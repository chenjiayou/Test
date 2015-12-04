package nc.vo.fbm.pub.constant;

/**
 * 
 * <p>
 *   Ʊ��ҵ��������
 * </p>
 * @author xwq
 * @date 2007-8-9
 * @version V5.0
 */
public class FbmBusConstant {
	

	//Ʊ�ݷ���
	public static String ORIEINTATION_AR = "ar";//Ӧ��
	public static String ORIEINTATION_AP = "ap";//Ӧ��
	public static String ORIEINTATION_NONE = "none";//����
	
	
	//��Ʊ��Դ����
	public static String GATHER_TYPE_INPUT = "input";//��Ʊ¼��
	public static String GATHER_TYPE_ENDORE = "endore";//��������
	public static String GATHER_TYPE_KEEP = "keep";//������ɣ���������ֱ�����ʱ������Ʊ�Ǽǵ�
	public static String GATHER_TYPE_RELIEF = "relief";//��������
	public static String GATHER_TYPE_UNISTORAGE = "unistorage";//ͳ������
	public static String GATHER_TYPE_RETURN = "return";//��Ʊ����
	
	//��ŵ�������
	public static String KEEP_CENTRE_IN = "centrein";//���Ĵ�ŵ�
	public static String KEEP_CENTRE_OUT = "centreout";//�������õ�
	public static String KEEP_BANK_IN = "bankin";//���д�ŵ�
	public static String KEEP_BANK_OUT = "bankout";//�������õ�
	
	//��ŷ�ʽ
	public static String KEEP_TYPE_STORE = "store";//������
	public static String KEEP_TYPE_RELIEF = "relief";//�������
	
	//���ֵ�������
	public static String DISCOUNT_TYPE_APPLY = "apply";//�������뵥
	public static String DISCOUNT_TYPE_TRANSACT = "transact";//���ְ���
	
	//��Ʊ����
	//public static String BACK_TYPE_DISCOUNT = "discount";//������Ʊ
	public static String BACK_TYPE_ENDORE = "endore";//������Ʊ
	public static String BACK_TYPE_INVOICE = "invoice";//��Ʊ��Ʊ
	public static String BACK_TYPE_GATHER = "gather";//��Ʊ��Ʊ
	public static String BACK_TYPE_DISABLE = "disable";//��Ʊ��Ʊ
	public static String BACK_TYPE_UNISTORAGE = "unistorage";//�����˳�
	public static String BACK_TYPE_UNISTORAGE_UNIT ="unit";//��������
	
	//Ʊ�ݵ������� 
	public static String BILLTYPE_GATHER = "36GA";//��Ʊ�Ǽǵ�
	public static String BILLTYPE_BANKKEEP = "36GB";//���д�ŵ�
	public static String BILLTYPE_BANKBACK = "36GC";//�������õ�
	public static String BILLTYPE_INNERKEEP = "36GD";//�ڲ���ŵ�
	public static String BILLTYPE_INNERBACK = "36GE";//�ڲ����õ�
	public static String BILLTYPE_DISCOUNT_APP = "36GF";//�������뵥
	public static String BILLTYPE_DISCOUNT_TRANSACT = "36GG";//���ְ���
	//2007.8.27 xwq �ص��ϲ�Ϊ����
	//public static String BILLTYPE_DISCOUNT_RECEIPT = "36GH";//���ֻص�
	public static String BILLTYPE_COLLECTION_UNIT = "36GI";//���հ���,
	//public static String BILLTYPE_COLLECTION_CENTRE = "36GJ";//�������հ���
	public static String BILLTYPE_IMPAWN = "36GO";//Ʊ����Ѻ��
	public static String BILLTYPE_INVOICE = "36GL";//��Ʊ����
	public static String BILLTYPE_BILLPAY = "36GM";//Ʊ�ݸ��
	public static String BILLTYPE_RETURN = "36GN";//Ʊ����Ʊ�� 
	public static String BILLTYPE_LIQUIDATE = "36GK";//�������㵥
	public static String BILLTYPE_ILLEGAL = "36GP";//�Ƿ�Ʊ�ݵǼ�
	public static String BILLTYPE_ENDORE = "36GQ";//�������
	
	public static String BILLTYPE_RELIEF = "36GR";//�������ⵥ
	public static String BILLTYPE_RECKON_ALARM = "36GS";//������������
	
	public static String BILLTYPE_RECKON_RECIEPT = "36GT";//��������ص�(��Ա��λ)
	public static String BILLTYPE_DISCOUNT_CAL = "FBM01";//��������
	public static String BILLTYPE_ENDORECENTER = "36GW";//���ı���
	public static String BILLTYPE_ENDORECLEAE = "36GX";//���ڱ���������ʱ����ƾ֤
	
	public static String BILLTYPE_ACCRULE = "36GY"; //�ڲ��˻���������
	
	
	
	//�˻�����PK
	public static String ACCOUNT_TYPE_LOCALKEEP = "ACCOUNTTYPE000000001";//���ش�Ż�
	public static String ACCOUNT_TYPE_BANKKEEP = "ACCOUNTTYPE000000002";//���д�Ż�
	public static String ACCOUNT_TYPE_CENTER = "ACCOUNTTYPE000000003";//���Ĵ�Ż�
	public static String ACCOUNT_TYPE_RELIEF = "ACCOUNTTYPE000000004";//���ĵ�����
	public static String ACCOUNT_TYPE_IMPAWN_CENTER = "ACCOUNTTYPE000000005";//�ڲ���Ѻ��
	public static String ACCOUNT_TYPE_IMPAWN_BANK = "ACCOUNTTYPE000000006";//������Ѻ��
	public static String ACCOUNT_TYPE_YF = "ACCOUNTTYPE000000007";//Ӧ��������
	public static String ACCOUNT_TYPE_LIQUID = "ACCOUNTTYPE000000008";//���㻧
	
	//�˻����ԭ��
	public static String ACCOUNT_REASON_DISCOUNT_INNER = "discount_inner";//�ڲ�����
	public static String ACCOUNT_REASON_DISCOUNT_OUTER = "discount_outer";//�ⲿ����
	public static String ACCOUNT_REASON_COLLECTION = "collection";//����
	public static String ACCOUNT_REASON_ENDORE_INNER = "endore_inner";//�ڲ�����
	public static String ACCOUNT_REASON_ENDORE_OUTER = "endore_outer";//�ⲿ����
	public static String ACCOUNT_REASON_ENDORE_AGENT = "endore_agent";//���Ĵ�����
	public static String ACCOUNT_REASON_INVOICE = "invoice";//��Ʊ
	public static String ACCOUNT_REASON_IMPAWN = "impawn";//�ⲿ��Ѻ
	public static String ACCOUNT_REASON_IMPAWNBACK = "impawn_back";//�ⲿ��Ѻ����
	public static String ACCOUNT_REASON_GATHER = "gather";//��Ʊ
	public static String ACCOUNT_REASON_BANKKEEP = "bankkeep";//���б��ܴ��
	public static String ACCOUNT_REASON_CENTERKEEP_SAVE = "centerkeep_save";//�ڲ����ܱ���
	public static String ACCOUNT_REASON_CENTERKEEP_RELIEF = "centerkeep_relief";//�ڲ���������
	public static String ACCOUNT_REASON_CENTERBACK = "centerback";//��������
	public static String ACCOUNT_REASON_BANKBACK = "bankback";//��������
	public static String ACCOUNT_REASON_RELIEF_USE = "relife_use";//����ʹ��
	public static String ACCOUNT_REASON_DISCOUNT_DISABLE = "discount_disable";//��������
	public static String ACCOUNT_REASON_COLLECTION_DISABLE = "collection_disable";//��������
	public static String ACCOUNT_REASON_RETURN_GATHER = "return_gather";//��Ʊ��Ʊ
	public static String ACCOUNT_REASON_RETURN_ENDORE = "return_endore";//������Ʊ
	public static String ACCOUNT_REASON_RETURN_INVOICE = "return_invoice";//��Ʊ��Ʊ
	public static String ACCOUNT_REASON_RETURN_RELIEF = "return_relief";//������Ʊ
	public static String ACCOUNT_REASON_ACCEPT = "accept";//�ж�
	public static String ACCOUNT_REASON_CENTER_USE = "center_user";//����ʹ��
	
	//ҵ��������
	public static String BUSINESS_ACT_GATHER = "gather";//��Ʊ
	public static String BUSINESS_ACT_CANCELGATHER = "cancelgather";//ȡ����Ʊ
	
	//���ܽڵ��
	public static String FUNCODE_GATHER="36201005"; //��Ʊ
	public static String FUNCODE_STORAGE_BANKKEEP="36201010";//���д��
	public static String FUNCODE_STORAGE_BANKBACK="36201015";//��������

	public static String FUNCODE_STORAGE_CENTERKEEP="36201017";//���Ĵ��
	public static String FUNCODE_STORAGE_CENTERBACK="36201019";//��������

	public static String FUNCODE_CONSIGN="36201020";//��������ת��
	public static String FUNCODE_DISCOUNT_APP="36201025";//��������
	public static String FUNCODE_DISCOUNT_TRANSACT="36201030";//���ְ���
	public static String FUNCODE_DISCOUNT_CAL = "36201035";//��������	
	public static String FUNCODE_ENDORE = "36201037";//�������
	public static String FUNCODE_IMPAWN = "36201040";//Ʊ����Ѻ
	
	
	public static String FUNCODE_RELIEF_OUT = "36201045";//��������
	
	public static String FUNCODE_RELIEF = "36201050";//��������
	public static String FUNCODE_RELIEF_RECIEPT = "36201055";//�����ص�
	
	public static String FUNCODE_INVOICE = "36201505";//��Ʊ�Ǽ�
	public static String FUNCODE_BILLPAY = "36201510";//Ʊ�ݸ���
	public static String FUNCODE_RETURN = "362025";//��Ʊ
	
	public static String FUNCODE_ILLEGAL = "36200835";//�Ƿ�Ʊ�ݵǼ�
	public static String FUNCODE_FBMBILL_BOOK = "36209002";//Ʊ�ݱ��鲾
	
	//������ʽ
	public static String ASSURETYPE_CREDIT = "CREDIT";//����
	public static String ASSURETYPE_ASSURE = "ASSURE";//����
	public static String ASSURETYPE_PLEDGE = "PLEDGE";//��Ѻ
	public static String ASSURETYPE_IMPAWN = "IMPAWN";//��Ѻ
	public static String ASSURETYPE_BAIL = "BAIL";//��֤��
	
	public static final String CCTYPE_GROUP = "GROUP"; //��������
	public static final String CCTYPE_CORP = "CORP";//��˾����
	public static final String CCTYPE_NONE = "NONE";//��ʹ������
	
	//���ְ�����
	public static final String DISCOUNT_RESULT_SUCCESS = "success"; //�ɹ�
	public static final String DISCOUNT_RESULT_DISABLE = "disalbe";//����
		
	//�ⲿ����״̬
	public static String OUTERBILL_USE = "use";//ʹ����
	public static String OUTERBILL_OVER = "over";//ʹ����� 
	
	public static Integer SYSTEM_FBM = new Integer(8);//�ո�������Ʊ��ϵͳ��ʽ���ɵ��ݵ���Դ��־
	
	//�������㷽��
	public static String RELIEF_DIRECTION_IN = "reliefin";//����
	public static String RELIEF_DIRECTION_OUT = "reliefout";//���� 

	//Ʊ������
	public static String BILL_PRIVACY = "bill_privacy";//˽��
	public static String BILL_UNISTORAGE = "bill_unistor";//ͳ��
	
	public static String FBM_SYSCODE="FBM";//��Դϵͳ-Ʊ��
	public static String DAP_SYSCODE="DAP";//��Դϵͳ-���ƽ̨
	
	//����ʹ��
	public static String SYSCODE_ARAP = "ARAP";
	public static String SYSCODE_FBM = "FBM";
	
	//�ո���ʹ��
	public static String AR = "AR";
	public static String AP = "AP";
	public static String EP = "EP";
	
	//��֤��ȡ����֤���������ڱ��顢�������ա����ְ�����֤ʱ�ж�������
	public static String OP_VOUCHER = "voucher"; //��֤
	public static String OP_CANCELVOUCHER = "cancelvoucher";//ȡ����֤
	public static String OP_CLEAR = "clear";//����
	
	
	//����ϵͳ��Դ
	public static String ENDORE_SYS_INPUT = "INPUT";
	public static String ENDORE_SYS_CMP = "CMP";
	
	//�ڲ��˻���������
	public static String ACCRULE_ACCREF_BILL = "BILL"; //�ڲ�Ʊ�ݻ�
	public static String ACCRULE_ACCREF_CURRENT = "CURRENT"; //�ڲ����ڻ�
	public static String ACCRULE_NONE = "NONE";//������
	
	//�ڲ��˻����˽��
	public static String ACCRULE_ACCMNY_PMJE = "PMJE"; //Ʊ����
	public static String ACCRULE_ACCMNY_YE = "YE"; //Ʊ����-����Ϣ
	
	//�ڲ��˻���������---��������
	public static String LIQUIDATE = "36GK"; //��������
	public static String RELIEF = "36GR"; //Ʊ�ݵ���
	public static String INNERKEEP = "36GD"; //�ڲ��й�
	public static String INNERBACK = "36GE"; //�ڲ�����
	public static String BILLPAY = "36GM"; //Ʊ�ݸ���
	public static String INVOICE = "36GL"; //��Ʊ�Ǽ�
	
	
	//�ڲ��˻���������---�����˻�����
	public static String ACC_IN = "IN"; //ת���˻�
	public static String ACC_OUT = "OUT"; //ת���˻�
	public static String ACC_INNER = "INNER"; //�ڲ��˻�
	
}
