/**
 *
 */
package nc.bs.fbm.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.gather.FBMPubQueryDAO;
import nc.bs.fbm.pub.InnerAccDestroyCheckImpl;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.logging.Logger;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.tm.cmpbankacc.ITMBankaccService;
import nc.uif.pub.exception.UifException;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-10-15
 *
 */
public class StorageBillService {

	private static final String INITCODE = "FBM003";
	private static final String GROUPCORP = "0001";

	private BaseDAO m_baseDAO = null;

	/**
	 * 操作银行帐户账接口
	 */
	private ITMBankaccService bankTallyService = null;

	public StorageBillService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 * <p>
	 * 退回动作
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-15
	 * @param billVo
	 * @param date
	 * @param pk_user
	 * @return
	 * @throws BusinessException
	 */
	public AggregatedValueObject returnStorageBillVo(AggregatedValueObject billVo,String date,String pk_user) throws BusinessException{
		if(billVo!=null&&billVo.getParentVO()!=null){
			StorageVO storageVo = (StorageVO) billVo.getParentVO();
			storageVo.setVbillstatus(Integer.valueOf(IFBMStatus.RETURNED));
			storageVo.setReturndate(new UFDate(date));
			storageVo.setReturnperson(pk_user);
			StorageVO newVo = updateAndReturnNewSuperVo(storageVo);
			if(newVo!=null)
				billVo.setParentVO(newVo);
		}
		return billVo;
	}

	/**
	 *
	 * <p>
	 * 更新并返回最新ts
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-16
	 * @param storageVo
	 * @return
	 * @throws BusinessException
	 */
	private StorageVO updateAndReturnNewSuperVo(StorageVO storageVo) throws BusinessException{
		HYPubBO bo = new HYPubBO();
		try {
			bo.update(storageVo);
		} catch (UifException e) {
			Logger.error(e.getMessage(),e);
			throw new BusinessException(e.getMessage());
		}
		StorageVO newVo = (StorageVO) bo.queryByPrimaryKey(StorageVO.class, storageVo.getPrimaryKey());
		newVo.setPowerVo(storageVo.getPowerVo());
		return newVo;
	}

	/**
	 *
	 * <p>
	 * 入库
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-16
	 * @param billVo
	 * @param date
	 * @param pk_user
	 * @return
	 * @throws BusinessException
	 * @throws NamingException
	 */
	public AggregatedValueObject inputStorageBillVO(AggregatedValueObject billVo,String date) throws Exception{
		if(billVo!=null&&billVo.getParentVO()!=null){
			StorageVO storageVo = (StorageVO) billVo.getParentVO();
			StoragePowerVO power = storageVo.getPowerVo();
			String pk_cubasdoc = null;
			String inputperson = null;
			if(power!=null){
				pk_cubasdoc =power.getPk_cubasdoc();
				inputperson = power.getCurrUserPK();
			}else{
				inputperson = InvocationInfoProxy.getInstance().getUserCode();
			}

			storageVo.setVbillstatus(Integer.valueOf(IFBMStatus.INPUT_SUCCESS));
			storageVo.setInputdate(UFDate.getDate(date));
			storageVo.setInputperson(inputperson);
			StorageVO newVo = updateAndReturnNewSuperVo(storageVo);
			if(newVo!=null)
				billVo.setParentVO(newVo);

			StorageBVO[] storageBVo = (StorageBVO[]) billVo.getChildrenVO();
			updateGatherKeepUnitByStorage(storageBVo,pk_cubasdoc,true);
			for (int i = 0; i < storageBVo.length; i++) {
				storageBVo[i].setKeepplace(pk_cubasdoc);
			}
		}
		return billVo;
	}

