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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Param;
import com.thinkgem.jeesite.modules.sys.service.ParamService;

/**
 * 系统参数表Controller
 * @author waile23
 * @version 2018-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/param")
public class ParamController extends BaseController {

	@Autowired
	private ParamService paramService;
	
	@ModelAttribute
	public Param get(@RequestParam(required=false) String id) {
		Param entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = paramService.get(id);
		}
		if (entity == null){
			entity = new Param();
		}
		return entity;
	}
	
	/**
	 * 通过参数类型编码获取参数列表（从缓存中获取）
	 * @param param
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:param:view")
	@RequestMapping(value = {"list", ""})
	public String list(Param param, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Param> list = paramService.findListByParamCategoryTypeCode(param.getParamCategoryTypeCode());
		model.addAttribute("list", list);
		return "modules/sys/paramList";
	}

	@RequiresPermissions("sys:param:view")
	@RequestMapping(value = "form")
	public String form(Param param, Model model) {
		model.addAttribute("param", param);
		return "modules/sys/paramForm";
	}

	@RequiresPermissions("sys:param:edit")
	@RequestMapping(value = "save")
	public String save(Param param, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, param)){
			return form(param, model);
		}
		paramService.saveParam(param);
		addMessage(redirectAttributes, "保存系统参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/param/?repage";
	}
	
	@RequiresPermissions("sys:param:edit")
	@RequestMapping(value = "delete")
	public String delete(Param param, RedirectAttributes redirectAttributes) {
		paramService.delete(param);
		addMessage(redirectAttributes, "删除系统参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/param/?repage";
	}
	
	/**
	 * 根据参数类型编码获取参数列表
	 * @param param
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:param:view")
	@RequestMapping(value = "findListByParamCategoryTypeCode")
	public String findListByParamCategoryTypeCode(Param param, Model model, RedirectAttributes redirectAttributes) {
		List<Param> list = paramService.findListByParamCategoryTypeCode(param.getParamCategoryTypeCode());
		model.addAttribute("list", list);
		return "modules/sys/paramList";
	}
}