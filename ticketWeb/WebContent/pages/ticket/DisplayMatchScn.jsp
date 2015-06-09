<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.DisplayMatchDetailBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="displayMacthForm" class="th.go.ticket.app.enjoy.form.DisplayMacthForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<title>รายงานแสดงรายละเอียดต่างๆ ภายใน Match</title>
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
		
		function lp_getReportByTicketType(av_matchId, av_season, av_awayTeamNameTH){
			
			try{
				gp_progressBarOn();
				window.open(gv_url + "?service=servlet.DisplayMatchDetailServlet&pageAction=onGetData&matchId=" + av_matchId + "&season=" + av_season + "&awayTeamName=" + av_awayTeamNameTH, "ttestt" );
				gp_progressBarOff();
			}catch(e){
				alert("lp_getReportByTicketType :: " + e);
			}
		}
	
	</script>
	
</head>
<body>
	<form id="frm" action="<%=servURL%>/EnjoyGenericSrv">
		<input type="hidden" id="service" 	name="service" value="servlet.DisplayMatchServlet" />  
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
					            	<h4 class="alert-heading">รายงานรายได้ตามประเภทตั๋ว</h4>
					          	</div>
					          	<div class="row">
									<div class="col-sm-12">
										<section class="panel panel-default">
											<div class="panel-body" align="left">
												<!-- Begin contents -->
												<table border="0" width="100%">
													<tr>
														<td align="left" valign="top">
															<div style="width:200px;" align="left">
																<span class="label label-inverse" style="width: 200px;height: 30px;text-align: center;padding-top: 10px;background-color: #656659;">
																	<B>ฤดูกาลแข่งขัน</B>
																</span>
																<br/>
																<div id='cssmenu' style="min-height:auto;  background:#000000; ">
													                <ul>
													                <%
													                	List<String> 					seasonList 		= displayMacthForm.getSeasonList();
												             	        List<DisplayMatchDetailBean> 	matchList		= null;
												             	        String							listId			= "firstList";
												             			
												             				
												           				for(String season :seasonList){
												           					matchList = (List<DisplayMatchDetailBean>) displayMacthForm.getMatchMap().get(season);
													                %>
													                   <li class='has-sub'>
													                   		<a href='#' id="<%=listId %>">
														                   	  <span>
														                   	  	<img src="<%=imgURL%>/football01.png" style="padding-right: 5px;">
														                   	  		<%=season%>
														                   	  </span>
														                  </a>									                   	 
													                      <ul>
													                      	<%
													                      		for(DisplayMatchDetailBean bean:matchList){
													                      	%>
																			<li onclick="">
																				<a href="#" onclick="lp_getReportByTicketType('<%=bean.getMatchId()%>', '<%=season%>', '<%=bean.getAwayTeamNameTH() %>')">
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
														<td align="left" valign="top">
															<iframe name="ttestt" 
																	src="<%=servURL%>/EnjoyGenericSrv?service=servlet.DisplayMatchDetailServlet&pageAction=new" 
																	scrolling="no"  
																	frameborder="0" 
																	width="1000" 
																	height="1000">
															</iframe>
														</td>
													</tr>
												</table>
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
		<div align="center" class="FreezeScreen" style="display:none;">
	        <center>
	        	<img id="imgProgress" valign="center" src="<%=imgURL%>/loading36.gif" alt="" />
	        	<span style="font-weight: bold;font-size: large;color: black;">Loading...</span>
	        </center>
	    </div>
	</form>
</body>
</html>