/**
 *  查询
 */
$(function(){
	queryList(); //加载分页信息
});

// 会员状态
Handlebars.registerHelper('statusCd', function(v) {
	if (v == '1') {
		return '正常';
	} else if (v == '2') {
		return '警告';
	} else if (v == '3') {
		return '冻结';
	} else{
		return '';
	}
});

// 性别
Handlebars.registerHelper('sexCd', function(v) {
	if (v == '1') {
		return '男';
	} else if (v == '2') {
		return '女';
	} else {
		return '';
	}
});

// 投诉状态
Handlebars.registerHelper('complaintCd', function(v) {
	if (v == '1001') {
		return '待处理';
	} else if (v == '1002') {
		return '已忽略';
	} else if (v == '1003') {
		return '警告';
	}  else if (v == '1004') {
		return '冻结';
	}  else if (v == '1005') {
		return '解冻';
	} else {
		return '';
	}
});

//获取年龄
Handlebars.registerHelper('ageCd', function(v) {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var day = myDate.getDate();
	var age = myDate.getFullYear() - v.substring(0, 4) - 1;
	if (v.substring(4, 6) < month || v.substring(4, 6) == month && v.substring(6, 8) <= day) {
		age++;
	}
	return age;
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
	Chief.ajax.postJson('/groupComplaint/queryGroupComplaintList', params, function(data){
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

// 后台充值广告费，钱包余额
$('.pull-right').on('click','#audit_btn', function (){
	var checked = $("#J_tabletpl input[type=checkbox]:checked");
	if(checked.length != 1) {
		Chief.layer.tips("请选择一条数据！");
		return;
	}
	// 获取该条数据详情
	var id = checked.eq(0).attr("data-id");
	var auditType = checked.eq(0).attr("data-auditType");
	var paramId = {
		id: id
	};
	Chief.ajax.postJson('/complaint/queryMemberComplaintDetail',paramId, function(data) {
		if('0' == data.code){
			var htmls = Handlebars.compile($("#T_complaintTpl").html());
			var ht = htmls(data.item);
			//弹框编辑
			dialog("投诉状态审核", ht);
			//初始化表单验证规则
			formValidate("#memberComplaintForm");
			$("#complaintStatus").find("option[value="+data.item.status+"]").attr("selected", "true");
		}else {
			Chief.layer.tips("系统异常", 1500);
		}
	});
});

// 修改会员投诉状态
function updateMemberComplaintInfo() {
	var flag = $("#memberComplaintForm").validate().form(); //若全部通过验证则form方法返回true
	if(!flag) {
		return false;
	}
	//获取表单数据
	var data = $('#memberComplaintForm').serialize();
	//发起请求
	Chief.ajax.postJson("/complaint/updateMemberComplaintInfo", data, function (data) {
		if(data.code == '0'){
			Chief.layer.tips("保存成功！");
			setTimeout('outTips()', 1000 );
		}else{
			Chief.layer.tips(data.msg);
		}
	});
}

//成功提示
function outTips(){
	Chief.layer.close();
	queryList();
}

//数据校验
function formValidate(){
	// 会员充值校验
	$("#memberComplaintForm").validate({
		errorPlacement: function(error, element) { //验证消息放置的位置
			error.appendTo(element.parent()).addClass('mValidate');
		},
		onfocusout: function(element) {
			$(element).valid();
		}
	});
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

