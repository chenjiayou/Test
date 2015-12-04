package nc.ui.fbm.pub.refmodel;

import java.awt.Container;
import java.util.Hashtable;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.relief.ReliefUtil;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.RefDataFilter;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.pub.ValueObject;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 *
 * 类功能说明： 调剂出库选票参照 日期：2007-10-22 程序员： wues
 *
 */
public class ReliefRefModel extends AbstractTMRefModel {

	private String[] FIELD_CODE = new String[] { "fbmbillno", "currtypename",
			"moneyy" ,"enddate"};
	private String[] HIDDEN_CODE = new String[] { "pk_curr" };
	private String[] FIELD_NAME = new String[] { nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"票据编号"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"币种"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"金额"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000395") /* @res"到期日期"*/ };
	/**
	 * 取某个状态的收票登记信息和票据基本信息 在此为已调剂存放
	 */
	// private String endStatus = "has_relief_keep";
	private String TITLE = nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000070")/* @res"票据选择"*/;

	private String PK_FIELD_NAME = "pk_register";

	public ReliefRefModel() {
		super(null);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setHiddenFieldCode(HIDDEN_CODE);
		setDefaultFieldCount(3);
		addDispConvertor();

	}

	public ReliefRefModel(Container con) {
		super(con);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setHiddenFieldCode(HIDDEN_CODE);
		setDefaultFieldCount(4);
		addDispConvertor();
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] { "pk_register", "pk_baseinfo", "enddate",
				"invoiceunit", "holdunit", "receiveunit", "payunit",
				"paybillunit", "registerstatus", "pk_curr", "moneyb", "moneyf",
				"frate", "brate", "fbmbilltype" };
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
		StringBuffer strBuf = new StringBuffer();
		strBuf
				.append(" (select distinct pk_register, fbm_baseinfo.pk_baseinfo, fbmbillno, fbm_baseinfo.moneyy,fbmbilltype, ");
		strBuf
				.append(" fbm_baseinfo.enddate, fbm_baseinfo.invoiceunit invoiceunit, fbm_register.brate, fbm_register.frate, fbm_register.moneyf, fbm_register.moneyb, ");
		strBuf
				.append("fbm_baseinfo.receiveunit, fbm_baseinfo.payunit, holdunit, paybillunit, pk_curr , registerstatus ,currtypename");
		strBuf
				.append(" from fbm_register  ");
		strBuf
				.append(" join (select fbm_baseinfo.*,bd_currtype.currtypename from fbm_baseinfo join bd_currtype on fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype ) fbm_baseinfo on (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) ");
		strBuf
				.append(" where isnull(fbm_register.dr,0)=0  and isnull(fbm_baseinfo.dr,0)=0 ");


		// 如果当前登录的为结算中心，则取中心及其下属的所有子单位；如果为结算单位则不加公司条件
		String pk_corp = ClientInfo.getCorpPK();
		if (FBMClientInfo.isSettleCenter(pk_corp))
			// 本单位及其下属子单位
			strBuf.append(" and fbm_register.pk_corp in (").append(
					ReliefUtil.getAllSubCorp(pk_corp)).append(")");

		strBuf.append(" ) gatherTable");
		return strBuf.toString();
	}

	@Override
	protected String getCurrencyName() {
		// TODO Auto-generated method stub
		return "pk_curr";
	}

	@Override
	protected String[] getMoneyKeys() {
		// TODO Auto-generated method stub
		return new String[] { "moneyy" };
	}

	protected RefDataFilter createRefFilter() {

		RefDataFilter m_RefFilter = null;
		m_RefFilter = new RefDataFilter();
		// 设置过滤的列
		m_RefFilter.setFilterIndex(new int[] { getFieldIndex("registerstatus") });
		// 设置过滤的值
		m_RefFilter.setFilterValue(new String[][] { { "has_relief_keep" } });
		m_RefFilter.setReverse(true);
		m_RefFilter.setRefPane(this.getParent());

		return m_RefFilter;
	}

	public ValueObject convertToVO(java.util.Vector vData) {
		ReliefBVO vo = new ReliefBVO();

		// 里面存放了field和vector中的index
		Hashtable hashFiledIndex = getHtCodeIndex();

		vo.setFbmbillno((String) vData.get((Integer) hashFiledIndex
				.get("fbmbillno")));

		vo.setPk_curr((String) (vData.get((Integer) hashFiledIndex
				.get("pk_curr"))));
		vo.setMoneyy(new UFDouble(vData.get(
				(Integer) hashFiledIndex.get("moneyy")).toString()));
		vo.setPk_source((String) vData.get((Integer) hashFiledIndex
				.get("pk_register")));
		vo.setPk_baseinfo((String) vData.get((Integer) hashFiledIndex
				.get("pk_baseinfo")));
		vo.setEnddate(new UFDate((String) vData.get((Integer) hashFiledIndex
				.get("enddate"))));
		vo.setInvoiceunit((String) vData.get((Integer) hashFiledIndex
				.get("invoiceunit")));
		vo.setHoldunit((String) vData.get((Integer) hashFiledIndex
				.get("holdunit")));
		vo.setReceiveunit((String) vData.get((Integer) hashFiledIndex
				.get("receiveunit")));
		vo.setPayunit((String) vData.get((Integer) hashFiledIndex
				.get("payunit")));
		vo.setPaybillunit((String) vData.get((Integer) hashFiledIndex
				.get("paybillunit")));
		return vo;
	}
	
	public boolean isCacheEnabled() {
		return false;
	}
}