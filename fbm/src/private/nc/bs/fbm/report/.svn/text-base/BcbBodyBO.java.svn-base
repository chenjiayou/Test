package nc.bs.fbm.report;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.jdbc.framework.DataSourceCenter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.util.DBConsts;
import nc.vo.fbm.report.BcbBodyMaxVO;
import nc.vo.fbm.report.BcbBodyVO;
import nc.vo.fbm.report.ReportParam;
import nc.vo.pub.BusinessException;

import com.borland.dx.dataset.StorageDataSet;

/**
 * 查询备查簿表体数据
 * 
 */
public class BcbBodyBO extends FbmBaseReportBO{
	private BaseDAO dao = new BaseDAO();
	
	public StorageDataSet queryData(ReportParam reportParam) throws BusinessException{
		
		//TODO 以主表PK为查询条件
		ReportTempTable r = new ReportTempTable();
		String tablename = r.createTempTableByClass(BcbBodyVO.class);
		
		String sql = "insert into "+tablename+" ( actioncode, actiondate, actionperson, beginstatus, billtype,  endstatus,isnew, moneyy, pk_action,pk_baseinfo, pk_bill, pk_corp, pk_org, pk_source, serialnum )select  actioncode, actiondate, actionperson, beginstatus, billtype,  endstatus,isnew, moneyy, pk_action,pk_baseinfo, pk_bill, pk_corp, pk_org, pk_source, serialnum from fbm_action as a ";
		sql = sql + " where  exists (select 1  from fbm_baseinfo left join fbm_action  on fbm_baseinfo.pk_baseinfo=fbm_action.pk_baseinfo where a.pk_baseinfo=fbm_baseinfo.pk_baseinfo and "+makeQueryConditionSql(reportParam)+") ";
		dao.executeUpdate(sql);
		
		updatePK2Name(tablename);
		
		String actionmax_tablename = r.createTempTableByClass(BcbBodyMaxVO.class);
		
		String togatherKey = "||";
		boolean isSQLSERVER = DataSourceCenter.getInstance().getDatabaseType() == DBConsts.SQLSERVER;
		if(isSQLSERVER){
			togatherKey = "+";
		}
		
		
		
		String maxsql ="insert into " + actionmax_tablename + "(pk_bill,serialnum)  select pk_bill " +togatherKey +"pk_baseinfo,max(serialnum) from "+tablename+" where actioncode <> '中心使用' and actioncode <> '核销' and billtype<>'票据质押' group by pk_bill " +togatherKey +"pk_baseinfo";
		dao.executeUpdate(maxsql);
		
		String maxsql_destroy ="insert into " + actionmax_tablename + "(pk_bill,serialnum)  select pk_bill " +togatherKey +"pk_baseinfo,serialnum from "+tablename+" where  actioncode = '核销' or (billtype='票据质押' and actioncode in('审核','质押回收'))";
		dao.executeUpdate(maxsql_destroy);
		
		List<BcbBodyMaxVO> a = (List<BcbBodyMaxVO>)dao.executeQuery("select * from " + actionmax_tablename, new BeanListProcessor(BcbBodyMaxVO.class));
		
		String joinpart = tablename + " join fbm_baseinfo on fbm_baseinfo.pk_baseinfo = "+tablename+".pk_baseinfo join  bd_currtype on bd_currtype.pk_currtype=fbm_baseinfo.pk_curr, " + actionmax_tablename + " where "+tablename+".pk_bill"+togatherKey+tablename+".pk_baseinfo="+actionmax_tablename+".pk_bill and "+tablename+".serialnum =" + actionmax_tablename+".serialnum" ;
		sql = "select vbillno,billtype,actiondate,actioncode,actionperson,beginstatus,endstatus, fbm_baseinfo.moneyy,"+tablename+".serialnum, remark, sxfmoneyy,storage_keepbank, storage_inputunit,  storage_outputunit,storage_keepbank,register_paybillunit,collection_bankacc, collection_bank, discount_bankacc, discount_bank, discount_rate,discount_lx, endore_endorser,endore_endorsee,impawn_creditunit, impawn_debitunit,relief_reliefunit,register_cctype, register_securityacc, register_securityrate, register_securitymoneyy, register_mode,accept_holdunit, accept_holderacc, accept_backsecacc,accept_banksecmoneyy ,return_person ,return_date ,return_type,"+tablename+".pk_bill,pk_action,fbm_baseinfo.pk_baseinfo,pk_corp,isnew,pk_source,pk_org,bd_currtype.currdigit from "+joinpart+" order by fbm_baseinfo.pk_baseinfo,"+tablename+".serialnum";
		
		
		
		List<BcbBodyVO> list = (List<BcbBodyVO>)dao.executeQuery(sql, new BeanListProcessor(BcbBodyVO.class));
		BcbBodyVO[] results = null;
		if(list !=null && list.size() > 0){
			results  = list.toArray(new BcbBodyVO[0]);
		}
		
		return  getDataSetBySuperVos(results, 2 ,BcbBodyVO.class);
	}
	
