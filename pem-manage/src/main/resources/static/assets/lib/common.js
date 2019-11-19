// 公共参数配置
var list = {
    limit : 10
}
$.ajaxSetup({
    statusCode: {
        499: function (data) {
        	window.location.href = data.responseText;
        }
    }
});
$('body').on('click','a,button',function (e) {
    $(this).blur();
})
var USER_ID,
	CORP_VCCID = "111111",
	MS_SERVER = "ms",
	DOWNLOAD_TYPE = "preview",
	PAR = window.parent,
	PAR_DOC = window.parent.document,
	TOP = window.top,
	TOP_DOC = window.top.document;

Chief = {
	/**
	 * 取消事件冒泡
	 * @param {Object}
	 *            e 事件对象
	 */
	stopBubble: function (e) {
		if (e && e.stopPropagation) {
			e.stopPropagation();
		} else {
			// ie
			window.event.cancelBubble = true;
		}
	},
	/**
	 * 入参转码
	 * @param {string}
	 * 		json格式
	 */
	transCoding: function (json) {
		var temp = encodeURIComponent(json);
		temp = CryptoJS.enc.Utf8.parse(temp);
		temp = CryptoJS.enc.Base64.stringify(temp);
		return temp;
	},
	/**
	 * 入参转码
	 * @param {string}
	 * 		json格式
	 */
	transDecoding: function (objStr) {
		var words = CryptoJS.enc.Base64.parse(objStr);
		words = words.toString(CryptoJS.enc.Utf8);
		words = decodeURIComponent(words)
		return words;
	},
	isEmpty: function (v) {
		switch (typeof v) {
			case 'undefined':
				return true;
			case 'string':
				if (v.replace(/(^[ \t\n\r]*)|([ \t\n\r]*$)/g, '').length == 0) return true;
				break;
			case 'boolean':
				if (!v) return true;
				break;
			case 'number':
				if (0 === v || isNaN(v)) return true;
				break;
			case 'object':
				if (null === v || v.length === 0) return true;
				for (var i in v) {
					return false;
				}
				return true;
		}
		return false;
	},
	isNotEmpty: function (v) {
		return !this.isEmpty(v)
	},
	setDisabled: function (btn, isDisabled) {
		$(btn).attr("disabled", isDisabled ? true : false)
	},
	/**
	 * URL中文参数-转码(转码后URL传送)
	 * @param :string
	 */
	encodeZh: function (v) {
		var evz = encodeURI(encodeURI(v));
		if (v != null) return evz;
		return null;
	},
	/**
	 * 获取URL中文参数并转码
	 * @param :string
	 */
	getUrlZh: function (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		var rZh = decodeURI(decodeURI(r[2]));
		if (r != null) return rZh;
		return null;
	},
	/**
	 * 电话加密
	 * @param mobile
	 * @returns 加密后的电话
	 */
	mobi2Str: function (mobile) {
		if (mobile.length === 11) {
			var str = mobile.substr(mobile.length - 3);
			for (var i = 3; i < 8; i++) {
				str += Chief.codeArr[mobile.charAt(i)];
			}
			return str + mobile.substr(0, 3);
		} else {
			return mobile;
		}
	},
	/**
	 * 电话解密
	 * @param str
	 * @returns 解密后的电话
	 */
	str2mobi: function (str) {
		if (str.length === 11) {
			var mobi = str.substr(str.length - 3);
			for (var i = 3; i < 8; i++) {
				var c = str.charAt(i);
				for (var j = 0; j < Chief.codeArr.length; j++) {
					if (Chief.codeArr[j] == c) {
						mobi += j;
						break;
					}
				}
			}
			return mobi + str.substr(0, 3);
		} else {
			return str;
		}
	},
	/**
	 * 将json对象转换为json字符串，解决IE8下用stringify中文会变成uncode编码的问题
	 * @param obj  json对象
	 * @returns json字符串
	 */
	jsonStringify: function (obj) {
		eval("var jsonStr = '" + JSON.stringify(obj) + "';");
		return jsonStr;
	},
	/**
	 * 将{"a":1,"b":2}这样的数据转换为a=1&b=2这样的字符串
	 * @param obj 原始数据，只支持object，并且属性的值只支持数字，布尔、字符串
	 * @param split 分隔符，默认是&，可不传
	 * @returns
	 */
	objToUrlParam: function (obj, split) {
		split = split ? split : '&';
		var returnStr = '';
		if (Object.prototype.toString.call(obj) == '[object Object]') {
			for (var data in obj) {
				var dataType = Object.prototype.toString.call(obj[data]);
				if (dataType == '[object String]' || dataType == '[object Number]' || dataType == '[object Boolean]') {
					returnStr += split + data + '=' + obj[data];
				}
			}
		}
		return returnStr.length > 0 ? returnStr.substr(1) : '';
	},
	/**
	 * 将公司名称按首字母排序
	 */
	channelSort: function (a, b) {
		return a.channelName.localeCompare(b.channelName);
	},
	/**
	 * 初始化下拉列表
	 * @param ele  select元素选择器
	 * @param beans  数据
	 * @param dataId id的字段名称
	 * @param dataName  显示的name的字段名称
	 * @returns {String}  option的html字符串
	 */
	initSelectOpts: function (ele, beans, dataId, dataName) {
		var optHtml = "";
		dataId = dataId ? dataId : 'ID';
		dataName = dataName ? dataName : 'Name';
		$(beans).each(function () {
			optHtml += "<option value='" + this[dataId] + "'>" + this[dataName] + "</option>";
		});
		if (ele) {
			$(ele).append(optHtml);
		}
		return optHtml;
	},
	/**
	 * 文件下载
	 * href  要下载的文件全路径
	 */
	fileDownLoad: function (href) {
		if (href) {
			$(".ifreamOutsD").remove();
			var $iframe = $(
				'<div class="ifreamOutsD" style="width:0px;height:0px;"><iframe id="ifile" width="0" height="0" ></iframe></div>'
			);
			$(document.body).append($iframe);
			document.getElementById("ifile").src = href;
		}
	},
	/**
	 * 文件导出
	 * result  接口返回的result
	 */
	reportExport: function (result) {

		if (result.bean && result.bean.onestUrl) {
			if (Chief.browser.isIE8()) {
				Chief.fileDownLoad2(result.bean.originalUrl, result.bean.fileName);
			} else {
				Chief.fileDownLoad(result.bean.onestUrl);
			}
		} else {
			showMessage('报表下载失败！');
		}
	},
//	取消checkbox选中状态
	checkedThis : function (id,classname,name){//id input父级容器 ，classname是 checkbox class名称,name是input的name
		$(id).on('click','.' + classname,function(){
			var obj = $(this)[0],
				boxArray = document.getElementsByName(name);
	       	for(var i=0;i<=boxArray.length-1;i++){ 
	            if(boxArray[i]==obj && obj.checked){ 
	               boxArray[i].checked = true; 
	            }else{ 
	               boxArray[i].checked = false; 
	            } 
	       	} 
		})
	},
//	小数点后自动补两位小数
	changeTwoDecimal_f : function (x,u) {
        var f_x = parseFloat(x);
        if (isNaN(f_x)) {
            return false;
        };
        var f_x = Math.round(x * 100) / 100;
        var s_x = f_x.toString();
        var pos_decimal = s_x.indexOf('.');
        if (pos_decimal < 0) {
            pos_decimal = s_x.length;
            s_x += '.';
        };
        while (s_x.length <= pos_decimal + 2) {
            s_x += '0';
        };
        return $(u).val(s_x);
    }
};
// 路径处理工具
Chief.http = {
	// 获取请求路径（不含协议头、域名/地址、端口）
	getReqUri: function (path) {
		var reg = new RegExp("^(http[s]?://)?[^\\/]*(/.*)");
		var ary = path.match(reg);
		if (ary != null) {
			return ary[2];
		}
		return path;
	}
};
Chief.record = {
	vcc_id: "111111",
	/**
	 * 获取录音地址
	 * @param sel jquery选择器或对象
	 */
	getRecordUrl: function (sel) {
		var _ele = (sel instanceof jQuery) ? sel : $(sel),
			href,
			data = {
				"recordFlag": _ele.attr("record-Flag"),
				"serUrl": _ele.attr("data-record"),
				"fileUrl": _ele.attr("data-url"),
				"fileName": _ele.attr("data-file"),
				"msServer": _ele.attr("data-msServer"),
				"http": _ele.attr("data-http")
			};
        href = data.msServer+'/'+data.fileUrl+'/'+data.fileName
		// if (data.recordFlag === "true") {
		//	href = Chief.wrapUrl(Chief.http.getReqUri(data.http + data.fileUrl)) + (data.fileName ? ("/" + data.fileName) : "");
		// } else {
		// 	data.fileUrl = data.fileUrl.replace(/\//g, "*");
		// 	href = Chief.wrapUrl(Chief.http.getReqUri(data.serUrl) + "&corpVccId=" + Chief.record.vcc_id + "&msServer=" + data
		// 		.msServer + "&fileName=" + data.fileUrl);
		// 	href += data.fileName ? ("*" + data.fileName) : "";
		// }
		return href;
	},
	/**
	 * 选择回复的录音
	 * @param $audio 录音标签选择器
	 * @param toSrc  当前点击的单选框的src
	 */
	chooseRecord: function ($audio, toSrc) {
		var curSrc = $audio.attr("src"),
			ie8Flag = Chief.browser.isIE8();
		if (toSrc != curSrc) {
			$audio.attr("src", "");
			try {
				$audio[0].pause();
			} catch (e) {}
			if (ie8Flag) {
				var _par = $audio.parent();
				//$audio.remove();
				_par.html(
					"<EMBED type='audio/mp3' style='vertical-align:middle;width: 350px;height: 40px;' class='reply-record' src='" +
					toSrc + "' id='reply' autostart='false'></EMBED>")
			} else {
				$audio.attr("src", toSrc);
				//$audio.parent().html("<audio style='vertical-align:middle;width: 350px;height: 40px;' class='reply-record' controls='controls' preload='preload' id='reply' type='audio/mp3' src='" + toSrc + "'></audio>");
				$(PAR_DOC).find('#reply').mediaelementplayer({
					alwaysShowControls: true,
					features: ['playpause', 'volume', 'progress'],
					audioVolume: 'horizontal',
					audioWidth: "350px",
					startVolume: 0.8,
					loop: false
				});
			}
		}
	},
    /**
     * 播放录音
     * @param selector jquery选择器或对象
	 * @param data_type  判斷詳情播放按鈕還是表格播放按鈕
     */
    play: function (selector,data_type, href) {
        // showWaitImg();
        selector = (selector instanceof jQuery) ? selector : $(selector);
        var ie8Flag = Chief.browser.isIE8(),
            player;
        // href = href ? href : Chief.record.getRecordUrl(selector);
        var touchId =selector.attr('data-id');
        var url = '/csma/api/video/getRcd?touchId='+touchId
        if (ie8Flag) {
            innerHtml = '<EMBED id="J_play_record" type="audio/mp3" src="' + url + '" width="350px" height="30px"></EMBED>';
        } else {
            innerHtml = "<audio id='J_play_record' controls='controls' preload='preload' type='audio/mp3' src='" + url +
                "'></audio>";
        }
        //判斷是否是詳情    詳情不彈窗，
		if(data_type){
            var params = {
                id: 'd-play-record',
                // top: window.parent,
                title: '播放录音',
                content: innerHtml,
                width: '400px',
                height: '129px',
                btn:['关闭'],
                closeCallback: function () {
                    if (!ie8Flag) {
                        player.pause();
                    } else {
                        PAR_DOC.getElementById("J_play_record").pause();
                    }
                }
            }
            Chief.layer.openArea(params);
		}else {
            $('#playSoundBox').append(innerHtml)
		}
        if (!ie8Flag) {
            // 初始化音频文件
            player = new MediaElementPlayer($(document).find("#J_play_record"), {
                // if the <video width> is not specified, this is the default
                defaultVideoWidth: 480,
                // if the <video height> is not specified, this is the default
                defaultVideoHeight: 270,
                // if set, overrides <video width>
                videoWidth: -1,
                // if set, overrides <video height>
                videoHeight: -1,
                // width of audio player
                audioWidth: 400,
                // height of audio player
                audioHeight: 30,
                // initial volume when the player starts
                startVolume: 0.8,
                // useful for <audio> player loops
                loop: false,
                // enables Flash and Silverlight to resize to content size
                enableAutosize: true,
                // the order of controls you want on the control bar (and other plugins below)
                features: ['playpause','progress','current','duration','tracks','volume','fullscreen'],
                // Hide controls when playing and mouse is not over the video
                alwaysShowControls: false,
                // force iPad's native controls
                iPadUseNativeControls: false,
                // force iPhone's native controls
                iPhoneUseNativeControls: false,
                // force Android's native controls
                AndroidUseNativeControls: false,
                // forces the hour marker (##:00:00)
                alwaysShowHours: false,
                // show framecount in timecode (##:00:00:00)
                showTimecodeFrameCount: false,
                // used when showTimecodeFrameCount is set to true
                framesPerSecond: 25,
                // turns keyboard support on and off for this instance
                enableKeyboard: true,
                // when this player starts, it will pause other players
                pauseOtherPlayers: true,
                // array of keyboard commands
                keyActions: []

            });
            player.load();
        }
        hideWaitImg();
    },
	/**
	 * 录音下载新的处理方法
	 * 第一个是A标签 第二个是所在的div元素
	 * @param specialFlag 明细日报表特殊处理 
	 */
	download: function (selector, setHrefSelect, specialFlag, reportFlag) {
		selector = (selector instanceof jQuery) ? selector : $(selector);
		setHrefSelect = (setHrefSelect instanceof jQuery) ? setHrefSelect : $(setHrefSelect);
		Chief.record.getDownloadRecord(setHrefSelect, specialFlag, selector, reportFlag);
	}
};
/**
 * 日期时间处理工具
 * @namespace Chief
 * @class date
 */
