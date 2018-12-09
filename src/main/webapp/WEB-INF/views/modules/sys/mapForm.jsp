<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%> 
<html>    
<head>   
	<title>地图管理</title>
   <!--  <meta name="decorator" content="default"/> -->
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=F0i6SrLmHquLVNLCqpExxPrj8mWVdFwx"></script>    
  	<!-- 加载鼠标绘制工具   -->     
    <script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>    
    <link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />    
    <!--加载检索信息窗口  -->  
    <script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>    
    <link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" /> 
 
    <script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
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
	  <!-- <div id="result">    
        <input type="button" value="获取绘制的覆盖物个数" onclick="alert(overlays.length)"/>    
        <input type="button" value="清除所有覆盖物" onclick="clearAll()"/>    
        <input type="button"  value="获取多边形的顶点" onclick="getPoint()"/><br/>    
        <input type="button"  value="开启线、面编辑功能" onclick="Editing('enable')"/>    
        <input type="button"  value="关闭线、面编辑功能" onclick="Editing('disable')"/>    
        <input type="button"  value="显示原有多边形" onclick="showPolygon(this)" /><br/>    
            
        <input type="button"  value="画多边形" onclick="draw(BMAP_DRAWING_POLYGON)" />    
        <input type="button"  value="画矩形" onclick="draw(BMAP_DRAWING_RECTANGLE)" />    
        <input type="button"  value="画线" onclick="draw(BMAP_DRAWING_POLYLINE)" />  
        <input type="button"  value="画圆形" onclick="draw(BMAP_DRAWING_CIRCLE)" />    
        <input type="button"  value="画点" onclick="draw(BMAP_DRAWING_MARKER)" /> 
        <span>右键获取任意点的经纬度</span>    
    </div>      -->
    <!--  <div id="resultShape"></div>    
     <div id="shape">坐标为</div>    -->
    <div id="map" style="height:100%;-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;"></div> 
    
          
