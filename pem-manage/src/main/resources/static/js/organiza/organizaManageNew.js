//设置setting
var setting = {
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false
	},
	edit: {
		enable: true,
		showRemoveBtn: showRemoveBtn,
		showRenameBtn: showRenameBtn
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
		beforeDrag: beforeDrag,
		beforeRemove: beforeRemove,
		beforeRename: beforeRename
	}
};

//禁止拖拽 false
function beforeDrag(treeId, treeNodes) {
	return false;
}

//隐藏显示编辑按钮
function showRenameBtn(treeId, treeNode) {
	// 根节点没有父机构parentId为null
	if(isDataNull(treeNode.parentId)){
		return false;
	}
	return true;
}

//隐藏显示删除按钮
function showRemoveBtn(treeId, treeNode) {
	// 根节点没有父机构parentId为null
	if(isDataNull(treeNode.parentId)){
		return false;
	}
	return true;
}

/**
 * 编辑节点
 * @param treeId 树ID
 * @param treeNode 树节点
 * @returns {Boolean}
 */
function beforeEditName(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	setTimeout(function() {
		zTree.editName(treeNode);
	}, 0);
	return false;
}

/**
 * 删除节点
 * @param treeId 树ID
 * @param treeNode 树节点
 * @returns {Boolean}
 */
function beforeRemove(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	
	// 获取参数
	var paramId = {
		organizaId: treeNode.id //机构ID
    };
	var orgId = treeNode.orgId; //机构主键ID
    Chief.ajax.postJson('/organizaManage/checkParentOrganiza',paramId, function(data) {
		if('0' == data.code){
			if(!isDataNull(data.items)){
				Chief.layer.tips("存在子机构，不能删除！", 800);
				return false;
			} else {
				Chief.ajax.postJson('/organizaManage/checkOrganizaUserDept',paramId,function(data){
					if(data.code == '0'){
						if(data.total > 0){
							Chief.layer.tips("机构已关联用户，不能删除！", 800);
							return false;
						} else {
							Chief.layer.confirm("是否确认删除！",function() {
								Chief.ajax.postJson('/organizaManage/delOrganizaManageInfo',{"id": orgId},function(data){
									if(data.code == '0'){
										Chief.layer.tips("删除成功！");
										// 在异步删除数据操作成功后，再手动调用 `removeNode` 删除节点
										zTree.removeNode(zTree.getSelectedNodes()[0]);
									}else{
										Chief.layer.tips(data.msg);
									}
								})
							});
						}
					}else{
						Chief.layer.tips(data.msg);
					}
				})
			}
		} else {
			Chief.layer.tips("系统异常", 500);
			return false;
		}
    });
    // 因为删除数据是异步操作，所以首先要拒绝删除操作
    return false;
}

/**
 * 编辑节点名称
 * @param treeId 树ID
 * @param treeNode 树节点
 * @param newName 新名称
 * @param isCancel 
 * @returns {Boolean}
 */
function beforeRename(treeId, treeNode, newName, isCancel) {
	if (newName.length == 0) {
		setTimeout(function() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.cancelEditName();
			Chief.layer.tips("节点名称不能为空", 2000);
			setTimeout('outTips()', 1500 );
		}, 0);
		return false;
	} else {
		var data = {
			id: treeNode.orgId,
			organizaName: newName
		}
		//发起请求
	    Chief.ajax.postJson("/organizaManage/updateOrganizaManageInfo", data, function (data) {
	        if("0" == data.code){
	            return true;
	        }else {
	            Chief.layer.tips("保存失败", 800);
	            return false;
	        }
	    });
		return true;
	}
	return false;
}

var newCount = 0;
/**
 * 添加节点
 * @param treeId 树ID
 * @param treeNode 树节点
 */
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	
	//判断是否点击新增按钮
	if (btn) btn.bind("click", function(){
		newCount = newCount + 1;
		var organizaName = "新增部门" + newCount;
		var data = {
			organizaName: organizaName,
			parentOrganizaId: treeNode.id,
			parentOrganizaName: treeNode.name,
			sort: newCount
		}
		//发起请求
	    Chief.ajax.postJson("/organizaManage/saveOrganizaManageInfo", data, function (data) {
	        if("0" == data.code){
	        	Chief.layer.tips("保存成功", 1500);
	        	setTimeout('outTips()', 2000 );
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.addNodes(treeNode, {id:data.item.organizaId, pId:treeNode.id, orgId:data.item.orgId, name:"新增部门" + newCount});
				var len = treeNode.children.length -1; // 获取要编辑的节点位置
				// 编辑新增的子节点
				beforeEditName(treeId, treeNode.children[len]);
	        }else if("-1" == data.code){
	            Chief.layer.tips(data.msg, 800);
	        }else {
	            Chief.layer.tips("保存失败", 800);
	        }
	    });
	});
};

// 鼠标移开按钮消失
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};

//初始化ztree树
$(document).ready(function(){
	queryRoleRightList();
});

//初始化加载组织机构管理树
function queryRoleRightList(){
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
			//组织机构根节点目前之能后台添加
			if(!isDataNull(data.items)){
				var treeObj = $.fn.zTree.init($("#treeDemo"), setting, data.items);
			} else {
				$(".emptyTree").addClass("display: block");
			}
		}else{
			Chief.layer.tips(data.msg);
		}
	})
}

//延迟关闭弹窗
function outTips(){
	Chief.layer.close();
}

//校验非空
function isDataNull(str) {
    if (str == null || str == "" || str == undefined || str == [] || str == "[]") {
        return true;
    }
    return false;
}
