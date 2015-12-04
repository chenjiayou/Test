package nc.bs.fbm.reciept;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckon.ReckonVO;

/**
 * 
 * 类功能说明： 清算单和清算回单DAO操作 日期：2007-12-21 程序员： wues
 * 
 */
public class RecieptDAO {

	/**
	 * 根据清算单主键和前置状态更新清算单为指定状态
	 * 
	 * @param pk_reckon
	 * @param status
	 * @return
	 * @throws DAOException
	 */
	public int updateReckonStatus(String pk_reckon, int beginStatus,
			int endStatus, String billType, ReckonVO vo) throws DAOException {
		String sql_reckon = new StringBuffer().append(
				" update fbm_reckon set vbillstatus = ").append(endStatus)
				.append(", unitvoucherdate='").append(
						vo.getUnitvoucherdate() == null ? "" : vo
								.getUnitvoucherdate()).append(
						"', unitvouchermanid='").append(
						vo.getUnitvouchermanid() == null ? "" : vo
								.getUnitvouchermanid()).append(
						"', voucherdate='").append(
						vo.getVoucherdate() == null ? "" : vo.getVoucherdate())
				.append("',vouchermanid='").append(
						vo.getVouchermanid() == null
								|| "".equals(vo.getVouchermanid().trim()) ? ""
								: vo.getVouchermanid()).append("'").append(
						" where pk_reckon= '").append(pk_reckon).append(
						"' and vbillstatus=").append(beginStatus).append(
						" and pk_billtypecode='").append(billType).append("'")
				.toString();
		return new BaseDAO().executeUpdate(sql_reckon);
	}

	/**
	 * 根据清算单主键和前置状态更新清算回单为指定状态
	 * 
	 * @param pk_reckon
	 * @param status
	 * @return
	 * @throws DAOException
	 */
	public int updateReceiptStatus(String pk_reckon, int beginStatus,
			int endStatus, ReckonVO vo) throws DAOException {
		String sql_reciept = new StringBuffer().append(
				" update fbm_reckon set vbillstatus = ").append(endStatus)
				.append(", unitvoucherdate='").append(
						vo.getUnitvoucherdate() == null ? "" : vo
								.getUnitvoucherdate()).append(
						"', unitvouchermanid='").append(
						vo.getUnitvouchermanid() == null ? "" : vo
								.getUnitvouchermanid()).append(
						"', voucherdate='").append(
						vo.getVoucherdate() == null ? "" : vo.getVoucherdate())
				.append("',vouchermanid='").append(
						vo.getVouchermanid() == null
								|| "".equals(vo.getVouchermanid().trim()) ? ""
								: vo.getVouchermanid()).append("'").append(
						" where def1= '").append(pk_reckon).append(
						"' and vbillstatus=").append(beginStatus).append(
						" and pk_billtypecode='").append(
						FbmBusConstant.BILLTYPE_RECKON_RECIEPT).append("'")
				.toString();
		return new BaseDAO().executeUpdate(sql_reciept);
	}

}
