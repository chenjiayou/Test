package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.fbm.gather.GatherBillService;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.itf.uap.bd.cust.ICustBasDocQuery;
import nc.vo.bd.b08.CubasdocVO;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.uapbd.bankaccount.BankaccbasVO;

/**
 * 备注：新收票登记的保存 单据动作执行中的动态执行类的动态执行类。
 * 
 * 创建日期：(2007-8-9)
 * 
 * @author 平台脚本生成
 */
public class N_36GA_SAVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GA_SAVE 构造子注解。
	 */
	public N_36GA_SAVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			Object retObj = null;
			RegisterVO registerVo = (RegisterVO) vo.m_preValueVo.getParentVO();
			checkSave(registerVo); // 前台校验转到后台校验。减少前台远程调用次数。
			UFDate currentOperDate = new UFDate(vo.m_currentDate);
			String receivebank = registerVo.getReceivebank();
			String paybank = registerVo.getPaybank();

			HYPubBO pubbo = new HYPubBO();
			BankaccbasVO bankvo[] = (BankaccbasVO[]) pubbo.queryByCondition(BankaccbasVO.class, "pk_bankaccbas = '"
					+ receivebank
					+ "' or pk_bankaccbas = '"
					+ paybank
					+ "'");
			if (bankvo != null && bankvo.length > 0) {
				for (int i = 0; i < bankvo.length; i++) {
					BankaccbasVO bankBasVO = bankvo[i];
					UFDate opendate = bankBasVO.getAccopendate();
					if (currentOperDate.before(opendate)) {
						throw new BusinessException(
								nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000020")/*
																														 * @res
																														 * "收款银行账户或者付款银行账户开户日期晚于业务日期，不允许保存。"
																														 */);
					}
				}
			}

			// 检查是否为新增收票
			boolean isNew = isNew(registerVo);

			GatherBillService gatherDao = new GatherBillService();

			gatherDao.saveBaseInfo(registerVo);

			vo.m_preValueVo.setParentVO(registerVo);

			retObj = runClass("nc.bs.trade.comsave.BillSave", "saveBill", "nc.vo.pub.AggregatedValueObject:36GA", vo, m_keyHas, m_methodReturnHas);// 36GA是单据类型
			if (retObj != null) {
				m_methodReturnHas.put("saveBill", retObj);
			}
			HYBillVO retBillVO = (HYBillVO) ((ArrayList) retObj).get(1);
			String actioncode = null;
			if (isNew) {
				actioncode = FbmActionConstant.SAVE;
			} else {
				actioncode = FbmActionConstant.EDITSAVE;
			}
			// ActionParamVO[] params =
			// DefaultParamAdapter.changeGather2Param(retBillVO,actioncode);
			// FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER,
			// actioncode).doAction(params);
			RegisterVO regVO = (RegisterVO) retBillVO.getParentVO();
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, actioncode).doAction(regVO, actioncode, false);

			gatherDao.deleteUnUsedBaseInfo();
			regVO = (RegisterVO)new HYPubBO().queryByPrimaryKey(RegisterVO.class,regVO.getPrimaryKey());
			retBillVO.setParentVO(regVO);
			
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

	/*
	 * 备注：平台编写原始脚本
	 */
	public String getCodeRemark() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000173")/*
																									 * @res
																									 * "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n	Object retObj=null;\n	return retObj;\n"
																									 */;
	}

	/*
	 * 备注：设置脚本变量的HAS
	 */
	private void setParameter(String key, Object val) {
		if (m_keyHas == null) {
			m_keyHas = new Hashtable();
		}
		if (val != null) {
			m_keyHas.put(key, val);
		}
	}

	private boolean isNew(SuperVO headVo) {
		boolean isnew = false;
		if (headVo.getPrimaryKey() == null
				|| headVo.getPrimaryKey().trim().length() == 0) {
			isnew = true;
		}
		return isnew;
	}
	
	private void checkSave(RegisterVO registervo) throws BusinessException {
		String payunit = registervo.getPayunit();
		String payacc = registervo.getPaybankacc();
		String payaccname = registervo.getPayaccname();
		String paybank = registervo.getPaybank();
		String paybankname = registervo.getPaybankname();
		
		
		String retMsg = "";
		retMsg = check4InnerCust(payunit, payacc, paybank,payaccname,paybankname);

		String receiveunit = registervo.getReceiveunit();
		String receiveacc = registervo.getReceivebankacc();
		String receivebank = registervo.getReceivebank();
		String receiveaccname = registervo.getReceiveaccname();
		String receivebankname = registervo.getReceivebankname();
		retMsg = retMsg + check4InnerCust(receiveunit, receiveacc, receivebank,receiveaccname,receivebankname);
		if (retMsg.trim().length() > 0) {
			throw new BusinessException(retMsg);
		}

	}
	private String check4InnerCust(String pk_cubasdoc, String pk_acc,
			String pk_bankdoc,String accname,String bankname) {
		if (pk_cubasdoc != null) {
			ICustBasDocQuery custQry = (ICustBasDocQuery) NCLocator.getInstance().lookup(ICustBasDocQuery.class);
			try {
				// 检查客商是否为手动录入
				CustBasVO inputCustBasVO = FBMProxy.retriveInputCust(pk_cubasdoc);
				if (inputCustBasVO != null) {
					return "";
				}

				CubasdocVO cubasdocVO = custQry.queryCustBasDocVOByPK(pk_cubasdoc);
				if (cubasdocVO != null) {
					Integer prop = ((CustBasVO) cubasdocVO.getParentVO()).getCustprop();
					if (prop.intValue() != 0) {// 内部客商
						HYPubBO client = new HYPubBO();
						if (pk_acc == null && accname!=null) {
							return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000032")/*
																														 * @res
																														 * "内部客商对应的银行账户不可手工录入."
																														 */;
						} else {
							if (FBMProxy.retriveInputBankacc(pk_acc) != null) {
								return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000032")/*
																															 * @res
																															 * "内部客商对应的银行账户不可手工录入."
																															 */;
							}
						}
						
						if(pk_acc!= null){
							BankaccbasVO bankaccbasVO = (BankaccbasVO) client.queryByPrimaryKey(BankaccbasVO.class, pk_acc);
	
							if (bankaccbasVO == null) {
								return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000032")/*
																															 * @res
																															 * "内部客商对应的银行账户不可手工录入."
																															 */;
							}
						}

					}
				}
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
				return e.getMessage();
			}
		}
		return "";
	}
	
}