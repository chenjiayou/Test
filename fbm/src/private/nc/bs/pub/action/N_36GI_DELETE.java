package nc.bs.pub.action;

import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 
 ***********************************************************
 * 日期：2008-3-17							   
 * 程序员:吴二山 							   
 * 功能：银行托收单删除						   
 ***********************************************************
 */
public class N_36GI_DELETE extends AbstractCenterOperation {

	/**
	 * N_36GI_DELETE 构造子注解。
	 */
	public N_36GI_DELETE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			CollectionVO headVo = (CollectionVO) vo.m_preValueVo.getParentVO();
			if (headVo.getOpbilltype().equals(FbmBusConstant.BILL_PRIVACY)) {// 私人的业务
				return delPrivacy(vo);
			} else {// 做统管业务
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
