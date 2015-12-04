package nc.impl.fbm.relief;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nc.bs.fbm.accdetail.StorageVOToAccDetail;
import nc.bs.fbm.gather.GatherBillService;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.relief.ReliefUtil;
import nc.bs.fbm.relief.action.SaveRelief;
import nc.bs.fbm.storage.StorageAccountBO;
import nc.bs.fbm.storage.StorageBillService;
import nc.bs.fbm.storage.StorageUtil;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.trade.business.HYPubBO;
import nc.bs.trade.comsave.BillSave;
import nc.itf.fbm.relief.IReliefService;
import nc.itf.fts.pub.ICommon;
import nc.uif.pub.exception.UifException;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

/**
 * ]
 *
 * 类功能说明： 票据调剂服务实现类 日期：2007-12-1 程序员： wues
 *
 */
public class ReliefServiceImpl implements IReliefService {

	private GatherBillService serv = null;

	/**
	 * 过滤条件，把收票登记的最新动作限定在某个范围内
	 */
	private String whereClause = "(select a.pk_source from fbm_action a  where a.serialnum = (select max(b.serialnum) from fbm_action b where b.pk_source =a.pk_source) and a.endstatus='"
			+ FbmStatusConstant.HAS_RELIEF_KEEP + "')";

	/**
	 * 根据pk_relief取到所有未曾出库的调剂票据返回 自己的票：返回调剂票据的RegisterVO 他人的票：返回新生成的收票登记单VO
	 *
	 * @param pk_relief
	 * @return
	 * @throws BusinessException
	 */
	public RegisterVO[] getRegisterVOByRelief(String pk_relief)
			throws BusinessException {
		// (1)根据pk_relief得到ActionParamVO，以利用现有拆分方法,在此同时也得到了调剂的票
		BusiActionParamVO<ReliefVO>[] params = getParams(pk_relief);
		// (2)将ActionParam进行拆分,区分出自己的票和他人调剂过来的票，同时缓存到HashMap中
		List<BusiActionParamVO>[] splitArr = ReliefUtil.splitParams(params);
		if (null == splitArr) {
			return null;
		}
		// (3)根据pk_relief得到调剂他人的未出库的RegisterVO
		RegisterVO[] otherRegisters = dealOther(pk_relief);
		// (4)根据拆分的List得到转化的未出库的RegsiterVO
		RegisterVO[] selfRegisters = dealSelf(splitArr[0]);
		//组合后返回
		RegisterVO[] allRegs = getAllVOS(selfRegisters, otherRegisters);

		return allRegs;
	}

	/**
	 * 将两个List合并后返回
	 *
	 * @param selfRegisters
	 * @param otherRegisters
	 * @return
	 */
	private RegisterVO[] getAllVOS(RegisterVO[] selfRegisters,
			RegisterVO[] otherRegisters) {
		List<RegisterVO> list = new ArrayList<RegisterVO>();

		list.addAll(getList(selfRegisters));
		list.addAll(getList(otherRegisters));

		return (RegisterVO[])list.toArray(new RegisterVO[0]);
	}

	private List<RegisterVO> getList(RegisterVO[] regs) {
		List<RegisterVO> list = new ArrayList<RegisterVO>();
		for (int i = 0; regs != null && i < regs.length; i++) {
			regs[i].setRegisterstatus(FbmStatusConstant.HAS_INNER_KEEP);
			list.add(regs[i]);
		}
		return list;
	}

	/**
	 * 取其他人的未出库的票
	 *
	 * @param pk_relief
	 * @return
	 * @throws BusinessException
	 */
	private RegisterVO[] dealOther(String pk_relief) throws BusinessException {

		String condition = new StringBuffer().append(
				" fbm_register.pk_register in ").append(whereClause).append(
				" and fbm_register.pk_source = '").append(pk_relief)
				.append("'").toString();

		GatherBillService serv = getGatherServ();

		RegisterVO[] vos = (RegisterVO[]) serv
				.queryRegisterVOSByCondition(condition);

		return vos;
	}

