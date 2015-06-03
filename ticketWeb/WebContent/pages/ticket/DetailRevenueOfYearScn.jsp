<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.DetailRevenueOfYearBean,th.go.ticket.app.enjoy.utils.EnjoyUtils"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="detailRevenueOfYearForm" class="th.go.ticket.app.enjoy.form.DetailRevenueOfYearForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<title>รายงานแสดงรายละเอียดรายได้ประจำปี</title>
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
			gp_progressBarOn();
			
			gv_service 	= "service=" + $('#service').val();
			gv_dataFlow	= '<%=detailRevenueOfYearForm.getDataFlow()%>';
			
			//$('#menu1').ptMenu();
			
			//alert(gv_dataFlow);
			drawChart();
			
			gp_progressBarOff();
			
		});
		
		function drawChart() {
			var lv_str 			= "";
	    	var jsonObj 		= null;
	        var data 			= null;
	        var options 		= null;
	        var chart 			= null;
	        
	        try{
	        	jsonObj 	= JSON.parse(gv_dataFlow);
	        	
	        	$.each(jsonObj.detail, function(idx, obj) {
		    		lv_str += ',["' + obj.awayTeamName + '", ' + obj.bookingPrices + ']';
				});
	        	
	        	if(lv_str=="") return;
	        	
	        	data = eval("google.visualization.arrayToDataTable(["
	        														+ "['Match', 'per Year']"
	        	                                	          		+ lv_str
	        	                                	        	+ "])");
	        	
	        	options = {
			      	         title: 'รายงานแสดงรายละเอียดรายได้ประจำปี'
			      	      };
	        	chart = new google.visualization.PieChart(document.getElementById('piechart'));
	        	chart.draw(data, options);	
	        }catch(e){
	        	alert("drawChart " + e);
	        }
	      }
		
		function lp_changeSeason(av_season){
			
			var lo_hidSeason 	= null;
			var lo_seasonTitle	= null;
			
			try{
				lo_hidSeason 	= document.getElementById("hidSeason");
				lo_seasonTitle 	= document.getElementById("seasonTitle");
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=changeSeason&" + $('#frm').serialize() + "&season=" + av_season,
		            beforeSend: gp_progressBarOn(),
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var season				= null;
		            	var errMsg				= null;
		            	var index				= 1;
		            	
		            	try{
		            		gp_progressBarOff();
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		//alert(status);
		            		if(status=="SUCCESS"){
		            			season				= jsonObj.season;
		            			lo_hidSeason.value 	= season;
		            			
		            			lo_seasonTitle.innerHTML = "ปี : " + season;
		            			
		            			lp_deleteTab("seasonTab");
		            			lp_deleteTab("resultTab");
		            			
		            			
		            			//alert(JSON.stringify(jsonObj.detail));
		            			
		            			$.each(jsonObj.seasonList, function(idx, obj) {
		            				//alert(obj.season);
		            				lp_addTableSeason(obj.season);
		            			});
		            			
		            			$.each(jsonObj.detail, function(idx, obj) {
		            				//alert(obj.awayTeamName + " " + obj.totalSeating + " " + obj.bookingPrices);
		            				lp_addTableResultTab(index++, obj.awayTeamName, obj.totalSeating, obj.bookingPrices, obj.matchId);
		            			});
		            			
		            			//วาดกราฟ
		            			gv_dataFlow = JSON.stringify(jsonObj);
		            			document.getElementById("piechart").innerHTML = "";
		            			drawChart();
		            			
		            		}else{
		            			errMsg = jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_changeSeason :: " + e);
		            	}
		            }
		        });
			}catch(e){
				alert("lp_changeSeason :: " + e);
			}
		}
		
		function lp_deleteTab(av_idTab){
			
			var lo_table		= null;
			var lv_row			= 0;
			var lv_length		= 0;
			
			try{
				
				lo_table 	= eval('document.getElementById("' + av_idTab + '")');
				lv_row		= av_idTab=="seasonTab"?0:1;
				lv_length	= lo_table.rows.length;
				
				for(var i=lv_row;i<lv_length;i++){
					lo_table.deleteRow(lv_row); 
				}
				
			}catch(e){
				alert("lp_deleteTab :: " + e);
			}
			
		}
		
		function lp_addTableSeason(av_season){
			
			var lo_table		= null;
			var lv_length 	 	= null;
			var row 		 	= null;
			var cell1 		 	= null;
			var lo_hidSeason 	= null;
			var lv_class		= "";
			var lv_click		= "";
			
			try{
				lo_table 		= document.getElementById("seasonTab");
				lv_length 		= lo_table.rows.length;
				row 			= lo_table.insertRow(lv_length);
				cell1 			= row.insertCell(0);
				lo_hidSeason 	= document.getElementById("hidSeason");
				
				cell1.align		= "center"; 
				cell1.title		= av_season; 
				
				if(av_season==lo_hidSeason.value){
					lv_class 	= "unLink";
				}else{
					lv_class 	= "link";
					//lv_click	= 'lp_changeSeason(\''+av_season+'\')';
					cell1.onclick = function() { 
						lp_changeSeason(av_season);
			        };
				}
				
				cell1.className	= lv_class;
				
				cell1.innerHTML = av_season;
				
				//alert(cell1.outerHTML);
				
				
			}catch(e){
				alert("lp_addTableSeason :: " + e);
			}
		}
		
		function lp_addTableResultTab(av_index, av_awayTeamName, av_totalSeating, av_bookingPrices, av_matchId){
			var lo_table		= null;
			var lv_length 	 	= null;
			var row 		 	= null;
			var cell1 		 	= null;
			var cell2 		 	= null;
			var cell3 		 	= null;
			var cell4 		 	= null;
			
			try{
				lo_table 		= document.getElementById("resultTab");
				lv_length 		= lo_table.rows.length;
				row 			= lo_table.insertRow(lv_length);
				
				row.className	= "rowSelect";
				
				row.onclick = function() { 
					lp_getReportByTicketType($("#hidSeason").val(), av_matchId, av_awayTeamName);
		        };
				
				cell1 			= row.insertCell(0);
				cell2 			= row.insertCell(1);
				cell3 			= row.insertCell(2);
				cell4 			= row.insertCell(3);
				
				cell1.align		= "center"; 
				cell2.align		= "center"; 
				cell3.align		= "center"; 
				cell4.align		= "right"; 
				
				cell1.innerHTML = av_index;
				cell2.innerHTML = av_awayTeamName;
				cell3.innerHTML = av_totalSeating;
				cell4.innerHTML = gp_format_str(av_bookingPrices, 2);
				
				
				
			}catch(e){
				alert("lp_addTableResultTab :: " + e);
			}
			
		}
		
		function lp_getReportByTicketType(av_season, av_matchId, av_awayTeamNameTH){
			
			try{
				
				window.location = gv_url + "?service=servlet.DisplayMatchServlet&pageAction=getReportByTicketType&season=" + av_season + "&matchId=" + av_matchId + "&awayTeamName=" + av_awayTeamNameTH;
				
			}catch(e){
				alert("lp_getReportByTicketType :: " + e);
			}
		}
	
	</script>
	
