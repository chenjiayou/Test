package nc.bs.fbm.returnbill;

import nc.bs.dap.out.IAccountProcMsg;
import nc.bs.fbm.pub.ResetRefValues;
import nc.bs.fbm.relief.AbstractGeneralVoucher;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.vo.dap.inteface.IAccountPlat;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

public class ReturnBillVoucher extends AbstractGeneralVoucher implements
		IAccountProcMsg {

	@Override
	public void completeDapMsgVO(DapMsgVO dapvo, SuperVO supervo,
			String unitType) {
		// TODO Auto-generated method stub

	}



	@Override
	public DapMsgVO getDapVO(AggregatedValueObject billVo, String opType) throws BusinessException {
		SuperVO superVO = (SuperVO) billVo.getParentVO();
		DapMsgVO msgVo = new DapMsgVO();
		msgVo.setBillCode((String)superVO.getAttributeValue(ReturnVO.VBILLNO));
		msgVo.setChecker((String)superVO.getAttributeValue(ReturnVO.VAPPROVEID));
		msgVo.setSys(FbmBusConstant.SYSCODE_FBM);
		msgVo.setDestSystem(IAccountPlat.DESTSYS_GL);
		msgVo.setCorp((String)superVO.getAttributeValue(ReturnVO.PK_CORP));
		if (FbmBusConstant.OP_VOUCHER.equals(opType)) {
			msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000167")/*@res "票据退票制证"*/);
			msgVo.setMsgType(DapMsgVO.ADDMSG);// 设置为增加消息
		} else {
			msgVo.setComment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000168")/*@res "票据退票取消制证"*/);
			msgVo.setMsgType(DapMsgVO.DELMSG);// 设置为删除消息
		}
		msgVo.setProc((String)superVO.getAttributeValue(ReturnVO.PK_BILLTYPECODE));
		msgVo.setOperator((String)superVO.getAttributeValue(ReturnVO.VOPERATORID));

		msgVo.setProcMsg(superVO.getPrimaryKey());

		msgVo.setBusiDate((UFDate)superVO.getAttributeValue(ReturnVO.DAPPROVEDATE));// 设置业务日期为审核日期
		ReturnBillServiceImpl srv = new ReturnBillServiceImpl();

		msgVo.setMoney(srv.sumMoneyy(billVo));
		
		ReturnBVO[] bvos =( ReturnBVO[])billVo.getChildrenVO();
		msgVo.setCurrency(bvos[0].getPk_curr());
		
		CurrencyPublicUtil currencyPublicUtil = new CurrencyPublicUtil((String)superVO.getAttributeValue(ReturnVO.PK_CORP));
		currencyPublicUtil.setPk_currtype_y(bvos[0].getPk_curr());
		UFDouble[] fbrate = currencyPublicUtil.getExchangeRate(String.valueOf(superVO.getAttributeValue(ReturnVO.DVOUCHERDATE)));
		for (int i = 0; i < bvos.length; i++) {
			UFDouble[] yfbmoney = currencyPublicUtil.getYfbMoney(bvos[i].getMoneyy(), String.valueOf(superVO.getAttributeValue(ReturnVO.DVOUCHERDATE)));
			bvos[i].setMoneyb(yfbmoney[2]);
			bvos[i].setBrate(fbrate[1]);
		}
		return msgVo;
	}



	@Override
	public String[] getVONames() {
		// TODO Auto-generated method stub
		return new String[]{
				HYBillVO.class.getName(),
				ReturnVO.class.getName(),
				ReturnBVO.class.getName()
		};
	}

	@Override
	public String handelTakenSqlValue(SuperVO supervo) {
		return "";
	}

	@Override
	public void setValues(AggregatedValueObject vo, String sql)
			throws BusinessException {
		
	}
	
	@Override
	public AggregatedValueObject queryDataByProcId(String billTypeOrProc,
			String procMsg) throws BusinessException {
		if (CommonUtil.isNull(billTypeOrProc) || CommonUtil.isNull(procMsg)) {
			return null;
		}
		String[] splits = procMsg.split(StorageVO.Procmsg_flag);
		if (splits == null || splits.length == 0) {
			return null;
		}
		String pk = splits[0];
		HYPubBO bo = new HYPubBO();
		String[] vonames = new String[] { HYBillVO.class.getName(),
				ReturnVO.class.getName(), ReturnBVO.class.getName() };
		AggregatedValueObject vo = bo.queryBillVOByPrimaryKey(vonames, pk);

		if (vo == null
				|| vo.getParentVO() == null
				|| vo.getChildrenVO() == null
				|| vo.getChildrenVO().length == 0) {
			return vo;
		}

		ResetRefValues.setReturnBodyValues(vo);

		return vo;
	}
	

}