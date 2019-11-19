//倒计时
var time = $(".time").html();
var timer = setInterval(function(){
	time--;
	if(time <= 0){
		time = 0;
		clearInterval(timer);
		window.history.back(-1);
	}
	$(".time").html(time);
},1000);
$("#goBack").click(function(){
	window.history.back(-1);
});
