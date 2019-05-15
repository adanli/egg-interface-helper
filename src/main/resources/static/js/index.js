$(function(){
    let flag = false;
    /**
     * 树状结构数据加载
     * @type {Array}
     */
    const data = [];
    for(let j = 0; j < 5; j++){
        var nodes = [];
        for(let i = 0; i < 2; i++){
            var node = {
                text: 'test' +i,
                id: i,
            //  color:"#fff",
//              backColor: "rgba(0,0,0,0.95)",
//              backColor: '#223043',
                href: 'controller'+i,
            }
            nodes.push(node);
        }
        var parent = {
            text: '文件夹' + j,
            id: j,
            icon:'glyphicon glyphicon-folder-close',
            // color:"#fff",
            // backColor:'#29384c',
//          backColor: "rgba(0,0,0,0.95)",
            selectable:false,
            nodes:nodes
        }
        data.push(parent);
    }

    $('#history').treeview({
        data: data,         // 数据源
        emptyIcon: '',    //没有子节点的节点图标
        multiSelect: false,    //多选
        levels: 0,
        expandIcon:"glyphicon glyphicon-triangle-right",
        collapseIcon: "glyphicon glyphicon-triangle-bottom",
        showBorder: true,
        borderColor: "#fff",
        selectedBackColor: '#f5f5f5',
//      onhoverColor: "#1f1f1f",
        onNodeChecked: function (event,data) {
            console.log("nodeId = "+data.nodeId);
            console.log("id = "+data.id);
        },
        onNodeSelected: function (event, data) {
            $('#i_content').attr("src", data.href);
            console.log("nodeId = "+data.nodeId);
            console.log("id = "+data.id);
        },
    });
    /**
     * 接口描述图标点击控制
     */
    $('#interface_desc_icon').click(function(){
        // alert(1);
        if(flag){
            $('#interface_desc_icon').attr('class','glyphicon glyphicon-triangle-left cursor')
            $('#interface_desc').css('display','none')
            flag = false;
        }else{
            $('#interface_desc_icon').attr('class','glyphicon glyphicon-triangle-bottom cursor')
            $('#interface_desc').css('display','inline');
            flag = true;
        }
    })
})

/**
 * URL名称修改
 */
function urlTextUpdate(){
    var text = $('#url_text').val()
    $('#interface_url').text(text);
    $('#href_content').text(text);
}