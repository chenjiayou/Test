package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.bs.uap.lock.PKLock;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：调剂清算单的保存 单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-11-1)
 *
 * @author 平台脚本生成
 */
public class N_36GK_SAVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GK_SAVE 构造子注解。
	 */
	public N_36GK_SAVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		CommonDAO dao = new CommonDAO();
		ReckonBVO[] reckonBVOs = (ReckonBVO[])vo.m_preValueVo.getChildrenVO();
		String[] pk_baseinfos = new String[reckonBVOs.length];
		for (int i = 0; i < reckonBVOs.length; i++) {
			pk_baseinfos[i] = reckonBVOs[i].getPk_baseinfo();
			// 以下查询防止pk_baseinfo为空的情况
			if (pk_baseinfos[i] == null) {
				RegisterVO registerVO = (RegisterVO) dao.getBaseDAO()
						.retrieveByPK(RegisterVO.class,
								reckonBVOs[i].getPk_source());

				if (null == registerVO) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000229")/* @res"根据收票登记单主键取到的收票登记单为空，请刷新重试。"*/);
				}
				pk_baseinfos[i] = registerVO.getPk_baseinfo();
			}
		}

		try {
			super.m_tmpVo = vo;
			Object retObj = null;
			//当点击修改时,未改变任何数据,不执行锁操作.
			if(pk_baseinfos.length>0){
				lock(pk_baseinfos, vo.m_operator);
			}

			//添加判断,如果为修改保存则不执行此验证.
			ReckonVO reckonvo = (ReckonVO)vo.m_preValueVo.getParentVO();
			String pk_value = reckonvo.getPk_reckon();
			if(pk_value == null||"".equals(pk_value)){
				validateUniqueBill(vo.m_preValueVo);
			}

			validateAccountDetail(vo.m_preValueVo);
			retObj = runClass("nc.bs.trade.comsave.BillSave", "saveBill",
					"nc.vo.pub.AggregatedValueObject:36GK", vo, m_keyHas,
					m_methodReturnHas);

			// 重新设值，将携带的值重新赋值
//			if (vo.m_preValueVo.getChildrenVO() != null
//					&& vo.m_preValueVo.getChildrenVO().length != 0) {// 得到的字表数据不为0或空
//				ResetRefValues.setReckonBodyRefValues(vo.m_preValueVo);
//			}

			HYBillVO retBillVO = (HYBillVO) ((ArrayList) retObj).get(1);

			if (retObj != null) {
				m_methodReturnHas.put("saveBill", retObj);
			}
			// 执 行公式 填充参照名称，解决效率问题，减少前台与后台交互次数。
			RegisterVO2BVO.actionOPExecFormula((HYBillVO) ((ArrayList) retObj).get(1));
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		} finally {
			//如果pk_baseinfos无值,不执行加锁与解锁操作.
			if(pk_baseinfos.length>0){
				unLock(pk_baseinfos, vo.m_operator);
			}
		}
	}

	/**
	 * 校验新保存的票据在清算字表中是否存在，如果存在抛出异常
	 * @throws BusinessException
	 */
	private void validateUniqueBill(nc.vo.pub.AggregatedValueObject vo) throws BusinessException {
		ReckonBVO[] vos = getReckonVOS((ReckonBVO[])vo.getChildrenVO());
		if (vos != null && vos.length != 0 ) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000176")/*@res "要清算的部分票据已经清算过，不能再次清算，请确认！"*/);
		}
	}

	private void validateAccountDetail(nc.vo.pub.AggregatedValueObject vo) throws BusinessException {
		String pks = getPKS((ReckonBVO[])vo.getChildrenVO());
		if (pks == null || "".equals(pks.trim())) {
			return ;
		}
		String condition = " pk_detail in (" + pks + ") and isliquid = 'N'";
		AccountDetailVO[] accountDetailVOs = (AccountDetailVO[])new HYPubBO().queryByCondition(AccountDetailVO.class, condition);
        // 清算单保存后，修改表体记录后仍保留原来的表体记录，保存时提示‘部分记录不是待清算状态 NCdp201016877
		// 表体添加公式后，处理增行删行后，BUFFer 里面实际的表体数据多于表体上显示的数据数量
		// 为了处理buffer 里的重复数据 增加了下面的处理。
		Map<String, String> childrenSize = new HashMap<String, String>();
		ReckonBVO bvo[] = (ReckonBVO[]) vo.getChildrenVO();
		for (int i = 0; i < bvo.length; i++) {
			childrenSize.put(bvo[i].getPk_source(), "");
		}
		if (accountDetailVOs.length != childrenSize.size()) {
        	throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000177")/*@res "要清算的部分票据不是待清算状态，请确认！"*/);
        }
	}

	private ReckonBVO[] getReckonVOS(ReckonBVO[] vos) throws BusinessException {
		String pks = getPKS(vos);
		if (pks == null || "".equals(pks.trim())) {
			return null;
		}
		String condition = " pk_detail in (" + pks + ")";
		return (ReckonBVO[])new HYPubBO().queryByCondition(ReckonBVO.class, condition);
	}

	/**
	 * 将清算字表的所有vo拼接成in字符串
	 * @param vos
	 */
	private String getPKS(ReckonBVO[] vos) {
		StringBuffer pks = null;
		for (int i = 0; null != vos && i < vos.length; i++) {
			if (null == pks) {
				pks = new StringBuffer();
			}
			pks.append("'").append(vos[i].getPk_detail()).append("'");
			if (i != vos.length-1) {
				pks.append(",");
			}
		}
		return null == pks ? null : pks.toString();
	}

	private void lock(String[] pk_baseinfo, String userid) throws BusinessException {
		if(null == pk_baseinfo || pk_baseinfo.length == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000230")/* @res"无法锁定当前票据，取得的票据基本信息为空。"*/);
		}
		if(null == userid || "".equals(userid.trim())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000231")/* @res"无法锁定当前票据， 取得的当前用户为空。"*/);
		}


		PKLock.getInstance().acquireBatchLock(pk_baseinfo, userid, "");
	}

	private void unLock(String[] pk_baseinfo, String userid) throws BusinessException {
		if(null == pk_baseinfo || pk_baseinfo.length == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000232")/* @res"无法解除当前票据的锁定，取得的票据基本信息为空。"*/);
		}
		if(null == userid || "".equals(userid.trim())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000233")/* @res"无法解除当前票据的锁定，取得的当前用户为空。"*/);
		}
		PKLock.getInstance().releaseBatchLock(pk_baseinfo, userid, "");
	}


	/*
	 * 备注：平台编写原始脚本
	 */
	public String getCodeRemark() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000173")/*@res "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n	Object retObj=null;\n	return retObj;\n"*/;
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
}