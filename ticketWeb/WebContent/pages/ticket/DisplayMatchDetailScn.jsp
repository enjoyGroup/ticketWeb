<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.DisplayMatchDetailBean,th.go.ticket.app.enjoy.utils.EnjoyUtils"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="displayMatchDetailForm" class="th.go.ticket.app.enjoy.form.DisplayMatchDetailForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<title>รายงานแสดงรายละเอียดต่างๆ ภายใน Match</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	
	<style>
		.link:hover{
			cursor: pointer;
			color: green;
			font-weight:bold;
		
		}
		
		.link{
			color: black;
			font-weight:bold;
		
		}
		
		.unLink{
			color: red;
			font-weight:bold;
		
		}
		
		.unLink:hover{
			cursor: default;
			font-weight:bold;
		
		}
		
		.headerRow{
			background-color: #473636; 
			color: white;
		}
		
		.headerRow:hover{
		    cursor: pointer;
		}
		
	</style>
	<script>
		
		var gv_service 		= null;
		var gv_url 			= '<%=servURL%>/EnjoyGenericSrv';
		var gv_dataFlow		= "";
		google.load("visualization", "1", {packages:["corechart"]});
		
		
		$(document).ready(function(){
			gv_service 		= "service=" + $('#service').val();
			gv_dataFlow	= '<%=displayMatchDetailForm.getJsonString()%>';
			
			if(document.getElementById('piechart')!=null){
				drawChart();
			}
			
		});
		
		function drawChart() {
			var lv_str 			= "";
	    	var jsonObj 		= null;
	        var data 			= null;
	        var options 		= null;
	        var chart 			= null;
	        
	        try{
	        	jsonObj 	= JSON.parse(gv_dataFlow);
	        	
	        	$.each(jsonObj.fieldZoneNameList, function(idx, obj) {
		    		lv_str += ',["' + obj.fieldZoneName + '", ' + obj.tatalMoney + ']';
				});
	        	
	        	if(lv_str=="") return;
	        	
	        	data = eval("google.visualization.arrayToDataTable(["
	        														+ "['Sale', 'per Match']"
	        	                                	          		+ lv_str
	        	                                	        	+ "])");
	        	
	        	options = {
			      	         title: 'รายงานแสดงรายละเอียดต่างๆ ภายใน Match'
			      	      };
	        	chart = new google.visualization.PieChart(document.getElementById('piechart'));
	        	chart.draw(data, options);	
	        }catch(e){
	        	alert("drawChart " + e);
	        }
	      }
	
	</script>
	
</head>
<body>
	<form id="frm" action="<%=servURL%>/EnjoyGenericSrv">
		<input type="hidden" id="service" 	name="service" value="servlet.DisplayMatchDetailServlet" />  
		<section class="vbox">
			<section>
				<section class="hbox stretch">
					<section id="content">
						<section class="vbox">
							<section class="scrollable padder">
					          	<div class="row">
									<div class="col-sm-12">
										<section class="panel panel-default">
											<div class="panel-body" align="left">
												<!-- Begin contents -->
												<div align="left">
													<div id="seasonTitle" style="font-weight: bold;">
														<%=displayMatchDetailForm.getAwayTeamName()%>
													</div><br/><br/>
													
													<%
													List<DisplayMatchDetailBean>	headerTicketTypeList = displayMatchDetailForm.getHeaderTicketTypeList();
													
													if(headerTicketTypeList!=null && headerTicketTypeList.size() > 0){
													%>
													
													<table class="table sim-panel-result-table" id="resultTab" border="1" style="border: 2; width:100%">
														<tr>
															<th style="text-align: center;" rowspan="2" valign="middle"><B>ลำดับ</B></th>
															<th style="text-align: center;" rowspan="2"><B>Zone<br/>ที่นั่ง</B></th>
															
															<%
																			
																for(DisplayMatchDetailBean bean:headerTicketTypeList){
															%>
															<th style="text-align: center;" colspan="2"><B><%=bean.getBookingTypeName()%></B></th>
															<%
																}
															%>
															<th style="text-align: center;" rowspan="2" valign="middle"><B>รวมเงิน</B></th>
														</tr>
														<tr>
															<%
																for(DisplayMatchDetailBean bean:headerTicketTypeList){
															%>
															<th style="text-align: center;"><B>บัตร</B></th>
															<th style="text-align: center;"><B>เงิน</B></th>
															<%
																}
															%>
														</tr>
														
														<%
															List<String>				fieldZoneNameList 	= displayMatchDetailForm.getFieldZoneNameList();
															int							rowSeq				= 0;
															Map							detailTicketMap		= null;
															DisplayMatchDetailBean		detailData			= null;
															Double						sumTatalMoney		= 0.00;
														
															for(String fieldZoneName:fieldZoneNameList){
																sumTatalMoney = (Double) displayMatchDetailForm.getSumTatalMoneyMap().get(fieldZoneName);
																rowSeq++;
														%>
														<tr>
															<td align="center"><%=rowSeq%></td>
															<td align="center"><%=fieldZoneName%></td>
															
															<%
																for(DisplayMatchDetailBean bean:headerTicketTypeList){
																				
																	detailTicketMap = (Map) displayMatchDetailForm.getDetailOfMatchMap().get(fieldZoneName);
																	
																	if(detailTicketMap.containsKey(bean.getBookingTypeId())){
																		detailData = (DisplayMatchDetailBean) detailTicketMap.get(bean.getBookingTypeId());
															%>
																		<td style="text-align: center;"><%=detailData.getTotalSeating() %></td>
																		<td style="text-align: right: ;" align="right"><%=EnjoyUtils.convertFloatToDisplay(detailData.getTatalMoney(), 2) %></td>
															<%
																	}else{
															%>
																		<td style="text-align: center;">0</td>
																		<td style="text-align: center;">0.00</td>
															<%
																	}
																}
															
															%>
															<td style="text-align: right: ;" align="right"><%=EnjoyUtils.convertFloatToDisplay(String.valueOf(sumTatalMoney), 2) %></td>
														</tr>
														<%}%>
													</table>
												</div>
												<div id="piechart" style="width: 900px; height: 500px;"></div>
												<%}else{ %>
													<div style="width: 100%;">
														<span>ไม่พบข้อมูล</span>
													</div>
												<%} %>
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
</body>
</html>