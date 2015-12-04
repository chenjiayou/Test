package nc.bs.pub.action;

import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 
 ***********************************************************
 * 日期：2008-3-17							   
 * 程序员:吴二山 							   
 * 功能：贴现办理单的删除，中心业务同时需要删除新生成的收票登记单						   
 ***********************************************************
 */
public class N_36GG_DELETE extends AbstractCenterOperation {
	/**
	 * N_36GG_DELETE 构造子注解。
	 */
	public N_36GG_DELETE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			DiscountVO headVo = (DiscountVO) vo.m_preValueVo.getParentVO();
			if ((headVo.getPk_discount_app() == null || "".equals(headVo
					.getPk_discount_app().trim()))
					&& FbmBusConstant.BILL_UNISTORAGE.equals(headVo
							.getOpbilltype())) {
				return delUniStorage(vo);
			} else {
				return delPrivacy(vo);
			}
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}
}
