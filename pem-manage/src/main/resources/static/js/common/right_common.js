$(function() {
	// 获取用户拥有的菜单
	getMenuList();
	// 退出登录
	$(document).on("click", "#loginOutBtn", function() {
		layer.confirm('真的要注销吗?', function(index){
			Chief.ajax.postJson('/pb/admin/lgOut', {}, function(data) {
				if (data.code == '0') {
					location.href = "login/login.html";
				}
			});
			  
			  layer.close(index);
			});       
	});
	// 加载登陆信息
	Chief.ajax.postJson('/pb/admin/getLoginInfo', {}, function(data) {
		if (data.code == '0') {
			$("#userName").html(data.item.userName);
			$("#roleName").html(data.item.roleName);
		}
	});
	
	//修改密码弹框
	$(document).on('click', '#upPassBtn', function() {
		var htmls = Handlebars.compile($("#updPass").html());
		var ht = htmls();
		Chief.layer.newEmptyDiv("修改密码", ht, '750px', "400px", "50px");
		checkcodemethod();
		checkForm();
	});
		
	//修改密码确认按钮
	$("body").on('click', '#b_updpass_confirm', function(){
		updatePassword();
		});
	
	//修改密码取消按钮
	$("body").on('click', '#b_updpass_cancel', function() {
		Chief.layer.close();
	});
	//刷新tab页宽度
	$("body").on('click', '#sidebar-collapse', function() {
		$(".panel-body").css("width","100%");
	});
	
});
// 获取登录用户所拥有的菜单
function getMenuList() {
	Chief.ajax.postJson(
					"/adminRight/queryMenuList",
					null,
					function(result, flag) {
						if (result.code == 0) {
							var menuList = result.items;
							var html = "";
							if (null != menuList) {
								for (var i = 0; i < menuList.length; i++) {
									var menu = menuList[i];
									// 一级菜单由于有图标，所以单独拿出来遍历
									var icon = menu.uiPrivCd;
									if (null != menu.child) {
										// 有下一级菜单
										html = html + '<li>';
										html = html
												+ '<a href="#" class="dropdown-toggle">';
										html = html + '    <i class="' + icon
												+ '"></i>';
										html = html
												+ '    <span class="menu-text">'
												+ menu.name + '</span>';
										html = html
												+ '    <b class="arrow icon-angle-down"></b>';
										html = html + '</a>';
										html = html + '<ul class="submenu">';
										// 迭代生成下一级菜单的html代码
										html = html
												+ getChildMenuHtml(menu.child);
										html = html + '</ul>';
										html = html + '</li>';
									} else {
										// 没有下一级菜单
										html = html + ' <li>';
										html = html
												+ '     <a href="javascript:;" class="subpage-url" data-url=".'
												+ menu.urlAddr + '">';
										html = html + '         <i class="'
												+ icon + '"></i> ' + menu.name;
										html = html + '     </a>';
										html = html + ' </li>';
									}
								}
							}
							// console.log(html)
							$("#menuHtml").html(html);
							// 默认选中首页
							$('#menuHtml>li:first').addClass('active').find('ul')
									.show().find('li').addClass('active')
						} else if (result.code == -3) {
							window.location.href = result.data;
						}
						// 子页面点击切换
						$(".nav-list .subpage-url").on('click', function() {
							if($(this).parents(".open").length == 0){
								$("#menuHtml .open .dropdown-toggle").trigger("click");
							}
							$("#sidebar li").removeClass('active');
							$(this).parent('li').addClass('active');
							var thisurl = $(this).attr("data-url");
//							$("#subpage").attr('src', thisurl);
							//iFrameHeight();
							var thistitle = $(this).text();//菜单名称
							addTab(thistitle,thisurl,0);//0标识父亲
							sessionStorage.setItem("right_title",thistitle);//存
						})
					})
}
var body_height;
if (window.frames.length == parent.frames.length) { // 判断是否执行在iframe环境中
	body_height = (document.body.clientHeight - 103) + "px";　　
}else{
	body_height = document.body.clientHeight + "px";
}
$(window).resize(function(){
	if (window.frames.length == parent.frames.length) {
		body_height = (document.body.clientHeight - 103) + "px";　　
	}
	$(".frame").css("height",body_height);
})
//使用tab
function addTab(title, url,num){
	if (window.frames.length == parent.frames.length) { // 判断是否执行在iframe环境中
		body_height = (document.body.clientHeight - 103) + "px";　　
	}else{
		body_height = document.body.clientHeight + "px";
	}
	title = title.replace(/^\s*|\s*$/g,"");
	if(num == 0){
		if ($('#tabs-list').tabs('exists', title)){
			$('#tabs-list').tabs('select', title);
			var tab = $('#tabs-list').tabs('getSelected');
			var index = $('#tabs-list').tabs('getTabIndex',tab);
			$('#tabs-list').tabs('close',title);
			var content = '<iframe  frameborder="0" class="frame"  src="'+url+'" style="min-width:1120px;width:100%;height:'+ body_height +'"></iframe>';
			$('#tabs-list').tabs('add',{
				title:title,
				content:content,
				closable:true,
				index:index
			});
		} else {
			var content = '<iframe  frameborder="0" class="frame"  src="'+url+'" style="min-width:1120px;width:100%;height:'+ body_height +'"></iframe>';
			$('#tabs-list').tabs('add',{
				title:title,
				content:content,
				closable:true
			});
		}
		$(".panel-body.panel-body-noheader.panel-body-noborder").css("width","100%");
	}else{
		if (parent.$('#tabs-list').tabs('exists', title)){
			parent.$('#tabs-list').tabs('select', title);
			var tab = $('#tabs-list').tabs('getSelected');
			var index = $('#tabs-list').tabs('getTabIndex',tab);
			$('#tabs-list').tabs('close',title);
			var content = '<iframe  frameborder="0" class="frame"  src="'+url+'" style="min-width:1120px;width:100%;height:'+ body_height +'"></iframe>';
			parent.$('#tabs-list').tabs('add',{
				title:title,
				content:content,
				closable:true,
				index:index
			});
		} else {
			var content = '<iframe  frameborder="0" class="frame"  src="'+url+'" style="min-width:1120px;width:100%;height:'+ body_height +'"></iframe>';
			parent.$('#tabs-list').tabs('add',{
				title:title,
				content:content,
				closable:true
			});
		}
		$(".panel-body.panel-body-noheader.panel-body-noborder").css("width","100%");
	}
}


