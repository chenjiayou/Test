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
 * 备注：调剂清算单的弃审 单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-11-1)
 *
 * @author 平台脚本生成
 */
public class N_36GK_UNAPPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GK_UNAPPROVE 构造子注解。
	 */
	public N_36GK_UNAPPROVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
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

			//校验回单是否已记账或已制证
			if(receiptbillvo!=null && receiptbillvo.getParentVO()!=null){
				ReckonVO receiptVO = (ReckonVO)receiptbillvo.getParentVO();
				if(UFBoolean.TRUE.equals(receiptVO.getUnittally()) || UFBoolean.TRUE.equals(receiptVO.getUnitvoucher())){
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000178")/*@res "清算回单已记账或已制证，无法取消审核清算单"*/);
				}
				hypubbo.deleteBill(receiptbillvo);
			}

			changeAccountDetailLiquidFlag(vo);
			if (retObj != null) {
				m_methodReturnHas.put("unApproveBill", retObj);
			}

			//清除受理日期
			ReckonVO reckonVO = (ReckonVO)((AggregatedValueObject)retObj).getParentVO();
			reckonVO.setDealdate(null);
			new SuperDMO().update(reckonVO);

			//记内部账户账
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
	 * 备注：平台编写原始脚本
	 */
	public String getCodeRemark() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000173")/*@res "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n	Object retObj=null;\n	return retObj;\n"*/;
	}

	/*
	 * 备注：设置脚本变量的HAS
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