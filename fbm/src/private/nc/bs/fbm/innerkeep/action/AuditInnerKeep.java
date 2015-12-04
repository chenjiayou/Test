/**
 *
 */
package nc.bs.fbm.innerkeep.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.HYBillVO;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-10-12
 *
 */
public class AuditInnerKeep<K extends HYBillVO,T extends StorageVO> extends ActionInnerKeep<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
			//校验票据动作
		if(!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_INNERKEEP)
					&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000254")/* @res"的前一操作必须是保存内部存放单。"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.bs.fbm.pub.action.AbstractAction#getBeginStatus(nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		StorageVO vo = (StorageVO)param.getSuperVO();
		String inputtype = vo.getInputtype();
		if(inputtype.equals(FbmBusConstant.KEEP_TYPE_STORE)){
			return FbmStatusConstant.ON_INNER_KEEP;
		}else {
			return FbmStatusConstant.ON_RELIEF_KEEP;
		}
	}

	/* (non-Javadoc)
	 * @see nc.bs.fbm.pub.action.AbstractAction#getEndStatus(nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		StorageVO vo = param.getSuperVO();
		String inputtype = vo.getInputtype();
		if(inputtype.equals(FbmBusConstant.KEEP_TYPE_STORE)){
			return FbmStatusConstant.ON_INNER_KEEP;
		}else {
			return FbmStatusConstant.ON_RELIEF_KEEP;
		}
	}

	/* (non-Javadoc)
	 * @see nc.bs.fbm.pub.action.AbstractAction#dealAccount(java.lang.String, nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	public void dealAccount(String pk_action, BusiActionParamVO<T> param)
			throws BusinessException {
		CommonDAO dao = new CommonDAO();
		BaseinfoVO baseinfoVO = dao.queryBaseinfoByPK(param.getPk_baseinfo());
		StorageVO storVO = param.getSuperVO();
		RegisterVO regVO = param.getRegisterVO();

		/**
		 * A单位票调剂存放/保管存放均生成3笔账
		 * 调剂存放：
		 * 	A单位本地存放户减少
		 *  中心调剂户增加
		 * 保管存放：
		 * 	A单位本地存放户减少
		 *  中心存放户增加
		 */
		AccountDetailVO[] vos = new AccountDetailVO[3];
		//A单位本地存放户减少
		vos[0] = new AccountDetailVO();
		vos[0].setPk_org(regVO.getKeepunit());
		vos[0].setMoneyy(baseinfoVO.getMoneyy().multiply(-1));
		vos[0].setPk_baseinfo(param.getPk_baseinfo());
		vos[0].setPk_action(pk_action);
		vos[0].setPk_type(FbmBusConstant.ACCOUNT_TYPE_LOCALKEEP);
		if (FbmBusConstant.KEEP_TYPE_STORE.equals(storVO.getInputtype())) {
			vos[0].setReason(FbmBusConstant.ACCOUNT_REASON_CENTERKEEP_SAVE);
		} else {
			vos[0].setReason(FbmBusConstant.ACCOUNT_REASON_CENTERKEEP_RELIEF);
		}
		vos[0].setIsliquid(UFBoolean.FALSE);//是否清算，默认false
		vos[0].setLiquidationdate(baseinfoVO.getEnddate());//清算日期
		//A中心存放户/调剂户增加
		vos[1] = new AccountDetailVO();
		vos[1].setPk_org(regVO.getKeepunit());
		vos[1].setMoneyy(baseinfoVO.getMoneyy());
		vos[1].setPk_baseinfo(param.getPk_baseinfo());
		vos[1].setPk_action(pk_action);
		if (FbmBusConstant.KEEP_TYPE_STORE.equals(storVO.getInputtype())) {
			vos[1].setPk_type(FbmBusConstant.ACCOUNT_TYPE_CENTER);
			vos[1].setReason(FbmBusConstant.ACCOUNT_REASON_CENTERKEEP_SAVE);
		} else {
			vos[1].setPk_type(FbmBusConstant.ACCOUNT_TYPE_RELIEF);
			vos[1].setReason(FbmBusConstant.ACCOUNT_REASON_CENTERKEEP_RELIEF);
		}

		vos[1].setIsliquid(UFBoolean.FALSE);//是否清算，默认false
		vos[1].setLiquidationdate(baseinfoVO.getEnddate());//清算日期


		dao.getBaseDAO().insertVOArray(vos);
	}



}