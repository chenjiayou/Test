package nc.bs.fbm.alarm;

import java.util.HashMap;
import java.util.Vector;

import nc.bs.fbm.reckon.alarm.FBMAlertMessageFormat;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.pub.pa.IBusinessPlugin2;
import nc.bs.pub.pa.html.IAlertMessage;
import nc.bs.trade.business.HYSuperDMO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.itf.uap.bd.notetype.INotetypeQry;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.fbm.DiscountCalculation.DiscountCalculationVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.pa.CurrEnvVO;
import nc.vo.pub.pa.Key;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 应收票据到期预警后台类
 * <p>创建人：bsrl
 * <b>日期：2007-12-11
 *
 */
public class FBMGatherAlarmBO  implements IBusinessPlugin2 {
	public FBMGatherAlarmBO(){
		   super();
		}

	private String billtype = FbmBusConstant.BILLTYPE_GATHER;
	private String orientation = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000171")/* @res"应收"*/;
	private String[] bodyFields = new String[] {nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000055")/* @res"票据类型"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"票据编号"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000048")/* @res"持票单位"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000172")/* @res"付票单位"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000173")/* @res"收款单位"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000174")/* @res"付款单位"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"币种"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"金额"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000175")/* @res"出票日期"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000176")/* @res"收票日期"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000068")/* @res"到期日期"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000394")/*@res "票据类别"*/};
	private String status = "'" + FbmStatusConstant.REGISTER + "','" + FbmStatusConstant.HAS_BANK_KEEP + "','" +
			 		FbmStatusConstant.HAS_INNER_KEEP + "','" + FbmStatusConstant.HAS_RELIEF_KEEP + "', '"
			 		+ FbmStatusConstant.ON_INNER_KEEP +"', " + "'" + FbmStatusConstant.ON_RELIEF_KEEP +
			 		"', '"+FbmStatusConstant.ON_BANK_BACK+"','" +FbmStatusConstant.ON_IMPAWN +"','"
			 		+ FbmStatusConstant.HAS_IMPAWN + "','" + FbmStatusConstant.ON_BANK_KEEP + "','"
			 		+ FbmStatusConstant.ON_INNER_BACK + "','" + FbmStatusConstant.ON_IMPAWN_BACK + "','"
			 		+ FbmStatusConstant.ON_GATHER + "'";

	private String keepststus = "'" +	FbmStatusConstant.HAS_INNER_KEEP + "','"
	    + FbmStatusConstant.HAS_RELIEF_KEEP + "', '" + FbmStatusConstant.ON_INNER_KEEP +"', "
	    + "'" + FbmStatusConstant.ON_RELIEF_KEEP + "', '"+ FbmStatusConstant.ON_INNER_BACK + "'" ;
	public int getImplmentsType() {
		return IMPLEMENT_RETURNFORMATMSG;
	}

	protected String getBillType() {
		return billtype;
	}

	protected String getOrientation() {
		return orientation;
	}

	protected String[] getbodyFields() {
		return bodyFields;
	}

	protected String getStatus() {
		return status;
	}

	private String getKeepStatus() {
		return keepststus;
	}
	/**
	 * 预警业务类型描述，用来为 [注册代理] 提供需要预警的业务类型的描述
	 * 创建日期：(01-4-25 20:31:32)
	 * @return java.lang.String
	 */
	public String getTypeDescription() {
		return null;
	}
	/**
	 * 预警业务类型名称，用来为 [注册代理] 提供需要预警的业务类型
	 * 一个需要预警的业务插件必须返回需要注册的业务类型。
	 * 创建日期：(01-4-25 20:29:28)
	 * @return java.lang.String
	 */
	public String getTypeName() {
		return null;
	}

	/* （非 Javadoc）
	 * @see nc.bs.pub.pa.IBusinessPlugin2#implementReturnFormatMsg(nc.vo.pub.pa.Key[], java.lang.Object, nc.vo.pub.lang.UFDate)
	 */
	public IAlertMessage[] implementReturnFormatMsg(Key[] keys, Object currEnvVO, UFDate clientLoginDate) throws BusinessException {
		Vector<IAlertMessage> vec = new Vector<IAlertMessage>();
		String corpid = ((CurrEnvVO) currEnvVO).getPk_corp();

		IAlertMessage[] ret = null;
		IAlertMessage temp = implementReturnFormatMsg(keys, corpid,clientLoginDate);
		if (temp != null){
			vec.addElement(temp);
		}
		vec.trimToSize();
		if (vec.size() > 0){
			ret = new IAlertMessage[vec.size()];
			vec.copyInto(ret);
		}
		return ret;
	}
	/**
	 * getKeys 方法注解。
	 */
	private Object   getValueByKey(String key,java.util.HashMap<String, Object> map) throws BusinessException {
		Object objdata = map.get(key);
		if(objdata != null){
			Object temp = objdata.toString().trim();
			if(temp.equals("") || temp.equals("null"))
		         objdata = null;
		}

		return objdata;

	}
	////根据主表VO,获得想要在预警平台中显示的字段
	//private String[] getColValue(PjzbVO pjzbvo){
