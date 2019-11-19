var global_limit = 10;
//管理端
var add_adminIds = "";
var remove_adminIds = "";
var ZTree = {
		tree: null,
		setting: {
			check: {
				enable: true,
				autoCheckTrigger: true
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "parentId",
				}
			}
		}
	};
//报表类别树配置
var setting = {
		check : {
			   enable : true,
			   chkStyle : "checkbox",    //复选框
		},
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
	 callback: {//点击回调
		//  onClick: showRightDetail,
	 } 
 };
Handlebars.registerHelper({
	eq : function(v1,v2){
		return v1 == v2;
	}
});
Handlebars.registerHelper('if_eq', function(v1, v2, opts) {
	if(v1 == v2)
		return opts.fn(this);
	else
		return opts.inverse(this);
});

//分页查询权限列表
function queryRoles(currentPage,limit){
	if (typeof currentPage == "undefined") {
        currentPage = 1;
    }
    if (typeof limit == "undefined") {
        limit = global_limit;
    }
    var roleName = $("#roleName").val();
    var url = "/sys/role/queryRoleList";
    var params = {"pageNumber": currentPage, "limit": limit,"roleName": roleName};
    Chief.ajax.postJson(url, params, function (result, flag) {
        var templet = Handlebars.compile($("#template_role_list").html());
        if (result.code == "0") {
        	Handlebars.registerHelper('isRoot', function() {
        	    return result.data;
        	});
            var htmlStr = templet(result);
            $("#tableBodyHtml").html(htmlStr);
            initPaginator(currentPage, result.total, queryRoles);
            $("#sample-table-2").colResizable({ // 手动拖动表格
				liveDrag:true, 
				fixed:false,
//				resizeMode:'overflow'
			});
        }else{
			Chief.layer.tips(result.msg);
		}
    });
}

