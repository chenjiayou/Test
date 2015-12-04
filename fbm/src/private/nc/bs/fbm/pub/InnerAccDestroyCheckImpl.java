/**
 *
 */
package nc.bs.fbm.pub;

import java.util.Map;

import nc.bs.bd.pub.BDOperateContextObject;
import nc.bs.fbm.gather.FBMPubQueryDAO;
import nc.bs.logging.Logger;
import nc.itf.uap.bd.account.IAccidConst;
import nc.vo.bd.b120.AccidVO;
import nc.vo.bd.service.IBDOperate;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;

/**
 * <p>
 * 内部账户销户时校验是否有未处理完毕的票据业务单据使用此内部账户
 * <p>创建人：lpf
 * <b>日期：2007-12-29
 *
 */
public class InnerAccDestroyCheckImpl implements IBDOperate {

	/**
	 *
	 */
	public InnerAccDestroyCheckImpl() {
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
		if ( IAccidConst.FROZENFLAG_CLOSE == Integer.parseInt(newaccvo.getFrozenflag())) {
			boolean isUnfinished = existUnfishedBill(newaccvo.getPk_accid());
			if(isUnfinished){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000335")/* @res"无法销户，账户<"*/
							+ newaccvo.getAccidname() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000336")/* @res"> 在票据系统中还存在未处理完成的单据！\r\n"*/);
			}
		}
	}

	/**
	 *
	 * <p>
	 * 查询是否存在使用此内部账户的票据
	 * <p>
	 * 作者：lpf
	 * 日期：2007-12-29
	 * @param pk_accid
	 * @return
	 */
	private boolean existUnfishedBill(String pk_accid){
		boolean ret = false;
		ret = queryInnerStorageBill(pk_accid);
		if(ret){
			return ret;
		}

		ret = queryInnerReckonBill(pk_accid);
		if(ret){
			return ret;
		}

		ret = queryInnerReliefBill(pk_accid);
		if(ret){
			return ret;
		}
		return ret;
	}

	/**
	 *
	 * <p>
	 * 票据调剂
	 * <p>
	 * 作者：lpf
	 * 日期：2007-12-29
	 * @param pk_accid
	 * @return
	 */
	private boolean queryInnerReliefBill(String pk_accid) {
		boolean ret = false;
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_relief from fbm_relief where inneracc='"+pk_accid+"' and isnull(dr,0)=0 ");
		sql.append(" and (vbillstatus<>"+String.valueOf(IFBMStatus.HAS_VOUCHER)+" and vbillstatus<>"+String.valueOf(IFBMStatus.HAS_UNIT_VOUCHER)+")");
		FBMPubQueryDAO dao = new FBMPubQueryDAO();
		try {
			Map map = dao.queryMapData(sql.toString());
			if(map!=null&&map.get(ReliefVO.PK_RELIEF)!=null){
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
	 * <p>
	 * 内部托管
	 * <p>
	 * 作者：lpf
	 * 日期：2007-12-29
	 * @param pk_accid
	 * @return
	 */
	private boolean queryInnerStorageBill(String pk_accid) {
		boolean ret = false;
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_storage from fbm_storage where keepaccount='"+pk_accid+"' and isnull(dr,0)=0 ");
		sql.append(" and (unitvoucher<> 'Y'  or centervoucher<> 'Y')");
		FBMPubQueryDAO dao = new FBMPubQueryDAO();
		try {
			Map map = dao.queryMapData(sql.toString());
			if(map!=null&&map.get(StorageVO.PK_STORAGE)!=null){
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
	 * 调剂清算
	 * <p>
	 * 作者：lpf
	 * 日期：2007-12-29
	 * @param pk_accid
	 * @return
	 */
	private boolean queryInnerReckonBill(String pk_accid) {
		boolean ret = false;
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_reckon from fbm_reckon where reckonunitacc='"+pk_accid+"' and isnull(dr,0)=0 ");
		sql.append(" and (vbillstatus<>"+String.valueOf(IFBMStatus.HAS_VOUCHER)+")");
		FBMPubQueryDAO dao = new FBMPubQueryDAO();
		try {
			Map map = dao.queryMapData(sql.toString());
			if(map!=null&&map.get(ReckonVO.PK_RECKON)!=null){
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
	 * 校验账户是否合法
	 * <p>
	 * 作者：lpf
	 * 日期：2007-12-29
	 * @param pk_accid
	 */
	public void checkInneraccValidate(String pk_accid) throws BusinessException{
		AccidVO accidVo = OuterProxy.getInnerAccountService().queryAccountDocByPk(pk_accid);
		if (accidVo == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000337")/* @res"查询不到对应的内部账户，请检查内部账户基本档案 "*/);
		}
		if (IAccidConst.FROZENFLAG_CLOSE == Integer.parseInt(accidVo.getFrozenflag())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000338")/* @res"该业务单据设置的内部账户已经销户，不允许该业务操作"*/);
		}

	}
}