Chief.date = {
	/**
	 * 格式化日期时间字符串
	 *
	 * @method dateTime2str
	 * @param {Date}
	 *            dt 日期对象
	 * @param {String}
	 *            fmt 格式化字符串，如：'yyyy-MM-dd hh:mm:ss'
	 * @return {String} 格式化后的日期时间字符串
	 */
	dateTime2str: function (dt, fmt) {
		var o = {
			"M+": dt.getMonth() + 1, //月份
			"d+": dt.getDate(), //日
			"h+": dt.getHours(), //小时
			"m+": dt.getMinutes(), //分
			"s+": dt.getSeconds(), //秒
			"q+": Math.floor((dt.getMonth() + 3) / 3), //季度
			"S": dt.getMilliseconds() //毫秒
		};
		if (/(y+)/.test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (dt.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		for (var k in o) {
			if (new RegExp("(" + k + ")").test(fmt)) {
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			}
		}
		return fmt;
	},
	/**
	 * 根据日期时间格式获取获取当前日期时间
	 * 
	 * @method dateTimeWrapper
	 * @param {String}
	 *            fmt 日期时间格式，如："yyyy-MM-dd hh:mm:ss";
	 * @return {String} 格式化后的日期时间字符串
	 */
	dateTimeWrapper: function (fmt) {
		if (arguments[0])
			fmt = arguments[0];
		return this.dateTime2str(new Date(), fmt);
	},
	/**
	 * 获取当前日期时间
	 * 
	 * @method getDatetime
	 * @param {String}
	 *            fmt [optional,default='yyyy-MM-dd hh:mm:ss'] 日期时间格式。
	 * @return {String} 格式化后的日期时间字符串
	 */
	getDatetime: function (fmt) {
		return this.dateTimeWrapper(fmt || 'yyyy-MM-dd hh:mm:ss');
	},
	/**
	 * 获取当前日期时间+毫秒
	 * 
	 * @method getDatetimes
	 * @param {String}
	 *            fmt [optional,default='yyyy-MM-dd hh:mm:ss'] 日期时间格式。
	 * @return {String} 格式化后的日期时间字符串
	 */
	getDatetimes: function (fmt) {
		var dt = new Date();
		return this.dateTime2str(dt, fmt || 'yyyy-MM-dd hh:mm:ss') + '.' +
			dt.getMilliseconds();
	},
	/**
	 * 获取当前日期（年-月-日）
	 * 
	 * @method getDate
	 * @param {String}
	 *            fmt [optional,default='yyyy-MM-dd'] 日期格式。
	 * @return {String} 格式化后的日期字符串
	 */
	getDate: function (fmt) {
		return this.dateTimeWrapper(fmt || 'yyyy-MM-dd');
	},
	/**
	 * 获取当前时间（时:分:秒）
	 * 
	 * @method getTime
	 * @param {String}
	 *            fmt [optional,default='hh:mm:ss'] 日期格式。
	 * @return {String} 格式化后的时间字符串
	 */
	getTime: function (fmt) {
		return this.dateTimeWrapper(fmt || 'hh:mm:ss');
	},
	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的字符串转换为日期
	 * @param str yyyy-MM-dd HH:mm:ss格式的字符串
	 * @returns 日期
	 */
	str2Date: function (str) {
		var arr1 = str.split(" ");
		if (arr1.length > 0) {
			var arr2 = arr1[0].split("-");
			if (arr2.length === 3) {
				str = arr2[1] + "/" + arr2[2] + "/" + arr2[0] + " " + (arr1.length > 1 ? arr1[1] : "00:00:00");
			}
		}
		return Date.parse(str);
	},
	/**
	 * 将yyyyMMddHHmmss格式的字符串转换为yyyy/MM/dd HH:mm:ss或yyyy-MM-dd HH:mm:ss
	 * @param str yyyyMMddHHmmss格式的字符串
	 * @pram split "/"或"-"
	 */
	str2Date2: function (str, split) {
		str = $.trim(str);
		if (str.length >= 14) {
			return str.substr(0, 4) + split + str.substr(4, 2) + split + str.substr(6, 2) + " " +
				str.substr(8, 2) + ":" + str.substr(10, 2) + ":" + str.substr(12, 2);
		} else {
			return str;
		}
	}
};
Chief.ajax = {
	/**
	 * 请求状态码
	 * 
	 * @type {Object}
	 */
	reqCode: {
		/**
		 * 成功返回码 0000
		 * 
		 * @type {Number} 1
		 * @property SUCC
		 */
		SUCC: 0,
		/**
		 * 没有访问权限
		 */
		FORBIDDEN: 403
	},
	/**
	 * 请求的数据类型
	 * 
	 * @type {Object}
	 * @class reqDataType
	 */
	dataType: {
		/**
		 * 返回html类型
		 * 
		 * @type {String}
		 * @property HTML
		 */
		HTML: "html",
		/**
		 * 返回json类型
		 * 
		 * @type {Object}
		 * @property JSON
		 */
		JSON: "json",
		/**
		 * 返回text字符串类型
		 * 
		 * @type {String}
		 * @property TEXT
		 */
		TEXT: "text"
	},
	/**
	 * 超时,默认超时30000ms
	 * 
	 * @type {Number} 10000ms
	 * @property TIME_OUT
	 */
	TIME_OUT: 60000,
	/**
	 * 显示请求成功信息
	 * 
	 * @type {Boolean} false
	 * @property SHOW_SUCC_INFO
	 */
	SHOW_SUCC_INFO: false,
	/**
	 * 显示请求失败信息
	 * 
	 * @type {Boolean} false
	 * @property SHOW_ERROR_INFO
	 */
	SHOW_ERROR_INFO: false,
	/**
	 * GetJson是对Chief.ajax的封装,为创建 "GET" 请求方式返回 "JSON"(text) 数据类型
	 * @param {String}
	 *            url HTTP(GET)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] GET请求成功回调函数
	 */
	getJson: function (url, cmd, callback) {
		if (arguments.length !== 3)
			callback = cmd, cmd = '';
		dataType = this.dataType.TEXT;
		this.ajax(url, 'GET', cmd, dataType, callback);
	},
	/**
	 * PostJsonAsync是对Chief.ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型,
	 * 采用同步阻塞的方式调用ajax
	 * @param {String}
	 *            url HTTP(POST)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] POST请求成功回调函数
	 */
	postJsonSync: function (url, cmd, callback) {
		dataType = this.dataType.TEXT;
		this.ajax(url, 'POST', cmd, dataType, callback, true);
	},
	/**
	 * postJsonSyncToken是对Chief.ajax的封装,为创建 "POST"请求方式返回 "JSON"(text) 数据类型(添加token),
	 * 采用同步阻塞的方式请求token调用ajax，
	 * @param {String}
	 *            url HTTP(POST)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] POST请求成功回调函数
	 */
	postJsonSyncToken: function (url, cmd, callback) {
		dataType = this.dataType.TEXT;
		this.ajaxToken(url, 'POST', cmd, dataType, callback, true);
	},
	/**
	 * PostJson是对Chief.ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型
	 * @param {String}
	 *            url HTTP(POST)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] POST请求成功回调函数
	 */
	postJson: function (url, cmd, callback, flag) {
		dataType = this.dataType.TEXT;

		this.ajax(url, 'POST', cmd, dataType, callback, '', flag);
	},
	/**
	 * postJsonToken是对Chief.ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型(带token)
	 * @param {String}
	 *            url HTTP(POST)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] POST请求成功回调函数
	 */
	postJsonToken: function (url, cmd, callback, flag) {
		dataType = this.dataType.TEXT;
		this.ajaxToken(url, 'POST', cmd, dataType, callback, '', flag);
	},
	/**
	 * 以JSON方式提交数据到服务端
	 * 
	 */
	postJsonTo: function (url, type, jsondata, dataType, callback, sync) {
		Chief.layer.showLoading(); //加载动画
		async = sync ? false : true;
		var thiz = Chief.ajax;
		var cache = (dataType == "html") ? true : false;
		$.ajax({
			type: type,
			url:  Chief.wrapUrl(url),
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(jsondata),
			dataType: dataType,
			async: sync,
			success: function (data) {
				if (!data) {
					return;
				}
				if (dataType == "html") {
					callback(data, true);
					return;
				}
				try {
					data = eval('(' + data + ')');
				} catch (e) {
//					console.log("JSON Format Error:" + e.toString());
				}
				var isSuc = thiz.printReqInfo(data);
				if (callback && data) {
					callback(data || {}, isSuc);
				}
			},
			error: function () {
				var retErr = {};
				retErr['returnCode'] = "SCRM-404";
				retErr['returnMessage'] = "网络异常或超时，请稍候再试！";
				callback(retErr, false);
			},
			complete: function () {
				layer.closeAll('loading'); //关闭加载动画
			}
		});
	},
	/**
	 * loadHtml是对Ajax load的封装,为载入远程 HTML 文件代码并插入至 DOM 中
	 * @param {Object}
	 *            obj Dom对象
	 * @param {String}
	 *            url HTML 网页网址
	 * @param {Function}
	 *            callback [optional,default=undefined] 载入成功时回调函数
	 */
	loadHtml: function (obj, url, data, callback) {
		$(obj).load(url, data, function (response, status, xhr) {
			callback = callback ? callback : function () {};
			status == "success" ? callback(true) : callback(false);
		});
	},
	/**
	 * loadTemp是对handlebars 的封装,请求模版加载数据
	 * @param {Object}
	 *            obj Dom对象
	 * @param {Object}
	 *            temp 模版
	 * @param {Object}
	 *            data 数据
	 */
	loadTemp: function (obj, temp, data) {
		var template = Handlebars.compile(temp.html());
		$(obj).html(template(data));
	},
	/**
	 * appendTemp是对handlebars 的封装,请求模版加载数据,在原来的html尾部增加内容
	 * @param obj 选择器或jquery对象容器
	 * @param temp 模版jquery对象或html
	 * @param {Object} data 数据
	 */
	appendTemp: function (obj, temp, data) {
		var template = Handlebars.compile((temp instanceof jQuery) ? temp.html() : temp);
		obj = (obj instanceof jQuery) ? obj : $(obj);
		return obj.append(template(data));
	},
	/**
	 * GetHtml是对Chief.ajax的封装,为创建 "GET" 请求方式返回 "hmtl" 数据类型
	 * @param {String}
	 *            url HTTP(GET)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] GET请求成功回调函数
	 */
	getHtml: function (url, cmd, callback) {
		if (arguments.length !== 3)
			callback = cmd, cmd = '';
		dataType = this.dataType.HTML;
		this.ajax(url, 'GET', cmd, dataType, callback);
	},
	/**
	 * GetHtmlSync是对Chief.ajax的封装,为创建 "GET" 请求方式返回 "hmtl" 数据类型
	 * 采用同步阻塞的方式调用ajax
	 * @param {String}
	 *            url HTTP(GET)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] GET请求成功回调函数
	 */
	getHtmlSync: function (url, cmd, callback) {
		if (arguments.length !== 3)
			callback = cmd, cmd = '';
		dataType = this.dataType.HTML;
		this.ajax(url, 'GET', cmd, dataType, callback, true);
	},
	/**
	 * 基于jQuery ajax的封装，可配置化
	 * 
	 * @method ajax
	 * @param {String}
	 *            url HTTP(POST/GET)请求地址
	 * @param {String}
	 *            type POST/GET
	 * @param {Object}
	 *            cmd json参数命令和数据
	 * @param {String}
	 *            dataType 返回的数据类型
	 * @param {Function}
	 *            callback [optional,default=undefined] 请求成功回调函数,返回数据data和isSuc
	 */
	ajax: function (url, type, cmd, dataType, callback, sync, flag) {
		if(cmd != null){//在分页时判断是否含有start参数
			if(cmd.hasOwnProperty('pageNumber') && cmd.hasOwnProperty('limit') && !cmd.hasOwnProperty('start')){
				cmd.start = (cmd.pageNumber - 1)*cmd.limit;
			}
		};
		Chief.layer.showLoading(); //加载动画
		async = sync ? false : true;
		var thiz = Chief.ajax;
		var cache = (dataType == "html") ? true : false;
		$.ajax({
			url: Chief.wrapUrl(url),
			type: type,
			data: cmd,
			cache: cache,
			dataType: dataType,
			async: async,
			timeout: thiz.TIME_OUT,
			beforeSend: function (xhr) {
				xhr.overrideMimeType("text/plain; charset=utf-8");
			},
			success: function (data) {
				if (!data) {
					return;
				}
				if (dataType == "html") {
					callback(data, true);
					return;
				}
				try {
					data = eval('(' + data + ')');
					//					if (data.returnCode=='PAGEFRAME-9527') {
					//						alert("登录凭证过期，请重新登录");
					//						window.location.reload();
					//						return;
					//					}
				} catch (e) {
//					console.log("JSON Format Error:" + e.toString());
				}
				var isSuc = thiz.printReqInfo(data);
				if (callback && data) {
					callback(data || {}, isSuc);
				}
			},
			error: function () {
				var retErr = {};
				retErr['returnCode'] = "SCRM-404";
				retErr['returnMessage'] = "网络异常或超时，请稍候再试！";
				callback(retErr, false);
			},
			complete: function () {
				layer.closeAll('loading'); //关闭加载动画
			}
		});
	},
	/**
	 * 打开请求返回代码和信息
	 * 
	 * @method printRegInfo
	 * @param {Object}
	 *            data 请求返回JSON数据
	 * @return {Boolean} true-成功; false-失败
	 */
	printReqInfo: function (data) {
		if (!data)
			return false;
		var code = data.returnCode,
			msg = data.returnMessage,
			succ = this.reqCode.SUCC;
		if (code == succ) {
			if (this.SHOW_SUCC_INFO) {

				Chief.layer.tips(msg);
			}
		} else {

			if (this.SHOW_ERROR_INFO) {
				art.layer.tips(msg);
			}
		}
		return !!(code == succ);
	},
	/**
	 * JSON对象转换URL参数
	 * 
	 * @method printRegInfo
	 * @param {Object}
	 *            json 需要转换的json数据
	 * @return {String} url参数字符串
	 */
	jsonToUrl: function (json) {
		var temp = [];
		for (var key in json) {
			if (json.hasOwnProperty(key)) {
				var _key = json[key] + "";
				_key = _key.replace(/\+/g, "%2B");
				_key = _key.replace(/\&/g, "%26");
				temp.push(key + '=' + _key);
			}
		}
		return temp.join("&");
	},
	msg: {
		"suc": function (obj, text) {
			var _text = text || "数据提交成功！";
			$(obj).html(
				"<div class='msg-hint'>" + "<h3 title='" + _text +
				"'><i class='hint-icon hint-suc-s'></i>" + _text +
				"</h3>" + "</div>").show();
		},
		"war": function (obj, text) {
			var _text = text || "数据异常，请稍后尝试!";
			$(obj).html(
				"<div class='msg-hint'>" + "<h3 title='" + _text +
				"'><i class='hint-icon hint-war-s'></i>" + _text +
				"</h3>" + "</div>").show();
		},
		"err": function (obj, text) {
			var _text = text || "数据提交失败!";
			$(obj).html(
				"<div class='msg-hint'>" + "<h3 title='" + _text +
				"'><i class='hint-icon hint-err-s'></i>" + _text +
				"</h3>" + "</div>").show();
		},
		"load": function (obj, text) {
			var _text = text || "正在加载中，请稍候...";
			$(obj).html(
				"<div class='msg-hint'>" + "<h3 title='" + _text +
				"'><i class='hint-loader'></i>" + _text + "</h3>" +
				"</div>").show();
		},
		"inf": function (obj, text) {
			var _text = text || "数据提交中，请稍等...";
			$(obj).html(
				"<div class='msg-hint'>" + "<h3 title='" + _text +
				"'><i class='hint-icon hint-inf-s'></i>" + _text +
				"</h3>" + "</div>").show();
		},
		"errorInfo": function (obj, text) {
			var _text = text || "数据提交失败!";
			$(obj)
				.html(
					"<div class='ui-tiptext-container ui-tiptext-container-message'><p class='ui-tiptext ui-tiptext-message'>" +
					"<i class='ui-tiptext-icon icon-message' title='阻止'></i>" +
					_text + "</p>" + "</div>").show();
		}
	}
};

//弹窗
Chief.layer = {
	/**
	 * tips提示框
	 * closetimes:Number 自定义显示多久关闭
	 */
	tips: function (msg,closetimes) {
		closetimes = closetimes ? closetimes :2000;
		layer.msg(msg, {
			time: closetimes,
//			offset: 'b',
			skin: "toasts"
//			,closeBtn: 1
		});
	},
	tips2: function (msg,closetimes) {
		closetimes = closetimes ? closetimes : 5000;
		layer.msg(msg, {
			time: closetimes,
			offset: 'b',
			skin: "toasts"
		});
	},
	openArea: function (params) {
		var p = {
				type: 1,
				offset: '10px',
				id: params.id,
				fixed: true,
				shadeClose : false,    //点击空白处弹出框消失
				title: params.title,
				skin: 'edit-class',
				area: [params.width, params.height],
				content: params.content,
				btn: params.btn,
				yes: params.okCallback,
				btn2: params.cancelCallback,
				cancel: params.closeCallback //关闭对话框回调函数
		};
		var d = layer.open(p);
		if (params.minHeight) {
				$('#'+params.id).css('min-height', params.minHeight);
		}
		return d;
	},
	/**
	 * 弹窗显示页面元素
	 */
	openDiv: function (id, okCallback, cancelCallback, offset) {
		offset = offset ? offset : "10px";
		indexss=layer.open({
			type: 1,
			offset: offset,
			content: $("#" + id),
			area: '50%',
			title: false,
			closeBtn: 0,
			offset: '4.4rem',
			btn: ['确定', '关闭'],
			yes: okCallback,
			btn2: cancelCallback
		});
	},
	editDiv: function (tit, msg, okCallback, cancelCallback, widths, heights, offset) {
		widths = widths ? widths : '580px';
		heights = heights ? heights : 'auto';
        offset = offset ? offset : "10px";
        cancelCallback = cancelCallback?cancelCallback : function(){layer.close(indexss)};
		indexss=layer.open({
			type: 1, //Page层类型
			skin: 'edit-class',
			offset: offset,
			area: [widths, heights],
			title: tit,
			shadeClose: true,
			closeBtn: 1,
			btn: ['取消', '确认'],
			shade: 0.6 //遮罩透明度
				,
//			maxmin: true //允许全屏最小化
//				,
			anim: 1 //0-6的动画形式，-1不开启
				,
			content: msg,
			yes: cancelCallback,
			btn2: okCallback
		});
	},
	edit2Div: function (tit, msg, okCallback, cancelCallback, widths, heights, offset) {
		widths = widths ? widths : '580px';
		heights = heights ? heights : 'auto';
		offset = offset ? offset : "10px";
		indexss=layer.open({
			type: 1, //Page层类型
			skin: 'edit-class',
			offset: offset,
			area: [widths, heights],
			title: tit,
			btn: ['保存', '关闭'],
			shade: 0.6 //遮罩透明度
				,
			maxmin: true //允许全屏最小化
				,
			anim: 1 //0-6的动画形式，-1不开启
				,
			content: msg,
			yes: okCallback,
			btn2: cancelCallback
		});
	},
	emptyDiv: function (tit,msg, widths, heights, offset) {
		widths = widths ? widths : 'auto';
		heights = heights ? heights : 'auto';
		offset = offset ? offset : "10px";
		indexss = layer.open({
			type: 1 //Page层类型
				,
			skin: 'empty-class',
			offset: offset,
			area: [widths, heights],
            title: tit,
			closeBtn: 0,
			shadeClose: true,
			shade: 0.6 //遮罩透明度
				,
			maxmin: false //允许全屏最小化
				,
			anim: 1 //0-6的动画形式，-1不开启
				,
			content: msg
		});
	},
	newEmptyDiv: function (tit, msg, widths, heights, offset,endCallback) {
		var bwidth = parseInt($('body').width()*0.85);
		var bheight = parseInt($('body').height()*0.85);
		widths = widths ? widths : ""+bwidth+""+"px";
		heights = heights ? heights : ""+bheight+""+"px";
        var btop = ($('body').height() - parseInt(heights))*0.5
        offset = offset ? offset : ""+btop+""+"px";
		indexss=layer.open({
			type: 1 ,//Page层类型
			skin: 'empty-class',
			area: [widths, heights],
			offset: offset,
			scrollbar: false,
			title: tit,
			shadeClose: true,
			shade: 0.6 ,//遮罩透明度
			maxmin: false,//允许全屏最小化
			anim: 1,//0-6的动画形式，-1不开启
			content: msg,
            end: endCallback
		});
	},
	newEmptyDiv2: function (tit, msg, widths, heights, offset,endCallback) {
		var bwidth = parseInt($('body').width()*0.85);
		var bheight = parseInt($('body').height()*0.85);
		var btop = ($('body').height() - bheight)*0.5
		widths = widths ? widths : ""+bwidth+""+"px";
		heights = heights ? heights : ""+bheight+""+"px";
		offset = offset ? offset : ""+btop+""+"px";
		indexss=layer.open({
			type: 1 ,//Page层类型
			skin: 'empty-class',
			area: [widths, heights],
			offset: offset,
			scrollbar: false,
			title: tit,
			shadeClose: false,
			shade: 0.6 ,//遮罩透明度
			maxmin: false,//允许全屏最小化
			anim: 1,//0-6的动画形式，-1不开启
			content: msg,
			end: endCallback
		});
	},
	viewDiv: function (tit, msg,cancelCallback, widths, heights, offset) {
		widths = widths ? widths : '580px';
		heights = heights ? heights : 'auto';
		offset = offset ? offset : "10px";
		indexss=layer.open({
			type: 1 //Page层类型
				,
			skin: 'edit-class',
			offset: offset,
			area: [widths, heights],
			title: tit,
			btn: [ '确认'],
			shade: 0.6 //遮罩透明度
				,
//			maxmin: true //允许全屏最小化
//				,
			anim: 1 //0-6的动画形式，-1不开启
				,
			content: msg,
			btn2: cancelCallback
		});
	},
	newViewDiv: function (tit, msg,cancelCallback, widths, heights, offset) {
		widths = widths ? widths : '580px';
		heights = heights ? heights : 'auto';
		offset = offset ? offset : "10px";
		indexss=layer.open({
			type: 1 //Page层类型
				,
			skin: 'edit-class',
			offset: offset,
			area: [widths, heights],
			title: tit,
			btn: [ '关闭'],
			shade: 0.6, //遮罩透明度
			maxmin: false, //允许全屏最小化
			anim: 1, //0-6的动画形式，-1不开启
			content: msg,
			btn2: cancelCallback
		});
	},
	//	图片放大
	showImg : function (url,offset){
		offset = offset ? offset : "20px";
	    var img = "<img src='" + url + "' />";
	    indexss=layer.open({  
		    type: 1,  
		    offset: '4.4rem',
		    shade: 0.6,  
		    fixed : false,
		    offset: offset,
		    title: false, //不显示标题  
		    shadeClose: true,
		    skin: "seeimg",
		    area: ['90%', '90%'],  
//		    area: [$width + 'px', $height + 'px'],  
		    content: img, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响  
		    cancel: function () {  
		        //layer.msg('图片查看结束！', { time: 5000, icon: 6 });  
		    }  
		});  
	},
	close: function (index) {
		if (index == undefined) {
			layer.closeAll();
		} else {
			layer.close(index);
		}
	},
	/*loading*/
	showLoading: function () {
		//		var index = layer.load(1, {shade: [0.8, '#000']}); 
		var index = layer.load(0);
		return index;
	},
	//	shade
	showLoadingShade: function () {
		var index = layer.load(1, {shade: [0.5, '#000']}); 
//		var index = layer.load(0, {
//			shade: false
//		});
		return index;
	},
	/*通知*/
	confirm: function (msg, okCallback, cancelCallback) {
		layer.confirm(msg,{btn: ['取消', '确认'],offset: '200px',skin: 'confirm-class'}, cancelCallback, okCallback);
	},
	/*通知2-自定义标题*/
	confirm2: function (msg, tit, okCallback, cancelCallback) {
		layer.confirm(msg, {btn: ['取消', '确认'], title: tit, offset: '200px',skin: 'confirm-class'}, cancelCallback, okCallback);
	}

}
/*ESC关闭弹窗*/
document.onkeydown = function (event) {
    var e = event || window.event;
    if (e && e.keyCode == 27) { //回车键的键值为13
    	if($(".layui-layer")){
    		Chief.layer.close();
    	}
    }
};

//屏蔽回车键
document.onkeydown = function () {
    if (window.event && window.event.keyCode == 13) {
        window.event.returnValue = false;
    }
}

//全局属性，列表分页每页显示多少条记录
var global_limit = 10;
var pageNum = 1;
/*
 * 分页初始化方法
 * currentPage:当前页码(必须)
 * count:总数量(必须)
 * queryListMethod:查询列表数据回调函数，回调函数参数格式为(page, limit),page:点击的页码，limit：每页数量(必须)
 * pagin:页面分页插件div的id
 * totalNum:页面总数量html的id
 * currPage:页面当前页html的id
 */
function initPaginator(currentPage, count, queryListMethod, pagin, totalNum, currPage,limitNum) {

    if (typeof count == "undefined") {
        count = 0;
    }
    var totalNumStr = "共" + count + "条";
    if (typeof totalNum != "undefined") {
        $("#" + totalNum).html(totalNumStr);
    } else {
        $("#totalNum").html(totalNumStr);
    }
    var limit = limitNum||global_limit; //每页显示多少条记录
    var pagination;
    if (typeof pagin != "undefined") {
        pagination = $("#" + pagin);
    } else {
        pagination = $("#pagination");
    }
    var totalPages = Math.floor((count + limit - 1) / limit) == 0 ? 1 : Math.floor((count + limit - 1) / limit);
    var options = {
        bootstrapMajorVersion: 3, //分页版本
        currentPage: currentPage, //当前页码
        numberOfPages: 5, //显示多少页，默认显示 5 页
        totalPages: totalPages, //总页数
        itemTexts: function (type, page, current) {
            switch (type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "末页";
                case "page":
                    return page;
            }
        },
        tooltipTitles: function (type, page, current) {
            switch (type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "末页";
                case "page":
                    return page;
            }
        },
        shouldShowPage : function(type, page, current){
            switch (type) {
                case "first":
                    if(current == 1){
                        return false;
                    }else{
                        return true;
                    };
                case "prev":
                    if(current == 1){
                        return false;
                    }else{
                        return true;
                    };
                case "next":
                    if(current == page){
                        return false;
                    }else{
                        return true;
                    };
                case "last":
                    if(current == page){
                        return false;
                    }else{
                        return true;
                    };
                case "page":
                    return page;
            }
        },
        onPageClicked: function (event, originalEvent, type, page) {
            // var pageVal = currPage?$("#" + currPage).val():pageNum;
            // console.log(pageVal)
            // if(pageVal == 1 && (type=='prev'||type=='first')){
            //     Chief.layer.tips('已经是第一页了');
            //     return false
            // }
            // if(pageVal == totalPages && (type=='next')){
            //     Chief.layer.tips('已经是最后一页了');
            //     return false
            // }

            //放入页面中当前页码的隐藏域中
            if (typeof currPage != "undefined"  && currPage != '') {
                $("#" + currPage).val(page);
            }
            queryListMethod(page, limit);
        }
    }
    pagination.bootstrapPaginator(options);
}
// function getUrlParam(name) {
//     var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
//     var r = window.location.search.substr(1).match(reg);
//     if (r != null) return escape(r[2]); return null;
// }
//获取当前页面url的参数
/**
 * 获取url中的参数
 * @param name 参数名
 * @param url 指定的路径，默认为当前url
 */
function getUrlParam(name, url) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = url ? url.substr(url.indexOf('?') + 1).match(reg) : window.location.search.substr(1).match(reg);
	if (r != null) {
		// return unescape(r[2]);
		return decodeURIComponent(r[2]);
	}
	return null;
}
//动态引入js文件
function getJsFile(source) {
	var script = document.createElement("script");
	script.type = "text/javascript";
	script.src = source;
	document.body.appendChild(script);
}
Chief.browser = {
	    /**
	     * 获取URL地址栏参数值
	     * name 参数名
	     * url [optional,default=当前URL]URL地址
	     * @return {String} 参数值
	     */
	    getParameter: function(name, url) {
	        var paramStr = url || window.location.search;
	        paramStr = paramStr.split('?')[1];
	        if ((!paramStr) || paramStr.length == 0) {
	            return null;
	        }
	        var params = paramStr.split('&');
	        for (var i = 0; i < params.length; i++) {
	            var parts = params[i].split('=', 2);
	            if (parts[0] == name) {
	                if (parts.length < 2 || typeof(parts[1]) === "undefined" ||
	                    parts[1] == "undefined" || parts[1] == "null")
	                    return '';
	                return parts[1];
	            }
	        }
	        return null;
	    },
	    /**
	     * 判断是不是IE
	     * @returns true:是 fasle:否
	     */
	    isIE: function() {
	        return (!!window.ActiveXObject || "ActiveXObject" in window) ? true : false;
	    },
	    /**
	     * 判断是不是IE8
	     * @returns true:是 fasle:否
	     */
	    isIE8: function() {
	        return Chief.browser.isIE() && !-[1, ] && document.documentMode;
	        // 此方法有兼容性问题
	        /*if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/8./i)=="8."){
	            return true;
	        }else{
	            return false;
	        }*/
	    },
	    /**
	     * 判断是不是IE9
	     * @returns true:是 fasle:否
	     */
	    isIE9: function() {
	        return (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE9.0");
	    }
	};
/**
 * cookie 操作，设置，取出，删除
 *
 * @namespace Rose
 * @class string
 */
Chief.cookie = {
    /**
     * 显示当前对象名称路径
     * @method toString
     * @return {String} 'Rose.string'
     */
    toString: function() {
        return 'Rose.cookie';
    },
    /**
     * 设置一个cookie
     * @method set
     * @param {String} name cookie名称
     * @param {String} value cookie值
     * @param {String} path 所在路径
     * @param {Number} expires 存活时间，单位:小时
     * @param {String} domain 所在域名
     * @return {Boolean} 是否成功
     */
    setCookie: function(name, value, expires, path, domain) {
        var str = name + "=" + encodeURIComponent(value);
        if (expires != undefined && expires != null && expires != '') {
            if (expires == 0) {
                expires = 100 * 365 * 24 * 60;
            }
            var exp = new Date();
            exp.setTime(exp.getTime() + expires * 60 * 1000);
            str += "; expires=" + exp.toGMTString();
        }
        if (path) {
            str += "; path=" + path;
        } else {
            str += "; path=/";
        }
        if (domain) {
            str += "; domain=" + domain;
        }
        document.cookie = str;
    },
    /**
     * 获取指定名称的cookie值
     * @method get
     * @param {String} name cookie名称
     * @return {String} 获取到的cookie值
     */
    getCookie: function(name) {
        var v = document.cookie.match('(?:^|;)\\s*' + name + '=([^;]*)');
        return v ? decodeURIComponent(v[1]) : null;
    },
    /**
     * 删除指定cookie,复写为过期
     * @method remove
     * @param {String} name cookie名称
     * @param {String} path 所在路径
     * @param {String} domain 所在域
     */
    remove: function(name, path, domain) {
        document.cookie = name + "=" +
            ((path) ? "; path=" + path : "") +
            ((domain) ? "; domain=" + domain : "") +
            "; expires=Thu, 01-Jan-70 00:00:01 GMT";
    }
};

//将Chief对象注册为符合AMD规范的模块，可使用requireJS模块化加载
if (typeof define === "function" && define.amd) {
    define('Chief', [], function() {
        return Chief;
    });
}

//去掉字符串头尾空格
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};

//过滤字符串参数首尾空格
function trimParam(param) {
    if (typeof param === "string") {
        var paramArr = param.split('&');
        param = {};
        for (var i = 0; i < paramArr.length; i++) {
            var item = paramArr[i].split('=');
            if (item.length > 1) {
                param[item[0]] = item[1];
            }
        }
    }

    $.each(param, function(i, v) {
        if (typeof v === 'string') {
            param[i] = $.trim(v);
        }
    });
    return param;
}

// 过滤字符串所有空格
String.prototype.wipeOffAllBlank = function() {
    return this.replace(/\s/g, "");
};

function showDom(domTag) {
    $(domTag).toggle();
}

function outCallShowDom(domTag) {
    var left_height = $(".left").height();
    var J_outcall_height = left_height - 120;

    if ($(domTag).hasClass("fn-hide")) {
        $(domTag).removeClass("fn-hide");
        $("#J_contact_left").css("height", "120px");
        $(".J_outcall_contact_list").css("height", J_outcall_height + "px");
    } else {
        $(domTag).addClass("fn-hide");
        $("#J_contact_left").css("height", "");
        $(".J_outcall_contact_list").css("height", left_height - 40 + "px");
    }
}

function setTab(name, cursel, n) {
    for (i = 1; i <= n; i++) {
        var menu = document.getElementById(name + i);
        var con = document.getElementById(name + "_" + i);
        menu.className = i == cursel ? "tabCurrent" : "tabNormal";
        con.style.display = i == cursel ? "block" : "none";
    }
}

var curDate = new Date();

function endDateMax() {
    var startDate = $("#sDate").val();
    var y = startDate.substring(0, 4);
    var maxDate = y + '-12-31';

    var y1 = JSON.stringify(curDate).substring(1, 5);
    var y2 = startDate.substring(0, 4);
    if (y2 < y1) {
        return maxDate;
    } else {
        return curDate;
    }
}

function compareVal() {
    var startDate = $("#sDate").val();
    var endDate = $("#eDate").val();
    var y1 = startDate.substring(0, 4);
    var y2 = endDate.substring(0, 4);
    var cy = JSON.stringify(curDate).substring(1, 5);
    var ytempDate = y1 + '-12-31';
    var ytempDateAll = ytempDate + ' 23:59:59';
    if (y1 !== y2) {
        $("#eDate").val(ytempDate);
        $("#CREATE_DATE").val(ytempDateAll);
    }
    if (y1 == cy) {
        $("#eDate").val(curDate.formatDD("yyyy-MM-DD"));
        $("#CREATE_DATE").val(curDate.formatDD("yyyy-MM-DD hh:mm:ss"));
    }
}
// 当文本框失去焦点时，自动设置默认值
function setInputDefaultDay(obj, startInput, endInput) {
    if (obj.value == "") {
        $("#sDate").val($("#sDate").attr("defaultValue"));
        $("#eDate").val($("#eDate").attr("defaultValue"));
        $("#" + startInput).val($("#" + startInput).attr("defaultValue"));
        $("#" + endInput).val($("#" + endInput).attr("defaultValue"));
        return false;
    }
}
// 获取当前月的第一天和当天
function getFirstAndLastMonthDay(startInput, endInput, dFormat) {
    var curDate = new Date();
    var fdate = curDate.formatDD(dFormat);
    var ldate = curDate.formatDD(dFormat);
    $("#sDate").val(fdate).attr("defaultValue", fdate);
    $("#eDate").val(ldate).attr("defaultValue", ldate);
    if (!flag) {
        fdate = curDate.formatDD("yyyy-MM-01 00:00:00");
        ldate = curDate.formatDD("yyyy-MM-DD 23:59:59");
    }
    $("#" + startInput).val(fdate).attr("defaultValue", fdate);
    $("#" + endInput).val(ldate).attr("defaultValue", ldate);
    return fdate + "," + ldate;
}

// 获取当前年的第一天和当前天
function getFirstAndLastYearDay(startInput, endInput, flag) {
    var curDate = new Date();
    var fdate = curDate.formatDD("yyyy-01-01");
    var ldate = curDate.formatDD("yyyy-MM-DD");
    $("#sDate").val(fdate).attr("defaultValue", fdate);
    $("#eDate").val(ldate).attr("defaultValue", ldate);
    if (!flag) {
        fdate = curDate.formatDD("yyyy-01-01 00:00:00");
        ldate = curDate.formatDD("yyyy-MM-DD 23:59:59");
    }
    $("#" + startInput).val(fdate).attr("defaultValue", fdate);
    $("#" + endInput).val(ldate).attr("defaultValue", ldate);
    return fdate + "," + ldate;
}

// 获取当前年的第一天和当前天
function setNoticeDate(startInput, endInput, flag) {
    var curDate = new Date();
    var fdate = curDate.formatDD("yyyy-MM-DD");
    var ldate = curDate.formatDD("2020-12-31");
    if (!flag) {
        fdate = curDate.formatDD("yyyy-MM-DD 00:00:00");
        ldate = curDate.formatDD("2020-12-31 23:59:59");
    }
    $("#" + startInput).val(fdate).attr("defaultValue", fdate);
    $("#" + endInput).val(ldate).attr("defaultValue", ldate);
    return fdate + "," + ldate;
}
// data.formatDD( "yyyy-MM-DD hh:mm:ss");
Date.prototype.formatDD = function(formatStr) {
    var date = this;
    var str = formatStr;
    str = str.replace(/yyyy|YYYY/, date.getFullYear());
    str = str.replace(/yy|YY/, (date.getYear() % 100) > 9 ? (date.getYear() % 100).toString() : "0" + (date.getYear() % 100));
    str = str.replace(/MM/, date.getMonth() > 8 ? (date.getMonth() + 1).toString() : "0" + (date.getMonth() + 1));
    str = str.replace(/M/g, date.getMonth() + 1);
    str = str.replace(/dd|DD/, date.getDate() > 9 ? date.getDate().toString() : "0" + date.getDate());
    str = str.replace(/d|D/g, date.getDate());
    str = str.replace(/hh|HH/, date.getHours() > 9 ? date.getHours().toString() : "0" + date.getHours());
    str = str.replace(/h|H/g, date.getHours());
    str = str.replace(/mm/, date.getMinutes() > 9 ? date.getMinutes().toString() : "0" + date.getMinutes());
    str = str.replace(/m/g, date.getMinutes());
    str = str.replace(/ss|SS/, date.getSeconds() > 9 ? date.getSeconds().toString() : "0" + date.getSeconds());
    str = str.replace(/s|S/g, date.getSeconds());
    return str;
};
Date.prototype.addDays = function(days) {
    this.setDate(this.getDate() + days);
    return this;
};
Date.prototype.Format = function(fmt) { // author: meizz
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds()
            // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) :
                (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

// 系统错误或异常提示语优化函数
function friendlyTips() {
    var list = [
        "正在排队，请稍候……",
        "系统繁忙，休息下……",
        "诗和远方，系统想去看看，请稍候……",
        "放下行囊，系统正在接入工作，请稍候……"
    ];

    var variable = Math.floor(Math.random() * 4);
    return list[variable];
}

//字符串转换成日期
function strToDate(dateStr, formatStr) {
    //YYYY是年
    //MM是“01”月的格式
    //DD是“01”日的格式
    //HH是小时、MN是分、SS是秒
    var digit = 0; //退位计数器
    var date = new Date();
    var newFormat = formatStr.toUpperCase();
    var year = getNumFromStr(dateStr, newFormat, 'YYYY');
    var month = getNumFromStr(dateStr, newFormat, 'MM');
    var da = getNumFromStr(dateStr, newFormat, 'DD');
    var hour = getNumFromStr(dateStr, newFormat, 'HH');
    var mn = getNumFromStr(dateStr, newFormat, 'MN');
    var ss = getNumFromStr(dateStr, newFormat, 'SS');
    if (year > 0)
        date.setFullYear(year);
    if (month > 0)
        date.setMonth(month - 1);
    if (da > 0)
        date.setDate(da);
    if (hour > 0)
        date.setHours(hour);
    if (mn > 0)
        date.setMinutes(mn);
    if (ss > 0)
        date.setSeconds(ss);
    return date;

    function getNumFromStr(target, frm, s) {
        //target是目标字符串，frm是模板字符串，s是匹配字符
        var len = s.length;
        var index = frm.indexOf(s);
        if (index < 0) return index;
        var reStr = target.substr(index - digit, len);
        var result = parseInt(reStr, 10); //(s=='SM'||s=='SD')&&
        if (result < 10 && (reStr.charAt(0) != 0)) {
            digit++;
        }
        return result;
    }
}

function compareDate(date1, date2) {
    date1 = date1.getTime();
    date2 = date2.getTime();

    if (date1 > date2) {
        return false;
    }
    return true;
}

$(function() {
    //展开顶部个人中心
//     if ($('#J_personInfo').length) {
//         $('#J_personInfo').toggle(
//             function() {
//                 $('#J_personInfo').find('i').removeClass("iconUp").addClass("iconDown");
//                 $('#J_dropDown').slideDown('fast');
//             },
//             function() {
//                 $('#J_personInfo').find('i').removeClass("iconDown").addClass("iconUp");
//                 $('#J_dropDown').hide();
//             }
//         );
//     }
    $('body').on("blur",'input',function(){
    	if($(this).attr('type') != 'file'){
    		this.value = this.value.replace(/(^\s*)|(\s*$)/g, '');
    	}
    })
});

/*
    让indexOf()方法兼容IE8
 */
(function() {
    if (!Array.prototype.indexOf) {
        Array.prototype.indexOf = function(elt, from) {
            var len = this.length >>> 0;
            var from = Number(arguments[1]) || 0;
            from = (from < 0) ? Math.ceil(from) : Math.floor(from);
            if (from < 0) {
                from += len;
            }

            for (; from < len; from++) {
                if (from in this && this[from] === elt) {
                    return from;
                }
            }
            return -1;
        };
    }
})();

//解决ie下console.log()报错问题
window.console = window.console || (function() {
    var c = {};
    c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile = c.clear = c.exception = c.trace = c.assert = function() {};
    return c;
})();

//单页面时控制右侧mainRight的div出现滚动条，使用iframe时mainRight为overflow:hidden，设置iframe高度，并使用iframe的自带滚动条
function initIframe() {
    var iframe = $("#J_busi_iframe:visible,#I_term:visible,#I_market:visible,#I_marketDetail:visible");
    try {
        //取右侧界面框架的高度:浏览器高度-顶部菜单高度
        var rightBox_height = $(window).height() - $(".header").height();
        $(iframe).height(rightBox_height);
    } catch (ex) {}
}

//获取当前页面url的参数
/**
 * 获取url中的参数
 * @param name 参数名
 * @param url 指定的路径，默认为当前url
 */
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = url ? url.substr(url.indexOf('?') + 1).match(reg) : window.location.search.substr(1).match(reg);
    if (r != null) {
        // return unescape(r[2]);
        return decodeURIComponent(r[2]);
    }
    return null;
}

//获取当前页面url加密的参数然后进行解密
function getDecodeQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return null;
}

//时间+分钟算出结果时间
function getTimeAddMinutes(currentTime, minutes) {
    currentTime = new Date(Date.parse(currentTime.replace(/-/g, "/")));
    currentTime.setMinutes(currentTime.getMinutes() + parseInt(minutes));
    return currentTime.Format("yyyy-MM-dd hh:mm:ss");
}
//时间+分钟算出结果时间
function getTimeAddSecond(currentTime, second) {
    currentTime = new Date(Date.parse(currentTime.replace(/-/g, "/")));
    currentTime.setSeconds(currentTime.getSeconds() + parseInt(second));
    return currentTime.Format("yyyy-MM-dd hh:mm:ss");
}

function right(mainStr, lngLen) {
    if (mainStr.length - lngLen >= 0 && mainStr.length >= 0 &&
        mainStr.length - lngLen <= mainStr.length) {
        return mainStr.substring(mainStr.length - lngLen, mainStr.length);
    } else {
        return null;
    }
}

//截取时间 2010-10-10 00:00:00.0 截取为 2010-10-10 00:00:00
function interceptDateTime(time) {
    if (time != null) {
        var t = time;
        return t.substring(0, 19);
    } else {
        return null;
    }
}

function showMessage(content, width) {
    if (width) {
        var params = {
            title: "提示信息",
            content: content,
            width: width,
            minHeight: "50px"
        };
    } else {
        var params = {
            title: "提示信息",
            content: content,
            width: "200px",
            minHeight: "50px"
        };
    }

    Chief.layer.openArea(params);
}

function showTopMessage(content) {
    showMessage(content);
}

function showTopMessage2(content) {
    var params = {
        id: null,
        // top: window.parent,
        title: "提示信息",
        content: content,
        width: "200px",
        minheight: "50px"
    };
    Chief.layer.openArea(params);
}

function showTopMessage3(content) {
    var params = {
        id: null,
        // top: window.parent,
        title: "提示信息",
        content: content,
        width: "250px"
    };
    Chief.layer.openArea(params);
}

function showCustomMessage(content, t, w, h) {
    var params = {
        id: null,
        // top: window.parent,
        title: t,
        content: content,
        width: w,
        height: h
    };
    Chief.layer.openArea(params);
}

// 把秒换算成时间
function getTimeBySecond(second) {
    var date3 = second * 1000; // 时间差的毫秒数
    // 计算出相差天数
    var days = Math.floor(date3 / (24 * 3600 * 1000));
    // 计算出小时数
    var leave1 = date3 % (24 * 3600 * 1000); // 计算天数后剩余的毫秒数
    var hours = Math.floor(leave1 / (3600 * 1000));
    // 计算相差分钟数
    var leave2 = leave1 % (3600 * 1000); // 计算小时数后剩余的毫秒数
    var minutes = Math.floor(leave2 / (60 * 1000));
    // 计算相差秒数
    var leave3 = leave2 % (60 * 1000); // 计算分钟数后剩余的毫秒数
    var seconds = Math.round(leave3 / 1000);

    if (days == 0) {
        return right("00" + hours, 2) + ":" + right("00" + minutes, 2) + ":" +
            right("00" + seconds, 2);
    } else {
        return days + "天 " + right("00" + hours, 2) + ":" +
            right("00" + minutes, 2) + ":" + right("00" + seconds, 2);
    }
}

//判断当前时间是否在  时间段之内
function timeRange(beginTime, endTime) {
    var strb = beginTime.split(":");
    if (strb.length != 2) {
        return false;
    }

    var stre = endTime.split(":");
    if (stre.length != 2) {
        return false;
    }

    var nowDate = new Date();
    var nowTime = nowDate.getHours() + ":" + nowDate.getMinutes();
    var strn = nowTime.split(":");
    if (stre.length != 2) {
        return false;
    }
    var b = new Date();
    var e = new Date();
    var n = new Date();

    b.setHours(strb[0]);
    b.setMinutes(strb[1]);
    e.setHours(stre[0]);
    e.setMinutes(stre[1]);
    n.setHours(strn[0]);
    n.setMinutes(strn[1]);

    if (n.getTime() - b.getTime() > 0 && n.getTime() - e.getTime() < 0) {
        return true;
    } else {
        return false;
    }
};

// 把秒转换成hh:MM:ss
function getTimeSpanDisplay(seconds) {
    if (!isNaN(seconds)) {
        var hours = Math.floor(seconds / (60 * 60));
        var _seconds = seconds % (60 * 60);
        var minutes = Math.floor(_seconds / 60);
        _seconds = _seconds % 60;

        var minutesText = "00" + minutes.toString();
        var secondsText = "00" + _seconds.toString();

        minutesText = minutesText.substr(minutesText.length - 2, 2);
        secondsText = secondsText.substr(secondsText.length - 2, 2);

        var arr = [];
        arr.push(hours.toString());
        arr.push(minutesText);
        arr.push(secondsText);

        return arr.join(":");
    }

    return "";
}


/**
 * 判断开始时间和结束时间之前的时间差有没有超过特定天数
 * @param days  天数，默认为7天
 * @param startSelector 开始时间输入框选择器，默认为"#startTime"
 * @param endSelector 结束时间输入框选择器，默认为"#endTime"
 * @param dayFlag 输入的时间是否精确到天：true-精确到天, false-精确到秒
 * @returns {Boolean} true-开始时间和结束时间差在days内,false-开始时间和结束时间差大于days
 */
function justifyTimeLength(days, startSelector, endSelector, dayFlag) {
    if (!days) {
        days = 7;
    }
    if (!startSelector) {
        startSelector = "#startTime";
    }
    if (!endSelector) {
        endSelector = "#endTime";
    }

    var sDate = Chief.date.str2Date($(startSelector).val()),
        eDate = Chief.date.str2Date($(endSelector).val());

    if (!sDate || !eDate) {
        return false;
    } else {
        if (dayFlag) {
            if ((eDate - sDate) > (60 * 60 * 24 * (days - 1) * 1000)) {
                return false;
            } else {
                return true;
            }
        } else {
            if ((eDate - sDate) > (60 * 60 * 24 * days * 1000)) {
                return false;
            } else {
                return true;
            }
        }
    }
}

/**
 * 判断开始时间和结束时间是否都输入完整
 * @param first  开始时间输入框选择器或结束时间输入框选择器
 * @param second 结束时间输入框选择器或开始时间输入框选择器
 * @returns {Boolean} true-开始和结束时间都输入了,false-开始和结束时间没有输入完整
 */

function justifyTimeInputOr(first, second) {
    var first = $(first).val();
    var second = $(second).val();
    if (first != "" && second != "") {
        return true;
    } else {
        return false;
    }
}

/**
 * 判断两个时间是否符合条件
 * @param startTime  开始时间  yyyy-MM-dd HH:mm:ss格式
 * @param endTime 结束时间 yyyy-MM-dd HH:mm:ss格式
 */
function justifyTime(startTime, endTime) {
    var firstTime = {
            "year": returnDate(startTime).getFullYear(),
            "month": returnDate(startTime).getMonth(),
            "date": returnDate(startTime).getDate()
        },
        secondTime = {
            "year": returnDate(endTime).getFullYear(),
            "month": returnDate(endTime).getMonth(),
            "date": returnDate(endTime).getDate()
        };
    if (firstTime.year == secondTime.year && firstTime.month == secondTime.month && firstTime.date == secondTime.date) {
        return true;
    } else {
        return false;
    }
}

/**
 * inTime 传入一个"2017-04-20 10:59:02"这种类型的时间
 * 返回 一个实例化时间
 * 兼容ie8
 */
function returnDate(inTime) {
    var s = inTime;
    var ps = s.split(" ");
    var pd = ps[0].split("-");
    var pt = ps.length > 1 ? ps[1].split(":") : [0, 0, 0];
    return new Date(pd[0], pd[1] - 1, pd[2], pt[0], pt[1], pt[2]);
}

$.fn.bindSortEvents = function(sortInfo, callback) {
    var thArray = $(this).find("[data-column]");
    for (var i = 0; i < thArray.length; ++i) {
        var th = thArray[i];
        $(th).click(function() {
            var columnName = $(this).attr("data-column");
            if (sortInfo.sort == columnName) {
                sortInfo.sortType = (sortInfo.sortType == "ASC") ? "DESC" : "ASC";
            } else {
                sortInfo.sort = columnName;
                sortInfo.sortType = "ASC";
            }
            callback();
        });
    }
};

// $.fn.pager = function(itemCount, pageSize, currentPage, callback) {
// 
//     try {
//         itemCount = parseInt(itemCount);
//     } catch (e) {
//         itemCount = 0;
//     }
// 
//     this.pagination(itemCount, {
//         'items_per_page': pageSize,
//         'num_display_entries': 4,
//         //'num_edge_entries': 0,
//         'prev_text': "上一页",
//         'next_text': "下一页",
//         'current_page': currentPage - 1,
//         'num_edge_entries': 1,
//         'ellipse_text': '...',
//         'call_callback_at_once': false, // 控制分页控件第一次不触发callback.
//         'callback': function(page) {
//             callback(page + 1);
//         }
//     });
//     if (itemCount >= 1) {
//         this.find('.noData-p').remove();
//         if (this.find('.total-item').length <= 0) {
//             this.append($("<span class='total-item'></span>"));
//         }
//         this.removeClass("fn-center").addClass("fn-right").find('.total-item').html("共" + itemCount.toString() + "条数据");
//     } else {
//         this.find('.total-item').remove();
//         var noData;
//         if ($(window.parent.document).find(this).length <= 0) { //表格在iframe中
//             noData = "<p class='mt-20 noData-p pagerFont'><img class='noDta' src='../../../image/common/nothing2.png' alt=''/> 暂时没有数据...</p>";
//         } else {
//             noData = "<p class='mt-20 noData-p pagerFont'><img class='noDta' src='../../image/common/nothing2.png' alt=''/>  暂时没有数据...</p>";
//         }
//         if (this.parents(".action-other").hasClass("action-other-show")) {
//             this.parents(".action-other").removeClass("action-other-show");
//             this.html(noData).removeClass("fn-right").addClass("fn-center");
//         } else {
//             this.html(noData).removeClass("fn-right").addClass("fn-center");
//         }
// 
//     }
// 
// 
// };

// $.fn.setSortIcon = function(sort, sortType) {
//     var thArray = $(this).find("[data-column]");
// 
//     for (var i = 0; i < thArray.length; ++i) {
//         var th = thArray[i];
// 
//         var a = $(th).find("a")[0];
// 
//         var columnName = $(th).attr("data-column");
// 
//         if (sort == columnName) {
//             if (sortType == "ASC") {
//                 $(a).attr("class", "sort_icon_asc");
//             } else {
//                 $(a).attr("class", "sort_icon_desc");
//             }
//         } else {
//             $(a).attr("class", "sort_icon");
//         }
//     }
// };
// 
// $.fn.resetSortIcon = function(sortInfo) {
//     $(this).setSortIcon(sortInfo.sort, sortInfo.sortType);
// };

/**
 * tab页切换方法
 * obj: 对象数组，对象包含三个属性：
 * srcSelector-触发事件的元素id
 * tabInit-tab页初始化事件
 * tarSelector-要显示的tab内容选择器
 */
$.fn.bindTab = function(obj, doc) {
    doc = doc ? doc : document;
    $(this).on("click", "li", function() {
        $(this).closest("ul").find("li").removeClass("ui-tab-item-current")
            .end().end().addClass("ui-tab-item-current");
        var id = $(this).attr("id"),
            len = obj.length,
            showFlag = $(this).hasClass("J_showed"),
            item;

        for (var i = 0; i < len; i++) {
            item = obj[i];
            if (item.srcSelector === id) {
                if (!showFlag) {
                    $(this).addClass("J_showed");
                    item.tabInit();
                } else {
                    if (item.alwaysInit) {
                        item.alwaysInit();
                    }
                }
                $(doc).find(item.tarSelector).removeClass("fn-hide").siblings(".J_tab_content").addClass("fn-hide");
                break;
            }
        }
    });
};

$.fn.tableSelect = function(ckbSelector, ckbAllSelector, dataArr) {
    var _table = $(this).closest('table'),
        _ckbAll = _table.find(ckbAllSelector);
    // 点击table行事件
    $(this).on('click', 'tr', function(e) {
        var _ckb = $(this).find(ckbSelector);
        if (_ckb.length === 0) {
            return false;
        }
        var id = _ckb.attr('id'),
            flag = true;
        if (_ckb.is(':checked')) {
            flag = false;
            // 取消全选按钮的选中状态
            if (_ckbAll.length > 0 && _ckbAll.is(':checked')) {
                _ckbAll.prop('checked', false);
            }
            $(this).removeClass('selectedTr');
        } else {
            // 判断全选按钮是否要选中
            if (_ckbAll.length > 0) {
                if ((_table.find('input[type=checkbox]').length - 1) == _table.find('input[type=checkbox]:checked').length) {
                    _ckbAll.prop('checked', true);
                }
            }
            $(this).addClass('selectedTr');
        }
        if (e.target.type != 'checkbox' && e.target.type != 'radio') {
            _ckb.prop('checked', flag);
        }

        if (dataArr) {
            setSelectData(id, flag, dataArr);
        }
    });
    if (ckbAllSelector) {
        $(this).on('click', ckbAllSelector, function() { /* 全选按钮的事件 */
            var selectAllFlag = this.checked;
            _table.find(ckbSelector).each(function() {
                if (selectAllFlag && !this.checked) {
                    setSelectData($(this).attr('id'), true, dataArr);
                }
                if (!selectAllFlag && this.checked) {
                    setSelectData($(this).attr('id'), false, dataArr);
                }
            });
        });
    }

    function setSelectData(id, flag, saveArr) {
        var exist = false;
        if (flag) {
            for (var i = 0; i < saveArr.length; i++) {
                if (saveArr[i] == id) {
                    exist = true;
                }
            }
            if (!exist) {
                saveArr.push(id);
            }
        } else {
            var position = -1;
            for (var j = 0; j < saveArr.length; j++) {
                if (saveArr[j] == id) {
                    position = j;
                    break;
                }
            }
            saveArr.splice(position, 1);
        }
    }
};

/*
 * Get Absolute Context Path
 */
Chief.globalPath = (function getContextPath() {
    var localObj = window.location;
	var contextPath = localObj.pathname.split("/")[1];
	var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath+"/api";
    return basePath;
}());

Chief.wrapUrl = function(url) {
    return Chief.globalPath + url;
};

function webTrackEvent(ele, category, action, opt_label, opt_value) {
    try {
        Log.trackEvent([category, action, opt_label, opt_value], ele);
    } catch (e) {}
}
/*
*底部按钮从左向右为0、1、2...
* emptyDiv不适用（未定义按钮）
*/
function getLayerButton(type) {
    return $(window.top.document).find(".layui-layer-btn a.layui-layer-btn" + type)[0];
}
//待修改
function addEventForCheckbox(checkboxSelect, arrayName, doc) {
    var tempDoc = doc ? doc : window.parent.document;
    $(tempDoc).on("click", checkboxSelect, function() {
        var trainId = "#" + $(this).attr("id");
        var selectALL = $(this).closest('tbody').prev('thead').find('input[type=checkbox]'); //全选复选框
        if (this.checked) {
            $(this).prop("checked", true);
            var checkboxList = $(this).closest('tbody').find('input[type=checkbox]:enabled').length; // 复选框数量
            var checkedNum = $(this).closest('tbody').find('input[type=checkbox]:checked').length; // 选中的复选框数量
            // 全选复选框处于未选中状态，当下面的复选框都被选中时，全选复选框应当被选中
            if (selectALL && !selectALL.is(':checked') && checkboxList == checkedNum && checkboxList > 0) {
                selectALL.prop("checked", true);
            }
            $(this).parents("tr").first().find("td").addClass("setBackGround");
            setTrain(trainId, true, arrayName, tempDoc);
        } else {
            $(this).prop("checked", false);
            if (selectALL && selectALL.is(':checked')) {
                selectALL.prop("checked", false);
            }
            $(this).parents("tr").first().find("td").removeClass("setBackGround");
            setTrain(trainId, false, arrayName, tempDoc);
        }
    });
}
//待修改
function addEventForRadio(checkboxSelect, arrayName, doc) {
    var tempDoc = doc ? doc : window.parent.document;
    $(tempDoc).off("click", checkboxSelect).on("click", checkboxSelect, function() {
        var trainId = "#" + $(this).attr("id");
        if ($(this).hasClass("j-choose")) {
            $(this).removeClass("j-choose");
            $(this).prop("checked", false);
            $(this).parents("tr").first().find("td").removeClass("setBackGround");
            $(this).parents("tr").eq(0).siblings().find("input").removeClass("j-choose");
            radioSet(trainId, false, arrayName, tempDoc);
        } else {
            $(this).addClass("j-choose");
            $(this).prop("checked", true);
            $(this).parents("tr").first().find("td").addClass("setBackGround");
            $(this).parents("tr").first().siblings().find("td").removeClass("setBackGround");
            $(this).parents("tr").eq(0).siblings().find("input").removeClass("j-choose");
            radioSet(trainId, true, arrayName, tempDoc);
        }
    });
}
/* 有两个数组的复选框选中事件
 *  @param arrayName1 存储对象id的数组名称
 *  @param arrayName2 存储对象name的数组名称
 *  @param doc 当前上下文
 * */
function addEventForCheckbox2(checkboxSelect, arrayName1, arrayName2, doc) {
    var tempDoc = doc ? doc : window.parent.document;
    $(tempDoc).on("click", checkboxSelect, function() {
        var trainId = "#" + $(this).attr("id");
        var selectALL = $(this).closest('tbody').prev('thead').find('input[type=checkbox]'); //全选复选框
        if (this.checked) {
            $(this).prop("checked", true);
            var checkboxList = $(this).closest('tbody').find('input[type=checkbox]:enabled').length; // 复选框数量
            var checkedNum = $(this).closest('tbody').find('input[type=checkbox]:checked').length; // 选中的复选框数量
            // 全选复选框处于未选中状态，当下面的复选框都被选中时，全选复选框应当被选中
            if (selectALL && !selectALL.is(':checked') && checkboxList == checkedNum && checkboxList > 0) {
                selectALL.prop("checked", true);
            }
            $(this).parents("tr").first().find("td").addClass("setBackGround");

            setTrain2(trainId, true, arrayName2, arrayName1, tempDoc);
            setTrain(trainId, true, arrayName1, tempDoc);
        } else {
            $(this).prop("checked", false);
            if (selectALL && selectALL.is(':checked')) {
                selectALL.prop("checked", false);
            }
            $(this).parents("tr").first().find("td").removeClass("setBackGround");

            setTrain2(trainId, false, arrayName2, arrayName1, tempDoc);
            setTrain(trainId, false, arrayName1, tempDoc);
        }
    });
}


// 阻止冒泡事件
function stopDefault(e) {
    if (e && e.preventDefault) {
        e.preventDefault();
    } else {
        window.event.returnValue = false;
    }
    return false;
}

/*为tr添加点解改变背景演示，同时设置选中状态
 * selectTboday 最外层tbody下tr的选择器名称
 * InputName 复选框的选择器名称
 * arrayName 存储选中对象的数组名字
 * @param doc 当前上下文
 * flagRadio  true的时候代表给单选框
 * */
function addEventForTr(selectTboday, InputName, arrayName, doc, flagRadio) {
    var tempDoc = doc ? doc : window.parent.document;
    $(tempDoc).off("click", selectTboday).on("click", selectTboday, function(e) {
        if ($(this).find(InputName).length != 1) {
            return false;
        }
        if (!(e.target.type === "checkbox" || e.target.type === "radio")) {
            var choose = $(this).find(InputName);
            var trainId = "#" + choose.attr("id");
            var selectALL = $(this).closest('tbody').prev('thead').find('input[type=checkbox]'); //全选复选框
            if (choose.is(':checked')) {
                $(this).find("td").removeClass("setBackGround");
                choose.prop("checked", false);
                // 全选复选框处于选中状态，下面的复选框有一个不被选中时，全选复选框应失去全选状态
                if (selectALL && selectALL.is(':checked')) {
                    selectALL.prop("checked", false);
                }
                if (flagRadio) {
                    choose.removeClass("j-choose");
                    choose.parents("tr").eq(0).siblings().find("input").removeClass("j-choose");
                    radioSet(trainId, false, arrayName, tempDoc);
                } else {
                    setTrain(trainId, false, arrayName, tempDoc);
                }

            } else {
                choose.prop("checked", true);
                var checkboxList = $(this).closest('tbody').find('input[type=checkbox]').length; // 复选框数量
                var checkedNum = $(this).closest('tbody').find('input[type=checkbox]:checked').length; // 选中的复选框数量
                // 全选复选框处于未选中状态，当下面的复选框都被选中时，全选复选框应当被选中
                if (selectALL && !selectALL.is(':checked') && checkboxList == checkedNum) {
                    selectALL.prop("checked", true);
                }
                if (flagRadio) {
                    choose.addClass("j-choose");
                    choose.parents("tr").eq(0).siblings().find("input").removeClass("j-choose");
                    radioSet(trainId, true, arrayName, tempDoc);
                    $(this).find("td").addClass("setBackGround");
                    $(this).siblings().find("td").removeClass("setBackGround");
                } else {
                    setTrain(trainId, true, arrayName, tempDoc);
                    $(this).find("td").addClass("setBackGround");
                }
            }
        }
    });
}
//待修改
/*清除事件累计的行选中 write by yy*/
function addEventForTrS(selectTboday, InputName, arrayName, doc) {
    var tempDoc = doc ? doc : window.parent.document;
    $(tempDoc).off("click", selectTboday).on("click", selectTboday, function(e) {
        if ($(this).find(InputName).length != 1) {
            return false;
        }
        if (!(e.target.type === "checkbox" || e.target.type === "radio")) {
            var choose = $(this).find(InputName);
            var trainId = "#" + choose.attr("id");
            var selectALL = $(this).closest('tbody').prev('thead').find('input[type=checkbox]'); //全选复选框
            if (!choose.attr("disabled")) {
                if (choose.is(':checked')) {
                    $(this).find("td").removeClass("setBackGround");
                    choose.prop("checked", false);
                    // 全选复选框处于选中状态，下面的复选框有一个不被选中时，全选复选框应失去全选状态
                    if (selectALL && selectALL.is(':checked')) {
                        selectALL.prop("checked", false);
                    }
                    setTrain(trainId, false, arrayName, tempDoc);
                } else {
                    choose.prop("checked", true);
                    var checkboxList = $(this).closest('tbody').find('input[type=checkbox]:enabled').length; // 复选框数量
                    var checkedNum = $(this).closest('tbody').find('input[type=checkbox]:checked').length; // 选中的复选框数量
                    // 全选复选框处于未选中状态，当下面的复选框都被选中时，全选复选框应当被选中
                    if (selectALL && !selectALL.is(':checked') && checkboxList == checkedNum && checkboxList > 0) {
                        selectALL.prop("checked", true);
                    }
                    setTrain(trainId, true, arrayName, tempDoc);
                    $(this).find("td").addClass("setBackGround");
                }
            }

        }
    });
}

/*为tr添加点解改变背景演示，同时设置选中状态
 * selectTboday 最外层tbody下tr的选择器名称
 * InputName 复选框的选择器名称
 * arrayName1 存储选中对象id的数组名字
 * arrayName2 存储选中对象name的数组名字
 * @param doc 当前上下文
 * */
function addEventForTr2(selectTboday, InputName, arrayName1, arrayName2, doc) {
    var tempDoc = doc ? doc : window.parent.document;
    $(tempDoc).on("click", selectTboday, function(e) {
        if ($(this).find(InputName).length != 1) {
            return false;
        }
        if (!(e.target.type === "checkbox" || e.target.type === "radio")) {
            var choose = $(this).find(InputName);
            var trainId = "#" + choose.attr("id");
            var selectALL = $(this).closest('tbody').prev('thead').find('input[type=checkbox]'); //全选复选框
            if (choose.is(':checked')) {
                $(this).find("td").removeClass("setBackGround");
                choose.prop("checked", false);
                // 全选复选框处于选中状态，下面的复选框有一个不被选中时，全选复选框应失去全选状态
                if (selectALL && selectALL.is(':checked')) {
                    selectALL.prop("checked", false);
                }
                setTrain2(trainId, false, arrayName2, arrayName1, tempDoc);
                setTrain(trainId, false, arrayName1, tempDoc);

            } else {
                choose.prop("checked", true);
                var checkboxList = $(this).closest('tbody').find('input[type=checkbox]').length; // 复选框数量
                var checkedNum = $(this).closest('tbody').find('input[type=checkbox]:checked').length; // 选中的复选框数量
                // 全选复选框处于未选中状态，当下面的复选框都被选中时，全选复选框应当被选中
                if (selectALL && !selectALL.is(':checked') && checkboxList == checkedNum) {
                    selectALL.prop("checked", true);
                }
                setTrain2(trainId, true, arrayName2, arrayName1, tempDoc);
                setTrain(trainId, true, arrayName1, tempDoc);
                $(this).find("td").addClass("setBackGround");
            }
        }
    });
}
/*清除事件累计的行选中 write by yy*/ //营销活动类，重庆在线公司
function addEventForTrS2(selectTboday, InputName, arrayName, doc, arrayName2) {
    var tempDoc = doc ? doc : window.parent.document;
    $(tempDoc).off("click", selectTboday).on("click", selectTboday, function(e) {
        if ($(this).find(InputName).length != 1) {
            return false;
        }
        if (!(e.target.type === "checkbox" || e.target.type === "radio")) {
            var choose = $(this).find(InputName);
            var trainId = "#" + choose.attr("id");
            var selectALL = $(this).closest('tbody').prev('thead').find('input[type=checkbox]'); //全选复选框
            if (!choose.attr("disabled")) {
                if (choose.is(':checked')) {
                    $(this).find("td").removeClass("setBackGround");
                    choose.prop("checked", false);
                    // 全选复选框处于选中状态，下面的复选框有一个不被选中时，全选复选框应失去全选状态
                    if (selectALL && selectALL.is(':checked')) {
                        selectALL.prop("checked", false);
                    }
                    setTrainS3(trainId, false, arrayName, tempDoc, arrayName2);
                } else {
                    choose.prop("checked", true);
                    var checkboxList = $(this).closest('tbody').find('input[type=checkbox]:enabled').length; // 复选框数量
                    var checkedNum = $(this).closest('tbody').find('input[type=checkbox]:checked').length; // 选中的复选框数量
                    // 全选复选框处于未选中状态，当下面的复选框都被选中时，全选复选框应当被选中
                    if (selectALL && !selectALL.is(':checked') && checkboxList == checkedNum && checkboxList > 0) {
                        selectALL.prop("checked", true);
                    }
                    setTrainS3(trainId, true, arrayName, tempDoc, arrayName2);
                    $(this).find("td").addClass("setBackGround");
                }
            }

        }
    });
}

/*为全选添加点击事件
 * allSelectTrain 为全选按钮的选择器名称
 * checkBoxSelect 为多选按钮选择器名称
 * arrayName 存储选中对象的数组名字
 * */
function allSelect(allSelectTrain, checkBoxSelect, arrayName, doc) {
    var tempDoc = doc ? doc : window.parent.document;
    $(tempDoc).off("click", allSelectTrain).on("click", allSelectTrain, function() {
        var trainCheckFlag = this.checked;
        $(tempDoc).find(checkBoxSelect).each(function() {
            var trainId = "#" + $(this).attr("id");
            if (trainCheckFlag) {
                $(this).parents("tr").first().find("td").addClass("setBackGround");
                if (!this.checked) {
                    setTrain(trainId, true, arrayName, tempDoc);
                    this.checked = true;
                }
            } else {
                $(this).parents("tr").first().find("td").removeClass("setBackGround");
                if (this.checked) {
                    this.checked = false;
                    setTrain(trainId, false, arrayName, tempDoc);
                }
            }
        });
    });
}

/*为全选添加点击事件
 * allSelectTrain 为全选按钮的选择器名称
 * checkBoxSelect 为多选按钮选择器名称
 * arrayName1 存储选中对象id的数组名字
 * arrayName2 存储选中对象name的数组名字
 * */
function allSelect2(allSelectTrain, checkBoxSelect, arrayName1, arrayName2, doc) {
    var tempDoc = doc ? doc : window.parent.document;
    $(tempDoc).on("click", allSelectTrain, function() {
        var trainCheckFlag = this.checked;
        $(tempDoc).find(checkBoxSelect).each(function() {
            var trainId = "#" + $(this).attr("id");
            if (trainCheckFlag) {
                $(this).parents("tr").first().find("td").addClass("setBackGround");
                if (!this.checked) {
                    setTrain2(trainId, true, arrayName2, arrayName1, tempDoc);
                    setTrain(trainId, true, arrayName1, tempDoc);
                    this.checked = true;
                }
            } else {
                $(this).parents("tr").first().find("td").removeClass("setBackGround");
                if (this.checked) {
                    this.checked = false;
                    setTrain2(trainId, false, arrayName2, arrayName1, tempDoc);
                    setTrain(trainId, false, arrayName1, tempDoc);
                }
            }
        });
    });
}

/*
 * 修改数组的内容(删除或增加)
 * selector 选择器
 * flag true:添加，false:删除
 * saveArray:存储选中对象的数组名字
 * */
function setTrain(selector, flag, saveArray, doc) {
    doc = doc ? doc : window.top.document;
    var trainlId = $(doc).find(selector).val(),
        repeat = false;
    if (flag) {
        for (var j = 0; j < saveArray.length; j++) {
            if (saveArray[j] == trainlId) {
                repeat = true;
            }
        }
        if (!repeat) {
            saveArray.push(trainlId);
        }
    } else {
        var position = -1;
        for (var i = 0; i < saveArray.length; i++) {
            if (saveArray[i] == trainlId) {
                position = i;
                break;
            }
        }
        saveArray.splice(position, 1);
    }
}
/*
 * 修改数组的内容(删除或增加)
 * selector 选择器
 * flag true:添加，false:删除
 * saveArray:存储选中对象的数组名字
 * */
function radioSet(selector, flag, saveArray, doc) {
    doc = doc ? doc : window.top.document;
    var trainlId = $(doc).find(selector).val(),
        repeat = false;
    if (flag) {
        saveArray.splice(0, saveArray.length);
        saveArray.push(trainlId);
    } else {
        saveArray.splice(0, saveArray.length);
    }
}

/*
 * 修改数组的内容(删除或增加)
 * selector 选择器
 * flag true:添加，false:删除
 * saveArray:存储选中对象name的数组名字
 * saveIdArray:存储选中对象id的数组名字
 * */
function setTrain2(selector, flag, saveArray, saveIdArray, doc) {
    doc = doc ? doc : window.top.document;
    var id = $(doc).find(selector).val(),
        trainlName = $(doc).find(selector).data("name"),
        repeat = false;
    if (flag) {
        for (var j = 0; j < saveIdArray.length; j++) {
            if (saveIdArray[j] == id) {
                repeat = true;
            }
        }
        if (!repeat) {
            saveArray.push(trainlName);
        }
    } else {
        var position = -1;
        for (var i = 0; i < saveIdArray.length; i++) {
            if (saveIdArray[i] == id) {
                position = i;
                break;
            }
        }
        saveArray.splice(position, 1);
    }
}

/**
 * 生成sn
 * @param id
 */
function generateSn(id) {
    if (!id) {
        return "";
    }
    if (!USER_ID) {
        Chief.ajax.postJsonSync("/seat/touch/seatInfo", {}, function(result) {

            // 用户是否登陆
            if (result.returnCode == "0" && result.object != null) {
                USER_ID = result.object;
            }
            if (result.bean) {
                var _txt = $('#hid_agt_chl_id');
                if (_txt.length > 0) {
                    _txt.val(result.bean.channelId);
                }
            }
        });
    }
    return hex_md5(USER_ID + id);
}

/**
 * 显示等待效果
 */
function showWaitImg() {
    // $(PAR_DOC).find("div.data-loading").show();
	Chief.layer.showLoading();
}

/**
 * 隐藏等待效果
 */
function hideWaitImg() {
    // $(PAR_DOC).find("div.data-loading").hide();
	Chief.layer.close("loading");
}

//拼接普通查询条件及匹配类型
function packParam(val, matcher) {
    var ret = '';
    if (val && '' != $.trim(val)) {
        ret += $.trim(val) + '|';
        if (matcher && '' != matcher) {
            ret += matcher;
        } else {
            ret += 'LIKE';
        }
    }
    return ret;
}

// 拼接时间查询条件及匹配类型
function packTimeParam(val1, val2, fmt) {
    if (!fmt || '' == fmt) {
        fmt = 'yyyyMMddhhmmss';
    }
    var d1 = new Date();
    var d2 = new Date();
    if (val1 && val2 && '' != val1 && '' != val2) {
        d1.setTime(Chief.date.str2Date(val1));
        d2.setTime(Chief.date.str2Date(val2));
        return d1.Format(fmt) + ';' + d2.Format(fmt) + '|BET';
    } else {
        if (val1 && '' != val1) {
            d1.setTime(Chief.date.str2Date(val1));
            return d1.Format(fmt) + '|GE';
        }
        if (val2 && '' != val2) {
            d2.setTime(Chief.date.str2Date(val2));
            return d2.Format(fmt) + '|LE';
        }
    }
}

// 获得字符长度
function strlen(str) {
    if (!str) {
        return;
    }
    var len = 0;
    for (var i = 0; i < str.length; i++) {
        var c = str.charCodeAt(i);
        //单字节加1
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
            len++;
        } else {
            len += 2;
        }
    }
    return len;
}

