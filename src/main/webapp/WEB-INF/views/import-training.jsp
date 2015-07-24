<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入结果显示页面</title>
</head>
<body>
	<h1>
		导入的 excel 文件名: ${request.excelFileName }&nbsp;&nbsp;
	</h1>
	<h1>
		导入的 excel sheet: ${request.excelSheetName }
	</h1>
	<h2>
		减去第一行, 培训信息总行数: ${request.totalCount }
		导入成功: ${request.successCount }
	</h2>
	<h3>
		${request.failCount } 条失败记录:
	</h3>
	<p>
		<s:iterator var="failInfo" value="#request.failInfos">
			<p style="margin-left: 100px;">
				<span style="width: 100px; display: inline-block;">
				<s:property value="#failInfo.name"/>&#9;
				</span>
				<s:property value="#failInfo.info"/>&nbsp;&nbsp;
			</p>
			<hr style="margin-left: 100px; color: #ccc"/>
		</s:iterator>
	</p>
</body>
</html>