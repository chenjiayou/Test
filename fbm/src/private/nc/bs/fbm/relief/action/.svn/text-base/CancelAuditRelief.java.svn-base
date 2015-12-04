package nc.bs.fbm.relief.action;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.gather.GatherBillService;
import nc.bs.fbm.pub.ActionQueryDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.relief.BusiReliefUtil;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

/**
 *
 * 类功能说明： 调剂出库-取消审核 日期：2007-10-24 程序员： wues
 *
 */
public class CancelAuditRelief<K extends HYBillVO,T extends ReliefVO> extends ActionRelief<K,T> {
	/**
	 * 每次check时都根据register表中的pk_source（在此为pk_relief）去校验所有生成的记录
	 * 造成不必要的校验，在循环一次校验完后根据此变量的值判断是否进行下一次校验
	 */
	private String hasChecked = null;

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		// 先校验基本状态
		String commonError = commonCheck(param);
		if (commonError != null)
			return commonError;

		if (null == hasChecked) {
			// 非自己的票，还要校验新收到的票前置状态是否为has_inner_keep，已内部存放,如果不是可能已被使用
			String errMsg = checkNewRegister(param);
			hasChecked = "HAS_CHECKED";
			if (null != errMsg) {
				return new StringBuffer().append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000268")/* @res"票据编号["*/).append(errMsg)
						.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000269")/* @res"]已被使用，不能弃审"*/).toString();
			}
		}
		return null;
	}

	/**
	 * 收到他人票的时候检查所有的收票登记单状态是否合法：has_inner_keep
	 *
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	private String checkNewRegister(BusiActionParamVO<T> param)
			throws BusinessException {
		if (!BusiReliefUtil.isSelfBill(param)) {
			GatherBillService gatherSrv = null;
			gatherSrv = new GatherBillService();
			RegisterVO[] vos = (RegisterVO[]) gatherSrv
					.queryRegisterVOSBySrc(((ReliefVO) param.getSuperVO())
							.getPk_relief());
			String errMsg = checkNewRegisters(vos);
			if (null != errMsg) {
				return errMsg;
			}
		}
		return null;
	}

	/**
	 * 调剂到别人票的时候需要校验新生成的收票登记记录的状态是否为已内部存放 可以建立用来进行校验的类 如果校验有误返回有误的票据编号
	 *
	 * @param vos
	 * @throws BusinessException
	 */
	private String checkNewRegisters(RegisterVO[] vos) throws BusinessException {
		StringBuffer buf = null;
		ActionQueryDAO actionDao = new ActionQueryDAO();
		for (int i = 0; vos != null && i < vos.length; i++) {
			ActionVO actionVO = actionDao.queryNewestByPk_register(vos[i]
					.getPrimaryKey());
			if (!FbmStatusConstant.HAS_RELIEF_KEEP.equals(actionVO
					.getEndstatus())) {
				if (null == buf) {
					buf = new StringBuffer();
				}
				buf.append(dao.queryBaseinfoByPK(vos[i].getPk_baseinfo())
						.getFbmbillno());
				if (i != vos.length - 1) {
					buf.append(",");
				}
			}
		}
		if (null != buf) {
			return buf.toString();
		}

		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		if (BusiReliefUtil.isSelfBill(param)) {
			// 自己的票
			return FbmStatusConstant.HAS_INNER_KEEP;
		} else
			// 旧他人的票，结束时候为已调剂
			return FbmStatusConstant.HAS_RELIEF_KEEP;
//
//		return FbmStatusConstant.HAS_RELIEF_KEEP;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return null;
	}

	/**
	 * 事后额外处理 删除由于调剂其他单位的票而在本公司生成的收票登记单
	 */
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		if (null == params || params.length == 0) {
			return;
		}
		// 如果是自己的票调剂给自己，不改变任何状态
		if (BusiReliefUtil.isSelfBill(params[0])) {

		} else {

			String pk_relief = params[0].getSuperVO().getPk_relief();// 得到调剂出库单的pk
			// 弃审时删除相应的收票登记单
			GatherBillService gatherSrv = new GatherBillService();
			gatherSrv.deleteRegisterVosForOuterSys(pk_relief);

		}
	}

	/**
	 * 弃审时将审核插入的账户删除
	 */
	public void dealAccount(String pk_action, BusiActionParamVO<T> param)
	throws BusinessException {
StringBuffer sqlbuffer = new StringBuffer();

		sqlbuffer.append(" select 1 from ");
		sqlbuffer.append(" fbm_accountdetail inner join fbm_reckon_b ");
		sqlbuffer.append(" on fbm_accountdetail.pk_detail = fbm_reckon_b.pk_detail");
		sqlbuffer.append(" where fbm_accountdetail.pk_action = '"	+ pk_action + "' ");
		sqlbuffer.append(" and pk_type = '"+ FbmBusConstant.ACCOUNT_TYPE_LIQUID + "'");
		sqlbuffer.append(" and isnull(fbm_reckon_b.dr,0)=0");
		BaseDAO bdao = new BaseDAO();
		Object ction = bdao.executeQuery(sqlbuffer.toString(), new ColumnListProcessor());
		List list = (List)ction;
		if(list!=null && list.size()>0)
		{
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000129")/*@res "已清算的票据不能执行此操作!"*/);
		}
		CommonDAO dao = new CommonDAO();
		dao.delAccountDetailByActionPK(pk_action);
    }
}