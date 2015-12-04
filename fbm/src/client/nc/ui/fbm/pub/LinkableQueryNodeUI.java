package nc.ui.fbm.pub;

import java.awt.Container;
import java.util.Hashtable;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.pub.FramePanel;
import nc.ui.pub.linkoperate.ILinkQuery;
import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.ui.pub.linkoperate.ILinkType;
import nc.ui.pub.querymodel.QELinkQueryData;
import nc.ui.pub.querymodel.QueryNodeUI;
import nc.ui.pub.querymodel.UIUtil;
import nc.ui.reportquery.demo.AbstractQueryParameterFactory;
import nc.ui.reportquery.demo.DefaultQueryParameterFactory;
import nc.vo.pub.querymodel.FormatModelDef;
import nc.vo.pub.querymodel.IEnvParam;
import nc.vo.pub.querymodel.ModelUtil;
import nc.vo.pub.querymodel.ParamConst;
import nc.vo.pub.querymodel.ParamUtil;
import nc.vo.pub.querymodel.ParamVO;
import nc.vo.pub.querymodel.QEPenetrateObject;
import nc.vo.pub.querymodel.QueryConst;
import nc.vo.pub.querymodel.QueryModelDef;
import nc.vo.pub.querymodel.QueryParamVO;

/**
 * �ع�������ȡ��ʽ�Ĳ��Խڵ�
 *
 * @author jl created on 2006-11-6
 */
public class LinkableQueryNodeUI extends QueryNodeUI implements ILinkQuery {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// ͨ����͸���������-true
	// ֱ�ӵ���ڵ��--false
	private boolean isLinkQuery = false;

	@Override
	public AbstractQueryParameterFactory getQueryParametersFactory(FormatModelDef fmd, String defDsName) {
		AbstractQueryParameterFactory factory = null;
		if (isLinkQuery()) {
			factory = new LinkableQueryParameterFactory(fmd, defDsName, true, this);
		} else {
			factory = super.getQueryParametersFactory(fmd, defDsName);
		}

		return factory;
	}

	public LinkableQueryNodeUI() {
		super();

	}

	public LinkableQueryNodeUI(FramePanel f) {
		super(f);
	}

	class LinkableQueryParameterFactory extends DefaultQueryParameterFactory {
		public LinkableQueryParameterFactory(FormatModelDef fmd, String defDsName, boolean bRun, Container c) {
			super(fmd, defDsName, bRun, c);
		}

		@Override
		public Hashtable getHashAliasParam() {
			return hashAliasParam;
		}

		@Override
		public Hashtable refreshWithInputHashParam(Hashtable inputhashAliasParam) {
			hashAliasParam = inputhashAliasParam;
			return inputhashAliasParam;
		}
	}

	// �������ո�ʽ��ƶ��壬�����漰������
	@Override
	protected boolean onBrowse(FormatModelDef fmd) {
		if(getFrame().getLinkType() == ILinkType.NONLINK_TYPE) {
			setLinkQuery(false);
		} else {
			setLinkQuery(true);
		}
		if (isLinkQuery()) {
			this.m_fmd = fmd;
			return true;
		} else {
			return super.onBrowse(fmd);
		}
	}

