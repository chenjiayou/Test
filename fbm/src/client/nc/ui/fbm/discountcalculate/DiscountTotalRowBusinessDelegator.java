/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import nc.bs.logging.Logger;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.trade.controller.IControllerBase;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDouble;

/**
 * @author lisg
 *
 */
public class DiscountTotalRowBusinessDelegator extends
		TotalRowBusinessDelegator {

	IRefTakenProccessor processor = null;
	public DiscountTotalRowBusinessDelegator(IControllerBase ctl, IRefTakenProccessor processor) {
		super(ctl);
		this.processor = processor;
	}
	
	/**
	 * <p>
	 * 对于给定的VO进行组织,填充合计行等信息
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-19
	 * @param vos
	 * @return
	 */
	public CircularlyAccessibleValueObject[] OrganizeTotalVO(CircularlyAccessibleValueObject[] vos){
		if(vos == null || vos.length == 0 || !TotalRowUITools.isLegalTotalController(getController())) return vos;
		
		Class voClass = vos[0].getClass();
		
		ITotalSpecify ts = (ITotalSpecify)getController();
		
		TotalRowPara trp = ts.getTotalRowPara(voClass.getName());
		//不进行合计分类
		if(trp == null || !trp.validate()) return vos;
		
		String KeyColumb =trp.getSpecifyFeild();
		String totalColumName =trp.getTotalDispName();
		String[] totalColums = trp.getTotalColName();
		String totaldistintColumName = trp.getDistinctColName();
		
		HashMap<String,Vector<CircularlyAccessibleValueObject>> specifyList = new HashMap<String,Vector<CircularlyAccessibleValueObject>>();
		HashMap<String, CircularlyAccessibleValueObject> totalList = new HashMap<String, CircularlyAccessibleValueObject> ();
		
		
		//进行分组
		for(int index=0; index < vos.length; index++){
			Object key = vos[index].getAttributeValue(KeyColumb);
			if(key == null && processor !=null){
				key = processor.getValueByTakenInVO(RegisterVO.TABLENAME, vos[index], KeyColumb);
			}
			
			String str_key = null;
			if(key == null){
				str_key = "";
			}else{
				str_key = key.toString();
			}
			if(!specifyList.containsKey(str_key)){
				try {
					CircularlyAccessibleValueObject totalvo = (CircularlyAccessibleValueObject)voClass.newInstance();
					if(totalColumName != null){
						totalvo.setAttributeValue(totalColumName, trp.getTotalFlagName(key));
						totalvo.setAttributeValue(totaldistintColumName, trp.getTotalFlag(key));
					}
					totalList.put(str_key, totalvo);
					//储备key和对应flag的关系
					trp.getKeyWithFlag().put(trp.getTotalFlag(key), str_key);
				} catch (Exception e) {
					Logger.error(e.getMessage(),e);
				}
				
				specifyList.put(str_key, new Vector<CircularlyAccessibleValueObject>());
			}
			
			specifyList.get(str_key).add(vos[index]);
			//更新合计行VO值
			if(totalList.containsKey(str_key)){//存在合计行,执行合计行更新
				CircularlyAccessibleValueObject totalvo = totalList.get(str_key);
				for(String totalcolum : totalColums){
					Object cur = vos[index].getAttributeValue(totalcolum);
					Object sum = totalvo.getAttributeValue(totalcolum);
					
					if(cur == null){//当前值为null,没有必要修改合计值
						continue;
					}
					
					if(cur instanceof UFDouble){
						if(sum == null){
							sum = cur;
						}else{
							sum = ((UFDouble)sum).add((UFDouble)cur);
						}
					}else if(cur instanceof Integer){
						if(sum == null){
							sum = cur;
						}else{
							sum = ((Integer)sum) + ((Integer)cur);
						}
					}else{//其他类型暂时不处理
						continue;
					}
					
					totalvo.setAttributeValue(totalcolum,sum);
				}
			}
		}
		
		Vector<CircularlyAccessibleValueObject> ret = new Vector<CircularlyAccessibleValueObject>();
		
		//将合计行与对应的原vo合并
		Iterator<String> iter = specifyList.keySet().iterator();
		while(iter.hasNext()){
			String vokey = iter.next();
			ret.addAll(specifyList.get(vokey));
			
			if(totalList.containsKey(vokey)){
				ret.add(totalList.get(vokey));
			}
		}
		
		ret.trimToSize();
		
		try {
			return ret.toArray((CircularlyAccessibleValueObject[])Array.newInstance(voClass, 0));
		} catch (Exception e) {
			Logger.error(e.getMessage(),e);
			return vos;
		}
		
	}

}
