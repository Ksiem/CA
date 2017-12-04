package com.dao;

import org.springframework.stereotype.Repository;
import com.dao.GenericDao;
import com.bean.SingleClass;

@Repository 
public interface ClassDao extends GenericDao<SingleClass, String>{
	
}
