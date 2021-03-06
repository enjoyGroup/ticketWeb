<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.EventMatchBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="eventMatchForm" class="th.go.ticket.app.enjoy.form.EventMatchForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8; IE=EDGE">
	<title>รายการ Match การแข่งขันประจำปี</title>
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
			padding: 0; 
			border-bottom: 1px solid #d4d4d4;
			border-top: none;
			background: linear-gradient(#f7f7f7 0%, #ececec 100%);
			height: 30px;
			vertical-align: middle;
		}
		
		.unLink{
			color: red;
			font-weight:bold;
			padding: 0; 
			border-bottom: 1px solid #d4d4d4;
			border-top: none;
			background: linear-gradient(#f7f7f7 0%, #ececec 100%);
			height: 30px;
			vertical-align: middle;
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
		var d 		        = new Date();
	    var toDay 	        = d.getDate() + '/' + (d.getMonth() + 1) + '/' + (d.getFullYear() + 543);
	    
		$(document).ready(function(){
			$('#menu1').ptMenu();
			gv_service 	= "service=" + $('#service').val();
 
			$("#btnSave").attr('disabled','disabled');
			$("#btnCancel").attr('disabled','disabled');
			$("#buttonAdd").attr('disabled','disabled');
			
	        if($("#seasonNew").val() != '') {
	        	$("#btnSave").removeAttr('disabled');
	        	$("#btnCancel").removeAttr('disabled');
	        	$("#buttonAdd").removeAttr('disabled');
	        }
	        
	    	 
	    	$(".dateFormat").live("focus", function(){
			    $(this).datepicker({ changeMonth: true, changeYear: true,dateFormat: 'dd/mm/yy', 
							    	isBuddhist: true, 
							    	defaultDate: toDay,dayNames: ['อาทิตย์','จันทร์','อังคาร','พุธ','พฤหัสบดี','ศุกร์','เสาร์'],
						            dayNamesMin: ['อา.','จ.','อ.','พ.','พฤ.','ศ.','ส.'],
						            monthNames: ['มกราคม','กุมภาพันธ์','มีนาคม','เมษายน','พฤษภาคม','มิถุนายน','กรกฎาคม','สิงหาคม','กันยายน','ตุลาคม','พฤศจิกายน','ธันวาคม'],
						            monthNamesShort: ['ม.ค.','ก.พ.','มี.ค.','เม.ย.','พ.ค.','มิ.ย.','ก.ค.','ส.ค.','ก.ย.','ต.ค.','พ.ย.','ธ.ค.']});
			});
			
	 
		});
	
	 
		
		function lp_checkFormatdate(av_object){ 
		//alert(av_object.id);	
			try{ 
				//สำหรับเช็ค Format วันที่ต้องเป็น dd/mm/yyyy(พ.ศ.) เท่านั้น
				if(!gp_checkDate(av_object))return; 
				
			}catch(e){
				alert("lp_checkFormatdate :: " + e);
			}
		}
		
	
		
		function lp_dateClick(ao_obj){
			   
			   var la_matchDate  = null;
			   var lv_index  = 0;
			   
			   try{
			    
			    la_matchDate  = document.getElementsByName("matchDate");
			    lv_index  = gp_rowTableIndex(ao_obj) - 1;
			    
			    la_matchDate[lv_index].focus();
			    
			   }catch(e){
			    alert("lp_dateClick :: " + e);
		     }
		  }
		
		function lp_addTableSeason(av_season){
			
			var lo_table		= null;
			var lv_length 	 	= null;
			var row 		 	= null;
			var cell1 		 	= null; 
			var cell2 		 	= null; 
			var lo_hidSeason 	= null;
			var lv_class		= ""; 
			 
			try{
				lo_table 		= document.getElementById("result_season");
				lv_length 		= lo_table.rows.length;
				row 			= lo_table.insertRow(lv_length);
				cell1 			= row.insertCell(0);
				lo_hidSeason 	= document.getElementById("hidSeason");
				cell2 			= row.insertCell(1);
				cell2.style     = "display:none;";
				
				cell1.align		= "center"; 
			 	cell1.title		= av_season; 
				
				if(av_season==lo_hidSeason.value){
					lv_class 	= "unLink";
				}else{
					lv_class 	= "link"; 
					cell1.onclick = function() { 
					    //alert(av_season);
						lp_changeSeason(av_season);
						
			        };
				}
				cell1.className	= lv_class; 
				cell1.innerHTML = "<span>"+av_season+"</span>"; 
				cell2.innerHTML = av_season;
				 
				  
	//alert(lo_table.rows.length);			
			}catch(e){
				alert("lp_addTableSeason :: " + e);
			}
		}
		
		function lp_addLinkAddSeason(av_season){

			var lo_table		= null;
			var lv_length 	 	= null;
			var row 		 	= null;
			var cell1 		 	= null; 
			var lv_class		= ""; 
			
			try{
				lo_table 		= document.getElementById("result_season");
				lv_length 		= lo_table.rows.length;
				row 			= lo_table.insertRow(lv_length);
				cell1 			= row.insertCell(0);  
				cell1.align		= "center";  
				 
				lv_class 	= "link"; 
				cell1.onclick = function() {  
					lp_add_row_season();
		        };
			 
		        cell1.className	= lv_class; 
				cell1.innerHTML =  "<span><a href='#'>+เพิ่มปีการแข่งขัน</a></span>";
				  
				
			}catch(e){
				alert("lp_addTableSeason :: " + e);
			}
		}
		
       function lp_changeSeason(av_season){
       
    	    if (confirm("ยืนยันการเปลี่ยนปีการแข่งขัน ?") == false) {
    	    	 return false;
    	    }  
    	    
    	    gv_mode             = "UpdateSeason";
			var lo_hidSeason 	= null;
			var lo_seasonTitle	= null;
	 
			try{
				lo_hidSeason 	= document.getElementById("hidSeason");
				lo_seasonTitle 	= document.getElementById("seasonNew");
 
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=changeSeason&season=" + av_season,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var season				= null;
		            	var errMsg				= null;
		            	var index				= 1;
		            	var size                = null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		                    //alert(status);
		            		if(status=="SUCCESS"){
		            			season				= jsonObj.season;
		            			size                = jsonObj.detailSize;
		                        // alert("size ::"+size);
		            			lo_hidSeason.value 	= season; 
		            			lo_seasonTitle.value = season;
		            			
		            		   lp_deleteTab("result_season");
		            		   lp_deleteTab("result_match");
		            	 		 
						        //  alert(JSON.stringify(jsonObj.detail));
		            			
		            			$.each(jsonObj.seasonList, function(idx, obj) {
		            		//alert(obj.season);
		            				lp_addTableSeason(obj.season);
		            			});  
		            			
		            			$.each(jsonObj.detail, function(idx, obj) {
		            			   //alert(obj.awayTeamNameTH + " " + obj.awayTeamNameEN );
		            				lp_addTableResultTab(index++, obj.awayTeamNameTH,obj.awayTeamNameEN, obj.matchDate, obj.matchTime , obj.matchId,size);
		            			});
		            			
		            			lp_addLinkAddSeason();
		            			  
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
		
		function lp_addTableResultTab(av_index, av_awayTeamNameTH,av_awayTeamNameEN,av_matchDate,av_matchTime,av_matchId,av_size){
			var lo_table		= null;
			var lv_length 	 	= null;
			var row 		 	= null;
			var row2 		 	= null;
			var cell1 		 	= null;
			var cell2 		 	= null;
			var cell3 		 	= null;
			var cell4 		 	= null;
			
			 try{
				lo_table 		= document.getElementById("result_match");
				lv_length 		= lo_table.rows.length;
				row 			= lo_table.insertRow(lv_length);
				cell1 			= row.insertCell(0);
				cell2 			= row.insertCell(1);
				cell3 			= row.insertCell(2);
				cell4 			= row.insertCell(3);
				cell5 			= row.insertCell(4);
				cell6			= row.insertCell(5);
				
				cell1.align		= "center"; 
				cell2.align		= "center"; 
				cell3.align		= "center"; 
				cell4.align		= "center"; 
				cell5.align		= "center"; 
				cell6.align		= "center"; 
				
				if(av_size == av_index){
					cell1.innerHTML = av_index ;
					cell2.innerHTML = "<input type='text'  id='awayTeamNameTH'  name='awayTeamNameTH'  class='awayTeamNameTH' maxlength='100' value='"+av_awayTeamNameTH+ "'/>" ;
					cell3.innerHTML = "<input type='text'  id='awayTeamNameEN'  name='awayTeamNameEN'  class='awayTeamNameEN' maxlength='100' value='"+av_awayTeamNameEN+ "'/>" ;
					cell4.innerHTML = "<input type='text'  id='matchDate"+ av_index +"' style='width:80%;' name='matchDate'   maxlength='8' class='dateFormat' placeholder='DD/MM/YYYY' onblur='lp_checkFormatdate(this);'  value='"+av_matchDate+ "'/>"+
					                  "<i class='fa fa-fw fa-calendar' id='trigger-DateFrom' style='cursor:pointer'  onclick = 'lp_dateClick(this);'></i>";
					cell5.innerHTML = "<input type='text'  id='matchTime'  name='matchTime'  maxlength='5' class='matchTime' value='"+av_matchTime+ "' onblur='gp_validateTime(this);' />";
					cell6.innerHTML	= "<input type='button' class='btn action-del-btn btn-danger' style='text-align: center;'  ondblclick='return false;' onclick='lp_del_row_table(this)' value='-'/>" +
									  "<input type='hidden' name='hidStartus' id='hidStartus'  value='U'/>"+
									  "<input type='hidden' name='matchId' id='matchId'  value='"+av_matchId+"'/>";
									  
					row2 		= lo_table.insertRow(lv_length+1); 
					cell1 		= row2.insertCell(0);
					cell2 		= row2.insertCell(1);
					cell3 		= row2.insertCell(2);
					cell4 		= row2.insertCell(3);
					cell5 		= row2.insertCell(4);
					cell6 		= row2.insertCell(5);  
					cell6.align	= "center";    
					cell6.innerHTML = "<input type='button' class='btn action-add-btn btn-success' style='text-align: center;' ondblclick='return false;' onclick='lp_add_row_match();' value='+' />"+
									  "<input type='hidden' name='hidStartus' id='hidStartus'  value='N'/>"+
									  "<input type='hidden' name='matchId' id='matchId'  value='0'/></td>";
									  
				}else{
					cell1.innerHTML = av_index ;
					cell2.innerHTML = "<input type='text'  id='awayTeamNameTH'  name='awayTeamNameTH' class='awayTeamNameTH'  maxlength='100'  value='"+av_awayTeamNameTH+ "'/>" ;
					cell3.innerHTML = "<input type='text'  id='awayTeamNameEN'  name='awayTeamNameEN' class='awayTeamNameEN'  maxlength='100' value='"+av_awayTeamNameEN+ "'/>" ;
					cell4.innerHTML = "<input type='text'  id='matchDate"+ av_index +"'  style='width:80%;' name='matchDate'   maxlength='8' class='dateFormat' placeholder='DD/MM/YYYY' onblur='lp_checkFormatdate(this);'  value='"+av_matchDate+ "'/>"+
									 "<i class='fa fa-fw fa-calendar' id='trigger-DateFrom' style='cursor:pointer'  onclick = 'lp_dateClick(this);'></i>";
					cell5.innerHTML = "<input type='text'  id='matchTime'  name='matchTime' maxlength='5'  class='matchTime'  value='"+av_matchTime+ "' onblur='gp_validateTime(this);' />";
					cell6.innerHTML	= "<input type='button' class='btn action-del-btn btn-danger' style='text-align: center;'  ondblclick='return false;' onclick='lp_del_row_table(this)' value='-'/>" +
									  "<input type='hidden' name='hidStartus' id='hidStartus'  value='U'/>"+
									  "<input type='hidden' name='matchId' id='matchId'  value='"+av_matchId+"'/>";
				}
	 
				
			}catch(e){
				alert("lp_addTableResultTab :: " + e);
			} 
			
		}
		
		function lp_add_row_match(){
			var lo_table 	 = null;
			var lv_length 	 = null;
			var row 		 = null;
			var cell1 		 = null;
			var cell2 		 = null;
			var cell3 		 = null;	 
			try{
				lo_table 	= document.getElementById("result_match");
				lv_length 	= lo_table.rows.length - 1;
			//alert(lv_length);
				row 		= lo_table.insertRow(lv_length);
				cell1 		= row.insertCell(0);
				cell2 		= row.insertCell(1);
				cell3 		= row.insertCell(2);
				cell4 		= row.insertCell(3);
				cell5 		= row.insertCell(4);
				cell6 		= row.insertCell(5); 
				
				cell1.align		= "center"; 
				cell2.align		= "center"; 
				cell3.align		= "center"; 
				cell4.align		= "center"; 
				cell5.align		= "center"; 
				cell6.align		= "center"; 
			  
				cell1.innerHTML = lv_length++ ;
				cell2.innerHTML = "<input type='text'  id='awayTeamNameTH'  name='awayTeamNameTH'  class='awayTeamNameTH' maxlength='100'  value=''/>" ;
				cell3.innerHTML = "<input type='text'  id='awayTeamNameEN'  name='awayTeamNameEN'  class='awayTeamNameEN' maxlength='100'  value=''/>" ;
				cell4.innerHTML = "<input type='text'  id='matchDate"+ lv_length +"' style='width:80%;' name='matchDate'   maxlength='8' class='dateFormat' placeholder='DD/MM/YYYY' onblur='lp_checkFormatdate(this);'  value=''/>"+
									"<i class='fa fa-fw fa-calendar' id='trigger-DateFrom' style='cursor:pointer'  onclick = 'lp_dateClick(this);'></i>";
				cell5.innerHTML = "<input type='text'  id='matchTime'  name='matchTime'  maxlength='5' class='matchTime' value='' onblur='gp_validateTime(this);'/>";
				cell6.innerHTML	="<input type='button' class='btn action-del-btn btn-danger' style='text-align: center;'  ondblclick='return false;' onclick='lp_del_row_table(this)' value='-'/>" + 
				 				  "<input type='hidden' name='hidStartus' id='hidStartus'  value='N'/>"+
				  				  "<input type='hidden' name='matchId' id='matchId'  value='0'/></td>";
				  
				
			}catch(e){
				alert("lp_add_row_match :: " + e);
			}
		}
		
		function lp_deleteTab(av_idTab){
			
			var lo_table		= null;
			var lv_row			= 0;
			var lv_length		= 0;
			
			try{
				
				lo_table 	= eval('document.getElementById("' + av_idTab + '")');
				lv_row		= 1;
				lv_length	= lo_table.rows.length;
				
				for(var i=lv_row;i<lv_length;i++){
					lo_table.deleteRow(lv_row); 
				}
				
			}catch(e){
				alert("lp_deleteTab :: " + e);
			}
			
		}
		
		function lp_add_row_season(){ //เปิดปิดให้ใส่ข้อมูลใหม่ทั้งหมด
			//alert("add season new");
			  if($("#seasonNew").val() != '') {
		        	$("#btnSave").removeAttr('disabled');
		        	$("#btnCancel").removeAttr('disabled');
		        	$("#buttonAdd").removeAttr('disabled');
		      }
			gv_mode          = "NewSeason";
			lo_season 	     = document.getElementById("seasonNew");
			lo_season.value  = "";  
			$('.inputDisabled').removeAttr("disabled"); 
    		lp_deleteTab("result_match");
    		lp_clear_match(); 
		
		}
		
		function lp_clear_match(){
			var lo_table 	 = null; 
			var row 		 = null;
			var cell1 		 = null;
			var cell2 		 = null;
			var cell3 		 = null;
			var cell4 		 = null;
			var cell5 		 = null;
			var cell6 		 = null;
			 
	  
			try{
				lo_table 	= document.getElementById("result_match");  
				row 		= lo_table.insertRow(1);   
				cell1 		= row.insertCell(0);
				cell2 		= row.insertCell(1);
				cell3 		= row.insertCell(2);
				cell4 		= row.insertCell(3);
				cell5 		= row.insertCell(4);
				cell6 		= row.insertCell(5);   
				 
				cell1.align	= "center"; 
				cell2.align	= "center";
				cell3.align	= "center"; 
				cell4.align	= "center"; 
				cell5.align	= "center"; 
				cell6.align	= "center";    
				cell6.innerHTML = "<input type='button' class='btn action-add-btn btn-success' style='text-align: center;' ondblclick='return false;' onclick='lp_add_row_match();' value='+' />"+
								  "<input type='hidden' name='hidStartus' id='hidStartus'  value='N'/>"+
								  "<input type='hidden' name='matchId' id='matchId'  value='0'/></td>";
			  
				
			}catch(e){
				alert("lp_clear_match :: " + e);
			}
		}
		
		function lp_del_row_table(ao_obj){  
			var lv_index			= 0;   
			var lo_tabResultDtl		= document.getElementById("result_match");
			var lo_matchId          = null;
			var lv_matchId          = null; 	
			var i                   = 0;
			var count				= 0;
			//alert(lo_tabResultDtl.rows.length);	
			if(lo_tabResultDtl.rows.length==3){
				alert("ปีการแข่งขันจะต้องมีอย่างน้อย 1 Match");
				return;			
			}
		 
				if(confirm("ต้องการลบรายการนี็?")){   
					lv_index	  = gp_rowTableIndex(ao_obj); 
					lo_matchId    = document.getElementsByName("matchId");
					lv_matchId    = lo_matchId[lv_index-1].value;
		           // alert("lv_matchId ::"+ lv_matchId);	
					
					if(lv_matchId!=""){ 
						if(lv_matchId == 0){
							lo_tabResultDtl.deleteRow(lv_index); 
				    		var length = lo_tabResultDtl.rows.length-2;
				    		for(var i=0;i<length;i++){ 
								rowNumber = i+1;
								lo_tabResultDtl.rows[i+1].cells[0].innerHTML=rowNumber;
				    		}
				    		 
						}else{
							gv_delList.push(lv_matchId);
	            			lo_tabResultDtl.deleteRow(lv_index); 
				    		var length = lo_tabResultDtl.rows.length-2; 
				    		for(var i=0;i<length;i++){ 
								rowNumber = i+1;
								lo_tabResultDtl.rows[i+1].cells[0].innerHTML=rowNumber;
				    		} 
						}   
					} 
				} 
		    }
		
		function lp_save_page(){
       	   // alert("lp_save_page :: "+lp_validate_data());
		    if(!lp_validate_data()){
				return false;
			}  
			
			var pageAction			= "UpdateSeason";
			var lv_params			= gv_service; 
			
			if(gv_mode == "NewSeason"){
				pageAction			= "NewSeason";
			} 	 
		 
		    try{
		    	lv_params 	+= "&pageAction=" + pageAction  + "&" + $('#frm').serialize();
		    	
		    	if(gv_delList.length>0){
		  //alert(gv_delList.toString());
		    		lv_params 	+= "&deleteList="+ gv_delList;
		    	}else{
		    		lv_params 	+= "&deleteList=none";
		    	}
	     //alert(lv_params);  
	
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
		              // alert(data);          	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		                  //alert(status);		
			            	if(status=="SUCCESS"){
			            		alert("บันทึกรายการเรียบร้อย  ");  
			            		gv_mode         = "saved";
			            		window.location = gv_url + "?service=servlet.EventMatchServlet&pageAction=new";
		            		}else{
		            			errMsg = jsonObj.errMsg; 
		            			alert(errMsg);
		            		}   
		            	}catch(e){
		            		alert("in lp_save_page :: " + e);
		            	}  
		            }
		        });
		    	  
		    }catch(e){
		    	alert("lp_save_page :: " + e);
		    }

			 gp_progressBarOff();
		}
		
		function lp_reset_page(){ 
			var pageAction			= "new";
			var lv_params			= gv_service;  
		 
		    try{
		    	lv_params 	+= "&pageAction=" + pageAction ; 
		  // alert(lv_params);
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: lv_params,
		            beforeSend: "",
		            success: function(data){ 
		            	window.location = gv_url + "?service=servlet.EventMatchServlet&pageAction=new";
		            }
		        });
		    	  
		    }catch(e){
		    	alert("lp_reset_page :: " + e);
		    }
		    
		}
		
		function   lp_validate_data(){
			var lo_table 	= document.getElementById("result_match");
			var lv_length 	= lo_table.rows.length; 
			var lv_season   = document.getElementById("seasonNew").value; 
		    var la_awayTeamNameTH = document.getElementsByName("awayTeamNameTH"); 
		    var la_awayTeamNameEN = document.getElementsByName("awayTeamNameEN"); 
		    var la_matchDate	  = document.getElementsByName("matchDate"); 
		    var la_matchTime	  = document.getElementsByName("matchTime"); 
		    var lv_flag1           = false;
		    var lv_flag2           = false;
		    var lv_flag3           = false;
		    var lv_flag4           = false;
			
		    try{ 
				
	            if(gp_trim(lv_season)==""){
	            	alert("กรุณากรอกรายละเอียด  season "); 
	                return false;
	            }  
    
				if(lv_length <= 2){
					alert("กรุณาระบุ รายการ Match อย่างน้อย 1 รายการ ");
					return false;
				}  else { 
			        for(var a=0 ; a < la_awayTeamNameTH.length;a++){
			        	if(la_awayTeamNameTH[a].value == ""){
			        		alert("กรุณาระบุทีมคู่แข่งภาษาไทย  ");
			        		lv_flag1 = false;
			        	}else{
			        		lv_flag1 = true; 
			        	}
			        }
			        
			        for(var b=0 ; b < la_awayTeamNameEN.length;b++){
			        	if(la_awayTeamNameEN[b].value == ""){
			        		alert("กรุณาระบุทีมคู่แข่งภาษาอังกฤษ  ");
			        		lv_flag2 = false;
			        	}else{
			        		lv_flag2 = true; 
			        	}
			        }
			        
			        for(var c=0 ; c < la_matchDate.length;c++){
			        	if(la_matchDate[c].value == ""){
			        		alert("กรุณาระบุวันที่แข่งขัน");
			        		lv_flag3 = false;
			        	}else{
			        		lv_flag3 = true; 
			        	}
			        }
			        
			        for(var d=0 ; d < la_matchTime.length;d++){
			        	if(la_matchTime[d].value == ""){
			        		alert("กรุณาระบุเวลาที่แข่งขัน  ");
			        		lv_flag4 = false;
			        	}else{
			        		lv_flag4 = true; 
			        	}
			        }
				 
			        if(lv_flag1==true && lv_flag2==true && lv_flag3==true && lv_flag4==true){
			       	   return true;
		        	}else{
		        	   return false;
		        	}
			        
				  
				} 
				
				
			}catch(e){
				alert("lp_validate_data :: " + e); 
				 
			}  
		}
		
		function lp_onblur_check_season(){ 
			var lv_season   = document.getElementById("seasonNew").value; 
			var lo_table 	= document.getElementById("result_season");
 
             if(lv_season != '') {
		        	$("#btnSave").removeAttr('disabled');
		        	$("#btnCancel").removeAttr('disabled');
		        	$("#buttonAdd").removeAttr('disabled');
		     }
             var lv_length 	= lo_table.rows.length;
        	
        	for(var i = 1 ; i < lv_length ; i ++){ 
       			//alert(gp_trim(lo_table.rows[i].cells[1].innerHTML));
        		if(gp_trim(lv_season) == gp_trim(lo_table.rows[i].cells[1].innerHTML)){
        			alert("ไม่สามารถเพิ่มปีการแข่งขันซ้ำกับที่มีอยู่แล้วได้");
        			break;
        		} 
        	}
        	
        	
		}
		
		var getId = (function () {
			  var incrementingId = 0;
			  return function(element) {
			    if (!element.id) {
			      element.id = "id_" + incrementingId++;
			      // Possibly add a check if this ID really is unique
			    }
			    return element.id;
			  };
			}());
		
		$(window).load(function() {
			var i = 1;
			$("tbody tr:last").find("input").each(function() {
			$(this).attr({
			  'id': function(_, id) { return id + i; } 
			});
			}).end().appendTo("#result_match");
		    i++; 
		});	
	 
	</script>
	
