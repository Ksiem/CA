package com.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.JOptionPane;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bean.OperateResult;
import com.bean.SingleClass;
import com.bean.Salary;
import com.dao.Pagination;
import com.opensymphony.xwork2.ActionSupport;
import com.service.salaryDaoService;
import com.util.ExportExcel;
import com.util.JsonUtil;

@SuppressWarnings("serial")
@Controller("salaryAction")
@Scope("prototype")
public class SalaryAction extends ActionSupport{
	private salaryDaoService ss = new salaryDaoService();
	public salaryDaoService getSs() {
		return ss;
	}
	@Resource
	public void setSs(salaryDaoService ss) {
		this.ss = ss;
	}

	//ajax传参
	private String queryname;
	private String querydate;
	
	private String pageIndex;
	private String pageSize;
	
	@SuppressWarnings("unchecked")
	public void querySalary(){
		try {
			Pagination p = ss.queryByNameAndDate(queryname,querydate,pageSize == null ? 10 : Integer.parseInt(pageSize), pageIndex == null ? 1 : Integer.parseInt(pageIndex)+1);
			Integer total = p.getTotalCount();
			List<Salary> rows = p.getList();
			
			System.out.println("total："+total);
			JsonUtil.writeJson(total, rows);
	
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	//返回导出的数据
	public void queryToExport(){
		OperateResult result = new OperateResult();
		ExportExcel<Salary> ex = new ExportExcel<Salary>();  
		 //构造标题列
		 String[] headers = {"id","课时费","补贴费","姓名","日期","备注"};
		 //String[] headers = {"id","姓名"};
		 //获取需要导出的数据
		 List<Salary> dataset = new ArrayList<Salary>();  
		 dataset = ss.queryExport(queryname,querydate); 
		 try {
//			 BufferedInputStream bis = new BufferedInputStream(  
//			         new FileInputStream("D://project/CA/WebRoot/WEB-INF/book.png"));  
//			 byte[] buf = new byte[bis.available()];  
//			 while ((bis.read(buf)) != -1)  
//			 {  
  
//			 }
			 
			/* 导出一般是整月导出，所以文件名为 ‘201X年XX月’,或者全部导出（意义不大），
			 具体导出时应该根据前置信息习题 所导出内容的限定范围 */
			 //StringBuilder filename = new StringBuilder();
			 StringBuilder exportName = new StringBuilder();
			 exportName.append("d://课时结算导出/");
			 System.out.println(exportName.toString());
			 if(queryname!=null){
				 exportName.append(queryname);
				 System.out.println(exportName.toString());
			 }
			 if(querydate!=null&&!querydate.equals("\"")){//空的querydate截取到的是一个双引号
				 exportName.append(querydate);
				 System.out.println(exportName.toString());
			 }
			 exportName.append("导出.xls");
			 System.out.println(exportName.toString());
			 File dic = new File("d://课时结算导出");
			 if(!dic.exists()){
				 dic.mkdir();
			 }
			 
			 OutputStream out = new FileOutputStream(exportName.toString()); 
			 ex.exportExcel(headers, dataset, out); 
			 out.close();  
			 //JOptionPane.showMessageDialog(null, "导出成功!");  
			 System.out.println("excel导出成功！");
			 result.setSuccess(true);
			 result.setResult("excel导出成功！");
		} catch (FileNotFoundException e) {
			result.setSuccess(false);
			result.setResult(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			result.setSuccess(false);
			result.setResult(e.getMessage());
			e.printStackTrace();
		} catch(Exception e){
			result.setSuccess(false);
			result.setResult(e.getMessage());
			e.printStackTrace();
		}
		JsonUtil.writeJson(result);
	}

	public String getQueryname() {
		return queryname;
	}

	public void setQueryname(String queryname) {
		this.queryname = queryname;
	}

	public String getQuerydate() {
		return querydate;
	}

	public void setQuerydate(String querydate) {
		this.querydate = querydate;
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
