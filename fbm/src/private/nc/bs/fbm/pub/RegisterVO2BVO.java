package nc.bs.fbm.pub;

import java.util.ArrayList;
import java.util.List;

import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.HYBillVO;

public class RegisterVO2BVO {

	/**
	 * VO转换类，由RegisterVO 转为相应的表体VO
	 * 
	 * @param bodyvo
	 *            需要的表体VO
	 * @param regvos
	 *            需要被转换成表体VO的源VO
	 * @return
	 * @throws BusinessException
	 */
	public static SuperVO[] getCoverVO(SuperVO bodyvo, SuperVO[] sourcevos)
			throws BusinessException {
		if (bodyvo instanceof StorageBVO) {
			return regvo2StorageBvo((RegisterVO[]) sourcevos);
		} else if (bodyvo instanceof ReliefBVO) {
			return regvo2ReliefBVO((RegisterVO[]) sourcevos);
		} else if (bodyvo instanceof ReckonBVO) {
			return regvo2ReckonBVO((AccountDetailVO[]) sourcevos);
		} else if (bodyvo instanceof ReturnBVO) {
			return regvo2ReturnBVO((RegisterVO[]) sourcevos);
		}
		return null;
	}
	
	/**
	 * 由RegisterVO 转换为 内部托管 表体VO StorageBVO
	 * 
	 * @param supervos
	 * @param regvos
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public static SuperVO[] regvo2StorageBvo(RegisterVO[] regvos)
			throws BusinessException {
		List list = new ArrayList();
		for (int i = 0; i < regvos.length; i++) {
			StorageBVO bodyvo = new StorageBVO();
			RegisterVO regvo = regvos[i];
			bodyvo.setPayunit(regvo.getPayunit());
			bodyvo.setPk_source(regvo.getPk_register());
			bodyvo.setPk_baseinfo(regvo.getPk_baseinfo());
			bodyvo.setPaybankacc(regvo.getPaybankacc());
			bodyvo.setReceiveunit(regvo.getReceiveunit());
			bodyvo.setReceivebankacc(regvo.getReceivebankacc());
			list.add(bodyvo);
		}
		return (SuperVO[]) list.toArray(new StorageBVO[0]);
	}
	
	/**
	 * 由RegisterVO 转换为 调剂 表体VO ReliefBVO
	 * 
	 * @param supervos
	 * @param regvos
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public static SuperVO[] regvo2ReliefBVO(RegisterVO[] regvos)
			throws BusinessException {
		List list = new ArrayList();
		for (int i = 0; i < regvos.length; i++) {
			ReliefBVO bodyvo = new ReliefBVO();
			RegisterVO regvo = regvos[i];
			bodyvo.setPayunit(regvo.getPayunit());
			bodyvo.setPk_source(regvo.getPk_register());
			bodyvo.setPk_baseinfo(regvo.getPk_baseinfo());
			bodyvo.setReceiveunit(regvo.getReceiveunit());
			bodyvo.setPaybillunit(regvo.getPaybillunit());
			bodyvo.setHoldunit(regvo.getHoldunit());
			list.add(bodyvo);
		}
		return (SuperVO[]) list.toArray(new ReliefBVO[0]);
	}

	/**
	 * 由RegisterVO 转换为 清算 表体VO ReliefBVO
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public static SuperVO[] regvo2ReckonBVO(AccountDetailVO[] regvos)
			throws BusinessException {
		List list = new ArrayList();
		for (int i = 0; i < regvos.length; i++) {
			ReckonBVO bodyvo = new ReckonBVO();
			AccountDetailVO detailvo = regvos[i];
			bodyvo.setPk_source(detailvo.getPk_register());
			bodyvo.setPk_baseinfo(detailvo.getPk_baseinfo());
			bodyvo.setPk_detail(detailvo.getPk_detail());
			list.add(bodyvo);
		}
		return (SuperVO[]) list.toArray(new ReckonBVO[0]);
	}

	/**
	 * 由RegisterVO 转换为 退票 表体VO ReturnBVO
	 * 
	 * @param supervos
	 * @param regvos
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public static SuperVO[] regvo2ReturnBVO(RegisterVO[] regvos)
			throws BusinessException {
		List list = new ArrayList();
		for (int i = 0; i < regvos.length; i++) {
			ReturnBVO bodyvo = new ReturnBVO();
			RegisterVO regvo = regvos[i];
			bodyvo.setPayunit(regvo.getPayunit());
			bodyvo.setPk_source(regvo.getPk_register());
			bodyvo.setPk_baseinfo(regvo.getPk_baseinfo());
			list.add(bodyvo);
		}
		return (SuperVO[]) list.toArray(new ReturnBVO[0]);
	}
	
	/**
	 * 执行公式
	 * 
	 * @param supervo
	 */
	public static void execFormula(SuperVO[] supervo) {
		nc.vo.pub.formulaset.FormulaParseFather f = new nc.bs.pub.formulaparse.FormulaParse();
		nc.vo.pub.SuperVOUtil.execFormulaWithVOs(supervo, new String[] {
				"payunit -> getColValue(fbm_baseinfo,payunit,pk_baseinfo,pk_baseinfo)",
				"payunitname -> getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,getColValue(fbm_baseinfo,payunit,pk_baseinfo,pk_baseinfo))",
				"paybankacc->getColValue(fbm_baseinfo,paybankacc,pk_baseinfo,pk_baseinfo)",
				"paybankaccname->getColValue(v_fbm_bankaccbas,account,pk_bankaccbas,getColValue(fbm_baseinfo,paybankacc,pk_baseinfo,pk_baseinfo))",
				"receiveunit->getColValue(fbm_baseinfo,receiveunit,pk_baseinfo,pk_baseinfo)",
				"receiveunitname->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,getColValue(fbm_baseinfo,receiveunit,pk_baseinfo,pk_baseinfo))",
				"receivebankacc->getColValue(fbm_baseinfo,receivebankacc,pk_baseinfo,pk_baseinfo)",
				"receivebankaccname->getColValue(v_fbm_bankaccbas,account,pk_bankaccbas,getColValue(fbm_baseinfo,receivebankacc,pk_baseinfo,pk_baseinfo))",
				"invoiceunit->getColValue(fbm_baseinfo,invoiceunit,pk_baseinfo,pk_baseinfo)",
				"invoiceunitname->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,getColValue(fbm_baseinfo,invoiceunit,pk_baseinfo,pk_baseinfo))",
				"holdunit->getColValue(fbm_register,holdunit,pk_register,pk_source)",
				"holdunitname->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,getColValue(fbm_register,holdunit,pk_register,pk_source))",
				"keepplace->getColValue(fbm_register,keepunit,pk_register,pk_source)",
				"keepplacename->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,getColValue(fbm_register,keepunit,pk_register,pk_source))",
				"paybillunit->getColValue(fbm_register,paybillunit,pk_register,pk_source)",
				"pk_curr->getColValue(fbm_baseinfo,pk_curr,pk_baseinfo,pk_baseinfo)",
				"currtypename->getColValue(bd_currtype,currtypename,pk_currtype,getColValue(fbm_baseinfo,pk_curr,pk_baseinfo,pk_baseinfo))",
				"keepbank->getColValue(fbm_register,keepunit,pk_register,pk_source)",
				"keepbankname->iif(getColValue(v_fbm_bankaccbas,account,pk_bankaccbas,keepplace) != null,getColValue(v_fbm_bankaccbas,account,pk_bankaccbas,keepplace), getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,keepplace))",
				"keepbankname->iif(keepbankname != null,keepbankname, getColValue(bd_bankdoc,bankdocname,pk_bankdoc,keepplace))",
				"paybillunitname->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,paybillunit)", }, null, f);
		
	}
	
	/**
	 * 主子表业务单据在执行 审核、弃审、确认与取消确认等操作时，通过公式查询出参照名称 解决前台调用后台效率问题。
	 * 
	 * @param retObj
	 */
	public static void actionOPExecFormula(HYBillVO retObj) {
		CircularlyAccessibleValueObject[] childvos = (retObj).getChildrenVO();
		execFormula((SuperVO[]) childvos);
		(retObj).setChildrenVO(childvos);
	}
	
	
}