package nc.bs.fbm.out;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nc.bs.fbm.pub.ActionQueryDAO;
import nc.bs.fbm.pub.BaseInfoBO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.ep.dj.DJZBVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

/**
 * ���ո��ת��Ϊ��Ҫ�Ĳ�������
 *
 * @author xwq
 *
 */
public class ArapBillDataAdapter {

	/**
	 * �ո�������-��������
	 */
	public static String ACTION_ADD = "add";

	/**
	 *  �ո�������-�༭����
	 */
	public static String ACTION_EDIT = "edit";

	/**
	 * �ո������� - ɾ��
	 */
	public static String ACTION_DEL = "del";

	/**
	 * �ո������� - ��Ч
	 */
	public static String ACTION_EFFECT = "effect";

	/**
	 * �ո������� - ȡ����Ч
	 */
	public static String ACTION_UNEFFECT = "uneffect";
	
	/**
	 * ԭ��
	 */
	private static final int YB = 0;

	/**
	 * ����
	 */
	private static final int FB = 1;


	/**
	 * ����
	 */
	private static final int BB = 2;

	/**
	 * ���ո����VOת��ΪƱ�ݴ���Ĳ�������
	 *
	 * @param param
	 * @return
	 */
	public static ArapBillParamVO buildBillParam(DJZBVO param, DJZBItemVO item,
			int row, String actionCode) throws BusinessException {

		ArapBillParamVO arapParam = new ArapBillParamVO();
		String djdl = param.header.getDjdl();
		String djlxbm = param.header.getDjlxbm();

		arapParam = new ArapBillParamVO();
		if(item.m_pjh != null){
			arapParam.setFbmbillno(item.m_pjh.trim());
		}
		arapParam.setPk_curr(item.getCurrTypePk());
		arapParam.setDjrq(param.header.getDjrq());

		//���ϲ�����
		if (!djdl.equals("hj")) {
			Map<String, UFDouble> mapMoneyy = null;
			Map<String, UFDouble> mapMoneyf = null;
			Map<String, UFDouble> mapMoneyb = null;
			mapMoneyy = sumMoney((DJZBItemVO[]) param.getChildrenVO(), djdl, YB);
			mapMoneyf = sumMoney((DJZBItemVO[]) param.getChildrenVO(), djdl, FB);
			mapMoneyb = sumMoney((DJZBItemVO[]) param.getChildrenVO(), djdl, BB);
			arapParam.setMoneyy(mapMoneyy.get(arapParam.getFbmbillno()));
			arapParam.setMoneyf(mapMoneyf.get(arapParam.getFbmbillno()));
			arapParam.setMoneyb(mapMoneyb.get(arapParam.getFbmbillno()));
		} else {
			UFDouble jfybje = item.getJfybje();
			UFDouble jffbje = item.getJffbje();
			UFDouble jfbbje = item.getJfbbje();
			if (jfybje == null) {
				jfybje = new UFDouble(0);
			}
			if (jffbje == null) {
				jffbje = new UFDouble(0);
			}
			if (jfbbje == null) {
				jfbbje = new UFDouble(0);
			}
			arapParam.setMoneyy(jfybje.add(item.getDfybje()));
			arapParam.setMoneyf(jffbje.add(item.getDffbje()));
			arapParam.setMoneyb(jfbbje.add(item.getDfbbje()));
		}
		arapParam.setFrate(item.getFbhl());// ���һ���
		arapParam.setBrate(item.getBbhl());// ���һ���

		UFDouble txlx_ybje = item.getTxlx_ybje();
		if (item.getTxlx_ybje() == null) {
			txlx_ybje = new UFDouble(0);
		}
		arapParam.setTxlx_ybje(txlx_ybje);// ������Ϣ

		UFDouble txfy_ybje = item.getYbtxfy();
		if (item.getYbtxfy() == null) {
			txfy_ybje = new UFDouble(0);
		}
		arapParam.setTxfy_ybje(txfy_ybje);// ����������

		arapParam.setPk_busbill(item.getDdhh());
		if (item.getJsfsbm() != null) {
			arapParam.setPk_billtypecode(item.getJsfsbm().trim());
		}
		arapParam.setPk_bill_h(param.header.getPrimaryKey());
		arapParam.setPk_bill_b(item.getPrimaryKey());
		arapParam.setOuterbilltype(djlxbm);// �������ͱ���
		arapParam.setOuterdjdl(djdl);// ���õ��ݴ���
		arapParam.setRow(row + 1);
		arapParam.setSkbankacc(item.getSkyhzh());// �տ������˺�
		arapParam.setFkbankacc(item.getFkyhzh());// ���������˺�
		arapParam.setVoperator(param.header.getLrr());
		arapParam.setDoperatdate(param.header.getDjrq());
		arapParam.setVeffector(param.header.getSxr());
		arapParam.setDeffectdate(param.header.getSxrq());
		arapParam.setJsfs(item.getPj_jsfs());

		if (actionCode.equals(ACTION_EFFECT)) {
			arapParam.setOuterstatus(FbmBusConstant.OUTERBILL_OVER);
		} else {
			arapParam.setOuterstatus(FbmBusConstant.OUTERBILL_USE);
		}

		String pk_corp = param.header.getDwbm();

		CommonDAO dao = new CommonDAO();
		String pk_cust = dao.queryCustByCorp(pk_corp);

		if (pk_cust == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000224")/* @res"��˾δ��Ӧ����"*/);
		}
		arapParam.setCurrentunit(pk_cust);// ������λ��ȡ��˾��Ӧ����

		arapParam.setOtherunit(item.getHbbm());// �Է���λ��ȡ������

		arapParam.setPk_corp(pk_corp);

		// ��ѯƱ�ݻ�����ϢVO
		if(arapParam.getFbmbillno()!=null&&arapParam.getFbmbillno().length() >0){
			BaseInfoBO baseBO = new BaseInfoBO();
			arapParam.setBaseinfoVO(baseBO.queryByFbmbillno(arapParam.getFbmbillno()));
			// ��ѯƱ�����¶���VO
			ActionQueryDAO actionDao = new ActionQueryDAO();
			arapParam.setNewActionVO(actionDao.queryNewestByFbmBillno(arapParam
					.getFbmbillno(),pk_corp));
			if (arapParam.getPk_busbill() == null) {
				arapParam.setPk_busbill(arapParam.getNewActionVO().getPk_source());
			}
			// ��ѯ��ǰ���ݹ���
			OuterRelationDAO relDao = new OuterRelationDAO();
			arapParam.setOuterVO(relDao.queryBypk_b(item.getPrimaryKey()));
		}
		return arapParam;
	}

