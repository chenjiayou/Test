package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.fbm.gather.GatherBillService;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 ***********************************************************
 * 日期：2008-3-17
 * 程序员:吴二山
 * 功能：中心业务的统一处理类入口
 ***********************************************************
 */
public abstract class AbstractCenterOperation extends AbstractCompiler2 {

	private java.util.Hashtable<String, Object> m_methodReturnHas = new java.util.Hashtable<String, Object>();
	private Hashtable<String, Object> m_keyHas = null;

	private CommonDAO dao = new CommonDAO();

	public AbstractCenterOperation() {
		super();
	}
	/**
	 *
	 * 单据保存操作：统管业务
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	protected Object saveUniStorage(PfParameterVO vo) throws BusinessException {
		Class cls = vo.m_preValueVo.getParentVO().getClass();
		SuperVO headVo = (SuperVO) vo.m_preValueVo.getParentVO();
		// 取动作编码
		if (!isNew(headVo)) {// 编辑保存
			// 先查询老的VO
			SuperVO oldVO = (SuperVO) getSuperVO(cls, headVo.getPrimaryKey());
			if (oldVO.getAttributeValue(DiscountVO.PK_SOURCE).equals(
					headVo.getAttributeValue(DiscountVO.PK_SOURCE))) {// 修改后的票和原来的票一样
				return savePrivacy(vo);// 直接进行保存即可
			}
			// 否则，都将原来的删除
			vo.m_preValueVo.setParentVO(oldVO);// 重新查询后赋值
			// 删除老的贴现申请单
			delPrivacy(vo);
			// 删除原来生成的收票登记单
			if (FbmBusConstant.BILL_UNISTORAGE.equals(oldVO
					.getAttributeValue(DiscountVO.OPBILLTYPE))) {
				doDelNewRegister(oldVO);
			}
		}
		vo.m_preValueVo.setParentVO(headVo);// 重新设值
		// 将原来的票据改为已中心使用,同时将headVO引用的票设置为新生成的票
		doCenterUse(headVo);
		// 执行保存
		return savePrivacy(vo);
	}

	/**
	 * 单据的删除操作：统管业务
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	protected Object delUniStorage(PfParameterVO vo) throws BusinessException {
		//首先删除相关的单据
		Object retObj = delPrivacy(vo);
		//同时删除新生成的收票登记单
		doDelNewRegister((SuperVO)vo.m_preValueVo.getParentVO());
		return retObj;
	}

	/**
	 *
	 * 单据的删除操作：私有业务
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	protected Object delPrivacy(PfParameterVO vo) throws BusinessException {
		Object retObj = null;
		retObj = runClass("nc.bs.trade.comdelete.BillDelete", "deleteBill",
				"nc.vo.pub.AggregatedValueObject:"
						+ vo.m_preValueVo.getParentVO().getAttributeValue(
								"pk_billtypecode"), vo, m_keyHas,
				m_methodReturnHas);
		if (retObj instanceof HYBillVO) {
			doAction((SuperVO) vo.m_preValueVo.getParentVO(),
					FbmActionConstant.DELETE);
		}
		if (retObj != null) {
			m_methodReturnHas.put("deleteBill", retObj);
		}
		return retObj;
	}

	/**
	 *
	 * 单据的保存操作，包含新增保存和修改保存：私有业务
	 * 要判断是新增保存还是修改保存,同时判断是否为把统管变为私有
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	protected Object savePrivacy(PfParameterVO vo) throws BusinessException {
		try {
				//修改一个贴现申请，当点修改时，由统管变为私有时的处理。
				Class cls = vo.m_preValueVo.getParentVO().getClass();
				SuperVO headVo = (SuperVO) vo.m_preValueVo.getParentVO();
				if (!isNew(headVo)) {// 编辑保存
					// 先查询老的VO
					SuperVO oldVO = (SuperVO) getSuperVO(cls, headVo.getPrimaryKey());
					if (!oldVO.getAttributeValue(DiscountVO.PK_SOURCE).equals(
							headVo.getAttributeValue(DiscountVO.PK_SOURCE))) {// 修改后的票和原来的票一样

						if (FbmBusConstant.BILL_UNISTORAGE.equals(oldVO.getAttributeValue(DiscountVO.OPBILLTYPE))) {
							// 否则，都将原来的删除
							vo.m_preValueVo.setParentVO(oldVO);// 重新查询后赋值
							// 删除老的贴现申请单
							delPrivacy(vo);
							// 删除原来生成的收票登记单
							doDelNewRegister(oldVO);
							vo.m_preValueVo.setParentVO(headVo);// 重新设值
						}
					}
				}
				//////////////////////////////////////////////////////////


			Object retObj = null;
			//没有必要增加票据是否已经被贴现或者托收的校验，已经通过票据的Action校验控制了仅有已登记的可以做下步操作
//			String checkClass = getCheckClass((SuperVO) vo.m_preValueVo.getParentVO());
//			if (null != checkClass && !"".equals(checkClass.trim())) {
//				// 保存前校验
//				runClass(checkClass ,
//						"beforeSaveBill", "nc.vo.pub.AggregatedValueObject:01", vo,
//						m_keyHas, m_methodReturnHas);
//			}
			// 执行保存动作
			retObj = runClass("nc.bs.trade.comsave.BillSave", "saveBill",
					"nc.vo.pub.AggregatedValueObject:"
							+ vo.m_preValueVo.getParentVO().getAttributeValue(
									"pk_billtypecode"), vo, m_keyHas,
					m_methodReturnHas);
			// 进行事后动作处理
			doAction((SuperVO) ((HYBillVO) ((ArrayList) retObj).get(1))
					.getParentVO(), getActionCode((SuperVO)vo.m_preValueVo.getParentVO()));
			setSaveValueParam(vo, retObj);
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

//	/**
//	 *
//	 * 根据单据类型取得在动作处理前的校验类
//	 *
//	 * @param superVO
//	 * @return
//	 */
//	private String getCheckClass(SuperVO superVO) {
//		String pk_billtypecode = (String) superVO
//				.getAttributeValue("pk_billtypecode");
//		if (FbmBusConstant.BILLTYPE_DISCOUNT_APP.equals(pk_billtypecode)
//				|| FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT
//						.equals(pk_billtypecode)) {
//			return DiscountBusCheck.class.getName();
//		}
//		if (FbmBusConstant.BILLTYPE_COLLECTION_UNIT.equals(pk_billtypecode)) {
//			return ConsignBankBusCheck.class.getName();
//		}
//		return null;
//	}

