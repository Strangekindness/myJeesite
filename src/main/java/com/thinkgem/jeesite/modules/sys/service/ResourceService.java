/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.GlobalConst;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.ResourceDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.entity.Resource;
import com.thinkgem.jeesite.modules.sys.entity.Role;

/**
 * 资源管理Service
 * @author waile23
 * @version 2018-04-30
 */
@Service
@Transactional(readOnly = true)
public class ResourceService extends CrudService<ResourceDao, Resource> {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	RoleResourceService roleResourceService;
	
	public Resource get(String id) {
		if(StringUtils.isBlank(id)){
			return new Resource();
		}
		Resource resource = (Resource)CacheUtils.get(GlobalConst.RESOURCE_CACHE, GlobalConst.RESOURCE_CACHE_ID_ + id);
		if (resource == null){
			resource = super.get(id);
			CacheUtils.put(GlobalConst.RESOURCE_CACHE, GlobalConst.RESOURCE_CACHE_ID_ + id, resource);
		}
		return resource;
	}
	
	public List<Resource> findList(Resource resource) {
		return super.findList(resource);
	}
	
	public Page<Resource> findPage(Page<Resource> page, Resource resource) {
		return super.findPage(page, resource);
	}
	
	@Transactional(readOnly = false)
	public void save(Resource resource) {
		super.save(resource);
		//修改时需要清空缓存
		CacheUtils.removeAll(GlobalConst.RESOURCE_CACHE);
	}
	
	@Transactional(readOnly = false)
	public void delete(Resource resource) {
		super.delete(resource);
		//删除时需要清空缓存
		CacheUtils.removeAll(GlobalConst.RESOURCE_CACHE);
	}
	
	/**
	 * 删除资源信息和角色资源关系信息
	 * @param resource
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public void deleteResource(Resource resource) throws Exception {
		//删除角色资源关系数据
		roleResourceService.deleteByResourceId(resource.getId());
		//删除资源数据
		this.delete(resource);
	}
	
	/**
	 * 从缓存中获取所有的资源列表
	 * @return
	 */
	public List<Resource> findAllList() {
		List<Resource> resourceList = (List<Resource>)CacheUtils.get(GlobalConst.RESOURCE_CACHE, GlobalConst.RESOURCE_LIST);
		if (resourceList == null){
			resourceList = dao.findAllList(new Resource());
			CacheUtils.put(GlobalConst.RESOURCE_CACHE, GlobalConst.RESOURCE_LIST, resourceList);
		}
		return resourceList;
	}
	
	/**
	 * 获取所有角色的列表
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public List<Role> findAllRoleList() throws Exception{
		return roleDao.findAllList(new Role());
	}
}