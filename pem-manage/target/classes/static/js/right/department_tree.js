//设置setting
var setting = {
	check: {
		enable: true,
		chkboxType: {"Y":"", "N":""}
	},
	view: {
		dblClickExpand: false
	},
	data: {
		key:{
			children: "children",
			isParent: "parent"
		},
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "parentId",
			orgId: "orgId"
		}
	},
	callback: {
		beforeClick: beforeClick,
		onCheck: onCheck
	}
};

function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

/**
 * 选中树节点
 * @param e
 * @param treeId 树ID
 * @param treeNode 树节点
 */
function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getCheckedNodes(true),
	ids = "";
	v = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
		ids += "'"+nodes[i].id+"'" + ",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (ids.length > 0 ) ids = ids.substring(0, ids.length-1);
	var deptObj = $("#S_userDepartment"); // 部门名称
	var deptIds = $("#H_userDepartment"); // 部门Id 做查询使用
	deptObj.prop("value", v);//设置值
	deptObj.prop("title", v);//设置title
	deptIds.prop("value", ids);//ids
}

// 点击显示部门树
function showMenu() {
	var deptObj = $("#S_userDepartment");
	var deptOffset = $("#S_userDepartment").offset();
	$("#menuContent").css({left:deptOffset.left + "px", top:deptOffset.top + deptObj.outerHeight() + "px"}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}

// 隐藏树
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}

// 点击页面其它部分隐藏树
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "S_userDepartment" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

//初始化ztree树
$(document).ready(function(){
	queryRoleDeptList();
});

//初始化加载组织机构管理树
function queryRoleDeptList(){
	Chief.ajax.postJson('/sys/role/queryUserDepartment',{}, function(data){
		if(data.code == '0'){
			var treeArray = data.items;
			if(null!=treeArray){
				for (var i = 0; i < treeArray.length; i++) {
					if(treeArray[i].hasState=='1'){
						treeArray[i].checked = true;
					}
				}
			}
			$.fn.zTree.init($("#treeDemo"), setting, data.items);
		}else{
			Chief.layer.tips(data.msg);
		}
	})
}