//分页查询管理端用户
function queryAdmins(currentPage,limit){
	if (typeof currentPage == "undefined") {
        currentPage = 1;
    }
    if (typeof limit == "undefined") {
        limit = global_limit;
    }
    var roleId=$("input[type=checkbox].roleId:checked").val();
    var loginId = $("#loginId").val();
    var userName = $("#userName").val();
    var userDepartment = $("#userDepartment").val();
    var url = "/sys/admin/queryAdminsByHasRoleState";
    var params = {"pageNumber": currentPage, "limit": limit,"loginId": loginId,"userName":userName,"roleId":roleId,"userDepartment":userDepartment};
    Chief.ajax.postJson(url, params, function (result, flag) {
        var templet = Handlebars.compile($("#admin_list_template").html());
        if (result.code == "0") {
        	var items = result.items;
        	//由于有分页操作，查询出来的数据需要要之前的操作作比对
        	if(null!=items){
        		for(var i=0;i<items.length;i++){
            		var item = items[i];
            		var id = item.id;
            		if(add_adminIds.indexOf(id)>-1){
            			//表示该用户新加入的，选中
            			item.hasState = '1';
            		}
            		if(remove_adminIds.indexOf(id)>-1){
            			//表示该用户新加入的，取消选中
            			item.hasState = '0';
            		}
            	}
        	}
            var htmlStr = templet(result);
            $("#admin_list_html").html(htmlStr);
            initPaginator(currentPage, result.total, queryAdmins,"pagination2","totalNum2");
        }else{
			Chief.layer.tips(result.msg);
		}
    });
}
var roleExistFlag = false;
//新增角色
function addRole(){
	roleExistFlag = false;
	if(!$("#roleForm").valid()){
		return;
	}
	Chief.ajax.postJsonSync('/sys/role/addRole',$('#roleForm').serialize(), function(data){
		if(data.code == '0'){
			Chief.layer.close();
			Chief.layer.tips("保存成功！");
			queryRoles();
		}else{
			roleExistFlag = true;
			Chief.layer.tips(data.msg);
		}
	})
}
//编辑角色
function editRole(id){
	if(!$("#roleForm").valid()){
		return;
	}
	var param =  $.param({'id':id})+'&'+$('#roleForm').serialize();
	Chief.ajax.postJson('/sys/role/editRole',param, function(data){
		if(data.code == '0'){
			Chief.layer.close();
			Chief.layer.tips("保存成功！");
			queryRoles();
		}else{
			Chief.layer.tips(data.msg);
		}
	})
}
//删除角色
function deleteRole(ids){
	Chief.ajax.postJson('/sys/role/deleteRole',{'ids':ids}, function(data){
		if(data.code == '0'){
			Chief.layer.close();
			Chief.layer.tips("删除成功！");
			queryRoles();
		}else{
			Chief.layer.tips(data.msg);
		}
	})
}
//修改管理端角色用户
function updateRoleAdmin(roleId){
	if(add_adminIds.length==0&&remove_adminIds.length ==0){
		Chief.layer.close();
		return;
	}
	var add_adminIds_update = "";
	var remove_adminIds_update = "";
	if(add_adminIds.length>1&&add_adminIds.substring(0,1)==','){
		add_adminIds_update = add_adminIds.substring(1);
	}
	if(remove_adminIds.length>1&&remove_adminIds.substring(0,1)==','){
		remove_adminIds_update = remove_adminIds.substring(1);
	}
	var params =  {"roleId":roleId,"addUserIds":add_adminIds_update,"removeUserIds":remove_adminIds_update};
	Chief.ajax.postJson("/sys/role/updateRoleAdmins",params, function (result, flag) {
		if(result.code == '0'){
			Chief.layer.close();
			Chief.layer.tips("保存成功！");
		}else{
			Chief.layer.tips(result.msg);
		}
	});
}
// 初始化报表树
function reportmanageTrees(roleId) {
	var url = "/reportmanage/businessCategory/getBusinessTree";
	var params = {'roleId':roleId};
    //异步请求导航菜单管理列表数据
	Chief.ajax.postJson(url, params, function (data, flag) {
		if (data.code == "0") {
			var treeArray = data.items;
			if(null!=treeArray){
				for (var i = 0; i < treeArray.length; i++) {
					if(treeArray[i].hasState=='1'){
						treeArray[i].checked = true;
					}
				}
			}
			$.fn.zTree.init($("#report_tree"), setting, treeArray);
		}else{
			Chief.layer.tips(result.msg);
		}
	});
}
//根据权限Id获取该角色的权限树
function queryRoleRightList(roleId){
	Chief.ajax.postJson('/sys/role/queryRightListByRole',{'roleId':roleId}, function(data){
		if(data.code == '0'){
			var treeArray = data.items;
			if(null!=treeArray){
				for (var i = 0; i < treeArray.length; i++) {
					if(treeArray[i].hasState=='1'){
						treeArray[i].checked = true;
					}
				}
			}
			ZTree.tree = $.fn.zTree.init($("#roleRightTree"), ZTree.setting, data.items);
			ZTree.tree.expandNode(ZTree.tree.getNodeByParam("id", '0', null));
		}else{
			Chief.layer.tips(data.msg);
		}
	})
}

