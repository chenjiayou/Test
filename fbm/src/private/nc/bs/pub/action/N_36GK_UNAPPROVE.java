package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.accdetail.ReckonVOToAccDetail;
import nc.bs.pub.SuperDMO;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * ��ע���������㵥������ ���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-11-1)
 *
 * @author ƽ̨�ű�����
 */
public class N_36GK_UNAPPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GK_UNAPPROVE ������ע�⡣
	 */
	public N_36GK_UNAPPROVE() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;

			procUnApproveFlow(vo);
			Object retObj = runClass("nc.bs.trade.comstatus.BillUnApprove",
					"unApproveBill", "nc.vo.pub.AggregatedValueObject:36GK",
					vo, m_keyHas, m_methodReturnHas);

			HYPubBO hypubbo = new HYPubBO();
			String[] voclasses = new String[] { HYBillVO.class.getName(),
					ReckonVO.class.getName(), ReckonBVO.class.getName() };

			HYBillVO receiptbillvo = (HYBillVO) hypubbo.queryBillVOByCondition(
					voclasses, " def1 = '"
							+ ((ReckonVO) vo.m_preValueVo.getParentVO())
									.getPk_reckon() + "'")[0];

			// new RecieptService().operateVoucher(receiptbillvo,
			// ((ReckonVO)receiptbillvo.getParentVO()).getPk_corp(),
			// RecieptService.OPERATE_CANCEL_VOUCHER);

			//У��ص��Ƿ��Ѽ��˻�����֤
			if(receiptbillvo!=null && receiptbillvo.getParentVO()!=null){
				ReckonVO receiptVO = (ReckonVO)receiptbillvo.getParentVO();
				if(UFBoolean.TRUE.equals(receiptVO.getUnittally()) || UFBoolean.TRUE.equals(receiptVO.getUnitvoucher())){
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000178")/*@res "����ص��Ѽ��˻�����֤���޷�ȡ��������㵥"*/);
				}
				hypubbo.deleteBill(receiptbillvo);
			}

			changeAccountDetailLiquidFlag(vo);
			if (retObj != null) {
				m_methodReturnHas.put("unApproveBill", retObj);
			}

			//�����������
			ReckonVO reckonVO = (ReckonVO)((AggregatedValueObject)retObj).getParentVO();
			reckonVO.setDealdate(null);
			new SuperDMO().update(reckonVO);

			//���ڲ��˻���
			ReckonVOToAccDetail reckonAccdetailSrv = new ReckonVOToAccDetail();
			reckonAccdetailSrv.delAccDetail(reckonVO);




			return retObj;

		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

	private void changeAccountDetailLiquidFlag(PfParameterVO vo)
			throws BusinessException {
		AggregatedValueObject billvo = vo.m_preValueVo;
		ReckonBVO[] childvos = (ReckonBVO[]) billvo.getChildrenVO();
		StringBuffer pk_details = new StringBuffer();
		for (int i = 0; i < childvos.length; i++) {
			if (i == 0) {
				pk_details.append(" (");
			}
			pk_details.append("'" + childvos[i].getPk_source() + "'");
			if (i == childvos.length - 1) {
				pk_details.append(" ) ");
			} else {
				pk_details.append(", ");
			}
		}
		BaseDAO basebo = new BaseDAO();
		String sql = "update fbm_accountdetail set isliquid = 'Y' where pk_detail in "
				+ pk_details;
		basebo.executeUpdate(sql);
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