<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.SeatZoneBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="seatZoneForm" class="th.go.ticket.app.enjoy.form.SeatZoneForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<title>กำหนด Zone ที่นั่ง/จำนวนที่นั่ง</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>	
	<link href="<%=cssURL%>/menu-styles2.css" rel="stylesheet">
	<script src="<%=jsURL%>/bootstrap.min.js" type="text/javascript"></script> 
	<script src="<%=jsURL%>/menu-script.js" type="text/javascript"></script>
	<script> 
		var gv_service 		= null;
		var gv_url 			= '<%=servURL%>/EnjoyGenericSrv';
		
		$(document).ready(function(){
			try{
				gp_progressBarOn();
				
				gv_service 		= "service=" + $('#service').val();
				
				//$('#menu1').ptMenu();
				$("#firstList").click();
			}catch(e){
				alert("onLoadPage :: " + e);
			}
			gp_progressBarOff();
		});
		
		function lp_selectMatch(av_matchId, av_season, av_awayTeamNameTH){
			
			var seasonTitle = null;
			
			try{
				gp_progressBarOn();
				
				seasonTitle = "ปี : " + av_season + "&nbsp;แข่งขันกับ&nbsp;" + av_awayTeamNameTH;
				$("#seasonTitle").html(seasonTitle);
				$("#matchId").val(av_matchId);
				$("#season").val(av_season);
				$("#awayTeamNameTH").val(av_awayTeamNameTH);
				
				gp_progressBarOff();
				
			}catch(e){
				alert("lp_selectMatch :: " + e);
			}
		}
		
		function lp_openBookingPage(av_fieldZoneId, av_fieldZoneName, av_fieldZoneNameTicket){
			
			//var windowName 	= "";
			var myWindow	= null;
			var params		= null;
			
			try{
				
				if(gp_trim($("#matchId").val())==""){
					alert("ขออภัยคุณยังไมได้เลือกโปรแกรมการแข่งขัน");
					return;
				}
				
				gp_progressBarOn();
				
				params		= "&fieldZoneId=" 			+ av_fieldZoneId 
							//+ "&fieldZoneName=" 		+ av_fieldZoneName 
							//+ "&fieldZoneNameTicket=" 	+ av_fieldZoneNameTicket 
							+ "&matchId=" 				+ $("#matchId").val()
							+ "&season=" 				+ $("#season").val();
							//+ "&awayTeamNameTH=" 		+ $("#awayTeamNameTH").val();
				//windowName 	= "booking" + $("#matchId").val() + av_fieldZoneId;
				
				myWindow = window.open(gv_url + "?service=servlet.SeatReservationServlet&pageAction=getZoneDetail" + params, "_self");
				myWindow.focus();
				
				
				gp_progressBarOff();
			}catch(e){
				alert("lp_openBookingPage :: " + e);
			}
		}
		
		
	</script>
</head>

