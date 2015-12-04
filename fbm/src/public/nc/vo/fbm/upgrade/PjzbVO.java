/**************************************************************************
 * Ʊ������VO
 * <br>Created on 2004-9-21 16:43:18<br>
 * @author Rocex Wang
 **************************************************************************/
package nc.vo.fbm.upgrade;

import java.lang.reflect.Method;
import java.util.*;

import nc.vo.pub.*;
import nc.vo.pub.lang.*;

/**************************************************************************
 * <br>Created on 2004-10-9 9:34:34<br>
 * @author Rocex Wang
 **************************************************************************/
public class PjzbVO extends SuperVO implements nc.vo.dap.inteface.IAccountGetBalanceInfo
{
    private String m_bbbz;
    private UFDouble m_bbhl;
    private UFDouble m_bbje;
    private String m_bz;
    private UFDouble m_bzjbl;
    private UFDouble m_bzje;
    private UFDouble m_bzjje;
    private String m_bzjzh;
    private String m_cpr;
    private UFDate m_cprq;
    private String m_cprzh;
    private UFDate m_czrq;
    private String m_czy;
    private String m_dbfs;
    private UFDate m_dqrq;
    private Integer m_dr;
    private String m_fbbz;
    private UFDouble m_fbhl;
    private UFDouble m_fbje;
    private String m_fkdw;
    private String m_fkdwzh;
    private String m_fkyh;
    private String m_pjbh;
    private String m_pjfx;
    private String m_pjlx;
    private String m_pjlyid;
    private UFDate m_pjsdrq;
    private String m_pjyylx;
    private String m_pjzt;
    private String m_pk_corp;
    private String m_pk_pjyy;
    private String m_pk_pjzb;
    private UFBoolean m_qcpj;
    private UFBoolean m_sfscpz;
    private String m_shr;
    private UFDate m_shrq;
    private Integer m_shzt;
    private String m_skdw;
    private String m_skdwzh;
    private String m_skyh;
    private UFDouble m_sxfje;
    private String m_thyy;
    private String m_ts;
    private String m_ybbz;
    private UFDouble m_ybje;

    private String m_zdyx1;
    private String m_zdyx2;
    private String m_zdyx3;
    private String m_zdyx4;
    private String m_zdyx5;
    private String m_zdyx6;
    private String m_zdyx7;
    private String m_zdyx8;
    private String m_zdyx9;
    private String m_zyx1;
    private String m_zyx10;
    private String m_zyx2;
    private String m_zyx3;
    private String m_zyx4;
    private String m_zyx5;
    private String m_zyx6;
    private String m_zyx7;
    private String m_zyx8;
    private String m_zyx9;

    /**
     * ʹ�������ֶν��г�ʼ���Ĺ����ӡ�
     * �������ڣ�(2004-9-24)
     */
    public PjzbVO()
    {
        super();
    }

    /**
     * ʹ���������г�ʼ���Ĺ����ӡ�
     * �������ڣ�(2004-9-24)
     * @param newPk_pjzb ����ֵ
     */
    public PjzbVO(String newPk_pjzb)
    {
        super();

        // Ϊ�����ֶθ�ֵ:
        m_pk_pjzb = newPk_pjzb;
    }

    /**
     * ���������������˺�
     * �������:
     * �������:
     * �������ڣ�(2004-4-5 12:52:31)
     * �����ˣ�����ΰ
     * @return java.lang.String
     */
    public String getBankCode()
    {
        if (getPjfx().equals("0"))
        {
            return getSkdwzh();
        }

        return getFkdwzh();
    }

    /**
     * ����bbbz��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getBbbz()
    {
        return m_bbbz;
    }

    /**
     * ����bbhl��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDouble
     */
    public UFDouble getBbhl()
    {
        return m_bbhl;
    }

    /**
     * ����bbje��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDouble
     */
    public UFDouble getBbje()
    {
        return m_bbje;
    }

    /**
     * ����bz��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getBz()
    {
        return m_bz;
    }

    /**
     * ����bzjbl��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDouble
     */
    public UFDouble getBzjbl()
    {
        return m_bzjbl;
    }

    /**
     * ����bzje��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDouble
     */
    public UFDouble getBzje()
    {
        return m_bzje;
    }

    /**
     * ����bzjje��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDouble
     */
    public UFDouble getBzjje()
    {
        return m_bzjje;
    }

