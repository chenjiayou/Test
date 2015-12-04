package nc.bs.pub.action;

import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：中心背书的删除 单据动作执行中的动态执行类的动态执行类。
 * 
 * 创建日期：(2008-2-27)
 * 
 * @author 平台脚本生成
 */
public class N_36GQ_DELETE extends AbstractCenterOperation {
	/**
	 * N_36GW_DELETE 构造子注解。
	 */
	public N_36GQ_DELETE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;

			EndoreVO checkvo = (EndoreVO) vo.m_preValueVo.getParentVO();
			// checkDelete(checkvo); // 前台背书单校验，由于效率改到后台进行校验.
			
			EndoreVO headVo = (EndoreVO) vo.m_preValueVo.getParentVO();
			// 判断是统管业务还是做的自己的业务：根据票据类型进行判断
			if (headVo.getOpbilltype().equals(FbmBusConstant.BILL_PRIVACY)) {// 私人的业务
				return delPrivacy(vo);
			} else {// 统管业务
				return delUniStorage(vo);
			}
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}
	
}
