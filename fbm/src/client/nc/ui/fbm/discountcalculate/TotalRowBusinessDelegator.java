/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import nc.bs.logging.Logger;
import nc.ui.fac.account.pub.BDTakenBusinessDelegator;
import nc.ui.trade.controller.IControllerBase;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * �����ϼ��е�BusinessDelegator
 * <p>
 * �������ڣ�2006-9-26
 * 
 * @author lisg
 * @since v5.0
 */
public class TotalRowBusinessDelegator extends BDTakenBusinessDelegator {
	
	private IControllerBase m_ctl = null;
	
	public TotalRowBusinessDelegator(IControllerBase ctl){
		super();
		m_ctl = ctl;
	}

	/**
	 * @return the m_ctl
	 */
	public IControllerBase getController() {
		return m_ctl;
	}

	/**
	 * @param m_ctl the m_ctl to set
	 */
	public void setController(IControllerBase ctl) {
		this.m_ctl = ctl;
	}

	/**
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-26
	 *
	 * @see nc.ui.trade.bsdelegate.BusinessDelegator#queryBodyAllData(java.lang.Class, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public CircularlyAccessibleValueObject[] queryBodyAllData(Class voClass, String billType, String key, String strWhere) throws Exception {
		//1.ִ��ԭʼ����
		CircularlyAccessibleValueObject[] voR = super.queryBodyAllData(voClass, billType, key, strWhere);
		//2.������Ϊ
		voR = OrganizeTotalVO(voR);
		
		return voR;
	}

	/**
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-26
	 *
	 * @see nc.ui.trade.bsdelegate.BusinessDelegator#queryByCondition(java.lang.Class, java.lang.String)
	 */
	@Override
	public SuperVO[] queryByCondition(Class voClass, String strWhere) throws Exception {
		//1.ִ��ԭʼ����
		SuperVO[] voR = super.queryByCondition(voClass, strWhere);
		//2.������Ϊ
		voR = (SuperVO[])OrganizeTotalVO(voR);
		
		return voR;
	}
	
	/**
	 * <p>
	 * ���ڸ�����VO������֯,���ϼ��е���Ϣ
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-19
	 * @param vos
	 * @return
	 */
	public CircularlyAccessibleValueObject[] OrganizeTotalVO(CircularlyAccessibleValueObject[] vos){
		if(vos == null || vos.length == 0 || !TotalRowUITools.isLegalTotalController(getController())) return vos;
		
		Class voClass = vos[0].getClass();
		
		ITotalSpecify ts = (ITotalSpecify)getController();
		
		TotalRowPara trp = ts.getTotalRowPara(voClass.getName());
		//�����кϼƷ���
		if(trp == null || !trp.validate()) return vos;
		
		String KeyColumb =trp.getSpecifyFeild();
		String totalColumName =trp.getTotalDispName();
		String[] totalColums = trp.getTotalColName();
		String totaldistintColumName = trp.getDistinctColName();
		
		HashMap<String,Vector<CircularlyAccessibleValueObject>> specifyList = new HashMap<String,Vector<CircularlyAccessibleValueObject>>();
		HashMap<String, CircularlyAccessibleValueObject> totalList = new HashMap<String, CircularlyAccessibleValueObject> ();
		
		
		//���з���
		for(int index=0; index < vos.length; index++){
			Object key = vos[index].getAttributeValue(KeyColumb);
			
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
					//����key�Ͷ�Ӧflag�Ĺ�ϵ
					trp.getKeyWithFlag().put(trp.getTotalFlag(key), str_key);
				} catch (Exception e) {
					Logger.error(e.getMessage(),e);
				}
				
				specifyList.put(str_key, new Vector<CircularlyAccessibleValueObject>());
			}
			
			specifyList.get(str_key).add(vos[index]);
			//���ºϼ���VOֵ
			if(totalList.containsKey(str_key)){//���ںϼ���,ִ�кϼ��и���
				CircularlyAccessibleValueObject totalvo = totalList.get(str_key);
				for(String totalcolum : totalColums){
					Object cur = vos[index].getAttributeValue(totalcolum);
					Object sum = totalvo.getAttributeValue(totalcolum);
					
					if(cur == null){//��ǰֵΪnull,û�б�Ҫ�޸ĺϼ�ֵ
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
					}else{//����������ʱ������
						continue;
					}
					
					totalvo.setAttributeValue(totalcolum,sum);
				}
			}
		}
		
		Vector<CircularlyAccessibleValueObject> ret = new Vector<CircularlyAccessibleValueObject>();
		
		//���ϼ������Ӧ��ԭvo�ϲ�
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