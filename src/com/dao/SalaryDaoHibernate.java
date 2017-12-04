package com.dao;

import org.springframework.stereotype.Repository;
import com.bean.Salary;

@Repository("SalaryDao")
public class SalaryDaoHibernate extends GenericDaoHibernate<Salary, String> implements SalaryDao{

}
