package nc.bs.fbm.report;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import nc.itf.cdm.util.CommonUtil;
import nc.vo.fbm.report.ReportParam;
import nc.vo.fvm.fundcashquery.IVarNameDefine;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.dbbase.QEDataSet;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.querymodel.DatasetUtil;

import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.StorageDataSet;
import com.borland.dx.dataset.Variant;

public class FbmBaseReportBO {
	
	protected String makeQueryConditionSql(ReportParam reportParam){
		StringBuffer sb = new StringBuffer();
		
		sb.append(" 1=1 ");
		if(!CommonUtil.isNull(reportParam.getFbmbillno())){
			sb.append(" and fbmbillno='"+reportParam.getFbmbillno()+"'");
		}
		if(!CommonUtil.isNull(reportParam.getPk_baseinfo())){
			sb.append(" and fbm_baseinfo.pk_baseinfo='"+reportParam.getPk_baseinfo()+"'");
		}
		if(!CommonUtil.isNull(reportParam.getReceiveunit())){
			sb.append(" and receiveunit='"+reportParam.getReceiveunit()+"'");
		}
		if(!CommonUtil.isNull(reportParam.getPayunit())){
			sb.append(" and payunit='"+reportParam.getPayunit()+"'");
		}
		if(!CommonUtil.isNull(reportParam.getInvoicedate_begin())){
			sb.append(" and invoicedate>='"+reportParam.getInvoicedate_begin()+"'");
		}
		if(!CommonUtil.isNull(reportParam.getInvoicedate_end())){
			sb.append(" and invoicedate <='"+reportParam.getInvoicedate_end()+"'");
		}
		if(!CommonUtil.isNull(reportParam.getEnddate_begin())){
			sb.append(" and enddate>='"+reportParam.getEnddate_begin()+"'");
		}
		if(!CommonUtil.isNull(reportParam.getEnddate_end())){
			sb.append(" and enddate<='"+reportParam.getEnddate_end()+"'");
		}
		if(!CommonUtil.isNull(reportParam.getPk_curr())){
			sb.append(" and pk_curr='"+reportParam.getPk_curr()+"'");
		}
		if(!CommonUtil.isNull(reportParam.getMoney_begin())){
			sb.append(" and fbm_baseinfo.moneyy>="+reportParam.getMoney_begin()+"");
		}
		if(!CommonUtil.isNull(reportParam.getMoney_end())){
			sb.append(" and fbm_baseinfo.moneyy <="+reportParam.getMoney_end()+"");
		}
		if(!CommonUtil.isNull(reportParam.getFbmbilltype())){
			sb.append(" and fbmbilltype = '"+reportParam.getFbmbilltype()+"'");
		}
		if(!CommonUtil.isNull(reportParam.getOrientation())){
			sb.append(" and orientation = '"+reportParam.getOrientation()+"'");
	
		}
		if(!CommonUtil.isNull(reportParam.getFbmbillstatus())){
			String fbmbillstatus = reportParam.getFbmbillstatus();

			Map<String,String> chinamap = getStatusChinaKeyMap();
			sb.append(" and fbm_action.endstatus = " + chinamap.get("'"+fbmbillstatus+"'"));
		}
		
		return sb.toString();
		
	}
	
