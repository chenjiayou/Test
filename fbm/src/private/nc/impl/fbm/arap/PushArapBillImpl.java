package nc.impl.fbm.arap;

import nc.bs.fbm.out.PushBillService;
import nc.itf.fbm.arap.IPushArapBill;
import nc.vo.arap.outer.IArapGeneralObj;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;

public class PushArapBillImpl implements IPushArapBill {

	public IArapGeneralObj buildArapObj(SuperVO vo,UFDate date) throws BusinessException {
		// TODO Auto-generated method stub
		PushBillService service = new PushBillService();
		IArapGeneralObj obj = service.buildArapObj(vo,date);
		return obj;
	}
}
