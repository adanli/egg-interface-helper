$(function(){
    constructDataList(); //构建类数据
    renderingTreeData(); //渲染树数据
})
/**
 * 根据目录组合数据
 * @param url
 * @param type
 * @constructor
 */
let constructDataListByDirectory = (params) => {
    let data = request('/ih/rest/apiService/v1/directory','GET', params); //请求父LIST
    let clazz = data.class;
    // if(clazz.length > 0){
        for(let i = 0; i < clazz.length; i++){
            let obj = clazz[i];
            obj .childData = request('/ih/rest/apiService/v1/interfaces', 'GET',{classId:obj.classId});
        }
        console.log(clazz);
        renderingData(clazz);
    // }
}

/**
 * 构建树数据
 * @param params
 * @returns {Array}
 */
let constructTreeData = (params) => {
    let nodes = [];
    let {directory} = request('/ih/rest/apiService/v1/directory','GET', params);
    if(directory.length > 0){
        for(let i in directory){
            let obj = directory[i];
            let node = {
                id: obj.directoryId,
                icon:'glyphicon glyphicon-folder-close',
                text: obj.name,
                nodes: constructTreeData({parentId:obj.directoryId})
            }
            nodes.push(node);
        }
    }
    if(JSON.stringify(nodes) == "[]"){
        return null
    }else{
        return nodes;
    }
}
/**
 * 渲染树数据
 */
let renderingTreeData = () => {
    let treeData = constructTreeData({});
    $('#val').treeview({
        data: treeData,         // 数据源
        emptyIcon: '',    //没有子节点的节点图标
        multiSelect: false,    //多选
        levels: 0,
        expandIcon:"glyphicon glyphicon-triangle-right",
        collapseIcon: "glyphicon glyphicon-triangle-bottom",
        showBorder: true,
        borderColor: "#fff",
        selectedBackColor: '#f5f5f5',
        selectedColor: '#000',
        onNodeExpanded: function(event, data){
            //阻止冒泡事件
            window.event? window.event.cancelBubble = true : e.stopPropagation();
        },
        onNodeCollapsed: function (event, data) {
            window.event? window.event.cancelBubble = true : e.stopPropagation();
        },
        onNodeSelected: function (event, data) {
            // console.log(data);
            $('#tree_select button').first().text(data.text);
            constructDataListByDirectory({parentId:data.id});
        },
    });
}
/**
 * 左侧tab点击请求数据
 */
let leftTabControl = (that) => {
    if($(that).hasClass('cus-select')){
        $(that).removeClass('cus-select');
        $('#synopsis').css('display', 'inline');
        $('#detailed').css('display', 'none');
    }else{
        $('#left tr').removeClass('cus-select');
        $(that).addClass('cus-select');
        $('#synopsis').css('display', 'none');
        $('#detailed').css('display', 'inline');
        let interface_id = $(that).attr('interface_id')
        let data = request('/ih/rest/apiService/v1/interface/'+interface_id,'GET',{});
        renderingDetailedData(data);
    }
}
/**
 * 渲染详细数据
 */
let renderingDetailedData = (data) => {
    const {name, description, type, url,  queryVO, headerVO, pathVO, bodyVO, responseVO} = data;
    $('#name').val(name);
    $('#description').val(description);
    $('#type').text(type);
    $('#url').val(url);
    let obj = {'query':queryVO, 'headers':headerVO, 'path':pathVO};
    let bodyResponseObj = {'body':bodyVO, 'response':responseVO};
    //params/headers
    for(let key in obj){
        renderingBottomDetailedData(key, obj[key]);
        // console.log(obj[key]);
    }
    bodyResponseData['bodyResponsedefault'] = bodyResponseObj;
    //body/response
    for(let key in bodyResponseObj){
        preparatoryWork('default', $.parseJSON(bodyResponseObj[key].example), '', key);
    }
    //填充example数据
    for(let key in bodyResponseObj){
        constructExampleData('default', bodyResponseObj[key], key);
    }
    //填充body/response input 数据
    // for(let key in bodyResponseObj){
    constructBodyResponseData('default');
    // }
    //将所有input置为不可点击
    $('.tab-content input').attr('disabled',true);
    $('.tab-content input').attr('placeholder','');
    $('.tab-content input').unbind();;
}
/**
 * 填充example数据
 */
