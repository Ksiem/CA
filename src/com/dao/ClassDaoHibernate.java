package com.dao;
import org.springframework.stereotype.Repository;

import com.bean.SingleClass;
@Repository("ClassDao")
public class ClassDaoHibernate extends
	GenericDaoHibernate<SingleClass, String> implements ClassDao{
}
