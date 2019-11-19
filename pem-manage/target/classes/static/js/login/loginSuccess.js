$(document).ready(function(){
	// 加载登陆信息
	Chief.ajax.postJson('/pb/admin/queryUserLoginInfo', {}, function(data) {
		if (data.code == '0') {
			loginPassWord();
		}
	});
});
//校验密码如果是初始化  弹出修改窗口
function loginPassWord(){
	var htmls = Handlebars.compile($("#updeUserPassWord").html());
	var ht = htmls();
	Chief.layer.newEmptyDiv2("提示", ht, '400px', "200px", "50px");
	$("div > span > a").remove();
}

$("body").on('click', '#b_updeUserPassWord_confirm', function(){
	var htmls = Handlebars.compile($("#updPass").html());
	var ht = htmls();
	Chief.layer.newEmptyDiv("修改密码", ht, '750px', "400px", "50px");
	$("#b_updpass_cancel").attr("style", "display:none;");
	checkcodemethod();
	checkForm();
});
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
//验证码图片
function checkcodemethod(){	
	$("#codeImage").attr('src','/report-manage/api/index/main/imgCode?codetime='+new Date().getTime());
}