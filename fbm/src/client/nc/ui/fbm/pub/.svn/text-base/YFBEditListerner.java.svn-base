/**
 *
 */
package nc.ui.fbm.pub;

import java.util.HashMap;

import nc.itf.cdm.util.CommonUtil;
import nc.itf.cdm.util.MathUtil;
import nc.ui.bill.tools.ColorConstants;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.IBillItem;
import nc.ui.tm.framework.billtemplet.BillCardPanelTools;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.tm.framework.util.CurrencyClientUtil;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * 原副本自动折算类
 * <p>
 * 创建日期：2007-6-26
 * 
 * @author lisg
 * @since v5.1
 */
public class YFBEditListerner extends AbstractItemEditListener {
	private HashMap<String, String> itemkeyMap = null;
	private HashMap<String, String> reverseItemkeyMap = null;
	private CurrencyClientUtil currencyClientUtil = null;
	
	public static String ITEMKEY = "ITEMKEY";
	public static String CY_ITEMKEY = ITEMKEY+"0";//币种
	public static String YB_ITEMKEY = ITEMKEY+"1";//原币
	public static String FB_ITEMKEY = ITEMKEY+"2";//辅币
	public static String BB_ITEMKEY = ITEMKEY+"3";//本币
	public static String ZF_ITEMKEY = ITEMKEY+"4";//折辅汇率
	public static String ZB_ITEMKEY = ITEMKEY+"5";//折本汇率
	public static String FR_ITEMKEY = ITEMKEY+"6";//是否固定汇率
	public static String ZD_ITEMKEY = ITEMKEY+"7";//换算日期
	public static String CY_SPECIALKEY = ITEMKEY+"8";//特殊币种，只做是否可编辑设置并不修改汇率值,用于界面编辑时触发本辅币是否可编辑使用
	
	private String[] moneyKeys = null;//币种、原币、辅币、本币、折辅汇率、折本汇率、是否固定汇率、换算日期、特殊币种
	

	/**
	 * 
	 */
	public YFBEditListerner() {
	}

	
	public YFBEditListerner(FBMManageUI ui,String itemKey,String...args) {
		super(ui, itemKey, IBillItem.HEAD);
		moneyKeys = args;
		
		for(int i=0; i < CommonUtil.getArrayLength(moneyKeys); i++){
			if(!CommonUtil.isNull(moneyKeys[i])){
				getItemkeyMap().put(moneyKeys[i], ITEMKEY+i);
				getReverseItemkeyMap().put(ITEMKEY+i, moneyKeys[i]);
			}
		}
	}


