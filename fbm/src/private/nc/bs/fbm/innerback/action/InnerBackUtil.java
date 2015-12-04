package nc.bs.fbm.innerback.action;

import java.util.Calendar;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.itf.cm.prv.CmpConst;
import nc.itf.cmp.bankacc.AccBusiVO;
import nc.itf.cmp.bankacc.CMPaccStatus;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

public class InnerBackUtil {
	
//	/**
//	 * 写银行帐户账 内部领用VO to 银行账户账VO
//	 * 
//	 * @param vo
//	 * @param pk_corp
//	 * @return
//	 * @throws BusinessException
//	 */
//	public static SettlementBodyVO[] getInsertBankVOS(StorageVO vo,String pk_corp) throws BusinessException {
//
//		SettlementBodyVO rvo = new SettlementBodyVO();
//
//		rvo.setPk_corp(pk_corp);// 公司
//		rvo.setSystemcode(ProductInfo.pro_FBM);// 来源系统
//		rvo.setPk_bill(vo.getPrimaryKey());// 业务单据主键
//
//		rvo.setBillcode(vo.getVbillno());// 单据编号
//		rvo.setBilldate(vo.getInputdate());// 业务日期取实际放款日期
//		rvo.setTallystatus(new Integer(CMPaccStatus.SUCCESSACCOUNT.getStatus()));// 记账状态,设置为结算成功日期
//		rvo.setFundformcode(1);// 银行存款
//
//		String selfAcc = null;
//
//		selfAcc = vo.getKeepaccount();
//		rvo.setReceive(vo.getSummoneyy());
//		
//		
//		rvo.setFundsflag(0);// 资金流向(0 资金流入1 资金流出2 转账出)
//		rvo.setDirection(CmpConst.Direction_Receive);// CmpConst.Direction_Receive=0收款；CmpConst.Direction_Receive=1付款；
//
//		rvo.setPk_account(selfAcc); // 本方帐
//		rvo.setPk_currtype(vo.getPk_accidcurr());// 币种
//		
//		rvo.setTradertype(CmpConst.TradeObjType_Bank);// 对方类型为银行
//
//		rvo.setTradertype(1);// 0 内部交易 1 外部交易
//		rvo.setPk_billtype(vo.getPk_billtypecode());
//		fillCurrKeyValue(rvo);
//		return new SettlementBodyVO[] { rvo };
//	}
//	
//	
//	/**
//	 * 删除银行帐户账 内部内部领用VO to 银行账VO
//	 * 
//	 * @param vo
//	 * @param pk_corp
//	 * @return
//	 * @throws BusinessException
//	 */
//	public static AccBusiVO getDelBankVOS(StorageVO vo, String pk_corp)
//			throws BusinessException {
//		AccBusiVO rvo = new AccBusiVO();
//		rvo.setPk_corp(pk_corp);
//		rvo.setPk_sourcebill(vo.getPrimaryKey());
//		rvo.setOperator(InvocationInfoProxy.getInstance().getUserCode());// 当前登陆人
//		rvo.setOperateDate(getToday());// 当前日期
//		return rvo;
//	}
//	
//	/**
//	 * <p>
//	 * 获得当前日期
//	 * <p>
//	 * 
//	 * @return UFDate
//	 */
//	public static UFDate getToday() {
//		Calendar c = Calendar.getInstance();
//		UFDate today = new UFDate(c.getTime());
//		today = today.getDateBefore(1);
//		return today;
//	}
//	
//	
//	public static CurrencyPublicUtil createCurrencyPublicUtil(String pk_corp,String pk_curr) {
//		CurrencyPublicUtil currencyPublicUtil = new CurrencyPublicUtil(pk_corp);
//		currencyPublicUtil.setPk_currtype_y(pk_curr);
//		
//		return currencyPublicUtil;
//	}
//	
//	
//	public static  void fillCurrKeyValue(SettlementBodyVO settlevo) throws BusinessException{
//		CurrencyPublicUtil currUtil = createCurrencyPublicUtil(settlevo.getPk_corp(),settlevo.getPk_currtype());
//		UFDouble[] fbrate = currUtil.getExchangeRate(String.valueOf(settlevo.getBilldate()));
//		UFDouble moneyy = settlevo.getReceive();
//		
//		UFDouble[] yfbmoney = currUtil.getYfbMoney(moneyy, String.valueOf(settlevo.getBilldate()));
//		settlevo.setFracrate(fbrate[0]);
//		settlevo.setLocalrate(fbrate[1]);
//		
//		settlevo.setReceivefrac(yfbmoney[1]);
//		settlevo.setReceivelocal(yfbmoney[2]);
//	
//	}
}
