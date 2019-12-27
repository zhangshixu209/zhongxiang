
function time(){
	//定义一个日期对象;
    var dateTime=new Date();
    //获得系统年份;
    var year=dateTime.getFullYear();
    //获得系统月份;
    var month=dateTime.getMonth() + 1;
    //获得系统当月分天数;
    var day=dateTime.getDate();
    //获得系统小时;
    var hours=dateTime.getHours();
    //获得系统分钟;
    var minutes=dateTime.getMinutes();
    //获得系统秒数;
    var second=dateTime.getSeconds(); 
    //获得系统星期几;
    var dayCycle=dateTime.getDay();
    //使用数组更改日期样式;
    var dayCycleArray=["日","一","二","三","四","五","六"];
    for(var i=0;i<7;i++){
        if(dayCycle==i){
           //将dayCycleArray的数赋值到系统星期几里面中去;
            dayCycle=dayCycleArray[i];
        }
    }
    month < 10 ? month='0'+month : month; 
    hours < 10 ? hours='0'+hours : hours; 
    minutes < 10 ? minutes='0'+minutes : minutes; 
    second < 10 ? second='0'+second : second; 
    //打印完整的系统日期;
    var dateStr = year + '年' + month + '月' + day + '日' + ' ' + hours + ':' + minutes + ':' + second + ' 星期' + dayCycle;
	document.getElementById("date_time").innerHTML = dateStr;
};
/**
 *  查询
 */
$(function(){
    queryList(); //加载分页信息
    time()
    setInterval("time()",1000);
    $("#userShowContainer").on("mouseenter",function(){
        $("#user_order").show();
    })
    $("#userShowContainer").on("mouseleave",function(){
        $("#user_order").hide();
    })
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
    Chief.ajax.postJson('/notice/queryMyNoticeMessageInfo', params, function(data){
        var dsa = Handlebars.compile($("#noticeList").html());
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
 * @function 检索条件，分页获取阅读与未阅读人信息
 * @param pageno 起始
 * @param limit 每页数量
 */
function queryDeatilList(pageno,limit){
    var cid=bid
    if(typeof pageno == "undefined"){
        pageno = 1;
    }
    if(typeof limit == "undefined"){
        limit = 10;
    }
    var start = (pageno - 1)*limit;
    //序列化查询Form
    params ="id="+cid+"&start=" + start + "&limit=" + limit;
    Chief.ajax.postJson('/notice/queryAllMeaasgeInfo', params, function(data){
        var dsa = Handlebars.compile($("#T_noticetpl").html());
        $("#J_tabletpl2").html(dsa(data));
        //初始化分页数据(当前页码，总数，回调查询函数)
        initPaginator(pageno,data.total, queryDeatilList,"pagination2","totalNum2");
    });
}

/**
 * 查询按钮
 */
$("#userSearch").click(function(){
    queryList();
});

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
           // dialog("查看详情", ht);
            Chief.layer.newEmptyDiv("查看详情",ht, "620px", "auto","100px");
            addEditor() ;
            indexEditor.html(data.item.item.content) ;

            //我知道了按钮
            $('#close').bind('click', function (){
                cancelFn() ;
            })
        }else {
            Chief.layer.tips("系统异常", 800);
        }
        //修改样式
        $('.ke-toolbar').css('display','none');
        $('.ke-statusbar').css('display','none');
    });
}

/**
 * @function 查看详情
 * @param id 公告id
 */
function queryDetil(id) {
    bid = id
    if(isDataNull(id)) {
        Chief.layer.tips("查询失败!")
        return;
    }
    var htmls = Handlebars.compile($("#T_readNoticeDetil").html());
    var ht = htmls();
    dialog("查看详情", ht);
    queryDeatilList() ;
    //数据格式化
    Handlebars.registerHelper('remarkName',function(v){
        console.log(v) ;
        if (v=="1"){
            return "已读" ;
        }else{
            return "未读" ;
        }
    })
    //修改样式
    $('.layui-layer-content').css('height','auto');

}

/**
 * @function 取消按钮
 */
function cancelFn(){
    //关闭弹窗
    Chief.layer.close();
}

/**
 * @function 弹窗 公共方法
 * @param title 标题
 * @param ht 模板
 */
function dialog(title, ht) {
    var height = Math.round($('body').height() * 0.95); //
    var top = Math.round(($('body').height() - height) * 0.5) + "px";
    var width = Math.round($('body').width() * 0.55) + "px"; //弹窗后为单行单列展示
    Chief.layer.newEmptyDiv(title, ht, width, "auto", top);
}

/**
 * @function 判断是否为null
 * @param str 目标
 */
function isDataNull(str) {
    if (str == null || str == "" || str == undefined) {
        return true;
    }
    return false;
}

/**
 * @function 数据格式化
 */
Handlebars.registerHelper('isReadName',function(v){
    if (v == "1"){
        return "已读" ;
    }else{
        return "未读" ;
    }
})

/**
 * @function 富文本编辑框
 */
function addEditor(){
    indexEditor = KindEditor.create('#editor', {
        //上传文件管理  指定上传文件的服务器端程序。
        uploadJson: '/pem-manage/api/workordermanage/uploadImg',
        //文件管理  指定浏览远程图片的服务器端程序。
        // fileManagerJson: 'handler/file_manager_json.ashx',
        height:"260px",
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
            console.log(url);
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