// 复选框隔页选中事件函数
// justifyChooseBool 判断是点击查询按钮查询还是点击翻页查询
// array 记录选中的数据id数组
// $all 全选复选框元素
// result 查询列表返回的数据
// idName 列表中普通复选框的类名
// top 选择列表是否在ifram中
// add by yuanyan
function alternateSelection(justifyChooseBool, array, $all, result, idName, top) {
    if (justifyChooseBool) {
        $all.prop("checked", false);
        array.splice(0, array.length);
    } else {
        $all.prop("checked", false);
        var matchedLen, tempId;
        if (top) { // 弹框内列表
            matchedLen = 0;
            for (var i = 0; i < result.object.data.length; i++) {
                tempId = result.object.data[i].grpId;
                for (var j = 0; j < array.length; j++) {
                    if (array[j] == tempId) {
                        matchedLen++;
                        $(top).find("#" + idName + "-" + tempId).prop("checked", true).parent("td").parent("tr").find("td").addClass("setBackGround");
                        break;
                    }
                }
            }
            if (result.object.data.length && result.object.data.length == matchedLen) {
                $all.prop("checked", true);
            }
        } else { // 公司群组列表
            matchedLen = 0;
            for (var l = 0; l < result.object.data.length; l++) {
                tempId = result.object.data[l].id;
                for (var k = 0; k < array.length; k++) {
                    if (array[k] == tempId) {
                        matchedLen++;
                        $("#" + idName + "-" + tempId).prop("checked", true).parent("td").parent("tr").find("td").addClass("setBackGround");
                        break;
                    }
                }
            }

            if (result.object.data.length && $("#container-channelInfo-info").find(".checkboxCor:enabled").length == matchedLen) {
                $all.prop("checked", true);
            }
        }
    }
}