<body>	
	<form id="frm">
		<input type="hidden" id="service" 			name="service" 			value="servlet.SeatZoneServlet" />
		<input type="hidden" id="matchId" 			name="matchId" 			value="<%=seatZoneForm.getMatchId() %>" />
		<input type="hidden" id="season" 			name="season" 			value="<%=seatZoneForm.getSeason() %>" />
		<input type="hidden" id="awayTeamNameTH" 	name="awayTeamNameTH" 	value="<%=seatZoneForm.getAwayTeamNameTH() %>" />       
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
	            					<h4 class="alert-heading">กำหนด Zone ที่นั่ง/จำนวนที่นั่ง</h4>
	          					</div>
								<div class="row" style="padding-left: 15px;">
									<div class="col-sm-12">
										<section class="panel panel-default">
											<div class="panel-body">
						        				<table style='width:100%;' border="0">
													<tr>
						              					<td style='width:15%;padding:0px;vertical-align: top;'>
						              						<table>
						              							<tr>
						              								<td>
						              								<div style="width:200px;" align="left">
																		<span class="label label-inverse" style="width: 200px;height: 30px;text-align: center;padding-top: 10px;background-color: #656659;">
																			<B>ฤดูกาลแข่งขัน</B>
																		</span><br/>
																		<div id='cssmenu' style="min-height:auto;  background:#000000; ">
															                <ul>
															                <%
															                	List<String> 					seasonList 		= seatZoneForm.getSeasonList();
														             	        List<SeatZoneBean> 				matchList		= null;
														             	        String							listId			= "firstList";
														             			
														             				
														           				for(String season :seasonList){
														           					matchList = (List<SeatZoneBean>) seatZoneForm.getMatchMap().get(season);
															                %>
															                   <li class='has-sub'>
															                   	  <a href='javascript:void(0)' id="<%=listId %>">
																                   	  <span>
																                   	  	<img src="<%=imgURL%>/football01.png" style="padding-right: 5px;">
																                   	  		<%=season%>
																                   	  </span>
																                  </a>
															                      <ul>
															                      	<%
															                      		for(SeatZoneBean bean:matchList){
															                      	%>
																					<li onclick="">
																						<a href="javascript:void(0)" onclick="lp_selectMatch('<%=bean.getMatchId()%>', '<%=season%>', '<%=bean.getAwayTeamNameTH() %>')">
																							<%=bean.getAwayTeamNameTH() %>
																						</a>
																					</li>
																					<%}%>
															 					</ul>
															                   </li>
															                <% listId = "otherList"; }%>
															                </ul>
														                </div>
																	</div>
						              								</td>
						              							</tr>
						              						</table>
										   				</td>
						                   				<td style='width:80%;padding:0px !important;text-align: center;'>
						                  	 				<table style="width: 100%">
						                  	 					<tr>
						                  	 						<td style="text-align: center;vertical-align: top;">
						                  	 							<div class="panel panel-info">
																	    	<div class="panel-heading" align="left">
																				<h4 class="panel-title"  id="seasonTitle">
																					ปี :&nbsp;<%=seatZoneForm.getSeason()%>&nbsp;แข่งขันกับ&nbsp;<%=seatZoneForm.getAwayTeamNameTH()%>
																				</h4>
																			</div>
						     											</div>
						                  	 							<div class='sim-panel-result' style="padding:10px;">
									                        				<img alt="<%=seatZoneForm.FILE_NAME%>" 
														                    	 title="<%=seatZoneForm.FILE_NAME%>"
														                    	 src="/ticketWeb/upload/<%=seatZoneForm.getImages()%>" 
														                    	 border="0" 
														                    	 width="651px" 
														                    	 height="376px" />
								               							</div>
						                  	 						</td>
						                  	 						<td style="vertical-align: top;">
						                  	 							
						                  	 							<%
						                  	 							List<SeatZoneBean> 	fieldZoneList = seatZoneForm.getFieldZoneList();
						                  	 							
						                  	 							for(SeatZoneBean fieldZoneBean:fieldZoneList){
						                  	 							
						                  	 							%>
						                  	 							
						                  	 							<div>
						                  	 								<input  type="button" 
						                  	 										id="<%=fieldZoneBean.getFieldZoneName() %>" 
						                  	 										name="<%=fieldZoneBean.getFieldZoneName() %>" 
						                  	 										class="btn btn-info" 
						                  	 										style="width:250px;"
						                  	 										onclick="lp_openBookingPage('<%=fieldZoneBean.getFieldZoneId()%>', '<%=fieldZoneBean.getFieldZoneName()%>', '<%=fieldZoneBean.getFieldZoneNameTicket()%>')"
						                  	 										value="<%=fieldZoneBean.getFieldZoneName()%>" />
									          							</div>
									          							<%} %>
									          						</td>
						                  	 					</tr>
						                  	 				</table>
				                   						</td>
				                 					</tr>
				             					</table>	
											</section>
										</div>
									</div>
								</section>
							</section>
						</section> 
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