// 获取菜单页面代码
function getChildMenuHtml(menuList) {
	var html = "";
	for (var i = 0; i < menuList.length; i++) {
		var menu = menuList[i];
		if (null != menu.child) {
			// 有下一级菜单
			html = html + '<li>';
			html = html + '<a href="#" class="dropdown-toggle">';
			html = html + '    <i class="icon-list"></i>';
			html = html + '    <span class="menu-text">' + menu.name
					+ '</span>';
			html = html + '    <b class="arrow icon-angle-down"></b>';
			html = html + '</a>';
			html = html + '<ul class="submenu">';
			// 无限迭代获取下一级子菜单html代码
			html = html + getChildMenuHtml(menu.child);
			html = html + '</ul>';
			html = html + '</li>';
		} else {
			// 无下一级菜单
			html = html + ' <li>';
			html = html
					+ '     <a href="javascript:;" class="subpage-url" data-url=".'
					+ menu.urlAddr + '">';
			html = html + '         <i class="icon-double-angle-right"></i> '
					+ menu.name;
			html = html + '     </a>';
			html = html + ' </li>';
		}
	}
	return html;
}

//修改密码输入框验证
function checkForm(){
	$("#regist-form").validate({
		rules : {
			// 密码校验
			password : {
				required : true
				//range:[6,16]
			},
			// 图形验证码
            regcheckcode : {
                required : true,
                maxlength : 4,
                minlength : 4
            },
  
			// 密码校验
			newPassword : {
				required : true,
				checkPsd : true,
				notEqualTo:"#password",
				maxlength : 16,
				minlength : 6
			},
			// 确认密码校验
			comfirmPassword : {
				required : true,
				equalTo : "#newPassword"
			}
		},
		messages : {
			password : {
				required : "请输入原密码"
			},
			
            regcheckcode:{
                required : "请输入图片验证码",
                maxlength : "图片验证码为4位",
                minlength : "图片验证码为4位"
            },
			newPassword : {
				required : "请输入新密码",
				notEqualTo: "新密码不能和原密码相同"
			},
			comfirmPassword : {
				required : "请确认新密码",
				equalTo :"重复密码与新密码不一致"
			}
		},
		errorElement: 'div',
		errorClass: 'help-block',
		errorPlacement : function(error, element) {
			error.insertAfter(element.parent());
		}
	});
	
}

//修改密码提交
function updatePassword(){
	if(!$("#regist-form").valid()){
		return false;
	}
	// 获取输入的用户名和密码值
	var password = $("#password").val();
	var newPassword = $("#newPassword").val();
	var comfirmPassword = $("#comfirmPassword").val();
	var imgCode = $("#regcheckcode").val();
	Chief.ajax.postJson('/index/main/updateAdminPassword', {"password":password,"newPassword":newPassword,
		"comfirmPassword":comfirmPassword,"imgCode":imgCode}, function(data){
		if(data.code == '0'){
				Chief.layer.tips("修改成功");
				Chief.layer.close();
		}else{
			checkcodemethod();
			Chief.layer.tips(data.msg,2000);
		}
	})
}

//验证码图片
function checkcodemethod(){	
	$("#codeImage").attr('src','/pem-manage/api/index/main/imgCode?codetime='+new Date().getTime());
}


