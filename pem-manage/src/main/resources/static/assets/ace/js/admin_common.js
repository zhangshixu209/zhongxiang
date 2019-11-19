//开始结束时间选择
//$('.date-picker').datepicker({autoclose:true}).next().on(ace.click_event, function(){
//	$(this).prev().focus();
//});
$('.datetimepicker').datetimepicker({
	language: 'zh-CN',
	todayHighlight: 1,//今天高亮
	// startView:2,
	// forceParse: 0,
	// showMeridian: 1,
	// minView: 2, 
	validateOnBlur:true,
	autoclose: 1//选择后自动关闭
});
function iFrameHeight() {
	/*var ifm= document.getElementById("subpage");
	var subWeb = document.frames ? document.frames["mainweb"].document :
	ifm.contentDocument;
	if(ifm != null && subWeb != null) {
	ifm.height = subWeb.body.scrollHeight;
	}*/
	$("#subpage").height($('.page-content').height())
}