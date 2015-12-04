package nc.vo.fbm.pub;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

public class StorageMiddleVO extends SuperVO {
	
	public String inputperson;
	
	public String keepunit;
	
	public String voperatorid;
	
	public String pk_storage;
	
	public UFDate dapprovedate;
	
	public String pk_billtypecode;
	
	public Integer vbillstatus;
	
	public String vapproveid;
	
	public UFDate inputdate;
	
	public String inputtype;
	
	public UFDate doperatedate;
	
	public String pk_corp;
	
	public String vbillno;
	
	public String pk_curr;
	 
	
	//子表数据
	public String pk_storage_b;
    public String pk_source;
    public String pk_baseinfo;
   
    public UFDouble discountinterest;//贴现利息
	
	public String getInputperson() {
		return inputperson;
	}

	public void setInputperson(String inputperson) {
		this.inputperson = inputperson;
	}

	public String getKeepunit() {
		return keepunit;
	}

	public void setKeepunit(String keepunit) {
		this.keepunit = keepunit;
	}

	public String getVoperatorid() {
		return voperatorid;
	}

	public void setVoperatorid(String voperatorid) {
		this.voperatorid = voperatorid;
	}

	public String getPk_storage() {
		return pk_storage;
	}

	public void setPk_storage(String pk_storage) {
		this.pk_storage = pk_storage;
	}

	public UFDate getDapprovedate() {
		return dapprovedate;
	}

	public void setDapprovedate(UFDate dapprovedate) {
		this.dapprovedate = dapprovedate;
	}

	public String getPk_billtypecode() {
		return pk_billtypecode;
	}

	public void setPk_billtypecode(String pk_billtypecode) {
		this.pk_billtypecode = pk_billtypecode;
	}

	public Integer getVbillstatus() {
		return vbillstatus;
	}

	public void setVbillstatus(Integer vbillstatus) {
		this.vbillstatus = vbillstatus;
	}

	public String getVapproveid() {
		return vapproveid;
	}

	public void setVapproveid(String vapproveid) {
		this.vapproveid = vapproveid;
	}

	public UFDate getInputdate() {
		return inputdate;
	}

	public void setInputdate(UFDate inputdate) {
		this.inputdate = inputdate;
	}

	public String getInputtype() {
		return inputtype;
	}

	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
	}

	public UFDate getDoperatedate() {
		return doperatedate;
	}

	public void setDoperatedate(UFDate doperatedate) {
		this.doperatedate = doperatedate;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getPk_storage_b() {
		return pk_storage_b;
	}

	public void setPk_storage_b(String pk_storage_b) {
		this.pk_storage_b = pk_storage_b;
	}

	public String getPk_source() {
		return pk_source;
	}

	public void setPk_source(String pk_source) {
		this.pk_source = pk_source;
	}

	public String getPk_baseinfo() {
		return pk_baseinfo;
	}

	public void setPk_baseinfo(String pk_baseinfo) {
		this.pk_baseinfo = pk_baseinfo;
	}

	
	public String getVbillno() {
		return vbillno;
	}

	public void setVbillno(String vbillno) {
		this.vbillno = vbillno;
	}

	
	public String getPk_curr() {
		return pk_curr;
	}

	public void setPk_curr(String pk_curr) {
		this.pk_curr = pk_curr;
	}

	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public UFDouble getDiscountinterest() {
		return discountinterest;
	}

	public void setDiscountinterest(UFDouble discountinterest) {
		this.discountinterest = discountinterest;
	}

	
}
