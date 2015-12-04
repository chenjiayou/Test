package nc.vo.fbm.pub;

import java.util.List;

import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.HYBillVO;


/**
 * 票据动作公用查询类
 * @author xwq
 *
 * 2008-11-6
 */
public class FBMActionQuery {
	
	private static FBMActionQuery query = null;
	
	public static FBMActionQuery getInstance(){
		if(query == null){
			query = new FBMActionQuery();
		}
		return query;
	}
	
	/**
	 * 获得收票的最新状态
	 * @param pk_register
	 * @return
	 * @throws BusinessException
	 */
	public String queryCurrentStatus(String pk_register) throws BusinessException{
		ActionVO actionvo = queryCurrentActionVO(pk_register);
		if(actionvo !=null ){
			return actionvo.getEndstatus();
		}
		return null;
	}
	/**
	 * 获得当前收票的单据对象
	 * @param pk_register
	 * @return
	 * @throws BusinessException
	 */
	public HYBillVO queryCurrentVO(String pk_register) throws BusinessException{
		ActionVO actionvo = queryCurrentActionVO(pk_register);
		HYBillVO billvo = new HYBillVO();
		if(actionvo !=null){
			String pk_billtype =actionvo.getBilltype();
			if(pk_billtype.equals(FbmBusConstant.BILLTYPE_BANKKEEP)){
				StorageVO vo = (StorageVO)FBMProxy.getUAPQuery().retrieveByPK(StorageVO.class, actionvo.getPk_bill());
				billvo.setParentVO(vo);
			}
		}
		return billvo;
	}
	
	/**
	 * 查询最新动作
	 * @param pk_register
	 * @return
	 * @throws BusinessException
	 */
	public ActionVO queryCurrentActionVO(String pk_register) throws BusinessException{
		List ret = (List)FBMProxy.getUAPQuery().retrieveByClause(ActionVO.class, "pk_source = '"+pk_register +"' order by serialnum desc");
		
		if(ret !=null && ret.size() > 0 ){
			return (ActionVO) ret.get(0);
		}
		return null;
	}
	/**
	 * 查询指定单据类型的最新动作
	 * @param pk_register
	 * @param pk_billtypecode
	 * @return
	 * @throws BusinessException
	 */
	public ActionVO queryLastBillTypeAction(String pk_baseinfo,String pk_billtypecode) throws BusinessException{
		List ret = (List)FBMProxy.getUAPQuery().retrieveByClause(ActionVO.class, "pk_baseinfo = '"+pk_baseinfo +"' and billtype='"+pk_billtypecode+"' order by serialnum desc");
		
		if(ret !=null && ret.size() > 0 ){
			return (ActionVO) ret.get(0);
		}
		return null;
	}
	
	/**
	 * 查询指定单据类型的单据对象
	 * @param pk_register
	 * @param pk_billtypecode
	 * @return
	 * @throws BusinessException
	 */
	public HYBillVO queryLastBillTypeBillVO(String pk_baseinfo,String pk_billtypecode) throws BusinessException{
		ActionVO actionvo = queryLastBillTypeAction(pk_baseinfo,pk_billtypecode);
		
		if(actionvo !=null){
			String pk_billtype =actionvo.getBilltype();
			if(pk_billtype.equals(FbmBusConstant.BILLTYPE_INNERKEEP)){
				return (HYBillVO)FBMProxy.getUifService().queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
						StorageVO.class.getName(), StorageBVO.class.getName()}, actionvo.getPk_bill());
			}
		}
		return null;
	}
}
