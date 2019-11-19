/**
 *  查询
 */
$(function(){
	//加载分页信息
	queryList();
});

/**
 * @function 检索条件，分页获取列表信息
 * @param pageno 起始
 * @param limit 每页数量
 */
function queryList(pageno,limit){
	if(typeof pageno == "undefined"){
		pageno = 1;
	}
	if(typeof limit == "undefined"){
		limit = 10;
	}
	var start = (pageno - 1)*limit;
	//序列化查询Form
	var params = $('.searchForm').serialize();
	params += "&start=" + start + "&limit=" + limit;
	Chief.ajax.postJson('/organizaManage/queryOrganizaManageList', params, function(data){
		var dsa = Handlebars.compile($("#T_tabletpl").html());
		$("#J_tabletpl").html(dsa(data));
		//初始化分页数据(当前页码，总数，回调查询函数)
		initPaginator(pageno,data.total, queryList);
	});
}

$("#J_search").click(function(){
	queryList();
});

//新增按钮
$('.pull-right').on('click','#add_btn', function (){
    var htmls = Handlebars.compile($("#T_addOrganizaManage").html());
    var ht = htmls();
    Chief.layer.editDiv("创建组织机构", ht,
		function(){ //OkCallBack
            //执行更新方法
            var flag = $("#addOrganizaManageForm").validate().form(); //若全部通过验证则form方法返回true
            if(flag) {
                saveOrganizaManage();
            }else {
                Chief.layer.tips("请按提示输入正确的内容");
                return false;
            }
        },
    	null,'750px', '400px', '80px');
    //初始化表单验证规则
    formValidate();
});
//组织机构管理新增
function saveOrganizaManage(){
	//获取表单数据
    var data = $('#addOrganizaManageForm').serialize();
	//发起请求
    Chief.ajax.postJson("/organizaManage/saveOrganizaManageInfo", data, function (data) {
        if("0" == data.code){
            Chief.layer.tips("保存成功", 800);
            Chief.layer.close();
            queryList();
        }else if("-1" == data.code){
            Chief.layer.tips(data.msg, 800);
        }else {
            Chief.layer.tips("保存失败", 800);
        }
    });
};

//删除组织机构管理
$("body").on('click','#del_btn',function() {
	//获取选中框数据
	var _edit = $("#J_tabletpl td").find("input[type='checkbox']:checked");//获取选中行数量
	if(_edit.length == 0){
		Chief.layer.tips('请选择一条数据！');
		return false;
	}
	var orgId = _edit.eq(0).attr("data-id"); //获取选中行的组织机构id
	var organizaId = _edit.eq(0).attr("data-organizaId"); //获取选中行的组织机构编码
	var paramId = {
		organizaId: organizaId //父机构ID
    };
    Chief.ajax.postJson('/organizaManage/checkParentOrganiza',paramId, function(data) {
		if('0' == data.code){
			if(!isDataNull(data.items)){
				Chief.layer.tips("存在子机构，不能删除！", 800);
			} else {
				delOrganizaManage(orgId);
			}
		} else {
			Chief.layer.tips("系统异常", 500);
		}
    });
});

//调用删除
function delOrganizaManage(orgId) {
	Chief.layer.confirm("是否确认删除！",function() {
		Chief.ajax.postJson('/organizaManage/delOrganizaManageInfo',{"id":orgId},function(data){
			if(data.code == '0'){
				Chief.layer.tips("删除成功！");
				queryList();
			}else{
				Chief.layer.tips(data.msg);
			}
		})
	})
}

//点击编辑按钮
$('.pull-right').on('click','#edit_Btn', function (){
    var checked = $("#J_tabletpl input[type=checkbox]:checked");
    if(checked.length != 1) {
        Chief.layer.tips("请选择一条数据！");
        return;
    }
    // 获取该条数据详情
	var id = checked.eq(0).attr("data-id");
	var paramId = {
        id: id
    };
    Chief.ajax.postJson('/organizaManage/queryOrganizaManageDetail',paramId, function(data) {
		if('0' == data.code){
            var htmls = Handlebars.compile($("#T_editOrganizaManage").html());
            var ht = htmls(data.item);
		    //弹框编辑
		    Chief.layer.editDiv("编辑", ht,
		        function(){ //OkCallBack
		    		//执行更新方法
		            var editFlag = $("#editOrganizaManageForm").validate().form(); //若全部通过验证则form方法返回true
		            if(editFlag) {
		            	updateOrganizaManage(id);
		            }else {
		                Chief.layer.tips("请按提示输入正确的内容");
		                return false;
		            }
		        },
		        null, '750px', '400px', '80px');
		    //初始化表单验证规则
		    formValidate();
		}else {
			Chief.layer.tips("系统异常", 500);
		}
    });
});

