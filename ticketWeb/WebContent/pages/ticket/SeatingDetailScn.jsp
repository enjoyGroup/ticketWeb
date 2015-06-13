<%@page import="th.go.ticket.app.enjoy.bean.FieldZoneDetailBean"%>
<%@page import="th.go.ticket.app.enjoy.bean.FieldzonemasterBean"%>
<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.SeatingDetailBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="seatingDetailForm" class="th.go.ticket.app.enjoy.form.SeatingDetailForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8; IE=EDGE">
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
		var gv_mode         = "UpdateZone";
		var gv_delList      = []; 
		var gv_ind          = 1;
		
		$(document).ready(function(){ 
			$('#menu1').ptMenu();
			gv_service 	= "service=" + $('#service').val();    
      	    
			lp_readOnly_field();
		/* 	
			if($("#hidUserLevel").val()  == "9"){
				if(($("#fieldZoneName").val()=="") || ($("#nameTicket").val()=="")||  ($("#nameRow").val()=="")){
					$("#btnSave").prop('disabled',true); 
					$('#btnCancel').prop("disabled",true); 
					$('#btnAdd').prop("disabled",true); 
				}else{ 
		        	$("#btnSave").removeAttr('disabled');
		        	$("#btnCancel").removeAttr('disabled');
		        	$("#btnAdd").removeAttr('disabled');
		        }
			    
			}else{
				if(($("#rows").val() <=0) || ($("#seating").val()<=0) 
						|| ($("#totalSeating").val()<=0) || ($("#startNo").val()<=0)  
						|| ($("#fieldZoneName").val() =="") 
						|| ($("#nameTicket").val() =="") 
						|| ($("#nameRow").val() ==""))
				{
					$("#btnSave").attr('disabled','disabled');
					$("#btnCancel").attr('disabled','disabled');
					$("#btnAdd").attr('disabled','disabled');
				}else{ 
		        	$("#btnSave").removeAttr('disabled');
		        	$("#btnCancel").removeAttr('disabled');
		        	$("#btnAdd").removeAttr('disabled');
		        }
			}
			 */
	        
			$(".bookingTypeNameClass").live("focus",function(){
				$(this).autocomplete({
			
				source: function(request, response) {
		           $.ajax({
		           	async:false,
			            type: "POST",
		               url: gv_url,
		               dataType: "json",
		               data: gv_service + "&pageAction=getBookingTypeName&bookingTypeName=" + gp_trim(request.term),//request,
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
			
			$('#btnSave').click(function(e) {
				
		        var isValid = true;
		
		        $('input[type="text"]').each(function() {
		
		            if ($.trim($(this).val()) == '') {
		
		                isValid = false;
		
		                $(this).css({
		
		                    "border": "1px solid red",
		
		                    "background": "#FFCECE"
		
		                });
		
		            }
		
		            else {
		
		                $(this).css({
		
		                    "border": "",
		
		                    "background": ""
		
		                });
		
		            }
		
		        });
		
		        if (isValid == false){
		
		            e.preventDefault();
		
		        } 
		    });

				
		});
		

		
		function lp_readOnly_field(){
			var lo_table        = document.getElementById("result_zone");   
			var lv_length 		= lo_table.rows.length; 
			//alert(lv_length);
			if(lv_length == 2){
				var la_idName = new Array("fieldZoneName","nameTicket","rows","seating","startNo","nameRowInd1","nameRowInd2","nameRow");
				var lo_obj    = null;
				try{
					$("#btnSave").prop('disabled',true); 
					$('#btnCancel').prop("disabled",true); 
					$('#btnAdd').prop("disabled",true); 
					for(var i=0;i<la_idName.length;i++){
			            lo_obj          = eval('document.getElementById("' + la_idName[i] + '")'); 
			            lo_obj.readOnly = true;  
					}
				}catch(e){
					alert("lp_readOnly_field :: " + e); 
				}
		   }

		}
		
		function lp_not_readOnly_field(){
			var la_idName = new Array("fieldZoneName","nameTicket","rows","seating","startNo","nameRowInd1","nameRowInd2","nameRow");
			var lo_obj    = null;
			try{
				$("#btnSave").prop('disabled',true); 
				$('#btnCancel').prop("disabled",true); 
				$('#btnAdd').prop("disabled",true); 
				for(var i=0;i<la_idName.length;i++){
		            lo_obj          = eval('document.getElementById("' + la_idName[i] + '")'); 
		            lo_obj.readOnly = false;  
				}
			}catch(e){
				alert("lp_not_readOnly_field :: " + e); 
			}

		}
		
		
		function lp_onblur_bookingTypeName(ao_obj){
		//alert(ao_obj.value);   
		 
		    try{ 
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=validateBookingTypeName&bookingTypeName=" +ao_obj.value,
		            beforeSend: "",
		            success: function(data){ 
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		             //alert(data);          	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		                 //alert(status);		
			            	if(status=="SUCCESS"){ 
		            		}else{
		            			errMsg = jsonObj.errMsg; 
		            			alert(errMsg);
		            		}   
		            	}catch(e){
		            		alert("in lp_onblur_bookingTypeName :: " + e);
		            	}  
		             
		            }
		        });
		    	  
		    }catch(e){
		    	alert("lp_onblur_bookingTypeName :: " + e);
		    }
	    
		}
		
	function lp_addTableZone(av_zoneId,av_zone){ 
			var lo_table		= null;
			var lv_length 	 	= null;
			var row 		 	= null;
			var cell1 		 	= null; 
			var lo_hidZone   	= null;
			var lv_class		= "";
			var lv_click		= "";
		//alert(av_zoneId);
		//alert(av_zone);
			try{
				lo_table        = document.getElementById("result_zone");   
				lv_length 		= lo_table.rows.length;
				row 			= lo_table.insertRow(lv_length);
				cell1 			= row.insertCell(0);
				lo_hidZone  	= document.getElementById("hidZoneName");
				
				cell1.align		= "center"; 
				cell1.title		= av_zone; 
				
				if(av_zone==lo_hidZone.value){
					lv_class 	= "unLink";
				}else{
					lv_class 	= "link"; 
					cell1.onclick = function() { 
					    //alert(av_season);
						lp_changeZone(av_zone,av_zoneId);
						
			        };
				}
				
				cell1.className	= lv_class; 
				cell1.innerHTML = av_zone; 
				  
				
			}catch(e){
				alert("lp_addTableZone :: " + e);
			}
		}
		
		function lp_addLinkAddZone(av_zone){

			var lo_table		= null;
			var lv_length 	 	= null;
			var row 		 	= null;
			var cell1 		 	= null; 
			var lv_class		= ""; 
			
			try{
				lo_table 		= document.getElementById("result_zone");
				lv_length 		= lo_table.rows.length;
				row 			= lo_table.insertRow(lv_length);
				cell1 			= row.insertCell(0);  
				cell1.align		= "center";  
				 
				lv_class 	= "link"; 
				cell1.onclick = function() {  
					lp_add_row_zone();
		        };
			 
		        cell1.className	= lv_class; 
				cell1.innerHTML =  "<a href='#'>เพิ่ม zone ที่นั่ง</a>";
				  
				
			}catch(e){
				alert("lp_addLinkAddZone :: " + e);
			}
		}
		
		function lp_add_row_zone(){ //เปิดปิดให้ใส่ข้อมูลใหม่ทั้งหมด
			$("#btnSave").attr('disabled','disabled');
			$("#btnCancel").attr('disabled','disabled');
			$("#btnAdd").attr('disabled','disabled');
			lp_not_readOnly_field();
			gv_mode          = "NewZone";
			document.getElementById("hidZoneName").value = "";
			document.getElementById("hidZoneId").value = "";
			document.getElementById("fieldZoneName").value = "";
			document.getElementById("nameTicket").value = "";
  		    document.getElementById("rows").value = 0;
  		    document.getElementById("seating").value = 0;
  		    document.getElementById("totalSeating").value = 0;
			document.getElementById("nameRow").value ="";
			document.getElementById("nameRowInd1").checked = true; 
    		lp_deleteTab("result_zone_detail");
    		lp_clear_zone(); 
   
		
		}
		
       function lp_changeZone(av_zone,av_zoneId){

   	    if (confirm("ยืนยันการเปลี่ยน Zone การแข่งขัน ?") == false) {
   	    	 return false;
   	    }  
   	    
    		gv_changeZone       = true;
    	    gv_mode             = "UpdateZone";
			var lo_hidZone 	    = null;
			var lo_zoneTitle	= null;
			var lv_param        = "";
		 
		 
			try{
				lo_hidZone 		= document.getElementById("hidZoneName");
				lo_zoneTitle 	= document.getElementById("fieldZoneName");
                lv_param        = "&pageAction=changeZone&zoneName=" + av_zone + "&zoneId=" + av_zoneId;
                //alert("change zone : "+lv_param);
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + lv_param,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var zoneName			= null;
		            	var errMsg				= null;
		            	var index				= 1;
		            	var index_detail        = 1;
		            	var size                = null;
		           //alert(data);      	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		           //alert(status);
		            		if(status=="SUCCESS"){
		            			zoneName			= jsonObj.ZONE_NAME;
		            		    size                = jsonObj.detailSize;
		                //alert("av_zoneId ::"+av_zoneId); 
		          		//alert("size ::"+size);
		          				document.getElementById("hidZoneId").value =av_zoneId;		 	
					           lo_hidZone.value   = zoneName;  
					           lo_zoneTitle.value = zoneName;
					         
					           lp_deleteTab("result_zone"); 
		            		   lp_deleteTab("result_zone_detail");
		            		   
		            		   document.getElementById("fieldZoneName").value = "";
		            		   document.getElementById("rows").value = "";
		            		   document.getElementById("seating").value = "";
		            		   document.getElementById("totalSeating").value = "";
		            	 		 
					            //alert(JSON.stringify(jsonObj.detail));
		            			
		            			$.each(jsonObj.zoneList, function(idx, obj) { 
		            		  //alert(obj.ZONE_ID);
		            		        document.getElementById("hidZoneId").value =av_zoneId;
		            				lp_addTableZone(obj.ZONE_ID,obj.ZONE);
		            			});  
		            			
		            			$.each(jsonObj.detail, function(idx, obj) {
		            		//alert("startSeatingNo:"+ obj.startSeatingNo+ "   fieldZoneNameTicket :"+obj.fieldZoneNameTicket);
		            				lp_addTableResultMaster(index++, obj.fieldZoneId,obj.fieldZoneName, obj.rows, obj.seating , obj.totalSeating,obj.typeRowName,obj.rowName,size,obj.fieldZoneNameTicket,obj.startSeatingNo);
		            				 
		            				$.each(obj.bookingList, function(idx, object){
		            					//alert("bookingList:"+ obj.fieldZoneId);
		            					lp_addTableResultTableDetail(index_detail++,obj.fieldZoneId,object.bookingTypeId,object.bookingTypeName,object.bookingPrices,object.seq,size);
		            				});
		            			}); 
		            			 
		            			lp_addLinkAddZone(av_zone); 
		            		}else{
		            			errMsg = jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_changeZone :: " + e);
		            	}
		            }
		        });
			}catch(e){
				alert("lp_changeZone :: " + e);
			}
		}
		
		function lp_addTableResultMaster(av_index,av_fieldZoneId,av_fieldZoneName, av_rows, av_seating , av_totalSeating,av_ind,av_rowName,av_size,av_fieldZoneNameTicket,av_startNo){ 
			
			//alert("startSeatingNo:"+ av_startNo+ "   fieldZoneNameTicket :"+ av_fieldZoneNameTicket);
			 try{ 
			   document.getElementById("fieldZoneName").value = av_fieldZoneName;
       		   document.getElementById("rows").value = av_rows;
       		   document.getElementById("seating").value =av_seating;
       		   document.getElementById("totalSeating").value =av_totalSeating;
       		   document.getElementById("nameRow").value =av_rowName;
       		   document.getElementById("nameTicket").value =av_fieldZoneNameTicket;
       		   document.getElementById("startNo").value =av_startNo;
       		   var ind = document.getElementsByName("nameRowInd");
 
               //alert(av_ind);	
       		   if(av_ind==1){
       			    ind[0].checked = true;  
       		   }else if(av_ind==2){
       			 	ind[1].checked = true; 
       		   }  
       		    
			}catch(e){
				alert("lp_addTableResultTab :: " + e);
			} 
			
		}
		
		function lp_addTableResultTableDetail(av_index,av_fieldZoneId,av_bookingTypeId,av_bookingTypeName, av_bookingPrices,av_seq,av_size){

			var lo_table		= null;
			var lv_length 	 	= null; 
			var row 		 	= null;
			var row2 		 	= null;
			var cell1 		 	= null;
			var cell2 		 	= null;
			var cell3 		 	= null;
			var cell4 		 	= null;
		 
			 try{
			lo_table 		= document.getElementById("result_zone_detail");
			lv_length 		= lo_table.rows.length;
			row 			= lo_table.insertRow(lv_length);
			cell1 			= row.insertCell(0);
			cell2 			= row.insertCell(1);
			cell3 			= row.insertCell(2);
			cell4 			= row.insertCell(3); 
			
			cell1.align		= "center"; 
			cell2.align		= "center"; 
			cell3.align		= "center"; 
			cell4.align		= "center";  
 
	
			if(av_size == av_index){
				cell1.innerHTML = av_index ;
				cell2.innerHTML = "<input type='text'  id='bookingTypeName'  name='bookingTypeName' class='bookingTypeNameClass'  onblur='lp_onblur_bookingTypeName(this)'  value='"+av_bookingTypeName+ "'/>"+
				  				  "<input type='hidden' name='bookingTypeId' id='bookingTypeId'  value='"+av_bookingTypeId+ "'/>";
				cell3.innerHTML = "<input type='text'  id='bookingTypePrice'  name='bookingTypePrice'  class='bookingTypePriceClass'  onblur='lp_onBlurFormatNumber(this);' value='"+av_bookingPrices+ "'/>" ;
				cell4.innerHTML	= "<input type='button' class='btn action-del-btn btn-danger' style='text-align: center;'  ondblclick='return false;' onclick='lp_del_row_table(this)' value='-'/>" +
								  "<input type='hidden' name='hidStartus' id='hidStartus'  value='U'/>"+
								  "<input type='hidden' name='fieldZoneId' id='fieldZoneId'  value='"+av_fieldZoneId+"'/>" +
								  "<input type='hidden' name ='seq' id ='seq'  value='" + av_seq + "' />"+
								  "<input type='hidden' name ='hidSeq' id ='hidSeq'  value='" + av_seq + "' />";
				 		  
				row2 		= lo_table.insertRow(lv_length+1); 
				cell1 		= row2.insertCell(0);
				cell2 		= row2.insertCell(1);
				cell3 		= row2.insertCell(2); 
				cell4 		= row2.insertCell(3); 
				cell4.align	= "center";    
				cell4.innerHTML = "<input type='button' class='btn action-add-btn btn-success' style='text-align: center;' ondblclick='return false;' onclick='lp_add_row_zoneDetail();' value='+' />"+
								  "<input type='hidden' name='hidStartus' id='hidStartus'  value='N'/>"+
								  "<input type='hidden' name='fieldZoneId' id='fieldZoneId'  value='0'/>";
					  
			}else{
				cell1.innerHTML = av_index ;
				cell2.innerHTML = "<input type='text'  id='bookingTypeName'  name='bookingTypeName'  class='bookingTypeNameClass' onblur='lp_onblur_bookingTypeName(this)' value='"+av_bookingTypeName+ "'/>" +
				 				  "<input type='hidden' name='bookingTypeId' id='bookingTypeId'  value='"+av_bookingTypeId+ "'/>";
				cell3.innerHTML = "<input type='text'  id='bookingTypePrice'  name='bookingTypePrice'  class='bookingTypePriceClass' onblur='lp_onBlurFormatNumber(this);'  value='"+av_bookingPrices+ "'/>" ; 
				cell4.innerHTML	= "<input type='button' class='btn action-del-btn btn-danger' style='text-align: center;'  ondblclick='return false;' onclick='lp_del_row_table(this)' value='-'/>" +
								  "<input type='hidden' name='hidStartus' id='hidStartus'  value='U'/>"+ 
								  "<input type='hidden' name ='seq' id ='seq'  value='" + av_seq + "' />"+
								  "<input type='hidden' name ='hidSeq' id ='hidSeq'  value='" + av_seq + "' />";
				 
			}  
  		
		}catch(e){
			alert("lp_addTableResultTab :: " + e);
		} 
		
	}
	 
		function lp_add_row_zoneDetail(){
	
			var lo_table 	 = null;
			var lv_length 	 = null;
			var row 		 = null;
			var cell1 		 = null;
			var cell2 		 = null;
			var cell3 		 = null;
			var lv_seq 	     = null;
			try{
				lo_table 	= document.getElementById("result_zone_detail");
				lv_length 	= lo_table.rows.length - 1;
			//alert(lv_length); 
				row 		= lo_table.insertRow(lv_length);
				cell1 		= row.insertCell(0);
				cell2 		= row.insertCell(1);
				cell3 		= row.insertCell(2);
				cell4 		= row.insertCell(3); 
				
				cell1.align		= "center"; 
				cell2.align		= "center"; 
				cell3.align		= "center"; 
				cell4.align		= "center";   
				lv_seq          = lv_length ;
 				cell1.innerHTML =  lv_seq ;
				cell2.innerHTML = "<input type='text'  id='bookingTypeName'  name='bookingTypeName'  class='bookingTypeNameClass'  onblur='lp_onblur_bookingTypeName(this)'  value=''/>" ;
				cell3.innerHTML = "<input type='text'  id='bookingTypePrice'  name='bookingTypePrice'  class='bookingTypePriceClass'  class='moneyOnly' onblur='lp_onBlurFormatNumber(this);'  value='0.00'/>" ; 
				cell4.innerHTML	="<input type='button' class='btn action-del-btn btn-danger' style='text-align: center;'  ondblclick='return false;' onclick='lp_del_row_table(this)' value='-'/>" + 
				 				  "<input type='hidden' name='hidStartus' id='hidStartus'  value='N'/>"+
				  				  "<input type='hidden' name='fieldZoneId' id='fieldZoneId'  value=''/>" + 
				  				  "<input type='hidden' name ='seq' id ='seq'  value='" + (lv_seq+1) + "' />"+
				 				  "<input type='hidden' name ='hidSeq' id ='hidSeq'  value='" + (lv_seq+1) + "' />"; 
				  
				
			}catch(e){
				alert("lp_add_row_zoneDetail :: " + e);
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
		
 
		function lp_clear_detail(){
			var lo_table 	 = null; 
			var row 		 = null;
			var cell1 		 = null;
			var cell2 		 = null;
			var cell3 		 = null;
			var cell4 		 = null;
			var cell5 		 = null;
			var cell6 		 = null;
			 
	  
			try{
				lo_table 	= document.getElementById("result_zone_detail");  
				row 		= lo_table.insertRow(1);
				cell1 		= row.insertCell(0);
				cell2 		= row.insertCell(1);
				cell3 		= row.insertCell(2);
				cell4 		= row.insertCell(3); 
			 
				
				cell1.align	= "center"; 
				cell2.align	= "center";
				cell3.align	= "center"; 
				cell4.align	= "center";  
            
				cell1.innerHTML = "1";
				cell2.innerHTML = "<input  type='text'  id='bookingTypeName'  name='bookingTypeName' class='bookingTypeNameClass'  onblur='lp_onblur_bookingTypeName(this)' value=''/>"; 
				cell3.innerHTML = "<input type='text' id='bookingTypePrice'  name='bookingTypePrice'  class='bookingTypePriceClass'  onblur='lp_onBlurFormatNumber(this);'  value=''/>"; 
				cell4.innerHTML = "<input type='button' class='btn action-del-btn btn-danger' style='text-align: center;'  ondblclick='return false;' onclick='lp_del_row_table(this)' value='-'/>" +
								  "<input type='hidden' name='hidStartus' id='hidStartus'  value='U'/>"+
								  "<input type='hidden' name='fieldZoneId' id='fieldZoneId'  value='0'/>"+ 
								  "<input type='hidden' name ='seq' id ='seq'  value='1' />"+
								  "<input type='hidden' name ='hidSeq' id ='hidSeq'  value='1' />";
			 
				row 		= lo_table.insertRow(2); 
				cell1 		= row.insertCell(0);
				cell2 		= row.insertCell(1);
				cell3 		= row.insertCell(2);
				cell4 		= row.insertCell(3);  
				cell4.align	= "center";    
				cell4.innerHTML = "<input type='button' class='btn action-add-btn btn-success' style='text-align: center;' ondblclick='return false;' onclick='lp_add_row_zoneDetail();' value='+' />"+
								  "<input type='hidden' name='hidStartus' id='hidStartus'  value=''/>"+
								  "<input type='hidden' name='fieldZoneId' id='fieldZoneId'  value='0'/>"+
								  "<input type='hidden' name ='seq' id ='seq'  value='2' />"+
								  "<input type='hidden' name ='hidSeq' id ='hidSeq'  value='2' />";
			  
				
			}catch(e){
				alert("lp_clear_detail :: " + e);
			}
		}
		
		function lp_clear_zone(){
			var lo_table 	 = null; 
			var row 		 = null;
			var cell1 		 = null;
			var cell2 		 = null;
			var cell3 		 = null;
			var cell4 		 = null;
			var cell5 		 = null;
			var cell6 		 = null;
			 
	  
			try{
				lo_table 	= document.getElementById("result_zone_detail");  
				row 		= lo_table.insertRow(1);
				cell1 		= row.insertCell(0);
				cell2 		= row.insertCell(1);
				cell3 		= row.insertCell(2);
				cell4 		= row.insertCell(3);  
				  

				cell4.align	= "center";    
				cell4.innerHTML = "<input type='button' class='btn action-add-btn btn-success' style='text-align: center;' ondblclick='return false;' onclick='lp_add_row_zoneDetail();' value='+' />"+
								  "<input type='hidden' name='hidStartus' id='hidStartus'  value=''/>"+
								  "<input type='hidden' name='fieldZoneId' id='fieldZoneId'  value='0'/>"+
				  				  "<input type='hidden' name ='seq' id ='seq'  value='1' />"+
				  				  "<input type='hidden' name ='hidSeq' id ='hidSeq'  value='1' />";
			  
				
			}catch(e){
				alert("lp_clear_match :: " + e);
			}
		}
		
		function lp_del_row_table(ao_obj){  
			var lv_index			= 0;   
			var lo_tabResultDtl		= document.getElementById("result_zone_detail");
			var lo_fieldZoneId      = null;
			var lv_fieldZoneId      = null; 
			var lo_fieldZoneSeq     = null;
			var lv_fieldZoneSeq     = null; 
			var i                   = 0;
			var count				= 0;
		   // alert("lo_tabResultDtl.rows.length :"+lo_tabResultDtl.rows.length);	 
		 
				if(confirm("ต้องการลบรายการนี็?")){   
					lv_index	      = gp_rowTableIndex(ao_obj); 
			        //alert("lv_index : "+lv_index);
					lo_fieldZoneId    = document.getElementById("hidZoneId");
					lo_fieldZoneSeq   = document.getElementsByName("seq");
					lv_fieldZoneId    = lo_fieldZoneId.value;
					lv_fieldZoneSeq   = lo_fieldZoneSeq[lv_index-1].value;
		           // alert("lv_fieldZoneId :: "+ lv_fieldZoneId + "lv_fieldZoneSeq :: "+ lv_fieldZoneSeq);	
		   
					if(lv_fieldZoneId!=""){ 
						if(lv_fieldZoneId == 0){
							lo_tabResultDtl.deleteRow(lv_index); 
				    		var length = lo_tabResultDtl.rows.length-2;
				    		for(var i=0;i<length;i++){ 
								rowNumber = i+1;
								lo_tabResultDtl.rows[i+1].cells[0].innerHTML=rowNumber;
				    		}
				    		 
						}else{ 
	            			gv_delList.push(lv_fieldZoneSeq);
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
		    if(!lp_validate_data()){
				return;
			}  
			
			var pageAction			= "UpdateZone";
			var lv_params			= gv_service; 
			var lo_ind1             = document.getElementById("nameRowInd1");
			var lo_totalSeating     = document.getElementById("totalSeating");
			var lv_rowName 			= document.getElementById("nameRow");
	 
			if(gv_mode == "NewZone" || $('#hidZoneId').val() == 0){
				pageAction			= "NewZone";
			} 	 
		 
		    try{
		    	lv_params 	+= "&pageAction=" + pageAction  + "&" + $('#frm').serialize() + 
		    					"&totalSeating=" + lo_totalSeating.value + "&nameRow=" +lv_rowName.value;
		     	
		    	if(gv_delList.length>0){
		   //alert(gv_delList.toString());
		    		lv_params 	+= "&deleteList="+ gv_delList;
		    	}else{
		    		lv_params 	+= "&deleteList=none";
		    	}
		    	
		    	if(lo_ind1.checked==true){
		    		lv_params 	+= "&nameRowInd=1";
		    	}else{
		    		lv_params 	+="&nameRowInd=2";
		    	}
			//alert(lv_params);  
 				gp_progressBarOn();
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: lv_params,
		            beforeSend: "",
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
			            		window.location = gv_url + "?service=servlet.SeatingDetailServlet&pageAction=new";
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
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: lv_params,
		            beforeSend: "",
		            success: function(data){  
		            }
		        });
		    	  
		    }catch(e){
		    	alert("lp_reset_page :: " + e);
		    }
		    
		}
		
		function lp_validate_data(){ 
			var lo_table 			= document.getElementById("result_zone_detail");
			var lv_fieldZoneName	= document.getElementById("fieldZoneName").value;
			var lv_nameTicket		= document.getElementById("nameTicket").value;
			var lv_rows 			= document.getElementById("rows").value;
			var lv_seating 			= document.getElementById("seating").value;
			var lv_startNo 			= document.getElementById("startNo").value;
			var lv_totalSeating 	= document.getElementById("totalSeating").value;
			var lv_rowName 			= document.getElementById("nameRow").value;
			var lv_length 			= lo_table.rows.length; 
			var lv_userLevel        = document.getElementById("hidUserLevel").value;
			var la_bookingTypeName  = document.getElementsByName("bookingTypeName");
			var la_bookingTypePrice  = document.getElementsByName("bookingTypePrice");
			var  lv_userLevel = document.getElementById("hidUserLevel").value;
			var lv_flag1           = false;
		    var lv_flag2           = false;
			  
			//if(lv_userLevel == "9"){
				/* if((lv_fieldZoneName=="") || (lv_nameTicket=="") || (lv_rowName=="") 
						|| ((document.getElementById("nameRowInd1").checked==false) && (document.getElementById("nameRowInd2").checked==false))){
					alert("กรุณากรอกรายละเอียด zone ให้ครบถ้วน");
					return false;
				} */
			    
			//}else{
				if((lv_rows<=0) && (lv_seating<=0) && (lv_totalSeating<=0) && (lv_startNo<=0) && (lv_fieldZoneName=="") && (lv_nameTicket=="") 
						&& ((document.getElementById("nameRowInd1").checked==false) && (document.getElementById("nameRowInd2").checked==false))){
					alert("กรุณากรอกรายละเอียด zone ให้ครบถ้วน");
					return false;
				}
			//}
		
			try{    
			//alert(lv_length);
				if(lv_length <= 2){
					alert("กรุณาระบุประเภทตั๋ว อย่างน้อย 1 รายการ ");
					return false;
				}else{
					
			        for(var i=0 ; i < la_bookingTypeName.length;i++){
			        	if(la_bookingTypeName[i].value == ""){
			        		alert("กรุณาระบุประเภทตั๋ว  ");
			        		lv_flag1 = false;
			        	}else{
			        		lv_flag1 = true;
			        	}
			        }
			       
			      // lv_userLevel = "1";
			        
				   for(var j=0 ; j < la_bookingTypeName.length;j++){
			           if(la_bookingTypePrice[j].value == ""){
			        		alert("กรุณาระบุราคาตั๋ว  ");
			        		lv_flag2 =  false;
			           }else{
			        	    lv_flag2 =  true;
			           }
			        	
		        	   if(lv_userLevel != "9"){
		        		   if(la_bookingTypePrice[j].value <= "0.00"){ 
							   alert("Admin เท่านั้นที่สามารถใส่เงินเป็น 0 ได้");
							   lv_flag2 =  false;
						   } else{
							   lv_flag2 =  true;
				           }
		        	   }
			        }
				 
			   
				   if(lv_flag1==true && lv_flag2==true){
					   return true;
				   }else{
					   return false;
				   }
				}
				
			}catch(e){
				alert("lp_validate_data :: " + e);
			}
			 
		}
		
		function lp_onblur_validate(ao_price){
			lv_index	 = gp_rowTableIndex(ao_price); 
			lo_price     = document.getElementsByName("bookingTypePrice");
			lv_price     = lo_price[lv_index-1].value;
			lv_userLevel = document.getElementById("hidUserLevel").value;
         //alert(lv_userLevel);	
         //alert(lv_price);
			if(lv_price<= "0.00" && lv_userLevel != "9"){ 
				alert("Admin เท่านั้นที่สามารถใส่เงินเป็น 0 ได้");
				return;
			}
		}
		
		function lp_calTotalSeating(){
			
			var lo_rows 			= null;
			var lo_seating 		    = null;
			var lo_totalSeating		= null;
			var lv_rows				= 0;
			var lv_seating 			= 0;
			var lv_totalSeating		= 0; 
			var lv_startNo          = 0;
			
			try{ 
				lo_rows					= document.getElementById("rows");
				lo_seating			    = document.getElementById("seating");
				lo_totalSeating			= document.getElementById("totalSeating");
				lv_startNo
				lo_nameInd              = document.getElementsByName("nameRowInd");
				lv_startNo              = document.getElementById("startNo").value; 
				lv_rows 			    = lo_rows.value;
		   	
				if((lo_rows.value != "0")) { 
					//alert("lo_rows.value != 0");
		   			document.getElementById("nameRowInd2").checked = true;
					document.getElementById("nameRowInd1").checked = false; 
					//document.getElementById("nameRow").style.readonly = true;
					$('#nameRowInd1').prop("disabled",true); 
					$('#nameRowInd2').prop("disabled",true); 
					$("#nameRow").attr('disabled','disabled');
				}else if(lv_rows== "0"){ 
					//alert("lo_rows.lv_rows ==0");
					document.getElementById("nameRowInd1").style.readonly = false;
					document.getElementById("nameRowInd2").style.readonly = false;
					document.getElementById("nameRowInd1").checked = true;
					document.getElementById("nameRowInd2").checked = false;
					document.getElementById("nameRow").value = ""; 
				    document.getElementById("seating").value = "0";
					document.getElementById("startNo").value = "0";
					document.getElementById("totalSeating").value = "0";
					$('#nameRow').removeProp("disabled"); 
				} 
				
				lv_seating 			    = lo_seating.value;
				lv_totalSeating			= lv_rows * lv_seating;  
				lo_totalSeating.value 	= lv_totalSeating;  
				generateNameOfRows();
				 
				
			}catch(e){
				alert("lp_calTotalSeating :: " + e);
			}
			
		}
		
		function lp_onclick_nameInd(){
			var lo_nameInd1  = document.getElementById("nameRowInd1");
			var lo_nameInd2  = document.getElementById("nameRowInd2");
	 
			 if(lo_nameInd2.checked == true){  
				document.getElementById("nameRow").value = ""; 
			}else if(lo_nameInd1.checked == true){  
				generateNameOfRows(); 
			} 
		}
		
		function  generateNameOfRows(){
		        var name        = [];
		        var nameOfRow   = "";
		        var lv_rows	    = null;
		        var lo_nameRow  = null;
		  
		        try{ 
		           name         = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
		           lv_rows	    = document.getElementById("rows").value;
		           lo_nameRow   = document.getElementById("nameRow");
		           
		           if(lv_rows > 0 && lv_rows <=26){
		   			document.getElementById("nameRowInd2").checked = false;
					document.getElementById("nameRowInd1").checked = true;
		            // alert(lv_rows);
		        	  for(var i = 0 ; i < lv_rows ; i ++){
		                  //alert(name[i]);
		        		  if(i==(lv_rows-1)){
		        			  nameOfRow += name[i];
		        		  }else{
		        			  nameOfRow += name[i] +",";
		        		  }
		        	  }
		        
		        	  lo_nameRow.value = nameOfRow; 
		           }else{
		        	   $('#nameRow').prop("disabled",false); 
		           }
		        } catch (e) {
		            alert("generateNameOfRows : "+e);
		        }
		            
		    }
		
		function lp_validate_nameOfRows(ao_obj){ 
			var lo_nameInd2  = document.getElementById("nameRowInd2");
			var lv_rows		= document.getElementById("rows").value;
			
			if(lo_nameInd2.checked == true){ 
				var lv_value = ao_obj.value;
				var lv_array = null;
			//alert(lv_value); 
				try{  
				 	if(lv_value != "" || lv_rows != 0){ 
						lv_array = lv_value.split(","); 
					    //alert(lv_array.length);
						if(lv_array.length != lv_rows){
							alert("จำนวนชื่อของแถวจะต้องมีจำนวนเท่ากับ จำนวนแถว");
							return;
						}
					}
				}catch (e) {
					// TODO: handle exception
					alert("lp_validate_nameOfRows :: " + e);
				}
			}
		}
		function lp_onBlurFormatNumber(ao_obj){
	        var lo_size 		    = null;
	        var lv_size             = null;
	         
			try{  
				lo_size 	= ao_obj;
				lv_size     = lo_size.value;
				
				if(gp_trim(lv_size)==""){
					lo_size.value = "0";
				}
				
				if(gp_format(lo_size, 2)==false){
					alert("กรุณาระบุตัวเลขเท่านั้น");
					lo_size.value = "0";
					return;
				}
				 
				
			}catch(e){
				alert("lp_onBlurFormatNumber :: " + e);
			}
		}
		
		function lp_check_data(){ 
			var lv_fieldZoneName	= document.getElementById("fieldZoneName").value;
			var lv_nameTicket		= document.getElementById("nameTicket").value;
			var lv_rows 			= document.getElementById("rows").value;
			var lv_seating 			= document.getElementById("seating").value;
			var lv_startNo 			= document.getElementById("startNo").value; 
			
			//(lv_rows=="0") && (lv_seating=="0") && (lv_startNo=="0") &&  
			if((lv_fieldZoneName!="") && (lv_nameTicket!="")){ 
					$("#btnSave").removeAttr('disabled');
		        	$("#btnCancel").removeAttr('disabled');
		        	$("#btnAdd").removeAttr('disabled');
			}else{
				$("#btnSave").attr('disabled','disabled');
				$("#btnCancel").attr('disabled','disabled');
				$("#btnAdd").attr('disabled','disabled');
			}
		}
  
		function lp_onblur_check_zone(){ 
			lv_zone     = document.getElementById("fieldZoneName").value; 
			lo_table 	= document.getElementById("result_zone");
             //alert("lv_zone ::"+ lv_zone);	 
             
        	lv_length 	= lo_table.rows.length;
        	
        	for(var i =0 ; i < lv_length ; i ++){
            // alert(gp_trim(lo_table.rows[i].cells[0].innerHTML));
        		if(gp_trim(lv_zone) == gp_trim(lo_table.rows[i].cells[0].innerHTML)){
        			alert("ไม่สามารถระบุชื่อ zone ซ้ำได้");
        			break;
        		}
        	}
        	
        	
		}
		
		function lp_check_fieldNumber(){
			var lv_rows 			= document.getElementById("rows").value;
			var lv_seating 			= document.getElementById("seating").value;
			var lv_startNo 			= document.getElementById("startNo").value; 
			var lo_rowName 			= document.getElementById("nameRow");
			
			if((lv_rows<=0) && (lv_seating<=0) && (lv_startNo<=0) ) {
				$("#nameRow").attr('disabled','disabled'); 
				$("#nameRowInd").attr('disabled','disabled'); 
	   			document.getElementById("nameRowInd2").checked = true;
				document.getElementById("nameRowInd1").checked = falsse;
			} 
					
		}
		
		
	</script>
</head>

<body >	
	<form id="frm">  
		<input type="hidden" id="service" 	name="service" value="servlet.SeatingDetailServlet" />
		<input type="hidden" id="hidZoneName" name="hidZoneName" value="<%=seatingDetailForm.getFieldzonemasterName()%>" />  
		<input type="hidden" id="hidZoneId"   name="hidZoneId" value="<%=seatingDetailForm.getFieldZoneId()%>" />  
		<input type="hidden" id="hidUserLevel" name="hidUserLevel" value="<%=seatingDetailForm.getUserLevel()%>">
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
								<div class="row" style="padding-left: 15px;">
									<div class="col-sm-12">
										<section class="panel panel-default">
											<div class="panel-body">
						        				<table style='width:100%;' border="0">
													<tr>
					              					<td style='width:10%;padding:0px;vertical-align: top;'>
					              						<table>
					              							<tr>
					              								<td>   
													                 <td style='width:10%;padding:0px !important'> 
												                      <table class="table sim-panel-result-table" id="result_zone">
																		<tr><th>Zone ที่นั่ง</th> </tr>
																		<%
																		List<FieldzonemasterBean>  list	=  seatingDetailForm.getZoneMasterList();
																		 
																		if(list.size()>0){
																			for(int i=0;i<list.size();i++){ 
																				%>
																				 <tr> 
																				 	<%if(list.get(i).equals(seatingDetailForm.getFieldzonemasterName())){%>
																				 		<td class="unLink" align="center" title="<%=list.get(i).getFieldZoneName()%>" >
																							<%=list.get(i).getFieldZoneName()%>
																						</td>	
																				 	<%}else{%>
																				 		<td class="link" onclick="lp_changeZone('<%=list.get(i).getFieldZoneName()%>','<%=list.get(i).getFieldZoneId()%>');"  align="center" title="<%=list.get(i)%>">
																						   <%=list.get(i).getFieldZoneName()%>
																						</td>
																				 	<%}%>
																				</tr> 
																				<% } 
																			} %>
															 			<tr><td align="center" class="link" onclick="lp_add_row_zone();" class="btn btn-success"><a href="#">เพิ่ม zone ที่นั่ง</a></tr>
															 		</table>
					              								</td>
					              							</tr>
					              						</table>
									   				</td>
						                   				<td style='width:100%;padding:0px !important;text-align: center;vertical-align: top;'>
						                   					<table width="100%" border="0" cellpadding="5" cellspacing="5" id="result_zone_master">
						                   					 <% 
						                   					    FieldzonemasterBean	master	= seatingDetailForm.getFieldzonemasterBean(); 
															 %>
						                   						<tr>
						                   							<td align="right" width="250px;">ชื่อ Zone ที่นั่ง : &nbsp;</td>
						                   							<td align="left" width="200px;">
												        				<input type='text' 
												        					id="fieldZoneName" 
												        					name='fieldZoneName' 
												        					maxlength="10"  
												        					value="<%=master.getFieldZoneName()%>"
												        					onblur="lp_check_data(),lp_onblur_check_zone();"/> 
												        			    <input type="hidden" 
												        			    		name="fieldZoneMasterId" 
												        			    		id="fieldZoneMasterId"  
												        			    		value="<%=master.getFieldZoneId()%>"> 
												        			    &nbsp;<span style="color: red;"><b>*</b></span> 
												        			</td>
												        			<td align="right" width="200px;">ชื่อ Zone ที่นั่ง บนตั๋ว: &nbsp;</td>
						                   							<td align="left" colspan="3" width="600px;">
												        				<input type='text' 
												        					id="nameTicket" 
												        					name='nameTicket' 
												        					maxlength="10"  
												        					onblur="lp_check_data();"
												        					value="<%=master.getFieldZoneNameTicket()%>"/>  
												        				&nbsp;<span style="color: red;"><b>*</b></span>
												        			</td>
						                   						</tr>
												        		<tr>
												        			<td align="right" width="250px;">จำนวนแถว : &nbsp;</td>
												        			<td align="left"  width="200px;">
												        				<input type='text' id="rows" 
												        				       name='rows' maxlength="50"      
												        				       class="numberOnly" 
                                                     						   value="<%=master.getRows()%>" 
                                                     						   onblur="lp_calTotalSeating();onKeyDownNumber(event),lp_check_data()"/>
                                                     						   &nbsp;<span style="color: red;"><b>*</b></span>
												        			</td>
												        			<td align="right" width="200px;">จำนวนที่นั่งต่อแถว : &nbsp;</td>
												        			<td align="left"  width="200px;">
												        				<input  type='text' 
												        						id="seating" 
												        						name="seating" 
												        						maxlength="20"  
												        					    class="numberOnly" 
												        						value="<%=master.getSeating()%>"
												        						onblur="lp_calTotalSeating(),lp_check_data();"/>
												        						&nbsp;<span style="color: red;"><b>*</b></span>
												        			</td>
												        			<td align="right" width="200px;">เลขที่นั่งเริ่มต้น : &nbsp;</td>
												        			<td align="left" width="200px;">
												        				<input  type='text' 
												        						id="startNo" 
												        						name="startNo" 
												        						maxlength="20"  
												        					    class="numberOnly" 
												        					    onblur="lp_check_data(),lp_calTotalSeating();"
												        						value="<%=master.getStartSeatingNo()%>" />
												        						&nbsp;<span style="color: red;"><b>*</b></span>
												        			</td>
												        		</tr>
												        		<tr>
												        			<td align="right" width="250px;">จำนวนที่นั่งทั้งหมดใน Zone : &nbsp;</td>
												        			<td align="left" colspan="5">
												        				<input type='text' 
												        					id="totalSeating" 
												        					name='totalSeating' 
												        					maxlength="20" 
												        					class="inputDisabled" 
												        					disabled="disabled"  
												        					value="<%=master.getTotalSeating()%>"/>
												        			</td>
												        		</tr>
												        		<tr>
												        			<td align="right" width="250px;">ชื่อแถว: &nbsp;</td>
												        			<td align="left">
												        			 <input type='radio' 
												        					id="nameRowInd1" 
												        					name='nameRowInd' 
												        		            <%if(master !=null && master.getTypeRowName()==1 ){%> checked="checked" <%} %>  
												        					value="1" 
												        					onclick="lp_onclick_nameInd();"/>ตามตัวอักษร A-Z 
												        			</td>
												        			<td align="left">
												        			 <input type='radio' 
												        			 		id="nameRowInd2" 
												        			 		name='nameRowInd'  
												        	                <%if(master !=null && master.getTypeRowName()==2 ){%> checked="checked" <%} %> 
												        			 		value="2" 
												        			 		onclick="lp_onclick_nameInd();"/>ตั้งชื่อแถวเอง
												        			 		</td>
												        		</tr>
												        		<tr></tr>
												        		<tr> 
												        			<td align="right" width="250px;">รายชื่อแถว : &nbsp;</td>
												        			<td align="left"  colspan="3">
												        				<textarea   id="nameRow" 
												        							name='nameRow'
																					rows="4" 
																					cols="70" 
																					onblur="lp_check_data();"
																					value="<%=master.getNameRow()%>"
																					onblur="lp_validate_nameOfRows(this);" ><%=master.getNameRow()%> 
																		</textarea>
																		&nbsp;<span style="color: red;"><b>*</b></span>
												        			</td>
												        		</tr>
												        	</table>
						                  	 				<table class="table sim-panel-result-table" id="result_zone_detail">
																	<tr >
											                            <th align="center">ลำดับ</th>
											                            <th align="center">ประเภทตั๋ว</th> 
											                            <th align="center">ราคา</th> 
											                            <th align="center">การทำงาน</th>
										                          	</tr>
																<%
																	List<FieldZoneDetailBean>  	fieldZoneDetail	    = seatingDetailForm.getFieldZoneDetailBeans(); 
																	FieldZoneDetailBean         fieldZoneDetailBean = null;
																	int						    seq					= 0;
																				 
																	if(fieldZoneDetail.size()>0){
																		for(int i=0;i<fieldZoneDetail.size();i++){ 
																			fieldZoneDetailBean = fieldZoneDetail.get(i); 
																			System.out.print("seq is  : "+fieldZoneDetail.get(i).getSeq());
																			seq++;
																	  %>
																		 <tr>
																			<td align="center">
																				<%=seq%>  
																			</td>
																			<td align="center">
																				<input  type="text" 
																						id="bookingTypeName" 
																						name="bookingTypeName" 
																						class="bookingTypeNameClass" 
																					    onblur="lp_onblur_bookingTypeName(this)"  
																						value="<%=fieldZoneDetailBean.getBookingTypeName()%>"/> 
																			</td>
																			<td align="center">
																				<input type="text" 
																					   id="bookingTypePrice" 
																					   name="bookingTypePrice"  
																					   class="bookingTypePriceClass" 
																					   onclick="lp_onblur_validate(this),lp_onBlurFormatNumber(this);"  
																					   value="<%=fieldZoneDetailBean.getBookingPrices()%>"/>
																			</td> 
																			<td style="text-align: center;">  
													                           	 <input type="button" 
													                           	 		class="btn action-del-btn btn-danger" 
													                           	 		style="text-align: center;"  
													                           	 		ondblclick="return false;" 
													                           	 		onclick="lp_del_row_table(this)" 
													                           	 		value="-"/>
									                           	 				 <input type="hidden" 
									                           	 				 		name="hidStartus" 
									                           	 				 		id="hidStartus"  
									                           	 				 		value="U"/>   
									                           	 				 <input type="hidden" 
									                           	 				 		name="fieldZoneId" 
									                           	 				 		id="fieldZoneId"  
									                           	 				 		value="<%=fieldZoneDetailBean.getFieldZoneId()%>"/>  
									                           	 				 <input  type="hidden" 
																						name="seq" 
																						id="seq"  
																						value="<%=fieldZoneDetailBean.getSeq()%>"/>
																				 <input type="hidden" 
									                           	 				 		name="hidSeq" 
									                           	 				 		id="hidSeq"  
									                           	 				 		value="<%=fieldZoneDetailBean.getSeq()%>"/> 
												                            </td>
																		</tr> 
																		<% } 
																	} %>
										       						<tr>
															 			<td style="visibility:hidden;"></td>
																		<td style="visibility:hidden;"></td>
																		<td style="visibility:hidden;"></td> 
																		<td style="text-align: center;"> 
												                       		 <input type="button" 
												                       				id="btnAdd"  
												                       				class="btn action-add-btn btn-success" 
												                       				style="text-align: center;"  
												                       				ondblclick="return false;" 
												                       				onclick="lp_add_row_zoneDetail();" value="+"/>
												                       		 <input type="hidden" 
												                       		 		name="hidStartus" 
												                       		 		id="hidStartus"  
												                       		 		value=""/>  
											                       	 	</td>
																	</tr> 
																</table>
																<div> 
											        				<input  type="button"  
											        						ondblclick="return false;" 
											        						id="btnSave" 
											        						name="btnSave" 
											        						value="บันทึก"  
											        						class="btn btn-success" 
											        						onclick="lp_save_page();"/> &nbsp;&nbsp;&nbsp;
											        				<button ondblclick="return false;" 
											        						id="btnCancel" 
											        						name="btnCancel"   
											        						class="btn btn-danger"  
											        						onclick="lp_reset_page();">ยกเลิก</button> 
																</div>
				                   						</td>
				                 					</tr>
				             					</table>
				             				</div>	
										</section>
									</div>
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