    /**
     * ����bzjzh��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getBzjzh()
    {
        return m_bzjzh;
    }

    /**
     * ���Ʊ�����ڡ�
     * �������ڣ�(2003-5-9 16:51:34)
     * @return nc.vo.pub.lang.UFDate
     */
    public nc.vo.pub.lang.UFDate getCheckDate()
    {
        return getCprq();
    }

    /**
     * ���Ʊ�ݱ�š�
     * �������ڣ�(2003-5-9 16:51:06)
     * @return java.lang.String
     */
    public String getCheckNo()
    {
        return getPjbh();
    }

    /**
     * ����cpr��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getCpr()
    {
        return m_cpr;
    }

    /**
     * ����cprq��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDate
     */
    public UFDate getCprq()
    {
        return m_cprq;
    }

    /**
     * ����cprzh��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getCprzh()
    {
        return m_cprzh;
    }

    /**************************************************************************
     * <br>Created on 2004-11-19 9:46:48<br>
     * @return Returns the UFDate m_czrq.
     **************************************************************************/
    public UFDate getCzrq()
    {
        return this.m_czrq;
    }

    /**************************************************************************
     * <br>Created on 2004-11-19 9:46:48<br>
     * @return Returns the String m_czy.
     **************************************************************************/
    public String getCzy()
    {
        return this.m_czy;
    }

    /**
     * ����dbfs��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getDbfs()
    {
        return m_dbfs;
    }

    /**
     * ����dqrq��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDate
     */
    public UFDate getDqrq()
    {
        return m_dqrq;
    }

    /**************************************************************************
     * <br>Created on 2004-10-27 16:20:02<br>
     * @return Returns the Integer m_dr.
     **************************************************************************/
    public Integer getDr()
    {
        return this.m_dr;
    }

    /**
     * ������ֵ�������ʾ���ơ�
     * �������ڣ�(2004-9-24)
     * @return java.lang.String ������ֵ�������ʾ���ơ�
     */
    public String getEntityName()
    {
        return "Pjzb";
    }

    /**
     * ����fbbz��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getFbbz()
    {
        return m_fbbz;
    }

    /**
     * ����fbhl��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDouble
     */
    public UFDouble getFbhl()
    {
        return m_fbhl;
    }

    /**
     * ����fbje��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDouble
     */
    public UFDouble getFbje()
    {
        return m_fbje;
    }

    /**
     * ����fkdw��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getFkdw()
    {
        return m_fkdw;
    }

    /**
     * ����fkdwzh��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getFkdwzh()
    {
        return m_fkdwzh;
    }

    /**
     * ����fkyh��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getFkyh()
    {
        return m_fkyh;
    }

    /**
     * ��ý��㷽ʽ��
     * �������ڣ�(2003-5-9 16:50:06)
     * @return java.lang.String
     */
    public String getJsfs()
    {
        return null;
    }

    /**
     * ���Ʊ�����͡�
     * �������ڣ�(2003-5-9 16:51:06)
     * @return java.lang.String
     */
    public String getNotetype()
    {
        return getPjlx();
    }

    /**
     * <p>ȡ�ø�VO�����ֶΡ�
     * �������ڣ�(2004-9-24)
     * @return java.lang.String
     */
    public String getParentPKFieldName()
    {
        return null;
    }

    /**
     * ����pjbh��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getPjbh()
    {
        return m_pjbh;
    }

    /**
     * ����pjfx��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getPjfx()
    {
        return m_pjfx;
    }

    /**
     * ����pjlx��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getPjlx()
    {
        return m_pjlx;
    }

    /**
     * ����pjlyid��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getPjlyid()
    {
        return m_pjlyid;
    }

    /**
     * ����pjsdrq��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDate
     */
    public UFDate getPjsdrq()
    {
        return m_pjsdrq;
    }

    /**************************************************************************
     * <br>Created on 2005-1-14 17:03:30<br>
     * @return Returns the String pjlylx.
     **************************************************************************/
    public String getPjyylx()
    {
        return this.m_pjyylx;
    }

    /**
     * ����pjzt��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getPjzt()
    {
        return m_pjzt;
    }

    /**
     * ����pk_corp��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getPk_corp()
    {
        return m_pk_corp;
    }

    /**************************************************************************
     * <br>Created on 2005-2-1 13:34:27</br>
     * @return String
     **************************************************************************/
    public String getPk_pjyy()
    {
        return this.m_pk_pjyy;
    }

