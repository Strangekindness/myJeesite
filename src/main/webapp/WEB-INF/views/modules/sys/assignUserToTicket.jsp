<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分配人员</title>
	<meta name="decorator" content="blank"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
	
		var officeTree;
		var selectedTree;//zTree已选择对象
		
		// 初始化
		$(document).ready(function(){
			officeTree = $.fn.zTree.init($("#officeTree"), setting, officeNodes);
			selectedTree = $.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
		});

		var setting = {view: {selectedMulti:false,nameIsHTML:true,showTitle:false,dblClickExpand:false},
				data: {simpleData: {enable: true}},
				callback: {onClick: treeOnClick}};
		
		var officeNodes=[
	            <c:forEach items="${officeList}" var="office">
	           		<c:if test="${office.id eq '1' or office.id eq '5'}">
			            {id:"${office.id}",
			             pId:"${not empty office.parent?office.parent.id:0}", 
			             name:"${office.name}"},
	             	</c:if>
	            </c:forEach>];
	
		var pre_selectedNodes =[
   		         <c:forEach items="${userList}" var="user">
   		        {id:"${user.id}",
   		         pId:"0",
   		         name:"<font color='red' style='font-weight:bold;'>${user.name}</font>"},
		        </c:forEach> 
   		         ];
		
		var selectedNodes =[
		         <c:forEach items="${userList}" var="user">
		        {id:"${user.id}",
		         pId:"0",
		         name:"<font color='red' style='font-weight:bold;'>${user.name}</font>"},
		        </c:forEach>
		         ];
		
		var pre_ids = "${selectIds}".split(",");
		var ids = "${selectIds}".split(",");
		var names = [];
		
		//点击选择项回调
		function treeOnClick(event, treeId, treeNode, clickFlag){
			$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode);
			if("officeTree"==treeId){
				if(treeNode.id == '5'){
					$.get("${ctx}/sys/role/users?officeId=" + treeNode.id, function(userNodes){
						$.fn.zTree.init($("#userTree"), setting, userNodes);
					});
				}
			}
			if("userTree"==treeId){
				if($.inArray(String(treeNode.id), ids)<0){
					selectedTree.addNodes(null, treeNode);
					//重新遍历选中节点的id
					ids = [];
					//重新遍历选中节点的名字
					names = [];
					var nodes = selectedTree.getNodes();
					for(var i=0; i<nodes.length; i++){ 
						ids.push(String(nodes[i].id));
						names.push(String(nodes[i].name));
					}  
				}
			};
			
			if("selectedTree"==treeId){
				if($.inArray(String(treeNode.id), pre_ids)<0){
					selectedTree.removeNode(treeNode);
					ids.splice($.inArray(String(treeNode.id), ids), 1);
					//重新遍历选中节点的id
					ids = [];
					//重新遍历选中节点的名字
					names = [];
					var nodes = selectedTree.getNodes();
					for(var i=0; i<nodes.length; i++){ 
						ids.push(String(nodes[i].id));
						names.push(String(nodes[i].name));
					}    
				}else{
					top.$.jBox.tip("原有成员不能清除！", 'info');
				}
			}
		};
		function clearAssign(){
			var submit = function (v, h, f) {
			    if (v == 'ok'){
					var tips="";
					if(pre_ids.sort().toString() == ids.sort().toString()){
						tips = "未给选择新成员！";
					}else{
						tips = "已选人员清除成功！";
					}
					ids=pre_ids.slice(0);
					selectedNodes=[];
					names=[];
					$.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
			    	top.$.jBox.tip(tips, 'info');
			    } else if (v == 'cancel'){
			    	// 取消
			    	top.$.jBox.tip("取消清除操作！", 'info');
			    }
			    return true;
			};
			tips="确定清除已选人员？";
			top.$.jBox.confirm(tips, "清除确认", submit);
		};
	</script>
</head>
<body>
	<div id="assignRole" class="row-fluid span12">
		<div class="span4" style="border-right: 1px solid #A8A8A8;">
			<p>所在部门：</p>
			<div id="officeTree" class="ztree"></div>
		</div>
		<div class="span3">
			<p>待选人员：</p>
			<div id="userTree" class="ztree"></div>
		</div>
		<div class="span3" style="padding-left:16px;border-left: 1px solid #A8A8A8;">
			<p>已选人员：</p>
			<div id="selectedTree" class="ztree"></div>
		</div>
	</div>
</body>
</html>
