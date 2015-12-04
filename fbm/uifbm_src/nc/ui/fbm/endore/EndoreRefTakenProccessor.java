package nc.ui.fbm.endore;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.fbm.pub.refmodel.BaseInfoAccBankRefModel;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.register.RegisterVO;


public class EndoreRefTakenProccessor extends RefTakenProccessor {

	public EndoreRefTakenProccessor(BillListPanel listpanel,
			BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
	}
	
	/**
	 * 协带值的实现方法
	 */
	@Override
	protected ITakenColUtil createTakenColUtil() {
		return new AbstractTakenColUtil() {
			protected RefTakenVO[] getRefTakenVOs() {
				return new RefTakenVO[] {
						new RefTakenVO("fbm_baseinfo",EndoreVO.PK_SOURCE,
								new String[] { 
												EndoreVO.FBMBILLTYPE,//票据类型
												EndoreVO.PK_CURR,//币种
												EndoreVO.CONTRACTNO,//交易合同号
												EndoreVO.INVOICEDATE,//出票日期
												
												EndoreVO.PK_BASEINFO,//票据基本信息ＰＫ
												
												EndoreVO.ENDDATE,//到期日期
												EndoreVO.INVOICEUNIT,//出票单位
												EndoreVO.KEEPUNIT,//存放地点
												
												EndoreVO.PAYBANK,//付款银行
												
												EndoreVO.PAYBANKACC,//付款银行帐号
												EndoreVO.PAYUNIT,//付款单位
//												
												EndoreVO.RECEIVEBANK,//收款银行
												EndoreVO.RECEIVEBANKACC,//收款银行帐号
												EndoreVO.RECEIVEUNIT,//收款单位
////												
												EndoreVO.MONEYY,//票面金额
												EndoreVO.MONEYB,//本币金额
												EndoreVO.BRATE,//本币汇率
												EndoreVO.FRATE,//辅币汇率
												EndoreVO.MONEYF,//辅币金额
												
//												EndoreVO.ENDORSEE_ACC,//被背书银行帐号
//												EndoreVO.ENDORSEE_BANK,//被背书银行
//												EndoreVO.ENDORSER_ACC,//背书银行帐号
//												EndoreVO.ENDORSER_BANK,//背书银行
												EndoreVO.ACCEPTANCENO,//承兑协议编号
												EndoreVO.FBMBILLNO//票据编号
												
											 },
								new String[] {
											  RegisterVO.FBMBILLTYPE,
											  RegisterVO.PK_CURR,
											  RegisterVO.CONTRACTNO,
											  RegisterVO.INVOICEDATE,
											  
											  RegisterVO.PK_BASEINFO,
											  
											  RegisterVO.ENDDATE,
											  RegisterVO.INVOICEUNIT,
											  RegisterVO.KEEPUNIT,
											  
											  RegisterVO.PAYBANK,
											  
											  RegisterVO.PAYBANKACC,
											  RegisterVO.PAYUNIT,
//											  
											  RegisterVO.RECEIVEBANK,
											  RegisterVO.RECEIVEBANKACC,
											  RegisterVO.RECEIVEUNIT,
////											  
											  RegisterVO.MONEYY,
											  RegisterVO.MONEYB,
											  RegisterVO.BRATE,
											  RegisterVO.FRATE,
											  RegisterVO.MONEYF,
											  

											  RegisterVO.ACCEPTANCENO,
											  RegisterVO.FBMBILLNO
											 
											  })

				};
			}
			@Override
			public CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[] {
						new CurrencyDecimalVO("fbm_baseinfo",
								EndoreVO.PK_CURR, new String[] {
								EndoreVO.MONEYY, EndoreVO.MONEYF,
								EndoreVO.MONEYB, EndoreVO.FRATE,
								EndoreVO.BRATE }) };
			}	
		};
	}

}
