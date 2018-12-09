/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.GlobalConst;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.ParamDao;
import com.thinkgem.jeesite.modules.sys.entity.Param;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 系统参数表Service
 * @author waile23
 * @version 2018-02-14
 */
@Service
@Transactional(readOnly = true)
public class ParamService extends CrudService<ParamDao, Param> {

	public Param get(String id) {
		if(StringUtils.isBlank(id)){
			return new Param();
		}
		Param param = (Param)CacheUtils.get(GlobalConst.PARAM_CACHE, GlobalConst.PARAM_CACHE_ID_ + id);
		if (param == null){
			param = super.get(id);
			CacheUtils.put(GlobalConst.PARAM_CACHE, GlobalConst.PARAM_CACHE_ID_ + id, param);
		}
		
		return param;
	}

	/**
	 * 获取参数列表带分页
	 * @param page
	 * @param param
	 * @return
	 */
	public Page<Param> findList(Page<Param> page, Param param) {
		return super.findPage(page, param);
	}
	
	/**
	 * 通过参数类型编码获取参数列表（从缓存中获取）
	 * @param paramCategoryTypeCode
	 * @return
	 * @throws Exception 
	 */
	public List<Param> findListByParamCategoryTypeCode(String paramCategoryTypeCode){
		List<Param> paramList = this.findAllList();		
		if(paramList == null) {
			return null;
		}
		if(StringUtils.isBlank(paramCategoryTypeCode)){
			return paramList;
		}
		List<Param> resultList = Lists.newArrayList();
		for(Param param : paramList) {
			if(param == null || StringUtils.isBlank(param.getParamCategoryTypeCode())){
				continue;
			}
			if(param.getParamCategoryTypeCode().equals(paramCategoryTypeCode)) {
				resultList.add(param);
			}
		}
		return resultList;
	}
	
	/**
	 * 获取所有参数列表（带缓存）
	 * @param param
	 * @return
	 */
	public List<Param> findAllList() {
		List<Param> paramList = (List<Param>)CacheUtils.get(GlobalConst.PARAM_CACHE, GlobalConst.PARAM_LIST);
		if (paramList == null){
			paramList = dao.findAllList(new Param());
			CacheUtils.put(GlobalConst.PARAM_CACHE, GlobalConst.PARAM_LIST, paramList);
		}
		return paramList;
	}
	
	/**
	 * 获取参数列表（带分页）
	 */
	public Page<Param> findPage(Page<Param> page, Param param) {
		return super.findPage(page, param);
	}
	
	/**
	 * 保存参数
	 */
	@Transactional(readOnly = false)
	public void save(Param param) {
		super.save(param);
		CacheUtils.removeAll(GlobalConst.PARAM_CACHE);
	}
		
	/**
	 * 删除参数信息
	 */
	@Transactional(readOnly = false)
	public void delete(Param param) {
		super.delete(param);
		CacheUtils.removeAll(GlobalConst.PARAM_CACHE);
	}
	
	/**
	 * 保存参数
	 * @param param
	 */
	@Transactional(readOnly = false)
	public void saveParam(Param param) {
		//参数分类名称冗余
		param.setParamCategoryTypeName(DictUtils.getDictLabel(param.getParamCategoryTypeCode(), "param_category_type", ""));
		//默认可用状态
		param.setStatus(1);
		//排序
		param.setSort(0);
		this.save(param);
	}
}