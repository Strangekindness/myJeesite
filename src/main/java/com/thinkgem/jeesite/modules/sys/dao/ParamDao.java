/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Param;

/**
 * 系统参数表DAO接口
 * @author waile23
 * @version 2018-02-14
 */
@MyBatisDao
public interface ParamDao extends CrudDao<Param> {
}