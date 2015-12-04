package nc.bs.fbm.endore;

import java.util.Hashtable;

import nc.bs.framework.common.NCLocator;
import nc.bs.trade.business.HYPubBO;
import nc.itf.uap.sf.ICreateCorpQueryService;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.para.SysInitBO_Client;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

public class SingletonEndoreService {

	/* Ψһʵ�� */
	private static SingletonEndoreService singleService = new SingletonEndoreService();

	private SingletonEndoreService() {
	}

	/**
	 * <p>
	 * SingletonEndoreServiceʵ����Ψһ��ڡ�
	 * <p>
	 * ���ߣ�wangyg ���ڣ�2008-5-7
	 * @return
	 */
	public static SingletonEndoreService getInstance() {

		return singleService;
	}

	/**
	 * <p>
	 * ȡ���������е�syscode�ֶΣ��ж��Ƿ����ո�����ʽ���ɵı������
	 * <p>
	 * ���ߣ�wangyg ���ڣ�2008-5-7
	 * @param pk_endore
	 * @return
	 */
	public String getSyscode(String pk_endore) throws BusinessException {

		HYPubBO hypubbo = new HYPubBO();
		EndoreVO endorevo = (EndoreVO) hypubbo.queryByPrimaryKey(
				EndoreVO.class, pk_endore);
		if (endorevo != null) {
			return endorevo.getSyscode();
		}

		return null;
	}

	/**
	 * <p>
	 * ��ѯ������ֵ��
	 * <p>
	 * ���ߣ�wangyg ���ڣ�2008-5-7
	 * @return
	 * @throws Exception
	 */
	public String getParameterValue(String pk_corp) throws BusinessException {

		String paramValue = "";
		paramValue = SysInitBO_Client.queryByParaCode(pk_corp,FBMParamConstant.FBM005).getValue();
		return paramValue;
	}

	/**
	 * <p>
	 * �ж��Ƿ������ո���
	 * <p>
	 * ���ߣ�wangyg ���ڣ�2008-3-27
	 * @param pk_corp
	 * @param product
	 * @return
	 * @throws BusinessException
	 */
	public boolean productEnableByCorp(String pk_corp, String[] product)
			throws BusinessException {
		if (pk_corp == null) {
			return false;
		}
		ICreateCorpQueryService ProductService = (ICreateCorpQueryService) NCLocator
				.getInstance().lookup(ICreateCorpQueryService.class.getName());
		Hashtable productEnabled = ProductService.queryProductEnabled(pk_corp,
				product);
		boolean flag = false;
		for (int i = 0; i < product.length; i++) {
			if (((UFBoolean) productEnabled.get(product[i])).booleanValue() == true) {
				flag = true;
			}
		}
		return flag;
	}

}
