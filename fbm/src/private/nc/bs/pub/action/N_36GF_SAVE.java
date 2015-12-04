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
 * 日期：2008-3-17							   
 * 程序员:吴二山 							   
 * 功能：贴现申请单的保存，包含中心业务(注意中心业务在编辑保存时需要先删除已生成的收票登记单)						   
 ***********************************************************
 */
public class N_36GF_SAVE extends AbstractCenterOperation {
	/**
	 * N_36GF_SAVE 构造子注解。
	 */
	public N_36GF_SAVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			DiscountVO headVo = (DiscountVO) vo.m_preValueVo.getParentVO();
			if((UFDate)headVo.getAttributeValue("applydate") == null){
				headVo.setApplydate(headVo.getDoperatedate());
			}
			// 判断是统管业务还是做的自己的业务：根据票据类型进行判断
			if (headVo.getOpbilltype().equals(FbmBusConstant.BILL_PRIVACY)) {// 私有票据
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
	 * 新生成收票登记单
	 */
	@Override
	protected RegisterVO changeRegister(RegisterVO register, SuperVO superVO)throws BusinessException {
		register.setPk_corp((String) superVO.getAttributeValue("pk_corp"));
		
		//取当前公司对应的客商
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
		if ((UFDate)supervo.getAttributeValue("applydate") != null) {//如果申请日期不为空则用申请日期
			regvo.setGatherdate((UFDate)supervo.getAttributeValue("applydate"));
			regvo.setDapprovedate((UFDate)supervo.getAttributeValue("applydate"));
		} else {
			regvo.setGatherdate((UFDate)supervo.getAttributeValue("doperatedate"));
			regvo.setDapprovedate((UFDate)supervo.getAttributeValue("doperatedate"));
		}
	}

}
