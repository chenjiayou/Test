package nc.itf.fbm.endore;

public interface IEndoreService 
{
	//判断收付报是否启用
	public boolean volidARAP(String pk_corp,String[] product) throws Exception ;

	
	//取"背书办理单是否与收付报单据集成应用"参数值
	public String getParamValue(String pk_corp) throws Exception;
	
	
	//取背书办理表中的syscode字段，判断是否由收付报推式生成的背书办理单
	public boolean isCratedByARAP(String pk_endore) throws Exception;
	
}
