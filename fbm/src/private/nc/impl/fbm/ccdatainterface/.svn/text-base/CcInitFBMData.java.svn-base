/**
 *
 */
package nc.impl.fbm.ccdatainterface;

import java.util.ArrayList;
import java.util.List;

import nc.bs.fbm.gather.FBMPubQueryDAO;
import nc.itf.cdm.util.CommonUtil;
import nc.vo.cc.control.ICcControl;
import nc.vo.cc.control.ICcInitialData;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.ccinterface.CCDataAdapter;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 授信CC初始化调用方法
 * <p>创建人：lpf
 * <b>日期：2007-9-27
 *
 */
public class CcInitFBMData implements ICcInitialData {

	/**
	 * 
	 */
	public CcInitFBMData() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 票据授信初始化调用方法,查询期初数据,已开票、已付票、在付票状态的未核销的开出票据-used
	 * 所有已付款、已核销状态的开出票据-release
	 */
	public ICcControl[] queryInitialData() throws BusinessException {
    	ICcControl[] invoiceControls=null;
    	ICcControl[] acceptControls=null;
        StringBuffer sql = new StringBuffer();
        sql.append(" select fbm_register.verifydate,fbm_register.cctype,fbm_register.securitymoney,fbm_register.vbillno,fbm_register.pk_register,fbm_baseinfo.enddate,fbm_register.pk_loanbank,");
        sql.append(" fbm_baseinfo.invoicedate, fbm_register.impawnmode,fbm_baseinfo.invoiceunit,fbm_baseinfo.payunit,fbm_baseinfo.receiveunit, " );
        sql.append(" fbm_register.isverify,fbm_baseinfo.fbmbilltype, fbm_baseinfo.fbmbillno,pk_curr,fbm_baseinfo.moneyy ");
        sql.append(" from fbm_register left join fbm_baseinfo on (fbm_baseinfo.pk_baseinfo =  fbm_register.pk_baseinfo)");
        sql.append(" where isnull(fbm_register.dr,0)=0 and isnull(fbm_baseinfo.dr,0)=0  ");
        sql.append(" and fbm_register.pk_billtypecode='36GL' and cctype<>'NONE' ");
        sql.append(" and (fbm_register.vbillstatus="+String.valueOf(IBillStatus.CHECKPASS)+" "); 
        sql.append(" or fbm_register.vbillstatus="+String.valueOf(IFBMStatus.Create)+" ) ");
       	FBMPubQueryDAO service = new FBMPubQueryDAO();
       	SuperVO[] invoiceVos = service.queryData(sql.toString(),RegisterVO.class);
        if(invoiceVos!=null&&invoiceVos.length>0){
        	invoiceControls = convertToCcontrol(invoiceVos);
        }
        
        sql = new StringBuffer();
        sql.append(" select fbm_register.cctype,fbm_register.securitymoney,fbm_register.impawnmode,fbm_accept.pk_source,fbm_baseinfo.enddate,fbm_accept.vbillno, ");
        sql.append(" fbm_baseinfo.invoicedate, fbm_baseinfo.invoiceunit,fbm_baseinfo.payunit,fbm_baseinfo.receiveunit, " );
        sql.append(" fbm_baseinfo.fbmbilltype, fbm_baseinfo.payunit,pk_curr,fbm_baseinfo.moneyy billmoneyy, ");
        sql.append(" fbm_accept.dacceptdate,fbm_accept.pk_accept,fbm_register.pk_loanbank ");
        sql.append(" from fbm_accept left join fbm_register on(fbm_register.pk_register=fbm_accept.pk_source) "); 
        sql.append(" left join fbm_baseinfo on (fbm_baseinfo.pk_baseinfo =  fbm_register.pk_baseinfo)");
        sql.append(" where isnull(fbm_register.dr,0)=0 and isnull(fbm_baseinfo.dr,0)=0  and isnull(fbm_accept.dr,0)=0 ");
        sql.append(" and fbm_register.pk_billtypecode='36GL' and cctype<>'NONE' ");
        sql.append(" and (fbm_accept.vbillstatus="+String.valueOf(IBillStatus.CHECKPASS)+" "); 
        sql.append(" or fbm_accept.vbillstatus="+String.valueOf(IFBMStatus.Create)+" )");
        SuperVO[] acceptVos = service.queryData(sql.toString(),AcceptVO.class);
        List<ICcControl> retList = new ArrayList<ICcControl>();
        
        if(acceptVos!=null&&acceptVos.length>0){
        	acceptControls = convertToReleaseData(acceptVos);
        	retList = CommonUtil.addArrayToList(retList, acceptControls);
         }

        if(invoiceControls!=null&&invoiceControls.length>0){
        	retList = CommonUtil.addArrayToList(retList, invoiceControls);
        }
        
        ICcControl[] retVos = null;
        if(retList.size()>0){
        	retVos = new ICcControl[retList.size()];
        	retVos=retList.toArray(retVos);
        }
        return retVos;
	}
	
	/**
	 * <p>
	 * 付款VO
	 * <p>
	 * 作者：lpf
	 * 日期：2007-11-2
	 * @param sourceVos
	 * @return
	 */
	private ICcControl[] convertToReleaseData(SuperVO[] sourceVos) {
		 ICcControl[] control = new ICcControl[sourceVos.length];
		for (int i = 0; i < sourceVos.length; i++) {
			AcceptVO acceptVo = (AcceptVO) sourceVos[i];
			control[i] = CCDataAdapter.getInstance().acceptToCControlForApprove(acceptVo);
		}
		return control;
	}

	/**
	 * <p>
	 * 开票组织VO,核销
	 * <p>
	 * 作者：lpf
	 * 日期：2007-11-2
	 * @param retvos
	 * @return
	 */
	private ICcControl[] convertToCcontrol(SuperVO[] retvos) {
		ICcControl[] ccControl;
		ArrayList<ICcControl> list = new ArrayList<ICcControl>();
        if(retvos!=null&&retvos.length>0){
        	for (int i = 0; i < retvos.length; i++) {
        		if(retvos[i]!=null){
        			Object isverifyobj = retvos[i].getAttributeValue(RegisterVO.ISVERIFY);
        			UFBoolean isverify = null;
        			ICcControl invoicecontrolVo = null;
        			ICcControl paybillControlVo = null;
        			if(isverifyobj!=null&&isverifyobj instanceof UFBoolean){
        				isverify = (UFBoolean) isverifyobj;
            			if(isverify.equals(UFBoolean.TRUE)){
            				paybillControlVo = CCDataAdapter.getInstance().InvoiceToCControlForCancelBill((RegisterVO)retvos[i]);
             			}
        			}

        			invoicecontrolVo = CCDataAdapter.getInstance().invoiceToCControlForApprove((RegisterVO)retvos[i]);
        			list.add(invoicecontrolVo);
    				if(paybillControlVo!=null){
    					list.add(paybillControlVo);
    				}
        		}
        	}
        }
        ccControl = new ICcControl[list.size()];
        ccControl = list.toArray(ccControl);
        return ccControl;
	}

}
