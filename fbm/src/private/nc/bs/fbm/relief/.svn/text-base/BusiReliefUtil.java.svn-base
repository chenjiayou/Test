package nc.bs.fbm.relief;

import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;

public class BusiReliefUtil {
	/**
	 * 将ActionParamVO进行拆分 分成自己的票据和其他的票据
	 *
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public static List<BusiActionParamVO>[] splitParams(BusiActionParamVO[] params)
			throws BusinessException {
		if (null == params || params.length == 0) {
			return null;
		}
		//第0个list存放自己的票，第一个元素存放他人调剂过来的票
		List<BusiActionParamVO>[] list = new ArrayList[2];

		for (int i = 0; i < params.length; i++) {
			if (isSelfBill(params[i])) {
				// 是自己的票
				if (null == list[0]) {
					list[0] = new ArrayList<BusiActionParamVO>();
				}
				list[0].add(params[i]);
			} else {
				if (null == list[1]) {
					list[1] = new ArrayList<BusiActionParamVO>();
				}
				list[1].add(params[i]);
			}
		}
		return list;
	}
	/**
	 * 根据票据PK和持票单位PK判断是否已经存在
	 *
	 * @param pk_baseInfo
	 * @param holdUnit
	 * @return
	 */
	public static boolean isSelfBill(BusiActionParamVO param)
			throws BusinessException {

		String pk_register = param.getPk_source();
		String holdUnit = ((ReliefVO)param.getSuperVO()).getReliefunit();

		RegisterVO vo = null;

		BaseDAO dao = new BaseDAO();
		if (null == param.getRegisterVO()) {
			vo = (RegisterVO) dao.retrieveByPK(RegisterVO.class, pk_register);
		} else {
			vo = param.getRegisterVO();
		}
		// 根据pk_register取得相应的收票登记单与holdunit对比，不能pk_baseinfo，因为有可能收到回头票
		if (null == vo || "".equals(vo.getPrimaryKey())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045","UPP36201045-000009")/* @res"根据收票登记单号无法查到对应的收票信息，数据错误。"*/);
		}
		//此时vo上的持票单位已经变成了调剂到的单位，所以需要通过pk_corp取客商
		CommonDAO commonDAO = new CommonDAO();
		if (!holdUnit.equals(commonDAO.queryCustByCorp(vo.getPk_corp()))) {
			// 不是自己的票据
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 根据fieldValue将SQL查询包上单引号
	 * 例：fieldValue = "555";
	 * 返回： "'555'",增强正常拼接时的正确性和易读性
	 * @param fieldCode
	 * @param fieldValue
	 * @return
	 */
	public static String enclose(String fieldValue){
		if (null == fieldValue || "".equals(fieldValue.trim())){
			return "''";
		} else {
			return "'" + fieldValue + "'";
		}
	}
}
