package nc.bs.fbm.pub.action;

import nc.bs.fbm.accpet.action.AuditAccept;
import nc.bs.fbm.accpet.action.CancelAuditAccept;
import nc.bs.fbm.accpet.action.DeleteAccept;
import nc.bs.fbm.accpet.action.SaveAccept;
import nc.bs.fbm.bankback.action.AuditBankBack;
import nc.bs.fbm.bankback.action.CancelAuditBankBack;
import nc.bs.fbm.bankback.action.DeleteBankBack;
import nc.bs.fbm.bankback.action.SaveBankBack;
import nc.bs.fbm.bankkeep.action.AuditBankKeep;
import nc.bs.fbm.bankkeep.action.CancelAuditBankKeep;
import nc.bs.fbm.bankkeep.action.DeleteBankKeep;
import nc.bs.fbm.bankkeep.action.SaveBankKeep;
import nc.bs.fbm.consignbank.action.AuditCollection;
import nc.bs.fbm.consignbank.action.CancelAuditCollection;
import nc.bs.fbm.consignbank.action.CancelTransactCollection;
import nc.bs.fbm.consignbank.action.DeleteCollection;
import nc.bs.fbm.consignbank.action.DisableCollection;
import nc.bs.fbm.consignbank.action.SaveCollection;
import nc.bs.fbm.consignbank.action.TransactCollection;
import nc.bs.fbm.discount.action.AuditDiscountTran;
import nc.bs.fbm.discount.action.CancelAuditDiscountTran;
import nc.bs.fbm.discount.action.DeleteDiscountTran;
import nc.bs.fbm.discount.action.SaveDiscountTran;
import nc.bs.fbm.discountapply.action.AuditDiscountApp;
import nc.bs.fbm.discountapply.action.CancelAuditDiscountApp;
import nc.bs.fbm.discountapply.action.DeleteDiscountApp;
import nc.bs.fbm.discountapply.action.SaveDiscountApp;
import nc.bs.fbm.endore.action.AuditEndore;
import nc.bs.fbm.endore.action.CancelAuditEndore;
import nc.bs.fbm.endore.action.DeleteEndore;
import nc.bs.fbm.endore.action.DestroyEndore;
import nc.bs.fbm.endore.action.SaveEndore;
import nc.bs.fbm.gather.action.AuditGather;
import nc.bs.fbm.gather.action.CancelAuditGather;
import nc.bs.fbm.gather.action.CancelCenterUserGather;
import nc.bs.fbm.gather.action.CenterUseGather;
import nc.bs.fbm.gather.action.DeleteGather;
import nc.bs.fbm.gather.action.SaveGather;
import nc.bs.fbm.impawn.action.AuditImpawn;
import nc.bs.fbm.impawn.action.CancelAuditImpawn;
import nc.bs.fbm.impawn.action.CancelImpawnBack;
import nc.bs.fbm.impawn.action.DeleteImpawn;
import nc.bs.fbm.impawn.action.ImpawnBack;
import nc.bs.fbm.impawn.action.SaveImpawn;
import nc.bs.fbm.innerback.action.AuditInnerBack;
import nc.bs.fbm.innerback.action.CancelAuditInnerBack;
import nc.bs.fbm.innerback.action.CanceloutInnerBack;
import nc.bs.fbm.innerback.action.DeleteInnerBack;
import nc.bs.fbm.innerback.action.OutputInnerBack;
import nc.bs.fbm.innerback.action.SaveInnerBack;
import nc.bs.fbm.innerkeep.action.AuditInnerKeep;
import nc.bs.fbm.innerkeep.action.CancelAuditInnerKeep;
import nc.bs.fbm.innerkeep.action.CancelInInnerKeep;
import nc.bs.fbm.innerkeep.action.DeleteInnerKeep;
import nc.bs.fbm.innerkeep.action.InputInnerKeep;
import nc.bs.fbm.innerkeep.action.SaveInnerKeep;
import nc.bs.fbm.invoice.action.AuditInvoice;
import nc.bs.fbm.invoice.action.CancelAuditInvoice;
import nc.bs.fbm.invoice.action.CancelDestroyInvoice;
import nc.bs.fbm.invoice.action.DeleteInvoice;
import nc.bs.fbm.invoice.action.DestroyInvoice;
import nc.bs.fbm.invoice.action.SaveInvoice;
import nc.bs.fbm.returnbill.action.AuditReturnBill;
import nc.bs.fbm.returnbill.action.CancelAuditReturnBill;
import nc.bs.fbm.returnbill.action.DeleteReturnBill;
import nc.bs.fbm.returnbill.action.SaveReturnBill;
import nc.itf.fbm.action.IBusiAction;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

