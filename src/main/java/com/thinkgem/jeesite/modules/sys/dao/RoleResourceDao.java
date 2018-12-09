/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.RoleResource;

/**
 * 角色资源关系DAO接口
 * @author waile23
 * @version 2018-04-30
 */
@MyBatisDao
public interface RoleResourceDao extends CrudDao<RoleResource> {
	
	/**
	 * 根据资源id删除资源角色对应关系
	 * @param resourceId
	 */
	public void deleteByResourceId(String resourceId);
	
	/**
	 * 根据资源id和角色id删除资源角色关系数据
	 * @param resourceId
	 * @param roleId
	 */
	public void deleteByResourceIdAndRoleId(@Param("resourceId")String resourceId, @Param("roleId")String roleId);
}