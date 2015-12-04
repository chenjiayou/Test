package nc.bs.fbm.pub;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.pub.BusinessException;

/**
 * 对从5.011升级上来的数据
 * 如果在dap_finindex表内找到了相关数据，如果是GA等旧票据类型
 * 则报错，不允许取消制证
 * @author xwq
 *
 */
public class UnVoucherCheckService {

	public void checkUnVoucher(String pk_bill) throws BusinessException{
		BaseDAO dao = new BaseDAO();
		List list = (List)dao.executeQuery("select pk_proc from dap_finindex where isnull(dr,0)=0 and substring(procmsg,1,20)='" + pk_bill + "'", new ColumnListProcessor());

		if(list != null && list.size() > 0){
			 String pk_proc = String.valueOf(list.get(0));
			 if("GA".equals(pk_proc) || "GB".equals(pk_proc) || "GC".equals(pk_proc)
				||"GD".equals(pk_proc) || "GE".equals(pk_proc) || "GF".equals(pk_proc)
				||"GG".equals(pk_proc) || "GH".equals(pk_proc) || "GI".equals(pk_proc)
				||"GJ".equals(pk_proc) || "GK".equals(pk_proc) || "GL".equals(pk_proc)
				||"GM".equals(pk_proc) || "GN".equals(pk_proc) || "GO".equals(pk_proc) || "GQ".equals(pk_proc)){
				 throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000156")/*@res "升级的票据数据，无法取消制证"*/);
			 }
		}

	}
}