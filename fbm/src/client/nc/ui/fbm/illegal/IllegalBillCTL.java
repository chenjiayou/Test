package nc.ui.fbm.illegal;

import nc.ui.trade.bill.ICardController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.trade.pub.HYBillVO;

/**
 * 非法票据处理的界面控制器Controller
 * 
 * @author wues
 * 
 */
public class IllegalBillCTL implements ICardController, ISingleController {
	/**
	 * 单表头：false，单表体:true
	 */
	public boolean isSingleDetail() {
		return true;
	}

	/**
	 * 返回具体卡片的子表隐列,即表体隐列 注意: 必须在该方法内进行实例化,该方法在构造中调用，
	 */
	public String[] getCardBodyHideCol() {
		return null;
	}

	/**
	 * 卡片显示时需要显示的按钮序列
	 */
	public int[] getCardButtonAry() {
		return new int[] { IBillButton.Query, IBillButton.Edit,
				IBillButton.Line, IBillButton.Save, IBillButton.Cancel,
				IBillButton.Refresh, IBillButton.Print };
	}

	/**
	 * 卡片是否显示行号,默认不进行显示
	 */
	public boolean isShowCardRowNo() {
		return false;
	}

	/**
	 * 卡片是否显示合计行，默认不显示
	 */
	public boolean isShowCardTotal() {
		return false;
	}

	/**
	 * 返回注册的此单据类型
	 */
	public String getBillType() {
		return FbmBusConstant.BILLTYPE_ILLEGAL;
	}

	/**
	 * 返回相应的VO序列
	 */
	public String[] getBillVoName() {
		return new String[] { HYBillVO.class.getName(),
				IllegalVO.class.getName(), IllegalVO.class.getName() };
	}

	/**
	 * 子表对应的查询条件
	 */
	public String getBodyCondition() {
		return null;
	}

	/**
	 * 获得表体自定义项(自由项)关键字
	 */
	public String getBodyZYXKey() {
		return null;
	}

	/**
	 * 获得BusinessAction种类(BD\PF) 设置为不走平台
	 */
	public int getBusinessActionType() {
		return IBusinessActionType.BD;
	}

	/**
	 * 返回该单据子表的主键
	 */
	public String getChildPkField() {
		return IllegalVO.PK_ILLEGAL;
	}

	/**
	 * 获得表头自定义项关键字
	 */
	public String getHeadZYXKey() {
		return null;
	}

	/**
	 * 返回该单据在主表的主键
	 */
	public String getPkField() {
		return IllegalVO.PK_ILLEGAL;
	}

	/**
	 * 审批进行中是否可修改。 系统默认不可修改，如果能修改则重载该方法
	 */
	public Boolean isEditInGoing() throws Exception {
		return new Boolean(false);
	}

	/**
	 * 是否存在单据状态。
	 */
	public boolean isExistBillStatus() {
		return false;
	}

	/**
	 * 是否加载卡片公式，缺省false
	 */
	public boolean isLoadCardFormula() {
		return false;
	}

	/**
	 * 返回列表显示时表体隐藏列
	 */
	public String[] getListBodyHideCol() {
		return null;
	}

	/**
	 * 返回列表显示时表头隐藏列
	 */
	public String[] getListHeadHideCol() {
		return null;
	}

	/**
	 * 列表显示时是否显示行号
	 */
	public boolean isShowListRowNo() {
		return true;
	}

	/**
	 * 列表现实时是否显示合计行
	 */
	public boolean isShowListTotal() {
		return true;
	}

}
