package nc.bs.fbm.impawn;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.IBillStatus;

/**
 * 
 * 功能： impanw操作类 日期：2007-10-15 程序员：wues
 */
public class ImpawnDAO {

	public ImpawnDAO() {
		super();
	}

	/**
	 * 根据质押单ID返回相应的质押单VO
	 * 
	 * @param pk_impawn
	 * @return
	 * @throws BusinessException
	 */
	public ImpawnVO getImpawnVO(String pk_impawn) throws DAOException {
		String sql = "select pk_impawn,pk_impawn,impawnno,pk_billtypecode,pk_source,pk_baseinfo,evaluatevalue,evaluateunit,"
				+ "impawnbank,impawnrate,impawnable,impawnunit,impawndate,impawnbackunit,impawnbackdate,moneyy,"
				+ "debitunit,creditunit,vbillno,vbillstatus,voperatorid,doperatedate,vapproveid,dapprovedate,"
				+ "vapprovenote,frate,brate,moneyf,moneyb,pk_corp,pk_org,def1,def2,def3,def4,def5"
				+ " from fbm_impawn where pk_impawn='" + pk_impawn + "'";
		ImpawnVO[] ret = (ImpawnVO[]) ((List<ImpawnVO>) (new BaseDAO()
				.executeQuery(sql, new BeanListProcessor(ImpawnVO.class))))
				.toArray(new ImpawnVO[0]);
		if (ret == null || ret.length == 0) {
			return null;
		}
		return ret[0];
	}

	/**
	 * 根据质押单pk，回收人和回收日期进行质押回收操作 返回更新的行数，如果为0说明未进行更新
	 * 
	 * @param pk_impawn
	 * @param backPerson
	 * @param backDate
	 * @return
	 * @throws DAOException
	 */
	public int impawnBack(String pk_impawn, String backPerson, UFDate backDate)
			throws DAOException {
		String sql = new StringBuffer().append(
				" update fbm_impawn set impawnbackunit='").append(backPerson)
				.append("', impawnbackdate='").append(backDate).append(
						"',vbillstatus=").append(IFBMStatus.IMPAWN_BACK)
				.append(" where pk_impawn='").append(pk_impawn).append(
						"' and vbillstatus=").append(IFBMStatus.CHECKPASS)
				.toString();
		return new BaseDAO().executeUpdate(sql);
	}
	
	/**
	 * 取消质押回收
	 * 
	 * @param pk_impawn
	 * @param backPerson
	 * @param backDate
	 * @return
	 * @throws DAOException
	 */
	public int cancelImpawnBack(String pk_impawn, String backPerson, UFDate backDate)
			throws DAOException {
		String sql = new StringBuffer().append(
				" update fbm_impawn set impawnbackunit=null, impawnbackdate=null,vbillstatus=").append(IBillStatus.CHECKPASS)
				.append(" where pk_impawn='").append(pk_impawn).append(
						"' and vbillstatus=").append(IFBMStatus.IMPAWN_BACK)
				.toString();
		return new BaseDAO().executeUpdate(sql);
	}
	
	/**
	 * TODO 根据[质押单主键]查询[物权担保单主键]
	 * 
	 * @param pk_fbmimpawn 质押单ID
	 * @return 物权担保单ID
	 * 
	 * @author zhouwb 2008-10-13
	 */
	public String queryPk_fiimpawn(String pk_fbmimpawn) throws DAOException {
		String sql = "select pk_id from fi_impawn where pk_othersys='"
				+ pk_fbmimpawn + "' and isnull(dr,0) = 0";
		List list = (List) new BaseDAO().executeQuery(sql, new ArrayListProcessor());

		if (list != null && list.size() == 1) {// 就一个元素,则返回
			return (String) ((Object[]) list.get(0))[0];
		}
		return null;
	}
}
