<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统参数表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		/* function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        } */
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/param/">系统参数</a></li>
		<shiro:hasPermission name="sys:param:edit"><li><a href="${ctx}/sys/param/form">参数添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="param" action="${ctx}/sys/param/" method="post" class="breadcrumb form-search">
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> --%>
		<ul class="ul-form">
			<li><label>参数分类：</label>
				<form:select path="paramCategoryTypeCode" class="input-medium">
					<form:option value="" label="--请选择--"/>
					<form:options items="${fns:getDictList('param_category_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="vertical-align:middle; text-align:center;">序号</th>
				<th style="vertical-align:middle; text-align:center;">参数分类</th>
				<th style="vertical-align:middle; text-align:center;">中文名称</th>
				<th style="vertical-align:middle; text-align:center;">参数键</th>
				<th style="vertical-align:middle; text-align:center;">参数值</th>
				<shiro:hasPermission name="sys:param:edit">
					<th style="vertical-align:middle; text-align:center;">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="bean" varStatus="index">
			<tr>
				<td style="vertical-align:middle; text-align:center;">
					${index.index+1}
				</td>
				<td style="vertical-align:middle; text-align:center;">
					${bean.paramCategoryTypeName}
				</td>
				<td style="vertical-align:middle; text-align:center;">
					<a href="${ctx}/sys/param/form?id=${bean.id}">
						${bean.paramCnName}
					</a>
				</td>
				<td style="vertical-align:middle; text-align:center;">
					${bean.paramKey}
				</td>
				<td style="vertical-align:middle; text-align:center;">
					${bean.paramValue}
				</td>
				<shiro:hasPermission name="sys:param:edit"><td style="vertical-align:middle; text-align:center;">
    				<a href="${ctx}/sys/param/form?id=${bean.id}">修改</a>
					<a href="${ctx}/sys/param/delete?id=${bean.id}" onclick="return confirmx('确认要删除该系统参数吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<%-- <div class="pagination">${page}</div> --%>
</body>
</html>