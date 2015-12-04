/**
 *
 */
package nc.bs.fbm.upgrade;

import java.util.List;
import java.util.Map;

import nc.bs.fbm.gather.FBMPubQueryDAO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.uap.bd.accbank.IGOAUsed;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

/**
 * <p>
 * 票据系统用于查询开户银行集团自有账号支付情况的实现类
 * <p>创建人：lpf
 * <b>日期：2008-1-17
 *
 */
public class FBMAccbankUpdateImpl implements IGOAUsed {
	private static String[] accbankField = new String[]{"pk_accbank","pk_corp"};

	/**
	 * 
	 */
	public FBMAccbankUpdateImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.itf.uap.bd.accbank.IGOAUsed#getCoprsOfGOAUsed(java.util.List)
	 */
	public Map<String, List<String>> getCoprsOfGOAUsed(List<String> goaPkList)
			throws BusinessException {
		if(null==goaPkList||goaPkList.size()==0)
			return null;
		Map<String, List<String>> mapList=null;
		String pksql = CommonUtil.buildSqlConditionForIn(goaPkList.toArray(new String[]{}));

		mapList = queryForRegister(mapList,pksql,BaseinfoVO.PAYBANKACC);
//		mapList = queryForRegister(mapList,pksql,BaseinfoVO.RECEIVEBANKACC);
		mapList = queryForDiscount(mapList,pksql);
		mapList = queryForCollection(mapList,pksql);
		mapList = queryForAccept(mapList,pksql,AcceptVO.BACKSECACCOUNT);
		mapList = queryForAccept(mapList,pksql,AcceptVO.HOLDERACC);
		mapList = queryForInvoice(mapList, pksql);
		return mapList;
	}

	/**
	 * <p>
	 * 开票保证金账号
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-17
	 * @param mapList
	 * @param pksql
	 * @return
	 */
	private Map<String, List<String>> queryForInvoice(Map<String, List<String>> mapList, String pksql) {
		String sql = " select distinct "+ RegisterVO.SECURITYACCOUNT+" pk_accbank,"+ RegisterVO.PK_CORP+" pk_corp from " 
					+" fbm_register where "+RegisterVO.PK_BILLTYPECODE+"='"+FbmBusConstant.BILLTYPE_INVOICE+"' and "
					+ RegisterVO.SECURITYACCOUNT+" in("+pksql+") and isnull(dr,0)=0";
		mapList = new FBMPubQueryDAO().queryMapwithKeyData(mapList,sql, accbankField);
		return mapList;
	}

	/**
	 * <p>
	 * 付款
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-17
	 * @param mapList 
	 * @param pksql
	 */
	private Map<String, List<String>> queryForAccept(Map<String, List<String>> mapList, String pksql,String itemkey) {
		String sql = " select distinct "+itemkey+" pk_accbank,"+AcceptVO.PK_CORP+" pk_corp from fbm_accept " 
					+" where "+itemkey+" in("+pksql+") and isnull(dr,0)=0 ";
		mapList = new FBMPubQueryDAO().queryMapwithKeyData(mapList,sql, accbankField);
		return mapList;
	}

	/**
	 * <p>
	 * 托收
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-17
	 * @param pksql
	 * @return
	 */
	private Map<String, List<String>> queryForCollection(Map<String, List<String>> mapList,String pksql) {
		String sql = " select "+CollectionVO.HOLDERACC+" pk_accbank,"+CollectionVO.PK_CORP+" pk_corp from " 
					+" fbm_collection where "+CollectionVO.HOLDERACC+" in("+pksql+") and isnull(dr,0)=0 ";
		mapList = new FBMPubQueryDAO().queryMapwithKeyData(mapList,sql, accbankField);
		return mapList;
	}

	/**
	 * <p>
	 * 贴现
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-17
	 * @param pksql
	 * @return
	 */
	private Map<String, List<String>> queryForDiscount(Map<String, List<String>> mapList,String pksql) {
		String sql = " select distinct "+DiscountVO.DISCOUNT_ACCOUNT+" pk_accbank,"+DiscountVO.PK_CORP+" pk_corp from " 
			+" fbm_discount where "+DiscountVO.DISCOUNT_ACCOUNT+" in("+pksql+") and isnull(dr,0)=0 ";
		mapList = new FBMPubQueryDAO().queryMapwithKeyData(mapList,sql, accbankField);
		return mapList;
	}

	/**
	 * <p>
	 * 开票
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-17
	 * @param pksql
	 * @return
	 */
	private Map<String, List<String>> queryForRegister(Map<String, List<String>> mapList,String pksql,String itemkey) {		
		String sql = " select distinct "+ itemkey+ " pk_accbank,"+ RegisterVO.PK_CORP + " pk_corp from fbm_register left join fbm_baseinfo on" 
				+" (fbm_register.pk_baseinfo=fbm_baseinfo.pk_baseinfo and isnull(fbm_register.dr,0)=0 and isnull(fbm_baseinfo.dr,0)=0 and fbm_register."+RegisterVO.PK_BILLTYPECODE+"='"+FbmBusConstant.BILLTYPE_INVOICE+"') " 
				+" where "+ itemkey+ " in("+ pksql + ") ";
		mapList = new FBMPubQueryDAO().queryMapwithKeyData(mapList,sql, accbankField);
		return mapList;
	}

}