    /**************************************************************************
     * <br>Created on 2004-10-9 15:10:21<br>
     * @return Returns the String m_pk_pjzb.
     **************************************************************************/
    public String getPk_pjzb()
    {
        return this.m_pk_pjzb;
    }

    /**
     * <p>ȡ�ñ�������
     * �������ڣ�(2004-9-24)
     * @return java.lang.String
     */
    public java.lang.String getPKFieldName()
    {
        return "pk_pjzb";
    }

    /**
     * ���ض����ʶ������Ψһ��λ����
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getPrimaryKey()
    {
        return m_pk_pjzb;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:24<br>
     * @return Returns the UFBoolean m_qcpj.
     **************************************************************************/
    public UFBoolean getQcpj()
    {
        return this.m_qcpj;
    }

    /**
     * ����sfscpz��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFBoolean
     */
    public UFBoolean getSfscpz()
    {
        return m_sfscpz;
    }

    /**
     * ����shr��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getShr()
    {
        return m_shr;
    }

    /**
     * ����shrq��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDate
     */
    public UFDate getShrq()
    {
        return m_shrq;
    }

    /**
     * ����shzt��Getter������
     * �������ڣ�(2004-9-24)
     * @return Integer
     */
    public Integer getShzt()
    {
        return m_shzt;
    }

    /**
     * ����skdw��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getSkdw()
    {
        return m_skdw;
    }

    /**
     * ����skdwzh��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getSkdwzh()
    {
        return m_skdwzh;
    }

    /**
     * ����skyh��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getSkyh()
    {
        return m_skyh;
    }

    /**
     * ����sxfje��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDouble
     */
    public UFDouble getSxfje()
    {
        return m_sxfje;
    }

    /**
     * <p>���ر����ơ�
     * �������ڣ�(2004-9-24)
     * @return java.lang.String
     */
    public java.lang.String getTableName()
    {
        return "fbm_pjzb";
    }

    /**
     * ����thyy��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getThyy()
    {
        return m_thyy;
    }

    /**************************************************************************
     * <br>Created on 2004-10-27 16:20:02<br>
     * @return Returns the String m_ts.
     **************************************************************************/
    public String getTs()
    {
        return this.m_ts;
    }

    /**
     * ����ybbz��Getter������
     * �������ڣ�(2004-9-24)
     * @return String
     */
    public String getYbbz()
    {
        return m_ybbz;
    }

