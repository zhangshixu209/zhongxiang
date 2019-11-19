$(document).ready(function(){
	//验证form表单
/*	$("#login-form").validate({
		rules : {*//****************//*//验证规则
            mobile:{
                required : true,
                minlength : 11,
 				maxlength : 11				
            },
            password:{
 				required : true,
 				checkPsd : true,
 				minlength : 6,
 				maxlength : 20
 			},
 			regcheckcode:{
 				required : true,
 				minlength : 4,
 				maxlength : 4
 			}
         },
         messages:{
        	mobile : {
				required : "请输入手机号",
				mobile : "手机号不正确"
			},
			password : {
				required : "请输入密码",
				checkPsd : "密码不正确"
			},
			regcheckcode:{
				required : "请输入图片验证码",
				minlength : "图片验证码为4位",
                maxlength : "图片验证码为4位"
			}
 		},
 		errorElement: 'div',
		errorClass: 'help-block',
		errorPlacement : function(error, element) {
			// error是错误提示元素span对象  element是触发错误的input对象
			error.insertAfter(element.parent());
		},
		onfocusout: function(element) {
	   		$(element).valid();
		}
	});*/
	//$("#verificationCode-wrap").hide();//短信验证码隐藏
	//获取验证码
	checkcodemethod();
	judge();
	//切换验证码
	$("#codeImage").on("click",function(){
		checkcodemethod();
	});
});
/*jQuery.validator.addMethod("ispass", function(eleVal, element) {//密码验证
	
	regPatt = /^[\u4E00-\u9FA5\uF900-\uFA2D]$/;
	if(regPatt.test(eleVal)){
		//msg="密码为非汉字";
		return false;
	}else if(eleVal.length<6 | eleVal.length>20){
		//msg="密码为6-20个字符";
		return false;
	}else{
		return true;
	}
	
}, "密码为6-20个字符");*/

function adminlogin() {//登录
	if ($("#login-form").valid()) {// 通过之后回调
		var password = document.getElementById("password").value;
		var username = document.getElementById("mobile").value;
		var codeImage = document.getElementById("regcheckcode").value;
		var msgCode = document.getElementById("verificationCode").value;
		if(Chief.isEmpty(username)){
			Chief.layer.tips("请输入手机号");
			return ;
		}
		if(Chief.isEmpty(password)){
			Chief.layer.tips("请输入密码");
			return ;
		}
		if(Chief.isEmpty(codeImage)){
			Chief.layer.tips("请输入验证码");
			return ;
		}
		Chief.ajax.postJson('/pb/admin/login', {"mobile":username,"password":encodeURIComponent(password),"imgCode":codeImage,"msgCode":msgCode}, function(data){
			if(data.code === '0'){
				//登录成功
				window.location.href = "../index.html";
			}else{
				checkcodemethod();
				Chief.layer.tips(data.msg);
			}
		});
	}
}

function judge(){
	Chief.ajax.postJson('/pb/admin/needs', {}, function(data){
		if(data.code === '0' && data.data ==="1"){
			$("#verificationCode-wrap").show();//短信验证码显示
		}
	});
}

//验证码图片
function checkcodemethod(){	
	$("#codeImage").attr('src','../api/pb/admin/imgCode?codetime='+new Date().getTime());
			
}

//回车事件
document.onkeydown = function (event) {
    var e = event || window.event;
    if (e && e.keyCode == 13) { //回车键的键值为13
        adminlogin();
    }
};



//点击发送手机验证码
$("#ActhCodeDate").on("click",function(){
	var _this = $(this);
	sendMsg(_this);
});

//发送手机验证码
function sendMsg(_this){
	//按钮不可点
	_this.attr("disabled","true");
	
	var imgCode = $("#regcheckcode").val();
	var mobile = $("#mobile").val();
	if(Chief.isEmpty(mobile)){
		Chief.layer.tips("请填写手机号码");
		_this.removeAttr("disabled");
		return;
	}
	if(!$("#login-form").validate().element($("#mobile"))){
		Chief.layer.tips("请填写正确的手机号");
		_this.removeAttr("disabled");
		return;
	}
	if(Chief.isEmpty(imgCode)){
		Chief.layer.tips("请输入图片验证码");
		_this.removeAttr("disabled");
		return;
	}
	Chief.ajax.postJson('/pb/admin/sendMsg', {"phone":mobile,"imgCode":imgCode}, function(data){
		if(data.code == '3' || data.code == '-1'){
			Chief.layer.tips(data.msg);
			checkcodemethod();
			_this.removeAttr("disabled");
		}else if(data.code == '1'){
			Chief.layer.tips(data.msg);
//			$("#verificationCode").val(data.data);
			sendCodeTime(document.getElementById("ActhCodeDate"));
		}
	});
}

//短信验证码获取倒计时
var clock = '';
var nums = 60;
var btn;
function sendCodeTime(thisBtn){ 
	btn = thisBtn;
	btn.disabled = true; //将按钮置为不可点击
	btn.value = nums+'秒后重新获取';
	clock = setInterval(doLoop, 1000); //一秒执行一次
}
function doLoop(){
	nums--;
	if(nums > 0){
		btn.value = nums+'秒后重新获取';
	}else{
		 clearInterval(clock); //清除js定时器
		 btn.disabled = false;
		 btn.value = '获取验证码';
		 nums = 60; //重置时间
	}
}
//验证码结束

/**
 * 隐藏显示密码
 */
function hideShowPsw(){
	var pType = $("#password").attr("type"); // 获取input类型
	// 判断input的类型
	if (pType == "password") {
		$("#password").attr("type", "text");
		$("#eye_img").attr("src", "../img/invisible.png");
	} else {
		$("#password").attr("type", "password");
		$("#eye_img").attr("src", "../img/visible.png");
	}
}
