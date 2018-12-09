/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 角色资源关系Entity
 * @author waile23
 * @version 2018-04-30
 */
public class RoleResource extends DataEntity<RoleResource> {
	
	private static final long serialVersionUID = 1L;
	private String roleId;		// 角色编码
	private String resourceId;		// 资源编码
	private Integer sort;		// 排序
	private Integer status;		// 启用状态 0:不可用 1:可用
	private Role role; // 角色对象
	
	public RoleResource() {
		super();
	}

	public RoleResource(String id){
		super(id);
	}

	@Length(min=1, max=64, message="角色编码长度必须介于 1 和 64 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	@Length(min=1, max=64, message="资源编码长度必须介于 1 和 64 之间")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}