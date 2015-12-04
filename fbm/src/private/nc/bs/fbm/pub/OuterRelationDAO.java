package nc.bs.fbm.pub;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.itf.cdm.util.CommonUtil;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;

/**
 * 外部单据关联关系查询
 * @author xwq
 *
 */
public class OuterRelationDAO {

	private BaseDAO dao = null ;
	
	public OuterRelationDAO() {
		// TODO Auto-generated constructor stub
		super();
		dao = new BaseDAO();
	}
	
	/**
	 * 
	 * 根据票据编号，查询票据最新详细信息
	 * 
	 * @return
	 */
//	public BillWholeVO queryBillWholeInfo(String fbmbillno,String pk_corp) throws BusinessException{
//		StringBuffer sb = new StringBuffer();
//		sb.append(" select fbm_baseinfo.pk_baseinfo,fbm_baseinfo.fbmbillno,fbm_baseinfo.pk_curr,fbm_baseinfo.moneyy,fbm_baseinfo.receiveunit,fbm_baseinfo.payunit,fbm_baseinfo.receivebankacc,fbm_baseinfo.paybankacc");
//		sb.append(",fbm_outer.pk_outerbill_h,fbm_outer.pk_outerbill_b,fbm_outer.outerdjdl,fbm_outer.outerbilltype,fbm_outer.outerstatus,fbm_outer.pk_busibill");
//		sb.append(",fbm_register.pk_register,fbm_register.paybillunit,fbm_register.holdunit,fbm_register.pk_billtypecode,fbm_register.pk_corp,fbm_register.sfflag,fbm_register.securitymoney,fbm_register.securityaccount,fbm_register.poundagemoney");
//		sb.append(",fbm_action.beginstatus,fbm_action.endstatus,fbm_action.billtype");
//		
//		sb.append(" from fbm_baseinfo join fbm_register on(fbm_baseinfo.pk_baseinfo = fbm_register.pk_baseinfo)");
//		sb.append(" join fbm_action on(fbm_register.pk_register = fbm_action.pk_source)");
//		sb.append(" left join fbm_outer on(fbm_register.pk_register = fbm_outer.pk_busibill)");
//		sb.append(" where fbm_baseinfo.fbmbillno = '" + fbmbillno + "'");
//		sb.append(" and isnull(fbm_baseinfo.dr,0) = 0");
//		sb.append(" and isnull(fbm_register.dr,0) = 0");
//		sb.append(" and isnull(fbm_action.dr,0) = 0");
//		sb.append(" and isnull(fbm_outer.dr,0) = 0");
//		sb.append(" and fbm_action.isnew = 'Y'");
//		//sb.append(" and isnull(fbm_outer.outerdjdl,0) <> 'hj'");//过滤划账结算单
//		//sb.append(" and fbm_register.pk_corp='" + pk_corp +"'");
//		//sb.append(" and ((fbm_outer.pk_billtypecode is null) or fbm_outer.pk_billtypecode in( '"+FbmBusConstant.BILLTYPE_GATHER+"' , '"+ FbmBusConstant.BILLTYPE_INVOICE+"'))");
//		sb.append(" order by fbm_action.serialnum desc");
//		BillWholeVO[] ret = (BillWholeVO[])((List<BillWholeVO>)(dao.executeQuery(sb.toString(),new BeanListProcessor(BillWholeVO.class)))).toArray(new BillWholeVO[0]);;
//		
//		if(ret == null || ret.length == 0){
//			return null;
//		}
//		
//		return ret[0];
//	}
	
	/**
	 * 删除外部单据与票据的关联关系
	 * @param pk_bill_b
	 * @throws BusinessException
	 */
	public void deleteRelation(String pk_bill_b) throws BusinessException{
		dao.deleteByClause(OuterVO.class, " pk_outerbill_b='" + pk_bill_b + "'");
	}
	/**
	 * 删除外部单据与票据的关联关系
	 * @param pk_bill_b
	 * @throws BusinessException
	 */
	public void deleteRelation(String[] pk_bill_b) throws BusinessException{
		String sql = " pk_outerbill_b in(" + CommonUtil.buildSqlConditionForIn(pk_bill_b) + ")";
		dao.deleteByClause(OuterVO.class,sql);
	}
	/**
	 * 删除外部单据与票据的关联关系
	 * @param pk_bill_b
	 * @throws BusinessException
	 */
	public void deleteRelationByhpk(String pk_bill_h) throws BusinessException{
		dao.deleteByClause(OuterVO.class, " pk_outerbill_h='" + pk_bill_h + "'");
	}
	
