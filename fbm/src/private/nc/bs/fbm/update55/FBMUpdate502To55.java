package nc.bs.fbm.update55;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.sm.accountmanage.AbstractUpdateAccount;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.pub.ICDMDAO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.dap.pub.IDapService;
import nc.itf.uap.bd.corp.ICorpQry;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.bd.BDGLOrgBookAccessor;
import nc.vo.bd.b54.GlorgbookVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.FbmControlRuleVO;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.para.SysInitVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * 资金票据从5.02、5.3、5.5升级到5.6
 * 
 * @author xwq
 *
 * 2008-10-7
 */
public class FBMUpdate502To55 extends AbstractUpdateAccount{

	public void doAfterUpdateData(String oldVersion, String newVersion)
			throws Exception {
		Logger.error("oldVersion:" + oldVersion + "  newVersion:" + newVersion);
		
		if((oldVersion.startsWith("5.02")||oldVersion.startsWith("5.3")||oldVersion.startsWith("5.5")) && newVersion.startsWith("5.6")){
			resetEndStatus();
			resetStorageBaseInfo();
		}
		
		if ((oldVersion.startsWith("5.02")||oldVersion.startsWith("5.3")) && newVersion.startsWith("5.6")) {
			fillKeepCorp4Storage();
			fillReliefCorp4Relief();
			fillReckonAlarm();
			updateEndoreSyscode(oldVersion);
			fillBankdoc4Storage();
			delFunregister();
			//2009-9-25 xwq  已存在的单据类型有可能已经有凭证，因此不能删除，否则会导致错误
//			delBillType("36GZ");
//			delBillType("36GX");
			fillSumit4Storage();
			upgradeInputData(oldVersion);
			updateFbmOuter() ;
			updateBaseInfoBankDoc();
			updateVoucherFlag();
			updateTallyFlag();
			fillCurr();
			updateFunDispcode();
			dealReckon();
			updateFbmBilltype();
			delUnUsedBusitrans();
			delBillactionconfig();
			
			fillDealDate();
			updateCumandocIounit();
			updateArapNoteType();
			
			deleteBusinessData();
			deleteIntefaceexec();
			deleteOldFbmInfo();
			upgradeNewBilltype();
			delOldVouchtemp();
			insertSysinit(FBMParamConstant.FBM005,"票据业务是否与应收、应付、现金平台、资金计划集成应用","0001ZZ1000000WYG25XN","Y");
			//更新FBM005参数的名称
			String updateSysinit = "update pub_sysinit set initname = '票据业务是否与应收、应付、现金平台、资金计划集成应用' where initcode = 'FBM005'";
			BaseDAO dao = new BaseDAO();
			dao.executeUpdate(updateSysinit);
			
			insertSysinit(FBMParamConstant.FBM006,"背书业务是否生成已收票的收票登记单","0001ZZ10000000FBM006","Y");
			fillOpBillType();
			resetDiscountActDate(oldVersion);
			fillEndoreBillno(oldVersion);
			rebuildOuter(oldVersion);
			delDefaultSubjClass(oldVersion);
			
			
			deleteOldDapVoucher() ;
			IDapService dapSrv = NCLocator.getInstance().lookup(IDapService.class);
			dapSrv.initCorps(null, "FBM");
			changeVouchertype();
			
			dealBankaccbasUpgrade();
			fillSummoney();
			
			updateUserBillTemplet();
			
			//xwq 20090930 更新领用单的领用方式字段值
			updateInnerBackInputType();
		}
		
	}
	
	private void updateUserBillTemplet() throws Exception {
		BaseDAO dao = new BaseDAO();
		String[] billtype = querySingleColbyWhere(dao,"pk_billtypecode", "pub_billtemplet", "bill_templetname='SYSTEM' and nodecode like '3620%' or pk_corp='@@@@' and pk_billtypecode='FI019'");
		if(CommonUtil.isNull(billtype)){
			return;
		}
		ICDMDAO comdao = (ICDMDAO) NCLocator.getInstance().lookup(ICDMDAO.class.getName());
		for (int i = 0; i < billtype.length; i++) {
			String typecode = billtype[i];
			Logger.error("开始调试：用户模板单据类型" + i + ":|" + billtype[i] + "|");
			String[] userTemplet = querySingleColbyWhere(dao,"pk_billtemplet", "pub_billtemplet", " pk_billtypecode = '" + typecode + "' and bill_templetname <> 'SYSTEM'");
			for (int j = 0; j < userTemplet.length; j++) {
				Logger.error("用户模板PK" + j + ":|" + userTemplet[j] + "|");
				String[] sysTempletPKs = querySingleColbyWhere(dao,"pk_billtemplet", "pub_billtemplet", " pk_billtypecode = '" + typecode + "' and bill_templetname = 'SYSTEM'");
				if (CommonUtil.getArrayLength(sysTempletPKs) == 0 || CommonUtil.isNull(sysTempletPKs[0])) {
					continue;
				}
				String syspk = sysTempletPKs[0];
				Logger.error("用户模板对应的系统模板PK:|" + syspk + "|");
				// 复制不存在的键值到用户模板中。
				String pk_user = userTemplet[j];
				String where = " pk_billtemplet = '" + syspk + "'";
				where += " and not exists (select 1 from pub_billtemplet_b t where pk_billtemplet = '" + pk_user + "' and t.itemkey = pub_billtemplet_b.itemkey and t.table_code = pub_billtemplet_b.table_code and t.pos = pub_billtemplet_b.pos)";
				String[] deal_PK = querySingleColbyWhere(dao,"pk_billtemplet_b", "pub_billtemplet_b", where);
				comdao.copyTableRow("pub_billtemplet_b", "pk_billtemplet_b", "pk_billtemplet", new String[] { pk_user }, deal_PK);
				
				// 删除多余的键值。
				String delsql = "delete from pub_billtemplet_b where pk_billtemplet = '" + pk_user + "'";
				delsql += " and not exists (select 1 from pub_billtemplet_b t where pk_billtemplet = '" + syspk + "' and t.itemkey = pub_billtemplet_b.itemkey and t.table_code = pub_billtemplet_b.table_code and t.pos = pub_billtemplet_b.pos)";
				dao.executeUpdate(delsql);
				// 更新用户模板项目与系统模板项目的数据类型和类型默认值得信息.
				// 获取数据类型变更的项目
				where = " pk_billtemplet = '" + pk_user + "' and exists (select 1 from pub_billtemplet_b t where t.pk_billtemplet = '" + syspk
						+ "' and t.itemkey = pub_billtemplet_b.itemkey and t.table_code = pub_billtemplet_b.table_code and t.pos = pub_billtemplet_b.pos " + "and t.datatype <> pub_billtemplet_b.datatype)" + " and itemkey not like 'def%'";
				deal_PK = querySingleColbyWhere(dao,"pk_billtemplet_b", "pub_billtemplet_b", where);
				
				// 对数据类型变更的项目进行处理
				String subsql1 = " update pub_billtemplet_b set datatype = (select datatype from pub_billtemplet_b t where t.pk_billtemplet = ";
				String subsql2 = " and t.itemkey = pub_billtemplet_b.itemkey and t.table_code = pub_billtemplet_b.table_code and t.pos = pub_billtemplet_b.pos)" + "where pk_billtemplet_b =";
				String subsql = null;
				for (int k = 0; k < deal_PK.length; k++) {
					String pk_bt_b = deal_PK[k];
					subsql = subsql1+" '"+syspk+"'"+subsql2+"'"+pk_bt_b+"' ";
					dao.executeUpdate(subsql);
				}
				
				// 获取参照类型变更的项目
				where = " pk_billtemplet = '" + pk_user + "' and exists (select 1 from pub_billtemplet_b t where t.pk_billtemplet = '" + syspk
						+ "' and t.itemkey = pub_billtemplet_b.itemkey and t.table_code = pub_billtemplet_b.table_code and t.pos = pub_billtemplet_b.pos " + "and (t.reftype <> pub_billtemplet_b.reftype "
						+ "or (t.reftype is null and pub_billtemplet_b.reftype is not null)" + "or (t.reftype is not null and pub_billtemplet_b.reftype is null)" + "))" + " and itemkey not like 'def%'";
				deal_PK = querySingleColbyWhere(dao,"pk_billtemplet_b", "pub_billtemplet_b", where);
				
				// 对参照类型变更的项目进行处理
				subsql1 = " update pub_billtemplet_b set reftype = (select reftype from pub_billtemplet_b t where t.pk_billtemplet = ";
				subsql2 = " and t.itemkey = pub_billtemplet_b.itemkey and t.table_code = pub_billtemplet_b.table_code and t.pos = pub_billtemplet_b.pos)" + "where pk_billtemplet_b = ";
				for (int k = 0; k < deal_PK.length; k++) {
					String pk_bt_b = deal_PK[k];
					subsql =  subsql1+" '"+syspk+"'"+subsql2+"'"+pk_bt_b+"' ";
					dao.executeUpdate(subsql);
				}
				delsql = "";
				// 重新复制页签信息到用户模板
				// 删除旧数据
				delsql = "delete from pub_billtemplet_t where pk_billtemplet = '" + pk_user + "'";
				dao.executeUpdate(delsql);
				// 插入新数据
				deal_PK = querySingleColbyWhere(dao,"pk_billtemplet_t", "pub_billtemplet_t", " pk_billtemplet = '" + syspk + "'");
				comdao.copyTableRow("pub_billtemplet_t", "pk_billtemplet_t", "pk_billtemplet", new String[] { pk_user }, deal_PK);
			}
		}
	}
	
