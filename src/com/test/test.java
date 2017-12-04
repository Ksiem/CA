package com.test;
import java.util.ArrayList;
import java.util.List;

import com.bean.SingleClass;
import com.dao.ClassDao;
import com.dao.ClassDaoHibernate;
import com.service.caculatorService;
import com.service.classDaoService;
public class test {
	private List<SingleClass> cl = new ArrayList<SingleClass>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		new test().testData();
		ClassDaoHibernate cdh = new ClassDaoHibernate();
		classDaoService ts = new classDaoService();
		ts.setClassDao(cdh);
//		ts.insertClass(new Class("初三",16,45,2));
		
//		String grade = ts.queryClassByGrade("初三").getGrade();
//		System.out.println(grade);
	}
	
	public void testData(){
//			String grade,double classHours,int pay,int stuNum
//		Class cla3 = new Class("初三",16,45,2);
//		Class cla2 = new Class("初二",30,40,6);
//		Class cla1 = new Class("初一",30,40,3);
		
//		方式一 ：从课时费大的开始计算
//		cl.add(cla3);
//		cl.add(cla2);
//		cl.add(cla1);
//		方式二 ：从课时费小的开始计算
//		cl.add(cla1);
//		cl.add(cla2);
//		cl.add(cla3);
//		1125+1500； 25*40+45*16 1000   30*5+30*5*4=150+600
		//根据课时费大小排列查询返回后  调用计算类获取当月结算课时
//		caculatorService cs =new caculatorService();
//		cs.caulate(cl);
	}
}
