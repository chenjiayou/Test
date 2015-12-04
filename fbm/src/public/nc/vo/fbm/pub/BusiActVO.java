package nc.vo.fbm.pub;

import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.SuperVO;

/**
 * 升级业务数据中间类
 * @author xwq
 *
 */
public class BusiActVO {
	
	public static int BASEINFO = 0;//基本信息
    
	public static int ACCEPT = 1;//承兑
    public static int COLLECTION = 2;//托收
    public static int DISCOUNT = 3;//贴现
    public static int GATHER = 4;//收票
    public static int IMPAWN = 5;//质押
    public static int INVOICE = 6;//开票
    public static int RETURNBILL = 7;//退票
    public static int ENDORE = 8;//背书
    public static int IMPAWNBACK = 9;//质押回收
    
    
    public static int INNER_KEEP = 10;//内部存放
    
    int busitype;
    SuperVO vo;
    OuterVO[] outerVO;
    
	public int getBusitype() {
		return busitype;
	}
	public void setBusitype(int busitype) {
		this.busitype = busitype;
	}
	public SuperVO getVo() {
		return vo;
	}
	public void setVo(SuperVO vo) {
		this.vo = vo;
	}
	
	
	
	public OuterVO[] getOuterVO() {
		return outerVO;
	}
	public void setOuterVO(OuterVO[] outerVO) {
		this.outerVO = outerVO;
	}
	public static String retriveBillType(int i){
		if(i == 0){
			return null;
		}else if(i == 1){
			return FbmBusConstant.BILLTYPE_BILLPAY;
		}else if(i == 2){
			return FbmBusConstant.BILLTYPE_COLLECTION_UNIT;
		}else if(i == 3){
			return FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT;
		}else if(i == 4){
			return FbmBusConstant.BILLTYPE_GATHER;
		}else if(i == 5){
			return FbmBusConstant.BILLTYPE_IMPAWN;
		}else if(i == 6){
			return FbmBusConstant.BILLTYPE_INVOICE;
		}else if(i == 7){
			return FbmBusConstant.BILLTYPE_RETURN;
		}else if(i == 8){
			return FbmBusConstant.BILLTYPE_ENDORE;
		}else if(i==9){
			return FbmBusConstant.BILLTYPE_IMPAWN;
		}else if(i==10){
			return FbmBusConstant.BILLTYPE_INNERKEEP;
		}
		return null;
		
	}
}
