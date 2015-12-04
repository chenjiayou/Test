package nc.impl.fbm.custdatainterface;

import java.sql.SQLException;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.vo.bd.b08.CubasdocVO;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.cdm.proxy.OuterProxy;
import nc.vo.pub.BusinessException;

/**
 * <p>
 * ���̺ϲ���
 * <p>
 * �������ڣ�(2008-1-4 10:48:30)
 *
 * @author lisg
 */
public class CustCombinImpl implements nc.vo.bd.b09.CustCombinExec {

	/**
	 * CustCombinBO ������ע�⡣
	 */
	public CustCombinImpl() {
		super();
	}

	/**
	 * <p>
	 * �ϲ� ��Ӧ�� (���̹�����)
	 * <p>
	 * �������ڣ�(2003-3-20 15:01:32)
	 *
	 * @param src_mpk ���ϲ��ļ�¼ key
	 * @param des_mpk �ϲ����ļ�¼ key
	 */
	public void combin_GYS(java.lang.String src_mpk, java.lang.String des_mpk) throws BusinessException {
		combinCus(src_mpk, des_mpk);
	}

	/**
	 * <p>
	 * �ϲ� �ͻ�
	 * <p>
	 * �������ڣ�(2003-3-20 15:01:32)
	 *
	 * @param src_mpk ���ϲ��ļ�¼ key
	 * @param des_mpk �ϲ����ļ�¼ key
	 */
	public void combin_KH(java.lang.String src_mpk, java.lang.String des_mpk) throws BusinessException {
		combinCus(src_mpk, des_mpk);
	}

	private void combinCus(java.lang.String src_mpk, java.lang.String des_mpk) throws BusinessException {
		try {
			CustCombinDAO dao = new CustCombinDAO();
			String err = "";
			String err2 = "";
			nc.vo.bd.b09.CumandocVO srcManVO = OuterProxy.getCustManDocQuery().queryCustVOByPK(src_mpk);
			nc.vo.bd.b09.CumandocVO desManVO = OuterProxy.getCustManDocQuery().queryCustVOByPK(des_mpk);
			CubasdocVO srcBasVO = OuterProxy.getCuBasDocQry().findCubasdocVOByPK(srcManVO.getPk_cubasdoc());
			CubasdocVO desBasVO = OuterProxy.getCuBasDocQry().findCubasdocVOByPK(desManVO.getPk_cubasdoc());
			boolean isUpdate = true;

			final boolean isSrcInner = ((CustBasVO) srcBasVO.getCustBasVO()).getCustprop().intValue() != 0;
			final boolean isdesInner = ((CustBasVO) desBasVO.getCustBasVO()).getCustprop().intValue() != 0;
			if (isSrcInner != isdesInner) {
				err2 += "[" + srcManVO.getCustname() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000230")/*@res "]��["*/ + desManVO.getCustname() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000231")/*@res "]���̲�ͬΪ�ڲ���ͬΪ�ⲿ,�޷��ϲ�,���޸Ŀ�������"*/;
				isUpdate = false;
			}
			if ((isSrcInner == isSrcInner) && isSrcInner) {
				final boolean isEqualsCorp1 = ((CustBasVO) srcBasVO.getCustBasVO()).getPk_corp1().equals(((CustBasVO) desBasVO.getCustBasVO()).getPk_corp1());
				if (!isEqualsCorp1) {
					err2 += "[" + srcManVO.getCustname() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000230")/*@res "]��["*/ + desManVO.getCustname() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000232")/*@res "]���̶�Ӧ��˾��ͬ,���޸Ŀ�������"*/;
					isUpdate = false;
				}
			}

			if (isUpdate) {
				updateCustPK(dao, srcManVO, desManVO);
			}

			if (err.length() > 0 || err2.length() > 0) {
				String multiErr = "";
				if (err.length() > 0) {
					multiErr += err + nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("10083405", "UPP10083405-000261")/* @res"Դ����Ϊ��Ͷ�ʵ�λ,Ŀ�Ŀ���Ϊ�ǳ�Ͷ�ʵ�λ,���ܽ��кϲ�!" */;
				}
				multiErr += err2;
				throw new BusinessException(multiErr);
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

	}

