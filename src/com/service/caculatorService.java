package com.service;

import java.util.List;
import com.bean.SingleClass;
import com.bean.Salary;

/**
 * 实际课时费计算类，需要数据库降序查询返回的数据源支持
 * @author Ksiem
 *
 */
public class caculatorService {
	private static Integer subsidyNum = 5;
	private double classHours;
	private double sum;
	private double subsidySum;
	private Salary sa;
	public Salary caulate(List<SingleClass> classlist){
		classHours = 0;
		sum = 0;
		subsidySum = 0;
		//获取单人单月的课程统计list step1.遍历检查总课时数目是否大于35，是则计算课时费，否则课时费为零
		for(SingleClass c:classlist){
			classHours += c.getClassHours();
			//计算单科补贴
			c.setSubsidy(c.getStuNum()>2?(c.getStuNum()-2)*subsidyNum*c.getClassHours():0);
			subsidySum += c.getSubsidy();
		}
		if(classHours>35.0){
			//计算额外课时费
			//数据源 按照单科课时费 从大到小排列 ——这步由数据库实现计算课时费多的
			
			//方式一 从多的开始计算
//			double resthours = classHours-35;
//			for(Class c:DescList(classlist)){
//				if(resthours<c.getClassHours()||resthours==c.getClassHours()){//有效课时小于最高课时费科目的总课时 时，按照单科计算总课时费
//					sum += resthours *(c.getPay());
//					break;
//				} else{//有效课时大于最高课时费科目的总课时 时，需要依次减去对应单科的课时，直到小于谋科的总课时
//					sum += c.getClassHours()*c.getPay();
//					resthours -= c.getClassHours();
//				}
//			}
			//方式二 从小的开始计算
			int det = 35;
			for(SingleClass c:classlist){
				
				//从最小课时开始减
				if(c.getClassHours()<det||c.getClassHours()==det){
					det -=  c.getClassHours();
				} else {
					//在不断的从便宜课时开始剔除 循环以后——出现有效课时了
//					这次计算要扣除剩余的det时间
					sum += (c.getClassHours()-det)*c.getPay_by_hour();
					System.out.println("该课"+c.getGrade()+"拥有"+(c.getClassHours()-det)+"个有效课时，该科课时费为："+(c.getClassHours()-det)*c.getPay_by_hour());
					det = 0;
					
				}
			}
			
		} else{
			System.out.println("总课时小于或等于35，不计算课时费");
			sum = 0;
		}
		
		//计算人头补贴
		System.out.println("20XX年XX月  某人当月有效课时费为："+sum+"，有效课时补贴费为："+subsidySum);
		sa = new Salary(sum,subsidySum);
		return sa;
	}

}
