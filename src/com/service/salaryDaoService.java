package com.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bean.SingleClass;
import com.bean.Salary;
import com.dao.Pagination;
import com.dao.SalaryDao;
@Service 
public class salaryDaoService {
	private SalaryDao salaryDao;

	public SalaryDao getSalaryDao() {
		return salaryDao;
	}
	@Resource
	public void setSalaryDao(SalaryDao salaryDao) {
		this.salaryDao = salaryDao;
	}
	
	
	//增删改查
		public void insertSalary(Salary c){
			try {
				this.salaryDao.save(c);
			} catch (Exception e) {
				throw new RuntimeException(e.toString());
			}
		}

		public void deleteSalary(Salary c){
			try {
				this.salaryDao.delete(c);
			} catch (Exception e) {
				throw new RuntimeException(e.toString());
			}
		}
		
		public void updateSalary(Salary c){
			try {
				this.salaryDao.update(c);
			} catch (Exception e) {
				throw new RuntimeException(e.toString());
			}
		}
		public Salary getExistSalary(Salary s){
			Salary ex = null;
			String hql = new String("from Salary where teacher = ? and date = ? ");
			String[] params = {s.getTeacher(),s.getDate()};
			try {
				ex = salaryDao.getEntityByHQL(hql,params);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ex;
		}
		
		
		public boolean listSave(List<Salary> list) {
			return salaryDao.saveEntities(list);
		}
		public List<Salary> queryExport(String teacher,String date){
			StringBuilder hql = new StringBuilder(" from Salary where 1=1 ");
			ArrayList<Object> params = new ArrayList<Object>();
			if(teacher!=null&&!teacher.equals("")){
				hql.append(" and teacher like ? "); 
				params.add("%"+teacher+"%");
			}
			if(date!=null&&!date.equals("")&&date.length()==7){
				hql.append(" and date like ? "); 
				params.add("%"+date+"%");
			}
			return salaryDao.listEntityByHQL(hql.toString(),params.toArray());
		}
		
		public Pagination query(int pageSize,int pageNumber){
			String hql = new String("from Salary where 1=1 ");
			String[] params ={};
			Pagination p = salaryDao.listEntityByHQL(hql, params, pageSize, pageNumber);

			return p;
		}
		
		public Pagination queryByNameAndDate(String teacher,String date,int pageSize,int pageNumber){
			StringBuilder hql = new StringBuilder("from Salary where 1=1 ");
			ArrayList<Object> params = new ArrayList<Object>();
			if(teacher!=null&&!teacher.equals("")){
				hql.append(" and teacher like ? "); 
				params.add("%"+teacher+"%");
			}
			if(date!=null&&!date.equals("")&&date.length()==7){
				hql.append(" and date like ? "); 
				params.add("%"+date+"%");
			}
			Pagination p = salaryDao.listEntityByHQL(hql.toString(), params.toArray(), pageSize, pageNumber);
			return p;
		}
}
