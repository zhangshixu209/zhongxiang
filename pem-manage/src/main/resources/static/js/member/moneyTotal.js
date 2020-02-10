/**
 *  查询
 */
$(function(){
	queryList(); //加载分页信息
});

// 备份还原状态
Handlebars.registerHelper('statusCd', function(v) {
	if (v == 'Y') {
		return '平衡';
	} else if (v == 'N') {
		return '异常';
	} else{
		return '';
	}
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
	Chief.ajax.postJson('/memberAssets/queryMemberMoneyTotalList', params, function(data){
		var dsa = Handlebars.compile($("#T_tabletpl").html());
		$("#J_tabletpl").html(dsa(data));
		//初始化分页数据(当前页码，总数，回调查询函数)
		initPaginator(pageno,data.total, queryList);
		$("#sample-table-1").colResizable({ // 手动拖动表格
			liveDrag:true,
			fixed:false,
//			resizeMode:'overflow'
		});
	});
}

$("#J_search").click(function(){
	queryList();
});

//任务导出
$("#export_btn").on("click",function(){
	var params = $('.searchForm').serialize();
	Chief.ajax.postJson('/memberAssets/queryMemberMoneyTotalList', params, function(data){
		if (data.code=="0") {
			if (data.total>0) {
				window.location.href="../api/memberAssets/exportMoney?" + encodeURI(encodeURI(params));
			} else {
				Chief.layer.tips('没有数据导出');
			}
		} else {
			Chief.layer.tips(data.msg);
		}
	});
});

//成功提示
function outTips(){
	Chief.layer.close();
	queryList();
}

//取消按钮关闭弹窗
function doCancel(){
	Chief.layer.close();
}

//校验非空
function isDataNull(str) {
	if (str == null || str == "" || str == undefined) {
		return true;
	}
	return false;
}

