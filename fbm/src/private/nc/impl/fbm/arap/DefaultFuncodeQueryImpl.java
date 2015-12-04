package nc.impl.fbm.arap;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.itf.fbm.arap.IFuncodeQuery;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;

public class DefaultFuncodeQueryImpl implements IFuncodeQuery {

	public String queryFuncode(String pk_bill_b) throws BusinessException {
		// TODO Auto-generated method stub
		BaseDAO dao = new BaseDAO();
		List list = (List) dao.retrieveByClause(OuterVO.class,
				" pk_outerbill_b = '" + pk_bill_b + "'");
		if (list == null || list.size() == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000371")/* @res"找不到关联数据" */);
		}
		OuterVO vo = (OuterVO) list.get(0);
		String billtype = vo.getPk_billtypecode();

		if (billtype == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000372")/* @res"缺少票据业务单据类型，无法联查" */);
		}

		//单据类型为收票登记单/背书办理单联查收票登记单
		if (billtype.equals(FbmBusConstant.BILLTYPE_GATHER)
				|| billtype.equals(FbmBusConstant.BILLTYPE_ENDORE)) {
			return FbmBusConstant.FUNCODE_GATHER;
		}
		//开票登记单
		if (billtype.equals(FbmBusConstant.BILLTYPE_INVOICE)) {
			return FbmBusConstant.FUNCODE_INVOICE;
		}
		//退票单
		if (billtype.equals(FbmBusConstant.BILLTYPE_RETURN)) {
			return FbmBusConstant.FUNCODE_RETURN;
		}

		if (billtype.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {
			return FbmBusConstant.FUNCODE_CONSIGN;
		}

		if (billtype.equals(FbmBusConstant.BILLTYPE_BILLPAY)) {
			return FbmBusConstant.FUNCODE_BILLPAY;
		}

		if (billtype.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {
			return FbmBusConstant.FUNCODE_DISCOUNT_TRANSACT;
		}
//
//		if (billtype.equals(FbmBusConstant.BILLTYPE_ENDORE)) {
//			// RegisterVO regVO = (RegisterVO)dao.retrieveByPK(RegisterVO.class,
//			// vo.getPk_register());
//			// if(regVO != null){
//			// if(regVO.getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_GATHER)){
//			// return FbmBusConstant.FUNCODE_GATHER;
//			// }else
//			// if(regVO.getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_INVOICE)){
//			// return FbmBusConstant.FUNCODE_INVOICE;
//			// }
//			// }
//			return FbmBusConstant.FUNCODE_ENDORE;
//		}

		return null;
	}

}