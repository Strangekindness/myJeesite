function test(){
	alert("测试附件js");
}

//上传图片
function uploadImg() {
	var tableId = $("#id").val();
	var uploader = WebUploader.create({
		swf : '${ctxStatic}/upload/Uploader.swf',
		server : '${ctx}/sys/attachment/qiniuUpload',
		pick : '#picker',
		accept: {
			title: 'Images',
			extensions: 'gif,jpg,jpeg,bmp,png',
			mimeTypes: 'image/*'
		},
		resize : false,
		auto : true,
		method : 'POST',
		formData : {
			'tableName' : 'crm_product',
			'tableId' : tableId,
			'attId':$("#fileId").val()
		},
		fileVal : "imagefile"
	});
	uploader.on('uploadSuccess',function(file, response) {
		$("#fileId").val(response.data.attachmentId);
		$("#productImage").val(file.name);
		top.$.jBox.tip(response.message);
		console.log(response.data.attachmentId);
		var $a = $("<a>" + file.name + "</a>");
		$a.attr("href",response.data.imageInfo);
		var $img = $("<img style='vertical-align:middle;' class='hand' src='${ctxStatic}/images/close_hover.png'></img>");
		var $img2 = $("<img style='width:100px;height:100px' class='hand' src='"+response.data.imageInfo+"'></img>");
		$img.attr("onclick", "deleteImg('" + file.name + "','" + response.data.attachmentId + "');");
		$("#attachmentId").remove();
		var $span = $("<span id='attachmentId'></span>");
		var $fileId = $("<input type=\"hidden\" name=\"fileId\"/>");
		$span.append($a);
		$span.append($fileId);
		$span.append("&nbsp;&nbsp;");
		$span.append($img);
		$span.append($img2);
		$("#uploaded").append($span);
	});
	uploader.on( 'uploadError', function( file ) {
	    $( '#'+file.id ).find('p.state').text('上传出错');
	});

	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').fadeOut();
	});
}
//删除图片
function deleteImg(fileName,fileId){
	$.ajax( {
		url : "${ctx}/sys/attachment/deleteImgById?fileName="+fileName+"&fileId="+fileId+"&id="+$("#fileId").val(),
		type:"post",
		async:false,
		success : function(data) {
			top.$.jBox.tip("删除图片成功！");
			$("#attachmentId").remove();
			$("#productImage").val("");
		},
		error : function(req) {
			alert(req.code);
		}
	});
} 