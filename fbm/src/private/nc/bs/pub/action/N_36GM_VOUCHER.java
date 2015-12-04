package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.accept.AcceptVoucher;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.itf.dap.pub.IDapSendMessage;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * 备注：票据付款的制证 单据动作执行中的动态执行类的动态执行类。
 * 
 * 创建日期：(2008-8-15)
 * 
 * @author 平台脚本生成
 */
public class N_36GM_VOUCHER extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GM_VOUCHER 构造子注解。
	 */
	public N_36GM_VOUCHER() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			AcceptVO acceptVO = (AcceptVO) vo.m_preValueVo.getParentVO();
			acceptVO.setUnitvoucher(new UFBoolean(true));
			BaseDAO dao = new BaseDAO();
			dao.updateVO(acceptVO);

			IDapSendMessage dapservice = OuterProxy.getDapSendMessageService();
			HYBillVO billvo = (HYBillVO) vo.m_preValueVo;
			acceptVO.setUnitvoucher(new UFBoolean(true));
			acceptVO.setVvouchermanid(vo.m_operator);
			acceptVO.setVvoucherdate(new UFDate(vo.m_currentDate));
			if (acceptVO.getSecuritymoney() == null) {
				acceptVO.setSecuritymoney(new UFDouble(0));
			}
			if (acceptVO.getSecuritymoneyb() == null) {
				acceptVO.setSecuritymoneyb(new UFDouble(0));
			}
			if (acceptVO.getBacksecmoney() == null) {
				acceptVO.setBacksecmoney(new UFDouble(0));
			}
			if (acceptVO.getBacksecmoneyb() == null) {
				acceptVO.setBacksecmoneyb(new UFDouble(0));
			}
			billvo.setChildrenVO(new SuperVO[] { acceptVO });// 单子表传平台有问题，必须要有子表数据

			dapservice.sendMessage(new AcceptVoucher().getDapMsgVo(billvo, FbmBusConstant.OP_VOUCHER), billvo);

			acceptVO = (AcceptVO) dao.retrieveByPK(AcceptVO.class, acceptVO.getPrimaryKey());
			HYBillVO retObj = new HYBillVO();
			retObj.setParentVO(acceptVO);
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
		return "	Object retObj=null;\n	return retObj;\n";
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