//		String ColValues[]=new String[11];
//		if(pjzbvo.getPjfx()=="0"){
//			ColValues[0]=nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("362002","UPP362002-000001")/*@res "应收"*/;//票据方向
//		}
//		ColValues[1]=pjzbvo.getPjlx();
//		return ColValues;
	//}
	/**
	 * 返回给定格式的一个接口对象...
	 * 创建日期：(01-5-10 17:32:27)
	 * @return nc.bs.pa.IFormatMessage
	 * @param keys nc.bs.pa.Key[]
	 */
	public nc.bs.pub.pa.html.IAlertMessage implementReturnFormatMsg(nc.vo.pub.pa.Key[] keys, java.lang.String corpPK, nc.vo.pub.lang.UFDate clientLoginDate) throws BusinessException
	{
		String keepunit = null;
		if(OuterProxy.getSettleCenter().isSettleCenter(corpPK)) {
			String strWhere=" bd_cubasdoc.pk_corp1 = '" + corpPK + "' ";
			CustBasVO[] custbasVos = null;
			try {
				custbasVos = OuterProxy.getCustManDocQuery().queryCustBasicInfo(strWhere, corpPK);
			} catch (BusinessException e) {
				Logger.error(e.getMessage(),e);
			}
			if(custbasVos==null||custbasVos.length==0)
				return null;
			keepunit = custbasVos[0].getPk_cubasdoc();
		}

		DiscountCalculationVO[]  registervos = null;
		//所要显示的列名
		String[] colNames = {""};
		//最后显示的所有列的列值
		String[][] colValues = null;
		//从传入的参数中解析必要的值
		String fbmbilltype = null;//票据类型
		String fbmbillstyle = null;//票据类别
		String pk_curr = null;//币种
		int preDays = 0;//报警提前天数
		HashMap<String, Object> map = new HashMap<String, Object>();
		Object objpreDays = null;
		for(int i = 0;i < keys.length; i ++){
		   map.put(keys[i].getName(), keys[i].getValue());
		}
//		获得用户定义的预警条件值
		fbmbilltype = (String)getValueByKey("fbmbilltype", map);
		fbmbillstyle = (String)getValueByKey("fbmbillstyle", map);
		if(fbmbillstyle == null )
			fbmbillstyle = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000062")/*@res "全部"*/;
		pk_curr = (String)getValueByKey("pk_currtype", map);
		objpreDays = getValueByKey("preDays",map);
//		if(fbmbilltype != null) {
//			if(fbmbilltype.equals("银行承兑汇票")) {
//				fbmbilltype = FbmBusConstant.ACCEPTANCE_BANK;
//			} else if(fbmbilltype.equals("商业承兑汇票")) {
//				fbmbilltype = FbmBusConstant.ACCEPTANCE_BUSI;
//			}
//		}




		if(objpreDays!= null){
		   preDays = new UFDouble(objpreDays.toString()).intValue();
		}
		if (preDays < 0)
		{
			colValues = new String[][]{{""}};
			return new FBMAlertMessageFormat(
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000177")/* @res"资金"*/ + getOrientation()+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000178")/* @res"票据预警信息"*/,
				new String[] {nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000177")/* @res"资金"*/ + getOrientation()+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000179")/* @res"票据预警提供的信息不够:距到期天数应为大于等于零的常数"*/},
				colNames, colValues,
				new float[] {},
				new String[] {nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000177")/* @res"资金"*/ + getOrientation()+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000178")/* @res"票据预警信息"*/ });
		}
		//组织数据：
		try{
			 StringBuffer strWhere = new StringBuffer();
			 //系统登录日期>=票据到期日期-报警提前日期
			 UFDate tempdate = clientLoginDate.getDateAfter(preDays);
			 strWhere.append(" exists ( select 1 from fbm_baseinfo b, fbm_action a where a.pk_source = v_fbm_regbase.pk_register and b.pk_baseinfo = v_fbm_regbase.pk_baseinfo " +
			 		"and isnull(a.dr, 0) = 0 and a.isnew = 'Y' and isnull(b.dr, 0) = 0 " +
			 		"and ( ");
			 if(fbmbillstyle.endsWith("自有"))
			     strWhere.append("( a.endstatus in ( "+ getStatus() +" )  and  v_fbm_regbase.pk_corp = '"+ corpPK +"') ");

			 if(fbmbillstyle.endsWith("统管") )
	 			  strWhere.append("( v_fbm_regbase.pk_corp != '"+ corpPK +"' and a.endstatus in ( "+ getKeepStatus() +" ) and v_fbm_regbase.keepunit =  '"+ keepunit +"') " );

	 		 if(fbmbillstyle.endsWith("全部")){
	 			strWhere.append("( a.endstatus in ( "+ getStatus() +" )  and  v_fbm_regbase.pk_corp = '"+ corpPK +"') ");
	 			if(getBillType().equalsIgnoreCase(FbmBusConstant.BILLTYPE_GATHER)) {
	 				strWhere.append("or ( v_fbm_regbase.pk_corp != '"+ corpPK +"' and a.endstatus in ( "+ getKeepStatus() +" ) and v_fbm_regbase.keepunit =  '"+ keepunit +"') " );
	 			}
	 		 }

	 		 strWhere.append("  )" + "  and  v_fbm_regbase.vbillstatus" +
			 		" = "+ IBillStatus.CHECKPASS + " and v_fbm_regbase.pk_billtypecode = '" + getBillType() +"' " );


			 if(tempdate != null) {
				 strWhere.append(" and  b.enddate <= '"+ tempdate.toString() + "' ");
			 }
			 if(pk_curr != null) {
				 strWhere.append("  and b.pk_curr = '"+ pk_curr +"' ");
			 }
			 if(fbmbilltype != null) {
				 strWhere.append(" and b.fbmbilltype = '"+ fbmbilltype +"'");
			 }
			 strWhere.append(" )");

			 HYSuperDMO datadmo = new HYSuperDMO();
			 registervos = (DiscountCalculationVO[]) datadmo.queryByWhereClause(DiscountCalculationVO.class,strWhere.toString(), " enddate asc");
			 if (registervos == null || registervos.length == 0){
				 return null;
			 }
//			 String[] inbases = new String[registervos.length];
//			 for(int i = 0; i < registervos.length; i ++) {
//				 inbases[i] = registervos[i].getPk_baseinfo();
//			 }
//			 String inbase = CommonUtil.buildSqlConditionForIn(inbases);
//			 BaseinfoVO[] baseinfo = (BaseinfoVO[]) datadmo.queryByWhereClause(BaseinfoVO.class," pk_baseinfo in ( " + inbase + ")");

			 for(int i = 0; i < registervos.length; i ++) {
				 CurrencyPublicUtil currutil = new CurrencyPublicUtil(corpPK, registervos[i].getPk_curr());
				 registervos[i].setMoneyy(registervos[i].getMoneyy().setScale(currutil.getYDecimalDigit(), UFDouble.ROUND_HALF_UP));
			 }

		}catch(Exception e){
			if (e instanceof BusinessException) {
				throw (BusinessException)e;
			} else {
				throw new BusinessException(e);
			}
		}
