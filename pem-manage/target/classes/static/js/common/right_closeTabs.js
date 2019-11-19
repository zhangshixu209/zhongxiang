/**
 * 初始化
 */
$(function(){
	tabCloseEven();
});

/**
 * 生成右键菜单
 */
function tabCloseEven() {
	$('#tabs-list').tabs({
　　　	onContextMenu: function(e, title, index){
		　　　e.preventDefault(); // 让默认事件失效
			$('#tabs-list').tabs('select', index); // 选中标签
			var pageXX = e.pageX;
			if(pageXX > 1120){ // 判断页签超出页面，重新计算
				pageXX = e.pageX - 120;
			}
			$('#tabClose').menu('show', { // 显示右键菜单
			　　　left: pageXX,
			　　　top: e.pageY
			});
			$("#tabClose").css("width","120px");
			$(".menu-shadow").css("display","none");
			$('#tabClose').menu({ // 右键点击标签
		        onClick: function (item) {
		            closeTab(item.id); // 调用关闭标签的方法
		        }
		    });
　　　	}
   });
}

/**
 * 关闭标签的方法
 * @param tabId 选中的ID
 */
function closeTab(tabId) {
    var alltabs = $('#tabs-list').tabs('tabs');
    var currentTab =$('#tabs-list').tabs('getSelected');
    var allTabtitle = [];
    $.each(alltabs, function(i, n){
        allTabtitle.push($(n).panel('options').title);
    });
    // 循环判断选中
    switch (tabId) {
        case "close": // 关闭
            var currtab_title = currentTab.panel('options').title;
            $('#tabs-list').tabs('close', currtab_title);
            
            break;
        case "closeall": // 关闭全部
            $.each(allTabtitle, function (i, n) {
                $('#tabs-list').tabs('close', n);
            });
            
            break;
        case "closeother": // 关闭其它
            var currtab_title = currentTab.panel('options').title;
            var tabIndex = $('#tabs-list').tabs('getTabIndex', currentTab);
            if (tabIndex == 0) {
                Chief.layer.tips("无其他标签！");
                return false;
            }
            $.each(allTabtitle, function (i, n) {
                if (n != currtab_title) {
                    $('#tabs-list').tabs('close', n);
                }
            });
            $('#tabs-list').tabs('scrollBy', -2000);
            break;
        case "closeright": // 关闭右侧
            var tabIndex = $('#tabs-list').tabs('getTabIndex', currentTab);
            if (tabIndex == alltabs.length - 1) {
            	Chief.layer.tips("无右侧标签！");
                return false;
            }
            $.each(allTabtitle, function (i, n) {
                if (i > tabIndex) {
                    $('#tabs-list').tabs('close', n);
                }
            });
            $('#tabs-list').tabs('scrollBy', -2000);
            break;
        case "closeleft": // 关闭左侧
            var tabIndex = $('#tabs-list').tabs('getTabIndex', currentTab);
            if (tabIndex == 0) {
                Chief.layer.tips("无左侧标签！");
                return false;
            }
            $.each(allTabtitle, function (i, n) {
                if (i < tabIndex) {
                    $('#tabs-list').tabs('close', n);
                }
            });
            $('#tabs-list').tabs('scrollBy', -2000);
            break;
    }
}