</head>
<body>
	<form id="frm" action="<%=servURL%>/EnjoyGenericSrv">
		<input type="hidden" id="service" 	name="service" value="servlet.DetailRevenueOfYearServlet" />  
		<input type="hidden" id="hidSeason" name="hidSeason" value="<%=detailRevenueOfYearForm.getSeason() %>" /> 
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
					            	<h4 class="alert-heading">รายงานแสดงรายละเอียดรายได้ประจำปี</h4>
					          	</div>
					          	<div class="row">
									<div class="col-sm-12">
										<section class="panel panel-default">
											<div class="panel-body" align="left">
												<!-- Begin contents -->
												<table border="0" width="100%">
													<tr>
														<td align="left" valign="top">
															<div style="width:200px;height:30px;background-color: rgb(68, 194, 122);border-left-width: 5px;-webkit-border-radius: 4px;border-left-color: #2b542c;" align="center" class="error-notice" >
																<span class='topic-head'><B>ฤดูกาลแข่งขัน</B></span><br/>
																<table class="table sim-panel-result-table" id="seasonTab" border="0" style="width:200px;" align="left">
																	<%
																	List<String>  list			=   detailRevenueOfYearForm.getSeasonList();
																	 
																	if(list.size()>0){
																		for(int i=0;i<list.size();i++){
																		
																		%>
																		 <tr class="oaerror success">
																		 
																		 	<%if(list.get(i).equals(detailRevenueOfYearForm.getSeason())){%>
																		 		<td class="unLink" align="center" title="<%=list.get(i)%>" >
																					<%=list.get(i)%>
																				</td>	
																		 	<%}else{%>
																		 		<td class="link" onclick="lp_changeSeason('<%=list.get(i)%>');" align="center" title="<%=list.get(i)%>">
																					<%=list.get(i)%>
																				</td>
																		 	<%}%>
																		</tr> 
																		<% } 
																	} %>
																</table>
															</div>
														</td>
														<td align="left" valign="top">
															<div class="row">
        														<div class="panel panel-primary filterable">
            														<div id="seasonTitle" class="panel-heading">
																		<h6 class="panel-title">ปี : <%=detailRevenueOfYearForm.getSeason() %></h6>
																	</div>
																	<table class="table" id="resultTab" style="width:100%;">
																		<thead>
																			<tr id="headRow">
																				<th width="10%" style="text-align: center;"><B>ลำดับ</B></th>
																				<th width="30%" style="text-align: center;"><B>Match การแข่งขัน</B></th>
																				<th width="30%" style="text-align: center;"><B>จำนวนคนเข้าดู</B></th>
																				<th width="30%" style="text-align: center;"><B>จำนวนเงิน</B></th>
																			</tr>
																		</thead>
																		<tbody>
																		<%
																		List<DetailRevenueOfYearBean>  	detailList			= detailRevenueOfYearForm.getDetailList();
																		DetailRevenueOfYearBean			detail				= null;
																		int								seq					= 0;
																		 
																		if(detailList.size()>0){
																			for(int i=0;i<detailList.size();i++){
																				detail = detailList.get(i);
																				seq++;
																			%>
																			 <tr class="rowSelect" onclick="lp_getReportByTicketType('<%=detailRevenueOfYearForm.getSeason()%>', '<%=detail.getMatchId()%>', '<%=detail.getAwayTeamNameTH()%>');">
																				<td align="center">
																					<B><%=seq%></B>
																				</td>
																				<td align="center">
																					<%=detail.getAwayTeamNameTH()%>
																				</td>
																				<td align="center">
																					<%=detail.getTotalSeating()%>
																				</td>
																				<td align="right">
																					<%=EnjoyUtils.convertFloatToDisplay(detail.getBookingPrices(), 2) %>
																				</td>
																			</tr> 
																			<% } 
																		} %>
																	</tbody>
																</table>
																</div>
															</div>
															<div id="piechart" style="width: 900px; height: 500px;"></div>
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