</head>
<body>	
<form id="frm" onsubmit="return false;">
	<input type="hidden" id="service" 	name="service" value="servlet.EventMatchServlet" />  
	<input type="hidden" id="hidSeason" name="hidSeason" value="<%=eventMatchForm.getSeason()%>" />  
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
					            	<h4 class="alert-heading">รายละเอียด Match การแข่งขัน</h4>
					          	</div>
					          	
						
								<div class="container main-container round-sm padding-xl-h ">
									
										
						        			 <!-- start : menu -->
												<div  class=" col-md-2  col2-1st cssmenu-mod" >	
									                      <table class="table sim-panel-result-table" id="result_season">
															<tr style="border:none;">
																<th class="round-sm" style="background-color:  #656659;font-size:1.0em; padding:4px 0px;width:100%; margin:0px !important">
																	<B style="text-align: center; margin:0px; color:white; font-size:1.0em">ปีการแข่งขัน</B>
																</th>
															</tr>
															<%
										 					List<String>  list			=   eventMatchForm.getSeasonList();
										 
										  					if(list.size()>0){ 
																for(int i=0;i<list.size();i++){
										
															%>
														 	<tr> 
														 		<%if(list.get(i).equals(eventMatchForm.getSeason())){%>
														 			<td class="unLink" align="center" title="<%=list.get(i)%>" >
																		<span><%=list.get(i)%></span>
																	</td>
																	<td style="display:none;">
																		<%=list.get(i)%>
																	</td>		
														 		<%}else{%>
														 			<td class="link" onclick="lp_changeSeason('<%=list.get(i)%>');" align="center" title="<%=list.get(i)%>">
																		<span><%=list.get(i)%></span>
																	</td>
																	<td style="display:none;">
																		<%=list.get(i)%>
																	</td>	
														 		<%}%>
															</tr> 
															<% } 
											 				} %>
								 							<tr>
								 								<td align="center" class="link" onclick="lp_add_row_season();">
								 									<a href="#">
									 									<span>
																			<B>+เพิ่มปีการแข่งขัน</B>
																		</span>
																		<br/>
																	</a>
																</td>
															</tr>
												 		</table> 
												 		<div style="padding:10px;" >
												 			<span style="color: red;"><b>*</b></span> 
									                         <input type='text' style="width:135px" id="seasonNew" name='seasonNew' value="<%=eventMatchForm.getSeason()%>" class="inputDisabled" disabled="disabled" onblur="lp_onblur_check_season();"/>&nbsp;
									                    </div>
									   				</div>
									   				 <!-- end : menu -->
									   			
									   			 <!-- start : table -->	
									            <div  class=" col-md-10  line-left">
						                  	 	<div class='sim-panel-result datagrid' style="padding:10px;">
						                        <table class="table sim-panel-result-table" id="result_match">
													<thead>
													<tr id="headRow">
							                            <th>ลำดับ</th>
							                            <th>ทีมคู่แข่งภาษาไทย</th>
							                            <th>ทีมคู่แข่งภาษาอังกฤษ</th>
							                            <th>วันที่แข่ง</th>
							                            <th>เวลาที่แข่ง</th>
							                            <th>Action</th>
						                          	</tr>
						                          	</thead>
												<%
														List<EventMatchBean>  	detailList			= eventMatchForm.getEventMatchList();
														EventMatchBean			detail				= null;
														int						seq					= 0;
																 
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
																			<input type="text" id="awayTeamNameTH" name="awayTeamNameTH" maxlength="100"  class="awayTeamNameTH" value="<%=detail.getAwayTeamNameTH()%>" />
																		</td>
																		<td align="center">
																			<input type="text" id="awayTeamNameEN" name="awayTeamNameEN" maxlength="100"  class="awayTeamNameEN" value="<%=detail.getAwayTeamNameEN()%>"/>
																		</td>
																		<td align="center"> 
																			 <input type="text" id="<%=detail.getMatchDateId()%>" name="matchDate" style="width:80%;" maxlength="8"   class="dateFormat" placeholder="DD/MM/YYYY" onblur="lp_checkFormatdate(this);"  value="<%=detail.getMatchDate()%>"  />
																			 <i class='fa fa-fw fa-calendar' id='trigger-DateFrom' style='cursor:pointer' onclick ="lp_dateClick(this);"></i>
																		</td>
																		<td align="center">
																			<input type="text" id="matchTime" name="matchTime" maxlength="5"  class="matchTime" value="<%=detail.getMatchTime()%>" onblur="gp_validateTime(this);"  />
																		</td>
																		<td style="text-align: center;">  
												                             <input type="button" class="btn action-del-btn btn-danger" style="text-align: center;"  ondblclick="return false;" onclick="lp_del_row_table(this)" value="-"/>
												                           	 <input type="hidden" name="hidStartus" id="hidStartus"  value="U"/>
												                           	 <input type="hidden" name="matchId" id="matchId"  value="<%=detail.getMatchId()%>"/>  
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
								                       					<input type="button"  id="buttonAdd" class="btn action-add-btn btn-success" style="text-align: center;"  ondblclick="return false;" onclick="lp_add_row_match();" value="+"/>
								                       		 			<input type="hidden" name="hidStartus" id="hidStartus"  value=""/>
									                         			<input type="hidden" name="matchId" id="matchId"  value=""/>  
								                       		 		</td>
																</tr> 
															</table>
						               					</div>
						               					
						               					<input type="button"  
								        					   ondblclick="return false;" 
								        					   id="btnSave" 
								        					   name="btnSave" 
								        					   value="บันทึก"  
								        					   class="btn action-add-btn btn-success pull-right" 
								        					   style="margin-left:12px; padding-right:24px; padding-left:24px;"
								        					   onclick="lp_save_page();" /> &nbsp;&nbsp;&nbsp;
								        				<button ondblclick="return false;" 
								        						id="btnCancel" 
								        						name="btnCancel"   
								        						class="btn btn-danger  pull-right"
								        						style="margin-left:12px"
								        						onclick="lp_reset_page();">ยกเลิก</button>  
								        						
								        						
						               					</div>
						               					<!-- end : table -->	
					                   				
								        			
					            		</div>
									</section>
						</section>
					</section>
				</section>  
				<a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
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
