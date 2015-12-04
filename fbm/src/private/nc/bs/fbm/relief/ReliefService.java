package nc.bs.fbm.relief;

import java.util.ArrayList;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cdm.util.SqlUtil;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;


public class ReliefService {

	public ReliefService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 弃审时置回被调剂单位
	 * @throws BusinessException 
	 */
	public void updateHoldUnitForUnApprove(HYBillVO reliefVo) throws BusinessException{
		if(reliefVo==null||reliefVo.getParentVO()==null||CommonUtil.isNull(reliefVo.getChildrenVO())){
			return;
		}
		ReliefBVO[] bodyVos = (ReliefBVO[]) reliefVo.getChildrenVO();
		ArrayList<String> registerList = new ArrayList<String>();
		for (int i = 0; i < bodyVos.length; i++) {
			String sourcePk = bodyVos[i].getPk_source();
			if(!CommonUtil.isNull(sourcePk)&&!registerList.contains(sourcePk)){
				registerList.add(sourcePk);
			}
		}
		String[] registerPks = null;
		if(registerList.size()>0){
			registerPks = registerList.toArray(new String[0]);
		}

		if(!CommonUtil.isNull(registerPks)){
			String sqlin = SqlUtil.getInClause(registerPks);
			String sql = " update fbm_register set holdunit=(select distinct pk_cubasdoc from bd_cubasdoc " 
				+"inner join fbm_register a on(bd_cubasdoc.pk_corp1=a.pk_corp) " 
				+"and a.pk_register=fbm_register.pk_register and isnull(a.dr,0)=0) where pk_register "
					+ sqlin + " ";
			try {
				new BaseDAO().executeUpdate(sql);
			} catch (DAOException e) {
				Logger.error(sql+e.getMessage(), e);
				throw new BusinessException(e.getMessage(),e);
			}
		}
	}

	/**
	 * 审核时修改引用收票登记单的持票单位为调剂单位，
	 * 弃审时置回被调剂单位
	 */
	public void updateHoldUnitForApprove(HYBillVO reliefVo) throws BusinessException {
		if(reliefVo==null||reliefVo.getParentVO()==null||CommonUtil.isNull(reliefVo.getChildrenVO())){
			return;
		}
		ReliefBVO[] bodyVos = (ReliefBVO[]) reliefVo.getChildrenVO();
		String reliefUnit = ((ReliefVO)reliefVo.getParentVO()).getReliefunit();
		if(CommonUtil.isNull(reliefUnit)){
			return;
		}
		ArrayList<String> registerList = new ArrayList<String>();
		for (int i = 0; i < bodyVos.length; i++) {
			String sourcePk = bodyVos[i].getPk_source();
			if(!CommonUtil.isNull(sourcePk)&&!registerList.contains(sourcePk)){
				registerList.add(sourcePk);
			}
		}
		String[] registerPks = null;
		if(registerList.size()>0){
			registerPks = registerList.toArray(new String[0]);
		}
		//锁收票PK
		if(!CommonUtil.isNull(registerPks)){
			String sqlin = SqlUtil.getInClause(registerPks);
			String sql = " update fbm_register set holdunit='"+reliefUnit +"' where pk_register "+sqlin+" ";
			try {
				new BaseDAO().executeUpdate(sql);
			} catch (DAOException e) {
				Logger.error(sql+e.getMessage(), e);
				throw new BusinessException(e.getMessage(),e);
			}
		}
	}
}