	private String[] querySingleColbyWhere(BaseDAO dao,String selColname,String tablename,String where) throws DAOException{
		String sql = " select "+selColname+" from "+tablename+" where "+where;
		ArrayList list = (ArrayList) dao.executeQuery(sql, new MapListProcessor());
		ArrayList<String> newlist = new ArrayList<String>();
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				if(map!=null&&map.get(selColname)!=null){
					String billtype = (String) map.get(selColname);
					if(!CommonUtil.isNull(billtype)){
						newlist.add(billtype);
						Logger.debug("用户模板单据类型：|" + billtype + "|");
					}
				}
			}
		}
		String[] retvalues = new String[0];
		if(newlist.size()>0){
			retvalues = newlist.toArray(new String[0]);
		}
		return retvalues;
	}
	
	/**
	 * 填充fbm_storage表中新增加的keepcorp字段
	 * 对于单据类型为内部托管 36GD的单据，keepcorp取keepunit(客商)对应公司
	 * 对于单据类型为内部领用 36GE的单据，keepcorp取outputunit(客商)对应公司
	 * @throws BusinessException
	 */
	private void fillKeepCorp4Storage()throws BusinessException{
		BaseDAO dao = new BaseDAO();
		
		String updateSQL_1 = "update fbm_storage set keepcorp=(select pk_corp1 from bd_cubasdoc where pk_cubasdoc=keepunit) where pk_billtypecode='36GD'";
		String updateSQL_2 = "update fbm_storage set keepcorp=(select pk_corp1 from bd_cubasdoc where pk_cubasdoc=outputunit) where pk_billtypecode='36GE'";
		
		dao.executeUpdate(updateSQL_1);
		dao.executeUpdate(updateSQL_2);
		
	}

	/**
	 * reliefcorp取reliefunit(客商)对应公司
	 * @throws BusinessException
	 */
	private void fillReliefCorp4Relief() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String updateSQL = "update fbm_relief set reliefcorp=(select pk_corp1 from bd_cubasdoc where pk_cubasdoc=reliefunit) ";
		dao.executeUpdate(updateSQL);
	}
	
	/**
	 * 填充清算设置转入帐户和转出帐户
	 * @throws BusinessException
	 */
	private void fillReckonAlarm() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String updateSQL = "update fbm_reckalarm set inacc=reckonacc,outacc=reckonacc";
		dao.executeUpdate(updateSQL);
	}
	
	/**
	 * 填充背书办理单来源单据字段
	 * @throws BusinessException
	 */
	private void updateEndoreSyscode(String fromVersion) throws BusinessException{
		BaseDAO dao = new BaseDAO();
		
		if(fromVersion.startsWith("5.02")){
			String updateSQL = "update fbm_endore set syscode ='CMP'";
			dao.executeUpdate(updateSQL);
		}else{
			String updateSQL_1 = "update fbm_endore set syscode ='CMP' where syscode='ARAP'";
			String updateSQL_2 = "update fbm_endore set syscode='INPUT' where syscode is null";
			dao.executeUpdate(updateSQL_1);
			dao.executeUpdate(updateSQL_2);
		}
	}
	
	/**
	 * 填充keepcorp对应结算中心对应的银行档案PK
	 * @throws BusinessException
	 */
	private void fillBankdoc4Storage() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String updateSQL = "update fbm_storage set pk_bankdoc = (select pk_bankdoc from bd_settlecenter join bd_settleunit on bd_settlecenter.pk_settlecenter = bd_settleunit.pk_settlecent join bd_bankdoc on bd_settlecenter.pk_settlecenter=bd_bankdoc.pk_settlecenter where bd_settleunit.pk_corp1=keepcorp)";
		dao.executeUpdate(updateSQL);
	}
	
	/**
	 * 删除无用的节点
	 * 票据升级
	 * @throws BusinessException
	 */
	private void delFunregister() throws BusinessException{
		String delSQL1 = "delete from sm_funcregister where cfunid='0001ZZ1000000000I1OY' and fun_code='36200810'";
		String delSQL2 = "delete from sm_butnregister where fun_code like '36200810%'";
		String delSQL3 = "delete from sm_butnregister where cfunid in('1001ZZ100000000097GC','1001ZZ100000000097GD')";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(delSQL1);
		dao.executeUpdate(delSQL2);
		dao.executeUpdate(delSQL3);
	}
	
	/**
	 * 删除无用的单据类型
	 * @throws BusinessException
	 */
