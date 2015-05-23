/**
 * 
 */
// js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath() {
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}

// 将表单序列化为对象, 供 easyui datagrid load 方法的参数使用
var serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
}

/**
 * 获取 url 参数
 */
// 获取地址栏的参数数组
function getUrlParams() {
	var search = window.location.search;
	// 写入数据字典
	var tmparray = search.substr(1, search.length).split("&");
	var paramsArray = new Array;
	if (tmparray != null) {
		for (var i = 0; i < tmparray.length; i++) {
			var reg = /[=|^==]/; // 用=进行拆分，但不包括==
			var set1 = tmparray[i].replace(reg, '&');
			var tmpStr2 = set1.split('&');
			var array = new Array;
			array[tmpStr2[0]] = tmpStr2[1];
			paramsArray.push(array);
		}
	}
	// 将参数数组进行返回
	return paramsArray;
}
// 根据参数名称获取参数值
function getParamValue(name) {
	var paramsArray = getUrlParams();
	if (paramsArray != null) {
		for (var i = 0; i < paramsArray.length; i++) {
			for ( var j in paramsArray[i]) {
				if (j == name) {
					return paramsArray[i][j];
				}
			}
		}
	}
	return null;
}
