<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>培训-学员关联信息 excel 导入</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/codergege.js"></script>
<script type="text/javascript">
	window.onload = function () {
		/* var name = getParameterValue("name");
		var oText = document.getElementById("name");
		oText.innerHTML = "培训项目班次名称: " + name; */
		var oText = document.getElementById("tid");
		oText.value = getParamValue("tid");
	};
</script>
</head>
<body>
	<h1 style="text-align: center;">培训-学员关联信息 excel 导入</h1>
	<div style="width: 600px; height: 200px; border: 1px solid blue; margin: 0 auto; padding: 5px;">
		<s:form action="training-importRelExcel" method="post" enctype="multipart/form-data" theme="simple">
				<input id="tid" type="hidden" name="tid">
				请选择 excel 文件:<s:file name="importExcelFile" />
			<s:submit value="导入" />
		</s:form>
		
		<p>
			1. 几乎支持所有 excel 版本<br/>
			2. 不要随意修改 excel 文件的后缀名<br/>
		</p>
	</div>
</body>
</html>