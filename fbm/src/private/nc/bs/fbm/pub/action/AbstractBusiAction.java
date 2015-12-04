package nc.bs.fbm.pub.action;

import nc.bs.fbm.pub.ActionQueryDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.bs.fbm.pub.SerialNumGenerator;
import nc.bs.fbm.pub.account.IBusiFbmAccount;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.uap.lock.PKLock;
import nc.itf.fbm.action.IBusiAction;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;

public abstract class AbstractBusiAction<K, T extends SuperVO> implements IBusiAction <K>, IBusiFbmAccount<T>  {

	protected CommonDAO dao = new CommonDAO();
	protected ActionQueryDAO actionDao = new ActionQueryDAO();
	protected OuterRelationDAO relDao = new OuterRelationDAO();

	public AbstractBusiAction() {
		super();
	}

	 public abstract BusiActionParamVO<T>[] buildParam(K data, String actioncode) throws BusinessException;

	public void doAction(K inparams, String actioncode,boolean isNesting) throws BusinessException {
		BusiActionParamVO<T>[] params = buildParam(inparams, actioncode);

		if (params != null && params.length > 0) {
			int len = params.length;
			String[] pk_baseinfo = new String[len];
			for (int i = 0; i < len; i++) {
				pk_baseinfo[i] = params[i].getPk_baseinfo();
				// ���²�ѯ��ֹpk_baseinfoΪ�յ����
				if (pk_baseinfo[i] == null) {
					RegisterVO registerVO = (RegisterVO) dao.getBaseDAO()
							.retrieveByPK(RegisterVO.class,
									params[i].getPk_source());

					if (null == registerVO) {
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000229")/* @res"������Ʊ�Ǽǵ�����ȡ������Ʊ�Ǽǵ�Ϊ�գ���ˢ�����ԡ�"*/);
					}
					pk_baseinfo[i] = registerVO.getPk_baseinfo();
				}
			}
			String userid = InvocationInfoProxy.getInstance().getUserCode();
			try {
				if(!isNesting)//���ΪǶ�׶�����ִ����������
				{
					// ����Ʊ�ݻ�����ϢPK����
					lock(pk_baseinfo, userid);
				}
				// ǰ�ö���
				beforeAction(params);

				// ִ�кϷ���У��
				doCheckArray(params);

				if (!params[0].getActioncode()
						.equals(FbmActionConstant.ONAUDIT)) {// ������е������ִ�к�������
					String pk_action = null;
					for (int i = 0; i < len; i++) {
						// ���¶���������
						pk_action = updateActionTable(params[i]);
						// ά��Ʊ���ڲ��˻�
						dealAccount(pk_action, params[i]);
					}

					// ά����ҵ�����������
					afterAction(params);
				}
			} finally {
				if(!isNesting)//�����Ƕ�׶����Ͳ�ִ����������
				{
					// �ͷ���
					unLock(pk_baseinfo, userid);
				}
			}
		}

	}


