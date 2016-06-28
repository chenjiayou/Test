  /***************************************************************\
  *     The skeleton of this class is generated by an automatic *
  * code generator for NC product. It is based on Velocity.     *
  \***************************************************************/
      	package nc.vo.fbm.reckon;

	import java.util.ArrayList;

import nc.vo.pub.NullFieldException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

/**
 * <b> �ڴ˴���Ҫ��������Ĺ��� </b>
 * �������㵥����VO
 * <p>
 *     �ڴ˴����Ӵ����������Ϣ
 * </p>
 *
 * ��������:2007-10-19
 * @author author bsrl
 * @version Your Project 5.02
 */
     public class ReckonBVO extends SuperVO {

         public static final String TABLECODE = "fbm_reckon_b";

             /**
	 *
	 */
	private static final long serialVersionUID = 1L;
			 public String pk_detail; // �˻���ϸpk
			 public String pk_source; // ��Ʊpk
             public UFDouble moneyy; //������
             public String direction; //���㷽��
             public String pk_baseinfo; // Ʊ�ݻ�����Ϣpk
             public String pk_reckon;// ��������pk
             public String pk_reckon_b; //����

             public UFDouble fbmbillmoney; //Ʊ����
             public UFDate enddate; //������
             public String holdunit;//��Ʊ��λ
             public String payunit;//���λ
             public String fbmbillstatus;//Ʊ��״̬

             public UFDouble frate;// ���һ���
             public UFDouble moneyf;// ����������
             public UFDouble brate;// ���һ���
             public UFDouble moneyb;// ����������
             public String fbmbillno;//Ʊ�ݱ��
             public String fbmbilltype;//Ʊ������
             public String pk_curr;

         	public UFDateTime ts;
        	public Integer dr;

    public static final String PK_CURR = "pk_curr";// ����
	public static final String FBMBILLNO = "fbmbillno";// Ʊ�ݱ��
	public static final String FBMBILLTYPE = "fbmbilltype";// Ʊ������

	public static final String FRATE = "frate";
	public static final String BRATE = "brate";
	public static final String MONEYF = "moneyf";
	public static final String MONEYB = "moneyb";

	public static final String PK_DETAIL = "pk_detail";
	public static final String PK_SOURCE = "pk_source";
	public static final String MONEYY = "moneyy";
	public static final String DIRECTION = "direction";
	public static final String PK_BASEINFO = "pk_baseinfo";
	public static final String PK_RECKON = "pk_reckon";
	public static final String PK_RECKON_B = "pk_reckon_b";

	public static final String FBMBILLMONEY = "fbmbillmoney";
	public static final String ENDDATE = "enddate";
	public static final String HOLDUNIT = "holdunit";
	public static final String PAYUNIT = "payunit";
	public static final String FBMBILLSTATUS = "fbmbillstatus";

	public static final String TABLENAME = TABLECODE;

        	
	// ��ʽ��Ҫ�õ����ֶ�
	private String payunitname;
	private String paybankaccname;
	private String receiveunitname;
	private String receivebankaccname;
	private String invoiceunitname;
	private String keepplacename;
	private String paybillunitname;
	private String holdunitname;
	private String keepbankname;
	private String currtypename;
         	
    public String getCurrtypename() {
		return currtypename;
	}

	public void setCurrtypename(String currtypename) {
		this.currtypename = currtypename;
	}

	public String getPayunitname() {
		return payunitname;
	}

	public void setPayunitname(String payunitname) {
		this.payunitname = payunitname;
	}

	public String getPaybankaccname() {
		return paybankaccname;
	}

	public void setPaybankaccname(String paybankaccname) {
		this.paybankaccname = paybankaccname;
	}

	public String getReceiveunitname() {
		return receiveunitname;
	}

	public void setReceiveunitname(String receiveunitname) {
		this.receiveunitname = receiveunitname;
	}

	public String getReceivebankaccname() {
		return receivebankaccname;
	}

	public void setReceivebankaccname(String receivebankaccname) {
		this.receivebankaccname = receivebankaccname;
	}

	public String getInvoiceunitname() {
		return invoiceunitname;
	}

	public void setInvoiceunitname(String invoiceunitname) {
		this.invoiceunitname = invoiceunitname;
	}

	public String getKeepplacename() {
		return keepplacename;
	}

	public void setKeepplacename(String keepplacename) {
		this.keepplacename = keepplacename;
	}

	public String getPaybillunitname() {
		return paybillunitname;
	}

	public void setPaybillunitname(String paybillunitname) {
		this.paybillunitname = paybillunitname;
	}

	public String getHoldunitname() {
		return holdunitname;
	}

	public void setHoldunitname(String holdunitname) {
		this.holdunitname = holdunitname;
	}

	public String getKeepbankname() {
		return keepbankname;
	}

	public void setKeepbankname(String keepbankname) {
		this.keepbankname = keepbankname;
	}

	 
        /**
			 * @return the fbmbillno
			 */
			public String getFbmbillno() {
				return fbmbillno;
			}

			/**
		 * @return the ts
		 */
		public UFDateTime getTs() {
			return ts;
		}

		/**
		 * @param ts the ts to set
		 */
		public void setTs(UFDateTime ts) {
			this.ts = ts;
		}

		/**
		 * @return the dr
		 */
		public Integer getDr() {
			return dr;
		}

		/**
		 * @param dr the dr to set
		 */
		public void setDr(Integer dr) {
			this.dr = dr;
		}

			/**
			 * @param fbmbillno the fbmbillno to set
			 */
			public void setFbmbillno(String fbmbillno) {
				this.fbmbillno = fbmbillno;
			}

			/**
			 * @return the fbmbilltype
			 */
			public String getFbmbilltype() {
				return fbmbilltype;
			}

			/**
			 * @param fbmbilltype the fbmbilltype to set
			 */
			public void setFbmbilltype(String fbmbilltype) {
				this.fbmbilltype = fbmbilltype;
			}

		/**
	   * ����pk_source��Getter����.
	   *
	   * ��������:2007-10-19
	   * @return String
	   */
	 public String getPk_source() {
		 return pk_source;
	  }

     /**
	   * ����pk_source��Setter����.
	   *
	   * ��������:2007-10-19
	   * @param newPk_source String
	   */
	public void setPk_source(String newPk_source) {

		pk_source = newPk_source;
	 }

        /**
	   * ����moneyy��Getter����.
	   *
	   * ��������:2007-10-19
	   * @return UFDouble
	   */
	 public UFDouble getMoneyy() {
		 return moneyy;
	  }

     /**
	   * ����moneyy��Setter����.
	   *
	   * ��������:2007-10-19
	   * @param newMoneyy UFDouble
	   */
	public void setMoneyy(UFDouble newMoneyy) {

		moneyy = newMoneyy;
	 }

        /**
	   * ����direction��Getter����.
	   *
	   * ��������:2007-10-19
	   * @return String
	   */
	 public String getDirection() {
		 return direction;
	  }

     /**
	   * ����direction��Setter����.
	   *
	   * ��������:2007-10-19
	   * @param newDirection String
	   */
	public void setDirection(String newDirection) {

		direction = newDirection;
	 }

        /**
	   * ����pk_baseinfo��Getter����.
	   *
	   * ��������:2007-10-19
	   * @return String
	   */
	 public String getPk_baseinfo() {
		 return pk_baseinfo;
	  }

     /**
	   * ����pk_baseinfo��Setter����.
	   *
	   * ��������:2007-10-19
	   * @param newPk_baseinfo String
	   */
	public void setPk_baseinfo(String newPk_baseinfo) {

		pk_baseinfo = newPk_baseinfo;
	 }

        /**
	   * ����pk _reckon��Getter����.
	   *
	   * ��������:2007-10-19
	   * @return String
	   */
	 public String getPk_reckon() {
		 return pk_reckon;
	  }

     /**
	   * ����pk _reckon��Setter����.
	   *
	   * ��������:2007-10-19
	   * @param newPk _reckon String
	   */
	public void setPk_reckon(String newPk_reckon) {

		pk_reckon = newPk_reckon;
	 }

        /**
	   * ����pk _reckon_b��Getter����.
	   *
	   * ��������:2007-10-19
	   * @return String
	   */
	 public String getPk_reckon_b() {
		 return pk_reckon_b;
	  }

     /**
	   * ����pk _reckon_b��Setter����.
	   *
	   * ��������:2007-10-19
	   * @param newPk _reckon_b String
	   */
	public void setPk_reckon_b(String newPk_reckon_b) {

		pk_reckon_b = newPk_reckon_b;
	 }


    /**
	  * ��֤���������֮��������߼���ȷ��.
	  *
	  * ��������:2007-10-19
	  * @exception nc.vo.pub.ValidationException �����֤ʧ��,�׳�
	  * ValidationException,�Դ�����н���.
	 */
	 public void validate() throws ValidationException {

	 	ArrayList errFields = new ArrayList(); // errFields record those null

                                                      // fields that cannot be null.
       		  // ����Ƿ�Ϊ�������յ��ֶθ��˿�ֵ,�������Ҫ�޸��������ʾ��Ϣ:

	   		if (pk_reckon_b == null) {
			errFields.add(new String("pk_reckon_b"));
			  }

	    StringBuffer message = new StringBuffer();
		message.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201050","UPP36201050-000002")/* @res"�����ֶβ���Ϊ��:"*/);
		if (errFields.size() > 0) {
		String[] temp = (String[]) errFields.toArray(new String[0]);
		message.append(temp[0]);
		for ( int i= 1; i < temp.length; i++ ) {
			message.append(",");
			message.append(temp[i]);
		}
		throw new NullFieldException(message.toString());
		}
	 }


   	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2007-10-19
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {

	 		return "pk_reckon";

	}

    /**
	 * @return the frate
	 */
	public UFDouble getFrate() {
		return frate;
	}

	/**
	 * @param frate the frate to set
	 */
	public void setFrate(UFDouble frate) {
		this.frate = frate;
	}

	/**
	 * @return the moneyf
	 */
	public UFDouble getMoneyf() {
		return moneyf;
	}

	/**
	 * @param moneyf the moneyf to set
	 */
	public void setMoneyf(UFDouble moneyf) {
		this.moneyf = moneyf;
	}

	/**
	 * @return the brate
	 */
	public UFDouble getBrate() {
		return brate;
	}

	/**
	 * @param brate the brate to set
	 */
	public void setBrate(UFDouble brate) {
		this.brate = brate;
	}

	/**
	 * @return the moneyb
	 */
	public UFDouble getMoneyb() {
		return moneyb;
	}

	/**
	 * @param moneyb the moneyb to set
	 */
	public void setMoneyb(UFDouble moneyb) {
		this.moneyb = moneyb;
	}

	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2007-10-19
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	 	  return "pk_reckon_b";
	 	}

	/**
      * <p>���ر�����.
	  * <p>
	  * ��������:2007-10-19
	  * @return java.lang.String
	 */
	public java.lang.String getTableName() {

		return "fbm_reckon_b";
	}

    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2007-10-19
	  */
	public ReckonBVO() {

			   super();
	  }

            /**
	 * ʹ���������г�ʼ���Ĺ�����.
	 *
	 * ��������:2007-10-19
	 * @param newPk _reckon_b ����ֵ
	 */
	 public ReckonBVO(String newPk_reckon_b) {

		// Ϊ�����ֶθ�ֵ:
		 pk_reckon_b = newPk_reckon_b;

    	}


     /**
	  * ���ض����ʶ,����Ψһ��λ����.
	  *
	  * ��������:2007-10-19
	  * @return String
	  */
	   public String getPrimaryKey() {

		 return pk_reckon_b;

	   }

     /**
	  * ���ö����ʶ,����Ψһ��λ����.
	  *
	  * ��������:2007-10-19
	  * @param newPk _reckon_b  String
	  */
	 public void setPrimaryKey(String newPk_reckon_b) {

				pk_reckon_b = newPk_reckon_b;

	 }

	  /**
       * ������ֵ�������ʾ����.
	   *
	   * ��������:2007-10-19
	   * @return java.lang.String ������ֵ�������ʾ����.
	   */
	 public String getEntityName() {

	   return "fbm_reckon_b";

	 }

	public UFDouble getFbmbillmoney() {
		return fbmbillmoney;
	}

	public void setFbmbillmoney(UFDouble fbmbillmoney) {
		this.fbmbillmoney = fbmbillmoney;
	}

	public UFDate getEnddate() {
		return enddate;
	}

	public void setEnddate(UFDate enddate) {
		this.enddate = enddate;
	}

	public String getHoldunit() {
		return holdunit;
	}

	public void setHoldunit(String holdunit) {
		this.holdunit = holdunit;
	}

	public String getPayunit() {
		return payunit;
	}

	public void setPayunit(String payunit) {
		this.payunit = payunit;
	}

	public String getFbmbillstatus() {
		return fbmbillstatus;
	}

	public void setFbmbillstatus(String fbmbillstatus) {
		this.fbmbillstatus = fbmbillstatus;
	}

	public String getPk_detail() {
		return pk_detail;
	}

	public void setPk_detail(String pk_detail) {
		this.pk_detail = pk_detail;
	}

	public String getPk_curr() {
		return pk_curr;
	}

	public void setPk_curr(String pk_curr) {
		this.pk_curr = pk_curr;
	}
}