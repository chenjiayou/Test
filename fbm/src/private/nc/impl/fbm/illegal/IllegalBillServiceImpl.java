package nc.impl.fbm.illegal;

import java.util.List;

import nc.itf.fbm.illegal.IIllegalBillService;
import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.pub.BusinessException;

/**
 * 非法票据管理提供服务层实现
 *
 * @author wues
 *
 */
public class IllegalBillServiceImpl implements IIllegalBillService {

	public IllegalBillServiceImpl() {
		super();
	}

	/**
	 * 增加非法票据
	 */
	public String addIllegalBill(IllegalVO vo) throws BusinessException {
		// 验证待增加的票据信息是否合法
		validateBillInfo(vo);
		//验证此票据编号是否已经存在
		String msg = validateBeforeInsert(vo);
		if (null != msg)//如果存在则将非法票据的主键返回
			return msg;
		IllegalDAO dao = new IllegalDAO();
		return dao.insertIllegal(vo);
	}

	/**
	 * 删除非法票据
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

	// 验证票据信息的合法性
	private void validateBillInfo(IllegalVO vo) throws BusinessException {
		if (null == vo) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36200835","UPP36200835-000002")/* @res"待增加的非法票据信息不允许为空"*/);
		}
		if (null == vo.getFbmbillno() || "".equals(vo.getFbmbillno().trim())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36200835","UPP36200835-000003")/* @res"待增加的非法票据编号不允许为空"*/);
		}
	}

	public String validateBeforeInsert(IllegalVO vo) throws BusinessException {
		IllegalDAO dao = new IllegalDAO();
		List billList = dao.queryIllegalBillByNo(vo.getFbmbillno());
		//此票据编号已经存在
		if (null != billList && billList.size() != 0)
			return ((IllegalVO)billList.get(0)).getPk_illegal();
		else
			return null;
	}

}