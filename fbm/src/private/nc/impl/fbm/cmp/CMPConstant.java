package nc.impl.fbm.cmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.fbm.out.AddPushBillValidator;
import nc.bs.fbm.out.ApAddBillValidator;
import nc.bs.fbm.out.ApDelBillValidator;
import nc.bs.fbm.out.ApEffectBillValidator;
import nc.bs.fbm.out.ApUneffectBillValidator;
import nc.bs.fbm.out.ArAddBillValidator;
import nc.bs.fbm.out.ArDelBillValidator;
import nc.bs.fbm.out.ArEffectBillValidator;
import nc.bs.fbm.out.ArUneffectBillValidator;
import nc.bs.fbm.out.ArapBillDataAdapter;
import nc.bs.fbm.out.DelPushBillValidator;
import nc.bs.fbm.out.EffectPushBillValidator;
import nc.bs.fbm.out.ReturnBillAdd;
import nc.bs.fbm.out.ReturnBillDel;
import nc.bs.fbm.out.ReturnBillEffect;
import nc.bs.fbm.out.ReturnBillUnEffect;
import nc.bs.fbm.out.UnEffectPushBillValidator;
/**
 * 现金平台接口需要的常量类
 * 
 * @author wues
 */

public class CMPConstant {

	/**
	 * 常量map;key=Action + BillType;value=IBillValidator.class.getName()
	 */
	private static Map<String, String> VALIDATIONMAP = new HashMap<String, String>();

	
	//支持老版数据升级处理定义的老版常量类
	public static String BILLTYPE_SK_OLD = "sk";
	public static String BILLTYPE_FK_OLD = "fk";
	public static String BILLTYPE_SJ_OLD = "sj";
	public static String BILLTYPE_FJ_OLD = "fj";
	public static String BILLTYPE_HJ_OLD = "hj";
	
	/**
	 * 退票单
	 */
	public static String BILLTYPE_RETURN = "returnbill";

	/**
	 * 从票据系统中推式生成的单据
	 */
	public static String BILLTYPE_FROM_FBM = "FBM";

	/**
	 * 收款单类型
	 */
	public static String BILLTYPE_SK = "F2";

	/**
	 * 付款单类型
	 */
	public static String BILLTYPE_FK = "F3";

	/**
	 * 收款结算单类型
	 */
	public static String BILLTYPE_SJ = "F4";

	/**
	 * 付款结算单
	 */
	public static String BILLTYPE_FJ = "F5";

	/**
	 * 应收单
	 */
	public static String BILLTYPE_YS = "F0";

	/**
	 * 应付单
	 */
	public static String BILLTYPE_YF = "F1";

	/**
	 * 划账结算单
	 */
	public static String BILLTYPE_HJ = "F6";

	/**
	 * 退票单
	 */
	public static String RETURN_DEL = ArapBillDataAdapter.ACTION_DEL + "_"
			+ BILLTYPE_RETURN;
	public static String RETURN_ADD = ArapBillDataAdapter.ACTION_ADD + "_"
			+ BILLTYPE_RETURN;
	public static String RETURN_EFFECT = ArapBillDataAdapter.ACTION_EFFECT
			+ "_" + BILLTYPE_RETURN;
	public static String RETURN_UNEFFECT = ArapBillDataAdapter.ACTION_UNEFFECT
			+ "_" + BILLTYPE_RETURN;

	/**
	 * 收款单
	 */
	public static String SK_DEL = ArapBillDataAdapter.ACTION_DEL + "_"
			+ BILLTYPE_SK;
	public static String SK_ADD = ArapBillDataAdapter.ACTION_ADD + "_"
			+ BILLTYPE_SK;
	public static String SK_EFFECT = ArapBillDataAdapter.ACTION_EFFECT + "_"
			+ BILLTYPE_SK;
	public static String SK_UNEFFECT = ArapBillDataAdapter.ACTION_UNEFFECT
			+ "_" + BILLTYPE_SK;

	/**
	 * 收款结算单
	 */
	public static String SJ_DEL = ArapBillDataAdapter.ACTION_DEL + "_"
			+ BILLTYPE_SJ;
	public static String SJ_ADD = ArapBillDataAdapter.ACTION_ADD + "_"
			+ BILLTYPE_SJ;
	public static String SJ_EFFECT = ArapBillDataAdapter.ACTION_EFFECT + "_"
			+ BILLTYPE_SJ;
	public static String SJ_UNEFFECT = ArapBillDataAdapter.ACTION_UNEFFECT
			+ "_" + BILLTYPE_SJ;

