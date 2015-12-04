package nc.bs.fbm.gather.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

/**
 * 
 * ********************************************************** 
 * ���ڣ�2008-3-12
 * ����Ա:���ɽ ���ܣ�����ʹ����Ʊ 
 * **********************************************************
 */
public class CenterUseGather<K extends RegisterVO, T extends RegisterVO>
		extends ActionGather<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param)
			throws BusinessException {
		String commonError = commonCheck(param);
		if (commonError != null)
			return commonError;
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		return FbmStatusConstant.HAS_RELIEF_KEEP;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		return FbmStatusConstant.HAS_CENTER_USE;
	}

	/**
	 * 
	 * �˻����� ����ʹ��A��λ�������Ʊ�ݣ� A��λ���ĵ��������� A��λ���㻧���� �������㻧�½�
	 * 
	 * @param pk_action
	 * @param param
	 * @throws BusinessException
	 */
	public void dealAccount(String pk_action, BusiActionParamVO<T> param)
			throws BusinessException {
		if (FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT.equals(param.getSuperVO()
				.getPk_billtypecode())) {// ���ְ���
			if (FbmBusConstant.DISCOUNT_RESULT_DISABLE.equals(param
					.getSuperVO().getAttributeValue(DiscountVO.RESULT))) { // ��������
				return;
			}
		}

		CommonDAO dao = new CommonDAO();
		BaseinfoVO baseinfoVO = dao.queryBaseinfoByPK(param.getPk_baseinfo());
		AccountDetailVO[] vos = new AccountDetailVO[2];

		// A���ĵ���������
		vos[0] = new AccountDetailVO();
		vos[0].setPk_org(param.getUnit_a());// �����ĵ�λ
		vos[0].setMoneyy(baseinfoVO.getMoneyy().multiply(-1));
		vos[0].setPk_baseinfo(param.getPk_baseinfo());
		vos[0].setPk_action(pk_action);
		vos[0].setPk_type(FbmBusConstant.ACCOUNT_TYPE_RELIEF);// ���ĵ���������
		vos[0].setReason(FbmBusConstant.ACCOUNT_REASON_CENTER_USE);// ����ʹ��
		vos[0].setIsliquid(UFBoolean.FALSE);// �Ƿ����㣬Ĭ��false
		vos[0].setLiquidationdate(baseinfoVO.getEnddate());// ��������
		vos[0].setPk_register(param.getRegisterVO().getPrimaryKey());// ����pk_register
		// A���㻧�½�
		vos[1] = new AccountDetailVO();
		vos[1].setPk_org(param.getUnit_a());// �����ĵ�λ����
		//vos[1].setMoneyy(baseinfoVO.getMoneyy().multiply(-1));
		vos[1].setMoneyy(baseinfoVO.getMoneyy());
		vos[1].setPk_baseinfo(param.getPk_baseinfo());
		vos[1].setPk_action(pk_action);
		vos[1].setPk_type(FbmBusConstant.ACCOUNT_TYPE_LIQUID);// A��λ���㻧����
		vos[1].setReason(FbmBusConstant.ACCOUNT_REASON_CENTER_USE);
		vos[1].setIsliquid(UFBoolean.FALSE);// �Ƿ����㣬Ĭ��false
		vos[1].setLiquidationdate(baseinfoVO.getEnddate());// ��������
		vos[1].setPk_register(param.getRegisterVO().getPrimaryKey());// ����pk_register

		dao.getBaseDAO().insertVOArray(vos);
	}

}
