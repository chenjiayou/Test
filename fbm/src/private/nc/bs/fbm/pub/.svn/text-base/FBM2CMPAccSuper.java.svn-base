package nc.bs.fbm.pub;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.itf.cm.prv.CmpConst;
import nc.itf.cmp.bankacc.CMPaccStatus;
import nc.itf.tm.cmpbankacc.ITMBankaccService;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uif.pub.exception.UifException;
import nc.vo.bd.b08.CubasdocVO;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.cdm.proxy.OuterProxy;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.FbmBankDocVO;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.uapbd.bankaccount.BankaccbasVO;
import nc.vo.uapbd.bankdoc.BankdocVO;

/**
 * 票据写现金平台账基类
 * 
 * @author xwq
 * 
 *         2008-11-13
 */
public class FBM2CMPAccSuper {

	/**
	 * 操作银行帐户账接口
	 */
	protected ITMBankaccService bankTallyService = null;

	protected ITMBankaccService getBankTallyService() {
		if (null == bankTallyService) {
			bankTallyService = (ITMBankaccService) NCLocator.getInstance().lookup(ITMBankaccService.class);
		}
		return bankTallyService;
	}

	/**
	 * 初始化传现金平台VO对象组
	 * 
	 * @param vos
	 */
	protected void initSettlementBodyVO(SettlementBodyVO[] vos, SuperVO headvo) {
		int len = vos.length;
		for (int i = 0; i < len; i++) {
			vos[i] = new SettlementBodyVO();
			vos[i].setSystemcode(ProductInfo.pro_FBM);
			vos[i].setPk_bill(headvo.getPrimaryKey());
			vos[i].setBillcode((String) headvo.getAttributeValue("vbillno"));
			vos[i].setTallystatus(new Integer(
					CMPaccStatus.SUCCESSACCOUNT.getStatus()));
			vos[i].setPk_billtype((String) headvo.getAttributeValue("pk_billtypecode"));
		}
	}

	/**
	 * 
	 * @param settlevo
	 * @throws BusinessException
	 */
	public void fillCurrKeyValue(SettlementBodyVO settlevo)
			throws BusinessException {
		CurrencyPublicUtil currUtil = createCurrencyPublicUtil(settlevo.getPk_corp(), settlevo.getPk_currtype());
		UFDouble[] fbrate = currUtil.getExchangeRate(String.valueOf(settlevo.getBilldate()));

		UFDouble moneyy = null;

		if (settlevo.getDirection() == CmpConst.Direction_Receive) {
			moneyy = settlevo.getReceive();
		} else {
			moneyy = settlevo.getPay();
		}

		if (settlevo.getFracrate() == null) {
			settlevo.setFracrate(fbrate[0]);
		}

		if (settlevo.getLocalrate() == null) {
			settlevo.setLocalrate(fbrate[1]);
		}
		settlevo.setFracrate(fbrate[0]);
		settlevo.setLocalrate(fbrate[1]);
		UFDouble[] yfbmoney = currUtil.getYfbMoney(moneyy, settlevo.getFracrate(), settlevo.getLocalrate());

		if (settlevo.getDirection() == CmpConst.Direction_Receive) {
			settlevo.setReceivefrac(yfbmoney[1]);
			settlevo.setReceivelocal(yfbmoney[2]);
		} else {
			settlevo.setPayfrac(yfbmoney[1]);
			settlevo.setPaylocal(yfbmoney[2]);
		}

	}

	public CurrencyPublicUtil createCurrencyPublicUtil(String pk_corp,
			String pk_curr) {
		CurrencyPublicUtil currencyPublicUtil = new CurrencyPublicUtil(pk_corp);
		currencyPublicUtil.setPk_currtype_y(pk_curr);

		return currencyPublicUtil;
	}

	/**
	 * 判断是否与现金平台集成应用
	 * 
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	protected boolean isTogatherWithCMP(String pk_corp)
			throws BusinessException {
		return OuterProxy.getSysInitQry().getParaBoolean(pk_corp, FBMParamConstant.FBM005).booleanValue();
	}

	/**
	 * 根据银行账号PK查询银行档案PK
	 * 
	 * @param bankaccPk
	 * @return
	 */
	protected String queryBankDocPkByBankaccPk(String bankaccPk) {
		String bankdocPk = null;
		if (CommonUtil.isNull(bankaccPk)) {
			return bankdocPk;
		}
		try {
			SuperVO queryVo = new HYPubBO().queryByPrimaryKey(BankaccbasVO.class, bankaccPk);
			if (queryVo != null) {
				bankdocPk = ((BankaccbasVO) queryVo).getPk_bankdoc();
			}
		} catch (UifException e) {
			// TODO Auto-generated catch block
			Logger.debug(e.getMessage());
		}
		return bankdocPk;
	}

