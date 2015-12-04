package nc.bs.fbm.plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.itf.fp.pub.IFIOPlanGetPerformDetail;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.fp.pub.IOBudgetQueryVO;
import nc.vo.fp.pub.IOPlanGridVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.IBillStatus;

/**
 * 资金计划启动时读取
 * @author xwq
 *
 * 2008-12-18
 */
public class FBMIOPlanGetInitData implements IFIOPlanGetPerformDetail{

	public IOBudgetQueryVO[] getIOPlanPerformDetail(IOPlanGridVO ioplangridvo)
			throws BusinessException {
		UFDate begDate = ioplangridvo.getGridstartdate();
		UFDate endDate = ioplangridvo.getGridenddate();
		String pk_corp = ioplangridvo.getPk_corp();
		String curType = ioplangridvo.getPk_currtype();
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_corp);
		parameter.addParam(curType);
		parameter.addParam(begDate);
		parameter.addParam(endDate);
		
		List<SuperVO> busivoList = new ArrayList<SuperVO>();
		
		List<SuperVO> gather = queryGather(parameter);
		if(gather !=null && gather.size()>0){
			busivoList.addAll(gather);
		}
		
		List<SuperVO> invoice = queryInvoice(parameter);
		if(invoice !=null && invoice.size()>0){
			busivoList.addAll(invoice);
		}
		
		List<SuperVO> accept = queryAccept(parameter);
		if(accept !=null && accept.size() > 0){
			busivoList.addAll(accept);
		}
		
		List<SuperVO> discount = queryDiscount(parameter);
		if(discount!=null && discount.size() > 0){
			busivoList.addAll(discount);
		}
		
		List<SuperVO> collection = queryCollection(parameter);
		if(collection!=null && collection.size() > 0){
			busivoList.addAll(collection);
		}
		
		List<SuperVO> storage = queryStorage(parameter);
		if(storage!=null && storage.size()>0){
			busivoList.addAll(storage);
		}
		
		List<SuperVO> returnLst = queryReturn(parameter);
		if(returnLst!=null && returnLst.size()>0){
			busivoList.addAll(returnLst);
		}
		
		List<IOBudgetQueryVO> retList = new ArrayList<IOBudgetQueryVO>();
		
		//增加付票已核销数据。
		List<SuperVO> destroyList = queryDestroyInvoice(parameter);
		if(destroyList!=null && destroyList.size()>0){
			for(int j=0;j<destroyList.size();j++){
				IOBudgetQueryVO[] iobudgetvos = FbmBill2PlanAdapter.bill2PlanVO(destroyList.get(j),"DESTROY");
				if(iobudgetvos!=null && iobudgetvos.length > 0){
					retList.addAll(Arrays.asList(iobudgetvos));
				}
			}
		}
		
		int len = busivoList.size();
		
