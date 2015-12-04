package nc.bs.fbm.gather.action;

import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

public class DestroyGather<K extends RegisterVO, T extends RegisterVO> extends ActionGather<K, T> {

	/**
	 * 
	 */
	public DestroyGather() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.bs.fbm.pub.action.AbstractAction#doCheck(nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.bs.fbm.pub.action.AbstractAction#getBeginStatus(nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		return FbmStatusConstant.REGISTER;
	}

	/* (non-Javadoc)
	 * @see nc.bs.fbm.pub.action.AbstractAction#getEndStatus(nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.HAS_DESTROY;
	}

	public void dealGather4Destroy(BusiActionParamVO<T> param)throws BusinessException{
		super.updateActionTable(param);
	}
	
	public BusiActionParamVO<T>[] build4Destroy(K data,String actioncode) throws BusinessException {
		return super.buildParam(data, actioncode);
	}
}
