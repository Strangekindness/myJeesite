/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {
	/**
	 * 查询字段类型列表
	 * @param dict
	 * @return
	 */
	public List<String> findTypeList(Dict dict);
	
	/**
	 *根据字典分类查询字典类型列表 
	 * @param dict
	 * @return
	 */
	public List<Dict> findTypeListByCategory(Dict dict);
	
}