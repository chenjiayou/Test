package nc.impl.fbm.endore;

import nc.bs.fbm.endore.SingletonEndoreService;
import nc.itf.fbm.endore.IEndoreService;
import nc.vo.fbm.pub.constant.FbmBusConstant;

public class IEndoreServiceImpl implements IEndoreService {
	
	/**
	 * 判断公司是否启用收付报
	 */
	public boolean volidARAP(String pk_corp, String[] product) throws Exception {
		//EndoreService es = new EndoreService();
		return SingletonEndoreService.getInstance().productEnableByCorp(pk_corp, product);
	}
	
	/**
	 * 取参数值
	 */
	public String getParamValue(String pk_corp) throws Exception {
		return SingletonEndoreService.getInstance().getParameterValue(pk_corp);
	}
	
	/**
	 * 判断是否为收复报推式生成的背书
	 */
	public boolean isCratedByARAP(String pk_endore) throws Exception {
		String arapflag = SingletonEndoreService.getInstance().getSyscode(pk_endore);
		if(FbmBusConstant.ENDORE_SYS_INPUT.equals(arapflag)){
			return false;
		}
		return true;
	}

}