$.fn.extend({
    ajaxBtn: function() {
        var $callback = typeof arguments[0] == "function" ? arguments[0] : undefined,
            $type = arguments[1],
            $url = Chief.wrapUrl(arguments[2]),
            $data = arguments[3],
            $_this = $(this);
        $(this).hide();
        $.ajax({
            type: $type,
            url: $url,
            data: $data,
            success: function(result) {
                if (result.returnCode == 0) {
                    if (result.bean) {
                        $callback ? $callback.apply($_this, [result.bean]) : "";
                    } else {
                        $callback ? $callback.apply($_this) : "";
                        $_this.show();
                    }
                } else {
                    $_this.show();
                }
            }
        });
    }
});
// 0秒录音设置数据
function controllDate() {
    if (arguments.length != 0) {
        if (arguments[0]["msServer"] == "ms") {
            $(this).show();
        }
        $(this).parent().attr("data-msServer", arguments[0]["msServer"]);
        $(this).parent().attr("data-file", arguments[0]["rcd_file_save_path"]);
        $(this).parent().attr("data-url", arguments[0]["rcd_file_nm"]);
        $(this).parents("tr").eq(0).find(".againSetTalkTime").html(arguments[0]["cnvst_tmlen_sec_cnt"]);
    } else {
        $(this).show();
    }
}

