<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资源管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/resource/">资源列表</a></li>
		<li class="active"><a href="${ctx}/sys/resource/form?id=${resource.id}">资源<shiro:hasPermission name="sys:resource:edit">${not empty resource.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:resource:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form id="selectRoleForm"  modelAttribute="resource" action="${ctx}/sys/resource/saveAssignedRole" method="post" class="hide">
		<input type="hidden" id="roleIds" name="roleIds" />
		<input type="hidden" id="id" name="id" value="${resource.id}"/>
	</form>
	<form:form id="inputForm" modelAttribute="resource" action="${ctx}/sys/resource/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">资源类型：</label>
			<div class="controls">
				<form:select id="resourceTypeCode" path="resourceTypeCode" class="input-medium required">
					<form:options items="${fns:getDictList('resource_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资源名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资源连接：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提交方式：</label>
			<div class="controls">
				<form:input path="httpMethod" htmlEscape="false" maxlength="100" class="input-small required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="50" class="required digits input-small"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline">排列顺序，升序。</span>
			</div>
		</div>
		<c:if test="${not empty resource.id}">
		<div class="control-group">
			<input id="assignButton" class="btn btn-primary" type="button" value="分配角色"/>
		</div>
		<script type="text/javascript">
			$("#assignButton").click(function(){
				top.$.jBox.open("iframe:${ctx}/sys/resource/assignRole?id=${resource.id}", "分配角色", 810, $(top.document).height()-140,{
					buttons:{"确定指派":"ok", "清除已选":"clear", "关闭":true}, bottomText:"",submit:function(v, h, f){
						var ids = h.find("iframe")[0].contentWindow.ids;
						if (v=="ok"){
							// 删除''的元素
							if(ids[0]==''){
								ids.shift();
							}
					    	$('#roleIds').val(ids);
					    	$('#selectRoleForm').submit();
					    	return true;
						} else if (v=="clear"){
							h.find("iframe")[0].contentWindow.clearAssign();
							return false;
		                }
					}, loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
			});
		</script>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="vertical-align:middle; text-align:center;">序号</th>
				<th style="vertical-align:middle; text-align:center;">角色名称</th>
				<th style="vertical-align:middle; text-align:center;">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${roleList}" var="role" varStatus="index">
			<tr>
				<td style="vertical-align:middle; text-align:center;">${index.index+1}</td>
				<td style="vertical-align:middle; text-align:center;">${role.name}</td>
				<td style="vertical-align:middle; text-align:center;">
					<a href="${ctx}/sys/resource/deleteRoleResource?roleIds=${role.id}&id=${resource.id}" 
						onclick="return confirmx('确认要将角色<b>[${role.name}]</b>移除吗？', this.href)">移除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<div class="form-actions">
			<shiro:hasPermission name="sys:resource:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>