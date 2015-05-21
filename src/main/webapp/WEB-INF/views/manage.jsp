<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	var dgCandidate;
	var centerTabs;
	$(function() {
		centerTabs = $('#centerTabs');
		dgCandidate = $('#dgCandidate').datagrid({
			url : getRootPath() + "/candidate-list",
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

		// candidates datagrid end

	});
	/* ------------- tree nav part start -------------------------------*/
	function showTabCandidate(title) {
		if (centerTabs == null) {
			centerTabs = $('#centerTabs');
		}
		if (centerTabs.tabs('exists', title)) {
			centerTabs.tabs('select', title);
		}
	}
	function showTabTraining(title) {
		if (centerTabs == null) {
			centerTabs = $('#centerTabs');
		}
		if (centerTabs.tabs('exists', title)) {
			centerTabs.tabs('select', title);
		}
	}
	function showTabExcel(title) {
		if (centerTabs == null) {
			centerTabs = $('#centerTabs');
		}
		if (centerTabs.tabs('exists', title)) {
			centerTabs.tabs('select', title);
		}
	}

	var url;
	/* candidate crud start */
	function searchCandidate() {
		var form = $('#searchFormCandidate').form();
		console.log(serializeObject(form));
		dgCandidate.datagrid('load', serializeObject(form));
	}
	function resetCandidate() {
		var form = $('#searchFormCandidate').form();
		dgCandidate.datagrid('load', {});
		form.find('input').val('');
		//$('#gender').prop('selectedIndex', 0);
		//$('#stat').prop('selectedIndex', 0);
	}
	function addCandidate() {
		$('#dlgCuCandidate').dialog('open').dialog('setTitle', '新增学员');
		$('#editFormCandidate').form('clear');
		url = getRootPath() + '/candidate-add';
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
		$.messager.confirm('Confirm', '确定要删除选中的数据吗?', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : getRootPath() + "/candidate-delete",
					data : {
						cids : cids
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
	function editCandidate() {
		var row = $('#dgCandidate').datagrid('getSelected');
		console.log(row);
		if (row) {
			$('#dlgCuCandidate').dialog('open').dialog('setTitle', '编辑学员信息');
			$('#editFormCandidate').form('load', row);
			url = getRootPath() + '/candidate-update';
		}
	}
	function saveCandidate() {
		$('#editFormCandidate').form('submit', {
			url : url,
			onSubmit : function() {
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
	<div data-options="region:'north'" style="height: 100px;"></div>
	<div data-options="region:'west',title:'管理菜单',split:true" style="width: 150px;">
		<ul>
			<span></span>
			<li class="tree"><span><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="showTabCandidate('学员列表')">学员列表</a></li>
			<li class="tree"><span><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="showTabTraining('培训项目列表')">培训项目列表</a></li>
			<li class="tree"><span><a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="showTabExcel('excel 导入')">excel 导入</a></li>
		</ul>
	</div>
	<div data-options="region:'center'">
		<div id="centerTabs" class="easyui-tabs" fit="true" border="false">
			<div title="学员列表" closable="false">
				<table id="dgCandidate"></table>
			</div>
			<div title="培训项目列表" closable="false">
				<table id="dgTraining"></table>
			</div>
			<div title="excel 导入" closable="false">
			</div>
		</div>
	</div>

	<!-- *************************** candidate part *******************************  -->
	<!-- candidates toolbar start  -->
	<div id="tbCandidate">

		<div>
			<form id="searchFormCandidate">
				姓名: <input id="candidateName" name="name" style="width: 80px; line-height: 20px; border: 1px solid #ccc" /> 
				性别: <select id="gender" class="easyui-combobox" name="gender" style="width: 80px;">
					<option value="">all</option>
					<option value="男">男</option>
					<option value="女">女</option>
				</select> 
				单位: <input id="unit" name="unit" style="width: 80px; line-height: 20px; border: 1px solid #ccc" />
				编制类型: <input id="bzlx" name="bzlx" style="width: 80px; line-height: 20px; border: 1px solid #ccc" /> 
				状态: <input id="state" name="state" style="width: 40px; line-height: 20px; border: 1px solid #ccc" />
				学分范围(包含):  
				<input id="credit1" name="credit1" style="width: 40px; line-height: 20px; border: 1px solid #ccc" />
				--
				<input id="credit2" name="credit2" style="width: 40px; line-height: 20px; border: 1px solid #ccc" />
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchCandidate()">Search</a> 
				<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetCandidate()">清空重置</a>
			</form>
		</div>
		<div>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addCandidate()">增加</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteCandidate()">删除</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="editCandidate()">修改</a>
		</div>
	</div>
	<!-- candidate toolbar end -->
	<!-- candidate crud dlg start -->
	<div id="dlgCuCandidate" class="easyui-dialog" style="width: 400px; height: 480px; padding: 10px 20px" closed="true" buttons="#dlgBtnsCandidate">
		<div class="ftitle">学员信息</div>
		<form id="editFormCandidate" method="post">
			<input type="hidden" name="cid" />
			<div class="fitem">
				<label>姓名:</label> <input name="name" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>性别:</label> <select id="gender" class="easyui-combobox" name="gender">
					<option value="男">男</option>
					<option value="女">女</option>
				</select>
			</div>
			<div class="fitem">
				<label>单位:</label> <input name="unit" class="easyui-validatebox" >
			</div>
			<div class="fitem">
				<label>职务:</label> <input name="post" class="easyui-validatebox" >
			</div>
			<div class="fitem">
				<label>出生年月:</label> <input name="birthday" />
			</div>
			<div class="fitem">
				<label>最高学历:</label> <input name="degree" class="easyui-validatebox" >
			</div>
			<div class="fitem">
				<label>参加工作时间:</label> <input name="operatingTime" class="easyui-datebox" >
			</div>
			<div class="fitem">
				<label>编制类型:</label> <input name="bzlx" class="easyui-validatebox" >
			</div>
			<div class="fitem">
				<label>状态:</label> <input name="state" class="easyui-validatebox" >
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