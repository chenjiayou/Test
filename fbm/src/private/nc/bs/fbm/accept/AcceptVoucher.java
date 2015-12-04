package nc.bs.fbm.accept;

import nc.bs.dao.BaseDAO;
import nc.bs.dap.out.IAccountProcMsg;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

public class AcceptVoucher implements IAccountProcMsg{

	public AggregatedValueObject queryDataByProcId(String billTypeOrProc,
			String procMsg) throws BusinessException {

		HYBillVO billvo = null;
		try {
			BaseDAO baseDao = new BaseDAO();
			AcceptVO acceptvo = (AcceptVO)baseDao.retrieveByPK(AcceptVO.class,procMsg );
			BaseinfoVO baseinfovo = (BaseinfoVO)baseDao.retrieveByPK(BaseinfoVO.class, acceptvo.getPk_baseinfo());
			billvo = new HYBillVO();
			acceptvo.setPk_curr(baseinfovo.getPk_curr());
			fillCurrKeyValue(acceptvo);
			billvo.setParentVO(acceptvo);
			
			billvo.setChildrenVO(new SuperVO[]{acceptvo});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return billvo;
	
	}

	public DapMsgVO getDapMsgVo(AggregatedValueObject billVO, String opType) throws BusinessException{
		AcceptVO acceptVO = (AcceptVO)billVO.getParentVO();
		DapMsgVO msgVo = new DapMsgVO();
		msgVo.setBillCode(acceptVO.getVbillno());
		msgVo.setMoney(acceptVO.getBillmoneyy());
		msgVo.setChecker(acceptVO.getVapproveid());
		msgVo.setSys(FbmBusConstant.SYSCODE_FBM);
		msgVo.setCorp(acceptVO.getPk_corp());
		msgVo.setProc(acceptVO.getPk_billtypecode());
		msgVo.setOperator(acceptVO.getVoperatorid());
		msgVo.setProcMsg(acceptVO.getPrimaryKey());
		msgVo.setBusiDate(acceptVO.getDapprovedate());

		CommonDAO commDAO = new CommonDAO();
		BaseinfoVO baseVO = commDAO.queryBaseinfoByPK(acceptVO.getPk_baseinfo());
		if(baseVO !=null){
			msgVo.setCurrency(baseVO.getPk_curr());
		}
		
		if (FbmBusConstant.OP_VOUCHER.equals(opType)) {
			msgVo.setMsgType(DapMsgVO.ADDMSG);// 设置为增加消息
			msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000125")/*@res "票据付款制证"*/);
		}else{
			msgVo.setMsgType(DapMsgVO.DELMSG);
			msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000126")/*@res "票据付款取消制证"*/);
		}

		return msgVo;
	}
	
	/**
	 * 得到原币本币辅币,本币汇率,辅币汇率。
	 * @param accdetailvo
	 */
	public void fillCurrKeyValue(AcceptVO acceptvo) throws BusinessException{
		String pk_corp = 	InvocationInfoProxy.getInstance().getCorpCode();
		CurrencyPublicUtil currUtil = createCurrencyPublicUtil(pk_corp,acceptvo.getPk_curr());
		UFDouble[] fbrate = currUtil.getExchangeRate(String.valueOf(acceptvo.getDacceptdate()));
		UFDouble moneyy = null;
		
			moneyy = acceptvo.getMoneyy();
		
		UFDouble[] yfbmoney = currUtil.getYfbMoney(moneyy, String.valueOf(acceptvo.getDacceptdate()));
		acceptvo.setFrate(fbrate[0]);
		acceptvo.setBrate(fbrate[1]);
		
		acceptvo.setMoneyf(yfbmoney[1]);
		acceptvo.setMoneyb(yfbmoney[2]);

		
	}
	
	public CurrencyPublicUtil createCurrencyPublicUtil(String pk_corp,String pk_curr) {
		CurrencyPublicUtil currencyPublicUtil = new CurrencyPublicUtil(pk_corp);
		currencyPublicUtil.setPk_currtype_y(pk_curr);

		return currencyPublicUtil;
	}

}