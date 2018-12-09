<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资源管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/resource/">资源列表</a></li>
		<shiro:hasPermission name="sys:resource:edit"><li><a href="${ctx}/sys/resource/form">资源添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="resource" action="${ctx}/sys/resource/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>资源类型：</label>
				<form:select path="resourceTypeCode" class="input-medium">
					<form:option value="" label="--请选择--"/>
					<form:options items="${fns:getDictList('resource_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>资源名称：</label>
				<form:input path="name" placeholder="模糊检索" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>资源链接：</label>
				<form:input path="url" placeholder="模糊检索" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>提交方式：</label>
				<form:input path="httpMethod" placeholder="模糊检索" htmlEscape="false" maxlength="100" class="input-medium"/>
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
				<th style="vertical-align:middle; text-align:center;">资源类型</th>
				<th style="vertical-align:middle; text-align:center;">资源名称</th>
				<th style="vertical-align:middle; text-align:center;">资源链接</th>
				<th style="vertical-align:middle; text-align:center;">提交方式</th>
				<th style="vertical-align:middle; text-align:center;">排序</th>
				<shiro:hasPermission name="sys:resource:edit"><th style="vertical-align:middle; text-align:center;">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="resource" varStatus="index">
			<tr>
				<td style="vertical-align:middle; text-align:center;">
					${index.index+1}
				</td>
				<td style="vertical-align:middle; text-align:center;"><a href="${ctx}/sys/resource/form?id=${resource.id}">
					${fns:getDictLabel(resource.resourceTypeCode, 'resource_type', '')}
				</a></td>
				<td style="vertical-align:middle; text-align:center;">
					${resource.name}
				</td>
				<td style="vertical-align:middle; text-align:center;">
					${resource.url}
				</td>
				<td style="vertical-align:middle; text-align:center;">
					${resource.httpMethod}
				</td>
				<td style="vertical-align:middle; text-align:center;">
					${resource.sort}
				</td>
				<shiro:hasPermission name="sys:resource:edit"><td style="vertical-align:middle; text-align:center;">
    				<a href="${ctx}/sys/resource/form?id=${resource.id}">修改</a>
					<a href="${ctx}/sys/resource/deleteResource?id=${resource.id}" onclick="return confirmx('确认要删除该资源管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>