	/**
	 *
	 * 脚本需要的方法，进行has的设值
	 *
	 * @param vo
	 * @param retObj
	 */
	private void setSaveValueParam(PfParameterVO vo, Object retObj) {
		setParameter("billVo", retObj);
		setParameter("userObj", vo.m_userObj);
		if (retObj != null) {
			m_methodReturnHas.put("saveBill", retObj);
		}
	}

	/**
	 * 取得动作类型
	 *
	 * @param superVO
	 * @return
	 */
	private String getActionCode(SuperVO superVO) {
		if (isNew(superVO)) {
			return FbmActionConstant.SAVE;
		} else {
			return FbmActionConstant.EDITSAVE;
		}
	}

	/*
	 * 备注：设置脚本变量的HAS
	 */
	private void setParameter(String key, Object val) {
		if (m_keyHas == null) {
			m_keyHas = new Hashtable<String, Object>();
		}
		if (val != null) {
			m_keyHas.put(key, val);
		}
	}

	/**
	 * 根据单据类型和动作编码进行Action处理
	 *
	 * @param billType
	 * @param actionCode
	 * @throws BusinessException
	 */
	private void doAction(SuperVO superVO, String actionCode)
			throws BusinessException {
		BusiActionFactory.getInstance().createActionClass(
				(String) superVO.getAttributeValue("pk_billtypecode"),
				actionCode).doAction(superVO, actionCode, false);
	}

