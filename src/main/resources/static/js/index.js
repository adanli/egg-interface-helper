$(function(){
    //请求目录数据
    requestDirectory();
    // getHistory(); //历史记录
    //初始化json编辑器
    initJsonEditor('default');
    //初始化AutoComplete
    initAutoComplete(availableTags);
})
// let flag = false;
//AutoComplete数据
const availableTags = [
    "String",
    "Integer"
];
//请求类型数据
const method = {
    "POST":"<span style='color: #49cc90; font-weight: bold; padding: 0px 0px; margin-right: 10px'>POST</span>",
    "PUT":"<span style='color: #fca130; font-weight: bold; padding: 0px 0px; margin-right: 10px'>PUT</span>",
    "DELETE":"<span style='color: #f93e3e; font-weight: bold; padding: 0px 0px; margin-right: 10px'>DELETE</span>",
    "GET":"<span style='color: #61affe; font-weight: bold; padding: 0px 0px; margin-right: 10px'>GET</span>",
}
/**
 * 初始化加载autoComplete
 */
let initAutoComplete = (availableTags) => {
    $('.type').autocomplete({
        source: availableTags
    });
}
/**
 * 获取选择当前tab页后缀
 */
function getSelectSuffix() {
    let suffix = $('#right_top_tab .active').attr('suffix');
    return suffix;
}
/**
 * 首个tab是否显示关闭
 */
function showOrHide(){
    //获得id后缀
    let suffix = getSelectSuffix();
    //获得tab长度
    let len = $('#right_top_tab li').length;
    if(len >= 2){
        $('#close_icon_default').css('display','inline');
        $('#close_icon_'+suffix).css('display','inline');
    }else{
        $('#close_icon_default').css('display','none');
        $('#close_icon_'+suffix).css('display','none');
    }
}
/**
 * tab关闭图标控制
 */
function closeTabContro(){
    //获得id后缀
    let suffix = getSelectSuffix();
    //获取上级元素长度
    let prevLen = $('#href_content_'+suffix).parent().prev().length;
    if(prevLen > 0){
        //将上一个元素置为选中
        $('#href_content_'+suffix).parent().prev().addClass('active');
        $('#content_'+suffix).prev().addClass('active');
    }else{
        //将下一个元素置为选中
        $('#href_content_'+suffix).parent().next().addClass('active');
        $('#content_'+suffix).next().addClass('active');
    }
    //删除元素
    $('#href_content_'+suffix).parent().remove();
    $('#content_'+suffix).remove();
    //判断是否显示关闭
    showOrHide();
}
/*/!**
 * 接口描述图标点击控制
 *!/
function interfaceDescription() {
    //获得id后缀
    let suffix = getSelectSuffix();
    if(flag){
        $('#interface_desc_icon_'+suffix).attr('class','glyphicon glyphicon-triangle-left cursor')
        $('#interface_desc_'+suffix).css('display','none')
        flag = false;
    }else{
        $('#interface_desc_icon_'+suffix).attr('class','glyphicon glyphicon-triangle-bottom cursor')
        $('#interface_desc_'+suffix).css('display','inline');
        flag = true;
    }
}*/
/**
 * URL动态修改
 */
function urlTextUpdate(){
    //获得id后缀
    let suffix = getSelectSuffix();
    let url_text_val = $('#url_text_'+suffix).val();
    if(url_text_val != ""){
        $('#href_content_'+suffix+' nobr').text(url_text_val);
        // $('#interface_url_'+suffix).text(url_text_val);
    }else{
        $('#href_content_'+suffix+' nobr').text('Untitled Request');
        // $('#interface_url_'+suffix).text('Untitled Request');
    }
}
/**
 * 请求类型select选择框修改
 * @param obj
 */
function changeSelect(obj){
    let sel = $(obj).text();
    $(obj).parent().prev().children().first().text(sel);
}
/**
 * 是否必填select选择框修改
 * @param obj
 */
let bottomValChangeSelect = (obj) => {
    let sel = $(obj).text();
    $(obj).parent().parent().prev().val(sel);
}
// params和header添加trtd
let parHeaderTr = '<tr flag="false">' +
    '<td><input class="form-control" type="text" name="code" placeholder="参数名称" onkeydown="addTrTd(this)"/></td>' +
    '<td><input class="form-control type" type="text" name="type" placeholder="数据类型" onkeydown="addTrTd(this)"/></td>' +
    '<td><input class="form-control" type="text" name="description" placeholder="属性描述" onkeydown="addTrTd(this)"/></td>' +
    '<td><div class="input-group">' +
    '<input class="form-control" type="text" name="necessary" disabled placeholder="是否必填" onkeydown="addTrTd(this)"/>' +
    '<div class="input-group-btn">' +
    '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" tabindex="-1">' +
    '<span class="caret dropdown-toggle" data-toggle="dropdown"></span>' +
    '</button><ul class="dropdown-menu pull-right"><li onclick="bottomValChangeSelect(this)"><a href="#">是</a></li>' +
    '<li onclick="bottomValChangeSelect(this)"><a href="#">否</a></li>' +
    '</ul></div></div></td>' +
    '<td><input class="form-control" type="text" name="maxLength" placeholder="最大长度" onkeydown="addTrTd(this)"/></td>' +
    '<td><input class="form-control" type="text" name="remark" placeholder="备注" onkeydown="addTrTd(this)"/></td></tr>';
