Handlebars.registerHelper({
	formatterSex: function(v) {
		if(v == '0') {
			return '男';
		} else {
			return '女';
		}
	},
	formatterValidStsCd: function(v) {
		if(v == '1') {
			return '启用';
		} else {
			return '禁用';
		}
	},
	formatterValidSt: function(v) {
		if(v == '1') {
			return '1';
		} else {
			return '0';
		}
	},
	formatterTypeCd: function(v) {
		if(v == 'menu') {
			return '菜单';
		} else if(v == 'button') {
			return '按钮';
		} else {
			return '链接';
		}
	},
	eq: function(v1, v2) {
		return v1 == v2;
	}
});
//初始化加载部门树
var ZTree = {
		tree: null,
		setting: {
			 check: {
                 enable: true,
                 chkStyle: "radio",
             },
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "parentId",
				}
			},
			callback: {//回调函数
		        onClick:function(event, treeId, treeNode){//点击每个节点回调
		        },
		        onAsyncSuccess:function(event, treeId, treeNode, msg) {//树结构加载完后回调
		        },
		        onCheck: function zTreeOnCheck(event, treeId, treeNode) { //复选框选中回调
					var treeObj = ZTree.tree; //ztree对象
					var checkedNodes = treeObj.getCheckedNodes(true);
					for(var i=0; i<checkedNodes.length; i++) {
						//如果当前数组中节点的 id != 本次点击选中节点id
						if(checkedNodes[i].id != treeNode.id){ 
							checkedNodes[i].checked = false; //取消选中效果
							treeObj.updateNode(checkedNodes[i]);//更新所有选中的节点..
						}	
					}
				} // onCheck end
		    }
		}
	};

//初始化加载部门树
function queryRoleRightList(){
	Chief.ajax.postJson('/sys/role/queryUserDepartment',{}, function(data){
		if(data.code == '0'){
			var treeArray = data.items;
			if(null!=treeArray){
				for (var i = 0; i < treeArray.length; i++) {
					if(treeArray[i].hasState=='1'){
						treeArray[i].checked = true;
					}
				}
			}
			ZTree.tree = $.fn.zTree.init($("#departmentTree"), ZTree.setting, data.items);
			ZTree.tree.expandNode(ZTree.tree.getNodeByParam("id", '0', null));
		}else{
			Chief.layer.tips(data.msg);
		}
	})
}
//编辑获取树的节点
function queryRoleRightListUpdate(id){
	Chief.ajax.postJson('/sys/role/queryUserDepartment',{}, function(data){
		if(data.code == '0'){
			var treeArray = data.items;
			if(null!=treeArray){
				for (var i = 0; i < treeArray.length; i++) {
					if(treeArray[i].hasState=='1'){
						treeArray[i].checked = true;
					}
				}
			}
			ZTree.tree = $.fn.zTree.init($("#departmentTree"), ZTree.setting, data.items);
			ZTree.tree.checkNode(ZTree.tree.getNodeByParam("id", id, null));
		}else{
			Chief.layer.tips(data.msg);
		}
	})
}

