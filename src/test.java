import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.action.SalaryAction;
import com.bean.Salary;
import com.bean.SingleClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.util.ExportExcel;


public class test {
	private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
	}

}