	/**
	 * @param ui
	 * @param itemKey
	 */
	public YFBEditListerner(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public YFBEditListerner(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		try{
			String pk_currency = (String)getBillItemValue(getReverseItemkeyMap().get(CY_ITEMKEY));
			getCurrencyClientUtil().setPk_currtype_y(pk_currency);
			if(pk_currency == null){
				return;
			}
			
			if(editEvent.getKey().equals(getReverseItemkeyMap().get(CY_ITEMKEY))||editEvent.getKey().equals(getReverseItemkeyMap().get(CY_SPECIALKEY))){
				String pk_loacalcurrency = getCurrencyClientUtil().getLocalCurrPK();
				String pk_fraccurrency = getCurrencyClientUtil().getFracCurrPK();
				boolean isLocalFracional = getCurrencyClientUtil().isLocalFractional();
				if(isLocalFracional){
					setBillItemEnable(getReverseItemkeyMap().get(FB_ITEMKEY), false);
					setBillItemEnable(getReverseItemkeyMap().get(BB_ITEMKEY), false);
					//本副币折算
					if(pk_currency.equals(pk_loacalcurrency)){
						//原币 = 本币
						setBillItemEnable(getReverseItemkeyMap().get(ZF_ITEMKEY), false);
						setBillItemEnable(getReverseItemkeyMap().get(ZB_ITEMKEY), false);
					}else if(pk_currency.equals(pk_fraccurrency)){
						//原币 = 辅币
						setBillItemEnable(getReverseItemkeyMap().get(ZF_ITEMKEY), false);
						setBillItemEnable(getReverseItemkeyMap().get(ZB_ITEMKEY), true);
					}else{
						setBillItemEnable(getReverseItemkeyMap().get(ZF_ITEMKEY), true);
						setBillItemEnable(getReverseItemkeyMap().get(ZB_ITEMKEY), true);
					}
				}else{
					//单主币核算
					setBillItemEnable(getReverseItemkeyMap().get(ZF_ITEMKEY), false);
					setBillItemEnable(getReverseItemkeyMap().get(FB_ITEMKEY), false);
					setBillItemEnable(getReverseItemkeyMap().get(BB_ITEMKEY), false);
					
					if(pk_currency.equals(pk_loacalcurrency)){
						setBillItemEnable(getReverseItemkeyMap().get(ZB_ITEMKEY), false);
					}else{
						setBillItemEnable(getReverseItemkeyMap().get(ZB_ITEMKEY), true);
					}
				}
			}
			
			UFDate exchangeDate = null;
			exchangeDate = (UFDate)getBillItemValue(getReverseItemkeyMap().get(ZD_ITEMKEY));
			if(exchangeDate == null){
				exchangeDate = ClientInfo.getCurrentDate();
			}
			
			//获取汇率精度
			int[] exchangeRateDigit = getCurrencyClientUtil().getExchangeRateDigit(pk_currency);
			
//			UFBoolean fixrate = (UFBoolean)getBillItemValue(getReverseItemkeyMap().get(FR_ITEMKEY));
//			if(fixrate == null){
//				if (getReverseItemkeyMap().containsKey(ZF_ITEMKEY)
//						&& getReverseItemkeyMap().containsKey(ZB_ITEMKEY)
//						&& (getItemKey().equals(getReverseItemkeyMap().get(ZB_ITEMKEY)) 
//						|| getItemKey().equals(getReverseItemkeyMap().get(ZF_ITEMKEY)))) {
//					fixrate = UFBoolean.TRUE;
//				} else {
//					fixrate = UFBoolean.FALSE;
//				}
//				fixrate = UFBoolean.FALSE;
//			}
			
			//获取汇率
			UFDouble[] exchangeRate = new UFDouble[2];
			if (((getBillItemValue(getReverseItemkeyMap().get(ZF_ITEMKEY))!=null
					&& getBillItemValue(getReverseItemkeyMap().get(ZB_ITEMKEY))!=null
					&& getReverseItemkeyMap().containsKey(ZF_ITEMKEY)
					&& getReverseItemkeyMap().containsKey(ZB_ITEMKEY)) 
					|| editEvent.getKey().equals(getReverseItemkeyMap().get(ZF_ITEMKEY))
					|| editEvent.getKey().equals(getReverseItemkeyMap().get(ZB_ITEMKEY))) 
					&& !editEvent.getKey().equals(getReverseItemkeyMap().get(CY_ITEMKEY))
					||editEvent.getKey().equals(getReverseItemkeyMap().get(CY_SPECIALKEY))) {
				UFDouble rateF = (UFDouble)getBillItemValue(getReverseItemkeyMap().get(ZF_ITEMKEY));
				UFDouble rateB = (UFDouble)getBillItemValue(getReverseItemkeyMap().get(ZB_ITEMKEY));

				exchangeRate[0] = MathUtil.getUFDouble(rateF);
				exchangeRate[1] = MathUtil.getUFDouble(rateB);
			} else {
				exchangeRate = getCurrencyClientUtil().getExchangeRate(exchangeDate.toString());
			}
			
			//设置汇率方便后面的计算
			getCurrencyClientUtil().setExchangeRate(exchangeRate);
			
			//设置折辅币和折本币的汇率
			if(getReverseItemkeyMap().containsKey(ZF_ITEMKEY)){
				getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(ZF_ITEMKEY)).setDecimalDigits(exchangeRateDigit[0]);
				getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(ZF_ITEMKEY)).setValue(exchangeRate[0]);
			}
			if(getReverseItemkeyMap().containsKey(ZB_ITEMKEY)){
				getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(ZB_ITEMKEY)).setDecimalDigits(exchangeRateDigit[1]);
				getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(ZB_ITEMKEY)).setValue(exchangeRate[1]);
			}
			
			//获取币种精度
			int[] currencyDigit = new int[3];
			currencyDigit[0] = getCurrencyClientUtil().getYDecimalDigit();
			if(getReverseItemkeyMap().containsKey(YB_ITEMKEY)){	
				getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(YB_ITEMKEY)).setDecimalDigits(currencyDigit[0]);
				
			}
			
			UFDouble moneyy = (UFDouble)getBillItemValue(getReverseItemkeyMap().get(YB_ITEMKEY));
			if(moneyy!=null){
				//根据汇率计算原副本
				UFDouble[] yfb = getCurrencyClientUtil().getYfbMoney(moneyy);
				
				if(getReverseItemkeyMap().containsKey(YB_ITEMKEY)){
					getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(YB_ITEMKEY)).setDecimalDigits(yfb[0].getPower());
					getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(YB_ITEMKEY)).setValue(yfb[0]);
				}
				
				if(getReverseItemkeyMap().containsKey(FB_ITEMKEY)){
					getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(FB_ITEMKEY)).setDecimalDigits(yfb[1].getPower());
					getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(FB_ITEMKEY)).setValue(yfb[1]);
				}
				
				if(getReverseItemkeyMap().containsKey(BB_ITEMKEY)){
					getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(BB_ITEMKEY)).setDecimalDigits(yfb[2].getPower());
					getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(BB_ITEMKEY)).setValue(yfb[2]);
				}
			}else{
				if(getReverseItemkeyMap().containsKey(YB_ITEMKEY)){
					getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(YB_ITEMKEY)).setValue(null);
				}
				if(getReverseItemkeyMap().containsKey(FB_ITEMKEY)){
					getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(FB_ITEMKEY)).setValue(null);
				}
				if(getReverseItemkeyMap().containsKey(BB_ITEMKEY)){
					getUI().getBillCardPanel().getHeadItem(getReverseItemkeyMap().get(BB_ITEMKEY)).setValue(null);
				}
				
			}
		}catch(BusinessException e){
			getUI().showErrorMessage(e.getMessage());
		}

	}

	private void setBillItemEnable(String itemKey, boolean enable){
		if(itemKey!=null){
			BillItem bi = getUI().getBillCardPanel().getHeadItem(itemKey);
			if(bi!=null){
				bi.setEnabled(enable);
				bi.setNull(enable);
				if(enable){
					bi.setForeground(ColorConstants.COLOR_DARKBLUE);
				}else{
					bi.setForeground(ColorConstants.COLOR_BLACK);
				}
				BillCardPanelTools.updateBillItemColor(bi);
			}
		}
	}
	
	private Object getBillItemValue(String itemKey){
		if(itemKey!=null){
			String valueObject = null;
			BillItem bi = getUI().getBillCardPanel().getHeadItem(itemKey);
			if(getUI().getRefTakenProccessor()!=null){
				valueObject = (String)getUI().getRefTakenProccessor().getValueByTakenInCard(itemKey);
			}else{				
				valueObject = (String)bi.getValueObject();
			}
			if(bi!=null){
				switch (bi.getDataType()) {
					case BillItem.BOOLEAN: {
						return new UFBoolean(valueObject);
					}
					case BillItem.DECIMAL:{
						if(!CommonUtil.isNull(valueObject)){
							return new UFDouble(Double.parseDouble(valueObject.toString()), bi.getDecimalDigits());
						}
						break;
					}
					case BillItem.DATE: {
						if (!CommonUtil.isNull(valueObject)) {
							return new UFDate(valueObject);
						}
						break;
					}
					default:
						return valueObject;
				}
			}
		}
		
		return null;
	}

	private HashMap<String, String> getItemkeyMap() {
		if(itemkeyMap == null){
			itemkeyMap = new HashMap<String, String>();
		}
		return itemkeyMap;
	}

	private HashMap<String, String> getReverseItemkeyMap() {
		if(reverseItemkeyMap == null){
			reverseItemkeyMap = new HashMap<String, String>();
		}
		return reverseItemkeyMap;
	}

	private CurrencyClientUtil getCurrencyClientUtil() {
		if(currencyClientUtil == null){
			currencyClientUtil = new CurrencyClientUtil(ClientInfo.getCorpPK());
		}
		return currencyClientUtil;
	}
}
