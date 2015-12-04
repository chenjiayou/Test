package nc.bs.pub.action;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;

/**
 * ��ע�����ı���ı��� ���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 * 
 * �������ڣ�(2008-2-27)
 * 
 * @author ƽ̨�ű�����
 */
public class N_36GQ_SAVE extends AbstractCenterOperation {
	/**
	 * N_36GW_SAVE ������ע�⡣
	 */
	public N_36GQ_SAVE() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			EndoreVO headVo = (EndoreVO) vo.m_preValueVo.getParentVO();
			// ǰ̨����ʱ��У�飬�������������������� Ǩ����̨����У�顣
			// checkSave(headVo.getPk_corp(), headVo.getOpbilltype());
			// �ж���ͳ��ҵ���������Լ���ҵ�񣺸���Ʊ�����ͽ����ж�
			if (headVo.getOpbilltype().equals(FbmBusConstant.BILL_PRIVACY)) {// ˽��Ʊ��
				return savePrivacy(vo);
			} else {
				return saveUniStorage(vo);
			}

		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

	/**
	 * ��������Ʊ�Ǽǵ�
	 */
	protected RegisterVO changeRegister(RegisterVO register, SuperVO superVO)
			throws BusinessException {
		register.setPk_corp((String) superVO.getAttributeValue("pk_corp"));
		register.setPaybillunit(register.getHoldunit());
		register.setHoldunit((String) superVO.getAttributeValue("endorser"));
		register.setKeepunit((String) superVO.getAttributeValue("endorser"));
		register.setPk_source((String) superVO.getAttributeValue("pk_source"));
		register.setGathertype(FbmBusConstant.GATHER_TYPE_UNISTORAGE);
		register.setVbillstatus(IBillStatus.FREE);
		register.setPrimaryKey(null);

		// ����ҵ�����ڡ�
		replaceRegisterValue(register, superVO);

		register.setDapprovedate((UFDate) superVO.getAttributeValue("dapprovedate"));
		register.setDoperatedate((UFDate) superVO.getAttributeValue("doperatedate"));
		register.setVapproveid((String) superVO.getAttributeValue("vapproveid"));
		register.setVoperatorid((String) superVO.getAttributeValue("voperatorid"));

		// �������һ��ʣ����
		// register.setBrate((UFDouble)superVO.getAttributeValue("brate"));
		// register.setFrate((UFDouble)superVO.getAttributeValue("frate"));
		// register.setMoneyy((UFDouble)superVO.getAttributeValue("moneyy"));

		// ���VBillNO����Ʊ�Ǽǵ��С�
		String pk_corp = (String) superVO.getAttributeValue("pk_corp");
		IBillcodeRuleService ibrs = (IBillcodeRuleService) NCLocator.getInstance().lookup(IBillcodeRuleService.class.getName());
		String vbillno = ibrs.getBillCode_RequiresNew(FbmBusConstant.BILLTYPE_GATHER, pk_corp, null, null);
		register.setVbillno(vbillno);
		
		return register;

	}

	// ��SuperVO�е������滻��RegisterVO�е�Gatherdateֵ��
	@Override
	protected void replaceRegisterValue(RegisterVO regvo, SuperVO supervo) {
		regvo.setGatherdate((UFDate) supervo.getAttributeValue("busdate"));
	}

}
