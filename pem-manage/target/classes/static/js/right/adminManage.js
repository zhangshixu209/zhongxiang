var global_limit = 10;
//管理端
var add_roleIds = "";
var remove_roleIds = "";
$(document).ready(function(){
	Handlebars.registerHelper({
		eq : function(v1,v2){
			return v1 == v2;
		}
	});
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
    $('body').on('click','.adminId',function (e) {
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
	//分页查询管理员
	$("body").on('click','#searchBtn',function(){
		queryAdmins();
	});
	//添加
	$("#adminAddBtn").click(function(){
		var templet = Handlebars.compile($("#template_admin_add").html());
		htmlStr = templet();
		Chief.layer.editDiv('创建用户',htmlStr,function(){
			addAdmin();
		},function(){},'600px','460px');
		$("input[name='sex']").eq(0).attr("checked","checked");
		$("input[name='validStsCd']").eq(0).attr("checked","checked");
		addFormValidate();
	})
	//编辑
	$("#adminRightBtn").click(function(){
		var adminId=$("input[type=checkbox].adminId:checked").val();
		if($("input[type=checkbox].adminId:checked").length==1){
			Chief.ajax.postJson("/sys/admin/queryAdminDetailByEdit", {"id":adminId}, function (result, flag) {
				if (result.code == "0") {
					var htmls = Handlebars.compile($("#template_admin_edit").html());
					var ht = htmls(result.item);
					Chief.layer.editDiv('编辑用户',ht,function(){
						updateAdmin(adminId)
					},function(){},'600px','460px');
					editFormValidate();
				}else{
					Chief.layer.tips(result.msg);
				}
			});
		}else{
			Chief.layer.tips("请选择一条用户记录！");
			return;
		}
	})
	//关联
	$("#adminRoleBtn").click(function(){
		var adminId=$("input[type=checkbox].adminId:checked").val();
		if($("input[type=checkbox].adminId:checked").length==1){
			add_roleIds = "";
			remove_roleIds = "";
			var templet = Handlebars.compile($("#admin_role_template").html());
			htmlStr = templet();
			Chief.layer.editDiv('关联角色',htmlStr,function(){
				updateAdminRole(adminId);
			},function(){},'800px','540px');
		}else{
			Chief.layer.tips("请选择一条用户记录！");
			return;
		}
	});
	
	//关联管理端角色弹出中点击查询角色按钮
	$("body").on('click','#searchRoleBtn',function(){
		queryRoles();
	});
	//关联角色中checkbox的change时间，记录用户的选中可取消操作，存入全局变量
	$("body").on('change','.roleId',function(){
		var state = $(this).prop("checked");
		var val = $(this).val();
		if(state){
			if(add_roleIds.indexOf(val)==-1){
				if(remove_roleIds.indexOf(val)==-1){
					add_roleIds = add_roleIds +","+ val;
				}else{
					remove_roleIds = remove_roleIds.replace(","+val,"");
				}
			}
		}else{
			if(remove_roleIds.indexOf(val)==-1){
				if(add_roleIds.indexOf(val)==-1){
					remove_roleIds = remove_roleIds +","+ val;
				}else{
					add_roleIds = add_roleIds.replace(","+val,"");
				}
			}
		}
	});
	
	//查看管理员详情
	$("body").on('click','#adminDetailBtn',function(){
		var adminId=$("input[type=checkbox].adminId:checked").val();
		if($("input[type=checkbox].adminId:checked").length==1){
			Chief.ajax.postJson("/sys/admin/queryAdminDetailByView", {"id":adminId}, function (result, flag) {
				if(result.code=='0'){
					var htmls = Handlebars.compile($("#template_deali").html());
					var ht = htmls(result);
					Chief.layer.viewDiv('查看详情',ht,function(){},'800px','540px');
					//分页查询角色关联的用户
					queryRoleUserList();
				}else{
					Chief.layer.tips(result.msg);
				}
			});
		}else{
			Chief.layer.tips("请选择一条用户记录！");
			return;
		}
	});
});
//分页查询权限列表
function queryAdmins(currentPage,limit){
	if (typeof currentPage == "undefined") {
        currentPage = 1;
    }
    if (typeof limit == "undefined") {
        limit = global_limit;
    }
    var loginId = $("#loginId").val();
    var userName = $("#userName").val();
    var url = "/sys/admin/queryAdmins";
    var params = {"pageNumber": currentPage, "limit": limit,"loginId": loginId,"userName":userName};
    Chief.ajax.postJson(url, params, function (result, flag) {
        var templet = Handlebars.compile($("#admin_list_template").html());
        if (result.code == "0") {
        	Handlebars.registerHelper('isRoot', function() {
        	    return result.data;
        	});
            var htmlStr = templet(result);
            $("#tableBodyHtml").html(htmlStr);
            initPaginator(currentPage, result.total, queryAdmins);
        }else{
			Chief.layer.tips(result.msg);
		}
    });
}

//添加管理员
function addAdmin(){
	if(!$("#adminAddForm").valid()){
		return;
	}
	Chief.ajax.postJson('/sys/admin/addAdmin',$('#adminAddForm').serialize(), function(data){
		if(data.code == '0'){
			Chief.layer.close();
			Chief.layer.tips("保存成功！");
			queryAdmins();
		}else{
			Chief.layer.tips(data.msg);
		}
	})
}
//修改管理员
function updateAdmin(id){
	if(!$("#adminUpdateForm").valid()){
		return;
	}
	var param =  $.param({'id':id})+'&'+$('#adminUpdateForm').serialize();
	Chief.ajax.postJson('/sys/admin/updateAdmin',param, function(data){
		if(data.code == '0'){
			Chief.layer.close();
			Chief.layer.tips("保存成功！");
			queryAdmins();
		}else{
			Chief.layer.tips(data.msg);
		}
	})
}

//关联角色分页查询角色
function queryRoles(currentPage,limit){
	if (typeof currentPage == "undefined") {
        currentPage = 1;
    }
    if (typeof limit == "undefined") {
        limit = global_limit;
    }
    var adminId=$("input[type=checkbox].adminId:checked").val();
    var roleName = $("#roleName").val();
    var url = "/sys/admin/queryRoleListWithHasAdmin";
    var params = {"pageNumber": currentPage, "limit": limit,"roleName":roleName,"userId":adminId};
    Chief.ajax.postJson(url, params, function (result, flag) {
        var templet = Handlebars.compile($("#role_list_template").html());
        if (result.code == "0") {
        	var items = result.items;
        	//由于有分页操作，查询出来的数据需要要之前的操作作比对
        	if(null!=items){
        		for(var i=0;i<items.length;i++){
            		var item = items[i];
            		var id = item.id;
            		if(add_roleIds.indexOf(id)>-1){
            			//表示该用户新加入的，选中
            			item.hasState = '1';
            		}
            		if(remove_roleIds.indexOf(id)>-1){
            			//表示该用户新加入的，取消选中
            			item.hasState = '0';
            		}
            	}
        	}
            var htmlStr = templet(result);
            $("#role_list_html").html(htmlStr);
            initPaginator(currentPage, result.total, queryRoles,"pagination2","totalNum2");
        }else{
			Chief.layer.tips(result.msg);
		}
    });
}

//修改管理端角色用户
function updateAdminRole(adminId){
	if(add_roleIds.length==0&&remove_roleIds.length ==0){
		Chief.layer.close();
		return;
	}
	var add_roleIds_update = "";
	var remove_roleIds_update = "";
	if(add_roleIds.length>1&&add_roleIds.substring(0,1)==','){
		add_roleIds_update = add_roleIds.substring(1);
	}
	if(remove_roleIds.length>1&&remove_roleIds.substring(0,1)==','){
		remove_roleIds_update = remove_roleIds.substring(1);
	}
	var params =  {"userId":adminId,"addRoleIds":add_roleIds_update,"removeRoleIds":remove_roleIds_update};
	Chief.ajax.postJson("/sys/admin/updateAdminRoles",params, function (result, flag) {
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
    var adminId=$("input[type=checkbox].adminId:checked").val();
    var params = {"pageNumber": currentPage, "limit": limit,"userId": adminId};
    Chief.ajax.postJson("/sys/admin/queryAdminRoles", params, function (result, flag) {
        var templet = Handlebars.compile($("#template_role_user_list").html());
        if (result.code == "0") {
            var htmlStr = templet(result);
            $("#roleUserHtml").html(htmlStr);
            initPaginator(currentPage, result.total, queryRoleUserList,"pagination3","totalNum3");
        }else{
        	Chief.layer.tips(result.msg);
        }
    });
}
function addFormValidate(){
	$("#adminAddForm").validate({
		errorElement: 'div',
		errorClass: 'help-block',
		rules : {
			userName : {
				required : true,
				isNormalText : true,
				maxlength : 10
			},
			loginId : {
				required : true,
				mobile :true
			},
			// 密码校验
			loginPw : {
				required : true,
				maxlength : 16,
				minlength : 6,
				checkPsd : true
			},
			// 确认密码校验
			againpassword : {
				required : true,
				equalTo : "#add_loginPw"
			},
			email:{
				required : true,
				email:true,
				maxlength:30
			}
		},
		messages : {
			userName : {
				required : "请输入用户名",
				isNormalText : "请勿输入特殊字符,数字或空格",
				maxlength : "最多支持10个字"
				
			},
			loginId : {
				required : "请输入手机号",
				mobile : "手机号码格式不正确"
			},
			loginPw : {
				required : "请输入密码",
				checkPsd : "密码为6-16位，且包含数字、字母、特殊字符两种或以上组成,且不能包含空格",
				maxlength : "密码为6-16位，且包含数字、字母、特殊字符两种或以上组成,且不能包含空格",
				minlength : "密码为6-16位，且包含数字、字母、特殊字符两种或以上组成,且不能包含空格"
			},
			againpassword : {
				required : "请再次输入确认密码",
				equalTo :"再次输入的密码不一致"
			},
			email:{
				required : "请输入邮箱",
				email:"请输入正确的邮箱",
				maxlength:"最多支持30个字"
			}
		},
		errorPlacement: function(error, element) { //验证消息放置的位置
			error.insertAfter(element.parent());
	       },
	       onfocusout: function(element) { $(element).valid();
	     }
	})
}

function editFormValidate(){
	$("#adminUpdateForm").validate({
		errorElement: 'div',
		errorClass: 'help-block',
		rules : {
			userName : {
				required : true,
				isNormalText : true,
				maxlength : 30
			},
			loginId : {
				required : true,
				mobile :true
			},
			// 密码校验
			loginPw : {
				maxlength : 20,
				minlength : 6,
				checkPsd : true
			},
			email:{
				required : true,
				email:true,
				maxlength : 30,
			}
		},
		messages : {
			userName : {
				required : "请输入用户名",
				isNormalText : "请勿输入特殊字符,数字或空格",
				maxlength : "不能超过30个字符"
			},
			loginId : {
				required : "请输入手机号",
				mobile : "手机号不正确"
			},
			loginPw : {
				checkPsd : "密码为6-16位，且包含数字、字母、特殊字符两种或以上组成,且不能包含空格",
				maxlength : "密码为6-16位，且包含数字、字母、特殊字符两种或以上组成,且不能包含空格",
				minlength : "密码为6-16位，且包含数字、字母、特殊字符两种或以上组成,且不能包含空格"
			},
			email:{
				required : "请输入邮箱",
				email:"请输入正确的邮箱",
				maxlength : "邮箱不能超过30个字符"
			}
		},
		errorPlacement: function(error, element) { //验证消息放置的位置
			error.insertAfter(element.parent());
	       },
	       onfocusout: function(element) { $(element).valid();
	     }
	})
}