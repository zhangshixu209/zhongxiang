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
	Chief.ajax.postJson('/operateLog/queryOperateLogList', params, function(data){
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
//操作页面代码
Handlebars.registerHelper('operatePageCd', function(v) {
	if(v == '1000') {
		return '首页';
	} else if(v == '1001') {
		return '会员管理';
	} else if(v == '1002') {
		return '轮播图管理';
	} else if(v == '1003') {
		return '提现审核管理';
	} else if(v == '1004') {
		return '投诉管理';
	} else if(v == '1005') {
		return '充值管理';
	} else if(v == '1006') {
		return '业务分类';
	} else if(v == '1007') {
		return '报表分类';
	} else if(v == '1008') {
		return '数据源管理';
	} else if(v == '1009') {
		return '报表配置';
	} else if(v == '1010') {
		return '公告分类';
	} else if(v == '1011') {
		return '公告管理';
	} else if(v == '1012') {
		return '我的公告';
	} else if(v == '1013') {
		return '组织机构管理';
	} else if(v == '1014') {
		return '用户管理';
	} else if(v == '1015') {
		return '角色管理';
	} else if(v == '1016') {
		return '权限管理';
	} else if(v == '1017') {
		return '参数配置';
	} else if(v == '1018') {
		return '数据表管理';
	}
});
//操作类型代码
Handlebars.registerHelper('operateTypeCd', function(v) {
	if(v == '1001') {
		return '下载';
	} else if(v == '1002') {
		return '新增';
	} else if(v == '1003') {
		return '编辑';
	} else if(v == '1004') {
		return '删除';
	} else if(v == '1005') {
		return '浏览';
	} else if(v == '1006') {
		return '登录';
	} else if(v == '1007') {
		return '提交';
	} else if(v == '1008') {
		return '查询';
	} else if(v == '1009') {
		return '主题(名称)信息查看';
	} else if(v == '1010') {
		return '查看下详情';
	} else if(v == '1011') {
		return '新增存草稿';
	} else if(v == '1012') {
		return '新增提交';
	} else if(v == '1013') {
		return '编辑存草稿';
	} else if(v == '1014') {
		return '编辑提交';
	} else if(v == '1015') {
		return '数据组确认';
	} else if(v == '1016') {
		return '验收驳回';
	} else if(v == '1017') {
		return '验收通过';
	} else if(v == '1018') {
		return '验收查询';
	} else if(v == '1019') {
		return '报表离线';
	} else if(v == '1020') {
		return '重命名';
	} else if(v == '1021') {
		return '移到我的报表';
	} else if(v == '1022') {
		return '发布上线';
	} else if(v == '1023') {
		return '创建报表';
	} else if(v == '1024') {
		return '移除报表';
	} else if(v == '1025') {
		return '高级汇总';
	} else if(v == '1026') {
		return '详情查询';
	} else if(v == '1027') {
		return '已阅读人信息查询';
	} else if(v == '1028') {
		return '未阅读人信息查询';
	} else if(v == '1029') {
		return '撤回';
	} else if(v == '1030') {
		return '禁用';
	} else if(v == '1031') {
		return '启用';
	} else if(v == '1032') {
		return '重置密码';
	} else if(v == '1033') {
		return '配置角色';
	} else if(v == '1034') {
		return '配置权限';
	} else if(v == '1035') {
		return '关联用户';
	}else if (v == '1036') {
		return "配置报表"
	}else if (v == '1037') {
        return "提交测试"
    }else if (v == '1038') {
        return "发布上线"
    }
});
