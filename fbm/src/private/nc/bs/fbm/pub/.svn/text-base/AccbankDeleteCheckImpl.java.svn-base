/**
 *
 */
package nc.bs.fbm.pub;

import java.util.Map;

import nc.bs.fbm.gather.FBMPubQueryDAO;
import nc.bs.logging.Logger;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CommonUtil;
import nc.uif.pub.exception.UifException;
import nc.vo.bd.b23.AccbankVO;
import nc.vo.bd.service.IBDOperate;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.SuperVO;

/**
 * <p>
 * 开户银行删除校验
 * <p>创建人：lpf
 * <b>日期：2008-1-4
 *
 */
public class AccbankDeleteCheckImpl implements IBDOperate {

	/**
	 * 
	 */
	public AccbankDeleteCheckImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.vo.bd.service.IBDOperate#afterOperate(java.lang.String, int, java.lang.String, java.lang.String, java.lang.Object)
	 */
	public void afterOperate(String fun_code, int opType, String pk1,
			String pk2, Object bd_docData) throws BusinessException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nc.vo.bd.service.IBDOperate#beforeOperate(java.lang.String, int, java.lang.String, java.lang.String, java.lang.Object)
	 */
	public void beforeOperate(String fun_code, int opType, String pk1,
			String pk2, Object bd_docData) throws BusinessException {
		AccbankVO newvo =(AccbankVO)bd_docData;
		
		String pk_accbank = newvo.getPk_accbank();
		boolean unfinished = checkUnfished(pk_accbank);
		if(unfinished){
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000386")/*无法完成删除开户银行档案操作，还存在引用此基本档案的票据系统单据*/);
		}
	}

	/**
	 * 
	 * <p>
	 * 
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-4
	 * @param pk_accbank
	 * @return
	 * @throws BusinessException 
	 */
	private boolean checkUnfished(String pk_accbank) throws BusinessException {
		boolean ret = false;
		if(CommonUtil.isNull(pk_accbank)){
			return ret;
		}
		ret = queryRegisterApply(pk_accbank);
		if(ret){
			return ret;
		}
		
		ret = queryCollectionApply(pk_accbank,CollectionVO.HOLDERACC);
		if(ret){
			return ret;
		}
		ret = queryAcceptApply(pk_accbank);
		if(ret){
			return ret;
		}
		
		ret = queryDiscountApply(pk_accbank);
		if(ret){
			return ret;
		}
		return ret;
	}

	/**
	 * 
	 * <p>
	 * 收票付票表引用
	 * fbm_register: 保证金帐号	securityaccount	char(20) 贷款银行	pk_loanbank	char(20)
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-4
	 * @param pk_accbank
	 * @return
	 * @throws BusinessException 
	 * @throws BusinessRuntimeException 
	 */
	private boolean queryRegisterApply(String pk_accbank) {
		boolean ret = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_register from fbm_register left join fbm_baseinfo ");
		sql.append(" on(fbm_register.pk_baseinfo=fbm_baseinfo.pk_baseinfo) where (securityaccount='"+pk_accbank+"' ");
		sql.append(" or pk_loanbank='"+pk_accbank+"' or receivebankacc='"+pk_accbank+"' ");
		sql.append(" or paybankacc='"+pk_accbank+"' ) ");
//		sql.append(" and ((pk_billtypecode='36GA') or (pk_billtypecode='36GL' and vbillstatus<>"+String.valueOf(IFBMStatus.Create)+")) ");
		sql.append(" and isnull(fbm_register.dr,0)=0 and isnull(fbm_baseinfo.dr,0)=0 ");
		FBMPubQueryDAO dao = new FBMPubQueryDAO();
		try {
			Map map= dao.queryMapData(sql.toString());
			if(map!=null&&map.get(RegisterVO.PK_REGISTER)!=null){
				ret = true;
			}
		} catch (BusinessRuntimeException e) {
			Logger.error(e.getMessage(),e);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
		}
		return ret;
	}
	
	/**
	 * 
	 * <p>
	 * 付款
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-4
	 * @param pk_accbank
	 * @return
	 * @throws BusinessException
	 */
	private boolean queryAcceptApply(String pk_accbank){
		boolean ret = false;
		StringBuffer sql = new StringBuffer();
		sql.append(" isnull(dr,0)=0 and ("+AcceptVO.HOLDERACC+"='"+pk_accbank+"' or "+AcceptVO.BACKSECACCOUNT+"='"+pk_accbank+"')");
		try {
			SuperVO[] queryVos = new HYPubBO().queryByCondition(AcceptVO.class, sql.toString());
			if(!CommonUtil.isNull(queryVos)){
				ret = true;
			}
		} catch (UifException e) {
			Logger.error(e.getMessage(),e);
		}
		return ret;
	}
	
	/**
	 * 
	 * <p>
	 * 贴现
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-4
	 * @param pk_accbank
	 * @return
	 * @throws BusinessException
	 */
	private boolean queryDiscountApply(String pk_accbank){
		boolean ret = false;
		StringBuffer sql = new StringBuffer();
		sql.append(" isnull(dr,0)=0 and "+DiscountVO.DISCOUNT_ACCOUNT+"='"+pk_accbank+"'");
		try {
			SuperVO[] queryVos = new HYPubBO().queryByCondition(DiscountVO.class, sql.toString());
			if(!CommonUtil.isNull(queryVos)){
				ret = true;
			}
		} catch (UifException e) {
			Logger.error(e.getMessage(),e);
		}
		return ret;
	}
	
	/**
	 * 
	 * <p>
	 * 托收
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-4
	 * @param classname
	 * @param pk_accbank
	 * @param itemkey
	 * @return
	 * @throws BusinessException
	 */
	private boolean queryCollectionApply(String pk_accbank,String itemkey) {
		boolean ret = false;
		StringBuffer sql = new StringBuffer();
		sql.append(" isnull(dr,0)=0 and "+itemkey+"='"+pk_accbank+"' ");
		try {
			SuperVO[] queryVos = new HYPubBO().queryByCondition(CollectionVO.class, sql.toString());
			if(!CommonUtil.isNull(queryVos)){
				ret = true;
			}
		} catch (UifException e) {
			Logger.error(e.getMessage(),e);
		}
		return ret;
	}
}