// body和response添加trtd
let bodResponseTr = '<tr flag="false">' +
    '<td><input class="form-control" type="text" name="code" disabled placeholder="参数名称" onkeydown="addBodyTrTd(this)"/></td>' +
    '<td><input class="form-control type" type="text" name="type" placeholder="数据类型" onkeydown="addBodyTrTd(this)"/></td>' +
    '<td><input class="form-control" type="text" name="description" placeholder="属性描述" onkeydown="addBodyTrTd(this)"/></td>' +
    '<td><div class="input-group">' +
    '<input class="form-control" type="text" name="necessary" disabled placeholder="是否必填" placeholder="是否必填" onkeydown="addBodyTrTd(this)"/>' +
    '<div class="input-group-btn">' +
    '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" tabindex="-1">' +
    '<span class="caret dropdown-toggle" data-toggle="dropdown"></span></button>' +
    '<ul class="dropdown-menu pull-right"><li onclick="bottomValChangeSelect(this)"><a href="#">是</a></li>\n' +
    '<li onclick="bottomValChangeSelect(this)"><a href="#">否</a></li></ul></div></div></td>' +
    '<td><input class="form-control" type="text" name="maxLength" placeholder="最大长度" onkeydown="addBodyTrTd(this)"/></td>' +
    '<td><input class="form-control" type="text" name="remark" placeholder="备注" onkeydown="addBodyTrTd(this)"/></td>' +
    '<td><input class="form-control" type="text" name="parent" disabled placeholder="父级" onkeydown="addBodyTrTd(this)"/></td></tr>';
/**
 * 自动追加tr,td
 */
function addTrTd(obj){
    let flag = $(obj).parent().parent().attr('flag');
    if(flag == 'true'){
        $(obj).parent().parent().attr('flag','false');
        $(obj).parent().parent().last().after(parHeaderTr);
        $(obj).parent().parent().parent().children('tr:last-child').attr('flag','true');
        //初始化AutoComplete
        initAutoComplete(availableTags);
    }
}
function addBodyTrTd(obj){
    let flag = $(obj).parent().parent().attr('flag');
    if(flag == 'true'){
        $(obj).parent().parent().attr('flag','false');
        $(obj).parent().parent().last().after(bodResponseTr);
        $(obj).parent().parent().parent().children('tr:last-child').attr('flag','true');
        //初始化AutoComplete
        initAutoComplete(availableTags);
    }
}
/**
 * 初始化json编辑器
 */
function initJsonEditor(suffix){
    var body_container,resp_container, body_options, resp_options, json;
    body_container = document.getElementById('body_json_editor_'+suffix);
    resp_container = document.getElementById('response_json_editor_'+suffix);
    //jsoneditor支持多种模式，modes: ['code', 'form', 'text', 'tree', 'view'], // allowed modes
    //jsoneditor初始化，mode为tree时，显示结构树，为text时，默认显示为纯文本
    body_options = {
        mode: 'code',
        modes: ['code', 'form', 'text', 'tree', 'view'],
        name: "jsonContent",
        onError: function (err) {alert(err.toString());},
        onEvent: function(node, event) {
            if (event.type === 'click') {
                var message = 'click on <' + node.field +
                    '> under path <' + node.path +
                    '> with pretty path: <' + prettyPrintPath(node.path) + '>';
                if (node.value) message += ' with value <' + node.value + '>';
                console.log(message);
            }
            function prettyPrintPath(path) {
                var str = '';
                for (var i=0; i<path.length; i++) {
                    var element = path[i];
                    if (typeof element === 'number') {
                        str += '[' + element + ']'
                    } else {
                        if (str.length > 0) str += ',';
                        str += element;
                    }}return str;
            }},
        onChange: function(){
            getBodyJsonData(suffix);
        }
    };
    resp_options = {
        mode: 'code',
        modes: ['code', 'form', 'text', 'tree', 'view'],
        name: "jsonContent",
        onError: function (err) {
            alert(err.toString());
        },
        onEvent: function(node, event) {
            if (event.type === 'click') {
                var message = 'click on <' + node.field +
                    '> under path <' + node.path +
                    '> with pretty path: <' + prettyPrintPath(node.path) + '>';
                if (node.value) message += ' with value <' + node.value + '>';
                console.log(message);
            }
            function prettyPrintPath(path) {
                var str = '';
                for (var i=0; i<path.length; i++) {
                    var element = path[i];
                    if (typeof element === 'number') {
                        str += '[' + element + ']'
                    } else {
                        if (str.length > 0) str += ',';
                        str += element;
                    }}return str;}},
        onChange: function(){
            getResponseJsonData(suffix);
        }
    };
    json = {};
    window['body_editor_'+suffix] = new JSONEditor(body_container, body_options, json);
    window['response_editor_'+suffix] = new JSONEditor(resp_container, resp_options, json);
}
/**
 * 解析textarea中的json
 */
