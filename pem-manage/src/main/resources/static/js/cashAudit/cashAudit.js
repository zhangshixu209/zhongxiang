/**
 *  查询
 */
$(function(){
	queryList(); //加载分页信息
});

// 审核状态
Handlebars.registerHelper('auditTypeCd', function(v) {
	if (v == '1') {
		return '审核通过';
	} else if (v == '2') {
		return '审核不通过';
	} else if (v == '3') {
		return '待审核';
	} else{
		return '';
	}
});

// 账号类型
Handlebars.registerHelper('accountTypeCd', function(v) {
	if (v == '1001') {
		return '支付宝';
	} else if (v == '1002') {
		return '微信';
	} else if (v == '1003') {
		return '银行卡';
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
	Chief.ajax.postJson('/cashAudit/queryCashAuditList', params, function(data){
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
	// Chief.layer.newEmptyDiv(title, ht, width, height + "px", top);
	//弹窗中间内容的高度-自动
	Chief.layer.newEmptyDiv(title, ht, width, "auto", top);
	//弹窗中间内容的高度-自动
 	var popup_outline_height = ($(".layui-layer-content").height() - 50) + "px";
	// var popup_outline_height = (height - 42 - 50) + "px";
	$(".popup_outline").css("cssText", "height:" + popup_outline_height + "!important;");
}

//成功提示
function outTips(){
	Chief.layer.close();
	queryList();
}

//点击编辑按钮
$('.pull-right').on('click','#audit_btn', function (){
	var checked = $("#J_tabletpl input[type=checkbox]:checked");
	if(checked.length != 1) {
		Chief.layer.tips("请选择一条数据！");
		return;
	}
	// 获取该条数据详情
	var id = checked.eq(0).attr("data-id");
	var auditType = checked.eq(0).attr("data-auditType");
	if (auditType != "3") {
		Chief.layer.tips("只能操作待审核数据！");
		return;
	}
	var paramId = {
		id: id
	};
	Chief.ajax.postJson('/cashAudit/queryCashAuditDetail',paramId, function(data) {
		if('0' == data.code){
			var htmls = Handlebars.compile($("#T_auditCashAudit").html());
			var ht = htmls(data.item);
			//弹框编辑
			dialog("提现审核", ht);
			//初始化表单验证规则
			formValidate("#auditCashAuditForm");
		}else {
			Chief.layer.tips("系统异常", 1500);
		}
	});
});

/**
 * 编辑 保存
 */
function updateCashAudit(val){
	if(val == "2"){
		var auditOpinion = $("#auditOpinion").val();
		if (isDataNull(auditOpinion)) {
			Chief.layer.tips("请填写审核意见，不通过原因！");
			return;
		}
	}
	//获取表单数据
	var data = $('#auditCashAuditForm').serialize();
	data += "&auditType=" + val; // 审核状态
	//发起请求
	Chief.ajax.postJson("/cashAudit/updateCashAuditInfo", data, function (data) {
		if("0" == data.code){
			Chief.layer.tips("处理成功", 1500);
			setTimeout('outTips()', 2000 );
		} else if("-1" == data.code){
			Chief.layer.tips(data.msg, 2000);
		} else {
			Chief.layer.tips("处理失败", 1500);
		}
	});
};

//数据校验
function formValidate(){
	// 审核验证
	$("#auditCashAuditForm").validate({
		errorPlacement: function(error, element) { //验证消息放置的位置
			error.appendTo(element.parent());
		},
		onfocusout: function(element) {
			$(element).valid();
		}
	});
}

//任务导出
$("#export_btn").on("click",function(){
	var params = $('.searchForm').serialize();
	Chief.ajax.postJson('/cashAudit/queryCashAuditList', params, function(data){
		if (data.code=="0") {
			if (data.total>0) {
				window.location.href="../api/cashAudit/exportMoney?" + encodeURI(encodeURI(params));
			} else {
				Chief.layer.tips('没有数据导出');
			}
		} else {
			Chief.layer.tips(data.msg);
		}
	});
});

//校验非空
function isDataNull(str) {
	if (str == null || str == "" || str == undefined) {
		return true;
	}
	return false;
}

