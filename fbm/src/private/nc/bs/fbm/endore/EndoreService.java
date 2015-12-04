package nc.bs.fbm.endore;

import java.util.Hashtable;

import nc.bs.framework.common.NCLocator;
import nc.bs.trade.business.HYPubBO;
import nc.itf.uap.sf.ICreateCorpQueryService;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

public class EndoreService {

	/**
	 * <p>
	 * 　制证时改变背书单状态，写入制证人，制证日期。
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-3-20
	 * @param obj
	 * @param statusType
	 */
	public EndoreVO updateUnitVoucher(EndoreVO vo)throws BusinessException
	{
		HYPubBO bo = new HYPubBO();
		bo.update(vo);
		return (EndoreVO)bo.queryByPrimaryKey(EndoreVO.class, vo.getPrimaryKey());
	}

	/**
	 * <p>
	 * 　单据冲销时，改变单据状态为已冲销。
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-3-24
	 * @param endorevo
	 * @param statusType
	 * @throws BusinessException
	 */
	public void updateDestroyStatus(EndoreVO endorevo,int statusType)throws BusinessException
	{
		EndoreVO newEndorevo = endorevo;
		//改变背书办理单状态
		newEndorevo.setVbillstatus(statusType);
		HYPubBO bo = new HYPubBO();
		try
		{
			bo.update(endorevo);
		}catch(Exception e)
		{
			if(e.getMessage().length()>0)
			{
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000140")/*@res "改变背书办理单已冲销状态时出现异常！"*/);
			}

		}
	}

	/**
	 * <p>
	 * 　判断是否起用收付报
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-3-27
	 * @param pk_corp
	 * @param product
	 * @return
	 * @throws BusinessException
	 */
	public boolean productEnableByCorp(String pk_corp,String[] product) throws BusinessException{
		if(pk_corp == null){
			return false;
		}
		ICreateCorpQueryService ProductService = (ICreateCorpQueryService) NCLocator.getInstance().lookup(ICreateCorpQueryService.class.getName());
		Hashtable productEnabled = ProductService.queryProductEnabled( pk_corp,product);
		boolean flag = false;
		for(int i=0;i<product.length;i++)
		{
			if(((UFBoolean) productEnabled.get(product[i])).booleanValue()==true)
			{
				flag = true;
			}
		}
		return flag ;
	}

	/**
	 * <p>
	 *  根据PK_BASEINFO和ACTIONCODE查询FBM_Action表，查询出最近的一条记录，返回ActionVO。
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-4-18
	 * @param pk_baseinfo
	 * @param actioncode
	 * @return
	 */
	public ActionVO queryPK_Action(String pk_baseinfo,String actioncode) throws BusinessException{

		ActionVO returnvo = null;
		String sqlCondition = " pk_baseinfo = '" + pk_baseinfo + "' and actioncode = '" + actioncode + "'  order by serialnum desc ";
		HYPubBO hypubbo = new HYPubBO();
		ActionVO[] actionvo = (ActionVO[]) hypubbo.queryByCondition(ActionVO.class,sqlCondition);
		if(actionvo.length>0)
		{
			 returnvo = actionvo[0];
		}
		return returnvo;
	}

}