package com.thinkgem.jeesite.common.persistence;

import java.io.Serializable;

/**
 * ResponseResult
 * Restful api 统一返回数据对象
 * @author waile23
 * @version 2017-11-12
 */
public class ResponseResult implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Integer code;

	protected String message;

	protected Object data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 构造函数
	 */
	public ResponseResult() {
	}

	public ResponseResult(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 返回成功值
	 * 
	 * @return
	 */
	public static ResponseResult success() {
		ResponseResult result = new ResponseResult();
		result.setResultCode(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 返回成功值
	 * 
	 * @param data
	 * @return
	 */
	public static ResponseResult success(Object data) {
		ResponseResult result = new ResponseResult();
		result.setResultCode(ResultCode.SUCCESS);
		result.setData(data);
		return result;
	}

	/**
	 * 返回错误值
	 * 
	 * @param resultCode
	 * @return
	 */
	public static ResponseResult failure(ResultCode resultCode) {
		ResponseResult result = new ResponseResult();
		result.setResultCode(resultCode);
		return result;
	}
	
	/**
	 * 对于不清楚哪类错误的情况，传入message
	 * @param message
	 * @return
	 */
	public static ResponseResult failure(String message) {
		ResponseResult result = new ResponseResult(ResultCode.FAILED.code(), message);
		return result;
	}

	/**
	 * 返回错误值以及数据
	 * 
	 * @param resultCode
	 * @param data
	 * @return
	 */
	public static ResponseResult failure(ResultCode resultCode, Object data) {
		ResponseResult result = new ResponseResult();
		result.setResultCode(resultCode);
		result.setData(data);
		return result;
	}
	
	/**
	 * 返回错误值以及数据
	 * 
	 * @param resultCode
	 * @param data
	 * @return
	 */
	public static ResponseResult failure(String message, Object data) {
		ResponseResult result = new ResponseResult(ResultCode.FAILED.code(), message);
		result.setData(data);
		return result;
	}	

	/**
	 * 枚举复制给code和message
	 * 
	 * @param code
	 */
	public void setResultCode(ResultCode code) {
		this.code = code.code();
		this.message = code.message();
	}

}