function getBodyJsonData(suffix){
    let str = eval('body_editor_'+suffix).getText();
    let json = $.parseJSON(str); //json数据
    let i = 0; //修改索引值
    analysisJson(suffix, json, i, '', 'body_');
}
function getResponseJsonData(suffix){
    let str = eval('response_editor_'+suffix).getText();
    let json = $.parseJSON(str); //json数据
    let i = 0; //修改索引值
    analysisJson(suffix, json, i, '', 'response_');
}
function analysisJson(suffix, json, i, parent, type){
    let obj = $('#'+type+suffix+' tr')
    let len = obj.length;
    for(name in json){
        if(i > len - 2){
            $('#'+type+suffix+' tr:last').after(bodResponseTr);
            //初始化AutoComplete
            initAutoComplete(availableTags);
        }
        obj.eq(i).find('td:first').find('input').val(name);
        obj.eq(i).find('td:last').find('input').val(parent);
        i++;
        obj.each(function() {$(this).attr('flag','false');}); //将所有input置为false(自动不可添加)
        obj.last().attr('flag','true'); //将最后一个input置为true(可自动添加)
        if(json[name] instanceof Array){
            i = analysisJson(suffix, json[name], i, name, type);
        }else if(json[name] instanceof Object){
            i = analysisJson(suffix, json[name], i, name, type);
        }
    }
    return i;
}
/**
 * 选择文件夹构建
 */
function construnctionSelectClass(data){
    //清空已存在内容
    $('#select_class').empty()
    for(let i = 0; i < data.length; i++){
        let val = data[i];
        $('#select_class').append('<a href="#" class="list-group-item list-group-item-action" id='+val.classId+' onclick="setSelectClass(this)">' +
            '<span class="glyphicon glyphicon-folder-close" style="padding-right: 10px"></span>'+val.name+'</a>');
    }
}
/**
 * 设置选中类
 */
function setSelectClass(obj){
    $('#select_class_id').val($(obj).attr('id'));
}
/**
 * 生成随机ID
 */
let random = function() {
    let str = "abcdefghijklmnopqrstuvwxyz0123456789"; // 可以作为常量放到random外面
    let result = "";
    for(let i = 0; i < 5; i++) {
        result += str[parseInt(Math.random() * str.length)];
    }
    return result;
}
/**
 * 添加tab控制
 */