	/**
	 *
	 * ���ҵ���ֶ� ������ˡ��������ں��Ƿ����� xwq 2007-9-3
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	protected void fillBusiField(CircularlyAccessibleValueObject vo,
			BusiActionParamVO<T> param, String actioncode) throws BusinessException {
		param.setActioncode(actioncode);
		if (actioncode.equals(FbmActionConstant.EDITSAVE)
				|| actioncode.equals(FbmActionConstant.SAVE)) {
			param.setActionperson((String) vo.getAttributeValue("voperatorid"));
			param.setActiondate((UFDate) vo.getAttributeValue("doperatedate"));
		}

		if (actioncode.equals(FbmActionConstant.ONAUDIT)
				|| actioncode.equals(FbmActionConstant.AUDIT)
				|| actioncode.equals(FbmActionConstant.DESTROY)
				|| actioncode.equals(FbmActionConstant.CANCELDESTROY)) {
			param.setActionperson((String) vo.getAttributeValue("vapproveid"));
			param.setActiondate((UFDate) vo.getAttributeValue("dapprovedate"));
		}
	}


//	/**
//	 * ִ��Ʊ�ݶ��� xwq 2007-8-14
//	 *
//	 * @param params
//	 *            ��������VO����
//	 * @return
//	 * @throws
//	 * @since NC5.0
//	 */
//	public void doAction(BusiActionParamVO[] params) throws BusinessException {
//		if (params != null && params.length > 0) {
//			int len = params.length;
//			String[] pk_baseinfo = new String[len];
//			for (int i = 0; i < len; i++) {
//				pk_baseinfo[i] = params[i].getPk_baseinfo();
//				// ���²�ѯ��ֹpk_baseinfoΪ�յ����
//				if (pk_baseinfo[i] == null) {
//					RegisterVO registerVO = (RegisterVO) dao.getBaseDAO()
//							.retrieveByPK(RegisterVO.class,
//									params[i].getPk_source());
//
//					if (null == registerVO) {
//						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000229")/* @res"������Ʊ�Ǽǵ�����ȡ������Ʊ�Ǽǵ�Ϊ�գ���ˢ�����ԡ�"*/);
//					}
//					pk_baseinfo[i] = registerVO.getPk_baseinfo();
//				}
//			}
//			String userid = InvocationInfoProxy.getInstance().getUserCode();
//			try {
//				// ����Ʊ�ݻ�����ϢPK����
//				lock(pk_baseinfo, userid);
//
//				// ǰ�ö���
//				beforeAction(params);
//
//				// ִ�кϷ���У��
//				doCheckArray(params);
//
//				if (!params[0].getActioncode()
//						.equals(FbmActionConstant.ONAUDIT)) {// ������е������ִ�к�������
//					String pk_action = null;
//					for (int i = 0; i < len; i++) {
//						// ���¶���������
//						pk_action = updateActionTable(params[i]);
//						// ά��Ʊ���ڲ��˻�
//						dealAccount(pk_action, params[i]);
//					}
//
//					// ά����ҵ�����������
//					afterAction(params);
//				}
//			} finally {
//				// �ͷ���
//				unLock(pk_baseinfo, userid);
//			}
//		}
//	}

