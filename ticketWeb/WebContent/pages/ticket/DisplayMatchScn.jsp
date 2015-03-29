<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.DisplayMatchDetailBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="displayMacthForm" class="th.go.ticket.app.enjoy.form.DisplayMacthForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>รายงานแสดงรายละเอียดต่างๆ ภายใน Match</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<link href="<%=cssURL%>/menu-styles2.css" rel="stylesheet">
	<script src="<%=jsURL%>/bootstrap.min.js" type="text/javascript"></script> 
	<script src="<%=jsURL%>/menu-script.js" type="text/javascript"></script>
	<script>
		
		var gv_service 		= null;
		var gv_url 			= '<%=servURL%>/EnjoyGenericSrv';
		
		
		$(document).ready(function(){
			gv_service 		= "service=" + $('#service').val();
			
			$('#menu1').ptMenu();
		});
	
	</script>
	
</head>
<body>
	<form id="frm" action="<%=servURL%>/EnjoyGenericSrv">
		<input type="hidden" id="service" 	name="service" value="servlet.DisplayMatchServlet" />  
		<div id="menu" style="width: 100%;background: black;">
			<%@ include file="/pages/menu/menu.jsp"%>
		</div>
		<div align="center" style="width:100%;position:relative;">
			<div style="height:100%; width:200px; position:absolute;margin:30px 0 0 0;">
				<B>ฤดูกาลแข่งขัน</B><br/>
				<div id='cssmenu' style="min-height:auto;  background:#000000; ">
	                <ul>
	                <%
	                	List<String> 					seasonList 		= displayMacthForm.getSeasonList();
             	        List<DisplayMatchDetailBean> 	matchList		= null;
             			
             				
           				for(String season :seasonList){
           					matchList = (List<DisplayMatchDetailBean>) displayMacthForm.getMatchMap().get(season);
	                %>
	                   <li class='has-sub'><a href='#'><span><%=season%></span></a>
	                      <ul>
	                      	<%
	                      		for(DisplayMatchDetailBean bean:matchList){
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
			<div style="position:absolute;height:100%;margin:20px 0 0 200px; padding:8px;width: 60%;">
				<iframe name="ttestt" src="<%=servURL%>/EnjoyGenericSrv?service=servlet.DisplayMatchDetailServlet&pageAction=new" scrolling="no"  
frameborder="0" width="1000" height="1000"></iframe>  
			</div>
		</div>
	</form>
</body>
</html>