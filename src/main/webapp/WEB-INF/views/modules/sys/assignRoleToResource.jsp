<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分配人员</title>
	<meta name="decorator" content="blank"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		var roleTree;
		var selectedTree;//zTree已选择对象
		
		// 初始化
		$(document).ready(function(){
			roleTree = $.fn.zTree.init($("#roleTree"), setting, roleNodes);
			selectedTree = $.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
		});

		var setting = {view: {selectedMulti:false,nameIsHTML:true,showTitle:false,dblClickExpand:false},
				data: {simpleData: {enable: true}},
				callback: {onClick: treeOnClick}};
		
		var roleNodes=[
	            <c:forEach items="${roleList}" var="role">
			            {id:"${role.id}",
			             pId:"0", 
			             name:"${role.name}"},
	            </c:forEach>];
		
		var selectedNodes =[
		         <c:forEach items="${selectedRoleList}" var="role">
		        {id:"${role.id}",
		         pId:"0",
		         name:"<font color='red' style='font-weight:bold;'>${role.name}</font>"},
		        </c:forEach>
		         ];
		
		var ids = [];
		var names = [];
		
		//获取被回显的人的id和姓名
		for(var i=0; i<selectedNodes.length; i++){ 
			ids.push(String(selectedNodes[i].id));
			names.push(String(selectedNodes[i].name));
		}    
		
		//点击选择项回调
		function treeOnClick(event, treeId, treeNode, clickFlag){
			$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode);
			if("roleTree"==treeId){
				if($.inArray(String(treeNode.id), ids)<0){
					selectedTree.addNodes(null, treeNode);
					ids.push(String(treeNode.id));
				}
			}
			if("selectedTree"==treeId){
				/* if($.inArray(String(treeNode.id), ids)<0){ */
					selectedTree.removeNode(treeNode);
					var nodes = selectedTree.getNodes();
					ids = [];
					for(var i=0; i<nodes.length; i++){ 
						ids.push(String(nodes[i].id));
						names.push(String(nodes[i].name));
					}    
					
				/* }else{
					top.$.jBox.tip("原有角色不能清除！", 'info');
				} */
			}
		};
		function clearAssign(){
			var submit = function (v, h, f) {
			    if (v == 'ok'){
					var tips="";
					ids=ids.slice(0);
					$.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
			    	top.$.jBox.tip(tips, 'info');
			    } else if (v == 'cancel'){
			    	// 取消
			    	top.$.jBox.tip("取消清除操作！", 'info');
			    }
			    return true;
			};
			tips="确定清除已选角色？";
			top.$.jBox.confirm(tips, "清除确认", submit);
		};
	</script>
</head>
<body>
	<div id="assignRole" class="row-fluid span9">
		<div class="span6" style="border-right: 1px solid #A8A8A8;">
			<p>角色列表：</p>
			<div id="roleTree" class="ztree"></div>
		</div>
		<div class="span3" >
			<p>已选角色：</p>
			<div id="selectedTree" class="ztree"></div>
		</div>
	</div>
</body>
</html>
