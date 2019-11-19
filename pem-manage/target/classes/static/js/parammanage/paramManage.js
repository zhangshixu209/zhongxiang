/**
 *  字典参数查询查询
 */
$(function(){
	//初始化加载分页信息
	queryList();
	
	//处理table中的转换
	Handlebars.registerHelper('statusType', function(status,options) {
        if(status == '0') {
        	return '无效';
        }else{
        	return '有效';
        }
    });
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
	var paramKey = $("#paramKey").val();
	var paramName = $("#paramName").val();
	var params = {
		start : start, //起始
		limit : limit, // 每页数量
		paramKey:paramKey,
		paramName:paramName
	}
	Chief.ajax.postJson('/paramManage/queryParamManageList', params, function(data){
		var paramManege = Handlebars.compile($("#T_tabletpl").html());
		$("#J_tabletpl").html(paramManege(data));
		//初始化分页数据(当前页码，总数，回调查询函数)
		initPaginator(pageno,data.total, queryList);
		$("#sample-table-1").colResizable({ // 手动拖动表格
			liveDrag:true, 
			fixed:false,
//			resizeMode:'overflow'
		});
	});
}

//查询按钮
$('#paramManege_search').bind('click', function (){
	queryList();
});
//重置按钮
$('#paramManege_clear').bind('click', function (){
	$('#paramManege_searchid')[0].reset();//清空form
});

//新增按钮 
$('#addParamManage').bind('click', function (){
	 var htmls = Handlebars.compile($("#editPredicMonitor").html());
	 var ht = htmls();
	 Chief.layer.editDiv("新增", ht,
		function(){ //OkCallBack
		 //执行更新方法
         var flag = $("#editPredicMonitorForm").validate().form(); //若全部通过验证则form方法返回true
         if(flag) {
             saveProjMonitorList();
         } else {
             Chief.layer.tips("请按提示输入正确的内容");
             return false;
         }
     },
 	null,
     '750px', '400px', '80px');
	 $("#id").val('');//新增时候清空id
	 //初始化表单验证规则
	 formValidate();
});


//编辑
$('#editParamManage').bind('click', function (){
	var ckboxes = $("input[type=checkbox].ace:checked");
	if(ckboxes.length == 0) {
		Chief.layer.tips("请选择一条数据！");
		return;
	}
	var $ckbox = ckboxes.eq(0);
	var id = $ckbox.val();
	var paramId = {
		id: id
	};
	Chief.ajax.postJson('/paramManage/queryParamManageList', paramId, function(data) {
		if(data.code == '0' && data.items.length > 0) {
			var htmls = Handlebars.compile($("#editPredicMonitor").html());
			var ht = htmls(data.items[0]);
			Chief.layer.editDiv("编辑", ht,
					function(){ //OkCallBack
			         //执行更新方法
			         var flag = $("#editPredicMonitorForm").validate().form(); //若全部通过验证则form方法返回true
			         if(flag) {
			             saveProjMonitorList();
			         }else {
			             Chief.layer.tips("请按提示输入正确的内容");
			         }
			     },null,'750px', '400px', '80px');
			$("input:radio[name='status'][value='"+data.items[0].status+"']").prop('checked',true);
		}else{
			Chief.layer.tips("获取数据失败,请刷新重试!");
		}
	});
});
	
	
//点击删除做逻辑删除
$('#delParamManage').bind('click',function (){
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
  Chief.ajax.postJson('/paramManage/delParamManage',paramId, function(data) {
		if('0' == data.code){
            //删除成功
			Chief.layer.tips("删除成功!", 1000);
			queryList();
			Chief.layer.close();
		}else {
			//删除失败
			Chief.layer.tips("系统异常!", 1000);
		}
  });
});


/**  事件定义 end  **/

/**
* 保存 新增
*/
function saveProjMonitorList(){
	//获取表单数据
 var data = $('#editPredicMonitorForm').serialize();
	//发起请求
 Chief.ajax.postJson("/paramManage/addEditParamManage", data, function (data) {
     if("0" == data.code){
         Chief.layer.tips("操作成功", 1500);
         Chief.layer.close();
         queryList();
     }else {
         Chief.layer.tips(data.msg, 1500);
     }
 });
};

/**
* 编辑 保存
*/
function updateProjMonitorList(){
 //获取表单数据
 var data = $('#editPredicMonitorForm').serialize();
 //发起请求
 Chief.ajax.postJson("/predictMonitor/updatePredictMonitor", data, function (data) {
     if("0" == data.code){
         Chief.layer.tips("保存成功", 1500);
         Chief.layer.close();
         queryList();
     }else {
         Chief.layer.tips("保存失败", 1500);
     }
 });
};


//数据校验规则
function formValidate(){
  $("#addPredicMonitorForm").validate({
      errorPlacement: function(error, element) { //验证消息放置的位置
          error.appendTo(element.parent()).addClass('bfuid');
      },
      onfocusout: function(element) {
          $(element).valid();
      }
  });

  $("#editPredicMonitorForm").validate({
      errorPlacement: function(error, element) { //验证消息放置的位置
          error.appendTo(element.parent()).addClass('bfuid');
      },
      onfocusout: function(element) {
          $(element).valid();
      }
  });
}
