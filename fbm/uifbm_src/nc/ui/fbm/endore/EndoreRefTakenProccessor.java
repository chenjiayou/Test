package nc.ui.fbm.endore;

import nc.ui.fac.account.pub.AbstractTakenColUtil;
import nc.ui.fac.account.pub.CurrencyDecimalVO;
import nc.ui.fac.account.pub.ITakenColUtil;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenVO;
import nc.ui.fbm.pub.refmodel.BaseInfoAccBankRefModel;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.register.RegisterVO;


public class EndoreRefTakenProccessor extends RefTakenProccessor {

	public EndoreRefTakenProccessor(BillListPanel listpanel,
			BillCardPanel cardpanel) {
		super(listpanel, cardpanel);
	}
	
	/**
	 * Э��ֵ��ʵ�ַ���
	 */
	@Override
	protected ITakenColUtil createTakenColUtil() {
		return new AbstractTakenColUtil() {
			protected RefTakenVO[] getRefTakenVOs() {
				return new RefTakenVO[] {
						new RefTakenVO("fbm_baseinfo",EndoreVO.PK_SOURCE,
								new String[] { 
												EndoreVO.FBMBILLTYPE,//Ʊ������
												EndoreVO.PK_CURR,//����
												EndoreVO.CONTRACTNO,//���׺�ͬ��
												EndoreVO.INVOICEDATE,//��Ʊ����
												
												EndoreVO.PK_BASEINFO,//Ʊ�ݻ�����Ϣ�У�
												
												EndoreVO.ENDDATE,//��������
												EndoreVO.INVOICEUNIT,//��Ʊ��λ
												EndoreVO.KEEPUNIT,//��ŵص�
												
												EndoreVO.PAYBANK,//��������
												
												EndoreVO.PAYBANKACC,//���������ʺ�
												EndoreVO.PAYUNIT,//���λ
//												
												EndoreVO.RECEIVEBANK,//�տ�����
												EndoreVO.RECEIVEBANKACC,//�տ������ʺ�
												EndoreVO.RECEIVEUNIT,//�տλ
////												
												EndoreVO.MONEYY,//Ʊ����
												EndoreVO.MONEYB,//���ҽ��
												EndoreVO.BRATE,//���һ���
												EndoreVO.FRATE,//���һ���
												EndoreVO.MONEYF,//���ҽ��
												
//												EndoreVO.ENDORSEE_ACC,//�����������ʺ�
//												EndoreVO.ENDORSEE_BANK,//����������
//												EndoreVO.ENDORSER_ACC,//���������ʺ�
//												EndoreVO.ENDORSER_BANK,//��������
												EndoreVO.ACCEPTANCENO,//�ж�Э����
												EndoreVO.FBMBILLNO//Ʊ�ݱ��
												
											 },
								new String[] {
											  RegisterVO.FBMBILLTYPE,
											  RegisterVO.PK_CURR,
											  RegisterVO.CONTRACTNO,
											  RegisterVO.INVOICEDATE,
											  
											  RegisterVO.PK_BASEINFO,
											  
											  RegisterVO.ENDDATE,
											  RegisterVO.INVOICEUNIT,
											  RegisterVO.KEEPUNIT,
											  
											  RegisterVO.PAYBANK,
											  
											  RegisterVO.PAYBANKACC,
											  RegisterVO.PAYUNIT,
//											  
											  RegisterVO.RECEIVEBANK,
											  RegisterVO.RECEIVEBANKACC,
											  RegisterVO.RECEIVEUNIT,
////											  
											  RegisterVO.MONEYY,
											  RegisterVO.MONEYB,
											  RegisterVO.BRATE,
											  RegisterVO.FRATE,
											  RegisterVO.MONEYF,
											  

											  RegisterVO.ACCEPTANCENO,
											  RegisterVO.FBMBILLNO
											 
											  })

				};
			}
			@Override
			public CurrencyDecimalVO[] getCurrencyDecimalVOs() {
				return new CurrencyDecimalVO[] {
						new CurrencyDecimalVO("fbm_baseinfo",
								EndoreVO.PK_CURR, new String[] {
								EndoreVO.MONEYY, EndoreVO.MONEYF,
								EndoreVO.MONEYB, EndoreVO.FRATE,
								EndoreVO.BRATE }) };
			}	
		};
	}

}
