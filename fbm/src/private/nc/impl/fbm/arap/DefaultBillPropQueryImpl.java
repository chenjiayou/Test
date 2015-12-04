package nc.impl.fbm.arap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.itf.fbm.arap.IBillPropQueryInterface;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.pub.BusinessException;

/**
 * 查询票据应收应付属性
 * @author xwq
 *
 */
public class DefaultBillPropQueryImpl implements IBillPropQueryInterface {
	public Map<String, String> queryBillProp(String pk_corp,String[] billno)throws BusinessException {
		if(billno == null && billno.length == 0 ){
			return null;
		}

		int len = billno.length;
		Map<String,String> ret = new HashMap<String,String>();
		BaseDAO dao = new BaseDAO();
		List list = null;
		for(int i = 0; i < len ; i++){
			list = (List)dao.retrieveByClause(BaseinfoVO.class, " isnull(dr,0)=0 and fbmbillno='"+ billno[i] + "'");
			if(list != null && list.size() > 0){
				BaseinfoVO baseinfoVO = (BaseinfoVO)list.get(0);
				ret.put(billno[i], baseinfoVO.getOrientation());
			}else{
				throw  new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000330")/* @res"不存在的票据编号"*/);
			}
		}

		return ret;
	}
//	public Map<String, String> queryBillProp(String pk_corp,String[] billno)
//			throws BusinessException {
//
//		if(billno == null && billno.length == 0 ){
//			return null;
//		}
//		int len = billno.length;
//
//		Map<String,String> ret = new HashMap<String,String>();
//		StringBuffer sb = new StringBuffer();
//		sb.append("select r.* from fbm_baseinfo b join fbm_register r on(b.pk_baseinfo = r.pk_baseinfo) where");
//		sb.append(" isnull(r.dr,0)=0 and isnull(b.dr,0)=0 ");
//		sb.append(" and r.pk_corp=? and b.fbmbillno=?");
//		String sql = sb.toString();
//
//		SQLParameter parameter = null;
//
//		BaseDAO dao = new BaseDAO();
//		List<RegisterVO> retList = null;
//		for(int i = 0 ; i < len ; i++){
//			parameter = new SQLParameter();
//			parameter.addParam(pk_corp);
//			parameter.addParam(billno[i]);
//			retList = (List<RegisterVO>)dao.executeQuery(sql, parameter, new BeanListProcessor(RegisterVO.class));
//			if(retList != null && retList.size() > 0){
//				RegisterVO[] vos = (RegisterVO[])retList.toArray(new RegisterVO[0]);
//				if(vos.length ==1 && vos[0].getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_INVOICE)){
//					ret.put(billno[i], FbmBusConstant.ORIEINTATION_AP);
//				}else{
//					ret.put(billno[i], FbmBusConstant.ORIEINTATION_AR);
//				}
//			}else{
//				throw  new BusinessException("没有该票据登记记录");
//			}
//		}
//
//		return ret;
//	}

	public Map<String, String> queryHistoryBillProp(String pk_corp,
			String[] pk_bill_b) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}