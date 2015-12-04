/**
 *
 */
package nc.ui.fbm.gather.reffilter;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;

/**
 * <p>
 * 收票登记票据编号的参照过滤器
 * 状态为已背书的应收票据+已付票应付票据
 * <p>创建人：lpf
 * <b>日期：2007-8-27
 *
 */
public class BaseInfoRefModelFilter extends BillItemRefModelFilter {

	public BaseInfoRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	@Override
	//把action过滤条件去掉放在参照，否则效率太低
	protected String getSelfFilterString() {

		//2009-1-17 xwq 去掉公司限制将所有可能被参照到的票据都查出来
//		return "exists(select 1 from fbm_register  where fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo   and (registerstatus='" + FbmStatusConstant.HAS_ENDORE
//		+ "' or registerstatus='" + FbmStatusConstant.HAS_PAYBILL + "' or registerstatus='" + FbmStatusConstant.HAS_RETURN+"') and sfflag = 'Y') ";
//		
		return "(" + getReturnFilter() + " or " + getHaspayFilter() + " or " +getEndoreFilter()+ ")" ;
	}
	
	//新增过滤条件：已退票&退票类型＝收票退票
	private String getReturnFilter(){
		return "(baseinfostatus='"+FbmStatusConstant.HAS_RETURN+"' and exists (select 1 from fbm_return join fbm_return_b on fbm_return.pk_return = fbm_return_b.pk_return  where fbm_return_b.pk_baseinfo=fbm_baseinfo.pk_baseinfo  and returntype='"+FbmBusConstant.BACK_TYPE_GATHER+"'))";
	}
	//新增过滤条件：已付票
	private String getHaspayFilter(){
		return "(baseinfostatus='"+FbmStatusConstant.HAS_PAYBILL+"' and exists(select 1 from bd_cubasdoc   where pk_cubasdoc = receiveunit ))";
	}
	//新增过滤条件：已背书&被背书单位=外部客商
	private String getEndoreFilter(){
		return "(baseinfostatus='"+FbmStatusConstant.HAS_ENDORE+"' and exists(select 1 from fbm_endore join bd_cubasdoc on pk_cubasdoc = endorsee where fbm_endore.pk_baseinfo=fbm_baseinfo.pk_baseinfo  and custprop=0))";
	}
}
