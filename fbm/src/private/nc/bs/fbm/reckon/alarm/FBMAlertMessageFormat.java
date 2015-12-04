package nc.bs.fbm.reckon.alarm;

/**
 * ��ʽ��Ԥ����ʾ��Ϣ�ࡣ
 * bsrl
 * �������ڣ�(2007-10-26) 
 */
 public class FBMAlertMessageFormat implements nc.bs.pub.pa.html.IAlertMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** ҵ����������Ϣ�ı��� */
	private java.lang.String m_title;
	/** ҵ����������Ϣ������ */
	private java.lang.String[] m_bottom;
	/** ҵ����������Ϣ������ */
	private java.lang.String[] m_top;

	/** ҵ����������Ϣ���ֶ����� */
	private String[] m_fields;
	/** ҵ����������Ϣ���ֶ����� */
	private  float [] m_widths;
	/** ҵ����������Ϣ�Ķ�Ӧ�ֶε����� */
	private java.lang.String[][] m_values;
/**
 * FBMAlertMessageFormat ������ע�⡣
 */
public FBMAlertMessageFormat() {
	super();
}
/**
 * FBMAlertMessageFormat ������ע�⡣
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
