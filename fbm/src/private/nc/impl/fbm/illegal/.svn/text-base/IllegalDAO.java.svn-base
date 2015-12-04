package nc.impl.fbm.illegal;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.pub.BusinessException;


/**
 * 功能：
       非法票据DAO
 * 日期：2007-10-17
 * 程序员：wues
 */
public class IllegalDAO {
	private BaseDAO dao = null;
	
	public IllegalDAO(){
		super();
		dao = new BaseDAO();
	}
	/**
	 * 非法票据插入
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	public String insertIllegal(IllegalVO vo) throws BusinessException {
		return dao.insertVO(vo);
	}
	/**
	 * 根据票据编号查询非法票据
	 * @param billNo
	 * @return
	 * @throws BusinessException
	 */
	public List queryIllegalBillByNo(String billNo) throws BusinessException {
		return (List) dao.retrieveByClause(IllegalVO.class,
				" isnull(dr,0)=0 and fbmbillno='" + billNo + "' ");
	}
	
	/**
	 * 根据票据编号删除非法票据 
	 * @param billNo
	 * @return
	 * @throws BusinessException
	 */
	public void deleteIllegalBillByRegister(String fbmbillno) throws BusinessException {
		 dao.deleteByClause(IllegalVO.class," isnull(dr,0)=0 and fbmbillno='" + fbmbillno + "' ");
	}
	
	public String queryFbmBillNoByPk_Source(String pk_baseinfo) throws BusinessException {
		BaseinfoVO baseinfoVO = (BaseinfoVO)dao.retrieveByPK(BaseinfoVO.class, pk_baseinfo );
		return baseinfoVO.getFbmbillno();
	}
}