	/**
	 * 外部单据生效
	 * @param pk_bill_h
	 * @throws BusinessException
	 */
	public void effectRelation(String pk_bill_b) throws BusinessException{
		dao.executeUpdate("update fbm_outer set outerstatus = '" + FbmBusConstant.OUTERBILL_OVER + "' where pk_outerbill_b = '" + pk_bill_b +"'");
	}
	
	/**
	 * 外部单据取消生效
	 * @param pk_bill_h
	 * @throws BusinessException
	 */
	public void uneffectRelation(String pk_bill_b) throws BusinessException{
		dao.executeUpdate("update fbm_outer set outerstatus = '" + FbmBusConstant.OUTERBILL_USE + "' where pk_outerbill_b = '" + pk_bill_b +"'");
	}
	
	/**
	 * 查询登记对应的外部关联
	 * 如果要查询质押单据对应的物权担保则传递的参数为质押单PK
	 * @param pk_register
	 * @param pk_billtypecode
	 * @return
	 * @throws BusinessException
	 */
	public OuterVO[] queryByPkBusibill(String pk_busibill) throws BusinessException{
		String sql = "select * from fbm_outer where pk_busibill='" + pk_busibill + "'  order by pk_outer desc" ;
		OuterVO[] ret = (OuterVO[])((List<OuterVO>)(dao.executeQuery(sql,new BeanListProcessor(OuterVO.class)))).toArray(new OuterVO[0]);
		if(ret == null || ret.length ==0){
			return null;
		}
		return ret;
	}
	/**
	 * 根据外部pk,查询出整个VO
	 * @param pk_impawn
	 * @return
	 * @throws BusinessException
	 */
	public OuterVO queryByOuterPK(String pk_impawn) throws BusinessException{
		String sql = "select * from fbm_outer where pk_outerbill_h='" + pk_impawn + "'  order by pk_outer desc" ;
		OuterVO[] ret = (OuterVO[])((List<OuterVO>)(dao.executeQuery(sql,new BeanListProcessor(OuterVO.class)))).toArray(new OuterVO[0]);
		if(ret == null || ret.length ==0){
			return null;
		}
		return ret[0];
	}
	/**
	 * 插入外部vo对象
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	public String insertOuterVO(OuterVO vo) throws BusinessException {
		return dao.insertVO(vo);
	}
	
	/**
	 * 根据业务单据PK来查询收付款单主表PK数组
	 * @param pk_busibill
	 * @return
	 * @throws BusinessException
	 */
	public String[] queryArapBillPks(String pk_busibill)throws BusinessException {
			BaseDAO dao = new BaseDAO();
			String sql= "select distinct pk_outerbill_h from fbm_outer where pk_busibill = '"+ pk_busibill +"'";
			List<String> list = (List<String>)dao.executeQuery(sql, new ColumnListProcessor());
			if(list != null){
				return (String[])list.toArray(new String[0]);
			}
			return null;
	}
	
	/**
	 * 判断付票登记生成的划账结算单是否已生效
	 * @param pk_register
	 * @return
	 * @throws BusinessException
	 */
	public boolean isHjEffect(String pk_register) throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String sql = "select pk_outer from fbm_outer where isnull(dr,0)=0 and pk_busibill = '"+pk_register+"' and pk_billtypecode = '36GL' and outerdjdl = 'hj' and outerstatus = 'over'";
		List<String> list = (List<String>)dao.executeQuery(sql, new ColumnListProcessor());
		
		return list != null && list.size() > 0;
	}
	
	/**
	 * 根据外部单据子表PK查询
	 * @param pk_b
	 * @return
	 * @throws BusinessException
	 */
	public OuterVO queryBypk_b(String pk_b) throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String sql = "select * from fbm_outer where isnull(dr,0)=0 and pk_outerbill_b = '"+pk_b+"'";
		OuterVO[] ret = (OuterVO[])((List<OuterVO>)(dao.executeQuery(sql,new BeanListProcessor(OuterVO.class)))).toArray(new OuterVO[0]);
		if(ret == null || ret.length ==0){
			return null;
		}
		return ret[0];
	}
	
	/**
	 * 根据外部单据主表PK查询
	 * @param pk_outerbill_h
	 * @return
	 * @throws BusinessException
	 */
	public OuterVO[] queryByPk_h(String pk_outerbill_h) throws BusinessException{
		BaseDAO dao = new BaseDAO();
		String sql = "select * from fbm_outer where isnull(dr,0)=0 and pk_outerbill_h = '"+pk_outerbill_h+"'";
		return (OuterVO[])((List<OuterVO>)(dao.executeQuery(sql,new BeanListProcessor(OuterVO.class)))).toArray(new OuterVO[0]);
	}
	
	
}
