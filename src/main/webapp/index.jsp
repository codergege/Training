<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>sshe demo project</title>
<script src="${pageContext.request.contextPath }/js/easyui/jquery.min.js"></script>
<script src="${pageContext.request.contextPath }/js/easyui/jquery.min.js"></script>
<script src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css" />
<script src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/codergege.js"></script>
<script>
	var $logRegDlg;
	var $loginForm;
	$(function() {
		// 登录对话框
		$logRegDlg = $('#logRegDlg');
		$logRegDlg.dialog({
			title:'用户登录',
			width:'400px',
			height:'300px',
			closable:false,
			modal: true,
			draggable: false,
			buttons: [{
				text: "登录",
				handler: function(){
					console.log($loginForm);
					$loginForm.submit();
					/*
					$.ajax({
						   type: "POST",
						   url: getRootPath() + "/user-login",
						   data: $('#loginForm').serialize(),
						   dataType: 'json',
							success: function(msg){
							   if(msg && msg.success=="true"){
						   		   $.messager.show({
						   			   title: "提示",
						   			   msg: msg.message
						   		   });
								$logRegDlg.dialog('close');
						   		window.location.href = getRootPath() + "/candidate-list";
							   }else{
								   $.messager.alert('Warning', msg.message);
							   }
						   }
						});
					*/
				}
			}]
		});
		
		//login form
		$loginForm = $('#loginForm').form({
			url: getRootPath() + "/user-login",
			success: function(r){
				var msg = $.parseJSON(r);
				   if(msg && msg.success=="true"){
			   		   $.messager.show({
			   			   title: "提示",
			   			   msg: msg.message
			   		   });
					$logRegDlg.dialog('close');
			   		window.location.href = getRootPath() + "/manage";
				   }else{
					   $.messager.alert('Warning', msg.message);
				   }
			   }
		});
	});
	
	
</script>
</head>
<body>
	<div id="logRegDlg">
		<form id="loginForm" method="post">
       	    用户名:<input type="text" name="loginName" class="easyui-validatebox" required="true"/><br/>
            密码:<input type="password" name="password" class="easyui-validatebox" required="true"/>
        </form>
	</div>
</body>
</html>