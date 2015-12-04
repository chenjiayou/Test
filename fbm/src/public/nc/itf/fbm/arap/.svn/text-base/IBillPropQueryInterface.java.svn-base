package nc.itf.fbm.arap;

import java.util.Map;

import nc.vo.pub.BusinessException;

/**
 * 
 * <p>
 *   提供给收付报接口
 *   查询票据收付属性
 * </p>
 * @author xwq
 * @date 2007-9-12
 * @version V5.0
 */
public interface IBillPropQueryInterface {
	
	/**
	 * 实时查询票据收付属性
	 * 即保存收付款单时调用
	 * 
	 * 规则：如果该票据只有付票登记，没有收票登记，则返回应付
	 * 			否则返回应收
	 * @param pk_corp 
	 * @param billno
	 * @return <单据编号,收付属性值>
	 * @throws BusinessException
	 */
	public Map<String,String> queryBillProp(String pk_corp,String[] billno) throws BusinessException;

	/**
	 * 历史查询票据收付属性
	 * 即生效以后需要查询收付属性时使用
	 * 
	 * 规则：如果对应的外部单据关联是开票付款，则返回应付；否则返回应收
	 * @param pk_corp
	 * @param pk_bill_b
	 * @return <单据编号,收付属性值>
	 * @throws BusinessException
	 */
	public Map<String,String> queryHistoryBillProp(String pk_corp,String[] pk_bill_b) throws BusinessException;
}