function tabControl(){
    //生成随机字符
    let ran = random();
    let href_content = '#content_'+ran;
    let id_content = 'href_content_'+ran;
    let close_icon = 'close_icon_'+ran;
    let content = 'content_'+ran;
    let interface_id = 'interface_id_'+ran;
    let interface_name = 'interface_name_'+ran;
    // let interface_url = 'interface_url_'+ran;
    // let interface_desc_icon = 'interface_desc_icon_'+ran;
    let interface_desc = 'interface_desc_'+ran;
    let content_select = 'content_select_'+ran;
    let url_text = 'url_text_'+ran;
    let right_bottom_tab = 'right_bottom_tab_'+ran;
    let right_bottom_tab_content = 'right_bottom_tab_content_'+ran;
    let href_params = '#params_'+ran;
    let href_headers = '#headers_'+ran;
    let href_body = '#body_'+ran;
    let href_response = '#response_'+ran;
    let id_params = 'params_'+ran;
    let id_headers = 'headers_'+ran;
    let id_body = 'body_'+ran;
    let id_response = 'response_'+ran;
    let body_json_editor = 'body_json_editor_'+ran;
    let response_json_editor = 'response_json_editor_'+ran;
    //移除所有选中
    $('#right_top_tab li').removeClass('active');
    //追加tab
    $('#right_top_tab li:last').after('<li role="presentation" class="active cus-padding" suffix='+ran+'>' +
        '<a href='+href_content+' id='+id_content+' data-toggle="tab" class="cus-right-top-height">' +
        '<input id='+interface_id+' style="display: none" type="text"/>' +
        '<nobr>Untitled Request</nobr>' +
        '<span id='+close_icon+' class="glyphicon glyphicon-remove cus-close-icon cursor" style="display: none" onclick="closeTabContro()"></span>' +
        '</a></li>');
    //移除内容选中
    $('#right_top_tab_content > div').each(function() {
        $(this).removeClass('active');
    })
    //追加内容
    $('#right_top_tab_content > div:last').after('<div class="tab-pane active" id='+content+'>' +
        '<div class="mrg div-mrg"><input id='+interface_name+' class="form-control cus-interface-name cus-input" type="text" placeholder="接口名称" />' +
        // '<span id='+interface_url+'></span>' +
        // '<span id='+interface_desc_icon+' class="glyphicon glyphicon-triangle-left cursor" onclick="interfaceDescription()"></span>' +
        '<input id='+interface_desc+' type="text" style="margin-top: 10px" class="form-control cus-interface-description cus-input" placeholder="接口描述"/>' +
        '</div><ul class="nav nav-list"><li class="divider"></li></ul><div class="mrg div-mrg">' +
        '<div class="row"><div class="col-md-11"><div class="input-group"><div id='+content_select+' class="input-group-btn">' +
        '<button type="button" class="btn btn-default dropdown-toggle" style="width: 100px" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
        '<span>GET</span><span style="text-align: center; float: right"><span class="caret"></span></span></button><ul class="dropdown-menu">' +
        '<li onclick="changeSelect(this)"><a href="#">GET</a></li>' +
        '<li onclick="changeSelect(this)"><a href="#">POST</a></li>' +
        '<li onclick="changeSelect(this)"><a href="#">PUT</a></li>' +
        '<li onclick="changeSelect(this)"><a href="#">DELETE</a></li></ul></div>' +
        '<input id='+url_text+' type="text" class="form-control" aria-label="..." onkeydown="urlTextUpdate()" onkeyup="urlTextUpdate()">' +
        '</div></div>' +
        '<div class="col-md-1">' +
        '<button class="btn cus-btn" data-toggle="modal" data-target="#selectClassModal" onclick="saveInterfaceGetClass()">保存</button>\</div></div></div><div class="mrg cus-right-bottom-input">'+
        '<ul id='+right_bottom_tab+' class="nav nav-pills cus-nav-right-pills mrg">' +
        '<li role="presentation" class="active"><a href='+href_params+' data-toggle="tab">params</a></li>' +
        '<li role="presentation"><a href='+href_headers+' data-toggle="tab">headers</a></li>' +
        '<li role="presentation"><a href='+href_body+' data-toggle="tab">body</a></li>' +
        '<li role="presentation"><a href='+href_response+' data-toggle="tab">response</a></li></ul>'+
        '<div id='+right_bottom_tab_content+' class="tab-content mrg">' +
        '<div class="tab-pane active div-mrg" id='+id_params+'>' +
        '<table class="table table-condensed"><caption>Query Params</caption><tbody>' +
        '<tr flag="true"><td><input class="form-control" type="text" name="code" placeholder="参数名称" onkeydown="addTrTd(this)"/></td>' +
        '<td><input class="form-control type" type="text" name="type" placeholder="数据类型" onkeydown="addTrTd(this)"/></td>' +
        '<td><input class="form-control" type="text" name="description" placeholder="属性描述" onkeydown="addTrTd(this)"/></td>' +
        '<td><div class="input-group"><input class="form-control" type="text" name="necessary" disabled placeholder="是否必填" onkeydown="addTrTd(this)"/>' +
        '<div class="input-group-btn"><button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" tabindex="-1">' +
        '<span class="caret dropdown-toggle" data-toggle="dropdown"></span></button><ul class="dropdown-menu pull-right">\n' +
        '<li onclick="bottomValChangeSelect(this)"><a href="#">是</a></li><li onclick="bottomValChangeSelect(this)"><a href="#">否</a></li>' +
        '</ul></div></div></td><td><input class="form-control" type="text" name="maxLength" placeholder="最大长度" onkeydown="addTrTd(this)"/></td>' +
        '<td><input class="form-control" type="text" name="remark" placeholder="备注" onkeydown="addTrTd(this)"/></td></tr>' +
        '</tbody></table></div>' +
        '<div class="tab-pane div-mrg" id='+id_headers+'>' +
        '<table class="table table-condensed"><caption>Headers</caption><tbody>' +
        '<tr flag="true">\n' +
        '    <td><input class="form-control" type="text" name="code" placeholder="参数名称" onkeydown="addTrTd(this)"/></td>\n' +
        '    <td><input class="form-control type" type="text" name="type" placeholder="数据类型" onkeydown="addTrTd(this)"/></td>\n' +
        '    <td><input class="form-control" type="text" name="description" placeholder="属性描述" onkeydown="addTrTd(this)"/></td>\n' +
        '    <td>\n' +
        '        <div class="input-group">\n' +
        '            <input class="form-control" type="text" name="necessary" disabled placeholder="是否必填" onkeydown="addTrTd(this)"/>\n' +
        '            <div class="input-group-btn">\n' +
        '                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" tabindex="-1">\n' +
        '                    <span class="caret dropdown-toggle" data-toggle="dropdown"></span>\n' +
        '                </button>\n' +
        '                <ul class="dropdown-menu pull-right">\n' +
        '                    <li onclick="bottomValChangeSelect(this)"><a href="#">是</a></li>\n' +
        '                    <li onclick="bottomValChangeSelect(this)"><a href="#">否</a></li>\n' +
        '                </ul>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </td>\n' +
        '    <td><input class="form-control" type="text" name="maxLength" placeholder="最大长度" onkeydown="addTrTd(this)"/></td>\n' +
        '    <td><input class="form-control" type="text" name="remark" placeholder="备注" onkeydown="addTrTd(this)"/></td>\n' +
        '</tr>' +
        '</tbody></table></div>' +
        '<div class="tab-pane div-mrg" id='+id_body+'>' +
        '<table class="table table-condensed"><caption>Body</caption><tbody>' +
        '<tr flag="true">\n' +
        '    <td><input class="form-control" type="text" name="code" disabled placeholder="参数名称" onkeydown="addBodyTrTd(this)"/></td>\n' +
        '    <td><input class="form-control type" type="text" name="type" placeholder="数据类型" onkeydown="addBodyTrTd(this)"/></td>\n' +
        '    <td><input class="form-control" type="text" name="description" placeholder="属性描述" onkeydown="addBodyTrTd(this)"/></td>\n' +
        '    <td>\n' +
        '        <div class="input-group">\n' +
        '            <input class="form-control" type="text" name="necessary" disabled placeholder="是否必填" onkeydown="addBodyTrTd(this)"/>\n' +
        '            <div class="input-group-btn">\n' +
        '                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" tabindex="-1">\n' +
        '                    <span class="caret dropdown-toggle" data-toggle="dropdown"></span>\n' +
        '                </button>\n' +
        '                <ul class="dropdown-menu pull-right">\n' +
        '                    <li onclick="bottomValChangeSelect(this)"><a href="#">是</a></li>\n' +
        '                    <li onclick="bottomValChangeSelect(this)"><a href="#">否</a></li>\n' +
        '                </ul>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '   </td>\n' +
        '    <td><input class="form-control" type="text" name="maxLength" placeholder="最大长度" onkeydown="addBodyTrTd(this)"/></td>\n' +
        '    <td><input class="form-control" type="text" name="remark" placeholder="备注" onkeydown="addBodyTrTd(this)"/></td>\n' +
        '    <td><input class="form-control" type="text" name="parent" disabled placeholder="父级" onkeydown="addBodyTrTd(this)"/></td>\n' +
        '</tr>' +
        '</tbody></table>' +
        '<div id='+body_json_editor+' class="cus-jsoneditor"></div>' +
        '</div><div class="tab-pane div-mrg" id='+id_response+'>' +
        '<table class="table table-condensed"><caption>Response</caption><tbody>' +
        '<tr flag="true">\n' +
        '    <td><input class="form-control" type="text" name="code" disabled placeholder="参数名称" onkeydown="addBodyTrTd(this)"/></td>\n' +
        '    <td><input class="form-control type" type="text" name="type" placeholder="数据类型" onkeydown="addBodyTrTd(this)"/></td>\n' +
        '    <td><input class="form-control" type="text" name="description" placeholder="属性描述" onkeydown="addBodyTrTd(this)"/></td>\n' +
        '    <td>\n' +
        '        <div class="input-group">\n' +
        '            <input class="form-control" type="text" name="necessary" disabled placeholder="是否必填" onkeydown="addBodyTrTd(this)"/>\n' +
        '            <div class="input-group-btn">\n' +
        '                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" tabindex="-1">\n' +
        '                    <span class="caret dropdown-toggle" data-toggle="dropdown"></span>\n' +
        '                </button>\n' +
        '                <ul class="dropdown-menu pull-right">\n' +
        '                    <li onclick="bottomValChangeSelect(this)"><a href="#">是</a></li>\n' +
        '                    <li onclick="bottomValChangeSelect(this)"><a href="#">否</a></li>\n' +
        '                </ul>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </td>\n' +
        '    <td><input class="form-control" type="text" name="maxLength" placeholder="最大长度" onkeydown="addBodyTrTd(this)"/></td>\n' +
        '    <td><input class="form-control" type="text" name="remark" placeholder="备注" onkeydown="addBodyTrTd(this)"/></td>\n' +
        '    <td><input class="form-control" type="text" name="parent" disabled placeholder="父级" onkeydown="addBodyTrTd(this)"/></td>\n' +
        '</tr>' +
        '</tbody></table>' +
        '<div id='+response_json_editor+' class="cus-jsoneditor"></div></div></div></div></div>');
    //判断是否显示关闭图标
    showOrHide();
    //初始化json编辑器
    initJsonEditor(ran);
    //初始化AutoComplete
    initAutoComplete(availableTags);
}
/**
 * 请求
 */