	/**
	 * <p>
	 * <p>
	 * ���ߣ�hzguo <br>
	 * ���ڣ�2006-6-10
	 *
	 * @param dao
	 * @param srcvo
	 * @param desvo
	 * @throws SQLException
	 */
	private void updateCustPK(CustCombinDAO dao, nc.vo.bd.b09.CumandocVO srcvo, nc.vo.bd.b09.CumandocVO desvo) throws BusinessException {
		// fbm_register
		String[] upparam = new String[] { desvo.getPk_cubasdoc(), srcvo.getPk_cubasdoc() };
		String sqlPaybillunit = "update fbm_register set paybillunit = ? where paybillunit = ?";
		String sqlHoldunit = "update fbm_register set holdunit = ? where holdunit = ?";
		String sqlKeepunit = "update fbm_register set keepunit = ? where keepunit = ?";
		dao.updateString(sqlPaybillunit, upparam);
		dao.updateString(sqlHoldunit, upparam);
		dao.updateString(sqlKeepunit, upparam);

		// fbm_accept
		sqlHoldunit = "update fbm_accept set holdunit = ? where holdunit = ?";
		dao.updateString(sqlHoldunit, upparam);

		// fbm_baseinfo
		String sqlInvoiceunit = "update fbm_baseinfo set invoiceunit = ? where invoiceunit = ?";
		String sqlPayunit = "update fbm_baseinfo set payunit = ? where payunit = ?";
		String sqlReceiveunit = "update fbm_baseinfo set receiveunit = ? where receiveunit = ?";
		sqlKeepunit = "update fbm_baseinfo set keepunit = ? where keepunit = ?";
		dao.updateString(sqlInvoiceunit, upparam);
		dao.updateString(sqlPayunit, upparam);
		dao.updateString(sqlReceiveunit, upparam);
		dao.updateString(sqlKeepunit, upparam);

		// fbm_storage
		sqlKeepunit = "update fbm_storage set keepunit = ? where keepunit = ?";
		String sqlOutputunit = "update fbm_storage set outputunit = ? where outputunit = ?";
		dao.updateString(sqlKeepunit, upparam);
		dao.updateString(sqlOutputunit, upparam);

		// fbm_endore
		String sqlEndorser = "update fbm_endore set endorser = ? where endorser = ?";
		String sqlendorsee = "update fbm_endore set endorsee = ? where endorsee = ?";
		dao.updateString(sqlEndorser, upparam);
		dao.updateString(sqlendorsee, upparam);

		// fbm_impawn
		String sqlImpawnbank = "update fbm_impawn set impawnbank = ? where impawnbank = ?";
		String sqlImpawnunit = "update fbm_impawn set impawnunit = ? where impawnunit = ?";
		dao.updateString(sqlImpawnbank, upparam);
		dao.updateString(sqlImpawnunit, upparam);

		// fbm_relief
		String sqlReliefunit = "update fbm_relief set reliefunit = ? where reliefunit = ?";
		dao.updateString(sqlReliefunit, upparam);

		// fbm_reckon
		String sqlReckonunit = "update fbm_reckon set reckonunit = ? where reckonunit = ?";
		dao.updateString(sqlReckonunit, upparam);

		// fbm_reckalarm
		sqlReckonunit = "update fbm_reckalarm set reckonunit = ? where reckonunit = ?";
		dao.updateString(sqlReckonunit, upparam);


	}


	/**
	 * <p>
	 * �ϲ� ���� ÿ����������
	 * <p>
	 * �������ڣ�(2003-3-20 15:01:32)
	 *
	 * @param src_mpks ���ϲ��ļ�¼ key src_mpks[0] ��־λ 2 �ͻ� src_mpks[1] ��־λ 3 ��Ӧ��
	 * @param des_mpks[0] ��־λ 2 �ͻ� des_mpks[1] ��־λ 3 ��Ӧ��
	 */
	public void combin_KS(java.lang.String[] src_mpks, java.lang.String[] des_mpks) throws BusinessException {
		for (int i = 0; i < src_mpks.length; i++) {
			combinCus(src_mpks[i], des_mpks[i]);
		}
	}

	/**
	 * �� Ӧ��/Ӧ�� �����ӵĽӿ� �����̻��������� �������� �������ڣ�(2003-4-14 13:45:15)
	 */
	public void setBasdocKey(java.lang.String srcKey, java.lang.String desKey) {
	}

	public void updateString(String sql, String[] param) throws DAOException {
		SQLParameter parameter = new SQLParameter();
		for (int i = 0; i < param.length; i++) {
			parameter.addParam(param[i]);
		}
		new BaseDAO().executeUpdate(sql, parameter);
	}
}