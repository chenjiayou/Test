package nc.bs.fbm.upgrade.arapproc;

import nc.bs.fbm.upgrade.BaseInfoGenerator;
import nc.bs.fbm.upgrade.IDataProcessor;
import nc.vo.pub.BusinessException;


/**
 * 类功能说明：
     此类将DataProcessor类进行包装，提供额外服务
 * 日期：2007-11-22
 * 程序员： wues
 *
 */
public abstract class ArapAbstractDataProcessor implements IDataProcessor{

	//生成VO的pk
	protected String billPK = null;
	//单据编号
	protected String billNo = null;

	private BaseInfoGenerator baseGenerator = null;

	protected String get() throws BusinessException {
		if (null == baseGenerator) {
			try {
				baseGenerator = new BaseInfoGenerator();
			} catch(Exception e) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000346")/* @res"生成基本信息生成器"*/);
			}
		}
		billPK = baseGenerator.getPK();
		return billPK;
	}

}