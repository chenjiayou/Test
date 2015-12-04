package nc.ui.fbm.returnbill.checker;

import java.util.List;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.SqlUtil;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.pub.BusinessException;

/**
 * 校验退票币种必须一致
 * @author xwq
 *
 * 2008-12-25
 */
public class ReturnBillCurrChecker extends AbstractUIChecker{

	public ReturnBillCurrChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String check() {
		ReturnBVO[] bvos = (ReturnBVO[])getUI().getBillCardPanel().getBillData().getBillModel().getBodyValueVOs(ReturnBVO.class.getName());
		if(bvos!=null && bvos.length > 0){
			String[] pks = new String[bvos.length];
			for(int i = 0;i<bvos.length;i++){
				pks[i] = bvos[i].getPk_baseinfo();
			}
			String sql = "select distinct(pk_curr) from fbm_baseinfo where pk_baseinfo " + SqlUtil.getInClause(pks);
			try {
				List list = (List)FBMProxy.getUAPQuery().executeQuery(sql, new ColumnListProcessor());
				if(list!=null && list.size() > 1){
					return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000023")/*@res "退票单包含多个币种的票据,无法保存"*/;
				}
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				Logger.error(e.getMessage());
			}
		}
		return null;
	}

}