		if(len>0){
			
			for(int i = 0; i < len; i++){
				IOBudgetQueryVO[] iobudgetvos = FbmBill2PlanAdapter.bill2PlanVO(busivoList.get(i),null);
				if(iobudgetvos!=null && iobudgetvos.length > 0){
					retList.addAll(Arrays.asList(iobudgetvos));
				}
			}
			
		}
		if(retList!=null && retList.size()>0){
			return retList.toArray(new IOBudgetQueryVO[0]);
		}else
		{
			return null;
		}
		
	}
	

	public IOBudgetQueryVO[] getRealIOPlanPerformDetail(
			IOPlanGridVO ioplangridvo) throws BusinessException {
		return getIOPlanPerformDetail(ioplangridvo);
	}

	/**
	 * 查询收票数据 
	 */
	private List<SuperVO> queryGather(SQLParameter parameter) throws BusinessException{
		String sql = "select r.* from fbm_register r left join fbm_baseinfo b on b.pk_baseinfo=r.pk_baseinfo " +
				" where pk_billtypecode='36GA' and r.pk_corp=? and b.pk_curr=? " +
				" and r.gatherdate >=? and r.gatherdate <=? " +
				" and r.vbillstatus="+IBillStatus.CHECKPASS;
		BaseDAO dao = new BaseDAO();
		return (List<SuperVO>)dao.executeQuery(sql, parameter, new BeanListProcessor(RegisterVO.class));
	}
	
	
	/**
	 * 查询开票数据 
	 */
	private List<SuperVO> queryInvoice(SQLParameter parameter) throws BusinessException{
		String sql = "select r.* from fbm_baseinfo b left join fbm_register r on b.pk_baseinfo=r.pk_baseinfo " +
				" where pk_billtypecode='36GL' " +
				//" and not exists(select 1 from fbm_action where fbm_action.pk_baseinfo = r.pk_baseinfo " +
				//" and billtype='36GL' and actioncode ='DESTROY') " +
				" and r.pk_corp=? and b.pk_curr=? and b.invoicedate >=? and b.invoicedate <=? " +
				" and r.vbillstatus="+IBillStatus.CHECKPASS;
		BaseDAO dao = new BaseDAO();
		return (List<SuperVO>)dao.executeQuery(sql, parameter, new BeanListProcessor(RegisterVO.class));
	}
	
	/**
	 * 查询已核销的付票数据。
	 * @param parameter
	 * @return
	 * @throws BusinessException
	 */
	private List<SuperVO> queryDestroyInvoice(SQLParameter parameter) throws BusinessException{
		String sql = "select r.* from fbm_register r  left join fbm_baseinfo b on b.pk_baseinfo=r.pk_baseinfo " +
				" where pk_billtypecode='36GL' " +
				" and exists(select 1 from fbm_action where fbm_action.pk_baseinfo = r.pk_baseinfo " +
				" and billtype='36GL' and actioncode ='DESTROY') and r.pk_corp=? and b.pk_curr=? " +
				" and b.invoicedate >=? and b.invoicedate <=?  and r.vbillstatus="+IBillStatus.CHECKPASS;
		BaseDAO dao = new BaseDAO();
		return (List<SuperVO>)dao.executeQuery(sql, parameter, new BeanListProcessor(RegisterVO.class));
	}
	
	/**
	 * 承兑
	 * @param parameter
	 * @return
	 * @throws BusinessException
	 */
	private List<SuperVO> queryAccept(SQLParameter parameter) throws BusinessException{
		String sql = "select a.* from fbm_accept a left join fbm_baseinfo b on b.pk_baseinfo = a.pk_baseinfo " +
				" where a.pk_corp=? and b.pk_curr=? and a.dacceptdate >=? and a.dacceptdate<=? " +
				" and a.vbillstatus="+IBillStatus.CHECKPASS;
		BaseDAO dao = new BaseDAO();
		return (List<SuperVO>)dao.executeQuery(sql, parameter, new BeanListProcessor(AcceptVO.class));
	}
	
	/**
	 * 贴现办理单
	 * @param parameter
	 * @return
	 * @throws BusinessException
	 */
	private List<SuperVO> queryDiscount(SQLParameter parameter) throws BusinessException{
		String sql = "select d.* from fbm_discount d left join fbm_baseinfo b on d.pk_baseinfo= b.pk_baseinfo " +
				" where d.pk_billtypecode = '36GG' and d.pk_corp=? and b.pk_curr=? " +
				" and d.ddiscountdate>=? and d.ddiscountdate<=? and d.result ='success' " +
				" and d.vbillstatus="+IBillStatus.CHECKPASS;
		BaseDAO dao = new BaseDAO();
		return (List<SuperVO>)dao.executeQuery(sql, parameter, new BeanListProcessor(DiscountVO.class));
	}
	
	/**
	 * 托收办理单
	 * @param parameter
	 * @return
	 * @throws BusinessException
	 */
	private List<SuperVO> queryCollection(SQLParameter parameter) throws BusinessException{
		String sql = "select c.* from fbm_collection c left join fbm_baseinfo b on c.pk_baseinfo=b.pk_baseinfo" +
				"  where c.pk_corp=? and b.pk_curr=? and c.dtransactdate >=? " +
				" and c.dtransactdate<=? and c.vbillstatus="+IFBMStatus.Transact;
		BaseDAO dao = new BaseDAO();
		return (List<SuperVO>)dao.executeQuery(sql, parameter, new BeanListProcessor(CollectionVO.class));
	}
	
	/**
	 * 内部托管单、内部领用单
	 * @param parameter
	 * @return
	 * @throws BusinessException
	 */
	private List<SuperVO> queryStorage(SQLParameter parameter) throws BusinessException{
		String sql = "select * from fbm_storage where keepcorp=? and pk_currtype=? " +
				"and dapprovedate>=? and dapprovedate<=? and pk_billtypecode in('36GD','36GE') and ("
//				+" vbillstatus="
//				+ IFBMStatus.SUBMIT
//				+ " or vbillstatus="
//				+ IFBMStatus.INPUT_SUCCESS
//				+ " or vbillstatus="
//				+ IFBMStatus.RETURNED
//				+ " or vbillstatus="
//				+ IFBMStatus.OUTPUT_SUCCESS
//				+ " or vbillstatus="
//				+ IFBMStatus.CHECKPASS 
				+ " unittally = 'Y'"
				+ ")";
		BaseDAO dao = new BaseDAO();
		return (List<SuperVO>)dao.executeQuery(sql, parameter, new BeanListProcessor(StorageVO.class));
	}
	
	/**
	 * 退票
	 * @param parameter
	 * @return
	 * @throws BusinessException
	 */
	private List<SuperVO> queryReturn(SQLParameter parameter)
			throws BusinessException {
		String sql = "select r.* from fbm_return r where pk_corp=? " +
				" and exists(select 1 from fbm_return_b left join fbm_baseinfo " +
				" on fbm_return_b.pk_baseinfo = fbm_baseinfo.pk_baseinfo " +
				" where fbm_return_b.pk_return=r.pk_return and fbm_baseinfo.pk_curr=?) " +
				" and dreturndate>=? and dreturndate<=? and r.returntype <> 'endore' " +
				" and ((r.vbillstatus="
				+ IFBMStatus.TRANSFORM
				+ " or r.vbillstatus="
				+ IFBMStatus.CHECKPASS
//				+ ") and r.returntype='"
//				+ FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT + "')";
				+ "))";
		BaseDAO dao = new BaseDAO();
		return (List<SuperVO>) dao.executeQuery(sql, parameter,
				new BeanListProcessor(ReturnVO.class));
	}
}

