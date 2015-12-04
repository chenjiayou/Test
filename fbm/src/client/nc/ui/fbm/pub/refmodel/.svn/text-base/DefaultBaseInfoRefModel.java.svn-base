package nc.ui.fbm.pub.refmodel;

import java.util.HashMap;
import java.util.Vector;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.vo.logging.Debug;

/**
 *
 * <p>
 *   票据基本信息参照
 *   用于收票登记
 * </p>
 * @author xwq
 * @date 2007-8-10
 * @version V5.0
 */
public class DefaultBaseInfoRefModel extends AbstractTMRefModel {

	private String[] FIELD_CODE = new String[]{"fbmbillno","currtypename","moneyy","enddate"};

	private String[] FIELD_NAME = new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"票据编号"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"币种"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"金额"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000395") /* @res"到期日期"*/};
	private String TITLE = nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000049")/* @res"票据基本信息参照"*/;
	private String TABLE_NAME = " fbm_baseinfo  join bd_currtype on fbm_baseinfo.pk_curr = bd_currtype.pk_currtype "
		+ "  ";
	private String PK_FIELD_NAME = "pk_baseinfo";

	public DefaultBaseInfoRefModel() {
		super();

		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setDefaultFieldCount(4);
		addDispConvertor();
		setMatchPkWithWherePart(false);
		
	}


	@Override
	public String[] getHiddenFieldCode() {
		return new String[] { "pk_baseinfo", "acceptanceno", "contractno","orientation",
				"disable",  "invoicedate", "invoiceunit", "keepunit",
				"payunit","paybank", "paybankacc", "receivebank", "receivebankacc",
				"receiveunit","fbmbilltype","pk_curr"};
	}


	@Override
	public String getRefNodeName() {
		return PK_FIELD_NAME;
	}


	@Override
	public String getRefTitle() {
		return TITLE;
	}


	@Override
	public String getTableName() {
		return TABLE_NAME;
	}




	@Override
	public String[] getRefShowNameValues() {
		String[] refNames = super.getRefShowNameValues();
		if(refNames!=null&&refNames.length>0){
			String[] returnStr = new String[1];
			returnStr[0] = refNames[0];
			return returnStr;
		}
		return refNames;
	}


	@Override
	protected String[] getMoneyKeys() {
		// TODO Auto-generated method stub
		return new String[]{"moneyy"};
	}


	@Override
	protected String getCurrencyName() {
		// TODO Auto-generated method stub
		return "pk_curr";
	}

	public void MatchNameData(String field,String blurstr){
		Vector result = matchData(field, blurstr);
		setSelectedData(result);
	}

	/**
	 * 返回值数组－－编码字段 创建日期：(2001-8-13 16:19:24)
	 *
	 * @return java.lang.String[]
	 */
	public java.lang.String[] getRefCodeValues() {
		String[] sDatas = super.getRefCodeValues();
		if(sDatas!=null&&sDatas.length>1){
			String[] returnStr = new String[1];
			returnStr[0] = sDatas[0];
			return returnStr;
		}
		return sDatas;
	}


	@Override
	public Vector matchData(String[] fields, String[] values) {
		// sxj
		if (values == null || values.length == 0) {
			setSelectedData(null);
			return null;
		}

		Vector v = null;
		String matchsql = getSql_Match(fields, values, getStrPatch(),
				getFieldCode(), getHiddenFieldCode(), getTableName(),
				getWherePart(), getOrderPart());

		try {
			v = queryMain(getDataSource(), matchsql);
		} catch (Exception e) {
			Debug.error(e.getMessage(), e);
		}

		if (v != null && v.size() > 0) {
			if (v != null && v.size() > 0) {
				v = getFilterPKsVector(v, getFilterStrategy());

			}
			setSelectedData(v);
		} else
			setSelectedData(null);

		boolean isDefConverted = true;
		if (v == null || v.size() == 1) {
			isDefConverted = false;
		}

		getConvertedData(false, v, isDefConverted);
		return getSelectedData();

	}


	public boolean isCacheEnabled() {
		return false;
	}

	/**
	 * 过滤掉重复的pk_baseinfo
	 */
//	public Vector getData() {
//		Vector vec = super.getData();
//		vec.trimToSize();
//		Vector newvec = new Vector();
//		HashMap<String, Vector> map = new HashMap<String, Vector>();
//		int dataindex = 3;
//		for (int i = 0; i < vec.size(); i++) {
//			Vector element = (Vector)vec.elementAt(i);
//			String key = (String) (element).elementAt(dataindex);
//			if(!CommonUtil.isNull(key)&&!map.containsKey(key)){
//				newvec.add(element);
//				map.put(key, element);
//			}
//		}
//		return newvec;
//	}


}