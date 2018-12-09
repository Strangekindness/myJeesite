/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.config;

import org.joda.time.DateTime;

/**
 * 常量类
 * @author waile23
 * @version 2017-12-24
 */
public class GlobalConst {
	//系统默认时间，转化为Date类型 GlobalConst.DEFAULT_DATE_TIME.toDate()
	public static final DateTime DEFAULT_DATE_TIME = new DateTime(1970, 1, 1, 0, 0, 0);
	//缓存
	public static final String USER_TYPE_MENU = "userTypeMenuCache";
	
	public static final String CAPTCHA_CACHE = "captchaCache";
	public static final String WECHAT_USER_CACHE = "wechatUserCache";
	public static final String WECHAT_OAUTH_CACHE = "wechatOauthCache";
	
	public static final String SERVICE_ITEM_CACHE = "serviceItemCache";
	public static final String SERVICE_ITEM_LIST = "serviceItemList";
	public static final String SERVICE_ITEM_CACHE_ID_ = "serviceItemId_";
	
	public static final String SERVICE_ITEM_PHASE_CACHE = "serviceItemPhaseCache";
	public static final String SERVICE_ITEM_PHASE_LIST = "serviceItemPhaseList";
	public static final String SERVICE_ITEM_PHASE_CACHE_ID_ = "serviceItemPhaseId_";	
	
	public static final String PRODUCT_CATEGORY_CACHE = "productCategoryCache";
	public static final String PRODUCT_CATEGORY_LIST = "productCategoryList";
	public static final String PRODUCT_CATEGORY_CACHE_ID_ = "productCategoryId_";
	
	public static final String PRODUCT_CACHE = "productCache";
	public static final String PRODUCT_LIST = "productList";
	public static final String PRODUCT_CACHE_ID_ = "productId_";
	public static final String PRODUCT_LIST_LATEST_TEN = "productListLatestTen";
	public static final String PRODUCT_LIST_LATEST_TEN_SALES_MAN_ID_ = "productListSalesManId_";
	
	public static final String WAREHOUSE_CACHE = "warehouseCache";
	public static final String WAREHOUSE_LIST = "warehoueList";
	public static final String WAREHOUSE_CACHE_ID_ = "warehouseId_";
	
	public static final String REGION_CACHE = "regionCache";
	public static final String REGION_LIST = "regionList";
	public static final String REGION_CACHE_ID_ = "regionId_";
	
	public static final String EVALUATION_CACHE = "evaluationCache";
	public static final String EVALUATION_LIST = "evaluationList";
	public static final String EVALUATION_CACHE_ID_ = "evaluationId_";
	
	public static final String BRAND_CACHE = "brandCache";
	public static final String BRAND_LIST = "brandList";
	public static final String BRAND_CACHE_ID_ = "brandId_";
	
	public static final String PARAM_CACHE = "paramCache";
	public static final String PARAM_LIST = "paramList";
	public static final String PARAM_CACHE_ID_ = "paramId_";
	
	public static final String SALES_CATEGORY_CACHE = "salesCategoryCache";
	public static final String SALES_CATEGORY_USER_DETAIL = "salesCategoryUserDetail";
	public static final String SALES_CATEGORY_LIST = "salesCategoryList";
	public static final String SALES_CATEGORY_LIST_CACHE_ID_ = "salesCategoryListCacheId_";
	
	public static final String CUSTOMER_DEPARTMENT_CACHE = "customerDepartmentCache";
	public static final String CUSTOMER_DEPARTMENT_LIST = "customerDepartmentList";
	public static final String CUSTOMER_DEPARTMENT_CACHE_ID_ = "customerDepartmentId_";
	
	public static final String SALES_DEPARTMENT_CACHE = "salesDepartmentCache";
	public static final String SALES_DEPARTMENT_LIST = "salesDepartmentList";
	public static final String SALES_DEPARTMENT_CACHE_ID_ = "salesDepartmentId_";
	
	public static final String TEST_USER_CACHE = "testUserCache";
	public static final String TEST_USER_LIST = "testUserList";
	public static final String TEST_USER_CACHE_ID_ = "testUserId_";
	
	public static final String RESOURCE_CACHE = "resourceCache";
	public static final String RESOURCE_LIST = "resourceList";
	public static final String RESOURCE_CACHE_ID_ = "resourceId_";
}
