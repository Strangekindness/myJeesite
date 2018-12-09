/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.ResponseResult;
import com.thinkgem.jeesite.common.persistence.RestfulPage;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.cms.service.CategoryService;

/**
 * 文章API
 * @author waile23
 *
 */
@RestController
@RequestMapping(value = "${wechatPath}/rest/cms")
public class CmsApiController extends BaseController{
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CategoryService categoryService;
	/**
	 * 文章列表
	 */
	@RequestMapping(value = "/article/list/{categoryId}", method = RequestMethod.GET)
	public ResponseResult list(@PathVariable("categoryId") String categoryId, 
			HttpServletRequest request, HttpServletResponse response) {
		
		Category category = categoryService.get(categoryId);
		Page<Article> restfulPage = new RestfulPage<Article>(request, response);
		Page<Article> articleList = articleService.findPage(restfulPage, new Article(category), false);
		
		return ResponseResult.success(articleList);
		
	}
	
	/**
	 * 通过文章Id获得文章及内容
	 */
	@RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
	public ResponseResult getArticle(@PathVariable("id") String id, 
			HttpServletRequest request, HttpServletResponse response) {
		Article article = null;
		try {
			article = articleService.getArticleById(id);
		} catch (Exception e) {
			return ResponseResult.failure("获取文章失败：" + e.getMessage());
		}
		return ResponseResult.success(article);
	}
}
