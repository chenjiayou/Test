package nc.bs.pub.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 
 ***********************************************************
 * ���ڣ�2008-3-17							   
 * ����Ա:���ɽ 							   
 * ���ܣ��������뵥�ı��棬��������ҵ��(ע������ҵ���ڱ༭����ʱ��Ҫ��ɾ�������ɵ���Ʊ�Ǽǵ�)						   
 ***********************************************************
 */
public class N_36GF_SAVE extends AbstractCenterOperation {
	/**
	 * N_36GF_SAVE ������ע�⡣
	 */
	public N_36GF_SAVE() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			DiscountVO headVo = (DiscountVO) vo.m_preValueVo.getParentVO();
			if((UFDate)headVo.getAttributeValue("applydate") == null){
				headVo.setApplydate(headVo.getDoperatedate());
			}
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
	@Override
	protected RegisterVO changeRegister(RegisterVO register, SuperVO superVO)throws BusinessException {
		register.setPk_corp((String) superVO.getAttributeValue("pk_corp"));
		
		//ȡ��ǰ��˾��Ӧ�Ŀ���
		String pk_cubasdoc = new CommonDAO().queryCustByCorp(register
				.getPk_corp());
		register.setHoldunit(pk_cubasdoc);
		register.setKeepunit(pk_cubasdoc);
		
		register.setPk_source(register.getPrimaryKey());
		register.setPrimaryKey(null);
		register.setGathertype(FbmBusConstant.GATHER_TYPE_UNISTORAGE);
		register.setVbillstatus(IBillStatus.FREE);
		
		replaceRegisterValue(register, superVO);
		
		register.setDoperatedate((UFDate)superVO.getAttributeValue("doperatedate"));
		register.setVapproveid((String)superVO.getAttributeValue("voperatorid"));
		register.setVoperatorid((String)superVO.getAttributeValue("voperatorid"));
		
		return register;
	}
	
	
	@Override
	protected void replaceRegisterValue(RegisterVO regvo, SuperVO supervo) {
		if ((UFDate)supervo.getAttributeValue("applydate") != null) {//����������ڲ�Ϊ��������������
			regvo.setGatherdate((UFDate)supervo.getAttributeValue("applydate"));
			regvo.setDapprovedate((UFDate)supervo.getAttributeValue("applydate"));
		} else {
			regvo.setGatherdate((UFDate)supervo.getAttributeValue("doperatedate"));
			regvo.setDapprovedate((UFDate)supervo.getAttributeValue("doperatedate"));
		}
	}

}
