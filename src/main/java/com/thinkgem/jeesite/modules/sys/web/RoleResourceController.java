/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

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
import com.thinkgem.jeesite.modules.sys.entity.RoleResource;
import com.thinkgem.jeesite.modules.sys.service.RoleResourceService;

/**
 * 角色资源关系Controller
 * @author waile23
 * @version 2018-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/roleResource")
public class RoleResourceController extends BaseController {

	@Autowired
	private RoleResourceService roleResourceService;
	
	@ModelAttribute
	public RoleResource get(@RequestParam(required=false) String id) {
		RoleResource entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = roleResourceService.get(id);
		}
		if (entity == null){
			entity = new RoleResource();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:roleResource:view")
	@RequestMapping(value = {"list", ""})
	public String list(RoleResource roleResource, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RoleResource> page = roleResourceService.findPage(new Page<RoleResource>(request, response), roleResource); 
		model.addAttribute("page", page);
		return "modules/sys/roleResourceList";
	}

	@RequiresPermissions("sys:roleResource:view")
	@RequestMapping(value = "form")
	public String form(RoleResource roleResource, Model model) {
		model.addAttribute("roleResource", roleResource);
		return "modules/sys/roleResourceForm";
	}

	@RequiresPermissions("sys:roleResource:edit")
	@RequestMapping(value = "save")
	public String save(RoleResource roleResource, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, roleResource)){
			return form(roleResource, model);
		}
		roleResourceService.save(roleResource);
		addMessage(redirectAttributes, "保存角色资源关系成功");
		return "redirect:"+Global.getAdminPath()+"/sys/roleResource/?repage";
	}
	
	@RequiresPermissions("sys:roleResource:edit")
	@RequestMapping(value = "delete")
	public String delete(RoleResource roleResource, RedirectAttributes redirectAttributes) {
		roleResourceService.delete(roleResource);
		addMessage(redirectAttributes, "删除角色资源关系成功");
		return "redirect:"+Global.getAdminPath()+"/sys/roleResource/?repage";
	}
}