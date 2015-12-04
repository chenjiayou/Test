package nc.impl.fbm.impawn;

import nc.bs.dao.DAOException;
import nc.bs.fbm.impawn.ImpawnDAO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.logging.Logger;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

/**
 *
 * 功能：
  	提供给物权担保系统调用的Service：质押回收
 * 日期：2007-10-15
 * 程序员：wues
 */
public class ImpawnServiceImpl implements nc.itf.fbm.impawn.IImpawnService {

	public ImpawnServiceImpl() {
	}

	/**
	 * 质押回收 参数fiImpawnVO：物权担保VO 目前需要参数：质押单PK，回收人，回收日期。
	 */
	public void impawnBack(nc.vo.fi.impawn.ImpawnVO fiImpawnVO)
			throws BusinessException {
		// 物权担保为空或者外系统主键为空均直接返回即可。
		if (null == fiImpawnVO || null == fiImpawnVO.getPk_othersys()
				|| "".equals(fiImpawnVO.getPk_othersys().trim())) {
			return;
		}
		// 取质押单PK，回收人，回收日期进行质押回收操作
		impawnBack(fiImpawnVO.getPk_othersys(), fiImpawnVO.getUneffectperson(),fiImpawnVO.getUneffectdate());
	}

	/**
	 * 调用dao进行质押回收操作s
	 *
	 * @param pk_impawn
	 * @param backPerson
	 * @param backDate
	 * @throws BusinessException
	 */
	private void impawnBack(String pk_impawn, String backPerson, UFDate backDate)
			throws BusinessException {
		ImpawnDAO dao = new ImpawnDAO();
		try {
			ImpawnVO newVO = dao.getImpawnVO(pk_impawn);
			//如果查询到此单据已经被回收直接返回
			if (null == newVO || IFBMStatus.IMPAWN_BACK == newVO.getVbillstatus()) {
				return;
			}
			newVO.setImpawnbackdate(backDate);
			newVO.setImpawnbackunit(backPerson);

			// 根据impawnVO构造为HYBillVO返回，同时进行质押回收的Action处理,进行质押回收操作
			impawnBackAction(getHYBillVO(newVO));
		} catch (DAOException e) {
			Logger.error(e);
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201040","UPP36201040-000017")/* @res"数据库操作异常"*/);
		} catch (Exception e) {
			Logger.error(e);
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201040","UPP36201040-000018")/* @res"进行票据系统相应质押回收时异常"*/);
		}
	}

	/**
	 * 将impawnVO构造成HYBillVO
	 *
	 * @param impawnVO
	 * @return
	 */
	private HYBillVO getHYBillVO(ImpawnVO impawnVO) {
		HYBillVO retBillVO = new HYBillVO();
		retBillVO.setParentVO(impawnVO);
		return retBillVO;
	}

	/**
	 * 质押回收Action处理
	 *
	 * @param billVO
	 * @throws BusinessException
	 */
	private void impawnBackAction(HYBillVO billVO) throws BusinessException {
//		ActionParamVO[] params = DefaultParamAdapter.changeImpawnParam(billVO,
//				FbmActionConstant.IMPAWNBACK);
//		FbmActionFactory.getInstance().createActionClass(
//				FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.IMPAWNBACK)
//				.doAction(params);
		
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.IMPAWNBACK).doAction(billVO, FbmActionConstant.IMPAWNBACK,false);

		
	}
}