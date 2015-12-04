/*
 * @(#)FFAcceptBill.java 2008-9-19
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
 * @version 1.0 2008-9-19
 * @since NC5.5
 */
public class FFAcceptBill extends QueryAcceptBillDAO implements IFormulaItem {

	public FFAcceptBill() throws NamingException {
		super();
	}

	/*
	 * 应付票据
	 * 
	 * @see nc.itf.ff.pub.IFormulaItem#getInterfaceVOs(nc.vo.ff.reason.FormulaCondVO)
	 */
	public ArrayList getInterfaceVOs(FormulaCondVO formulaconditionvo)
			throws BusinessException {
		return getInterfaceVOs(formulaconditionvo, Flowdirection.OUT);
	}
}
