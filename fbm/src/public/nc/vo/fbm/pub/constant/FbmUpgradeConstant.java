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
 * 类功能说明：
     票据升级定义的常量类
 * 日期：2007-11-22
 * 程序员： wues
 *
 */
public class FbmUpgradeConstant {
	//业务类型
	public static int BASEINFO = 0;//基本信息
    public static int ACCEPT = 1;//承兑
    public static int COLLECTION = 2;//托收
    public static int DISCOUNT = 3;//贴现
    public static int GATHER = 4;//收票
    public static int IMPAWN = 5;//质押
    public static int INVOICE = 6;//开票
    public static int RETURNBILL = 7;//退票
    public static int ENDORE = 8;//背书

    //业务类型对应的中文名
    private static String baseInfoName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000378")/* @res"基本信息"*/;
    private static String acceptName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000379")/* @res"承兑"*/;
    private static String collectionName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000380")/* @res"托收"*/;
    private static String discountName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000381")/* @res"贴现"*/;
    private static String gatherName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000382")/* @res"收票"*/;
    private static String impawnName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000020")/* @res"质押"*/;
    private static String invoiceName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000383")/* @res"开票"*/;
    private static String returnBillName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000021")/* @res"退票"*/;
    private static String endoreName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000384")/* @res"背书"*/;


    //存放所有的Key-Value, 常量保留一份
    private static Map<Integer,String>  nameMap =  null;
    //存放所有的类对应关系
    private static Map<Integer,String> classMap = null;

    static {
    	//填充中文名
    	fillNameMap();
    	//填充类名
    	fillClassMap();
    }

    /**
     * 填充VO类名
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
     * 填充具体业务处理的中文名
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
     * 取得具体业务的中文名称
     * 先加载静态初始化块初始化nameMap完成
     */
    public static String getZnName(int busiType){
    	return null == nameMap ? null : nameMap.get(new Integer(busiType));
    }
    /**
     * 根据类型业务类型取得相应的类名
     * @param busiType
     * @return
     */
    public static  String getClassName(int busiType) {
    	return null == classMap ? null : classMap.get(new Integer(busiType));
    }
}