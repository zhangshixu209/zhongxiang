/**
 *  查询
 */
$(function(){
	queryList(); //加载分页信息
	//Handlebar注册自定义方法
	Handlebars.registerHelper("getUrl", function(filePath){
		return Chief.wrapUrl(filePath);
	})
});

// 备份还原状态
Handlebars.registerHelper('statusCd', function(v) {
	if (v == '1') {
		return '备份';
	} else if (v == '2') {
		return '还原';
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
	Chief.ajax.postJson('/sys/backups/queryMySqlList', params, function(data){
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
	Chief.layer.newEmptyDiv(title, ht, "548px", "247px", top);
	//弹窗中间内容的高度-自动
	// Chief.layer.newEmptyDiv(title, ht, width, "auto", top);
	//弹窗中间内容的高度-自动
 	var popup_outline_height = ($(".layui-layer-content").height() - 50) + "px";
	// var popup_outline_height = (height - 42 - 50) + "px";
	$(".popup_outline").css("cssText", "height:" + popup_outline_height + "!important;");
}

// 后台充值广告费，钱包余额
$('.pull-right').on('click','#bf_btn', function (){
	var htmls = Handlebars.compile($("#T_complaintTpl").html());
	var ht = htmls();
	//弹框编辑
	dialog("备份", ht);
});

// 备份
function updateMemberComplaintInfo() {
	var flag = $("#memberComplaintForm").validate().form(); //若全部通过验证则form方法返回true
	if(!flag) {
		return false;
	}
	//获取表单数据
	var data = $('#memberComplaintForm').serialize();
	Chief.ajax.postJson('/sys/backups/backupsDBInfo',data,function(data){
		if(data.code == '0'){
			Chief.layer.tips("备份成功！");
			setTimeout('outTips()', 1000 );
		} else {
			Chief.layer.tips(data.msg);
		}
	});
}

// 后台充值广告费，钱包余额
$('.pull-right').on('click','#hy_btn', function (){
	var checked = $("#J_tabletpl input[type=checkbox]:checked");
	if(checked.length != 1) {
		Chief.layer.tips("请选择一条数据！");
		return;
	}
	var id = checked.eq(0).attr("data-id");
	$("#sqlUrl").val(id);
	var paramId = {
		id: id
	};
	Chief.ajax.postJson('/sys/backups/queryMySqlList',paramId, function(data) {
		if('0' == data.code){
			var htmls = Handlebars.compile($("#T_complaintHYTpl").html());
			var ht = htmls(data.items[0]);
			//弹框编辑
			dialog("还原", ht);
			//初始化表单验证规则
		} else {
			Chief.layer.tips("系统异常", 1500);
		}
	});

});

function updateMemberComplaintHYInfo() {
	var flag = $("#memberComplaintHYForm").validate().form(); //若全部通过验证则form方法返回true
	if(!flag) {
		return false;
	}
	//获取表单数据
	var data = $('#memberComplaintHYForm').serialize();
	Chief.ajax.postJson('/sys/backups/reductionDBInfo',data,function(data){
		if(data.code == '0'){
			Chief.layer.tips("还原成功！");
			setTimeout('outTips()', 1000 );
		} else {
			Chief.layer.tips(data.msg);
		}
	});
}

//成功提示
function outTips(){
	Chief.layer.close();
	queryList();
}

//取消按钮关闭弹窗
function doCancel(){
	Chief.layer.close();
}

//校验非空
function isDataNull(str) {
	if (str == null || str == "" || str == undefined) {
		return true;
	}
	return false;
}