	/**
	 *
	 * <p>
	 * 出库
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-16
	 * @param billVo
	 * @param date
	 * @param pk_user
	 * @return
	 * @throws BusinessException
	 */
	public AggregatedValueObject outputStorageBillVO(AggregatedValueObject billVo,String date) throws Exception{
		if(billVo!=null&&billVo.getParentVO()!=null){
			StorageVO storageVo = (StorageVO) billVo.getParentVO();
			StoragePowerVO power = storageVo.getPowerVo();
			String outputperson = null;
			if(power!=null){
				outputperson = power.getCurrUserPK();
			}else{
				outputperson = InvocationInfoProxy.getInstance().getUserCode();
			}
			storageVo.setVbillstatus(Integer.valueOf(IFBMStatus.OUTPUT_SUCCESS));
			storageVo.setOutputdate(UFDate.getDate(date));
			storageVo.setOutputperson(outputperson);
			StorageVO newVo = updateAndReturnNewSuperVo(storageVo);
			if(newVo!=null)
				billVo.setParentVO(newVo);
			updateKeepUnitByInnerBackOut((HYBillVO) billVo);
		}
		return billVo;
	}

	/**
	 *
	 * <p>
	 * 取消出库
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-16
	 * @param billVo
	 * @return
	 * @throws BusinessException
	 */
	public AggregatedValueObject cancelOutStorageBillVO(AggregatedValueObject billVo,String pk_corp) throws BusinessException{
		if(billVo!=null&&billVo.getParentVO()!=null){
			StorageVO storageVo = (StorageVO) billVo.getParentVO();
			if(storageVo.getKeepcorp().equals(storageVo.getPk_corp())){
				storageVo.setVbillstatus(Integer.valueOf(IFBMStatus.SUBMIT));
			}else{
				storageVo.setVbillstatus(Integer.valueOf(IFBMStatus.CHECKPASS));
			}
			storageVo.setOutputdate(null);
			storageVo.setOutputperson(null);
			updateInputUnitfromInnerKeepInput((HYBillVO) billVo,pk_corp);
			StorageVO newVo = updateAndReturnNewSuperVo(storageVo);
			if(newVo!=null)
				billVo.setParentVO(newVo);
		}
		return billVo;
	}

	/**
	 *
	 * <p>
	 * 取消入库
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-16
	 * @param billVo
	 * @return
	 * @throws BusinessException
	 */
	public AggregatedValueObject cancelInStorageBillVO(AggregatedValueObject billVo) throws BusinessException{
		if(billVo!=null&&billVo.getParentVO()!=null){
			StorageVO storageVo = (StorageVO) billVo.getParentVO();

			if(storageVo.getKeepcorp().equals(storageVo.getPk_corp())){
				storageVo.setVbillstatus(Integer.valueOf(IFBMStatus.SUBMIT));
			}else{
				storageVo.setVbillstatus(Integer.valueOf(IFBMStatus.CHECKPASS));
			}
			storageVo.setInputdate(null);
			storageVo.setInputperson(null);
			StorageVO newVo = updateAndReturnNewSuperVo(storageVo);
			if(newVo!=null)
				billVo.setParentVO(newVo);
			updateKeepUnitByInnerBackOut((HYBillVO) billVo);
		}
		return billVo;
	}

