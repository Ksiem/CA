package com.action;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.bean.OperateResult;
import com.bean.SingleClass;
import com.bean.Salary;
import com.dao.Pagination;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionSupport;
import com.service.caculatorService;
import com.service.classDaoService;
import com.service.salaryDaoService;
import com.util.DateUtil;
import com.util.JsonUtil;
@SuppressWarnings("serial")
@Controller("classCountAction")
@Scope("prototype")
public class ClassCountAction extends ActionSupport{
	private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	private classDaoService ts = new classDaoService();
	private salaryDaoService ss = new salaryDaoService();
	//Ajax传参
	private String addClass;
	private String delClass;
	private String modClass;
	private List<SingleClass> saveList = new ArrayList<SingleClass>();
	private List<SingleClass> delList = new ArrayList<SingleClass>();
	private List<SingleClass> modList = new ArrayList<SingleClass>();
	private String addTeacher;
	private String addDate; 
	private SingleClass row;
	private Integer queryId;
	private String pageIndex;
	private String pageSize;
	
	private String caTeacher;
	private String caDate;
	
	public classDaoService getTs() {
		return ts;
	}
	@Resource
	public void setTs(classDaoService ts) {
		this.ts = ts;
	}
	public salaryDaoService getSs() {
		return ss;
	}
	@Resource
	public void setSs(salaryDaoService ss) {
		this.ss = ss;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	//TODO：忘记做分页了,请求时增加size和pageNumber两个字段
	public void queryClass(){	
		try {
			Pagination p = ts.queryByTeaAndDate(addTeacher,addDate,pageSize == null ? 10 : Integer.parseInt(pageSize), pageIndex == null ? 1 : Integer.parseInt(pageIndex)+1);
			System.out.println("pageIndex:"+pageIndex+",size:"+pageSize);
			Integer total = p.getTotalCount();
			List<SingleClass> rows = p.getList();
			System.out.println("total："+total);
			for(SingleClass c:rows){
				System.out.println(c.toString());
			}
			JsonUtil.writeJson(total, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void queryById(){
		SingleClass c = new SingleClass();
		try {
			c = ts.queryById(queryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonUtil.writeJson(c);
	}
	public void saveClass(){
		OperateResult result = new OperateResult();
		if(addClass!=null&&!addClass.equals("[]")){
			System.out.println("saveClass-addClass:"+getAddClass());
			try {
				row = JsonUtil.jsonToList(addClass).get(0);
				ts.updateClass(row);
				result.setSuccess(true);
				result.setResult("课时录入成功");
			} catch (Exception e) {
				result.setSuccess(false);
				result.setResult("课时录入失败");
				e.printStackTrace();
			}
		}
		JsonUtil.writeJson(result);
	}
	
	//批量保存页面 录入 的课程信息
	public void saveClassList(){
		boolean flag = true;
		StringBuilder str = new StringBuilder();
		OperateResult result = new OperateResult();
		if(addClass!=null&&!addClass.equals("[]")){
			try{
				saveList = JsonUtil.jsonToList(addClass);
				if(ts.listSave(saveList)){
					str.append("新增课程信息成功;");
				}
			}catch(Exception e){
				flag = false;
				str.append("新增课程信息失败;");
				e.printStackTrace();
			}	
		}
		if(modClass!=null&&!modClass.equals("[]")){
			//批量保存
			try {
				modList = JsonUtil.jsonToList(modClass);
				if(ts.listMod(modList)){
					str.append("修改课程信息成功;");
				}
			} catch (Exception e) {
				flag = false;
				str.append("修改课程信息失败;");
				e.printStackTrace();
			}	
		}
		if(delClass!=null&&!delClass.equals("[]")){
			//批量保存
			try {
				delList = JsonUtil.jsonToList(delClass);
				if(ts.listDel(delList)){	
					str.append("删除课程信息成功;");
				}
			} catch (Exception e) {
				flag = false;
				str.append("删除课程信息失败;");
				e.printStackTrace();
			}	
		}
		result.setSuccess(flag);
		result.setResult(str.toString());
		JsonUtil.writeJson(result);
		
	}
	

	public void deleteClass(){
		OperateResult result = new OperateResult();
		try {
			row = JsonUtil.jsonToClass(getDelClass());
			ts.deleteClass(row);
			result.setSuccess(true);
			result.setResult("删除课程信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(true);
			result.setResult("删除课程信息失败");
		}	
		JsonUtil.writeJson(result);
		System.out.println("deleteRow:"+gson.toJson(result));
	}
	
	/*计算课时方法*/
	public void caculator(){
		System.out.println(caTeacher+":"+caDate);
		//获取当月month of year该teacher的所有课时记录，按照pay_by_hour 升序或者降序盘列返回
		List<SingleClass> lc = ts.getOrderList(caTeacher,caDate);
		if(lc.size()==0){
			String errorText = "查询不到该月相关的课时数据，请先录入课时！";
			JsonUtil.writeJson(errorText); 
			return ;
		}
		//调用Service.caculator()方法，算出总课时费，总补贴
		caculatorService cs = new caculatorService();
		Salary sa = cs.caulate(lc);
		sa.setTeacher(caTeacher);
		sa.setDate(caDate);
		//如果salary表已存在该teacher+date的记录，则更新，否则在salary表生成一条新的记录,返回新记录信息，并显示在页面上
		Salary ex = ss.getExistSalary(sa);
		if(ex!=null){
			ex.setSum(sa.getSum());
			ex.setSubsidy(sa.getSubsidy());
			System.out.println(ex.toString());
			ss.updateSalary(ex);
			JsonUtil.writeJson(ex);
		} else{
			System.out.println(sa.toString());
			ss.updateSalary(sa);
			JsonUtil.writeJson(sa); 
		}	
	}
	
	
	
	
	
	public List<SingleClass> getSaveList() {
		return saveList;
	}
	public void setSaveList(List<SingleClass> saveList) {
		this.saveList = saveList;
	}
	public SingleClass getrow() {
		return row;
	}
	public void setrow(SingleClass row) {
		this.row = row;
	}
	public String getAddTeacher() {
		return addTeacher;
	}
	public void setAddTeacher(String addTeacher) {
		this.addTeacher = addTeacher;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getCaTeacher() {
		return caTeacher;
	}
	public void setCaTeacher(String caTeacher) {
		this.caTeacher = caTeacher;
	}
	public String getCaDate() {
		return caDate;
	}
	public void setCaDate(String caDate) {
		this.caDate = caDate;
	}
	
	public String getDelClass() {
		return delClass;
	}
	public void setDelClass(String delClass) {
		this.delClass = delClass;
	}
	public String getAddClass() {
		return addClass;
	}
	public void setAddClass(String addClass) {
		this.addClass = addClass;
	}
	public String getModClass() {
		return modClass;
	}
	public void setModClass(String modClass) {
		this.modClass = modClass;
	}
	public Integer getQueryId() {
		return queryId;
	}
	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
}