function requestApi(url,type,data){
    $.ajax({
        url:url,
        type:type,
        data:JSON.stringify(data),
        dataType:'json',
        contentType: 'application/json',
        success:function(resp){
            console.log(resp);
            $('#selectClassModal').modal('hide');
        },
        error:function(resp){
            console.log(resp);
        }
    })
}
/**
 * 请求树数据
 */
function requestGetTree(url, type, selectTree){
    $.ajax({
        url:url,
        type:type,
        contentType: 'application/json',
        success:function(resp){
            if(resp.data != null){
                if(selectTree == ''){ // 构建可选择文件夹
                    construnctionSelectClass(resp.data);
                }else{ //构建类树
                    if(selectTree == 'his'){
                        constructHistoryTree(resp.data, selectTree);
                    }else{
                        constructCollectionTree(resp.data, selectTree);
                    }
                }
            }
        },
        error:function(resp){
            console.log(resp);
        }
    })
}
/**
 * 请求历史记录树子集数据
 */
let requestGetTreeHistoryChildNode = (url, type, index, selectTree) => {
    $.ajax({
        url:url,
        type:type,
        contentType: 'application/json',
        success:function(resp){
            constructHitoryTreeChildNode(resp.data, index, selectTree);
        },
        error:function(resp){
            console.log(resp);
        }
    })
}
/**
 * 构建历史记录树数据
 */
