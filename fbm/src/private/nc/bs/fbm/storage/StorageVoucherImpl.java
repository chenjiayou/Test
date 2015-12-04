/**
 *
 */
package nc.bs.fbm.storage;

import nc.bs.dap.out.IAccountProcMsg;
import nc.bs.fbm.transfer.AbstractTransferPlatImpl;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.vo.dap.inteface.IAccountPlat;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * 内部存放、领用处理凭证的实现类
 * <p>创建人：lpf
 * <b>日期：2007-12-3
 *
 */
public class StorageVoucherImpl extends AbstractTransferPlatImpl implements IAccountProcMsg {
	private static final String COMMENTS = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000238")/*@res "内部托管/内部领用"*/;
	private static final String SYSTEM = "FBM";

	/* (non-Javadoc)
	 * @see nc.bs.dap.out.IAccountProcMsg#queryDataByProcId(java.lang.String, java.lang.String)
	 */
	public AggregatedValueObject queryDataByProcId(String billTypeOrProc,
			String procMsg) throws BusinessException {
		if(CommonUtil.isNull(billTypeOrProc)||CommonUtil.isNull(procMsg)){
			return null;
		}
		String[] splits = procMsg.split(StorageVO.Procmsg_flag);
		if(splits==null||splits.length==0){
			return null;
		}
		String pk = splits[0];
		AggregatedValueObject vo = new StorageBillService().queryStorageBillVobyPK(null,pk);
		if(vo!=null&&vo.getChildrenVO()!=null){
			StorageBVO[] storagebVos = (StorageBVO[]) vo.getChildrenVO();
			for (int i = 0; i < storagebVos.length; i++) {
				if(!CommonUtil.isNull(storagebVos[i].getAcceptancetype())){
//					if(storagebVos[i].getAcceptancetype().equals(FbmBusConstant.ACCEPTANCE_BANK)){
//						storagebVos[i].setAcceptancetype("银行承兑汇票");
//					}else{
//						storagebVos[i].setAcceptancetype("商业承兑汇票");
//					}
				}
			}
		}
		return vo;
	}

	/**
	 *
	 * <p>
	 * 转换会计平台VO
	 * isCenter:TRUE中心制证；FALSE单位制证
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-24
	 * @param billVo
	 * @param addmsg
	 * @return
	 */
	public DapMsgVO getDapMsgVo(AggregatedValueObject billVO, String opType){
		StorageVO storageVo = (StorageVO) billVO.getParentVO();
		UFDouble moneyy = new UFDouble(0.0);
		String pk_currency=null;
		StoragePowerVO power = storageVo.getPowerVo();
		String rela_corp = power.getRela_corp();
		String corpPK = power.getPk_corp();
		String currUser = power.getCurrUserPK();
		boolean isUnitBill = power.isUnitBill();

		//获得本币汇率
		CurrencyPublicUtil currencyPublicUtil = new CurrencyPublicUtil(corpPK);
		currencyPublicUtil.setPk_currtype_y(storageVo.getPk_currtype());
		
		UFDate voucherDate = isUnitBill?storageVo.getUnitvoucherdate():storageVo.getVvoucherdate();
		
		try{
			UFDouble[] fbrate = currencyPublicUtil.getExchangeRate(String.valueOf(voucherDate));
			
			for (int i = 0; i < billVO.getChildrenVO().length; i++) {
				StorageBVO bodyVo = (StorageBVO) billVO.getChildrenVO()[i];
				moneyy = moneyy.add(bodyVo.getMoneyy());
				UFDouble[] yfbmoney = currencyPublicUtil.getYfbMoney(bodyVo.getMoneyy(), String.valueOf(voucherDate));
				bodyVo.setMoneyb(yfbmoney[2]);
				bodyVo.setMoneybrate(fbrate[1]);
				pk_currency = bodyVo.getPk_curr();
			}
		}catch(Exception e){
			
			throw new RuntimeException(e.getMessage());
		}

		DapMsgVO msgVo = new DapMsgVO();
		msgVo.setBillCode(storageVo.getVbillno());
		msgVo.setComment(COMMENTS);
		msgVo.setCorp(storageVo.getPk_corp());
		msgVo.setBusiDate(storageVo.getDoperatedate());
		msgVo.setCurrency(pk_currency);
		msgVo.setMoney(moneyy);
		msgVo.setChecker(storageVo.getVapproveid());
		msgVo.setSys(SYSTEM);
		msgVo.setOperator(currUser);

		msgVo.setBillCode(storageVo.getVbillno());//单据编码
		msgVo.setProc(storageVo.getPk_billtypecode());
		msgVo.setDestSystem(IAccountPlat.DESTSYS_GL);

		if(isUnitBill){
			msgVo.setProcMsg(createProcMsg(storageVo.getPrimaryKey(),rela_corp));
			msgVo.setCorp(rela_corp);
		}else{
			msgVo.setProcMsg(createProcMsg(storageVo.getPrimaryKey(),corpPK));
			msgVo.setCorp(corpPK);
		}
		if (FbmBusConstant.OP_CANCELVOUCHER.equals(opType)) {// 取消制证
			msgVo.setMsgType(DapMsgVO.DELMSG);// 取消制证设置为删除消息，默认为增加消息
		}
		return msgVo;
	}

	/**
	 *
	 * <p>
	 * 传送平台PK构造
	 * <p>
	 * 作者：lpf
	 * 日期：2007-12-5
	 * @param pk
	 * @param pk_corp
	 * @return
	 */
	private String createProcMsg(String pk,String pk_corp){
		return pk+StorageVO.Procmsg_flag+pk_corp;
	}
}