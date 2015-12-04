package nc.bs.fbm.report;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.fbm.report.BcbHeadVO;
import nc.vo.fbm.report.ReportParam;
import nc.vo.pub.BusinessException;

import com.borland.dx.dataset.StorageDataSet;


/**
 * 查询票据基本档案PK
 * @author xwq
 *
 */
public class BcbHeadBO extends FbmBaseReportBO{

	private BaseDAO dao = new BaseDAO();
	
	public StorageDataSet queryData(ReportParam reportParam) throws BusinessException{
		ReportTempTable r = new ReportTempTable();
		String tablename = r.createTempTableByClass(BcbHeadVO.class);
		
		//将查询结果插入临时表
		StringBuffer sb  = new StringBuffer();
		sb.append("INSERT INTO "+tablename+" ( ACCEPTANCENO, CONTRACTNO, DEF1, DEF2, DEF3, DEF4, DEF5, DISABLE, ENDDATE, FBMBILLNO, FBMBILLTYPE, INVOICEDATE, INVOICEUNIT, INVOICEUNITNAME, KEEPUNIT, MONEYY,ORIENTATION, PAYBANK, PAYBANKACC, PAYUNIT, PK_BASEINFO, PK_CURR, RECEIVEACCNAME, RECEIVEBANK,RECEIVEBANKACC, RECEIVEUNIT, RECEIVEUNITNAME,fbmbillstatus,currdigit ) ");
		sb.append(orgQuerySql(reportParam));
		dao.executeUpdate(sb.toString());
		
		updatePK2Name(tablename);
		String sql = "select fbmbillno,orientation,fbmbillstatus,fbmbilltype,pk_curr,moneyy,enddate,invoicedate,invoiceunit,payunit,paybankacc,paybank,receiveunit,receivebankacc,receivebank,disable,keepunit,acceptanceno,contractno,pk_baseinfo ,currdigit from "+tablename+" order by fbmbillno";
		
		List<BcbHeadVO> list = (List<BcbHeadVO>)dao.executeQuery(sql, new BeanListProcessor(BcbHeadVO.class));
		BcbHeadVO[] results = null;
		if(list !=null && list.size() > 0){
			results  = list.toArray(new BcbHeadVO[0]);
		}
		
		return getDataSetBySuperVos(results, 2 ,BcbHeadVO.class);
	}
	
	/**
	 * 根据查询条件获得票据基本信息查询结果
	 * @return
	 */
	private String orgQuerySql(ReportParam reportParam){
		String sql = "select ACCEPTANCENO, CONTRACTNO, DEF1, DEF2, DEF3, DEF4, DEF5, DISABLE, ENDDATE, FBMBILLNO, FBMBILLTYPE, INVOICEDATE, INVOICEUNIT, INVOICEUNITNAME, KEEPUNIT, fbm_baseinfo.MONEYY,ORIENTATION, PAYBANK, PAYBANKACC, PAYUNIT, fbm_baseinfo.PK_BASEINFO, PK_CURR, RECEIVEACCNAME, RECEIVEBANK,RECEIVEBANKACC, RECEIVEUNIT, RECEIVEUNITNAME,fbm_action.endstatus,currdigit from fbm_baseinfo left join fbm_action on fbm_baseinfo.pk_baseinfo=fbm_action.pk_baseinfo left join bd_currtype on pk_curr = bd_currtype.pk_currtype where serialnum = (select max(serialnum) from fbm_action b where b.pk_baseinfo=fbm_baseinfo.pk_baseinfo)";
		return sql + " and " + makeQueryConditionSql(reportParam);
	}
	
	/**
	 * 后期要修改成批量的形式
	 * @throws BusinessException
	 */
	private void updatePK2Name(String tablename)throws BusinessException{
		List<String> list = new LinkedList<String>();
		
		//更新票据状态字段 
		Map<String,String> valuemap = getStatusEnglishKeyMap();
		list.addAll(ReportSqlBuilder.buildByConstant(tablename, "fbmbillstatus", valuemap));
		
		
		//更新票据类型字段PK -> 名称
		String sql = "update "+tablename+" set fbmbilltype = (select notetypename from bd_notetype where pk_notetype=fbmbilltype)";
		list.add(sql);
		//更新币种
		sql = "update  "+tablename+" set pk_curr = (select currtypename from bd_currtype where pk_currtype=pk_curr)";
		list.add(sql);
		//更新付款单位
		sql = "update  "+tablename+" set payunit = (select custname from v_fbm_cubasdoc where pk_cubasdoc = payunit)";
		list.add(sql);
		//更新付款单位账号
		sql = "update  "+tablename+" set paybankacc = (select account from v_fbm_bankaccbas where pk_bankaccbas = paybankacc)";
		list.add(sql);
		//更新付款单位银行
		sql = "update  "+tablename+" set paybank = (select bankdocname from v_fbm_bankdoc where pk_bankdoc = paybank)  ";
		list.add(sql);
		
		//更新收款单位
		sql = "update  "+tablename+" set receiveunit = (select custname from v_fbm_cubasdoc where pk_cubasdoc = receiveunit)";
		list.add(sql);
		//更新收款单位账号
		sql = "update  "+tablename+" set receivebankacc = (select account from v_fbm_bankaccbas where pk_bankaccbas = receivebankacc)";
		list.add(sql);
		//更新收款单位银行
		sql = "update  "+tablename+" set receivebank = (select bankdocname from v_fbm_bankdoc where pk_bankdoc = receivebank)  ";
		list.add(sql);
		
		//更新票据方向
		sql ="update  "+tablename+" set orientation='应付票据' where orientation='ap'";
		list.add(sql);
		sql ="update  "+tablename+" set orientation='应收票据' where orientation='ar'";
		list.add(sql);
		
		//更新出票单位
		sql = "update  "+tablename+" set invoiceunit = (select custname from bd_cubasdoc where pk_cubasdoc = invoiceunit)";
		list.add(sql);
		
		
		//更新存放地点
		sql = "update  "+tablename+" set keepunit = isnull((select custname from bd_cubasdoc where pk_cubasdoc = keepunit),keepunit)";
		list.add(sql);
		sql = "update  "+tablename+" set keepunit = isnull((select bankdocname from bd_bankdoc where pk_bankdoc = keepunit),keepunit)";
		list.add(sql);
		new CommonDAO().executeUpdate((String[])list.toArray(new String[0]));
		
		
		
	}
}
