package nc.impl.fbm.gather;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.plan.PlanFacade;
import nc.bs.trade.business.HYPubBO;
import nc.itf.fbm.gather.IGatherService;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.uif.pub.exception.UifException;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

/**
 * TODO ��Ʊ�Ǽǵ� ��̨�ӿ�ʵ����
 *
 * @author zhouwb 2008-9-18
 *
 */
public class GatherServiceImpl implements IGatherService {

	public void executeQxsp(RegisterVO registerVo) throws BusinessException
	{
		if(registerVo == null)
			return;

		//ȡ����ƱǰУ��
		checkQxsp(registerVo);

//		registerVo.setInvoiceplanitem(null);
		registerVo.setSfflag(new UFBoolean(false));
		update(registerVo);

		//ά���ʽ�ƻ�
//		PlanFacade facade = new  PlanFacade();
//		facade.delPlanExec(registerVo, registerVo.getPk_corp());
	}

	public void executeSpwc(RegisterVO registerVo) throws BusinessException
	{
		if(registerVo == null)
			return;

		registerVo.setSfflag(new UFBoolean(true));
		update(registerVo);
		//ά���ʽ�ƻ�
//		PlanFacade facade = new  PlanFacade();
//		facade.insertPlanExec(registerVo, registerVo.getPk_corp());
	}

	/**
	 * TODO �������ݿ�
	 * @param registerVo
	 * @throws UifException
	 */
	private void update(RegisterVO registerVo) throws UifException
	{
		HYPubBO pubbo = new HYPubBO();
		pubbo.update(registerVo);
	}

	/**
	 * TODO ȡ����ƱУ��
	 * @param regVo
	 * @throws BusinessException
	 */
	private void checkQxsp(RegisterVO regVo) throws BusinessException
	{
		//��ѯƱ����ִ��ҵ������Ĵ�����
		String sSql = "select count(1) from fbm_action where pk_source = '" + regVo.getPrimaryKey() + "' and isnull(dr,0)=0";

		Integer actionCount = (Integer)new BaseDAO().executeQuery(sSql, new ColumnProcessor());

		if(actionCount != null && actionCount.intValue() > 2)
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000043")/*@res "Ʊ����ִ�й�����ҵ�������������ȡ����Ʊ��"*/);
	}

}