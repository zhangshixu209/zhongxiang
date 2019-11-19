function iFrameHeight() {
	/*var ifm = document.getElementById("subpage");
	var subWeb = document.frames ? document.frames["mainweb"].document
			: ifm.contentDocument;
	if (ifm != null && subWeb != null) {
		ifm.height = subWeb.body.scrollHeight;
	}*/
	//$("#subpage").height($('.page-content').height())
}
/**
 * 點擊音頻播放
 * @data_type  true => 表示表格中的播放按鈕  false => 表示詳情中的播放
 * true 的話  就按照正常流程彈框
 * false   隱藏按鈕  把播放器放在父元素 #playSoundBox 中
 * **/
$('body').on('click', '.playButton', function() {
	var data_type = $(this).attr('data-type');
	Chief.record.play(this, data_type)
})

/**
 * 点击tr的时候行变色,返回复选框结果
 * @param {当前点击的元素} _this 
 */
function changeColor(_this){
	var check = _this.find('input[type=checkbox]').prop('checked');
	if (check) {
		_this.find('input[type=checkbox]').removeProp('checked');
		_this.find('input[type=checkbox]').prop('checked', false).change();
		_this.find('td').removeClass('addTrColor');
		//有一个选中th的全选复选框为不选中
		_this.parents("table").find("th input[type=checkbox]").prop('checked', false);
	} else {
		_this.siblings('tr').find('input[type=checkbox]').prop('checked', false);
		_this.find('input[type=checkbox]').prop("checked", true).change();
		_this.siblings('tr').find('td').removeClass('addTrColor');
		_this.find('td').addClass('addTrColor');
	}
	return check;
}

//點擊tr--列表单选
$('body').on('click','.radioBox tbody tr',function(e) {
	if ($(this).parents('table').attr('date-type') == 'false') {
		return false
	}
	if (e && e.stopPropagation) {
		//W3C取消冒泡事件
		e.stopPropagation();
	} else {
		//IE取消冒泡事件
		window.event.cancelBubble = true;
	}
	if($(this).parents(".fht-fixed-body").length > 0){
		var checked = changeColor($(this));
		var indexClick = $(this).index(".fht-fixed-body .fht-tbody tbody tr");
		var _that = $(".fht-fixed-column .fht-tbody tbody tr").eq(indexClick);
		if(checked){
			_that.find('input[type=checkbox]').removeProp('checked')
			_that.find('input[type=checkbox]').prop('checked', false)
					// .change()
			_that.find('td').removeClass('addTrColor')
		}else{
			_that.siblings('tr').find('input[type=checkbox]').prop(
				'checked', false)
			_that.find('input[type=checkbox]').prop("checked", true)
					// .change()
			_that.siblings('tr').find('td').removeClass('addTrColor')
			_that.find('td').addClass('addTrColor')
		}
	}else if($(this).parents(".fht-fixed-column").length > 0){
		var indexClick = $(this).index(".fht-fixed-column .fht-tbody tbody tr");
		$(".fht-fixed-body .fht-tbody tbody tr").eq(indexClick).trigger("click");
	}else{
		changeColor($(this));
	}
});
//列表多选
$('body').on('click','.checkboxBox tbody tr',function(e,param) {
	//var notrclickFlag = returnParam(e)
	if ($(this).parents('table').attr('date-type') == 'false') {
		return false
	}
	if (e && e.stopPropagation) {
		//W3C取消冒泡事件
		e.stopPropagation();
	} else {
		//IE取消冒泡事件
		window.event.cancelBubble = true;
	}
	if(param || !$(this).hasClass("notrclick")){
		var check = $(this).find('input[type=checkbox]').prop('checked');
		if (check) {
			$(this).find('input[type=checkbox]').removeProp('checked');
			$(this).find('input[type=checkbox]').prop('checked', false).change();
			$(this).find('td').removeClass('addTrColor');
			//有一个选中th的全选复选框为不选中
			$(this).parents("table").find("th input[type=checkbox]").prop('checked', false);
		} else {
			$(this).find('input[type=checkbox]').prop("checked", true).change();
			$(this).find('td').addClass('addTrColor');
		}
		var allLength = $(this).parent("tbody").find("input[type=checkbox]").length;
			var checkedLength = $(this).parent("tbody").find("input[type=checkbox]:checked").length;
			if(allLength == checkedLength){
			$(this).parents("table").find("th input[type=checkbox]").prop('checked', true);
			}else {
			$(this).parents("table").find("th input[type=checkbox]").prop('checked', false);
			}
	}else{
		return false;
	}
});

$('body').on('click', 'tbody tr input[type=checkbox]', function(e) {
	if (e && e.stopPropagation) {
		//W3C取消冒泡事件
		e.stopPropagation();
	} else {
		//IE取消冒泡事件
		window.event.cancelBubble = true;
	}
	$(this).prop("checked", !$(this).prop('checked'))
	if($(this).parents("tr.notrclick").length != 0){
		$(this).parents('tr').trigger("click",true);
	}else{
		$(this).parents('tr').click();
	}
	// return false
})

//点击重置按钮
$('#reset').on('click',function() {
	$(this).parent().parent().parent().find('input').val('')
	$(this).parent().parent().parent().find('select').find('option:first').prop("selected", 'selected');
});

//全选全不选
$("body").on("click",'thead th input[type=checkbox]',function() {
    if($(this).prop("checked") == true) {
        // 当前复选框的祖先及table下面的tr里面的复选框全部被被选中
        $(this).parents("table").find("tr input[type=checkbox]").prop("checked", true);
		$(this).parents("table").find("tr td").addClass('addTrColor');
    }else {
        // 当前复选框的祖先及table下面的tr里面的复选框全不选
        $(this).parents("table").find("tr input[type=checkbox]").prop("checked", false);
		$(this).parents("table").find("tr td").removeClass('addTrColor');
    }
});