	protected void lock(String[] pk_baseinfo, String userid) throws BusinessException {
		if(null == pk_baseinfo || pk_baseinfo.length == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000230")/* @res"�޷�������ǰƱ�ݣ�ȡ�õ�Ʊ�ݻ�����ϢΪ�ա�"*/);
		}
		if(null == userid || "".equals(userid.trim())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000231")/* @res"�޷�������ǰƱ�ݣ� ȡ�õĵ�ǰ�û�Ϊ�ա�"*/);
		}


		boolean isLock = PKLock.getInstance().acquireBatchLock(pk_baseinfo, userid, "");
		if(!isLock){
			throw new BusinessException(new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000396")/* @res"����ʧ�ܣ�Ʊ�����ڱ�ʹ�ã����Ժ����ԡ�"*/));
		}
	}

	protected void unLock(String[] pk_baseinfo, String userid) throws BusinessException {
		if(null == pk_baseinfo || pk_baseinfo.length == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000232")/* @res"�޷������ǰƱ�ݵ�������ȡ�õ�Ʊ�ݻ�����ϢΪ�ա�"*/);
		}
		if(null == userid || "".equals(userid.trim())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000233")/* @res"�޷������ǰƱ�ݵ�������ȡ�õĵ�ǰ�û�Ϊ�ա�"*/);
		}
		PKLock.getInstance().releaseBatchLock(pk_baseinfo, userid, "");
	}

	/**
	 *
	 * ���¶��������� xwq 2007-8-14
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	protected String updateActionTable(BusiActionParamVO<T> param)
			throws BusinessException {
		String actionCode = param.getActioncode();
		if (actionCode.equals(FbmActionConstant.AUDIT)
				|| actionCode.equals(FbmActionConstant.SAVE)
				|| actionCode.equals(FbmActionConstant.TRANSACT)
				|| actionCode.equals(FbmActionConstant.DISABLE)
				|| actionCode.equals(FbmActionConstant.IMPAWNBACK)
				|| actionCode.equals(FbmActionConstant.INPUT_SUCCESS)
				|| actionCode.equals(FbmActionConstant.OUTPUT_SUCCESS)
				|| actionCode.equals(FbmActionConstant.DESTROY)
				|| actionCode.equals(FbmActionConstant.CENTERUSE)) {// ���ݱ�������,��������ϳ���������
			// �����¶���
			ActionVO action = new ActionVO();
			action.setBeginstatus(getBeginStatus(param));
			action.setEndstatus(getEndStatus(param));
			action.setActioncode(param.getActioncode());
			action.setBilltype(param.getBilltype());
			action.setPk_bill(param.getPk_bill());// ����ҵ�񵥾�PK
			action.setPk_source(param.getPk_source());
			action.setActionperson(param.getActionperson());
			action.setActiondate(param.getActiondate());
			action.setIsnew(new UFBoolean(true));// ���õ�ǰ����Ϊ���¶���
			action.setPk_org(param.getPk_org());

			SerialNumGenerator genDMO = new SerialNumGenerator();
			action.setSerialnum(genDMO.getNextID(param.getPk_baseinfo()));
			action.setPk_baseinfo(param.getPk_baseinfo());
			action.setPk_corp(param.getPk_corp());
			// ���ж�������Ϊ��ʷ����
			actionDao.disableHistoryAction(param.getPk_source());
			// �����¶���
			String pk_action =  dao.getBaseDAO().insertVO(action);
			resetEndStatus(action,param.getPk_baseinfo());
			return pk_action;

		} else if (actionCode.equals(FbmActionConstant.DELETE)
				|| actionCode.equals(FbmActionConstant.CANCELAUDIT)
				|| actionCode.equals(FbmActionConstant.CANCELINPUT)
				|| actionCode.equals(FbmActionConstant.CANCELOUTPUT)
				|| actionCode.equals(FbmActionConstant.CANCELDESTROY)
				|| actionCode.equals(FbmActionConstant.CANCELTRANSACT)
				|| actionCode.equals(FbmActionConstant.CANCELCENTERUSER)
				|| actionCode.equals(FbmActionConstant.CANCELIMPAWNBACK)) {// �������
																			// ��������ɾ����ȡ�����⡢ȡ�����
			ActionVO lastAction = actionDao.queryNewestByPk_register(param
					.getPk_source());

			dao.getBaseDAO().deleteVO(lastAction);
			// ��ѯ�����������Ϊ�¶���
			ActionVO newAction = actionDao.queryNewestByPk_register(param
					.getPk_source());
			if (newAction != null) {
				newAction.setIsnew(new UFBoolean(true));
				dao.getBaseDAO().updateVO(newAction);
			}
			
			resetEndStatus(newAction,param.getPk_baseinfo());
			return lastAction.getPrimaryKey();
		}

		return null;
	}

	/**
	 *
	 * �����˻���ϸ����
	 *
	 * Ĭ�ϲ������˻����ݣ�ֻ�����˻�����Ķ����ڸ��Ǹ÷��� xwq 2007-8-21
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	public void dealAccount(String pk_action, BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub

	}

	/**
	 *
	 * ҵ�����ǰ��Ч��У��(����) xwq 2007-9-3
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	protected void doCheckArray(BusiActionParamVO<T> params[])
			throws BusinessException {
		int len = params.length;
		StringBuffer sb = new StringBuffer();
		boolean error = false;
		String tmp = null;
		String outTmp = null;

		for (int i = 0; i < len; i++) {
			if (!params[i].isForUpgrade()) {// ������ʹ��ʱҪУ��
				tmp = doCheck(params[i]);
				outTmp = checkOuter(params[i]);
			}
			if (tmp != null) {
				error = true;
				sb.append(tmp);
				sb.append("\n");
			}
			if (outTmp != null) {
				error = true;
				sb.append(outTmp);
				sb.append("\n");
			}

		}

		if (error) {
			throw new BusinessException(sb.toString());
		}
	}

	/**
	 *
	 * ҵ�����ǰ��Ч��У��(����) xwq 2007-8-14
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	protected abstract String doCheck(BusiActionParamVO<T> param)
			throws BusinessException;

	/**
	 *
	 * ���ض�����ʼǰ����Ʊ״̬ xwq 2007-8-28
	 *
	 * @param param
	 *            TODO
	 * @param
	 * @return
	 * @throws BusinessException
	 *             TODO
	 * @throws
	 * @since NC5.0
	 */
	protected abstract String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException;

	/**
	 *
	 * ���ض������������Ʊ״̬ xwq 2007-8-28
	 *
	 * @param param
	 *            TODO
	 * @param
	 * @return
	 * @throws BusinessException
	 *             TODO
	 * @throws
	 * @since NC5.0
	 */
	protected abstract String getEndStatus(BusiActionParamVO<T> param)
			throws BusinessException;

	/**
	 *
	 * ��ڲ��� xwq 2007-9-4
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	protected void  beforeAction(BusiActionParamVO<T>[] params)
			throws BusinessException {
		int len = params.length;
		for (int i = 0; i < len; i++) {

			// ��ѯҵ�񵥾�VO
			T superVO = (T) dao.getBaseDAO().retrieveByPK(
					getClassByBillType(params[i].getBilltype()),
					params[i].getPk_bill());

			RegisterVO registerVO = (RegisterVO) dao.getBaseDAO().retrieveByPK(
					RegisterVO.class, params[i].getPk_source());

			if(null==registerVO){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000149")/*@res "�޷��õ�Ʊ����Ϣ����ȷ��Ʊ���Ƿ���ڣ�"*/);
			}

			BaseinfoVO baseinfoVO = (BaseinfoVO) dao.getBaseDAO().retrieveByPK(
					BaseinfoVO.class, registerVO.getPk_baseinfo());

			if (null == superVO) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000234")/* @res"ȡ�õĵ�ǰ����Ϊ�գ���ִ��Ʊ��ѡ���ˢ�²�����"*/);
			}
			if (null == registerVO) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000235")/* @res"ȡ�õĵ�ǰ����Ʊ�Ǽǵ�Ϊ�գ���ִ��Ʊ��ѡ���ˢ�²�����"*/);
			}
			if (null == baseinfoVO) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000236")/* @res"ȡ�õĵ�ǰƱ�ݻ�����ϢΪ�գ���ִ��Ʊ��ѡ���ˢ�²�����"*/);
			}

			params[i].setSuperVO(superVO);


			params[i].setRegisterVO(registerVO);
			params[i].setBaseinfoVO(baseinfoVO);

			if (params[i].getActioncode().equals(FbmActionConstant.EDITSAVE)) {
				// ɾ��ԭaction
				ActionVO actionVO = actionDao
						.queryNewestActionByPk_bill(params[i].getPk_bill());
				if (actionVO != null) {
					dao.getBaseDAO().deleteVO(actionVO);
					// ɾ��ԭ�˻���ϸ����
					dao.delAccountDetailByActionPK(actionVO.getPk_action());
					// ��ѯ�����������Ϊ�¶���
					ActionVO newAction = actionDao
							.queryNewestByPk_register(actionVO.getPk_source());
					if (newAction != null) {
						newAction.setIsnew(new UFBoolean(true));
						dao.getBaseDAO().updateVO(newAction);
					}
					resetEndStatus(newAction,actionVO.getPk_baseinfo());
				}

				// ���������޸�Ϊ��������
				params[i].setActioncode(FbmActionConstant.SAVE);
			}

			// ���û�д��������Ϣ��������ô��ѯ�õ�
			if (params[i].getPk_baseinfo() == null) {
				params[i].setPk_baseinfo(baseinfoVO.getPrimaryKey());
				params[i].setMoneyy(baseinfoVO.getMoneyy());
			}

			ActionVO lastActionVO = actionDao.queryNewestByPk_baseinfo(
					registerVO.getPk_baseinfo(), params[i].getPk_corp());
			//ActionVO lastActionVO = actionDao.queryNewestByPk_register(params[i].getPk_source());

			params[i].setLastActionVO(lastActionVO);
		}
	}

	/**
	 * ����ⲿ���ݹ����Ϸ��� Ĭ��ʵ��Ϊ�ո����־Ϊ��
	 *
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	protected String checkOuter(BusiActionParamVO<T> param) throws BusinessException {
		RegisterVO registerVO = param.getRegisterVO();
		if (!registerVO.getSfflag().booleanValue()) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000237")/* @res"Ʊ�ݵ��տ���߸���û����ɣ��޷����к���ҵ��"*/;
		}
		return null;
	}

	/**
	 * ����ִ�к󷽷�
	 *
	 * @param param
	 * @throws BusinessException
	 */
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {

	}

	/**
	 * ���ݵ������ͱ��뷵��VO����
	 *
	 * @param pk_billtypecode
	 * @return
	 */
	private Class getClassByBillType(String pk_billtypecode) {
		if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_GATHER)
				|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INVOICE)) {
			return RegisterVO.class;
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BANKKEEP)
				|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BANKBACK)
				|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERKEEP)
				|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERBACK)) {
			return StorageVO.class;
		} else if (pk_billtypecode
				.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {
			return CollectionVO.class;
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_DISCOUNT_APP)
				|| pk_billtypecode
						.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {
			return DiscountVO.class;
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BILLPAY)) {
			return AcceptVO.class;
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_ENDORE)) {
			return EndoreVO.class;
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_IMPAWN)) {
			return ImpawnVO.class;
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RETURN)) {
			return ReturnVO.class;
		} else if (FbmBusConstant.BILLTYPE_RELIEF.equals(pk_billtypecode)) {
			return ReliefVO.class;
		}
		return null;
	}

	/**
	 * ����У��
	 *
	 * @param fbmbillno
	 * @param actionVO
	 * @return
	 * @throws BusinessException
	 */
	protected String commonCheck(BusiActionParamVO<T> param) throws BusinessException {
		ActionVO actionVO = param.getLastActionVO();
		String fbmbillno = param.getBaseinfoVO().getFbmbillno();
		if (actionVO == null) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000238")/* @res"�Ҳ���Ʊ�ݶ���"*/;
		}
		// У��״̬
		if (!actionVO.getEndstatus().equals(getBeginStatus(param))) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/
					+ fbmbillno
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000240")/* @res"��״̬��"*/
					+ BusiMessageTranslator.retriveStatusName(actionVO
							.getEndstatus()) + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000241")/* @res",����ʧ�ܡ�"*/
					+ BusiMessageTranslator.translateAction(param);
		}
		String actionCode = param.getActioncode();// �����¼�¼����ҪУ��ʱ���Ⱥ�
		if (actionCode.equals(FbmActionConstant.AUDIT)
				|| actionCode.equals(FbmActionConstant.SAVE)
				|| actionCode.equals(FbmActionConstant.TRANSACT)
				|| actionCode.equals(FbmActionConstant.DISABLE)
				|| actionCode.equals(FbmActionConstant.IMPAWNBACK)
				|| actionCode.equals(FbmActionConstant.INPUT_SUCCESS)
				|| actionCode.equals(FbmActionConstant.OUTPUT_SUCCESS)
				|| actionCode.equals(FbmActionConstant.DESTROY)
				|| actionCode.equals(FbmActionConstant.CENTERUSE)) {
			// added by wes show a message dialog when actiondate is null
			if (null == actionVO.getActiondate()) {
				return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000242")/* @res"ǰһ��������Ϊ�գ���ȷ�ϡ�"*/;
			}
			// У��ҵ������
			if (actionVO.getActiondate().after(param.getActiondate())) {
				return  nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000150")/*@res "��ǰҵ������:"*/+ param.getActiondate() +"," + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/ + fbmbillno + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000243")/* @res"�ĵ�ǰҵ����������ǰһҵ�����ڡ�"*/
						+ BusiMessageTranslator.translateAction(param);
			}
		}
		return null;
	}

	/**
	 * ������Ʊ�Ǽǵ�����Ʊ��״̬
	 * ����Ʊ�ݻ�����Ϣ����Ʊ��״̬
	 * @param actionvo
	 * @throws BusinessException
	 */
	private void resetEndStatus(ActionVO actionvo,String pk_baseinfo) throws BusinessException{
		if(actionvo == null){//������Ʊ�Ǽǻ��߸�Ʊ�ǼǱ�ɾ�������
			if(pk_baseinfo!=null){
				ActionVO newAction = actionDao.queryNewestByPk_baseinfo(pk_baseinfo,null);
				if(newAction!=null){
					String sql = "update fbm_baseinfo set baseinfostatus = '"+newAction.getEndstatus()+"' where pk_baseinfo='"+pk_baseinfo+"'";
					dao.getBaseDAO().executeUpdate(sql);
				}
			}
			return;
		}
		String pk_register = actionvo.getPk_source();
		String sql = "update fbm_register set registerstatus = '"+actionvo.getEndstatus()+"' where pk_register = '"+pk_register+"'";
		dao.getBaseDAO().executeUpdate(sql);
		//String pk_baseinfo = actionvo.getPk_baseinfo();
		sql = "update fbm_baseinfo set baseinfostatus = '"+actionvo.getEndstatus()+"' where pk_baseinfo='"+pk_baseinfo+"'";
		dao.getBaseDAO().executeUpdate(sql);
	}

}