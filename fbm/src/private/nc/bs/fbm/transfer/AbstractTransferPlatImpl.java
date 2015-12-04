/**
 *
 */
package nc.bs.fbm.transfer;

import nc.bs.fbm.reciept.RecieptService;
import nc.itf.dap.pub.IDapSendMessage;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;

/**
 * <p>
 * ��ƽ̨�ĳ�����
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-12-5
 *
 */
public abstract class AbstractTransferPlatImpl {
	private IDapSendMessage dap;
	/**
	 *
	 */
	public AbstractTransferPlatImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * ��ƽ̨
	 * addmsg:TRUE ����ƾ֤��FALSE��ɾ��ƾ֤
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-10-26
	 * @param billVo
	 * @param addmsg
	 * @param msgVo
	 * @throws BusinessException
	 */
	public void dapSendMessage(AggregatedValueObject billVo, boolean opType) throws BusinessException {
		String stropType = null;

		if(opType){
			stropType = FbmBusConstant.OP_VOUCHER;
		}else{
			stropType = FbmBusConstant.OP_CANCELVOUCHER;
		}
		DapMsgVO msgVo = getDapMsgVo(billVo, stropType);

		IDapSendMessage dapservice = getDapService();

		if(!opType){
			checkIsEditForDelVoucher(msgVo, dapservice);
		}

//		if(opType){
//			msgVo.setMsgType(DapMsgVO.ADDMSG);
//		}else{
//			msgVo.setMsgType(DapMsgVO.DELMSG);
//		}

		dapservice.sendMessage(msgVo, billVo);
	}

	/**
	 *
	 * <p>
	 *
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-12-5
	 * @return
	 */
	private IDapSendMessage getDapService() {
		if(dap==null)
			dap = OuterProxy.getDapSendMessageService();
		return dap;
	}

	/**
	 * <p>
	 * У��ƾ֤�Ƿ����ɾ��
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-10-26
	 * @param msgVo
	 * @param dap
	 * @throws BusinessException
	 */
	protected void checkIsEditForDelVoucher(DapMsgVO msgVo, IDapSendMessage dap)
			throws BusinessException {
		boolean isEdit = dap.isEditBillType(msgVo.getCorp(),msgVo.getSys(),msgVo.getProc(),msgVo.getBusiType(),msgVo.getProcMsg());
		if(!isEdit){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000345")/* @res"������ƾ֤����ҵ���ܽ��в���!"*/);
		}
	}

	/**
	 *
	 * <p>
	 * ������ƽ̨��ϢVO
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-12-5
	 * @param vo
	 * @return
	 */
	public abstract DapMsgVO getDapMsgVo(AggregatedValueObject billVO, String opType);
}