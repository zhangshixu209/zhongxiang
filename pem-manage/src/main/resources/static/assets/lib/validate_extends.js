// 验证提示信息
jQuery.extend(jQuery.validator.messages, {
    required: "必填字段",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: jQuery.validator.format("最多输入 {0} 个字"),
    minlength: jQuery.validator.format("最少输入 {0} 个字"),
    rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
    range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: jQuery.validator.format("请输入一个最大为{0} 的值"),
    min: jQuery.validator.format("请输入一个最小为{0} 的值"),
});

// 密码验证
jQuery.validator.addMethod("checkPsd", function(value, element) {
    var regPatt1 = /^[\u4E00-\u9FA5\uF900-\uFA2D]$/;
    var regPatt2 = /^((?=.*\d)(?=.*[^\d])|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{6,20}$/;
    var regPatt3 = /^\S*$/;
    return this.optional(element) || (!regPatt1.test(value) && regPatt2.test(value)&& regPatt3.test(value));
}, "密码为6-16位，且包含数字、字母、特殊字符两种或以上组成,且不能包含空格");


$.validator.defaults.ignore = "";
// 验证必须大于0的正数
$.validator.addMethod("ispositiveNumber", function(value, element) {
    var tel = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
    return this.optional(element) || ((tel.test(value))&&value != 0);
}, "请输入正数");
// 邮编验证
$.validator.addMethod("isZipCode", function(value, element) {
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的邮政编码");

// 中文姓名验证
$.validator.addMethod("chinaName", function(value, element, param) {
    var tel = /^([\u4e00-\u9fa5]{2,4})$/;
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的姓名");
//手机号码验证
$.validator.addMethod("mobile", function(value, element) {
	return this.optional( element ) || /^(13[0-9]|14[5-9]|15[012356789]|166|17[0-8]|18[0-9]|19[8-9])[0-9]{8}$/.test(value);
//	 return this.optional( element ) || /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/.test(value);
// return this.optional( element ) || /^(86\+)?0?((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\d{8}$/.test(value);
//   return this.optional(element) || /^\d{11}$/.test(value);
}, '手机号码格式不正确');
// 手机号或固话验证
$.validator.addMethod("phone", function(value, element) {
    // return this.optional( element ) || /^1\d{10}$/.test(value) || /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/.test(value);
    return this.optional(element) || /^\d{11}$/.test(value) || /^[\d|\-]{7,15}$/.test(value);
}, '请输入正确的电话号码');

// 验证结束日期大于开始日期
jQuery.validator.methods.compareDate = function(value, element, param) {
    var startDate = jQuery(param).val();
//  var startArr = startDate.split("-");
//	var endArr = value.split("-");
//  var date1 = new Date(Date.parse(startArr[1] + "/" + startArr[2] + "/" + startArr[0]));
//  var date2 = new Date(Date.parse(endArr[1] + "/" + endArr[2] + "/" + endArr[0]));
  	var date1 = new Date(Date.parse(startDate.replace("-", "/")));
	var date2 = new Date(Date.parse(value.replace("-", "/")));
    //var dt = new Date();
    return date1 < date2  ;
};

// 验证开始日期小于结束日期
jQuery.validator.methods.comparedDate = function(value, element, param) {
    // 触发结束日期的验证
    if ($(param).val() != "") {
        $(param).valid();
    }
    return true;
};

// 验证是否有中文字符,和部分中文状态的特殊字符
$.validator.addMethod("isChinaText", function(value, element) {
    var exp = /[\u4e00-\u9fa5]|[\（\）\《\》\——\；\，\。\“\”\<\>\！：]/;
    return this.optional(element) || !exp.test(value);
}, '输入格式有误');

// 在父窗口验证两次密码输入是否相同
$.validator.addMethod("isequle", function(value, element, param) {

    return $(window.parent.document).find(param).eq(0).val() == value;
}, '请输入相同的密码');

//验证两次输入值是否不相同
$.validator.addMethod("notEqualTo", function(value, element,param) {
	return value != $(param).val();
}, '两次输入的值不能相同!');

// 验证正整数
$.validator.addMethod("positiveInteger", function(value, element) {
    var tempInt = parseInt(value);
    return tempInt > 0 && (tempInt + "") == value;
}, '请输入正整数');

// 验证至多两位小数
$.validator.addMethod("numDecimal2", function(value, element, param) {
    // 不允许输入0
    //var reg = /^(0*[1-9]\d*(\.?|\.0{0,2})|\d*\.[1-9]\d?|\d*\.\d?[1-9])$/;
    var reg = /^\d+(\.?|\.\d{1,2})$/;
    return this.optional(element) || reg.test(value);
}, "请输入正整数或者至多两位小数");
//}, "请输入正的最多两位小数");
// ip地址效验
$.validator.addMethod("ipAddrValidation", function(value, element) {
    var reg = /^((25[0-5]|2[0-4]\d|[01]?\d\d?)($|(?!\.$)\.)){4}$/;
    return this.optional(element) || reg.test(value);
}, "请输正确的ip地址");
// 验证是否包含非法字符
$.validator.addMethod("isNormalText", function(value, element) {
    var exp = /^[^`·~!@#$￥%^……& *(（)）_\-|\{\}｛｝0-9+=——<《>》?？\\/、,，.。:：;；'‘’"“”\[【\]】]*$/;
    return this.optional(element) || exp.test(value);
}, '请勿输入特殊字符,数字或空格');
// 判断是否包含中英文特殊字符，除英文"-_"字符外
jQuery.validator.addMethod("isContainsSpecialChar", function(value, element) {  
     var reg = RegExp(/[(\ )(\`)(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\+)(\=)(\|)(\{)(\})(\')(\:)(\;)(\')(',)(\[)(\])(\.)(\<)(\>)(\/)(\?)(\~)(\！)(\@)(\#)(\￥)(\%)(\…)(\&)(\*)(\（)(\）)(\—)(\+)(\|)(\{)(\})(\【)(\】)(\‘)(\；)(\：)(\”)(\“)(\’)(\。)(\，)(\、)(\？)]+/);   
     return this.optional(element) || !reg.test(value);       
}, "含有中英文特殊字符");  
// 验证是否只有字母和数字
$.validator.addMethod("isNumAndChar", function(value, element) {
    return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
}, '请输入字母和数字');
// 验证身份证号码
$.validator.addMethod("IdVerificationDate", function(value, element) {
    value = value.toUpperCase();

    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(value))) {
        return false;
    }

    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
    //下面分别分析出生日期和校验位 
    var len, re;
    len = value.length;
    if (len == 15) {
        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
        var arrSplit = value.match(re);

        //检查生日日期是否正确 
        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {
            return false;
        } else {
            //将15位身份证转成18位 
            //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
            var nTemp = 0,
                i;
            value = value.substr(0, 6) + '19' + value.substr(6, value.length - 6);
            for (i = 0; i < 17; i++) {
                nTemp += value.substr(i, 1) * arrInt[i];
            }
            value += arrCh[nTemp % 11];
            return true;
        }
    }
    if (len == 18) {
        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
        var arrSplit = value.match(re);

        //检查生日日期是否正确 
        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {
            return false;
        } else {
            //检验18位身份证的校验码是否正确。 
            //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
            var valnum;
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
            var nTemp = 0,
                i;
            for (i = 0; i < 17; i++) {
                nTemp += value.substr(i, 1) * arrInt[i];
            }
            valnum = arrCh[nTemp % 11];
            if (valnum != value.substr(17, 1)) {
                return false;
            }
            return true;
        }
    }
    return false;
}, '身份证号码错误');

// 验证金额， 最多8位数字或者 6位数字+2位小数
$.validator.addMethod("checkPosiNumSixTwo", function(value, element) {
    var regUrl = /^\d{0,6}(\.\d{1,2})?$/;
    //var regUrl = /^(\d{1,6}|\d{1,6}\.\d{1,2})$/;
    return this.optional(element) || regUrl.test(value);
}, '请输入0-999999.99范围内,最多两位小数的数字');