<script type="text/javascript">    
    function G(id){    
    	return document.getElementById(id);    
	}    
    
    // 百度地图API功能    
    var map = new BMap.Map('map');    
    var poi = new BMap.Point(125.33136,43.89215);    
    map.centerAndZoom(poi, 12);    
    map.enableScrollWheelZoom();      
    var overlays = [];    
    var overlaycomplete = function(e){    
        overlays.push(e.overlay);     
    };    
    var styleOptions = {    
        strokeColor:"green",    //边线颜色。    
        fillColor:"green",      //填充颜色。当参数为空时，圆形将没有填充效果。    
        strokeWeight: 1,       //边线的宽度，以像素为单位。    
        strokeOpacity: 0.8,    //边线透明度，取值范围0 - 1。    
        fillOpacity: 0.2,      //填充的透明度，取值范围0 - 1。    
        strokeStyle: 'solid' //边线的样式，solid或dashed。    
    }   
    
    
    // 通过JavaScript的prototype属性继承于BMap.Control
    ZoomControl.prototype = new BMap.Control();

    // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
    // 在本方法中创建个p元素作为控件的容器,并将其添加到地图容器中
    ZoomControl.prototype.initialize = function(map){
      // 创建一个DOM元素
      var p = document.createElement("p");
      p.innerHTML = '<p id="r-result">搜索地址:<input type="text" id="suggestId" size="20" value="百度" style="width:150px;" /></p><p id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></p>';

      // 添加DOM元素到地图中
      map.getContainer().appendChild(p);
      // 将DOM元素返回
      return p;
    }

    // 创建控件
    var myZoomCtrl = new ZoomControl();
    // 添加到地图当中
    map.addControl(myZoomCtrl);

    var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
        {"input" : "suggestId"
        ,"location" : map
    });

    ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
    var str = "";
        var _value = e.fromitem.value;
        var value = "";
        if (e.fromitem.index > -1) {
            value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        }
        str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;

        value = "";
        if (e.toitem.index > -1) {
            _value = e.toitem.value;
            value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        }
        str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
        G("searchResultPanel").innerHTML = str;
    });

    var myValue;
    ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
    var _value = e.item.value;
        myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;

        setPlace();
    }); 
    
    
    
    
        
    //实例化鼠标绘制工具    
    var drawingManager = new BMapLib.DrawingManager(map, {    
        isOpen: false, //是否开启绘制模式    
        enableDrawingTool: true, //是否显示工具栏    
        drawingToolOptions: {    
            anchor: BMAP_ANCHOR_TOP_LEFT, //位置    
            offset: new BMap.Size(5, 5), //偏离值    
        },    
        circleOptions: styleOptions, //圆的样式    
        polylineOptions: styleOptions, //线的样式    
        polygonOptions: styleOptions, //多边形的样式    
        rectangleOptions: styleOptions //矩形的样式    
    });
        
    //添加鼠标绘制工具监听事件，用于获取绘制结果    
    drawingManager.addEventListener('overlaycomplete', overlaycomplete);    
        
    map.addEventListener("rightclick",function(e){    
        if(confirm(e.point.lng + "," + e.point.lat)){    
            $("shape").innerHTML=$("shape").innerHTML+" <br/>("+e.point.lng+","+e.point.lat+")";    
            }    
        });    
    function draw(type){    
        drawingManager.open();     
        drawingManager.setDrawingMode(type);    
    }       
            
    function clearAll() {    
        for(var i = 0; i < overlays.length; i++){    
            map.removeOverlay(overlays[i]);    
        }    
        overlays.length = 0       
    }    
    
    function getPoint(){    
        $("resultShape").innerHTML=''; 
        var pointArray = [];
        for(var i = 0; i < overlays.length; i++){    
            var overlay=overlays[i].getPath();    
            $("resultShape").innerHTML=$("resultShape").innerHTML+overlay.length+'边形1111:<br/>';    
            for(var j = 0; j < overlay.length; j++){    
                var grid =overlay[j];    
                $("resultShape").innerHTML=$("resultShape").innerHTML+(j+1)+ "个点:("+grid.lng+","+grid.lat+");<br/>";  
                
                var temp = {"lng": grid.lng, "lat": grid.lat};
                pointArray.push(temp);
            }    
        } 
        
        var pointArrayStr = JSON.stringify(pointArray);
       
          jQuery.ajax({
	        type: "post",
	        url:"${ctx}/sys/area/mapSave/", 
	        dataType: "json",
	        data: {
	        	pointArrayStr : pointArrayStr
	        },
	        error: function(request) {
	        	
	        },
	        success: function(data) {
	        	
	        }
	    }); 
    }    
    
    function Editing(state){    
        for(var i = 0; i < overlays.length; i++){    
            state=='enable'?overlays[i].enableEditing():overlays[i].disableEditing();    
        }    
    }    
        
    function  showPolygon(btn){    
      var polygon = new BMap.Polygon([    
          new BMap.Point(113.941853,22.530777),    
          new BMap.Point(113.940487,22.527789),    
          new BMap.Point(113.94788,22.527597),    
          new BMap.Point(113.947925,22.530618)    
      ], styleOptions);  //创建多边形    
      map.addOverlay(polygon);   //增加多边形    
      // overlays.push(polygon); //是否把该图像加入到编辑和删除行列    
       btn.setAttribute('disabled','false');    
       showText();    
     }    
         
     function showText(){    
        var opts = {    
        position : new BMap.Point(113.941853,22.530777),    // 指定文本标注所在的地理位置    
        offset   : new BMap.Size(30, 30)    //设置文本偏移量    
       }    
     var label = new BMap.Label("不可编辑", opts);  // 创建文本标注对象    
        label.setStyle({    
        color : "black",    
         fontSize : "16px",    
        fillColor:"red",      //填充颜色。当参数为空时，圆形将没有填充效果。    
        });    
      map.addOverlay(label);      
     }    
</script>    
</body>    
</html>    