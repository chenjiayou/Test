package nc.vo.fbm.pub.constant;

import java.util.HashMap;
import java.util.Map;

import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.ReturnMiddleVO;
import nc.vo.fbm.register.RegisterVO;


/**
 *
 * �๦��˵����
     Ʊ����������ĳ�����
 * ���ڣ�2007-11-22
 * ����Ա�� wues
 *
 */
public class FbmUpgradeConstant {
	//ҵ������
	public static int BASEINFO = 0;//������Ϣ
    public static int ACCEPT = 1;//�ж�
    public static int COLLECTION = 2;//����
    public static int DISCOUNT = 3;//����
    public static int GATHER = 4;//��Ʊ
    public static int IMPAWN = 5;//��Ѻ
    public static int INVOICE = 6;//��Ʊ
    public static int RETURNBILL = 7;//��Ʊ
    public static int ENDORE = 8;//����

    //ҵ�����Ͷ�Ӧ��������
    private static String baseInfoName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000378")/* @res"������Ϣ"*/;
    private static String acceptName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000379")/* @res"�ж�"*/;
    private static String collectionName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000380")/* @res"����"*/;
    private static String discountName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000381")/* @res"����"*/;
    private static String gatherName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000382")/* @res"��Ʊ"*/;
    private static String impawnName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000020")/* @res"��Ѻ"*/;
    private static String invoiceName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000383")/* @res"��Ʊ"*/;
    private static String returnBillName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000021")/* @res"��Ʊ"*/;
    private static String endoreName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000384")/* @res"����"*/;


    //������е�Key-Value, ��������һ��
    private static Map<Integer,String>  nameMap =  null;
    //������е����Ӧ��ϵ
    private static Map<Integer,String> classMap = null;

    static {
    	//���������
    	fillNameMap();
    	//�������
    	fillClassMap();
    }

    /**
     * ���VO����
     */
    private static void fillClassMap() {
    	if (null == classMap) {
    		classMap = new HashMap<Integer, String>();
    	}
    	classMap.put(new Integer(BASEINFO), BaseinfoVO.class.getName());
    	classMap.put(new Integer(ACCEPT), AcceptVO.class.getName());
    	classMap.put(new Integer(COLLECTION), CollectionVO.class.getName());
    	classMap.put(new Integer(DISCOUNT), DiscountVO.class.getName());
    	classMap.put(new Integer(GATHER), RegisterVO.class.getName());
    	classMap.put(new Integer(IMPAWN), ImpawnVO.class.getName());
    	classMap.put(new Integer(INVOICE), RegisterVO.class.getName());
    	classMap.put(new Integer(RETURNBILL), ReturnMiddleVO.class.getName());
    	classMap.put(new Integer(ENDORE), EndoreVO.class.getName());
    }

    /**
     * ������ҵ�����������
     */
    private static void fillNameMap(){
    	if (null == nameMap){
    		nameMap = new HashMap<Integer, String>();
    	}
    	nameMap.put(new Integer(BASEINFO), baseInfoName);
    	nameMap.put(new Integer(ACCEPT), acceptName);
    	nameMap.put(new Integer(COLLECTION), collectionName);
    	nameMap.put(new Integer(DISCOUNT), discountName);
    	nameMap.put(new Integer(GATHER), gatherName);
    	nameMap.put(new Integer(IMPAWN), impawnName);
    	nameMap.put(new Integer(INVOICE), invoiceName);
    	nameMap.put(new Integer(RETURNBILL), returnBillName);
    	nameMap.put(new Integer(ENDORE), endoreName);
    }

    /**
     * ȡ�þ���ҵ�����������
     * �ȼ��ؾ�̬��ʼ�����ʼ��nameMap���
     */
    public static String getZnName(int busiType){
    	return null == nameMap ? null : nameMap.get(new Integer(busiType));
    }
    /**
     * ��������ҵ������ȡ����Ӧ������
     * @param busiType
     * @return
     */
    public static  String getClassName(int busiType) {
    	return null == classMap ? null : classMap.get(new Integer(busiType));
    }
}