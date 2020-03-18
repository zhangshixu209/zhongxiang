/**
 *  查询
 */
$(function(){
	queryList(); //加载分页信息
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
	Chief.ajax.postJson('/goodsType/queryGoodsTypeList', params, function(data){
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
	Chief.layer.newEmptyDiv(title, ht, width, "278px", top);
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
	formValidate("#addRotationChartForm");
});
//新增
function saveRotationChart(){
	var flag = $("#addRotationChartForm").validate().form(); //若全部通过验证则form方法返回true
	if(!flag) {
		return false;
	}

	//获取表单数据
	var data = $('#addRotationChartForm').serialize();
	//发起请求
	Chief.ajax.postJson("/goodsType/saveGoodsTypeInfo", data, function (data) {
		if(data.code == '0'){
			Chief.layer.tips("保存成功！");
			Chief.layer.close();
			queryList();
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
		Chief.ajax.postJson('/goodsType/delGoodsTypeInfo',{"id":id},function(data){
			if(data.code == '0'){
				Chief.layer.tips("删除成功！");
				setTimeout('outTips()', 2000 );
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
	Chief.ajax.postJson('/goodsType/queryGoodsTypeDetail',paramId, function(data) {
		if('0' == data.code){
			var htmls = Handlebars.compile($("#T_editRotationChart").html());
			var ht = htmls(data.item);
			//弹框编辑
			dialog("编辑", ht);
			//初始化表单验证规则
			formValidate("#editRotatonChartForm");
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

	//获取表单数据
	var data = $('#editRotationChartForm').serialize();
	//发起请求
	Chief.ajax.postJson("/goodsType/updateGoodsTypeInfo", data, function (data) {
		if("0" == data.code){
			Chief.layer.tips("保存成功", 1500);
			Chief.layer.close();
			queryList();
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
		}else {
			Chief.layer.tips("系统异常", 800);
		}
	});
}

//校验非空
function isDataNull(str) {
	if (str == null || str == "" || str == undefined) {
		return true;
	}
	return false;
}

