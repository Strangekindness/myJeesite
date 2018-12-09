/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 系统参数表Entity
 * @author waile23
 * @version 2018-02-14
 */
public class Param extends DataEntity<Param> {
	
	private static final long serialVersionUID = 1L;
	private String paramCategoryTypeCode;		// 参数分类代码
	private String paramCategoryTypeName;		// 参数分类名称(冗余)
	private String paramCnName;		// 参数中文名
	private String paramKey;		// 参数键
	private String paramValue;		// 参数值
	private String description;		// 参数说明
	private Integer sort;		// 排序
	private Integer status;		// 启用状态 0:不可用 1:可用
	
	public Param() {
		super();
		this.status = 1;
	}

	public Param(String id){
		super(id);
	}

	@Length(min=1, max=64, message="参数分类代码长度必须介于 1 和 64 之间")
	public String getParamCategoryTypeCode() {
		return paramCategoryTypeCode;
	}

	public void setParamCategoryTypeCode(String paramCategoryTypeCode) {
		this.paramCategoryTypeCode = paramCategoryTypeCode;
	}
	
	@Length(min=1, max=64, message="参数分类名称(冗余)长度必须介于 1 和 64 之间")
	public String getParamCategoryTypeName() {
		return paramCategoryTypeName;
	}

	public void setParamCategoryTypeName(String paramCategoryTypeName) {
		this.paramCategoryTypeName = paramCategoryTypeName;
	}
	
	@Length(min=1, max=200, message="参数中文名长度必须介于 1 和 200 之间")
	public String getParamCnName() {
		return paramCnName;
	}

	public void setParamCnName(String paramCnName) {
		this.paramCnName = paramCnName;
	}
	
	@Length(min=1, max=100, message="参数键长度必须介于 1 和 100 之间")
	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	
	@Length(min=1, max=100, message="参数值长度必须介于 1 和 100 之间")
	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	@Length(min=1, max=255, message="参数说明长度必须介于 1 和 255 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}