//		票据类型，持票单位，付款单位，币种，
		nc.vo.pub.SuperVOUtil.execFormulaWithVOs(registervos,
					new String[]{
						"fbmbilltype -> getColValue(bd_notetype,notetypename,pk_notetype,fbmbilltype)",
						"holdunit->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,holdunit)",
						"payunit->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,payunit)",
						"pk_curr->getColValue(bd_currtype,currtypename,pk_currtype,pk_curr)",
						"receiveunit->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,receiveunit)",
						"paybillunit->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,paybillunit)",
						});
		Vector<String[]> vct = new Vector<String[]>();
		String[] row = null;
		for(int i = 0; i < registervos.length ;i ++){
			if(getBillType().equals(FbmBusConstant.BILLTYPE_GATHER)) {
				row = new String[12];
			} else {
				row = new String[8];
			}
			row[0] = (String)registervos[i].getAttributeValue(DiscountCalculationVO.FBMBILLTYPE);
			row[1] = (String)registervos[i].getAttributeValue(DiscountCalculationVO.FBMBILLNO);
			if(getBillType().equals(FbmBusConstant.BILLTYPE_GATHER)) {
				row[2] = (String)registervos[i].getAttributeValue(DiscountCalculationVO.HOLDUNIT);
				row[3] = (String)registervos[i].getAttributeValue(DiscountCalculationVO.PAYBILLUNIT);
				row[4] = (String)registervos[i].getAttributeValue(DiscountCalculationVO.RECEIVEUNIT);
				row[5] = (String)registervos[i].getAttributeValue(DiscountCalculationVO.PAYUNIT);
				row[6] = (String)registervos[i].getAttributeValue(DiscountCalculationVO.PK_CURR);
				row[7] = registervos[i].getAttributeValue(DiscountCalculationVO.MONEYY).toString();
				row[8] = registervos[i].getAttributeValue(DiscountCalculationVO.INVOICEDATE).toString();
				row[9] = registervos[i].getAttributeValue(DiscountCalculationVO.GATHERDATE).toString();
				row[10] = registervos[i].getAttributeValue(DiscountCalculationVO.ENDDATE).toString();
				if(registervos[i].getAttributeValue(DiscountCalculationVO.PK_CORP).toString().equalsIgnoreCase(corpPK)) {
					row[11] = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000060")/*@res "自有"*/;
				} else {
					row[11] = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000061")/*@res "统管"*/;
				}

			} else {
				row[2] = (String)registervos[i].getAttributeValue(DiscountCalculationVO.RECEIVEUNIT);
				row[3] = (String)registervos[i].getAttributeValue(DiscountCalculationVO.PAYUNIT);
				row[4] = (String)registervos[i].getAttributeValue(DiscountCalculationVO.PK_CURR);
				row[5] = registervos[i].getAttributeValue(DiscountCalculationVO.MONEYY).toString();
				row[6] = registervos[i].getAttributeValue(DiscountCalculationVO.INVOICEDATE).toString();
				row[7] = registervos[i].getAttributeValue(DiscountCalculationVO.ENDDATE).toString();
			}
			vct.addElement(row);
		}

		if(vct.size() == 0)
			return null;
		String[][] datas = new String[vct.size()][12];
		vct.copyInto(datas);
