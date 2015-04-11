<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.SeatingDetailBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="seatingDetailForm" class="th.go.ticket.app.enjoy.form.SeatingDetailForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>กำหนด Zone ที่นั่ง/จำนวนที่นั่ง</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>	
	
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
		var gv_mode         = "UpdateSeason";
		var gv_delList      = [];
		
		$(document).ready(function(){ 
			$('#menu1').ptMenu();
			gv_service 	= "service=" + $('#service').val();
	 
		});
		
	    
		
		function lp_save_page(){
			 
		    
		}
		
		function lp_reset_page(){ 
			 
		    
		}
		
	 
  
	</script>
	
</head>
<body>
	
<form id="frm">
<input type="hidden" id="service" 	name="service" value="servlet.SeatingDetailServlet" />    
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
				<div class="row">
					<div class="col-sm-12">
						<section class="panel panel-default">
							<header class="panel-heading font-bold">รายชื่อ Zone</header>
							<div class="panel-body">
						        <table style='width:100%;' class='table'>
									<tr>
						              <td style='width:15%;padding:0px !important'>
						                  <div class='sim-panel-result' style="padding:10px;">
						                      <table class="table sim-panel-result-table" id="result_season">
												<tr><th>ปีการแข่งขัน</th> </tr>
										       <!--  ZONE -->
									 			<tr><td align="center" class="link" onclick="lp_add_row_season();"><a href="#">+เพิ่ม zone ที่นั่ง</a></tr>
									 		</table>
										   </td>
						                   <td style='width:80%;padding:0px !important'>
						                    <div style="padding:10px;" >
						                         <input type='text' id="seasonNew" name='seasonNew' value="" class="inputDisabled" disabled="disabled" > 
						                    </div>
						                    <br>
						                  	 <div class='sim-panel-result' style="padding:10px;">
						                        <table class="table sim-panel-result-table" id="result_match">
													<tr>
							                            <th>ลำดับ</th>
							                            <th>ทีมคู่แข่งภาษาไทย</th>
							                            <th>ทีมคู่แข่งภาษาอังกฤษ</th>
							                            <th>วันที่แข่ง</th>
							                            <th>เวลาที่แข่ง</th>
							                            <th>Action</th>
						                          	</tr>
												<%
													List<SeatingDetailBean>  	detailList			= seatingDetailForm.getSeatingDetailBeans();
													SeatingDetailBean			detail				= null;
													int						    seq					= 0;
																 
													if(detailList.size()>0){
														for(int i=0;i<detailList.size();i++){
															detail = detailList.get(i);
															seq++;
													  %>
														 <tr>
															<td align="center">
																<%=seq%>
															</td>
															<td align="center">
																<input type="text" id="" name="" value=""/>
															</td>
															<td align="center">
																<input type="text" id="" name="" value=""/>
															</td>
															<td align="center"> 
																<input type="text" id="" name="" value=""/>
															</td>
															<td align="center">
																<input type="text" id="" name="" value=""/>
															</td>
															<td style="text-align: center;"> 
									                             <input type="button" class="btn action-del-btn btn-danger" style="text-align: center;"  ondblclick="return false;" onclick="" value="-"/>
									                           	 <input type="hidden" name="hidStartus" id="hidStartus"  value="U"/>
									                           	 <input type="hidden" name="" id=""  value=""/>  
								                            </td>
														</tr> 
														<% } 
													} %>
											       <tr>
											 			<td style="visibility:hidden;"></td>
														<td style="visibility:hidden;"></td>
														<td style="visibility:hidden;"></td>
														<td style="visibility:hidden;"></td>
														<td style="visibility:hidden;"></td>
														<td style="text-align: center;"> 
								                       		<input type="button"  class="btn action-add-btn btn-success" style="text-align: center;"  ondblclick="return false;" onclick="lp_add_row_match();" value="+"/>
								                       		 <input type="hidden" name="hidStartus" id="hidStartus"  value="N"/>
									                         <input type="hidden" name="matchId" id="matchId"  value="0"/>  
								                       	 </td>
													</tr> 
											</table>
						               	</div>
					                   </td>
					                 </tr>
					             </table>	
							</section>
						</div>
						</br>
						<div align="center">	  
							<input type="button"  ondblclick="return false;" id="btnSave" name="btnSave" value="บันทึก"  onclick="lp_save_page();"/> 
							<button ondblclick="return false;" id="btnCancel" name="btnCancel"   onclick="lp_reset_page();">ยกเลิก</button> 
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