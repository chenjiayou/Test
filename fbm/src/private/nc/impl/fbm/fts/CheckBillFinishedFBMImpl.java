package nc.impl.fbm.fts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import nc.bs.dao.BaseDAO;
import nc.itf.fts.pub.ICheckBillFinished;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.vo.fts.base.IBaseVO;
import nc.vo.fts.fts503.FtsVoucherVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;

/**
 * �����ս�ʱ��ҪУ�鵥���Ƿ������
 * @author xwq
 *
 * 2008-11-25
 */
public class CheckBillFinishedFBMImpl implements ICheckBillFinished{

	public Vector checkBusData(IBaseVO vo) throws BusinessException {
		// TODO Auto-generated method stub
		Vector<String> errMsg = new Vector<String>();
		FtsVoucherVO voucher = (FtsVoucherVO) vo;
		String centcorp = voucher.getPk_corp();
		UFDate date  = voucher.getCheckeddate();

		List<String> keepMsg = getInnerKeepErrorMsg(centcorp, date);
		if(keepMsg != null && keepMsg.size() > 0){
			errMsg.addAll(keepMsg);
		}

		List<String> backMsg = getInnerBackErrorMsg(centcorp, date);
		if(backMsg !=null && backMsg.size() > 0){
			errMsg.addAll(backMsg);
		}

		List<String> reliefMsg = getReliefErrorMsg(centcorp, date);
		if(reliefMsg != null && reliefMsg.size() > 0){
			errMsg.addAll(reliefMsg);
		}

		List<String> reckonMsg = getReckonErrorMsg(centcorp, date);
		if(reckonMsg !=null && reckonMsg.size() > 0){
			errMsg.addAll(reckonMsg);
		}

		return errMsg;
	}

	/**
	 * �ڲ��йܵ��Ƿ����
	 * @param centcorp
	 * @param date
	 * @return
	 * @throws BusinessException
	 */
	private List<String> getInnerKeepErrorMsg(String centcorp,UFDate date) throws BusinessException{
		String sql = "select vbillno from fbm_storage where pk_billtypecode = '36GD' and vbillstatus <>13 and dealdate is not null and dealdate <='"+date+"' and keepcorp in("+getSubCorpSQL(centcorp)+")" ;
		BaseDAO dao = new BaseDAO();
		final List<String> list = new ArrayList<String>();
		dao.executeQuery(sql, new ResultSetProcessor(){
			public Object handleResultSet(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String no = rs.getString("vbillno");
					list.add( nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000038")/*@res "�ڲ��йܵ���"*/ + no + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000039")/*@res "��δ������� \n "*/);
				}
				if (list.size() != 0) {
					return list;
				}
				return null;
			}
		});
		return list;
	}

	/**
	 * �ڲ����õ��Ƿ����
	 * @param centcorp
	 * @param date
	 * @return
	 * @throws BusinessException
	 */
	private List<String> getInnerBackErrorMsg(String centcorp,UFDate date) throws BusinessException{
		String sql = "select vbillno from fbm_storage where pk_billtypecode = '36GE' and vbillstatus <>14 and dealdate is not null and dealdate <='"+date+"' and keepcorp in("+getSubCorpSQL(centcorp)+")" ;
		BaseDAO dao = new BaseDAO();
		final List<String> list = new ArrayList<String>();
		dao.executeQuery(sql, new ResultSetProcessor(){
			public Object handleResultSet(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String no = rs.getString("vbillno");
					list.add( nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000040")/*@res "�ڲ����õ���"*/ + no + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000039")/*@res "��δ������� \n "*/);
				}
				if (list.size() != 0) {
					return list;
				}
				return null;
			}
		});
		return list;
	}

	/**
	 * �������Ƿ����
	 * @param centcorp
	 * @param date
	 * @return
	 * @throws BusinessException
	 */
	private List<String> getReliefErrorMsg(String centcorp,UFDate date) throws BusinessException{
		String sql = "select vbillno from fbm_relief where pk_billtypecode='36GR' and vbillstatus <>1 and dealdate is not null and dealdate <='"+date+"' and pk_corp in("+getSubCorpSQL(centcorp)+")" ;
		BaseDAO dao = new BaseDAO();
		final List<String> list = new ArrayList<String>();
		dao.executeQuery(sql, new  ResultSetProcessor(){
			public Object handleResultSet(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String no = rs.getString("vbillno");
					list.add( nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000041")/*@res "Ʊ�ݵ�������"*/ + no + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000039")/*@res "��δ������� \n "*/);
				}
				if (list.size() != 0) {
					return list;
				}
				return null;
			}
		});
		return list;
	}

	/**
	 * ���㵥�Ƿ����
	 * @param centcorp
	 * @param date
	 * @return
	 * @throws BusinessException
	 */
	private List<String> getReckonErrorMsg(String centcorp,UFDate date) throws BusinessException{
		String sql = "select vbillno from fbm_reckon where pk_billtypecode='36GK' and vbillstatus <>1 and dealdate is not null and dealdate <='"+date+"' and pk_corp in("+getSubCorpSQL(centcorp)+")" ;
		BaseDAO dao = new BaseDAO();
		final List<String> list = new ArrayList<String>();
		dao.executeQuery(sql, new  ResultSetProcessor(){
			public Object handleResultSet(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String no = rs.getString("vbillno");
					list.add( nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000042")/*@res "Ʊ�����㵥��"*/ + no + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000039")/*@res "��δ������� \n "*/);
				}
				if (list.size() != 0) {
					return list;
				}
				return null;
			}
		});
		return list;
	}

	/**
	 * �õ��¼���˾PK�ϼ�
	 * @param centcorp
	 * @return
	 */
	private String getSubCorpSQL(String centcorp){
		return "select bd_settleunit.pk_corp1 from bd_settleunit left join bd_settlecenter on bd_settlecenter.pk_settlecenter = bd_settleunit.pk_settlecent where bd_settlecenter.pk_corp = '"+centcorp+"' and bd_settleunit.pk_corp1 <>'"+centcorp+"'";
	}
}