    /**
     * ����ybje��Getter������
     * �������ڣ�(2004-9-24)
     * @return UFDouble
     */
    public UFDouble getYbje()
    {
        return m_ybje;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:24<br>
     * @return Returns the String m_zdyx1.
     **************************************************************************/
    public String getZdyx1()
    {
        return this.m_zdyx1;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:24<br>
     * @return Returns the String m_zdyx2.
     **************************************************************************/
    public String getZdyx2()
    {
        return this.m_zdyx2;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:50<br>
     * @return Returns the String m_zdyx3.
     **************************************************************************/
    public String getZdyx3()
    {
        return this.m_zdyx3;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:50<br>
     * @return Returns the String m_zdyx4.
     **************************************************************************/
    public String getZdyx4()
    {
        return this.m_zdyx4;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:50<br>
     * @return Returns the String m_zdyx5.
     **************************************************************************/
    public String getZdyx5()
    {
        return this.m_zdyx5;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:50<br>
     * @return Returns the String m_zdyx6.
     **************************************************************************/
    public String getZdyx6()
    {
        return this.m_zdyx6;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:50<br>
     * @return Returns the String m_zdyx7.
     **************************************************************************/
    public String getZdyx7()
    {
        return this.m_zdyx7;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @return Returns the String m_zdyx8.
     **************************************************************************/
    public String getZdyx8()
    {
        return this.m_zdyx8;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @return Returns the String m_zdyx9.
     **************************************************************************/
    public String getZdyx9()
    {
        return this.m_zdyx9;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @return Returns the String m_zyx1.
     **************************************************************************/
    public String getZyx1()
    {
        return this.m_zyx1;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @return Returns the String m_zyx10.
     **************************************************************************/
    public String getZyx10()
    {
        return this.m_zyx10;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @return Returns the String m_zyx2.
     **************************************************************************/
    public String getZyx2()
    {
        return this.m_zyx2;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @return Returns the String m_zyx3.
     **************************************************************************/
    public String getZyx3()
    {
        return this.m_zyx3;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @return Returns the String m_zyx4.
     **************************************************************************/
    public String getZyx4()
    {
        return this.m_zyx4;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @return Returns the String m_zyx5.
     **************************************************************************/
    public String getZyx5()
    {
        return this.m_zyx5;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:52<br>
     * @return Returns the String m_zyx6.
     **************************************************************************/
    public String getZyx6()
    {
        return this.m_zyx6;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:52<br>
     * @return Returns the String m_zyx7.
     **************************************************************************/
    public String getZyx7()
    {
        return this.m_zyx7;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:52<br>
     * @return Returns the String m_zyx8.
     **************************************************************************/
    public String getZyx8()
    {
        return this.m_zyx8;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:52<br>
     * @return Returns the String m_zyx9.
     **************************************************************************/
    public String getZyx9()
    {
        return this.m_zyx9;
    }

    /**
     * ����bbbz��setter������
     * �������ڣ�(2004-9-24)
     * @param newBbbz String
     */
    public void setBbbz(String newBbbz)
    {
        m_bbbz = newBbbz;
    }

    /**
     * ����bbhl��setter������
     * �������ڣ�(2004-9-24)
     * @param newBbhl UFDouble
     */
    public void setBbhl(UFDouble newBbhl)
    {
        m_bbhl = newBbhl;
    }

    /**
     * ����bbje��setter������
     * �������ڣ�(2004-9-24)
     * @param newBbje UFDouble
     */
    public void setBbje(UFDouble newBbje)
    {
        m_bbje = newBbje;
    }

    /**
     * ����bz��setter������
     * �������ڣ�(2004-9-24)
     * @param newBz String
     */
    public void setBz(String newBz)
    {
        m_bz = newBz;
    }

    /**
     * ����bzjbl��setter������
     * �������ڣ�(2004-9-24)
     * @param newBzjbl UFDouble
     */
    public void setBzjbl(UFDouble newBzjbl)
    {
        m_bzjbl = newBzjbl;
    }

    /**
     * ����bzje��setter������
     * �������ڣ�(2004-9-24)
     * @param newBzje UFDouble
     */
    public void setBzje(UFDouble newBzje)
    {
        m_bzje = newBzje;
    }

    /**
     * ����bzjje��setter������
     * �������ڣ�(2004-9-24)
     * @param newBzjje UFDouble
     */
    public void setBzjje(UFDouble newBzjje)
    {
        m_bzjje = newBzjje;
    }

    /**
     * ����bzjzh��setter������
     * �������ڣ�(2004-9-24)
     * @param newBzjzh String
     */
    public void setBzjzh(String newBzjzh)
    {
        m_bzjzh = newBzjzh;
    }

    /**
     * ����cpr��setter������
     * �������ڣ�(2004-9-24)
     * @param newCpr String
     */
    public void setCpr(String newCpr)
    {
        m_cpr = newCpr;
    }

    /**
     * ����cprq��setter������
     * �������ڣ�(2004-9-24)
     * @param newCprq UFDate
     */
    public void setCprq(UFDate newCprq)
    {
        m_cprq = newCprq;
    }

    /**
     * ����cprzh��setter������
     * �������ڣ�(2004-9-24)
     * @param newCprzh String
     */
    public void setCprzh(String newCprzh)
    {
        m_cprzh = newCprzh;
    }

    /**************************************************************************
     * <br>Created on 2004-11-19 9:46:48<br>
     * @param czrq The UFDate m_czrq to set.
     **************************************************************************/
    public void setCzrq(UFDate czrq)
    {
        this.m_czrq = czrq;
    }

    /**************************************************************************
     * <br>Created on 2004-11-19 9:46:49<br>
     * @param czy The String m_czy to set.
     **************************************************************************/
    public void setCzy(String czy)
    {
        this.m_czy = czy;
    }

    /**
     * ����dbfs��setter������
     * �������ڣ�(2004-9-24)
     * @param newDbfs String
     */
    public void setDbfs(String newDbfs)
    {
        m_dbfs = newDbfs;
    }
    /**
     * ����dbfs��setter������
     * �������ڣ�(2004-9-24)
     * @param newDbfs String
     */
    public void setDbfs(int newDbfs)
    {
        m_dbfs =(new Integer(newDbfs)).toString();
    }

    public void setAttributeValue(String attributeName, Object value) {
    	if((!attributeName.equalsIgnoreCase("dbfs"))||(value==null)){
    	       super.setAttributeValue(attributeName,value);	
    	}else{    	    	
    	   setDbfs(Integer.parseInt(value.toString()));	
    	}

	}
    /**
     * ����dqrq��setter������
     * �������ڣ�(2004-9-24)
     * @param newDqrq UFDate
     */
    public void setDqrq(UFDate newDqrq)
    {
        m_dqrq = newDqrq;
    }

    /**************************************************************************
     * <br>Created on 2004-10-27 16:20:02<br>
     * @param dr The Integer m_dr to set.
     **************************************************************************/
    public void setDr(Integer dr)
    {
        this.m_dr = dr;
    }

    /**
     * ����fbbz��setter������
     * �������ڣ�(2004-9-24)
     * @param newFbbz String
     */
    public void setFbbz(String newFbbz)
    {
        m_fbbz = newFbbz;
    }

    /**
     * ����fbhl��setter������
     * �������ڣ�(2004-9-24)
     * @param newFbhl UFDouble
     */
    public void setFbhl(UFDouble newFbhl)
    {
        m_fbhl = newFbhl;
    }

    /**
     * ����fbje��setter������
     * �������ڣ�(2004-9-24)
     * @param newFbje UFDouble
     */
    public void setFbje(UFDouble newFbje)
    {
        m_fbje = newFbje;
    }

    /**
     * ����fkdw��setter������
     * �������ڣ�(2004-9-24)
     * @param newFkdw String
     */
    public void setFkdw(String newFkdw)
    {
        m_fkdw = newFkdw;
    }

    /**
     * ����fkdwzh��setter������
     * �������ڣ�(2004-9-24)
     * @param newFkdwzh String
     */
    public void setFkdwzh(String newFkdwzh)
    {
        m_fkdwzh = newFkdwzh;
    }

    /**
     * ����fkyh��setter������
     * �������ڣ�(2004-9-24)
     * @param newFkyh String
     */
    public void setFkyh(String newFkyh)
    {
        m_fkyh = newFkyh;
    }

    /**
     * ����pjbh��setter������
     * �������ڣ�(2004-9-24)
     * @param newPjbh String
     */
    public void setPjbh(String newPjbh)
    {
        m_pjbh = newPjbh;
    }

    /**
     * ����pjfx��setter������
     * �������ڣ�(2004-9-24)
     * @param newPjfx String
     */
    public void setPjfx(String newPjfx)
    {
        m_pjfx = newPjfx;
    }

    /**
     * ����pjlx��setter������
     * �������ڣ�(2004-9-24)
     * @param newPjlx String
     */
    public void setPjlx(String newPjlx)
    {
        m_pjlx = newPjlx;
    }

    /**
     * ����pjlyid��setter������
     * �������ڣ�(2004-9-24)
     * @param newPjlyid String
     */
    public void setPjlyid(String newPjlyid)
    {
        m_pjlyid = newPjlyid;
    }

    /**
     * ����pjsdrq��setter������
     * �������ڣ�(2004-9-24)
     * @param newPjsdrq UFDate
     */
    public void setPjsdrq(UFDate newPjsdrq)
    {
        m_pjsdrq = newPjsdrq;
    }

    /**************************************************************************
     * <br>Created on 2005-1-14 17:03:31<br>
     * @param pjlylx The String pjlylx to set.
     **************************************************************************/
    public void setPjyylx(String pjlylx)
    {
        this.m_pjyylx = pjlylx;
    }

    /**
     * ����pjzt��setter������
     * �������ڣ�(2004-9-24)
     * @param newPjzt String
     */
    public void setPjzt(String newPjzt)
    {
        m_pjzt = newPjzt;
    }

    /**
     * ����pk_corp��setter������
     * �������ڣ�(2004-9-24)
     * @param newPk_corp String
     */
    public void setPk_corp(String newPk_corp)
    {
        m_pk_corp = newPk_corp;
    }

    /**************************************************************************
     * <br>Created on 2005-2-1 13:34:47</br>
     * @param pk_pjyy
     **************************************************************************/
    public void setPk_pjyy(String pk_pjyy)
    {
        this.m_pk_pjyy = pk_pjyy;
    }

    /**************************************************************************
     * <br>Created on 2004-10-9 15:10:21<br>
     * @param pk_pjzb The String m_pk_pjzb to set.
     **************************************************************************/
    public void setPk_pjzb(String pk_pjzb)
    {
        this.m_pk_pjzb = pk_pjzb;
    }

    /**
     * ���ö����ʶ������Ψһ��λ����
     * �������ڣ�(2004-9-24)
     * @param newPk_pjzb String
     */
    public void setPrimaryKey(String newPk_pjzb)
    {
        m_pk_pjzb = newPk_pjzb;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:24<br>
     * @param qcpj The UFBoolean m_qcpj to set.
     **************************************************************************/
    public void setQcpj(UFBoolean qcpj)
    {
        this.m_qcpj = qcpj;
    }

    /**
     * ����sfscpz��setter������
     * �������ڣ�(2004-9-24)
     * @param newSfscpz UFBoolean
     */
    public void setSfscpz(UFBoolean newSfscpz)
    {
        m_sfscpz = newSfscpz;
    }

    /**
     * ����shr��setter������
     * �������ڣ�(2004-9-24)
     * @param newShr String
     */
    public void setShr(String newShr)
    {
        m_shr = newShr;
    }

    /**
     * ����shrq��setter������
     * �������ڣ�(2004-9-24)
     * @param newShrq UFDate
     */
    public void setShrq(UFDate newShrq)
    {
        m_shrq = newShrq;
    }

    /**
     * ����shzt��setter������
     * �������ڣ�(2004-9-24)
     * @param newShzt Integer
     */
    public void setShzt(Integer newShzt)
    {
        m_shzt = newShzt;
    }

    /**
     * ����skdw��setter������
     * �������ڣ�(2004-9-24)
     * @param newSkdw String
     */
    public void setSkdw(String newSkdw)
    {
        m_skdw = newSkdw;
    }

    /**
     * ����skdwzh��setter������
     * �������ڣ�(2004-9-24)
     * @param newSkdwzh String
     */
    public void setSkdwzh(String newSkdwzh)
    {
        m_skdwzh = newSkdwzh;
    }

    /**
     * ����skyh��setter������
     * �������ڣ�(2004-9-24)
     * @param newSkyh String
     */
    public void setSkyh(String newSkyh)
    {
        m_skyh = newSkyh;
    }

    /**
     * ����sxfje��setter������
     * �������ڣ�(2004-9-24)
     * @param newSxfje UFDouble
     */
    public void setSxfje(UFDouble newSxfje)
    {
        m_sxfje = newSxfje;
    }

    /**
     * ����thyy��setter������
     * �������ڣ�(2004-9-24)
     * @param newThyy String
     */
    public void setThyy(String newThyy)
    {
        m_thyy = newThyy;
    }

    /**************************************************************************
     * <br>Created on 2004-10-27 16:20:02<br>
     * @param ts The String m_ts to set.
     **************************************************************************/
    public void setTs(String ts)
    {
        this.m_ts = ts;
    }

    /**
     * ����ybbz��setter������
     * �������ڣ�(2004-9-24)
     * @param newYbbz String
     */
    public void setYbbz(String newYbbz)
    {
        m_ybbz = newYbbz;
    }

    /**
     * ����ybje��setter������
     * �������ڣ�(2004-9-24)
     * @param newYbje UFDouble
     */
    public void setYbje(UFDouble newYbje)
    {
        m_ybje = newYbje;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:24<br>
     * @param zdyx1 The String m_zdyx1 to set.
     **************************************************************************/
    public void setZdyx1(String zdyx1)
    {
        this.m_zdyx1 = zdyx1;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:24<br>
     * @param zdyx2 The String m_zdyx2 to set.
     **************************************************************************/
    public void setZdyx2(String zdyx2)
    {
        this.m_zdyx2 = zdyx2;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:50<br>
     * @param zdyx3 The String m_zdyx3 to set.
     **************************************************************************/
    public void setZdyx3(String zdyx3)
    {
        this.m_zdyx3 = zdyx3;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:50<br>
     * @param zdyx4 The String m_zdyx4 to set.
     **************************************************************************/
    public void setZdyx4(String zdyx4)
    {
        this.m_zdyx4 = zdyx4;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:50<br>
     * @param zdyx5 The String m_zdyx5 to set.
     **************************************************************************/
    public void setZdyx5(String zdyx5)
    {
        this.m_zdyx5 = zdyx5;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:50<br>
     * @param zdyx6 The String m_zdyx6 to set.
     **************************************************************************/
    public void setZdyx6(String zdyx6)
    {
        this.m_zdyx6 = zdyx6;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:50<br>
     * @param zdyx7 The String m_zdyx7 to set.
     **************************************************************************/
    public void setZdyx7(String zdyx7)
    {
        this.m_zdyx7 = zdyx7;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @param zdyx8 The String m_zdyx8 to set.
     **************************************************************************/
    public void setZdyx8(String zdyx8)
    {
        this.m_zdyx8 = zdyx8;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @param zdyx9 The String m_zdyx9 to set.
     **************************************************************************/
    public void setZdyx9(String zdyx9)
    {
        this.m_zdyx9 = zdyx9;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @param zyx1 The String m_zyx1 to set.
     **************************************************************************/
    public void setZyx1(String zyx1)
    {
        this.m_zyx1 = zyx1;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @param zyx10 The String m_zyx10 to set.
     **************************************************************************/
    public void setZyx10(String zyx10)
    {
        this.m_zyx10 = zyx10;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @param zyx2 The String m_zyx2 to set.
     **************************************************************************/
    public void setZyx2(String zyx2)
    {
        this.m_zyx2 = zyx2;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @param zyx3 The String m_zyx3 to set.
     **************************************************************************/
    public void setZyx3(String zyx3)
    {
        this.m_zyx3 = zyx3;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:51<br>
     * @param zyx4 The String m_zyx4 to set.
     **************************************************************************/
    public void setZyx4(String zyx4)
    {
        this.m_zyx4 = zyx4;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:52<br>
     * @param zyx5 The String m_zyx5 to set.
     **************************************************************************/
    public void setZyx5(String zyx5)
    {
        this.m_zyx5 = zyx5;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:52<br>
     * @param zyx6 The String m_zyx6 to set.
     **************************************************************************/
    public void setZyx6(String zyx6)
    {
        this.m_zyx6 = zyx6;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:52<br>
     * @param zyx7 The String m_zyx7 to set.
     **************************************************************************/
    public void setZyx7(String zyx7)
    {
        this.m_zyx7 = zyx7;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:52<br>
     * @param zyx8 The String m_zyx8 to set.
     **************************************************************************/
    public void setZyx8(String zyx8)
    {
        this.m_zyx8 = zyx8;
    }

    /**************************************************************************
     * <br>Created on 2004-10-21 15:20:52<br>
     * @param zyx9 The String m_zyx9 to set.
     **************************************************************************/
    public void setZyx9(String zyx9)
    {
        this.m_zyx9 = zyx9;
    }

    /**
     * ��֤���������֮��������߼���ȷ�ԡ�
     * �������ڣ�(2004-9-24)
     * @exception nc.vo.pub.ValidationException �����֤ʧ�ܣ��׳�
     *     ValidationException���Դ�����н��͡�
     */
    public void validate() throws ValidationException
    {
        ArrayList errFields = new ArrayList(); // errFields record those null fields that cannot be null.
        // ����Ƿ�Ϊ������յ��ֶθ��˿�ֵ���������Ҫ�޸��������ʾ��Ϣ��
        if (m_pjfx == null)
        {
            errFields.add(new String("m_pjfx"));
        }
        if (m_pjlx == null)
        {
            errFields.add(new String("m_pjlx"));
        }
        if (m_pk_pjzb == null)
        {
            errFields.add(new String("m_pk_pjzb"));
        }
        if (m_sfscpz == null)
        {
            errFields.add(new String("m_sfscpz"));
        }
        // construct the exception message:
        StringBuffer message = new StringBuffer();
        message.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620","UPP3620-000017")/*@res "�����ֶβ���Ϊ�գ�"*/);
        if (errFields.size() > 0)
        {
            String[] temp = (String[]) errFields.toArray(new String[0]);
            message.append(temp[0]);
            for (int i = 1; i < temp.length; i++)
            {
                message.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620","UPP3620-000018")/*@res "��"*/);
                message.append(temp[i]);
            }
            // throw the exception:
            throw new NullFieldException(message.toString());
        }
    }
}