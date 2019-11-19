//节点树配置
var setting = {
	data: {
		key:{
			children: "children",
			isParent: "parent"
		},
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "parentId",
		}
	},
	view: {
		showIcon: true,
		showLine: true,
	},
	callback: {
		onClick: showRightDetail,
	}
};

$(document).ready(function(){
	Handlebars.registerHelper({
		formatterState : function(v){
			if(v=='0'){
				return '校验';
			}else{
				return '不校验';
			}
		},
		formatterTypeCd : function(v){
			if(v=='menu'){
				return '菜单';
			}else if(v=='button'){
				return '按钮';
			}else{
				return '链接';
			}
		},
		eq : function(v1,v2){
			return v1 == v2;
		}
	});	
	$("#parentId").val("");
	initRightTrees();
	
});

//点击添加权限
$("#addRightBtn").on('click',function(){
	var htmls = Handlebars.compile($("#template_group_Search").html());
	var parentId = $("#parentId").val();
	if(Chief.isEmpty(parentId)){
		Chief.layer.tips("请选择权限树节点！");
		return;
	}
	var ht = htmls({});
	Chief.layer.editDiv('添加菜单',ht,function(){
		var parentId = $("#parentId").val();
		if(Chief.isEmpty(parentId)){
			Chief.layer.tips("请选择权限树节点！");
			return;
		}
		if(!$("#rightForm").valid()){
			return false;
		}
		var param =  $.param({'parentId':parentId})+'&'+$('#rightForm').serialize();
		Chief.ajax.postJson('/sys/right/addRight',param, function(data){
			if(data.code == '0'){
				Chief.layer.close();
				Chief.layer.tips("保存成功！");
				initRightTrees();
			}else{
				Chief.layer.tips(data.msg);
			}
		})},null,'580px','420px');
	$("input[name='typeCd']").eq(0).attr("checked","checked");
	$("input[name='tsvldUrlParaFlag']").eq(0).attr("checked","checked");
	formValidate();
});

//点击修改权限
$("#updRightBtn").on('click',function(){
	var id = $("#parentId").val();
	if(Chief.isEmpty(id)){
		Chief.layer.tips("请选择权限进行编辑");
		return;
	}
	Chief.ajax.postJson("/sys/right/queryRightDetail", {"id":id}, function (result, flag) {
        if (result.code == "0") {
        	var htmls = Handlebars.compile($("#template_group_Search").html());
			var typeRightHtml = htmls(result.item);//传入数据
			Chief.layer.editDiv('菜单编辑', typeRightHtml, function(){editRight()},null,'580px','420px');
			formValidate();
        }else{
			Chief.layer.tips(result.msg);
		}
    });
});

//点击删除权限
$("#delRightBtn").on('click',function(){
	deleteRight();
});
//初始化导航菜单管理列表信息
function initRightTrees() {
    var url = "/sys/right/queryRightList";
    var params = {};
//	异步请求导航菜单管理列表数据
    Chief.ajax.postJson(url, params, function (result, flag) {
        if (result.code == "0") {
            //渲染列表数据
            var data = result.items;
            $.fn.zTree.init($("#tree1"), setting, data);
        }else{
			Chief.layer.tips(result.msg);
		}
    });
}


//显示节点树权限详情
function showRightDetail(event, treeId, treeNode, clickFlag) {
    Chief.ajax.postJson("/sys/right/queryRightDetailByView", {"id":treeNode.id}, function (result, flag) {
        if (result.code == "0") {
            var rightTemp = Handlebars.compile($("#rightView").html());
			$("#viewRightHtml").html(rightTemp(result.item));
			$("#parentId").val(treeNode.id);
			$("#p_parentId").val(treeNode.parentId);
        }else{
			Chief.layer.tips(result.msg);
		}
    });
}


//编辑权限
function editRight(){
	if(!$("#rightForm").valid()){
		return;
	}
	var id = $("#parentId").val();
	var param =  $.param({'id':id,"sysTypeCd":$("#add_sysTypeCd").val()})+'&'+$('#rightForm').serialize();
	Chief.ajax.postJson('/sys/right/editRight',param, function(data){
		if(data.code == '0'){
			initRightTrees();
			Chief.layer.close();
			Chief.layer.tips("保存成功！");
		}else{
			Chief.layer.tips(data.msg);
		}
	})
}

//删除权限
function deleteRight(){
	var id = $("#parentId").val();
	var p_id = $("#p_parentId").val();
	if(Chief.isEmpty(parentId)){
		Chief.layer.tips("请选择权限进行删除");
		return;
	}
	if(Chief.isEmpty(p_id)){
		Chief.layer.tips("根节点不能删除");
		return;
	}
	Chief.layer.confirm("确认删除权限吗?",function(){
		var param =  {'id':id};
		Chief.ajax.postJson('/sys/right/deleteRight',param, function(data){
			if(data.code == '0'){
				initRightTrees();
				Chief.layer.close();
				Chief.layer.tips("删除成功！");
			}else{
				Chief.layer.tips(data.msg);
			}
		})
	});
}

//数据校验规则
function formValidate(){
	$("#rightForm").validate({
		errorElement: 'div',
		errorClass: 'help-block',
		 rules : {
			name:{
	           required : true,
	           maxlength : 30
	        },
			orderLevel:{
				required : true,
				number:true,
				digits:true,
				range:[0,999],
				max:999
			},
			urlAddr:{
				maxlength : 500
			},
			description:{
	        	maxlength : 120
			}
	    },
	    messages:{
	    	name:{
				required : "请填写权限名称",
				maxlength : "最多支持30个字"
	    	},
			orderLevel:{
				required : "请填写排序号",
				number : "排序必须为整数",
				max:"请输入0到999的整数"
			},
			description:{
				maxlength : "最多支持500个字"
			},
			urlAddr:{
				maxlength : "最多支持120个字"
			}
		},
		errorPlacement: function(error, element) { //验证消息放置的位置
			error.insertAfter(element.parent());
	       },
	       onfocusout: function(element) { 
	    	   $(element).valid();
	       }
	});
}