//查询用户
function queryUsers(currentPage, limit) {
	// 因为添加了数据多的时候列表固定头部，所以需要先清空列表容器
	$("#table-list-container").empty();
	$("#table-list-container").html($("#table-list-model").html());
	if(typeof currentPage == "undefined") {
		currentPage = 1;
	}
	if(typeof limit == "undefined") {
		limit = list.limit;
	}
	
	var realName = $("#realName").val();
	var mobile = $("#mobile").val();
	var userDepartment = $("#H_userDepartment").val();
	var params = {};
	params.userName = realName;
	params.loginId = mobile;
	params.userDepartment = userDepartment;
	params.pageNumber = currentPage;
	params.limit = limit;
	queryAdmins(params);
}
//查询管理员
function queryAdmins(params) {
	Chief.ajax.postJson('/sys/admin/queryAdmins', params, function(data) {
		if(data.code == "0") {
			var template = Handlebars.compile($("#template_admin_list").html());
			//渲染列表数据
			var htmlStr = template(data);
			$("#usersHtml").html(htmlStr);
			initPaginator(params.pageNumber, data.total, queryUsers);
			$("#sample-table-23").colResizable({ // 手动拖动表格
				liveDrag:true, 
				fixed:false,
//				resizeMode:'overflow'
			});
		} else {
			Chief.layer.tips(data.msg);
		}
	});
}
//查询角色
function queryRoles(currentPage, limit) {
	/*if(typeof currentPage == "undefined") {
		currentPage = 1;
	}
	if(typeof limit == "undefined") {
		limit = global_limit;
	}*/
	var userId = $('#userId').val();
	var roleName = $('#roleName').val();
	var params = {
		userId: userId,
		roleName: roleName,
		pageNum: 1,
		pageSize: 10000
	};
	Chief.ajax.postJson('/sys/role/queryRolesForUser', params, function(data) {
		if(data.code == '0') {
			var template = Handlebars.compile($("#template_role_list").html());
			//渲染列表数据
			$("#rolesHtml").html(template(data));
			initPaginator(currentPage, data.total, queryRoles);
			//注册授予/撤销按钮事件
			$('button[name=addRemoveBtnInRoleList]').each(function(index, element) {
				$(element).click(function() {
					var $button = $(this);
					var ruId = $button.attr('ruId');
					if(ruId == '0') {
						//授予角色
						var roleId = $button.attr('roleId');
						var userId = $button.attr('userId');
						var params = {
							roleId: roleId,
							userId: userId
						};
						Chief.ajax.postJson('/sys/role/addRoleUserForUser', params, function(data) {
							if(data.code == '0') {
								$button.attr('ruId', data.item.ruId)
								$button.toggleClass('btn-danger');
								$button.text('撤销');
								Chief.layer.tips('授予角色成功！');
								queryUsers()
							} else {
								Chief.layer.tips(data.msg);
							}
						});
					} else {
						//撤销角色
						var id = $button.attr('ruId');
						var params = {
							id: id
						};
						Chief.ajax.postJson('/sys/role/removeRoleUserForUser', params, function(data) {
							if(data.code == '0') {
								$button.attr('ruId', 0)
								$button.toggleClass('btn-danger');
								$button.text('授予');
								Chief.layer.tips('撤销角色成功！');
								queryUsers()
							} else {
								Chief.layer.tips(data.msg);
							}
						});
					}
					return false;
				});
			});
		} else {
			Chief.layer.tips(data.msg);
		}
	});
}
// 页面事件
var eventInit = function(){
	//查询按钮
	$("#userSearch").click(function() {
		queryUsers();
	});

	//添加管理员
	$('#addAdmin').click(function() {
		var htmls = Handlebars.compile($("#tianjiaAdmin").html());
		var ht = htmls();
		Chief.layer.newEmptyDiv("添加管理员", ht, '800px', "450px", "50px");
		queryRoleRightList();//初始化部门树
		//	初始化validata表单验证
		$("#addAdminForm").validate({
			submitHandler: function(form) {
				/*var loginId = $("#loginId").val();
			    if(!(/^1[34578]\d{9}$/.test(loginId))){ 
			    	Chief.layer.tips("请输入正确的工号"); 
			        return; 
			    } */
				//ztree获取选中id
				var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
				var nodes =treeObj.getCheckedNodes(true);
				/*if(nodes.length == "0"){
					Chief.layer.tips("请选择部门");
					return;
				}*/
				if(nodes.length != "1"){
					Chief.layer.tips("每个人必须属于一个部门!");
					return;
				}
				var departmentId = nodes[0].id;
				var departmentName = nodes[0].name;
				$('#userDepartment').val(departmentId);
				$('#userDepartmentName').val(departmentName);
				//获取表单数据
				var param = $('#addAdminForm').serialize();
				Chief.ajax.postJson('/sys/admin/addAdmin', param, function(data) {
					if(data.code == '0') {
						//清空表单
						$('#userName').val('');
						$('#loginId').val('');
						$('#loginPw').val('');
						$('#userDepartment').val('');
						Chief.layer.tips("保存成功！");
						queryUsers()
					} else {
						Chief.layer.tips(data.msg);
					}
				});
				//关闭弹框
				Chief.layer.close();
			},
			errorPlacement: function(error, element) {  
				error.appendTo(element.parent()).addClass('user_bfuid');  
			}
		});
	});
	//编辑管理员
	$('#editAdmin').click(function() {
		var ckboxes = $("input[type=checkbox].ace:checked");
		if(ckboxes.length == 0) {
			Chief.layer.tips("请选择一条数据！");
			return;
		}
		var $ckbox = ckboxes.eq(0);
		var id = $ckbox.val();
		var paramId = {
			id: id
		};
		Chief.ajax.postJson('/sys/admin/queryAdminDetailByEdit', paramId, function(data) {
			if(data.code == '0') {
				var htmls = Handlebars.compile($("#bianjiAdmin").html());
				var ht = htmls(data.item);
				Chief.layer.newEmptyDiv("编辑管理员", ht, '800px', "450px", "50px");
				queryRoleRightListUpdate(data.item.user_department_id);//初始化部门树
				//	初始化validata表单验证
				$("#editAdminForm").validate({
					submitHandler: function(form) {
						/*var loginId = $("#loginId").val();
					    if(!(/^1[34578]\d{9}$/.test(loginId))){ 
					    	Chief.layer.tips("请输入正确的工号"); 
					        return; 
					    } */
						var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
						var nodes =treeObj.getCheckedNodes(true);
						/*if(nodes.length == "0"){
							Chief.layer.tips("请选择部门");
							return;
						}*/
						if(nodes.length != "1"){
							Chief.layer.tips("每个人必须属于一个部门!");
							return;
						}
						var departmentId = nodes[0].id;
						var departmentName = nodes[0].name;
						$('#userDepartment').val(departmentId);
						$('#userDepartmentName').val(departmentName);
						//获取表单数据
						var param = $('#editAdminForm').serialize();
						Chief.ajax.postJson('/sys/admin/updateAdmin', param, function(data) {
							if(data.code == '0') {
								Chief.layer.tips("保存成功！");
								queryUsers()
							} else {
								Chief.layer.tips(data.msg);
							}
						});
						//关闭弹框
						Chief.layer.close();
					},
					errorPlacement: function(error, element) {  
						error.appendTo(element.parent()).addClass('user_bfuid');  
					}
				});
			} else {
				Chief.layer.tips(data.returnMessage);
			}
		});
	});
	
	//删除管理员
	$('#delAdmin').click(function() {
		var ckboxes = $("input[type=checkbox].ace:checked");
		if(ckboxes.length == 0) {
			Chief.layer.tips("请选择一条数据！");
			return;
		}
		var $ckbox = ckboxes.eq(0);
		var id = $ckbox.val();
		var paramId = {
			id: id
		};
		layer.confirm('确定要删除用户吗？', function(index){
			Chief.ajax.postJson('/sys/admin/delAdminDetail', paramId, function(data) {
				if(data.code == '0') {
					Chief.layer.tips("删除成功！");
					queryUsers();
				} else {
					Chief.layer.tips(data.msg);
				}
			});
			
			layer.close(index);
		}); 
		
	});
	//重置用户密码
	$('#resetAdminPwd').click(function() {
		var ckboxes = $("input[type=checkbox].ace:checked");
		if(ckboxes.length == 0) {
			Chief.layer.tips("请选择一条用户数据！", 800);
			return;
		}
		var userId = ckboxes.eq(0).val();
		var param = {userId: userId};
		
		layer.confirm('确定要重置该用户的密码吗?', function(index){
			//执行重置密码
			Chief.ajax.postJson('/sys/admin/resetAdminPwd', param, function(data) {
				if(data.code == '0') {
					Chief.layer.tips("密码重置成功！");
					queryUsers();
				} else {
					Chief.layer.tips(data.msg);
				}
			});
			
			layer.close(index);
		}); 
	});
	//根据id设置用户账号启用和禁用
	$('.resetenable').click(function() {
		var ckboxes = $("input[type=checkbox].ace:checked");
		if(ckboxes.length == 0) {
			Chief.layer.tips("请选择一条数据！");
			return;
		}
		var $ckbox = ckboxes.eq(0);
		var validStsCd = $ckbox.attr("validStsCd");
		var attribute = $(this).attr("attribute");//表示点击的按钮类型
		if(attribute == validStsCd){
			if(attribute == "0"){
				Chief.layer.tips("已经是禁用,不允许重复设置!");
				return;
			}else{
				Chief.layer.tips("已经是启用,不允许重复设置!");
				return;
			}
		}
		var id = $ckbox.val();
		var paramId = {
			id: id,
			status:validStsCd
		};
		layer.confirm('确定要'+((attribute == '0')?'禁用':'启用')+'设置吗？', function(index){
			//执行重置密码
			Chief.ajax.postJson('/sys/admin/enableORdisable', paramId, function(data) {
				if(data.code == '0') {
					Chief.layer.tips("操作成功！");
					queryUsers();
				} else {
					Chief.layer.tips(data.msg);
				}
			});
			layer.close(index);
		}); 
		
	});

	//关联角色
	$('#guan').click(function() {
		var ckboxes = $("input[type=checkbox].ace:checked");
		if(ckboxes.length == 0) {
			Chief.layer.tips("请选择一条用户数据！");
			return;
		}
		var userId = ckboxes.eq(0).val();
		var params = {
			userId: userId,
		};
		var htmls = Handlebars.compile($("#guanlian").html());
		var ht = htmls(params);
		Chief.layer.newEmptyDiv("关联角色", ht, '750px', "450px", "50px", endCallback);
		//注册查询按钮事件
		$('#queryBtnInRole').click(function() {
			queryRoles();
			return false;
		});
		//注册关闭按钮事件
		$('#closeBtnInRole').click(function() {
			queryUsers();
			Chief.layer.close();
			return false;
		});
		function endCallback(){
			queryUsers();
			Chief.layer.close();
			return false;
		}
		$('#queryBtnInRole').trigger('click');//触发初始化加载
	});
	
	// 批量新增用户
	$('#batch_add').click(function() {
		var htmls = Handlebars.compile($("#T_importUserInfo").html());
	    var ht = htmls();
	    Chief.layer.newEmptyDiv("批量导入用户", ht,'600px', "320px");
		//加载上传控件
	    uploaderInit('#filePicker');
	    bindEvent(); //按钮事件绑定
	});
}

