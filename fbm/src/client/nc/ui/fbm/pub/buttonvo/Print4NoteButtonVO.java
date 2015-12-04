/*
 * Created on 2005-3-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nc.ui.fbm.pub.buttonvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * @author daixh
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Print4NoteButtonVO implements IBillButtonVO {
	// 2005-06-01:为了在多语言环境下处理权限而新增的属性
	private final static String btnChinaName = "票据通打印";

	/**
	 * 
	 */
	public Print4NoteButtonVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		// 初始化按钮处理
		// public static final int OP_INIT=4;
		// //编辑时按钮处理
		// public static final int OP_EDIT = 0;
		// //单据新增状态
		// public static final int OP_ADD = 1;
		// //非编辑时按钮处理
		// public static final int OP_NOTEDIT = 2;
		// //参照单据时新增单据状态
		// public static final int OP_REFADD = 3;
		// //不能增加、只能修改非编辑时按钮处理
		// public static final int OP_NOADD_NOTEDIT = 5;
		// //不能增加、修改时按钮处理
		// public static final int OP_NO_ADDANDEDIT = 6;
		// //所有的功能都可用
		// public static final int OP_ALL=7;
		//

		// 1.支付按钮
		ButtonVO btnVo = new ButtonVO();
		btnVo.setBtnNo(IFBMButton.PRINT4NOTE);
		// 2005-06-01:为了在多语言环境下处理权限而新增的属性
		btnVo.setBtnChinaName(btnChinaName);
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000397")/* @res"票据通打印"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000397")/* @res"票据通打印"*/);
		btnVo.setBusinessStatus(null);
		btnVo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });
		btnVo.setHotKey("P");
		btnVo.setDisplayHotKey("(Ctrl+Alt+P)");
		// btnVo.setOperateStatus(null);
		return btnVo;
	}

}