let constructHistoryTree = (data, selectTree) => {
    let treeData = [];
    for(let i = 0; i < data.length; i++){
        let val = data[i];
        let parent = {
            index: i,
            text: val,
            icon:'glyphicon glyphicon-folder-close',
            selectable:false,
            nodes:[]
        }
        treeData.push(parent);
    }
    $('#'+selectTree).treeview({
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
            requestGetTreeHistoryChildNode('/ih/rest/apiService/v1/interface/history?date='+data.text,'GET', data.index, selectTree);
        },
    });
}

/**
 * 构建历史记录树子集数据
 */
function constructHitoryTreeChildNode(data, index, selectTree){
    $("#"+selectTree).treeview("deleteNode", [index, { node: {}}])
    for(let i = 0; i < data.length; i++){
        let obj = data[i];
        let node = {
            id: obj.interfaceId,
            text: method[obj.type] + obj.url,
            selectable:false
        };
        $("#"+selectTree).treeview("addNode", [index, { node: node }])
    }
}
/**
 * 请求接口数据
 */
let requestInterfaceValue = (url, type) => {
    $.ajax({
        url:url,
        type:type,
        contentType: 'application/json',
        success:function(resp){
            construnctionInterfaceValue(resp.data);
        },
        error:function(resp){
            console.log(resp);
        }
    })
}
/**
 * 接口数据填充
 * @param data
 */
let construnctionInterfaceValue = (data) => {
    let {name, interfaceId, code, url, type, description, queryVO, headerVO, bodyVO, responseVO} = data;
    //获得id后缀
    let suffix = getSelectSuffix();
    $('#interface_id_'+suffix).val(interfaceId); //要修改接口的ID
    $('#href_content_'+suffix+' nobr').text(name); //tab名称
    $('#interface_name_'+suffix).val(name); //接口名称
    $('#interface_url_'+suffix).text(code); //code
    $('#url_text_'+suffix).val(url); //url
    $('#content_select_'+suffix).children().children().first().text(type); //请求类型
    $('#interface_desc_'+suffix).val(description); //接口描述
    let obj = {'params':queryVO, 'headers':headerVO, 'body':bodyVO, 'response':responseVO};
    for(let key in obj){
        constructRightBottomData(key, obj[key], suffix);
    }
}
let constructRightBottomData = (head, vo, suffix) => {
    let trObjs = $('#'+head+'_'+suffix+' tr');
    let params = vo.params;
    for(let i in params){
        $(trObjs).each(function(){$(this).attr('flag', 'false')}); // 将所有输入框置为不可自动增加
        //追加新的输入框
        if(head == 'params' || head == 'headers'){
            $(trObjs).last().after(parHeaderTr);
        }else{
            $(trObjs).last().after(bodResponseTr);
        }
        let trObj = trObjs.eq(i);
        let obj = params[i];
        for(let key in obj){
            $(trObj).find('input[name='+key+']').val(obj[key]);
        }
        trObjs = $('#'+head+'_'+suffix+' tr');
    }
    $(trObjs).parent().children('tr:last-child').attr('flag', 'true') // 将最后输入框置为可自动增加
    if(vo.hasOwnProperty('example')){
        let example = vo.example;
        if(example != null){
            eval(head+'_editor_'+suffix).setText(example);
        }
    }
    //初始化AutoComplete
    initAutoComplete(availableTags);
}
/**
 * 获取数据
 * @param head
 * @param suffix
 * @returns {Array}
 */
function getVal(head, suffix){
    let listVO = [];
    $('#'+head+'_'+suffix+' tr').each(function(){
        let val = {};
        $(this).find('td').find('input').each(function(){
            let inpVal = $(this).val();
            if(inpVal == '' || inpVal == 'undefind'){
                return true
            }
            val[$(this).attr('name')] = $(this).val();
        })
        if(JSON.stringify(val) == '{}'){
            return true;
        }
        if(val.necessary != ''){
            val.necessary = val.necessary == '是'?'Y':'N';
        }
        listVO.push(val);
    })
    return listVO;
}

/**
 * 判断是编辑还是保存
 */
let saveUpdateControll = () => {
    //获得id后缀
    let suffix = getSelectSuffix();
    let interface_id = $('#interface_id_'+suffix).val();
    if(interface_id != ''){
        //修改
        updateInterface(interface_id);
    }else{
        //保存
        saveInterface();
    }
}
/**
 * 构建保存/编辑数据
 */
let constructSaveOrUpdateData = () => {
    //获得id后缀
    let suffix = getSelectSuffix();
    let data = {};
    data.interfaceVO = {
        "classId":$('#select_class_id').val(),
        "name": $('#interface_name_'+suffix).val(),
        "code":$('#interface_url_'+suffix).text(),
        "url":$('#url_text_'+suffix).val(),
        "type": $('#content_select_'+suffix).children().children().first().text(),
        "description": $('#interface_desc_'+suffix).val()
    }
    data.queryVO = {
        "params": getVal('params', suffix),
    }
    data.headerVO = {
        "params": getVal('headers', suffix),
    }
    data.bodyVO = {
        "params": getVal('body', suffix),
        "example": eval('body_editor_'+suffix).getText()
    }
    data.responseVO = {
        "params": getVal('response', suffix),
        "example": eval('response_editor_'+suffix).getText()
    }
    return data
}
/**
 * 保存接口数据
 */
