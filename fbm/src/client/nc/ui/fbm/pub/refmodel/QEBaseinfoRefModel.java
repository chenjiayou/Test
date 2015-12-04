package nc.ui.fbm.pub.refmodel;

import java.awt.Container;
import java.math.BigDecimal;
import java.util.Vector;

import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.RefDataFilter;
import nc.vo.pub.lang.UFNumberFormat;

public class QEBaseinfoRefModel extends AbstractTMRefModel {

	protected RefDataFilter createRefFilter() {
		return null;
	}

	private String[] FIELD_CODE = new String[]{"fbmbillno","pk_curr","moneyy"};
	private String[] FIELD_NAME = new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"票据编号"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"币种"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"金额"*/};
	private String TITLE = nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000039")/* @res"票据参照"*/;
	//本公司收票记录。实际使用时，编写增加状态过滤条件的子类实现
	private String TABLE_NAME = "(select fbm_baseinfo.pk_baseinfo, fbmbillno," +
					"pk_curr," +
					"fbm_baseinfo.moneyy "
			+ " from fbm_register join fbm_baseinfo on (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) " +
					"where isnull(fbm_register.dr,0)=0 " +
					"and isnull(fbm_baseinfo.dr,0)=0  " +
					"and fbm_register.pk_corp in (select pk_corp1" +
					" from bd_settleunit unit" +
					" where pk_settlecent in (select bd_settlecenter.pk_settlecenter" +
					" from bd_settlecenter" +
					" where pk_corp = '"+ ClientEnvironment.getInstance().getCorporation().getPk_corp()+"')" +
					" union" +
					" select pk_corp1" +
					" from bd_settleunit unit" +
					" where unit.pk_corp1 = '"+ ClientEnvironment.getInstance().getCorporation().getPk_corp()+"'" +
					" union" +
					" select pk_corp from bd_corp where bd_corp.pk_corp = '"+ ClientEnvironment.getInstance().getCorporation().getPk_corp()+"'"

			+ ") ) gathertable ";
	
	//测试用
//	private String TABLE_NAME = "(select fbm_baseinfo.pk_baseinfo, fbmbillno," +
//				"pk_curr," +
//				"fbm_baseinfo.moneyy "
//			+ " from fbm_register join fbm_baseinfo on (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) " +
//				"where isnull(fbm_register.dr,0)=0 " +
//				"and isnull(fbm_baseinfo.dr,0)=0  " ;
//				"and fbm_register.pk_corp in (select pk_corp1" +
//				" from bd_settleunit unit" +
//				" where pk_settlecent in (select bd_settlecenter.pk_settlecenter" +
//				" from bd_settlecenter" +
//				" where pk_corp = '"+ ClientEnvironment.getInstance().getCorporation().getPk_corp()+"'))" +
//				" union" +
//				" select pk_corp1" +
//				" from bd_settleunit unit" +
//				" where unit.pk_corp1 = '"+ ClientEnvironment.getInstance().getCorporation().getPk_corp()+"'" +
//				" union" +
//				" select pk_corp from bd_corp where bd_corp.pk_corp = '"+ ClientEnvironment.getInstance().getCorporation().getPk_corp()+"'"
//			
//			+ ") ) gathertable ";
	
	
	/**
	 * add lsl 以上SQL语句翻译后的结果如下
	 */
	//	select fbm_baseinfo.pk_baseinfo, fbmbillno,pk_curr,fbm_baseinfo.moneyy 
	//
	//	from fbm_register join fbm_baseinfo on (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) 
	//	                 
	//	where isnull(fbm_register.dr,0)=0 and isnull(fbm_baseinfo.dr,0)=0 and fbm_register.pk_corp in 
	//	 
	//	 (
	//	      select pk_corp1   from bd_settleunit unit where pk_settlecent in 
	//	      
	//	        (
	//	          select bd_settlecenter.pk_settlecenter from bd_settlecenter where pk_corp = 【当前公司】
	//	                
	//	          union
	//	                
	//	          select pk_corp1 from bd_settleunit unit  where unit.pk_corp1 = 【当前公司】
	//	            
	//	          union
	//	                
	//	          select pk_corp from bd_corp where bd_corp.pk_corp = 【当前公司】
	//
	//	       )
	//	       
	//	  ) gathertable

	private String PK_FIELD_NAME = "pk_baseinfo";

	public QEBaseinfoRefModel() {
		super(null);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setDefaultFieldCount(3);
	}

	public QEBaseinfoRefModel(Container con) {
		super(con);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setDefaultFieldCount(3);
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] {"pk_baseinfo"};
	}

	@Override
	public String getRefNodeName() {
		// TODO Auto-generated method stub
		return PK_FIELD_NAME;
	}

	@Override
	public String getRefTitle() {
		// TODO Auto-generated method stub
		return TITLE;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

	@Override
	protected String getCurrencyName() {
		// TODO Auto-generated method stub
		return "pk_curr";
	}

	@Override
	protected String[] getMoneyKeys() {
		// TODO Auto-generated method stub
		return new String[]{"moneyy"};
	}



	@Override
	public String[][] getFormulas() {
		// TODO Auto-generated method stub
		return new String[][]{
				{"pk_curr","pk_curr->getColValue(bd_currtype,currtypename,pk_currtype,pk_curr)"}};
	}

	@Override
	public Vector getData() {
		// TODO Auto-generated method stub
		Vector v = super.getData();
		if(v!=null&& v.size() > 0){
			int len = v.size();
			for(int i = 0 ; i < len ; i++){
				Vector vj = (Vector)v.get(i);
				for(int j = 0 ; j < vj.size(); j++){
					Object obj = vj.get(j);
					if(obj instanceof BigDecimal){
						double newObj = ((BigDecimal)obj).doubleValue();
						vj.set(j, UFNumberFormat.format(newObj));
					}
				}
			}
		}
		return v;

	}
}
