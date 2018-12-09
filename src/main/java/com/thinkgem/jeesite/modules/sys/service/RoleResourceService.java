/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleResourceDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.RoleResource;

/**
 * 角色资源关系Service
 * @author waile23
 * @version 2018-04-30
 */
@Service
@Transactional(readOnly = true)
public class RoleResourceService extends CrudService<RoleResourceDao, RoleResource> {

	@Autowired
	private RoleDao roleDao;
	
	public RoleResource get(String id) {
		return super.get(id);
	}
	
	public List<RoleResource> findList(RoleResource roleResource) {
		return super.findList(roleResource);
	}
	
	public Page<RoleResource> findPage(Page<RoleResource> page, RoleResource roleResource) {
		return super.findPage(page, roleResource);
	}
	
	@Transactional(readOnly = false)
	public void save(RoleResource roleResource) {
		super.save(roleResource);
	}
	
	@Transactional(readOnly = false)
	public void delete(RoleResource roleResource) {
		super.delete(roleResource);
	}
	
	/**
	 * 根据资源id获取角色列表
	 * @param resourceId
	 * @return
	 */
	public List<Role> findRoleListByResourceId(String resourceId){
		if(StringUtils.isBlank(resourceId)){
			return null;
		}
		RoleResource roleResource = new RoleResource();
		roleResource.setResourceId(resourceId);
		List<RoleResource> list = this.findList(roleResource);
		
		String roleIds = StringUtils.EMPTY;
		Map<String, String> paramMap = new HashMap<String, String>();
		if(list != null && list.size() > 0){
			for(RoleResource rr : list){
				roleIds += rr.getRole().getId() + ",";
			}
			roleIds = roleIds.replace(",", "','");
			paramMap.put("roleIds", roleIds);
		}
		
		return roleDao.findListByRoleIds(paramMap);
	}
	
	/**
	 * 根据资源id删除资源与角色的对应关系
	 * @param resourceId
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void deleteByResourceId(String resourceId) throws Exception{
		if(StringUtils.isBlank(resourceId)){
			throw new Exception("资源信息不存在！");
		}
		dao.deleteByResourceId(resourceId);
	}
	
	/**
	 * 保存资源与角色关联数据
	 * @param resourceId
	 * @param roleIds
	 */
	@Transactional(readOnly = false)
	public void saveRoleResource(String resourceId, String roleIds){
		if(StringUtils.isBlank(roleIds)){
			return;
		}
		
		String roleIdArray[] = roleIds.split(",");
		if(roleIdArray != null && roleIdArray.length > 0){
			for(int index=0; index<roleIdArray.length; index++){
				RoleResource roleResource = new RoleResource();
				roleResource.preInsert();
				roleResource.setIsNewRecord(true);
				roleResource.setResourceId(resourceId);
				Role role = roleDao.get(roleIdArray[index]);
				roleResource.setRole(role);
 				roleResource.setStatus(1);
 				roleResource.setSort(0);
 				this.save(roleResource);
			}
		}
	}
	
	/**
	 * 根据资源id和角色id删除资源角色关联关系数据
	 * @param resourceId
	 * @param roleIds
	 */
	@Transactional(readOnly = false)
	public void deleteRoleResource(String resourceId, String roleId){
		if(StringUtils.isBlank(resourceId) || StringUtils.isBlank(roleId)){
			return;
		}
		
		dao.deleteByResourceIdAndRoleId(resourceId, roleId);
	}
}