//		产生格式化信息
		FBMAlertMessageFormat msg = new FBMAlertMessageFormat();
		msg.setTitle(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000177")/* @res"资金"*/ + getOrientation()+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000180")/* @res"票据预警"*/);
		msg.setTop(new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000181")/* @res"即将到期的"*/ + getOrientation()+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000182")/* @res"票据信息"*/});
		msg.setBodyFields(getbodyFields());
		msg.setBodyValue(datas);
		return msg;
	}

	/**
	 * 业务插件实现此方法时，如果需要预警，返回一个非空的字符串...。
	 * 创建日期：(01-5-10 17:01:25)
	 * @return java.lang.String
	 * @param keys nc.bs.pa.Key[]
	 */
	public String implementReturnMessage(nc.vo.pub.pa.Key[] keys, String corpPK, nc.vo.pub.lang.UFDate clientLoginDate) throws BusinessException {
		return null;
	}
	/**
	 * 业务插件如果实现此方法，则意味着预警平台将 Key 数组和 fileName 传入
	 * 业务插件可以根据 Key[] 来判断是否需要预警，如果要，则将预警信息写入到 fileName 文件中，并且一定要返回 true，否则视为不需预警
	 * 创建日期：(01-4-20 9:03:48)
	 * @return java.lang.String
	 * @param aas nc.bs.pa.RegistryItem
	 */
	public boolean implementWriteFile(nc.vo.pub.pa.Key[] keys, String fileName, String corpPK, nc.vo.pub.lang.UFDate clientLoginDate) throws BusinessException {
		return false;
	}
	/**
	 * getKeys 方法注解。
	 */
	public nc.vo.pub.pa.Key[] getKeys() {
		return null;
	}
	/**
	 * 当业务插件实现此方法时，则如果需要预警，则返回一个非空的的对象...
	 * 创建日期：(01-5-10 17:04:38)
	 * @return java.lang.Object
	 * @param keys nc.bs.pa.Key[]
	 */
	public Object implementReturnObject(nc.vo.pub.pa.Key[] keys, String corpPK, nc.vo.pub.lang.UFDate clientLoginDate) throws BusinessException {
		return null;
	}
	/* （非 Javadoc）
	 * @see nc.bs.pub.pa.IBusinessPlugin2#implementReturnMessage(nc.vo.pub.pa.Key[], java.lang.Object, nc.vo.pub.lang.UFDate)
	 */
	public String[] implementReturnMessage(Key[] keys, Object currEnvVO, UFDate clientLoginDate) throws BusinessException {
		// TODO 自动生成方法存根
		return null;
	}
	/* （非 Javadoc）
	 * @see nc.bs.pub.pa.IBusinessPlugin2#implementReturnObject(nc.vo.pub.pa.Key[], java.lang.Object, nc.vo.pub.lang.UFDate)
	 */
	public Object implementReturnObject(Key[] keys, Object currEnvVO, UFDate clientLoginDate) throws BusinessException {
		// TODO 自动生成方法存根
		return null;
	}
	/* （非 Javadoc）
	 * @see nc.bs.pub.pa.IBusinessPlugin2#implementWriteFile(nc.vo.pub.pa.Key[], java.lang.String, java.lang.Object, nc.vo.pub.lang.UFDate)
	 */
	public boolean implementWriteFile(Key[] keys, String fileName, Object currEnvVO, UFDate clientLoginDate) throws BusinessException {
		// TODO 自动生成方法存根
		return false;
	}
}