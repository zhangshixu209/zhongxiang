/**
 *  查询
 */
$(function(){
	queryList(); //加载分页信息
	//Handlebar注册自定义方法
	Handlebars.registerHelper("getUrl", function(filePath){
		return Chief.wrapUrl(filePath);
	});
});

// 在线状态
Handlebars.registerHelper('onlineStatusCd', function(v) {
	if (v == '1') {
		return '在线';
	} else if (v == '2') {
		return '离线';
	} else{
		return '';
	}
});

/**
 * @function 检索条件，分页获取列表信息
 * @param pageno 起始
 * @param limit 每页数量
 */
function queryList(pageno,limit){
	if(typeof pageno == "undefined"){
		pageno = 1;
	}
	if(typeof limit == "undefined"){
		limit = 10;
	}
	var start = (pageno - 1)*limit;
	//序列化查询Form
	var params = $('.searchForm').serialize();
	params += "&start=" + start + "&limit=" + limit;
	Chief.ajax.postJson('/rotation/queryRotationChartList', params, function(data){
		var dsa = Handlebars.compile($("#T_tabletpl").html());
		$("#J_tabletpl").html(dsa(data));
		//初始化分页数据(当前页码，总数，回调查询函数)
		initPaginator(pageno,data.total, queryList);
		$("#sample-table-1").colResizable({ // 手动拖动表格
			liveDrag:true,
			fixed:false,
//			resizeMode:'overflow'
		});
	});
}

$("#J_search").click(function(){
	queryList();
});

//弹窗 公共方法
function dialog(title, ht) {
	var height = Math.round($('body').height() * 0.95); //
	var top = Math.round(($('body').height() - height) * 0.5) + "px";
	var width = Math.round($('body').width() * 0.5) + "px"; //弹窗后为单行单列展示
	Chief.layer.newEmptyDiv(title, ht, width, height + "px", top);
	//弹窗中间内容的高度-自动
	//42-弹窗背景高度减去弹窗内容之外高度		50-按钮整体高度
	var popup_outline_height = (height - 42 - 50) + "px";
	$(".popup_outline").css("cssText", "height:" + popup_outline_height + "!important;");
}

//新增按钮
$('.pull-right').on('click','#add_btn', function (){
	var htmls = Handlebars.compile($("#T_addRotationChart").html());
	var ht = htmls();
	//弹窗
	dialog("新增", ht);
	initUpload(); //加载上传控件
	formValidate("#addRotationChartForm");
});
//新增
function saveRotationChart(){
	var flag = $("#addRotationChartForm").validate().form(); //若全部通过验证则form方法返回true
	if(!flag) {
		return false;
	}
	var filePaths = $("#filePaths").val();
	if(isDataNull(filePaths)){
		Chief.layer.tips("请上传附件！");
		return false;
	}
	//获取表单数据
	var data = $('#addRotationChartForm').serialize();
	//发起请求
	Chief.ajax.postJson("/rotation/saveRotationChartInfo", data, function (data) {
		if(data.code == '0'){
			Chief.layer.tips("保存成功！");
			setTimeout('outTips()', 1000 );
		}else{
			Chief.layer.tips(data.msg);
		}
	});
};

//成功提示
function outTips(){
	Chief.layer.close();
	queryList();
}

//删除轮播图
$("body").on('click','#del_btn',function() {
	//获取选中框数据
	var _edit = $("#J_tabletpl td").find("input[type='checkbox']:checked");//获取选中行数量
	if(_edit.length == 0){
		Chief.layer.tips('请选择一条数据！');
		return false;
	}
	var id = _edit.eq(0).attr("data-id"); //获取主键ID
	delRotationChart(id);
});

//调用删除
function delRotationChart(id) {
	Chief.layer.confirm("是否确认删除！",function() {
		Chief.ajax.postJson('/rotation/deleteRotationChartInfo',{"id":id},function(data){
			if(data.code == '0'){
				Chief.layer.tips("删除成功！");
				setTimeout('outTips()', 1000 );
			}else{
				Chief.layer.tips(data.msg);
			}
		})
	})
}

//点击编辑按钮
$('.pull-right').on('click','#edit_btn', function (){
	var checked = $("#J_tabletpl input[type=checkbox]:checked");
	if(checked.length != 1) {
		Chief.layer.tips("请选择一条数据！");
		return;
	}
	// 获取该条数据详情
	var id = checked.eq(0).attr("data-id");
	var paramId = {
		id: id
	};
	Chief.ajax.postJson('/rotation/queryRotationChartDetail',paramId, function(data) {
		if('0' == data.code){
			var htmls = Handlebars.compile($("#T_editRotationChart").html());
			var ht = htmls(data.item);
			//弹框编辑
			dialog("编辑", ht);
			//初始化表单验证规则
			formValidate("#editRotatonChartForm");
			initUpload(); // 初始化上传组件
			if (data.item.onlineStatus == "1") {
				$("input:radio[name='onlineStatus'][value='1']").prop('checked',true);
			} else {
				$("input:radio[name='onlineStatus'][value='2']").prop('checked',true);
			}
		}else {
			Chief.layer.tips("系统异常", 1500);
		}
	});
});

