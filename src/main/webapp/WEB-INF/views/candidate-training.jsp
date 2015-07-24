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
	var dgCandidateTraining;
	var cid;
	$(function() {
		centerTabs = $('#centerTabs');
		cid = getParamValue("cid");
		console.log(cid);
		initTabCandidateTraining();
		initCandidateInfo();
	});
	
	function initCandidateInfo(){
		dgCandidate = $('#dgCandidate').datagrid({
			url : getRootPath() + "/candidate-list?cid=" + cid,
			fit : false,
			fitColumns : true,
			autoRowHeight : true,
			striped : true,
			nowrap : false,
			//remoteSort: true,
			//sortable: true,
			//分页
			pagination : false,
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
			} ] ]
		});
	}
	
	function initTabCandidateTraining(){
		dgTraining = $('#dgTraining').datagrid({
			url: getRootPath + "/training-list?cid=" + cid,
			fit: true,
			fitColumns: true,
			autoRowHeight: true,
			striped: true,
			nowrap: false,
			pagination: true,
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
			}]],
			toolbar: "#tbTraining"
		});
	}
	
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div id="centerTabs" class="easyui-tabs" fit="true" border="false">
			<div title="项目-学员 关联列表" closable="false">
				<table id="dgCandidate"></table>
				<table id="dgTraining"></table>
			</div>
		</div>
	</div>

	<!-- *************************** candidate part *******************************  -->
	<!-- candidates toolbar start  -->
	<div id="tbTraining">
		<div>
			|导出 学员-培训 关联信息 |
			<s:a action="candidate-relExport" >
				<s:param name="format">xls</s:param> 
				<s:param name="cid"><s:property value="#parameters.cid[0]"/></s:param> 
				xls格式
			</s:a>|
			<s:a action="candidate-relExport" >
				<s:param name="format">xlsx</s:param> 
				<s:param name="cid"><s:property value="#parameters.cid[0]"/></s:param> 
				xlsx格式
			</s:a>|
		</div>
	</div>
	<!-- candidate toolbar end -->
</body>
</html>