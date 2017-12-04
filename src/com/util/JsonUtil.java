package com.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bean.OperateResult;
import com.bean.Salary;

import com.bean.SingleClass;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * 生成Json格式数据的工具类
 * tangwei
 *
 */
public class JsonUtil {
	private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	/**
	 * 针对easyui-datagrid所需的数据格式，以servlet生成json数据
	 * @param total
	 * @param rows
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void writeJson(Integer total, List rows) {
		Map m = new HashMap();
		m.put("total", total);
		m.put("rows", rows);
		try {
			ServletActionContext.getResponse().setHeader("content-type", "text/html; charset=UTF-8");
			ServletActionContext.getResponse().getWriter().print(gson.toJson(m));
			System.out.println(gson.toJson(m));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 针对操作结果的数据格式，以servlet生成json数据
	 * @param r
	 */
	public static void writeJson(SingleClass c) {
		try {
			ServletActionContext.getResponse().setHeader("content-type", "text/html; charset=UTF-8");
			ServletActionContext.getResponse().getWriter().print(gson.toJson(c));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeJson(boolean f) {
		try {
			ServletActionContext.getResponse().setHeader("content-type", "text/html; charset=UTF-8");
			ServletActionContext.getResponse().getWriter().print(gson.toJson(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeJson(OperateResult r) {
		try {
			ServletActionContext.getResponse().setHeader("content-type", "text/html; charset=UTF-8");
			ServletActionContext.getResponse().getWriter().print(gson.toJson(r));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeJson(List<SingleClass> r) {
		try {
			ServletActionContext.getResponse().setHeader("content-type", "text/html; charset=UTF-8");
			ServletActionContext.getResponse().getWriter().print(gson.toJson(r));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * json 转 List<T>
     */
    public static List<SingleClass> jsonToList(String jsonString) {
        @SuppressWarnings("unchecked")
        List<SingleClass> ts = (List<SingleClass>) gson.fromJson(jsonString, new TypeToken<List<SingleClass>>() {}.getType());
        return ts;
    }
    
    public static  Object jsonToObject(String jsonString) {
        Object o =  gson.fromJson(jsonString, new TypeToken<Object>() {}.getType());
        return o;
    }
    
    public static  SingleClass jsonToClass(String jsonString) {
    	SingleClass o =  gson.fromJson(jsonString, SingleClass.class);
        return o;
    }

    public static void writeJson(Salary s) {
		try {
			ServletActionContext.getResponse().setHeader("content-type", "text/html; charset=UTF-8");
			ServletActionContext.getResponse().getWriter().print(gson.toJson(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public static void writeJson(String str) {
		try {
			ServletActionContext.getResponse().setHeader("content-type", "text/html; charset=UTF-8");
			ServletActionContext.getResponse().getWriter().print(gson.toJson(str));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
