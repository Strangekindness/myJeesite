/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Resource;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.service.ResourceService;
import com.thinkgem.jeesite.modules.sys.service.RoleResourceService;

/**
 * 资源管理Controller
 * @author waile23
 * @version 2018-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/resource")
public class ResourceController extends BaseController {

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private RoleResourceService roleResourceService;
	
	@ModelAttribute
	public Resource get(@RequestParam(required=false) String id) {
		Resource entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourceService.get(id);
		}
		
		if (entity == null){
			entity = new Resource();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:resource:view")
	@RequestMapping(value = {"list", ""})
	public String list(Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Resource> page = resourceService.findPage(new Page<Resource>(request, response), resource); 
		model.addAttribute("page", page);
		return "modules/sys/resourceList";
	}

	@RequiresPermissions("sys:resource:view")
	@RequestMapping(value = "form")
	public String form(Resource resource, Model model) {
		model.addAttribute("resource", resource);
		model.addAttribute("roleList", roleResourceService.findRoleListByResourceId(resource.getId()));
		return "modules/sys/resourceForm";
	}

	@RequiresPermissions("sys:resource:edit")
	@RequestMapping(value = "save")
	public String save(Resource resource, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, resource)){
			return form(resource, model);
		}
		resourceService.save(resource);
		model.addAttribute("resource", resource);
		model.addAttribute("roleList", roleResourceService.findRoleListByResourceId(resource.getId()));
		return "modules/sys/resourceForm";
	}
	
	@RequiresPermissions("sys:resource:edit")
	@RequestMapping(value = "delete")
	public String delete(Resource resource, RedirectAttributes redirectAttributes) {
		resourceService.delete(resource);
		addMessage(redirectAttributes, "删除资源管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/resource/?repage";
	}
	
	/**
	 * 删除资源管理信息和资源角色关系数据
	 * @param resource
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sys:resource:edit")
	@RequestMapping(value = "deleteResource")
	public String deleteResource(Resource resource, RedirectAttributes redirectAttributes) throws Exception {
		resourceService.deleteResource(resource);
		addMessage(redirectAttributes, "删除资源管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/resource/?repage";
	}
	
	/**
	 * 为资源分配角色
	 * @param resource
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "assignRole")
	public String assignRole(Resource resource, Model model) throws Exception {
		List<Role> selectedRoleList =  roleResourceService.findRoleListByResourceId(resource.getId());
		model.addAttribute("roleList", resourceService.findAllRoleList());
		//获取被选角色列表
		model.addAttribute("selectedRoleList", selectedRoleList);
		return "modules/sys/assignRoleToResource";
	}
	
	/**
	 * 保存被指派的角色信息
	 * @param resource
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveAssignedRole")
	public String saveAssignedRole(Resource resource, Model model) throws Exception {
		//删除原有的资源与角色的对应关系数据
		roleResourceService.deleteByResourceId(resource.getId());
		//保存资源与角色的对应关系
		roleResourceService.saveRoleResource(resource.getId(), resource.getRoleIds());
		//获取用户列表
		model.addAttribute("roleList", roleResourceService.findRoleListByResourceId(resource.getId()));
		return "modules/sys/resourceForm";
	}
	
	/**
	 * 根据资源id和角色id删除资源角色关联关系数据
	 * @param resource
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deleteRoleResource")
	public String deleteRoleResource(Resource resource, Model model){
		//保存资源与角色的对应关系
		roleResourceService.deleteRoleResource(resource.getId(), resource.getRoleIds());
		//获取用户列表
		model.addAttribute("roleList", roleResourceService.findRoleListByResourceId(resource.getId()));
		return "modules/sys/resourceForm";
	}
}