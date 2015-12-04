package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.endore.EndoreClearVoucher;
import nc.bs.fbm.endore.EndoreService;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：背书办理的冲销
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2008-3-21)
 * @author 平台脚本生成
 */
public class N_36GQ_DESTROY extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GQ_DESTROY 构造子注解。
 */
public N_36GQ_DESTROY() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
@SuppressWarnings("unchecked")
public Object runComClass(PfParameterVO vo) throws BusinessException {
	try{
		super.m_tmpVo=vo;
		//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####

		HYBillVO hbvo = new HYBillVO();

		EndoreVO endorevo = (EndoreVO)vo.m_preValueVo.getParentVO();

		//改变票据状态
		BusiActionFactory.getInstance().createActionClass(
						FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.DESTROY)
						.doAction(endorevo, FbmActionConstant.DESTROY, false);

		//改变背书办理单状态为已冲销
		new EndoreService().updateDestroyStatus(endorevo, IFBMStatus.HAS_CLEAR);

		//背书冲销生成凭证
		new EndoreClearVoucher().voucher(vo.m_preValueVo, FbmBusConstant.OP_CLEAR);

		String pk_key = endorevo.getPrimaryKey();

		EndoreVO newvo =  (EndoreVO)new HYPubBO().queryByPrimaryKey(EndoreVO.class,pk_key);

		hbvo.setParentVO(newvo);

		return hbvo;

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
public String getCodeRemark(){
	return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000173")/*@res "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n	Object retObj=null;\n	return retObj;\n"*/;}
/*
* 备注：设置脚本变量的HAS
*/
private void setParameter(String key,Object val)	{
	if (m_keyHas==null){
		m_keyHas=new Hashtable();
	}
	if (val!=null)	{
		m_keyHas.put(key,val);
	}
}
}