	/**
	 * 后期要修改成批量的形式
	 * @throws BusinessException
	 */
	private void updatePK2Name(String tablename)throws BusinessException{
		
		List<String> list = new LinkedList<String>();
		Map<String,String> valuemap = new HashMap<String,String>();
		valuemap.put("'SAVE'", "'保存'");
		valuemap.put("'AUDIT'","'审核'");
		valuemap.put("'TRANSACT'","'办理'");
		valuemap.put("'DISABLE'","'作废'");
		valuemap.put("'INPUT'","'确认'");
		valuemap.put("'OUTPUT'","'确认'");
		valuemap.put("'DESTROY'","'核销'");
		valuemap.put("'CENTERUSE'","'中心使用'");
		valuemap.put("'IMPAWNBACK'","'质押回收'");
		
		list.addAll(ReportSqlBuilder.buildByConstant(tablename, "actioncode", valuemap));
		
		
		valuemap =  getStatusEnglishKeyMap();
		
		list.addAll(ReportSqlBuilder.buildByConstant(tablename, "beginstatus", valuemap));
		list.addAll(ReportSqlBuilder.buildByConstant(tablename, "endstatus", valuemap));
		
		StringBuffer sb = new StringBuffer();
		
		//fbm_action
		sb = new StringBuffer();
		sb.append("update "+tablename+" set actionperson = (select user_name from sm_user where cuserid=actionperson)");
		list.add(sb.toString()); 
		
		//维护收票登记单
		sb = new StringBuffer();
		sb.append("update "+tablename+" set ");
		sb.append(" billtype = '收票登记',");
		sb.append(" vbillno = (select vbillno from fbm_register where fbm_register.pk_register="+tablename+".pk_bill),");
		sb.append(" register_paybillunit = (select custname from fbm_register join bd_cubasdoc on fbm_register.paybillunit=bd_cubasdoc.pk_cubasdoc where fbm_register.pk_register="+tablename+".pk_bill),");
		sb.append(" moneyy = (select moneyy from fbm_register where fbm_register.pk_register="+tablename+".pk_bill),");
		sb.append(" remark = (select note from fbm_register where fbm_register.pk_register="+tablename+".pk_bill)");
		//sb.append(" (billtype,vbillno,register_paybillunit,moneyy) = ");
		//sb.append(" (select '收票登记', vbillno,custname,moneyy from bd_cubasdoc join fbm_register on fbm_register.paybillunit=bd_cubasdoc.pk_cubasdoc and fbm_register.pk_register=fbm_action_temp.pk_bill  ) ");
		sb.append(" where billtype = '36GA'");
		list.add(sb.toString());
//		
//
		//处理银行字段，银行托管和领用用keepunit,内部领用和托管用pk_bankdoc
		sb = new StringBuffer();
		sb.append("update " + tablename + " set ");
		sb.append(" storage_keepbank = isnull((select bankdocname from fbm_storage left join bd_bankdoc on fbm_storage.keepunit =bd_bankdoc.pk_bankdoc where fbm_storage.pk_storage="+tablename+".pk_bill),storage_keepbank)");
		sb.append(" where billtype in('36GB','36GC')");
		list.add(sb.toString());

		sb = new StringBuffer();
		sb.append("update " + tablename + " set ");
		sb.append(" storage_keepbank = isnull((select custname from fbm_storage left join bd_cubasdoc on fbm_storage.keepunit =bd_cubasdoc.pk_cubasdoc where fbm_storage.pk_storage="+tablename+".pk_bill),storage_keepbank)");
		sb.append(" where billtype in('36GB','36GC')");
		list.add(sb.toString());
		
		sb = new StringBuffer();
		sb.append("update " + tablename + " set ");
		sb.append(" storage_keepbank = isnull( (select bankdocname from fbm_storage left join bd_bankdoc on fbm_storage.pk_bankdoc =bd_bankdoc.pk_bankdoc where fbm_storage.pk_storage="+tablename+".pk_bill),storage_keepbank)");
		sb.append(" where billtype in('36GD','36GE')");
		list.add(sb.toString());
		
		//维护银行托管 托管单位
		sb = new StringBuffer();
		sb.append("update " + tablename + " set ");
		sb.append(" storage_inputunit=(select unitname from bd_corp where bd_corp.pk_corp="+tablename+".pk_corp)");
		sb.append(" where billtype in('36GB')");
		list.add(sb.toString());
		//维护银行领用 领用单位
		sb = new StringBuffer();
		sb.append("update " + tablename + " set ");
		sb.append(" storage_outputunit=(select unitname from bd_corp where bd_corp.pk_corp="+tablename+".pk_corp)");
		sb.append(" where billtype in('36GC')");
		list.add(sb.toString());
		//维护内部托管 托管单位
		sb = new StringBuffer();
		sb.append("update " + tablename + " set ");
		sb.append(" storage_inputunit=(select unitname from fbm_storage join bd_corp on bd_corp.pk_corp=fbm_storage.keepcorp  where fbm_storage.pk_storage="+tablename+".pk_bill)");
		sb.append(" where billtype in('36GD')");
		list.add(sb.toString());
		//维护内部领用 领用单位
		sb = new StringBuffer();
		sb.append("update " + tablename + " set ");
		sb.append(" storage_outputunit=(select unitname from fbm_storage join bd_corp on bd_corp.pk_corp=fbm_storage.keepcorp  where fbm_storage.pk_storage="+tablename+".pk_bill)");
		sb.append(" where billtype in('36GE')");
		list.add(sb.toString());
//		//维护银行托管、内部托管
		sb = new StringBuffer();
		sb.append("update "+tablename+" set ");
		sb.append(" vbillno = (select vbillno from fbm_storage where fbm_storage.pk_storage = "+tablename+".pk_bill),");
		sb.append(" remark = (select memo from fbm_storage where fbm_storage.pk_storage = "+tablename+".pk_bill),");
		sb.append(" billtype = (select case billtype when '36GB' then '银行托管' else '内部托管' end from fbm_storage where fbm_storage.pk_storage="+tablename+".pk_bill),");
		//sb.append(" storage_keepbank = (select bankdocname from fbm_storage left join bd_bankdoc on fbm_storage.pk_bankdoc =bd_bankdoc.pk_bankdoc where fbm_storage.pk_storage="+tablename+".pk_bill),");
		//sb.append(" storage_inputunit = (select custname from  fbm_storage left join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc=fbm_storage.keepunit where fbm_storage.pk_storage="+tablename+".pk_bill),");
		sb.append(" moneyy = (select sum(fbm_baseinfo.moneyy) from fbm_baseinfo inner join fbm_storage_b on fbm_baseinfo.pk_baseinfo=fbm_storage_b.pk_baseinfo where fbm_storage_b.pk_storage = "+tablename+".pk_bill)");

		//		sb.append(" update fbm_action_temp set (vbillno,billtype,storage_keepbank,storage_inputunit)=");
//		sb.append(" (select vbillno, case billtype when '36GB' then '银行托管' else '内部托管' end,bankdocname ,custname from fbm_storage left join bd_bankdoc on fbm_storage.pk_bankdoc =bd_bankdoc.pk_bankdoc left join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc=fbm_storage.keepunit where  fbm_storage.pk_storage = fbm_action_temp.pk_bill)");
		sb.append(" where billtype in('36GB','36GD')");
		list.add(sb.toString());
////		
////		//维护银行领用、内部领用
		sb = new StringBuffer();
		sb.append("update "+tablename+" set ");
		sb.append(" vbillno = (select vbillno from fbm_storage where fbm_storage.pk_storage = "+tablename+".pk_bill),");
		sb.append(" remark = (select memo from fbm_storage where fbm_storage.pk_storage = "+tablename+".pk_bill),");
		sb.append(" billtype = (select case billtype when '36GC' then '银行领用' else '内部领用' end from fbm_storage where fbm_storage.pk_storage="+tablename+".pk_bill),");		
		//sb.append(" storage_keepbank = (select bankdocname from fbm_storage left join bd_bankdoc on fbm_storage.pk_bankdoc =bd_bankdoc.pk_bankdoc where fbm_storage.pk_storage="+tablename+".pk_bill),");
		//sb.append(" storage_outputunit = (select custname from  fbm_storage left join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc=fbm_storage.outputunit where fbm_storage.pk_storage="+tablename+".pk_bill),");
		sb.append(" moneyy = (select sum(fbm_baseinfo.moneyy) from fbm_baseinfo inner join fbm_storage_b on fbm_baseinfo.pk_baseinfo=fbm_storage_b.pk_baseinfo where fbm_storage_b.pk_storage = "+tablename+".pk_bill)");


		//		sb.append(" update fbm_action_temp set (vbillno,billtype,storage_keepbank,storage_outputunit)=");
//		sb.append(" (select vbillno, case billtype when '36GC' then '银行领用' else '内部领用' end,bankdocname ,custname from fbm_storage left join bd_bankdoc on fbm_storage.pk_bankdoc =bd_bankdoc.pk_bankdoc left join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc=fbm_storage.outputunit where  fbm_storage.pk_storage = fbm_action_temp.pk_bill)");
		sb.append(" where billtype in('36GC','36GE')");
		list.add(sb.toString());
////		
////		
		
////		//托收
		sb = new StringBuffer();
		sb.append("update "+tablename+" set ");
		sb.append(" vbillno = (select vbillno from fbm_collection where fbm_collection.pk_collection="+tablename+".pk_bill),");
		sb.append(" billtype ='银行托收',");
		sb.append(" remark = (select note from fbm_collection where fbm_collection.pk_collection="+tablename+".pk_bill),");
		sb.append(" collection_bankacc = (select accountname from fbm_collection left join bd_bankaccbas on fbm_collection.holderacc=bd_bankaccbas.pk_bankaccbas where fbm_collection.pk_collection="+tablename+".pk_bill),");
		sb.append(" collection_bank =  (select bankdocname from fbm_collection left join bd_bankaccbas on fbm_collection.holderacc=bd_bankaccbas.pk_bankaccbas left join bd_bankdoc on bd_bankdoc.pk_bankdoc = bd_bankaccbas.pk_bankdoc  where fbm_collection.pk_collection="+tablename+".pk_bill),");
		sb.append(" moneyy = ( select moneyy from fbm_collection where fbm_collection.pk_collection="+tablename+".pk_bill)");
//		sb.append(" update fbm_action_temp set (vbillno,billtype,collection_bankacc,collection_bank,moneyy) =");
//		sb.append(" (select vbillno,'银行托收',accountname,bankdocname,moneyy from fbm_collection left join bd_bankaccbas on fbm_collection.holderacc=bd_bankaccbas.pk_bankaccbas left  join bd_bankdoc on bd_bankaccbas.pk_bankdoc = bd_bankdoc.pk_bankdoc where fbm_collection.pk_collection = fbm_action_temp.pk_bill)");
		sb.append(" where billtype ='36GI'");
		list.add(sb.toString());
////		
		//贴现申请
		sb = new StringBuffer();
		sb.append(" update " + tablename + " set ");
		sb.append(" vbillno = (select vbillno from fbm_discount where fbm_discount.pk_discount="+tablename+".pk_bill),");
		sb.append(" remark = (select note from fbm_discount where fbm_discount.pk_discount="+tablename+".pk_bill),");
		sb.append(" billtype = '贴现申请',");
		sb.append(" discount_bankacc = (select bd_bankaccbas.accountname from fbm_discount left join bd_bankaccbas on fbm_discount.discount_account=bd_bankaccbas.pk_bankaccbas where fbm_discount.pk_discount="+tablename+".pk_bill),");
		sb.append(" discount_bank = (select bd_bankdoc.bankdocname from fbm_discount left join bd_bankdoc on pk_discount_bank=bd_bankdoc.pk_bankdoc where fbm_discount.pk_discount="+tablename+".pk_bill),");
		sb.append(" discount_rate = (select discountyrate from fbm_discount where fbm_discount.pk_discount="+tablename+".pk_bill),");
		sb.append(" discount_lx = (select discountinterest from fbm_discount where fbm_discount.pk_discount="+tablename+".pk_bill),");
		sb.append(" moneyy =  (select moneyy from fbm_discount where fbm_discount.pk_discount="+tablename+".pk_bill)");
//		sb.append(" update fbm_action_temp set (vbillno,billtype, discount_bankacc,discount_bank,discount_rate,discount_lx,moneyy )=");
//		sb.append(" (select vbillno,'票据贴现',bd_bankaccbas.accountname,bd_bankdoc.bankdocname ,discountyrate,discountinterest,moneyy from fbm_discount left join bd_bankaccbas on fbm_discount.discount_account=bd_bankaccbas.pk_bankaccbas left join bd_bankdoc on pk_discount_bank=bd_bankdoc.pk_bankdoc where pk_discount=pk_bill) ");
		sb.append(" where billtype ='36GF'");
		list.add(sb.toString());
		
		//贴现办理
		sb = new StringBuffer();
		sb.append(" update " + tablename + " set ");
		sb.append(" vbillno = (select vbillno from fbm_discount where fbm_discount.pk_discount="+tablename+".pk_bill),");
		sb.append(" remark = (select note from fbm_discount where fbm_discount.pk_discount="+tablename+".pk_bill),");
		sb.append(" billtype = '贴现办理',");
		sb.append(" discount_bankacc = (select bd_bankaccbas.accountname from fbm_discount left join bd_bankaccbas on fbm_discount.discount_account=bd_bankaccbas.pk_bankaccbas where fbm_discount.pk_discount="+tablename+".pk_bill),");
		sb.append(" discount_bank = (select bd_bankdoc.bankdocname from fbm_discount left join bd_bankdoc on pk_discount_bank=bd_bankdoc.pk_bankdoc where fbm_discount.pk_discount="+tablename+".pk_bill),");
		sb.append(" discount_rate = (select discountyrate from fbm_discount where fbm_discount.pk_discount="+tablename+".pk_bill),");
		sb.append(" discount_lx = (select discountinterest from fbm_discount where fbm_discount.pk_discount="+tablename+".pk_bill),");
		sb.append(" moneyy =  (select moneyy from fbm_discount where fbm_discount.pk_discount="+tablename+".pk_bill)");
//		sb.append(" update fbm_action_temp set (vbillno,billtype, discount_bankacc,discount_bank,discount_rate,discount_lx,moneyy )=");
//		sb.append(" (select vbillno,'票据贴现',bd_bankaccbas.accountname,bd_bankdoc.bankdocname ,discountyrate,discountinterest,moneyy from fbm_discount left join bd_bankaccbas on fbm_discount.discount_account=bd_bankaccbas.pk_bankaccbas left join bd_bankdoc on pk_discount_bank=bd_bankdoc.pk_bankdoc where pk_discount=pk_bill) ");
		sb.append(" where billtype  ='36GG'");
		list.add(sb.toString());
		
////		
////		//背书
		sb = new StringBuffer();
		sb.append(" update " + tablename + " set ");
		sb.append(" vbillno = (select vbillno from fbm_endore where fbm_endore.pk_endore ="+tablename+".pk_bill),");
		sb.append(" remark = (select note from fbm_endore where fbm_endore.pk_endore ="+tablename+".pk_bill),");
		sb.append(" billtype = '背书办理',");
		sb.append("endore_endorser = (select custname from fbm_endore left join bd_cubasdoc on fbm_endore.endorser=pk_cubasdoc  where fbm_endore.pk_endore ="+tablename+".pk_bill),");
		sb.append("endore_endorsee= (select custname from fbm_endore left join bd_cubasdoc on fbm_endore.endorsee=pk_cubasdoc  where fbm_endore.pk_endore ="+tablename+".pk_bill),");
		sb.append("moneyy =  (select moneyy from fbm_endore where fbm_endore.pk_endore ="+tablename+".pk_bill)");
//		sb.append(" update fbm_action_temp set (vbillno,billtype ,endore_endorser,endore_endorsee,moneyy)=");
//		sb.append("(select vbillno,'背书办理',a.custname,b.custname,moneyy from fbm_endore left join bd_cubasdoc a on fbm_endore.endorser=a.pk_cubasdoc left join bd_cubasdoc b on fbm_endore.endorsee=b.pk_cubasdoc where pk_endore = pk_bill)");
		sb.append(" where billtype ='36GQ'");
		list.add(sb.toString());
////		
////		//质押
		sb = new StringBuffer();
		sb.append(" update " + tablename + " set ");
		sb.append(" vbillno = (select vbillno from fbm_impawn where pk_impawn = "+tablename+".pk_bill),");
		sb.append(" billtype='票据质押',");
		sb.append(" impawn_creditunit=(select creditunit from fbm_impawn  where pk_impawn = "+tablename+".pk_bill),");
		sb.append(" impawn_debitunit=(select custname from fbm_impawn join bd_cubasdoc on debitunit=pk_cubasdoc where pk_impawn = "+tablename+".pk_bill),");
		sb.append(" moneyy =  (select moneyy from fbm_impawn where  pk_impawn ="+tablename+".pk_bill)");
//		sb.append("update fbm_action_temp set(vbillno,billtype,impawn_creditunit,impawn_debitunit,moneyy)=");
//		sb.append("(select vbillno,'票据质押',a.custname,b.custname,moneyy from fbm_impawn left join bd_cubasdoc a on fbm_impawn.creditunit = a.pk_cubasdoc left join bd_cubasdoc b on fbm_impawn.debitunit=b.pk_cubasdoc where pk_impawn=pk_bill )");
		sb.append("where billtype = '36GO'");
		list.add(sb.toString());
//////		
//////		//调剂
		sb = new StringBuffer();
		sb.append(" update " + tablename + " set ");
		sb.append(" vbillno = (select vbillno from fbm_relief where pk_relief ="+tablename+".pk_bill),");
		sb.append(" remark = (select memo from fbm_relief where pk_relief ="+tablename+".pk_bill),");
		sb.append(" billtype='票据调剂',");
		sb.append(" relief_reliefunit = (select custname from fbm_relief join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc = fbm_relief.reliefunit where pk_relief ="+tablename+".pk_bill),");
		sb.append(" moneyy = (select sum(fbm_baseinfo.moneyy) from fbm_baseinfo inner join fbm_relief_b on fbm_baseinfo.pk_baseinfo=fbm_relief_b.pk_baseinfo where fbm_relief_b.pk_relief = "+tablename+".pk_bill)");


		//		sb.append(" update fbm_action_temp set(vbillno,billtype,relief_reliefunit)=");
//		sb.append("(select vbillno,'票据调剂',custname from fbm_relief join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc = fbm_relief.reliefunit where pk_relief = pk_bill)");
		sb.append("where billtype='36GR'");
		list.add(sb.toString());
//////		
//////		//票据付款
		sb = new StringBuffer();
		sb.append(" update " + tablename + " set ");
		sb.append(" billtype='票据付款',");
		sb.append(" vbillno = (select vbillno from fbm_accept where pk_accept ="+tablename+".pk_bill),");
		sb.append(" remark = (select note from fbm_accept where pk_accept ="+tablename+".pk_bill),");
		sb.append(" accept_holdunit = (select custname from fbm_accept left join bd_cubasdoc on fbm_accept.holdunit = pk_cubasdoc where pk_accept ="+tablename+".pk_bill),");
		sb.append(" accept_holderacc = (select accountname from fbm_accept left join bd_bankaccbas on pk_bankaccbas=holderacc where pk_accept ="+tablename+".pk_bill),");
		sb.append(" accept_backsecacc = (select accountname from fbm_accept left join bd_bankaccbas on backsecaccount=pk_bankaccbas where pk_accept ="+tablename+".pk_bill),");
		sb.append(" accept_banksecmoneyy=(select moneyy from fbm_accept where pk_accept ="+tablename+".pk_bill),");
		sb.append(" moneyy=(select moneyy from fbm_accept where pk_accept ="+tablename+".pk_bill)");
//		sb.append(" update fbm_action_temp set(vbillno,billtype,accept_holdunit,accept_holderacc,accept_backsecacc,accept_banksecmoneyy,moneyy)=");
//		sb.append("(select vbillno,'票据付款',custname,a.accountname,b.accountname,moneyy,moneyy from fbm_accept left join bd_bankaccbas a on a.pk_bankaccbas=holderacc left join bd_bankaccbas b on b.pk_bankaccbas=backsecaccount left join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc=holderacc where pk_accept = pk_bill)");
		sb.append(" where billtype='36GM'");
		list.add(sb.toString());
//////		
//////		//维护付票登记单
		sb = new StringBuffer();
		sb.append(" update " + tablename + " set ");
		sb.append(" billtype='付票登记',");
		sb.append(" vbillno = (select vbillno from fbm_register where pk_register ="+tablename+".pk_bill),");
		sb.append(" remark = (select note from fbm_register where pk_register ="+tablename+".pk_bill),");
		sb.append(" register_cctype = (select case cctype when 'NONE' then '不受授信控制' when 'CORP' then '企业授信' when 'GROUP' then '集团授信' else '' end from fbm_register where pk_register ="+tablename+".pk_bill),");
		sb.append(" register_securityacc = (select accountname from fbm_register left join bd_bankaccbas on securityaccount=pk_bankaccbas where pk_register ="+tablename+".pk_bill),");
		sb.append(" register_securityrate = (select securityrate from fbm_register where pk_register ="+tablename+".pk_bill),");
		sb.append(" register_mode = (select impawnmode from fbm_register where pk_register ="+tablename+".pk_bill),");
		sb.append(" moneyy =(select moneyy from fbm_register where pk_register ="+tablename+".pk_bill)");
//		sb.append("update fbm_action_temp set (billtype,vbillno,register_cctype,register_securityacc,register_securityrate,register_securitymoneyy,register_mode,moneyy)= ");
//		sb.append(" (select '付票登记',vbillno,cctype,accountname,securityrate,securitymoney,impawnmode,moneyy from fbm_register left join bd_bankaccbas on fbm_register.securityaccount=bd_bankaccbas.pk_bankaccbas where fbm_register.pk_register=fbm_action_temp.pk_bill ) ");
		sb.append(" where billtype = '36GL'");
		list.add(sb.toString());
////		
////		//退票单
		sb = new StringBuffer();
		sb.append(" update " + tablename + " set ");
		sb.append(" billtype = '集中退票',");
		sb.append(" vbillno =(select vbillno from fbm_return where pk_return="+tablename+".pk_bill),");
		sb.append(" remark = (select returnnote from fbm_return where pk_return="+tablename+".pk_bill),");
		sb.append(" return_person = (select user_name from fbm_return  left join sm_user on cuserid=returnperson where pk_return="+tablename+".pk_bill),");
		sb.append(" return_date = (select dreturndate from fbm_return where pk_return="+tablename+".pk_bill),");
		sb.append(" return_type=(select case returntype when 'endore' then '背书退票' when 'invoice' then '付票退票' when 'gather' then '收票退票' when 'disable' then '废票退票' when 'unistorage' then '中心退出' when 'unit' then '中心退入' else  '' end from fbm_return where pk_return="+tablename+".pk_bill),");
		sb.append(" moneyy = (select sum(fbm_baseinfo.moneyy) from fbm_baseinfo inner join fbm_return_b on fbm_baseinfo.pk_baseinfo=fbm_return_b.pk_baseinfo where fbm_return_b.pk_return = "+tablename+".pk_bill)");
		//		sb.append("update fbm_action_temp set(billtype,vbillno,return_person,return_date,return_type)=");
//		sb.append("(select '集中退票',vbillno,user_name,dreturndate,case returntype when 'endore' then '背书退票' when 'invoice' then '付票退票' when 'gather' then '收票退票' when 'disable' then '废票退票' when 'unistorage' then '中心退出' when 'unit' then '中心退入' else  '' end from fbm_return left join sm_user on cuserid=returnperson where pk_bill=pk_return)");
		sb.append(" where billtype='36GN'");
		list.add(sb.toString());
//		
		new CommonDAO().executeUpdate((String[])list.toArray(new String[0]));

	}
}