	/**
	 *
	 * <p>
	 * 制证动作,增加参数校验，单位或者中心制证
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-16
	 * @param billVo
	 * @return
	 * @throws BusinessException
	 */
	public AggregatedValueObject makeVoucherStorageBillVO(AggregatedValueObject billVo,String busdate,String userPk) throws BusinessException{
		if(billVo!=null&&billVo.getParentVO()!=null&&billVo.getChildrenVO()!=null){
			StorageVO storageVo = (StorageVO) billVo.getParentVO();
			StoragePowerVO power = storageVo.getPowerVo();
			if(power==null)
				return billVo;

			StorageBVO[] storagebVos = (StorageBVO[]) billVo.getChildrenVO();
			for (int i = 0; i < storagebVos.length; i++) {
				if(!CommonUtil.isNull(storagebVos[i].getAcceptancetype())){
//					if(storagebVos[i].getAcceptancetype().equals(FbmBusConstant.ACCEPTANCE_BANK)){
//						storagebVos[i].setAcceptancetype("银行承兑汇票");
//					}else{
//						storagebVos[i].setAcceptancetype("商业承兑汇票");
//					}
				}
			}

			boolean isGLVoucher = isOwnerApplyBill(power,storageVo);
//			boolean allmakevoucher = queryVoucherParam();
			power.setUnitBill(isGLVoucher);


			if(isGLVoucher){//自己的单据
				storageVo.setUnitvoucherdate(power.getCurrDate());
				storageVo.setUnitvouchermanid(power.getCurrUserPK());
				storageVo.setUnitvoucher(new UFBoolean(true));
				sendDAPMessage(billVo,true);
			}else{//下级单位的
				storageVo.setVvoucherdate(power.getCurrDate());
				storageVo.setVvouchermanid(power.getCurrUserPK());
				sendDAPMessage(billVo,true);
				storageVo.setCentervoucher(new UFBoolean(true));

				//****
//				if(allmakevoucher){//判断参数：FBM003 中心点制证时是否自动制单位的凭证.
//					power.setUnitBill(true);
//					storageVo.setUnitvoucherdate(power.getCurrDate());
//					storageVo.setUnitvouchermanid(power.getCurrUserPK());
//					storageVo.setUnitvoucher(new UFBoolean(true));
//
//					sendDAPMessage(billVo,true);
//				}
				//****
			}

			StorageVO newVo = updateAndReturnNewSuperVo(storageVo);
			if(newVo!=null)
				billVo.setParentVO(newVo);
		}
		return billVo;
	}