//数据导出的相关方法
Chief.exportChief = {
    url: '',
    postDatas: {},
    time: 60,
    timer: null,
    init: function(url, params, otherOutPut) {
        var ele = window.parent.document.getElementById('J_export_valid_cont');
        if (ele) {
            ele.remove();
        }
        this.url = url;
        this.postDatas = params;
        this.validPopWin(otherOutPut);
    },
    bindEvent: function(url) {
        $(TOP_DOC).find('#J_export_valid_form').off();
        $(TOP_DOC).find('#J_export_valid_form .msg-code-btn').click(function() {
            webTrackEvent(this, "adm-export-sendMsg", "click");
            if (!$(this).hasClass('J_disabled')) {
                var imgCode = $(PAR_DOC).find('#img_code_inp').val();
                if (imgCode.length === 4) {
                    $(this).addClass('J_disabled').html('发送中...');
                    Chief.exportChief.exportSendMsg(imgCode);
                } else {
                    $(PAR_DOC).find('#img_code_inp-error').html('请正确输入图形验证码');
                }
            }
        });
        $(TOP_DOC).find('#J_export_valid_form #img_code_inp').blur(function() {
            var tempVal = $(this).val(),
                _err = $(PAR_DOC).find('#img_code_inp-error');
            if (tempVal.length === 4) {
                _err.html('');
            } else {
                _err.html('请输入4位图形验证码');
            }
        });
        $(TOP_DOC).find('#J_export_valid_form .code-img').click(function() {
            webTrackEvent(this, "adm-export-codeImg", "refresh");
            Chief.exportChief.refreshCodeImg();
        });
        $(TOP_DOC).find('#J_export_valid_form #msg_code_inp').blur(function() {
            var tempVal = $(this).val(),
                _err = $(PAR_DOC).find('#msg_code_inp-error');
            if (tempVal.length === 6) {
                _err.html('');
            } else {
                _err.html('请输入6位短信验证码');
            }
        });
    },
    getModule: function() {
        var url = this.url;
        var bIdx = url.indexOf('!'),
            eIdx = url.indexOf('?');
        if (bIdx > -1 && eIdx > -1) {
            return url.substring(bIdx + 1, eIdx);
        } else {
            return '';
        }
    },
    validPopWin: function(otherOutPut) {
        var strHtml = "<div class='pop-code-cont' id='J_export_valid_cont'><form class='code-form' id='J_export_valid_form'>" +
            "<ul>" +
            "<li>请输入验证码</li>" +
            "<li class='relative'>" +
            "<input type='text' id='img_code_inp' name='img_code_inp' class='inputText' placeholder='请输入图形验证码' maxlength='4'>" +
            "<img class='code-img'>" +
            "</li>" +
            "<li class='error-msg'><span class='error' id='img_code_inp-error'></span></li>" +
            "<li class='relative'>" +
            "<input type='text' id='msg_code_inp' name='msg_code_inp' class='inputText' placeholder='请输入短信验证码' maxlength='6'>" +
            "<a class='msg-code-btn'>获取验证码</a>" +
            "</li>" +
            "<li class='error-msg'><span class='error' id='msg_code_inp-error'></span></li>" +
            "</ul>" +
            "</form></div>";
//待修改
        var params = {
            // id: 'p-export-code',
            title: '请确定验证码',
            content: strHtml,
            // modal: true,
						btn:['确定','取消'],
            // okVal: '确定',
            okCallback: function() {
                webTrackEvent(this, "adm-export", "adm-export-confirm");
                var _form = $(TOP_DOC).find('#J_export_valid_form');
                var imgCode = _form.find('#img_code_inp').val(),
                    msgCode = _form.find('#msg_code_inp').val();
                var validFlag = true;
                if (imgCode.length !== 4) {
                    _form.find('#img_code_inp-error').html('请输入4位图形验证码');
                    validFlag = false;
                } else {
                    _form.find('#img_code_inp-error').html('');
                }
                if (msgCode.length !== 6) {
                    _form.find('#msg_code_inp-error').html('请输入6位短信验证码');
                    validFlag = false;
                } else {
                    _form.find('#msg_code_inp-error').html('');
                }
                if (!validFlag) {
                    return false;
                }

                Chief.exportChief.exportData(otherOutPut);
            },
            // cancelVal: '取消',
            cancelCallback: function() {
                webTrackEvent(this, "adm-export", "adm-export-cancel");
            },
            closeCallback: function() {
                Chief.exportChief.clearTimer(Chief.exportChief.timer);
            }
        };
        Chief.layer.openArea(params);
        this.bindEvent();
        this.resetPopWinData();
    },
    resetPopWinData: function() {
        Chief.exportChief.time = 60;
        this.refreshCodeImg();
        Chief.exportChief.clearTimer(Chief.exportChief.timer);
    },
    // 发送验证码倒计时
    msgSendTimer: function() {
        var _btn = $(PAR_DOC).find('#J_export_valid_form .msg-code-btn');
        if (Chief.exportChief.time == 0) {
            _btn.removeClass('J_disabled').html('获取验证码');
            Chief.exportChief.clearTimer(Chief.exportChief.timer);
            Chief.exportChief.time = 60;
        } else {
            _btn.html(Chief.exportChief.time-- + '秒后重新发送');
            Chief.exportChief.timer = setTimeout(function() {
                Chief.exportChief.msgSendTimer();
            }, 1000);
        }
    },
    clearTimer: function(timer) {
        if (timer) {
            clearInterval(timer);
        }
    },
    // 数据导出
    exportData: function(otherOutPut) {
        showWaitImg();
        this.postDatas.module = this.getModule();
        this.postDatas.codeImage = $(PAR_DOC).find('#img_code_inp').val();
        this.postDatas.verificationCode = $(PAR_DOC).find('#msg_code_inp').val();
        Chief.ajax.postJsonSync(this.url, this.postDatas, function(result) {
            hideWaitImg();
            if (result.returnCode != "0") {
                var msg = result.returnMessage;
                showMessage(msg && msg.length > 75 ? msg.substr(0, 75) + '...' : msg);
                return;
            }
            if (otherOutPut) {
                if (result.bean) {
                    Chief.fileDownLoad(Chief.wrapUrl(Chief.exportChief.url) + '&passCode=' + result.bean.passCode + '&' + jQuery.param(Chief.exportChief.postDatas));
                } else {
                    showMessage("验证失败，请重新验证");
                }
            } else {
                Chief.reportExport(result);
            }
        });
    }
};

