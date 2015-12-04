/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import java.util.Hashtable;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.fbm.DiscountCalculation.DiscountCalculationVO;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 
 * <p>
 * 创建日期：2006-9-6
 * 
 * @author lisg
 * @since v5.0
 */
public class DiscountCalculationController 
implements ICardController, ISingleController,ITotalSpecify {
	
	private Hashtable<String, TotalRowPara> httrp = null;
	
	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.bill.ICardController#getCardBodyHideCol()
	 */
	public String[] getCardBodyHideCol() {
		return new String[]{
				"def5"
		};
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.bill.ICardController#getCardButtonAry()
	 */
	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Query,
				IFBMButton.Discount_Calculate,
				IBillButton.Print
		};
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.bill.ICardController#isShowCardRowNo()
	 */
	public boolean isShowCardRowNo() {
		return true;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.bill.ICardController#isShowCardTotal()
	 */
	public boolean isShowCardTotal() {
		return false;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.controller.IControllerBase#getBillType()
	 */
	public String getBillType() {
		return "FBM01";
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.controller.IControllerBase#getBillVoName()
	 */
	public String[] getBillVoName() {
		return new String[]{
				nc.vo.trade.pub.HYBillVO.class.getName(), 
				DiscountCalculationVO.class.getName(),
				DiscountCalculationVO.class.getName()
		};
	}

	public String getBodyCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.controller.IControllerBase#getBodyZYXKey()
	 */
	public String getBodyZYXKey() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.controller.IControllerBase#getBusinessActionType()
	 */
	public int getBusinessActionType() {
		return IBusinessActionType.BD;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.controller.IControllerBase#getChildPkField()
	 */
	public String getChildPkField() {
		return RegisterVO.PK_REGISTER;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.controller.IControllerBase#getHeadZYXKey()
	 */
	public String getHeadZYXKey() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.controller.IControllerBase#getPkField()
	 */
	public String getPkField() {
		return RegisterVO.PK_REGISTER;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.controller.IControllerBase#isEditInGoing()
	 */
	public Boolean isEditInGoing() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.controller.IControllerBase#isExistBillStatus()
	 */
	public boolean isExistBillStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.controller.IControllerBase#isLoadCardFormula()
	 */
	public boolean isLoadCardFormula() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-20
	 *
	 * @see nc.ui.trade.bill.ISingleController#isSingleDetail()
	 */
	public boolean isSingleDetail() {
		return true;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-27
	 *
	 * @see nc.ui.fbm.m23.ITotalSpecify#getTotalRowPara(java.lang.String)
	 */
	public TotalRowPara getTotalRowPara(String voname) {
		return getHtTrp().get(voname);
	}
	
	public Hashtable<String, TotalRowPara> creatHashTable(){
		Hashtable<String, TotalRowPara> htR = new Hashtable<String, TotalRowPara>();
		
		TotalRowPara trp = new TotalRowPara(RegisterVO.class.getName(),
				                                    RegisterVO.PK_CURR,//根据原币币种进行分类
				                                    RegisterVO.FBMBILLNO,//票据编号
		                                            "def5",
												new String[]{
			                                        RegisterVO.MONEYY,//原币金额
			                                        RegisterVO.TXJZ,//贴现净值
			                                        RegisterVO.TXLX//贴现利息
												});
		
		htR.put(RegisterVO.class.getName(), trp);
		
		return htR;
	}

	/**
	 * @return the httrp
	 */
	private Hashtable<String, TotalRowPara> getHtTrp() {
		if(httrp == null){
			httrp = creatHashTable();
		}
		
		return httrp;
	}

}
