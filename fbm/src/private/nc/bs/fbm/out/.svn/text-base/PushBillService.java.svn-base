package nc.bs.fbm.out;

import nc.bd.accperiod.AccountCalendar;
import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.trade.business.HYPubBO;
import nc.itf.cdm.util.CommonUtil;
import nc.vo.arap.outer.IArapGeneralObj;
import nc.vo.bd.b09.CumandocVO;
import nc.vo.bd.b23.AccbankVO;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.outer.CreateArapObj;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * 推式生成单据服务类
 * @author xwq
 *
 */
public class PushBillService {

	public PushBillService() {
		super();
	}

	/**
	 * 构造推式生成收付款对象
	 * @param vo
	 * @return
	 */
	public IArapGeneralObj buildArapObj(SuperVO vo,UFDate date) throws BusinessException{
		//校验是否已经生成单据
		checkExistBill(vo);

		String pk_billtypecode = (String)vo.getAttributeValue("pk_billtypecode");
		String pk_sourcefield = "pk_source";
		if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INVOICE)){
			pk_sourcefield = "pk_register";
		}
		String pk_register = (String) vo.getAttributeValue(pk_sourcefield);

		BaseDAO dao = new BaseDAO();
		RegisterVO registerVO = (RegisterVO) dao.retrieveByPK(RegisterVO.class, pk_register);
		BaseinfoVO baseinfoVO = (BaseinfoVO)dao.retrieveByPK(BaseinfoVO.class, registerVO.getPk_baseinfo());

		CreateArapObj obj = new CreateArapObj();


		String pk_cubasdoc = null;
		//设置区分单据类型的字段
		if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT) ){//托收
			obj.setDfybje((UFDouble)vo.getAttributeValue("moneyy"));
			obj.setDfbbje((UFDouble)vo.getAttributeValue("moneyb"));
			obj.setDffbje((UFDouble)vo.getAttributeValue("moneyf"));
			obj.setDirection(IArapGeneralObj.YS_DIRECTION);
			pk_cubasdoc = registerVO.getPaybillunit();
			String skbankacc = (String)vo.getAttributeValue(CollectionVO.HOLDERACC);
			if(skbankacc != null){
				AccbankVO accbankVO = (AccbankVO)dao.retrieveByPK(AccbankVO.class, skbankacc);
				obj.setSkbankacc(skbankacc);//收款银行账号
				obj.setSkbank(accbankVO.getBankname());//收款银行名称
			}
			obj.setBusdate(new UFDate(String.valueOf(vo.getAttributeValue(CollectionVO.DCOLLECTIONDATE))));//款项收妥日期
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)){//贴现
			obj.setDfybje((UFDouble)vo.getAttributeValue("moneyy"));
			obj.setDfbbje((UFDouble)vo.getAttributeValue("moneyb"));
			obj.setDffbje((UFDouble)vo.getAttributeValue("moneyf"));
			obj.setTxlx_ybje((UFDouble)vo.getAttributeValue("discountinterest"));
			obj.setTxlx_fbje((UFDouble)vo.getAttributeValue("interestmoneyf"));
			obj.setTxlx_bbje((UFDouble)vo.getAttributeValue("interestmoneyb"));
			obj.setYbtxfy((UFDouble)vo.getAttributeValue("discountcharge"));
			obj.setBbtxfy((UFDouble)vo.getAttributeValue("chargemoneyb"));
			obj.setFbtxfy((UFDouble)vo.getAttributeValue("chargemoneyf"));

			obj.setDirection(IArapGeneralObj.YS_DIRECTION);
			pk_cubasdoc = registerVO.getPaybillunit();
			String skbankacc = (String)vo.getAttributeValue(DiscountVO.DISCOUNT_ACCOUNT);
			if(skbankacc != null){
				AccbankVO accbankVO = (AccbankVO)dao.retrieveByPK(AccbankVO.class, skbankacc);
				obj.setSkbankacc(skbankacc);//收款银行账号
				obj.setSkbank(accbankVO.getBankname());//收款银行名称
			}
			obj.setBusdate(new UFDate(String.valueOf(vo.getAttributeValue(DiscountVO.DDISCOUNTDATE))));//贴现日期
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INVOICE) ){//开票
			//如果已付票标志打上则无法生成
			UFBoolean sfflag = (UFBoolean)vo.getAttributeValue(RegisterVO.SFFLAG);
			if(sfflag!= null && sfflag.booleanValue()){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000227")/* @res"票据已执行付票操作，无法生成划账结算单"*/);
			}


			obj.setDirection(IArapGeneralObj.ZZ_DIRECTION);
			pk_cubasdoc =baseinfoVO.getPayunit();
			obj.setBusdate(baseinfoVO.getInvoicedate());//出票日期
			//付款信息
			String fkbankacc = baseinfoVO.getPaybankacc();
			if(fkbankacc != null){
				AccbankVO accbankVO = (AccbankVO)dao.retrieveByPK(AccbankVO.class, fkbankacc);
				obj.setFkbankacc(fkbankacc);
				obj.setFkbank(accbankVO.getBankname());
			}
			String impawnmode = (String)vo.getAttributeValue(RegisterVO.IMPAWNMODE);
			obj.setImpawnmode(impawnmode);

			//保证金信息
			if(impawnmode.equals(FbmBusConstant.ASSURETYPE_BAIL)){
				String assureacc = (String)vo.getAttributeValue(RegisterVO.SECURITYACCOUNT);
				UFDouble assuremoneyy = (UFDouble)vo.getAttributeValue(RegisterVO.SECURITYMONEY);
				UFDouble assuremoneyb = (UFDouble)vo.getAttributeValue(RegisterVO.SECURITYMONEYB);
				UFDouble assuremoneyf = (UFDouble)vo.getAttributeValue(RegisterVO.SECURITYMONEYF);

				obj.setAssureacc(assureacc);
				obj.setAssuremoneyy(assuremoneyy);
				obj.setAssuremoneyb(assuremoneyb);
				obj.setAssuremoneyf(assuremoneyf);
			}
			//手续费信息
			UFDouble poundagemoneyy = (UFDouble)vo.getAttributeValue(RegisterVO.POUNDAGEMONEY);
			UFDouble poundagemoneyb = (UFDouble)vo.getAttributeValue(RegisterVO.POUNDAGEMONEYB);
			UFDouble poundagemoneyf = (UFDouble)vo.getAttributeValue(RegisterVO.POUNDAGEMONEYF);
			obj.setPoundagemoneyy(poundagemoneyy);
			obj.setPoundagemoneyb(poundagemoneyb);
			obj.setPoundagemoneyf(poundagemoneyf);

		}else if( pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BILLPAY)){//承兑
			obj.setJfybje((UFDouble)vo.getAttributeValue("moneyy"));
			obj.setJfbbje((UFDouble)vo.getAttributeValue("moneyb"));
			obj.setJffbje((UFDouble)vo.getAttributeValue("moneyf"));
			obj.setDirection(IArapGeneralObj.YF_DIRECTION);
			pk_cubasdoc = baseinfoVO.getReceiveunit();
			String skbankacc = baseinfoVO.getReceivebankacc();

			if(skbankacc!= null){
				AccbankVO accbankVO = (AccbankVO)dao.retrieveByPK(AccbankVO.class, skbankacc);
				obj.setSkbankacc(skbankacc);//收款银行账号
				obj.setSkbank(accbankVO.getBankname());//收款银行名称
			}

			String fkbankacc = baseinfoVO.getPaybankacc();
			if(fkbankacc!=null){
				AccbankVO accbankVO = (AccbankVO)dao.retrieveByPK(AccbankVO.class, fkbankacc);
				obj.setFkbankacc(fkbankacc);
				if(accbankVO != null){
					obj.setFkbank(accbankVO.getBankname());
				}
			}

			obj.setBusdate(new UFDate(String.valueOf(vo.getAttributeValue(AcceptVO.DACCEPTDATE))));//付款日期
		}
		//设置不区分单据类型字段
		obj.setPk_corp((String)vo.getAttributeValue("pk_corp"));
		obj.setPk_curr(baseinfoVO.getPk_curr());//币种编码
		obj.setFbmbillno(baseinfoVO.getFbmbillno());//票据编号
		obj.setPk_busibill(vo.getPrimaryKey());
		obj.setPk_billtypecode(pk_billtypecode);//单据类型
		obj.setOperator(InvocationInfoProxy.getInstance().getUserCode());
		obj.setOperatordate(date);
		obj.setPk_id(vo.getPrimaryKey());
		obj.setBbhl((UFDouble)vo.getAttributeValue("brate"));
		obj.setFbhl((UFDouble)vo.getAttributeValue("frate"));

		String cust_sql = null;
		if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)){//参照客户
			cust_sql = "pk_cubasdoc='" + pk_cubasdoc + "' and pk_corp='" + (String)vo.getAttributeValue("pk_corp")+ "' and (custflag='0' or custflag='2')";
		}
		if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INVOICE)|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BILLPAY)){//参照供应商
			cust_sql = "pk_cubasdoc='" + pk_cubasdoc + "' and pk_corp='" + (String)vo.getAttributeValue("pk_corp")+ "' and (custflag='1' or custflag='2')";
		}
		SuperVO[] cusmanVOs = new HYPubBO().queryByCondition(CumandocVO.class,cust_sql );
		if(cusmanVOs != null && cusmanVOs.length >0){
			obj.setPk_cust(String.valueOf(cusmanVOs[0].getAttributeValue("pk_cumandoc")));
		}

		//设置会计年度和会计期间
		AccountCalendar calendar = AccountCalendar.getInstance();
		calendar.setDate(date);
		nc.vo.bd.period.AccperiodVO accpriodvo = calendar.getYearVO();
		obj.setDjkjnd(accpriodvo.getPeriodyear());
		nc.vo.bd.period2.AccperiodmonthVO monthVO = calendar.getMonthVO();
		obj.setDjkjqj(monthVO.getMonth());

		return obj;
	}



	/**
	 * 检查是否已经推式生成单据
	 * @param vo
	 * @throws BusinessException
	 */
	protected void checkExistBill(SuperVO vo) throws BusinessException{
		String pk = vo.getPrimaryKey();
		OuterRelationDAO dao = new OuterRelationDAO();
		OuterVO[] outerVos = dao.queryByPkBusibill(pk);

		String pk_billtypecode = (String)vo.getAttributeValue("pk_billtypecode");
		if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT) || pk_billtypecode.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT) || pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BILLPAY)){
			//托收或贴现 必须没有外部关联才能生成单据
			if(!CommonUtil.isNull(outerVos)){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000228")/* @res"已经生成收付报单据"*/);
			}
		}else if(pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INVOICE)){
			//外部关联如果有划账结算单则无法再次生成
			if(!CommonUtil.isNull(outerVos)){
				int len = outerVos.length ;
				String djdl = null;
				for(int i = 0; i < len ; i ++){
					djdl = outerVos[i].getOuterdjdl();
					if(djdl.equals("hj")){
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000228")/* @res"已经生成收付报单据"*/);
					}
				}
			}
		}
	}
}