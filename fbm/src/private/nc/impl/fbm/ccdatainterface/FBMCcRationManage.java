/**
 *
 */
package nc.impl.fbm.ccdatainterface;

import java.util.Map;

import nc.bs.fbm.gather.FBMPubQueryDAO;
import nc.vo.cc.control.ICcControl;
import nc.vo.cc.control.ICcReturn;
import nc.vo.cc.controlexception.CcControlException;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.ccinterface.CCDataAdapter;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

/**
 * <p>
 * 票据授信控制类
 * <p>创建人：lpf
 * <b>日期：2007-9-24
 *
 */
public class FBMCcRationManage {
	private static boolean isCCEnable = false;

	/**
	 *
	 */
	public FBMCcRationManage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * 开票登记审核
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-24
	 * @param invoiceVo
	 * @throws BusinessException
	 */
	public String approveInvoiceBill(RegisterVO invoiceVo) throws BusinessException{
		if(!isCCEnable()){
			return null;
		}
		ICcControl ccontrol = CCDataAdapter.getInstance().invoiceToCControlForApprove(invoiceVo);

		ICcReturn ccReturn = OuterProxy.getCcRationManage().applyCcRation(ccontrol);

		return dealCCException(ccReturn);
	}

	/**
	 * <p>
	 * 开票登记弃审
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-24
	 * @param invoiceVo
	 * @throws BusinessException
	 */
	public String unApproveInvoiceBill(RegisterVO invoiceVo) throws BusinessException{
		if(!isCCEnable()){
			return null;
		}
		ICcControl ccontrol = CCDataAdapter.getInstance().invoiceToCControlForUnApprove(invoiceVo);

		ICcReturn ccReturn = OuterProxy.getCcRationManage().applyCcRation(ccontrol);
		return dealCCException(ccReturn);
	}

	/**
	 * <p>
	 * 票据付款审核（释放额度）
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-28
	 * @param acceptVo
	 * @return
	 */
	public String approveBillPay(AcceptVO acceptVo)throws BusinessException{
		if(!isCCEnable()){
			return null;
		}
		ICcControl ccontrol = CCDataAdapter.getInstance().acceptToCControlForApprove(acceptVo);

		ICcReturn ccReturn = OuterProxy.getCcRationManage().releaseCcRation(ccontrol);
		return dealCCException(ccReturn);
	}

	/**
	 * <p>
	 * 票据付款弃审(反释放)
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-28
	 * @param acceptVo
	 * @return
	 */
	public String unApproveBillPay(AcceptVO acceptVo) throws BusinessException{
		if(!isCCEnable()){
			return null;
		}
		ICcControl ccontrol = CCDataAdapter.getInstance().acceptToCControlForUnApprove(acceptVo);

		ICcReturn ccReturn = OuterProxy.getCcRationManage().unReleaseCcRation(ccontrol);
		return dealCCException(ccReturn);
	}

	/**
	 * <p>
	 * 开票核销(释放)
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-22
	 * @param returnVo
	 * @return
	 * @throws BusinessException
	 */
	public String cancelInvoiceBill(RegisterVO registerVo) throws BusinessException{
		if(!isCCEnable()||registerVo.getCctype().equals(FbmBusConstant.CCTYPE_NONE)){
			return null;
		}
		ICcControl ccontrol = CCDataAdapter.getInstance().InvoiceToCControlForCancelBill(registerVo);

		ICcReturn ccReturn = OuterProxy.getCcRationManage().releaseCcRation(ccontrol);
		return dealCCException(ccReturn);
	}

	/**
	 * <p>
	 * 开票取消核销（反释放）
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-22
	 * @param returnVo
	 * @return
	 * @throws BusinessException
	 */
	public String delCancelInvoiceBill(RegisterVO registerVo) throws BusinessException{
		if(!isCCEnable()||registerVo.getCctype().equals(FbmBusConstant.CCTYPE_NONE)){
			return null;
		}
		ICcControl ccontrol = CCDataAdapter.getInstance().InvoiceToCControlForDelCancel(registerVo);

		ICcReturn ccReturn = OuterProxy.getCcRationManage().unReleaseCcRation(ccontrol);
		return dealCCException(ccReturn);
	}

	/**
	 * <p>
	 * 根据CC返回参数对Exception分别处理
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-27
	 * @param ccReturn
	 * @return
	 * @throws CcControlException
	 */
	private String dealCCException(ICcReturn ccReturn) throws CcControlException{
		if(ccReturn.getReturnCode() == ICcReturn.CC_SUCCESS){
			return null;
		}else if(ccReturn.getReturnCode() == ICcReturn.CC_HINT){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000373")/* @res"授信系统提示："*/+ccReturn.getReturnMsg();
		}else{
			throw new CcControlException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000374")/* @res"授信系统错误"*/+ccReturn.getReturnMsg());
		}
	}

	/**
	 *
	 * <p>
	 * 查询授信系统是否启用
	 * <p>
	 * 作者：lpf
	 * 日期：2007-11-5
	 * @return
	 * @throws BusinessException
	 */
	private boolean isCCEnable() throws BusinessException {
		if(!isCCEnable){
			String sql = " select distinct pk_codetocode from sm_createcorp left outer join sm_codetocode on sm_createcorp.funccode = sm_codetocode.funccode "
				+ " where pk_codetocode = 'CC' ";
			FBMPubQueryDAO bo = new FBMPubQueryDAO();
			Map map = bo.queryMapData(sql);
			if (map != null && map.get("pk_codetocode") != null)
				isCCEnable=true;
		}

		return isCCEnable;
	}

}