/**
 * 编辑 保存
 */
function updateRotationChart(){
	var flag = $("#editRotationChartForm").validate().form(); //若全部通过验证则form方法返回true
	if(!flag) {
		return false;
	}
	var filePaths = $("#filePaths").val();
	if(isDataNull(filePaths)){
		Chief.layer.tips("请上传附件！");
		return false;
	}
	//获取表单数据
	var data = $('#editRotationChartForm').serialize();
	//发起请求
	Chief.ajax.postJson("/rotation/updateRotationChartInfo", data, function (data) {
		if("0" == data.code){
			Chief.layer.tips("保存成功", 1500);
			setTimeout('outTips()', 1000 );
		} else if("-1" == data.code){
			Chief.layer.tips(data.msg, 2000);
		} else {
			Chief.layer.tips("保存失败", 1500);
		}
	});
};

//数据校验
function formValidate(){
	// 轮播图新增验证
	$("#addRotationChartForm").validate({
		errorPlacement: function(error, element) { //验证消息放置的位置
			error.appendTo(element.parent());
		},
		onfocusout: function(element) {
			$(element).valid();
		}
	});
	//轮播图编辑验证
	$("#editRotationChartForm").validate({
		errorPlacement: function(error, element) { //验证消息放置的位置
			error.appendTo(element.parent());
		},
		onfocusout: function(element) {
			$(element).valid();
		}
	});
}

//上线轮播图
$("body").on('click','#online_btn',function() {
	//获取选中框数据
	var _edit = $("#J_tabletpl td").find("input[type='checkbox']:checked");//获取选中行数量
	if(_edit.length == 0){
		Chief.layer.tips('请选择一条数据！');
		return false;
	}
	var id = _edit.eq(0).attr("data-id"); //获取主键ID
	var onlineStatus = _edit.eq(0).attr("data-onlineStatus"); //获取轮播图状态
	if (onlineStatus == "1") {
		Chief.layer.tips('轮播图已上线！');
		return false;
	}
	var param = {
		id: id,
		onlineStatus: "1"
	}
	updateOnlineStatus(param);
});

//离线轮播图
$("body").on('click','#offline_btn',function() {
	//获取选中框数据
	var _edit = $("#J_tabletpl td").find("input[type='checkbox']:checked");//获取选中行数量
	if(_edit.length == 0){
		Chief.layer.tips('请选择一条数据！');
		return false;
	}
	var id = _edit.eq(0).attr("data-id"); //获取主键ID
	var onlineStatus = _edit.eq(0).attr("data-onlineStatus"); //获取轮播图状态
	if (onlineStatus == "2") {
		Chief.layer.tips('轮播图已离线！');
		return false;
	}
	var param = {
		id: id,
		onlineStatus: "2"
	}
	updateOnlineStatus(param);
});

// 上线/离线状态修改
function updateOnlineStatus(param){
	Chief.ajax.postJson("/rotation/updateOnlineStatus", param, function (data) {
		if("0" == data.code){
			Chief.layer.tips("保存成功", 1500);
			setTimeout('outTips()', 1000 );
		} else if("-1" == data.code){
			Chief.layer.tips(data.msg, 2000);
		} else {
			Chief.layer.tips("保存失败", 1500);
		}
	});
}

/**
 * 查看详情
 * @param id 轮播图id
 */
function queryDetail(id) {
	if(isDataNull(id)) {
		Chief.layer.tips("查询失败!")
		return;
	}
	var params = {id: id}
	Chief.ajax.postJson('/rotation/queryRotationChartDetail',params, function(data) {
		if('0' == data.code){
			var htmls = Handlebars.compile($("#T_rotationChartDetail").html());
			var ht = htmls(data.item);
			dialog("查看详情", ht);
			if (data.item.onlineStatus == "1") {
				$("input:radio[name='onlineStatus'][value='1']").prop('checked',true);
			} else {
				$("input:radio[name='onlineStatus'][value='2']").prop('checked',true);
			}
		}else {
			Chief.layer.tips("系统异常", 800);
		}
	});
}

// 文件路径,文件名拼接 返回
function writeFilePaths(data) {
	if(!data.item) {
		return false;
	}
	if(data.item.fileList && data.item.fileList.length > 0) {
		var fileList = data.item.fileList;
		var pathArry = [];
		for(var i=0; i<fileList.length; i++) {
			var fileItem = fileList[i];
			var pathItem = fileItem.filePath + "#" + fileItem.fileName; // 文件存储路径#原始文件名
			pathArry.push(pathItem); // 按上传顺序放入数组
		}

		return pathArry.join("$");
	}
}

/** 下方是文件上传相关代码 */
// 给附件中的删除按钮绑定事件
function bindEvent() {
	//删除按钮 绑定click事件
	$('.ndiv').on('click', 'span.del' ,function(){
		var filePathArrytem = $('#filePaths').val().split("$");
		var span = $(this);
		var $p = span.parent();

		// 更新filePaths
		// 使用splice函数进行数组元素移除：
		var index = $('div.file-list p.file-item').index($p);
		if (index > -1) {
			filePathArrytem.splice(index, 1);
		}
		$('#filePaths').val(filePathArrytem.join("$"));
		$p.remove(); //移除这一行
	});
}

