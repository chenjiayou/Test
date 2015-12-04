/**
 *
 */
package nc.bs.fbm.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.trade.business.HYPubBO;
import nc.bs.trade.comcheckunique.BillIsUnique;
import nc.itf.cdm.util.CommonUtil;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

/**
 * <p>
 * 票据基本信息服务类
 * 收票/开票中都需要特殊处理
 * <p>创建人：lpf
 * <b>日期：2007-9-4
 *
 */
public class BaseInfoBO {

	/**
	 *
	 */
	public BaseInfoBO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * 保存票据基本信息
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-4
	 * @param baseinfoVo
	 * @return String 基本信息主键
	 * @throws BusinessException
	 */
	public String saveBaseinfo(BaseinfoVO baseinfoVo) throws BusinessException{
		String key=baseinfoVo.getPk_baseinfo();

		HYPubBO bo=new HYPubBO();
		if(CommonUtil.isNull(key)){
			key=bo.insert(baseinfoVo);
		}else{
			bo.update(baseinfoVo);
		}
		return key;
	}

	/**
	 * <p>
	 * 校验票据编号是否重复
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-4
	 * @param baseinfoVo
	 * @throws BusinessException
	 */
	public void checkisFBMBillnoUnique(BaseinfoVO baseinfoVo) throws BusinessException {
		HYBillVO billVo = new HYBillVO();
		billVo.setParentVO(baseinfoVo);

		BillIsUnique unique = new BillIsUnique();

		ArrayList<String[]> unlist = new ArrayList<String[]>();
		unlist.add(new String[] { BaseinfoVO.FBMBILLNO });

		try {
			unique.checkBillIsUnique(billVo, unlist);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000331")/* @res"该单据中的票据编号已经存在，请重新录入"*/);
		}
	}

	public BaseinfoVO queryByFbmbillno(String fbmbillno) throws BusinessException{
		BaseDAO dao = new BaseDAO();
		if(fbmbillno!=null){
			fbmbillno= fbmbillno.trim();
		}
		List<BaseinfoVO> baseinfoVos = (List<BaseinfoVO>)dao.retrieveByClause(BaseinfoVO.class, "isnull(dr,0)=0 and fbmbillno='" + fbmbillno +"'");
		if(baseinfoVos == null || baseinfoVos.size() == 0){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000332")/* @res"票据编号不存在"*/);
		}
		return (BaseinfoVO)baseinfoVos.get(0);
	}

	/**
	 * 重写上面方法:根据票据编号查询相应的票据基本信息，如果查询不到不抛异常
	 * @param fbmbillno
	 * @return
	 * @throws BusinessException
	 */
	public BaseinfoVO queryBaseInfoByFbmbillno(String fbmbillno) throws BusinessException{
		BaseDAO dao = new BaseDAO();
		if(fbmbillno!=null){
			fbmbillno= fbmbillno.trim();
		}
		List<BaseinfoVO> baseinfoVos = (List<BaseinfoVO>)dao.retrieveByClause(BaseinfoVO.class, "isnull(dr,0)=0 and fbmbillno='" + fbmbillno +"'");
		if(baseinfoVos == null || baseinfoVos.size() == 0){
			return null;
		}
		return (BaseinfoVO)baseinfoVos.get(0);
	}
	

	/**
	 * 根据票据编号取得票据收付属性
	 * @param fbmbillno
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, String> queryOrientByBillno(String[] billno) throws BusinessException{
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
				//查找不到不做处理
			}
		}

		return ret;
	}

}