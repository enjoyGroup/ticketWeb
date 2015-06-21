<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.SummaryRevenueOfYearBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="summaryRevenueOfYearForm" class="th.go.ticket.app.enjoy.form.SummaryRevenueOfYearForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<title>รายงานสรุปรายได้ประจำปี</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<style>
		.headerRow:hover{
		    cursor: pointer;
		}
		
		div.dhtmlx_window_active,
		div.dhx_modal_cover_dv {
    		position: fixed !important;
		}	
	</style>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script type="text/javascript">
		
		google.load("visualization", "1", {packages:["corechart"]});
	    //google.setOnLoadCallback(drawChart);
		
		var gv_service 		= null;
		var gv_url 			= '<%=servURL%>/EnjoyGenericSrv';
		
		$(document).ready(function(){
			gp_progressBarOn();
			
			//$('#menu1').ptMenu();
			
			drawChart();
			
			$("#tbl_result").tablesorter(); 
			$( "#tbl_result th" ).resizable();
			
			gp_progressBarOff();
		});
		
		function drawChart() {
	    	
	    	var lv_str 			= "";
	    	var jsonObj 		= null;
	    	var lv_dataFlow		= '<%=summaryRevenueOfYearForm.getDataFlow() %>';//alert(lv_dataFlow);
	    	
	    	try{
	    		jsonObj 	= JSON.parse(lv_dataFlow);
	    		if(jsonObj.dataFlow.length > 0){
		    		$.each(jsonObj.dataFlow, function(idx, obj) {
			    		lv_str += ',["ปี ' + obj.season + '", ' + obj.bookingPrice + ',  "#b87333"]';
					});
		    		
		    		
			    	var data = eval( 'google.visualization.arrayToDataTable(['
									+ '["Element", "จำนวนเงิน", { role: "style" } ]'		    	                                                         
									+ lv_str
			    	                + '])');
			    	
					var view = new google.visualization.DataView(data);
					view.setColumns([0, 1,
					                 { calc: "stringify",
					                   sourceColumn: 1,
					                   type: "string",
					                   role: "annotation" },
					                 2]);
					var options = {
					  title: "รายงานสรุปรายได้ประจำปี",
					  width: 600,
					  height: 400,
					  bar: {groupWidth: "95%"},
					  legend: { position: "none" },
					};
					var chart = new google.visualization.ColumnChart(document.getElementById("columnchart_values"));
					chart.draw(view, options);
	    		}
		    	
	    	}catch(e){
	    		alert("drawChart :: " + e);
	    	}
	  	}
		
		function lp_getDetailFromSeason(av_val){
			
			try{
				
				window.location = gv_url + "?service=servlet.DetailRevenueOfYearServlet&pageAction=getDetailFromSeason&season=" + av_val;
				
			}catch(e){
				alert("lp_getDetailFromSeason :: " + e);
			}
		}
		
  </script>
</head>
<body>
	<form id="frm" action="<%=servURL%>/EnjoyGenericSrv">
		<input type="hidden" id="service" 	name="service" value="servlet.DetailRevenueOfYearServlet" />
		<div id="menu" style="width: 100%;background: black;">
			<%@ include file="/pages/menu/menu.jsp"%>
		</div>
		<section class="vbox">
			<section>
				<section class="hbox stretch">
					<section id="content">
						<section class="vbox">
							<section class="scrollable padder">
								<div class="container alert alert-block alert-error fade in">
					            	<h4 class="alert-heading">รายงานสรุปรายได้ประจำปี</h4>
					          	</div>
					          	<div class="container main-container round-sm padding-xl ">
					          	
					          	
												<div  class=" col-md-6  col6-1st">
													<div class="datagrid" style="/*width:70%;border:1px solid #ccc*/">
														<table class="tablex" id="tbl_result" style="width:80%;" align="center" border="1">
															<thead>
																<tr style="background-color:#A2F9D4;">
															    	<th style="width: 10%; text-align: center;">ลำดับ</th>
															   		<th style="width: 50%; text-align: center;">ปีการแข่งขัน</th>
															   		<th style="width: 40%; text-align: center;">จำนวนเงิน</th>
															    </tr>
															</thead>
															<tbody>
																<%
																List<SummaryRevenueOfYearBean>  list			=   summaryRevenueOfYearForm.getResultList();
																SummaryRevenueOfYearBean 		bean			=   null;
																int 							rowNumber		=   0;
																 
																if(list.size()>0){
																	for(int i=0;i<list.size();i++){
																		bean = list.get(i);
																		rowNumber = i+1; 
																	
																	%>
																	 <tr class="rowSelect" onclick="lp_getDetailFromSeason('<%=bean.getSeason()%>');">
																		<td align="center">
																			<B><%=rowNumber%></B>
																		</td>
																		<td align="center" >
																			<B><%=bean.getSeason()%></B>
																		</td>
																		<td align="right">
																			<B><%=bean.getBookingPrice()%></B>
																		</td>
																	</tr> 
																	<% } 
																}else{ %>
																<tr align="center">
																	<td align="center" colspan="3">
																		<B>ไม่พบข้อมูล</B>
																	</td>
																</tr> 
																<%} %>
															</tbody>
														</table>
													</div>
													</div>
												<div class="col6-2nd line-left  col-md-6 " id="columnchart_values" ></div>
									
									
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