//重写js的toFixed方法 解决负数不兼容问题,不能保留两位小数问题
Number.prototype.toFixed = function(s) {
    var that = this,
        changenum, index;
    // 负数
    if (this < 0) {
        that = -that;
    }
    changenum = (parseInt(that * Math.pow(10, s) + 0.5) / Math.pow(10, s)).toString();
    index = changenum.indexOf(".");
    if (index < 0 && s > 0) {
        changenum = changenum + ".";
        for (var i = 0; i < s; i++) {
            changenum = changenum + "0";
        }
    } else {
        index = changenum.length - index;
        for (var i = 0; i < (s - index) + 1; i++) {
            changenum = changenum + "0";
        }
    }
    if (this < 0) {
        if (changenum == 0) {
            return 0;
        } else {
            return -changenum;
        }

    } else {
        return changenum;
    }
};

var basePath = getBasePath();

function getBasePath(){
    var localObj = window.location;
	var contextPath = localObj.pathname.split("/")[1];
	var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
    // return basePath ;
	return "";
 }
function trimInput(id){
    $(id).on("blur",function(){
        this.value = this.value.replace(/(^\s*)|(\s*$)/g, '');
    });
}
/**
 * 将传入数据转换为字符串,并清除字符串中非数字与.的字符按数字格式补全字符
 * 保留兩位小數
 */
