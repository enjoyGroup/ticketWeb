<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.SeatReservationBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="seatReservationForm" class="th.go.ticket.app.enjoy.form.SeatReservationForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<title>จำหน่ายตั๋วการแข่งขัน</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<!-- TODO : move all css to files later -->
	<style type="text/css">
	  .padd-all-4{padding: 4px !important;}
	  .padd-all-8{padding: 8px !important;}
	  .padd-all-12{padding: 12px !important;}
	  .no-padd-all{padding: 0px !important;}
	  
	  .marg-all-4{margin: 4px !important;}
	  .marg-all-8{margin: 8px !important;}
	  .marg-all-12{margin: 12px !important;}
	  .no-marg-all{margin: 0px !important;}
	  .no-marg-left{margin-left: 0px !important;}
	  .no-marg-right{margin-right: 0px !important;}
	
	  .hide{ display: none}
	  .trace , .trace *{outline: 1px solid red;}
	
	  .line-dark{border:1px solid black;}
	  .line-gray{border:1px solid #ccc;}
	
	  .seat-holder{ width: 100%;}
	  .seat-row{width: 100%}
	  .seat-col{width: 2.95%; color:white;  min-height: 40px;margin:0px 0.5px; cursor: pointer; text-align: center; text-decoration: uppercase; font-weight: lighter; float: left; margin-bottom: 4px;  }
	  .seat-col span{ font-size: 0.7em; margin-top: 10px !important; overflow: auto;position: absolute; margin-left: -4px}
	  .seat-col-occupy span{display: none}
	
	  .seat-col-free{background: url('/ticketWeb/images/seat-free.png') no-repeat 10%; cursor: pointer}
	  .seat-col-occupy{background: url('/ticketWeb/images/seat-occupy.png') no-repeat 10%; cursor:no-drop }
	  .seat-col-occupy2{background: url('/ticketWeb/images/seat-occupy2.png') no-repeat 10%; cursor:no-drop }
	  .seat-col-bookking-oth{background: url('/ticketWeb/images/seat-bookking.png') no-repeat 10%; cursor:no-drop }
	  .seat-col-bookking{background: url('/ticketWeb/images/seat-bookking.png') no-repeat 10%; cursor:pointer }
	  .seat-col-head{background: blue; cursor:default; color:#fff; }
	
	  .user-type-select{padding: 4px 16px !important; }
	  	
	  .seat-green{ background-color:  rgba(0, 206, 0,0.3);}
	  .seat-green:hover{ background-color:  rgba(0, 206, 0, 0.7);}
	
	  .seat-blue{ background-color:   rgba(7, 113, 255,0.3);}
	  .seat-blue:hover{ background-color:   rgba(7, 113, 255, 0.7);}
	
	  .seat-yellow{ background-color: rgba(255, 223, 5,0.3);}
	  .seat-yellow:hover{ background-color: rgba(255, 223, 5, 0.7);}
	
	  .seat-pink{ background-color:   rgba(255, 183, 221,0.3);}
	  .seat-pink:hover{ background-color:   rgba(255, 183, 221, 0.7);}
	
	  .seat-orange{ background-color: rgba(255, 170, 48,0.3);}
	  .seat-orange:hover{ background-color: rgba(255, 170, 48, 0.7);}
	
	  .seat-violet{ background-color: rgba(186, 138, 238,0.3);}
	  .seat-violet:hover{ background-color: rgba(186, 138, 238, 0.7);}
	
	  .seat-gray{ background-color:   rgba(174, 174, 174,0.3);}
	  .seat-gray:hover{ background-color:   rgba(174, 174, 174, 0.7);}
	</style>
	<!-- End TODO  -->
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="format-detection" content="telephone=no">
	<meta name="description" content="">
	<meta name="keywords" content="">
	
	<script>
		var gv_service 			= null;
		var gv_url 				= '<%=servURL%>/EnjoyGenericSrv';
		var gv_free 			= '<%=seatReservationForm.FREE%>';
		var gv_active 			= '<%=seatReservationForm.ACTIVE%>';
		var gv_pending 			= '<%=seatReservationForm.PENDING%>';
		var gv_reject 			= '<%=seatReservationForm.REJECT%>';
		var gv_flagAlterSeat 	= '<%=seatReservationForm.getFlagAlterSeat()%>';//0 ไม่สามารถเลือกที่นั่งได้, 1 เลือกที่นั่งได้
	
		$(document).ready(function(){
			try{
				gp_progressBarOn();
				//$('#menu1').ptMenu();
				
				gv_service 		= "service=" + $('#service').val();
				
				if(gv_flagAlterSeat=="1"){
					lp_getSeatBooking();
					setInterval(function(){ lp_getSeatBooking(); }, 3000);
				}
				/*$(window).on('beforeunload', function(){
					lp_clearDataBeforeCloseWindow();
				    return 'ข้อมูลที่นั่งได้ถูกเคลียร์แล้ว คุณต้องการปิดหน้าต่างนี้ ?';
				});
				
				$(window).bind('unload', function(){
					lp_clearDataBeforeCloseWindow();
				});*/
				
			}catch(e){
				alert("onLoadPage :: " + e);
			}
			gp_progressBarOff();
		});
		
		function lp_getSeatBooking(){
			
			try{
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=getSeatBooking&" + $('#frm').serialize(),
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	var sizeList			= 0;
		            	
		            	try{
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		//alert(status);
		            		if(status=="SUCCESS"){
		            			sizeList = parseInt(jsonObj.sizeList);
		            			
		            			//ปรับทุกทีนั่งให้ว่าง
	            				lp_clearSeatBooking();
		            			
		            			//alert(sizeList);
		            			if(sizeList > 0){
		            				
		            				$.each(jsonObj.detail, function(idx, obj) {
		            					//alert(obj.ticketId + " "+ obj.seatingNo + " "+ obj.ticketUserUniqueId + " "+ obj.ticketStatus + " "+ obj.classSeat);
		            					lp_setSeatBooking(obj.ticketId, obj.seatingNo, obj.ticketUserUniqueId, obj.ticketStatus, obj.classSeat, obj.seatBookingTypeId);
			            			});
		            			}
		            			
		            		}else{
		            			errMsg = jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_getSeatBooking :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_getSeatBooking :: " + e);
			}
			
		}
		
		function lp_clearSeatBooking(){
			
			var la_ticketId 			= null;
			var la_seatingNo 			= null;
			var la_ticketUserUniqueId 	= null;
			var la_seatIndex 			= null;
			var la_ticketStatus 		= null;
			var la_hidNumSeat			= null;
			var la_seatBookingTypeId 	= null;
			var la_numTicketType		= null;
			var lo_divSeat				= null;
			var lo_spanNumSeat			= null;
			
			try{
				
				la_seatingNo 			= document.getElementsByName("seatingNo");
				la_seatIndex 			= document.getElementsByName("seatIndex");
				la_ticketId 			= document.getElementsByName("ticketId");
				la_ticketUserUniqueId 	= document.getElementsByName("ticketUserUniqueId");
				la_ticketStatus 		= document.getElementsByName("ticketStatus");
				la_hidNumSeat 			= document.getElementsByName("hidNumSeat");
				la_seatBookingTypeId 	= document.getElementsByName("seatBookingTypeId");
				la_numTicketType 		= document.getElementsByName("numTicketType");
				
				for(var i=0;i<la_numTicketType.length;i++){
					la_numTicketType[i].value = 0;
				}
				
				for(var i=0;i<la_seatingNo.length;i++){
					lo_divSeat 		= eval('document.getElementById("seat' + la_seatIndex[i].value + '")');
					lo_spanNumSeat 	= eval('document.getElementById("numSeat' + la_seatIndex[i].value + '")');
					
					la_ticketId[i].value			= "";
					la_ticketUserUniqueId[i].value	= "";
					la_ticketStatus[i].value		= "";
					la_seatBookingTypeId[i].value	= "";
					lo_divSeat.className 			= "seat-col seat-col-free seat-blue round";
					lo_spanNumSeat.innerHTML		= la_hidNumSeat[i].value;
					
				}
				
				
			}catch(e){
				alert("lp_clearSeatBooking :: " + e);
			}
			
		}
		
		function lp_setSeatBooking(av_ticketId, av_seatingNo, av_ticketUserUniqueId, av_ticketStatus, av_classSeat, av_seatBookingTypeId){
			
			var la_ticketId 			= null;
			var la_seatingNo 			= null;
			var la_ticketUserUniqueId 	= null;
			var la_seatIndex 			= null;
			var la_ticketStatus 		= null;
			var la_hidNumSeat			= null;
			var la_seatBookingTypeId 	= null;
			var lv_seatingNo			= "";
			var lo_divSeat				= null;
			var lo_spanNumSeat			= null;
			var lo_numTicketType		= null;
			var lo_userUniqueId			= null;
			var lv_numTicketType		= 0;
			
			try{
				
				la_seatingNo 			= document.getElementsByName("seatingNo");
				la_seatIndex 			= document.getElementsByName("seatIndex");
				la_ticketId 			= document.getElementsByName("ticketId");
				la_ticketUserUniqueId 	= document.getElementsByName("ticketUserUniqueId");
				la_ticketStatus 		= document.getElementsByName("ticketStatus");
				la_hidNumSeat 			= document.getElementsByName("hidNumSeat");
				la_seatBookingTypeId 	= document.getElementsByName("seatBookingTypeId");
				lo_userUniqueId			= document.getElementById("userUniqueId");
				
				if(lo_userUniqueId.value==av_ticketUserUniqueId && av_ticketStatus=="P"){
					lo_numTicketType = eval('document.getElementById("numTicketType' + av_seatBookingTypeId + '")');
					lv_numTicketType = parseInt(gp_replaceComma(lo_numTicketType.value)) + 1;
					
					lo_numTicketType.value = lv_numTicketType;
					gp_format(lo_numTicketType, 0);
				}
				
				for(var i=0;i<la_seatingNo.length;i++){
					lv_seatingNo 	= la_seatingNo[i].value;
					lo_divSeat 		= eval('document.getElementById("seat' + la_seatIndex[i].value + '")');
					lo_spanNumSeat 	= eval('document.getElementById("numSeat' + la_seatIndex[i].value + '")');
					
					if(av_seatingNo==lv_seatingNo){
						la_ticketId[i].value			= av_ticketId;
						la_ticketUserUniqueId[i].value	= av_ticketUserUniqueId;
						la_ticketStatus[i].value		= av_ticketStatus;
						la_seatBookingTypeId[i].value	= av_seatBookingTypeId;
						lo_divSeat.className 			= av_classSeat;
						lo_spanNumSeat.innerHTML		= "";
						
						break;
					}
					
				}
				
				
			}catch(e){
				alert("lp_setSeatBooking :: " + e);
			}
			
		}
		
		function lp_setTicketType(av_ticketTypeIndex, av_bookingTypeId, av_bookingTypeName){
			
			var la_btnTicketType 		= null;
			var la_hidRefBookingType	= null;
			
			try{
				
				la_btnTicketType 		= document.getElementsByName("btnTicketType");
				la_hidRefBookingType 	= document.getElementsByName("hidRefBookingType");
				
				for(var i=0;i<la_btnTicketType.length;i++){
					
					if(i==av_ticketTypeIndex){
						la_btnTicketType[i].className = "";
					}else{
						la_btnTicketType[i].className = "btn-unSelect";
					}
					
				}
				
				for(var i=0;i<la_hidRefBookingType.length;i++){
					
					if(la_hidRefBookingType[i].value==av_bookingTypeId){
						la_hidRefBookingType[i].parentNode.className = "txt-select";
					}else{
						la_hidRefBookingType[i].parentNode.className = "txt-unSelect";
					}
					
				}
				
				$("#bookingTypeId").val(av_bookingTypeId);
				$("#bookingTypeName").val(av_bookingTypeName);
				
			}catch(e){
				alert("lp_setTicketType :: " + e);
			}
			
		}
		
		function lp_booking(av_index){
			var la_ticketId 			= null;
			var la_seatingNo 			= null;
			var la_ticketUserUniqueId 	= null;
			var la_seatIndex 			= null;
			var la_ticketStatus 		= null;
			var la_hidNumSeat			= null;
			var la_seatBookingTypeId	= null;
			var lv_seatingNo			= "";
			var lo_divSeat				= null;
			var lo_spanNumSeat			= null;
			var lo_userUniqueId			= null;
			var lv_params				= "";
			
			try{
				la_seatingNo 			= document.getElementsByName("seatingNo");
				la_seatIndex 			= document.getElementsByName("seatIndex");
				la_ticketId 			= document.getElementsByName("ticketId");
				la_ticketUserUniqueId 	= document.getElementsByName("ticketUserUniqueId");
				la_ticketStatus 		= document.getElementsByName("ticketStatus");
				la_hidNumSeat 			= document.getElementsByName("hidNumSeat");
				la_seatBookingTypeId 	= document.getElementsByName("seatBookingTypeId");
				lo_userUniqueId 		= document.getElementById("userUniqueId");
				
				//Status ถูกจองโดยไม่ใช่ User นี้
				if(gv_pending==gp_trim(la_ticketStatus[av_index].value) && la_ticketUserUniqueId[av_index].value!=lo_userUniqueId.value){
					alert("ไม่สามารถยกเลิกได้เนื่องจาก ถูกจองโดยคนอื่นแล้ว");
					return;
				}
				
				//สถานะจองเสร็จแล้วไม่สามารถเปลี่ยนแปลงได้
				if(gv_active==la_ticketStatus[av_index].value)return;
				
				lv_params = gv_service + "&ticketId=" 		+ la_ticketId[av_index].value
									   + "&seatingNo=" 		+ la_seatingNo[av_index].value
									   + "&matchId=" 		+ $("#matchId").val()
									   + "&fieldZoneId=" 	+ $("#fieldZoneId").val()
									   + "&bookingTypeId=" 	+ $("#bookingTypeId").val()
									   + "&userUniqueId=" 	+ $("#userUniqueId").val()
									   + "&ticketStatus=" 	+ la_ticketStatus[av_index].value
									   + "&season=" 		+ $("#season").val();
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=booking&" + lv_params,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		//alert(status);
		            		if(status=="SUCCESS"){
		            			
		            			lp_getSeatBooking();
		            		}else{
		            			errMsg = jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_booking :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_booking :: " + e);
			}
			
		}
		
		function lp_goBack(){
			
			try{
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=goBack&" + $('#frm').serialize(),
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		//alert(status);
		            		if(status=="SUCCESS"){
		            			window.location = gv_url + "?service=servlet.SeatZoneServlet&pageAction=new";
		            		}else{
		            			errMsg = jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_goBack :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_goBack :: " + e);
			}
		}
		
		function lp_goNext(){
			
			var la_numTicketType 	= null;
			var lv_flag				= true;
			
			try{
				
				la_numTicketType = document.getElementsByName("numTicketType");
				
				for(var i=0;i<la_numTicketType.length;i++){
					if(la_numTicketType[i].value>0){
						lv_flag = false;
						break;
					}
				}
				
				if(lv_flag==true){
					alert("กรุณาระบุจำนวนตั๋วก่อนทำรายการถัดไป");
					return;
				}
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=goNext&" + $('#frm').serialize(),
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	var params				= "";
		            	
		            	try{
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		//alert(status);
		            		if(status=="SUCCESS"){
		            			//alert(gp_sanitizeURLString(JSON.stringify(jsonObj)));
		            			params = "&matchId=" + $("#matchId").val() + "&season=" + $("#season").val() + "&fieldZoneId=" + $("#fieldZoneId").val() + "&ticketIdList=" + gp_sanitizeURLString(JSON.stringify(jsonObj));
		            			
		            			window.location = gv_url + "?service=servlet.SeatSummaryReservationServlet&pageAction=getSummaryReserv" + params;
		            		}else{
		            			errMsg = jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_goNext :: " + e);
		            	}
		            }
		        });
			}catch(e){
				alert("lp_goNext :: " + e);
			}
			
		}
		
		//av_flag 0 คือ ลบ, 1 คือ บวก
		function lp_setNumTicketType(av_flag, av_seatBookingTypeId){
			
			var lo_numTicketType 	= null;
			var lv_numTicketType	= 0;
			var lo_btnMinus			= null;
			
			try{
				
				lo_numTicketType 	= eval('document.getElementById("numTicketType' + av_seatBookingTypeId + '")');
				lo_btnMinus 		= eval('document.getElementById("btnMinus' + av_seatBookingTypeId + '")');
				
				if(av_flag=="0"){
					lv_numTicketType = parseInt(gp_replaceComma(lo_numTicketType.value)) - 1;
				}else{
					lv_numTicketType = parseInt(gp_replaceComma(lo_numTicketType.value)) + 1;
				}
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=forStandZone&seatBookingTypeId=" + av_seatBookingTypeId + "&numTicketType=" + lv_numTicketType,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		//alert(status);
		            		if(status=="SUCCESS"){
		            			lo_numTicketType.value = lv_numTicketType;
		        				gp_format(lo_numTicketType, 0);
		        				
		        				//ค่าผลรวมเป็น 0 ให้ set disabled ปุ่มลบ
		        				if(lv_numTicketType==0){
		        					lo_btnMinus.disabled = true;
		        				}else{
		        					lo_btnMinus.disabled = false;
		        				}
		        				
		            		}else{
		            			errMsg = jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_setNumTicketType :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_setNumTicketType :: " + e);
			}
			
		}
		
	</script>
</head>
<body>
<form id="frm" action="<%=servURL%>/EnjoyGenericSrv">
	<input type="hidden" id="service" 				name="service" 				value="servlet.SeatReservationServlet" />
	<input type="hidden" id="fieldZoneId" 			name="fieldZoneId" 			value="<%=seatReservationForm.getFieldZoneId()%>" />
	<input type="hidden" id="fieldZoneName" 		name="fieldZoneName" 		value="<%=seatReservationForm.getFieldZoneName()%>" />
	<input type="hidden" id="fieldZoneNameTicket" 	name="fieldZoneNameTicket" 	value="<%=seatReservationForm.getFieldZoneNameTicket()%>" />
	<input type="hidden" id="matchId" 				name="matchId" 				value="<%=seatReservationForm.getMatchId()%>" />
	<input type="hidden" id="season" 				name="season" 				value="<%=seatReservationForm.getSeason()%>" />
	<input type="hidden" id="awayTeamNameTH" 		name="awayTeamNameTH" 		value="<%=seatReservationForm.getAwayTeamNameTH()%>" />
	<input type="hidden" id="userUniqueId" 			name="userUniqueId" 		value="<%=seatReservationForm.getUserUniqueId()%>" />
	<input type="hidden" id="bookingTypeId" 		name="bookingTypeId" 		value="<%=seatReservationForm.getBookingTypeId()%>" />
	<input type="hidden" id="bookingTypeName" 		name="bookingTypeName" 		value="<%=seatReservationForm.getBookingTypeName()%>" />
	<input type="hidden" id="flagAlterSeat" 		name="flagAlterSeat" 		value="<%=seatReservationForm.getFlagAlterSeat()%>" />
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
				            	<h4 class="alert-heading">จำหน่ายตั๋วการแข่งขัน</h4>
				          	</div>
				          	<div class="row">
								<div class="col-sm-12">
									<!-- start : wrapper -->
									<div class="wrapper">
									  	<!-- start : container -->
									  	<div class="container ">
   											<div class="span12 sim-planel round padd-all-4">
												<!-- start : options -->
											    <div class="panel panel-info">
											    	<div class="panel-heading">
														<h4 class="panel-title">
															ปี :&nbsp;<%=seatReservationForm.getSeason()%>&nbsp;แข่งขันกับ&nbsp;<%=seatReservationForm.getAwayTeamNameTH()%>&nbsp;โซน&nbsp;<%=seatReservationForm.getFieldZoneName()%>
														</h4>
													</div>
     											</div>
     											<!-- end : options -->
     											<!-- start : seat -->
     											<%
												List<SeatReservationBean> 		seatPerRowList 		= null;
												
												if(seatReservationForm.getFlagAlterSeat().equals("1")){
												%>
												<div class="span8 round line-gray padd-all-4 no-marg-left">
													<div class="seat-holder" id="-1">
														<% for(String seatName:seatReservationForm.getSeatNameList()){%>
														<div style="clear: both"></div>
														<div class="seat-col seat-col-head " id="<%=seatName %>"><%=seatName %></div>
														<%
														seatPerRowList = (List<SeatReservationBean>) seatReservationForm.getRowsMap().get(seatName);
														for(SeatReservationBean	seatPerRowBean:seatPerRowList){
														%>
														<div title="<%=seatPerRowBean.getSeatingNo()%>" class="<%=seatPerRowBean.getClassSeat()%>" id="seat<%=seatPerRowBean.getSeatIndex()%>" onclick="lp_booking(<%=seatPerRowBean.getSeatIndex()%>);" data-ref-id="">
															<span id="numSeat<%=seatPerRowBean.getSeatIndex()%>"><%=seatPerRowBean.getNumSeat()%></span>
															<input type="hidden" name="ticketId" 			value="<%=seatPerRowBean.getTicketId()%>" />
															<input type="hidden" name="ticketStatus" 		value="<%=seatPerRowBean.getTicketStatus()%>" />
															<input type="hidden" name="ticketUserUniqueId" 	value="<%=seatPerRowBean.getTicketUserUniqueId()%>" />
															<input type="hidden" name="seatIndex" 			value="<%=seatPerRowBean.getSeatIndex()%>" />
															<input type="hidden" name="seatingNo" 			value="<%=seatPerRowBean.getSeatingNo()%>" />
															<input type="hidden" name="hidNumSeat" 			value="<%=seatPerRowBean.getNumSeat()%>" />
															<input type="hidden" name="seatBookingTypeId" 	value="<%=seatPerRowBean.getSeatBookingTypeId()%>" />
														</div>	
														<%}}%>
													</div>
												</div>
												<%}else{ %>
												<div class="span8 round line-gray padd-all-4 no-marg-left">
													<div class="seat-holder" id="-1" align="center" style="background: url('<%=imgURL%>/field.png');background-position: left 0%;height:60vh;padding-top: 50px;">
														<!-- แบบไม่เลือกที่นั่ง -->
														<img src="<%=imgURL%>/CHN300_2.png" border="0" />
													</div>
												</div>
												<%} %>
												<table border="0" >
													<tr>
														<td>
			    											<div class="span3 round line-gray padd-all-4 no-marg-right" >
			    												
			    												<%
			    												List<SeatReservationBean> 		ticketTypeList 	= seatReservationForm.getTicketTypeList();
			    												int								ticketTypeIndex	= 0;
			    												Map								mapBookingType	= seatReservationForm.getMapBookingType();
			    												int								numTicketType	= 0;
			    												for(SeatReservationBean ticketTypeBean:ticketTypeList){
			    													if(seatReservationForm.getFlagAlterSeat().equals("1")){
			    												%>
					        											<a href="javascript:void(0)" onclick="lp_setTicketType('<%=ticketTypeIndex%>', '<%=ticketTypeBean.getBookingTypeId()%>', '<%=ticketTypeBean.getBookingTypeName()%>');" class='btn padd-all-4 user-type-select'>
					      													<img src="<%=imgURL + "/" + ticketTypeBean.getBookingTypeImage()%>" 
					      														 style='width:36px;' 
					      														 id="btnTicketType<%=ticketTypeIndex%>"
					      														 name="btnTicketType"
					      														 class="<%=ticketTypeBean.getClassBtn()%>"
					      														 alt="<%=ticketTypeBean.getBookingTypeName()%>" 
					      														 title="<%=ticketTypeBean.getBookingTypeName()%>" />
					    												</a>
			    												<% }else{%>
			    													<a class='btn padd-all-4 user-type-select' disabled>
					      													<img src="<%=imgURL + "/" + ticketTypeBean.getBookingTypeImage()%>" 
					      														 style='width:36px;' 
					      														 id="btnTicketType<%=ticketTypeIndex%>"
					      														 name="btnTicketType"
					      														 class="btn-unSelect"
					      														 alt="<%=ticketTypeBean.getBookingTypeName()%>" 
					      														 title="<%=ticketTypeBean.getBookingTypeName()%>" />
					      											</a>
			    												<%}
			    												
			    												ticketTypeIndex++; 
			    												}%>
			    												<div style='clear:both'></div>
				    												<hr>
				    												<table border="0" cellpadding="5" cellspacing="2">
				    													<%
																    	for(SeatReservationBean ticketTypeBean:ticketTypeList){
																    	%>
				    													<tr>
				    														<td align="left">
				    														<%if(seatReservationForm.getFlagAlterSeat().equals("1")){ %>
				    															<span class="<%=ticketTypeBean.getClassTxt()%>" >
				    																<%=ticketTypeBean.getBookingTypeName()%>
				    																<input type="hidden" name="hidRefBookingType" value="<%=ticketTypeBean.getBookingTypeId()%>" />
				    															</span>
				    														<%}else{ %>
				    															<span class="txt-unSelect">
				    																<%=ticketTypeBean.getBookingTypeName()%>
				    															</span>
				    														<%}%>
				    															&nbsp;&nbsp;:&nbsp;&nbsp; 
				    														</td>
				    														<td align="left">
				    															<%=ticketTypeBean.getBookingPrices()%>&nbsp;&nbsp;
				    														</td>
				    														<td align="left">
				    															บาท
				    														</td>
				    													</tr>
				    													<%} %>
				    												</table>
																    <table border="0" cellpadding="5" cellspacing="2">
																    	<%
																    	for(SeatReservationBean ticketTypeBean:ticketTypeList){
																    		if(mapBookingType.containsKey(ticketTypeBean.getBookingTypeId())){
																    			numTicketType = (Integer) mapBookingType.get(ticketTypeBean.getBookingTypeId());
																    		}else{
																    			numTicketType = 0;
																    		}
																    	%>
																    	<tr>
																    		<td align="right">
																    			<input type="button" id="btnMinus<%=ticketTypeBean.getBookingTypeId()%>" name="btnMinus" onclick="lp_setNumTicketType('0', '<%=ticketTypeBean.getBookingTypeId()%>');" class="btn" style="width: 30px;" value="-" <%if(seatReservationForm.getFlagAlterSeat().equals("1") || ticketTypeBean.getNumTicketType().equals("0")){ %> disabled="disabled" <%} %> />
																    		</td>
																    		<td align="right" style="white-space: nowrap;">
																    			&nbsp;&nbsp;<%=ticketTypeBean.getBookingTypeName()%>&nbsp;:&nbsp;
																    		</td>
																    		<td align="left">
																    			<input  type="text" 
						    															class="input-disabled" 
						    															style="text-align:right; max-width:50%"
						    															id="numTicketType<%=ticketTypeBean.getBookingTypeId()%>"
						    															name="numTicketType"
						    															readonly="readonly"
						    															value="<%=numTicketType%>"
						    															 />
						    													<span>คน</span>&nbsp;&nbsp;
			    																<input type="button" id="btnPlus<%=ticketTypeBean.getBookingTypeId()%>" name="btnPlus" onclick="lp_setNumTicketType('1', '<%=ticketTypeBean.getBookingTypeId()%>');" class="btn" style="width: 30px;" value="+" <%if(seatReservationForm.getFlagAlterSeat().equals("1")){ %> disabled="disabled" <%} %> />
																    		</td>
																    	</tr>
																    	<%} %>
																    </table>
			    													<!--  
			    													<input class='form-control input' placeholder='0' style='text-align:right; max-width:75%'>
			    													<span class="btn"> + </span>
			    													-->
			    												</div>
    														</td>
    													</tr>
    													<tr>
    														<td><br/><br/></td>
    													</tr>
    													<tr>
    														<td>
    															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    															<input type="button" id="btnBack" name="btnBack" onclick="lp_goBack();" class="btn" style="width: 150px;" value="<< ย้อนกลับ" />&nbsp;&nbsp;
    															<input type="button" id="btnNext" name="btnNext" onclick="lp_goNext();" class="btn" style="width: 150px;" value="บันทึก" />
    														</td>
    													</tr>
    												</table>
    												<!-- end : seat -->
    												<div class="prototype hide">
												   		<div class="seat-col seat-col-head " id='seat-col-head-prototype'>A</div>
												    	<div class="seat-col seat-col-free round" id='seat-col-prototype' data-ref-id='-1'></div>
    												</div>
  												</div>
											</div>
											<!-- end : container -->
									</div>
									<!-- end : wrapper -->
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
</body>
</html>

