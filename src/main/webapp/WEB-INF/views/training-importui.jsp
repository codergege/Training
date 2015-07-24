<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>培训项目信息 excel 导入</title>
</head>
<body>
	<h1 style="text-align: center;">培训项目信息 excel 导入</h1>
	<div style="width: 600px; height: 200px; border: 1px solid blue; margin: 0 auto; padding: 5px;">
		<s:form action="training-importExcel" method="post" enctype="multipart/form-data" theme="simple">
				请选择 excel 文件:<s:file name="importExcelFile" />
			<s:submit value="导入" />
		</s:form>
		<p>
			几乎支持所有 excel 版本<br/><br/>
			注意: excel 2003 与 excel 2007 版本是一个新旧版本分水岭, 旧的 excel 版本与新的 excel 版本的文件采用了不同的文本格式, 2007 是基于 xml 的文本格式, 又因为程序通过判断文件后缀名来决定解析 excel 文件的方式, 所以请不要随意更改 excel 文件的后缀名.<br/>
		</p>
	</div>
</body>
</html>