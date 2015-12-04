/**
 *
 */
package nc.ui.fbm.pub;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.vo.bd.notetype.INotetypeConst;
import nc.vo.bd.notetype.NotetypeVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.IBillStatus;

/**
 * 功能说明： <b>创建日期：2007-8-13 <b>创 建 人：lpf <b>功能描述： <b>其 他： <b>
 */
public class ComBoxUtil {

	/**
	 *
	 */
	public ComBoxUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 汇票票据类型
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getAcceptanceType()
			throws BusinessException {
		String sql = " select * from bd_notetype where pk_corp = '0001' order by notetypecode ";
		List volist = FBMProxy.queryListVO(sql, NotetypeVO.class);
		NotetypeVO[] vos = (NotetypeVO[]) volist.toArray(new NotetypeVO[] {});

		if (vos == null || vos.length == 0) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000073")/*
																										 * @res
																										 * "找不到票据类型"
																										 */);
		}

		List<DefaultConstEnum> list = new ArrayList<DefaultConstEnum>();

		for (int i = 0; i < vos.length; i++) {
			if (vos[i].getNoteclass().intValue() == INotetypeConst.NOTECLASS_COMMERCIALDRAFT) {
				DefaultConstEnum e = new DefaultConstEnum(
						vos[i].getPrimaryKey(), vos[i].getNotetypename());
				list.add(e);
			}
		}

		if (list == null || list.size() == 0) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000074")/*
																										 * @res
																										 * "找不到商业汇票的票据类型"
																										 */);
		}

		return (DefaultConstEnum[]) list.toArray(new DefaultConstEnum[0]);
		// TODO Auto-generated method stub
		// return new DefaultConstEnum[] {
		// new DefaultConstEnum(FbmBusConstant.ACCEPTANCE_BANK,
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000056")/*
		// @res"银行承兑汇票"*/),
		// new DefaultConstEnum(FbmBusConstant.ACCEPTANCE_BUSI,
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000057")/*
		// @res"商业承兑汇票"*/) };
	}

	/**
	 * 中心统管业务区分票据操作类型
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getOpBillType() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.BILL_PRIVACY,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000075")/*
																											 * @res
																											 * "私有"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.BILL_UNISTORAGE,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000061")/*
																											 * @res
																											 * "统管"
																											 */) };
	}

	/**
	 * 收票来源类型
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getGathertype() {
		// TODO Auto-generated method stub
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.GATHER_TYPE_INPUT,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000072")/*
																									 * @res
																									 * "收票录入"
																									 */),
				new DefaultConstEnum(
						FbmBusConstant.GATHER_TYPE_ENDORE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000073")/*
																									 * @res
																									 * "付票生成"
																									 */),
				new DefaultConstEnum(
						FbmBusConstant.GATHER_TYPE_RELIEF,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000074")/*
																									 * @res
																									 * "调剂生成"
																									 */),
				new DefaultConstEnum(
						FbmBusConstant.GATHER_TYPE_UNISTORAGE,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000076")/*
																											 * @res
																											 * "统管生成"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.GATHER_TYPE_RETURN,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000077")/*
																											 * @res
																											 * "退票生成"
																											 */) };
	}

	/**
	 * 票据状态
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getFBillStatus() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmStatusConstant.REGISTER,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000075")/*
																									 * @res
																									 * "已登记"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_ENDORE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000076")/*
																									 * @res
																									 * "在背书"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_ENDORE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000077")/*
																									 * @res
																									 * "已背书"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_DISCOUNT,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000078")/*
																									 * @res
																									 * "在贴现"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_DISCOUNT,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000079")/*
																									 * @res
																									 * "已贴现"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_COLLECTION,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000080")/*
																									 * @res
																									 * "在托收"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_COLLECTION,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000081")/*
																									 * @res
																									 * "已托收"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_BANK_KEEP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000082")/*
																									 * @res
																									 * "在银行存"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_BANK_KEEP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000083")/*
																									 * @res
																									 * "已银行存放"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_BANK_BACK,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000084")/*
																									 * @res
																									 * "在银行领用"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_INNER_KEEP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000085")/*
																									 * @res
																									 * "在内部存放"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_INNER_KEEP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000086")/*
																									 * @res
																									 * "已内部存放"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_INNER_BACK,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000087")/*
																									 * @res
																									 * "在内部领用"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_DISABLE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000088")/*
																									 * @res
																									 * "已作废"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_IMPAWN,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000089")/*
																									 * @res
																									 * "在质押"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_IMPAWN,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000090")/*
																									 * @res
																									 * "已质押"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_GATHER,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000091")/*
																									 * @res
																									 * "在收票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_RETURN,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000092")/*
																									 * @res
																									 * "在退票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_RETURN,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000093")/*
																									 * @res
																									 * "已退票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_INVOICE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000094")/*
																									 * @res
																									 * "在开票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_INVOICE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000095")/*
																									 * @res
																									 * "已开票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_PAY,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000096")/*
																									 * @res
																									 * "在付款"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_PAY,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000097")/*
																									 * @res
																									 * "已付款"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_PAYBILL,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000098")/*
																									 * @res
																									 * "在付票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_PAYBILL,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000099")/*
																									 * @res
																									 * "已付票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_RELIEF_KEEP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000100")/*
																									 * @res
																									 * "在调剂存放"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_RELIEF_KEEP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000101")/*
																									 * @res
																									 * "已调剂存放"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_DESTROY,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000102")/*
																									 * @res
																									 * "已核销"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_RELIEF,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000103")/*
																									 * @res
																									 * "已调剂"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_RELIEF,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000104")/*
																									 * @res
																									 * "在调剂领用"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_CENTER_USE,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000078")/*
																											 * @res
																											 * "已中心使用"
																											 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_CENTER_RETURN,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000079")/*
																											 * @res
																											 * "在中心退出"
																											 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_CENTER_RETURN,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000080")/*
																											 * @res
																											 * "已中心退出"
																											 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_UNIT_RETURN,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000081")/*
																											 * @res
																											 * "在单位退入"
																											 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_UNIT_RETURN,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000082")/*
																											 * @res
																											 * "已单位退入"
																											 */), };
	}

	/**
	 * <p>
	 * 给收票查询提供的票据状态常量定义
	 * <p>
	 * 作者：lpf 日期：2007-11-22
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getGatherFBillStatus() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmStatusConstant.REGISTER,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000075")/*
																									 * @res
																									 * "已登记"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_ENDORE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000076")/*
																									 * @res
																									 * "在背书"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_ENDORE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000077")/*
																									 * @res
																									 * "已背书"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_DISCOUNT,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000078")/*
																									 * @res
																									 * "在贴现"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_DISCOUNT,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000079")/*
																									 * @res
																									 * "已贴现"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_COLLECTION,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000080")/*
																									 * @res
																									 * "在托收"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_COLLECTION,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000081")/*
																									 * @res
																									 * "已托收"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_BANK_KEEP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000082")/*
																									 * @res
																									 * "在银行存放"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_BANK_KEEP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000083")/*
																									 * @res
																									 * "已银行存放"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_BANK_BACK,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000084")/*
																									 * @res
																									 * "在银行领用"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_INNER_KEEP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000085")/*
																									 * @res
																									 * "在内部存放"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_INNER_KEEP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000086")/*
																									 * @res
																									 * "已内部存放"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_INNER_BACK,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000087")/*
																									 * @res
																									 * "在内部领用"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_DISABLE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000088")/*
																									 * @res
																									 * "已作废"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_IMPAWN,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000089")/*
																									 * @res
																									 * "在质押"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_IMPAWN,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000090")/*
																									 * @res
																									 * "已质押"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_GATHER,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000091")/*
																									 * @res
																									 * "在收票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_RETURN,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000092")/*
																									 * @res
																									 * "在退票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_RETURN,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000093")/*
																									 * @res
																									 * "已退票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_RELIEF_KEEP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000100")/*
																									 * @res
																									 * "在调剂存放"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_RELIEF_KEEP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000101")/*
																									 * @res
																									 * "已调剂存放"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_DESTROY,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000102")/*
																									 * @res
																									 * "已核销"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_RELIEF,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000103")/*
																									 * @res
																									 * "已调剂领用"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_RELIEF,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000104")/*
																									 * @res
																									 * "在调剂领用"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_CENTER_USE,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000078")/*
																											 * @res
																											 * "已中心使用"
																											 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_CLEAR,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000083")/*
																											 * @res
																											 * "已冲销"
																											 */) };
	}

	// 给开票查询提供的票据状态常量定义
	public static DefaultConstEnum[] getInvoiceFBillstatus() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmStatusConstant.ON_INVOICE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000094")/*
																									 * @res
																									 * "在开票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_INVOICE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000095")/*
																									 * @res
																									 * "已开票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_PAY,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000096")/*
																									 * @res
																									 * "在付款"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_PAY,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000097")/*
																									 * @res
																									 * "已付款"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_PAYBILL,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000098")/*
																									 * @res
																									 * "在付票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_PAYBILL,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000099")/*
																									 * @res
																									 * "已付票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.ON_RETURN,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000092")/*
																									 * @res
																									 * "在退票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_RETURN,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000093")/*
																									 * @res
																									 * "已退票"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_DESTROY,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000102")/*
																									 * @res
																									 * "已核销"
																									 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_CENTER_USE,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000078")/*
																											 * @res
																											 * "已中心使用"
																											 */),
				new DefaultConstEnum(
						FbmStatusConstant.HAS_CLEAR,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000083")/*
																											 * @res
																											 * "已冲销"
																											 */) };
	}

	/**
	 * 调剂清算方向
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getReckonDirection() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.RELIEF_DIRECTION_IN,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000105")/*
																									 * @res
																									 * "调剂存放"
																									 */),
				new DefaultConstEnum(
						FbmBusConstant.RELIEF_DIRECTION_OUT,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000106")/*
																									 * @res
																									 * "调剂领用"
																									 */) };
	}

	// /**
	// * 票据存放单据类型
	// */
	// public static DefaultConstEnum[] getStorageBusType(){
	// return new DefaultConstEnum[]{
	// new DefaultConstEnum(FbmBusConstant.KEEP_CENTRE_IN,"中心存放单"),
	// new DefaultConstEnum(FbmBusConstant.KEEP_BANK_IN,"银行存放单"),
	// new DefaultConstEnum(FbmBusConstant.KEEP_CENTRE_OUT,"中心领用单"),
	// new DefaultConstEnum(FbmBusConstant.KEEP_BANK_OUT,"银行领用单")
	// };
	// }

	/**
	 * 票据存放方式
	 */
	public static DefaultConstEnum[] getStorageKeepType() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.KEEP_TYPE_STORE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000107")/*
																									 * @res
																									 * "保管托管"
																									 */),
				new DefaultConstEnum(
						FbmBusConstant.KEEP_TYPE_RELIEF,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000105")/*
																									 * @res
																									 * "调剂托管"
																									 */) };
	}

	/**
	 * <p>
	 * 获得单据状态
	 * <p>
	 * 作者：bsrl <br>
	 */
	public static DefaultConstEnum[] getBillStatus(String billType) {
		Vector<DefaultConstEnum> v = new Vector<DefaultConstEnum>();
		v.add(new DefaultConstEnum(
				new Integer(IFBMStatus.NOPASS),
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000108")/*
																							 * @res
																							 * "审批未通过"
																							 */));
		v.add(new DefaultConstEnum(
				new Integer(IFBMStatus.CHECKPASS),
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000109")/*
																							 * @res
																							 * "审批通过"
																							 */));
		v.add(new DefaultConstEnum(
				new Integer(IFBMStatus.CHECKGOING),
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000110")/*
																							 * @res
																							 * "审批进行中"
																							 */));
		// 内部存放单,查询条件单据状态字段,去掉"提交状态"
		// v.add(new DefaultConstEnum(new Integer(IFBMStatus.COMMIT),
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000111")/*
		// @res"提交状态"*/));
		v.add(new DefaultConstEnum(
				new Integer(IFBMStatus.FREE),
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000112")/*
																							 * @res
																							 * "自由态"
																							 */));

		// 银行托收
		if (billType.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.Transact),
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000113")/*
																								 * @res
																								 * "已办理"
																								 */));
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.Create),
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000114")/*
																								 * @res
																								 * "已生成单据"
																								 */));
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.Disable),
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000088")/*
																								 * @res
																								 * "已作废"
																								 */));
			v.add(new DefaultConstEnum(
					IFBMStatus.HAS_UNIT_VOUCHER,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000121")/*
																								 * @res
																								 * "已单位制证"
																								 */));
			v.add(new DefaultConstEnum(
					IFBMStatus.HAS_VOUCHER_VAR,
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPT36201020-000072")/*
																										 * @res
																										 * "已制证"
																										 */));
		}
		// 贴现办理
		if (billType.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)
				|| billType.equals(FbmBusConstant.BILLTYPE_BILLPAY)
				|| billType.equals(FbmBusConstant.BILLTYPE_INVOICE)) {
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.Create),
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000114")/*
																								 * @res
																								 * "已生成单据"
																								 */));
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.HAS_VOUCHER_VAR),
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPT36201020-000072")/*
																										 * @res
																										 * "已制证"
																										 */));
		}
		// 贴现申请
		if (billType.equals(FbmBusConstant.BILLTYPE_DISCOUNT_APP)) {
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.ON_TRANSACT),
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000115")/*
																								 * @res
																								 * "在办理"
																								 */));
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.HAS_TRANSACT),
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000113")/*
																								 * @res
																								 * "已办理"
																								 */));
		}

		if (billType.equals(FbmBusConstant.BILLTYPE_RETURN)) {
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.TRANSFORM),
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000116")/*
																								 * @res
																								 * "已转出"
																								 */));
		}

		if (billType.equals(FbmBusConstant.BILLTYPE_INNERKEEP)) {
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.SUBMIT),
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000084")/*
																										 * @res
																										 * "已提交"
																										 */));
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.INPUT_SUCCESS),
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000117")/*
																								 * @res
																								 * "已确认"
																								 */));
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.RETURNED),
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000119")/*
																								 * @res
																								 * "已退回"
																								 */));
		}

		if (billType.equals(FbmBusConstant.BILLTYPE_INNERBACK)) {
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.SUBMIT),
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000084")/*
																										 * @res
																										 * "已提交"
																										 */));
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.OUTPUT_SUCCESS),
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000118")/*
																								 * @res
																								 * "已确认"
																								 */));
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.RETURNED),
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000119")/*
																								 * @res
																								 * "已退回"
																								 */));
		}

		if (billType.equals(FbmBusConstant.BILLTYPE_GATHER)) {
			v.add(new DefaultConstEnum(
					new Integer(IFBMStatus.INIT),
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000123")/*
																								 * @res
																								 * "暂存"
																								 */));
		}

		return (DefaultConstEnum[]) v.toArray(new DefaultConstEnum[0]);
	}

	/**
	 * <p>
	 * 退票类型定义
	 * <p>
	 * 作者：lpf 日期：2007-8-31
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getReturnType(boolean isUnit) {
		if (isUnit) {
			return new DefaultConstEnum[] {
					new DefaultConstEnum(
							FbmBusConstant.BACK_TYPE_DISABLE,
							nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000124")/*
																										 * @res
																										 * "废票退票"
																										 */),
					new DefaultConstEnum(
							FbmBusConstant.BACK_TYPE_ENDORE,
							nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000125")/*
																										 * @res
																										 * "背书退票"
																										 */),
					new DefaultConstEnum(
							FbmBusConstant.BACK_TYPE_INVOICE,
							nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000126")/*
																										 * @res
																										 * "付票退票"
																										 */),
					new DefaultConstEnum(
							FbmBusConstant.BACK_TYPE_GATHER,
							nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000127")/*
																										 * @res
																										 * "收票退票"
																										 */),
					new DefaultConstEnum(
							FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT,
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000085")/*
																												 * @res
																												 * "单位退入"
																												 */) };
		} else {
			return new DefaultConstEnum[] {
					new DefaultConstEnum(
							FbmBusConstant.BACK_TYPE_DISABLE,
							nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000124")/*
																										 * @res
																										 * "废票退票"
																										 */),
					new DefaultConstEnum(
							FbmBusConstant.BACK_TYPE_ENDORE,
							nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000125")/*
																										 * @res
																										 * "背书退票"
																										 */),
					new DefaultConstEnum(
							FbmBusConstant.BACK_TYPE_INVOICE,
							nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000126")/*
																										 * @res
																										 * "付票退票"
																										 */),
					new DefaultConstEnum(
							FbmBusConstant.BACK_TYPE_GATHER,
							nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000127")/*
																										 * @res
																										 * "收票退票"
																										 */),
					new DefaultConstEnum(
							FbmBusConstant.BACK_TYPE_UNISTORAGE,
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000086")/*
																												 * @res
																												 * "中心退出"
																												 */) };
		}

	}

	/**
	 * <p>
	 * 担保方式定义
	 * <p>
	 * 作者：lpf 日期：2007-9-4
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getAssureType() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.ASSURETYPE_ASSURE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000128")/*
																									 * @res
																									 * "担保"
																									 */),
				new DefaultConstEnum(
						FbmBusConstant.ASSURETYPE_BAIL,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000129")/*
																									 * @res
																									 * "保证金"
																									 */),
				new DefaultConstEnum(
						FbmBusConstant.ASSURETYPE_CREDIT,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000130")/*
																									 * @res
																									 * "信用"
																									 */),
				new DefaultConstEnum(
						FbmBusConstant.ASSURETYPE_IMPAWN,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000131")/*
																									 * @res
																									 * "抵押"
																									 */),
				new DefaultConstEnum(
						FbmBusConstant.ASSURETYPE_PLEDGE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000020")/*
																									 * @res
																									 * "质押"
																									 */) };
	}

	public static DefaultConstEnum[] getCCType() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.CCTYPE_CORP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000132")/*
																									 * @res
																									 * "企业授信"
																									 */),
				new DefaultConstEnum(
						FbmBusConstant.CCTYPE_GROUP,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000133")/*
																									 * @res
																									 * "集团授信"
																									 */),
				new DefaultConstEnum(
						FbmBusConstant.CCTYPE_NONE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000134")/*
																									 * @res
																									 * "不使用授信"
																									 */) };
	}

	/**
	 * 贴现办理结果
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getDiscountResult() {
		// TODO Auto-generated method stub
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.DISCOUNT_RESULT_SUCCESS,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000135")/*
																									 * @res
																									 * "成功"
																									 */),
				new DefaultConstEnum(
						FbmBusConstant.DISCOUNT_RESULT_DISABLE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000007")/*
																									 * @res
																									 * "作废"
																									 */) };
	}

	/**
	 * 票据质押-状态
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getImpawnStatus() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						IBillStatus.FREE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000112")/*
																									 * @res
																									 * "自由态"
																									 */),
				new DefaultConstEnum(
						IBillStatus.CHECKGOING,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000110")/*
																									 * @res
																									 * "审批进行中"
																									 */),
				new DefaultConstEnum(
						IBillStatus.NOPASS,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000136")/*
																									 * @res
																									 * "审批不通过"
																									 */),
				new DefaultConstEnum(
						IBillStatus.CHECKPASS,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000109")/*
																									 * @res
																									 * "审批通过"
																									 */),
				new DefaultConstEnum(
						IFBMStatus.IMPAWN_BACK,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000137")/*
																									 * @res
																									 * "已回收"
																									 */) };

	}

	/**
	 * 调剂出库单据状态
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getReliefStatus() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						IBillStatus.FREE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000112")/*
																									 * @res
																									 * "自由态"
																									 */),
				new DefaultConstEnum(
						IBillStatus.CHECKPASS,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000109")/*
																									 * @res
																									 * "审批通过"
																									 */),
				// new DefaultConstEnum(IFBMStatus.HAS_VOUCHER,
				// nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000120")/*
				// @res"已中心制证"*/),
				// new DefaultConstEnum(IFBMStatus.HAS_UNIT_VOUCHER,
				// nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000121")/*
				// @res"已单位制证"*/),
				new DefaultConstEnum(
						IBillStatus.CHECKGOING,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000110")/*
																									 * @res
																									 * "审批进行中"
																									 */),
				new DefaultConstEnum(
						IBillStatus.NOPASS,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000136")/*
																									 * @res
																									 * "审批不通过"
																									 */)
		// adv from hzg&zhj just make a new output item
		// new DefaultConstEnum(IFBMStatus.HAS_OUTPUT, "已调剂出库"),
		};
	}

	/**
	 * <p>
	 * 　背书办理单据状态
	 * <p>
	 * 作者：wangyg 日期：2008-4-2
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getEndoreStatus() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						IBillStatus.FREE,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000112")/*
																									 * @res
																									 * "自由态"
																									 */),
				new DefaultConstEnum(
						IBillStatus.CHECKPASS,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000109")/*
																									 * @res
																									 * "审批通过"
																									 */),
				new DefaultConstEnum(
						IBillStatus.CHECKGOING,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000110")/*
																									 * @res
																									 * "审批进行中"
																									 */),
				new DefaultConstEnum(
						IFBMStatus.HAS_VOUCHER_VAR,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPT36201020-000072")/*
																											 * @res
																											 * "已制证"
																											 */),
				new DefaultConstEnum(
						IFBMStatus.HAS_CLEAR,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000083")/*
																											 * @res
																											 * "已冲销"
																											 */), };
	}

	public static DefaultConstEnum[] getSyscode() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.ENDORE_SYS_INPUT,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000087")/*
																											 * @res
																											 * "手工录入"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.ENDORE_SYS_CMP,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000088")/*
																											 * @res
																											 * "现金平台"
																											 */) };

	}

	/**
	 * 内部账户入账设置--入账账户参照下拉列表
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getAccRef() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.ACCRULE_ACCREF_BILL,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000089")/*
																											 * @res
																											 * "内部票据户"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.ACCRULE_ACCREF_CURRENT,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000090")/*
																											 * @res
																											 * "内部活期户"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.ACCRULE_NONE,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000091")/*
																											 * @res
																											 * "不入账"
																											 */)

		};
	}

	public static DefaultConstEnum[] getReckonInAccRef() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.ACCRULE_ACCREF_CURRENT,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000090")/*
																											 * @res
																											 * "内部活期户"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.ACCRULE_NONE,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000091")/*
																											 * @res
																											 * "不入账"
																											 */)

		};
	}

	public static DefaultConstEnum[] getReckonOutAccRef() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.ACCRULE_ACCREF_BILL,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000089")/*
																											 * @res
																											 * "内部票据户"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.ACCRULE_NONE,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000091")/*
																											 * @res
																											 * "不入账"
																											 */)

		};
	}

	/**
	 * 内部账户入账设置---入账金额下拉列表.
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getAccMny() {
		return new DefaultConstEnum[] { new DefaultConstEnum(
				FbmBusConstant.ACCRULE_ACCMNY_PMJE,
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000237")/*
																									 * @res
																									 * "票面金额"
																									 */),
		// new DefaultConstEnum(FbmBusConstant.ACCRULE_ACCMNY_YE,"票面金额-贴现息")
		};
	}

	/**
	 * 内部账户入账设置---单据名称下拉列表
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getAccRuleName() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.LIQUIDATE,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000092")/*
																											 * @res
																											 * "调剂清算"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.RELIEF,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000093")/*
																											 * @res
																											 * "票据调剂"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.INNERKEEP,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPT3620ADD-000238")/*
																											 * @res
																											 * "内部托管"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.INNERBACK,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPT3620ADD-000239")/*
																											 * @res
																											 * "内部领用"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.BILLPAY,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPT36201510-000045")/*
																											 * @res
																											 * "票据付款"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.INVOICE,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPT36201505-000089")/*
																											 * @res
																											 * "付票登记"
																											 */) };
	}

	/**
	 * 内部账户入账设置--入账账户名称
	 * 
	 * @return
	 */
	public static DefaultConstEnum[] getAccName() {
		return new DefaultConstEnum[] {
				new DefaultConstEnum(
						FbmBusConstant.ACC_IN,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPT3620ADD-000240")/*
																											 * @res
																											 * "转入账户"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.ACC_OUT,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPT3620ADD-000241")/*
																											 * @res
																											 * "转出账户"
																											 */),
				new DefaultConstEnum(
						FbmBusConstant.ACC_INNER,
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0000438")/*
																										 * @res
																										 * "内部账户"
																										 */) };
	}

}