// 初始化上传组件
function initUpload(){
	uploaderInit('#filePicker');
	bindEvent(); //按钮事件绑定
}

function uploaderInit(picker) {

	var uploaderS;
//    var filePathArry = []; // 存放文件路径和名称
	var filePathArry = "";
	uploaderS = WebUploader.create({
		// 选完文件后，是否自动上传
		auto: true,
		//	去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
		duplicate: true, //
		// swf文件路径
		swf: '../assets/js/Uploader.swf',
		// 文件接收服务端。
		server: '/pem-manage/api/rotation/uploadImg',
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick: picker,
		fileVal: "file",
		// 限制文件数量
		//fileNumLimit: 10,
		fileSizeLimit: 90 * 1024 * 1024, // 90 M 大小文件
		fileSingleSizeLimit: 10000 * 1024, // 单个文件
		// 只允许选择图片、word、txt、excel类型文件。
		accept: {
			title: 'Images',
			extensions: 'png,jpg,jpeg',
			mimeTypes: '' // 'image/*'
		},
		// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		resize: false
	});

	//当某个文件的分块在发送前触发
	var showLoadingSha;
	//加入上传队列前
	uploaderS.on('beforeFileQueued', function(file, obj){
		if(file.size > 10*1024*1024) {
			Chief.layer.tips("单个文件不能超过10M");
			return false;
		}
		var fileList = $('div.file-list').find('.file-item'); //放入的文件数量
		if(fileList.length > 0) {
			Chief.layer.tips("文件数量超出限制");
			return false;
		}
		if(file.name.lenth > 100) {
			Chief.layer.tips("文件名称过长");
			return false;
		}
	});

	uploaderS.on('uploadBeforeSend', function(file, data) {
		showLoadingSha = Chief.layer.showLoadingShade(); //加载动画
		/*data = $.extend(data, {
            "orgId": $("#id").val()//公司ID
        });*/
	});
	//	上传成功
	uploaderS.on('uploadSuccess', function(file, data) {
		Chief.layer.close(showLoadingSha);
		if("0" == data.code) {
			filePathArry = $("#filePaths").val();
			var filePath = data.item.fileName;
			var url = Chief.wrapUrl(filePath);
			// <img src="'+url+'"/>
			var html = '<p class="file-item file-item-box"><span class="file-name">' +
				file.name  +  // 原始文件名
				"</span>"  +
				'<span class="del"><a href="javascript:;">删除</a></span>' +
				'<span><a class="download" href="'+ url  +'" download>下载</a></span>' +
				'</p>';
			$('.ndiv').append(html);
			// 把每次上传的文件路径，文件名拼接成字符串放到隐藏域中
			if(!isDataNull(filePath)) {
				var filePathItem = filePath + "#" + file.name; // 文件存储路径#原始文件名
				filePathArry += "$"+filePathItem; // 按上传顺序放入数组
				if(filePathArry.indexOf('$') === 0){
					filePathArry = filePathArry.substring(1);
				}
				$('#filePaths').val(filePathArry); //写入隐藏域
			}
		}else {
			Chief.layer.tips("文件上传失败!")
		}
	});

	//	上传失败
	uploaderS.on('uploadError', function(file, response) {
		Chief.layer.tips("上传失败")
		Chief.layer.close(showLoadingSha);
	});
	//请求出错
	uploaderS.on('error', function(file, response) {
		switch (file) {
			case "Q_TYPE_DENIED":
				Chief.layer.tips("文件格式不正确");
				return false;
//	        case "Q_EXCEED_NUM_LIMIT":
//	            Chief.layer.tips("文件数量超出限制");
//	            return false;
			/* case "F_DUPLICATE":
                 Chief.layer.tips("已经选择过该文件");
                 return false;*/
//	        case "F_EXCEED_SIZE":
//	            Chief.layer.tips("单个文件数量超出限制");
//	            return false;
			case "Q_EXCEED_SIZE_LIMIT ":
				Chief.layer.tips("文件大小超出限制");
				return false;
			default :
				break;
		}
		Chief.layer.close(showLoadingSha);
	});
	if(navigator.userAgent.indexOf("MSIE")>0 && navigator.userAgent.indexOf("MSIE 9.0")>0 ||navigator.userAgent.indexOf("MSIE")>0 && navigator.userAgent.indexOf("MSIE 8.0")>0){
		var setHeader = function(object, data, headers) {
			headers['Access-Control-Allow-Origin'] = '*';
			headers['Access-Control-Request-Headers'] = 'content-type';
			headers['Access-Control-Request-Method'] = 'POST';
		};
		uploaderS.on('uploadBeforeSend ', setHeader);
	}

}

//校验非空
function isDataNull(str) {
	if (str == null || str == "" || str == undefined) {
		return true;
	}
	return false;
}

