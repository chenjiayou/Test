package nc.bs.fbm.upgrade.arapproc;

import nc.bs.fbm.upgrade.IDataProcessor;


/**
 * 
 * �๦��˵���� 
 * 	Processor����������Processor 
 * ���ڣ�2007-11-20 
 * ����Ա�� wues
 */
public class ArapProcessorFactory {
	public static IDataProcessor createProcessor(int processorType) {
		IDataProcessor processor = null;
		switch (processorType) {
		case 0://������Ϣ
			processor = new ArapBaseinfoProcessor();
			break;
		case 1://�ж�
			processor = new ArapAcceptProcessor();
			break;
		case 2://����
			processor = new ArapCollectionProcessor();
			break;
		case 3://����
			processor = new ArapDiscountProcessor();
			break;
		case 4://��Ʊ
			processor = new ArapGatherProcessor();
			break;
		case 5://��Ѻ
			processor = new ArapImpawnProcessor();
			break;
		case 6://��Ʊ
			processor = new ArapInvoiceProcessor();
			break;
		case 7://��Ʊ
			processor = new ArapReturnProcessor();
			break;
		case 8://����
			processor = new ArapEndoreProcessor();
			break;
		case 9://��Ѻ����
			processor = new ArapImpawnBackProcessor();
		}
		return processor;
	}
}
