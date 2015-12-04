package nc.vo.fbm.impawn;


/**
 * 
 * 功能：
       票据质押的唯一性校验
 * 日期：2007-10-8
 * 程序员：wues
 */
public class GetImpawnUniqueCheck implements java.io.Serializable, nc.vo.trade.pub.IBDGetCheckClass {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GetImpawnUniqueCheck() {
		super();
		
	}

	public String getCheckClass() {
		return nc.vo.fbm.impawn.ImpawnUniqueCheck.class.getName();
	}

}