	/**
	 * �ո�������ת��Ϊ�����������
	 *
	 * @param param
	 * @param actionCode
	 * @return
	 * @throws BusinessException
	 */
	public HYBillVO buildEndoreData(DJZBVO param) throws BusinessException {
		EndoreVO endoreVO = new EndoreVO();
		HYBillVO billVO = new HYBillVO();

		billVO.setParentVO(endoreVO);
		return billVO;
	}

	/**
	 * �ϲ�Ʊ�ݽ�� moneytype 0 ԭ�� 1 ���� 2 ����
	 *
	 * @param items
	 * @return
	 */
	private static Map<String, UFDouble> sumMoney(DJZBItemVO[] items,
			String djdl, int moneytype) throws BusinessException{
		Map<String, UFDouble> map = new HashMap<String, UFDouble>();
		Set<String> billnoSet = new HashSet<String>();
		String billno = null;
		UFDouble money = null;

		for (int i = 0; i < items.length; i++) {

			if(items[i].getPjh()!=null){
				billno = items[i].getPjh().trim();
			}
			else{
				continue;
			}

			if (djdl.equals("sk") || djdl.equals("sj")) {
				switch (moneytype) {
				case YB:
					money = items[i].getDfybje();
					break;
				case FB:
					money = items[i].getDffbje();
					break;
				case BB:
					money = items[i].getDfbbje();
					break;
				}
			}
			if (djdl.equals("fk") || djdl.equals("fj")) {
				switch (moneytype) {
				case YB:
					money = items[i].getJfybje();
					break;
				case FB:
					money = items[i].getJffbje();
					break;
				case BB:
					money = items[i].getJfbbje();
					break;
				}
			}
			if (money == null) {
				money = new UFDouble(0);
			}

			if (billnoSet.contains(billno)) {
				UFDouble tmpMoney = map.get(billno);
				map.put(billno, tmpMoney.add(money));
			} else {
				map.put(billno, money);
			}
			billnoSet.add(billno);
		}
		return map;
	}

}