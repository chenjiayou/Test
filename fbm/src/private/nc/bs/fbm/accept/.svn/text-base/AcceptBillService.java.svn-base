/**
 *
 */
package nc.bs.fbm.accept;

import nc.bs.fbm.gather.GatherBillService;
import nc.bs.logging.Logger;
import nc.impl.fbm.ccdatainterface.FBMCcRationManage;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.tm.cmpbankacc.ITMBankaccService;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;

/**
 * <p>
 * 票据付款DAO
 * <p>创建人：lpf
 * <b>日期：2007-10-8
 *
 */
public class AcceptBillService {

	/**
	 * 操作银行帐户账接口
	 */
	private ITMBankaccService bankTallyService = null;
	
	/**
	 * 
	 */
	public AcceptBillService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * 审核前操作
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-26
	 * @param acceptVo
	 * @throws BusinessException 
	 */
	public String applyRationBeforeGMApprove(AcceptVO acceptVo) throws BusinessException{
		if(acceptVo == null)
			return null;
		if (acceptVo.getCctype() == null
				|| acceptVo.getCctype().equals(FbmBusConstant.CCTYPE_NONE))
			return null;
				
		return new FBMCcRationManage().approveBillPay(acceptVo);
	}
	
	/**
	 * <p>
	 * 弃审前操作
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-26
	 * @param acceptVo
	 * @throws BusinessException 
	 */
	public String applyRationBeforeGMUnApprove(AcceptVO acceptVo) throws BusinessException{
		if(acceptVo == null)
			return null;
		if (acceptVo.getCctype() == null
				|| acceptVo.getCctype().equals(FbmBusConstant.CCTYPE_NONE))
			return null;
				
		return new FBMCcRationManage().unApproveBillPay(acceptVo);
	}
	
	/**
	 * 
	 * <p>
	 * 处理手工录入的参照
	 * <p>
	 * 作者：lpf
	 * 日期：2007-12-26
	 * @param acceptVo
	 * @return
	 */
	public AcceptVO saveHandInRefData(AcceptVO acceptVo){
		String pk_cubasdoc = insertHolderUnit(acceptVo.getHoldunit(), acceptVo.getHoldunitname());
		acceptVo.setHoldunit(pk_cubasdoc);
		
		String pk_accbank = insertHolderacc(acceptVo.getHolderacc(),acceptVo.getPk_curr(),acceptVo.getHolderaccode(),acceptVo.getHolderaccname());
		acceptVo.setHolderacc(pk_accbank);
		return acceptVo;
	}
	
	private String insertHolderUnit(String holderunit,String holderunitname){
		String pk_cubasdoc = holderunit;
		GatherBillService gatherbo = new GatherBillService();
		if(CommonUtil.isNull(holderunit)&&!CommonUtil.isNull(holderunitname)){
			try {
				pk_cubasdoc = gatherbo.insertInputCustDoc(holderunitname);
			} catch (BusinessException e) {
				Logger.error(e.getMessage(),e);
			}
		}
		return pk_cubasdoc;
	}
	
	private String insertHolderacc(String pk_accbank,String pk_curr, String bankaccode, String bankaccname){
		GatherBillService gatherbo = new GatherBillService();
		try {
			if(CommonUtil.isNull(pk_accbank)&&!CommonUtil.isNull(bankaccode)){
				pk_accbank = gatherbo.insertInputAccbank(pk_curr, bankaccode, bankaccname);
			}
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
		}
		return pk_accbank;
	}
 
	
}
