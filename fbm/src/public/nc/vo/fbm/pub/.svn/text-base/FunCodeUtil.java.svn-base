/**
 *
 */
package nc.vo.fbm.pub;

import nc.vo.cc.control.ICcBusType;
import nc.vo.fbm.pub.constant.FbmBusConstant;

/**
 * <p>
 * 票据产品节点号工具类
 * <p>
 * 创建人：lpf 
 * <b>日期：2007-8-30
 * 
 */
public class FunCodeUtil {

	/**
	 * 
	 */
	public FunCodeUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * 根据billtype查找节点号
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-9
	 * @param billtype
	 * @return
	 */
	public static String getFuncodeByBillType(String billtype) {
		String funcode = "";
		if (billtype.equals(FbmBusConstant.BILLTYPE_BANKKEEP))
			funcode = FbmBusConstant.FUNCODE_STORAGE_BANKKEEP;
		else if (billtype.equals(FbmBusConstant.BILLTYPE_BANKBACK))
			funcode = FbmBusConstant.FUNCODE_STORAGE_BANKBACK;
		else if (billtype.equals(FbmBusConstant.BILLTYPE_GATHER))
			funcode = FbmBusConstant.FUNCODE_GATHER;
		else if(billtype.equals(FbmBusConstant.BILLTYPE_RETURN))
			funcode = FbmBusConstant.FUNCODE_RETURN;
		else if(billtype.equals(FbmBusConstant.BILLTYPE_INVOICE)){
			funcode = FbmBusConstant.FUNCODE_INVOICE;
		}else if(billtype.equals(FbmBusConstant.BILLTYPE_BILLPAY)){
			funcode = FbmBusConstant.FUNCODE_BILLPAY;
		}else if(billtype.equals(FbmBusConstant.BILLTYPE_DISCOUNT_APP)){
			funcode = FbmBusConstant.FUNCODE_DISCOUNT_APP;
		}else if(billtype.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)){
			funcode = FbmBusConstant.FUNCODE_CONSIGN;
		}else if(billtype.equals(FbmBusConstant.BILLTYPE_IMPAWN)){
			funcode = FbmBusConstant.FUNCODE_IMPAWN;
		} else if(FbmBusConstant.BILLTYPE_INNERBACK.equals(billtype)){//内部领用
			funcode = FbmBusConstant.FUNCODE_STORAGE_CENTERBACK;
		}
		
		return funcode;
	}
	
	/**
	 * 
	 * <p>
	 * 为CC提供的节点号查询方法
	 * <p>
	 * 作者：lpf
	 * 日期：2007-11-7
	 * @param busType
	 * @return
	 */
	public static String getFuncodeForCC(String busType){
		String funcode = "";
		if(busType.equals(ICcBusType.FBM_BILLTYPE_INVOICE)){
			funcode = FbmBusConstant.FUNCODE_INVOICE;
		}
		else if(busType.equals(ICcBusType.FBM_BILLTYPE_ACCEPT)){
			funcode = FbmBusConstant.FUNCODE_BILLPAY;
		}
		return funcode;		
	}
}
