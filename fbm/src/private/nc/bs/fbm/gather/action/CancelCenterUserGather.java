package nc.bs.fbm.gather.action;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
/**
 *
 ***********************************************************
 * ���ڣ�2008-3-18
 * ����Ա:���ɽ
 * ���ܣ�ɾ������ʹ����Ʊ
 ***********************************************************
 */
public class CancelCenterUserGather<K extends RegisterVO,T extends RegisterVO> extends ActionGather<K,T> {


	@Override
	protected String doCheck(BusiActionParamVO param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO param)
			throws BusinessException {
		return FbmStatusConstant.HAS_CENTER_USE;
	}

	//ֱ��ɾ���˶���
	protected String getEndStatus(BusiActionParamVO param)
			throws BusinessException {
//			return FbmStatusConstant.HAS_RELIEF_KEEP;
		return null;
	}
	/**
	 * ȡ������ʹ��ʱ������ʹ�����ӵ��˻�ɾ��
	 */
//	public void dealAccount(String pk_action, ActionParamVO param)
//			throws BusinessException {
//		CommonDAO dao = new CommonDAO();
//		dao.delAccountDetailByActionPK(pk_action);
//	}

	public void dealAccount(String pk_action, BusiActionParamVO<T> param)
	throws BusinessException {
		StringBuffer sqlbuffer = new StringBuffer();

		sqlbuffer.append(" select 1 from ");
		sqlbuffer.append(" fbm_accountdetail inner join fbm_reckon_b ");
		sqlbuffer.append(" on fbm_accountdetail.pk_detail = fbm_reckon_b.pk_detail");
		sqlbuffer.append(" where fbm_accountdetail.pk_action = '"	+ pk_action + "' ");
		sqlbuffer.append(" and pk_type = '"+ FbmBusConstant.ACCOUNT_TYPE_LIQUID + "'");
		sqlbuffer.append(" and isnull(fbm_reckon_b.dr,0)=0");
		BaseDAO bdao = new BaseDAO();
		Object ction = bdao.executeQuery(sqlbuffer.toString(), new ColumnListProcessor());
		List list = (List)ction;
		if(list!=null && list.size()>0)
		{
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000129")/*@res "�������Ʊ�ݲ���ִ�д˲���!"*/);
		}

		CommonDAO dao = new CommonDAO();
		dao.delAccountDetailByActionPK(pk_action);
    }
}