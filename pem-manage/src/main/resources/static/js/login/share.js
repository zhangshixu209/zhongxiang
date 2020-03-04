$(document).ready(function(){
	let extensionCode = getUrlParam("extensionCode");
	$("#extensionCode").val(extensionCode);
	console.log(extensionCode);
	$(".lsh_list_innerbox .sex_box a").click(function(){
		$(this).addClass("cur").siblings().removeClass("cur");
	})
});

// 发送短信验证码
var sends = {
	checked:1,
	send:function(){
		let memberAccount = $("#memberAccount").val();
		if (isDataNull(memberAccount)) {
			Chief.layer.tips("请输入手机号", 800);
			return false;
		}
		Chief.ajax.postJson('/member/sendCodeForWeb/' + memberAccount , {}, function(data){
			if (data.code == '0') {
				var time = 60;
				function timeCountDown(){
					if(time == 0){
						clearInterval(timer);
						$('a.lsh_a1').removeClass("clicker").html("获取验证码");
						sends.checked = 1;
						return true;
					}
					$('a.lsh_a1').html(time+"S");
					time--;
					return false;
					sends.checked = 0;
				}
				timeCountDown();
				$('a.lsh_a1').addClass("clicker");
				var timer = setInterval(timeCountDown,1000);
				$("#sessionId").val(data.item.sessionId);
				Chief.layer.tips(data.msg);
			} else {
				Chief.layer.tips(data.msg);
				return false;
			}
		});
	}
};

// 注册
function appRegister() {// 注册
	var password = $("#password").val();
	var password2 = $("#password2").val();
	var username = $("#memberAccount").val();
	var msgCode = $("#code").val();
	var sessionId = $("#sessionId").val();
	if(Chief.isEmpty(username)){
		Chief.layer.tips("请输入手机号");
		return ;
	}
	if(Chief.isEmpty(msgCode)){
		Chief.layer.tips("请输入验证码");
		return ;
	}
	if(Chief.isEmpty(password)){
		Chief.layer.tips("请输入密码");
		return ;
	}
	if(Chief.isEmpty(password2)){
		Chief.layer.tips("请输入确认密码");
		return ;
	}
	if(Chief.isNotEmpty(password2) && Chief.isNotEmpty(password)){
		if (password2 != password) {
			Chief.layer.tips("两次密码输入不一致！");
			return ;
		}
	}
	let extensionCode = $("#extensionCode").val();
	let params = {
		memberAccount: username,
		memberPwd: password,
		code: msgCode,
		sessionId: sessionId,
		extensionCode: extensionCode
	};
	Chief.ajax.postJson('/member/userRegister', params, function(data){
		if(data.code === '0'){
			Chief.layer.tips(data.msg);
		}else{
			checkcodemethod();
			Chief.layer.tips(data.msg);
		}
	});
}

function downloadApp() {
	window.open('http://www.lhzxkj.cn:8080/pem-manage/api/appVersion/downloadQRCode');
}

//校验非空
function isDataNull(str) {
	if (str == null || str == "" || str == undefined) {
		return true;
	}
	return false;
}