	/**
	 * 根据VO的PK得到SuperVO
	 *
	 * @param pk_id
	 * @return
	 * @throws BusinessException
	 */
	private SuperVO getSuperVO(Class cls, String pk_id)
			throws BusinessException {
		SuperVO vo = (SuperVO) dao.getBaseDAO().retrieveByPK(cls, pk_id);
		if (vo == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000172")/*@res "根据PK无法取得相关收票，请确认数据的正确性或刷新重试。"*/);
		}
		return vo;
	}

	/**
	 * 判断是新增保存还是修改保存
	 *
	 * @param superVO
	 * @return
	 */
	private boolean isNew(SuperVO superVO) {
		if (null != superVO.getPrimaryKey()
				&& 0 != superVO.getPrimaryKey().trim().length()) {
			return false;
		}
		return true;
	}

	/*
	 * 备注：平台编写原始脚本
	 */
	public String getCodeRemark() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000173")/*@res "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n	Object retObj=null;\n	return retObj;\n"*/;
	}

	/**
	 * 将原来用到的收票登记VO设置为中心使用，同时生成一张新的收票登记单
	 *
	 * @param headVo
	 * @throws BusinessException
	 */
	private void doCenterUse(SuperVO headVo) throws BusinessException {
		RegisterVO register = (RegisterVO) getSuperVO(RegisterVO.class,
				(String) headVo.getAttributeValue("pk_source"));

		//子类实现此方法根据自己需要重新给registervo赋值
		replaceRegisterValue(register,headVo);
		//将老票作中心使用
		doAction(register, FbmActionConstant.CENTERUSE);
		// 生成新的票据单
		RegisterVO newRegVO = changeRegister(register, headVo);
		HYBillVO billVO = new HYBillVO();
		billVO.setParentVO(newRegVO);
		GatherBillService service = new GatherBillService();
		String[] pk_arry = service.saveRegisterVos(new HYBillVO[] { billVO });
		// 换票的过程,将新生成的票据主键放到原VO中。
		if (pk_arry.length > 0) {
			headVo.setAttributeValue("pk_source", pk_arry[0]);
		}
	}

	/**
	 *
	 * 将register重新设值为新的值，生成一张新的收票登记单
	 * superVO是具体做业务时的VO（贴现VO，托收VO，背书VO。。。）
	 * @param register
	 * @param superVO
	 */
	protected RegisterVO changeRegister(RegisterVO register, SuperVO superVO) throws BusinessException{return null;}

	/**
	 *
	 * 删除新生成的收票登记单
	 *
	 * @param superVO
	 * @throws BusinessException
	 */
	private void doDelNewRegister(SuperVO superVO) throws BusinessException {
		// 得到新的RegisterVO
		RegisterVO register = (RegisterVO) getSuperVO(RegisterVO.class,
				(String) superVO.getAttributeValue("pk_source"));

		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER,FbmActionConstant.CANCELAUDIT).doAction(register,FbmActionConstant.CANCELAUDIT,true);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, FbmActionConstant.DELETE).doAction(register, FbmActionConstant.DELETE,true);
		dao.getBaseDAO().deleteByPK(register.getClass(), register.getPrimaryKey());

		//GatherBillService service = new GatherBillService();

		//service.de

		//.deleteRegisterVosForOuterSys(register.getPk_source());// 根据pk_source删除新生成的收票登记单



		register.setPrimaryKey(register.getPk_source());// 重新设置成为主键register

		doAction(register, FbmActionConstant.CANCELCENTERUSER);
	}

	/**
	 * <p>
	 *  子类有选择的实现此方法。此方法的作用是将RegisterVO中的Gatherdate,Dapprovedate用SuperVO中的值替换掉。
	 *  此方法只能统管业务会用到，背书办理、银行托收、贴现办理。
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-4-8
	 * @param regvo
	 * @param supervo
	 */
	protected void replaceRegisterValue(RegisterVO regvo,SuperVO supervo){}

}