//上传控件初始化
function uploaderInit(picker) {
    var uploaderS;
    var $btn =$("#ctlBtn");  //导入按钮
    var filePathArry = "";
    uploaderS = WebUploader.create({
        // 选完文件后，是否自动上传
        auto: false,
        //	去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
        duplicate: true, //
        // swf文件路径
        swf: '../assets/js/Uploader.swf',
        // 文件接收服务端。
        server: '/pem-manage/api/sys/admin/importExcelUserData',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: picker,
        fileVal: "file",
        // 限制文件数量
//        fileNumLimit: 1,
        fileSizeLimit: 10 * 1024 * 1024, // 90 M 大小文件
        fileSingleSizeLimit: 10000 * 1024, // 单个文件
        // 只允许选择图片、word、txt、excel类型文件。
        accept: {
            title: 'Images',
            extensions: 'xls,xlsx',
            mimeTypes: '' // 'image/*'
        },
        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false
    });

    //当某个文件的分块在发送前触发
    var showLoadingSha;
    //加入上传队列前
    uploaderS.on('beforeFileQueued', function(file, obj){
    	if(file.size > 10*1024*1024) {
    		Chief.layer.tips("单个文件不能超过10M");
    		return false;
    	}
    	var fileList = $('div.file-list').find('.file-item'); //放入的文件数量
    	if(fileList.length == 1) {
    		Chief.layer.tips("文件数量超出限制");
    		return false;
    	}
    	if(file.name.lenth > 100) {
    		Chief.layer.tips("文件名称过长");
    		return false;
    	}
    });
    // 当有文件添加进来的时候
    uploaderS.on('fileQueued', function( file ) {
    	filePathArry = $("#filePaths").val();
        var html = '<p style="padding-left: 145px;" class="file-item file-item-box"><span class="file-name">' +
        			file.name  +  // 原始文件名
        			"</span>"  +
        			'<span class="del" style="padding-left: 12px;"><a href="javascript:;">删除</a></span>' + 
        			'</p>';
        $('.ndiv').append(html);
    });
    uploaderS.on('uploadBeforeSend', function(file, data) {
        showLoadingSha = Chief.layer.showLoadingShade(); //加载动画
    });
    //	上传成功
    uploaderS.on('uploadSuccess', function(file, data) {
        Chief.layer.close(showLoadingSha);
        if("0" == data.code) {
        	Chief.layer.tips(data.msg);
        	setTimeout('outTips()', 1500);
        }else {
        	Chief.layer.tips("文件上传失败！" + data.msg);
        	// 上传失败删除附件
        	setTimeout(function() {
    	        //执行去除附件
        		$('div.file-list').find('.file-item').remove(); //放入的文件数量
        	}, 1.5 * 1000);
        }
    });
    //	上传失败
    uploaderS.on('uploadError', function(file, response) {
    	Chief.layer.tips("上传失败")
        Chief.layer.close(showLoadingSha);
    });
    //请求出错
    uploaderS.on('error', function(file, response) {
    	switch (file) {
	        case "Q_TYPE_DENIED":
	            Chief.layer.tips("文件格式不正确");
	            return false;
	        case "Q_EXCEED_SIZE_LIMIT ":
	            Chief.layer.tips("文件大小超出限制");
	            return false;
	        default :
	            break;
    	}
        Chief.layer.close(showLoadingSha);
    });
    // 手动上传文件
    $btn.on('click', function() {
    	var fileList = $('div.file-list').find('.file-item'); //放入的文件数量
    	if(fileList.length == 0) {
    		Chief.layer.tips("请先添加附件！");
    		return false;
    	}
        uploaderS.upload();
    });
    if(navigator.userAgent.indexOf("MSIE")>0 && navigator.userAgent.indexOf("MSIE 9.0")>0 ||navigator.userAgent.indexOf("MSIE")>0 && navigator.userAgent.indexOf("MSIE 8.0")>0){
        var setHeader = function(object, data, headers) {
            headers['Access-Control-Allow-Origin'] = '*';
            headers['Access-Control-Request-Headers'] = 'content-type';
            headers['Access-Control-Request-Method'] = 'POST';
        };
        uploaderS.on('uploadBeforeSend ', setHeader);
    }
}

//给附件中的删除按钮绑定事件
function bindEvent() {
	$('.ndiv').on('click', 'span.del' ,function(){
		var filePathArrytem = $('#filePaths').val().split("$");
		var span = $(this);
		var $p = span.parent();
		// 使用splice函数进行数组元素移除：
		var index = $('div.file-list p.file-item').index($p);
		if (index > -1) {
			filePathArrytem.splice(index, 1);
		}
		$('#filePaths').val(filePathArrytem.join("$"));
		$p.remove(); //移除这一行
	});
}


//判断字符串是否为空
function isDataNull(str) {
    if (str == null || str == "" || str == undefined) {
        return true;
    }
    return false;
}

//保存成功提示
function outTips(){
	Chief.layer.close();
	queryUsers(); // 调用查询列表
}

//取消按钮关闭弹窗
function doCancel(){
    Chief.layer.close();
}

$(document).ready(function() {
	queryUsers();
	eventInit();// 点击事件初始化
});

