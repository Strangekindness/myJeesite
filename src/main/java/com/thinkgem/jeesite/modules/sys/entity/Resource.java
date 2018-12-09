/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 资源管理Entity
 * @author waile23
 * @version 2018-04-30
 */
public class Resource extends DataEntity<Resource> {
	
	private static final long serialVersionUID = 1L;
	private String resourceTypeCode;		// 资源类型 :1系统、2业务
	private String name;		// 资源名称
	private String url;		// 资源连接
	private String httpMethod;		// 提交方式
	private Integer sort;		// 排序
	private Integer status;		// 启用状态 0:不可用 1:可用
	private String roleIds; // 指派的角色Id字符串
	
	public Resource() {
		super();
		this.status = 1;
		sort = 0;
	}

	public Resource(String id){
		super(id);
	}

	@Length(min=0, max=10, message="资源类型长度必须介于 0 和 10 之间")
	public String getResourceTypeCode() {
		return resourceTypeCode;
	}

	public void setResourceTypeCode(String resourceTypeCode) {
		this.resourceTypeCode = resourceTypeCode;
	}
	
	@Length(min=0, max=100, message="资源名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=200, message="资源连接长度必须介于 0 和 200 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=100, message="提交方式长度必须介于 0 和 100 之间")
	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
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

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
}