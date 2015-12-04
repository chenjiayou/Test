package nc.bs.fbm.out;

import java.util.List;

import nc.bs.fbm.gather.GatherBillService;
import nc.bs.fbm.pub.ActionQueryDAO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.IBillStatus;

public class ApUneffectBillValidator extends AbstractBillValidator {

	public void checkBaseinfo(ArapBillParamVO param) throws BusinessException {

	}

	public void doCheck(ArapBillParamVO param) throws BusinessException {
		//如果是反向操作如果没有找到关联关系则直接返回
		if(param.getOuterVO() == null){
			return;
		}
		
		// TODO Auto-generated method stub
		if (billnoSet.contains(param.getFbmbillno())) {
			return;
		}

		
		if(param.getNewActionVO() == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000389")/* @res"找不到票据业务操作"*/);
		}
		String curStatus = param.getNewActionVO().getEndstatus();
		if (!(curStatus.equals(FbmStatusConstant.HAS_ENDORE) || curStatus
				.equals(FbmStatusConstant.HAS_PAYBILL))) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000214")/* @res"票据状态不是已背书或已付票"*/);
		}
		// 校验动作
		if(param.getOuterVO() == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000388")/* @res"找不到票据和收付报单据的关联关系"*/);
		}
		ActionQueryDAO dao = new ActionQueryDAO();
		
		//ActionVO curAction = dao.queryNewestByPk_register(param.getOuterVO().getPk_busibill());
		ActionVO curAction = dao.queryNewestByPk_register(param.getOuterVO().getPk_register());
		if (curAction.getSerialnum().intValue() < param.getNewActionVO()
				.getSerialnum().intValue()) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000215")/* @res"票据已进行后续业务操作"*/);
		}
	}

	@Override
	public void beforeAction(ArapBillParamVO param) throws BusinessException {
		//如果是反向操作如果没有找到关联关系则直接返回
		if(param.getOuterVO() == null){
			return;
		}
		
		if (!billnoSet.contains(param.getFbmbillno())) {
			OuterVO outerVO = outRelDao.queryBypk_b(param.getPk_bill_b());
			if (outerVO == null) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000216")/* @res"未找到单据和票据的关联"*/);
			}

			//String pk_register = outerVO.getPk_busibill();
			String pk_register = outerVO.getPk_register();

			String otherunit = param.getOtherunit();
			if(otherunit == null){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000217")/* @res"供应商字段不能为空，无法弃审."*/);
			}

			boolean fbmEnable = comDao.productEnableByCust(otherunit, FbmBusConstant.SYSCODE_FBM);

			if(fbmEnable){
				// 检查推式生成单据是否有后续操作
				List registerList = (List) baseDao.retrieveByClause(RegisterVO.class,
						" isnull(dr,0)=0 and pk_source ='" + pk_register + "'");
				if (registerList != null && registerList.size() > 0) {// 如果有推式生成收票登记单
					RegisterVO regVO = (RegisterVO) registerList.get(0);
					ActionVO[] actionVos = actionDao.queryAllByPk_register(regVO
							.getPrimaryKey());
					if (actionVos.length != 2) {
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000218")/* @res"推式生成的收票登记单已进行后续处理"*/);
					}
				}
			}
			List endoreList = (List) baseDao.retrieveByClause(EndoreVO.class,
					" isnull(dr,0)=0 and pk_source = '" + pk_register + "' order by ts desc");
			EndoreVO[] vos = (EndoreVO[]) endoreList.toArray(new EndoreVO[0]);
			if (vos != null && vos.length > 0) {
					GatherBillService service = new GatherBillService();
					service.deleteRegisterVosForOuterSys(vos[0].getPrimaryKey());
			}
			//刷新
			//查询票据最新动作VO
			param.setNewActionVO(actionDao.queryNewestByFbmBillno(param.getFbmbillno(),param.getPk_corp()));
			if(param.getPk_busbill() == null){
				param.setPk_busbill(param.getNewActionVO().getPk_source());
			}
		}
	}

	@Override
	public void afterAction(ArapBillParamVO param) throws BusinessException {
		//如果是反向操作如果没有找到关联关系则直接返回
		if(param.getOuterVO() == null){
			return;
		}
		
		if (!billnoSet.contains(param.getFbmbillno())) {
			List endoreList = (List) baseDao.retrieveByClause(EndoreVO.class,
					" isnull(dr,0)=0 and pk_source = '"
							+ param.getNewActionVO().getPk_source() + "' order by ts desc");
			EndoreVO[] vos = (EndoreVO[]) endoreList.toArray(new EndoreVO[0]);
			if (vos == null || vos.length == 0) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000212")/* @res"找不到背书办理单"*/);
			}
			
			vos[0].setVbillstatus(IBillStatus.FREE);
			baseDao.updateVO(vos[0]);
			
			// 弃审背书办理单
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.CANCELAUDIT).doAction(vos[0],FbmActionConstant.CANCELAUDIT,false);
			// 设置登记表收付款标志
				if (FbmBusConstant.BILLTYPE_INVOICE.equals(param.getOuterVO()
						.getPk_billtypecode())) {
					GatherBillService service = new GatherBillService();
					service.updateRegisterSfflag(param.getNewActionVO()
							.getPk_source(), false);
				}
		}
		// 维护外部关联
		outRelDao.uneffectRelation(param.getOuterVO().getPk_outerbill_b());

		super.afterAction(param);
	}

}