function getFloatStr(num){
    num += '';
    num = num.replace(/[^0-9|\.]/g, ''); //清除字符串中的非数字非.字符
    if(/^0+/) //清除字符串开头的0
        num = num.replace(/^0+/, '');
    if(!/\./.test(num)) //为整数字符串在末尾添加.00
        num += '.00';
    if(/^\./.test(num)) //字符以.开头时,在开头添加0
        num = '0' + num;
    num += '00';        //在字符串末尾补零
    num = num.match(/\d+\.\d{2}/)[0];
    return num;
};

/*checkbox全选  */
function checkAll(sid,pid){
	var that = $(pid).find('tr'),
		checkboxLen,	/*选中checkbox的长度*/
		thatLength;		/*tr的长度*/
		/* 全选，全不选*/
	$(sid).on('change',function(){
		if($(this).prop('checked')){
			$.each($(pid).find("tr input[type = 'checkbox']"), function(index,ele) {
				$(ele).prop('checked',true).change();
			});
		}else{
			$.each($(pid).find("tr input[type = 'checkbox']"), function(index,ele) {
				$(ele).prop('checked',false).change();
			});
		}
	});
	/* 当列表中复选框全部选中时，判断第一个复选框是否选中 */
	$(pid).on('change',"input[type = 'checkbox']",function(){
		firstBox()
	});
	firstBox();
	function firstBox(m){
		checkboxLen = $(pid).find("tr input[type = 'checkbox']:checked").length;/* 列表中复选框选中行数 */
		thatLength = $(pid).find('tr').length;/* 列表中tr行数 */
		if(checkboxLen < thatLength){
			$(sid).prop('checked',false);
		}else{
			$(sid).prop('checked',true);
			if(checkboxLen == 0 && thatLength == 0){
				$(sid).prop('checked',false);
			}
		}
	}
}
/*数组去重 */
function uniq(array){
    var temp = []; //一个新的临时数组
    for(var i = 0; i < array.length; i++){
        if(temp.indexOf(array[i]) == -1){
            temp.push(array[i]);
        }
    }
    return temp;
};
/*任务添加执行公司分页时保存已选择公司 */
function gSelect(pid,g_selectId){
	var checkboxLen = $(pid).find("tr input[type = 'checkbox']");
	if(g_selectId.length !=0){
		for(var j=0;j<g_selectId.length;j++){
			for(var k=0;k<checkboxLen.length;k++){
				if(g_selectId[j] == checkboxLen[k].id){
					$(checkboxLen[k]).prop('checked',true);
				}
			}
		};
	};
	$(pid).on('change',"input[type = 'checkbox']",function(){
		if($(this).prop('checked') == true){
			g_selectId.push($(this).prop('id'));
		}else{
			for(var i=0;i<g_selectId.length;i++){
				if($(this).prop('id') == g_selectId[i]){
					g_selectId.splice(i,1);
				}
			}
		}
	})
};
//只要在弹框里的关闭按钮加个closess class名，点击就能关闭弹框
$('body').on('click', '.closess', function(){
	if (typeof indexss == "undefined") {
		layer.closeAll();
	} else {
		layer.close(indexss);
	}
	layer.close(indexss);
})