package nc.impl.fbm.accrule;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.itf.fbm.accrule.IAccRule;
import nc.vo.fbm.accrule.AccRuleVO;
import nc.vo.pub.BusinessException;

public class AccRuleImpl implements IAccRule {

	public AccRuleVO retriveAccRef(String billtype, String accname)
			throws BusinessException {
		// TODO Auto-generated method stub
		String where = " accrulename='"+billtype+"' and accname='"+accname+"'";
		BaseDAO dao = new BaseDAO();
		List<AccRuleVO> vos = (List<AccRuleVO>)dao.retrieveByClause(AccRuleVO.class, where);
		if(vos == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000180")/*@res "找不到对应的内部帐户入账规则设置"*/);
		}
		if(vos!=null && vos.size() > 1){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000181")/*@res "内部帐户入账规则设置有多个，请修正"*/);
		}
		return vos.get(0);
	}

}