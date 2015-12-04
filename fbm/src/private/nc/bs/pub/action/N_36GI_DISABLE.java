package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.NCLocator;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.itf.fbm.illegal.IIllegalBillService;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
	/**
	 * ��ע����������ת�˵�������
	���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
	 *
	 * �������ڣ�(2007-8-28)
	 * @author ƽ̨�ű�����
	 */
	public class N_36GI_DISABLE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
	private Hashtable m_keyHas=null;
	/**
	 * N_36GI_DISABLE ������ע�⡣
	 */
	public N_36GI_DISABLE() {
		super();
	}
	/*
	* ��ע��ƽ̨��д������
	* �ӿ�ִ����
	*/
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try{
			super.m_tmpVo = vo;
		    Object retObj=null;

		    setParameter("billVo", retObj);
			setParameter("userObj", vo.m_userObj);
			CollectionVO clvo = (CollectionVO)vo.m_preValueVo.getParentVO();
//			clvo.setVdisablemanid(vo.m_operator);
			clvo.setDdisabledate(new UFDate(vo.m_currentDate));
			clvo.setVbillstatus(IFBMStatus.Disable);
		    retObj = runClass("nc.bs.trade.comsave.BillSave","saveBill","nc.vo.pub.AggregatedValueObject:36GI",vo,m_keyHas,m_methodReturnHas);

		    CommonDAO commondao = new CommonDAO();
		    BaseinfoVO baseinfovo = new BaseinfoVO();
		    baseinfovo.setPk_baseinfo(clvo.getPk_baseinfo());
		    baseinfovo.setDisable(new UFBoolean(true));
		    commondao.updateBaseinfoDisableStatusbyPk(baseinfovo);

			createIllegalVO(clvo);
			HYBillVO retBillVO = (HYBillVO)((ArrayList)retObj).get(1);
			if(retBillVO instanceof HYBillVO){
//				ActionParamVO[] params = DefaultParamAdapter.changeCollection2Param(retBillVO,FbmActionConstant.DISABLE);
//				FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.DISABLE).doAction(params);
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.DISABLE).doAction((CollectionVO)retBillVO.getParentVO(), FbmActionConstant.DISABLE,false);

			}

		    if (retObj != null) {
				m_methodReturnHas.put("saveBill",retObj);
			}
		    return (AggregatedValueObject)retBillVO;
		} catch (Exception ex) {
		if (ex instanceof BusinessException)
			throw (BusinessException) ex;
		else
	    throw new PFBusinessException(ex.getMessage(), ex);
	}
	}
	private void createIllegalVO(CollectionVO clvo) throws BusinessException {
		IIllegalBillService iIllegalBillService = (IIllegalBillService)NCLocator.getInstance().lookup(IIllegalBillService.class.getName());
		IllegalVO illegalVO = new IllegalVO();
		illegalVO.setEnddate(clvo.getDqrq());
		illegalVO.setPk_curr(clvo.getYbbz());
		illegalVO.setPayunitname(clvo.getFkdw());
		illegalVO.setMoneyy(clvo.getPmje());
		illegalVO.setFbmbillno(clvo.getPk_baseinfo());
		illegalVO.setInvoicedate(clvo.getCprq());

//		nc.vo.pub.SuperVOUtil.execFormulaWithVOs(new IllegalVO[] {illegalVO},
//				new String[]{
//			 		"payunitname->getColValue(bd_cubasdoc,custname,pk_cubasdoc,payunitname)",
//					"fbmbillno->getColValue(fbm_baseinfo,fbmbillno,pk_baseinfo,fbmbillno)"
//					});
		HYPubBO hypubbo = new HYPubBO();
		BaseinfoVO baseinfo = (BaseinfoVO)hypubbo.queryByPrimaryKey(BaseinfoVO.class, clvo.getPk_baseinfo());
		illegalVO.setInvoiceunitname(baseinfo.getInvoiceunit());
		illegalVO.setReceiveunitname(baseinfo.getReceiveunit());
		nc.vo.pub.SuperVOUtil.execFormulaWithVOs(new IllegalVO[] {illegalVO},
				new String[]{
			 		"payunitname->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,payunitname)",
			 		"moneyy->getColValue(fbm_baseinfo,moneyy,pk_baseinfo,fbmbillno)",
					"fbmbillno->getColValue(fbm_baseinfo,fbmbillno,pk_baseinfo,fbmbillno)",
					"invoiceunitname->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,invoiceunitname)",
					"receiveunitname->iif(getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,receiveunitname) != null,getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,receiveunitname), receiveunitname)"
					});

		if(illegalVO != null) {
			iIllegalBillService.addIllegalBill(illegalVO);
		}
	}
	/*
	* ��ע��ƽ̨��дԭʼ�ű�
	*/
	public String getCodeRemark(){
	return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000173")/*@res "	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\n	Object retObj=null;\n	return retObj;\n"*/;
	}
	/*
	* ��ע�����ýű�������HAS
	*/
	private void setParameter(String key,Object val)	{
		if (m_keyHas==null){
			m_keyHas=new Hashtable();
		}
		if (val!=null)	{
			m_keyHas.put(key,val);
		}

	}

//	private IllegalVO convertCollectionVOtoIllegalVO(CollectionVO collectionVO) throws BusinessException{
//		if(collectionVO == null)
//			return null;
//		IllegalBillServiceImpl illegalBillServiceImpl = new IllegalBillServiceImpl();
//		IllegalVO illegalVO = new IllegalVO();
//		illegalVO.setEnddate(collectionVO.getDqrq());
//		illegalVO.setPk_curr(collectionVO.getYbbz());
//		illegalVO.setPayunitname(collectionVO.getFkdw());
//		illegalVO.setMoneyy(collectionVO.getPmje());
//		illegalVO.setFbmbillno(illegalBillServiceImpl.queryFbmBillNoByPk_Source(collectionVO.getPk_baseinfo()));
//		illegalVO.setInvoicedate(collectionVO.getCprq());
////		illegalVO.setIllegal_opmem(collectionVO.get)
////		illegalVO.setIllegal_recmem(collectionVO.get)
//		return illegalVO;
//	}
}