import nc.bs.fbm.relief.action.SaveRelief;
import nc.bs.fbm.relief.action.AuditRelief;
import nc.bs.fbm.relief.action.CancelAuditRelief;
import nc.bs.fbm.relief.action.CancelOutputRelief;
import nc.bs.fbm.relief.action.CancelVoucherRelief;
import nc.bs.fbm.relief.action.DeleteRelief;
import nc.bs.fbm.relief.action.OutputRelief;
import nc.bs.fbm.relief.action.VoucherRelief;

public class BusiActionFactory  {
	private static BusiActionFactory factory = null;
	
	public static BusiActionFactory getInstance(){
		if(factory == null){
			factory = new BusiActionFactory();
		}
		return factory;
	}
	
	private BusiActionFactory(){
		super();
	}
	
	public IBusiAction createActionClass(String billtype ,String actioncode) throws BusinessException{
		if(billtype.equals(FbmBusConstant.BILLTYPE_DISCOUNT_APP)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveDiscountApp<DiscountVO, DiscountVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditDiscountApp<DiscountVO, DiscountVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditDiscountApp<DiscountVO, DiscountVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteDiscountApp<DiscountVO, DiscountVO>();
			}
		}
		
		if(billtype.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveDiscountTran<DiscountVO, DiscountVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditDiscountTran<DiscountVO, DiscountVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditDiscountTran<DiscountVO, DiscountVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteDiscountTran<DiscountVO, DiscountVO>();
			}

		}
		
		//收票登记单
		if(billtype.equals(FbmBusConstant.BILLTYPE_GATHER)){ 
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveGather<RegisterVO, RegisterVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditGather<RegisterVO, RegisterVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditGather<RegisterVO, RegisterVO>(); 
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteGather<RegisterVO, RegisterVO>();
			}
			if(actioncode.equals(FbmActionConstant.CENTERUSE)){
				return new CenterUseGather<RegisterVO,RegisterVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELCENTERUSER))
			{
				return new CancelCenterUserGather<RegisterVO,RegisterVO>();
			}
			
		}
		
		//付票登记单
		if(billtype.equals(FbmBusConstant.BILLTYPE_INVOICE)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveInvoice<RegisterVO, RegisterVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditInvoice<RegisterVO, RegisterVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditInvoice<RegisterVO, RegisterVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteInvoice<RegisterVO, RegisterVO>();
			}
			if(actioncode.equals(FbmActionConstant.DESTROY)){
				return new DestroyInvoice<RegisterVO, RegisterVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELDESTROY)){
				return new CancelDestroyInvoice<RegisterVO, RegisterVO>();
			}
		}
		
		if(billtype.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveCollection<CollectionVO, CollectionVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditCollection<CollectionVO, CollectionVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditCollection<CollectionVO, CollectionVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteCollection<CollectionVO, CollectionVO>();
			}
			if(actioncode.equals(FbmActionConstant.TRANSACT)){
				return new TransactCollection<CollectionVO, CollectionVO>();
			}
			if(actioncode.equals(FbmActionConstant.DISABLE)){
				return new DisableCollection<CollectionVO, CollectionVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELTRANSACT)){
				return new CancelTransactCollection<CollectionVO, CollectionVO>();
			}
		}
		
		if(billtype.equals(FbmBusConstant.BILLTYPE_BILLPAY)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveAccept<AcceptVO, AcceptVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditAccept<AcceptVO, AcceptVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditAccept<AcceptVO, AcceptVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteAccept<AcceptVO, AcceptVO>();
			}
		}
		
		if(billtype.equals(FbmBusConstant.BILLTYPE_BANKKEEP)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveBankKeep<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditBankKeep<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditBankKeep<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteBankKeep<HYBillVO,StorageVO>();
			}

		}
		
		if(billtype.equals(FbmBusConstant.BILLTYPE_BANKBACK)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveBankBack<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditBankBack<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditBankBack<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteBankBack<HYBillVO,StorageVO>();
			}

		}
		
		//背书办理
		if(billtype.equals(FbmBusConstant.BILLTYPE_ENDORE)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveEndore<EndoreVO,EndoreVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditEndore<EndoreVO,EndoreVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditEndore<EndoreVO,EndoreVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteEndore<EndoreVO,EndoreVO>();
			}
			if(actioncode.equalsIgnoreCase(FbmActionConstant.DESTROY)){
				return new DestroyEndore<EndoreVO,EndoreVO>();
			}
		}
		
		//质押
		if(billtype.equals(FbmBusConstant.BILLTYPE_IMPAWN)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveImpawn<ImpawnVO,ImpawnVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditImpawn<ImpawnVO,ImpawnVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditImpawn<ImpawnVO,ImpawnVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteImpawn<ImpawnVO,ImpawnVO>();
			}
			if (actioncode.equals(FbmActionConstant.IMPAWNBACK)) {
				return new ImpawnBack<ImpawnVO,ImpawnVO>();
			}
			if (actioncode.equals(FbmActionConstant.CANCELIMPAWNBACK)){
				return new CancelImpawnBack<ImpawnVO, ImpawnVO>();
			}
		}
		
		if(billtype.equals(FbmBusConstant.BILLTYPE_INNERBACK)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveInnerBack<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditInnerBack<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditInnerBack<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteInnerBack<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.OUTPUT_SUCCESS)){
				return new OutputInnerBack<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELOUTPUT)){
				return new CanceloutInnerBack<HYBillVO,StorageVO>();
			}
		}
		
		if(billtype.equals(FbmBusConstant.BILLTYPE_INNERKEEP)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveInnerKeep<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditInnerKeep<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditInnerKeep<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteInnerKeep<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.INPUT_SUCCESS)){
				return new InputInnerKeep<HYBillVO,StorageVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELINPUT)){
				return new CancelInInnerKeep<HYBillVO,StorageVO>();
			}
		}
		
		if(billtype.equals(FbmBusConstant.BILLTYPE_RETURN)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveReturnBill<HYBillVO,ReturnVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditReturnBill<HYBillVO,ReturnVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditReturnBill<HYBillVO,ReturnVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteReturnBill<HYBillVO,ReturnVO>();
			}
		}
		
		if (FbmBusConstant.BILLTYPE_RELIEF.equals(billtype)){
			if(actioncode.equals(FbmActionConstant.SAVE) || actioncode.equals(FbmActionConstant.EDITSAVE)){
				return new SaveRelief<HYBillVO, ReliefVO>();
			}
			if(actioncode.equals(FbmActionConstant.AUDIT)|| actioncode.equals(FbmActionConstant.ONAUDIT )){
				return new AuditRelief<HYBillVO, ReliefVO>();
			}
			if(actioncode.equals(FbmActionConstant.CANCELAUDIT)){
				return new CancelAuditRelief<HYBillVO, ReliefVO>();
			}
			if(actioncode.equals(FbmActionConstant.DELETE)){
				return new DeleteRelief<HYBillVO, ReliefVO>();
			}
			//出库
			if(actioncode.equals(FbmActionConstant.OUTPUT_SUCCESS)){
				return new OutputRelief<HYBillVO, ReliefVO>();
			}
			//取消出库
			if(actioncode.equals(FbmActionConstant.CANCELOUTPUT)){
				return new CancelOutputRelief<HYBillVO, ReliefVO>();
			}
			//制证
			if (FbmActionConstant.VOUCHER.equals(actioncode)) {
				return new VoucherRelief<HYBillVO, ReliefVO>();
			}
			//取消制证
			if (FbmActionConstant.CANCELVOUCHER.equals(actioncode)) {
				return new CancelVoucherRelief<HYBillVO, ReliefVO>();
			}
		}
		
		return null;
	}
}
