/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.CardEventHandler;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;

/**
 * <p>
 * �ϼƿ�Ƭ�¼�������
 * <p>
 * �������ڣ�2006-9-19
 * 
 * @author lisg
 * @since v5.0
 */
public abstract class TotalCardEventHandler extends CardEventHandler {



	public TotalCardEventHandler(TotalRowBillCardUI billUI, ICardController control) {
		super(billUI, control);
	}
	
	public TotalRowBillCardUI getTotalRowBillCardUI(){
		return (TotalRowBillCardUI)getBillUI();
	}
	

	/**
	 * <p>
	 * �жϵ�ǰ��VO�Ƿ��Ǻϼ�VO
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-19
	 * @param vo
	 * @return
	 */
	public boolean isCountVO(SuperVO vo){
		if(vo == null || !TotalRowUITools.isLegalTotalController(getUIController())) return false;
		
		ITotalSpecify ts = getTotalSpecifyController();
		TotalRowPara trp = ts.getTotalRowPara(vo.getClass().getName());
		if(trp == null) return false;
		return trp.isCountVO(vo);
	}
	
	/**
	 * <p>
	 * ���غϼƷ��ദ�������,���������Ǹ�����ʱ,����null
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-19
	 * @return
	 */
	public ITotalSpecify getTotalSpecifyController(){
		if(getUIController() instanceof ITotalSpecify){
			return (ITotalSpecify)getUIController();
		}
		
		return null;
	}
	
	/**
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-20
	 *
	 * @see nc.ui.trade.bill.BillEventHandler#doBodyQuery(java.lang.String)
	 */
	@Override
	protected void doBodyQuery(String strWhere) throws Exception, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		SuperVO[] queryVos = getBusiDelegator().queryByCondition(
				Class.forName(getUIController().getBillVoName()[2]),
				strWhere == null ? "" : strWhere);
		
		reSetData(queryVos);
	}
	
	
	/**
	 * <p>
	 * ֱ���������ݣ��������ֱ�Ӹ���buffer�������кϼ��еĴ���
	 * ���Ҫʹ�úϼ��д�����ʹ��reSetRowVOs(...)
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-8
	 * @param queryVos
	 * @throws Exception
	 */
	protected void reSetData(SuperVO[] queryVos) throws Exception{
		
		getBufferData().clear();

		AggregatedValueObject vo = (AggregatedValueObject) Class.forName(
				getUIController().getBillVoName()[0]).newInstance();
		vo.setChildrenVO(queryVos);
		getBufferData().addVOToBuffer(vo);
		
		//ִ��buffer����
		doUpdateBuffer(CommonUtil.isNull(queryVos));
	}
	
	/**
	 * <p>
	 * ����Buffer�Ĳ���,Ĭ��ʹ��ϵͳ����
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-20
	 * @throws Exception
	 */
	protected void doUpdateBuffer(boolean isNull)throws Exception{
		updateBuffer();
	}
	
	/**
	 * <p>
	 * ����������VO,�ú������Զ����ɺϼ��У�ע�⴫��Ĳ���VO�������ϼ���
	 * ��������Ѿ���֯�õ�VO���飬��ʹ��reSetData(...)��������Buffer
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-8
	 * @param vos
	 * @throws Exception
	 */
	protected void reSetRowVOs(SuperVO[] vos) throws Exception{
		
		vos = (SuperVO[])((TotalRowBusinessDelegator)getBillUI().getBusiDelegator()).OrganizeTotalVO(vos);
		
		//2.������������
		reSetData(vos);
		
	}
}
