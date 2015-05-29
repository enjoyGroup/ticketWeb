<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
		<title>Chainart FC</title>
		<%@ include file="/pages/include/enjoyInclude.jsp"%>
		<style type="text/css">
			button{
				border:0px;
				background-color:#ffffff;
				cursor:pointerk
			}
			.txtbox
			{
				font-size: 12px;
				color: #336bba;
				font-family: "Tahoma";
				background-color: #eef5fb;
				padding: 1px;
				border: 1px solid #a7c1e5;
			}
			
			.my-form{
				margin-top:12px; 
				-webkit-border-radius: 12px;
				-moz-border-radius: 12px;
				border-radius: 12px;
			}
		</style>
		<script language="JavaScript" type="text/JavaScript">
		function lp_changeEnterToTab(e)
		{
			var keycode =(window.event) ? event.keyCode : e.keyCode;
			if(keycode == 13) {
				event.keyCode = 9;
			}
		} 
		
		function lp_changeEnterToTab_forPWD(e)
		{
			var keycode =(window.event) ? event.keyCode : e.keyCode;
			if(keycode == 13) {
				//lp_submit_login();
				$('#btnLogin').click();
			}
		} 
		
		$(document).ready(function(){
			$('#username').focus();				
			$('#btnLogin').click(function(){
				var url 	= '<%=servURL%>/EnjoyGenericSrv?service=servlet.LoginServlet';
				var userId	= null;
				var pass	= null;
				var params	= null;
				
				try{
					userId 	= $('#username').val();
					pass 	= $('#user_pwd').val();						
					if (userId == "") {
						alert("กรุณาระบุรหัสผู้ใช่ก่อนทำการเข้าสู่ระบบ");
						$('#username').focus();
						return false;
					}
					if (pass == "") {
						alert("กรุณาระบุรหัสผ่านก่อนทำการเข้าสู่ระบบ");
						$('#user_pwd').focus();
						return false;
					}
					
					params 	= "userId=" + userId + "&passWord=" + pass;
					$.ajax({
						async:false,
			            type: "POST",
			            url: url,
			            data: params,
			            beforeSend: "",
			            success: function(data){
			            	var jsonObj 			= null;
			            	var status				= null;
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			if (jsonObj.FlagChange == "Y"){
		            				window.location.replace('/ticketWeb/pages/ticket/ChangePassScn.jsp');
		            			} else {
		            				window.location.replace('/ticketWeb/pages/menu/index.jsp');
		            			}
		            		}else{
		            			alert(jsonObj.errMsg);
		            		}
			            }
			        });
				}catch(err){
					alert("btnLogin :: " + err);
				}
				
			});
		});
		</script>	
	</head>
	<body>
		<div style="width: 100%;height:100%;" align="center">
			<table border="0" width="50%">
				<tr>
					<td>User :</td>
					<td>
						<input id="username"  name="username" type="text"  maxlength="20" onkeydown="lp_changeEnterToTab_forPWD();" />
					</td>
				</tr>
				<tr>
					<td>Password :</td>
					<td>
						<input id="user_pwd"  name="user_pwd"  type="password"  maxlength="50" onkeydown="lp_changeEnterToTab_forPWD();" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" id="btnLogin" value="Login" style="width: 80px;">
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
