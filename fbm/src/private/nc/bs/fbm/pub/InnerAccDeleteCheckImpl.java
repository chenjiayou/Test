/**
 *
 */
package nc.bs.fbm.pub;

import nc.bs.bd.pub.BDOperateContextObject;
import nc.bs.logging.Logger;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CommonUtil;
import nc.uif.pub.exception.UifException;
import nc.vo.bd.b120.AccidVO;
import nc.vo.bd.service.IBDOperate;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2008-1-4
 *
 */
public class InnerAccDeleteCheckImpl implements IBDOperate {

	/**
	 * 
	 */
	public InnerAccDeleteCheckImpl() {
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
		BDOperateContextObject context = (BDOperateContextObject) bd_docData;
		AccidVO newaccvo = (AccidVO) context.getNewVO();
		boolean isUnfinished = existUnfishedBill(newaccvo.getPk_accid());
		if(isUnfinished){
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000387")/*无法删除，账户*/
						+ newaccvo.getAccidname() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000336")/* @res"> 在票据系统中还存在未处理完成的单据！\r\n"*/);
		}
	}

	/**
	 * 
	 * <p>
	 * 校验是否存在未完成的单据
	 * <p>
	 * 作者：lpf
	 * 日期：2008-1-4
	 * @param pk_accid
	 * @return
	 */
	private boolean existUnfishedBill(String pk_accid) {
		boolean ret = false;		
		ret = queryBillVos(ReckonVO.class,ReckonVO.INACC,pk_accid);
		if(ret)
			return ret;
		
		ret = queryBillVos(ReckonVO.class,ReckonVO.OUTACC,pk_accid);
		if(ret)
			return ret;
		
		ret = queryBillVos(StorageVO.class,StorageVO.KEEPACCOUNT,pk_accid);
		if(ret)
			return ret;
		
		ret = queryBillVos(ReliefVO.class,ReliefVO.INNERACC,pk_accid);
		if(ret)
			return ret;
		return ret;
	}
	
	private boolean queryBillVos(Class classname,String itemkey,String pk_accid) {
		boolean ret = false;
		String sql = " isnull(dr,0)=0 and "+itemkey+"='"+pk_accid+"' ";
		try {
			SuperVO[] queryVos = new HYPubBO().queryByCondition(classname, sql);
			if(!CommonUtil.isNull(queryVos)){
				ret = true;
			}
		} catch (UifException e) {
			Logger.error(e.getMessage(),e);
		}
		
		return ret;
	}

//	private boolean queryStorageBillVos(String pk_accid){
//		boolean ret = false;
//		String sql = " isnull(dr,0)=0 and "+StorageVO.KEEPACCOUNT+"='"+pk_accid+"' ";
//		try {
//			SuperVO[] queryVos = new HYPubBO().queryByCondition(StorageVO.class, sql);
//			if(!CommonUtil.isNull(queryVos)){
//				ret = true;
//			}
//		} catch (UifException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return ret;
//	}

}
