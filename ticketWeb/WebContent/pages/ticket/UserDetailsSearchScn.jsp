<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.UserDetailsBean, th.go.ticket.app.enjoy.bean.RefuserstatusBean,th.go.ticket.app.enjoy.model.Userprivilege"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="userDetailsMaintananceForm" class="th.go.ticket.app.enjoy.form.UserDetailsMaintananceForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	UserDetailsBean 		userDetailsBean 	= userDetailsMaintananceForm.getUserDetailsBean();
	List<UserDetailsBean> 	dataList 			= userDetailsMaintananceForm.getUserDetailsBeanList();
	List<RefuserstatusBean> refuserstatusCombo 	= userDetailsMaintananceForm.getRefuserstatusCombo();
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
			
			gp_progressBarOff();
			
			 
			$('#btnSearch').click(function(){ 
				try{
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + "&pageAction=searchUserDetail&" + $('#frm').serialize(),
			            beforeSend: "",
			            success: function(data){
			            	window.location.replace('/ticketWeb/pages/ticket/UserDetailsSearchScn.jsp');
			            }
			        });
				}catch(err){
					alert("btnSearch :: " + err);
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
		
		function lp_sendEditPage(av_val, av_index){
			try{
//				if(la_flagAddSales[av_index].value=="N" || 
//						(la_flagAddSales[av_index].value=="Y" && (la_chassisDisp[av_index].value!="" && la_engineNoDisp[av_index].value!=""))){
					window.location.replace(gv_url + "?service=servlet.UserDetailsMaintananceServlet&pageAction=getUserDetail&userUniqueId=" + av_val);
//				}else{
//					alert("ไม่อนุญาตให้แก้ไขใบส่งเสิรมการขายโดยตรง");
//				}
			}catch(e){
				alert("lp_sendEditPage :: " + e);
			}
		}
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.SearchUserDetailsMaintananceServlet" />
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
									        	<table width="800px" border="0" cellpadding="5" cellspacing="5">
									        		<tr>
									        			<td align="right" width="150px;">
									        				ชื่อ-นามสกุล  : &nbsp;
									        			</td>
									        			<td align="left" width="350px;">
									        				<input type='text' id="userName" name='userName' maxlength="50" />
									        			</td>
									        			<td align="right">
									        				User ID : &nbsp;
									        			</td>
									        			<td align="left">
									        				<input type='text' id="userId" name='userId' maxlength="20" />
									        				&nbsp;
									        				<span id="inValidSpan"></span>
									        			</td>
									        		</tr>
									        		<tr>
									        			<td align="right">
									        				สถานะ :&nbsp;
									        			</td>
									        			<td align="left" colspan="3">
									        				<select id="userStatus" name="userStatus">
									        					<option value="">ไม่ระบุ</option>
									        					<% for(RefuserstatusBean beanStatus:refuserstatusCombo){ %>
									        						<option value="<%=beanStatus.getUserStatusCode()%>"><%=beanStatus.getUserStatusName()%></option>
									        					<%} %>
									        				</select>
									        				<input type="button" id="btnSearch" class='btn btn-danger' value='ค้นหา' onclick="lp_search();" />&nbsp;&nbsp;&nbsp;
									        				<input type="reset" id="btnReset" class='btn btn-danger' value='เริ่มใหม่' />
									        			</td>
									        		</tr>
									        	</table>
								        	</div>
										</section>
									</div>
								</div>
							</section>
							
							<section class="scrollable padder">
								<div class="alert alert-block alert-error fade in">
					            	<h4 class="alert-heading">ข้อมูลผู้ใช้งานระบบ</h4>
					          	</div>
								<div class="row">
									<div class="col-sm-12">
										<section class="panel panel-default">
											<div class="panel-body" align="center">
												<table id="tb_result" cellpadding="6" cellspacing="6" border="1" style="overflow-y:auto;width:800px;">
									               <tr bgcolor="#473636"  class="text_white" height="26px;">
														<th  style="text-align: center;" width="30px;" ><B>ลำดับ</B></th>
														<th  style="text-align: left;"   width="20px;"><B>ชื่อ-นามสกุล</B></th>
														<th  style="text-align: left;"   width="100px;"><B>User Id</B></th> 
														<th  style="text-align: left;"   width="150px;"><B>E-mail</B></th>
														<th  style="text-align: left;"   width="100px;"><B>สถานะ</B></th> 
														<th  style="text-align: center;" width="150px;"><B>สิทธิ์การใช้งาน</B></th>
													</tr> 
													<%
														UserDetailsBean 	  bean 		= null;
		    											int					  seq		= 1;
														
														if(dataList.size()>0){
															for(int i=0;i<dataList.size();i++){
																bean = dataList.get(i);															
													%>
																<tr class="rowSelect" onclick="lp_sendEditPage('<%=bean.getUserUniqueId()%>', <%=i%>)" >
																	<td style="text-align:center"><%=seq%></td>
																	<td><%=bean.getUserName()%></td>
																	<td><%=bean.getUserId()%></td>
																	<td><%=bean.getUserEmail()%></td>
																	<td><%=bean.getUserStatus()%></td>
																	<td><%=bean.getUserPrivilege()%></td>
																</tr>
													<% 			seq++;
															} 
														} else{  %>
															<tr height="26px;"><td colspan="6"><b>ไม่พบข้อมูลที่ระบุ</b></td></tr>
													<%  } %>  
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
		<div align="center" class="FreezeScreen" style="display:none;">
	        <center>
	        	<img id="imgProgress" valign="center" src="<%=imgURL%>/loading36.gif" alt="" />
	        	<span style="font-weight: bold;font-size: large;color: black;">Loading...</span>
	        </center>
	    </div>
	</form>
	
</body>
</html>