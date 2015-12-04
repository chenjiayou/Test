package nc.vo.fbm.relief;

import java.util.ArrayList;

import nc.vo.pub.NullFieldException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * 类功能说明： 票据调剂VO 日期：2007-11-30 程序员： wues
 * 
 */
public class ReliefVO extends SuperVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static String tablecode = "fbm_relief";

	public String pk_relief;
	public String pk_billtypecode;
	public String reliefunit;
	public String outperson;
	public UFDate outdate;
	public String memo;
	public String inneracc;
	public UFBoolean isout;
	public UFDouble summoney;
	public String acccurtype;

	public String vbillno;
	public Integer vbillstatus;
	public String voperatorid;
	public UFDate doperatedate;
	public UFDate dapprovedate;
	public String vapproveid;
	public String vapprovenote;

	public String def1;
	public String def2;
	public String def3;
	public String def4;
	public String def5;
	public String pk_corp;
	public UFDateTime ts;
	public Integer dr;

	public String vouchermanid;// 中心制证人
	public UFDate voucherdate;// 中心制证日期
	public String unitvouchermanid;// 单位制证人
	public UFDate unitvoucherdate;// 单位制证日期

	public String pk_currtype;
	public UFBoolean unittally;// 单位记账
	public UFBoolean unitvoucher;// 单位制证
	public UFBoolean centervoucher;// 中心制证
	public String reliefcorp;// 调剂公司
	public UFDate dealdate;// 受理日期
	public String tallyman;// 记账人
	public UFDate tallydate;// 记账日期
	public String businessno;// 业务流水号

	private UFBoolean writebankacc;

	private UFBoolean writeinneracc;

	public UFBoolean getWritebankacc() {
		return writebankacc;
	}

	public void setWritebankacc(UFBoolean writebankacc) {
		this.writebankacc = writebankacc;
	}

	public UFBoolean getWriteinneracc() {
		return writeinneracc;
	}

	public void setWriteinneracc(UFBoolean writeinneracc) {
		this.writeinneracc = writeinneracc;
	}

	public static final String VOUCHERMANID = "vouchermanid";// 中心制证人
	public static final String VOUCHERDATE = "voucherdate";// 中心制证日期
	public static final String UNITVOUCHERMANID = "unitvouchermanid";// 单位制证人
	public static final String UNITVOUCHERDATE = "unitvoucherdate";// 单位制证日期

	public static final String PK_RELIEF = "pk_relief";
	public static final String PK_BILLTYPECODE = "pk_billtypecode";
	public static final String RELIEFUNIT = "reliefunit";
	public static final String OUTPERSON = "outperson";
	public static final String OUTDATE = "outdate";
	public static final String INNERACC = "inneracc";
	public static final String MEMO = "memo";
	public static final String ISOUT = "isout";
	public static final String SUMMONEY = "summoney";
	public static final String ACCCURTYPE = "acccurtype";

	public static final String VBILLNO = "vbillno";
	public static final String DAPPROVEDATE = "dapprovedate";
	public static final String VBILLSTATUS = "vbillstatus";
	public static final String VAPPROVEID = "vapproveid";
	public static final String VAPPROVENOTE = "vapprovenote";
	public static final String VOPERATORID = "voperatorid";
	public static final String DOPERATEDATE = "doperatedate";

	public static final String DEF1 = "def1";
	public static final String DEF2 = "def2";
	public static final String DEF4 = "def4";
	public static final String DEF3 = "def3";
	public static final String DEF5 = "def5";
	public static final String PK_CORP = "pk_corp";
	public static final String TS = "ts";
	public static final String DR = "dr";
	public static final String PK_CURRTYPE = "pk_currtype";
	public static final String UNITTALLY = "unittally";
	public static final String UNITVOUCHER = "unitvoucher";
	public static final String CENTERVOUCHER = "centervoucher";
	public static final String RELIEFCORP = "reliefcorp";
	public static final String DEALDATE = "dealdate";
	public static final String TALLYMAN = "tallyman";
	public static final String TALLYDATE = "tallydate";

	/**
	 * @return the acccurtype
	 */
	public String getAcccurtype() {
		return acccurtype;
	}

	/**
	 * @param acccurtype
	 *            the acccurtype to set
	 */
	public void setAcccurtype(String acccurtype) {
		this.acccurtype = acccurtype;
	}

	public UFBoolean getIsout() {
		return isout;
	}

	/**
	 * @return the vouchermanid
	 */
	public String getVouchermanid() {
		return vouchermanid;
	}

	/**
	 * @param vouchermanid
	 *            the vouchermanid to set
	 */
	public void setVouchermanid(String vouchermanid) {
		this.vouchermanid = vouchermanid;
	}

	/**
	 * @return the voucherdate
	 */
	public UFDate getVoucherdate() {
		return voucherdate;
	}

	/**
	 * @param voucherdate
	 *            the voucherdate to set
	 */
	public void setVoucherdate(UFDate voucherdate) {
		this.voucherdate = voucherdate;
	}

	/**
	 * @return the unitvouchermanid
	 */
	public String getUnitvouchermanid() {
		return unitvouchermanid;
	}

	/**
	 * @param unitvouchermanid
	 *            the unitvouchermanid to set
	 */
	public void setUnitvouchermanid(String unitvouchermanid) {
		this.unitvouchermanid = unitvouchermanid;
	}

	/**
	 * @return the unitvoucherdate
	 */
	public UFDate getUnitvoucherdate() {
		return unitvoucherdate;
	}

	/**
	 * @param unitvoucherdate
	 *            the unitvoucherdate to set
	 */
	public void setUnitvoucherdate(UFDate unitvoucherdate) {
		this.unitvoucherdate = unitvoucherdate;
	}

	public void setIsout(UFBoolean isout) {
		this.isout = isout;
	}

	public String getPk_billtypecode() {
		return pk_billtypecode;
	}

	public void setPk_billtypecode(String pk_billtypecode) {
		this.pk_billtypecode = pk_billtypecode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getVbillno() {
		return vbillno;
	}

	public void setVbillno(String vbillno) {
		this.vbillno = vbillno;
	}

	public Integer getVbillstatus() {
		return vbillstatus;
	}

	public void setVbillstatus(Integer vbillstatus) {
		this.vbillstatus = vbillstatus;
	}

	public String getVoperatorid() {
		return voperatorid;
	}

	public void setVoperatorid(String voperatorid) {
		this.voperatorid = voperatorid;
	}

	public UFDate getDoperatedate() {
		return doperatedate;
	}

	public void setDoperatedate(UFDate doperatedate) {
		this.doperatedate = doperatedate;
	}

	public UFDate getDapprovedate() {
		return dapprovedate;
	}

	public void setDapprovedate(UFDate dapprovedate) {
		this.dapprovedate = dapprovedate;
	}

	public String getVapproveid() {
		return vapproveid;
	}

	public void setVapproveid(String vapproveid) {
		this.vapproveid = vapproveid;
	}

	public String getVapprovenote() {
		return vapprovenote;
	}

	public void setVapprovenote(String vapprovenote) {
		this.vapprovenote = vapprovenote;
	}

	public String getDef1() {
		return def1;
	}

	public void setDef1(String def1) {
		this.def1 = def1;
	}

	public String getDef2() {
		return def2;
	}

	public void setDef2(String def2) {
		this.def2 = def2;
	}

	public String getDef3() {
		return def3;
	}

	public void setDef3(String def3) {
		this.def3 = def3;
	}

	public String getDef4() {
		return def4;
	}

	public void setDef4(String def4) {
		this.def4 = def4;
	}

	public String getDef5() {
		return def5;
	}

	public void setDef5(String def5) {
		this.def5 = def5;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public UFDateTime getTs() {
		return ts;
	}

	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public String getPk_relief() {
		return pk_relief;
	}

	public void setPk_relief(String pk_relief) {
		this.pk_relief = pk_relief;
	}

	public String getReliefunit() {
		return reliefunit;
	}

	public void setReliefunit(String reliefunit) {
		this.reliefunit = reliefunit;
	}

	public String getOutperson() {
		return outperson;
	}

	public void setOutperson(String outperson) {
		this.outperson = outperson;
	}

	public UFDate getOutdate() {
		return outdate;
	}

	public void setOutdate(UFDate outdate) {
		this.outdate = outdate;
	}

	public String getInneracc() {
		return inneracc;
	}

	public void setInneracc(String inneracc) {
		this.inneracc = inneracc;
	}

	/**
	 * 验证对象各属性之间的数据逻辑正确性.
	 * 
	 * 创建日期:2007-8-20
	 * 
	 * @exception nc.vo.pub.ValidationException
	 *                如果验证失败,抛出 ValidationException,对错误进行解释.
	 */
	public void validate() throws ValidationException {

		ArrayList errFields = new ArrayList(); // errFields record those null

		// fields that cannot be null.
		// 检查是否为不允许空的字段赋了空值,你可能需要修改下面的提示信息:

		if (pk_relief == null) {
			errFields.add(new String("pk_relief"));
		}

		StringBuffer message = new StringBuffer();
		message.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045", "UPP36201045-000013")/*
																											 * @res
																											 * "下列字段不能为空:"
																											 */);
		if (errFields.size() > 0) {
			String[] temp = (String[]) errFields.toArray(new String[0]);
			message.append(temp[0]);
			for (int i = 1; i < temp.length; i++) {
				message.append(",");
				message.append(temp[i]);
			}
			throw new NullFieldException(message.toString());
		}
	}

	/**
	 * <p>
	 * 取得父VO主键字段.
	 * <p>
	 * 创建日期:2007-8-20
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getParentPKFieldName() {

		return null;

	}

	/**
	 * <p>
	 * 取得表主键.
	 * <p>
	 * 创建日期:2007-8-20
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPKFieldName() {
		return "pk_relief";
	}

	/**
	 * <p>
	 * 返回表名称.
	 * <p>
	 * 创建日期:2007-8-20
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {

		return "fbm_relief";
	}

	/**
	 * 按照默认方式创建构造子.
	 * 
	 * 创建日期:2007-8-20
	 */
	public ReliefVO() {

		super();
	}

	/**
	 * 使用主键进行初始化的构造子.
	 * 
	 * 创建日期:2007-8-20
	 * 
	 * @param newPk_storage
	 *            主键值
	 */
	public ReliefVO(String newPk_relief) {

		// 为主键字段赋值:
		pk_relief = newPk_relief;

	}

	/**
	 * 返回对象标识,用来唯一定位对象.
	 * 
	 * 创建日期:2007-8-20
	 * 
	 * @return String
	 */
	public String getPrimaryKey() {

		return pk_relief;

	}

	/**
	 * 设置对象标识,用来唯一定位对象.
	 * 
	 * 创建日期:2007-8-20
	 * 
	 * @param newPk_storage
	 *            String
	 */
	public void setPrimaryKey(String newPk_relief) {

		pk_relief = newPk_relief;

	}

	/**
	 * 返回数值对象的显示名称.
	 * 
	 * 创建日期:2007-8-20
	 * 
	 * @return java.lang.String 返回数值对象的显示名称.
	 */
	public String getEntityName() {

		return "fbm_relief";

	}

	/**
	 * @return the summoney
	 */
	public UFDouble getSummoney() {
		return summoney;
	}

	/**
	 * @param summoney
	 *            the summoney to set
	 */
	public void setSummoney(UFDouble summoney) {
		this.summoney = summoney;
	}

	public String getPk_currtype() {
		return pk_currtype;
	}

	public void setPk_currtype(String pk_currtype) {
		this.pk_currtype = pk_currtype;
	}

	public UFBoolean getUnittally() {
		return unittally;
	}

	public void setUnittally(UFBoolean unittally) {
		this.unittally = unittally;
	}

	public UFBoolean getUnitvoucher() {
		return unitvoucher;
	}

	public void setUnitvoucher(UFBoolean unitvoucher) {
		this.unitvoucher = unitvoucher;
	}

	public UFBoolean getCentervoucher() {
		return centervoucher;
	}

	public void setCentervoucher(UFBoolean centervoucher) {
		this.centervoucher = centervoucher;
	}

	public String getReliefcorp() {
		return reliefcorp;
	}

	public void setReliefcorp(String reliefcorp) {
		this.reliefcorp = reliefcorp;
	}

	public UFDate getDealdate() {
		return dealdate;
	}

	public void setDealdate(UFDate dealdate) {
		this.dealdate = dealdate;
	}

	public String getTallyman() {
		return tallyman;
	}

	public void setTallyman(String tallyman) {
		this.tallyman = tallyman;
	}

	public UFDate getTallydate() {
		return tallydate;
	}

	public void setTallydate(UFDate tallydate) {
		this.tallydate = tallydate;
	}

	public String getBusinessno() {
		return businessno;
	}

	public void setBusinessno(String businessno) {
		this.businessno = businessno;
	}
}