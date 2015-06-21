<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.CancelSeatBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="cancelSeatForm" class="th.go.ticket.app.enjoy.form.CancelSeatForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>ยกเลิกตั๋วการแข่งขัน</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<style>
		.headerRow:hover{
		    cursor: pointer;
		}
		
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
			gp_progressBarOff();
			gv_service 		= "service=" + $('#service').val();
			
			if($("#resultSize").val() > 9){
				$('#tbl_result').fixedHeaderTable({
					footer: true
				});
			}
			
			
			 
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
			            beforeSend: gp_progressBarOn(),
			            success: function(data){
		            		gp_progressBarOff();
			            	window.location.replace('<%=pagesURL%>/CancelSeatScn.jsp');
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
			
			
			$('#btnSave').click(function(){ 
				var lv_params		= gv_service;  
				var lv_ticketIdList	= "";
				var la_chkCancel	= null;
				var la_hidTicketId	= null;
				
			    try{
			    	
			    	la_chkCancel 	= document.getElementsByName("i_chk_cancel");
			    	la_hidTicketId 	= document.getElementsByName("hidTicketId");
			    	
			    	for(var i=0;i<la_chkCancel.length;i++){
			    		if(la_chkCancel[i].checked==true){
			    			if(lv_ticketIdList==""){
			    				lv_ticketIdList = "'" + la_hidTicketId[i].value + "'";
			    			}else{
			    				lv_ticketIdList = lv_ticketIdList + ",'" + la_hidTicketId[i].value + "'";
			    			}
			    			
			    		}
			    	}
			    	
					/*$('#tbl_result tr').each(function (i, row) {
					    var $actualrow 	= $(row);
						var lv_ticketId	= "";
					    $checkbox 	= $actualrow.find('input:checked');
					    if ($checkbox.is(':checked')) {
					    	if (lv_ticketIdList != "") { lv_ticketIdList = lv_ticketIdList + ","; }
						    lv_ticketId 	= $actualrow.find("td").eq(2).text();

						    lv_ticketId 	= lv_ticketId.replace("<b>",  "");
						    lv_ticketId 	= lv_ticketId.replace("</b>", "");
					    	lv_ticketIdList = lv_ticketIdList + lv_ticketId;
					    }
					});	*/	

			    	lv_params 	+= "&pageAction=save&ticketIdList=" + lv_ticketIdList; 
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: lv_params,
			            beforeSend: gp_progressBarOn(),
			            success: function(data){
		            		gp_progressBarOff();
	            			alert("บันทึกข้อมูลเรียบร้อยแล้ว");
	            			$("#btnSearch").click();
	            			<%--window.location.replace('<%=pagesURL%>/CancelSeatScn.jsp');--%>
			            }
			        });
			    }catch(e){
			    	alert("lp_save_page :: " + e);
			    }				
			});

			
			$('#btnCancel').click(function(){ 
			    try{
					$('#tbl_result tr').each(function (i, row) {
					    var $actualrow 	= $(row);
					    $checkbox 	= $actualrow.find('input:checked');
					    if ($checkbox.is(':checked')) {
					    	$checkbox.attr('checked', false);
					    }
					});		
			    }catch(e){
			    	alert("lp_cancel_checkbox :: " + e);
			    }				
			}); 
			
			/*$('#season').change(function(){ 
				var pageAction			= "searchTeam";
				var lv_params			= gv_service;  
				try{
					lv_params 	+= "&pageAction=" + pageAction + "&season=" + this.value; 
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: lv_params,
			            beforeSend: gp_progressBarOn(),
			            success: function(data){
			            	var jsonObj 			= null;
			            	var status				= null;
			            	var errMsg				= null;
			            	try{
			            		gp_progressBarOff();
			            		jsonObj = JSON.parse(data);
			            		status	= jsonObj.status;
			            		if(status=="SUCCESS"){
			            			var select = $('#matchId');
			            			$('option', select).remove();
			            			
		            				var option = new Option("ไม่ระบุ", "");
		            			    select.append($(option));
		            			    $.each(jsonObj.teamList, function(idx, obj) {
			            				var option = new Option(obj.awayTeamNameTH, obj.matchId);
			            			    select.append($(option));
			            			});
			            		}else{
			            			errMsg = jsonObj.errMsg;
			            			alert(errMsg);
			            		}
			            	}catch(e){
			            		alert("in lp_changeSeason :: " + e);
			            	}
			            }
			        });
				}catch(err){
					alert("btnSearch :: " + err);
				}
				
			});*/
			
			$("#season").autocomplete({
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=getSeason"+"&season=" + gp_trim(request.term),
		                success: function( data, textStatus, jqXHR) {
		                    var items = data;
		                    response(items);
		                },
		                error: function(jqXHR, textStatus, errorThrown){
		                     alert( textStatus);
		                }
		            });
		          },
			      minLength: 0,//กี่ตัวอักษรถึงทำงาน
			      open: function() {
						//Data return กลับมาแล้วทำไรต่อ
			      },
			      close: function() {

			      },
			      focus:function(event,ui) {

			      },
			      select: function( event, ui ) {
			    	  
			      }
			});
			
			$( "#awayTeamNameTH" ).autocomplete({
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=getAwayTeamNameTH"+"&season=" + $("#season").val() + "&awayTeamNameTH=" + gp_trim(request.term),
		                success: function( data, textStatus, jqXHR) {
		                    var items = data;
		                    response(items);
		                },
		                error: function(jqXHR, textStatus, errorThrown){
		                     alert( textStatus);
		                }
		            });
		          },
			      minLength: 0,//กี่ตัวอักษรถึงทำงาน
			      open: function() {
						//Data return กลับมาแล้วทำไรต่อ
			      },
			      close: function() {

			      },
			      focus:function(event,ui) {

			      },
			      select: function( event, ui ) {
			    	  
			      }
			});
			
		});
		
		function lp_sumAmt(){
			
			var la_chkCancel 	= null;
			var la_amt			= null;
			var lo_totalAmt		= null;
			var lv_totalAmt		= 0.00;
			
			try{
				
				la_chkCancel 	= document.getElementsByName("i_chk_cancel");
				la_amt 			= document.getElementsByName("amt");
				lo_totalAmt		= document.getElementById("totalAmt");
				
				for(var i=0;i<la_chkCancel.length;i++){
					if(la_chkCancel[i].checked==true){
						lv_totalAmt = lv_totalAmt + parseFloat(la_amt[i].value);
					}
				}
				
				lo_totalAmt.innerHTML = gp_format_str(String(lv_totalAmt), 2);
				
			}catch(e){
				alert("lp_sumAmt :: " + e);
			}
		}
		
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.CancelSeatServlet" />
		<input type="hidden" id="resultSize" 	name="resultSize" value="<%=cancelSeatForm.getResultSize()%>" />
		<div id="menu" style="width: 100%;background: black;">
			<%@ include file="/pages/menu/menu.jsp"%>
		</div>
		<section class="vbox">
			<section>
				<section class="hbox stretch">
					<section id="content">
						<section class="vbox">
							<section class="scrollable padder">
								<div class="alert alert-block alert-error fade in container">
					            	<h4 class="alert-heading">เงื่อนไขค้นหาตั๋วฟุตบอล</h4>
					          	</div>
								
									<div class="container main-container round-sm padding-lg-h">
									
									        	<table width="100%" class="user-register-table user-search-table " border="0" cellpadding="5" cellspacing="5">
									        		<tr>
									        			<td align="right" width="150px;">
									        				ฤดูกาลแข่งขัน :&nbsp;
									        			</td>
									        			<td align="left" width="350px;">
									        				<input type="text" id="season" name="season" value="<%=cancelSeatForm.getSeason()%>" />
									        			</td>
									        			<td align="right" width="150px;">
									        				ทีมคู่แข่ง :&nbsp;
									        			</td>
									        			<td align="left" width="350px;">
									        				<input type="text" id="awayTeamNameTH" name="awayTeamNameTH" value="<%=cancelSeatForm.getAwayTeamNameTH()%>" />
									        			</td>
									        		</tr>
									        		<tr>
									        			<td align="right" width="150px;">
									        				Zone :&nbsp;
									        			</td>
									        			<td align="left" width="350px;">
									        				<select id="fieldZoneId" name="fieldZoneId">
									        					<option value="">ไม่ระบุ</option>
									        					<%  List<CancelSeatBean> 	fieldZoneList = cancelSeatForm.getFieldZoneList();
									        						for(CancelSeatBean beanZone:fieldZoneList){ %>
									        						<option value="<%=beanZone.getFieldZoneId()%>" <%if(cancelSeatForm.getFieldZoneId().equals(beanZone.getFieldZoneId())){%>selected="selected"<%}%>>
									        							<%=beanZone.getFieldZoneName()%>
									        						</option>
									        					<%} %>
									        				</select>
									        			</td>
									        			<td align="right" width="150px;">
									        				Ticket Id  : &nbsp;
									        			</td>
									        			<td align="left" width="350px;">
									        				<input type='text' id="ticketId" name='ticketId' maxlength="17" value="<%=cancelSeatForm.getTicketId()%>" />
									        			</td>
									        		</tr>
									        		<tr>
									        			<td align="right" width="150px;">
									        				เลขที่นั่ง  : &nbsp;
									        			</td>
									        			<td align="left" colspan="3">
									        				<input type='text' id="seatingNoBegin" name='seatingNoBegin date' maxlength="10" value="<%=cancelSeatForm.getSeatingNoBegin()%>" />
									        				&nbsp; - &nbsp;
									        				<input type='text' id="seatingNoEnd" name='seatingNoEnd date' maxlength="10" value="<%=cancelSeatForm.getSeatingNoEnd()%>" />
									        				&nbsp;
									        				<span id="inValidSpan"></span>
									        				<input type="button" id="btnSearch" class='btn btn-primary pull-right padding-sm' style="margin-right:12px; padding-right:24px; padding-left:24px;" value='ค้นหา'/>
										        				<input type="button" id="btnReset" class='btn pull-right padding-sm'  style="margin-right:12px" value='เริ่มใหม่' />
									        			</td>
									        		</tr>
									        	</table>
								        	</div>

							</section>
							<br>
							
							<section class="scrollable padder">
								
					          	<div class="container main-container round-sm padding-no" >
									<div id="seasonTitle" class="padding-md round-sm season-title-head" style="margin-bottom:4px !important">
										<h6 class="panel-title" style="font-size:1.0em">รายละเอียดตั๋วฟุตบอล</h6>
									</div>
									
								
												<div class="datagrid round-sm" style="max-height:250px; overflow:auto;border:1px solid #ccc !important">	
													<table class="sim-panel-result-table" id="tbl_result" border="1" width="100%">
														<thead> 
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
														</thead>
														<tfoot>
															<tr>
																<td align="right" colspan="8">
																	<B>รวมเงิน</B>&nbsp;<span id="totalAmt">0.00</span>
																</td>
															</tr> 
														</tfoot>
														<tbody>
															<%
															List<CancelSeatBean>  	list			=   cancelSeatForm.getResultList();
															CancelSeatBean 			bean			=   null;
															int 					rowNumber		=   0;
															 
															if(list.size()>0){
																for(int i=0;i<list.size();i++){
																	bean = list.get(i);
																	rowNumber = i+1; 
																
																%>
																 <tr>
																	<td align="center">
																		<input type="checkbox" id="i_chk_cancel" name="i_chk_cancel" class="i_chk_cancel" onclick="lp_sumAmt();" />
																	</td>
																	<td align="center">
																		<%=rowNumber%>
																	</td>
																	<td align="center">
																		<%=bean.getTicketId()%>
																		<input type="hidden" name="hidTicketId" value="<%=bean.getTicketId()%>" />
																	</td>
																	<td align="center">
																		<%=bean.getSeatingNo()%>
																	</td>
																	<td align="center">
																		<%=bean.getSeason()%>
																	</td>
																	<td align="center">
																		<%=bean.getAwayTeamNameTH()%>
																	</td>
																	<td align="center">
																		<%=bean.getBookingTypeName()%>
																	</td>
																	<td align="right">
																		<%=bean.getBookingPrices()%>
																		<input type="hidden" name="amt" value="<%=bean.getBookingPrices()%>" />
																	</td>
																</tr> 
																<% } %>
															<%}else{ %>
																<tr align="center">
																	<td align="center" colspan="8">
																		<B>ไม่พบข้อมูล</B>
																	</td>
																</tr> 
															<%} %>
														</tbody>
													</table>	
												</div>
									</div>
									<div align="center" class="panel-body">
				        				<input type="button" id="btnSave"   class='btn btn-danger' value='บันทึก'/>&nbsp;&nbsp;&nbsp;
				        				<input type="button"  id="btnCancel" class='btn btn-danger' value='เริ่มเลือกใหม่' />
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