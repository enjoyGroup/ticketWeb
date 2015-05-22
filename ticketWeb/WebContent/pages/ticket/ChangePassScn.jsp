<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>เพิ่ม Match การแข่งขัน</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>	
	<script>
		var gv_service 		= null;
		var gv_url 			= '<%=servURL%>/EnjoyGenericSrv';

		$(document).ready(function(){
			gv_service 		= "service=" + $('#service').val();
			$('#menu1').ptMenu();
			
			$('#btnSave').click(function(){
			    var lv_oldUserPassword			= null;
			    var lv_newUserPassword			= null;
			    var lv_confirmUserPassword		= null;
			    
			    try{
			    	lv_oldUserPassword 		= gp_trim($("#oldUserPassword").val());
					lv_newUserPassword 		= gp_trim($("#newUserPassword").val());
					lv_confirmUserPassword 	= gp_trim($("#confirmUserPassword").val());
					
					if(lv_oldUserPassword == "") {
						alert("กรุณาระบุรหัสผ่านเดิมก่อนทำการบันทึก");
						$("#oldUserPassword").focus();
						return;
					} else if(lv_newUserPassword == "") {
						alert("กรุณาระบุรหัสผ่านใหม่ก่อนทำการบันทึก");
						$("#newUserPassword").focus();
						return;
					}else if(lv_confirmUserPassword == "") {
						alert("กรุณาระบุยืนยันรหัสผ่านก่อนทำการบันทึก");
						$("#confirmUserPassword").focus();
						return;
					}
					
					if(lv_newUserPassword != lv_confirmUserPassword){
						alert("ยืนยันรหัสผ่านไม่ตรงกับรหัสผ่านใหม่ กรุณาระบุใหม่อีกครั้ง");
						$("#confirmUserPassword").focus();
						return;
					}
			    	
//					document.getElementById("pageAction").value 	= "save";
//			    	params 	= $('#frm').serialize();
/*
			    	$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: params,
			            beforeSend: "",
			            success: function(data){
			            	alert(data)
			            	//window.location.replace('/motorcyclesWeb/pages/motor/SummarySaleDetailScn.jsp');
			            }
			        });
*/
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + "&pageAction=save&" + $('#frm').serialize(),
			            beforeSend: "",
			            success: function(data){
			            	var jsonObj 			= null;
			            	var status				= null;
			            	
			            	try{
			            		jsonObj = JSON.parse(data);
			            		status	= jsonObj.status;
			            		
			            		if(status=="SUCCESS"){
			            			alert("บันทึกข้อมูลเรียบร้อยแล้ว กรุณาทำการ Login ใหม่อีกครั้ง");	
			            			window.location.replace('/ticketWeb');
			            		}else{
			            			errMsg 	= jsonObj.errMsg;
			            			alert(errMsg);
			            		}
			            	}catch(e){
			            		alert("in btnSave :: " + e);
			            	}
			            }
			        });
			    }catch(e){
			    	alert("btnSave :: " + e);
			    }
			    
			});
		
			$('#btnReset').click(function(){
				var pageAction			= "new";
				var lv_params			= gv_service;  
			 
			    try{
			    	lv_params 	+= "&pageAction=" + pageAction ; 
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: lv_params,
			            beforeSend: "",
			            success: function(data){  
			            }
			        });
			    	  
			    }catch(e){
			    	alert("lp_reset_page :: " + e);
			    }			    
			});
			
		});
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.ChangePasswordServlet" />
		<div id="menu" style="width: 100%;background: black;">
			<%@ include file="/pages/menu/menu.jsp"%>
		</div>
		<div align="center" style="width:100%;position: absolute;height: 100%;">
			<section class="scrollable padder">
				<div class="alert alert-block alert-error fade in">
	            	<h2 class="alert-heading">เปลี่ยนรหัสผ่าน</h2>
          		</div>
	          	<div class="row">
					<div class="col-sm-12">
						<section class="panel panel-default">
							<div class="panel-body">
								<table border="0" cellpadding="0" cellspacing="5" class="table span12" align="center">
									<colgroup>
										<col align="left" width="50%" />
										<col align="left" />
									</colgroup>
									<tr>
										<td><label class="control-label" style="text-align:right">รหัสผ่านเดิม : </label></td>
										<td class="no-padd-left">
											<input type="password" id="oldUserPassword" name="oldUserPassword" />
										</td>
									</tr>
									<tr>
										<td><label class="control-label" style="text-align:right">รหัสผ่านใหม่ : </label></td>
										<td class="no-padd-left">
											<input type="password" id="newUserPassword" name="newUserPassword" />
										</td>
									</tr>
									<tr>
										<td><label class="control-label" style="text-align:right">ยื่นยันรหัสผ่าน : </label></td>
										<td class="no-padd-left">
											<input type="password" id="confirmUserPassword" name="confirmUserPassword" />
										</td>
									</tr>
									<tr>
										<td colspan="2" align="center">
											<input type="button" class="btn btn-primary" id="btnSave" name="btnSave" value="บันทึก" />
											<input type="button" class="btn btn-primary" id="btnReset" name="btnReset" value="เริ่มใหม่" />
										</td>
									</tr>
								</table>
							</div>
						</section>
					</div>
				</div>
			</section>
		</div>
	</form>
</body>
</html>