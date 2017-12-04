package com.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * 课时类总类  有效属性为对应年级grade的价格pay，以及根据classHours计算得出的最终课时费
 * 初步计算  课时费 = pay*classhours
 * @author Ksiem
 *
 */
@Entity
@Table(name = "t_class")
public class SingleClass {
	//private static Integer subsidyNum = 5;//每小时有效人头补贴
	@Id
	@Expose()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Expose()
	private String teacher;//执教老师
	@Expose()
	private String month_of_year;//年月日时间戳
	@Expose()
	private String grade;//年级
	@Expose()
	private Integer classhours;//上课小时数
//	@Expose()
//	private Integer pay;//计算课时费+补贴的总报酬
	@Expose()
	private Integer pay_by_hour;//该课程每小时报酬
	@Expose()
	private Integer stuNum;//该课学生数目
	@Expose()
	private Integer subsidy;//该课程人头补贴数额
	
	public SingleClass(){
		
	}
	
//	public Class(String grade,Integer classhours,Integer pay,Integer stuNum){
//		this.grade = grade;
//		this.classhours = classhours;
//		this.pay = pay;
//		this.stuNum = stuNum;
//		this.subsidy = stuNum>2?(stuNum-2)*subsidyNum*classhours:0;
//	}
	
	@Column(name = "stuNum", nullable = true, length = 10)
	public Integer getStuNum() {
		return stuNum;
	}
	public void setStuNum(Integer stuNum) {
		this.stuNum = stuNum;
	}
	@Column(name = "subsidy", nullable = true, length = 20)
	public Integer getSubsidy() {
		return subsidy;
	}
	public void setSubsidy(Integer subsidy){
		this.subsidy = subsidy;
	}
	@Column(name = "id",unique = true,nullable = false, length = 20)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "grade", nullable = true, length = 30)
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
//	@Column(name = "pay", nullable = true, length = 11)
//	public Integer getPay() {
//		return pay;
//	}
//	public void setPay(Integer pay) {
//		this.pay = pay;
//	}
	@Column(name = "classhours", nullable = true, length = 20)
	public Integer getClassHours() {
		return classhours;
	}
	public void setClassHours(Integer classhours) {
		this.classhours = classhours;
	}
	@Column(name = "teacher", nullable = true, length = 30)
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	@Column(name = "month_of_year", nullable = true, length = 30)
	public String getMonth_of_year() {
		return month_of_year;
	}
	public void setMonth_of_year(String month_of_year) {
		this.month_of_year = month_of_year;
	}
	@Column(name = "pay_by_hour", nullable = true, length = 20)
	public Integer getPay_by_hour() {
		return pay_by_hour;
	}
	public void setPay_by_hour(Integer pay_by_hour) {
		this.pay_by_hour = pay_by_hour;
	}
    /*******************非Dao相关方法********************/
	@Override
	public String toString() {
		return "Class [id=" + id + ", teacher=" + teacher + ", month_of_year="
				+ month_of_year + ", grade=" + grade + ", classhours="
				+ classhours + ", pay_by_hour=" + pay_by_hour + ", stuNum="
				+ stuNum + ", subsidy=" + subsidy + "]";
	}
	
}
