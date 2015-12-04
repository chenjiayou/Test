package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.ActionQueryDAO;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.framework.common.NCLocator;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.impl.fbm.cmp.CMPConstant;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cmp.fts.ICMP4AcceptanceService;
import nc.vo.cmp.BusiInfo;
import nc.vo.cmp.ReturnBillRetDetail;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * ��ע��������Ʊ��ת�� ���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-10-10)
 *
 * @author ƽ̨�ű�����
 */
public class N_36GN_TRANSFORM extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GN_TRANSFORM ������ע�⡣
	 */
	public N_36GN_TRANSFORM() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			// ####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
			Object retObj = null;
			// ��ѯ��Ʊ�ĵ��ݱ��ո������õ��ӱ�PK����
			ReturnVO parent = (ReturnVO) vo.m_preValueVo.getParentVO();
			ReturnBVO[] child = (ReturnBVO[]) vo.m_preValueVo.getChildrenVO();

			int len = child.length;
			String pk_register = null;
			Set<OuterVO> outerVOS = new HashSet<OuterVO>();
			BaseDAO dao = new BaseDAO();
			OuterRelationDAO outRelDao = new OuterRelationDAO();
			String returnType = parent.getReturntype();

			for (int i = 0; i < len; i++) {
				pk_register = child[i].getPk_source();
				OuterVO[] outerVos = null;
				if (returnType.equals(FbmBusConstant.BACK_TYPE_DISABLE)
						|| returnType.equals(FbmBusConstant.BACK_TYPE_GATHER)
						|| returnType.equals(FbmBusConstant.BACK_TYPE_INVOICE)) {
					outerVos = outRelDao.queryByPkBusibill(pk_register);
				} else if (returnType.equals(FbmBusConstant.BACK_TYPE_ENDORE)) {
					// ��ѯ��ñ��鵥PK
					List<EndoreVO> endorevos = (List<EndoreVO>) dao
							.retrieveByClause(EndoreVO.class,
									"isnull(dr,0)=0 and pk_source='"
											+ pk_register + "'");
					if (endorevos != null && endorevos.size() > 0) {
						outerVos = outRelDao.queryByPkBusibill(endorevos.get(0)
								.getPrimaryKey());
					}
				}

				if (outerVos != null && outerVos.length > 0) {
					for (int j = 0; j < outerVos.length; j++) {
						String djdl = outerVos[j].getOuterdjdl();

						if (returnType.equals(FbmBusConstant.BACK_TYPE_DISABLE)
								|| returnType
										.equals(FbmBusConstant.BACK_TYPE_GATHER)) {
							// ��Ʊ��Ʊ���Ʊ��Ʊȡ�տ���տ���㵥
							if (djdl.equals(CMPConstant.BILLTYPE_SJ)
									|| djdl.equals(CMPConstant.BILLTYPE_SK)) {
								outerVOS.add(outerVos[j]);
							}
						} else if (returnType
								.equals(FbmBusConstant.BACK_TYPE_ENDORE)
								|| returnType
										.equals(FbmBusConstant.BACK_TYPE_INVOICE)) {
							// ��Ʊ��Ʊ������Ʊȡ����򸶿���㵥
							if (djdl.equals(CMPConstant.BILLTYPE_FJ)
									|| djdl.equals(CMPConstant.BILLTYPE_FK)) {
								outerVOS.add(outerVos[j]);
							}
						}
					}
				}
			}
			String returnPerson = parent.getReturnperson();
			if (returnPerson == null) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
						.getNCLangRes().getStrByID("fbmcomm",
								"UPPFBMComm-000365")/* @res"��Ʊ��Ϊ��" */);
			}
			UFDate returnDate = parent.getDreturndate();
			if (returnDate == null) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
						.getNCLangRes().getStrByID("fbmcomm",
								"UPPFBMComm-000366")/* @res"��Ʊ����Ϊ��" */);
			}

			Object retMessage = null;// �ո���������Ϣ
			// ��ʽ�����ո�������
			if (outerVOS != null && outerVOS.size() > 0) {

				// TODO �޸�pk_bill_b���������outervo����������pk
				List<ReturnBillRetDetail> objs = dealCmpBill(parent
						.getPk_corp(), returnPerson, new UFDate(vo.m_currentDate), outerVOS);
				OuterVO outerVO = null;
				BaseDAO baseDAO = new BaseDAO();
				ActionQueryDAO actionDao = new ActionQueryDAO();
				for (int i = 0; objs != null && i < objs.size(); i++) {
					outerVO = new OuterVO();
					outerVO.setPk_outerbill_h(objs.get(i).getPk_bill());
					outerVO.setPk_outerbill_b(objs.get(i).getPk_billdetail());
					outerVO.setOuterdjdl(objs.get(i).getClassflag());
					outerVO.setOuterstatus(FbmBusConstant.OUTERBILL_OVER);
					outerVO.setPk_corp(objs.get(i).getPk_corp());
					outerVO.setPk_billtypecode(FbmBusConstant.BILLTYPE_RETURN);
					outerVO.setPk_busibill(parent.getPrimaryKey());

					if (!CommonUtil.isNull(objs.get(i).getNotenumber())) {
						outerVO.setPk_register(actionDao
								.queryNewestByFbmBillno(
										objs.get(i).getNotenumber(),
										parent.getPk_corp()).getPk_source());
					}
					baseDAO.insertVO(outerVO);
				}

				retMessage = getRetMess(objs);

			}

			// ά���ⲿ������ϵ(�����Ʊ�ӿ��߱�׼�ո����ӿڣ���ô�˴���ת�Ƶ��ӿ�ʵ����)
			// �޸ĵ���״̬Ϊ��ת��
			ReturnVO returnVO = (ReturnVO) dao.retrieveByPK(ReturnVO.class,
					parent.getPrimaryKey());
			returnVO.setVbillstatus(IFBMStatus.TRANSFORM);
			dao.updateVO(returnVO);
			HYPubBO bo = new HYPubBO();
			AggregatedValueObject ret = bo
					.queryBillVOByPrimaryKey(new String[] {
							HYBillVO.class.getName(), ReturnVO.class.getName(),
							ReturnBVO.class.getName() }, parent.getPrimaryKey());
			// ִ �й�ʽ ���������ƣ����Ч�����⣬����ǰ̨���̨����������
			RegisterVO2BVO.actionOPExecFormula((HYBillVO) ret);

			List retList = new ArrayList();
			retList.add(ret);
			retList.add(retMessage);
			return retList;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

	/**
	 * �����ֽ�ƽ̨
	 * @param objs
	 * @return
	 */
	private Object getRetMess(List<ReturnBillRetDetail> objs) {
		for (int i = 0; objs != null && i < objs.size(); i++) {
			if (objs.get(i).getSuccessMsg() != null
					&& !"".equals(objs.get(i).getSuccessMsg().trim())) {
				return objs.get(i).getSuccessMsg();
			}
		}
		return null;
	}

	private List<ReturnBillRetDetail> dealCmpBill(String pk_corp,
			String pk_operator, UFDate opDate, Set<OuterVO> outerVOS)
			throws BusinessException {
		ICMP4AcceptanceService cmpServ = (ICMP4AcceptanceService) NCLocator
				.getInstance().lookup(ICMP4AcceptanceService.class.getName());

		BusiInfo info = null;

		Map<String, List<String>> param = getParam(outerVOS
				.toArray(new OuterVO[0]));

		List<String> sjfjList = param.get(CMPConstant.BILLTYPE_SJ);
		List<String> sfList = param.get(CMPConstant.BILLTYPE_SK);

		List<ReturnBillRetDetail> result = null;

		if (sjfjList != null) {// �ո��������billid��Ϊ��
			if (result == null) {
				result = new ArrayList<ReturnBillRetDetail>();
			}

			info = new BusiInfo();
			info.setBilltype(CMPConstant.BILLTYPE_SJ);// ������Ӧ���տ���㵥/������㵥
			info.setOperator(pk_operator);
			info.setCorp(pk_corp);
			info.setDetailid(sjfjList.toArray(new String[0]));
			info.setOperateDate(opDate);
			List<ReturnBillRetDetail> processReturnBill = cmpServ
					.processReturnBill(info);
			if (null != processReturnBill && processReturnBill.size() != 0) {
				result.addAll(processReturnBill);
			}

		}
		if (sfList != null) {
			if (result == null) {
				result = new ArrayList<ReturnBillRetDetail>();
			}
			info = new BusiInfo();
			info.setBilltype(CMPConstant.BILLTYPE_SK);// �տ/���
			info.setOperateDate(opDate);
			info.setCorp(pk_corp);
			info.setOperator(pk_operator);
			info.setDetailid(sfList.toArray(new String[0]));

			List<ReturnBillRetDetail> processReturnBill = cmpServ
					.processReturnBill(info);
			if (null != processReturnBill && processReturnBill.size() != 0) {
				result.addAll(processReturnBill);
			}
		}

		return result;
	}

	private Map<String, List<String>> getParam(OuterVO[] vos) {
		if (CommonUtil.isNull(vos)) {
			return null;
		}
		Map<String, List<String>> param = new HashMap<String, List<String>>();
		List<String> sjfjlist = null;
		List<String> sflist = null;
		for (int i = 0; i < vos.length; i++) {
			if (CMPConstant.BILLTYPE_SJ.equals(vos[i].getOuterdjdl())
					|| CMPConstant.BILLTYPE_FJ.equals(vos[i].getOuterdjdl())) { // �սḶ��
				if (null == sjfjlist) {
					sjfjlist = new ArrayList<String>();
				}
				sjfjlist.add(vos[i].getPk_outerbill_b());
			} else if (CMPConstant.BILLTYPE_FK.equals(vos[i].getOuterdjdl())
					|| CMPConstant.BILLTYPE_SK.equals(vos[i].getOuterdjdl())) {// �տ��
				if (null == sflist) {
					sflist = new ArrayList<String>();
				}
				sflist.add(vos[i].getPk_outerbill_b());
			}
		}

		if (null != sjfjlist) {
			param.put(CMPConstant.BILLTYPE_SJ, sjfjlist);
		}
		if (null != sflist) {
			param.put(CMPConstant.BILLTYPE_SK, sflist);
		}

		return param;
	}

	/*
	 * ��ע��ƽ̨��дԭʼ�ű�
	 */
	public String getCodeRemark() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000173")/*@res "	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\n	Object retObj=null;\n	return retObj;\n"*/;
	}

	/*
	 * ��ע�����ýű�������HAS
	 */
	private void setParameter(String key, Object val) {
		if (m_keyHas == null) {
			m_keyHas = new Hashtable();
		}
		if (val != null) {
			m_keyHas.put(key, val);
		}
	}
}