/**
 *
 */
package nc.bs.fbm.invoice;

import java.util.List;

import javax.naming.NamingException;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.fbm.gather.FBMPubQueryDAO;
import nc.bs.fbm.gather.GatherBillService;
import nc.bs.fbm.pub.BaseInfoBO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.logging.Logger;
import nc.bs.trade.business.HYPubBO;
import nc.impl.fbm.ccdatainterface.FBMCcRationManage;
import nc.itf.cdm.util.CommonUtil;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;

/**
 * <p>
 * ��Ʊ��̨������
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-9-6
 *
 */
public class InvoiceBillService {
	/**
	 *
	 */
	public InvoiceBillService() {
	}

	/**
	 * <p>
	 * ����Ʊ�ݻ�����Ϣ
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-9-6
	 * @param invoiceVo
	 * @return
	 * @throws BusinessException
	 */
	public String saveBaseinfo(RegisterVO invoiceVo) throws Exception{
		BaseinfoVO baseVo=invoiceVo.getBaseinfoVO();

//		new GatherBillService().checkRegisterNoUnique(invoiceVo);

		BaseInfoBO baseService=new BaseInfoBO();
//		baseService.checkisFBMBillnoUnique(baseVo);
		//Ϊ�����Ч�ʸ���������������ֱ��ʹ��UAP�ķ�����
		HYPubBO bo = new HYPubBO();
		String sql = " fbmbillno='"+invoiceVo.getFbmbillno()+"' ";
		if(!CommonUtil.isNull(invoiceVo.getPk_baseinfo())){
			sql = sql+" and pk_baseinfo !='"+invoiceVo.getPk_baseinfo()+"' and isnull(dr,0)=0 ";
		}
		SuperVO[] queryVo = bo.queryByCondition(BaseinfoVO.class, sql);
		if(!CommonUtil.isNull(queryVo)){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000331")/* @res"�õ����е�Ʊ�ݱ���Ѿ����ڣ�������¼��"*/);
		}

		String key=baseService.saveBaseinfo(baseVo);

		return key;
	}

	/**
	 * ɾ��Ʊ�ݻ�����Ϣ
	 * @param parentVO
	 * @throws BusinessException
	 */
	public void deleteBaseInfo(RegisterVO parentVO) throws BusinessException {
		if(parentVO==null||parentVO.getPk_baseinfo()==null)
			return;
		new CommonDAO().deleteBaseinfobyPk(parentVO.getPk_baseinfo());
	}

	public void checkDestroyBill(AggregatedValueObject vo) throws BusinessException{
		if(vo==null||vo.getParentVO()==null){
			return;
		}
		RegisterVO registerVo = (RegisterVO) vo.getParentVO();
		String fbillstatus = registerVo.getRegisterstatus();

		String newstatus = queryLatestStatus(registerVo.getPk_baseinfo());
		if(!CommonUtil.isNull(newstatus)){
			fbillstatus = newstatus;
		}
		
		if(CommonUtil.isNull(fbillstatus)||fbillstatus.equals(FbmStatusConstant.HAS_RETURN)){
			return;
		}

		RegisterVO newVo = new CommonDAO().queryLastGather(registerVo.getPk_baseinfo(), registerVo.getPk_corp());

		String curStatus = newVo.getRegisterstatus();
		if(!curStatus.equals(FbmStatusConstant.REGISTER)){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201505","UPP36201505-000004")/* @res"Ʊ�ݵ�ǰ״̬�����ѵǼ�"*/);
		}

		if(!newVo.getSfflag().booleanValue()){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201505","UPP36201505-000005")/* @res"��Ʊ�Ǽǵ���û���ո����޷�����"*/);
		}
	}
	/**
	 *
	 * <p>
	 * ����
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-10-22
	 * @param vo
	 * @throws BusinessException
	 * @return Object[]
	 */
	public Object DestroyBill(AggregatedValueObject vo,String currdate,String operator) throws Exception{
		if(vo==null||vo.getParentVO()==null){
			return null;
		}
		RegisterVO registerVo = (RegisterVO) vo.getParentVO();

		registerVo.setIsverify(UFBoolean.TRUE);
		registerVo.setVerifydate(new UFDate(currdate));
		registerVo.setVerifyman(operator);

		String ccReturnMsg = applyRationBeforeCancelBill(registerVo);

		RegisterVO returnVo = (RegisterVO) new FBMPubQueryDAO()
				.updateVoreturnlatestTs(registerVo, new String[] {
						RegisterVO.ISVERIFY, RegisterVO.VERIFYDATE,
						RegisterVO.VERIFYMAN });
		vo.setParentVO(returnVo);

		new GatherBillService().updateGatherBillDisable(registerVo.getPk_baseinfo(), UFBoolean.TRUE);

		return new Object[] { ccReturnMsg,vo };
	}

