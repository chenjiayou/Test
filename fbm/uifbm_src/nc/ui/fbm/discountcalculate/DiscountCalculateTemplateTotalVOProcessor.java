/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import nc.ui.querytemplate.IQueryTemplateTotalVOProcessor;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.pub.query.QueryTempletTotalVO;

/**
 * @author bsrl
 *
 */
public class DiscountCalculateTemplateTotalVOProcessor implements
		IQueryTemplateTotalVOProcessor {

	/**
	 * 
	 */
	public DiscountCalculateTemplateTotalVOProcessor() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.querytemplate.IQueryTemplateTotalVOProcessor#processQueryTempletTotalVO(nc.vo.pub.query.QueryTempletTotalVO)
	 */
	public void processQueryTempletTotalVO(QueryTempletTotalVO totalVO) {
		QueryConditionVO[] vos = totalVO.getConditionVOs();
		for (int i = 0; i < vos.length; i++) {
			QueryConditionVO queryConditionVO = vos[i];
			queryConditionVO.setFieldCode(queryConditionVO.getFieldCode().replaceFirst("fbm_register", "v_fbm_regbase"));
		}
		totalVO.setConditionVOs(vos);
//		for (int i = 0; i < conditionVOs.length; i++) {
//			 conditionVOs[i].getFieldCode().replaceFirst("fbm_register", "v_fbm_regbase");
//		}

	}

}