//保存角色权限
function  saveRoleRight(roleId){
	//获取权限树选中的节点  角色对应菜单
	var roleRightzTreeObj = $.fn.zTree.getZTreeObj("roleRightTree"); 
	var roleRightNodes = roleRightzTreeObj.getCheckedNodes();
	//var nodes = ZTree.tree.getCheckedNodes();
	var arrays=[];
	arrays.splice(0,arrays.length);
	for(var i=0;i<roleRightNodes.length;i++){
		arrays.push(roleRightNodes[i].id);
	}
	// //获取权限树选中的节点 角色对应报表分类
	// var reportzTreeObj = $.fn.zTree.getZTreeObj("report_tree");
	// var reportNodes = reportzTreeObj.getCheckedNodes();
	// var reportTypeId=[];
	// for(var i=0;i<reportNodes.length;i++){
	// 	reportTypeId.push(reportNodes[i].id);
	// }
	Chief.ajax.postJson('/sys/role/updateRoleRights', 
			{'roleId' : roleId,'rightIds':arrays.join(",")}, function(result){
		if(result.code == '0'){
			Chief.layer.close();
			Chief.layer.tips("保存成功！");
		}else{
			Chief.layer.tips(result.msg);
		}
	});
}
//分页查询角色详情中关联用户
function queryRoleUserList(currentPage,limit){
	if (typeof currentPage == "undefined") {
        currentPage = 1;
    }
    if (typeof limit == "undefined") {
        limit = global_limit;
    }
    var roleId=$("input[type=checkbox].roleId:checked").val();
    var params = {"pageNumber": currentPage, "limit": limit,"roleId": roleId};
    Chief.ajax.postJson("/sys/role/queryRoleAdmins", params, function (result, flag) {
        var templet = Handlebars.compile($("#template_role_user_list").html());
        if (result.code == "0") {
			var htmlStr = templet(result);
            $("#ruleUserHtml").html(htmlStr);
            initPaginator(currentPage, result.total, queryRoleUserList,"pagination3","totalNum3");
        }else{
        	Chief.layer.tips(result.msg);
        }
    });
}
/*
//分页查询角色详情中关联用户
function queryRoleErUserList(currentPage,limit){
	if (typeof currentPage == "undefined") {
        currentPage = 1;
    }
    if (typeof limit == "undefined") {
        limit = global_limit;
    }
    var roleId=$("input[type=checkbox].roleId:checked").val();
    var params = {"pageNumber": currentPage, "limit": limit,"roleId": roleId};
    Chief.ajax.postJson("/sys/role/queryRoleErUsers", params, function (result, flag) {
		var templet = Handlebars.compile($("#template_role_er_user_list").html());
        if (result.code == "0") {
            var htmlStr = templet(result);
            $("#ruleUserHtml").html(htmlStr);
            initPaginator(currentPage, result.total, queryRoleErUserList,"pagination5","totalNum5");
        }else{
        	Chief.layer.tips(result.msg);
        }
    });
}*/
//数据校验规则
function formValidate(){
	$("#roleForm").validate({
		errorClass: 'help-block',
		rules : {
			roleName:{
	            required : true,
	            isNormalText : true,
	            maxlength:30
	        },
	        description:{
	        	maxlength : 100
			}
	    },
	    messages:{
	    	roleName:{
				required : "请填写角色名称",
				isNormalText : "请勿输入特殊字符,数字或空格",
				maxlength : "最多支持30个字"
	    	},
			description:{
				maxlength : "最多支持100个字"
			}
		},
		errorPlacement: function(error, element) { //验证消息放置的位置
			$(element).parents().next(".promport").html(error);
		},
       onfocusout: function(element) { 
    	   $(element).valid();
       }
	});
}

