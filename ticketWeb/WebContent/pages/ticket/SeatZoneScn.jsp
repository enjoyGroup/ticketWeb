<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.SeatZoneBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="seatZoneForm" class="th.go.ticket.app.enjoy.form.SeatZoneForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>กำหนด Zone ที่นั่ง/จำนวนที่นั่ง</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>	
	<link href="<%=cssURL%>/menu-styles2.css" rel="stylesheet">
	<script src="<%=jsURL%>/bootstrap.min.js" type="text/javascript"></script> 
	<script src="<%=jsURL%>/menu-script.js" type="text/javascript"></script>
	<script> 
		var gv_service 		= null;
		var gv_url 			= '<%=servURL%>/EnjoyGenericSrv';
		
		$(document).ready(function(){
			gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			
			$('#menu1').ptMenu();
			
			gp_progressBarOff();
		});
	</script>
</head>

<body>	
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.SeatZoneServlet" />    
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
														             			
														             				
														           				for(String season :seasonList){
														           					matchList = (List<SeatZoneBean>) seatZoneForm.getMatchMap().get(season);
															                %>
															                   <li class='has-sub'><a href='#'><span><img src="/ticketWeb/images/football01.png" style="padding-right: 5px;"><%=season%></span></a>
															                      <ul>
															                      	<%
															                      		for(SeatZoneBean bean:matchList){
															                      	%>
																					<li onclick="">
																						<a href="<%=servURL%>/EnjoyGenericSrv?service=servlet.DisplayMatchDetailServlet&pageAction=onGetData&matchId=<%=bean.getMatchId()%>&season=<%=season%>&awayTeamName=<%=bean.getAwayTeamNameTH() %>" target="ttestt">
																							<%=bean.getAwayTeamNameTH() %>
																						</a>
																					</li>
																					<%}%>
															 					</ul>
															                   </li>
															                <%}%>
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
						                  	 							<div class='sim-panel-result' style="padding:10px;">
									                        				<img src="/ticketWeb/images/Soccer.jpg">
								               							</div>
						                  	 						</td>
						                  	 						<td style="vertical-align: top;">
						                  	 							<div>
								               								<button ondblclick="return false;" id="btnWR" name="btnWR"	class="btn btn-info" style="width: 250px;">WR</button> 
									          							</div>
									          							<div>
									          								<button ondblclick="return false;" id="btnWL" name="btnWL"  class="btn btn-warning" style="width: 250px;">WL</button>
									          							</div>
									          							<div>
									          								<button ondblclick="return false;" id="btnE1" name="btnE1"  class="btn btn-success" style="width: 250px;">E1</button>
									          							</div>
									          							<div>
									          								<button ondblclick="return false;" id="btnE2" name="btnE2"  class="btn btn-danger" style="width: 250px;">E2</button>
									          							</div>
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
	</form>
</body>
</html>