	/**
	 * <p>
	 * 判断是中心还是单位；
	 * 中心返回false,单位返回true;
	 * 如果是中心又是单位，则自己作为单位申请的单据返回true，还是处理下级单据false；
	 * <p>
	 * 作者：lpf
	 * 日期：2007-11-30
	 * @param power
	 */
	private boolean isOwnerApplyBill(StoragePowerVO power,StorageVO storageVo) {
		boolean ret=true;
		boolean isCenter = power.isSettleCenter();
		boolean isUnit = power.isSettleUnit();

		String pk_cubasdoc = power.getPk_cubasdoc();
		String pk_billtypecode = storageVo.getPk_billtypecode();
		String keepunit = storageVo.getKeepunit();
		if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERBACK)){
			keepunit = storageVo.getOutputunit();
		}

		if(isCenter&&isUnit){
			if(!pk_cubasdoc.equals(keepunit)){
				ret=false;
			}
		}else{
			if(isCenter){
				ret = false;
			}
		}
		return ret;
	}

	/**
	 *
	 * <p>
	 * TRUE:同时生成;FALSE:分别生成
	 * <p>
	 * 作者：lpf
	 * 日期：2007-11-29
	 * @throws BusinessException
	 */
	public boolean queryVoucherParam() throws BusinessException {
		boolean ret=false;
		UFBoolean boolvalue = UFBoolean.FALSE;
		try {
			boolvalue = OuterProxy.getSysInitQry().getParaBoolean(GROUPCORP, INITCODE);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000029")/*@res "没有查询到对应的关于成员单位凭证生成的集团设置参数,请检查！"*/);
		}
		if(boolvalue!=null)
			ret = boolvalue.booleanValue();
		return ret;
	}

	/**
	 *
	 * <p>
	 * 取消制证，单位先取消，中心再取消
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-16
	 * @param billVo
	 * @return
	 * @throws BusinessException
	 */
	public AggregatedValueObject cancelVoucherStorageBillVO(AggregatedValueObject billVo,String busdate,String userPk) throws BusinessException{
		if(billVo!=null&&billVo.getParentVO()!=null&&billVo.getChildrenVO()!=null){
			StorageVO storageVo = (StorageVO) billVo.getParentVO();
			StoragePowerVO power = storageVo.getPowerVo();
			if(power==null)
				return billVo;

			String pk_corp = power.getPk_corp();
			String rela_corp = power.getRela_corp();
			boolean centervoucher = true;
//			boolean allmakevoucher = queryVoucherParam();
			if(pk_corp.equals(rela_corp)){
				centervoucher = false;
			}

			power.setUnitBill(!centervoucher);
			sendDAPMessage(billVo,false);

			if(centervoucher){
				//判断keepaccount是否为空，如果不为空执行，为空不执行
				if(storageVo.getKeepaccount()!=null)
				{
					new InnerAccDestroyCheckImpl().checkInneraccValidate(storageVo.getKeepaccount());
				}

				storageVo.setVvoucherdate(null);
				storageVo.setVvouchermanid(null);
				storageVo.setCentervoucher(new UFBoolean(false));
//				sendDAPMessage(billVo,false);
				//****
//				if(allmakevoucher){//判断参数：FBM003 中心点取消制证时是否自动取消单位的凭证.
//					storageVo.setUnitvoucherdate(null);
//					storageVo.setUnitvouchermanid(null);
//					storageVo.setUnitvoucher(new UFBoolean(false));
//					sendDAPMessage(billVo,false);
//				}
				//****

			}else{
				storageVo.setUnitvoucherdate(null);
				storageVo.setUnitvouchermanid(null);
				storageVo.setUnitvoucher(new UFBoolean(false));
//				sendDAPMessage(billVo,false);

			}

			StorageVO newVo = updateAndReturnNewSuperVo(storageVo);
			if(newVo!=null)
				billVo.setParentVO(newVo);
		}
		return billVo;
	}

	/**
	 *
	 * <p>
	 * 传平台
	 * addmsg:true(生成凭证) false（删除凭证）
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-24
	 * @param storageVo
	 * @throws BusinessException
	 */
	private void sendDAPMessage(AggregatedValueObject billVo,boolean addmsg) throws BusinessException{
		new StorageVoucherImpl().dapSendMessage(billVo, addmsg);
	}

	/**
	 * <p>
	 * 银行存放审核
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-9
	 * @param storageVo
	 */
	public void updateKeepUnitByStorageApprove(HYBillVO storageBillVo) throws BusinessException{
		if(storageBillVo==null||storageBillVo.getParentVO()==null||storageBillVo.getChildrenVO()==null){
			return;
		}
		StorageVO storageVo = (StorageVO) storageBillVo.getParentVO();
		StorageBVO[] storageBVo = (StorageBVO[]) storageBillVo.getChildrenVO();
		if(storageVo.getVbillstatus().intValue()!=IBillStatus.CHECKPASS){
			return;
		}
		String keepUnit = storageVo.getKeepunit();
		updateGatherKeepUnitByStorage(storageBVo, keepUnit,true);
	}

	/**
	 *
	 * <p>
	 * 银行存放弃审/银行领用审核，更新为持票单位
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-17
	 * @param storageBillVo
	 */
	public void updateInputUnitfromBankKeepUnpprove(HYBillVO storageBillVo) throws BusinessException{
		if(storageBillVo==null||storageBillVo.getParentVO()==null||storageBillVo.getChildrenVO()==null){
			return;
		}
		StorageVO storageVo = (StorageVO) storageBillVo.getParentVO();
		StorageBVO[] storageBVo = (StorageBVO[]) storageBillVo.getChildrenVO();
		if(storageVo.getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_BANKBACK)){
			if(!storageVo.getVbillstatus().equals(Integer.valueOf(IBillStatus.CHECKPASS))){
				return;
			}
		}
		updateGatherKeepUnitByStorage(storageBVo, null,false);
	}

	/**
	 *
	 * <p>
	 * 银行领用弃审，根据存放单存放地点更新
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-17
	 * @param storageBillVo
	 * @return
	 */