//	private void delBillType(String pk_billtypecode) throws BusinessException{
//		BaseDAO dao = new BaseDAO();
//		String delSQL1 = "delete from pub_busiclass where pk_billtype = '"+pk_billtypecode+"'";
//		String delSQL2 = "delete from pub_billactionconfig where pk_billactiongroup in(select pk_billactiongroup from pub_billactiongroup where pk_billtype  = '"+pk_billtypecode+"')";
//		String delSQL3 = "delete from pub_billactiongroup  where pk_billtype = '"+pk_billtypecode+"'";
//		String delSQL4 = "delete from pub_billaction where pk_billtype = '"+pk_billtypecode+"'";
//		String delSQL5 = "delete from pub_votable where pk_billtype = '"+pk_billtypecode+"'";
//		String delSQL6 = "delete from dap_defitem where  pk_billtype = '"+pk_billtypecode+"'";
//		String delSQL7 = "delete from dap_billfactor where pk_billtype = '"+pk_billtypecode+"'";
//		String delSQL8 = "delete from bd_billtype where pk_billtypecode = '"+pk_billtypecode+"'";
//		dao.executeUpdate(delSQL1);
//		dao.executeUpdate(delSQL2);
//		dao.executeUpdate(delSQL3);
//		dao.executeUpdate(delSQL4);
//		dao.executeUpdate(delSQL5);
//		dao.executeUpdate(delSQL6);
//		dao.executeUpdate(delSQL7);
//		dao.executeUpdate(delSQL8);
//	}
	
	/**
	 * 对于pk_corp=keepcorp的记录设置提交人和提交日期为审核人和审核日期
	 * @throws BusinessException
	 */
	private void fillSumit4Storage() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String updateSQL = "update fbm_storage set submitmanid=vapproveid,submitdate = dapprovedate where keepcorp=pk_corp ";
		dao.executeUpdate(updateSQL);
	}
	
	/**
	 * 升级手工录入的银行帐号和客商和银行
	 * @param oldversion
	 * @throws BusinessException
	 */
	private void upgradeInputData(String oldversion) throws BusinessException{
		BaseDAO dao = new BaseDAO();
		if(oldversion.startsWith("5.02")){
			String insertSQL = "insert into fbm_bankaccbas(pk_bankaccbas,pk_corp,account,accountname,pk_currtype) select pk_accbank,'FBMC',bankacc,bankname,pk_currtype from bd_accbank where  pk_corp='FBMC' and pk_accbank not in(select pk_bankaccbas from fbm_bankaccbas)";
			String insertSQL_1  ="insert into fbm_cubasdoc(custcode,custname,custshortname,pk_areacl,pk_corp,pk_cubasdoc,url) select custcode,custname,custshortname,pk_areacl,pk_corp,pk_cubasdoc,url from bd_cubasdoc where pk_corp='FBMC'";
			
			dao.executeUpdate(insertSQL);
			dao.executeUpdate(insertSQL_1);
			
			String bankdoc = "insert into fbm_bankdoc (bankdoccode,bankdocname,pk_bankdoc) select bankname,bankname,pk_accbank from bd_accbank where  pk_corp='FBMC'";
			dao.executeUpdate(bankdoc);
			
			String delSQL = "delete from bd_accbank where  pk_corp='FBMC'";
			dao.executeUpdate(delSQL);
			String delSQL1 = "delete from bd_cubasdoc where  pk_corp='FBMC'";
			dao.executeUpdate(delSQL1);
			
		}else if(oldversion.startsWith("5.3")){
			String insertSQL = "insert into fbm_bankaccbas(pk_bankaccbas,pk_corp,account,accountname,pk_currtype) select pk_accbank,'FBMC',bankacc,bankname,pk_currtype from fbm_accbank ";
			dao.executeUpdate(insertSQL);
			String bankdoc = "insert into fbm_bankdoc (bankdoccode,bankdocname,pk_bankdoc) select bankname,bankname,pk_accbank from fbm_accbank";
			dao.executeUpdate(bankdoc);
		}
		
		String dealpk_bank = "update fbm_baseinfo set paybank = paybankacc,receivebank = receivebankacc";
		
		dao.executeUpdate(dealpk_bank);
	}
	
	/**
	 * 升级外部关联表
	 * @throws BusinessException
	 */
	private void updateFbmOuter() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String sql1 = "update fbm_outer set outerdjdl = 'F2' where outerdjdl = 'sk'";
		String sql2 = "update fbm_outer set outerdjdl = 'F3' where outerdjdl = 'fk'";
		String sql3 = "update fbm_outer set outerdjdl = 'F4' where outerdjdl = 'sj'";
		String sql4 = "update fbm_outer set outerdjdl = 'F5' where outerdjdl = 'fj'";
		dao.executeUpdate(sql1);
		dao.executeUpdate(sql2);
		dao.executeUpdate(sql3);
		dao.executeUpdate(sql4);
		
		String sql5 = "update fbm_outer set pk_billtypecode = '36GQ',pk_busibill = (select pk_endore from fbm_endore where pk_source = fbm_outer.pk_busibill) where pk_billtypecode = '36GA'  and outerdjdl in('F3' ,'F5') ";
		dao.executeUpdate(sql5);
	}
	
	/**
	 * 填充收款银行和付款银行字段值
	 * @throws BusinessException
	 */
	private void updateBaseInfoBankDoc() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String sql1  = "update fbm_baseinfo set receivebank = (select pk_bankdoc from bd_bankaccbas where bd_bankaccbas.pk_bankaccbas=receivebankacc) where receivebankacc in (select pk_bankaccbas from bd_bankaccbas)";
		String sql2 = "update fbm_baseinfo set paybank = (select pk_bankdoc from bd_bankaccbas where bd_bankaccbas.pk_bankaccbas=paybankacc) where paybankacc in (select pk_bankaccbas from bd_bankaccbas)";
		
		dao.executeUpdate(sql1);
		dao.executeUpdate(sql2);
	}
	
	/**
	 * 更新付票，承兑、托收、贴现的已生成单据状态的已制证标志为true
	 * @throws BusinessException
	 */
	private void updateVoucherFlag() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		//先全部将制证设为N
		String invoice_init = "update fbm_register set voucher = 'N'";
		String accept_init = "update fbm_accept set unitvoucher = 'N'";
		String collection_init = "update fbm_collection set unitvoucher = 'N'";
		String discount_init = "update fbm_discount set unitvoucher = 'N'";
		String storage_init = "  update fbm_storage set unitvoucher = 'N',centervoucher='N'";
		String relief_init = "update fbm_relief set unitvoucher = 'N',centervoucher='N'";
		String reckon_init = "update fbm_reckon set unitvoucher = 'N',centervoucher='N'";
		
		dao.executeUpdate(invoice_init);
		dao.executeUpdate(accept_init);
		dao.executeUpdate(collection_init);
		dao.executeUpdate(discount_init);
		dao.executeUpdate(storage_init);
		dao.executeUpdate(relief_init);
		dao.executeUpdate(reckon_init);
		
		
		//fbm_register表增加制证人和制证日期后，下句sql要修改，填充这两个字段
		String sql_invoice = "update fbm_register set vbillstatus = 1,voucher='Y' where pk_billtypecode ='36GL' and (vbillstatus=12 or sfflag='Y')";
		String sql_accept = "update fbm_accept set vbillstatus = 1,unitvoucher='Y',vvouchermanid=vapproveid,vvoucherdate=dapprovedate where vbillstatus = 12";
		String sql_collection = "update fbm_collection set vbillstatus = 9,unitvoucher='Y',vvoucherid=vapproveid,dvoucherdate = dapprovedate where vbillstatus in ( 12,19,25)";
		String sql_discount = " update fbm_discount set vbillstatus = 1,unitvoucher='Y',vvoucherid=vapproveid,dvoucherdate=dapprovedate where  vbillstatus in ( 12,19,25)";
		String sql_storage = "update fbm_storage set centervoucher='Y' where vbillstatus = 16";
		String sql_storage2  = " update fbm_storage set centervoucher='Y',unitvoucher = 'Y' where vbillstatus = 23";
		String sql_relief = "update fbm_relief set centervoucher = 'Y' where vbillstatus = 19";
		String sql_relief2 = "update fbm_relief  set centervoucher='Y',unitvoucher = 'Y' where vbillstatus = 23";
		String sql_reckon = "update fbm_reckon set centervoucher = 'Y' where vbillstatus = 19";
		String sql_reckon2 = "update fbm_reckon  set centervoucher='Y',unitvoucher = 'Y' where vbillstatus = 23";
		
		dao.executeUpdate(sql_invoice);
		dao.executeUpdate(sql_accept);
		dao.executeUpdate(sql_collection);
		dao.executeUpdate(sql_discount);
		dao.executeUpdate(sql_storage);
		dao.executeUpdate(sql_storage2);
		dao.executeUpdate(sql_relief);
		dao.executeUpdate(sql_relief2);
		dao.executeUpdate(sql_reckon);
		dao.executeUpdate(sql_reckon2);
		
		String storage_billstatus = " update fbm_storage set vbillstatus = 13 where vbillstatus in( 16,23) and pk_billtypecode = '36GD'";
		String storage_billstatus2 = "  update fbm_storage set vbillstatus = 14 where vbillstatus in( 16,23) and pk_billtypecode = '36GE'";
		String relief_billstatus = "update fbm_relief set vbillstatus = 1 where vbillstatus in(19,23)";
		String reckon_billstatus = "update fbm_reckon set vbillstatus = 1 where vbillstatus in(19,23)";
		
		dao.executeUpdate(storage_billstatus);
		dao.executeUpdate(storage_billstatus2);
		dao.executeUpdate(relief_billstatus);
		dao.executeUpdate(reckon_billstatus);
	}
	
	/**
	 * 将单位记账按钮设置为false
	 * @throws BusinessException
	 */
	private void updateTallyFlag() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String storage = "update fbm_storage set unittally = 'N'";
		String relief = "update fbm_relief set unittally = 'N'";
		String reckon = "update fbm_reckon set unittally = 'N'";
		dao.executeUpdate(storage);
		dao.executeUpdate(relief);
		dao.executeUpdate(reckon);
	}
	
	/**
	 * 填充表的新增的币种字段
	 * @throws BusinessException
	 */
	private void fillCurr() throws BusinessException{
		fillStorageBaseinfoPK();
		String storage = "update fbm_storage set pk_currtype = (select distinct(pk_curr) from fbm_baseinfo join fbm_storage_b on fbm_baseinfo.pk_baseinfo=fbm_storage_b.pk_baseinfo where fbm_storage_b.pk_storage = fbm_storage.pk_storage)";
		String relief = "update fbm_relief set pk_currtype = (select distinct(pk_curr) from fbm_baseinfo join fbm_relief_b on fbm_baseinfo.pk_baseinfo=fbm_relief_b.pk_baseinfo where fbm_relief_b.pk_relief = fbm_relief.pk_relief)";
		String reckon = "update fbm_reckon set pk_curr  = (select distinct(pk_curr) from fbm_baseinfo join fbm_reckon_b on fbm_baseinfo.pk_baseinfo=fbm_reckon_b.pk_baseinfo where fbm_reckon_b.pk_reckon = fbm_reckon.pk_reckon)";
		
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(storage);
		dao.executeUpdate(relief);
		dao.executeUpdate(reckon);
	}
	
	/**
	 * 更新票据节点号显示编码
	 * @throws BusinessException
	 */
	private void updateFunDispcode() throws BusinessException{
		List<String> sqlarray = new ArrayList<String>();
		sqlarray.add("update sm_funcregister set disp_code = '20040501' where fun_code = '3620'");
		sqlarray.add("update sm_funcregister set disp_code = '2004050108' where fun_code = '362008'");
		sqlarray.add("update sm_funcregister set disp_code = '200405010835' where fun_code = '36200835'");
		sqlarray.add("update sm_funcregister set disp_code = '200405010848' where fun_code = '36200848'");
		sqlarray.add("update sm_funcregister set disp_code = '200405010860' where fun_code = '36200860'");
		sqlarray.add("update sm_funcregister set disp_code = '2004050110' where fun_code = '362010'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011005' where fun_code = '36201005'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011010' where fun_code = '36201010'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011015' where fun_code = '36201015'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011017' where fun_code = '36201017'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011019' where fun_code = '36201019'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011020' where fun_code = '36201020'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011025' where fun_code = '36201025'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011030' where fun_code = '36201030'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011035' where fun_code = '36201035'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011037' where fun_code = '36201037'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011040' where fun_code = '36201040'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011045' where fun_code = '36201045'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011050' where fun_code = '36201050'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011055' where fun_code = '36201055'");
		sqlarray.add("update sm_funcregister set disp_code = '2004050115' where fun_code = '362015'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011505' where fun_code = '36201505'");
		sqlarray.add("update sm_funcregister set disp_code = '200405011510' where fun_code = '36201510'");
		sqlarray.add("update sm_funcregister set disp_code = '2004050125' where fun_code = '362025'");
		sqlarray.add("update sm_funcregister set disp_code = '2004050190' where fun_code = '362090'");
		sqlarray.add("update sm_funcregister set disp_code = '200405019001' where fun_code = '36209001'");
		sqlarray.add("update sm_funcregister set disp_code = '200405019002' where fun_code = '36209002'");
		sqlarray.add("update sm_funcregister set disp_code = '200405019003' where fun_code = '36209003'");
		sqlarray.add("update sm_funcregister set disp_code = '200405019004' where fun_code = '36209004'");
		String[] sqls = sqlarray.toArray(new String[0]);

		BaseDAO dao = new BaseDAO();
		for(String sql:sqls){
			dao.executeUpdate(sql);
		}
	}
	
	/**
	 * 处理清算单数据
	 * @throws BusinessException
	 */
	private void dealReckon() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String sql = "update fbm_reckon set inacc = reckonunitacc , outacc = reckonunitacc ";
		dao.executeUpdate(sql);
	}
	
	/**
	 * 更新票据类型
	 * @throws BusinessException
	 */
	private void updateFbmBilltype() throws BusinessException{
		String bank = "update fbm_baseinfo set fbmbilltype = 'FBMTYPE0000000000001' where fbmbilltype='bank'";
		String busi = "update fbm_baseinfo set fbmbilltype = 'FBMTYPE0000000000002' where fbmbilltype='busi'";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(bank);
		dao.executeUpdate(busi);
	}
	
	
	/**
	 * 删除无用的pub_busitrans表数据
	 * @throws BusinessException
	 */
	private void delUnUsedBusitrans() throws BusinessException{
		String del = "delete from pub_busitrans where pk_id in('0001FBM0000000000003','0001FBM0000000000005','0001FBM0000000000013','0001FBM0000000000015')";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(del);
	}
	
	/**
	 * 删除托收办理单下按钮重复
	 * @throws BusinessException
	 */
	private void delBillactionconfig() throws BusinessException{
		String sql1 = "delete from pub_billactionconfig where pk_billactionconfig = '1001ZZ10000000004Z0V' and actiontype='TRANSACT'";
		String sql2 = "delete from pub_billactionconfig where pk_billactionconfig = '1001ZZ1000000000AS4D' and actiontype='CANCELTRANSACT'";
		String sql3 = "delete from pub_billactionconfig where pk_billactionconfig = '1001ZZ10000000004Z0W' and actiontype='DISABLE'";
		String sql4 = "delete from pub_billactionconfig where pk_billactionconfig = '1001ZZ100000000053ND' and actiontype='CREATE'";
		String sql5 = "delete from pub_billactionconfig where pk_billactionconfig = '0001ZZ10000000001RA4' and actiontype='VOUCHER'";
		String sql6 = "delete from pub_billactionconfig where pk_billactionconfig = '0001ZZ10000000001RA5' and actiontype='CANCELVOUCHER'";
		
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql1);
		dao.executeUpdate(sql2);
		dao.executeUpdate(sql3);
		dao.executeUpdate(sql4);
		dao.executeUpdate(sql5);
		dao.executeUpdate(sql6);
	}
	/**
	 * 内部领用单子表数据可能pk_baseinfo字段没有值
	 * @throws BusinessException
	 */
	private void fillStorageBaseinfoPK() throws BusinessException{
		String sql = " update fbm_storage_b set pk_baseinfo = (select pk_baseinfo from fbm_register where fbm_register.pk_register = fbm_storage_b.pk_source) where pk_baseinfo is null";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql);
	}
	
	/**
	 * 设置受理日期
	 * @throws BusinessException
	 */
	private void fillDealDate() throws BusinessException{
		String sql4Keep = "update fbm_storage set dealdate = inputdate where vbillstatus = 13";
		String sql4Back = "update fbm_storage set dealdate = outputdate where vbillstatus = 14";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql4Keep);
		dao.executeUpdate(sql4Back);
		
		String sql4Relief = "update fbm_relief set dealdate = dapprovedate where dealdate is null ";
		String sql4Reckon = "update fbm_reckon set dealdate = dapprovedate where dealdate is null";
		dao.executeUpdate(sql4Relief);
		dao.executeUpdate(sql4Reckon);
	}
	
	/**
	 * 更新筹投资单位
	 * @throws BusinessException
	 */
	private void updateCumandocIounit() throws BusinessException{
		String updateCumandoc = "update bd_cumandoc set iounit = 'Y' where pk_cubasdoc in(select keepunit from fbm_storage where pk_billtypecode in('36GB','36GC'))";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(updateCumandoc);
	}
	
	/**
	 * 更新收付报票据类型
	 * @throws BusinessException
	 */
	private void updateArapNoteType() throws BusinessException{
//		IProductVersionQueryService productSrv = (IProductVersionQueryService)NCLocator.getInstance().lookup(IProductVersionQueryService.class);
//		ProductVersionVO[] versions = productSrv.queryByProductCode("2008");
//		if(versions !=null && versions.length > 0){
//			String sql1=" update arap_djfb set notetype = "+
//			" (select pk_notetype from bd_notetype join  fbm_baseinfo on bd_notetype.notetypecode=fbm_baseinfo.fbmbilltype "+
//			" where fbm_baseinfo.fbmbillno=pjh) where notetype is null and pjh is not null and pjh in"+
//			 " (select  fbmbillno from fbm_baseinfo where fbmbilltype in('bank','busi'))";
//			 String sql2=" update arap_djfb set notetype = "+
//			 " (select pk_notetype from bd_notetype join  fbm_baseinfo on bd_notetype.pk_notetype=fbm_baseinfo.fbmbilltype" +
//			  " where fbm_baseinfo.fbmbillno=pjh) where notetype is null and pjh is not null and pjh in"+
//			  " (select  fbmbillno from fbm_baseinfo where fbmbilltype in ('FBMTYPE0000000000001','FBMTYPE0000000000002'))";
//
//			 BaseDAO dao = new BaseDAO();
//			 dao.executeUpdate(sql1);
//			 dao.executeUpdate(sql2);
//		}
	}
	
	
	/**
	 * 删除业务数据
	 * @throws Exception
	 */
	private void deleteBusinessData() throws Exception{
		BaseDAO dao = new BaseDAO();
		String sql = "delete from BD_NODEPROPERTY where pk_funcregister ='0001AA100000000024Z3'";
		dao.executeUpdate(sql);
	}
	
	
	/**
	 * 删除bd_interfaceexec表数据
	 * @throws Exception
	 */
	private void deleteIntefaceexec() throws Exception{
		BaseDAO dao = new BaseDAO();
		String sql = "delete from bd_interfaceexec where pk_interface in('FBM10081616000000001','FBM10081616000000002')";
		dao.executeUpdate(sql);
	}
	
	/**
	 * 删除旧票据信息，包括功能节点注册等
	 */
	private void deleteOldFbmInfo() throws Exception{
		BaseDAO dao = new BaseDAO();
		String funcodeStr = " ('362001','362002','362003','362004','362005','36200102','36200103','36200104','362006','36200101','362020','362070','36207010','36207020') ";

		//贴现试算旧的单据模板(pub_billtemplet)pk
		String pk_billtemplet_discal = "0001ZZ100000000026PF";

		String sql_hotkey = "delete from pub_hotkeyregister where fun_code in"+funcodeStr;
		String sql_btn  = "delete from sm_butnregister where parent_id in (select cfunid from sm_funcregister where fun_code in"+funcodeStr+")";
		String sql_fun = "delete from sm_funcregister where fun_code in"+funcodeStr;
		String sql_billtemplet_b = "delete from pub_billtemplet_b where pk_billtemplet = '"+pk_billtemplet_discal+"' ";
		String sql_billtemplet = "delete from pub_billtemplet where pk_billtemplet = '"+pk_billtemplet_discal+"' ";
		int hotkey = dao.executeUpdate(sql_hotkey);
		Logger.debug("删除快捷键注册记录共" + hotkey);
		int btn = dao.executeUpdate(sql_btn);
		Logger.debug("删除按钮注册记录共" + btn);
		int fun = dao.executeUpdate(sql_fun);
		Logger.debug("删除功能节点共"+fun);
		int billtemplet_b = dao.executeUpdate(sql_billtemplet_b);
		Logger.debug("删除贴现试算旧模版子表记录共"+billtemplet_b);
		int billtemplet = dao.executeUpdate(sql_billtemplet);
		Logger.debug("删除贴现试算旧模版"+billtemplet);

		String sql_billcoderule = "delete from pub_billcode_rule where pk_billtypecode in('GA','GB','GC','GD','GE','GF','GG','GH','GI','GJ','GK','GM','GN','GO','GP')";
		String sql_billtype = "delete from bd_billtype where pk_billtypecode in('GA','GB','GC','GD','GE','GF','GG','GH','GI','GJ','GK','GM','GN','GO','GP')";

		int billcoderule = dao.executeUpdate(sql_billcoderule);
		Logger.debug("删除单据编码规则记录共" + billcoderule);
		int billtype = dao.executeUpdate(sql_billtype);
		Logger.debug("删除单据类型记录共"+ billtype);
	}

	
	/**
	 * 新的单据类型需要插入dap_controlrule表
	 * 否则制证时会出现修改标志为空的问题
	 * @throws Exception
	 */
	private void upgradeNewBilltype() throws Exception{
		IDapService dapSrv = (IDapService)NCLocator.getInstance().lookup(IDapService.class.getName());
		String[] pk_billtype = new String[]{
				FbmBusConstant.BILLTYPE_COLLECTION_UNIT,
				FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT,
				FbmBusConstant.BILLTYPE_ENDORE,
				FbmBusConstant.BILLTYPE_ENDORECLEAE,
				FbmBusConstant.BILLTYPE_INNERBACK,
				FbmBusConstant.BILLTYPE_INNERKEEP,
				FbmBusConstant.BILLTYPE_LIQUIDATE,
				FbmBusConstant.BILLTYPE_RECKON_RECIEPT,
				FbmBusConstant.BILLTYPE_RELIEF
		};

		ICorpQry CorpQuery = (ICorpQry) NCLocator.getInstance().lookup(ICorpQry.class.getName());
		String[] pk_corps = CorpQuery.getAccountedCorpPKs();
		try {
			if (pk_corps != null) {
				for (int i = 0; i < pk_corps.length; i++) {
					GlorgbookVO[] glorgbookvo = BDGLOrgBookAccessor.getGLOrgBookVOsByPk_Corp(pk_corps[i]);
					if(glorgbookvo!=null){
						for (int j = 0; j < glorgbookvo.length; j++) {
							initControlRule(pk_corps[i], glorgbookvo[j].getPk_glorg(), glorgbookvo[j].getPk_glbook(), pk_billtype);
						}
					}
				}
			}
		} catch (Exception ex) {
			Logger.error(ex);
		}
	}

    public void initControlRule(String pk_corp, String pk_glorg,
            String pk_glbook,String[] pk_billtypes) throws BusinessException {

        try {
            //拷贝dap_controlrule
            BilltypeVO[] billtypes = null;
            if(pk_billtypes != null && pk_billtypes.length>0){
            	billtypes = new BilltypeVO[pk_billtypes.length];
            	for(int i=0; i<pk_billtypes.length; i++){
            		billtypes[i]= new BilltypeVO();
            		billtypes[i].setPrimaryKey(pk_billtypes[i]);
            		billtypes[i].setIsaccount(UFBoolean.TRUE);
            	}
            }

            BaseDAO dao = new BaseDAO();
            FbmControlRuleVO[] rules = new FbmControlRuleVO[billtypes.length];

            for (int i = 0; i < billtypes.length; i++) {
                if (billtypes[i].getIsaccount() == null
                        || !billtypes[i].getIsaccount().booleanValue()) {
                    continue;
                }
                //查询是否存在规则
                String sql = "Select pk_corp from dap_controlrule where pk_corp='"+pk_corp+"' and pk_billtype='"+billtypes[i].getPk_billtypecode()+"'  and pk_glorg='"+pk_glorg+"' and pk_glbook='"+pk_glbook+"' ";
                List list = (List)dao.executeQuery(sql, new ColumnListProcessor());
                if (list !=null && list.size() > 0) {
                    continue;
                }


                rules[i] = new FbmControlRuleVO();
                rules[i].setPk_corp(pk_corp);
                rules[i].setPk_glorg(pk_glorg);
                rules[i].setPk_glbook(pk_glbook);
                rules[i].setPk_billtype(billtypes[i].getPrimaryKey());

                //fgj2001-12-01
                rules[i].setControl("NNYNNNNNNNNNNNN");


                dao.insertVO(rules[i]);

            }
        } catch (Exception ex) {
            Logger.error(ex);
            throw new BusinessException(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("fidap","UPPfidap-000003")/*@res "控制条件初始化异常！"*/ + ex.getMessage());

        }
    }
    
	/**
	 * 删除5.02的某些默认模板，防止和5.3重复
	 * @throws Exception
	 */
	private void delOldVouchtemp() throws Exception{
		BaseDAO dao = new BaseDAO();
		String sql = "delete from dap_vouchtemp where pk_vouchtemp in('0001ZZ1000000000DL7Y','0001ZZ1000000000E272','0001ZZ1000000000E275','0001ZZ1000000000E278','0001ZZ1000000000EZE2','0001ZZ1000000000DRDD','0001ZZ1000000000EZE8','0001ZZ1000000000FAXE','0001ZZ10000000004S35','0017PF1000000000033K','0017PF1000000000033F','0017PF1000000000033A')";
		dao.executeUpdate(sql);
	}
	
	

    /**
     * 为已建账公司添加参数
     * @throws Exception
     */
    public void insertSysinit(String initcode,String initname,String pk_sysinittemp,String defaultvalue) throws Exception{
		BaseDAO dao = new BaseDAO();
		String queryFbmCorp = "select pk_corp from sm_createcorp where funccode = '3620'";
		List corpList = (List)dao.executeQuery(queryFbmCorp, new ColumnListProcessor());
		if(corpList != null && corpList.size() > 0){
			int count = corpList.size();
			SysInitVO vo = new SysInitVO();
			vo.setControlflag(false);
			vo.setEditflag(new UFBoolean(true));
			vo.setInitcode(initcode);
			vo.setInitname(initname);
			vo.setMakedate(new UFDate(new Date()));
			vo.setSysinit(pk_sysinittemp);
			vo.setValue(defaultvalue);
			for(int i = 0; i < count;i++){
				String pk_corp  = (String) corpList.get(i);
				String hasParam = "select 1 from pub_sysinit where initcode='"+initcode+"' and pk_org='" + pk_corp+ "'";
				List list = (List)dao.executeQuery(hasParam, new ColumnListProcessor());
				if(list == null || list.size() == 0){
					vo.setPk_org(pk_corp);
					dao.insertVO(vo);
				}
			}
		}


    }
    
    /**
     * 填充银行托收、贴现申请和贴现办理、背书的票据类别字段
     */
    public void fillOpBillType() throws Exception{
    	String sql_unistor_collection = "update fbm_collection set opbilltype='bill_unistor' where  pk_source in (select pk_register from fbm_register where gathertype='unistorage')";
    	String sql_privacy_collection = "update fbm_collection set opbilltype='bill_privacy'  where  pk_source in (select pk_register from fbm_register where gathertype<>'unistorage')";

    	String sql_unistor_discount = "update fbm_discount set opbilltype='bill_unistor' where  pk_source in (select pk_register from fbm_register where gathertype='unistorage')";
    	String sql_privacy_discount = "update fbm_discount set opbilltype='bill_privacy'  where  pk_source in (select pk_register from fbm_register where gathertype<>'unistorage')";

    	String sql_unistor_endore = "update fbm_endore set opbilltype='bill_unistor' where  pk_source in (select pk_register from fbm_register where gathertype='unistorage')";
    	String sql_privacy_endore = "update fbm_endore set opbilltype='bill_privacy'  where  pk_source in (select pk_register from fbm_register where gathertype<>'unistorage')";

    	BaseDAO dao = new BaseDAO();
    	dao.executeUpdate(sql_unistor_collection);
    	dao.executeUpdate(sql_privacy_collection);
    	dao.executeUpdate(sql_unistor_discount);
    	dao.executeUpdate(sql_privacy_discount);
    	dao.executeUpdate(sql_unistor_endore);
    	dao.executeUpdate(sql_privacy_endore);


    }
    
    
    /**
     * 由于贴现办理单业务日期由实际贴现日期修改为办理日期，如果办理日期为空则选择操作日期、
     * 故对数据进行升级
     * @throws Exception
     */
    public void resetDiscountActDate(String oldversion) throws Exception{
    	if(oldversion.startsWith("5.02")){
    		String resetDate = "update fbm_action set actiondate = (select isnull(dtransactdate,doperatedate) from fbm_discount where fbm_discount.pk_discount = pk_bill) where billtype='36GG'";
    		BaseDAO dao = new BaseDAO();
    		dao.executeUpdate(resetDate);
    	}
    }
    
    
    /**
     * 填充502背书单的单据号
     * @throws Exception
     */
    public void fillEndoreBillno(String oldversion) throws Exception{
    	if(oldversion.startsWith("5.02")){
	    	BaseDAO dao = new BaseDAO();
	    	List list = (List)dao.retrieveByClause(EndoreVO.class, "isnull(dr,0)=0 and vbillno is null");
	    	if(list !=null && list.size() > 0){
	    		int count = list.size();
	    		IBillcodeRuleService billcodeSrv = (IBillcodeRuleService) NCLocator.getInstance().lookup(IBillcodeRuleService.class.getName());
	    		String vbillno = null;
	    		for(int i =0 ;i < count;i++){
	    			EndoreVO endoreVO = (EndoreVO)list.get(i);
	    			vbillno = billcodeSrv.getBillCode_RequiresNew(FbmBusConstant.BILLTYPE_ENDORE,endoreVO.getPk_corp(),null,null);
	    			endoreVO.setVbillno(vbillno);
	    			dao.updateVO(endoreVO);
	    		}
	    	}
    	}
    }
    
    /**
     * 更新背书单fbm_outer表的关系
     * 将单据类型为GA和fj,fk的修改为背书单PK
     * @throws Exception
     */
    public void rebuildOuter(String oldversion) throws Exception{
    	if(oldversion.startsWith("5.02")){
	    	BaseDAO dao = new BaseDAO();
	    	//检查是否存在收付报表，不存在时直接返回
	    	try{
	    		String checksql = "select 1 from arap_djfb";
	    		dao.executeQuery(checksql, new ColumnListProcessor());
	    	}catch(Exception e){
	    		return;
	    	}
	
	    	String rebuildEndorePK = "update fbm_outer set pk_busibill = isnull((select pk_endore from fbm_endore where pk_source=fbm_outer.pk_busibill),pk_busibill) where outerdjdl in('fk','fj') and pk_billtypecode='36GA'";
	    	String rebuildBillType = "update fbm_outer set pk_billtypecode = '36GQ' where outerdjdl in('fk','fj') and pk_billtypecode='36GA'";
	
	
	    	dao.executeUpdate(rebuildEndorePK);
	    	dao.executeUpdate(rebuildBillType);
	

	
	    	//更新502生成背书单没有汇率等字段的问题
	    	String ratesql = "update fbm_endore set brate = (select bbhl from arap_djfb join fbm_outer on fb_oid=pk_outerbill_b where pk_busibill=fbm_endore.pk_endore),frate = (select fbhl from arap_djfb join fbm_outer on fb_oid=pk_outerbill_b where pk_busibill=fbm_endore.pk_endore),moneyb=(select jfbbje from arap_djfb join fbm_outer on fb_oid=pk_outerbill_b where pk_busibill=fbm_endore.pk_endore),moneyf=(select jffbje from arap_djfb join fbm_outer on fb_oid=pk_outerbill_b where pk_busibill=fbm_endore.pk_endore) where syscode = 'ARAP'";
	    	dao.executeUpdate(ratesql);
    	}
    }
    
    /**
     * 删除502的默认科目分类定义
     * @throws Exception
     */
    public void delDefaultSubjClass(String oldversion) throws Exception{
    	if(oldversion.startsWith("5.02")){
	    	String delSql = "delete from dap_insubjclass where pk_corp = '0001' and pk_insubjclass in('1001ZZ1000000000D830','1001ZZ1000000000D831','1001ZZ1000000000D833','1001ZZ1000000000D834','1001ZZ1000000000DZUY','1001ZZ1000000000EZDV')";
	    	BaseDAO dao = new BaseDAO();
	    	dao.executeUpdate(delSql);
    	}
    }
    
    /**
     * 将结算凭证转换为总帐凭证
     * @throws BusinessException
     */
    private void changeVouchertype() throws BusinessException{
    	String sql = "update dap_vouchtemp set destsystem =0  where pk_sys='FBM' and pk_proc like '36G%' and not exists(select 1 from dap_vouchtemp b where b.pk_corp = dap_vouchtemp.pk_corp and destsystem=0 and b.pk_proc = dap_vouchtemp.pk_proc )";
    	BaseDAO dao = new BaseDAO();
    	dao.executeUpdate(sql);
    }
    
	public void doBeforeUpdateDB(String oldVersion, String newVersion)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void doBeforeUpdateData(String oldVersion, String newVersion)
			throws Exception {
		// TODO Auto-generated method stub
		Logger.error("oldVersion:" + oldVersion + "  newVersion:" + newVersion);
		
		if ((oldVersion.startsWith("5.02")||oldVersion.startsWith("5.3")) && newVersion.startsWith("5.5")) {
			delDefaultVoucher();
			delSysinit();
		}
	}
	
	/**
	 * 升级帐套前先删除默认的影响因素、默认科目分类定义和默认凭证模板
	 * @throws BusinessException
	 */
	private void delDefaultVoucher() throws BusinessException{
		String factor = "delete from dap_factor where pk_billtype = 'FBM' ";
		String insubjclass ="delete from dap_insubjclassfactor where PK_INSUBJECT in (select pk_insubjclass from dap_insubjclass where  pk_billtype = 'FBM' and pk_corp='0001')";
		String insubjclass2 = "delete from dap_insubjclass where pk_billtype = 'FBM' and pk_corp='0001'";
		String vouchtemp = "delete from dap_vouchtemp_b where pk_vouchtemp in(select pk_vouchtemp from dap_vouchtemp where pk_sys = 'FBM' and pk_corp='0001')";
		String vouchtemp2 ="delete from dap_vouchtemp where pk_sys = 'FBM' and pk_corp='0001'";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(factor);
		dao.executeUpdate(insubjclass);
		dao.executeUpdate(insubjclass2);
		dao.executeUpdate(vouchtemp);
		dao.executeUpdate(vouchtemp2);
		
		//公司自定义的科目分类定义以及影响因素不删除，但是名称相同的需要进行PK替换
		String deffactor1 = "update dap_insubjclassfactor set pk_factor='FBMCZZ1000000000I8N5' where pk_factor='0001ZZ1000000000I8N5' ";//调剂单位
		String deffactor2 = "update dap_insubjclassfactor set pk_factor='FBMCZZ1000000000I8N6' where pk_factor='0001ZZ1000000000I8N6' ";//持票单位
		String deffactor3 = "update dap_insubjclassfactor set pk_factor='FBMCZZ1000000000I8N7' where pk_factor='0001ZZ1000000000I8N7' ";//入库单位
		String deffactor4 = "update dap_insubjclassfactor set pk_factor='FBMCZZ1000000000KEL3' where pk_factor='0001ZZ1000000000KEL3' ";//调剂方向
		String deffactor5 = "update dap_insubjclassfactor set pk_factor='FBMCZZ1000000000DUGS' where pk_factor='1001ZZ1000000000DUGS' ";//入库方式
		String deffactor6 = "update dap_insubjclassfactor set pk_factor='FBMCZZ1000000000EZE0' where pk_factor='1001ZZ1000000000EZE0' ";//清算单位
		dao.executeUpdate(deffactor1);
		dao.executeUpdate(deffactor2);
		dao.executeUpdate(deffactor3);
		dao.executeUpdate(deffactor4);
		dao.executeUpdate(deffactor5);
		dao.executeUpdate(deffactor6);
	}
	
	/**
	 * 删除FBM002和FBM003参数设置，由于 PK少一位导致无法修改值
	 * @throws BusinessException
	 */
	private void delSysinit() throws BusinessException{
		String sql = "delete from pub_sysinit where initcode in ('FBM002','FBM003')";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql);
	}
	
	/**
	 * 处理银行账户升级
	 * @throws BusinessException
	 */
	private void dealBankaccbasUpgrade() throws BusinessException{
		//更新票据基本信息付款银行账号
		String baseinfo_paybankacc = "UPDATE fbm_baseinfo SET paybankacc = isnull((SELECT newpk FROM bd_bankupdate WHERE oldpk = fbm_baseinfo.paybankacc and (para1=fbm_baseinfo.payunit or para1=(select pk_corp1 from bd_cubasdoc where pk_cubasdoc=fbm_baseinfo.payunit)) AND isnull(dr, 0) = 0),paybankacc) WHERE paybankacc IN (SELECT oldpk FROM bd_bankupdate WHERE isnull(dr, 0) = 0)";
		//更新票据基本信息收款银行账号
		String baseinfo_recievebankacc = "UPDATE fbm_baseinfo SET receivebankacc = isnull((SELECT newpk FROM bd_bankupdate WHERE oldpk = fbm_baseinfo.receivebankacc and (para1=fbm_baseinfo.payunit or para1=(select pk_corp1 from bd_cubasdoc where pk_cubasdoc=fbm_baseinfo.payunit)) AND isnull(dr, 0) = 0),receivebankacc) WHERE receivebankacc IN (SELECT oldpk FROM bd_bankupdate WHERE isnull(dr, 0) = 0)";
	
		//更新开票保证金账号
		String register_securityacc = "UPDATE fbm_register SET securityaccount = isnull((SELECT newpk FROM bd_bankupdate WHERE oldpk = fbm_register.securityaccount and (para1=(select payunit from fbm_baseinfo where pk_baseinfo=fbm_register.pk_baseinfo) or para1=pk_corp) AND isnull(dr, 0) = 0),securityaccount) WHERE securityaccount IN (SELECT oldpk FROM bd_bankupdate WHERE isnull(dr, 0) = 0) and pk_billtypecode='36GL'";
		//更新贴现办理单贴现银行账号
		String discount_account = "UPDATE fbm_discount SET discount_account = isnull((SELECT newpk FROM bd_bankupdate WHERE oldpk = fbm_discount.discount_account and (para1=(select holdunit from fbm_register where pk_register=pk_source) or para1=pk_corp) AND isnull(dr, 0) = 0),discount_account) WHERE discount_account IN (SELECT oldpk FROM bd_bankupdate WHERE isnull(dr, 0) = 0)";
		
		//更新托收办理单 托收银行帐号
		String collection_account = "UPDATE fbm_collection SET holderacc = isnull((SELECT newpk FROM bd_bankupdate WHERE oldpk = fbm_collection.holderacc  and (para1=(select holdunit from fbm_register where pk_register=pk_source) or para1=pk_corp) AND isnull(dr, 0) = 0),holderacc) WHERE holderacc  IN (SELECT oldpk FROM bd_bankupdate WHERE isnull(dr, 0) = 0)";
		
		//更新承兑银行账号
		String accept_account_holdacc = "UPDATE fbm_accept SET holderacc = isnull((SELECT newpk FROM bd_bankupdate WHERE oldpk = fbm_accept.holderacc  and (para1=holdunit or para1=(select pk_corp1 from bd_cubasdoc where pk_cubasdoc=holdunit))),holderacc) WHERE holderacc  IN (SELECT oldpk FROM bd_bankupdate WHERE isnull(dr, 0) = 0)";
		String accept_account_security = "UPDATE fbm_accept SET backsecaccount = isnull((SELECT newpk FROM bd_bankupdate WHERE oldpk = fbm_accept.backsecaccount  and para1=pk_corp ),backsecaccount) WHERE backsecaccount  IN (SELECT oldpk FROM bd_bankupdate WHERE isnull(dr, 0) = 0)";
		
		
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(baseinfo_paybankacc);
		dao.executeUpdate(baseinfo_recievebankacc);
		dao.executeUpdate(register_securityacc);
		dao.executeUpdate(discount_account);
		dao.executeUpdate(collection_account);
		dao.executeUpdate(accept_account_holdacc);
		dao.executeUpdate(accept_account_security);
		
	}
	
	/**
	 * 处理升级合计金额
	 * @throws BusinessException
	 */
	private void fillSummoney() throws BusinessException{
		String storageSQL = "update fbm_storage set summoneyy = (select sum(moneyy) from fbm_baseinfo join fbm_storage_b on fbm_storage_b.pk_baseinfo = fbm_baseinfo.pk_baseinfo where fbm_storage_b.pk_storage=fbm_storage.pk_storage) where summoneyy is null";
		String reliefSQL = "update fbm_relief set summoney = (select sum(moneyy) from fbm_baseinfo join fbm_relief_b on fbm_relief_b.pk_baseinfo=fbm_baseinfo.pk_baseinfo where fbm_relief_b.pk_relief=fbm_relief.pk_relief) where summoney is null";
		String reckonSQL = "update fbm_reckon set reckonmoneysum = (select sum(fbm_baseinfo.moneyy) from fbm_baseinfo join fbm_reckon_b on fbm_reckon_b.pk_baseinfo = fbm_baseinfo.pk_baseinfo where fbm_reckon_b.pk_reckon=fbm_reckon.pk_reckon) where reckonmoneysum is null";
		
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(storageSQL);
		dao.executeUpdate(reliefSQL);
		dao.executeUpdate(reckonSQL);
	}
	
	/**
	 * 更新收票登记单最新票据状态
	 * 更新票据基本信息最新票据状态
	 * @throws BusinessException
	 */
	private void resetEndStatus() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String sql = "update fbm_register set registerstatus = (select endstatus from fbm_action where pk_source=pk_register and serialnum = (select max(serialnum) from fbm_action as  b where b.pk_source=pk_register)) where isnull(dr,0) = 0";
		dao.executeUpdate(sql);
		sql = "update fbm_baseinfo set baseinfostatus = (select endstatus from fbm_action where fbm_baseinfo.pk_baseinfo = fbm_action.pk_baseinfo and serialnum = (select max(serialnum) from fbm_action b where b.pk_baseinfo= fbm_baseinfo.pk_baseinfo)) where isnull(dr,0) = 0";
		dao.executeUpdate(sql);
	}
	
	/**
	 * 重新设置fbm_storage表的pk_baseinfo字段值，升级前可能由于程序错误，某些记录pk_baseinfo值为空
	 * @throws BusinessException
	 */
	private void resetStorageBaseInfo() throws BusinessException{
		String sql = "update fbm_storage_b set pk_baseinfo = (select pk_baseinfo from fbm_register where pk_register = fbm_storage_b.pk_source) where isnull(dr,0)=0 and pk_baseinfo is null";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql);
	}
	
	/**
	 * 设置内部领用单的领用类型值
	 * @throws BusinessException
	 */
	public void updateInnerBackInputType() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		HYBillVO[] vos  =  (HYBillVO[])new HYPubBO().queryBillVOByCondition(new String[] { HYBillVO.class.getName(),
				StorageVO.class.getName(), StorageBVO.class.getName()}, " pk_billtypecode = '36GE' and inputtype is null and isnull(fbm_storage.dr,0)=0 ");
		if(vos!= null && vos.length > 0){
			
			String pk_storage = null;
			String pk_baseinfo = null;
			String inputtype = null;
			String tmpSql = null;
			List<Map> list = new LinkedList<Map>();
			List<String> updateSqls = new ArrayList<String>(); 
			for(HYBillVO vo:vos){
				pk_storage = vo.getParentVO().getPrimaryKey();
				pk_baseinfo = (String)vo.getChildrenVO()[0].getAttributeValue(StorageBVO.PK_BASEINFO);
				tmpSql = "select pk_bill,inputtype,pk_billtypecode from fbm_action join fbm_storage on pk_bill = pk_storage where pk_baseinfo = '"+pk_baseinfo+"' and billtype in('36GD','36GE') and isnull(fbm_storage.dr,0)=0  and isnull(fbm_action.dr,0)=0 order by serialnum desc ";
				list  = (List)dao.executeQuery(tmpSql, new MapListProcessor());
				if(list != null && list.size()  >0){
					boolean isMatch = false;
					String innerkeepPK = null;
					for(Map m:list){
						if(isMatch && !m.get("pk_bill").toString().equals(pk_storage) && m.get("pk_billtypecode").equals("36GD")){
							innerkeepPK = m.get("pk_bill").toString();
							break;
						}
						if(m.get("pk_bill").toString().equals(pk_storage)){//领用单PK
							isMatch = true;
						}
					}
					
					updateSqls.add("update fbm_storage set inputtype = (select inputtype from fbm_storage where pk_storage = '"+innerkeepPK+"') where pk_storage = '"+pk_storage+"'");
				}
			}
			if(updateSqls != null && updateSqls.size()>0){
				String[] sqls = updateSqls.toArray(new String[0]);
				PersistenceManager sessionManager = null;
				try {
					sessionManager = PersistenceManager.getInstance();
					JdbcSession session = sessionManager.getJdbcSession();
					for (int i = 0; i < sqls.length; i++) {
						if (!CommonUtil.isNull(sqls[i])) {
							session.addBatch(sqls[i]);
						}
					}
					 session.executeBatch();
				} catch (Exception e) {
					Logger.error(e.getMessage(), e);
					throw new DAOException(e.getMessage());
				} finally {
					if (sessionManager != null) {
						sessionManager.release();
					}
				}
			}
		}
	}
	
	/**
	 * 删除旧默认模板
	 * @throws BusinessException
	 */
	private void deleteOldDapVoucher() throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String sql ="delete from dap_vouchtemp where pk_sys = 'FBM' and pk_vouchtemp in('1001ZZ1000000000DL7Y','1001ZZ1000000000E272','1001ZZ1000000000E275','1001ZZ1000000000E278','1001ZZ1000000000EZE2','1001ZZ1000000000DRDD','1001ZZ1000000000EZE8','1001ZZ1000000000FAXE','0001PF1000000000033A','0001PF1000000000033F','0001PF1000000000033K','0001ZZ10000000004S35')";
		dao.executeUpdate(sql);
	}

}
