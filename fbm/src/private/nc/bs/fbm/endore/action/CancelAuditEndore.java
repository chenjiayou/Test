package nc.bs.fbm.endore.action;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.endore.EndoreService;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

public class CancelAuditEndore<K extends EndoreVO, T extends EndoreVO> extends ActionEndore<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if (commonError != null)
			return commonError;

		ActionVO actionVO = param.getLastActionVO();
		// 校验票据动作
		if (!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
				&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_ENDORE) && actionVO
				.getPk_bill().equals(param.getPk_bill()))) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000134")/*@res "前一操作必须是审核背书单。"*/;
		}

		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		RegisterVO reg = param.getRegisterVO();
		if(FbmBusConstant.BILLTYPE_GATHER.equals(reg.getPk_billtypecode())){
			return FbmStatusConstant.HAS_ENDORE;
		}else {
			return FbmStatusConstant.HAS_PAYBILL;
		}
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		RegisterVO reg = param.getRegisterVO();
		if(FbmBusConstant.BILLTYPE_GATHER.equals(reg.getPk_billtypecode())){
			return FbmStatusConstant.ON_ENDORE;
		}else {
			return FbmStatusConstant.ON_PAYBILL;
		}
	}

	public void dealAccount(String pk_action, BusiActionParamVO<T> param) throws BusinessException {
		//如果已经清算，则无法弃审
		String opbilltype = ((EndoreVO)param.getSuperVO()).getOpbilltype();
		if(FbmBusConstant.BILL_UNISTORAGE.equals(opbilltype)){
			String pkAction = "";

			String pk_baseinfo = param.getPk_baseinfo();

			EndoreService endores = new EndoreService();
			ActionVO actionvo = endores.queryPK_Action(pk_baseinfo, FbmActionConstant.CENTERUSE);
			if(null==actionvo)
			{
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000127")/*@res "无法得到票据动作。"*/);
			}
			pkAction = actionvo.getPk_action();

			if("".equals(pkAction))
			{
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000128")/*@res "无法得到pk_action　不能删除清算户"*/);
			}

			HYPubBO hypubbo = new HYPubBO();
			//删除清算户

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

			AccountDetailVO[] centeruseaccs = (AccountDetailVO[])hypubbo.queryByCondition(AccountDetailVO.class, " pk_action = '" + pkAction + "' and pk_type = '" + FbmBusConstant.ACCOUNT_TYPE_LIQUID + "'" );
	        if(centeruseaccs.length == 1) {
			    dao.getBaseDAO().deleteVO(centeruseaccs[0]);
	        }
		}

	}
}