$(function() {
	btnAuthorize();
});

/**
 *	按钮权限控制,用于平台管理端页面按钮是否授权显示
 */
function btnAuthorize(obj) {
	var params = [];
	var ele = $('[mo]');
	var sw = false;
	if(obj != undefined){//特殊处理handlebars初始化结束模板中的按钮
		sw = true;
	}
	/*if(obj && obj instanceof jQuery) {
		ele = obj.find('[mo]');
		sw = true;
	}*/
	ele.each(function() {
		var _this = $(this);
		var tempVal = {
			'btnCd' : _this.attr('mo'),
			'btnId' : _this.attr('id')
		};
		params.push(tempVal);
	});	
	if(params.length) {
		var btns = JSON.stringify(params);
		Chief.ajax.postJson('/adminRight/btnAuthorize',{'btns':btns},function(result){
			if("0" == result.code && result.items && result.items.length > 0) {
				var len = result.items.length;
				for(var i = 0; i < len; i++){
					var tmp = result.items[i];
					var tgt = sw ? $('.btn-danger[mo='+tmp.mo+']') : $('#'+tmp['btnId']);
					if(tgt) {
						if (tmp['btnCd'] == '1') {
							if(tgt.attr("callback") != undefined) {
								tgt.show('normal', doCallback(tgt.attr("callback")));
							} else {
								tgt.show();
							}
						}else{
							tgt.hide();
						}
					}
		    	}
			}
		});
	}
}

/**
 * 执行字符串函数回调
 */
function doCallback(cb) {
	if(typeof cb === "string") {
		var func, args;
		if(cb.indexOf("(") != -1 && cb.indexOf(")") != -1) {
			func = window[cb.substring(0, cb.indexOf("("))];
			args = cb.substring(cb.indexOf("(") + 1, cb.indexOf(")")).replace(/\s+/g, "").split(",");
		} else {
			func = window[cb];
		}
		if(typeof func === 'function') {
			if(args) {
				for(var i = 0; i < args.length; i++) {
					if(args[i].indexOf("'") == -1 && args[i].indexOf("\"") == -1) {
						var tmp = parseInt(args[i]);
						if(!isNaN(tmp)) {
							args[i] = tmp;
						}
					}
				}
				func.apply(window, args);
			} else {
				func.apply(window);
			}
		}
	}
}