function saveInterface(){
    //构建要保存的数据
    let data = constructSaveOrUpdateData();
    console.log(data);
    requestApi('/ih/rest/apiService/v1/interface','POST', data);
}
/**
 * 编辑接口
 */
let updateInterface = (interface_id) => {
    //构建要保存的数据
    let data = constructSaveOrUpdateData();
    console.log(data);
    requestApi('/ih/rest/apiService/v1/interface/'+interface_id,'PUT', data);
}

/**
 * 查询历史记录时间
 */
let getHistory = () => {
    requestGetTree('/ih/rest/apiService/v1/interface/history/date','GET','his');
}
/**
 * 保存Model框查询类数据
 */
let saveInterfaceGetClass = () => {
    requestGetTree('/ih/rest/apiService/v1/classes','GET','');
}


/**
 * 清空目录modal并隐藏
 */
let clearDirectoryModal = () => {
    $('#directory_input div input').each(function() {
        $(this).val('');
    });
    $('#directoryModal').modal('hide');
    //隐藏切换按钮
    $('#directoryModal .modal-header .btn').css('display','none');
    $('#classModal .modal-header .btn').css('display','none');
}
/**
 * 清空类Modal并隐藏
 */
let clearClassModal = () => {
    $('#class_input div input').each(function() {
        $(this).val('');
    });
    $('#classModal').modal('hide');
    //隐藏切换按钮
    $('#directoryModal .modal-header .btn').css('display','none');
    $('#classModal .modal-header .btn').css('display','none');
}
/**
 * Modal切换
 */
let modalSwitch = (that) => {
    let type = $(that).attr('cus-type');
    let arr = type.split(',');
    $('#'+arr[1]+'Modal').modal('hide');
    $('#'+arr[0]+'Modal .modal-header .btn').css('display','inline');
    $('#'+arr[0]+'Modal').modal('show');
    $('#'+arr[0]+'_parent_id').val($('#'+arr[1]+'_parent_id').val());
}
/**
 * 请求
 * @param url
 * @param type
 * @param data
 */
let request = (url, type, data) => {
    let response = null;
    $.ajax({
        url:url,
        type:type,
        data: data,
        async: false,
        contentType: 'application/json',
        success:function(resp){
            if(resp.data != null){
                response = resp.data;
            }
        },
        error:function(resp){
            console.log(resp);
        }
    })
    return response;
}
/**
 * 请求
 * @param url
 * @param type
 * @param data
 */
let requestString = (url, type, data) => {
    $.ajax({
        url:url,
        type:type,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success:function(resp){
            console.log(resp)
        },
        error:function(resp){
            console.log(resp);
        }
    })
}
/**
 * 保存OR修改目录
 */
let saveOrUpdateDirectory = () => {
    let data = {};
    $('#directory_input div input').each(function() {
        data[$(this).attr('name')] = $(this).val();
    });
    if(data.directoryId != ''){
        //修改目录
        requestString('/ih/rest/apiService/v1/directory/'+data.directoryId,'PUT', data);
    }else{
        //添加目录
        let url = '/ih/rest/apiService/v1/directory?name='+data.name;
        if(data.parentDirectoryId != ''){
            url += '&parentId='+data.parentDirectoryId;
        }
        request(url,'POST', {});
    }
    //清空modal并隐藏
    clearDirectoryModal();
}
/**
 * 保存Or修改类名
 */
function saveOrUpdateClass(){
    let data = {};
    $('#class_input div input').each(function() {
        data[$(this).attr('name')] = $(this).val();
    });
    console.log(data);
    if(data.classId != ''){
        //修改类
        request('/ih/rest/apiService/v1/class/'+data.classId+'?className='+data.name,'PUT', {});
    }else{
        //添加类
        requestString('/ih/rest/apiService/v1/class','POST', data);
    }
    //清空modal并隐藏
    clearClassModal();
}
/**
 * 删除目录Or类Or接口
 */
let del = (id, type) => {
    let url;
    if(type == 'directory'){
        url = '/ih/rest/apiService/v1/directory/'+id;
    }else if(type == 'class'){
        url = '/ih/rest/apiService/v1/class/'+id;
    }else if(type == 'interface'){
        url = '/ih/rest/apiService/v1/interface/'+id;
    }
    requestString(url,'DELETE',{});
    //阻止冒泡事件
    window.event? window.event.cancelBubble = true : e.stopPropagation();
}
/**
 * 编辑
 */
let edit = (id, type) => {
    let url;
    if(type == 'directory'){
        url = '/ih/rest/apiService/v1/directory/'+id;
    }else if(type == 'class'){
        url = '/ih/rest/apiService/v1/class/'+id;
    }
    let response = request(url,'GET',{});
    for(let key in response){
        $('#'+type+'_input div').find('input[name='+key+']').val(response[key]);
    }
    $('#'+type+'Modal').modal('show');
    //阻止冒泡事件
    window.event? window.event.cancelBubble = true : e.stopPropagation();
}
/**
 * 返回上级
 * @type {Array}
 */
