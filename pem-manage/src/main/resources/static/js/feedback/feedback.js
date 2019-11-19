/**
 *@初始反馈意见模块js 
 */
$(function(){
	queryList();
	//Handlebar注册自定义方法
	Handlebars.registerHelper("getUrl", function(filePath){
		return Chief.wrapUrl(filePath);
	})
});

//状态转换
Handlebars.registerHelper('if_even', function(value,option) {
	if(value == '1'){
		return '已回复';
	}else{
		return '待回复';
	}
});

/**
 * 初始化查询列表
 */
function queryList(pageno,limit){
    if(typeof pageno == "undefined"){
        pageno = 1;
    }
    if(typeof limit == "undefined"){
        limit = 10;
    }
    var start = (pageno - 1)*limit;
    var params = $(".queryForm").serialize();

    params += "&start=" + start;
    params += "&limit=" + limit;
    Chief.ajax.postJson('/feedback/queryList', params, function(data){
        if ("0" == data.code) {
            var ht = Handlebars.compile($("#T_feedbackList").html());
            $("#J_feedbackList").html(ht(data));
            //初始化分页数据(当前页码，总数，回调查询函数)
            initPaginator(pageno, data.total, queryList);
            $("#sample-table-1").colResizable({ // 手动拖动表格
    			liveDrag:true, 
    			fixed:false,
//    			resizeMode:'overflow'
    		});
        } else {
            Chief.layer.tips("系统异常", 500);
        }
    })
}

//条件查询按钮点击
$("#queryLi").click(function(){
	queryList();
})

/** 列表上方操作按钮事件*/
$('p#buttonList').on('click', 'button', function (){
	var _this = (this);
	if("updateReply" == _this.id) {
		updateReply(); //编辑
	}else if("delReply" == _this.id) {
		delReply(); //删除
	}
    
});


//编辑回复意见打开
function updateReply() {
    var checked = $("#J_feedbackList input[type=checkbox]:checked");
    if(checked.length != 1) {
        Chief.layer.tips("请选择一条数据！");
        return;
    }
    var status = checked.eq(0).attr("data-orderStatus");
    // 状态为已回复状态，不允许发起回复
    if(status == 1){
    	Chief.layer.tips("反馈已回复!");
        return;
    }
    
    // 获取该条数据详情
	var id = checked.eq(0).attr("data-id");
    var paramId = {
        id: id
    };
    Chief.ajax.postJson('/feedback/selectByPrimaryKey',paramId, function(data) {
		if('0' == data.code){
			//filePaths回写到隐藏域
            var filePaths = writeFilePaths(data); //拼接字符串
			data.item.filePaths = filePaths; //添加filePaths属性
			
            var htmls = Handlebars.compile($("#T_editFeedback").html());
            var ht = htmls(data.item);
            //弹窗
            dialog("意见反馈回复", ht);
            //初始化表单验证规则
            formValidate("#editFeedbackForm");
		}else {
			Chief.layer.tips("系统异常", 500);
		}
    });
}


//回复意见提交
function doUpdateFeedback(){
	//获取表单数据
	var editFlag = $("#editFeedbackForm").validate().form(); //若全部通过验证则form方法返回true
  if(!editFlag) {
      return false;
  }
  var id = $("#id").val().trim();
  var replyContent = $("#replyContent").val().trim();
  data = "replyContent=" + replyContent+"&id="+id;
  //发起请求
  Chief.ajax.postJson("/feedback/doUpdateFeedback", data, function (data) {
      if("0" == data.code){
          Chief.layer.tips("保存成功", 1500);
          Chief.layer.close();
          queryList();
      }else {
          Chief.layer.tips(data.msg, 1500);
      }
  });
}

// 删除工单
function delReply() {
    var checked = $("#J_feedbackList input[type=checkbox]:checked");
    if(checked.length != 1) {
        Chief.layer.tips("请选择一条数据！");
        return;
    }
    // 获取该条数据详情
	var id = checked.eq(0).attr("data-id");
    var params = {
        id: id
    };
    Chief.layer.confirm('删除后不可恢复，确定要删除吗?', function(index){
		//执行删除
    	Chief.ajax.postJson('/feedback/deleteByPrimaryKey',params, function(data) {
    		if('0' == data.code){
                Chief.layer.tips("删除成功", 1500);
                queryList();
    		}else {
    			Chief.layer.tips("系统异常", 1500);
    		}
        });
		  
		Chief.layer.close(index);
	});
}

//弹窗 公共方法
function dialog(title, ht) {
    var height = Math.round($('body').height() * 0.95); //
    var top = Math.round(($('body').height() - height) * 0.5) + "px";
    var width = Math.round($('body').width() * 0.5) + "px"; //弹窗后为单行单列展示
    Chief.layer.newEmptyDiv(title, ht, width, height + "px", top);
    var popup_outline_height = (height - 42 - 50) + "px";
    $(".popup_outline").css("cssText", "height:" + popup_outline_height + "!important;");
}

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
                 required : "请填写回复内容"
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
    return ""
}


