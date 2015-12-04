package nc.itf.fbm.gather;

import java.util.Vector;

public interface IGatherBatchConsign {

	public String batchBankConsignOP(Vector vec,String currentdate,String operator) throws Exception;
	
	public String batchImpawnOP(Vector vec,String currentdate,String operator)throws Exception;
	
	public String batchDiscountOP(Vector vec,String currentdate,String operator)throws Exception;
	
}
