package nc.vo.fbm.outer;

import java.io.Serializable;

import nc.itf.cdm.util.MathUtil;
import nc.vo.arap.outer.IArapGeneralObj;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.ep.dj.DJZBVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

public class CreateArapObj implements IArapGeneralObj,Serializable{

	private String pk_curr;
	
	private String pk_cust;
	
	private String pk_busibill;
	
	private String pk_billtypecode;
	
	private String pk_corp;
	
	private String fbmbillno;//单据编号
	
	private UFDate busdate;//单据业务日期
	
	private int direction;//收付方向
	
	private String djkjqj;//会计期间,其实是会计月份
	
	private String djkjnd;//单据会计年度
	
	private UFDouble dfybje;//贷方原币金额
	private UFDouble dfbbje;//贷方本币金额
	private UFDouble dffbje;//贷方辅币金额
	
	private UFDouble jfybje;//借方原币金额
	private UFDouble jfbbje;//借方本币金额
	private UFDouble jffbje;//借方辅币金额

	private UFDouble bbhl;//本币汇率
	 
	private UFDouble fbhl;//辅币汇率
	
	
	private String pk_id;//单据PK

	private String operator;//操作人
	
	private UFDate operatordate;//操作日期
	
	private String fkbank;//付款银行
	private String fkbankacc;//付款银行账号
	private String skbank;//收款银行
	private String skbankacc;//收款银行账号
	
	private UFDouble txlx_ybje;//贴现利息原币金额
	private UFDouble txlx_bbje;//贴现利息本币金额
	private UFDouble txlx_fbje;//贴现利息辅币金额
	
	private UFDouble ybtxfy;//贴现手续费原币金额
	private UFDouble bbtxfy;//贴现手续费本币金额
	private UFDouble fbtxfy;//贴现手续费辅币金额
	
	//划账结算单部分字段
	private String assureacc;//保证金账户
	private UFDouble assuremoneyy;//保证金金额
	private UFDouble assuremoneyf;
	private UFDouble assuremoneyb;
	private UFDouble poundagemoneyy ;//手续费
	private UFDouble poundagemoneyf;
	private UFDouble poundagemoneyb;
	
	private String impawnmode;//担保方式
	
