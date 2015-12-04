/**
 *
 */
package nc.ui.fbm.gather;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 收票登记VO转换类
 * <p>
 * 创建人：lpf <b>日期：2007-8-29
 * 
 */
public final class RegisterVOConverter {

	/**
	 * 
	 */
	public RegisterVOConverter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * VO转换调用的公用方法
	 * <p>
	 * 作者：lpf 日期：2007-8-29
	 * 
	 * @param RegisterVO
	 * @param billtype
	 * @return
	 * @throws BusinessException
	 */
	public HYBillVO convertTool(RegisterVO[] RegisterVO, String billtype)
			throws BusinessException {
		if (CommonUtil.isNull(RegisterVO)) {
			return null;
		}
		HYBillVO returnVo = null;
		if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_BANKBACK)
				|| billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_BANKKEEP)) {
			returnVo = RegisterVOsToStorageVOs(RegisterVO, billtype);
		} else if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_RETURN)) {
			returnVo = RegisterVOsToReturnVos(RegisterVO);
		} else if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_DISCOUNT_APP)) {
			returnVo = RegisterVOsToDiscountVos(RegisterVO);
		} else if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {
			returnVo = RegisterVOsToDiscountTVos(RegisterVO);

		} else if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {
			returnVo = RegisterVOsToConsignVos(RegisterVO);
		} else if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_BILLPAY)) {
			returnVo = RegisterVOsToAcceptVOs(RegisterVO);
		} else if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_IMPAWN)) {
			returnVo = RegisterVOsToImpawnVOs(RegisterVO);
		} else if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_INNERBACK)) {
			returnVo = RegisterVOSToInnerBackVO(RegisterVO, billtype);
		}

		return returnVo;
	}

	/**
	 * 收票登记-内部领用VO转换
	 * 
	 * @param registerVO
	 * @param billtype
	 * @return
	 */
	private HYBillVO RegisterVOSToInnerBackVO(RegisterVO[] registerVO,
			String billtype) {
		HYBillVO storageVos = new HYBillVO();
		StorageVO storageHeadVo = new StorageVO();
		storageHeadVo.setPk_billtypecode(billtype);

		String validStatus = FbmStatusConstant.HAS_INNER_KEEP;

		storageHeadVo.setVoperatorid(ClientInfo.getUserPK());
		storageHeadVo.setDoperatedate(ClientInfo.getCurrentDate());

		storageHeadVo.setPk_corp(ClientInfo.getCorpPK());

		storageHeadVo.setInputtype(FbmBusConstant.KEEP_TYPE_STORE);// 保存类型为已内部存放

		storageHeadVo.setInputdate(ClientInfo.getCurrentDate());
		storageHeadVo.setOutputdate(ClientInfo.getCurrentDate());

		storageHeadVo.setVbillstatus(IBillStatus.FREE);
		storageHeadVo.setInputperson(ClientInfo.getUserPK());
		storageHeadVo.setOutputperson(ClientInfo.getUserPK());
		ArrayList<StorageBVO> alBVos = new ArrayList<StorageBVO>();
		for (int i = 0; i < registerVO.length; i++) {
			StorageBVO bvo = new StorageBVO();
			bvo.setPk_source(registerVO[i].getPrimaryKey());
			bvo.setPk_baseinfo(registerVO[i].getPk_baseinfo());
			if (registerVO[i].getSfflag().booleanValue()
					&& registerVO[i].getRegisterstatus().equals(validStatus)) {
				alBVos.add(bvo);
			}
		}
		StorageBVO[] storageBVos = new StorageBVO[alBVos.size()];
		alBVos.toArray(storageBVos);

		storageVos.setParentVO(storageHeadVo);
		storageVos.setChildrenVO(storageBVos);
		return storageVos;
	}

	/**
	 * <p>
	 * 收票登记-质押VO转换 没有批处理
	 * <p>
	 * 作者：lpf 日期：2007-10-9
	 * 
	 * @param registerVO
	 * @return
	 */
	private HYBillVO RegisterVOsToImpawnVOs(RegisterVO[] registerVO) {
		ImpawnVO impawnVo = new ImpawnVO();
		for (int i = 0; i < registerVO.length; i++) {
			if ((registerVO[i].getRegisterstatus().equals(FbmStatusConstant.REGISTER) && registerVO[i].getSfflag().booleanValue())) {
				impawnVo.setPk_source(registerVO[i].getPrimaryKey());
				impawnVo.setPk_baseinfo(registerVO[i].getPk_baseinfo());
				impawnVo.setDoperatedate(ClientInfo.getCurrentDate());
				impawnVo.setVoperatorid(ClientInfo.getUserPK());
				impawnVo.setVbillstatus(IBillStatus.FREE);
				impawnVo.setPk_billtypecode(FbmBusConstant.BILLTYPE_IMPAWN);
				impawnVo.setPk_corp(ClientInfo.getCorpPK());
				impawnVo.setImpawnrate(new UFDouble(0));
			}
		}
		HYBillVO returnVo = new HYBillVO();
		returnVo.setParentVO(impawnVo);
		return returnVo;
	}

	/**
	 * 
	 * <p>
	 * 收票登记-银行托收VO转换 说明：托收单没有批处理
	 * <p>
	 * 作者：lpf 日期：2007-9-6
	 * 
	 * @param RegisterVO
	 * @param billtype
	 * @return
	 */
	private HYBillVO RegisterVOsToConsignVos(RegisterVO[] RegisterVO) {
		CollectionVO collVo = new CollectionVO();
		for (int i = 0; i < RegisterVO.length; i++) {
			if ((RegisterVO[i].getRegisterstatus().equals(FbmStatusConstant.REGISTER)
					|| RegisterVO[i].getRegisterstatus().equals(FbmStatusConstant.HAS_BANK_KEEP) || RegisterVO[i].getRegisterstatus().equals(FbmStatusConstant.HAS_IMPAWN))
					&& RegisterVO[i].getSfflag().booleanValue()) {
				collVo.setDoperatedate(ClientInfo.getCurrentDate());
				collVo.setVoperatorid(ClientInfo.getUserPK());
				collVo.setVbillstatus(new Integer(IBillStatus.FREE));
				collVo.setPk_corp(ClientInfo.getCorpPK());
				collVo.setPk_billtypecode(FbmBusConstant.BILLTYPE_COLLECTION_UNIT);
				collVo.setPk_baseinfo(RegisterVO[i].getPk_baseinfo());
				collVo.setPk_source(RegisterVO[i].getPrimaryKey());
				collVo.setOpbilltype(FbmBusConstant.BILL_PRIVACY);
				break;
			}
		}
		HYBillVO returnVo = new HYBillVO();
		returnVo.setParentVO(collVo);
		return returnVo;
	}

	/**
	 * <p>
	 * 收票登记-贴现申请单VO转换 说明：贴现单没有批处理
	 * <p>
	 * 作者：lpf 日期：2007-9-6
	 * 
	 * @param RegisterVO
	 * @param billtype
	 * @return
	 */
	private HYBillVO RegisterVOsToDiscountVos(RegisterVO[] RegisterVO) {
		DiscountVO discontVo = new DiscountVO();
		for (int i = 0; i < RegisterVO.length; i++) {
			if ((RegisterVO[i].getRegisterstatus().equals(FbmStatusConstant.REGISTER) || RegisterVO[i].getRegisterstatus().equals(FbmStatusConstant.HAS_BANK_KEEP))
					&& RegisterVO[i].getSfflag().booleanValue()) {
				discontVo.setDoperatedate(ClientInfo.getCurrentDate());
				discontVo.setVoperatorid(ClientInfo.getUserPK());
				discontVo.setVbillstatus(new Integer(IBillStatus.FREE));
				discontVo.setPk_corp(ClientInfo.getCorpPK());
				discontVo.setPk_billtypecode(FbmBusConstant.BILLTYPE_DISCOUNT_APP);
				discontVo.setPk_baseinfo(RegisterVO[i].getPk_baseinfo());
				discontVo.setPk_source(RegisterVO[i].getPrimaryKey());
				discontVo.setApplydate(discontVo.getDoperatedate());
				discontVo.setOpbilltype(FbmBusConstant.BILL_PRIVACY);
				// collVo.setMoneyy(RegisterVO[i].getMoneyy());
				// collVo.setMoneyb(RegisterVO[i].getMoneyb());
				// collVo.setMoneyf(RegisterVO[i].getMoneyf());
				// collVo.setFrate(RegisterVO[i].getFrate());
				// collVo.setBrate(RegisterVO[i].getBrate());
				break;
			}
		}
		HYBillVO returnVo = new HYBillVO();
		returnVo.setParentVO(discontVo);
		return returnVo;
	}

	private HYBillVO RegisterVOsToDiscountTVos(RegisterVO[] RegisterVO) {
		DiscountVO discontVo = new DiscountVO();
		for (int i = 0; i < RegisterVO.length; i++) {
			if ((RegisterVO[i].getRegisterstatus().equals(FbmStatusConstant.REGISTER) || RegisterVO[i].getRegisterstatus().equals(FbmStatusConstant.HAS_BANK_KEEP))
					&& RegisterVO[i].getSfflag().booleanValue()) {
				discontVo.setDoperatedate(ClientInfo.getCurrentDate());
				discontVo.setVoperatorid(ClientInfo.getUserPK());
				discontVo.setVbillstatus(new Integer(IBillStatus.FREE));
				discontVo.setPk_corp(ClientInfo.getCorpPK());
				discontVo.setPk_billtypecode(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT);
				discontVo.setPk_baseinfo(RegisterVO[i].getPk_baseinfo());
				discontVo.setPk_source(RegisterVO[i].getPrimaryKey());
				discontVo.setApplydate(discontVo.getDoperatedate());
				discontVo.setOpbilltype(FbmBusConstant.BILL_PRIVACY);
				// collVo.setMoneyy(RegisterVO[i].getMoneyy());
				// collVo.setMoneyb(RegisterVO[i].getMoneyb());
				// collVo.setMoneyf(RegisterVO[i].getMoneyf());
				// collVo.setFrate(RegisterVO[i].getFrate());
				// collVo.setBrate(RegisterVO[i].getBrate());
				break;
			}
		}
		HYBillVO returnVo = new HYBillVO();
		returnVo.setParentVO(discontVo);
		return returnVo;
	}

	/**
	 * <p>
	 * 收票登记-集中退票VO转换，因为收票节点上加入了按钮状态控制，所以能调用此方法的必然存在有效的VO
	 * <p>
	 * 作者：lpf 日期：2007-8-31
	 * 
	 * @param RegisterVO
	 * @param billtype
	 * @return
	 */
	private HYBillVO RegisterVOsToReturnVos(RegisterVO[] registerVO) {
		HYBillVO returnVo = new HYBillVO();
		// 根据单据状态排序
		SuperVO[][] sffilterVos = splitSuperVOsByAttribute(registerVO, RegisterVO.SFFLAG);
		RegisterVO[] sfvalidVo = null;
		for (int i = 0; i < sffilterVos.length; i++) {
			sfvalidVo = (RegisterVO[]) sffilterVos[i];
			if (sfvalidVo[0].getSfflag().booleanValue())
				break;
		}

		SuperVO[][] splitVos = splitSuperVOsByAttribute(sfvalidVo, RegisterVO.REGISTERSTATUS);
		if (splitVos.length == 0)
			return returnVo;
		RegisterVO[] sourceVo = null;
		for (int i = 0; i < splitVos.length; i++) {
			sourceVo = (RegisterVO[]) splitVos[i];
			if (sourceVo[0].getRegisterstatus().equalsIgnoreCase(FbmStatusConstant.REGISTER)
					|| sourceVo[0].getRegisterstatus().equalsIgnoreCase(FbmStatusConstant.HAS_DISCOUNT)
					|| sourceVo[0].getRegisterstatus().equalsIgnoreCase(FbmStatusConstant.HAS_ENDORE)
					|| sourceVo[0].getRegisterstatus().equalsIgnoreCase(FbmStatusConstant.HAS_PAYBILL)) {
				break;
			}
		}

		if (CommonUtil.isNull(sourceVo)) {
			return returnVo;
		}
		ReturnVO returnHeadVo = new ReturnVO();
		returnHeadVo.setDoperatedate(ClientInfo.getCurrentDate());
		returnHeadVo.setVoperatorid(ClientInfo.getUserPK());
		returnHeadVo.setVbillstatus(new Integer(IBillStatus.FREE));
		returnHeadVo.setPk_corp(ClientInfo.getCorpPK());
		returnHeadVo.setReturnperson(ClientInfo.getUserPK());
		returnHeadVo.setDreturndate(ClientInfo.getCurrentDate());
		returnHeadVo.setBusdate(new UFDate(System.currentTimeMillis()));
		returnHeadVo.setPk_billtypecode(FbmBusConstant.BILLTYPE_RETURN);
		String returnType = "";
		if (sourceVo[0].getRegisterstatus().equalsIgnoreCase(FbmStatusConstant.REGISTER)) {
			returnType = FbmBusConstant.BACK_TYPE_GATHER;
		} else if (sourceVo[0].getRegisterstatus().equalsIgnoreCase(FbmStatusConstant.HAS_DISABLE)) {
			returnType = FbmBusConstant.BACK_TYPE_DISABLE;
		} else if (sourceVo[0].getRegisterstatus().equalsIgnoreCase(FbmStatusConstant.HAS_ENDORE)) {
			returnType = FbmBusConstant.BACK_TYPE_ENDORE;
		} else if (sourceVo[0].getRegisterstatus().equalsIgnoreCase(FbmStatusConstant.HAS_PAYBILL)
				|| sourceVo[0].getRegisterstatus().equalsIgnoreCase(FbmStatusConstant.HAS_INVOICE)) {
			returnType = FbmBusConstant.BACK_TYPE_INVOICE;
		}
		returnHeadVo.setReturntype(returnType);
		ReturnBVO[] returnBodyVo = new ReturnBVO[sourceVo.length];
		for (int i = 0; i < sourceVo.length; i++) {
			returnBodyVo[i] = new ReturnBVO();
			returnBodyVo[i].setPk_source(sourceVo[i].getPrimaryKey());
			returnBodyVo[i].setPk_baseinfo(sourceVo[i].getPk_baseinfo());
		}
		returnVo.setParentVO(returnHeadVo);
		returnVo.setChildrenVO(returnBodyVo);
		return returnVo;
	}

	/**
	 * <p>
	 * 收票登记到银行保管VO转换,设置状态过滤
	 * <p>
	 * 作者：lpf 日期：2007-8-29
	 * 
	 * @param RegisterVO
	 * @param billtype
	 * @return
	 * @throws BusinessException
	 */
	private HYBillVO RegisterVOsToStorageVOs(RegisterVO[] RegisterVO,
			String billtype) {
		HYBillVO storageVos = new HYBillVO();
		StorageVO storageHeadVo = new StorageVO();

		storageHeadVo.setPk_billtypecode(billtype);
		String validStatus = "";
		if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_BANKKEEP)) {
			validStatus = FbmStatusConstant.REGISTER;
		} else {
			validStatus = FbmStatusConstant.HAS_BANK_KEEP;
		}

		storageHeadVo.setVoperatorid(ClientInfo.getUserPK());
		storageHeadVo.setDoperatedate(ClientInfo.getCurrentDate());
		storageHeadVo.setPk_corp(ClientInfo.getCorpPK());
		storageHeadVo.setInputdate(ClientInfo.getCurrentDate());
		storageHeadVo.setOutputdate(ClientInfo.getCurrentDate());
		storageHeadVo.setVbillstatus(IBillStatus.FREE);
		storageHeadVo.setInputperson(ClientInfo.getUserPK());
		storageHeadVo.setOutputperson(ClientInfo.getUserPK());
		ArrayList<StorageBVO> alBVos = new ArrayList<StorageBVO>();
		for (int i = 0; i < RegisterVO.length; i++) {
			StorageBVO bvo = new StorageBVO();
			bvo.setPk_source(RegisterVO[i].getPrimaryKey());
			if (RegisterVO[i].getSfflag().booleanValue()
					&& RegisterVO[i].getRegisterstatus().equals(validStatus)) {
				alBVos.add(bvo);
			}
		}
		StorageBVO[] storageBVos = new StorageBVO[alBVos.size()];
		alBVos.toArray(storageBVos);

		storageVos.setParentVO(storageHeadVo);
		storageVos.setChildrenVO(storageBVos);
		return storageVos;
	}

	/**
	 * <p>
	 * 开票VO到承兑VO转换
	 * <p>
	 * 作者：lpf 日期：2007-9-19
	 * 
	 * @param RegisterVO
	 * @param billtype
	 * @return
	 */
	private HYBillVO RegisterVOsToAcceptVOs(RegisterVO[] RegisterVos) {
		HYBillVO acceptVo = new HYBillVO();
		RegisterVO sourceVo = null;
		for (int i = 0; i < RegisterVos.length; i++) {
			if (RegisterVos[i].getRegisterstatus().equalsIgnoreCase(FbmStatusConstant.HAS_PAYBILL)
					&& RegisterVos[i].getSfflag().booleanValue()) {
				sourceVo = RegisterVos[i];
				break;
			}
		}
		if ((sourceVo == null)) {
			return acceptVo;
		}
		AcceptVO acceptHeadVo = new AcceptVO();
		acceptHeadVo.setPk_source(sourceVo.getPrimaryKey());
		acceptHeadVo.setVoperatorid(ClientInfo.getUserPK());
		acceptHeadVo.setDoperatedate(ClientInfo.getCurrentDate());
		acceptHeadVo.setVbillstatus(IBillStatus.FREE);
		acceptHeadVo.setPk_billtypecode(FbmBusConstant.BILLTYPE_BILLPAY);
		acceptHeadVo.setPk_corp(ClientInfo.getCorpPK());
		acceptVo.setParentVO(acceptHeadVo);
		return acceptVo;
	}

	public static SuperVO[][] splitSuperVOsByAttribute(SuperVO[] vos,
			String attribute) {
		if (vos == null
				|| attribute == null
				|| (attribute = attribute.trim()).length() == 0)
			return null;
		HashMap map = new HashMap();
		Object oldValue = null;
		Object value;
		ArrayList list = null;
		ArrayList valueList = new ArrayList();
		Object nul = new Object();
		for (int i = 0; i < vos.length; i++) {
			// if vos[i] is null,filter it
			if (vos[i] == null)
				continue;
			value = vos[i].getAttributeValue(attribute);
			if (value == null)
				value = nul;

			if (value.equals(oldValue)) {
				list.add(vos[i]);
			} else {
				if (map.containsKey(value)) {
					list = (ArrayList) map.get(value);
				} else {
					list = new ArrayList();
					map.put(value, list);
					valueList.add(value);
				}
				list.add(vos[i]);
			}
			oldValue = value;
		}

		if (map.size() > 0) {
			Object[] oss;
			ArrayList tempList;
			list = new ArrayList();
			oss = (Object[]) Array.newInstance(vos.getClass(), map.size());

			Object[] oo = valueList.toArray();
			for (int i = 0; i < oo.length; i++) {
				tempList = (ArrayList) map.get(oo[i]);
				Object[] os = (Object[]) Array.newInstance(vos[0].getClass(), tempList.size());
				oss[i] = tempList.toArray(os);
			}

			return (SuperVO[][]) oss;
		}
		return null;
	}

}
