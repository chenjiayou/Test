package nc.bs.fbm.invoice;

import nc.bs.dao.BaseDAO;
import nc.bs.dap.out.IAccountProcMsg;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

public class InvoiceVoucher implements IAccountProcMsg {

	public AggregatedValueObject queryDataByProcId(String billTypeOrProc,
			String procMsg) throws BusinessException {

		HYBillVO billvo = null;
		try {
			BaseDAO baseDao = new BaseDAO();
			RegisterVO regvo = (RegisterVO)baseDao.retrieveByPK(RegisterVO.class,procMsg );
			BaseinfoVO baseinfovo = (BaseinfoVO)baseDao.retrieveByPK(BaseinfoVO.class, regvo.getPk_baseinfo());
			billvo = new HYBillVO();
			regvo.setPk_curr(baseinfovo.getPk_curr());
			fillCurrKeyValue(regvo);
			billvo.setParentVO(regvo);
			
			billvo.setChildrenVO(new SuperVO[]{regvo});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return billvo;
	
	}

	public DapMsgVO getDapMsgVo(AggregatedValueObject billVO, String opType) throws BusinessException{
		RegisterVO regVO = (RegisterVO)billVO.getParentVO();
		DapMsgVO msgVo = new DapMsgVO();
		msgVo.setBillCode(regVO.getVbillno());
		msgVo.setMoney(regVO.getMoneyy());
		msgVo.setChecker(regVO.getVapproveid());
		msgVo.setSys(FbmBusConstant.SYSCODE_FBM);
		msgVo.setCorp(regVO.getPk_corp());
		msgVo.setProc(regVO.getPk_billtypecode());
		msgVo.setOperator(regVO.getVvouchermanid());
		msgVo.setProcMsg(regVO.getPrimaryKey());
		msgVo.setBusiDate(regVO.getDvoucherdate());
		CommonDAO commDAO = new CommonDAO();
		BaseinfoVO baseVO = commDAO.queryBaseinfoByPK(regVO.getPk_baseinfo());
		if(baseVO !=null){
			msgVo.setCurrency(baseVO.getPk_curr());
		}

		if (FbmBusConstant.OP_VOUCHER.equals(opType)) {
			msgVo.setMsgType(DapMsgVO.ADDMSG);// 设置为增加消息
			msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000143")/*@res "付票登记制证"*/);
		}else{
			msgVo.setMsgType(DapMsgVO.DELMSG);
			msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000144")/*@res "付票登记取消制证"*/);
		}

		return msgVo;
	}
	
	/**
	 * 得到原币本币辅币,本币汇率,辅币汇率。
	 * @param accdetailvo
	 */
	public void fillCurrKeyValue(RegisterVO regvo) throws BusinessException{
		String pk_corp = 	InvocationInfoProxy.getInstance().getCorpCode();
		CurrencyPublicUtil currUtil = createCurrencyPublicUtil(pk_corp,regvo.getPk_curr());
		UFDouble[] fbrate = currUtil.getExchangeRate(String.valueOf(regvo.getInvoicedate()));
		UFDouble moneyy = null;
		
			moneyy = regvo.getMoneyy();
		
		UFDouble[] yfbmoney = currUtil.getYfbMoney(moneyy, String.valueOf(regvo.getInvoicedate()));
		regvo.setFrate(fbrate[0]);
		regvo.setBrate(fbrate[1]);
		
		regvo.setMoneyf(yfbmoney[1]);
		regvo.setMoneyb(yfbmoney[2]);

		
	}
	
	public CurrencyPublicUtil createCurrencyPublicUtil(String pk_corp,String pk_curr) {
		CurrencyPublicUtil currencyPublicUtil = new CurrencyPublicUtil(pk_corp);
		currencyPublicUtil.setPk_currtype_y(pk_curr);

		return currencyPublicUtil;
	}

}