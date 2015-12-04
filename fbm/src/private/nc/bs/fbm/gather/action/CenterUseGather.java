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
 * 日期：2008-3-12
 * 程序员:吴二山 功能：中心使用收票 
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
	 * 账户处理 中心使用A单位调剂存放票据： A单位中心调剂户减少 A单位清算户上升 中心清算户下降
	 * 
	 * @param pk_action
	 * @param param
	 * @throws BusinessException
	 */
	public void dealAccount(String pk_action, BusiActionParamVO<T> param)
			throws BusinessException {
		if (FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT.equals(param.getSuperVO()
				.getPk_billtypecode())) {// 贴现办理
			if (FbmBusConstant.DISCOUNT_RESULT_DISABLE.equals(param
					.getSuperVO().getAttributeValue(DiscountVO.RESULT))) { // 办理作废
				return;
			}
		}

		CommonDAO dao = new CommonDAO();
		BaseinfoVO baseinfoVO = dao.queryBaseinfoByPK(param.getPk_baseinfo());
		AccountDetailVO[] vos = new AccountDetailVO[2];

		// A中心调剂户减少
		vos[0] = new AccountDetailVO();
		vos[0].setPk_org(param.getUnit_a());// 调出的单位
		vos[0].setMoneyy(baseinfoVO.getMoneyy().multiply(-1));
		vos[0].setPk_baseinfo(param.getPk_baseinfo());
		vos[0].setPk_action(pk_action);
		vos[0].setPk_type(FbmBusConstant.ACCOUNT_TYPE_RELIEF);// 中心调剂户减少
		vos[0].setReason(FbmBusConstant.ACCOUNT_REASON_CENTER_USE);// 中心使用
		vos[0].setIsliquid(UFBoolean.FALSE);// 是否清算，默认false
		vos[0].setLiquidationdate(baseinfoVO.getEnddate());// 清算日期
		vos[0].setPk_register(param.getRegisterVO().getPrimaryKey());// 设置pk_register
		// A清算户下降
		vos[1] = new AccountDetailVO();
		vos[1].setPk_org(param.getUnit_a());// 调出的单位减少
		//vos[1].setMoneyy(baseinfoVO.getMoneyy().multiply(-1));
		vos[1].setMoneyy(baseinfoVO.getMoneyy());
		vos[1].setPk_baseinfo(param.getPk_baseinfo());
		vos[1].setPk_action(pk_action);
		vos[1].setPk_type(FbmBusConstant.ACCOUNT_TYPE_LIQUID);// A单位清算户减少
		vos[1].setReason(FbmBusConstant.ACCOUNT_REASON_CENTER_USE);
		vos[1].setIsliquid(UFBoolean.FALSE);// 是否清算，默认false
		vos[1].setLiquidationdate(baseinfoVO.getEnddate());// 清算日期
		vos[1].setPk_register(param.getRegisterVO().getPrimaryKey());// 设置pk_register

		dao.getBaseDAO().insertVOArray(vos);
	}

}
