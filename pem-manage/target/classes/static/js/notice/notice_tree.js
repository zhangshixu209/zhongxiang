function zTreeOnCheck(event, treeId, treeNode) {
    //console.log(ZTree.tree.getCheckedNodes()[0].getParentNode()) 
    console.log(treeNode.getParentNode());// 获取父节点
    console.log(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
};
//节点树配置
//初始化加载部门树
var ZTree = {
    setting: {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y" : "s", "N" : "ps" }, // 设置斧子关联关系
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId",
            }
        },
        callback: {
            onCheck: zTreeOnCheck
        }
    }
};


//初始化加载权限树
function getDeepartmentUserTree(){
    Chief.ajax.postJson('/sys/admin/getDeepartmentUserTree',{}, function(data){
        if(data.code == '0'){
            var treeArray = data.items;
            if(null!=treeArray){
                for (var i = 0; i < treeArray.length; i++) {
                    if(treeArray[i].hasState=='1'){
                        treeArray[i].checked = true;
                    }
                }
            }
            ZTree.tree = $.fn.zTree.init($("#departmentTree"), ZTree.setting, data.items);
            ZTree.tree.expandNode(ZTree.tree.getNodeByParam("id", '0', null));
        }else{
            Chief.layer.tips(data.msg);
        }
    })
}



//初始化加载权限树并设置回显
function getNoticePermissionTree(id){
    Chief.ajax.postJson('/notice/getNoticePermissionTree',{"noticeId":id}, function(data){
        if(data.code == '0'){
            var treeArray = data.items;
            if(null!=treeArray){
                for (var i = 0; i < treeArray.length; i++) {
                    if(treeArray[i].hasState=='1'){
                        treeArray[i].checked = true;
                    }
                }
            }
            ZTree.tree = $.fn.zTree.init($("#departmentTree"), ZTree.setting, data.items);
            ZTree.tree.expandNode(ZTree.tree.getNodeByParam("id", '0', null));
        }else{
            Chief.layer.tips(data.msg);
        }
    })
}


