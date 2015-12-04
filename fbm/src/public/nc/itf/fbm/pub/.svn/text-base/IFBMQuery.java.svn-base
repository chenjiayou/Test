package nc.itf.fbm.pub;

import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;

public interface IFBMQuery {

	/**
	 * 查询表体接口，在实现类中用公司查询参照中要显示的名称，省去了 参照的连接数，提高效率。
	 * 
	 * @param c
	 * @param wherePart
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CircularlyAccessibleValueObject[] queryBodyData(Class c,
			String wherePart) throws BusinessException;

	/**
	 * 前台界面 Listener 参照协带时调后台次数过多，这里通传pks查询出多个VO一次返回所有表体行。
	 * 
	 * @param c
	 * @param pks
	 * @return
	 * @throws BusinessException
	 */
	public CircularlyAccessibleValueObject[] queryBodyVOs(SuperVO supervo,
			String[] pks)
			throws BusinessException;
}
