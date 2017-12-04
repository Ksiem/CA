package com.service;



import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.dao.ClassDao;
import com.dao.Pagination;
import com.bean.SingleClass;
@Service 
public class classDaoService {
	private ClassDao classDao;
	@Resource
	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}
	//增删改查
	public void insertClass(SingleClass c){
		try {
			this.classDao.save(c);
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}

	public void deleteClass(SingleClass c){
		try {
			this.classDao.delete(c);
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}
	
	public void updateClass(SingleClass c){
		try {
			this.classDao.update(c);
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}
	
	public boolean listSave(List<SingleClass> list) {
		return classDao.saveEntities(list);
	}
	public boolean listMod(List<SingleClass> list){
		return classDao.updateEntities(list);
	}
	public boolean listDel(List<SingleClass> list){
		return classDao.delEntities(list);
	}
	//通过id查询
	public SingleClass queryById(Integer id){
		String hql = "from SingleClass where 1=1 and id = ? ";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(id);
		SingleClass c=classDao.getEntityByHQL(hql,params.toArray());
		return c;
	}
	
//	单个课时录入页面 通过 特定课程名称 查询
	public Pagination queryByTeaAndDate(String teacher,String date,int pageSize,int pageNumber){
		StringBuilder hql = new StringBuilder("from SingleClass where 1=1 ");
		ArrayList<Object> params = new ArrayList<Object>();
		if(teacher!=null&&!teacher.equals("")){
			hql.append(" and teacher = ? "); 
			params.add(teacher);
		}
		if(date!=null&&!date.equals("")){
			hql.append(" and month_of_year = ? "); 
			params.add(date);
		}
		
		Pagination p = classDao.listEntityByHQL(hql.toString(), params.toArray(), pageSize, pageNumber);
		return p;
	}
	
	public Pagination queryAll(String teacher,String date,int pageSize,int pageNumber){
		String hql = new String("from SingleClass where 1=1 and teacher=? and month_of_year=?");
		String[] params ={teacher,date};
		Pagination p = classDao.listEntityByHQL(hql, params, pageSize, pageNumber);
		return p;
	}
	
	
	public List<SingleClass> getOrderList(String teacher,String month){
		StringBuilder hql = new StringBuilder("from SingleClass where teacher=? and month_of_year=? order by pay_by_hour");
		String[] params ={teacher,month};
		List<SingleClass> lc= classDao.listEntityByHQL(hql.toString(), params);
		return lc;
	}
	
}
