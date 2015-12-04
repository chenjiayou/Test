package nc.bs.fbm.pub;

import nc.bs.bd.def.ICheckDefquoteCanChg;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.bd.def.DefquoteSuperVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;

/**
 * 自定义项检查引用类
 * 注意不要变更类路径nc.bs.fbm.pub.FBMChkDefquoteCanChg
 * @author xwq
 *
 */
public class FBMChkDefquoteCanChg implements ICheckDefquoteCanChg {

	public boolean checkCanChg(DefquoteSuperVO vodefquote, String tblname)
			throws BusinessException {
		// TODO Auto-generated method stub
		if(vodefquote == null || tblname == null)
			return true;
		
		String pk_defused = vodefquote.getPk_defused();
		
		
		String billtype = null;
		if(pk_defused.endsWith("fbmvuserdef36ga00001")){
			billtype = FbmBusConstant.BILLTYPE_GATHER;
		}else if(pk_defused.endsWith("fbmvuserdef36gb00001")){
			billtype = FbmBusConstant.BILLTYPE_BANKKEEP;
		}else if(pk_defused.endsWith("fbmvuserdef36gc00001")){
			billtype = FbmBusConstant.BILLTYPE_BANKBACK;
		}else if(pk_defused.endsWith("fbmvuserdef36gd00001")){
			billtype = FbmBusConstant.BILLTYPE_INNERKEEP;
		}else if(pk_defused.endsWith("fbmvuserdef36ge00001")){
			billtype = FbmBusConstant.BILLTYPE_INNERBACK;
		}else if(pk_defused.endsWith("fbmvuserdef36gg00001")){
			billtype = FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT;
		}else if(pk_defused.endsWith("fbmvuserdef36gi00001")){
			billtype = FbmBusConstant.BILLTYPE_COLLECTION_UNIT;
		}else if(pk_defused.endsWith("fbmvuserdef36gk00001")){
			billtype = FbmBusConstant.BILLTYPE_LIQUIDATE;
		}else if(pk_defused.endsWith("fbmvuserdef36gl00001")){
			billtype = FbmBusConstant.BILLTYPE_INVOICE;
		}else if(pk_defused.endsWith("fbmvuserdef36gm00001")){
			billtype = FbmBusConstant.BILLTYPE_BILLPAY;
		}else if(pk_defused.endsWith("fbmvuserdef36gn00001")){
			billtype = FbmBusConstant.BILLTYPE_RETURN;
		}else if(pk_defused.endsWith("fbmvuserdef36go00001")){
			billtype = FbmBusConstant.BILLTYPE_IMPAWN;
		}else if(pk_defused.endsWith("fbmvuserdef36gq00001")){
			billtype = FbmBusConstant.BILLTYPE_ENDORE;
		}else if(pk_defused.endsWith("fbmvuserdef36gr00001")){
			billtype = FbmBusConstant.BILLTYPE_RELIEF;
		}
		
		
		String fieldname=vodefquote.getFieldname();
		IUAPQueryBS impl=(IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
		String selectsql="select count(*) from "+tblname+" where (len(rtrim(isnull("+fieldname+",' ')))<>0) and isnull(dr,0)=0 ";
		
		if(billtype != null){
			selectsql = selectsql + " and pk_billtypecode='"+billtype+"'";
		}
		Object o=impl.executeQuery(selectsql,new ColumnProcessor());
		if(o!=null && new Integer(o.toString()).intValue()>0) {
			return false;
		}
		return true;
	}

}
