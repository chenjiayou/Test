package nc.ui.fbm.gather.checker;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;

/**
 * ������Ϣ�ʺ�У��
 * @author xwq
 *
 * 2008-9-28
 */
public class BaseinfoAccountChecker extends AbstractUIChecker {

	public BaseinfoAccountChecker(){
		super();
	}

	public BaseinfoAccountChecker(FBMManageUI ui){
		super(ui);
	}
	@Override
	public String check() {
		// String payunit =
		// (String)getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYUNIT).getValueObject();
		// String payacc =
		// (String)getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC).getValueObject();
		// String paybank =
		// (String)getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBANK).getValueObject();
		// String retMsg = "";
		// retMsg = check4InnerCust(payunit,payacc,paybank);
		//
		// String receiveunit =
		// (String)getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEUNIT).getValueObject();
		// String receiveacc =
		// (String)getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).getValueObject();
		// String receivebank =
		// (String)getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANK).getValueObject();
		// retMsg = retMsg +
		// check4InnerCust(receiveunit,receiveacc,receivebank);
		//
		//
		// return retMsg.equals("")?null:retMsg;
		return null;
	}

	/**
	 * У���ڲ�����ʱ�������˻��Ϳ������б���Ϊ���ջ��
	 * @return
	 */
	// private String check4InnerCust(String pk_cubasdoc,String pk_acc,String
	// pk_bankdoc) {
	// if(pk_cubasdoc !=null){
	// ICustBasDocQuery custQry =
	// (ICustBasDocQuery)NCLocator.getInstance().lookup(ICustBasDocQuery.class);
	// try {
	// //�������Ƿ�Ϊ�ֶ�¼��
	// CustBasVO inputCustBasVO = FBMProxy.retriveInputCust(pk_cubasdoc);
	// if(inputCustBasVO !=null){
	// return "";
	// }
	//
	// CubasdocVO cubasdocVO = custQry.queryCustBasDocVOByPK(pk_cubasdoc);
	// if(cubasdocVO !=null ){
	// Integer prop =((CustBasVO)cubasdocVO.getParentVO()).getCustprop();
	// if(prop.intValue() !=0){//�ڲ�����
	// HYPubBO_Client client = new HYPubBO_Client();
	//
	// if(pk_acc == null){
	// return
	// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000032")/*@res
	// "�ڲ����̶�Ӧ�������˻������ֹ�¼��."*/;
	// }else{
	// if(FBMProxy.retriveInputBankacc(pk_acc) !=null){
	// return
	// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000032")/*@res
	// "�ڲ����̶�Ӧ�������˻������ֹ�¼��."*/;
	// }
	// }
	// BankaccbasVO bankaccbasVO =
	// (BankaccbasVO)client.queryByPrimaryKey(BankaccbasVO.class, pk_acc);
	//
	// if(bankaccbasVO == null){
	// return
	// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000032")/*@res
	// "�ڲ����̶�Ӧ�������˻������ֹ�¼��."*/;
	// }
	// if(pk_bankdoc == null){
//							//return "�ڲ����̶�Ӧ�����в����ֹ�¼��.";
//						}
	// // BankdocVO bankdocVO =
	// (BankdocVO)client.queryByPrimaryKey(BankdocVO.class, pk_bankdoc);
	// // if(bankdocVO == null){
	// // //return "�ڲ����̶�Ӧ�����в����ֹ�¼��.";
	// // }
	//
	// }
	// }
	// } catch (BusinessException e) {
	// Logger.error(e.getMessage(),e);
	// return e.getMessage();
	// }
	// }
	// return "";
	// }


}