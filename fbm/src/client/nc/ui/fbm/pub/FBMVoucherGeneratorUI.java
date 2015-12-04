package nc.ui.fbm.pub;

import nc.ui.dap.voucher.VoucherGeneratorUI;
import nc.ui.pub.ButtonObject;
import nc.ui.trade.multiappinterface.IParent;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.dap.rtvouch.DapFinMsgVO;
import nc.vo.pub.AggregatedValueObject;

//since 5.3
public class FBMVoucherGeneratorUI extends VoucherGeneratorUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ButtonObject m_btnReturn = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000038")/*@res "返回"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("362002","UPP362002-000024")/*@res "返回单据界面"*/, 2,"返回");	/*-=notranslate=-*/
	private nc.ui.glpub.IParent m_parent;
	private AggregatedValueObject billVO = null;
	private DapMsgVO m_msgvo = null;
	public static void main(String[] args) {
	}
	/******************************************************************
	 * 按钮条事件响应。
	 * 创建日期：(2001-5-22 14:25:24)
	 * @param bo nc.ui.pub.ButtonObject
	 ******************************************************************/
	public void onButtonClicked(ButtonObject bo) {
		//如果是返回按钮事件则BtnReturn(),否则调用父类方法
		if(bo==m_btnReturn){
			this.BtnReturn();
		}else{
			super.onButtonClicked(bo);
			}
		}

	public FBMVoucherGeneratorUI(){
			super();
	}
	private void addButton(){
		ButtonObject oriButtons[]=super.getButtons();
		int ButtonNum=getButtons().length;
		ButtonObject[] m_newButtonAry = new ButtonObject[ButtonNum+1];
		for (int j=0;j<ButtonNum;j++){
			m_newButtonAry[j]=oriButtons[j];
		}
		m_newButtonAry[ButtonNum]=m_btnReturn;
		setButtons(m_newButtonAry);

	}
	/**
	 * 设定节点
	 * @param strNodeCode
	 */
	public void setIParent(nc.ui.glpub.IParent parent){
		m_parent = parent;

	}

	public void setBillData(AggregatedValueObject aggbillVO){
		this.billVO = aggbillVO;
	}
	
	/**
	 * 显示凭证界面
	 *
	 */
	public void showVoucherPanel(){
		if(billVO == null){
			this.showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("ftspub","UPPftspub-000113")/*@res "数据为空，不可以制证！"*/);
			BtnReturn();
		}
		else{
		    if (m_msgvo == null) {
		        this.showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("ftspub","UPPftspub-000114")/*@res "没有设置会计平台VO，不能制证！"*/);
				BtnReturn();
		    }
		    DapFinMsgVO dapFinMsgVO = DapFinMsgVO.changeFinIndexVO(m_msgvo, null);
		    dapFinMsgVO.setFlag(null);
		    DapFinMsgVO[] vos = new DapFinMsgVO[]{dapFinMsgVO};

			super.setIsSelfDeliver(false);
			super.readData(vos,true,null,0,null);
			addButton();
			super.invoke(null,m_parent);
		}
	}
	/***
	 * 根据节点号返回
	 *
	 */
	private void BtnReturn(){
		if (m_parent != null)
			m_parent.closeMe();
	}
	
	public void setMsgVO(DapMsgVO msgvo) {
	    m_msgvo = msgvo;
	}
}
