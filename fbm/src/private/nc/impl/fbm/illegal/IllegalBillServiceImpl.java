package nc.impl.fbm.illegal;

import java.util.List;

import nc.itf.fbm.illegal.IIllegalBillService;
import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.pub.BusinessException;

/**
 * �Ƿ�Ʊ�ݹ����ṩ�����ʵ��
 *
 * @author wues
 *
 */
public class IllegalBillServiceImpl implements IIllegalBillService {

	public IllegalBillServiceImpl() {
		super();
	}

	/**
	 * ���ӷǷ�Ʊ��
	 */
	public String addIllegalBill(IllegalVO vo) throws BusinessException {
		// ��֤�����ӵ�Ʊ����Ϣ�Ƿ�Ϸ�
		validateBillInfo(vo);
		//��֤��Ʊ�ݱ���Ƿ��Ѿ�����
		String msg = validateBeforeInsert(vo);
		if (null != msg)//��������򽫷Ƿ�Ʊ�ݵ���������
			return msg;
		IllegalDAO dao = new IllegalDAO();
		return dao.insertIllegal(vo);
	}

	/**
	 * ɾ���Ƿ�Ʊ��
	 */
	public void deleteIllegalBill(String fbmbillno) throws BusinessException {
		IllegalDAO dao = new IllegalDAO();
		dao.deleteIllegalBillByRegister(fbmbillno);
	}

	public String queryFbmBillNoByPk_Source(String pk_baseinfo)
			throws BusinessException {
		IllegalDAO dao = new IllegalDAO();
		return dao.queryFbmBillNoByPk_Source(pk_baseinfo);
	}

	// ��֤Ʊ����Ϣ�ĺϷ���
	private void validateBillInfo(IllegalVO vo) throws BusinessException {
		if (null == vo) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36200835","UPP36200835-000002")/* @res"�����ӵķǷ�Ʊ����Ϣ������Ϊ��"*/);
		}
		if (null == vo.getFbmbillno() || "".equals(vo.getFbmbillno().trim())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36200835","UPP36200835-000003")/* @res"�����ӵķǷ�Ʊ�ݱ�Ų�����Ϊ��"*/);
		}
	}

	public String validateBeforeInsert(IllegalVO vo) throws BusinessException {
		IllegalDAO dao = new IllegalDAO();
		List billList = dao.queryIllegalBillByNo(vo.getFbmbillno());
		//��Ʊ�ݱ���Ѿ�����
		if (null != billList && billList.size() != 0)
			return ((IllegalVO)billList.get(0)).getPk_illegal();
		else
			return null;
	}

}