//防止编辑清空
let bodyResponseData = {};
/**
 * 获得table所有数据并构造
 */
let getTableDataConstruct = (type, suffix) => {
    let bodyResponseObj = {}, params = {}, arrays = [];
    $('#'+type+'_'+suffix+' tr').each(function(){
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
        val.necessary = val.necessary == undefined || val.necessary == '' ?'否':val.necessary;
        arrays.push(val);
    });
    params.params = arrays;
    bodyResponseObj[type] = params;

    bodyResponseData['bodyResponse'+suffix] = bodyResponseObj;
    console.log(bodyResponseObj);
}
/**
 * 解析前准备工作
 */
let preparatoryWork = (suffix, json, parent, type) => {
    let obj = $('#'+type+'_div_'+suffix);
    //删除input
    $(obj).empty();
    analysisJson(suffix, json, parent, type, '');
}
/**
 * 递归构造Body,Response 数据
 * @param suffix
 * @param json
 * @param parent
 * @param type
 */
let analysisJson = (suffix, json, parent, type, trace) => {
    let obj = $('#'+type+'_div_'+suffix);
    let str = '';
    if(parent == ''){
        if(type == 'body'){
            str = '<table class="table table-condensed"><caption>Body</caption><tbody>';
        }else{
            str = '<table class="table table-condensed"><caption>Response</caption><tbody>';
        }
    }else{
        str = '<table class="table table-condensed"><caption>'+parent+'</caption><tbody>';
    };
    let i = 0;
    for(name in json){
        str += '<tr flag="false"><td><input class="form-control" type="text" name="code" disabled placeholder="参数名称" autocomplete="off"  value="'+name+'"/></td>' +
            '      <td><input class="form-control type" type="text" name="type" placeholder="数据类型" autocomplete="off" /></td>' +
            '      <td><input class="form-control" type="text" name="description" placeholder="属性描述" autocomplete="off" /></td><td>' +
            '         <div class="input-group"><div class="input-group-btn">' +
            '            <span class="dropdown-toggle" data-toggle="dropdown" tabindex="-1">' +
            '                <input class="form-control" type="text" name="necessary" disabled placeholder="是否必填" autocomplete="off" />' +
            '            </span>' +
            '            <ul class="dropdown-menu pull-right">' +
            '                <li onclick="bottomValChangeSelect(this)"><a href="#">是</a></li>' +
            '                <li onclick="bottomValChangeSelect(this)"><a href="#">否</a></li>' +
            '            </ul></div></div>\</td>' +
            '        <td><input class="form-control" type="text" name="maxLength" placeholder="最大长度" autocomplete="off" /></td>\n' +
            '      <td><input class="form-control" type="text" name="remark" placeholder="备注" autocomplete="off" /></td>\n' +
            '<td><input class="form-control" type="hidden" name="parent" disabled placeholder="父级" autocomplete="off"  value="'+parent+'"/></td>' +
            '<td><input class="form-control" type="text" name="trace" disabled placeholder="链路" autocomplete="off"  value="'+trace+'"/></td></tr>';
        i++;
        if(json[name] instanceof Array){
            analysisJson(suffix, json[name], name, type, trace+'/'+name);
        }else if(json[name] instanceof Object){
            analysisJson(suffix, json[name], name, type, trace+'/'+name);
        }
    }
    str += '</tbody></table>';
    if(i != 0){
        $(obj).prepend(str);
    }
}
/**
 * 填充body/response input 数据
 */
let constructBodyResponseData = (suffix) => {
    if(bodyResponseData.hasOwnProperty('bodyResponse'+suffix)){
        let bodyResponseObj = bodyResponseData['bodyResponse'+suffix];
        for(let key in bodyResponseObj){
            let arrays = bodyResponseObj[key].params;
            let obj = $('#'+key+'_div_'+suffix+' tr');
            $(obj).each(function(){
                // console.log($(this).find('input[name=code]').val());
                let codeVal = $(this).find('input[name=code]').val();
                // let parentVal = $(this).find('input[name=parent]').val();
                let traceVal = $(this).find('input[name=trace]').val();
                // $(this).find('input').each(function(){
                for(let i in arrays){
                    let obj = arrays[i];
                    if(obj.trace == null){
                        obj.trace = '';
                    }
                    if(codeVal == obj.code && traceVal == obj.trace){
                        for(let key in obj){
                            $(this).find('input[name='+key+']').val(obj[key]);
                        }
                    };
                }
            })
        }
    }
}