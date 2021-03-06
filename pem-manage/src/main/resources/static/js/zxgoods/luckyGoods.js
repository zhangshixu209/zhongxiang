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

// 认证信息
Handlebars.registerHelper('businessTypeCd', function(v) {
	if (v == '1') {
		return '个人';
	} else if (v == '2') {
		return '公司';
	} else{
		return '';
	}
});

// 审核状态
Handlebars.registerHelper('goodsStatusCd', function(v) {
	if (v == '1001') {
		return '待审核';
	} else if (v == '1002') {
		return '审核通过';
	} else if (v == '1003') {
		return '审核不通过';
	}  else if (v == '1004') {
		return '已上架';
	}  else if (v == '1005') {
		return '已下架';
	}  else if (v == '1006') {
		return '已结束';
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
	Chief.ajax.postJson('/lucky/queryLuckyGoodsList', params, function(data){
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
	if (auditType != "1001") {
		Chief.layer.tips("只能操作待审核数据！");
		return;
	}
	var paramId = {
		id: id
	};
	Chief.ajax.postJson('/lucky/queryLuckyGoodsDetail',paramId, function(data) {
		if('0' == data.code){
			var htmls = Handlebars.compile($("#T_auditCashAudit").html());
			var chatList = data.item.goodsExplainImg.split("$");
			var fileList = [];
			$.each(chatList, function (i, v) {
				var listArr = {
					filePath: v
				};
				fileList.push(listArr);
			});
			// var list = {fileList: fileList}
			data.item.goodsExplainImg = fileList;
			var ht = htmls(data.item);
			//弹框编辑
			dialog("商品审核", ht);
			$(".min_img").click(function(){
				var _this = $(this);//将当前的img元素作为_this传入函数
				imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
			});
			if (data.item.isHaveShop == "1001") {
				$(".goodsUrlClass").removeClass("hide");
				$("input:radio[name='isHaveShop'][value='1001']").prop('checked',true);
			} else {
				$(".goodsExplainImgClass").removeClass("hide");
				$("input:radio[name='isHaveShop'][value='1002']").prop('checked',true);
			}
			//初始化表单验证规则
			formValidate("#auditCashAuditForm");
		}else {
			Chief.layer.tips("系统异常", 1500);
		}
	});
});

// 显示大图
function imgShow(outerdiv, innerdiv, bigimg, _this) {
	var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
	$(bigimg).attr("src", src);//设置#bigimg元素的src属性
	/*获取当前点击图片的真实大小，并显示弹出层及大图*/
	//$("<img/>").attr("src", src).load(function() {
	$("<img/>").attr("src", src).on("load",function(){
		var windowW = $(window).width();//获取当前窗口宽度
		var windowH = $(window).height();//获取当前窗口高度
		var realWidth = this.width;//获取图片真实宽度
		var realHeight = this.height;//获取图片真实高度
		var imgWidth, imgHeight;
		var scale = 0.6;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放
		if (realHeight > windowH * scale) {//判断图片高度
			imgHeight = windowH * scale;//如大于窗口高度，图片高度进行缩放
			imgWidth = imgHeight / realHeight * realWidth;//等比例缩放宽度
			if (imgWidth > windowW * scale) {//如宽度扔大于窗口宽度
				imgWidth = windowW * scale;//再对宽度进行缩放
			}
		} else if (realWidth > windowW * scale) {//如图片高度合适，判断图片宽度
			imgWidth = windowW * scale;//如大于窗口宽度，图片宽度进行缩放
			imgHeight = imgWidth / realWidth * realHeight;//等比例缩放高度
		} else {//如果图片真实高度和宽度都符合要求，高宽不变
			imgWidth = realWidth;
			imgHeight = realHeight;
		}
		$(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放
		var w = (windowW - imgWidth) / 2;//计算图片与窗口左边距
		var h = (windowH - imgHeight) / 4;//计算图片与窗口上边距
		$(innerdiv).css({"top": h, "left": w});//设置#innerdiv的top和left属性
		$(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
	});
	$(outerdiv).click(function() {//再次点击淡出消失弹出层
		$(this).fadeOut("fast");
	});
}

/**
 * 编辑 保存
 */
function updateCashAudit(val){
	if(val == "1003"){
		var auditOpinion = $("#auditOpinion").val();
		if (isDataNull(auditOpinion)) {
			Chief.layer.tips("请填写审核意见，不通过原因！");
			return;
		}
	}
	//获取表单数据
	var data = $('#auditCashAuditForm').serialize();
	data += "&goodsStatus=" + val; // 审核状态
	//发起请求
	Chief.ajax.postJson("/lucky/updateLuckyGoodsInfo", data, function (data) {
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

//上架商品
$("body").on('click','#online_btn',function() {
	//获取选中框数据
	var _edit = $("#J_tabletpl td").find("input[type='checkbox']:checked");//获取选中行数量
	if(_edit.length == 0){
		Chief.layer.tips('请选择一条数据！');
		return false;
	}
	var id = _edit.eq(0).attr("data-id"); //获取主键ID
	var onlineStatus = _edit.eq(0).attr("data-auditType"); //获取商品状态
	if (onlineStatus == "1004") {
		Chief.layer.tips('商品已上架！');
		return false;
	} else if (onlineStatus == "1006"){
		Chief.layer.tips('商品已结束！');
		return false;
	} else if (onlineStatus != "1002"){
		Chief.layer.tips('只能上架审核通过的商品！');
		return false;
	}
	var param = {
		id: id,
		goodsStatus: "1004"
	}
	updateOnlineStatus(param);
});

//下架商品
$("body").on('click','#offline_btn',function() {
	//获取选中框数据
	var _edit = $("#J_tabletpl td").find("input[type='checkbox']:checked");//获取选中行数量
	if(_edit.length == 0){
		Chief.layer.tips('请选择一条数据！');
		return false;
	}
	var id = _edit.eq(0).attr("data-id"); //获取主键ID
	var onlineStatus = _edit.eq(0).attr("data-auditType"); //获取商品状态
	if (onlineStatus == "1005") {
		Chief.layer.tips('商品已下架！');
		return false;
	} else if (onlineStatus == "1006"){
		Chief.layer.tips('商品已结束！');
		return false;
	} else if (onlineStatus != "1002"){
		Chief.layer.tips('只能下架审核通过的商品！');
		return false;
	}
	var paramId = {
		id: id
	};
	Chief.ajax.postJson('/lucky/queryLuckyGoodsDetail',paramId, function(data) {
		if('0' == data.code){
			var htmls = Handlebars.compile($("#T_rejectOrderCode").html());
			var ht = htmls(data.item);
			//弹窗
			Chief.layer.newEmptyDiv("下架原因", ht,'600px',"270px");
		}else {
			Chief.layer.tips("系统异常", 1500);
		}
	});
});

//下架确定功能
function doSubmit(orderStatus){
	var id = $("#id").val();
	var param = {
		id: id,
		goodsStatus: orderStatus,
		auditOpinion: $("#auditOpinion_D").val(),
		goodsDesc: $("#goodsDesc_D").val(),
		memberAccount: $("#memberAccount_D").val()
	}
	var auditOpinion_D = $("#auditOpinion_D").val();
	if (isDataNull(auditOpinion_D)) {
		Chief.layer.tips("请填写下架原因!");
		return false;
	}
	updateOnlineStatus(param);
}

// 上架/下架状态修改
function updateOnlineStatus(param){
	Chief.ajax.postJson("/lucky/updateLuckyGoodsInfo", param, function (data) {
		if("0" == data.code){
			Chief.layer.tips("处理成功", 1500);
			setTimeout('outTips()', 2000 );
		} else if("-1" == data.code){
			Chief.layer.tips(data.msg, 2000);
		} else {
			Chief.layer.tips("处理失败", 1500);
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
//取消按钮关闭弹窗
function doCancel(){
	Chief.layer.close();
}
