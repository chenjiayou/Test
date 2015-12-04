package nc.bs.fbm.reckon.alarm;

/**
 * 格式化预警提示信息类。
 * bsrl
 * 创建日期：(2007-10-26) 
 */
 public class FBMAlertMessageFormat implements nc.bs.pub.pa.html.IAlertMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 业务插件返回信息的标题 */
	private java.lang.String m_title;
	/** 业务插件返回信息的描述 */
	private java.lang.String[] m_bottom;
	/** 业务插件返回信息的描述 */
	private java.lang.String[] m_top;

	/** 业务插件返回信息的字段数组 */
	private String[] m_fields;
	/** 业务插件返回信息的字段数组 */
	private  float [] m_widths;
	/** 业务插件返回信息的对应字段的内容 */
	private java.lang.String[][] m_values;
/**
 * FBMAlertMessageFormat 构造子注解。
 */
public FBMAlertMessageFormat() {
	super();
}
/**
 * FBMAlertMessageFormat 构造子注解。
 */
public FBMAlertMessageFormat(
	String title,
	String []top,
	String[] fields,
	String[][] value,
	float[] widths,
	String[] bottom) {
	super();
	setTitle(title);
	setTop(top);
	setBodyFields(fields);
	setBodyValue(value);
	setBodyWidths(widths);
	setBottom(bottom);
}

public String[] getBodyFields() {
	
	return m_fields;
}

public Object[][] getBodyValue() {
	
	return m_values;
}

public float[] getBodyWidths() {
	return m_widths;
}

public String[] getBottom() {
	
	return m_bottom;
}

public String getTitle() {
	return m_title;
}

public String[] getTop() {
	
	return m_top;
}

public void setBodyFields(String [] fields) {
	m_fields=fields;
}

public void setBodyValue( String [][] values) {
	m_values=values;
}

public void setBodyWidths( float [] widths) {
	m_widths=widths;
	
}

public void setBottom(String [] bottom) {
	m_bottom=bottom;
}

public void setTitle(String title) {
	m_title=title;
}

public void setTop(String [] top) {
	m_top=top;
}
}
