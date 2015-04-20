<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.SeatReservationBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="seatReservationForm" class="th.go.ticket.app.enjoy.form.SeatReservationForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>จำหน่ายตั๋วการแข่งขัน</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="format-detection" content="telephone=no">
	<meta name="description" content="">
	<meta name="keywords" content="">
	
	<script>
		$(document).ready(function(){
			$('#menu1').ptMenu();
		});
	</script>
	
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
	  .seat-col-bookking{background: url('/ticketWeb/images/seat-bookking.png') no-repeat 10%; cursor:no-drop }
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
</head>
<body>
<form id="frm" action="<%=servURL%>/EnjoyGenericSrv">
	<input type="hidden" id="service" 			name="service" 			value="servlet.SeatReservationServlet" />
	<input type="hidden" id="fieldZoneId" 		name="fieldZoneId" 		value="<%=seatReservationForm.getFieldZoneId()%>" />
	<input type="hidden" id="fieldZoneName" 	name="fieldZoneName" 	value="<%=seatReservationForm.getFieldZoneName()%>" />
	<input type="hidden" id="matchId" 			name="matchId" 			value="<%=seatReservationForm.getMatchId()%>" />
	<input type="hidden" id="season" 			name="season" 			value="<%=seatReservationForm.getSeason()%>" />
	<input type="hidden" id="awayTeamNameTH" 	name="awayTeamNameTH" 	value="<%=seatReservationForm.getAwayTeamNameTH()%>" />
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
											    <div class="row no-padd-all">
       												<div class='span12'>
														ปี :&nbsp;<%=seatReservationForm.getSeason()%>&nbsp;แข่งขันกับ&nbsp;<%=seatReservationForm.getAwayTeamNameTH()%>&nbsp;โซน&nbsp;<%=seatReservationForm.getFieldZoneName()%>
       												</div>
     											</div>
     											<!-- end : options -->
     											<!-- start : seat -->
     											<div class="span8 round line-gray padd-all-4 no-marg-left">
      												<div class="seat-holder" id='-1'>
        												<div class="seat-row">
												        	<!-- <div class="seat-col seat-col-head " id='ch-a'>A</div>
												          	<div class="seat-col seat-col-occupy round" id='a-1-2'data-ref-id='-1' ></div>
												          	<div class="seat-col seat-col-occupy round" id='a-1-4' data-ref-id='-1' ></div>
												          	<div class="seat-col seat-col-free seat-green round"   	id='a-1-1' data-ref-id='-1' ></div>
												          	<div class="seat-col seat-col-free seat-blue round"   	id='a-1-3' data-ref-id='-1' ></div>
												          	<div class="seat-col seat-col-free seat-yellow round"   id='a-1-3' data-ref-id='-1' ></div>
												          	<div class="seat-col seat-col-free seat-pink round"   	id='a-1-3' data-ref-id='-1' ></div>
												          	<div class="seat-col seat-col-free seat-orange round"   id='a-1-3' data-ref-id='-1' ></div>
												          	<div class="seat-col seat-col-free seat-violet round"   id='a-1-3' data-ref-id='-1' ></div>
												          	<div class="seat-col seat-col-free seat-gray round"   	id='a-1-3' data-ref-id='-1' ></div> -->
        												</div>
      												</div>
    											</div>
    											<div class="span3 round line-gray padd-all-4 no-marg-right" >
    												
    												<%
    												List<SeatReservationBean> 		ticketTypeList = seatReservationForm.getTicketTypeList();
    												for(SeatReservationBean ticketTypeBean:ticketTypeList){
    												%>
        											<a href="javascript:void()" class='btn padd-all-4 user-type-select'>
      													<img src="<%=imgURL + "/" + ticketTypeBean.getBookingTypeImage()%>" 
      														 style='width:36px' 
      														 alt="<%=ticketTypeBean.getBookingTypeName()%>" 
      														 title="<%=ticketTypeBean.getBookingTypeName()%>" />
    												</a>
    												<%}%>
    												<div style='clear:both'></div>
	    												<hr>
													    <ul>
													    	<%
													    	for(SeatReservationBean ticketTypeBean:ticketTypeList){
													    	%>
													      	<li><%=ticketTypeBean.getBookingTypeName()%>&nbsp;<%=ticketTypeBean.getBookingPrices()%>&nbsp;บาท</li>
													      	<%} %>
													    </ul>
    													<input class='form-control input' placeholder='0' style='text-align:right; max-width:75%'>
    													<span class="btn"> + </span>
    												</div>
    												<!-- end : seat -->
    												<div class="prototype hide">
												   		<div class="seat-col seat-col-head " id='seat-col-head-prototype'>A</div>
												    	<div class="seat-col seat-col-free round" id='seat-col-prototype' data-ref-id='-1'></div>
    												</div>
  												</div>
											</div>
											<!-- end : container -->
											<script type="text/javascript">
  												$(document).ready(function(){
										        /*
										        {
												    "zone_id": "A1",
												    "zone_title": "west side",
												    "total_seat": [
												        {
												            "row_id": "1",
												            "row_title": "A",
												            "row_color": "blue",
												            "list": [
												                {
												                    "col_id": "1",
												                    "status": "occupy",
												                    "reservation_ref": "A326891",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "2",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "3",
												                    "status": "occupy",
												                    "reservation_ref": "A356891",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "4",
												                    "status": "occupy",
												                    "reservation_ref": "A326791",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "5",
												                    "status": "occupy",
												                    "reservation_ref": "A316891",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "6",
												                    "status": "occupy",
												                    "reservation_ref": "A326861",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "7",
												                    "status": "occupy",
												                    "reservation_ref": "A322891",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "8",
												                    "status": "occupy",
												                    "reservation_ref": "A326491",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "9",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "10",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "11",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "12",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "13",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "14",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "15",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "16",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "17",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "18",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "19",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "20",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "21",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "22",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "23",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "24",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "25",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "26",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "27",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "28",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "29",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "30",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "31",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                }
												            ]
												        },
												        {
												            "row_id": "2",
												            "row_title": "B",
												            "row_color": "green",
												            "list": [
												                {
												                    "col_id": "1",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "2",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "3",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "4",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "5",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "6",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "7",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "8",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "9",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "10",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "11",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "12",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "13",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "14",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "15",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "16",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "17",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "18",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "19",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "20",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "21",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "22",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "23",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "24",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "25",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "26",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "27",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "28",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "29",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "30",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "31",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                }
												            ]
												        },
												        {
												            "row_id": "3",
												            "row_title": "C",
												            "row_color": "violet",
												            "list": [
												                {
												                    "col_id": "1",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "2",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "3",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "4",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "5",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "6",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "7",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "8",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "9",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "10",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "11",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "12",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "13",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "14",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "15",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "16",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "17",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "18",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "19",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "20",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "21",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "22",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "23",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "24",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "25",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "26",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "27",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "28",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "29",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "30",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "31",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                }
												            ]
												        },
												        {
												            "row_id": "4",
												            "row_title": "D",
												            "row_color": "pink",
												            "list": [
												                {
												                    "col_id": "1",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "2",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "3",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "4",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "5",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "6",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "7",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "8",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "9",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "10",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "11",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "12",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "13",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "14",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "15",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "16",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "17",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "18",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "19",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "20",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "21",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "22",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "23",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "24",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "25",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "26",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "27",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "28",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "29",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "30",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                },
												                {
												                    "col_id": "31",
												                    "status": "free",
												                    "reservation_ref": "",
												                    "col_color": ""
												                }
												            ]
												        }
												    ]
												}
												*/
												var sample_json = '{"zone_id":"A1","zone_title":"west side","total_seat":[{"row_id":"1","row_title":"A","row_color":"blue","list":[{"col_id":"1","status":"occupy","reservation_ref":"A326891","col_color":""},{"col_id":"2","status":"free","reservation_ref":"","col_color":""},{"col_id":"3","status":"occupy","reservation_ref":"A356891","col_color":""},{"col_id":"4","status":"occupy","reservation_ref":"A326791","col_color":""},{"col_id":"5","status":"occupy","reservation_ref":"A316891","col_color":""},{"col_id":"6","status":"occupy","reservation_ref":"A326861","col_color":""},{"col_id":"7","status":"occupy","reservation_ref":"A322891","col_color":""},{"col_id":"8","status":"occupy","reservation_ref":"A326491","col_color":""},{"col_id":"9","status":"free","reservation_ref":"","col_color":""},{"col_id":"10","status":"free","reservation_ref":"","col_color":""},{"col_id":"11","status":"free","reservation_ref":"","col_color":""},{"col_id":"12","status":"free","reservation_ref":"","col_color":""},{"col_id":"13","status":"free","reservation_ref":"","col_color":""},{"col_id":"14","status":"free","reservation_ref":"","col_color":""},{"col_id":"15","status":"free","reservation_ref":"","col_color":""},{"col_id":"16","status":"free","reservation_ref":"","col_color":""},{"col_id":"17","status":"free","reservation_ref":"","col_color":""},{"col_id":"18","status":"free","reservation_ref":"","col_color":""},{"col_id":"19","status":"free","reservation_ref":"","col_color":""},{"col_id":"20","status":"free","reservation_ref":"","col_color":""},{"col_id":"21","status":"free","reservation_ref":"","col_color":""},{"col_id":"22","status":"free","reservation_ref":"","col_color":""},{"col_id":"23","status":"free","reservation_ref":"","col_color":""},{"col_id":"24","status":"free","reservation_ref":"","col_color":""},{"col_id":"25","status":"free","reservation_ref":"","col_color":""},{"col_id":"26","status":"free","reservation_ref":"","col_color":""},{"col_id":"27","status":"free","reservation_ref":"","col_color":""},{"col_id":"28","status":"free","reservation_ref":"","col_color":""},{"col_id":"29","status":"free","reservation_ref":"","col_color":""},{"col_id":"30","status":"free","reservation_ref":"","col_color":""},{"col_id":"31","status":"free","reservation_ref":"","col_color":""}]},{"row_id":"2","row_title":"B","row_color":"green","list":[{"col_id":"1","status":"free","reservation_ref":"","col_color":""},{"col_id":"2","status":"free","reservation_ref":"","col_color":""},{"col_id":"3","status":"free","reservation_ref":"","col_color":""},{"col_id":"4","status":"free","reservation_ref":"","col_color":""},{"col_id":"5","status":"free","reservation_ref":"","col_color":""},{"col_id":"6","status":"free","reservation_ref":"","col_color":""},{"col_id":"7","status":"free","reservation_ref":"","col_color":""},{"col_id":"8","status":"free","reservation_ref":"","col_color":""},{"col_id":"9","status":"free","reservation_ref":"","col_color":""},{"col_id":"10","status":"free","reservation_ref":"","col_color":""},{"col_id":"11","status":"free","reservation_ref":"","col_color":""},{"col_id":"12","status":"free","reservation_ref":"","col_color":""},{"col_id":"13","status":"free","reservation_ref":"","col_color":""},{"col_id":"14","status":"free","reservation_ref":"","col_color":""},{"col_id":"15","status":"free","reservation_ref":"","col_color":""},{"col_id":"16","status":"free","reservation_ref":"","col_color":""},{"col_id":"17","status":"free","reservation_ref":"","col_color":""},{"col_id":"18","status":"free","reservation_ref":"","col_color":""},{"col_id":"19","status":"free","reservation_ref":"","col_color":""},{"col_id":"20","status":"free","reservation_ref":"","col_color":""},{"col_id":"21","status":"free","reservation_ref":"","col_color":""},{"col_id":"22","status":"free","reservation_ref":"","col_color":""},{"col_id":"23","status":"free","reservation_ref":"","col_color":""},{"col_id":"24","status":"free","reservation_ref":"","col_color":""},{"col_id":"25","status":"free","reservation_ref":"","col_color":""},{"col_id":"26","status":"free","reservation_ref":"","col_color":""},{"col_id":"27","status":"free","reservation_ref":"","col_color":""},{"col_id":"28","status":"free","reservation_ref":"","col_color":""},{"col_id":"29","status":"free","reservation_ref":"","col_color":""},{"col_id":"30","status":"free","reservation_ref":"","col_color":""},{"col_id":"31","status":"free","reservation_ref":"","col_color":""}]},{"row_id":"3","row_title":"C","row_color":"violet","list":[{"col_id":"1","status":"free","reservation_ref":"","col_color":""},{"col_id":"2","status":"free","reservation_ref":"","col_color":""},{"col_id":"3","status":"free","reservation_ref":"","col_color":""},{"col_id":"4","status":"free","reservation_ref":"","col_color":""},{"col_id":"5","status":"free","reservation_ref":"","col_color":""},{"col_id":"6","status":"free","reservation_ref":"","col_color":""},{"col_id":"7","status":"free","reservation_ref":"","col_color":""},{"col_id":"8","status":"free","reservation_ref":"","col_color":""},{"col_id":"9","status":"free","reservation_ref":"","col_color":""},{"col_id":"10","status":"free","reservation_ref":"","col_color":""},{"col_id":"11","status":"free","reservation_ref":"","col_color":""},{"col_id":"12","status":"free","reservation_ref":"","col_color":""},{"col_id":"13","status":"free","reservation_ref":"","col_color":""},{"col_id":"14","status":"free","reservation_ref":"","col_color":""},{"col_id":"15","status":"free","reservation_ref":"","col_color":""},{"col_id":"16","status":"free","reservation_ref":"","col_color":""},{"col_id":"17","status":"free","reservation_ref":"","col_color":""},{"col_id":"18","status":"free","reservation_ref":"","col_color":""},{"col_id":"19","status":"free","reservation_ref":"","col_color":""},{"col_id":"20","status":"free","reservation_ref":"","col_color":""},{"col_id":"21","status":"free","reservation_ref":"","col_color":""},{"col_id":"22","status":"free","reservation_ref":"","col_color":""},{"col_id":"23","status":"free","reservation_ref":"","col_color":""},{"col_id":"24","status":"free","reservation_ref":"","col_color":""},{"col_id":"25","status":"free","reservation_ref":"","col_color":""},{"col_id":"26","status":"free","reservation_ref":"","col_color":""},{"col_id":"27","status":"free","reservation_ref":"","col_color":""},{"col_id":"28","status":"free","reservation_ref":"","col_color":""},{"col_id":"29","status":"free","reservation_ref":"","col_color":""},{"col_id":"30","status":"free","reservation_ref":"","col_color":""},{"col_id":"31","status":"free","reservation_ref":"","col_color":""}]},{"row_id":"4","row_title":"D","row_color":"pink","list":[{"col_id":"1","status":"free","reservation_ref":"","col_color":""},{"col_id":"2","status":"free","reservation_ref":"","col_color":""},{"col_id":"3","status":"free","reservation_ref":"","col_color":""},{"col_id":"4","status":"free","reservation_ref":"","col_color":""},{"col_id":"5","status":"free","reservation_ref":"","col_color":""},{"col_id":"6","status":"free","reservation_ref":"","col_color":""},{"col_id":"7","status":"free","reservation_ref":"","col_color":""},{"col_id":"8","status":"free","reservation_ref":"","col_color":""},{"col_id":"9","status":"free","reservation_ref":"","col_color":""},{"col_id":"10","status":"free","reservation_ref":"","col_color":""},{"col_id":"11","status":"free","reservation_ref":"","col_color":""},{"col_id":"12","status":"free","reservation_ref":"","col_color":""},{"col_id":"13","status":"free","reservation_ref":"","col_color":""},{"col_id":"14","status":"free","reservation_ref":"","col_color":""},{"col_id":"15","status":"free","reservation_ref":"","col_color":""},{"col_id":"16","status":"free","reservation_ref":"","col_color":""},{"col_id":"17","status":"free","reservation_ref":"","col_color":""},{"col_id":"18","status":"free","reservation_ref":"","col_color":""},{"col_id":"19","status":"free","reservation_ref":"","col_color":""},{"col_id":"20","status":"free","reservation_ref":"","col_color":""},{"col_id":"21","status":"free","reservation_ref":"","col_color":""},{"col_id":"22","status":"free","reservation_ref":"","col_color":""},{"col_id":"23","status":"free","reservation_ref":"","col_color":""},{"col_id":"24","status":"free","reservation_ref":"","col_color":""},{"col_id":"25","status":"free","reservation_ref":"","col_color":""},{"col_id":"26","status":"free","reservation_ref":"","col_color":""},{"col_id":"27","status":"free","reservation_ref":"","col_color":""},{"col_id":"28","status":"free","reservation_ref":"","col_color":""},{"col_id":"29","status":"free","reservation_ref":"","col_color":""},{"col_id":"30","status":"free","reservation_ref":"","col_color":""},{"col_id":"31","status":"free","reservation_ref":"","col_color":""}]}]}';
												var json_obj = $.parseJSON(sample_json);
													console.log(json_obj);
  												var seat_wrapper = $('.seat-row');
  													seat_wrapper.empty();
												//Loop
												var current_row = -1;
												var zone_id = json_obj.zone_id;
												var total_seat = json_obj.total_seat;
												var output = '';
  												//render 
												$.each(total_seat,function(key,value){
												  	if(current_row != value.row_id){
												    	//add separator
												    	output += '<div style="clear:both"></div>';
												    	current_row = value.row_id;												
												    	//add row head
												    	output += '<div class="seat-col seat-col-head " id="'+zone_id+'-'+value.row_id+'">'+value.row_title+'</div>';
												}
											    //render row list
											    var current_list = value.list;
											    var row_id = value.row_id;
											    $.each(current_list,function(inner_key,inner_value){
											    	var current_color = (inner_value.status=='occupy')?'':value.row_color;
											  		output += '<div class="seat-col seat-col-'+inner_value.status+' seat-'+current_color+' round"   id="'+zone_id+'-'+row_id+'-'+inner_value.col_id+'" data-ref-id="'+inner_value.reservation_ref+'" ><span>'+inner_value.col_id+'</span></div>';
											    })//end inner each											
											  })//end each 
											  //Set seat 
											  seat_wrapper.html(output);
											  //Set sample click 
											  $('.seat-col-free').on('click',function(){
											    	alert('free for reservation');
											  })
										})
										</script>
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

