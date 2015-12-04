package nc.bs.fbm.consignbank.action;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.endore.EndoreService;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;

public class DisableCollection<K extends CollectionVO, T extends CollectionVO>
		extends ActionCollection<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param)
			throws BusinessException {
		String commonError = commonCheck(param);
		if (commonError != null)
			return commonError;

		ActionVO actionVO = param.getLastActionVO();
		// 校验票据动作
		if (!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
				&& actionVO.getBilltype().equals(
						FbmBusConstant.BILLTYPE_COLLECTION_UNIT) && actionVO
				.getPk_bill().equals(param.getPk_bill()))) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"fbmcomm", "UPPFBMComm-000239")/* @res"票据" */
					+ param.getBaseinfoVO().getFbmbillno()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"fbmcomm", "UPPFBMComm-000260")/* @res"的前一操作必须是审核银行托收单。" */
					+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.ON_COLLECTION;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.HAS_DISABLE;
	}

	public void dealAccount(String pk_action, BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		CommonDAO dao = new CommonDAO();
		BaseinfoVO baseinfoVO = dao.queryBaseinfoByPK(param.getPk_baseinfo());

		// 本地存放户减少
		AccountDetailVO[] vos = new AccountDetailVO[1];
		vos[0] = new AccountDetailVO();
		vos[0].setPk_org(param.getPk_org());
		vos[0].setMoneyy(baseinfoVO.getMoneyy().multiply(-1));
		vos[0].setPk_baseinfo(param.getPk_baseinfo());
		vos[0].setPk_action(pk_action);
		vos[0].setPk_type(FbmBusConstant.ACCOUNT_TYPE_LOCALKEEP);
		vos[0].setReason(FbmBusConstant.ACCOUNT_REASON_COLLECTION_DISABLE);
		dao.getBaseDAO().insertVOArray(vos);

		// HYPubBO hypubbo = new HYPubBO();
		// //删除清算户
		// AccountDetailVO[] centeruseaccs =
		// (AccountDetailVO[])hypubbo.queryByCondition(AccountDetailVO.class, "
		// pk_baseinfo = '" + param.getPk_baseinfo() + "' and reason = '" +
		// FbmBusConstant.ACCOUNT_REASON_CENTER_USE + "'" );
		// if(centeruseaccs.length == 1) {
		// dao.getBaseDAO().deleteVO(centeruseaccs[0]);
		// }
		String opbilltype = ((CollectionVO) param.getSuperVO()).getOpbilltype();
		// 只有统管的票据才执行删除清算户。
		if (FbmBusConstant.BILL_UNISTORAGE.equals(opbilltype)) {
			String pkAction = "";

			String pk_baseinfo = param.getPk_baseinfo();

			EndoreService endores = new EndoreService();
			ActionVO actionvo = endores.queryPK_Action(pk_baseinfo,
					FbmActionConstant.CENTERUSE);
			if (null == actionvo) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000127")/*@res "无法得到票据动作。"*/);
			}
			pkAction = actionvo.getPk_action();

			if ("".equals(pkAction)) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000128")/*@res "无法得到pk_action　不能删除清算户"*/);
			}

			HYPubBO hypubbo = new HYPubBO();
			// 删除清算户

			StringBuffer sqlbuffer = new StringBuffer();

			sqlbuffer.append(" select 1 from ");
			sqlbuffer.append(" fbm_accountdetail inner join fbm_reckon_b ");
			sqlbuffer.append(" on fbm_accountdetail.pk_detail = fbm_reckon_b.pk_detail");
			sqlbuffer.append(" where fbm_accountdetail.pk_action = '"	+ pkAction + "' ");
			sqlbuffer.append(" and pk_type = '"+ FbmBusConstant.ACCOUNT_TYPE_LIQUID + "'");
			sqlbuffer.append(" and isnull(fbm_reckon_b.dr,0)=0");
			BaseDAO bdao = new BaseDAO();
			Object ction = bdao.executeQuery(sqlbuffer.toString(), new ColumnListProcessor());
			List list = (List)ction;
			if(list!=null && list.size()>0)
			{
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000129")/*@res "已清算的票据不能执行此操作!"*/);
			}

			AccountDetailVO[] centeruseaccs = (AccountDetailVO[]) hypubbo
					.queryByCondition(AccountDetailVO.class, " pk_action = '"
							+ pkAction + "' and pk_type = '"
							+ FbmBusConstant.ACCOUNT_TYPE_LIQUID + "'");

			if (centeruseaccs.length == 1) {
				dao.getBaseDAO().deleteVO(centeruseaccs[0]);
			}
		}
	}
}