// 事件初始化
var eventInit = function(){
	$("body").on('click','#tableBodyHtml tr',function(e){
    	if(e && e.stopPropagation){
            //W3C取消冒泡事件
            e.stopPropagation();
        }else{
            //IE取消冒泡事件
            window.event.cancelBubble = true;
        }
    	if($(this).find('input[type=checkbox]').prop("disabled")){
    		return false;
    	}
    	var check = $(this).find('input[type=checkbox]').prop('checked')
    	if(check){
            $(this).find('input[type=checkbox]').removeProp('checked')
            $(this).find('input[type=checkbox]').prop('checked',false)
            $(this).find('td').removeClass('addTrColor')
        }else {
            $(this).siblings('tr').find('input[type=checkbox]').prop('checked',false)
            $(this).find('input[type=checkbox]').prop("checked", true).change()
             $(this).siblings('tr').find('td').removeClass('addTrColor')
             $(this).find('td').addClass('addTrColor')
        }
	});
    $('body').on('click','.roleId',function (e) {
        if(e && e.stopPropagation){
            //W3C取消冒泡事件
            e.stopPropagation();
        }else{
            //IE取消冒泡事件
            window.event.cancelBubble = true;
        }
        $(this).prop("checked", !$(this).prop('checked'))
        $(this).parents('tr').click();
        // return false
    }); 
	//分页查询角色
	$("body").on('click','#searchBtn',function(){
		queryRoles();
	});
	//点击添加角色按钮
	$("body").on('click','#roleAddBtn',function(){
		var htmls = Handlebars.compile($("#template_role").html());
		var ht = htmls({});
		Chief.layer.editDiv('添加角色',ht,function(){
			 //执行更新方法
	         var flag = $("#roleForm").validate().form(); //若全部通过验证则form方法返回true
	         if(flag) {
				 addRole();
				 if(roleExistFlag){
					 return false;
				 }
	         } else {
	             Chief.layer.tips("请按提示输入正确的内容");
	             return false;
	         }
			 
		},null,'580px','300px', "100px");
		formValidate();
	});
	//点击角色配置按钮和报表分类角色关联
	$("body").on('click','#roleRightBtn',function(){
		var roleId = $("input[type=checkbox].roleId:checked").val();
		if($("input[type=checkbox].roleId:checked").length==1){
			var htmls = Handlebars.compile($("#template_btn2").html());
			var ht = htmls();
			Chief.layer.editDiv('权限配置',ht,function(){
				saveRoleRight(roleId);//保存角色对应菜单和按钮以及报表分类
			},null,'750px','400px', "50px");
			queryRoleRightList(roleId);
			reportmanageTrees(roleId);
		}else{
			Chief.layer.tips("请选择一条角色记录！");
			return;
		}	
	});
	//点击关联管理端用户按钮
	$("body").on('click','#roleAdminBtn',function(){
		var roleId=$("input[type=checkbox].roleId:checked").val();
		if($("input[type=checkbox].roleId:checked").length==1){
			add_adminIds = "";
			remove_adminIds = "";
			var templet = Handlebars.compile($("#admin_template").html());
			htmlStr = templet();
			Chief.layer.editDiv('关联用户',htmlStr,function(){
				updateRoleAdmin(roleId);
			},null,'750px','400px', "50px");
			//初始化触发
			$('#searchAdminBtn').trigger('click');
		}else{
			Chief.layer.tips("请选择一条角色记录！");
			return;
		}
	});
	
	//管理端，联用户中checkbox的change时间，记录用户的选中可取消操作，存入全局变量
	$("body").on('change','.adminId',function(){
		var state = $(this).prop("checked");
		var val = $(this).val();
		if(state){
			if(add_adminIds.indexOf(val)==-1){
				if(remove_adminIds.indexOf(val)==-1){
					add_adminIds = add_adminIds +","+ val;
				}else{
					remove_adminIds = remove_adminIds.replace(","+val,"");
				}
			}
		}else{
			if(remove_adminIds.indexOf(val)==-1){
				if(add_adminIds.indexOf(val)==-1){
					remove_adminIds = remove_adminIds +","+ val;
				}else{
					add_adminIds = add_adminIds.replace(","+val,"");
				}
			}
		}
	});
	
	//关联管理端用户弹出中点击查询用户按钮
	$("body").on('click','#searchAdminBtn',function(){
		queryAdmins();
	});
	//点击编辑角色按钮
	$("body").on('click','#roleEditBtn',function(){
		var roleId=$("input[type=checkbox].roleId:checked").val();
		if($("input[type=checkbox].roleId:checked").length==1){
			Chief.ajax.postJson("/sys/role/queryRoleDetailByEdit", {"id":roleId}, function (result, flag) {
				if (result.code == "0") {
					var htmls = Handlebars.compile($("#template_role").html());
					var ht = htmls(result.item);
					Chief.layer.editDiv('编辑角色',ht,function(){
						 var flag = $("#roleForm").validate().form(); //若全部通过验证则form方法返回true
				         if(flag) {
				        	 editRole(roleId)
				         } else {
				             Chief.layer.tips("请按提示输入正确的内容");
				             return false;
				         }
					},function(){//关闭
						Chief.layer.close();
					},'580px','320px', "50px");
					  formValidate();
				}else{
					Chief.layer.tips(result.msg);
				}
			});
		}else{
			Chief.layer.tips("请选择一条角色记录！");
			return;
		}
	});
	//点击删除角色按钮
	$("body").on('click','#roleDelBtn',function(){
		var ids = "";
		if($("input[type=checkbox].roleId:checked").length>0){
			$("input[type=checkbox].roleId:checked").each(function(index){
				if(index==0){
					ids = $(this).val();
				}else{
					ids = ids +","+$(this).val();
				}
			});
			Chief.layer.confirm("确认删除选中的角色吗?",function(){
				deleteRole(ids)
			});
		}else{
			Chief.layer.tips("请选择要删除的角色记录！");
			return;
		}
	});
	//查看角色详情
	$("body").on('click','#showRoleDetailBtn',function(){
		if($("input[type=checkbox].roleId:checked").length==1){
			var roleId=$("input[type=checkbox].roleId:checked").val();
			Chief.ajax.postJson("/sys/role/queryRoleDetailByView", {"id":roleId}, function (result, flag) {
				if(result.code=='0'){
					//分页查询角色关联的用户
					var htmls = Handlebars.compile($("#template_detail").html());
					var ht = htmls(result);
					Chief.layer.viewDiv('查看详情',ht,null,'750px','500px', "50px");
					queryRoleUserList();
				}else{
					Chief.layer.tips(result.msg);
				}
			});
		}else{
			Chief.layer.tips("请选择一条角色记录！");
			return;
		}
	});
}
// 页面初始化
$(document).ready(function(){
	queryRoles();
	eventInit();
});