let backSuperiorData = [];
let backSuperior = () => {
    let val = backSuperiorData.pop();
    if(val == 'null' || val == undefined){
        requestDirectory();
    }else{
        requestChild(val,'');
    }
}
/**
 * 组装面包屑
 */
let constructBreadCrumb = (breadCrumb) => {
    $('#breadcrumb').empty();
    $('#breadcrumb').append(breadCrumb);
}
/**
 * 请求目录
 */
let requestDirectory = () => {
    let directory = request('/ih/rest/apiService/v1/directory','GET',{});
    //面包屑
    constructBreadCrumb(directory.breadCrumbs);
    constructCollectionTree(directory);
}
/**
 * 请求子集
 */
let requestChild = (directoryId, parentDirectoryId) => {
    if(parentDirectoryId != ''){
        backSuperiorData.push(parentDirectoryId); //记录上级
    }
    let child = request('/ih/rest/apiService/v1/directory','GET',{parentId:directoryId});
    //面包屑
    constructBreadCrumb(child.breadCrumbs);
    // if(child.class.length <= 0 && child.directory.length <= 0){return false;};
    constructCollectionTree(child);
    //阻止冒泡事件
    window.event? window.event.cancelBubble = true : e.stopPropagation();
}
/**
 * 构造数据
 * @param data
 */
let constructCollectionTree = (data) => {
    let treeData = [];
    let clazz = data.class;
    let directory = data.directory;
    if(clazz.length > 0){
        for(let i = 0; i < clazz.length; i++){
            let obj = clazz[i];
            let parent = {
                index: i,
                text: '<span class="cursor" title='+obj.url+'>'+obj.code+'</span>' +
                    '<br>' +
                    '<span class="glyphicon glyphicon-edit cursor" style="float: right; margin-right: 10px;" onclick="edit(\''+obj.classId+'\',\'class\')"></span>' +
                    '<span class="glyphicon glyphicon-trash cursor" style="float: right; margin-right: 10px;" onclick="del(\''+obj.classId+'\',\'class\')"></span>',
                id: obj.classId,
                icon:'glyphicon glyphicon-copyright-mark',
                selectable:false,
                nodes: []
            }
            treeData.push(parent);
        }
    }
    if(directory.length > 0){
        for(let i in directory){
            let obj = directory[i];
            let node = {
                text: '<span class="cursor" onclick="requestChild(\''+obj.directoryId+'\',\''+obj.parentDirectoryId+'\')" title="'+obj.name+'">'+obj.name+'</span>' +
                    '<br>' +
                    '<span class="glyphicon glyphicon-plus cursor" style="float: right; margin-right: 10px;" onclick="$(\'#class_parent_id\').val(\''+obj.directoryId+'\');$(\'#classModal .modal-header .btn\').css(\'display\',\'inline\');$(\'#classModal\').modal(\'show\')"></span>' +
                    '<span class="glyphicon glyphicon-edit cursor" style="float: right; margin-right: 10px;" onclick="edit(\''+obj.directoryId+'\',\'directory\')"></span>' +
                    '<span class="glyphicon glyphicon-trash cursor" style="float: right; margin-right: 10px;" onclick="del(\''+obj.directoryId+'\',\'directory\')"></span>',
                id: obj.directoryId,
                icon:'glyphicon glyphicon-folder-close',
                selectable:false,
            }
            treeData.push(node);
        }
    }
    $('#collection').treeview({
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
            //折叠所有
            // $('#'+selectTree).treeview('collapseAll', { silent:true });
            let interface = request('/ih/rest/apiService/v1/interfaces','GET',{classId:data.id});
            constructCollectionTreeChildNode(interface, data.index);
        },
        onNodeChecked: function (event,data) {
            console.log(data);
        },
        onNodeSelected: function (event, data) {
            console.log(data);
            //判断是否添加tab
            if(whetherAddTab(data.id)){
                //添加tab
                tabControl();
                //请求tab数据
                requestInterfaceValue('/ih/rest/apiService/v1/interface/'+data.id,'GET');
            };
        },
    });
}
/**
 * 判断是否添加tab
 */
let whetherAddTab = (name) => {
    let flag = true;
    $('#right_top_tab li a input').each(function(){
        let val = $(this).val();
        if(name == val){
            flag = false;
            return false;
        }
    })
    return flag;
}
/**
 * 构建接口数据
 */
function constructCollectionTreeChildNode(data, index){
    $("#collection").treeview("deleteNode", [index, { node: {}}])
    for(let i in data){
        let obj = data[i];
        let node = {
            text: method[obj.type] + '<span class="cursor" title="'+obj.url+'">'+obj.url+'</span>' +
                '<br>' +
                '<span class="glyphicon glyphicon-trash cursor" style="float: right; margin-right: 10px" onclick="del(\''+obj.interfaceId+'\',\'interface\')"></span>',
            id: obj.interfaceId,
            name: obj.name,
        };
        $("#collection").treeview("addNode", [index, { node: node }])
    }
}