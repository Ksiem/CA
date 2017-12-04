package com.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/*
 * 结算类
 * 月结课时费+补贴费 ps为备注 对应teacher与date 作为鉴别与查找的依据
 * */
@Entity
@Table(name = "t_salary")
public class Salary {
	/*月结课时费+补贴费 ps为备注*/
	@Id
	@Expose()
	private int id;
	@Expose()
	private double sum;
	@Expose()
	private double subsidy;
	@Expose()
	private String teacher;
	@Expose()
	private String date;
	@Expose()
	private String ps;
	
	public Salary(){
		
	}
	
	public Salary(double sum,double subsidy){
		this.sum = sum;
		this.subsidy = subsidy;
	}
	@Column(name = "sum", nullable = false, length = 20)
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	@Column(name = "subsidy", nullable = false, length = 20)
	public double getSubsidy() {
		return subsidy;
	}
	public void setSubsidy(double subsidy) {
		this.subsidy = subsidy;
	}
	@Column(name = "ps", nullable = true, length = 64)
	public String getPs() {
		return ps;
	}
	public void setPs(String ps) {
		this.ps = ps;
	}
	@Column(name = "date", nullable = false, length = 32)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	@Column(name = "teacher", nullable = false, length = 32)
	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	@Column(name = "id", nullable = false, length = 10)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "salary [sum=" + sum + ", subsidy=" + subsidy + ", teacher="
				+ teacher + ", date=" + date + "]";
	}
	
	
}
