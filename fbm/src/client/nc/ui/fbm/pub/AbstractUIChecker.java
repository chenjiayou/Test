/**
 *
 */
package nc.ui.fbm.pub;


/**
 * <p>
 *
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-8-16
 *
 */
public abstract class AbstractUIChecker implements IUIChecker {
	private FBMManageUI ui;
	/**
	 * 
	 */
	public AbstractUIChecker() {
		// TODO Auto-generated constructor stub
	}
	
	

	public AbstractUIChecker(FBMManageUI ui) {
		super();
		this.ui = ui;
	}

	/**
	 * У�������ʵ�ֵķ���
	 * @return String ������Ϣ/У��ɹ��򷵻�null
	 */
	public abstract String check();
	
	protected FBMManageUI getUI(){
		return ui;
	}

	
	/**
	 * <p>
	 * �ж�Ϊ�ա�
	 * <p>
	 * ���ߣ�qbh <br>
	 * ���ڣ�2005-12-8
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNULL(Object o) {
		if (o == null || o.toString().trim().length()==0) {
			return true;
		}
		return false;
	}
	
}
