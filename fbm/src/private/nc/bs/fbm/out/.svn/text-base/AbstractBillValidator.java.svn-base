package nc.bs.fbm.out;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.ActionQueryDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.pub.BusinessException;


public abstract class AbstractBillValidator implements IBillValidator {

	protected Set<String> billnoSet = new HashSet<String>();//已背书的票据，防止多条同一票据形成多个收票登记单
	protected Map<String,String> lastEndorePK = new HashMap<String,String>();//记录已生成背书单的PK,key为票据编号，value为背书单PK
	protected BaseDAO baseDao = new BaseDAO();
	protected OuterRelationDAO outRelDao = new OuterRelationDAO();
	protected ActionQueryDAO actionDao = new ActionQueryDAO();
	protected CommonDAO comDao = new CommonDAO();
	

	/**
	 * 在此统一校验，如果对方类型不为0则，抛异常提示对方类型必须为客商
	 */
	public void checkBaseinfo(ArapBillParamVO param) throws BusinessException {
		
//		if (0 != param.getTradertype()) {
//			throw new BusinessException("对方类型必须为客商");
//		}

	}
	
	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		// TODO Auto-generated method stub
		billnoSet.add(param.getFbmbillno());
	}

	public void beforeAction(ArapBillParamVO param) throws BusinessException {
		// TODO Auto-generated method stub
		
	}
	
	
}