	protected String queryCustbyPk(String custPk) {
		if (CommonUtil.isNull(custPk)) {
			return custPk;
		}

		try {
			CubasdocVO queryVo = nc.vo.fbm.proxy.OuterProxy.getCuBasDocQry().findCubasdocVOByPK(custPk);
			if (queryVo != null) {
				return custPk;
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			Logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 填充银行账对方信息
	 * 
	 * @param rvo
	 * @throws BusinessException
	 */
	protected void buildOppInfo(SettlementBodyVO rvo) throws BusinessException {
		if (rvo.getTradertype() == null) {
			return;
		}
		HYPubBO bo = new HYPubBO();
		if (rvo.getPk_trader() != null) {// 交易对象
			String pk_opp = rvo.getPk_trader();
			if (rvo.getTradertype() == CmpConst.TradeObjType_Bank) {// 对方是银行
				// 查询银行档案
				BankdocVO bankdocVO = (BankdocVO) bo.queryByPrimaryKey(BankdocVO.class, pk_opp);
				if (bankdocVO != null) {// 基本档案中有此银行
					rvo.setPk_trader(pk_opp);
				} else {
					FbmBankDocVO fbmBankdocVO = (FbmBankDocVO) bo.queryByPrimaryKey(FbmBankDocVO.class, pk_opp);
					if (fbmBankdocVO != null) {
						rvo.setTradername(fbmBankdocVO.getBankdocname());
					}
					rvo.setPk_trader(null);
				}
			} else if (rvo.getTradertype() == CmpConst.TradeObjType_KeShang) {// 对方是客商
				// ICuBasDocQry qry =
				// (ICuBasDocQry)NCLocator.getInstance().lookup(ICuBasDocQry.class);
				// CubasdocVO cubasdocVO = qry.findCubasdocVOByPK(pk_opp);
				List list = (List) FBMProxy.getUAPQuery().executeQuery("select * from bd_cubasdoc where pk_cubasdoc ='"
						+ pk_opp
						+ "'", new BeanListProcessor(CubasdocVO.class));
				if (list != null && list.size() > 0) {
					rvo.setPk_trader(pk_opp);
				} else {
					CustBasVO cubasVO = FBMProxy.retriveInputCust(pk_opp);
					rvo.setPk_trader(null);
					if (cubasVO != null) {
						rvo.setTradername(cubasVO.getCustname());
					}
				}
			}
		}

		if (rvo.getPk_oppaccount() != null) {// 对方账号
			BankaccbasVO bankaccbasVO = (BankaccbasVO) bo.queryByPrimaryKey(BankaccbasVO.class, rvo.getPk_oppaccount());
			if (bankaccbasVO != null) {
				rvo.setPk_oppaccount(rvo.getPk_oppaccount());
			} else {
				bankaccbasVO = FBMProxy.retriveInputBankacc(rvo.getPk_oppaccount());
				rvo.setPk_oppaccount(null);
				if (bankaccbasVO != null) {
					rvo.setOppaccount(bankaccbasVO.getAccountname());
				}
			}
		}
	}

	protected String queryBankaccbyPk(String bankaccPk) {
		if (CommonUtil.isNull(bankaccPk)) {
			return bankaccPk;
		}

		try {
			SuperVO queryVo = new HYPubBO().queryByPrimaryKey(BankaccbasVO.class, bankaccPk);
			if (queryVo != null) {
				return bankaccPk;
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			Logger.error(e.getMessage());
		}
		return null;
	}

	protected String queryCustbasDocByCorpPk(String corpPk) {
		if (CommonUtil.isNull(corpPk)) {
			return corpPk;
		}
		SuperVO[] queryVo = null;
		try {
			queryVo = new HYPubBO().queryByCondition(CubasdocVO.class, "pk_corp1='"
					+ corpPk
					+ "' and isnull(dr,0)=0");
		} catch (UifException e) {
			// TODO Auto-generated catch block
			Logger.error(e.getMessage());
		}
		if (!CommonUtil.isNull(queryVo)) {
			return queryVo[0].getPrimaryKey();
		}
		return null;
	}

	/***
	 * 写账方法，子类构造参数调用即可。
	 * 
	 * @param rvos
	 * @param writebank
	 * @throws Exception
	 */
	public void writeBankAcc(SettlementBodyVO[] rvos, UFBoolean writebankacc)
			throws BusinessException {
		if (writebankacc == null || writebankacc.booleanValue() == false) {
			getBankTallyService().writeBankaccWithCheck(rvos, false);
		} else {
			getBankTallyService().writeBankacc(rvos);
		}
	}

}
