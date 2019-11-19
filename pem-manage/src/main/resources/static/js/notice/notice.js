var people_id =[]; // 定义全局已选取人员的id数组
/**
 *  查询
 */
$(function(){
    queryList(); //加载分页信息
    queryNoticeStatus(); //初始化工单状态
    getDeepartmentUserTree();//初始化加载人员名单
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
    Chief.ajax.postJson('/notice/queryNoticeMessageInfo', params, function(data){
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

/**
 * @function 检索条件，分页获取阅读人信息
 * @param pageno 起始
 * @param limit 每页数量
 */
function queryReadList(pageno,limit){
    var noticeId = bid ;
    if(typeof pageno == "undefined"){
        pageno = 1;
    }
    if(typeof limit == "undefined"){
        limit = 10;
    }
    var start = (pageno - 1)*limit;
    //序列化查询Form
    params ="id="+noticeId+"&start=" + start + "&limit=" + limit;
    Chief.ajax.postJson('/notice/queryReadMessageInfo', params, function(data){
        var dsa = Handlebars.compile($("#T_noticetpl").html());
        $("#J_tabletpl2").html(dsa(data));
        //初始化分页数据(当前页码，总数，回调查询函数)
        initPaginator(pageno,data.total, queryReadList,"pagination2","totalNum2");
    });
}


/**
 * @function 检索条件，分页获取未阅读人信息
 * @param pageno 起始
 * @param limit 每页数量
 */
function queryUnReadList(pageno,limit){
    var noticeId = cid ;
    if(typeof pageno == "undefined"){
        pageno = 1;
    }
    if(typeof limit == "undefined"){
        limit = 10;
    }
    var start = (pageno - 1)*limit;
    //序列化查询Form
    params ="id="+noticeId+"&start=" + start + "&limit=" + limit;
    Chief.ajax.postJson('/notice/queryUnReadMeaasgeInfo', params, function(data){
        var dsa = Handlebars.compile($("#T_noticetp2").html());
        $("#J_tabletpl3").html(dsa(data));
        //初始化分页数据(当前页码，总数，回调查询函数)
        initPaginator(pageno,data.total, queryUnReadList,"pagination3","totalNum3");
    });
}


/**
 * @function 检索条件，分页获取阅读与未阅读人信息
 * @param pageno 起始
 * @param limit 每页数量
 */
function queryDeatilList(pageno,limit){
    var noticeId = did ;
    if(typeof pageno == "undefined"){
        pageno = 1;
    }
    if(typeof limit == "undefined"){
        limit = 10;
    }
    var start = (pageno - 1)*limit;
    //序列化查询Form
    params ="id="+noticeId+"&start=" + start + "&limit=" + limit;
    Chief.ajax.postJson('/notice/queryAllMeaasgeInfo', params, function(data){
        var dsa = Handlebars.compile($("#T_noticetp3").html());
        $("#J_tabletpl4").html(dsa(data));
        //初始化分页数据(当前页码，总数，回调查询函数)
        initPaginator(pageno,data.total, queryDeatilList,"pagination4","totalNum4");
    });
}

/**
 *  查询按钮
 */
$("#J_search").click(function(){
    queryList();
});

/**
 * @function 公告状态下拉列表加载
 */
function queryNoticeStatus() {
    var params = {}
    Chief.ajax.postJson("/notice/queryNoticeStatusMessageInfo", params, function (data) {
        if(data.code == "0") {
            var shtml = Handlebars.compile($("#T_noticeStatustpl").html());
            var dataHtml = shtml(data);
            $("#status").html(dataHtml);
        }else {
            Chief.layer.tips("系统异常")
        }
    }, false);
}

/**
 * @function 加载公告类型并设置选中状态
 */
function loadNoticeType(item){
    Chief.ajax.postJson('/noticetype/queryAllNoticetypeMessageInfo', "", function(data){
        if(data.code == "0") {
            var html = Handlebars.compile($("#T_noticetypetpl").html()); //编译模板
            var dataHtml = html(data); //传入数据
            $("#editNoticeType").html(dataHtml); //加载select框
            if(item) {
                //设置选中
                $("#editNoticeType").val(item.noticeTypeId);
            }
        }else {
            Chief.layer.tips("查询业务区名称失败", 500);
        }

    });
}

/**
 * @function 加载公告类型
 */
function queryNoticeType() {
    var params = {}
    Chief.ajax.postJson("/noticetype/queryAllNoticetypeMessageInfo", params, function (data) {
        if(data.code == "0") {
            var shtml = Handlebars.compile($("#T_noticetypetpl").html());
            var dataHtml = shtml(data);
            $("#noticeType").html(dataHtml);
        }else {
            Chief.layer.tips("系统异常")
        }
    }, false);
}

/**
 * @function 加载紧急程度下拉并设置选中
 */
function loadInstancyLevel(item){
    Chief.ajax.postJson('/notice/queryInstancyLevelMessageInfo', "", function(data){
        if(data.code == "0") {
            var html = Handlebars.compile($("#T_instancyLeveltpl").html()); //编译模板
            var dataHtml = html(data); //传入数据
            $("#editInstancyLevel").html(dataHtml); //加载select框
            if(item) {
                //设置选中
                $("#editInstancyLevel").val(item.instancyLevelId);
            }
        }else {
            Chief.layer.tips("查询业务区名称失败", 500);
        }

    });
}

/**
 * @function 加载紧急程度下拉
 */
function queryInstancyLevel() {
    var params = {}
    Chief.ajax.postJson("/notice/queryInstancyLevelMessageInfo", params, function (data) {
        if(data.code == "0") {
            var shtml = Handlebars.compile($("#T_instancyLeveltpl").html());
            var dataHtml = shtml(data);
            $("#instancyLevel").html(dataHtml);
        }else {
            Chief.layer.tips("系统异常")
        }
    }, false);
}

/**
 *  编辑按钮
 */
$('#buttonList').on('click','#editParamManage', function (){
    var checked = $("#J_tabletpl input[type=checkbox]:checked");
    if(checked.length != 1) {
        Chief.layer.tips("请选择一条数据！");
        return;
    }
    var statusId = checked.eq(0).attr("data-status");
    if(statusId=="1003"){
        Chief.layer.tips("已发布的公告不可修改！");
        return ;
    }
    // 获取该条数据详情
    var id = checked.eq(0).attr("data-id");
    var paramId = {
        id: id
    };
    Chief.ajax.postJson('/notice/queryNoticeDetailMessageInfo',paramId, function(data) {
        if('0' == data.code){
            var htmls = Handlebars.compile($("#T_editNotice").html());
            var ht = htmls(data.item);
            dialog2("编辑公告",ht);
//            Chief.layer.newEmptyDiv("编辑公告", ht,'750px', '520px', '40px');
            //加载富文本编辑框
            updateEditor() ;
            //设置富文本编辑框的值
            upEditor.html(data.item.content) ;
            //加载公告类型下拉并设置选中
            loadNoticeType(data.item) ;
            //加载紧急程度下拉,并设置选中
            loadInstancyLevel(data.item) ;
            //获取到sendTotal设置单选框对象
            var sendTotal = data.item.sendTotal ;
            if(sendTotal=='-9999'){
                $("#editAllPeople").attr("checked",true) ;
            }else{
                $("#editSomePeople").attr("checked",true) ;
                $("#selectPeople").show() ;
                loadPermission(id) ;
            }

            //存草稿按钮
            $('#editSaveDraft').bind('click', function (){
                var flag = $("#editNoticeForm").validate().form(); //若全部通过验证则form方法返回true
                if(flag) {
                    editSaveDraf();
                }else {
                    Chief.layer.tips("请按提示输入正确的内容");
                    return false;
                }
            })
            //取消按钮
            $('#editCancel').bind('click', function (){
                cancelFn() ;
            })
            //发布
            $('#editSave').bind('click', function (){
                var flag = $("#editNoticeForm").validate().form(); //若全部通过验证则form方法返回true
                if(flag) {
                    editSavePublish() ;
                }else {
                    Chief.layer.tips("请按提示输入正确的内容");
                    return false;
                }
            })
            //初始化表单验证规则
            formValidate();
            //getDeepartmentUserTree();//初始化加载人员名单

        }else {
            Chief.layer.tips("系统异常", 500);
        }
    });
})

/**
 *  创建按钮
 */
$('#buttonList').on('click','#addParamManage', function (){
    var htmls = Handlebars.compile($("#T_addNotice").html());
    var ht = htmls();
    dialog2("创建公告",ht);
//    Chief.layer.newEmptyDiv("创建公告", ht,'750px', '480px', '20px');
    addEditor() ;
    //存草稿按钮
    $('#saveDraft').bind('click', function (){
     	 var flag = $("#addNoticeForm").validate().form(); //若全部通过验证则form方法返回true
     	    if(flag) {
     	        saveDraf();
     	    }else {
     	        Chief.layer.tips("请按提示输入正确的内容");
     	        return false;
     	    }
     })
    //取消按钮
    $('#cancel').bind('click', function (){
        cancelFn() ;
    })
    //发布
    $('#save').bind('click', function (){
        var flag = $("#addNoticeForm").validate().form(); //若全部通过验证则form方法返回true
        if(flag) {
            savePublish() ;
        }else {
            Chief.layer.tips("请按提示输入正确的内容");
            return false;
        }
    })
    //加载公告类型下拉
    queryNoticeType();
    //加载紧急程度下拉
    queryInstancyLevel() ;
    //初始化表单验证规则
    formValidate();
    //getDeepartmentUserTree();//初始化加载人员名单
});

/**
 *  删除按钮
 */
$('#buttonList').on('click','#deleteParamManage', function (){
    var checked = $("#J_tabletpl input[type=checkbox]:checked");
    if(checked.length != 1) {
        Chief.layer.tips("请选择一条数据！");
        return;
    }
    var statusId = checked.eq(0).attr("data-status");
    if(statusId =='1003'){
        Chief.layer.tips("已发布的公告不可执行此操作！");
        return;
    }
    // 获取该条数据详情
    var id = checked.eq(0).attr("data-id");
    var paramId = {
        id: id
    };

    Chief.layer.confirm('确定要删除吗?', function(index){
        //执行删除
        Chief.ajax.postJson('/notice/deleteNoticeMessageInfo',paramId, function(data) {
            if('0' == data.code){
                Chief.layer.tips("删除成功", 800);
                queryList();
            }else {
                Chief.layer.tips("系统异常", 800);
            }
        });

        Chief.layer.close(index);
    });
}) ;

/**
 *  发布按钮
 */
$('#buttonList').on('click','#publish', function (){
    var checked = $("#J_tabletpl input[type=checkbox]:checked");
    if(checked.length != 1) {
        Chief.layer.tips("请选择一条数据！");
        return;
    }
    var statusId = checked.eq(0).attr("data-status");
    if(statusId=="1003"){
        Chief.layer.tips("已发布的公告不可进行此操作！");
        return ;
    }
    // 获取该条数据详情
    var id = checked.eq(0).attr("data-id");
    //console.log(id) ;
    var paramId = {
        id: id
    };
    Chief.layer.confirm('确定要发布吗?', function(index){
        //执行发布
        Chief.ajax.postJson('/notice/publishBtnNoticeMessageInfo',paramId, function(data) {
            if('0' == data.code){
                Chief.layer.tips("发布成功", 800);
                queryList();
            }else if('-1' == data.code){
                Chief.layer.tips(data.msg, 800);
            }else{
                Chief.layer.tips("系统异常", 800);
            }
        });

        Chief.layer.close(index);
    });
}) ;

/**
 *  撤回按钮
 */
$('#buttonList').on('click','#recall', function (){
    var checked = $("#J_tabletpl input[type=checkbox]:checked");
    if(checked.length != 1) {
        Chief.layer.tips("请选择一条数据！");
        return;
    }
    // 获取该条数据详情
    var id = checked.eq(0).attr("data-id");
    //console.log(id) ;
    var paramId = {
        id: id
    };

    Chief.layer.confirm('确定要撤回吗?', function(index){
        //执行发布
        Chief.ajax.postJson('/notice/recallBtnNoticeMessageInfo',paramId, function(data) {
            if('0' == data.code){
                Chief.layer.tips("撤回成功", 800);
                queryList();
            }else if('-1'==data.code){
                Chief.layer.tips(data.msg);
            }else if('-2'==data.code){
                Chief.layer.tips(data.msg);
            }else {
                Chief.layer.tips("系统异常", 800);
            }
        });

        Chief.layer.close(index);
    });
}) ;

/**
 * @function 点击公告主题展示详情按钮
 * @param id 公告id
 */
function queryNotceiDetail(id) {
    if(isDataNull(id)) {
        Chief.layer.tips("查询失败!")
        return;
    }
    var params = {id: id}
    Chief.ajax.postJson('/notice/queryNoticeContentById',params, function(data) {
        if('0' == data.code){
            var htmls = Handlebars.compile($("#T_noticeDeatil").html());
            var ht = htmls(data.item);
            dialog("查看详情", ht);
            loadEditorDetail() ;
            loadEditor.html(data.item.item.content) ;
            $('#close').bind('click', function (){
                cancelFn() ;
            })
        }else {
            Chief.layer.tips("系统异常", 1500);
        }
        //修改样式
        $('.ke-toolbar').css('display','none');
        $('.ke-statusbar').css('display','none');
    });
}

/**
 * @function 点击查看阅读人信息
 * @param id 公告id
 */
function queryReadDetil(id) {
    bid = id ;
    if(isDataNull(id)) {
        Chief.layer.tips("查询失败!")
        return;
    }
    var htmls = Handlebars.compile($("#T_readNoticeDetil").html());
    var ht = htmls();
    queryReadList() ;
    dialog("查看阅读人信息", ht);
    //数据格式化
    Handlebars.registerHelper('remarkName',function(v){
        //console.log(v) ;
        if (v=="1"){
            return "已读" ;
        }else{
            return "未读" ;
        }
    })

}

/**
 * @function 点击查看未阅读人信息
 * @param id 公告id
 */
function queryUnReadDetil(id) {
    cid = id ;
    if(isDataNull(id)) {
        Chief.layer.tips("查询失败!")
        return;
    }
    var htmls = Handlebars.compile($("#T_unReadNoticeDetil").html());
    var ht = htmls();
    queryUnReadList() ;
    dialog("查看未阅读人信息", ht);
    //数据格式化
    Handlebars.registerHelper('remarkName',function(v){
        //console.log(v) ;
        if (v=="1"){
            return "已读" ;
        }else{
            return "未读" ;
        }
    })

}

/**
 * @function 查看详情
 * @param id 公告id
 */
function queryDetil(id) {
    did = id ;
    if(isDataNull(id)) {
        Chief.layer.tips("查询失败!")
        return;
    }
    var htmls = Handlebars.compile($("#T_allAeadNoticeDetil").html());
    var ht = htmls();
    queryDeatilList() ;
    dialog("查看详情", ht);
    //数据格式化
    Handlebars.registerHelper('remarkName',function(v){
        //console.log(v) ;
        if (v=="1"){
            return "已读" ;
        }else{
            return "未读" ;
        }
    })
}

/**
 * @function 按钮隐藏与展示
 * @param val 是否选择全部人员 1全部  0部分
 */
function disableOrshow(val){
    if(val==1){
        $("#selectPeople").hide() ;
    }else{
        $("#selectPeople").show() ;
        loadPeople();
    }
}

/**
 * @function 点击选择对象加载树结构
 */
function loadPeople(){
    getDeepartmentUserTree();//初始化加载人员名单
}

/**
 * @function 点击选择对象加载树结构
 */
function loadPermission(id){
    getNoticePermissionTree(id) ;
}

/**
 * @function 取消按钮
 */
function cancelFn(){
    //关闭弹窗
    Chief.layer.close();
}

/**
 * @function 创建存草稿
 */
function saveDraf(){
    //var data = $('#addNoticeForm').serialize();
    // 换一种拼参方式,以Map形式
	var params = {}
    var formArray = $('#addNoticeForm').serializeArray();
	$.each(formArray,function(i,item){
		params[item.name] = item.value;
	});
	
    var noticeTypeName =$("#noticeType option:selected").text().trim();
    var instancyLevelName =$("#instancyLevel option:selected").text().trim();
    var content = editor.html();
    saveProjMonitorList() ;
    var peopleIds = JSON.stringify(people_id);

    //增加参数
    params['noticeTypeName'] = noticeTypeName;
    params['instancyLevelName'] = instancyLevelName;
    params['peopleIds'] = peopleIds;
    params['content'] = content;
    Chief.ajax.postJson("/notice/insertNoticeMessageInfo",params,function(data){
        if("0" == data.code){
            Chief.layer.tips("创建成功", 800);
            Chief.layer.close();
            queryList();
        }else {
            Chief.layer.tips("创建失败", 800);
        }
    });
}

/**
 * @function 编辑存草稿
 */
function editSaveDraf(){
	//var data = $('#editNoticeForm').serialize();
	// 换一种拼参方式,以Map形式
	var params = {}
    var formArray = $('#editNoticeForm').serializeArray();
	$.each(formArray,function(i,item){
		params[item.name] = item.value;
	});
	
    var noticeTypeName =$("#editNoticeType option:selected").text().trim();
    var instancyLevelName =$("#editInstancyLevel option:selected").text().trim();
    var content = upEditor.html();
    saveProjMonitorList() ;
    var peopleIds = JSON.stringify(people_id);
    //增加参数
    params['noticeTypeName'] = noticeTypeName;
    params['instancyLevelName'] = instancyLevelName;
    params['peopleIds'] = peopleIds;
    params['content'] = content;
    //console.log(data) ;
    Chief.ajax.postJson("/notice/updateNoticeMessageInfo",params,function(data){
        if("0" == data.code){
            Chief.layer.tips("创建成功", 800);
            Chief.layer.close();
            queryList();
        }else {
            Chief.layer.tips("创建失败", 800);
        }
    });
}

/**
 * @function 编辑发布
 */
function editSavePublish(){
    //var data = $('#editNoticeForm').serialize();
    // 换一种拼参方式,以Map形式
	var params = {}
    var formArray = $('#editNoticeForm').serializeArray();
	$.each(formArray,function(i,item){
		params[item.name] = item.value;
	});
    var noticeTypeName =$("#editNoticeType option:selected").text().trim();
    var instancyLevelName =$("#editInstancyLevel option:selected").text().trim();
    var content = upEditor.html() ;
    saveProjMonitorList() ;
    //console.log(people_id) ;
    var peopleIds = JSON.stringify(people_id);
    
    //增加参数
    params['noticeTypeName'] = noticeTypeName;
    params['instancyLevelName'] = instancyLevelName;
    params['peopleIds'] = peopleIds;
    params['content'] = content;
    //console.log(data) ;
    Chief.ajax.postJson("/notice/editPublishNoticeMessageInfo",params,function(data){
        if("0" == data.code){
            Chief.layer.tips("创建成功", 3000);
            Chief.layer.close();
            queryList();
        }else {
            Chief.layer.tips("创建失败", 3000);
        }
    });
}

/**
 * @function 创建发布
 */
function savePublish(){
    //var data = $('#addNoticeForm').serialize();
    // 换一种拼参方式,以Map形式
	var params = {}
    var formArray = $('#addNoticeForm').serializeArray();
	$.each(formArray,function(i,item){
		params[item.name] = item.value;
	});
	
    var noticeTypeName =$("#noticeType option:selected").text().trim();
    var instancyLevelName =$("#instancyLevel option:selected").text().trim();
    var content = editor.html();
    //console.log(content) ;
    saveProjMonitorList() ;
    //console.log(people_id) ;
    var peopleIds = JSON.stringify(people_id);
    //console.log(peopleIds) ;
    
    //增加参数
    params['noticeTypeName'] = noticeTypeName;
    params['instancyLevelName'] = instancyLevelName;
    params['peopleIds'] = peopleIds;
    params['content'] = content;
    //console.log(data) ;
    Chief.ajax.postJson("/notice/publishNoticeMessageInfo",params,function(data){
        if("0" == data.code){
            Chief.layer.tips("创建成功", 3000);
            Chief.layer.close();
            queryList();
        }else {
            Chief.layer.tips("创建失败", 3000);
        }
    });
}

/**
 * @function 获取权限树结果底层的人员id
 */
function saveProjMonitorList(){
    if($(".people:checked").val() == "0" && ZTree.tree.getCheckedNodes().length == 0){ //判断选择部分的话 是否选择人员
        //$("#addNoticeForm").html()
        Chief.layer.tips("请选择人员");
        return false;
    }else if($(".people:checked").val() == "0" && ZTree.tree.getCheckedNodes().length != 0){
        var checkedPeple = ZTree.tree.getCheckedNodes();
            people_id = [];
        $.each(checkedPeple,function(index,item){
            if(!item.children){
                people_id.push(item.id);
            }
        })
    }else{ // 这是选择全部人员

    }
}

/**
 * @function 弹窗 公共方法--高度自动
 * @param title 标题
 * @param ht 模板
 */
function dialog(title, ht) {
    var height = Math.round($('body').height() * 0.95); //
    var top = Math.round(($('body').height() - height) * 0.5) + "px";
    var width = Math.round($('body').width() * 0.55) + "px"; //弹窗后为单行单列展示
    Chief.layer.newEmptyDiv(title, ht, width, "auto", top);
    //弹窗中间内容的高度-自动
//  var popup_outline_height = ($(".layui-layer-content").height() - 50) + "px";
    //42-弹窗背景高度减去弹窗内容之外高度		50-按钮整体高度
    var popup_outline_height = (height - 42 - 50) + "px";
   // $(".popup_outline").css("cssText", "height:" + popup_outline_height + "!important;");
    console.log(height,$(".layui-layer-content").height(),popup_outline_height);
}

/**
 * @function 弹窗 公共方法--高度自动
 * @param title 标题
 * @param ht 模板
 */
function dialog2(title, ht) {
    var height = Math.round($('body').height() * 0.95); //
    var top = Math.round(($('body').height() - height) * 0.5) + "px";
    Chief.layer.newEmptyDiv(title, ht, '850px', height + "px", top);
    //弹窗中间内容的高度-自动
//  var popup_outline_height = ($(".layui-layer-content").height() - 50) + "px";
    //42-弹窗背景高度减去弹窗内容之外高度		50-按钮整体高度
    var popup_outline_height = (height - 42 - 50) + "px";
    $(".popup_outline").css("cssText", "height:" + popup_outline_height + "!important;");
//    console.log(height,$(".layui-layer-content").height(),popup_outline_height);
}



/**
 * @function 数据校验规则
 */
function formValidate(){
    $("#addNoticeForm").validate({
        errorPlacement: function(error, element) { //验证消息放置的位置
//			error.insertAfter(element.parent());
//          error.appendTo(element.parent()).addClass('bfuid');
            error.appendTo(element.parent());
        },
        onfocusout: function(element) {
            $(element).valid();
        }
    });

    $("#editNoticeForm").validate({
        errorPlacement: function(error, element) { //验证消息放置的位置
//			error.insertAfter(element.parent());
            error.appendTo(element.parent()).addClass('bfuid');
        },
        onfocusout: function(element) {
            $(element).valid();
        }
    });
}

/**
 * @function 是否为null判断
 */
function isDataNull(str) {
    if (str == null || str == "" || str == undefined) {
        return true;
    }
    return false;
}

/**
 * @function 新增富文本编辑框
 */
function addEditor(){
     editor = KindEditor.create('#editor', {
        //上传文件管理  指定上传文件的服务器端程序。
        uploadJson: '/report-manage/api/noticefilemanage/uploadImg',
        //文件管理  指定浏览远程图片的服务器端程序。
        // fileManagerJson: 'handler/file_manager_json.ashx',
        height:"220",
        //编辑器宽度
        width: '78%',
        //禁止拖动
        resizeType: 0,
        allowImageRemote : false,
        allowImageUpload:true,
        //编辑器的显示功能
        items:[   //'print',  打印功能
            'source', '|', 'undo', 'redo', '|', 'preview',  'template', 'cut', 'copy', 'paste',
            'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
            'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
            'superscript', 'clearhtml', 'quickformat', 'selectall', '|',  '/',
            'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
            'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',
            'table', 'hr', 'emoticons', '|', 'image'
        ],
        afterBlur: function () { this.sync(); } ,
        // 上传文件后执行的回调函数
        afterUpload : function(url) {
            console.log(url);
        },
        filePostName:"file"
    });

}

/**
 * @function 编辑富文本编辑框
 */
function updateEditor(){
    upEditor = KindEditor.create('#updateEditor', {
        //上传文件管理  指定上传文件的服务器端程序。
        uploadJson: '/report-manage/api/noticefilemanage/uploadImg',
        //文件管理  指定浏览远程图片的服务器端程序。
        // fileManagerJson: 'handler/file_manager_json.ashx',
        height:"300px",
        //编辑器宽度
        width: '78%',
        //禁止拖动
        resizeType: 0,
        //展示网络图片标签false不显示
        allowImageRemote : false,
        //编辑器的显示功能
        items:[ 
              'source', '|', 'undo', 'redo', '|', 'preview',  'template', 'cut', 'copy', 'paste',
              'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
              'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
              'superscript', 'clearhtml', 'quickformat', 'selectall', '|',  '/',
              'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
              'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',
              'table', 'hr', 'emoticons', '|', 'image'
        ],
        afterBlur: function () { this.sync(); } ,
        // 上传文件后执行的回调函数
        afterUpload : function(url) {
        },
        // 上传图片、Flash、视音频、文件时，支持添加别的参数一并传到服务器。
        /*extraFileUploadParams: {
            file : "test.jpg",
            item_id : 1000,
            category_id : 1
        } ,
        extraParams:{
            file : "test.jpg",
            item_id : 1000
        },*/
        filePostName:"file"
    });

}

/**
 * @function 点击公告主题富文本编辑框加载
 */
function loadEditorDetail(){
    loadEditor = KindEditor.create('#detailEditor', {
        //上传文件管理  指定上传文件的服务器端程序。
        uploadJson: '/report-manage/api/workordermanage/uploadImg',
        //文件管理  指定浏览远程图片的服务器端程序。
        // fileManagerJson: 'handler/file_manager_json.ashx',
        height:"250",
        //编辑器宽度
        width: '100%',
        //禁止拖动
        resizeType: 0,
        //设置只读
        readonlyMode : true,
        allowImageRemote : false,
        //编辑器的显示功能
        items:[],
        afterBlur: function () { this.sync(); } ,
        // 上传文件后执行的回调函数
        afterUpload : function(url) {
        },
        // 上传图片、Flash、视音频、文件时，支持添加别的参数一并传到服务器。
        extraFileUploadParams: {
            file : "test.jpg",
            item_id : 1000,
            category_id : 1
        } ,
        extraParams:{
            file : "test.jpg",
            item_id : 1000
        },
        filePostName:"file"
    });

}

