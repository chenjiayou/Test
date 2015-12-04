package nc.bs.fbm.upgrade.arapproc;

import nc.bs.fbm.upgrade.IDataProcessor;


/**
 * 
 * 类功能说明： 
 * 	Processor工厂，生产Processor 
 * 日期：2007-11-20 
 * 程序员： wues
 */
public class ArapProcessorFactory {
	public static IDataProcessor createProcessor(int processorType) {
		IDataProcessor processor = null;
		switch (processorType) {
		case 0://基本信息
			processor = new ArapBaseinfoProcessor();
			break;
		case 1://承兑
			processor = new ArapAcceptProcessor();
			break;
		case 2://托收
			processor = new ArapCollectionProcessor();
			break;
		case 3://贴现
			processor = new ArapDiscountProcessor();
			break;
		case 4://收票
			processor = new ArapGatherProcessor();
			break;
		case 5://质押
			processor = new ArapImpawnProcessor();
			break;
		case 6://开票
			processor = new ArapInvoiceProcessor();
			break;
		case 7://退票
			processor = new ArapReturnProcessor();
			break;
		case 8://背书
			processor = new ArapEndoreProcessor();
			break;
		case 9://质押回收
			processor = new ArapImpawnBackProcessor();
		}
		return processor;
	}
}
