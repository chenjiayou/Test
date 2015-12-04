package nc.vo.fbm.pub.constant;

/**
 * 
 * <p>
 *   单据业务动作常量
 * </p>
 * @author xwq
 * @date 2007-8-9
 * @version V5.0
 */
public class FbmActionConstant {
//	public static String GATHER_SAVE = "gather_save";//收票登记单新增保存
//	public static String GATHER_AUDIT = "gather_audit";//收票登记单审核
//	public static String GATHER_CANCELAUDIT = "gather_cancleaudit";//收票登记单弃审
//	
//	public static String DISCOUNT_APP_SAVE = "discount_app_save";//贴现申请单保存
//	public static String DISCOUNT_APP_AUDIT = "discount_app_audit";//贴现申请单审核
//	public static String DISCOUNT_APP_CANCELAUDIT = "discount_app_cancelaudit";//贴现申请单弃审
//	public static String DISCOUNT_BACK_SAVE = "discount_back_save";//贴现回单保存
//	public static String DISCOUNT_BACK_AUDIT = "discount_back_audit";//贴现回单审核
//	public static String DISCOUNT_BACK_CANCELAUDIT = "discount_back_cancelaudit";//贴现保存弃审
//	public static String DISCOUNT_TRANSACT_SAVE = "discount_transact_save";//贴现办理单保存
//	public static String DISCOUNT_TRANSACT_AUDIT = "discount_transact_audit";//贴现办理单审核
//	public static String DISCOUNT_TRANSACT_CANCELAUDIT = "discount_transact_cancelaudit";//贴现办理单弃审
//	
//	public static String COLLECTION_SAVE = "collection_save";//托收办理单保存
//	public static String COLLECTION_AUDIT = "collection_audit";//托收办理单审核
//	public static String COLLECTION_CANCELAUDIT = "collection_cancelaudit";//托收办理单审核
//	
//	public static String IMPAWN_SAVE = "impawn_save";//质押单保存
//	public static String IMPAWN_AUDIT = "impawn_audit";//质押单审核
//	public static String IMPAWN_CANCELAUDIT = "impawn_cancelaudit";//质押单弃审
//	public static String IMPAWN_BACK = "impawn_back";//质押回收
	
	public static String SAVE = "SAVE";//新增保存
	public static String DELETE = "DELETE";//删除
	public static String AUDIT = "AUDIT";//审核
	public static String CANCELAUDIT = "CANCELAUDIT";//弃审
	public static String EDITSAVE = "EDITSAVE";//修改保存
	public static String ONAUDIT = "ONAUDIT";//审核後状态为审核进行中
	public static String TRANSACT = "TRANSACT";//办理
	public static String CANCELTRANSACT = "CANCELTRANSACT";//取消办理
	public static String DISABLE = "DISABLE";//作废

	public static String IMPAWNBACK = "IMPAWNBACK";//质押回收
	public static String CANCELIMPAWNBACK = "CANCELIMPAWNBACK";//取消质押回收
	public static String INPUT_SUCCESS = "INPUT";//入库
	public static String OUTPUT_SUCCESS = "OUTPUT";//出库
	public static String CANCELINPUT="CANCELIN";//取消入库
	public static String CANCELOUTPUT="CANCELOUT";//取消出库
	//added by wes
	public static String VOUCHER = "VOUCHER";//制证
	public static String CANCELVOUCHER = "CANCELVOUCHER";//取消制证
	
	public static String CANCELDESTROY="CANCELDESTROY";//取消核销
	public static String DESTROY="DESTROY";//核销
	public static String CENTERUSE = "CENTERUSE";//中心使用
	public static String CANCELCENTERUSER = "CANCELCENTERUSER";//取消中心使用

	
}
