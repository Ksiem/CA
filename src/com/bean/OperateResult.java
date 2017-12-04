package com.bean;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;

/**
 * 存放操作结果的类
 * tangwei
 *
 */
@Component("operateResult")
public class OperateResult {
	@Expose() private Boolean success;
	@Expose() private String result;
	
	//保存数据库是否成功
	@Expose() private Boolean successSave;
	@Expose() private String resultSave;
	
	//成功的保单号
	@Expose() private String successCode;
	
	//失败的保单号
	@Expose() private String failCode;
	
	//规则库校验失败返回给前台的值
	@Expose() private String applyInfoMessage;
	
	public String getApplyInfoMessage() {
		return applyInfoMessage;
	}
	public void setApplyInfoMessage(String applyInfoMessage) {
		this.applyInfoMessage = applyInfoMessage;
	}
	public String getFailCode() {
		return failCode;
	}
	public void setFailCode(String failCode) {
		this.failCode = failCode;
	}
	
	public String getSuccessCode() {
		return successCode;
	}
	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
	}
	public Boolean getSuccessSave() {
		return successSave;
	}
	public void setSuccessSave(Boolean successSave) {
		this.successSave = successSave;
	}
	public String getResultSave() {
		return resultSave;
	}
	public void setResultSave(String resultSave) {
		this.resultSave = resultSave;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
