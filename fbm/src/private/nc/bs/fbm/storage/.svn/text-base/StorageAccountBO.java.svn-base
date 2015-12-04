package nc.bs.fbm.storage;

import nc.bs.fbm.accdetail.StorageVOToAccDetail;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.itf.fts.pub.ICommon;
import nc.vo.fbm.pub.FBMPublicQry;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

/**
 * 内部托管和内部领用账户BO类
 * 
 * @author xwq
 * 
 */
public class StorageAccountBO {

	/**
	 * 
	 * @param billvo
	 * @param writeInnerAcc
	 * @param operator
	 * @param currentDate
	 * @throws BusinessException
	 */
	public void account4InnerBack(HYBillVO billvo, UFBoolean writeInnerAcc,
			String operator, UFDate currentDate) throws BusinessException {
		// NCdp200603114
		// 登陆日期小于受理日期时，内部领用单及内部托管单应可以确认，即视同倒起息，按确认日期即登陆日期维护内部账户账。目前不可以

		// //如果确认日期小于受理日期，则报错
		StorageVO storageVO = (StorageVO) billvo.getParentVO();
		UFDate inputdate = storageVO.getInputdate();// 确认日期,即登录日期
		UFDate submitdate = storageVO.getSubmitdate();// 提交日期
		ICommon commonInterface = (ICommon) NCLocator.getInstance().lookup(ICommon.class);
		UFDate dealdate = commonInterface.getAutoProcessDate(FBMPublicQry.retrivePk_centcorpByPkCorp(storageVO.getPk_corp()));

		if (inputdate != null && dealdate != null && inputdate.after(dealdate)) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000030")/*
																												 * @res
																												 * "确认日期不能大于当前受理日期"
																												 */);
		}

		if (submitdate != null
				&& inputdate != null
				&& submitdate.after(inputdate)) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000031")/*
																												 * @res
																												 * "确认日期必须大于提交日期"
																												 */);
		}

		// 记内部账户账
		StorageVOToAccDetail storageSrv = new StorageVOToAccDetail();
		InvocationInfoProxy info = InvocationInfoProxy.getInstance();
		storageVO.setWriteinneracc(writeInnerAcc);
		billvo.setParentVO(storageVO);
		storageSrv.addAccDetail(billvo, info.getCorpCode(), operator, currentDate);

		// 记中心票据账
		StorageUtil storageUtil = new StorageUtil();
		storageUtil.insertBankAcc4Center(billvo, info.getCorpCode(), operator, currentDate);

	}
}
