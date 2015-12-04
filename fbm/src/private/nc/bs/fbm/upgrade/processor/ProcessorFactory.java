package nc.bs.fbm.upgrade.processor;

import nc.bs.fbm.upgrade.IDataProcessor;


/**
 * 
 * 类功能说明： 
 * 	Processor工厂，生产Processor 
 * 日期：2007-11-20 
 * 程序员： wues
 */
public class ProcessorFactory {
	public static IDataProcessor createProcessor(int processorType) {
		IDataProcessor processor = null;
		switch (processorType) {
		case 0://基本信息
			processor = new BaseinfoProcessor();
			break;
		case 1://承兑
			processor = new AcceptProcessor();
			break;
		case 2://托收
			processor = new CollectionProcessor();
			break;
		case 3://贴现
			processor = new DiscountProcessor();
			break;
		case 4://收票
			processor = new GatherProcessor();
			break;
		case 5://质押
			processor = new ImpawnProcessor();
			break;
		case 6://开票
			processor = new InvoiceProcessor();
			break;
		case 7://退票
			processor = new ReturnProcessor();
			break;
		case 8://背书
			processor = new EndoreProcessor();
			break;
		case 9://质押回收
			processor = new ImpawnBackProcessor();
			break;
		case 10://内部存放
			processor = new InnerKeepProcessor();
			break;
		}
		return processor;
	}
}
