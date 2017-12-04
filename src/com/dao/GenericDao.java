package com.dao;

import java.io.Serializable;
import java.util.List;

import com.dao.Pagination;

/**
 * 通用Dao接口
 * 
 * @author tangwei
 * 
 * @param <T>
 *            实体对象类
 * @param <PK>
 *            主键
 */
public interface GenericDao<T, PK extends Serializable> {

	/**
	 * 根据主键获得一个实体对象
	 * 
	 * @param id
	 * @return
	 */
	public T findById(PK id);

	/**
	 * 获得表中的所有实体对象
	 * 
	 * @return
	 */
	public List<T> findAll();

	/**
	 * 新增一个实体对象
	 * 
	 * @param entity
	 * @return
	 */
	public T save(T entity);
	
	/**
	 * 批量新增实体对象
	 * 
	 * @param list
	 * @return
	 */
	public <T> boolean saveEntities(List<T> list);
	/**
	 * 更新一个实体对象
	 * 
	 * @param entity
	 */
	public void update(T entity);

	public <T> boolean updateEntities(List<T> list);
	/**
	 * 删除一个实体对象
	 * 
	 * @param entity
	 */
	public void delete(T entity);
	
	public <T> boolean delEntities(List<T> list);

	/**
	 * 根据HQL语句和查询条件，获得具体数量
	 * 
	 * @param hql
	 *            不需要也不能写select count(*),直接从from开始写
	 * @param params
	 * @return
	 */
	public Long countByHQL(String hql, Object[] params);
	
	/**
	 * 根据HQL语句和查询条件，获得一个实体对象
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public T getEntityBySQL(final String sql, final Object[] params);	
	/**
	 * 根据HQL语句和查询条件，获得一个实体对象
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public T getEntityByHQL(String hql, Object[] params);
	/**
	 * 根据HQL语句和查询条件，获得多个实体对象
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<T> listEntityByHQL(String hql, Object[] params);

	/**
	 * 根据HQL语句和查询条件，获得多个实体对象,分页显示,可排序
	 * 
	 * @param hql
	 *            必须是查询整个model类的信息，不能只查询部分字段 正确写法：from 类名 where 条件 order by
	 *            错误写法：select 表名(字段名) from 类名 where 条件
	 * @param params
	 * @param pageSize
	 *            每页显示记录数
	 * @param pageNumber
	 *            当前第几页
	 * @return
	 */
	public Pagination listEntityByHQL(String hql, Object[] params,
			Integer pageSize, Integer pageNumber);

	public Pagination listEntityByHQL1(String hql, Object[] params,
			Integer pageSize, Integer pageNumber);
	
	public Pagination listEntityByHQLbox(String hql, Object[] params,
			Integer pageSize, Integer pageNumber);

	/**
	 * 执行删除或者更新的HQL语句
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public Integer updateByHQL(String hql, Object[] params);

	/**
	 * 根据SQL语句和查询条件，获得list对象
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * */
	public List<String> getListBySQL(String hql, Object[] params);

	 /** 根据SQL语句和查询条件，获得Str对象
		 * 
		 * @param sql
		 * @param params
		 * @return
		 * */
	public String getStrBySQL(String hql, Object[] params);
	
	
	/**
	 * 根据SQL语句和查询条件，获得多个实体对象
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<T> listEntityBySQL(String sql, Object[] params);

	
}
