<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Candidate List Page</title>
<script src="${pageContext.request.contextPath }/js/easyui/jquery.min.js"></script>
<script src="${pageContext.request.contextPath }/js/easyui/jquery.min.js"></script>
<script src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css" />
<script src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/codergege.js"></script>
<link href="${pageContext.request.contextPath }/css/codergege.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	var centerTabs;
	var dgTrainingCandidate;
	var tid;
	$(function() {
		centerTabs = $('#centerTabs');
		tid = getParamValue("tid");
		console.log(tid);
		initTabTrainingCandidate();
		initTrainingInfo();
	});
	
	function initTrainingInfo(){
		dgTraining = $('#dgTraining').datagrid({
			url: getRootPath + "/training-list?tid=" + tid,
			fit: false,
			fitColumns: true,
			autoRowHeight: true,
			striped: true,
			nowrap: false,
			pagination: false,
			checkOnSelect: true,
			columns: [[{
				field: 'tid',
				checkbox: true
			},{
				field: 'name',
				title: '培训班次名称',
				width: 100,
				sortable: true
			},{
				field: 'content',
				title: '培训内容',
				width: 200
			},{
				field: 'level',
				title: '培训级别',
				width: 80,
				sortable: true
			},{
				field: 'trainingTime',
				title: '培训时间',
				width: 150
			},{
				field: 'location',
				title: '培训地点',
				width: 100 
			},{
				field: 'creditHour',
				title: '培训学时',
				width: 40,
				sortable: true
			},{
				field: 'trainingLx',
				title: '培训类型',
				width: 60,
				sortable: false
			},{
				field: 'trainingOrg',
				title: '培训机构',
				width: 60
			},{
				field: 'credit',
				title: '学分',
				width: 20
			}]]
		});
	}
	function initTabTrainingCandidate(){
		dgCandidate = $('#dgCandidate').datagrid({
			url : getRootPath() + "/candidate-list?tid=" + tid,
			fit : true,
			fitColumns : true,
			autoRowHeight : true,
			striped : true,
			nowrap : false,
			//remoteSort: true,
			//sortable: true,
			//分页
			pagination : true,
			//pageSize: 10,
			//pageList: [10, 20, 30, 40, 50]

			//rownumbers: true,
			checkOnSelect : true,
			columns : [ [ {
				field : "cid",
				checkbox : true
			}, {
				field : 'name',
				title : '姓名',
				//fixed : true,
				width : 50,
				sortable: true,
			}, {
				field : 'gender',
				title : '性别',
				//fixed : true,
				width : 20,
				//sortable: true,
			}, {
				field : 'unit',
				title : '单位',
				//fixed : true,
				width : 100,
				//sortable: true,
			}, {
				field: 'post',
				title: '职务',
				width: 100,
				//sortable: true,
			}, {
				field : 'birthday',
				title : '出生年月',
				//fixed : true,
				width : 100,
				//sortable: true,
			}, {
				field: 'degree',
				title: '最高学历',
				width: 40,
				//sortable: true,
			}, {
				field: 'operatingTime',
				title: '参加工作时间',
				width: 100,
				//sortable: true,
			}, {
				field: 'bzlx',
				title: '编制类型',
				width: 80,
				//sortable: true,
			}, {
				field : 'state',
				title : '状态',
				//fixed : true,
				width : 40,
				//sortable: true,
			}, {
				field : 'credit',
				title : '学分总计',
				width : 40,
				//sortable: true,
			} ] ],
			toolbar : '#tbCandidate'
		});
	}
	
	

	var url;
	
	function addCandidate() {
		$('#dlgCuCandidate').dialog('open').dialog('setTitle', '关联学员');
		$('#editFormCandidate').form('clear');
		//alert(getParamValue('tid'));
		url = getRootPath() + '/candidate-rel';
	}
	function deleteCandidate() {
		var rows = $('#dgCandidate').datagrid('getSelections');
		if (rows.length == 0) {
			alert("删除操作时, 至少要选择一行! ");
			return false;
		}
		var cids = "";
		for (var i = 0; i < rows.length; i++) {
			cids += rows[i].cid;
			if (i < (rows.length - 1)) {
				cids += ", ";
			} else {
				break;
			}
		}
		$.messager.confirm('Confirm', '确定要取消关联选中的学员吗?', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : getRootPath() + "/candidate-unrel",
					data : {
						cids : cids,
						tid: tid
					},
					dataType : 'json',
					success : function(msg) {
						if (msg && msg.success == "true") {
							dgCandidate.datagrid('reload');
							$.messager.show({
								title : "提示",
								msg : msg.message
							});
						} else {
							$.messager.alert('Warning', msg.message);
						}
					}
				});
			}
		});
	}
	
	function saveCandidate() {
		$('#editFormCandidate').form('submit', {
			url : url,
			onSubmit : function(param) {
				param.tid = getParamValue("tid");
				return $(this).form('validate');
			},
			success : function(result) {
				console.log(url);
				console.log(result);
				var result = $.parseJSON(result);
				if (result.success == null || result.success == "false") {
					$.messager.show({
						title : 'Error',
						msg : result.message
					});
				} else {
					$('#dlgCuCandidate').dialog('close'); // close the dialog
					dgCandidate.datagrid('reload'); // reload the user data
					$.messager.show({
						title : '提示',
						msg : result.message
					});
				}
			}
		});
	}
	/* candidate crud end */
	
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div id="centerTabs" class="easyui-tabs" fit="true" border="false">
			<div title="项目-学员 关联列表" closable="false">
				<table id="dgTraining"></table>
				<table id="dgCandidate"></table>
			</div>
		</div>
	</div>

	<!-- *************************** candidate part *******************************  -->
	<!-- candidates toolbar start  -->
	<div id="tbCandidate">
		<div>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addCandidate()"style="margin-left: 20px;">关联学员</a> 
			|导出 学员-培训 关联信息 |
			<s:a action="training-relExport" >
				<s:param name="format">xls</s:param> 
				<s:param name="tid"><s:property value="#parameters.tid[0]"/></s:param> 
				xls格式(适用excel 2003 及之前版本)
			</s:a>|
			<s:a action="training-relExport" >
				<s:param name="format">xlsx</s:param> 
				<s:param name="tid"><s:property value="#parameters.tid[0]"/></s:param> 
				xlsx格式(适用excel 2007 及之后版本)
			</s:a>|
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteCandidate()" style="float: right;">取消关联</a> 
		</div>
	</div>
	<!-- candidate toolbar end -->
	<!-- candidate crud dlg start -->
	<div id="dlgCuCandidate" class="easyui-dialog" style="width: 400px; height: 500px; padding: 10px 20px" closed="true" buttons="#dlgBtnsCandidate">
		<div class="ftitle">学员信息</div>
		<form id="editFormCandidate" method="post">
			<div class="fitem">
				<label>姓名:</label> <input name="name" class="easyui-validatebox" required="true">
			</div>
		</form>
	</div>
	<div id="dlgBtnsCandidate">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveCandidate()">Save</a> <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgCuCandidate').dialog('close')">Cancel</a>
	</div>
	<!-- candidate crud dlg end -->
	<!-- *************************** candidate part end *******************************  -->
</body>
</html>