package nc.bs.fbm.pub;

import java.util.Date;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.sm.accountmanage.AbstractUpdateAccount;
import nc.itf.dap.pub.IDapService;
import nc.itf.uap.bd.corp.ICorpQry;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.ui.bd.BDGLOrgBookAccessor;
import nc.vo.bd.b54.GlorgbookVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.FbmControlRuleVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.para.SysInitVO;

/**
 * 票据升级程序
 * @author xwq
 *
 */
public class FBMDataUpdateBO extends AbstractUpdateAccount{

	public void doAfterUpdateData(String oldVersion, String newVersion)
			throws Exception {
		// TODO Auto-generated method stub
		Logger.error("oldVersion:" + oldVersion + "  newVersion:" + newVersion);

		if (newVersion.startsWith("5.3")) {
			deleteBusinessData();//删除上版本的业务数据
			deleteIntefaceexec();
			deleteOldFbmInfo();
			upgradeNewBilltype();
			delOldVouchtemp();
			insertSysinit();
			fillOpBillType();
			fillCurrtype();
			resetDiscountActDate();
			fillEndoreBillno();//填充502背书单据编号
			rebuildOuter();//更新对应关系
			delDefaultSubjClass();//删除5.02的科目分类定义
		}
	}

	/**
	 * 删除5.02的某些默认模板，防止和5.3重复
	 * @throws Exception
	 */
	private void delOldVouchtemp() throws Exception{
		BaseDAO dao = new BaseDAO();
		String sql = "delete from dap_vouchtemp where pk_vouchtemp in('1001ZZ1000000000DL7Y','1001ZZ1000000000E272','1001ZZ1000000000E275','1001ZZ1000000000E278','1001ZZ1000000000EZE2','1001ZZ1000000000DRDD','1001ZZ1000000000EZE8','1001ZZ1000000000FAXE','1001ZZ10000000004S35','1017PF1000000000033K','1017PF1000000000033F','1017PF1000000000033A')";
		dao.executeUpdate(sql);
	}

	public void doBeforeUpdateDB(String oldVersion, String newVersion)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void doBeforeUpdateData(String oldVersion, String newVersion)
			throws Exception {
		// TODO Auto-generated method stub

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
     * 为已建账公司添加参数：背书办理单是否与收付报单据集成应用
     * @throws Exception
     */
    public void insertSysinit() throws Exception{
		BaseDAO dao = new BaseDAO();
		String queryFbmCorp = "select pk_corp from sm_createcorp where funccode = '3620'";
		List corpList = (List)dao.executeQuery(queryFbmCorp, new ColumnListProcessor());
		if(corpList != null && corpList.size() > 0){
			int count = corpList.size();
			SysInitVO vo = new SysInitVO();
			vo.setControlflag(false);
			vo.setEditflag(new UFBoolean(true));
			vo.setInitcode("FBM005");
			vo.setInitname(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000155")/*@res "背书办理单是否与收付报单据集成应用"*/);
			vo.setMakedate(new UFDate(new Date()));
			vo.setSysinit("0001ZZ1000000WYG25XN");
			vo.setValue("Y");
			for(int i = 0; i < count;i++){
				String pk_corp  = (String) corpList.get(i);
				String hasParam = "select 1 from pub_sysinit where initcode='FBM005' and pk_org='" + pk_corp+ "'";
				List list = (List)dao.executeQuery(hasParam, new ColumnListProcessor());
				if(list == null || list.size() == 0){
					vo.setPk_org(pk_corp);
					//vo.setPk_sysinit("FBM005" + pk_corp + "0000000001");
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
     *  填充调剂单，清算单，内部存放和领用的币种字段
     * @throws Exception
     */
    public void fillCurrtype() throws Exception{
    	String reliefCurr = "update fbm_relief set pk_currtype =  (select distinct pk_curr from fbm_relief_b join fbm_baseinfo on fbm_relief_b.pk_baseinfo=fbm_baseinfo.pk_baseinfo where fbm_relief_b.pk_relief=fbm_relief.pk_relief)";
    	String reckonCurr = "update fbm_reckon set pk_curr = (select distinct pk_curr from fbm_reckon_b join fbm_baseinfo on fbm_reckon_b.pk_baseinfo=fbm_baseinfo.pk_baseinfo where fbm_reckon_b.pk_reckon=fbm_reckon.pk_reckon)";
    	String storageCurr = "update fbm_storage set pk_currtype = (select distinct pk_curr from fbm_storage_b join fbm_baseinfo on fbm_storage_b.pk_baseinfo=fbm_baseinfo.pk_baseinfo where fbm_storage_b.pk_storage=fbm_storage.pk_storage) where pk_billtypecode in('36GD','36GE')";

    	BaseDAO dao = new BaseDAO();
    	dao.executeUpdate(reliefCurr);
    	dao.executeUpdate(reckonCurr);
    	dao.executeUpdate(storageCurr);
    }

    /**
     * 由于贴现办理单业务日期由实际贴现日期修改为办理日期，如果办理日期为空则选择操作日期、
     * 故对数据进行升级
     * @throws Exception
     */
    public void resetDiscountActDate() throws Exception{
    	String resetDate = "update fbm_action set actiondate = (select isnull(dtransactdate,doperatedate) from fbm_discount where fbm_discount.pk_discount = pk_bill) where billtype='36GG'";
    	BaseDAO dao = new BaseDAO();
    	dao.executeUpdate(resetDate);
    }

    /**
     * 填充502背书单的单据号
     * @throws Exception
     */
    public void fillEndoreBillno() throws Exception{
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

    /**
     * 更新背书单fbm_outer表的关系
     * 将单据类型为GA和fj,fk的修改为背书单PK
     * @throws Exception
     */
    public void rebuildOuter() throws Exception{
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

    	//更新付款生成的背书单syscode为arap
    	String endore_syscode = "update fbm_endore set syscode='" + FbmBusConstant.SYSCODE_ARAP + "' where pk_endore in(select pk_busibill from fbm_outer where pk_billtypecode='36GQ')";
    	dao.executeUpdate(endore_syscode);

    	//更新502生成背书单没有汇率等字段的问题
    	String ratesql = "update fbm_endore set brate = (select bbhl from arap_djfb join fbm_outer on fb_oid=pk_outerbill_b where pk_busibill=fbm_endore.pk_endore),frate = (select fbhl from arap_djfb join fbm_outer on fb_oid=pk_outerbill_b where pk_busibill=fbm_endore.pk_endore),moneyb=(select jfbbje from arap_djfb join fbm_outer on fb_oid=pk_outerbill_b where pk_busibill=fbm_endore.pk_endore),moneyf=(select jffbje from arap_djfb join fbm_outer on fb_oid=pk_outerbill_b where pk_busibill=fbm_endore.pk_endore) where syscode = 'ARAP'";
    	dao.executeUpdate(ratesql);
    }

    /**
     * 删除502的默认科目分类定义
     * @throws Exception
     */
    public void delDefaultSubjClass() throws Exception{
    	String delSql = "delete from dap_insubjclass where pk_corp = '0001' and pk_insubjclass in('1001ZZ1000000000D830','1001ZZ1000000000D831','1001ZZ1000000000D833','1001ZZ1000000000D834','1001ZZ1000000000DZUY','1001ZZ1000000000EZDV')";
    	BaseDAO dao = new BaseDAO();
    	dao.executeUpdate(delSql);
    }
}