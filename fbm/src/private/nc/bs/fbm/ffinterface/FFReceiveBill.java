/*
 * @(#)FFReceiveBill.java 2008-9-27
 * Copyright 2008 UFIDA Software CO.LTD. All rights reserved.
 */
package nc.bs.fbm.ffinterface;

import java.util.ArrayList;

import javax.naming.NamingException;

import nc.itf.ff.enumerate.Flowdirection;
import nc.itf.ff.pub.IFormulaItem;
import nc.vo.ff.reason.FormulaCondVO;
import nc.vo.pub.BusinessException;

/**
 * TODO 简要说明
 * 
 * <p>
 * 
 * @author wangxf
 * @version 1.0 2008-9-27
 * @since NC5.5
 */
public class FFReceiveBill extends QueryAcceptBillDAO implements IFormulaItem {

	/**
	 * FFReceiveBill的构造方法
	 * 
	 * @throws NamingException
	 */
	public FFReceiveBill() throws NamingException {
		super();
	}

	/**
	 * FFReceiveBill的构造方法
	 * 
	 * @param dbName
	 * @throws NamingException
	 */
	public FFReceiveBill(String dbName) throws NamingException {
		super(dbName);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.itf.ff.pub.IFormulaItem#getInterfaceVOs(nc.vo.ff.reason.FormulaCondVO)
	 */
	public ArrayList getInterfaceVOs(FormulaCondVO formulaconditionvo)
			throws BusinessException {
		return getInterfaceVOs(formulaconditionvo, Flowdirection.IN);
	}

}
