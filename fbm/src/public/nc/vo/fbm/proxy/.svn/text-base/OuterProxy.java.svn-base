/**
 *
 */
package nc.vo.fbm.proxy;

import nc.bs.framework.common.NCLocator;
import nc.bs.framework.naming.Context;
import nc.bs.logging.Logger;
import nc.itf.uap.bd.notetype.INotetypeQry;
import nc.itf.cc.control.ICcRationManage;
import nc.itf.dap.pub.IDapSendMessage;
import nc.itf.fbm.relief.IReliefService;
import nc.itf.fts.pub.ICommon;
import nc.itf.uap.IPKLockBS;
import nc.itf.uap.bd.ISettleCenter;
import nc.itf.uap.bd.ISettleUnitQry;
import nc.itf.uap.bd.accbank.IAccbankUpdateService;
import nc.itf.uap.bd.account.IAccountDoc;
import nc.itf.uap.bd.currtype.ICurrtype;
import nc.itf.uap.bd.cust.ICuBasDocQry;
import nc.itf.uap.bd.cust.ICustManDocQuery;
import nc.itf.uap.busibean.ISysInitQry;
import nc.itf.uap.sf.ICreateCorpQueryService;
import nc.itf.uap.sfapp.IBillcodeRuleService;

/**
 * 
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-8-13
 *
 */
public class OuterProxy {

	/**
	 * 
	 */
	public OuterProxy() {
		// TODO Auto-generated constructor stub
	}

	private static Context getLocator() {
		return NCLocator.getInstance();
	}
	
	/**
	 * <p>
	 * 获取客商基本档案查询类。
	 * <p>
	 * 作者：hzguo <br>
	 * 日期：2006-6-29
	 * 
	 * @return
	 */
	public static ICuBasDocQry getCuBasDocQry() {
		return (ICuBasDocQry) getLocator().lookup(ICuBasDocQry.class.getName());
	}

	/**
	 * <p>
	 * 获取客商管理档案查询类。
	 * <p>
	 * 作者：qbh <br>
	 * 日期：2006-6-30
	 * 
	 * @return
	 */
	public static ICustManDocQuery getCustManDocQuery() {
		return (ICustManDocQuery) getLocator().lookup(ICustManDocQuery.class.getName());
	}
	
	/**
	 * 
	 * <p>
	 * 获得单据号服务类
	 * <p>
	 * 作者：lpf
	 * 日期：2007-8-14
	 * @return
	 */
	public static IBillcodeRuleService getBillCodeRuleService(){
		return (IBillcodeRuleService) getLocator().lookup(IBillcodeRuleService.class.getName());
	}

	/**
	 * <p>
	 * <p>
	 * 作者：qbh <br>
	 * 日期：2006-7-3
	 * 
	 * @return
	 */
	public static IPKLockBS getPKLockBS() {
		return (IPKLockBS) getLocator().lookup(IPKLockBS.class.getName());
	}

	/**
	 * <p>
	 * <p>
	 * 作者：qbh <br>
	 * 日期：2006-7-4
	 * 
	 * @return
	 */
	public static ICurrtype getCurrtype() {
		return (ICurrtype) getLocator().lookup(ICurrtype.class.getName());
	}
	
	/**
	 * <p>
	 * 创建公司数据库查询服务
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-18
	 * @return
	 */
	public static ICreateCorpQueryService getCreateCorpQuery(){
		return (ICreateCorpQueryService) getLocator().lookup(ICreateCorpQueryService.class.getName());
	}
	
	/**
	 * <p>
	 * 授信控制类
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-24
	 * @return
	 */
	public static ICcRationManage getCcRationManage(){
		return (ICcRationManage) getLocator().lookup(ICcRationManage.class.getName());
	}
	
    public static ISysInitQry getSysInitQry() {
    	try {
            return (ISysInitQry)getLocator().lookup(ISysInitQry.class.getName());
        }catch(Exception e){
        	Logger.error(e.getMessage(),e);
            return null;
        }
    }

	/**
	 * <p>
	 * 获取结算中心相关业务接口。
	 * <p>
	 * 作者：qbh <br>
	 * 日期：2006-7-5
	 * 
	 * @return
	 */
	public static ISettleCenter getSettleCenter() {
		return (ISettleCenter) getLocator().lookup(ISettleCenter.class.getName());
	}
	
	/**
	 * 
	 * <p>
	 * 得到会计平台服务接口
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-24
	 * @return
	 */
	public static IDapSendMessage getDapSendMessageService(){
		return (IDapSendMessage) getLocator().lookup(IDapSendMessage.class.getName());
	}
	
	/**
	 * <p>
	 * 结算单位查询接口
	 * <p>
	 * 作者：lpf
	 * 日期：2007-11-28
	 * @return
	 */
	public static ISettleUnitQry getSettleUnitQryService(){
		return (ISettleUnitQry)getLocator().lookup(ISettleUnitQry.class.getName());
	}
	
	/**
	 * <p>
	 * 得到物权担保的服务接口
	 * <p>
	 * @return
	 */
	public static nc.itf.fi.impawn.IImpawn getImpawnService(){
		return (nc.itf.fi.impawn.IImpawn)getLocator().lookup(nc.itf.fi.impawn.IImpawn.class.getName());
	}
	
	/**
	 * 得到票据调剂服务类
	 * @return
	 */
	public static IReliefService getReliefService() {
		return (IReliefService) getLocator().lookup(IReliefService.class.getName());
	}
	
	/**
	 * <p>
	 * 得到内部账户服务类
	 * <p>
	 * 作者：lpf
	 * 日期：2007-12-29
	 * @return
	 */
	public static IAccountDoc getInnerAccountService(){
		return (IAccountDoc)getLocator().lookup(IAccountDoc.class.getName());
	}

	/**
	 * 现金平台得到票据类型服务类
	 * @return
	 */
	public static INotetypeQry getNoteTypeService() {
		return (INotetypeQry)getLocator().lookup(INotetypeQry.class.getName());
	}
	
	
    /**
     * <p>
     * 开户银行升级服务 V5.02
	 * 作者：lpf
	 * 日期：2008-01-17
	 * @return
     */
    
    public static IAccbankUpdateService getAccbankUpdateService(){
   		return (IAccbankUpdateService)getLocator().lookup(IAccbankUpdateService.class.getName());
    }
    
    /**
     * 获得结算提供服务类
     */
    public static ICommon getFTSCommonService(){
    	return (ICommon)getLocator().lookup(ICommon.class.getName());
    }
}