	public  StorageDataSet getDataSetBySuperVos(SuperVO[] vos, int digit,Class cls) throws BusinessException{
		int iRowCount = (vos == null) ? 0 : vos.length;
		SuperVO vo = null;
		if (iRowCount == 0) {// 查询数据为空，必须构造column
		// System.out.println("DatasetUtil.getDatasetByCAVOs：行数为0");
			try {
				vo = (SuperVO) cls.newInstance();
			} catch (Exception e) {
				throw new BusinessException(e);
			}
		} else {
			vo = vos[0];
			cls = vo.getClass();
		}

		// 反射获得类属性信息
		QEDataSet sds = new QEDataSet();
		try {
			String names[] = vo.getAttributeNames();
			String realnames[] = null;
			if (vo instanceof IVarNameDefine) {
				realnames = ((IVarNameDefine) vo).getVaribleNames();
			}

			// 获得列数
			int iColCount = (names == null) ? 0 : names.length;

			Vector vecName = new Vector();
			Vector vecCol = new Vector();
			for (int i = 0; i < iColCount; i++) {
				try {
					// 创建列
					Column col = new Column();
					if (!CommonUtil.isNull(realnames)
							&& !CommonUtil.isNull(realnames[i])) {
						col.setCaption(realnames[i]);

						col.setColumnName(names[i]);
						// 记录
						vecName.addElement(names[i]);
						vecCol.addElement(col);
						// 获得列类型
						Field fld = null;
						try {
							fld = cls.getField( names[i]);
						} catch (NoSuchFieldException e) {
							throw new BusinessException(e);
						}
						int iType = class2VariantType(fld.getType());
						/*
						 * int iType = Variant.STRING; Object obj =
						 * vos[0].getAttributeValue(names[i]); if (obj != null &&
						 * obj.getClass().getSuperclass() == Number.class) iType =
						 * Variant.DOUBLE;
						 */
						col.setDataType(iType);
					}

				} catch (Exception e) {
					throw new BusinessException(e);
				}
			}
			// 获得有效列数
			iColCount = vecName.size();
			if (iColCount == 0) {
				// System.out.println("DatasetUtil.getDatasetByCAVOs：列数为0");
				return null;
			}
			// 重构列名数组和列数组
			names = new String[iColCount];
			vecName.copyInto(names);
			Column[] cols = new Column[iColCount];
			vecCol.copyInto(cols);

			// 转换
			sds = new QEDataSet();
			sds.setColumns(cols);
			sds.open();
			for (int i = 0; i < iRowCount; i++) {
				// 构造数据行
				DataRow row = new DataRow(sds);
				for (int j = 0; j < iColCount; j++) {
					Object obj = vos[i].getAttributeValue(names[j]);
					String str = (obj == null) ? null : obj.toString();
					int iColType = cols[j].getDataType();
					// 填充数据行
					DatasetUtil.makeDataRow(row, str, j, iColType);
					row = row;
				}
				// 加行
				sds.addRow(row);
			}
			sds.first();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return sds;
	}
	
	/**
	 * JAVA类型转化为Variant类型 创建日期：(2002-12-2 10:35:40)
	 * 
	 * @return int
	 * @param clas
	 *            java.lang.Class
	 */
	private  int class2VariantType(Class cls) {
		int varType = Variant.STRING;
		if (cls == Integer.class)
			varType = Variant.INT;
		else if (cls == UFDouble.class)
			varType = Variant.DOUBLE;
		return varType;
	}

	protected Map<String,String> getStatusChinaKeyMap(){
		Map<String,String> valuemap = getStatusEnglishKeyMap();
		Map<String,String> newvaluemap = new HashMap<String,String>();
		Set s = valuemap.keySet();
		Iterator itr = s.iterator();
		String tmp= null;
		while(itr.hasNext()){
			tmp = (String) itr.next();
			newvaluemap.put(valuemap.get(tmp),tmp);
		}
		return newvaluemap;
	}
 	protected Map<String,String> getStatusEnglishKeyMap(){
		Map<String,String> valuemap = new HashMap<String,String>();
		valuemap = new HashMap<String,String>();
		valuemap.put("'none'", "''");
		valuemap.put("'on_gather'","'在收票'");
		valuemap.put("'register'","'已登记'");
		valuemap.put("'on_endore'","'在背书'");
		valuemap.put("'has_endore'","'已背书'");
		valuemap.put("'on_discount'","'在贴现'");
		valuemap.put("'has_discount'","'已贴现'");
		valuemap.put("'on_collection'","'在托收'");
		valuemap.put("'has_collection'","'已托收'");
		valuemap.put("'on_bank_keep'","'在银行托管'");
		valuemap.put("'has_bank_keep'","'已银行托管'");
		valuemap.put("'on_bank_back'","'在银行领用'");
		valuemap.put("'on_inner_keep'","'在内部托管'");
		valuemap.put("'has_inner_keep'","'已内部托管'");
		valuemap.put("'on_inner_back'","'在内部领用'");
		valuemap.put("'on_relief_keep'","'在调剂托管'");
		valuemap.put("'has_relief_keep'","'已调剂托管'");
		valuemap.put("'on_impawn'","'在质押'");
		valuemap.put("'has_impawn'","'已质押'");
		valuemap.put("'on_impawn_back'","'在质押回收'");
		valuemap.put("'has_disable'","'已作废'");
		valuemap.put("'on_return'","'在退票'");
		valuemap.put("'has_return'","'已退票'");
		valuemap.put("'on_invoice'","'在开票'");
		valuemap.put("'has_invoice'","'已开票'");
		valuemap.put("'on_pay'","'在付款'");
		valuemap.put("'has_pay'","'已付款'");
		valuemap.put("'on_paybill'","'在付票'");
		valuemap.put("'has_paybill'","'已付票'");
		valuemap.put("'on_relief'","'在调剂'");
		valuemap.put("'has_relief'","'已调剂'");
		valuemap.put("'has_destroy'","'已核销'");
		valuemap.put("'has_center_use'","'已中心使用'");
		valuemap.put("'has_clear'","'已冲销'");
		valuemap.put("'on_center_return'","'在中心退出'");
		valuemap.put("'has_center_return'","'已中心退出'");
		valuemap.put("'on_unit_return'","'在单位退入'");
		valuemap.put("'has_unit_return'","'已单位退入'");
		return valuemap;
	}
}
