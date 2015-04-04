<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.UserDetailsBean, th.go.ticket.app.enjoy.bean.RefuserstatusBean,th.go.ticket.app.enjoy.model.Userprivilege"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="userDetailsMaintananceForm" class="th.go.ticket.app.enjoy.form.UserDetailsMaintananceForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String 					pageMode 			= userDetailsMaintananceForm.getPageMode();
	UserDetailsBean 		userDetailsBean 	= userDetailsMaintananceForm.getUserDetailsBean();
	List<RefuserstatusBean> refuserstatusCombo 	= userDetailsMaintananceForm.getRefuserstatusCombo();
	List<Userprivilege> 	userprivilegeList	= userDetailsMaintananceForm.getUserprivilegeList();
	int 					couChkRow			= 0;


%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>เพิ่ม Match การแข่งขัน</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<script>
		var gv_service 			= null;
		var gv_url 				= '<%=servURL%>/EnjoyGenericSrv';
		var gv_checkDupUserId 	= false;
		
		$(document).ready(function(){
			gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			$('#menu1').ptMenu();
			
			if($("#pageMode").val()=="EDIT"){
				lp_setModeEdit();
			}
			
			/*
			$('#btnSave').on('click',function(){
				
				var la_chkUserPrivilege = null;
				var lv_userPrivilege	= "";
				
				try{
					
					la_chkUserPrivilege = document.getElementsByName("chkUserPrivilege");
					
					for(var i=0;i<la_chkUserPrivilege.length;i++){
						
						if(la_chkUserPrivilege[i].checked==true){
							
							if(lv_userPrivilege==""){
								lv_userPrivilege = la_chkUserPrivilege[i].value;
							}else{
								lv_userPrivilege = lv_userPrivilege + "," + la_chkUserPrivilege[i].value;
							}
							
						}
						
					}
					
					$("#hidUserPrivilege").val(lv_userPrivilege);
					
					
					if(!lp_validate()){
						return;
					}
					
					if(!confirm("Password จะถูกส่งไปที่ E-mail ที่คุณกรอก คุณกรอก E-mail ถูกต้องแล้วใช่หรือไม่ ?")){
						$('#userEmail').focus();
						return;
					}
				}catch(e){
					alert("btnSave :: " + e);
				}
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: "pageAction=save&" + $('#frm').serialize(),
		            beforeSend: gp_progressBarOn(),
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var userUniqueId		= 0;
		            	
		            	try{
		            		gp_progressBarOff();
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			userUniqueId = jsonObj.userUniqueId;
		            			
		            			//alert("บันทึกเรียบร้อย " + userUniqueId);
		            			//location.reload();
		            			//window.location = gv_url + "?service=servlet.UserDetailsMaintananceServlet&pageAction=getUserDetail&userUniqueId=" + userUniqueId;
		            		}else{
		            			alert(jsonObj.errMsg);
		            			
		            		}
		            	}catch(e){
		            		alert("in btnSave :: " + e);
		            	}
		            }
		        });
				
			});
			*/
			gp_progressBarOff();
			
		});
		
		function lp_validate(){
			var la_idName               = new Array("userName", "userSurname", "userId", "userEmail");
		    var la_msg               	= new Array("ชื่อ"	  , "นามสกุล"	 , "User ID", "E-mail");
		    
			try{
				
				lo_flagAddSales 		= document.getElementById("flagAddSales");
				lo_commTotalAmount 		= document.getElementById("commTotalAmount");
				
				for(var i=0;i<la_idName.length;i++){
		            lo_obj          = eval('$("#' + la_idName[i] + '")');
		            
		            if(gp_trim(lo_obj.val())==""){
		            	alert("กรุณาระบุ " + la_msg[i]);
		            	lo_obj.focus();
		                return false;
		            }
		        }
				
				if(gv_checkDupUserId==false){
					alert("มี userid นี้ในระบบแล้ว");
					$("#userId").focus();
					return false;
				}
				
				
			}catch(e){
				alert("lp_validate :: " + e);
				return false;
			}
			
			return true;
		}
		
		function lp_checkDupUserId(){
			
			var lv_userId = null;
			
			try{
				lv_userId = gp_trim($("#userId").val());
				
				$("#inValidSpan").html("");
				
				if(lv_userId==""){
					return;
				}
				
				$("#userId").val(lv_userId);
				
				params 	= "pageAction=checkDupUserId&" + $('#frm').serialize();
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: gp_progressBarOn(),
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var cou					= 0;
		            	
		            	try{
		            		gp_progressBarOff();
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			cou = parseInt(jsonObj.COU);
		            			
		            			if(cou > 0){
		            				$("#inValidSpan").css("color", "red");
		            				$("#inValidSpan").html("มี userid นี้ในระบบแล้ว");
		            				
		            				gv_checkDupUserId = false;
		            				
		            			}else{
		            				$("#inValidSpan").css("color", "green");
		            				$("#inValidSpan").html("userid นี้ใช้งานได้");
		            				
		            				gv_checkDupUserId = true;
		            			}
		            			
		            		}else{
		            			alert(jsonObj.errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_checkDupUserId :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_checkDupUserId :: " + e);
			}
		}
		
		function lp_setModeEdit(){
			
			var la_chkUserPrivilege = null;
			var la_userPrivilegeDb	= null;
			
			try{
				la_chkUserPrivilege = document.getElementsByName("chkUserPrivilege");
				lo_hidUserPrivilege = document.getElementsByName("hidUserPrivilege");
				
				if(gp_trim($("#hidUserPrivilege").val())!=""){
					la_userPrivilegeDb = gp_trim($("#hidUserPrivilege").val()).split(",");
					
					for(var i=0;i<la_userPrivilegeDb.length;i++){
						for(var j=0;j<la_chkUserPrivilege.length;j++){
							if(la_chkUserPrivilege[j].value==la_userPrivilegeDb[i]){
								la_chkUserPrivilege[j].checked = true;
								break;
							}
						}
					}
					
				}
				
				lp_checkDupUserId();
				
			}catch(e){
				alert("lp_setModeEdit :: " + e);
			}
			
		}
		
		function lp_save(){
			var la_chkUserPrivilege = null;
			var lv_userPrivilege	= "";
			
			try{
				
				la_chkUserPrivilege = document.getElementsByName("chkUserPrivilege");
				
				for(var i=0;i<la_chkUserPrivilege.length;i++){
					
					if(la_chkUserPrivilege[i].checked==true){
						
						if(lv_userPrivilege==""){
							lv_userPrivilege = la_chkUserPrivilege[i].value;
						}else{
							lv_userPrivilege = lv_userPrivilege + "," + la_chkUserPrivilege[i].value;
						}
						
					}
					
				}
				
				$("#hidUserPrivilege").val(lv_userPrivilege);
				
				
				if(!lp_validate()){
					return;
				}
				
				if(!confirm("Password จะถูกส่งไปที่ E-mail ที่คุณกรอก คุณกรอก E-mail ถูกต้องแล้วใช่หรือไม่ ?")){
					$('#userEmail').focus();
					return;
				}
			}catch(e){
				alert("btnSave :: " + e);
			}
			
			$.ajax({
				async:false,
	            type: "POST",
	            url: gv_url,
	            data: "pageAction=save&" + $('#frm').serialize(),
	            beforeSend: gp_progressBarOn(),
	            success: function(data){
	            	var jsonObj 			= null;
	            	var status				= null;
	            	var userUniqueId		= 0;
	            	
	            	try{
	            		gp_progressBarOff();
	            		
	            		jsonObj = JSON.parse(data);
	            		status	= jsonObj.status;
	            		
	            		if(status=="SUCCESS"){
	            			userUniqueId = jsonObj.userUniqueId;
	            			
	            			//alert("บันทึกเรียบร้อย " + userUniqueId);
	            			//location.reload();
	            			//window.location = gv_url + "?service=servlet.UserDetailsMaintananceServlet&pageAction=getUserDetail&userUniqueId=" + userUniqueId;
	            		}else{
	            			alert(jsonObj.errMsg);
	            			
	            		}
	            	}catch(e){
	            		alert("in btnSave :: " + e);
	            	}
	            }
	        });
		}
		
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.UserDetailsMaintananceServlet" />
		<input type="hidden" id="hidUserPrivilege" 	name="hidUserPrivilege" value="<%=userDetailsBean.getUserPrivilege()%>" />
		<input type="hidden" id="pageMode" 	name="pageMode" value="<%=pageMode%>" />
		<input type="hidden" id="userUniqueId" 	name="userUniqueId" value="<%=userDetailsBean.getUserUniqueId()%>" />
		<div id="menu" style="width: 100%;background: black;">
			<%@ include file="/pages/menu/menu.jsp"%>
		</div>
		<section class="vbox">
			<section>
				<section class="hbox stretch">
					<section id="content">
						<section class="vbox">
							<section class="scrollable padder">
								<div class="alert alert-block alert-error fade in">
					            	<h4 class="alert-heading"><%=userDetailsMaintananceForm.getTitlePage() %></h4>
					          	</div>
								<div class="row">
									<div class="col-sm-12">
										<section class="panel panel-default">
											<div class="panel-body" align="center">
									        	<table width="600px" border="0" cellpadding="5" cellspacing="5">
									        		<tr>
									        			<td align="right" width="150px;">
									        				ชื่อ <span style="color: red;"><b>*</b></span> : &nbsp;
									        			</td>
									        			<td align="left" width="350px;">
									        				<input type='text' id="userName" name='userName' value="<%=userDetailsBean.getUserName() %>" maxlength="50" />
									        			</td>
									        		</tr>
									        		<tr>
									        			<td align="right">
									        				นามสกุล <span style="color: red;"><b>*</b></span> : &nbsp;
									        			</td>
									        			<td align="left">
									        				<input type='text' id="userSurname" name='userSurname' value="<%=userDetailsBean.getUserSurname() %>" maxlength="100" />
									        			</td>
									        		</tr>
									        		<tr>
									        			<td align="right">
									        				User ID <span style="color: red;"><b>*</b></span> : &nbsp;
									        			</td>
									        			<td align="left">
									        				<input type='text' id="userId" name='userId' value="<%=userDetailsBean.getUserId() %>" onchange="lp_checkDupUserId();" maxlength="20" />
									        				&nbsp;
									        				<span id="inValidSpan"></span>
									        			</td>
									        		</tr>
									        		<tr>
									        			<td align="right">
									        				E-mail <span style="color: red;"><b>*</b></span> : &nbsp;
									        			</td>
									        			<td align="left">
									        				<input type="text" id="userEmail" name="userEmail" value="<%=userDetailsBean.getUserEmail()%>" maxlength="100" />
									        			</td>
									        		</tr>
									        		<tr>
									        			<td align="right">
									        				สถานะ :&nbsp;
									        			</td>
									        			<td align="left">
									        				<select id="userStatus" name="userStatus">
									        					<% for(RefuserstatusBean beanStatus:refuserstatusCombo){ %>
									        					<option value="<%=beanStatus.getUserStatusCode()%>" <%if(userDetailsBean.getUserStatus().equals(beanStatus.getUserStatusCode())){ %> selected <%} %> ><%=beanStatus.getUserStatusName()%></option>
									        					<%} %>
									        				</select>
									        			</td>
									        		</tr>
									        		<tr>
									        			<td align="right">
									        				<input type="checkbox" id="flagChangePassword" name="flagChangePassword" value="Y" <%if(userDetailsBean.getFlagChangePassword().equals("Y")){ %> checked="checked" <%} %> /> :&nbsp;
									        			</td>
									        			<td align="left">
									        				ต้องการเปลี่ยนรหัสผ่านเมื่อ Login ครั้งแรก
									        			</td>
									        		</tr>
									        	</table>
									        	<br/>
									        	<table width="500px" border="0" cellpadding="5" cellspacing="5">
									        		<tr>
									        			<td align="left" colspan="4">
									        				สิทธิ์การใช้ระบบ
									        			</td>
									        		</tr>
									        		
									        		<%
									        			for(Userprivilege beanUserprivilege:userprivilegeList){
									        		%>
									        		<%if(couChkRow==0){ %><tr><%} %>
									        			<td align="right">
									        				<input type="checkbox" id="chkUserPrivilege<%=beanUserprivilege.getPrivilegeCode() %>" name="chkUserPrivilege" value="<%=beanUserprivilege.getPrivilegeCode() %>" /> :&nbsp;
									        			</td>
									        			<td align="left">
									        				<%=beanUserprivilege.getPrivilegeName() %>
									        			</td>
									        		<%if(couChkRow==1){ %>
									        		</tr>
									        		<%}
									        			if(couChkRow==1){
									        				couChkRow=0;
									        			}else{
									        				couChkRow++;
									        			}
									        			
									        		} %>
									        		<tr>
									        			<td align="center" colspan="4">
									        				<br/><br/>
									        				<input type="button" id="btnSave" class='btn btn-danger' value='บันทึก' onclick="lp_save();" />&nbsp;&nbsp;&nbsp;
									        				<input type="reset" id="btnReset" class='btn btn-danger' value='เริ่มใหม่' />
									        			</td>
									        		</tr>
									        	</table>
								        	</div>
										</section>
									</div>
								</div>
							</section>
						</section>
					</section>
					<a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
				</section>
			</section>
		</section>
	</form>
	<div align="center" class="FreezeScreen" style="display:none;">
        <center>
        	<img id="imgProgress" valign="center" src="<%=imgURL%>/loading36.gif" alt="" />
        	<span style="font-weight: bold;font-size: large;color: black;">Loading...</span>
        </center>
    </div>
</body>
</html>