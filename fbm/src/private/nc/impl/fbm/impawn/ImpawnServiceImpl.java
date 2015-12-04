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
 * ���ܣ�
  	�ṩ����Ȩ����ϵͳ���õ�Service����Ѻ����
 * ���ڣ�2007-10-15
 * ����Ա��wues
 */
public class ImpawnServiceImpl implements nc.itf.fbm.impawn.IImpawnService {

	public ImpawnServiceImpl() {
	}

	/**
	 * ��Ѻ���� ����fiImpawnVO����Ȩ����VO Ŀǰ��Ҫ��������Ѻ��PK�������ˣ��������ڡ�
	 */
	public void impawnBack(nc.vo.fi.impawn.ImpawnVO fiImpawnVO)
			throws BusinessException {
		// ��Ȩ����Ϊ�ջ�����ϵͳ����Ϊ�վ�ֱ�ӷ��ؼ��ɡ�
		if (null == fiImpawnVO || null == fiImpawnVO.getPk_othersys()
				|| "".equals(fiImpawnVO.getPk_othersys().trim())) {
			return;
		}
		// ȡ��Ѻ��PK�������ˣ��������ڽ�����Ѻ���ղ���
		impawnBack(fiImpawnVO.getPk_othersys(), fiImpawnVO.getUneffectperson(),fiImpawnVO.getUneffectdate());
	}

	/**
	 * ����dao������Ѻ���ղ���s
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
			//�����ѯ���˵����Ѿ�������ֱ�ӷ���
			if (null == newVO || IFBMStatus.IMPAWN_BACK == newVO.getVbillstatus()) {
				return;
			}
			newVO.setImpawnbackdate(backDate);
			newVO.setImpawnbackunit(backPerson);

			// ����impawnVO����ΪHYBillVO���أ�ͬʱ������Ѻ���յ�Action����,������Ѻ���ղ���
			impawnBackAction(getHYBillVO(newVO));
		} catch (DAOException e) {
			Logger.error(e);
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201040","UPP36201040-000017")/* @res"���ݿ�����쳣"*/);
		} catch (Exception e) {
			Logger.error(e);
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201040","UPP36201040-000018")/* @res"����Ʊ��ϵͳ��Ӧ��Ѻ����ʱ�쳣"*/);
		}
	}

	/**
	 * ��impawnVO�����HYBillVO
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
	 * ��Ѻ����Action����
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