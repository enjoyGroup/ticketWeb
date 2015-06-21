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
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>ค้นหาผู้ใช้งานระบบ</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<style type="text/css">
		.height250 {
	        height: 250px;
	        overflow-x: auto;
	        overflow-y: auto;
		}
	</style>
	<script>
		var gv_service 			= null;
		var gv_url 				= '<%=servURL%>/EnjoyGenericSrv';
		var gv_checkDupUserId 	= false;
		
		$(document).ready(function(){
			gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			
			gp_progressBarOff();
			
			 
			$('#btnSearch').click(function(){ 
				try{
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + "&pageAction=searchUserDetail&" + $('#frm').serialize(),
			            beforeSend: gp_progressBarOn(),
			            success: function(data){
			            	gp_progressBarOff();
			            	window.location.replace('<%=pagesURL%>/UserDetailsSearchScn.jsp');
			            }
			        });
				}catch(err){
					alert("btnSearch :: " + err);
				}
				
			});
			
			$('#btnReset').click(function(){
			 
			    try{
			    	gp_progressBarOn();
			    	window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=new";
			    	gp_progressBarOff();
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
		
		//*********************************************************************************//
		//    รายละเอียดเกี่ยวกับการกดปุ่ม First/Previous/Next/Last บนหน้าจอ				       //												
		//*********************************************************************************//
		function lp_first_page()
		{
			if ($("#selPage").val() != "1")
			{
				$("#selPage").val("1");
				lp_selPage();
			}	
		}
		
		function lp_previous_page()
		{
			if ($("#selPage").val() != "1")
			{
				var lv_selPage = parseFloat($("#selPage").val()) - 1;
				$("#selPage").val(lv_selPage);
				lp_selPage();
			}	
		}
		
		function lp_next_page()
		{
			if ($("#selPage").val() != $("#txtMaxresult").val())
			{
				var lv_selPage = parseFloat($("#selPage").val()) + 1;
				$("#selPage").val(lv_selPage);
				lp_selPage();
			}	
		}
		
		function lp_last_page()
		{
			if ($("#selPage").val() != $("#txtMaxresult").val())
			{
				$("#selPage").val($("#txtMaxresult").val());
				lp_selPage();
			}	
		}

		function lp_selPage(){
			
			var lv_selPage = null;
			
			try{
				lv_selPage = $("#selPage").val();
		    	params 	= "service=" + $("#service").val() + "&pageAction=getPage&pageNum=" + lv_selPage;
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: "",
		            success: function(data){
		            	window.location.replace('<%=pagesURL%>/UserDetailsSearchScn.jsp');
		            }
		        });
				
			}catch(e){
				alert("lp_selPage :: " + e);
			}
		}
		//*********************************************************************************//
		
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
								<div>
									<div class="alert alert-block alert-error fade in container">
						            	<h4 class="alert-heading"><%=userDetailsMaintananceForm.getTitlePage() %></h4>
						            </div>
						            
										<div class="container main-container round-sm padding-no" >
											
												<div class="panel-body" align="center">
													
													<%
													UserDetailsBean userdetailForm = userDetailsMaintananceForm.getUserDetailsBean();
													%>
													
										        	<table class="user-register-table user-search-table" width="100%" border="0" cellpadding="5" cellspacing="5">
										        		<tr>
										        			<td align="right" width="150px;">
										        				ชื่อ-นามสกุล  : &nbsp;
										        			</td>
										        			<td align="left" width="350px;">
										        				<input type='text' id="userName" name='userName' maxlength="50" value="<%=userdetailForm.getUserName() %>" />
										        			</td>
										        			<td align="right">
										        				User ID : &nbsp;
										        			</td>
										        			<td align="left">
										        				<input type='text' id="userId" name='userId' maxlength="20" value="<%=userdetailForm.getUserId() %>" />
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
										        						<option value="<%=beanStatus.getUserStatusCode()%>" <%if(beanStatus.getUserStatusCode().equals(userdetailForm.getUserStatus())){%> selected="selected" <%} %>>
										        							<%=beanStatus.getUserStatusName()%>
										        							</option>
										        					<%} %>
										        				</select>
										        				
										        				<input type="button" id="btnSearch" class='btn btn-primary pull-right padding-sm' style="margin-right:12px; padding-right:24px; padding-left:24px;" value='ค้นหา'/>
										        				<input type="button" id="btnReset" class='btn pull-right padding-sm'  style="margin-right:12px" value='เริ่มใหม่' />
										        			</td>
										        		</tr>
										        	</table>
									        	</div>
											</div>
										</div>
										<div style="clear:both"></div><br>
									
							</section>
							
							<section class="scrollable padder">
							
							
									<div class="container main-container round-sm padding-no" >
									<div id="seasonTitle" class="padding-md round-sm season-title-head" style="">
										<h6 class="panel-title" style="font-size:1.0em">ข้อมูลผู้ใช้งานระบบ</h6>
									</div>
										<div class="datagrid">
										<table class="table sim-panel-result-table" id="tbl_result" border="1" width="100%" style="margin-bottom:0px !important">
														<thead> 
											               <tr height="26px;">
																<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
																<th  style="text-align: center;" width="20%"><B>ชื่อ-นามสกุล</B></th>
																<th  style="text-align: center;" width="15%"><B>User Id</B></th> 
																<th  style="text-align: center;" width="20%"><B>E-mail</B></th>
																<th  style="text-align: center;" width="20%"><B>สถานะ</B></th> 
																<th  style="text-align: center;" width="20%"><B>สิทธิ์การใช้งาน</B></th>
															</tr> 
														</thead>
														<tfoot>
															<tr height="26px;">
																<td colspan="6" align="right">
																	<span style="top: -3px;">จำนวน&nbsp;</span>
																	<input type="text" id="i_txt_nvt_totalresult" name="i_txt_nvt_totalresult" style="top:0px;left:0px;width: 50px;"  readonly="readonly" value="<%=userDetailsMaintananceForm.getTotalRecord()%>" >
																	<span style="top: -3px;">&nbsp;รายการ&nbsp;&nbsp;</span>
																	<img id="i_img_nvt_first" name="i_img_nvt_first" src="<%=imgURL%>/first.gif" style="cursor:hand;top:1px;" title="First" onclick="lp_first_page();">
																	<img id="i_img_nvt_prev"  name="i_img_nvt_prev"  src="<%=imgURL%>/prv.gif"   style="cursor:hand;top:1px;" title="Previous" onclick="lp_previous_page();">
																	<input type="text" id="selPage" name="selPage" style="top:0px;left:0px;width: 30px;text-align: right;" maxlength="3" readonly="readonly" value="<%=userDetailsMaintananceForm.getPageNum()%>">
												
																	<span class="c_field_label" style="top:-5px;">/</span>
																	<span id="i_txt_nvt_total" class="c_field_label" style="top:-5px;" name="i_txt_nvt_total"><%=userDetailsMaintananceForm.getTotalPage()%></span>
																	<input type="hidden" id="txtMaxresult" name="txtMaxresult" value="<%=userDetailsMaintananceForm.getTotalPage()%>" >
																	<img id="i_img_nvt_next"  name="i_img_nvt_next" src="<%=imgURL%>/next.gif" style="cursor:hand;top:1px;" title="Next" onclick="lp_next_page();">
																	<img id="i_img_nvt_last"  name="i_img_nvt_last" src="<%=imgURL%>/last.gif" style="cursor:hand;top:1px;" title="Last" onclick="lp_last_page();">
																</td>
															</tr>
														</tfoot>
														<tbody>
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
																	<tr height="26px;"><td colspan="6" align="center"><b>ไม่พบข้อมูลที่ระบุ</b></td></tr>
															<%  } %>  
														</tbody>
													</table>
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