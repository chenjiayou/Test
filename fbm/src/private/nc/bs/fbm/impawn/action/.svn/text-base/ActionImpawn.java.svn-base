package nc.bs.fbm.impawn.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.AbstractBusiAction;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * 功能： 票据质押的action 日期：2007-10-10 程序员：wues
 */
public abstract class ActionImpawn<K extends ImpawnVO, T extends ImpawnVO>  extends AbstractBusiAction<K, T> {
	
	/**
	 * <p>
	 * 票据质押动作转换参数 wes
	 * </p>
	 * 
	 * @param billVo
	 * @param actionCode
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public BusiActionParamVO<T>[] buildParam(K data, String actioncode)
			throws BusinessException {
		BusiActionParamVO<T>[] params = new BusiActionParamVO[1];
		params[0] = new BusiActionParamVO<T>();
		fillBusiField(data, params[0], actioncode);

		// 增加校验
		if (FbmActionConstant.IMPAWNBACK.equals(actioncode)) {
			// 回收时取回收日期和回收人
			params[0].setActiondate(data.getImpawnbackdate());
			params[0].setActionperson(data.getImpawnbackunit());
		} else {
			// 其他取业务日期,一律取质押日期和质押人
			params[0].setActiondate(data.getImpawndate());
			params[0].setActionperson(data.getImpawnunit());
		}
		params[0].setActioncode(actioncode);
		params[0].setPk_bill(data.getPrimaryKey());
		params[0].setPk_source(data.getPk_source());
		params[0].setSourcefield(ImpawnVO.PK_SOURCE);
		params[0].setBilltype(FbmBusConstant.BILLTYPE_IMPAWN);
		params[0].setViewVO(data);// 界面数据VO，superVO被abstractAction的重新查询冲掉了，记录界面的VO需要在viewVO中保存

		params[0].setSuperVO((T)data);// 推式生成时用到vo对象
		params[0].setPk_org(data.getImpawnunit());
		params[0].setPk_baseinfo(data.getPk_baseinfo());
		params[0].setPk_corp(data.getPk_corp());
		return params;
	}
	
	/**
	 * VO转换 将票据系统中的nc.vo.fbm.impawn.ImpawnVO改为物权担保需要的nc.vo.fi.impawn.ImpawnVO
	 * 
	 * @param oldVO
	 * @param pk_outer
	 * @return
	 * @throws BusinessException
	 */
	protected nc.vo.fi.impawn.ImpawnVO changeVO(nc.vo.fbm.impawn.ImpawnVO oldVO)
			throws BusinessException {
		nc.vo.fi.impawn.ImpawnVO newVO = new nc.vo.fi.impawn.ImpawnVO();
		CommonDAO comDAO = new CommonDAO();
		BaseinfoVO baseVO = comDAO.queryBaseinfoByPK(oldVO.getPk_baseinfo());
		newVO.setPk_corp(oldVO.getPk_corp());
		newVO.setOperator(oldVO.getVoperatorid());
		newVO.setOperatedate(oldVO.getImpawndate());
		
		newVO.setDatasource("BILLSYS");
		
		newVO.setImpawncode(oldVO.getVbillno());// 质押单据编号
		
		newVO.setImpawnname(baseVO.getFbmbillno());//票据编号
		
		newVO.setImpawnallvalue(oldVO.evaluatevalue);// 评估价值：票面金额
		newVO.setImpawnrate(oldVO.getImpawnrate());// 质押率
		newVO.setImpawnvalue(oldVO.getImpawnable());// 可质押价值
		newVO.setImpawnedvalue(new UFDouble(0));// 已质押价值
		newVO.setImpawnablevalue(newVO.getImpawnvalue());// 剩余质押价值=可质押价值
		newVO.setBillbegindate(baseVO.getInvoicedate());// 票据签发日期
		newVO.setBillenddate(baseVO.getEnddate());// 票据到期日期
		newVO.setImpawnflag(UFBoolean.FALSE);// 未有合同占用
		newVO.setImpawntype("ZHIYA");// 质押抵押类别
		newVO.setImpawnstatus("Y");// 设置质押单属性为正常
		newVO.setImpawnprop("SELFUNIT");// 属性
		//newVO.setImpawnowner(InvocationInfoProxy.getInstance().getCorpCode());// 所有权单位,取当前登陆单位
		newVO.setImpawnowner(oldVO.getPk_corp());// 所有权单位,取当前登陆单位

		newVO.setFi_def36(baseVO.getFbmbillno());// 设置自定义项36，在此取票据编号，唯一
		newVO.setEvaluateunit(oldVO.getEvaluateunit());// 评估单位
		newVO.setSetupcorp("");// 设立公司
		newVO.setCurrency(baseVO.getPk_curr());// 币种主键
		newVO.setBank(oldVO.getImpawnbank());// 质押银行
		// 外系统主键设置为质押单ID
		newVO.setPk_othersys(oldVO.getPk_impawn());
		
		newVO.setUneffectdate(oldVO.getImpawnbackdate());//设置质押回收日期=作废日期
		
		newVO.setUneffectperson(oldVO.impawnbackunit);//设置作废人为质押回收人
		
		return newVO;
	}

	/**
	 * 判断某个系统是否启用
	 * 
	 * @param productCode
	 *            系统code
	 * @return
	 * @throws BusinessException
	 */
	protected boolean isEnabled(String pk_corp,String productCode) throws BusinessException {
		if (OuterProxy.getCreateCorpQuery().isEnabled(
				pk_corp, productCode)) {
			return true;
		}
		return false;
	}
}