	/**
	 * 取其他人的未出库的票
	 *
	 * @param pk_relief
	 * @return
	 * @throws BusinessException
	 */
	private RegisterVO[] dealSelf(List<BusiActionParamVO> selfList)
			throws BusinessException {
		String pks = getSelfPKs(selfList);// 将所有pk拼接成一个字符串
		if (null == pks || "".equals(pks.trim())) {
			return null;
		}
		
		String sql =  "(select a.pk_source from fbm_action a  where" +
				"  a.serialnum = (select max(b.serialnum) from fbm_action b " +
				" where b.pk_source =a.pk_source and b.pk_source in " + pks +
				" and b.endstatus='" + FbmStatusConstant.HAS_RELIEF_KEEP + "'))";
//		String condition = new StringBuffer().append(
//		" fbm_register.pk_register in ").append(whereClause).append(
//		" and fbm_register.pk_register in").append(pks)
//		.toString();
		String condition = new StringBuffer().append(
				" fbm_register.pk_register in ").append(sql).append(
				" and fbm_register.pk_register in").append(pks)
				.toString();

		GatherBillService serv = getGatherServ();

		RegisterVO[] vos = (RegisterVO[]) serv
				.queryRegisterVOSByCondition(condition);
		return vos;
	}


	/**
	 * 得到自己的票的Pk列表 将票据pk拼成in条件从数据库中一次取出，避免多次访问数据库
	 *
	 * @param selfList
	 * @return
	 */
	private String getSelfPKs(List<BusiActionParamVO> selfList) {
		if (null == selfList || selfList.size() == 0) {
			return null;
		}
		StringBuffer buf = new StringBuffer().append("(");
		Iterator<BusiActionParamVO> it = selfList.iterator();
		while (it.hasNext()) {
			BusiActionParamVO vo = it.next();
			buf.append("'").append(vo.getPk_source()).append("',");
		}
		int len = buf.length();
		buf.deleteCharAt(len - 1);
		buf.append(")");
		return buf.toString();
	}

	private BusiActionParamVO<ReliefVO>[] getParams(String pk_relief)
			throws BusinessException {
		// (1)根据调剂单PK取相应的聚合VO
		HYBillVO vo = getBillVOByRelief(pk_relief);
		// (2) 将vo转换成ActionParamVO以方便用现有方法进行拆分
		BusiActionParamVO<ReliefVO>[] params = new SaveRelief().buildParam(vo, null);
		if (null == params || params.length == 0) {
			Logger.error("将聚合VO转成ActionParamVO时异常(HYBillVO->ActionParamVO[])");
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045","UPP36201045-000010")/* @res"参数转化异常(HYBillVO->ActionParamVO[])"*/);
		}
		return params;
	}

	/**
	 * 根据给定的调剂单pk取得相应的聚合VO
	 *
	 * @param pk_relief
	 * @return
	 * @throws BusinessException
	 */
	private HYBillVO getBillVOByRelief(String pk_relief)
			throws BusinessException {
		HYBillVO vo = null;
		try {
			vo = (HYBillVO) new HYPubBO()
					.queryBillVOByPrimaryKey(
							new String[] { HYBillVO.class.getName(),
									ReliefVO.class.getName(),
									ReliefBVO.class.getName() }, pk_relief);
		} catch (UifException e) {
			Logger.error(e);
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045","UPP36201045-000011")/* @res"根据票据调剂单PK获得聚合VO异常"*/);
		} catch (Exception e) {
			Logger.error(e);
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045","UPP36201045-000011")/* @res"根据票据调剂单PK获得聚合VO异常"*/);
		}
		if (vo == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045","UPP36201045-000012")/* @res"数据错误，根据给定的调剂单PK无法取到相应的聚合VO"*/);
		}
		return vo;
	}

	private GatherBillService getGatherServ() throws BusinessException {
		if (serv == null) {
			serv = new GatherBillService();
		}
		return serv;
	}