	public DJZBVO getBillVO() {
		DJZBVO bill = new DJZBVO();
		DJZBHeaderVO head= new DJZBHeaderVO();
		head.setBzbm(pk_curr);
		//head.setKsbm_cl(pk_cust);
		head.setLybz(FbmBusConstant.SYSTEM_FBM);
		head.setDdlx(pk_busibill);
		head.setDwbm(pk_corp);
		head.setDjkjnd(djkjnd);
		head.setDjkjqj(djkjqj);
		head.setDjrq(busdate);//单据日期
		head.setQcbz(new UFBoolean(false));//期初标志
		//head.setKsbm_cl(pk_cust);
		head.setZdr(operator);
		head.setZdrq(operatordate);
		head.setLrr(operator);
		//设置金额
		head.setBbhl(bbhl);
		head.setFbhl(fbhl);
		DJZBItemVO[] items= new DJZBItemVO[1] ;
		items[0] = new DJZBItemVO();
		if(direction == YF_DIRECTION ){
			head.setYbje(jfybje); 
			head.setFbje(jffbje);
			head.setBbje(jfbbje);
			items[0].setJfybje(jfybje);
			items[0].setJfbbje(jfbbje);
			items[0].setJffbje(jffbje);
			items[0].setYbye(jfybje);
			items[0].setJfybwsje(jfybje);
			
		}else if(direction == YS_DIRECTION){
			head.setYbje(dfybje);
			head.setFbje(dffbje);
			head.setBbje(dfbbje);
			items[0].setDfybje(dfybje);
			items[0].setDfbbje(dfbbje);
			items[0].setDffbje(dffbje);
			items[0].setYbye(dfybje);
			items[0].setDfybwsje(dfybje);
			items[0].setTxlx_ybje(txlx_ybje);
			items[0].setTxlx_fbje(txlx_fbje);
			items[0].setTxlx_bbje(txlx_bbje);
			
			items[0].setYbtxfy(ybtxfy);
			items[0].setBbtxfy(bbtxfy);
			items[0].setFbtxfy(fbtxfy);
		}
		
		items[0].setPjh(fbmbillno);
		items[0].setBzbm(pk_curr);

		items[0].setBbhl(bbhl);
		items[0].setFbhl(fbhl);
		items[0].setDdlx(pk_busibill);//上游单据pk
		items[0].setDdhh(pk_busibill);
		items[0].setJsfsbm(pk_billtypecode);//业务单据类型
		items[0].setSkyhzh(skbankacc);//收款银行账号
		items[0].setSkyhmc(skbank);//收款银行名称
		items[0].setFkyhzh(fkbankacc);//付款银行账号
		items[0].setFkyhmc(fkbank);//付款银行名称
		items[0].setQxrq(busdate);//起效日期
		
		if(direction == ZZ_DIRECTION){//划账结算单
			if(FbmBusConstant.ASSURETYPE_BAIL.equals(impawnmode)){
				if(MathUtil.isZero(assuremoneyf)){
					assuremoneyf = new UFDouble(0);
				}
				if(MathUtil.isZero(assuremoneyb)){
					assuremoneyb = new UFDouble(0);
				}
				if(MathUtil.isZero(poundagemoneyf)){
					poundagemoneyf = new UFDouble(0);
				}
				if(MathUtil.isZero(poundagemoneyb)){
					poundagemoneyb = new UFDouble(0); 
				}
				items = new DJZBItemVO[3];
				//付款信息
				items[0] = new DJZBItemVO();
				items[0].setPjh(fbmbillno);
				items[0].setBzbm(pk_curr);
				items[0].setJfybje(assuremoneyy.add(poundagemoneyy));
				items[0].setJffbje(assuremoneyf.add(poundagemoneyf));
				items[0].setJfbbje(assuremoneyb.add(poundagemoneyb));
				items[0].setYbye(assuremoneyy.add(poundagemoneyy));
				items[0].setJfybwsje(assuremoneyy.add(poundagemoneyy));
				items[0].setBbhl(bbhl);
				items[0].setFbhl(fbhl);
				items[0].setDdlx(pk_id);//上游单据pk
				items[0].setDdhh(pk_id);
				items[0].setJsfsbm(pk_billtypecode);//业务单据类型
				items[0].setFkyhzh(fkbankacc);//付款银行账号
				items[0].setFkyhmc(fkbank);//付款银行名称
				items[0].setQxrq(busdate);//起效日期
				//保证金信息
				items[1] = new DJZBItemVO();
				items[1].setPjh(fbmbillno);
				items[1].setDdlx(pk_id);//上游单据pk
				items[1].setDdhh(pk_id);
				items[1].setJsfsbm(pk_billtypecode);//业务单据类型
				items[1].setBzbm(pk_curr);
				items[1].setSkyhzh(assureacc);
				items[1].setDfybje(assuremoneyy);
				items[1].setDfbbje(assuremoneyb);
				items[1].setDffbje(assuremoneyf);
				items[1].setYbye(assuremoneyy);
				items[1].setDfybwsje(assuremoneyy);
				items[1].setBbhl(bbhl);
				items[1].setFbhl(fbhl);
				items[1].setQxrq(busdate);//起效日期
				//手续费信息
				items[2] = new DJZBItemVO();
				items[2].setPjh(fbmbillno);
				items[2].setDdlx(pk_id);//上游单据pk
				items[2].setDdhh(pk_id);
				items[2].setJsfsbm(pk_billtypecode);//业务单据类型
				items[2].setBzbm(pk_curr);
				items[2].setDfybje(poundagemoneyy);
				items[2].setDfbbje(poundagemoneyb);
				items[2].setDffbje(poundagemoneyf);
				items[2].setYbye(poundagemoneyy);
				items[2].setDfybwsje(poundagemoneyy);
				items[2].setBbhl(bbhl);
				items[2].setFbhl(fbhl);
				items[2].setQxrq(busdate);//起效日期
//				items[2].setFkyhzh(fkbankacc);//付款银行账号
//				items[2].setFkyhmc(fkbank);//付款银行名称
			}else{
				items = new DJZBItemVO[2];
				//付款信息
				items[0] = new DJZBItemVO();
				items[0].setPjh(fbmbillno);
				items[0].setDdlx(pk_id);//上游单据pk
				items[0].setDdhh(pk_id);
				items[0].setJsfsbm(pk_billtypecode);//业务单据类型
				items[0].setBzbm(pk_curr);
				items[0].setJfybje(poundagemoneyy);
				items[0].setJffbje(poundagemoneyf);
				items[0].setJfbbje(poundagemoneyb);
				items[0].setYbye(poundagemoneyy);
				items[0].setJfybwsje(poundagemoneyy);
				items[0].setBbhl(bbhl);
				items[0].setFbhl(fbhl);
				items[0].setDdlx(pk_id);//上游单据pk
				items[0].setFkyhzh(fkbankacc);//付款银行账号
				items[0].setFkyhmc(fkbank);//付款银行名称
				items[0].setQxrq(busdate);//起效日期

				//手续费信息
				items[1] = new DJZBItemVO();
				items[1].setPjh(fbmbillno);
				items[1].setDdlx(pk_id);//上游单据pk
				items[1].setDdhh(pk_id);
				items[1].setJsfsbm(pk_billtypecode);//业务单据类型
				items[1].setBzbm(pk_curr);
				items[1].setDfybje(poundagemoneyy);
				items[1].setDfbbje(poundagemoneyb);
				items[1].setDffbje(poundagemoneyf);
				items[1].setYbye(poundagemoneyy);
				items[1].setDfybwsje(poundagemoneyy);
				items[1].setBbhl(bbhl);
				items[1].setFbhl(fbhl);
				items[1].setQxrq(busdate);//起效日期
//				items[1].setFkyhzh(fkbankacc);//付款银行账号
//				items[1].setFkyhmc(fkbank);//付款银行名称
			}

		}
		
		
		
		bill.setParentVO(head);
		bill.setChildrenVO(items);
		return bill;
	}

