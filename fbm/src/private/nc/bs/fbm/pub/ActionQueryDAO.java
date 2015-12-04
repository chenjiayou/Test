package nc.bs.fbm.pub;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;

/**
 * ��ѯ������DAO��
 *
 * @author xwq
 *
 */
public class ActionQueryDAO {

	public ActionVO queryNewestByFbmBillno(String fbmbillno,String pk_corp)
			throws BusinessException {
		BaseinfoVO baseinfoVO = new BaseInfoBO().queryByFbmbillno(fbmbillno);
		if (baseinfoVO == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000330")/* @res"�����ڵ�Ʊ�ݱ��"*/);
		}
		return queryNewestByPk_baseinfo(baseinfoVO.getPk_baseinfo(),pk_corp);
	}

	/**
	 * ��ѯƱ�����¶���
	 *
	 * @param pk_baseinfo
	 * @return
	 * @throws BusinessException
	 */
	public ActionVO queryNewestByPk_baseinfo(String pk_baseinfo,String pk_corp)
			throws BusinessException {
		String sql = "select * from fbm_action where isnull(dr,0)=0 and pk_baseinfo = '"
				+ pk_baseinfo + "' order by serialnum desc";
		BaseDAO dao = new BaseDAO();
		List<ActionVO> list = (List<ActionVO>) (dao.executeQuery(sql,
				new BeanListProcessor(ActionVO.class)));
		if (list != null && list.size() > 0) {
			for(ActionVO actionVO:list){
				//�ų��Ǳ���˾�ĳж�ҵ��
				if(actionVO.getPk_corp() != null && !actionVO.getPk_corp().equals(pk_corp) && actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_BILLPAY)){
					continue;
				}
				return actionVO;
			}
		}
		return null;
	}


	/**
	 * ��ѯ�����ո�Ʊ��Ӧ�����ж���
	 *
	 * @param pk_register
	 * @return
	 * @throws BusinessException
	 */
	public ActionVO[] queryAllByPk_register(String pk_register)
			throws BusinessException {
		String sql = "select * from fbm_action where isnull(dr,0)=0 and pk_source = ? order by serialnum desc";
		BaseDAO dao = new BaseDAO();
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_register);
		return (ActionVO[]) ((List<ActionVO>) (dao.executeQuery(sql, parameter,
				new BeanListProcessor(ActionVO.class))))
				.toArray(new ActionVO[0]);
	}

	/**
	 *
	 * ��ѯ��ǰ���¶��� xwq 2007-8-31
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	public ActionVO queryNewestByPk_register(String pk_register)
			throws BusinessException {
		String sql = "select * from fbm_action where isnull(dr,0)=0 and pk_source = ? order by serialnum desc";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_register);
		BaseDAO dao = new BaseDAO();
		ActionVO[] action = (ActionVO[]) ((List<ActionVO>) (dao.executeQuery(
				sql, parameter, new BeanListProcessor(ActionVO.class))))
				.toArray(new ActionVO[0]);

		if (action != null && action.length > 0) {
			return (ActionVO) action[0];
		}
		return null;
	}

	/**
	 *
	 * ����ҵ�񵥾ݶ�Ӧ�����¶�����¼ xwq 2007-9-4
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	public ActionVO queryNewestActionByPk_bill(String pk_bill)
			throws BusinessException {
		String sql = "select * from fbm_action where isnull(dr,0)=0 and isnew = 'Y' and pk_bill = '"
				+ pk_bill + "' ";
		BaseDAO dao = new BaseDAO();
		ActionVO[] vos = (ActionVO[]) ((List<ActionVO>) (dao.executeQuery(sql,
				new BeanListProcessor(ActionVO.class))))
				.toArray(new ActionVO[0]);
		if (vos != null && vos.length > 0) {
			return vos[0];
		}
		return null;
	}
	/**
	 * 
	 * ����where������ѯ��Ӧ��ActionVO
	 * added by wes @ 2008-04-07
	 * @param whereClause
	 * @return
	 */
	public ActionVO[] queryActionByWhereClause(String whereClause)
			throws BusinessException {
		BaseDAO dao = new BaseDAO();
		List list = (List) dao.retrieveByClause(ActionVO.class, whereClause);
		if (null != list && list.size() != 0) {
			return (ActionVO[]) list.toArray(new ActionVO[list.size()]);
		}
		return null;
	}
	
	/**
	 *
	 * ���ö�������Ϊ��ʷ���� xwq 2007-8-31
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	public void disableHistoryAction(String pk_source) throws BusinessException {
		String sql = "update fbm_action set isnew='N' where pk_source=?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_source);
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql, parameter);
	}

	/**
	 * �Ӹ���pk_registers�й��˳�����������pk_registers
	 *
	 * @param registers
	 * @return
	 * @throws BusinessException
	 */
	public String[] getRegistersLastestAction(String register, String endstatus)
			throws BusinessException {
		String sql = new StringBuffer()
				.append("select a.pk_source from fbm_action a ")
				.append(
						" where a.serialnum = (select max(b.serialnum) from fbm_action b where b.pk_source =a.pk_source) and a.pk_source in ")
				.append(register).append(" and a.endstatus = '").append(
						endstatus).append("'").toString();

		BaseDAO dao = new BaseDAO();
		String[] rs = (String[]) dao.executeQuery(sql,
				new ResultSetProcessor() {

					private static final long serialVersionUID = 1L;

					public Object handleResultSet(ResultSet rs)
							throws SQLException {
						ArrayList<String> list = new ArrayList<String>();
						String value;
						while (rs.next()) {
							value = rs.getString(1);
							if (value != null) {
								value = value.trim();
							}
							list.add(value);
						}
						return (String[]) list.toArray(new String[0]);
					}
				});
		return rs;
	}
	
	/**
	 * TODO ʹ�ã�ҵ�񵥾�ID �� ��������ݲ�ѯ��ʼ״̬
	 * 
	 * @param pk_bill
	 * @param actioncode
	 * @return 
	 * @throws BusinessException
	 * 
	 * @author zhouwb 2008-10-09
	 */
	public String getBeginstatusByBillaction(String pk_bill, String actioncode) throws BusinessException
	{
		String sSql = "SELECT beginstatus FROM fbm_action WhERE pk_bill='" + pk_bill + 
			"' and actioncode='" + actioncode + "' and isnull(dr,0)=0";
		
		return (String)new BaseDAO().executeQuery(sSql, new ColumnProcessor());
	}
	
	/**
	 * TODO ʹ�ã�ҵ�񵥾�ID �� ��������ݲ�ѯ����״̬
	 * 
	 * @param pk_bill
	 * @param actioncode
	 * @return 
	 * @throws BusinessException
	 * 
	 * @author zhouwb 2008-10-09
	 */
	public String getEndstatusByBillaction(String pk_bill, String actioncode) throws BusinessException
	{
		String sSql = "SELECT endstatus FROM fbm_action WhERE pk_bill='" + pk_bill + 
			"' and actioncode='" + actioncode + "' and isnull(dr,0)=0";
		
		return (String)new BaseDAO().executeQuery(sSql, new ColumnProcessor());
	}
}