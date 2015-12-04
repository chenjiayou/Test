package nc.bs.pub.action;

import nc.bs.fbm.endore.EndoreService;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.uif.pub.exception.UifException;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.uapbd.bankaccount.BankaccbasVO;

/**
 * 
 *********************************************************** 
 * ���ڣ�2008-3-17 ����Ա:���ɽ ���ܣ����ְ����棬��������ҵ��(ע������ҵ��༭����ʱ��Ҫ��ɾ�����ɵ���Ʊ�Ǽǵ�)
 *********************************************************** 
 */
public class N_36GG_SAVE extends AbstractCenterOperation {

	/**
	 * N_36GG_SAVE ������ע�⡣
	 */
	public N_36GG_SAVE() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			DiscountVO headVo = (DiscountVO) vo.m_preValueVo.getParentVO();
			checkSave(headVo); // ǰ̨У�鷽�����޸�Ч��ʱ��ת����̨����У�顣
			if ((headVo.getPk_discount_app() == null || "".equals(headVo.getPk_discount_app().trim()))
					&& FbmBusConstant.BILL_UNISTORAGE.equals(headVo.getOpbilltype())) {// ����������뵥Ϊ����Ϊͳ��Ʊ��
				return saveUniStorage(vo);
			} else {// ˽��ҵ��ֱ�ӱ���
				// �����������뵥����Ϊͳ��ҵ�������ְ�����Ϊ����
				// ��Ҫɾ�������������뵥���ɵĵ����������㻧����Ҫ��������action֮ǰ���˻������
				// if (headVo.getPk_discount_app() != null
				// && !"".equals(headVo.getPk_discount_app())&&
				// FbmBusConstant.BILL_UNISTORAGE.equals(headVo
				// .getOpbilltype())&&
				// FbmBusConstant.DISCOUNT_RESULT_DISABLE.equals(headVo.getResult()))
				// {
				// //delAccountDetail(headVo);
				// }

				return savePrivacy(vo);
			}

		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

	/**
	 * ������ְ������������������뵥�������ɵ��˻�����ɾ��
	 * 
	 * @param headVo
	 * @throws BusinessException
	 */
	private void delAccountDetail(DiscountVO headVo) throws BusinessException {
		// ActionQueryDAO dao = new ActionQueryDAO();
		// ActionVO[] actionVO = dao.queryActionByWhereClause(" actioncode='" +
		// FbmActionConstant.CENTERUSE + "' and pk_bill='" +
		// headVo.getPk_discount_app() + "' ");
		EndoreService endores = new EndoreService();
		ActionVO actionvo = endores.queryPK_Action(headVo.getPk_baseinfo(), FbmActionConstant.CENTERUSE);
		if (null == actionvo) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000127")/*
																										 * @res
																										 * "�޷��õ�Ʊ�ݶ�����"
																										 */);
		}
		String pkAction = actionvo.getPk_action();

		CommonDAO comDao = new CommonDAO();
		comDao.delAccountDetailByActionPK(pkAction);

	}

	/**
	 * ��������Ʊ�Ǽǵ�
	 */
	protected RegisterVO changeRegister(RegisterVO register_main, SuperVO superVO)
			throws BusinessException {
		RegisterVO register = (RegisterVO) register_main.clone();
		register.setPk_corp((String) superVO.getAttributeValue("pk_corp"));

		// ȡ��ǰ��˾��Ӧ�Ŀ���
		String pk_cubasdoc = new CommonDAO().queryCustByCorp(register.getPk_corp());
		register.setPaybillunit(register.getHoldunit());

		register.setHoldunit(pk_cubasdoc);
		register.setKeepunit(pk_cubasdoc);

		register.setPk_source(register.getPrimaryKey());
		register.setPrimaryKey(null);
		register.setGathertype(FbmBusConstant.GATHER_TYPE_UNISTORAGE);
		register.setVbillstatus(IBillStatus.FREE);

		// ����ҵ�����ڡ�
		replaceRegisterValue(register, superVO);

		register.setDoperatedate((UFDate) superVO.getAttributeValue("doperatedate"));
		register.setVapproveid((String) superVO.getAttributeValue("voperatorid"));
		register.setVoperatorid((String) superVO.getAttributeValue("voperatorid"));
		// ���VBillNO����Ʊ�Ǽǵ��С�
		String pk_corp = (String) superVO.getAttributeValue("pk_corp");
		IBillcodeRuleService ibrs = (IBillcodeRuleService) NCLocator.getInstance().lookup(IBillcodeRuleService.class.getName());
		String vbillno = ibrs.getBillCode_RequiresNew(FbmBusConstant.BILLTYPE_GATHER, pk_corp, null, null);
		register.setVbillno(vbillno);
		return register;
	}

	// ��SuperVO�е������滻��RegisterVO�е�Gatherdate,Dapprovedateֵ��
	@Override
	protected void replaceRegisterValue(RegisterVO regvo, SuperVO supervo) {
		if ((UFDate) supervo.getAttributeValue("dtransactdate") != null) {// ����������ڲ�Ϊ�����ð�������
			regvo.setGatherdate((UFDate) supervo.getAttributeValue("dtransactdate"));
			regvo.setDapprovedate((UFDate) supervo.getAttributeValue("dtransactdate"));
		} else {
			regvo.setGatherdate((UFDate) supervo.getAttributeValue("doperatedate"));
			regvo.setDapprovedate((UFDate) supervo.getAttributeValue("doperatedate"));
		}
	}

	/**
	 * ǰ̨У�鷽���Ƶ���̨����У�顣(Ч���޸�)
	 * 
	 * @param discountvo
	 * @throws BusinessException
	 */
	private void checkSave(DiscountVO discountvo) throws BusinessException {
		String result = discountvo.getResult();
		UFDate discountdate = discountvo.getDdiscountdate();
		if (result.equalsIgnoreCase(FbmBusConstant.DISCOUNT_RESULT_SUCCESS)) {
			try {
				String discount_account = discountvo.getDiscount_account();
				if (discount_account != null) {
					BankaccbasVO bankaccbasVO = (BankaccbasVO) FBMProxy.getUifService().queryByPrimaryKey(BankaccbasVO.class, discount_account);
					UFDate opendate = (UFDate) bankaccbasVO.getAccopendate();
					if (discountdate != null
							&& opendate != null
							&& discountdate.before(opendate)) {
						throw new BusinessException(
								nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000002")/*
																															 * @res
																															 * "ʵ���������ڲ������ڿ�������"
																															 */);
					}
				} else {
					throw new BusinessException(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000003")/*
																														 * @res
																														 * "���������˺Ų���Ϊ��"
																														 */);
				}
			} catch (UifException e) {
				Logger.error(e.getMessage());
			}
		}
	}
}