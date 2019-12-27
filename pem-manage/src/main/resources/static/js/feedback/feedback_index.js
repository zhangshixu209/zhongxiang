/**
 *@首页反馈意见模块js 
 */

//取消按钮关闭弹窗
function doCancel(){
    Chief.layer.close();
}

//数据校验规则
function formValidate(id){
    $(id).validate({
    	 errorClass: 'help-block',
         rules : {
        	 replyContent:{
            	 required : true
             }
         },
         messages:{
        	 replyContent:{
                 required : "请填写意见信息"
             }
         },
        errorPlacement: function(error, element) { //验证消息放置的位置
            error.appendTo(element.parent());
        },
        onfocusout: function(element) {
            $(element).valid();
        }
    });
}

//判断字符串是否为空
function isDataNull(str) {
    if (str == null || str == "" || str == undefined) {
        return true;
    }
    return false;
}


/**
 * 反馈建议
 */
$(".feedSuggestions").click(function(){
	var htmls = Handlebars.compile($("#T_addFeedback").html());
    var ht = htmls();
    //弹窗
    dialog("反馈/意见", ht);
    //加载上传控件
    uploaderInit('#filePicker');
    bindEvent(); //按钮事件绑定
    //初始化表单验证规则
    formValidate("#addFeedbackForm");
});
//给附件中的删除按钮绑定事件
function bindEvent() {
	$('.ndiv').on('click', 'span.del' ,function(){
		var filePathArrytem = $('#filePaths').val().split("$");
		var span = $(this);
		var $p = span.parent();
		// 使用splice函数进行数组元素移除：
		var index = $('div.file-list p.file-item').index($p);
		if (index > -1) {
			filePathArrytem.splice(index, 1);
		}
		$('#filePaths').val(filePathArrytem.join("$"));
		$p.remove(); //移除这一行
	});
}
//上传控件初始化
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
        server: '/pem-manage/api/workordermanage/uploadImg',
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
            extensions: 'png,jpg,jpeg,xls,xlsx,doc,docx,txt',
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
    	if(fileList.length > 9) {
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
    });
    //	上传成功
    uploaderS.on('uploadSuccess', function(file, data) {
        Chief.layer.close(showLoadingSha);
        if("0" == data.code) {
        	filePathArry = $("#filePaths").val();
        	var filePath = data.item.fileName;
            var url = Chief.wrapUrl(filePath);
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

//执行新增  传入工单状态
function insertSelective(orderStatus) {
	var flag = $("#addFeedbackForm").validate().form(); //若全部通过验证则form方法返回true
	 if(!flag) {
	     return false;
	 }
	 //获取表单数据
	 var data = $('#addFeedbackForm').serialize();
	 //发起请求
	 Chief.ajax.postJson("/feedback/insertSelective", data, function (data) {
	     if ("0" == data.code) {
	         Chief.layer.tips("保存成功", 1500);
	         setTimeout('outTips()', 1000 );
	     } else {
	         Chief.layer.tips(data.msg);
	     }
	 });
}

//保存成功提示
function outTips(){
	Chief.layer.close();
}


