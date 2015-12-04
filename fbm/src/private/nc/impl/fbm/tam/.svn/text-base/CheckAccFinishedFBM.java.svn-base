package nc.impl.fbm.tam;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.itf.tam.account.accid.ICheckAccFinished;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.tam.account.acclog.AccErrMsg;
import nc.vo.tam.account.itfvo.AccountParam;

/**
 * 销户前校验单据是否已结束
 * @author xwq
 *
 * 2008-11-25
 */
public class CheckAccFinishedFBM implements ICheckAccFinished{

	public String getSourceSystem() {
		// TODO Auto-generated method stub
		return FbmBusConstant.SYSCODE_FBM;
	}

	public AccErrMsg isFinish(AccountParam accParam) throws BusinessException {
		// TODO Auto-generated method stub
		AccErrMsg errMsg = new AccErrMsg();
		String pk_accid = accParam.getPk_accid();
		StringBuffer sb = new StringBuffer();
		String keepMsg = getInnerKeepMsg(pk_accid);
		if(keepMsg !=null ){
			sb.append(keepMsg);
		}
		String backMsg = getInnerBackMsg(pk_accid);
		if(backMsg !=null){
			sb.append(backMsg);
		}
		String reliefMsg = getReliefMsg(pk_accid);
		if(reliefMsg !=null){
			sb.append(reliefMsg);
		}
		String reckonMsg = getReckonMsg(pk_accid);
		if(reckonMsg !=null){
			sb.append(reckonMsg);
		}
		if(sb.length() > 0){
			//errMsg.setMessage(sb.toString());
		}

		return null;
	}

	/**
	 * 内部托管未结束单据
	 * @param pk_accid
	 * @return
	 * @throws BusinessException
	 */
	private String getInnerKeepMsg(String pk_accid) throws BusinessException{
		String sql = "select vbillno from fbm_storage where pk_billtypecode = '36GD' and vbillstatus <> 13 and keepaccount='"+pk_accid+"'";
		BaseDAO dao = new BaseDAO();
		List<String> ret = (List<String>)dao.executeQuery(sql, new ColumnListProcessor());
		if(ret != null && ret.size() > 0){
			StringBuffer sb = new StringBuffer();
			sb.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000049")/*@res "未结束的内部托管单\n"*/);
			for(String s:ret){
				sb.append(s);
				sb.append("\n");
			}
			return sb.toString();
		}
		return null;
	}
	/**
	 * 内部领用单未结束单据
	 * @param pk_accid
	 * @return
	 * @throws BusinessException
	 */
	private String getInnerBackMsg(String pk_accid) throws BusinessException{
		String sql = "select vbillno from fbm_storage where pk_billtypecode = '36GE' and vbillstatus <> 14 and keepaccount='"+pk_accid+"'";
		BaseDAO dao = new BaseDAO();
		List<String> ret = (List<String>)dao.executeQuery(sql, new ColumnListProcessor());
		if(ret != null && ret.size() > 0){
			StringBuffer sb = new StringBuffer();
			sb.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000050")/*@res "未结束的内部领用单\n"*/);
			for(String s:ret){
				sb.append(s);
				sb.append("\n");
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * 调剂单未结束单据
	 * @param pk_accid
	 * @return
	 * @throws BusinessException
	 */
	private String getReliefMsg(String pk_accid) throws BusinessException{
		String sql = "select vbillno from fbm_relief where vbillstatus <>1 and inneracc='"+pk_accid+"'";
		BaseDAO dao = new BaseDAO();
		List<String> ret = (List<String>)dao.executeQuery(sql, new ColumnListProcessor());
		if(ret != null && ret.size() > 0){
			StringBuffer sb = new StringBuffer();
			sb.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000051")/*@res "未结束的调剂单\n"*/);
			for(String s:ret){
				sb.append(s);
				sb.append("\n");
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * 清算单未结束单据
	 * @param pk_accid
	 * @return
	 * @throws BusinessException
	 */
	private String getReckonMsg(String pk_accid) throws BusinessException{
		String sql = "select vbillno from fbm_reckon where vbillstatus <>1 and inacc = '"+pk_accid+"' and outacc='"+pk_accid+"'";
		BaseDAO dao = new BaseDAO();
		List<String> ret = (List<String>)dao.executeQuery(sql, new ColumnListProcessor());
		if(ret != null && ret.size() > 0){
			StringBuffer sb = new StringBuffer();
			sb.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000052")/*@res "未结束的清算单\n"*/);
			for(String s:ret){
				sb.append(s);
				sb.append("\n");
			}
			return sb.toString();
		}
		return null;
	}

}
 