	/**
	 *
	 * <p>
	 * ȡ������
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-10-22
	 * @param vo
	 * @throws BusinessException
	 * @throws NamingException
	 * @return Object[]
	 */
	public Object CancelDestroyBill(AggregatedValueObject vo) throws Exception{
		if(vo==null||vo.getParentVO()==null){
			return null;
		}
		RegisterVO registerVo = (RegisterVO) vo.getParentVO();

		String ccReturnMsg = applyRationBeforeDelCancel(registerVo);

		registerVo.setIsverify(UFBoolean.FALSE);
		registerVo.setVerifydate(null);
		registerVo.setVerifyman(null);
		RegisterVO returnVo = (RegisterVO) new FBMPubQueryDAO()
				.updateVoreturnlatestTs(registerVo, new String[] {
						RegisterVO.ISVERIFY, RegisterVO.VERIFYDATE,
						RegisterVO.VERIFYMAN });
		vo.setParentVO(returnVo);

		new GatherBillService().updateGatherBillDisable(registerVo.getPk_baseinfo(), UFBoolean.FALSE);

		return new Object[] { ccReturnMsg,vo };
	}


	/**
	 * <p>
	 * ���ǰ����
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-9-26
	 * @param registerVo
	 * @throws BusinessException
	 */
	public String applyRationBeforeGLApprove(RegisterVO registerVo) throws BusinessException{
		if(registerVo == null)
			return null;
		if(registerVo.getCctype().equals(FbmBusConstant.CCTYPE_NONE))
			return null;

		return new FBMCcRationManage().approveInvoiceBill(registerVo);
	}

	/**
	 * <p>
	 * ����ǰ����
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-9-26
	 * @param registerVo
	 * @throws BusinessException
	 */
	public String applyRationBeforeGLUnApprove(RegisterVO registerVo) throws BusinessException{
		if(registerVo == null)
			return null;
		if(registerVo.getCctype().equals(FbmBusConstant.CCTYPE_NONE))
			return null;

		return new FBMCcRationManage().unApproveInvoiceBill(registerVo);
	}

	/**
	 *
	 * <p>
	 *
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-10-22
	 * @param registerVo
	 * @return
	 * @throws BusinessException
	 */
	private String applyRationBeforeCancelBill(RegisterVO registerVo) throws BusinessException{
		if(registerVo == null)
			return null;
		if(registerVo.getCctype().equals(FbmBusConstant.CCTYPE_NONE))
			return null;

		return new FBMCcRationManage().cancelInvoiceBill(registerVo);
	}

	/**
	 *
	 * <p>
	 *
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-10-22
	 * @param registerVo
	 * @return
	 * @throws BusinessException
	 */
	private String applyRationBeforeDelCancel(RegisterVO registerVo) throws BusinessException{
		if(registerVo == null)
			return null;
		if(registerVo.getCctype().equals(FbmBusConstant.CCTYPE_NONE))
			return null;

		return new FBMCcRationManage().delCancelInvoiceBill(registerVo);
	}

	/**
	 *
	 * <p>
	 * Invoice���ɵ��ݣ���Ϊ���޸�״̬���²�ѯ
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-10-29
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	public AggregatedValueObject generateSFBill(AggregatedValueObject vo) throws BusinessException {
		if(vo!=null&&vo.getParentVO()!=null){
			SuperVO headVo = (SuperVO) vo.getParentVO();

			SuperVO queryVo = new HYPubBO().queryByPrimaryKey(headVo.getClass(),headVo.getPrimaryKey());
			vo.setParentVO(queryVo);
		}
		return vo;
	}
	
	/**
	 * 
	 * <p>
	 * ����Ʊ�ݻ�����Ϣ��ѯ����״̬
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2008-1-8
	 * @param pk_baseinfo
	 */
	private String queryLatestStatus(String pk_baseinfo){
		String sql = "select * from fbm_action where isnull(dr,0)=0 and pk_baseinfo = '" + pk_baseinfo + "' order by serialnum desc";
		List<ActionVO> list = null;
		try {
			list = (List<ActionVO>)(new BaseDAO().executeQuery(sql,new BeanListProcessor(ActionVO.class)));
		} catch (DAOException e) {
			Logger.error(e.getMessage(),e);
		}
		ActionVO actionvo = null;
		if(list != null && list.size() > 0){
			actionvo =  (ActionVO)list.get(0);
		}
		if(actionvo!=null){
			return actionvo.getEndstatus();
		}
		
		return null;
	}
	

}