/**
 * 编辑 保存
 */
function updateOrganizaManage(){
    //获取表单数据
    var data = $('#editOrganizaManageForm').serialize();
    //发起请求
    Chief.ajax.postJson("/organizaManage/updateOrganizaManageInfo", data, function (data) {
        if("0" == data.code){
            Chief.layer.tips("保存成功", 800);
            Chief.layer.close();
            queryList();
        }else {
            Chief.layer.tips("保存失败", 800);
        }
    });
};
//添加子机构
function addSonOrganiza(parentId){
	queryParentOrganiza(parentId); //调用父机构查询
	var htmls = Handlebars.compile($("#T_addSonOrganiza").html());
    var ht = htmls();
    Chief.layer.editDiv("添加子机构", ht,
		function(){ //OkCallBack
            //执行更新方法
            var flag = $("#addSonOrganizaForm").validate().form(); //若全部通过验证则form方法返回true
            if(flag) {
            	saveSonOrganiza();
            }else {
                Chief.layer.tips("请按提示输入正确的内容");
                return false;
            }
        },
    	null,'750px', '450px', 'auto');
    //初始化表单验证规则
    formValidate();
}

//组织机构管理-子机构新增
function saveSonOrganiza(){
	//获取表单数据
    var data = $('#addSonOrganizaForm').serialize();
    //对名称select框值和文本重新获取
    var parentOrganizaId = $("#A_parentOrganizaName option:selected").val();
    var parentOrganizaName = $("#A_parentOrganizaName option:selected").text().trim();
    data += "&parentOrganizaId=" + parentOrganizaId + "&parentOrganizaName=" + parentOrganizaName;
	//发起请求
    Chief.ajax.postJson("/organizaManage/saveOrganizaManageInfo", data, function (data) {
        if("0" == data.code){
            Chief.layer.tips("保存成功", 800);
            Chief.layer.close();
            queryList();
        }else if("-1" == data.code){
            Chief.layer.tips(data.msg, 800);
        }else {
            Chief.layer.tips("保存失败", 800);
        }
    });
};

//父机构查询,新增时用
function queryParentOrganiza(parentId) {
    var params = {}
    Chief.ajax.postJson("/organizaManage/queryParentOrganizaList", params, function (data) {
        if(data.code == "0") {
            var shtml = Handlebars.compile($("#T_parentOrganiza").html());
            var dataHtml = shtml(data);
            $("#A_parentOrganizaName").html(dataHtml);
            if(!isDataNull(parentId)){
            	//设置选中
				$('#A_parentOrganizaName').val(parentId);
				$('#A_parentOrganizaName').attr("disabled", true);
            }
        }else {
            Chief.layer.tips("系统异常")
        }
    }, false);
}
//数据校验
function formValidate(){
	//组织机构管理新增验证
    $("#addOrganizaManageForm").validate({
        errorPlacement: function(error, element) { //验证消息放置的位置
            error.appendTo(element.parent()).addClass('reportvalidate');
        },
        onfocusout: function(element) {
            $(element).valid();
        }
    });
    //组织机构管理编辑验证
    $("#editOrganizaManageForm").validate({
        errorPlacement: function(error, element) { //验证消息放置的位置
            error.appendTo(element.parent()).addClass('reportvalidate');
        },
        onfocusout: function(element) {
            $(element).valid();
        }
    });
    //子机构添加验证
    $("#addSonOrganizaForm").validate({
        errorPlacement: function(error, element) { //验证消息放置的位置
            error.appendTo(element.parent()).addClass('reportvalidate');
        },
        onfocusout: function(element) {
            $(element).valid();
        }
    });
}

//校验非空
function isDataNull(str) {
    if (str == null || str == "" || str == undefined) {
        return true;
    }
    return false;
}