	public String autoInnerBack(ReliefVO reliefVO,UFDate outputDate,String tallyman,UFDate tallydate) throws BusinessException {
		HYBillVO storageBillVO = new HYBillVO();
		StorageVO storageHeadVo = new StorageVO();

		storageHeadVo.setPk_billtypecode(FbmBusConstant.BILLTYPE_INNERBACK);
		storageHeadVo.setVoperatorid(reliefVO.getVoperatorid());
		storageHeadVo.setDoperatedate(reliefVO.getDoperatedate());
		storageHeadVo.setPk_corp(reliefVO.getPk_corp());
		storageHeadVo.setOutputperson(reliefVO.getVoperatorid());
		storageHeadVo.setOutputdate(reliefVO.getDoperatedate());
		storageHeadVo.setVapproveid(reliefVO.getVapproveid());
		storageHeadVo.setDapprovedate(reliefVO.getDapprovedate());
		storageHeadVo.setOutputunit(reliefVO.getReliefunit());
		storageHeadVo.setVbillstatus(IFBMStatus.OUTPUT_SUCCESS);
		storageHeadVo.setPk_currtype(reliefVO.getPk_currtype());
		storageHeadVo.setUnittally(new UFBoolean(false));
		storageHeadVo.setCentervoucher(new UFBoolean(false));
		storageHeadVo.setUnitvoucher(new UFBoolean(false));
		storageHeadVo.setKeepcorp(reliefVO.getReliefcorp());
		storageHeadVo.setKeepaccount(reliefVO.getInneracc());
		storageHeadVo.setSummoneyy(reliefVO.getSummoney());
		storageHeadVo.setInputtype(FbmBusConstant.KEEP_TYPE_RELIEF);
		
		ICommon commonInterface = (ICommon)NCLocator.getInstance().lookup(ICommon.class);
		UFDate dealdate = commonInterface.getAutoProcessDate(reliefVO.getPk_corp());
		if(dealdate!=null){
			storageHeadVo.setDealdate(dealdate);
			String businessno = commonInterface.getBusinessNo("36LW", reliefVO.getReliefcorp(), null, null);
			storageHeadVo.setBusinessno(businessno);
		}
		String billno = OuterProxy.getBillCodeRuleService().getBillCode_RequiresNew(
				FbmBusConstant.BILLTYPE_INNERBACK, reliefVO.getPk_corp(),
				null, null);
		storageHeadVo.setVbillno(billno);

		RegisterVO[] registerVO =  getRegisterVOByRelief(reliefVO.getPrimaryKey());
		ArrayList<StorageBVO> alBVos = new ArrayList<StorageBVO>();
		for (int i = 0; i < registerVO.length; i++) {
			StorageBVO bvo = new StorageBVO();
			bvo.setPk_source(registerVO[i].getPrimaryKey());
			bvo.setPk_baseinfo(registerVO[i].getPk_baseinfo());
			if (registerVO[i].getSfflag().booleanValue()
					&&(registerVO[i].getRegisterstatus().equals(FbmStatusConstant.HAS_INNER_KEEP)
					||registerVO[i].getRegisterstatus().equals(FbmStatusConstant.HAS_RELIEF_KEEP))
					) {
				alBVos.add(bvo);
			}
		}
		StorageBVO[] storageBVos = new StorageBVO[alBVos.size()];
		alBVos.toArray(storageBVos);

		storageBillVO.setParentVO(storageHeadVo);
		storageBillVO.setChildrenVO(storageBVos);
		//保存单据
		BillSave billsave = new BillSave();
		List ret = billsave.saveBill(storageBillVO);

		//保存领用单
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERBACK, FbmActionConstant.SAVE).doAction(storageBillVO, FbmActionConstant.SAVE,true);
		//审核领用单
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERBACK, FbmActionConstant.AUDIT).doAction(storageBillVO, FbmActionConstant.AUDIT,true);
		//出库领用单
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERBACK, FbmActionConstant.OUTPUT_SUCCESS).doAction(storageBillVO, FbmActionConstant.OUTPUT_SUCCESS,true);


		try {
			new StorageBillService().outputStorageBillVO(storageBillVO,String.valueOf(outputDate));
		} catch (Exception e) {
			throw new BusinessException(e);
		}

		//维护内部账户帐和银行账户帐
		StorageAccountBO accBO = new StorageAccountBO();
		accBO.account4InnerBack((HYBillVO)ret.get(1), UFBoolean.FALSE, tallyman,tallydate);
		
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000048")/*@res "已自动生成单位已确认的内部领用单"*/+storageHeadVo.getVbillno();
	}


}