//	public void updateInputUnitfromBankBackUnpprove(HYBillVO storageBillVo) throws BusinessException{
//		if(storageBillVo==null||storageBillVo.getParentVO()==null||storageBillVo.getChildrenVO()==null){
//			return;
//		}
//
//		StorageBVO[] storageBVo = (StorageBVO[]) storageBillVo.getChildrenVO();
//		StringBuffer sql = new StringBuffer();
//		sql.append(" update fbm_register cc set keepunit = (select aa.keepunit from " );
//		sql.append(" (select a.pk_storage,a.keepunit,a.ts,b.pk_source from fbm_storage a,fbm_storage_b b ");
//		sql.append(" where a.pk_storage=b.pk_storage  and a.pk_billtypecode = '"+FbmBusConstant.BILLTYPE_BANKKEEP+"') aa,");
//		sql.append(" (select pk_source,max(ts) ts from " );
//		sql.append(" (select a.pk_storage,a.keepunit,a.ts ,b.pk_source from fbm_storage a,fbm_storage_b b " );
//		sql.append(" where a.PK_STORAGE=b.pk_storage  and a.pk_billtypecode = '"+FbmBusConstant.BILLTYPE_BANKKEEP+"') group by pk_source) bb ");
//		sql.append(" where aa.ts=bb.ts and aa.pk_source=bb.pk_source and cc.pk_register=aa.pk_source and cc.pk_register=bb.pk_source )");
//		sql.append(" where cc.pk_register in (");
//		for (int i = 0; i < storageBVo.length; i++) {
//			if (i > 0) {
//				sql.append(",");
//			}
//			sql.append("'" + storageBVo[i].getPk_source() + "'");
//		}
//		sql.append(") ");
//		getBaseDAO().executeUpdate(sql.toString());
//
//	}
	/**
	 *
	 * <p>
	 * 银行领用弃审，根据存放单存放地点更新
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-17
	 * @param storageBillVo
	 * @return
	 */
	/**
	 *
	 * <p>
	 * 银行领用弃审，根据存放单存放地点更新
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-17
	 * @param storageBillVo
	 * @return
	 */
	public void updateInputUnitfromBankBackUnpprove(HYBillVO storageBillVo) throws BusinessException{
		if(storageBillVo==null||storageBillVo.getParentVO()==null||storageBillVo.getChildrenVO()==null){
			return;
		}

		StorageBVO[] storageBVo = (StorageBVO[]) storageBillVo.getChildrenVO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_source,keepunit from fbm_storage_b left join fbm_storage ");
		sql.append(" on(fbm_storage.pk_storage=fbm_storage_b.pk_storage) where vbillstatus="+String.valueOf(IBillStatus.CHECKPASS)+" and pk_source in(");

		for (int i = 0; i < storageBVo.length; i++) {
			if (i > 0) {
				sql.append(",");
			}
			sql.append("'" + storageBVo[i].getPk_source() + "'");
		}
		sql.append(") ");
		FBMPubQueryDAO query = new FBMPubQueryDAO();
		java.util.List list = query.queryArrayListData(sql.toString());
		Map map = new HashMap();
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i)!=null){
					Map<String, String> listmap = (Map<String, String>) list.get(i);
					String pk_source = listmap.get(StorageBVO.PK_SOURCE);
					String pk_keepunit = listmap.get(StorageVO.KEEPUNIT);
					if(!CommonUtil.isNull(pk_source)&&!CommonUtil.isNull(pk_keepunit)){
						map.put(pk_source, pk_keepunit);
					}
				}
			}
		}
		if(map.size()>0){
			for (int i = 0; i < storageBVo.length; i++) {
				sql = new StringBuffer();
				String pk_source = storageBVo[i].getPk_source();
				String keepUnit = (String)map.get(pk_source);
				if(map.get(pk_source)!=null&&!CommonUtil.isNull(keepUnit)){
					sql.append(" update fbm_register set keepunit='"+keepUnit+"' where pk_register='"+pk_source+"' ");
					getBaseDAO().executeUpdate(sql.toString());
				}
			}
		}

	}

	/**
	 *
	 * <p>
	 * 中心票據存放取消入库時調用方法(更新为持票单位)
	 * 中心票据领用出库
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-17
	 * @param storageBillVo
	 */
	public void updateKeepUnitByInnerBackOut(HYBillVO storageBillVo) throws BusinessException{
		if(storageBillVo==null||storageBillVo.getParentVO()==null||storageBillVo.getChildrenVO()==null){
			return;
		}
		StorageBVO[] storageBVo = (StorageBVO[]) storageBillVo.getChildrenVO();
		updateGatherKeepUnitByStorage(storageBVo, null,false);
	}

	/**
	 *
	 * <p>
	 * 中心领用取消出库
	 * 中心存放入库
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-17
	 * @param storageBillVo
	 * @return
	 * @throws BusinessException
	 * @throws BusinessException
	 */
	public void updateInputUnitfromInnerKeepInput(HYBillVO storageBillVo,String corpPk) throws BusinessException {
		if(storageBillVo==null||storageBillVo.getParentVO()==null||storageBillVo.getChildrenVO()==null){
			return;
		}
		StorageBVO[] storageBVo = (StorageBVO[]) storageBillVo.getChildrenVO();
		String pk_cubasdoc = getCorpCubasdoc(corpPk);
		updateGatherKeepUnitByStorage(storageBVo,pk_cubasdoc,true);
	}

	/**
	 * <p>
	 * 更新收票登記的存放地點
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-17
	 * @param storageBVo
	 * @param keepUnit
	 */
	private void updateGatherKeepUnitByStorage(StorageBVO[] storageBVo,	String keepUnit, boolean isCommit) throws BusinessException{
		StringBuffer sql = new StringBuffer();
		if (isCommit) {
			sql.append("update fbm_register set keepunit='" + keepUnit + "' where pk_register in (");
		} else {
			sql.append("update fbm_register set keepunit=holdunit where pk_register in (");
		}
		ArrayList<String> list = new ArrayList<String>();
		String[] lockPks = null;
		for (int i = 0; i < storageBVo.length; i++) {
			if (i > 0) {
				sql.append(",");
			}
			sql.append("'" + storageBVo[i].getPk_source() + "'");
			list.add(storageBVo[i].getPk_source());
		}
		if(list.size()>0){
			lockPks = new String[list.size()];
			list.toArray(lockPks);
		}

		sql.append(") and isnull(dr,0)=0 ");
		getBaseDAO().executeUpdate(sql.toString());
	}

	/**
	 *
	 * <p>
	 * 根据公司编码取出对应的内部客商
	 * <p>
	 * 作者：lpf
	 * 日期：2007-8-15
	 */
	public String getCorpCubasdoc(String pk_corp){
		String strWhere=" bd_cubasdoc.pk_corp1 = '" + pk_corp + "' ";
		CustBasVO[] custbasVos = null;
		try {
			custbasVos = OuterProxy.getCustManDocQuery().queryCustBasicInfo(strWhere, pk_corp);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
		}
		if(custbasVos==null||custbasVos.length==0)
			return null;
		return custbasVos[0].getPk_cubasdoc();
	}

	/**
	 *
	 * <p>
	 * 审核清空退回条件
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-18
	 * @param storageBillVo
	 * @return
	 * @throws BusinessException
	 */
	public AggregatedValueObject updateReturnInfoForUnapprove(AggregatedValueObject storageBillVo) throws BusinessException {
		if(storageBillVo!=null&&storageBillVo.getParentVO()!=null){
			StorageVO storageVo = (StorageVO) storageBillVo.getParentVO();
			if(storageVo.getReturnperson()!=null){
				storageVo.setReturndate(null);
				storageVo.setReturnperson(null);
				storageVo.setReturnreason(null);
				storageVo = updateAndReturnNewSuperVo(storageVo);
				storageBillVo.setParentVO(storageVo);
			}
		}
		return storageBillVo;
	}

	/**
	 *
	 * <p>
	 * 后台推式生成内部领用单方法,提供给票据调剂
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-25
	 * @param storageBillVo
	 * @throws BusinessException
	 */
	public void saveStorageHYBillVO(HYBillVO storageBillVo) throws BusinessException{
		if (storageBillVo == null || storageBillVo.getParentVO() == null
				|| storageBillVo.getChildrenVO() == null
				|| storageBillVo.getChildrenVO().length == 0) {
			return;
		}
		try {
			HYPubBO pubbo = new HYPubBO();
			AggregatedValueObject vo = pubbo.saveBill(storageBillVo);
			doBillAction((HYBillVO)vo,FbmActionConstant.SAVE);
//			doBillAction((HYBillVO)vo,FbmActionConstant.AUDIT);
//			StorageVO headVo = (StorageVO) vo.getParentVO();
//			headVo.setVapproveid(headVo.getVoperatorid());
//			headVo.setDapprovedate(headVo.getDoperatedate());
//			headVo.setVbillstatus(Integer.valueOf(IFBMStatus.OUTPUT_SUCCESS));
//			headVo.setOutputdate(headVo.getDoperatedate());
//			headVo.setOutputperson(headVo.getVoperatorid());
//			vo.setParentVO(headVo);
//
//			doBillAction((HYBillVO)vo,FbmActionConstant.OUTPUT_SUCCESS);
//
//			pubbo.update(headVo);
//
//			//added by wes to update keep unit
//			updateKeepUnitByInnerBackOut(storageBillVo);

		} catch (Exception e) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000169")/*@res "推式生成内部领用单错误"*/+e.getMessage());
		}
	}

	private void doBillAction(HYBillVO storageBillVo, String actioncode) throws BusinessException{
//		ActionParamVO[] params = DefaultParamAdapter.changeInnerBack2Param(storageBillVo,actioncode);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERBACK, actioncode).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERBACK, actioncode).doAction(storageBillVo, actioncode,false);

	}


	/**
	 * <p>
	 * 审批流定义单据项目作为审核条件，需要重新填充VO
	 * <p>
	 * 作者：lpf
	 * 日期：2007-12-24
	 * @param vo
	 * @param pk
	 */
	public PfParameterVO filterVOForApproveFlow(PfParameterVO vo, String pk) {
		try {
			AggregatedValueObject billVo = queryStorageBillVobyPK(vo.m_preValueVo.getParentVO(),pk);
			if(billVo!=null){
				vo.m_preValueVo = billVo;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return vo;
	}

	/**
	 * <p>
	 * 根据PK查询最新数据
	 * <p>
	 * 作者：lpf
	 * 日期：2007-12-25
	 * @param pk
	 * @return
	 * @throws UifException
	 * @throws BusinessException
	 */
	public AggregatedValueObject queryStorageBillVobyPK(CircularlyAccessibleValueObject pvo,String pk) throws BusinessException {
		if(CommonUtil.isNull(pk)){
			return null;
		}

		HYPubBO bo = new HYPubBO();
		SuperVO headVo = null;
		
		headVo = bo.queryByPrimaryKey(StorageVO.class, pk);
		
		
		HYBillVO vo = new HYBillVO();
		if(pvo==null){
			vo.setParentVO(headVo);
		}else
		{
			vo.setParentVO(pvo);
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_storage_b,fbm_baseinfo.fbmbilltype acceptancetype,fbm_baseinfo.fbmbillno,fbm_register.pk_baseinfo,fbm_storage_b.pk_source,fbm_baseinfo.payunit,");
		sql.append(" fbm_baseinfo.paybankacc,fbm_baseinfo.receiveunit,fbm_baseinfo.receivebankacc,fbm_baseinfo.pk_curr,");
		sql.append(" fbm_baseinfo.moneyy,fbm_baseinfo.invoicedate,fbm_register.gatherdate,fbm_baseinfo.enddate,fbm_baseinfo.invoiceunit,fbm_register.keepunit,");
		sql.append(" fbm_register.paybillunit,fbm_storage_b.isoutdisaccount,fbm_register.moneyb,fbm_register.brate as moneybrate from fbm_storage_b left join fbm_register ");
		sql.append(" on(fbm_storage_b.pk_source=fbm_register.pk_register) join fbm_baseinfo on (fbm_register.pk_baseinfo=fbm_baseinfo.pk_baseinfo) where ");
		sql.append(" fbm_storage_b.pk_storage='"+pk+"' ");
		FBMPubQueryDAO dao = new FBMPubQueryDAO();
		SuperVO[] queryVos = dao.queryData(sql.toString(), StorageBVO.class);
		vo.setChildrenVO(queryVos);
		return vo;
	}

	private BaseDAO getBaseDAO() {
		if (m_baseDAO == null) {
			m_baseDAO = new BaseDAO();
		}
		return m_baseDAO;
	}


}