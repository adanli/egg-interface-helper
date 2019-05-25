$(function(){
    constructDataList();
})
/**
 * 渲染详细数据
 */
let renderingDetailedData = (data) => {
    const {name, description, type, url,  queryVO, headerVO, bodyVO, responseVO} = data;
    $('#name').val(name);
    $('#description').val(description);
    $('#type').text(type);
    $('#url').val(url);
    let obj = {'query':queryVO, 'headers':headerVO, 'body':bodyVO, 'response':responseVO};
    for(let key in obj){
        renderingBottomDetailedData(key, obj[key]);
        console.log(obj[key]);
    }
}
let renderingBottomDetailedData = (key, vo) => {
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
            str += '<tr><td>'+obj.code+'</td><td>'+obj.type+'</td><td>'+obj.description+'</td><td>'+obj.necessary+'</td><td>'+obj.maxLength+'</td><td>'+obj.remark+'</td>';
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
}
/**
 * 左侧tab点击请求数据
 */
let leftTabControl = (that) => {
    if($(that).hasClass('cus-select')){
        $(that).removeClass('cus-select');
        $('#form').css('display', 'inline');
        $('#detailed').css('display', 'none');
    }else{
        $('#left tr').removeClass('cus-select');
        $(that).addClass('cus-select');
        $('#form').css('display', 'none');
        $('#detailed').css('display', 'inline');
        let interface_id = $(that).attr('interface_id')
        let data = request('/ih/rest/apiService/v1/interface/'+interface_id,'GET',{});
        renderingDetailedData(data);
    }
}
/**
 * 渲染数据
 * @param data
 */
let renderingData = (data) => {
    let leftStr = '';
    let rightStr = '';
    for(let i = 0; i < data.length; i++){
        let obj = data[i];
        leftStr += '<table class="table table-striped">' +
            '       <caption>'+obj.name+'</caption>' +
            '            <tbody>';
        rightStr += '<table class="table table-striped">' +
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