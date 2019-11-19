package com.chmei.nzbdata.common.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chmei.nzbdata.common.dao.IBaseDao;

/**
 * 用户Dao基类
 */
@Repository("baseDao")
public class BaseDaoImpl implements IBaseDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	/**
	 * 根据Id获取对象
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param id
	 *            主键
	 * @return Object对象
	 */
	@Override
	public Object queryForObject(String sqlId, Object id) {
		return getSqlSessionTemplate().selectOne(sqlId, id);
	}

	/**
	 * 根据Id获取对象
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param id
	 *            主键
	 * @param cls
	 *            返回的对象Class
	 * @return cls对应的类
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T queryForObject(String sqlId, Object id, Class<T> cls) {
		return (T) getSqlSessionTemplate().selectOne(sqlId, id);
	}

	/**
	 * 根据条件获取对象
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param params
	 *            参数
	 * @return
	 */
	@Override
	public Object queryForObject(String sqlId, Map<String, Object> params) {
		return getSqlSessionTemplate().selectOne(sqlId, params);
	}

	/**
	 * 根据条件获取对象
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param params
	 *            参数
	 * @param cls
	 *            返回的对象Class
	 * @return cls对应的类
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T queryForObject(String sqlId, Map<String, Object> params,
			Class<T> cls) {
		return (T) getSqlSessionTemplate().selectOne(sqlId, params);
	}

	/**
	 * 获取数据总条�?
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param params
	 *            参数
	 * @return 条数
	 */
	@Override
	public int getTotalCount(String sqlId, Map<String, Object> params) {
		return (Integer) getSqlSessionTemplate().selectOne(sqlId, params);
	}

	/**
	 * 查询列表
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param params
	 *            参数
	 * @param cls
	 *            返回的对象Class
	 * @return 列表<cls对应的类>
	 */
	@Override
	public <T> List<T> queryForList(String sqlId, Map<String, Object> params,
			Class<T> cls) {
		return getSqlSessionTemplate().selectList(sqlId, params);
	}

	/**
	 * 查询列表
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param params
	 *            参数
	 * @return 列表
	 */
	@Override
	public List<Map<String, Object>> queryForList(String sqlId,
			Map<String, Object> param) {
		return getSqlSessionTemplate().selectList(sqlId, param);
	}
	
	/**
	 * @param sqlId
	 * @param params
	 * @return
	 * @see com.chmei.nzbdata.common.dao.IBaseDao#queryForList(java.lang.String, java.lang.Object) 
	 */ 
	@Override
	public List<Map<String, Object>> queryForList(String sqlId, Object params) {
		return getSqlSessionTemplate().selectList(sqlId, params);
	}

	/**
	 * 修改数据
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param object
	 *            对象
	 * @return 修改的行
	 */
	@Override
	public int update(String sqlId, Object object) {
		return getSqlSessionTemplate().update(sqlId, object);
	}

	/**
	 * 插入数据
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param object
	 *            待插入的对象
	 * @return 插入条数
	 */
	@Override
	public int insert(String sqlId, Object object) {
		return (Integer) getSqlSessionTemplate().insert(sqlId, object);
	}

	/**
	 * 删除数据
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param id
	 *            主键
	 * @return 主键
	 */
	@Override
	public int delete(String sqlId, int id) {
		return getSqlSessionTemplate().delete(sqlId, id);
	}
	
	/**
	 * @param sqlId
	 * @param param
	 * @return
	 * @see com.chmei.nzbdata.common.dao.IBaseDao#delete(java.lang.String, java.lang.Object) 
	 */ 
	@Override
	public int delete(String sqlId, Object params) {
		return getSqlSessionTemplate().delete(sqlId, params);
	}
	
	/**
	 * 删除数据
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param map
	 *            待删除的对象
	 * @return 主键
	 */
	@Override
	public int delete(String sqlId, Map<String, Object> map) {
		return getSqlSessionTemplate().delete(sqlId, map);
	}
	
}