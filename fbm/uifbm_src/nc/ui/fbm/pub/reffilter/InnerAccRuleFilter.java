package nc.ui.fbm.pub.reffilter;


import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.fbm.accrule.IAccRule;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.accrule.AccRuleVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.tam.account.IAccConst;
import nc.vo.tam.account.accid.AccidVO;

/**
 * �ڲ��ʻ����˹�������У��
 * @author xwq
 *
 * 2008-8-28
 */
public class InnerAccRuleFilter extends BillItemRefModelFilter{

	private String billtype;
	private String accname;
	
	public InnerAccRuleFilter(String billtype,String accname){
		super();
		this.billtype = billtype;
		this.accname = accname;
	}

	@Override
	protected String getSelfFilterString() {
		int acctype = -1 ;
		AccRuleVO vo = null;
		try {
			IAccRule rule = (IAccRule) NCLocator.getInstance().lookup(IAccRule.class.getName());
			vo = rule.retriveAccRef(billtype, accname);

		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
		}
		if(vo == null){
			throw new BusinessRuntimeException("�޷��ҵ��ڲ��˻����˹���");
		}
		if(vo.getAccref().equals(FbmBusConstant.ACCRULE_ACCREF_BILL)){
			acctype = IAccConst.ACCL_BILL;
		}else if(vo.getAccref().equals(FbmBusConstant.ACCRULE_ACCREF_CURRENT)){
			acctype = IAccConst.ACCCL_CURRENT;
		}
		return " " +AccidVO.ACCTYPE + "="+acctype+" and frozenflag in(0,1,2)";
	}

	
}