	/**
	 * �������������ڵ㣨���ѯ����͸�������������ʱ��ִ��
	 */
	public void doQueryAction(ILinkQueryData querydata) {
		setLinkQuery(true);
		String pk_base_all = querydata.getBillID();
		String pk_base = null;
		Hashtable hashParam = null;
		QELinkQueryData qeQueryData = (QELinkQueryData) querydata;
		QEPenetrateObject peneObject = (QEPenetrateObject) qeQueryData.getUserObject();
		DefaultQueryParameterFactory factory = (DefaultQueryParameterFactory)getQueryParametersFactory(m_fmd, m_dsName);
		String[] quoteIds = m_fmd.getQuoteIds();
		String[] quoteAlias = m_fmd.getQuoteAliases();

		if(!CommonUtil.isNull(pk_base_all)) {
			int index = pk_base_all.indexOf("*");
			pk_base = pk_base_all.substring(index + 1);
		}
		if (pk_base != null && pk_base.trim().length() > 0) {
			hashParam = new Hashtable();
			QueryParamVO qeParamVO = new QueryParamVO();
			qeParamVO
					.setConsultCode("nc.ui.fbm.pub.refmodel.QEBaseinfoRefModel");
			qeParamVO.setDsName("design");
			qeParamVO.setRefPk(pk_base);
			qeParamVO.setValue(pk_base);
			qeParamVO.setDataType(ParamConst.REF_ID);
			qeParamVO.setOperaCode("=");
			qeParamVO.setParamCode("pk_baseinfo");
			qeParamVO.setParamName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"Ʊ�ݱ��"*/);
			hashParam.put("pk_baseinfo", qeParamVO);
		} else {
	//		AbstractQueryParameterFactory factory = getQueryParametersFactory(m_fmd, m_dsName);
	//		Hashtable hashPeneRow = peneObject.getHashPeneRow();
			// ��ȡ������Ҫ�����ݣ�����HashParam

	//		Enumeration keyEnum = hashPeneRow.keys();
			if (quoteIds != null) {
				// ֻ�ܵõ����õĵ�һ����ѯ
				ParamVO[] paramVOs = ParamUtil.getAllParam(quoteIds[0], m_dsName);
				hashParam = peneObject.getHashParam();
	//			if (hashParam == null) {
	//				hashParam = new Hashtable();
	//				while (keyEnum.hasMoreElements()) {
	//					String element = (String) keyEnum.nextElement();
	//					for (int i = 0; i < (paramVOs == null ? 0 : paramVOs.length); i++) {
	//						String paramCode = paramVOs[i].getParamCode();
	//						if (element.equalsIgnoreCase(paramCode)) {
	//							paramVOs[i].setValue((String) hashPeneRow.get(element));
	//							hashParam.put(paramVOs[i].getParamCode(), paramVOs[i]);
	//							break;
	//						}
	//					}
	//				}
	//			}

			}
		}
		Hashtable hashAliasParam = new Hashtable();
		hashParam = UIUtil.addEnvInfo(hashParam);
		QueryModelDef qmd = ModelUtil.getQueryDef(quoteIds[1], m_dsName);
		hashAliasParam.put(quoteAlias[0], hashParam);

		Hashtable hashParam2 = (Hashtable)hashParam.clone();
		hashParam2 = UIUtil.addEnvInfo(hashParam2);
		addEnvInfo(quoteIds[1], hashParam2);
		hashAliasParam.put(quoteAlias[1],hashParam2);
//		hashAliasParam.put(quoteAlias[1], new Hashtable());
		factory.getQueryParametersBean(hashAliasParam);
		getMainPanel().onBrowse(factory);
	}

	private void addEnvInfo(String qmdID,Hashtable hashParam){
		QueryModelDef qmd = ModelUtil.getQueryDef(qmdID, m_dsName);
		// jl+ ���˽ڵ���뵽�Զ��廷��������
		Object[] objEnvParams = new Object[] {
				getIEnvParam(qmd).getClass().getName(), qmd.getDsName(),
				UIUtil.makeEnvInfo(), getModuleCode() };
		hashParam.put(QueryConst.ENV_PARAM_KEY, objEnvParams);
	}

	private boolean isLinkQuery() {
		return isLinkQuery;
	}

	private void setLinkQuery(boolean isLinkQuery) {
		this.isLinkQuery = isLinkQuery;
	}

	private IEnvParam getIEnvParam(QueryModelDef qmd) {
		IEnvParam iEnvParam = null;
		try {
			String className = ParamConst.ENV_PARAM_CLASS;
			// ��ò�ѯ�����б�����Զ��廷�������ӿ�ʵ������
			if (qmd != null && qmd.getQueryBaseVO() != null
					&& qmd.getQueryBaseVO().getIEnvParamClass() != null)
				className = qmd.getQueryBaseVO().getIEnvParamClass();
			// ����
			Class cls = Class.forName(className);
			iEnvParam = (IEnvParam) cls.newInstance();
		} catch (Exception e) {
			Logger.error(e.getMessage(),e);
		}
		return iEnvParam;
	}
}