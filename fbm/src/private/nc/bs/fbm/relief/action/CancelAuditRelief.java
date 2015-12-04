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
 * �๦��˵���� ��������-ȡ����� ���ڣ�2007-10-24 ����Ա�� wues
 *
 */
public class CancelAuditRelief<K extends HYBillVO,T extends ReliefVO> extends ActionRelief<K,T> {
	/**
	 * ÿ��checkʱ������register���е�pk_source���ڴ�Ϊpk_relief��ȥУ���������ɵļ�¼
	 * ��ɲ���Ҫ��У�飬��ѭ��һ��У�������ݴ˱�����ֵ�ж��Ƿ������һ��У��
	 */
	private String hasChecked = null;

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		// ��У�����״̬
		String commonError = commonCheck(param);
		if (commonError != null)
			return commonError;

		if (null == hasChecked) {
			// ���Լ���Ʊ����ҪУ�����յ���Ʊǰ��״̬�Ƿ�Ϊhas_inner_keep�����ڲ����,������ǿ����ѱ�ʹ��
			String errMsg = checkNewRegister(param);
			hasChecked = "HAS_CHECKED";
			if (null != errMsg) {
				return new StringBuffer().append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000268")/* @res"Ʊ�ݱ��["*/).append(errMsg)
						.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000269")/* @res"]�ѱ�ʹ�ã���������"*/).toString();
			}
		}
		return null;
	}

	/**
	 * �յ�����Ʊ��ʱ�������е���Ʊ�Ǽǵ�״̬�Ƿ�Ϸ���has_inner_keep
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
	 * ����������Ʊ��ʱ����ҪУ�������ɵ���Ʊ�ǼǼ�¼��״̬�Ƿ�Ϊ���ڲ���� ���Խ�����������У����� ���У�����󷵻������Ʊ�ݱ��
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
			// �Լ���Ʊ
			return FbmStatusConstant.HAS_INNER_KEEP;
		} else
			// �����˵�Ʊ������ʱ��Ϊ�ѵ���
			return FbmStatusConstant.HAS_RELIEF_KEEP;
//
//		return FbmStatusConstant.HAS_RELIEF_KEEP;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return null;
	}

	/**
	 * �º���⴦�� ɾ�����ڵ���������λ��Ʊ���ڱ���˾���ɵ���Ʊ�Ǽǵ�
	 */
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		if (null == params || params.length == 0) {
			return;
		}
		// ������Լ���Ʊ�������Լ������ı��κ�״̬
		if (BusiReliefUtil.isSelfBill(params[0])) {

		} else {

			String pk_relief = params[0].getSuperVO().getPk_relief();// �õ��������ⵥ��pk
			// ����ʱɾ����Ӧ����Ʊ�Ǽǵ�
			GatherBillService gatherSrv = new GatherBillService();
			gatherSrv.deleteRegisterVosForOuterSys(pk_relief);

		}
	}

	/**
	 * ����ʱ����˲�����˻�ɾ��
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
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000129")/*@res "�������Ʊ�ݲ���ִ�д˲���!"*/);
		}
		CommonDAO dao = new CommonDAO();
		dao.delAccountDetailByActionPK(pk_action);
    }
}