	/**
	 * 付款单
	 */
	public static String FK_DEL = ArapBillDataAdapter.ACTION_DEL + "_"
			+ BILLTYPE_FK;
	public static String FK_ADD = ArapBillDataAdapter.ACTION_ADD + "_"
			+ BILLTYPE_FK;
	public static String FK_EFFECT = ArapBillDataAdapter.ACTION_EFFECT + "_"
			+ BILLTYPE_FK;
	public static String FK_UNEFFECT = ArapBillDataAdapter.ACTION_UNEFFECT
			+ "_" + BILLTYPE_FK;

	/**
	 * 付款结算单
	 */
	public static String FJ_DEL = CMPBillDataAdapter.ACTION_DEL + "_"
			+ BILLTYPE_FJ;
	public static String FJ_ADD = CMPBillDataAdapter.ACTION_ADD + "_"
			+ BILLTYPE_FJ;
	public static String FJ_EFFECT = CMPBillDataAdapter.ACTION_EFFECT + "_"
			+ BILLTYPE_FJ;
	public static String FJ_UNEFFECT = CMPBillDataAdapter.ACTION_UNEFFECT
			+ "_" + BILLTYPE_FJ;

	/**
	 * 处理由票据系统推式生成的单据
	 */
	public static String FBM_DEL = CMPBillDataAdapter.ACTION_DEL + "_"
			+ BILLTYPE_FROM_FBM;
	public static String FBM_ADD = CMPBillDataAdapter.ACTION_ADD + "_"
			+ BILLTYPE_FROM_FBM;
	public static String FBM_EFFECT = CMPBillDataAdapter.ACTION_EFFECT + "_"
			+ BILLTYPE_FROM_FBM;
	public static String FBM_UNEFFECT = CMPBillDataAdapter.ACTION_UNEFFECT
			+ "_" + BILLTYPE_FROM_FBM;

	/**
	 * 静态初始化块，将校验Map初始化
	 */
	static {
		// 处理由票据推式生成的单据
		VALIDATIONMAP.put(FBM_DEL, DelPushBillValidator.class.getName());
		VALIDATIONMAP.put(FBM_ADD, AddPushBillValidator.class.getName());
		VALIDATIONMAP.put(FBM_EFFECT, EffectPushBillValidator.class.getName());
		VALIDATIONMAP.put(FBM_UNEFFECT, UnEffectPushBillValidator.class
				.getName());

		// 处理退票
		VALIDATIONMAP.put(RETURN_DEL, ReturnBillDel.class.getName());
		VALIDATIONMAP.put(RETURN_ADD, ReturnBillAdd.class.getName());
		VALIDATIONMAP.put(RETURN_EFFECT, ReturnBillEffect.class.getName());
		VALIDATIONMAP.put(RETURN_UNEFFECT, ReturnBillUnEffect.class.getName());

		// 处理收款单
		VALIDATIONMAP.put(SK_DEL, ArDelBillValidator.class.getName());
		VALIDATIONMAP.put(SK_ADD, ArAddBillValidator.class.getName());
		VALIDATIONMAP.put(SK_EFFECT, ArEffectBillValidator.class.getName());
		VALIDATIONMAP.put(SK_UNEFFECT, ArUneffectBillValidator.class.getName());

		// 收款结算单
		VALIDATIONMAP.put(SJ_DEL, ArDelBillValidator.class.getName());
		VALIDATIONMAP.put(SJ_ADD, ArAddBillValidator.class.getName());
		VALIDATIONMAP.put(SJ_EFFECT, ArEffectBillValidator.class.getName());
		VALIDATIONMAP.put(SJ_UNEFFECT, ArUneffectBillValidator.class.getName());

		// 处理付款单
		VALIDATIONMAP.put(FK_DEL, ApDelBillValidator.class.getName());
		VALIDATIONMAP.put(FK_ADD, ApAddBillValidator.class.getName());
		VALIDATIONMAP.put(FK_EFFECT, ApEffectBillValidator.class.getName());
		VALIDATIONMAP.put(FK_UNEFFECT, ApUneffectBillValidator.class.getName());

		// 处理付款结算单
		VALIDATIONMAP.put(FJ_DEL, ApDelBillValidator.class.getName());
		VALIDATIONMAP.put(FJ_ADD, ApAddBillValidator.class.getName());
		VALIDATIONMAP.put(FJ_EFFECT, ApEffectBillValidator.class.getName());
		VALIDATIONMAP.put(FJ_UNEFFECT, ApUneffectBillValidator.class.getName());
		
	}

	public static Map<String, String> getMap() {
		return VALIDATIONMAP;
	}
	
	/**
	 * 取所有需要处理的单据类型：sj，fj，sk，fk
	 * @return
	 */
	public static List<String> getDealBillType() {
		List<String> list = new ArrayList<String>();
		list.add(BILLTYPE_FJ);
		list.add(BILLTYPE_FK);
		list.add(BILLTYPE_SJ);
		list.add(BILLTYPE_SK);
		return list;
		
	}

}
