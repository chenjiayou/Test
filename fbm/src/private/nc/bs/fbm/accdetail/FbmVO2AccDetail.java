package nc.bs.fbm.accdetail;

import nc.itf.cdm.util.CommonUtil;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.vo.cdm.exception.InnerAccountException;
import nc.vo.fts.account.AccDetailVO;
import nc.vo.fts.account.AccMessageVO;
import nc.vo.fts.account.IAccountConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;

/**
 * 票据单据对象转换为内部帐户帐对象的基类
 * @author xwq
 *
 * 2008-9-24
 */
public class FbmVO2AccDetail {

	
	/**
	 * 处理内部帐户账返回的消息，如果消息类型为hint则抛异常提示用户，其他不处理
	 *
	 * @param vos
	 * @throws BusinessException
	 */
	protected void dealInnerAccountException(AccMessageVO[] vos)
			throws BusinessException {
		StringBuffer errMsg = null;
		for (int i = 0; !CommonUtil.isNull(vos) && i < vos.length; i++) {
			if (IAccountConst.HINT.equals(vos[i].getMessagetype())) {// 如果为提示信息
				if (null == errMsg) {
					errMsg = new StringBuffer();
				}
				errMsg.append(vos[i].getErrormessge()).append("\n");
			}
		}
		if (null != errMsg) {
			throw new InnerAccountException(IAccountConst.HINT, errMsg
					.toString());
		}
	}
	
	public CurrencyPublicUtil createCurrencyPublicUtil(String pk_corp,String pk_curr) {
		CurrencyPublicUtil currencyPublicUtil = new CurrencyPublicUtil(pk_corp);
		currencyPublicUtil.setPk_currtype_y(pk_curr);
		
		return currencyPublicUtil;
	}
	
	
	public void fillCurrKeyValue(AccDetailVO accdetailVO) throws BusinessException{
		CurrencyPublicUtil currUtil = createCurrencyPublicUtil(accdetailVO.getPk_oppcorp(),accdetailVO.getPk_currtype());
		UFDouble[] fbrate = currUtil.getExchangeRate(String.valueOf(accdetailVO.getTallydate()));
		UFDouble moneyy = null;
		if(accdetailVO.getSfflag() == IAccountConst.INFLAG){
			moneyy = accdetailVO.getInmoney();
		}else{
			moneyy = accdetailVO.getOutmoney();
		}
		UFDouble[] yfbmoney = currUtil.getYfbMoney(moneyy, String.valueOf(accdetailVO.getTallydate()));
		accdetailVO.setFrate(fbrate[0]);
		accdetailVO.setBrate(fbrate[1]);
		if(accdetailVO.getSfflag() == IAccountConst.INFLAG){
			accdetailVO.setInmoneyf(yfbmoney[1]);
			accdetailVO.setInmoneyb(yfbmoney[2]);
		}else{
			accdetailVO.setOutmoneyf(yfbmoney[1]);
			accdetailVO.setOutmoneyb(yfbmoney[2]);
		}
		
	}
	
	 
	
	
}
