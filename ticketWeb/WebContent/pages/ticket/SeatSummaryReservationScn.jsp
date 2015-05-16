<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.SeatSummaryReservationBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="seatSummaryReservationForm" class="th.go.ticket.app.enjoy.form.SeatSummaryReservationForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<title>รายละเอียดตั๋ว</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<style>
		.headerRow:hover{
		    cursor: pointer;
		}
	</style>
	<script type="text/javascript">
		
		var gv_service 		= null;
		var gv_url 			= '<%=servURL%>/EnjoyGenericSrv';
		
		$(document).ready(function(){
			gp_progressBarOn();
			
			gp_progressBarOff();
		});
		
		function lp_print(){
			
			try{
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=print&" + $('#frm').serialize(),
		            beforeSend: gp_progressBarOn(),
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		gp_progressBarOff();
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		//alert(status);
		            		if(status=="SUCCESS"){
		            			alert("Printing...");
		            			//alert(JSON.stringify(jsonObj.detail));
		            			//window.location = gv_url + "?service=servlet.SeatZoneServlet&pageAction=new";
		            		}else{
		            			errMsg = jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_print :: " + e);
		            	}
		            }
		        });
			}catch(e){
				alert("lp_print :: " + e);
			}
			
		}
		
		function lp_goBack(){
			
			try{
				
				window.location = gv_url + "?service=servlet.SeatZoneServlet&pageAction=new";
				
			}catch(e){
				alert("lp_goBack :: " + e);
			}
		}
		
  </script>
</head>
<body>
	<form id="frm" action="<%=servURL%>/EnjoyGenericSrv">
		<input type="hidden" id="service" 		name="service" 		value="servlet.SeatSummaryReservationServlet" />
		<input type="hidden" id="fieldZoneId" 	name="fieldZoneId" 	value="<%=seatSummaryReservationForm.getFieldZoneId()%>" />
		<input type="hidden" id="matchId" 		name="matchId" 		value="<%=seatSummaryReservationForm.getMatchId()%>" />
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
					            	<h4 class="alert-heading">รายละเอียดตั๋ว</h4>
					          	</div>
					          	<div class="row">
									<div class="col-sm-12">
										<section class="panel panel-default">
											<div class="panel-body" align="center">
												<!-- Begin contents -->
												<div style="margin-top: 30px;" align="center">
													<table border="0" style="width:60%">
														<tr align="left">
															<td width="5%" style="font-weight: bold;" class='topic-head'>
																Match
															</td>
															<td width="95%">
																ชัยนาท vs <%=seatSummaryReservationForm.getAwayTeamNameTH()%>
															</td>
														</tr>
														<tr align="left">
															<td width="5%" style="font-weight: bold;" class='topic-head'>
																วันที่
															</td>
															<td width="95%">
																<%=seatSummaryReservationForm.getMatchDate()%> เวลา : <%=seatSummaryReservationForm.getMatchTime()%>
															</td>
														</tr>
													</table>
													<table class="table sim-panel-result-table" id="tbl_result" border="1" style="border: 2; width:60%">
														<thead> 
															<tr class="headerRow">
																<th width="10%" style="text-align: center;"><B>ลำดับ</B></th>
																<th width="20%" style="text-align: center;"><B>Ticket Id</B></th>
																<th width="20%" style="text-align: center;"><B>เลขที่นั่ง</B></th>
																<th width="25%" style="text-align: center;"><B>ประเภทตั๋ว</B></th>
																<th width="25%" style="text-align: center;"><B>ราคา</B></th>
															</tr>
														</thead>
														<tbody>
															<%
															List<SeatSummaryReservationBean>  	list			=   seatSummaryReservationForm.getResultList();
															SeatSummaryReservationBean 			bean			=   null;
															int 								rowNumber		=   0;
															 
															if(list.size()>0){
																for(int i=0;i<list.size();i++){
																	bean = list.get(i);
																	rowNumber = i+1; 
																
																%>
																 <tr>
																	<td align="center">
																		<B><%=rowNumber%></B>
																	</td>
																	<td align="center" >
																		<B><%=bean.getTicketId()%></B>
																	</td>
																	<td align="center" >
																		<B><%=bean.getSeatingNo()%></B>
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
																	<td align="center" colspan="4"></td>
																	<td align="right">
																		<B><%=seatSummaryReservationForm.getSumBookingPrices()%></B>
																	</td>
																</tr> 
															<%}else{ %>
															<tr align="center">
																<td align="center" colspan="5">
																	<B>ไม่พบข้อมูล</B>
																</td>
															</tr> 
															<%} %>
														</tbody>
													</table>
													<br/>
													<iframe name="ttestt" 
															src="<%=servURL%>/EnjoyGenericSrv?service=servlet.SeatSummaryReservationServlet&pageAction=print" 
															scrolling="yes"  
															frameborder="0" 
															width="800" 
															height="600">
													</iframe>
													<br/>
													<!--  <input type="button" id="btnSubmit" name="btnSubmit" onclick="lp_save();" class="btn" style="width: 150px;" value="พิมพ์" />&nbsp;&nbsp;-->
													<input type="button" id="btnBack" name="btnBack" onclick="lp_goBack();" class="btn" style="width: 150px;" value="ทำรายการต่อไป >>" />
												</div>
												<!-- End contents -->
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