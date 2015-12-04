package nc.impl.fbm.cmp;

import java.util.HashMap;
import java.util.Map;

import nc.bs.fbm.out.ArapBillDataAdapter;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cmp.plugin.ICMPAddPlugin;
import nc.itf.cmp.plugin.ICMPSignPlugin;
import nc.vo.cmp.BusiStatus;
import nc.vo.cmp.settlement.SettlementAggVO;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.cmp.settlement.SettlementHeadVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;

/**
 * ʵ���ֽ�ƽ̨�ṩ�ӿ� ����ģʽͳһ���� Ϊ����ʹ���ϴ�����������У�鶼��after�����н��У� ǰ�÷���������ᣬ���ַ�ʽЧ�ʽϵͣ���Ҫ����Ҫ�ع�
 *
 * @author wues
 *
 */
public class CMPServiceAdapterFacade extends AbstractCommandProcess implements
		ICMPAddPlugin, ICMPSignPlugin {

	/**
	 * ��Ʊ�ݽ���֧��ʱ��ҪƱ��ϵͳ�ṩƱ�ݵķ���(������Ʊ֧�����ǿ�Ʊ֧��)
	 */
	public void beforeAdd(SettlementAggVO info) throws BusinessException {
		if (info == null) {
			return;
		}

		CmpHelper.debug(info, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
				.getStrByID("3620add", "UPP3620ADD-000212")/*
															 * @res
															 * "beforeAdd������ʼ==>>>>>\n"
															 */, "");
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {

			// �����嵥��
			dealRedBill(info,ArapBillDataAdapter.ACTION_ADD);

			// ����Ʊ�ݷ���
			addOrient(info,ArapBillDataAdapter.ACTION_ADD);

			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000213")/* @res "<<<<<<======beforeAdd����=======>>>>>����" */);

		}

		
	}

	/**
	 * ҵ�񵥾���������
	 */
	public void afterAdd(SettlementAggVO info) throws BusinessException {
		if (info == null) {
			return;
		}

		CmpHelper
				.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
						"3620add", "UPP3620ADD-000214")/* @res "<<<<<<======afterAdd����=======>>>>>��ʼ==>>" */);
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {

			// �����嵥��
			dealRedBill(info,ArapBillDataAdapter.ACTION_ADD);

			// ���Ӻ���⴦��
			doProcess(info, getClass(info, ArapBillDataAdapter.ACTION_ADD),ArapBillDataAdapter.ACTION_ADD);

			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000215")/* @res "<<<<<<======afterAdd����=======>>>>>����==>>" */);
		}
	}

	/**
	 * �����ո�����ͬʱ�ж���Ʊ���ɲ��봦��
	 */
	public void beforeEdit(SettlementAggVO oldinfo, SettlementAggVO newinfo)
			throws BusinessException {
		if (newinfo == null || oldinfo == null) {
			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000216")/*
																		 * @res
																		 * "=========newinfo��oldinfoΪ��========="
																		 */);
			return;
		}
		CmpHelper
				.debug(
						newinfo,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"3620add", "UPP3620ADD-000217")/* @res "<<<<<<======beforeEdit����=======>>>>>��ʼ==>>" */,
						"");

		// �ж��Ƿ�Ϊ�������ݣ����Ϊ��������ֱ�����쳣����
		// updateDataDealer(newinfo); 090106
		// ��xwqȷ�ϣ�����ʼδ����Ʊ�ݣ��༭����Ʊ��������������ݲ�����edit��У��
		if (isFBMEnabled(((SettlementHeadVO) newinfo.getParentVO())
				.getPk_corp())) {
			// ���Ʊ�ݷ���
			addOrient(newinfo,ArapBillDataAdapter.ACTION_EDIT);

			// ��Ʊ���ɵ��ݲ�����
			noProcessReturnBill(oldinfo, newinfo);

			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000218")/*@res "<<<<<<======beforeEdit����=======>>>>>����==>>"*/);
		}
	}

	/**
	 * ҵ�񵥾ݱ༭����
	 */
	public void afterEdit(SettlementAggVO oldinfo, SettlementAggVO newinfo)
			throws BusinessException {
		//�տ�ݴ棬�޸ģ����ݴ�ʱ�����ߵ�������fbm_outer��д���ݣ���������ǲ���ȷ��
		//���Լ��������жϣ����������ݴ�ʱ��ֱ�ӷ��ؼ��ɡ�
		SettlementHeadVO newHeadVO = (SettlementHeadVO)newinfo.getParentVO();
		if(newHeadVO.getBusistatus().equals(BusiStatus.Tempeorary.getBillStatusKind())){
			// ��ɾ
			afterDelete(oldinfo);
			return ;
		}
		if (newinfo == null) {
			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000219")/*
																		 * @res "
																		 * =======newinfoΪ��========="
																		 */);
			return;
		}
		CmpHelper
				.debug(
						newinfo,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"3620add", "UPP3620ADD-000218")/* @res "<<<<<<======beforeEdit����=======>>>>>����==>>" */,
						"");
		if (isFBMEnabled(((SettlementHeadVO) newinfo.getParentVO())
				.getPk_corp())) {
			// ��Ʊ���ɵĵ��ݲ��봦��
			if (isReturnBil(oldinfo, newinfo)) {
				return;
			}

			// ��ADD����Ҫ����ǰ��������ȫ��Ϣ���Ƿ�Ϊ��Ʊ���ɵ���
			noAddProcess(newinfo,ArapBillDataAdapter.ACTION_EDIT);

			// ��ɾ
			afterDelete(oldinfo);

			// ���
			afterAdd(newinfo);
		}
	}

	/**
	 * ҵ�񵥾�ɾ��
	 */
	public void afterDelete(SettlementAggVO info) throws BusinessException {
		if (info == null) {
			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000220")/*
																		 * @res
																		 * "===========infoΪ��============="
																		 */);
			return;
		}
		CmpHelper
				.debug(
						info,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"3620add", "UPP3620ADD-000221")/* @res "<<<<<<======afterDelete����=======>>>>>��ʼ==>>" */,
						"");
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {
			// ��ADD����Ҫ����ǰ��������ȫ��Ϣ���Ƿ�Ϊ��Ʊ���ɵ���
			noAddProcess(info,ArapBillDataAdapter.ACTION_DEL);

			// �ų�Ӧ��Ӧ������ɾ������
			boolean isContinuous = processYsYf(info);
			if (false == isContinuous) {
				return;
			}

			// ɾ������
			doProcess(info, getClass(info, ArapBillDataAdapter.ACTION_DEL),ArapBillDataAdapter.ACTION_DEL);
		}
	}

	/**
	 * ǩ�ֺ�
	 */
	public void afterSign(SettlementAggVO info) throws BusinessException {
		if (info == null) {
			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000222")/*
																		 * @res
																		 * "=======infoΪ��================="
																		 */);
			return;
		}

		CmpHelper
				.debug(
						info,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"3620add", "UPP3620ADD-000223")/* @res "<<<<<<======afterSign����=======>>>>>��ʼ==>>" */,
						"");
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {
			noAddProcess(info,ArapBillDataAdapter.ACTION_EFFECT);
			// ǩ�ֺ���÷���
			doProcess(info, getClass(info, ArapBillDataAdapter.ACTION_EFFECT),ArapBillDataAdapter.ACTION_EFFECT);

			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000224")/*@res "<<<<<<======afterSign����=======>>>>>����==>>"*/);
		}
	}

	/**
	 * ȡ��ǩ�ֺ�
	 */
	public void afterReverseSign(SettlementAggVO info) throws BusinessException {
		if (info == null) {
			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000222")/*
																		 * @res
																		 * "=======infoΪ��================="
																		 */);
			return;
		}

		CmpHelper
				.debug(
						info,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"3620add", "UPP3620ADD-000225")/* @res "<<<<<<======afterReverseSign����=======>>>>>��ʼ==>>" */,
						"");
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {
			noAddProcess(info,ArapBillDataAdapter.ACTION_UNEFFECT);
			// ȡ��ǩ�ֺ���÷���
			doProcess(info, getClass(info, ArapBillDataAdapter.ACTION_UNEFFECT),ArapBillDataAdapter.ACTION_UNEFFECT);

			CmpHelper
					.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("3620add", "UPP3620ADD-000226")/*@res "<<<<<<======afterReverseSign����=======>>>>>����==>>"*/);
		}
	}

	/**
	 * ǩ��ʱ�ж��Ƿ�Ϊ�������ݣ����Ϊ�������ݲ�����ǩ��
	 */
	public void beforeSign(SettlementAggVO info) throws BusinessException {
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {
			// �ж��Ƿ�Ϊ�������ݣ����Ϊ��������ֱ�����쳣����
			updateDataDealer(info,ArapBillDataAdapter.ACTION_EFFECT);
		}
	}

	
	/**
	 * �ݴ�ʱ��ҪУ��Ʊ�ݱ����Ʊ��ϵͳ���Ƿ���� 
	 * @see nc.itf.cmp.plugin.ICMPAddPlugin#aftertempsave(nc.vo.cmp.settlement.SettlementAggVO)
	 */
	public void aftertempsave(SettlementAggVO info) throws BusinessException {
		if (isFBMEnabled(((SettlementHeadVO) info.getParentVO()).getPk_corp())) {
			SettlementBodyVO[] vos = (SettlementBodyVO[]) info.getChildrenVO();
			SettlementHeadVO head = (SettlementHeadVO) info.getParentVO();
			//NCdp201058738 
			//����������裺
			 //����һ��ί���տ�������̵��տ���㵥���Ȳ���ѡ������ί���տ��־���㱣�档Ȼ����޸ģ���ѡ�ϡ�����ί���տ��־�����ݴ档Ȼ���ٵ��޸ģ�ȥ��������ί���տ��־�Ĺ����㱣�档�ͻ���ʾ������ʧ�� Ʊ���Ѿ�����������ʹ�á�
			afterDelete(info);
			
			for (int i = 0; !CommonUtil.isNull(vos) && i < vos.length; i++) {
				isNeedFbmDeal(vos[i], head,ArapBillDataAdapter.ACTION_ADD);// �ж�ÿһ�б����Ƿ���Ҫfbm����
			}
		}
	}
	
	public void beforeReverseSign(SettlementAggVO info)
			throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void afterFtsGather(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void afterFtsPay(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void aftertempDelete(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}


	public void beforeDelete(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void beforeFtsGather(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void beforeFtsPay(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void beforetempDelete(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void beforetempsave(SettlementAggVO info) throws BusinessException {
		// TODO Auto-generated method stub

	}

	/**
	 * ����Ӧ��Ӧ������������������򷵻�false���к����������򷵻�ture��ֱ���˳�
	 *
	 * @return
	 * @throws BusinessException
	 */
	private boolean processYsYf(SettlementAggVO info) throws BusinessException {
		SettlementHeadVO headVO = (SettlementHeadVO) info.getParentVO();
		boolean returnBill = info.isReturnBill();
		String billType = headVO.getPk_tradetype();

		// �ų�Ӧ�յ���Ӧ����
		if (billType.equals(CMPConstant.BILLTYPE_YS)
				|| billType.equals(CMPConstant.BILLTYPE_YF)) {
			if (returnBill) {//��Ʊ
				OuterRelationDAO relDao = new OuterRelationDAO();
				SuperVO[] items = (SuperVO[]) info.getChildrenVO();
				for (int i = 0; i < items.length; i++) {
					relDao.deleteRelation(items[i].getPrimaryKey());
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * ���ݶ������ͺ���Ӧ�ĵ������ͻ����Ҫ�Ĵ�����
	 *
	 * @param info
	 * @param action
	 * @return
	 */
	private String getClass(SettlementAggVO info, String action)
			throws BusinessException {
		SettlementHeadVO vo = (SettlementHeadVO) info.getParentVO();

		if (info.isReturnBill()) {// ��ǰ����Ϊ��Ʊ����
			String key = action + "_" + CMPConstant.BILLTYPE_RETURN;
			return CMPConstant.getMap().get(key);
		}

		String parentBillType = getDjdl(vo);
		if (CommonUtil.isNull(parentBillType)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000227")/*@res "���ݵ������ͣ�"*/ + vo.getPk_tradetype()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000228")/*@res "��ȡ�õĵ��ݴ���Ϊ�ա�"*/);
		}

		vo.setDef11(parentBillType);//def11������ʱ���õ��ݴ��࣬����ÿ�δ��������ݿ��ѯ

		String key = action + "_" + parentBillType;

		return CMPConstant.getMap().get(key);
	}
	
	
	/**
	 * �ж��Ƿ�Ϊ��Ʊ����
	 *
	 * @return
	 */
	private boolean isReturnBil(SettlementAggVO oldInfo, SettlementAggVO newInfo)
			throws BusinessException {
		// ��Ʊ�༭������
		OuterRelationDAO relDao = new OuterRelationDAO();
		OuterVO[] outerVos = relDao.queryByPk_h(newInfo.getParentVO()
				.getPrimaryKey());
		if (!CommonUtil.isNull(outerVos)) {
			if (outerVos[0].getPk_billtypecode().equals(
					FbmBusConstant.BILLTYPE_RETURN)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��Ʊ�༭���账��
	 *
	 * @param oldinfo
	 * @param newinfo
	 * @throws BusinessException
	 */
	private void noProcessReturnBill(SettlementAggVO oldinfo,
			SettlementAggVO newinfo) throws BusinessException {
		SettlementHeadVO vo = (SettlementHeadVO) newinfo.getParentVO();

		String pk_billtype = vo.getPk_tradetype();// ��������
		
		if (isReturnBil(oldinfo, newinfo)
				|| ProductInfo.pro_FBM.equals(vo.getSystemcode())) {
			if (pk_billtype != CMPConstant.BILLTYPE_HJ) {
				isFbmFieldChanged(oldinfo, newinfo);
			}
			return;
		}
	}

	/**
	 * �����Ʊ���ɵ��� �ֶ��Ƿ��޸�
	 *
	 * @param oldVo
	 * @param newVo
	 * @throws BusinessException
	 */
	private void isFbmFieldChanged(SettlementAggVO oldVo, SettlementAggVO newVo)
			throws BusinessException {
		SettlementBodyVO[] oldItems = (SettlementBodyVO[]) oldVo
				.getChildrenVO();
		SettlementBodyVO[] newItems = (SettlementBodyVO[]) newVo
				.getChildrenVO();

		int oldLen = oldVo.getChildrenVO().length;
		Map<String, SettlementBodyVO> oldMap = new HashMap<String, SettlementBodyVO>();
		for (int i = 0; i < oldLen; i++) {
			oldMap.put(oldItems[i].getPk_bill(), oldItems[i]);
		}

		int len = newVo.getChildrenVO().length;
		// ѭ������ֶ�ֵ
		for (int i = 0; i < len; i++) {
			// newVo�ﲻ����VO״̬ΪDELETE��
			if (newItems[i].getStatus() == VOStatus.DELETED) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000182")/*@res "�ʽ�Ʊ�����ɵ��ݲ���ɾ��"*/);
			}
			// newVo�ﲻ����VO״̬ΪNEW��
			if (newItems[i].getStatus() == VOStatus.NEW) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000183")/*@res "�ʽ�Ʊ�����ɵ��ݲ�������"*/);
			}
			SettlementBodyVO oldTmp = oldMap.get(newItems[i].getPk_bill());

			if (oldTmp == null) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000184")/*@res "�ʽ�Ʊ�����ɵ��ݱ༭�󲻿�����"*/);
			}

			if (!isEqual(oldTmp.getNotenumber(), newItems[i].getNotenumber())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "�ʽ�Ʊ�����ɵ��ݣ���"*/ + (i + 1)
						+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000186")/*@res "��Ʊ�ݱ�Ų������޸�"*/);
			}
			if (!isEqual(oldTmp.getReceive(), newItems[i].getReceive())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "�ʽ�Ʊ�����ɵ��ݣ���"*/ + (i + 1) + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000188")/*@res "�н������޸�"*/);
			}
			if (!isEqual(oldTmp.getPay(), newItems[i].getPay())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "�ʽ�Ʊ�����ɵ��ݣ���"*/ + (i + 1) + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000188")/*@res "�н������޸�"*/);
			}
			if (!isEqual(oldTmp.getPk_currtype(), newItems[i].getPk_currtype())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "�ʽ�Ʊ�����ɵ��ݣ���"*/ + (i + 1) + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000189")/*@res "�б��ֲ������޸�"*/);
			}
			if (!isEqual(oldTmp.getPk_trader(), newItems[i].getPk_trader())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000185")/*@res "�ʽ�Ʊ�����ɵ��ݣ���"*/ + (i + 1)
						+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000229")/*@res "�жԷ����Ʋ������޸�"*/);
			}

		}
	}

	/**
	 * �Ƚ�ĳ�ֶ�ֵ�Ƿ����
	 *
	 * @param vo1
	 * @param vo2
	 * @param field
	 * @return
	 */
	private boolean isEqual(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		}
		if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
			return false;
		}

		if (o1.equals(o2)) {
			return true;
		} else {
			return false;
		}
	}

}