let constructExampleData = (suffix, vo,  head) => {
    if(vo.hasOwnProperty('example')){
        let example = vo.example;
        if(example != null){
            $('#'+head+'_div_'+suffix).append('<textarea class="form-control" disabled placeholder="Json字符串" rows='+example.split('\n').length+'>'+example+'</textarea>');
        }
    }
}
let renderingBottomDetailedData = (key, vo) => {
    $('#'+key).empty();
    let params = vo.params;
    if(JSON.stringify(params) != '[]'){
        let str = '<table class="table"><thead><caption>'+key.substr(0,1).toUpperCase()+key.substr(1)+'</caption><tr>' +
            '         <th>参数名称</th>' +
            '         <th>数据类型</th>' +
            '         <th>属性描述</th>' +
            '         <th>是否必填</th>' +
            '         <th>最大长度</th>' +
            '         <th>备注</th></tr></thead><tbody>';
        for(let i = 0; i < params.length; i++){
            let obj = params[i];
            for(let key in obj){
                if(obj[key] == null){
                    obj[key] = '';
                }
            }
            str += '<tr><td>'+obj.code+'</td><td>'+obj.type.replace(/</g,'&lt;').replace(/>/g,'&gt;')+'</td>' +
                '<td>'+obj.description+'</td><td>'+obj.necessary+'</td><td>'+obj.maxLength+'</td><td>'+obj.remark+'</td></tr>';
        }
        str += '</tbody></table>';
        $('#'+key).append(str);
    }
}
/*let renderingBottomDetailedData = (key, vo) => {
    $('#'+key).empty();
    let params = vo.params;
    if(JSON.stringify(params) != '[]'){
        let str = '<table class="table table-striped"><thead><tr>' +
            '         <th>参数名称</th>' +
            '         <th>数据类型</th>' +
            '         <th>属性描述</th>' +
            '         <th>是否必填</th>' +
            '         <th>最大长度</th>' +
            '         <th>备注</th>';
        if(key == 'body' || key == 'response'){
            str += '<th>父级</th>';
        }
        str += '</tr></thead><tbody>';
        for(let i = 0; i < params.length; i++){
            let obj = params[i];
            for(let key in obj){
                if(obj[key] == null){
                    obj[key] = '';
                }
            }
            str += '<tr><td>'+obj.code+'</td><td>'+obj.type.replace(/</g,'&lt;').replace(/>/g,'&gt;')+'</td><td>'+obj.description+'</td><td>'+obj.necessary+'</td><td>'+obj.maxLength+'</td><td>'+obj.remark+'</td>';
            if(key == 'body' || key == 'response'){
                str+= '<td>'+obj.parent+'</td>';
            }
            str += '</tr>';
        }
        str += '</tbody></table>';
        $('#'+key).append(str);
        if(vo.hasOwnProperty('example')){
            let example = vo.example;
            if(example != null){
                $('#'+key).append('<textarea class="form-control" disabled placeholder="Json字符串" rows='+example.split('\n').length+'>'+example+'</textarea>');
            }
        }
    }
}*/
/**
 * 组合数据
 * @param url
 * @param type
 * @constructor
 */
let constructDataList = (url, type) => {
    let data = request('/ih/rest/apiService/v1/classes','GET', {}); //请求父LIST
    for(let i = 0; i < data.length; i++){
        let obj = data[i];
        obj .childData = request('/ih/rest/apiService/v1/interfaces', 'GET',{classId:obj.classId});
    }
    console.log(data);
    renderingData(data);
}
/**
 * 渲染数据
 * @param data
 */
let renderingData = (data) => {
    $('#left').empty();
    $('#form').empty();
    let leftStr = '';
    let rightStr = '';
    for(let i = 0; i < data.length; i++){
        let obj = data[i];
        leftStr += '<table class="table">' +
            '       <caption>'+obj.name+'</caption>' +
            '            <tbody>';
        rightStr += '<table class="table">' +
            '       <caption>&nbsp</caption>' +
            '            <tbody>';
        for(let j = 0; j < obj.childData.length; j++){
            let childObj = obj.childData[j];
            leftStr += '<tr class="cursor" onclick="leftTabControl(this)" interface_id = '+childObj.interfaceId+'><td>'+childObj.name+'</td></tr>';
            rightStr += '<tr><td style="width: 50%">'+childObj.url+'</td><td>'+childObj.description+'</td></tr>';
        }
        leftStr += '</tbody></table>';
        rightStr += '</tbody></table>';
    }
    $('#left').append(leftStr);
    $('#form').append(rightStr);
}
/**
 * 请求数据
 * @param url
 * @param type
 * @param data
 * @returns {Array}
 */
let request = (url, type, data) => {
    let respData = [];
    $.ajax({
        url: url,
        type: type,
        data: data,
        async: false, //关闭异步
        contentType: 'application/json',
        success:function(resp){
            if(resp.data != null){
                respData = resp.data;
            }
        },
        error:function(resp){
            console.log(resp);
        }
    })
    return respData;
}