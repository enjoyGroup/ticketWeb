<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.SeatZoneBean,th.go.ticket.app.enjoy.bean.SeatSummaryReservationBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="cancelSeatForm" class="th.go.ticket.app.enjoy.form.CancelSeatForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>ยกเลิกตั๋วการแข่งขัน</title>
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
				var pageAction			= "searchTicketDetail";
				var lv_params			= gv_service;  
				try{
			    	lv_params 	+= "&pageAction=" + pageAction + "&" + $('#frm').serialize(); 
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: lv_params,
			            beforeSend: "",
			            success: function(data){
			            	window.location.replace('/ticketWeb/pages/ticket/CancelSeatScn.jsp');
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
			
			
			$('#btnSave').click(function(){ 
				var pageAction			= "save";
				var lv_params			= gv_service;  
			 
			    try{
			    	lv_params 	+= "&pageAction=" + pageAction + "&ticketIdList=8"; 
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: lv_params,
			            beforeSend: "",
			            success: function(data){
	            			alert("บันทึกข้อมูลเรียบร้อยแล้ว");	
			            }
			        });
			    }catch(e){
			    	alert("lp_save_page :: " + e);
			    }				
			}); 

			
			$('#btnCancel').click(function(){ 
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
			    	alert("lp_cancel_page :: " + e);
			    }				
			}); 
		});
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.CancelSeatServlet" />
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
					            	<h4 class="alert-heading">เงื่อนไขค้นหาตั๋วฟุตบอล</h4>
					          	</div>
								<div class="row">
									<div class="col-sm-12">
										<section class="panel panel-default">
											<div class="panel-body" align="center">
									        	<table width="800px" border="0" cellpadding="5" cellspacing="5">
									        		<tr>
									        			<td align="right" width="150px;">
									        				เลขที่นั่ง  : &nbsp;
									        			</td>
									        			<td align="left" width="350px;">
									        				<input type='text' id="seatingNoBegin" name='seatingNoBegin' maxlength="10" />
									        			</td>
									        			<td align="right">
									        				- &nbsp;
									        			</td>
									        			<td align="left">
									        				<input type='text' id="seatingNoEnd" name='seatingNoEnd' maxlength="10" />
									        				&nbsp;
									        				<span id="inValidSpan"></span>
									        			</td>
									        		</tr>
									        		<tr>
									        			<td align="right" width="150px;">
									        				Ticket Id  : &nbsp;
									        			</td>
									        			<td align="left" colspan="3">
									        				<input type='text' id="ticketId" name='ticketId' maxlength="17" />
									        				<input type="button" id="btnSearch" class='btn btn-danger' value='ค้นหา'/>&nbsp;&nbsp;&nbsp;
									        				<input type="reset" id="btnReset" class='btn btn-danger' value='เริ่มค้นใหม่' />
									        			</td>
									        		</tr>
													<!-- 
									        		<tr>
									        			<td align="right">
									        				Zone :&nbsp;
									        			</td>
									        			<td align="left" colspan="3">
									        				<select id="fieldZoneId" name="fieldZoneId">
									        					<option value="">ไม่ระบุ</option>
									        					<%  List<SeatZoneBean> 	fieldZoneList = cancelSeatForm.getFieldZoneList();
									        						for(SeatZoneBean beanZone:fieldZoneList){ %>
									        						<option value="<%=beanZone.getFieldZoneId()%>"><%=beanZone.getFieldZoneName()%></option>
									        					<%} %>
									        				</select>
									        				<input type="button" id="btnSearch" class='btn btn-danger' value='ค้นหา'/>&nbsp;&nbsp;&nbsp;
									        				<input type="reset" id="btnReset" class='btn btn-danger' value='เริ่มใหม่' />
									        			</td>
									        		</tr>
									        		 -->
									        	</table>
								        	</div>
										</section>
									</div>
								</div>
							</section>
							
							<section class="scrollable padder">
								<div class="alert alert-block alert-error fade in">
					            	<h4 class="alert-heading">รายละเอียดตั๋วฟุตบอล</h4>
					          	</div>
								<div class="row">
									<div class="col-sm-12">
										<section class="panel panel-default">
											<div class="panel-body" align="center">
												<table class="table sim-panel-result-table" id="tbl_result" border="1" style="border: 2; width:60%" >
													<tr class="headerRow">
														<th width="5%"  style="text-align: center;"><B></B></th>
														<th width="3%"  style="text-align: center;"><B>ลำดับ</B></th>
														<th width="15%" style="text-align: center;"><B>Ticket Id</B></th>
														<th width="10%" style="text-align: center;"><B>เลขที่นั่ง</B></th>
														<th width="10%" style="text-align: center;"><B>ฤดูกาล</B></th>
														<th width="30%" style="text-align: center;"><B>ทีมคู่แข่งขัน</B></th>
														<th width="15%" style="text-align: center;"><B>ประเภทตั๋ว</B></th>
														<th width="25%" style="text-align: center;"><B>ราคา</B></th>
													</tr>
													<%
													List<SeatSummaryReservationBean>  	list			=   cancelSeatForm.getResultList();
													SeatSummaryReservationBean 			bean			=   null;
													int 								rowNumber		=   0;
													 
													if(list.size()>0){
														for(int i=0;i<list.size();i++){
															bean = list.get(i);
															rowNumber = i+1; 
														
														%>
														 <tr>
															<td align="center">
																<B><input type="checkbox" id="i_chk_cancel" name="i_chk_cancel"></B>
															</td>
															<td align="center">
																<B><%=rowNumber%></B>
															</td>
															<td align="center" >
																<B><%=bean.getTicketId()%></B>
															</td>
															<td align="center" >
																<B><%=bean.getSeatingNo()%></B>
															</td>
															<td align="center" >
																<B><%=bean.getSeason()%></B>
															</td>
															<td align="center" >
																<B><%=bean.getAwayTeamNameTH()%></B>
															</td>
															<td align="center">
																<B><%=bean.getBookingTypeName()%></B>
															</td>
															<td align="right">
																<B><%=bean.getBookingPrices()%></B>
															</td>
														</tr> 
														<% } %>
														<tr>
															<td align="right" colspan="7"><B>รวม</B></td>
															<td align="right">
																<B>0</B>
															</td>
														</tr> 
													<%}else{ %>
														<tr align="center">
															<td align="center" colspan="8">
																<B>ไม่พบข้อมูล</B>
															</td>
														</tr> 
													<%} %>
												</table>	
											</div>	
										</section>
									</div>
									<div align="center" class="panel-body">
				        				<input type="button" id="btnSave"   class='btn btn-danger' value='บันทึก'/>&nbsp;&nbsp;&nbsp;
				        				<input type="reset"  id="btnCancel" class='btn btn-danger' value='เริ่มเลือกใหม่' />
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