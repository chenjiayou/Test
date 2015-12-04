package nc.impl.fbm.dapmsg;

import nc.bs.fbm.accept.AcceptVoucher;
import nc.bs.fbm.consignbank.ConsignBankVoucher;
import nc.bs.fbm.discount.DiscountVoucher;
import nc.bs.fbm.endore.EndoreVoucher;
import nc.bs.fbm.reciept.RecieptService;
import nc.bs.fbm.relief.AbstractGeneralVoucher;
import nc.bs.fbm.relief.ReliefHelper;
import nc.bs.fbm.returnbill.ReturnBillVoucher;
import nc.bs.fbm.storage.StorageVoucherImpl;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.bs.fbm.invoice.InvoiceVoucher;

public class IDapMsgServiceImpl  implements nc.itf.fbm.dapmsg.IDapMsgService{

	public DapMsgVO getDapMsgVo(String billtype, AggregatedValueObject billVO,
			String opType)  throws BusinessException{
		if(billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_LIQUIDATE) || billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_RECKON_RECIEPT)) {
			return new RecieptService().getDapMsgVo(billVO, opType);
		}
		if(billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {
			return new ConsignBankVoucher().getDapVO(billVO, opType,AbstractGeneralVoucher.UNIT);
		}
		if(billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {
			return new DiscountVoucher().getDapVO(billVO, opType,AbstractGeneralVoucher.UNIT);
		}
		if(billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_ENDORE)) {
			return new EndoreVoucher().getDapVO(billVO, opType,AbstractGeneralVoucher.UNIT);
		}
		if(billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_INNERKEEP) || billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_INNERBACK)) {
			return new StorageVoucherImpl().getDapMsgVo(billVO, opType);
		}
		if(billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_RELIEF)) {
			return new ReliefHelper().getDapMsgVo(billVO, opType);
		}
		if(billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_BILLPAY)){
			return new AcceptVoucher().getDapMsgVo(billVO,opType); 
		}
		if(billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_INVOICE)){
			return new InvoiceVoucher().getDapMsgVo(billVO,opType);
		}
		if(billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_RETURN)){
			return new ReturnBillVoucher().getDapVO(billVO, opType);
		}
		
		return null;
	}

}
