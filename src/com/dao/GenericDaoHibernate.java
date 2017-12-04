package com.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

import com.dao.GenericDao;
import com.dao.Pagination;

@SuppressWarnings("deprecation")
public class GenericDaoHibernate<T, PK extends Serializable> implements GenericDao<T, PK> {

	private final Class<T> clazz;
	protected HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	public GenericDaoHibernate() {
		clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource()
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public T findById(PK id) {
		return (T) this.hibernateTemplate.get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return (List<T>) this.hibernateTemplate.find("from " + clazz.getName());
	}

	@Override
	public T save(T entity) {
		this.hibernateTemplate.persist(entity);
		return entity;	
	}

	@SuppressWarnings("hiding")
	public <T> boolean saveEntities(List<T> list) {
		boolean flag = false;
		int batchSize = 30;
		int i = 0;
		try {
		for (Object entity : list) {
			this.hibernateTemplate.persist(entity);
		i++;
		if (i % batchSize == 0) {
			this.hibernateTemplate.flush();
			this.hibernateTemplate.clear();
		}
		}
		flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("saveEntities:"+flag);
		return flag;
		}
	@Override
	public void update(T entity) {
		this.hibernateTemplate.merge(entity);
	}
	@Override
	public <T> boolean updateEntities(List<T> list){
		boolean flag = false;
		int batchSize = 30;
		int i = 0;
		try {
		for (Object entity : list) {
			this.hibernateTemplate.update(entity);
		i++;
		if (i % batchSize == 0) {
			this.hibernateTemplate.flush();
			this.hibernateTemplate.clear();
		}
		}
		flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("updateEntities:"+flag);
		return flag;
	}
	@Override
	public void delete(T entity) {
		this.hibernateTemplate.delete(entity);
	}

	public <T> boolean delEntities(List<T> list) {
		boolean flag = false;
		int batchSize = 30;
		int i = 0;
		try {
			for (Object entity : list) {
				this.hibernateTemplate.delete(entity);
			i++;
			if (i % batchSize == 0) {
				this.hibernateTemplate.flush();
				this.hibernateTemplate.clear();
			}
			}
			flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("delEntities:"+flag);
		return flag;
	}
	/**
	 * 根据HQL语句和查询条件，获得一个对象
	 * 例：分页时查询出总的记录数
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object queryObject(final String hql, final Object[] params) {
		Object obj = this.hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				return query.uniqueResult();
			}
		});
		return obj;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object queryObject1(final String hql, final Object[] params) {
		Object obj = this.hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(hql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				return query.uniqueResult();
			}
		});
		return obj;
	}

	@Override
	public Long countByHQL(String hql, Object[] params) {
		// 拼接成新的hql字符串，用于查询总共多少条数据, 如果有order by要截取掉，因为有的数据库不支持
		String nhql = "select count(*) " + hql.split(" order by")[0];
		return (Long) this.queryObject(nhql, params); // 获得总的记录数
	}
	
	@Override
	public T getEntityByHQL(String hql, Object[] params) {
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) this.hibernateTemplate.find(hql, params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listEntityByHQL(String hql, Object[] params) {
		return (List<T>) this.hibernateTemplate.find(hql, params);
	}

	/**
	 * 分页查询
	 * 
	 * @param hql
	 * @param params
	 * @param pageSize
	 * @param pageNumber
	 * @param totalCount
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Pagination getPagination(final String hql, final Object[] params, final Integer pageSize, final Integer pageNumber, final Integer totalCount) {
		return (Pagination) this.hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Pagination pagination = new Pagination(pageSize, pageNumber, totalCount);
				Query query = session.createQuery(hql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				query.setFirstResult(pageSize * (pageNumber - 1));
				query.setMaxResults(pageSize);
				pagination.setList(query.list());
				return pagination;
			}
		});
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Pagination getPagination1(final String hql, final Object[] params, final Integer pageSize, final Integer pageNumber, final Integer totalCount) {
		return (Pagination) this.hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Pagination pagination = new Pagination(pageSize, pageNumber, totalCount);
				Query query = session.createSQLQuery(hql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				query.setFirstResult(pageSize * (pageNumber - 1));
				query.setMaxResults(pageSize);
				pagination.setList(query.list());
				return pagination;
			}
		});
	}
	
	@Override
	public Pagination listEntityByHQL(String hql, Object[] params, Integer pageSize, Integer pageNumber) {
		String nhql = "select count(*) " + hql.substring((hql.split("from")[0].length()-1)<0?0:(hql.split("from")[0].length()-1), hql.length()-1).split(" order by")[0];
		long temp = (Long) this.queryObject(nhql, params);
		Integer totalCount = (int) temp;
		return this.getPagination(hql, params, pageSize, pageNumber, totalCount);
	}
	
	@Override
	public Pagination listEntityByHQL1(String hql, Object[] params, Integer pageSize, Integer pageNumber) {
		String nhql = "select count(*) from (select distinct ij.docserialnum,ir.docname,ir.scanuser,ir.orgcode,ir.cartonno,ir.boxno,to_char(ir.scantime, 'yyyy-mm-dd hh24:mi:ss'),count(ir.docname),ij.bqacceptno " + hql.substring((hql.split("from")[0].length()-1)<0?0:(hql.split("from")[0].length()-1), hql.length()-1).split(" order by")[0]+")";
		Integer totalCount = Integer.parseInt(this.queryObject1(nhql, params).toString());
		return this.getPagination1(hql, params, pageSize, pageNumber, totalCount);
	}
	
	
	@Override
	public Pagination listEntityByHQLbox(String hql, Object[] params, Integer pageSize, Integer pageNumber) {
		String nhql = "select count(*) from (select distinct ij.docserialnum,to_char(ij.creationtime, 'yyyy-mm-dd hh24:mi:ss'),ij.maindocname,ir.scanuser,ir.orgcode,ir.cartonno,ir.boxno,count(ir.docname) " + hql.substring((hql.split("from")[0].length()-1)<0?0:(hql.split("from")[0].length()-1), hql.length()-1).split(" order by")[0]+")";
		Integer totalCount = Integer.parseInt(this.queryObject1(nhql, params).toString());
		return this.getPagination1(hql, params, pageSize, pageNumber, totalCount);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer updateEntity(final String hql, final Object[] params) {
		Integer i = this.hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				Integer count = query.executeUpdate();
				return count;
			}
		});
		return i;
	}

	@Override
	public Integer updateByHQL(String hql, Object[] params) {
		return updateEntity(hql, params);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> getListBySQL(final String hql, final Object[] params) {
		return this.hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(hql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				return query.list();
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getStrBySQL(final String hql, final Object[] params) {
		return this.hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(hql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				
				if (query.uniqueResult() == null) {
					return "";
				}
				else {
					return query.uniqueResult().toString();
				}

			}
		});
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<T> listEntityBySQL(final String sql, final Object[] params) {
		return this.hibernateTemplate.execute(new HibernateCallback(){
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException {
				Query query = session.createSQLQuery(sql).addEntity(clazz);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				return query.list();
			}
		});
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T getEntityBySQL(final String sql, final Object[] params) {
		return (T) this.hibernateTemplate.execute(new HibernateCallback(){
			@Override
			public T doInHibernate(Session session) throws HibernateException {
				Query query = session.createSQLQuery(sql).addEntity(clazz);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				
				List<T> ss =query.list();
				if(ss!=null&&ss.size()!=0){
					return  ss.get(0);
				}else {
					return null;
				}
			}
		});
	}


}