	public int getDirection() {
		// TODO Auto-generated method stub
		return direction;
	}

	public String getPk_curr() {
		return pk_curr;
	}

	public void setPk_curr(String pk_curr) {
		this.pk_curr = pk_curr;
	}

	public String getPk_cust() {
		return pk_cust;
	}

	public void setPk_cust(String pk_cust) {
		this.pk_cust = pk_cust;
	}


	
	public String getPk_busibill() {
		return pk_busibill;
	}

	public void setPk_busibill(String pk_busibill) {
		this.pk_busibill = pk_busibill;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getFbmbillno() {
		return fbmbillno;
	}

	public void setFbmbillno(String fbmbillno) {
		this.fbmbillno = fbmbillno;
	}

	public UFDate getBusdate() {
		return busdate;
	}

	public void setBusdate(UFDate busdate) {
		this.busdate = busdate;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public String getDjkjqj() {
		return djkjqj;
	}

	public void setDjkjqj(String djkjqj) {
		this.djkjqj = djkjqj;
	}

	public String getDjkjnd() {
		return djkjnd;
	}

	public void setDjkjnd(String djkjnd) {
		this.djkjnd = djkjnd;
	}

	public UFDouble getDfybje() {
		return dfybje;
	}

	public void setDfybje(UFDouble dfybje) {
		this.dfybje = dfybje;
	}

	public UFDouble getJfybje() {
		return jfybje;
	}

	public void setJfybje(UFDouble jfybje) {
		this.jfybje = jfybje;
	}

	public String getPk_id() {
		return pk_id;
	}

	public void setPk_id(String pk_id) {
		this.pk_id = pk_id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public UFDate getOperatordate() {
		return operatordate;
	}

	public void setOperatordate(UFDate operatordate) {
		this.operatordate = operatordate;
	}

	public String getFkbank() {
		return fkbank;
	}

	public void setFkbank(String fkbank) {
		this.fkbank = fkbank;
	}

	public String getFkbankacc() {
		return fkbankacc;
	}

	public void setFkbankacc(String fkbankacc) {
		this.fkbankacc = fkbankacc;
	}

	public String getSkbank() {
		return skbank;
	}

	public void setSkbank(String skbank) {
		this.skbank = skbank;
	}

	public String getSkbankacc() {
		return skbankacc;
	}

	public void setSkbankacc(String skbankacc) {
		this.skbankacc = skbankacc;
	}

	public String getAssureacc() {
		return assureacc;
	}

	public void setAssureacc(String assureacc) {
		this.assureacc = assureacc;
	}

	public UFDouble getAssuremoneyy() {
		return assuremoneyy;
	}

	public void setAssuremoneyy(UFDouble assuremoneyy) {
		this.assuremoneyy = assuremoneyy;
	}

	public UFDouble getPoundagemoneyy() {
		return poundagemoneyy;
	}

	public void setPoundagemoneyy(UFDouble poundagemoneyy) {
		this.poundagemoneyy = poundagemoneyy;
	}

	public String getImpawnmode() {
		return impawnmode;
	}

	public void setImpawnmode(String impawnmode) {
		this.impawnmode = impawnmode;
	}

	public UFDouble getDfbbje() {
		return dfbbje;
	}

	public void setDfbbje(UFDouble dfbbje) {
		this.dfbbje = dfbbje;
	}

	public UFDouble getDffbje() {
		return dffbje;
	}

	public void setDffbje(UFDouble dffbje) {
		this.dffbje = dffbje;
	}

	public UFDouble getJfbbje() {
		return jfbbje;
	}

	public void setJfbbje(UFDouble jfbbje) {
		this.jfbbje = jfbbje;
	}

	public UFDouble getJffbje() {
		return jffbje;
	}

	public void setJffbje(UFDouble jffbje) {
		this.jffbje = jffbje;
	}

	public UFDouble getBbhl() {
		return bbhl;
	}

	public void setBbhl(UFDouble bbhl) {
		this.bbhl = bbhl;
	}

	public UFDouble getFbhl() {
		return fbhl;
	}

	public void setFbhl(UFDouble fbhl) {
		this.fbhl = fbhl;
	}

	public UFDouble getAssuremoneyf() {
		return assuremoneyf;
	}

	public void setAssuremoneyf(UFDouble assuremoneyf) {
		this.assuremoneyf = assuremoneyf;
	}

	public UFDouble getAssuremoneyb() {
		return assuremoneyb;
	}

	public void setAssuremoneyb(UFDouble assuremoneyb) {
		this.assuremoneyb = assuremoneyb;
	}

	public UFDouble getPoundagemoneyf() {
		return poundagemoneyf;
	}

	public void setPoundagemoneyf(UFDouble poundagemoneyf) {
		this.poundagemoneyf = poundagemoneyf;
	}

	public UFDouble getPoundagemoneyb() {
		return poundagemoneyb;
	}

	public void setPoundagemoneyb(UFDouble poundagemoneyb) {
		this.poundagemoneyb = poundagemoneyb;
	}

	public String getPk_billtypecode() {
		return pk_billtypecode;
	}

	public void setPk_billtypecode(String pk_billtypecode) {
		this.pk_billtypecode = pk_billtypecode;
	}

	public UFDouble getTxlx_ybje() {
		return txlx_ybje;
	}

	public void setTxlx_ybje(UFDouble txlx_ybje) {
		this.txlx_ybje = txlx_ybje;
	}

	public UFDouble getTxlx_bbje() {
		return txlx_bbje;
	}

	public void setTxlx_bbje(UFDouble txlx_bbje) {
		this.txlx_bbje = txlx_bbje;
	}

	public UFDouble getTxlx_fbje() {
		return txlx_fbje;
	}

	public void setTxlx_fbje(UFDouble txlx_fbje) {
		this.txlx_fbje = txlx_fbje;
	}

	public UFDouble getYbtxfy() {
		return ybtxfy;
	}

	public void setYbtxfy(UFDouble ybtxfy) {
		this.ybtxfy = ybtxfy;
	}

	public UFDouble getBbtxfy() {
		return bbtxfy;
	}

	public void setBbtxfy(UFDouble bbtxfy) {
		this.bbtxfy = bbtxfy;
	}

	public UFDouble getFbtxfy() {
		return fbtxfy;
	}

	public void setFbtxfy(UFDouble fbtxfy) {
		this.fbtxfy = fbtxfy;
	}
 
	
	
}